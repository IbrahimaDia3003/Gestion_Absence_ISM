package sn.ism.gestion.web.controllers;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;
import sn.ism.gestion.web.dto.Request.JustificationRequest;

@RestController
@RequestMapping("/api/web/etudiants")
public interface IEtudiantWebController extends Controller<Etudiant> {

    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody EtudiantSimpleRequest request,
                BindingResult bindingResult);


    @GetMapping("/{id}/absences")
    ResponseEntity<Map<String,Object>> getMyListAbsences(@PathVariable String id, 
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size);

    @GetMapping("/matricule/{matricule}")
    ResponseEntity<Map<String,Object>> findByMatricule(@PathVariable String matricule);

    @PutMapping("/updateSimple/{id}")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Map<String, Object>> Update(@PathVariable String id, @RequestBody EtudiantSimpleRequest request);

     @PostMapping("/{id}/justificationAbsence")
     ResponseEntity<Map<String,Object>> justifierAbsence(
             @PathVariable String id ,
             @RequestBody JustificationRequest justification);
}
