package sn.ism.gestion.web.dto.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VigileAllResponse {

    private String id;
    private String nom;
    private String prenom;
    private String login;
    private String utilisateurId;

}
