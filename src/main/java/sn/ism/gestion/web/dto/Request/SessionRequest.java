package sn.ism.gestion.web.dto.Request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.ModeCours;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SessionRequest
{
    private String coursId;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private int nombreHeures;
    private ModeCours mode;
    private String classeId;
    private boolean valide;
    private String salleId;
}
