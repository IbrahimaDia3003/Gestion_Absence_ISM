package sn.ism.gestion.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/pointages")
public interface IAbsenceController extends Controller<Absence> {

    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody AbsenceRequest request,
                                               BindingResult bindingResult);

    @PostMapping("/pointer")
     ResponseEntity<?> pointerEtudiantByQRcode(@RequestParam String sessionId,
                                       @RequestParam String etudiantId);

    @PostMapping("/pointerByMatricule")
    ResponseEntity<?> pointerEtudiantByMatricule(@RequestParam String sessionId,
                                      @RequestParam String matricule);


    @GetMapping("/{etudiantId}")
    ResponseEntity<Map<String,Object>> findAbsencesByEtudiant(@PathVariable String id, 
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}/details")
    ResponseEntity<Map<String,Object>> findByDetailsId(@PathVariable String id);

}
