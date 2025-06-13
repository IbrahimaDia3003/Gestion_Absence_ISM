package sn.ism.gestion.web.controllers;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.JustificationRequest;

@RestController
@RequestMapping("/api/justifications")
public interface IJustificationController extends Controller<Justification> {

    
    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody JustificationRequest objet,
        BindingResult bindingResult);

    @GetMapping("/{idAbsence}/justification")
    ResponseEntity<Map<String, Object>> SelectByAbsenceId(@PathVariable String idAbsence);
    
    
}
