package sn.ism.gestion.mobile.controllers;

import java.util.Map;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.mobile.dto.Request.EtudiantQrCodeRequest;
import sn.ism.gestion.mobile.dto.Response.SessionEtudiantQrCodeMobileResponse;

@RestController
@RequestMapping("/api/mobile/vigiles")
public interface IVigileMobileController
{

    @GetMapping("")
    @ApiResponse(responseCode = "200")
    ResponseEntity<Map<String, Object>> SelectAll();

    @PostMapping("/{vigileId}/pointer")
    ResponseEntity<Map<String, Object>> pointerEtudiant(@PathVariable String vigileId,@RequestBody EtudiantQrCodeRequest etudiantSession);

    @GetMapping("/{vigileId}/historiquePointages")
    ResponseEntity<Map<String, Object>> getHistoriquePointages(@PathVariable String vigileId);
}
