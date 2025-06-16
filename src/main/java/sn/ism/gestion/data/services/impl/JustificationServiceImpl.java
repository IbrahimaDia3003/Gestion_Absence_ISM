package sn.ism.gestion.data.services.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.data.enums.StatutJustification;
import sn.ism.gestion.data.repositories.*;
import sn.ism.gestion.data.services.IJustificationService;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.web.dto.Request.JustificationValidationRequest;
import sn.ism.gestion.web.dto.Response.JusitficationAllResponse;
import sn.ism.gestion.web.dto.Response.JustificationSimpleResponse;

import java.util.List;

@Service
public  class JustificationServiceImpl implements IJustificationService
{


    @Autowired
    private AbsenceRepository absenceRepository;

    @Autowired
    private JustificationRepository justificationRepository;
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private SessionsCoursRepository sessionCoursRepository;

    
    @Autowired
    private ClasseRepository classeRepository;


    @Override
    public Justification create(Justification object)
    {
        return justificationRepository.save(object);
    }

//    @Override
//    public Justification createJustication(Justification justification)
//    {
//        return justificationRepository.save(justification);
//    }

    @Override
    public Justification traiterJustication(String id, JustificationValidationRequest justificationRequest)
    {
        Justification justification = justificationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundExecption("Justification non trouvée"));

        Absence absence = absenceRepository.findById(justification.getAbsenceId())
                .orElseThrow(() -> new EntityNotFoundExecption("Absence non trouvée"));

        absence.setJustifiee(true);
        if (justification.getStatut().equals(StatutJustification.VALIDEE))
            throw new IllegalStateException("Justification déjà traitée");

        absenceRepository.save(absence);
        justification.setStatut(justificationRequest.getStatut());
        return justificationRepository.save(justification);
    }


    @Override
    public Justification update(String id, Justification object)
    {
        return null;
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
        return justificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Justification non trouvée"));
    }

    @Override
    public JustificationSimpleResponse findByIdWitt(String id)
    {
        Justification justification = justificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucune justification"));

        Absence absence = absenceRepository.findById(justification.getAbsenceId())
                .orElseThrow(() -> new RuntimeException("Aucun Absence trouvé"));

        Etudiant etudiant = etudiantRepository.findById(absence.getEtudiantId())
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));

        Utilisateur utilisateur = utilisateurRepository.findById(etudiant.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur de l'étudiant introuvable"));

        Classe classe = classeRepository.findById(etudiant.getClasseId())
                .orElseThrow(() -> new RuntimeException("Classe de l'étudiant introuvable"));

        SessionCours session = sessionCoursRepository.findById(absence.getSessionId()).orElse(null);

        JustificationSimpleResponse dto = new JustificationSimpleResponse();
//        dto.setAbsenceId(absence.getId());
        dto.setCommentaire(justification.getCommentaire());
        dto.setDateSoumission(justification.getDateSoumission());
        dto.setFichierUrl(justification.getFichierUrl());
        dto.setStatut(justification.getStatut());
        dto.setNomCompletEtudiant(utilisateur.getPrenom()+" "+utilisateur.getNom());
        dto.setClasseEtudiant(classe.getLibelle());

        return dto;
    }

    @Override
    public JustificationSimpleResponse getJustificationByIdAbsenceId(String absenceId)
    {
        Justification justification = justificationRepository.findJustificationByAbsenceId(absenceId);
        if (justification == null) {
            throw new EntityNotFoundExecption("Justification non trouvée pour l'absence ID: " + absenceId);
        }
        JustificationSimpleResponse response = new JustificationSimpleResponse();
        response.setId(justification.getId());
        response.setCommentaire(justification.getCommentaire());
        response.setStatut(justification.getStatut());
        response.setDateSoumission(justification.getDateSoumission());
        response.setFichierUrl(justification.getFichierUrl());
//        response.setAbsenceId(justification.getAbsenceId());
        response.setImageUrl(justification.getUrl()); // ✅ nouvelle ligne
        // Récupération de l'étudiant associé à cette justification
        Etudiant etudiantOpt = etudiantRepository.findEtudiantByAbsenceIdsContaining(justification.getAbsenceId());

        etudiantRepository.findById(etudiantOpt.getId()).ifPresent(e -> {
            utilisateurRepository.findById(e.getUtilisateurId()).ifPresent(u -> {
                response.setNomCompletEtudiant(u.getPrenom() + " " + u.getNom());
            });
            classeRepository.findById(e.getClasseId()).ifPresent(c -> {
                response.setClasseEtudiant(c.getLibelle());
            });
        });

        return response;
    }

    @Override
    public List<Justification> findAll()
    {
        return List.of();
    }

    @Override
    public List<JusitficationAllResponse> findAllResponse()
    {

        var justifications = justificationRepository.findAll();
        if (justifications.isEmpty()) 
            return null ;
        
        return justifications.stream().map(
          justification -> {
              JusitficationAllResponse justificationResponse = new JusitficationAllResponse();
              
              
              Etudiant etudiantOpt = etudiantRepository.findEtudiantByAbsenceIdsContaining(justification.getAbsenceId());
                if (etudiantOpt != null) 
                {
                    justificationResponse.setId(justification.getId());
                    justificationResponse.setCommentaire(justification.getCommentaire());
                    justificationResponse.setStatut(justification.getStatut());
                    justificationResponse.setDateSoumission(justification.getDateSoumission());
                    justificationResponse.setClasseEtudiant(etudiantOpt.getUtilisateurId());
//                    justificationResponse.setImageUrl(justification.getUrl()); // ✅ nouvelle ligne

                    etudiantRepository.findById(etudiantOpt.getId()).ifPresent(e -> {
                        utilisateurRepository.findById(e.getUtilisateurId()).ifPresent(u -> {
                            justificationResponse.setNomCompletEtudiant(u.getPrenom() + " " + u.getNom());
                        });
                        classeRepository.findById(e.getClasseId()).ifPresent(c -> {
                            justificationResponse.setClasseEtudiant(c.getLibelle());
                        });
                    });
                    
                }else {
                    return null;
                }
                
                return justificationResponse;
                            
          }).toList();

    }
}
