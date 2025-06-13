package sn.ism.gestion.mobile.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.StatutJustification;

import java.time.LocalDate;

@Getter
@Setter
public class JustificationSimpleMobileResponse {
    
    private String absenceId;
    private String commentaire;
    private String fichierUrl;
    private StatutJustification statut;
    private LocalDate dateSoumission;

}
