package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "notifications")
public class Notification extends AbstractEntity 
{
    private String etudiantId;
    private String type;
    private String message;
}