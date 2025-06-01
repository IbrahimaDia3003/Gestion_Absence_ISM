package sn.ism.gestion.data.services.impl;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.enums.Situation;
import sn.ism.gestion.data.repositories.*;
import sn.ism.gestion.data.repositories.EtudiantRepository;
import sn.ism.gestion.data.repositories.AbsenceRepository;
import sn.ism.gestion.data.services.IAbsenceService;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.utils.mapper.AbsenceMapper;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.AbsenceSimpleResponse;



@Service
public class AbsenceServiceImpl implements IAbsenceService {

    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private EtudiantRepository etudiantRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private AbsenceMapper absenceMapper;
    @Autowired
    private SessionsCoursRepository sessionCoursRepository;


    @Override
    public Absence create(Absence object) {
        return absenceRepository.save(object);
    }

    @Override
    public Absence createAbsence(AbsenceRequest absenceRequest) {
    var existingEtudiant = etudiantRepository.findEtudiantById(absenceRequest.getEtudiantId())
            .orElseThrow(()-> new RuntimeException("Etudiant not found ou id baxxoul"));

        Absence absenceCreate = absenceMapper.toEntityR(absenceRequest);
        absenceCreate.setEtudiantId(existingEtudiant.getId());
        return absenceRepository.save(absenceCreate);
    }
//
//    @Override
//    public Absence pointerEtudiant(String sessionId, String etudiantId) {
//        Absence absence = absenceRepository.findOneBySessionIdAndEtudiantId(sessionId, etudiantId)
//            .orElseThrow(() -> new EntityNotFoundExecption("Absence non initialisée"));
//
//        LocalDateTime heureActuelle = LocalDateTime.now();
//        LocalDateTime heureDebut = sessionCoursRepository.findById(sessionId)
//            .orElseThrow().getHeureDebut();
//
//        if (heureActuelle.isBefore(heureDebut.plusMinutes(5))) {
//            absence.setType(Situation.PRESENT);
//        } else {
//            absence.setType(Situation.RETARD);
//        }
//            absence.setHeurePointage(LocalTime.now());
//            absenceRepository.save(absence);
//        return absence;
//    }

    public Absence pointerEtudiantByMatricule(String sessionId, String matricule) {
        Absence absence = absenceRepository.findOneBySessionIdAndEtudiantId(sessionId, matricule)
                .orElseGet(() -> {
                    sessionCoursRepository.findById(sessionId)
                            .orElseThrow(() -> new EntityNotFoundExecption("Session introuvable"));
                    var etu = etudiantRepository.findByMatricule(matricule)
                            .orElseThrow(() -> new EntityNotFoundExecption("Étudiant introuvable"));

                    Absence newAbsence = new Absence();
                    newAbsence.setSessionId(sessionId);
                    newAbsence.setEtudiantId(etu.getId());
                    newAbsence.setJustifiee(false);
                    return absenceRepository.save(newAbsence);
                });

        LocalDateTime heureDebut = sessionCoursRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundExecption("Session introuvable"))
                .getHeureDebut();

        LocalDateTime heureActuelle = LocalDateTime.now();

        if (heureActuelle.isBefore(heureDebut.plusMinutes(5))) {
            absence.setType(Situation.PRESENT);
        } else {
            absence.setType(Situation.RETARD);
        }

        absence.setHeurePointage(LocalTime.now());
        return absenceRepository.save(absence);
    }

    public Absence pointerEtudiant(String sessionId, String etudiantId) {
        Absence absence = absenceRepository.findOneBySessionIdAndEtudiantId(sessionId, etudiantId)
                .orElseGet(() -> {
                    sessionCoursRepository.findById(sessionId)
                            .orElseThrow(() -> new EntityNotFoundExecption("Session introuvable"));
                    etudiantRepository.findById(etudiantId)
                            .orElseThrow(() -> new EntityNotFoundExecption("Étudiant introuvable"));
                    Absence newAbsence = new Absence();
                    newAbsence.setSessionId(sessionId);
                    newAbsence.setEtudiantId(etudiantId);
                    newAbsence.setJustifiee(false);
                    return absenceRepository.save(newAbsence);
                });

        LocalDateTime heureDebut = sessionCoursRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundExecption("Session introuvable"))
                .getHeureDebut();

        LocalDateTime heureActuelle = LocalDateTime.now();

        if (heureActuelle.isBefore(heureDebut.plusMinutes(5))) {
            absence.setType(Situation.PRESENT);
        } else {
            absence.setType(Situation.RETARD);
        }

        absence.setHeurePointage(LocalTime.now());
        return absenceRepository.save(absence);
    }

    @Override
    public Absence update(String id, Absence absence) {
        Absence existing = absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence non trouvée avec ID : " + id));
        absence.setId(existing.getId()); // Assure que c'est un update
        return absenceRepository.save(absence);
    }

    @Override
    public boolean delete(String id) {
        if (!absenceRepository.existsById(id)) {
            return false;
        }
        absenceRepository.deleteById(id);
        return true;
    }

    @Override
    public Absence findById(String id) {
        return absenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Absence non trouvée avec ID : " + id));
    }

    @Override
    public List<Absence> findAll() {
        return absenceRepository.findAll();
    }

    @Override
    public Page<Absence> findAll(Pageable pageable) {
        return absenceRepository.findAll(pageable);
    }

    @Override
    public Page<AbsenceAllResponse> getAllAbsences(Pageable pageable) {
        Page<Absence> absences = absenceRepository.findAll(pageable);

        return absences.map(a -> {
            AbsenceAllResponse dto = new AbsenceAllResponse();
            dto.setType(a.getType());
            dto.setSessionId(a.getSessionId());
            dto.setJustifiee(a.isJustifiee());
            etudiantRepository.findById(a.getEtudiantId()).ifPresent(e -> {
                dto.setClasseEtudiant(e.getClasseId());
                utilisateurRepository.findById(e.getUtilisateurId()).ifPresent(u -> {
                    dto.setPrenomEtudiant(u.getPrenom());
                    dto.setNonEtudiant(u.getNom());
                });
            });
            return dto;
        });
    }

    @Override
     public AbsenceSimpleResponse getOne(String id) {
         Absence absence = absenceRepository.findById(id)
                 .orElseThrow(() -> new RuntimeException("Aucun Absence trouvé"));

         Etudiant etudiant = etudiantRepository.findById(absence.getEtudiantId())
                 .orElseThrow(() -> new RuntimeException("Etudiant introuvable"));

         AbsenceSimpleResponse dto = new AbsenceSimpleResponse();
         dto.setType(absence.getType());
         dto.setSessionId(absence.getSessionId());
         dto.setType(absence.getType());
         dto.setJustificationId(absence.getJustificationId());
         dto.setJustifiee(absence.isJustifiee());
         dto.setClasseEtudiant(etudiant.getClasseId());


         return dto;
     }
    
}