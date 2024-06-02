package vietquan37.com.example.projects.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import vietquan37.com.example.projects.entity.Cage;
import vietquan37.com.example.projects.payload.request.CageDTO;
import vietquan37.com.example.projects.payload.response.CageResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 18.0.2.1 (Oracle Corporation)"
)
@Component
public class CageMapperImpl implements CageMapper {

    @Override
    public Cage map(CageDTO cageDTO) {
        if ( cageDTO == null ) {
            return null;
        }

        Cage.CageBuilder cage = Cage.builder();

        cage.cageNumber( cageDTO.getCageNumber() );
        cage.cageStatus( cageDTO.getCageStatus() );
        cage.capacity( cageDTO.getCapacity() );

        cage.updatedAt( java.time.LocalDateTime.now() );
        cage.createdAt( java.time.LocalDateTime.now() );
        cage.deleted( false );

        return cage.build();
    }

    @Override
    public void updateMap(CageDTO DTO, Cage cage) {
        if ( DTO == null ) {
            return;
        }

        cage.setCapacity( DTO.getCapacity() );
        cage.setCageNumber( DTO.getCageNumber() );
        cage.setCageStatus( DTO.getCageStatus() );

        cage.setUpdatedAt( java.time.LocalDateTime.now() );
    }

    @Override
    public CageResponse mapResponse(Cage cage) {
        if ( cage == null ) {
            return null;
        }

        CageResponse.CageResponseBuilder cageResponse = CageResponse.builder();

        cageResponse.id( cage.getId() );
        cageResponse.cageNumber( cage.getCageNumber() );
        cageResponse.cageStatus( cage.getCageStatus() );
        cageResponse.updatedAt( cage.getUpdatedAt() );
        cageResponse.createdAt( cage.getCreatedAt() );
        cageResponse.deleted( cage.isDeleted() );
        cageResponse.capacity( cage.getCapacity() );

        return cageResponse.build();
    }
}
