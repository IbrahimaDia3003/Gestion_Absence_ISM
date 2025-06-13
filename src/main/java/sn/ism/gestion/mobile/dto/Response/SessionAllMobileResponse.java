package sn.ism.gestion.mobile.dto.Response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.ModeCours;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class SessionAllMobileResponse {

    private String id;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureDebut;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureFin;
    private ModeCours mode;
    private String classeLibelle;

}
