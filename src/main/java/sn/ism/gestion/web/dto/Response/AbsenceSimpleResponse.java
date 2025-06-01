package sn.ism.gestion.web.dto.Response;

import sn.ism.gestion.data.enums.Situation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AbsenceSimpleResponse {

    private String sessionId;
    private String classeEtudiant;
    private Situation type;
    private boolean justifiee;
    private String justificationId;
    private String heurePointage;
}
