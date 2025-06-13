package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.StatusPaiment;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Document(collection = "paiements")
public class Paiement extends AbstractEntity {

    private String id;
    @Indexed(unique = true)
    private String etudiantId;
    private String mois;   // <-- Ajout du champ mois
    private String annee;
    private LocalDate datePaiement;
    private BigDecimal montant;
    private StatusPaiment status;
}
