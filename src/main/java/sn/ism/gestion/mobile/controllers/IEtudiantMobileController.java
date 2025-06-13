package sn.ism.gestion.mobile.controllers;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;
import sn.ism.gestion.web.dto.Request.JustificationRequest;

import java.util.Map;
@RestController
@RequestMapping("/api/mobile/etudiants")
public interface IEtudiantMobileController
{
    @GetMapping("")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Map<String, Object>> SelectAll();

    @GetMapping("/{id}/absences")
    ResponseEntity<Map<String,Object>> getMyListAbsences(@PathVariable String id);

     @GetMapping("/qrcode/{matricule}")
     ResponseEntity<Map<String,Object>> getQrCodeEtudiant(@PathVariable String matricule);

     @PostMapping("/{id}/justificationAbsence")
     ResponseEntity<Map<String,Object>> justifierAbsence(
             @PathVariable String id ,
             @RequestBody JustificationRequest justification);

     @GetMapping("/{id}/justifications")
     ResponseEntity<Map<String,Object>> getJustifications(@PathVariable String id);

     @GetMapping("/{id}/SessionCours/")
     ResponseEntity<Map<String,Object>> getSessionCoursByEtudiantId(@PathVariable String id);

     @GetMapping("/{id}/justifications/{justificationId}")
     ResponseEntity<Map<String,Object>> getJustificationById(@PathVariable String justificationId);

}
