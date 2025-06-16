package sn.ism.gestion.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.Pointages;
import sn.ism.gestion.data.enums.StatusPaiment;
import sn.ism.gestion.data.repositories.PointageRepository;
import sn.ism.gestion.data.services.ISessionCoursService;
import sn.ism.gestion.data.services.PointageService;
import sn.ism.gestion.mobile.dto.Request.EtudiantQrCodeRequest;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointageServiceimpl implements PointageService
{

    @Autowired
    private PointageRepository pointageRepository;
    @Autowired
    private ISessionCoursService sessionCoursService;
    @Override
    public Pointages createPointage(String vigileId, EtudiantQrCodeRequest etudiantQrCode)
    {

        List<SessionEtudiantQrCodeMobileResponse> sessions = sessionCoursService.getSessionsDuJourWithEtudiant();

        // Filtrer par matricule
        SessionEtudiantQrCodeMobileResponse session = sessions.stream()
                .filter(s -> s.getMatricule().equalsIgnoreCase(etudiantQrCode.getMatricule()))
                .findAny()
                .orElse(null);
        if (session == null)
            return null;



        // Vérifier le statut de paiement
//        StatusPaiment statut = session.getPaiementStatut();
//        if (!(statut == StatusPaiment.PAYE || statut == StatusPaiment.PASSE)) {
//            throw new RuntimeException("Étudiant non à jour dans son paiement");
//        }

        // Création et sauvegarde du pointage

        Pointages pointage = new Pointages();
        pointage.setSessionId(etudiantQrCode.getSessionId());
        pointage.setMatricule(session.getMatricule());
        pointage.setNomComplet(session.getNomComplet());
        pointage.setCours(session.getCours());
        pointage.setDateSession(session.getDateSession());
        pointage.setHeureSession(session.getHeureSession());
        pointage.setPaiementStatut(session.getPaiementStatut());
        pointage.setVigileId(vigileId);
        pointage.setClasseLibelle(session.getClasseLibelle());
        pointage.setSalleCours(session.getSalleCours());


        return pointageRepository.save(pointage);
    }

    @Override
    public List<Pointages> getPointagesByVigile(String vigileId)
    {
        if (vigileId != null && !vigileId.isEmpty())
            return pointageRepository.findPointagesByVigileId(vigileId);
        return null;
    }
}
