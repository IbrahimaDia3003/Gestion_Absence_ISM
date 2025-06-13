package sn.ism.gestion.mobile.dto.Response;

import lombok.Getter;
import lombok.Setter;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;

import java.util.List;

@Getter
@Setter
public class EtudiantSimpleMobileResponse
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