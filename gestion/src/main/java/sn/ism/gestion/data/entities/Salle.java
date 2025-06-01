package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "salles")
public class Salle extends AbstractEntity {
    private String nom;
    private String numero;
    private int nombrePlaces;
}