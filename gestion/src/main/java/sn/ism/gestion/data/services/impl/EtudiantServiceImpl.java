package sn.ism.gestion.data.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.enums.Role;
import sn.ism.gestion.data.enums.Situation;
import sn.ism.gestion.data.repositories.*;
import sn.ism.gestion.data.services.IEtudiantService;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.utils.mapper.EtudiantMapper;
import sn.ism.gestion.utils.mapper.UtilisateurMapper;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.EtudiantAllResponse;
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
    @Autowired private JustificationServiceImpl justificationServiceImpl;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private FiliereRepository filiereRepository;


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
    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }

    @Override
    public Page<Etudiant> findAll(Pageable pageable) {
        return etudiantRepository.findAll(pageable);
    }

    @Override
    public Etudiant getByMatricule(String matricule) {
        return etudiantRepository.findByMatricule(matricule)
                .orElseThrow(() -> new EntityNotFoundExecption("Étudiant avec ce matricule non trouvé"));
    }

    @Override
    public Absence justifierAbsence(String absenceId, JustificationRequest justification) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new EntityNotFoundExecption("Pointage non trouvée"));
        if (absence.getType()!=Situation.ABSENCE){
            throw new EntityNotFoundExecption("Pas une Absence");
        }
        Justification justificationCreate = justification.toJustification();
        justificationCreate.setAbsenceId(absence.getId());
        absence.setJustifiee(true);
        justificationServiceImpl.createJustication(justification);
        return absenceRepository.save(absence);
    }

    @Override
    public Page<EtudiantAllResponse> getAllEtudiants(Pageable pageable) {
        Page<Etudiant> etudiants = etudiantRepository.findAll(pageable);

        return etudiants.map(e -> {
            EtudiantAllResponse dto = new EtudiantAllResponse();
            dto.setId(e.getId());
            dto.setMatricule(e.getMatricule());
            dto.setTelephone(e.getTelephone());
            dto.setClasseId(e.getClasseId());
            utilisateurRepository.findById(e.getUtilisateurId()).ifPresent(u -> {
                dto.setNom(u.getNom());
                dto.setPrenom(u.getPrenom());
            });


            return dto;
        });
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
            dtoAbsence.setSessionId(abs.getSessionId()); // tu peux remplacer par le nom si tu as la session
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
        dto.setClasse(classe.getLibelle());
        dto.setAbsences(absenceResponses);

        if (filiere != null) {
            dto.setFiliere(filiere.getNom());
        }

        dto.setAbsences(absenceResponses);

        return dto;
    }

//     @Override
//     public EtudiantSimpleResponse getOne(String id) {
//         Etudiant etudiant = etudiantRepository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Aucun Etudiant trouvé"));
//
//         Utilisateur utilisateur = utilisateurRepository.findById(etudiant.getUtilisateurId())
//                 .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
//
//         EtudiantSimpleResponse dto = new EtudiantSimpleResponse();
//         dto.setId(etudiant.getId());
//         dto.setMatricule(etudiant.getMatricule());
//         dto.setTelephone(etudiant.getTelephone());
//        //  dto.setUtilisateurId(utilisateur.getId());
//        //  dto.setLogin(utilisateur.getLogin());
//         dto.setNom(utilisateur.getNom());
//         dto.setPrenom(utilisateur.getPrenom());
//
//         return dto;
//     }


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
    public Page<Absence> getAbsencesByEtudiantId(String etudiantId, Pageable pageable) {
        return absenceRepository.findByEtudiantIdAndType(etudiantId, Situation.ABSENCE, pageable);
    }

}