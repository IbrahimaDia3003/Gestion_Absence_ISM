package sn.ism.gestion.data.services.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.enums.Role;
import sn.ism.gestion.data.enums.Situation;
import sn.ism.gestion.data.enums.StatusPaiment;
import sn.ism.gestion.data.repositories.*;
import sn.ism.gestion.data.services.IEtudiantService;
import sn.ism.gestion.mobile.dto.Request.EtudiantQrCodeRequest;
import sn.ism.gestion.mobile.dto.Response.AbsenceEtudiantResponse;
import sn.ism.gestion.mobile.dto.Response.SessionAllMobileResponse;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.utils.mapper.EtudiantMapper;
import sn.ism.gestion.utils.mapper.UtilisateurMapper;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.EtudiantAllResponse;
import sn.ism.gestion.web.dto.Response.EtudiantSimpleResponse;


@Service
@RequiredArgsConstructor
public class EtudiantServiceImpl implements IEtudiantService {

    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private EtudiantRepository etudiantRepository;
    @Autowired private AbsenceRepository absenceRepository;
    @Autowired private UtilisateurMapper utilisateurMapper;
    @Autowired private EtudiantMapper etudiantMapper;
    @Autowired private JustificationRepository justificationRepository;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private FiliereRepository filiereRepository;
    @Autowired private SessionsCoursRepository sessionsCoursRepository;
    @Autowired private PaiementRepository paiementRepository;
    @Autowired private CoursRepository coursRepository;
    @Autowired private SalleRepository salleRepository;
    @Autowired private SessionCoursServiceImpl sessionCoursServiceImpl;


    public Etudiant createEtudiant(EtudiantSimpleRequest etudiantSimpleRequest) {
        var existingEtudiant = etudiantRepository.findByMatricule(etudiantSimpleRequest.getMatricule());
        if (existingEtudiant.isPresent()) {
            throw new EntityNotFoundExecption("Un étudiant avec ce matricule existe déjà");
        }
        Utilisateur utilisateur = utilisateurMapper.toEntity(etudiantSimpleRequest.getUtilisateurcreate());
        utilisateur.setRole(Role.ETUDIANT);
        utilisateur = utilisateurRepository.save(utilisateur);

        Etudiant etudiantCreate = etudiantMapper.toEntityR(etudiantSimpleRequest);
        etudiantCreate.setUtilisateurId(utilisateur.getId());
        return etudiantRepository.save(etudiantCreate);
    }


    @Override
    public Etudiant create(Etudiant object) {
        return null;
    }

    @Override
    public Etudiant update(String id, Etudiant object) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundExecption("Étudiant non trouvé"));
        etudiant.setMatricule(object.getMatricule());
        return etudiantRepository.save(etudiant);
    }

    @Override
    public boolean delete(String id) {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElse(null);
        if (etudiant != null) {
            etudiantRepository.delete(etudiant);
            return true;
        }
        return false;
    }

    @Override
    public Etudiant findById(String id) {
        return etudiantRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundExecption("Étudiant non trouvé"));
    }

    @Override
    public List<Etudiant> findAll()
    {
        return etudiantRepository.findAll();
    }


    @Override
    public Etudiant getByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new EntityNotFoundExecption("Étudiant avec ce matricule non trouvé"));
    }

    @Override
    public Justification justifierAbsence(String absenceId, Justification justification)
    {

        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new EntityNotFoundExecption("Absence non trouvée"));
        if (absence.getType()==Situation.PRESENT || absence.isJustifiee()) {
            throw new EntityNotFoundExecption("Pas une Absence ou déjà justifiée");
        }
        justification.setAbsenceId(absence.getId());
        absence.setJustifiee(true);
        absenceRepository.save(absence);
        return justificationRepository.save(justification);

    }

    @Override
    public List<EtudiantAllResponse> getAllEtudiants() {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        return etudiants.stream().map(e -> {
            EtudiantAllResponse dto = new EtudiantAllResponse();
            dto.setId(e.getId());
            dto.setMatricule(e.getMatricule());
            dto.setTelephone(e.getTelephone());
            Optional<Classe> optClasse = classeRepository.findById(e.getClasseId());
            Classe classe = optClasse.get();

            dto.setClasseLibelle(classe.getLibelle());
            utilisateurRepository.findById(e.getUtilisateurId()).ifPresent(u -> {
                dto.setNom(u.getNom());
                dto.setPrenom(u.getPrenom());
            });

            return dto;
        }).toList();
    }
    @Override
    public EtudiantSimpleResponse getOne(String id)
    {
        Etudiant etudiant = etudiantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun Étudiant trouvé"));

        Utilisateur utilisateur = utilisateurRepository.findById(etudiant.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Classe classe = classeRepository.findById(etudiant.getClasseId()).orElse(null);

        Filiere filiere = (classe != null && classe.getFiliereId() != null)
                ? filiereRepository.findById(classe.getFiliereId()).orElse(null)
                : null;

        List<Absence> absences = absenceRepository
                .findAbsenceByEtudiantIdAndType(etudiant.getId(),Situation.ABSENCE);

        List<AbsenceAllResponse> absenceResponses = absences.stream().map(abs -> {
            AbsenceAllResponse dtoAbsence = new AbsenceAllResponse();
            dtoAbsence.setId(abs.getId());
            dtoAbsence.setType(abs.getType());
            dtoAbsence.setJustifiee(abs.isJustifiee());
            sessionsCoursRepository.findById(abs.getSessionId()).ifPresent(s -> {
                dtoAbsence.setDate(s.getDateSession());
                coursRepository.findById(s.getCoursId()).ifPresent(c -> {
                    dtoAbsence.setSessionCourslibelle(c.getLibelle());
                });
            });

            dtoAbsence.setNonEtudiant(utilisateur.getNom());
            dtoAbsence.setPrenomEtudiant(utilisateur.getPrenom());
            dtoAbsence.setClasseEtudiant(etudiant.getClasseId());
            return dtoAbsence;
        }).toList();

        EtudiantSimpleResponse dto = new EtudiantSimpleResponse();
        dto.setId(etudiant.getId());
        dto.setMatricule(etudiant.getMatricule());
        dto.setTelephone(etudiant.getTelephone());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());
        classeRepository.findById(etudiant.getClasseId()).ifPresent(c -> {
            dto.setClasse(c.getLibelle());
        });

        dto.setAbsences(absenceResponses);

        if (filiere != null) {
            dto.setFiliere(filiere.getNom());
        }

        dto.setAbsences(absenceResponses);

        return dto;
    }

    @Override
    public EtudiantSimpleResponse findByMat(String matricule) {
        Etudiant etudiant = etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Aucun Etudiant trouvé"));

        Utilisateur utilisateur = utilisateurRepository.findById(etudiant.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        EtudiantSimpleResponse dto = new EtudiantSimpleResponse();
        dto.setId(etudiant.getId());
        dto.setMatricule(etudiant.getMatricule());
        dto.setTelephone(etudiant.getTelephone());
        // dto.setUtilisateurId(utilisateur.getId());
        // dto.setLogin(utilisateur.getLogin());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());

        return dto;
    }

    @Override
    public SessionEtudiantQrCodeMobileResponse findByQrCode(String matricule)
    {
        List<SessionEtudiantQrCodeMobileResponse> sessionEtudiantQrCode = sessionCoursServiceImpl.getEtudiantAndSessionCours();
        if (sessionEtudiantQrCode.isEmpty())
            return null; // Aucune session trouvée pour le jour


        return sessionEtudiantQrCode.stream().filter(
                session -> session.getMatricule().equals(matricule))
                .findFirst().orElseThrow(() ->
                 new EntityNotFoundExecption("Aucune session trouvée pour l'étudiant avec le matricule: " + matricule)
        );

    }

    @Override
    public List<SessionAllMobileResponse> getSessionCoursByEtudiantId(String etudiantId)
    {
        Optional<Etudiant> etudiant = etudiantRepository.findById(etudiantId);
        List<SessionCours> sessionCours = sessionsCoursRepository.findSessionCoursByClasseId(etudiant.get().getClasseId());

        if (sessionCours.isEmpty())
            return null;

        List<SessionAllMobileResponse> responseList = new ArrayList<>();
        for (SessionCours session : sessionCours) {
            SessionAllMobileResponse response = new SessionAllMobileResponse();
            response.setId(session.getId());
            response.setHeureDebut(session.getHeureDebut());
            response.setHeureFin(session.getHeureFin());
            response.setMode(session.getMode());
            response.setDate(session.getDateSession());
            coursRepository.findById(session.getCoursId()).ifPresent(c -> {
                response.setCoursLibelle(c.getLibelle());
            });
            coursRepository.findById(session.getCoursId()).ifPresent(c ->
            {
                response.setCoursLibelle(c.getLibelle());
            });
            classeRepository.findById(session.getClasseId()).ifPresent(classe -> {
                response.setClasseLibelle(classe.getLibelle());
            });;
            salleRepository.findById(session.getSalleId()).ifPresent(salle -> {
                response.setSalleCours(salle.getNumero());
            });



            responseList.add(response);
        }

        return responseList;

    }

    @Override
    public List<Justification> getJustificationsByEtudiantId(String etudiantId)
    {
        Optional<Etudiant> etudiantOpt = etudiantRepository.findById(etudiantId);
        if (etudiantOpt.isEmpty())
            return null;

        Etudiant etudiant = etudiantOpt.get();
        List<String> absenceIds = etudiant.getAbsenceIds();
        List<Justification> justifications = new ArrayList<>();

        for (String absenceId : absenceIds) {
            Optional<Absence> absenceOpt = absenceRepository.findById(absenceId);
            if (absenceOpt.isPresent()) {
                Justification justification = justificationRepository.findJustificationByAbsenceId(absenceOpt.get().getId());
                if (justification != null) {
                    justifications.add(justification);
                }
            }
        }

        return justifications;

    }



    @Override
    public List<AbsenceEtudiantResponse> getAbsencesByEtudiantId(String etudiantId)
    {
        List<Absence> absences = absenceRepository.findAbsenceByEtudiantId(etudiantId);

        return absences.stream().map(a -> {
            AbsenceEtudiantResponse dto = new AbsenceEtudiantResponse();
            dto.setId(a.getId());
            dto.setType(a.getType());
            dto.setSessionId(a.getSessionId());
            sessionsCoursRepository.findById(a.getSessionId()).ifPresent(s -> {
                dto.setDate(s.getDateSession());
                coursRepository.findById(s.getCoursId()).ifPresent(c -> {
                    dto.setCoursLibelle(c.getLibelle());
                });
            });
            dto.setJustifiee(a.isJustifiee());
            etudiantRepository.findById(etudiantId)
                    .flatMap(e ->
                            classeRepository.findById(e.getClasseId()))
                    .ifPresent(c ->
                    {
                        dto.setClasseEtudiant(c.getLibelle());
                    }
            );
            return dto;
        }).toList();
    }

    public Optional<EtudiantQrCodeRequest> getEtudiantSessionPourPointage(String matricule) {
        LocalDate aujourdHui = LocalDate.now();
        List<SessionCours> sessionsDuJours = sessionsCoursRepository.findSessionCoursByDateSession(aujourdHui);

        for (SessionCours session : sessionsDuJours) {
            Optional<Classe> optClasse = classeRepository.findById(session.getClasseId());
            if (optClasse.isEmpty()) continue;

            Classe classe = optClasse.get();
            List<Etudiant> etudiants = etudiantRepository.findByclasseId(classe.getId());

            for (Etudiant etudiant : etudiants) {
                if (!etudiant.getMatricule().equals(matricule)) continue;

                Optional<Utilisateur> optUtilisateur = utilisateurRepository.findById(etudiant.getUtilisateurId());
                Optional<Paiement> optPaiement = paiementRepository.findById(etudiant.getId());

                if (optUtilisateur.isEmpty() || optPaiement.isEmpty()) return Optional.empty();

                StatusPaiment statut = optPaiement.get().getStatus();
                if (!statut.equals(StatusPaiment.PAYE) && !statut.equals(StatusPaiment.PASSE)) {
                    return Optional.empty(); // Paiement invalide
                }

                Utilisateur utilisateur = optUtilisateur.get();

                EtudiantQrCodeRequest dto = new EtudiantQrCodeRequest();
                dto.setMatricule(etudiant.getMatricule());
                dto.setNomComplet(utilisateur.getPrenom() + " " + utilisateur.getNom());
                dto.setCours(classe.getLibelle());
                dto.setDateSession(session.getDateSession());
                dto.setHeureSession(session.getHeureDebut());
                dto.setPaiementStatut(statut);

                return Optional.of(dto);
            }
        }

        return Optional.empty();
    }



}


//        List<SessionEtudiantQrCodeMobileResponse> sessions = sessionCoursService.getSessionsDuJourWithEtudiant();
//        return sessions.stream()
//                .filter(session -> session.getMatricule().equals(matricule))
//                .findFirst()
//                .orElseThrow(() -> new EntityNotFoundExecption("Étudiant non trouvé"));