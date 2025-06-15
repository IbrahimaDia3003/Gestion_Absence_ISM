package sn.ism.gestion.data.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.ModeCours;

@Getter
@Setter
@Document(collection = "sessions")
public class SessionCours extends AbstractEntity {

    private String coursId;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateSession ;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureDebut;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime heureFin;
    private int nombreHeures;
    private ModeCours mode;
    private String classeId;
    private boolean valide;
    private String salleId;
}
