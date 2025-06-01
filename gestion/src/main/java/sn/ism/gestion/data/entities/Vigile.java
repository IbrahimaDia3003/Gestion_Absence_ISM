package sn.ism.gestion.data.entities;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "vigiles")
public class Vigile extends AbstractEntity{

    private String utilisateurId ;
}
