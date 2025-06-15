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
import sn.ism.gestion.data.services.IAbsenceService;
import sn.ism.gestion.utils.mapper.AbsenceMapper;
import sn.ism.gestion.web.controllers.IAbsenceWebController;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.RestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AbsenceWebControllerImpl implements IAbsenceWebController {

    @Autowired
    private final IAbsenceService absenceService;
    @Autowired
    private final AbsenceMapper absenceMapper;


    @Override
    public ResponseEntity<Map<String, Object>> SelectAll( int page,int size)
    {
        List<AbsenceAllResponse> all = absenceService.getAllAbsences();

        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<AbsenceAllResponse> content = all.subList(start, end);

        Page<AbsenceAllResponse> pageResult = new PageImpl<>(content, PageRequest.of(page, size), all.size());
        Page<AbsenceAllResponse> response = pageResult.map(absenceMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "AbsenceAllResponses"),
                HttpStatus.OK);
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        var absence = absenceService.getOne(id);
        var absenceDto = absenceMapper.toDtoAll(absence);
        return new ResponseEntity<>(RestResponse.response(
                        HttpStatus.OK,absenceDto,
                        "AbsenceSimpleResponse"),
                HttpStatus.OK);
    }



    @Override
    public ResponseEntity<Map<String, Object>> findAbsencesByEtudiant(String id, int page, int size)
    {
        List<AbsenceAllResponse> all = absenceService.getAbsencebyEtudiantId(id);
        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<AbsenceAllResponse> content = all.subList(start, end);
        Page<AbsenceAllResponse> pageResult = new PageImpl<>(content, PageRequest.of(page, size), all.size());
        Page<AbsenceAllResponse> response = pageResult.map(absenceMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "AbsenceAllResponsesEtudiant"),
                HttpStatus.OK);

    }





}
