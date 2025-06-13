package sn.ism.gestion.data.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Vigile;
import sn.ism.gestion.web.dto.Request.VigileSimpleRequest;
import sn.ism.gestion.web.dto.Response.VigileAllResponse;

public interface IVigileService extends Service<Vigile>
{
     VigileAllResponse getOne(String id);
     Vigile createVigile(VigileSimpleRequest vigileSimpleRequest) ;
     Page<VigileAllResponse> getAllVigiles(Pageable pageable);
}
