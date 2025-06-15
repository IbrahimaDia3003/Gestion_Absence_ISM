package sn.ism.gestion.web.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.Situation;

import java.time.LocalDate;

@Getter
@Setter
public class AbsenceAllResponse {

    private String id ;
    private String nonEtudiant;
    private String prenomEtudiant;
    private String classeEtudiant;
    private String sessionCourslibelle;
    private LocalDate date;
    private Situation type;
    private boolean justifiee;

}
