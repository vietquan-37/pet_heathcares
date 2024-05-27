package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Services;
import vietquan37.com.example.projects.payload.request.ServiceDTO;
import vietquan37.com.example.projects.payload.response.ServiceResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class ServiceMapperImpl implements ServiceMapper {

    @Override
    public Services mapDto(ServiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Services.ServicesBuilder services = Services.builder();

        services.name( dto.getName() );
        services.type( dto.getType() );
        services.price( dto.getPrice() );

        services.updatedAt( java.time.LocalDateTime.now() );
        services.createdAt( java.time.LocalDateTime.now() );
        services.deleted( false );

        return services.build();
    }

    @Override
    public ServiceResponse mapToAllServiceDto(Services service) {
        if ( service == null ) {
            return null;
        }

        ServiceResponse.ServiceResponseBuilder serviceResponse = ServiceResponse.builder();

        serviceResponse.id( service.getId() );
        serviceResponse.name( service.getName() );
        serviceResponse.type( service.getType() );
        serviceResponse.price( service.getPrice() );
        serviceResponse.updatedAt( service.getUpdatedAt() );
        serviceResponse.createdAt( service.getCreatedAt() );
        serviceResponse.deleted( service.isDeleted() );

        return serviceResponse.build();
    }

    @Override
    public ServiceResponse mapToAllServiceDtoForUser(Services service) {
        if ( service == null ) {
            return null;
        }

        ServiceResponse.ServiceResponseBuilder serviceResponse = ServiceResponse.builder();

        serviceResponse.id( service.getId() );
        serviceResponse.name( service.getName() );
        serviceResponse.type( service.getType() );
        serviceResponse.price( service.getPrice() );
        serviceResponse.createdAt( service.getCreatedAt() );
        serviceResponse.updatedAt( service.getUpdatedAt() );
        serviceResponse.deleted( service.isDeleted() );

        return serviceResponse.build();
    }

    @Override
    public void updateServiceFromDto(ServiceDTO dto, Services service) {
        if ( dto == null ) {
            return;
        }

        service.setName( dto.getName() );
        service.setType( dto.getType() );
        service.setPrice( dto.getPrice() );

        service.setUpdatedAt( java.time.LocalDateTime.now() );
    }
}
