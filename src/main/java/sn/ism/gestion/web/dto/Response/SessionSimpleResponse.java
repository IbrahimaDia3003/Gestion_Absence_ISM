package sn.ism.gestion.web.dto.Response;

import sn.ism.gestion.data.enums.ModeCours;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.Role;

@Getter
@Setter
public class SessionSimpleResponse {

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
    private String salleId;

}
