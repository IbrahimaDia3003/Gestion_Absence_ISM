package sn.ism.gestion.mobile.dto.Response;
import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.Security.DTO.Response.UtilisateurSimpleResponse;

@Getter
@Setter
public class VigileSimpleMobileResponse {

  
    private String utilisateurId ;

    private UtilisateurSimpleResponse utilisateur;


}

