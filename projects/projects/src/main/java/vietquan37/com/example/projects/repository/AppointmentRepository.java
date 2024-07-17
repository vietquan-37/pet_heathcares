package vietquan37.com.example.projects.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vietquan37.com.example.projects.entity.Appointment;
import vietquan37.com.example.projects.enumClass.AppointmentStatus;
import vietquan37.com.example.projects.enumClass.TimeFrame;


import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer>, PagingAndSortingRepository<Appointment, Integer> {
    Appointment findByPetIdAndAppointmentDate(Integer petId, LocalDate appointmentDate);
    Optional<Appointment> findByIdAndDeletedIsFalse(Integer id);
    Optional<Appointment> findByPaymentsPaymentId(String id);
    Page<Appointment> findAllByCustomerIdAndDeletedIsFalse(Integer customerId, Pageable pageable);
    Page<Appointment> findAllByDoctorIdAndDeletedIsFalseAndAppointmentStatus(Integer customerId, Pageable pageable,AppointmentStatus status);
    Page<Appointment> findAllByDeletedIsFalse( Pageable pageable);
    int countAppointmentByPetIdAndAppointmentDateAfter(Integer petId, LocalDate appointmentDate);
    int countAppointmentByDoctorIdAndTimeFrameAndAppointmentDateAndAppointmentStatus(Integer doctorId, TimeFrame timeFrame, LocalDate date, AppointmentStatus statuses);
}
