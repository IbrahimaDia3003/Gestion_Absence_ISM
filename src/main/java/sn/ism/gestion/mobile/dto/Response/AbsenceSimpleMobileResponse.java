package sn.ism.gestion.mobile.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.Situation;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class AbsenceSimpleMobileResponse
{

    private String id;
    // Informations sur l’absence
    private Situation type;
    private boolean justifiee;
    private String justificationId;
    // Informations sur la session de cours
    private String sessionId;
    private LocalDate sessionDate;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    // Informations sur l’étudiant
    private String nomEtudiant;
    private String prenomEtudiant;
    private String classeEtudiant;

}
