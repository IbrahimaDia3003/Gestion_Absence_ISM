package sn.ism.gestion.web.dto.Request;

import sn.ism.gestion.Security.DTO.Request.UtilisateurCreateRequest;
import sn.ism.gestion.data.entities.Admin;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminSimpleRequest {

    private UtilisateurCreateRequest utilisateurcreate;

    public Admin toAdmin() {
        Admin admin = new Admin();

        return admin;
    }
}
