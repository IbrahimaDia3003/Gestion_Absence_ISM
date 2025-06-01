package sn.ism.gestion.data.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "etudiants")
@Getter
@Setter
public class Etudiant extends AbstractEntity {

    private String matricule;
    private String utilisateurId;
    private String telephone;
    private String classeId;
    List<String> absenceIds ;

}
