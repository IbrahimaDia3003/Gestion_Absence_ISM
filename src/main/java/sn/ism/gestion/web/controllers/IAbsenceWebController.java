package sn.ism.gestion.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/web/absences")
public interface IAbsenceWebController  {

    @GetMapping("")
    ResponseEntity<Map<String, Object>> SelectAll(  @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{etudiantId}")
    ResponseEntity<Map<String,Object>> findAbsencesByEtudiant(@PathVariable String id, 
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    ResponseEntity<Map<String, Object>> SelectdById(@PathVariable String id);

}
