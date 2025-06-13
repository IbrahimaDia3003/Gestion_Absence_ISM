package sn.ism.gestion.data.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Document(collection = "anneesScolaires")
@Getter
@Setter
public class AnneeScolaire extends AbstractEntity
{
    private String libelle;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateDebut;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateFin;
    private boolean enCours;
}