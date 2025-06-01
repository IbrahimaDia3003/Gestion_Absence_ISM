package sn.ism.gestion.data.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Document(collection = "filieres")
public class Filiere extends AbstractEntity {

    private String nom;
    List<String> moduleIds ;

}