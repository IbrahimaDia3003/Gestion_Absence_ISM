package sn.ism.gestion.web.dto.Response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EtudiantSimpleResponse {

    private String id;
    private String nom;
    private String prenom;
    private String classeId;
    private String matricule;
    private String telephone;
    List<String> absenceIds ;

}