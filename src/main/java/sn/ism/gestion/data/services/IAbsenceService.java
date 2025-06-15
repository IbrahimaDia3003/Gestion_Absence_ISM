package sn.ism.gestion.data.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.AbsenceSimpleResponse;

import java.util.List;


public interface IAbsenceService extends Service<Absence>
{

    AbsenceSimpleResponse getOne(String id);
    List<AbsenceAllResponse> getAllAbsences();
    List<AbsenceAllResponse> getAbsencebySessionId(String sessionId);
    List<AbsenceAllResponse> getAbsencebyEtudiantId(String etudiantId);
}
