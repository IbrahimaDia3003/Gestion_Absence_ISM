package sn.ism.gestion.web.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sn.ism.gestion.data.enums.ModeCours;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter

@AllArgsConstructor
@NoArgsConstructor
public class SessionRequest
{

    private String id;
    private String coursId;
    private LocalDate date;
    private LocalDateTime heureDebut;
    private LocalDateTime heureFin;
    private int nombreHeures;
    private ModeCours mode;
    private String classeId;
    private boolean valide;
}
