package sn.ism.gestion.web.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.enums.StatutJustification;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JustificationRequest {



    @NotBlank(message = "L'ID de l'étudiant est requis")

    @NotBlank(message = "Le commentaire est requis")
    private String commentaire;

    private String fichierUrl;

    @NotBlank(message = "Le statut est requis")
    private StatutJustification statut=StatutJustification.EN_ATTENTE ;


    public Justification toJustification()
    {
        Justification justification = new Justification();
        justification.setCommentaire(commentaire);
        justification.setFichierUrl(fichierUrl);
        justification.setStatut(statut);

        return justification;
    }
}