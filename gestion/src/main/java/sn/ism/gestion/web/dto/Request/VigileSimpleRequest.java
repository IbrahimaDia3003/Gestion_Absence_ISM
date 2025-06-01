package sn.ism.gestion.web.dto.Request;

import jakarta.validation.constraints.NotBlank;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Vigile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VigileSimpleRequest {

    private UtilisateurCreateRequest utilisateurcreate;

    public Vigile toVigile() {
        Vigile vigile = new Vigile();

        return vigile;
    }

}
