package sn.ism.gestion.mobile.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.Situation;

import java.time.LocalDate;

@Getter
@Setter
public class AbsenceEtudiantResponse
{
    private String id ;
    private String sessionId;
    private Situation type;
    private boolean justifiee;
    private String coursLibelle;
    private LocalDate date;
    private String classeEtudiant;

}
