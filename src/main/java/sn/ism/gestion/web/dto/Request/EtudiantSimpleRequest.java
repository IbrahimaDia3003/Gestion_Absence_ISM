package sn.ism.gestion.web.dto.Request;

import sn.ism.gestion.Security.DTO.Request.UtilisateurCreateRequest;
import sn.ism.gestion.data.entities.Etudiant;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EtudiantSimpleRequest {

    @NotBlank(message = "Le matricule est requis")
    private String matricule;
    @NotBlank(message = "Le numéro est requis")
    private String telephone;

    private UtilisateurCreateRequest utilisateurcreate;


    public Etudiant toEtudiant() {
        Etudiant etudiant = new Etudiant();

        etudiant.setMatricule(matricule);
        etudiant.setTelephone(telephone);

        return etudiant;
    }
}