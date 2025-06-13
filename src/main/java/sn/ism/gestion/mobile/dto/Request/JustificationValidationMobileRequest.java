package sn.ism.gestion.mobile.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.enums.StatutJustification;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class JustificationValidationMobileRequest {

    @NotBlank(message = "Le statut est requis")
    private String statut;


    public Justification toValidate() {
        Justification justification = new Justification();
        justification.setStatut(StatutJustification.valueOf(statut));
        return justification;
    }
}
