package sn.ism.gestion.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Salle;

import java.util.Map;

@RestController
@RequestMapping("/api/web/salles")
public interface SallewebController
{
    @GetMapping("")
    ResponseEntity<Map<String, Object>> SelectAll();
}
