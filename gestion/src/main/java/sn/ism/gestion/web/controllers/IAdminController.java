package sn.ism.gestion.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Admin;
import sn.ism.gestion.web.dto.Request.AdminSimpleRequest;
import sn.ism.gestion.web.dto.Request.JustificationValidationRequest;

import java.util.Map;
public interface IAdminController extends Controller<Admin> {

    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody AdminSimpleRequest request,
                                               BindingResult bindingResult);

    @PutMapping("/{id}/valider")
    ResponseEntity<Map<String,Object>> traiterJustification(
            @PathVariable String id,
            @RequestParam JustificationValidationRequest request
            );


}
