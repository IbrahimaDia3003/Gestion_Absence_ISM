package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "inscriptions")
public class Inscription extends AbstractEntity 
{
    private String etudiantId;
    private String classeId;
    private String anneeScolaireId;
}