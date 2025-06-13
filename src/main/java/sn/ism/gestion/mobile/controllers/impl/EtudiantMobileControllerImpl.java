package sn.ism.gestion.mobile.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.data.repositories.PaiementRepository;
import sn.ism.gestion.data.services.IEtudiantService;
import sn.ism.gestion.data.services.IJustificationService;
import sn.ism.gestion.data.services.ISessionCoursService;
import sn.ism.gestion.mobile.dto.Response.AbsenceEtudiantResponse;
import sn.ism.gestion.mobile.dto.Response.JustificationSimpleMobileResponse;
import sn.ism.gestion.mobile.dto.Response.SessionAllMobileResponse;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;
import sn.ism.gestion.mobile.dto.RestResponseMobile;
import sn.ism.gestion.utils.mapper.AbsenceMapper;
import sn.ism.gestion.utils.mapper.EtudiantMapper;
import sn.ism.gestion.mobile.controllers.IEtudiantMobileController;
import sn.ism.gestion.utils.mapper.JustificationMapper;
import sn.ism.gestion.utils.mapper.SessionMapper;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.EtudiantAllResponse;
import sn.ism.gestion.web.dto.RestResponse;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class EtudiantMobileControllerImpl implements IEtudiantMobileController {

    @Autowired private IEtudiantService etudiantService;
    @Autowired private EtudiantMapper etudiantMapper;
    @Autowired private ISessionCoursService sessionCoursService;
    @Autowired private AbsenceMapper absenceMapper;
    @Autowired private IJustificationService justificationService;
    @Autowired private JustificationMapper justificationMapper;
    @Autowired private SessionMapper sessionMapper;
    @Autowired
    private PaiementRepository paiementRepository;


    @Override
    public ResponseEntity<Map<String, Object>> justifierAbsence(String id, JustificationRequest justification)
    {
        Justification justificationAbsence = etudiantService.justifierAbsence(id, justification.toJustification());

//        Justification justificationAdd = justificationMapper.toEntityR(justificationAbsence);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(RestResponseMobile.of(HttpStatus.CREATED, "JustificationAbsence", justificationAbsence));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getJustifications(String id)
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    {
        List<Justification> justifications = etudiantService.getJustificationsByEtudiantId(id);

        // À implémenter si nécessaire
        return ResponseEntity.ok(RestResponseMobile.ofSuccess("JustificationsEtudiant", justifications));

    }
    @Override
    public ResponseEntity<Map<String, Object>> getSessionCoursByEtudiantId(String id)
    {
        List<SessionAllMobileResponse> sessionCours = etudiantService.getSessionCoursByEtudiantId(id);

        return ResponseEntity.ok(RestResponseMobile.ofSuccess("SessionCoursEtudiants",sessionCours));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getJustificationById(String justificationId)
    {
        Justification justification = justificationService.findById(justificationId);
        if (justification == null)
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(RestResponseMobile.of(HttpStatus.NO_CONTENT, "JustificationNotFound", null));

        JustificationSimpleMobileResponse justificationDto = justificationMapper.toDtoMobile(justification);
        return ResponseEntity.ok(
                RestResponseMobile.ofSuccess("JustificationSimpleResponse", justificationDto));
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll()
    {
        List<EtudiantAllResponse> etudiants = etudiantService.getAllEtudiants();

        return new  ResponseEntity<>(RestResponse.response(HttpStatus.OK, etudiants, "JustificationAllResponse"), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getMyListAbsences(String id) {
        List<Absence> absences = etudiantService.getAbsencesByEtudiantId(id);
        List<AbsenceEtudiantResponse> absencesDto = absences.stream()
                .map(absence -> absenceMapper.toDtoMobile(absence))
                .toList();

        return ResponseEntity.ok(RestResponseMobile.ofSuccess("EtudiantListePointages", absencesDto));
    }

    @Override
    public ResponseEntity<Map<String, Object>> getQrCodeEtudiant(String matricule)
    {
        SessionEtudiantQrCodeMobileResponse sessionEtudiant = etudiantService.findByQrCode(matricule);

        if (sessionEtudiant == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(RestResponseMobile.of(HttpStatus.NO_CONTENT, "EtudiantSessionQrCodeResponse", null));
        }

        return ResponseEntity.ok(
                RestResponseMobile.ofSuccess("EtudiantSessionQrCodeResponse", sessionEtudiant)
        );
    }

}