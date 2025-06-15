package sn.ism.gestion.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Vigile;
import sn.ism.gestion.web.dto.Request.VigileSimpleRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/web/vigiles")
public interface IVigileWebController extends Controller<Vigile> {

    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody VigileSimpleRequest request,
                                               BindingResult bindingResult);

//    @PostMapping("/pointer")
//    ResponseEntity<Map<String,Object>> pointerEtudiant(
//        @RequestParam String matricule );
}
