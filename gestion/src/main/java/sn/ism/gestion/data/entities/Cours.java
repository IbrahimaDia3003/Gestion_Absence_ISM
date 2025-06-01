package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Document(collection = "cours")
@Getter
@Setter
public class Cours extends AbstractEntity {

    private String libelle;
    private String professeurId;
    private String moduleId;
    private List<String> classeIds;
    private String semestre;
    private int nombreHeuresGlobal;
    private String anneeScolaireId;
}