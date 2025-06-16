package sn.ism.gestion.mobile.dto.Request;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.StatusPaiment;

import java.time.LocalDate;
import java.time.LocalTime;


@Getter
@Setter
public class  EtudiantQrCodeRequest
{
    private String nomComplet;
    private String sessionId;
    private String matricule;
    private String cours;
    private LocalDate dateSession; // Format ISO ou "02/06/2025"
    private LocalTime heureSession; // Exemple : "10h00"
    private StatusPaiment paiementStatut; // "à jour", "non payé", etc.
    private String classeLibelle; // Libellé de la classe
    private String salleCours;
}
