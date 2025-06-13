package sn.ism.gestion.data.entities;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "photos")
public class Photo extends AbstractEntity
{
    private String name;
    private String type;
    private String path; // chemin local ou URI d’accès
}
