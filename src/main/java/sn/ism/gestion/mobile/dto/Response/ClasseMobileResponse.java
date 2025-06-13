package sn.ism.gestion.mobile.dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClasseMobileResponse
{
    private String libelle;
    private String niveau;
    private String filiereId;
    private String anneeScolaireId;
    private List<String> etudiantIds;

}
