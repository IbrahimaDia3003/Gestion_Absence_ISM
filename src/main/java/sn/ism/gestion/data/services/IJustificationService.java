package sn.ism.gestion.data.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Request.JustificationValidationRequest;
import sn.ism.gestion.web.dto.Response.JusitficationAllResponse;

import java.util.Optional;

public interface IJustificationService extends Service<Justification> {
    Justification createJustication(JustificationRequest justificationRequest);
    Justification traiterJustication(String absenceId , JustificationValidationRequest justificationRequest);
    Page<JusitficationAllResponse> findAllWith(Pageable pageable);
    Optional<Justification> findJustificationById(String id);
    Justification findJustificationByAbsenceId(String absenceId);
}
