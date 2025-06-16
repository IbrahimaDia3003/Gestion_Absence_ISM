package sn.ism.gestion.mobile.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.enums.StatutJustification;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JustificationMobileRequest {

    @NotBlank(message = "L'identifiant de l'absence est requis")
    private String absenceId;

    @NotBlank(message = "Le commentaire est requis")
    private String commentaire;

    private String fichierUrl;

    private MultipartFile imageUrl; // <-- image envoyée depuis mobile/front


    @NotBlank(message = "Le statut est requis")
    private StatutJustification statut = StatutJustification.EN_ATTENTE ;


    public Justification toJustification() {
        Justification justification = new Justification();
        justification.setCommentaire(commentaire);
        justification.setFichierUrl(fichierUrl);
        justification.setStatut(statut);

        return justification;
    }
}