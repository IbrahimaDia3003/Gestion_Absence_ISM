package sn.ism.gestion.data.entities;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "admin")
public class Admin extends AbstractEntity {

    private String utilisateurId ;

}