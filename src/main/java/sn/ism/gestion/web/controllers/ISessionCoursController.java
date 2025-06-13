package sn.ism.gestion.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/sessions")
public interface ISessionCoursController {

    @GetMapping("")
    ResponseEntity<Map<String, Object>> getAllSessionCours(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);
    @GetMapping("/duJour")
    ResponseEntity<Map<String,Object>> getSessionsDuJour(LocalDate date ,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    ResponseEntity<Map<String,Object>> findById(@PathVariable String id);
}
