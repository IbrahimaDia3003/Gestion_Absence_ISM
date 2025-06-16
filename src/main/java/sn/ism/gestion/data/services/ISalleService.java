package sn.ism.gestion.data.services;

import sn.ism.gestion.data.entities.Salle;
import sn.ism.gestion.web.dto.Response.SalleResponse;

import java.util.List;

public interface ISalleService
{

    // Salle createSalle(Salle salle);
     List<SalleResponse> findAllSalles();
}
