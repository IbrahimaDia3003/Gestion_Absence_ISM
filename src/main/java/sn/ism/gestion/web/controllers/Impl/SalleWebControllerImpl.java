package sn.ism.gestion.web.controllers.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import sn.ism.gestion.web.controllers.SallewebController;
import sn.ism.gestion.web.dto.Response.SalleResponse;
import sn.ism.gestion.data.services.ISalleService;
import sn.ism.gestion.web.dto.RestResponse;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class SalleWebControllerImpl implements SallewebController
{
    private final ISalleService salleService;

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll()
    {
        List<SalleResponse> salles = salleService.findAllSalles();

        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK, salles, "Salles retrieved successfully"),
                HttpStatus.OK);
    }

}
