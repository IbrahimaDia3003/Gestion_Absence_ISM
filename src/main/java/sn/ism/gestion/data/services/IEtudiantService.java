package sn.ism.gestion.data.services;

import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.mobile.dto.Request.EtudiantQrCodeRequest;
import sn.ism.gestion.mobile.dto.Response.AbsenceEtudiantResponse;
import sn.ism.gestion.mobile.dto.Response.SessionAllMobileResponse;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.EtudiantAllResponse;
import sn.ism.gestion.web.dto.Response.EtudiantSimpleResponse;

import java.util.List;
import java.util.Optional;


public interface IEtudiantService extends Service<Etudiant>{
     
    List<AbsenceEtudiantResponse> getAbsencesByEtudiantId(String etudiantId);
    EtudiantSimpleResponse getOne(String id);
    Etudiant createEtudiant(EtudiantSimpleRequest etudiantSimpleRequest) ;
    Etudiant getByMatricule(String matricule);
    Justification justifierAbsence(String absenceId, Justification justificatif);
    List<EtudiantAllResponse> getAllEtudiants();
    EtudiantSimpleResponse findByMat(String matricule);
    SessionEtudiantQrCodeMobileResponse findByQrCode(String matriculLe);
    List<SessionAllMobileResponse> getSessionCoursByEtudiantId(String etudiantId);
    List<Justification> getJustificationsByEtudiantId(String etudiantId);
    Optional<EtudiantQrCodeRequest> getEtudiantSessionPourPointage(String matricule);
}


