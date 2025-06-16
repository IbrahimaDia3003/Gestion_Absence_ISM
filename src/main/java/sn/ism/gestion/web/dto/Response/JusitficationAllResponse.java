package sn.ism.gestion.web.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.StatutJustification;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class JusitficationAllResponse
{
    private String id;
    private String nomCompletEtudiant;
    private String classeEtudiant;
    private String commentaire;
    private StatutJustification statut;
    private LocalDate dateSoumission;
//    private Multiple imageUrl;
}
