package sn.ism.gestion.mobile.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.Situation;
@Getter
@Setter
public class AbsenceAllMobileResponse {

    private String id ;
    private String nonEtudiant;
    private String prenomEtudiant;
    private String classeEtudiant;
    private String sessionId;
    private Situation type;
    private boolean justifiee;
}
