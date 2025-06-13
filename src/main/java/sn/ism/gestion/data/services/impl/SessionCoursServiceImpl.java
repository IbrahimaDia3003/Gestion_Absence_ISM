package sn.ism.gestion.data.services.impl;

import java.time.LocalDate;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.data.enums.StatusPaiment;
import sn.ism.gestion.data.repositories.*;
import sn.ism.gestion.data.services.ISessionCoursService;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;

@Service
public class SessionCoursServiceImpl implements ISessionCoursService {

    @Autowired private SessionsCoursRepository sessionCoursRepository;
    @Autowired private EtudiantRepository etudiantRepository;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private PaiementRepository paiementRepository;
    @Autowired private SessionsCoursRepository sessionsCoursRepository;

    @Override
    public SessionCours create(SessionCours object) {
        return sessionCoursRepository.save(object);
    }

    @Override
    public SessionCours update(String id, SessionCours object) {
        SessionCours existing = sessionCoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session non trouvée"));
        object.setId(existing.getId());
        return sessionCoursRepository.save(object);
    }

    @Override
    public boolean delete(String id) {
        if (!sessionCoursRepository.existsById(id)) return false;
        sessionCoursRepository.deleteById(id);
        return true;
    }


    @Override
    public List<SessionCours> findAll() {

        return sessionCoursRepository.findAll();
    }


    @Override
    public SessionCours findById(String id)
    {
        return sessionCoursRepository.findById(id).get();
    }

//    @Scheduled(cron = "0 0 6-18 * * *")
    @Override
    public List<SessionEtudiantQrCodeMobileResponse> getSessionsDuJourWithEtudiant()
    {
        LocalDate aujourdHui = LocalDate.now();
        List<SessionCours> sessionsDuJours = sessionCoursRepository.findSessionCoursByDateSession(aujourdHui);
        if (sessionsDuJours.isEmpty()) {
            return Collections.emptyList();
        }

        List<SessionEtudiantQrCodeMobileResponse> reponses = new ArrayList<>();

        for (SessionCours session : sessionsDuJours) {
            Optional<Classe> optClasse = classeRepository.findById(session.getClasseId());
            if (optClasse.isEmpty()) continue;

            Classe classe = optClasse.get();
            List<Etudiant> etudiants = etudiantRepository.findByclasseId(classe.getId());

            for (Etudiant etudiant : etudiants) {
                Optional<Utilisateur> optUtilisateur = utilisateurRepository.findById(etudiant.getUtilisateurId());
                if (optUtilisateur.isEmpty()) continue;

                Utilisateur utilisateur = optUtilisateur.get();

                SessionEtudiantQrCodeMobileResponse dto = new SessionEtudiantQrCodeMobileResponse();
                dto.setSessionId(session.getId());
                dto.setNomComplet(utilisateur.getPrenom() + " " + utilisateur.getNom());
                dto.setMatricule(etudiant.getMatricule());
                dto.setCours(classe.getLibelle());
                dto.setHeureSession(session.getHeureDebut());
                dto.setDateSession(session.getDateSession()); // déjà LocalDate

                List<Paiement> paiements = paiementRepository.findAll();
                Optional<Paiement> paiement = paiements.stream()
                        .filter(paiement1 -> paiement1.getEtudiantId().equals(etudiant.getId())
                        ).findFirst();
                dto.setPaiementStatut(paiement.get().getStatus());
                reponses.add(dto);
            }
        }
        return reponses;


    }

    @Override
    public List<SessionCours> findSessionCoursByDateSession()
    {
        LocalDate aujourdHui = LocalDate.now();
       return sessionCoursRepository.findSessionCoursByDateSession(aujourdHui);
    }

//    @Override
//    public List<SessionCours> findSessionCoursByEtudiantId(String etudiantId)
//    {
//
//        return List.of();
//    }

    @Override
    public List<SessionAllResponse> getAllSessionCours(LocalDate date )
    {
        List<SessionCours> sessions = sessionCoursRepository.findByDate(date);

        if (sessions.isEmpty()) {
            return Collections.emptyList(); // retourne une liste vide plutôt que null
        }
        return sessions.stream().map(s -> {
            SessionAllResponse dto = new SessionAllResponse();
            dto.setId(s.getId());
            dto.setSalle(s.getSalleId());
            dto.setDate(s.getDateSession());
            dto.setHeureDebut(s.getHeureDebut());
            dto.setHeureFin(s.getHeureFin());
            dto.setMode(s.getMode());
            return dto;
        }).toList();
//        return sessions;
    }

}
