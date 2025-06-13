package sn.ism.gestion.data.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.enums.StatutJustification;
import sn.ism.gestion.data.repositories.AbsenceRepository;
import sn.ism.gestion.data.repositories.JustificationRepository;
import sn.ism.gestion.data.services.IAbsenceService;
import sn.ism.gestion.data.services.IJustificationService;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.utils.mapper.JustificationMapper;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Request.JustificationValidationRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.JusitficationAllResponse;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JustificationServiceImpl implements IJustificationService {


    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private JustificationRepository justificationRepository;

    @Autowired
    private JustificationMapper justificationMapper;

    @Autowired
    IAbsenceService absenceService;



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
    public Page<JusitficationAllResponse> findAllWith(Pageable pageable) {
        // Récupérer la page d'absences
        Page<AbsenceAllResponse> allAbsences = absenceService.getAllAbsences(pageable);
        List<AbsenceAllResponse> absences = allAbsences.getContent();
        var justifications = justificationRepository.findAll();
        var absencesAll = absenceRepository.findAll();

        List<AbsenceAllResponse> absenceList = absencesAll.stream()
                .map(absence -> absences.stream()
                        .filter(a -> Objects.equals(a.getId(), absence.getId())
                                && !absence.getJustificationId().isEmpty())
                        .findFirst()
                        .orElse(null))
                .toList();




        List<JusitficationAllResponse> responseList = absenceList.stream()
                .map(absence -> {
                    JusitficationAllResponse jusitficationAllResponse = new JusitficationAllResponse();

                    if (absence != null)
                    {

                        Justification justification = justifications.stream()
                                .filter(j -> Objects.equals(j.getAbsenceId(), absence.getId()))
                                .findFirst()
                                .orElse(null);

                        if (justification != null) {
                            jusitficationAllResponse.setId(justification.getId());
                            jusitficationAllResponse.setNomCompletEtudiant(absence.getNonEtudiant());
                            jusitficationAllResponse.setClasseEtudiant(absence.getClasseEtudiant());
                            jusitficationAllResponse.setStatut(justification.getStatut());
                            jusitficationAllResponse.setDateSoumission(justification.getDateSoumission());
                            jusitficationAllResponse.setCommentaire(justification.getCommentaire());
//                        } else {
//                            jusitficationAllResponse.setStatut(StatutJustification.EN_ATTENTE);
                        }
                    }
                    else {
                        return null;
                    }


                    return jusitficationAllResponse;
                })
                .collect(Collectors.toList());


        // Retourner une page avec les résultats
        return new PageImpl<>(responseList, pageable, allAbsences.getTotalElements());
    }

    @Override
    public Justification findJustificationByAbsenceId(String absenceId)
    {
        return justificationRepository.findJustificationByAbsenceId(absenceId);
    }

    @Override
    public Optional<Justification> findJustificationById(String id)
    {
        return justificationRepository.findById(id);
    }


//    @Override
//    public Page<JusitficationAllResponse> findAllWith(Pageable pageable)
//    {
//        var allAbsences = absenceService.getAllAbsences(pageable);
//        var justifications = justificationRepository.findAll(pageable);
//
//         var responseStream = allAbsences.getContent().stream().map(absence ->
//         {
//             JusitficationAllResponse jusitficationAllResponse = new JusitficationAllResponse();
//
//             justifications.stream().map(
//                justification ->
//                    {
//                        justification.getAbsenceId().equals(absence.getId());
//
//
//                        jusitficationAllResponse.setId(absence.getId());
//                        jusitficationAllResponse.setStatut(justification.getStatut());
//                        jusitficationAllResponse.setNomCompletEtudiant(absence.getNonEtudiant());
//                        jusitficationAllResponse.setDateSoumission(justification.getDateSoumission());
//                        jusitficationAllResponse.setCommentaire(justification.getCommentaire());
//                        jusitficationAllResponse.setClasseEtudiant(absence.getClasseEtudiant());
//                        jusitficationAllResponse.setCommentaire(justification.getCommentaire());
//
//                        return jusitficationAllResponse;
//
//                    });
//            return absence;
//        });
//
//
//
//        return (Page<JusitficationAllResponse>) responseStream;
//
//
//
//    }

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
    public Justification findById(String id)
    {
//        return justificationRepository.findById(id);
        return null;
    }

    @Override
    public List<Justification> findAll() {
        return justificationRepository.findAll();
    }
}
