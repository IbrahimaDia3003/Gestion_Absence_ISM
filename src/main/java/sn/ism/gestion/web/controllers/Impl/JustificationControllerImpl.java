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

import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.services.IJustificationService;
import sn.ism.gestion.utils.mapper.JustificationMapper;
import sn.ism.gestion.web.controllers.IJustificationController;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.JusitficationAllResponse;
import sn.ism.gestion.web.dto.Response.JustificationSimpleResponse;
import sn.ism.gestion.web.dto.RestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/justifications")
@CrossOrigin(origins = "http://localhost:4200")
public class JustificationControllerImpl implements IJustificationController {

    private final IJustificationService justificationService;
    private final JustificationMapper justificationMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(JustificationRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Justification justification = justificationService.createJustication(request);
        Justification entityJustification = justificationMapper.toEntity(justification);

        return new ResponseEntity<>(RestResponse.response(HttpStatus.CREATED, entityJustification, "JustificationCreate"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectByAbsenceId(String idAbsence)
    {
        Justification justification = justificationService.findJustificationByAbsenceId(idAbsence);

        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK, justification, "JustificationByAbsenceId"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Justification> responseJustif = justificationService.findAll(pageable);
<<<<<<< HEAD
=======
        Page<JustificationSimpleResponse> response = responseJustif.map(justificationMapper::toDto);

>>>>>>> fc3dfa5351683357fece5a9d73558b0e70244ce4
//        Page<JusitficationAllResponse> response = justificationService.findAllWith(pageable);
        Page<JustificationSimpleResponse> response = responseJustif.map(justificationMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "JustificationAllResponses"),
                HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> SelectdById(@PathVariable String id) {
        var justification = justificationService.findJustificationById(id);
        var justificationSimpleResponse = justificationMapper.toDto(justification);
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK,justificationSimpleResponse, "justificationSimpleResponse"),
                HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Justification request) {
        return null;
    }


    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        Justification justification = justificationService.findById(id);
        justificationService.delete(id);
        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.ACCEPTED, justification, "JustificationUpdate"),
                HttpStatus.ACCEPTED);
    }

}
