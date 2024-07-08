package vietquan37.com.example.projects.service.impl;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vietquan37.com.example.projects.config.PayPalService;
import vietquan37.com.example.projects.entity.*;
import vietquan37.com.example.projects.enumClass.CageStatus;
import vietquan37.com.example.projects.enumClass.Role;
import vietquan37.com.example.projects.enumClass.ServiceTypes;
import vietquan37.com.example.projects.exception.OperationNotPermittedException;
import vietquan37.com.example.projects.exception.UserMistake;
import vietquan37.com.example.projects.mapper.HospitalizedPetMapper;
import vietquan37.com.example.projects.payload.request.HospitalizedPetDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetRecordDTO;
import vietquan37.com.example.projects.payload.request.UpdatePetServiceDTO;
import vietquan37.com.example.projects.payload.response.HospitalizedPetResponse;
import vietquan37.com.example.projects.payload.response.HospitalizedServiceResponse;
import vietquan37.com.example.projects.payload.response.PaymentResponse;
import vietquan37.com.example.projects.repository.*;
import vietquan37.com.example.projects.service.IHospitalizedPetService;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HospitalizedPetService implements IHospitalizedPetService {
    private final HospitalizedPetRepository repository;
    private final HospitalizedPetMapper mapper;
    private final PetRepository petRepository;
    private final DoctorRepository doctorRepository;
    private final ServiceRepository serviceRepository;
    private final HospitalizedPetServiceRepository hospitalizedPetServiceRepository;
    private final CageRepository cageRepository;
    private final PayPalService payPalService;
    private final PaymentRepository paymentRepository;
    private static final int MAX = 5;


    @Override
    public void addHospitalizedPet(HospitalizedPetDTO dto) throws UserMistake {
        var pet = petRepository.findByIdAndDeletedIsFalse(dto.getPetId()).orElseThrow(() -> new EntityNotFoundException("pet not found"));
        if (repository.findByPetIdAndDeletedIsFalseAndDischargeDate(pet.getId(), null).isPresent()) {
            throw new UserMistake("pet is already under treatment");
        }
        var doctor = doctorRepository.findById(dto.getDoctorId()).orElseThrow(() -> new EntityNotFoundException("doctor not found"));
        var cage = cageRepository.findByIdAndDeletedIsFalseAndCageStatus(dto.getCageId(), CageStatus.Available).orElseThrow(() -> new EntityNotFoundException("cage not found or it has been occupied"));
        HospitalizedPet hospitalizedPet = mapper.mapDto(dto);
        hospitalizedPet.setPet(pet);
        hospitalizedPet.setTotalPrice(BigDecimal.ZERO);
        hospitalizedPet.setCage(cage);
        hospitalizedPet.setDoctor(doctor);
        repository.save(hospitalizedPet);
        int currentOccupancy = repository.countHospitalizedPetByCageIdAndDischargeDateAndDeletedIsFalse(cage.getId(), null);
        if (currentOccupancy >= cage.getCapacity()) {
            cage.setCageStatus(CageStatus.Occupied);
            cageRepository.save(cage);
        }
    }

    @Override
    public void updateServiceForPet(Integer id, UpdatePetServiceDTO dto) throws  UserMistake {
        var hospitalizedPet = repository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("pet care not found"));
        List<HospitalizedPetServices> hospitalizedPetServices = new ArrayList<>();
        if (hospitalizedPet.getDischargeDate() != null) {
            throw new UserMistake("Cannot update service for pet have discharged");
        }
        for (Integer serviceId : dto.getServiceIds()) {
            Services service = serviceRepository.findByIdAndDeletedIsFalseAndType(serviceId, ServiceTypes.HOSPITALIZATION).orElseThrow(() -> new EntityNotFoundException("service not found"));
            HospitalizedPetServices hospitalizedPetService = new HospitalizedPetServices();
            hospitalizedPetService.setHospitalizedPet(hospitalizedPet);
            hospitalizedPetService.setService(service);
            hospitalizedPetService.setUsageDate(LocalDate.now());
            hospitalizedPetServices.add(hospitalizedPetService);
            hospitalizedPet.setTotalPrice(hospitalizedPet.getTotalPrice().add(service.getPrice()));
        }
        repository.save(hospitalizedPet);
        hospitalizedPetServiceRepository.saveAll(hospitalizedPetServices);
    }

    @Override
    public void deleteServiceForPet(Integer id) throws UserMistake {
        var service = hospitalizedPetServiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("service not found"));
        var hospitalizedPet = service.getHospitalizedPet();
        if (hospitalizedPet.getDischargeDate() != null) {
            throw new UserMistake("Cannot delete service for pet have discharged");
        }
        hospitalizedPet.setTotalPrice(hospitalizedPet.getTotalPrice().subtract(service.getService().getPrice()));
        hospitalizedPetServiceRepository.delete(service);
    }

    @Override
    public Page<HospitalizedPetResponse> getAllForDoctor(int page, Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        var doctor = doctorRepository.findByUser_Id(user.getId()).orElseThrow(() -> new EntityNotFoundException("doctor not found"));
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<HospitalizedPet> pet = repository.findAllByDeletedIsFalseAndDoctorId(pageable, doctor.getId());
        return pet.map(mapper::mapForDoctor);
    }


    @Override
    public HospitalizedPetResponse getById(Integer id) {
        var hospitalizedPet = repository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("pet care not found"));
        return mapper.mapForResponse(hospitalizedPet);
    }

    @Override
    public Page<HospitalizedPetResponse> getAllForStaff(int page) {
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<HospitalizedPet> pet = repository.findAllByDeletedIsFalse(pageable);
        return pet.map(mapper::mapForResponse);
    }


    @Override
    public void deleteHospitalizedPet(Integer id) throws OperationNotPermittedException {
        var hospitalizedPet = repository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("pet care not found"));
        if (hospitalizedPet.getDischargeDate() != null) {
            throw new OperationNotPermittedException("Cannot delete information when it have been discharged");
        }
        var cage = hospitalizedPet.getCage();
        int currentOccupancy = repository.countHospitalizedPetByCageIdAndDischargeDateAndDeletedIsFalse(cage.getId(), null);
        if (currentOccupancy >= cage.getCapacity()) {
            cage.setCageStatus(CageStatus.Available);
            cageRepository.save(cage);
        }
        hospitalizedPet.setDeleted(true);
        repository.save(hospitalizedPet);
    }


    @Override
    public List<HospitalizedPetResponse> getAllForCustomer(Authentication authentication) {
        User user = ((User) authentication.getPrincipal());
        List<HospitalizedPet> all = repository.findAllByPetCustomerUserIdAndDeletedIsFalse(user.getId());
        return all.stream().map(mapper::mapForResponse).collect(Collectors.toList());
    }

    @Override
    public Page<HospitalizedServiceResponse> getAllServiceById(Integer id, Authentication authentication, int page) throws OperationNotPermittedException {
        var hospitalizedPet = repository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("pet care not found"));
        User user = ((User) authentication.getPrincipal());
        Integer userId = hospitalizedPet.getPet().getCustomer().getUser().getId();
        if (!Objects.equals(userId, user.getId()) && !user.getRole().equals(Role.STAFF)) {
            throw new OperationNotPermittedException("You are not allow to view service");
        }
        if (page < 0) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, MAX);
        Page<HospitalizedPetServices> services = hospitalizedPetServiceRepository.findAllByHospitalizedPetId(id, pageable);
        return services.map(mapper::mapForService);
    }

    @Override
    public PaymentResponse payHospitalizedFee(Integer id, Authentication authentication) throws OperationNotPermittedException, PayPalRESTException, UserMistake {
        var hospitalizedPet = repository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("pet care not found"));
        User user = ((User) authentication.getPrincipal());
        Integer userId = hospitalizedPet.getPet().getCustomer().getUser().getId();
        if (!Objects.equals(userId, user.getId())) {
            throw new OperationNotPermittedException("You are not allow to pay that pet care");
        }
        if (hospitalizedPet.isPaid()) {
            throw new UserMistake("Cannot pay for the paid pet care");
        }
        if (hospitalizedPet.getDischargeDate() == null) {
            throw new UserMistake("Cannot pay for pets without discharge date");
        }
        return handlePayment(hospitalizedPet);
    }

    @Override
    public List<HospitalizedPetResponse> getAllHospitalizedPetByPetId(Authentication authentication, Integer id) throws OperationNotPermittedException {
        List<HospitalizedPet> hospitalizedPets = repository.findAllByPetIdAndDischargeDateIsNotNullAndDeletedIsFalse(id);
        User currentUser = (User) authentication.getPrincipal();

        if (!hospitalizedPets.isEmpty()) {
            User petOwner = hospitalizedPets.get(0).getPet().getCustomer().getUser();
            if (!Objects.equals(petOwner.getId(), currentUser.getId())) {
                throw new OperationNotPermittedException("You are not allowed to view this pet's care details.");
            }
        }

        return hospitalizedPets.stream().map(mapper::mapForResponse).collect(Collectors.toList());
    }


    @Override
    public void dischargeHospitalizedPet(Integer id, Authentication authentication) throws OperationNotPermittedException, UserMistake {
        var hospitalizedPet = repository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("pet care not found"));
        User user = ((User) authentication.getPrincipal());
        Integer userId = hospitalizedPet.getDoctor().getUser().getId();
        if (!Objects.equals(userId, user.getId())) {
            throw new OperationNotPermittedException("You are not allowed to discharge that pet care");
        }
        if (hospitalizedPet.getDischargeDate() != null) {
            throw new UserMistake("Cannot update information when it has been discharged");
        }

        var cage = hospitalizedPet.getCage();
        LocalDate admissionDate = hospitalizedPet.getAdmissionDate();
        LocalDate dischargeDate = LocalDate.now();
        long days = ChronoUnit.DAYS.between(admissionDate, dischargeDate);

        var service = serviceRepository.findByName("Overnight Stay").orElseThrow(() -> new EntityNotFoundException("Service not found"));

        for (int i = 0; i < days; i++) {
            LocalDate usageDate = admissionDate.plusDays(i);
            HospitalizedPetServices hospitalizedService = new HospitalizedPetServices();
            hospitalizedService.setHospitalizedPet(hospitalizedPet);
            hospitalizedService.setService(service);
            hospitalizedService.setUsageDate(usageDate);  // Set the usage date
            hospitalizedPetServiceRepository.save(hospitalizedService);
            hospitalizedPet.setTotalPrice(hospitalizedPet.getTotalPrice().add(service.getPrice()));
        }

        int currentOccupancy = repository.countHospitalizedPetByCageIdAndDischargeDateAndDeletedIsFalse(cage.getId(), null);
        if (currentOccupancy >= cage.getCapacity()) {
            cage.setCageStatus(CageStatus.Available);
            cageRepository.save(cage);
        }

        hospitalizedPet.setDischargeDate(dischargeDate);
        if (hospitalizedPet.getDischargeDate().equals(hospitalizedPet.getAdmissionDate())) {
            throw new UserMistake("Cannot discharge this pet");
        }

        repository.save(hospitalizedPet);
    }


    @Override
    public void updateHospitalizedPetForDoctor(Integer id, UpdatePetRecordDTO dto, Authentication authentication) throws OperationNotPermittedException, UserMistake {
        var hospitalizedPet = repository.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new EntityNotFoundException("pet care not found"));
        User user = ((User) authentication.getPrincipal());
        Integer userId = hospitalizedPet.getDoctor().getUser().getId();
        if (!Objects.equals(userId, user.getId())) {
            throw new OperationNotPermittedException("You are not allow to update that pet care");
        }
        if (hospitalizedPet.getDischargeDate() != null) {
            throw new UserMistake("Cannot update information when it have been discharged");
        }

        mapper.mapUpdatePetRecord(dto, hospitalizedPet);
        repository.save(hospitalizedPet);

    }

    private PaymentResponse handlePayment(HospitalizedPet hospitalizedPet) throws PayPalRESTException {
        try {
            Payment payment = payPalService.createPayment(hospitalizedPet.getTotalPrice().toString(), "HospitalizedPet payment");

            if (hospitalizedPet.getPayments() != null) {
                Payments oldPayments = hospitalizedPet.getPayments();
                hospitalizedPet.setPayments(null);
                repository.save(hospitalizedPet);

                paymentRepository.delete(oldPayments);
            }

            Payments newPayments = new Payments();
            newPayments.setPaymentId(payment.getId());
            paymentRepository.save(newPayments);

            hospitalizedPet.setPayments(newPayments);
            repository.save(hospitalizedPet);

            for (Links link : payment.getLinks()) {
                if ("approval_url".equals(link.getRel())) {
                    return PaymentResponse.builder().paymentUrl(link.getHref()).build();
                }
            }
        } catch (PayPalRESTException e) {
            throw new PayPalRESTException("Payment creation failed", e);
        }

        throw new PayPalRESTException("Payment creation failed");
    }

}
