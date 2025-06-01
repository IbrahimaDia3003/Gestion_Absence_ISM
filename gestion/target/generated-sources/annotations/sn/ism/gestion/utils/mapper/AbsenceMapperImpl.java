package sn.ism.gestion.utils.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.enums.Situation;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.AbsenceSimpleResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T14:10:14+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class AbsenceMapperImpl implements AbsenceMapper {

    @Override
    public AbsenceAllResponse toDto(AbsenceAllResponse absence) {
        if ( absence == null ) {
            return null;
        }

        AbsenceAllResponse absenceAllResponse = new AbsenceAllResponse();

        absenceAllResponse.setId( absence.getId() );
        absenceAllResponse.setNonEtudiant( absence.getNonEtudiant() );
        absenceAllResponse.setPrenomEtudiant( absence.getPrenomEtudiant() );
        absenceAllResponse.setClasseEtudiant( absence.getClasseEtudiant() );
        absenceAllResponse.setSessionId( absence.getSessionId() );
        absenceAllResponse.setType( absence.getType() );
        absenceAllResponse.setJustifiee( absence.isJustifiee() );

        return absenceAllResponse;
    }

    @Override
    public AbsenceSimpleResponse toDtoAll(AbsenceSimpleResponse etudiant) {
        if ( etudiant == null ) {
            return null;
        }

        AbsenceSimpleResponse absenceSimpleResponse = new AbsenceSimpleResponse();

        absenceSimpleResponse.setId( etudiant.getId() );
        absenceSimpleResponse.setType( etudiant.getType() );
        absenceSimpleResponse.setJustifiee( etudiant.isJustifiee() );
        absenceSimpleResponse.setJustificationId( etudiant.getJustificationId() );
        absenceSimpleResponse.setSessionId( etudiant.getSessionId() );
        absenceSimpleResponse.setSessionDate( etudiant.getSessionDate() );
        absenceSimpleResponse.setHeureDebut( etudiant.getHeureDebut() );
        absenceSimpleResponse.setHeureFin( etudiant.getHeureFin() );
        absenceSimpleResponse.setNomEtudiant( etudiant.getNomEtudiant() );
        absenceSimpleResponse.setPrenomEtudiant( etudiant.getPrenomEtudiant() );
        absenceSimpleResponse.setClasseEtudiant( etudiant.getClasseEtudiant() );

        return absenceSimpleResponse;
    }

    @Override
    public Absence toEntity(Absence request) {
        if ( request == null ) {
            return null;
        }

        Absence absence = new Absence();

        absence.setId( request.getId() );
        absence.setCreatedAt( request.getCreatedAt() );
        absence.setUpdatedAt( request.getUpdatedAt() );
        absence.setEtudiantId( request.getEtudiantId() );
        absence.setSessionId( request.getSessionId() );
        absence.setType( request.getType() );
        absence.setJustifiee( request.isJustifiee() );
        absence.setJustificationId( request.getJustificationId() );
        absence.setHeurePointage( request.getHeurePointage() );

        return absence;
    }

    @Override
    public Absence toEntityR(AbsenceRequest request) {
        if ( request == null ) {
            return null;
        }

        Absence absence = new Absence();

        absence.setEtudiantId( request.getEtudiantId() );
        absence.setSessionId( request.getSessionId() );
        if ( request.getType() != null ) {
            absence.setType( Enum.valueOf( Situation.class, request.getType() ) );
        }
        absence.setJustifiee( request.isJustifiee() );

        return absence;
    }
}
