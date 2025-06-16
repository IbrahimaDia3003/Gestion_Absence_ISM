package sn.ism.gestion.web.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.JustificationRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/web/justifications")
public interface IJustificationWebController extends Controller<Justification>
{
    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody JustificationRequest objet,
        BindingResult bindingResult);
    
    @GetMapping("/{absenceId}/justification")
    ResponseEntity<Map<String, Object>> findJustification(@PathVariable String absenceId);
}
