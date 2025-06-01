package sn.ism.gestion.web.dto.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Login est obligatoire")
    @Email(message = "Login doit être valide")
    private String login;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String motDePasse;
}