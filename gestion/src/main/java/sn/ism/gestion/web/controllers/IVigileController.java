package sn.ism.gestion.web.controllers;

import java.util.Map;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Vigile;
import sn.ism.gestion.web.dto.Request.VigileSimpleRequest;

@RestController
@RequestMapping("/api/vigiles")
public interface IVigileController extends Controller<Vigile> {

    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody VigileSimpleRequest request,
                                               BindingResult bindingResult);

    @PostMapping("/pointer")
    ResponseEntity<Map<String,Object>> pointerEtudiant(
        @RequestParam String matricule );
}
