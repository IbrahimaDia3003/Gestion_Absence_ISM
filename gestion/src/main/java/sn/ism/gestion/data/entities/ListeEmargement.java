package sn.ism.gestion.data.entities;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@Document(collection = "emargements")
public class ListeEmargement extends AbstractEntity {
    private String sessionId;
    private List<String> etudiantsPresents;
    private boolean ouverte;
}