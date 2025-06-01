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
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.services.IEtudiantService;
import sn.ism.gestion.utils.mapper.EtudiantMapper;
import sn.ism.gestion.web.controllers.IEtudiantController;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.EtudiantAllResponse;
import sn.ism.gestion.web.dto.RestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/etudiants")
@CrossOrigin(origins = "http://localhost:4200")
public class EtudiantControllerImpl implements IEtudiantController {

    private final IEtudiantService etudiantService;
    private final EtudiantMapper etudiantMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(EtudiantSimpleRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Etudiant etudiant = etudiantService.createEtudiant(request);
        Etudiant entityEtudiant = etudiantMapper.toEntity(etudiant);

        return new ResponseEntity<>(RestResponse.response(HttpStatus.CREATED, entityEtudiant, "EtudiantCreate"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<EtudiantAllResponse> etudiants = etudiantService.getAllEtudiants(pageable);
        Page<EtudiantAllResponse> response = etudiants.map(etudiantMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "EtudiantAllResponses"),
                HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        var etudiant = etudiantService.getOne(id);
        var etudiantDto = etudiantMapper.toDtoAll(etudiant);
        return new ResponseEntity<>(
                 RestResponse.response(
                        HttpStatus.OK,etudiantDto,
                        "etudiantSimpleResponse"),
                HttpStatus.OK);
    }

    
    @Override
    public ResponseEntity<Map<String, Object>> findByMatricule(String matricule) {
        var etudiant = etudiantService.findByMat(matricule);
        var etudiantDto = etudiantMapper.toDtoAll(etudiant);
        return new ResponseEntity<>(
                 RestResponse.response(
                        HttpStatus.OK,etudiantDto,
                        "etudiantSimpleResponse"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Etudiant request) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, EtudiantSimpleRequest request) {
       return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> justifierAbsence(String id, JustificationRequest justificationRequest) {

        Absence justificationAbsence = etudiantService.justifierAbsence(id, justificationRequest);
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.ACCEPTED, justificationAbsence, "jusificationAbsence"),
                HttpStatus.ACCEPTED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        Etudiant etudiant = etudiantService.findById(id);
        etudiantService.delete(id);
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.ACCEPTED, etudiant, "Etudiant"),
                HttpStatus.ACCEPTED);
    }


    @Override
    public ResponseEntity<Map<String, Object>> getMyListAbsences(String id,
                                                                 @RequestParam(defaultValue = "0") int page,
                                                                 @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<?> absences = etudiantService.getAbsencesByEtudiantId(id, pageable);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        absences.getContent(),
                        absences.getNumber(),
                        absences.getTotalPages(),
                        absences.getTotalElements(),
                        absences.isFirst(),
                        absences.isLast(),
                        "EtudiantlistePointages"),
                HttpStatus.OK);
    }


   
}