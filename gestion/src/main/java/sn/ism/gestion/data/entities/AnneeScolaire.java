package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Document(collection = "anneesScolaires")
@Getter
@Setter
public class AnneeScolaire extends AbstractEntity {
    private String libelle;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean enCours;
}