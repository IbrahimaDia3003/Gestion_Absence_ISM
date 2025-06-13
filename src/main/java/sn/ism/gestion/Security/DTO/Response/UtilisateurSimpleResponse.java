package sn.ism.gestion.Security.DTO.Response;
import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.Role;

@Getter
@Setter
public class UtilisateurSimpleResponse {

    private String id;
    private String nom;
    private String prenom;
    private String login;
    private String photo;
    private Role role;

}
