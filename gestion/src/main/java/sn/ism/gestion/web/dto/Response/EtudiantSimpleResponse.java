package sn.ism.gestion.web.dto.Response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EtudiantSimpleResponse
{
    private String id;
    private String matricule;
    private String telephone;
    private String nom;
    private String prenom;
    private String login;
    private String photo;
    private String classe;
    private String niveau;
    private String filiere;
    private List<AbsenceAllResponse> absences;
}