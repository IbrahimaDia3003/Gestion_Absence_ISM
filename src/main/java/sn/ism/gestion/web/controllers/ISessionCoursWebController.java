package sn.ism.gestion.web.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/web/sessions")
public interface ISessionCoursWebController {

//    @GetMapping("/duJour")
//    ResponseEntity<Map<String,Object>> getSessionsDuJour(LocalDate date ,
//                    @RequestParam(defaultValue = "0") int page,
//                    @RequestParam(defaultValue = "10") int size);

    @GetMapping("/{id}")
    ResponseEntity<Map<String,Object>> findById(@PathVariable String id);

    @GetMapping("/duJour")
    ResponseEntity<Map<String,Object>> findSessionCoursByDateSession(
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size
    );
    @GetMapping("/{sessionId}/Absences")
    ResponseEntity<Map<String,Object>> findAbsencesBySessionId(
                    @PathVariable String sessionId,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "10") int size
    );

}
