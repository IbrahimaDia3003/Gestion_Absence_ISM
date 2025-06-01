package sn.ism.gestion.utils.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.JustificationSimpleResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T14:10:14+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class JustificationMapperImpl implements JustificationMapper {

    @Override
    public JustificationSimpleResponse toDto(Justification Justification) {
        if ( Justification == null ) {
            return null;
        }

        JustificationSimpleResponse justificationSimpleResponse = new JustificationSimpleResponse();

        justificationSimpleResponse.setAbsenceId( Justification.getAbsenceId() );
        justificationSimpleResponse.setCommentaire( Justification.getCommentaire() );
        justificationSimpleResponse.setFichierUrl( Justification.getFichierUrl() );
        justificationSimpleResponse.setStatut( Justification.getStatut() );
        justificationSimpleResponse.setDateSoumission( Justification.getDateSoumission() );

        return justificationSimpleResponse;
    }

    @Override
    public Justification toEntity(Justification request) {
        if ( request == null ) {
            return null;
        }

        Justification justification = new Justification();

        justification.setId( request.getId() );
        justification.setCreatedAt( request.getCreatedAt() );
        justification.setUpdatedAt( request.getUpdatedAt() );
        justification.setAbsenceId( request.getAbsenceId() );
        justification.setCommentaire( request.getCommentaire() );
        justification.setFichierUrl( request.getFichierUrl() );
        justification.setStatut( request.getStatut() );
        justification.setDateSoumission( request.getDateSoumission() );

        return justification;
    }

    @Override
    public Justification toEntityR(JustificationRequest request) {
        if ( request == null ) {
            return null;
        }

        Justification justification = new Justification();

        justification.setCommentaire( request.getCommentaire() );
        justification.setFichierUrl( request.getFichierUrl() );
        justification.setStatut( request.getStatut() );

        return justification;
    }
}
