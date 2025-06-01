package sn.ism.gestion.web.dto.Request;

import jakarta.validation.constraints.NotBlank;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.enums.Role;
import sn.ism.gestion.data.enums.StatutJustification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JustificationValidationRequest {

    @NotBlank(message = "Le statut est requis")
    private String statut;


    public Justification toValidate() {
        Justification justification = new Justification();
        justification.setStatut(StatutJustification.valueOf(statut));
        return justification;
    }
}
