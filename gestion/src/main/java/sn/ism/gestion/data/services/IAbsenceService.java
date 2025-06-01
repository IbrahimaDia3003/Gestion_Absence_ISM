package sn.ism.gestion.data.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.AbsenceSimpleResponse;


public interface IAbsenceService extends Service<Absence> {

    Absence pointerEtudiantByMatricule(String sessionId, String matricule);
    Absence pointerEtudiant(String sessionId, String etudiantId);
    Absence createAbsence(AbsenceRequest object);
    AbsenceSimpleResponse getOne(String id);
    Page<AbsenceAllResponse> getAllAbsences(Pageable pageable);

}
