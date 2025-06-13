package sn.ism.gestion.mobile.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.data.entities.Pointages;
import sn.ism.gestion.data.entities.Vigile;
import sn.ism.gestion.data.services.ISessionCoursService;
import sn.ism.gestion.data.services.IVigileService;
import sn.ism.gestion.data.services.PointageService;
import sn.ism.gestion.mobile.dto.Request.EtudiantQrCodeRequest;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;
import sn.ism.gestion.mobile.controllers.IVigileMobileController;
import sn.ism.gestion.mobile.dto.RestResponseMobile;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
public class VigileMobileController implements IVigileMobileController
{
    @Autowired private final ISessionCoursService sessionCoursService;
    @Autowired private final PointageService pointageService;
    @Autowired private final IVigileService  vigileService ;


    @Override
    public ResponseEntity<Map<String, Object>> SelectAll()
    {
        List<Vigile> vigiles = vigileService.findAll();
        return ResponseEntity.ok(RestResponseMobile.ofSuccess("VigilesList", vigiles));
    }

    /**
     * Pointage d'un étudiant par QR code.
     * Vérifie si l'étudiant est inscrit à une session du jour et si son paiement est valide.
     *
     * @param etudiantSession Contient le matricule de l'étudiant à pointer.
     * @return Réponse avec le statut du pointage.
     */
    @Override
    public ResponseEntity<Map<String, Object>> pointerEtudiant(String vigileId, EtudiantQrCodeRequest etudiantSession)
    {

        Pointages pointage = pointageService.createPointage(vigileId, etudiantSession);
        return ResponseEntity.ok(RestResponseMobile.of(HttpStatus.CREATED,"EtudiantPointe", pointage));


//        return ResponseEntity.ok(RestResponseMobile.ofSuccess("EtudiantPointe", true));

        // Récupère toutes les sessions du jour avec les infos étudiant
//        List<SessionEtudiantQrCodeMobileResponse> sessions = sessionCoursService.getSessionsDuJourWithEtudiant();
//
//        // Trouver une session qui correspond parfaitement aux données envoyées
//        SessionEtudiantQrCodeMobileResponse session = sessions.stream()
//                .filter(s ->
//                        Objects.equals(s.getMatricule(), etudiantSession.getMatricule()) &&
//                                Objects.equals(s.getCours(), etudiantSession.getCours()) &&
//                                Objects.equals(s.getDateSession(), etudiantSession.getDateSession()) &&
//                                Objects.equals(s.getHeureSession(), etudiantSession.getHeureSession())
//                )
//                .findFirst()
//                .orElse(null);
//
//        if (session == null) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT)
//                    .body(RestResponseMobile.ofError(HttpStatus.NO_CONTENT, "Aucune session trouvée pour cet étudiant avec les informations fournies"));
//        }
//
//        // Vérification du statut de paiement
//        String paiementStatut = session.getPaiementStatut() != null ? session.getPaiementStatut().name() : null;
//        if (paiementStatut == null || (!paiementStatut.equals("PAYE") && !paiementStatut.equals("PASSE"))) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT)
//                    .body(RestResponseMobile.ofError(HttpStatus.NO_CONTENT, "Paiement non valide"));
//        }
//
//        // Enregistrement du pointage
//        pointageService.createPointage(vigileId, etudiantSession);
//
//        return ResponseEntity.ok(RestResponseMobile.ofSuccess("EtudiantPointe", true));
    }



    @Override
    public ResponseEntity<Map<String, Object>> getHistoriquePointages(String vigileId)
    {
        List<Pointages> pointages = pointageService.getPointagesByVigile(vigileId);
        return ResponseEntity.ok(RestResponseMobile.ofSuccess("HistoriquePointages", pointages));
    }


}
