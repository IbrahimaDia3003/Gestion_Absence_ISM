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
import sn.ism.gestion.web.dto.Response.SessionSimpleResponse;

@Service
public class SessionCoursServiceImpl implements ISessionCoursService {

    @Autowired private SessionsCoursRepository sessionCoursRepository;
    @Autowired private EtudiantRepository etudiantRepository;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private PaiementRepository paiementRepository;
    @Autowired private CoursRepository coursRepository;
    @Autowired private SalleRepository salleRepository;


    @Override
    public SessionCours create(SessionCours object)
    {
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
                dto.setClasseLibelle(classe.getLibelle());
                dto.setHeureSession(session.getHeureDebut());
                dto.setDateSession(session.getDateSession()); // déjà LocalDate
                coursRepository.findById(session.getCoursId()).ifPresent(c ->
                {
                    dto.setCours(c.getLibelle());
                });
                salleRepository.findById(session.getSalleId()).ifPresent(salle -> {
                    dto.setSalleCours(salle.getNumero());
                });

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
    public SessionSimpleResponse findSessionCoursById(String id)
    {
        SessionCours session = sessionCoursRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session non trouvée"));
        SessionSimpleResponse response = new SessionSimpleResponse();
        response.setId(session.getId());
        response.setDate(session.getDateSession());
        response.setHeureDebut(session.getHeureDebut());
        response.setHeureFin(session.getHeureFin());
        response.setMode(session.getMode());
        coursRepository.findById(session.getCoursId()).ifPresent(c -> {
            response.setCoursLibelle(c.getLibelle());
        });
        classeRepository.findById(session.getClasseId()).ifPresent(classe -> {
            response.setClasseLibelle(classe.getLibelle());
        });
        salleRepository.findById(session.getSalleId()).ifPresent(salle -> {
            response.setSalleCours(salle.getNumero());
        });


        return response;
    }

    @Scheduled(cron = "0 0 17-21 * * *")
    public List<SessionEtudiantQrCodeMobileResponse> getEtudiantAndSessionCours()
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
                dto.setClasseLibelle(classe.getLibelle());
                dto.setHeureSession(session.getHeureDebut());
                dto.setDateSession(session.getDateSession()); // déjà LocalDate
                coursRepository.findById(session.getCoursId()).ifPresent(c ->
                {
                    dto.setCours(c.getLibelle());
                });
                salleRepository.findById(session.getSalleId()).ifPresent(salle -> {
                    dto.setSalleCours(salle.getNumero());
                });

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
    public List<SessionAllResponse> getAllSessionCoursDuJour()
    {
        LocalDate date = LocalDate.now();
        List<SessionCours> sessions = sessionCoursRepository.findSessionCoursByDateSession(date);

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
            coursRepository.findById(s.getCoursId()).ifPresent(c -> {
                dto.setCoursLibelle(c.getLibelle());
            });
            classeRepository.findById(s.getClasseId()).ifPresent(classe -> {
                dto.setClasseLibelle(classe.getLibelle());
            });
            salleRepository.findById(s.getSalleId()).ifPresent(salle -> {
                dto.setSalleCours(salle.getNumero());
            });

            return dto;
        }).toList();
    }



    @Override
    public List<SessionAllResponse> getAllSessionCours()
    {
        List<SessionCours> sessions = sessionCoursRepository.findAll();
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
            coursRepository.findById(s.getCoursId()).ifPresent(c ->
            {
                dto.setCoursLibelle(c.getLibelle());
            });
            classeRepository.findById(s.getClasseId()).ifPresent(classe -> {
                dto.setClasseLibelle(classe.getLibelle());
            });
            salleRepository.findById(s.getSalleId()).ifPresent(salle -> {
                dto.setSalleCours(salle.getNumero());
            });
            return dto;
        }).toList();
    }

}
