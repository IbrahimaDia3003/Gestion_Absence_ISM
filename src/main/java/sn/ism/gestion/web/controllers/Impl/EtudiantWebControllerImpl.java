package sn.ism.gestion.web.controllers.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.services.IEtudiantService;
import sn.ism.gestion.mobile.dto.Response.AbsenceEtudiantResponse;
import sn.ism.gestion.utils.mapper.EtudiantMapper;
import sn.ism.gestion.web.controllers.IEtudiantWebController;
import sn.ism.gestion.web.dto.Request.EtudiantSimpleRequest;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.EtudiantAllResponse;
import sn.ism.gestion.web.dto.RestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class EtudiantWebControllerImpl implements IEtudiantWebController {
    @Autowired
    private final IEtudiantService etudiantService;
    @Autowired
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
    public ResponseEntity<Map<String, Object>> SelectAll( int page,int size)
    {
        List<EtudiantAllResponse> all = etudiantService.getAllEtudiants();

        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<EtudiantAllResponse> content = all.subList(start, end);

        Page<EtudiantAllResponse> pageResult = new PageImpl<>(content, PageRequest.of(page, size), all.size());
        Page<EtudiantAllResponse> response = pageResult.map(etudiantMapper::toDto);

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
    public ResponseEntity<Map<String, Object>> justifierAbsence(String id, JustificationRequest justification) {

        Justification justificationAbsence = etudiantService.justifierAbsence(id,justification.toJustification());
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
    public ResponseEntity<Map<String, Object>> getMyListAbsences(String id,int page,int size)
    {
        List<AbsenceEtudiantResponse> all = etudiantService.getAbsencesByEtudiantId(id);

        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<AbsenceEtudiantResponse> content = all.subList(start, end);

//        Page<?> absences = etudiantService.getAbsencesByEtudiantId(id );


        PageImpl<AbsenceEtudiantResponse> response = new PageImpl<>(content, PageRequest.of(page, size), all.size());
//        Page<EtudiantAllResponse> response = pageResult.map(etudiantMapper::toDtoAll);

        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "EtudiantlistePointages"),
                HttpStatus.OK);
    }



}