package sn.ism.gestion.web.dto.Response;

import java.time.LocalDateTime;

import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.enums.StatutJustification;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JustificationSimpleResponse {
    
    private String absenceId;
    private String commentaire;
    private String fichierUrl;
    private StatutJustification statut;
    private LocalDateTime dateSoumission;

}
