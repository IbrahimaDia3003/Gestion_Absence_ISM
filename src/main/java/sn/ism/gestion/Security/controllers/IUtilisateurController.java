package sn.ism.gestion.Security.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.ism.gestion.Config.Controller;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.Security.DTO.Request.LoginRequest;
import sn.ism.gestion.Security.DTO.Request.UtilisateurCreateRequest;

import java.util.Map;

@RestController
@RequestMapping("/api/utilisateurs")
public interface IUtilisateurController extends Controller<Utilisateur> {

    @PostMapping("/login")
    ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequest request);

    @PostMapping("")
    ResponseEntity<Map<String, Object>> Create(@Valid @RequestBody UtilisateurCreateRequest objet,
                                               BindingResult bindingResult);

//    @GetMapping("/login/")
//    ResponseEntity<Map<String,Object>> findByLogin(@PathVariable String login);


}
