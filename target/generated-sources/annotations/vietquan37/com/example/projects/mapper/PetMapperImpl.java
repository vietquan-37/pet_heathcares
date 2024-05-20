package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Customer;
import vietquan37.com.example.projects.entity.Pet;
import vietquan37.com.example.projects.entity.User;
import vietquan37.com.example.projects.payload.request.PetDTO;
import vietquan37.com.example.projects.payload.response.PetResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class PetMapperImpl implements PetMapper {

    @Override
    public Pet mapDto(PetDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Pet.PetBuilder pet = Pet.builder();

        pet.name( dto.getName() );
        pet.species( dto.getSpecies() );
        pet.gender( dto.getGender() );
        pet.birthDate( dto.getBirthDate() );

        pet.updatedAt( java.time.LocalDateTime.now() );
        pet.createdAt( java.time.LocalDateTime.now() );
        pet.deleted( false );

        return pet.build();
    }

    @Override
    public PetResponse mapToPetResponse(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetResponse.PetResponseBuilder petResponse = PetResponse.builder();

        petResponse.id( pet.getId() );
        petResponse.name( pet.getName() );
        petResponse.species( pet.getSpecies() );
        petResponse.gender( pet.getGender() );
        petResponse.birthDate( pet.getBirthDate() );
        petResponse.updatedAt( pet.getUpdatedAt() );
        petResponse.createdAt( pet.getCreatedAt() );
        petResponse.deleted( pet.isDeleted() );
        petResponse.ownerName( petCustomerUserFullName( pet ) );
        petResponse.imageUrl( pet.getImageUrl() );

        return petResponse.build();
    }

    @Override
    public PetResponse mapToPetResponseForUser(Pet pet) {
        if ( pet == null ) {
            return null;
        }

        PetResponse.PetResponseBuilder petResponse = PetResponse.builder();

        petResponse.id( pet.getId() );
        petResponse.name( pet.getName() );
        petResponse.species( pet.getSpecies() );
        petResponse.gender( pet.getGender() );
        petResponse.birthDate( pet.getBirthDate() );
        petResponse.updatedAt( pet.getUpdatedAt() );
        petResponse.createdAt( pet.getCreatedAt() );
        petResponse.deleted( pet.isDeleted() );
        petResponse.imageUrl( pet.getImageUrl() );

        return petResponse.build();
    }

    @Override
    public Pet mapUpdateDto(PetDTO dto, Pet existingPet) {
        if ( dto == null ) {
            return existingPet;
        }

        existingPet.setName( dto.getName() );
        existingPet.setSpecies( dto.getSpecies() );
        existingPet.setGender( dto.getGender() );
        existingPet.setBirthDate( dto.getBirthDate() );

        existingPet.setUpdatedAt( java.time.LocalDateTime.now() );

        return existingPet;
    }

    private String petCustomerUserFullName(Pet pet) {
        if ( pet == null ) {
            return null;
        }
        Customer customer = pet.getCustomer();
        if ( customer == null ) {
            return null;
        }
        User user = customer.getUser();
        if ( user == null ) {
            return null;
        }
        String fullName = user.getFullName();
        if ( fullName == null ) {
            return null;
        }
        return fullName;
    }
}
