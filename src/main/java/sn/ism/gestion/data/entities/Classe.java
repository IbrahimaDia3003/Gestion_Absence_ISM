package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Document("classes")
@Getter
@Setter
public class Classe extends AbstractEntity 
{
    private String libelle;
    private String niveau;
    private String filiereId;
    private String anneeScolaireId;
    private List<String> etudiantIds;
}