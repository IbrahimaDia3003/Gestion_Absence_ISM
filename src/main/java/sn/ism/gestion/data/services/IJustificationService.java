package sn.ism.gestion.data.services;

import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Request.JustificationValidationRequest;
import sn.ism.gestion.web.dto.Response.JusitficationAllResponse;
import sn.ism.gestion.web.dto.Response.JustificationSimpleResponse;

import java.util.List;

public interface IJustificationService extends Service<Justification> {
//    Justification createJustication(Justification justification);

    Justification traiterJustication(String absenceId, JustificationValidationRequest justificationRequest);

    List<JusitficationAllResponse> findAllResponse();

    JustificationSimpleResponse findByIdWitt(String id);

    JustificationSimpleResponse getJustificationByIdAbsenceId(String absenceId);
}
