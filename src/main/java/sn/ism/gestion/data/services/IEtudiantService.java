package sn.ism.gestion.data.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.EtudiantAllResponse;
import sn.ism.gestion.web.dto.Response.EtudiantSimpleResponse;


public interface IEtudiantService extends Service<Etudiant>{
     
    Page<Absence> getAbsencesByEtudiantId(String etudiantId, Pageable pageable);
    EtudiantSimpleResponse getOne(String id);
    Etudiant createEtudiant(EtudiantSimpleRequest etudiantSimpleRequest) ;
    Etudiant getByMatricule(String matricule);
    Absence justifierAbsence(String absenceId, JustificationRequest justificatif);
    Page<EtudiantAllResponse> getAllEtudiants(Pageable pageable);
    EtudiantSimpleResponse findByMat(String matricule);

    }


