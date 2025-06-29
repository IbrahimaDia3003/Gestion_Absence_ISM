package sn.ism.gestion.data.services;

import java.time.LocalDate;
import java.util.List;

import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.SessionCours;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;
import sn.ism.gestion.web.dto.Response.SessionSimpleResponse;

public interface ISessionCoursService extends Service<SessionCours> {

    List<SessionAllResponse> getAllSessionCoursDuJour();
    List<SessionAllResponse> getAllSessionCours();
    List<SessionEtudiantQrCodeMobileResponse> getSessionsDuJourWithEtudiant();
    SessionSimpleResponse findSessionCoursById(String id);
//    List<SessionCours> findSessionCoursByEtudiantId(String etudiantId);

}
