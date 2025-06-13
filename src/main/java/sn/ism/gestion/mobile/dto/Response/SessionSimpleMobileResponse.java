package sn.ism.gestion.mobile.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.ModeCours;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class SessionSimpleMobileResponse {

    private  String id;
    private String coursId;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private int nombreHeures;
    private ModeCours mode;
    private String classeId;
    private boolean valide;
    private List<String> etudiantsAttendus;

}
