package sn.ism.gestion.web.dto.Response;


import sn.ism.gestion.Security.DTO.Response.UtilisateurSimpleResponse;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AdminSimpleResponse {

    private String id;
    private String nom;
    private String prenom;
    private UtilisateurSimpleResponse utilisateur;


}
