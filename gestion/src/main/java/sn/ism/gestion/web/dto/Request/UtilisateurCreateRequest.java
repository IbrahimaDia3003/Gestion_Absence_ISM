package sn.ism.gestion.web.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.data.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UtilisateurCreateRequest {

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Login est obligatoire")
    @Email(message = "Login doit être valide")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String motDePasse;

    @NotBlank(message = "Le rôle est obligatoire")
    private String role; // Enum sous forme de String


    @URL(message = "L'URL de la photo est invalide")
    private String photo;


    public Utilisateur toEntity() {
        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setPrenom(prenom);
        u.setLogin(login);
        u.setMotDePasse(motDePasse);
        u.setPhoto(photo);
        u.setRole(Role.valueOf(role));
        return u;
    }
}