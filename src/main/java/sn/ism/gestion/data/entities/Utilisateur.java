package sn.ism.gestion.data.entities;

import sn.ism.gestion.data.enums.Role;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "utilisateurs")
public class Utilisateur extends AbstractEntity {

    private String nom;
    private String prenom;
    private String login;
    private String motDePasse;
    private String photo;

    private Role role;
}