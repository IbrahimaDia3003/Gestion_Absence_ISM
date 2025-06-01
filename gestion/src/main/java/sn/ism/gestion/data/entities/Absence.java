package sn.ism.gestion.data.entities;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.enums.Situation;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;

@Getter
@Setter
@Document(collection = "absences")
public class Absence extends AbstractEntity {

    private String etudiantId;
    private String sessionId;
    private Situation type;
    private boolean justifiee;
    private String justificationId;
    private LocalTime heurePointage;

    
}