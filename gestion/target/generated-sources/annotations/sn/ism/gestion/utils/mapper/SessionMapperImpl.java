package sn.ism.gestion.utils.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.ism.gestion.data.entities.SessionCours;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T14:10:14+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class SessionMapperImpl implements SessionMapper {

    @Override
    public SessionAllResponse toDto(SessionAllResponse sessionCours) {
        if ( sessionCours == null ) {
            return null;
        }

        SessionAllResponse sessionAllResponse = new SessionAllResponse();

        sessionAllResponse.setId( sessionCours.getId() );
        sessionAllResponse.setDate( sessionCours.getDate() );
        sessionAllResponse.setHeureDebut( sessionCours.getHeureDebut() );
        sessionAllResponse.setHeureFin( sessionCours.getHeureFin() );
        sessionAllResponse.setMode( sessionCours.getMode() );

        return sessionAllResponse;
    }

    @Override
    public SessionCours toEntity(SessionCours sessionCours) {
        if ( sessionCours == null ) {
            return null;
        }

        SessionCours sessionCours1 = new SessionCours();

        sessionCours1.setId( sessionCours.getId() );
        sessionCours1.setCreatedAt( sessionCours.getCreatedAt() );
        sessionCours1.setUpdatedAt( sessionCours.getUpdatedAt() );
        sessionCours1.setCoursId( sessionCours.getCoursId() );
        sessionCours1.setDate( sessionCours.getDate() );
        sessionCours1.setHeureDebut( sessionCours.getHeureDebut() );
        sessionCours1.setHeureFin( sessionCours.getHeureFin() );
        sessionCours1.setNombreHeures( sessionCours.getNombreHeures() );
        sessionCours1.setMode( sessionCours.getMode() );
        sessionCours1.setClasseId( sessionCours.getClasseId() );
        sessionCours1.setValide( sessionCours.isValide() );

        return sessionCours1;
    }
}
