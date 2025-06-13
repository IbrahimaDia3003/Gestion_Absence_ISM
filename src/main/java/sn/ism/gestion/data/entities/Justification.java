package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.StatutJustification;

import java.time.LocalDateTime;

@Getter
@Setter
@Document(collection = "justifications")
public class Justification extends AbstractEntity {
    private String absenceId;
    private String commentaire;
    private String fichierUrl;
    private StatutJustification statut;
    private LocalDateTime dateSoumission = LocalDateTime.now() ;
}