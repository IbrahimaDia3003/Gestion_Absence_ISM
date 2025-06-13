package sn.ism.gestion.web.dto.Response;
import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.Security.DTO.Response.UtilisateurSimpleResponse;

@Getter
@Setter
public class VigileSimpleResponse {

  
    private String utilisateurId ;

    private UtilisateurSimpleResponse utilisateur;


}

