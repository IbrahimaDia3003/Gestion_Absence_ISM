package sn.ism.gestion.web.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.ModeCours;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class SessionAllResponse {

    private String id;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private ModeCours mode;
    private String salle;


}
