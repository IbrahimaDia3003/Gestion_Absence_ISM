package sn.ism.gestion.data.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.enums.StatutJustification;
import sn.ism.gestion.data.repositories.AbsenceRepository;
import sn.ism.gestion.data.repositories.JustificationRepository;
import sn.ism.gestion.data.services.IJustificationService;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.utils.mapper.JustificationMapper;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Request.JustificationValidationRequest;

import java.util.List;

@Service
public class JustificationServiceImpl implements IJustificationService {


    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private JustificationRepository justificationRepository;

    @Autowired
    private JustificationMapper justificationMapper;



    @Override
    public Justification create(Justification object) {
        return justificationRepository.save(object);

    }

    @Override
    public Justification createJustication(JustificationRequest justificationRequest) {
//        var existingAbsence = absenceRepository.findById(justificationRequest.getAbsenceId())
//                .orElseThrow(()-> new RuntimeException("Etudiant not found ou id baxxoul"));
//
        Justification justificationCreate = justificationMapper.toEntityR(justificationRequest);
//        justificationCreate.setAbsenceId(existingAbsence.getId());
        return justificationRepository.save(justificationCreate);
    }

    @Override
    public Justification traiterJustication(String absenceId, JustificationValidationRequest justificationRequest) {
        Absence absence = absenceRepository.findById(absenceId)
                .orElseThrow(() -> new EntityNotFoundExecption("Absence non trouvée"));

        Justification justification = justificationRepository.findById(absence.getJustificationId())
                .orElseThrow(() -> new EntityNotFoundExecption("Justification non trouvée"));

        if (!justification.getStatut().equals(StatutJustification.EN_ATTENTE)) {
            throw new IllegalStateException("Justification déjà traitée");
        }
        justification.setStatut(StatutJustification.valueOf(justificationRequest.getStatut()));
        return justificationRepository.save(justification);
    }

    @Override
    public Justification update(String id, Justification object) {
        return null;
    }


    @Override
    public Page<Justification> findAll(Pageable pageable) {
        return justificationRepository.findAll(pageable);
    }

    @Override
    public boolean delete(String id) {
        if (!justificationRepository.existsById(id)) {
            return false;
        }
        justificationRepository.deleteById(id);
        return true;
    }

    @Override
    public Justification findById(String id) {
        return justificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Justification non trouvée avec ID : " + id));
    }

    @Override
    public List<Justification> findAll() {
        return justificationRepository.findAll();
    }
}
