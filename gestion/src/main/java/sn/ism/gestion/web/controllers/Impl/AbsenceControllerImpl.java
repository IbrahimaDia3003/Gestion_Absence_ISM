package sn.ism.gestion.web.controllers.Impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.services.IAbsenceService;
import sn.ism.gestion.utils.mapper.AbsenceMapper;
import sn.ism.gestion.web.controllers.IAbsenceController;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.RestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/pointages")
@CrossOrigin(origins = "http://localhost:4200")
public class AbsenceControllerImpl implements IAbsenceController {

    private final IAbsenceService absenceService;
    private final AbsenceMapper absenceMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(AbsenceRequest request, BindingResult bindingResult) {

            if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Absence absence = absenceService.createAbsence(request);
        Absence entityAbsence = absenceMapper.toEntity(absence);

        return new ResponseEntity<>(RestResponse.response(  
            HttpStatus.CREATED, 
                    entityAbsence, 
                    "PointageCreateRequest"), 
            HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<AbsenceAllResponse> absences = absenceService.getAllAbsences(pageable);
        Page<AbsenceAllResponse> response = absences.map(absenceMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "PointageAllResponses"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> pointerEtudiantByQRcode(@RequestParam String sessionId, @RequestParam String etudiantId) {
        Absence absence = absenceService.pointerEtudiant(sessionId, etudiantId);
        return ResponseEntity.ok().body(absence);
    }

    @Override
    public ResponseEntity<?> pointerEtudiantByMatricule(@RequestParam String sessionId, @RequestParam String matricule) {
        Absence absence = absenceService.pointerEtudiantByMatricule(sessionId, matricule);
        return ResponseEntity.ok().body(absence);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        var absence = absenceService.getOne(id);
        var absenceDto = absenceMapper.toDtoAll(absence);
        return new ResponseEntity<>(
                new RestResponse().response(
                        HttpStatus.OK,absenceDto,
                        "PointageSimpleResponse"),
                HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Absence request) {
        return null;
    }

    
    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        Absence absence = absenceService.findById(id);
        absenceService.delete(id);
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.ACCEPTED, absence, "DeleteAbsence"),
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> findAbsencesByEtudiant(String id, int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> findByDetailsId(String id) {
        return null;
    }

    

}
