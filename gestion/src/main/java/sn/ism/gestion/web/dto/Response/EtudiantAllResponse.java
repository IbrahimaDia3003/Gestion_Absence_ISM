package sn.ism.gestion.web.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.data.entities.Absence;

import java.util.List;

@Getter
@Setter
public class EtudiantAllResponse {


    private String id;
    private String nom;
    private String prenom;
    private String classeId;
    private String matricule;
    private String telephone;


}

