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

import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.services.IJustificationService;
import sn.ism.gestion.utils.mapper.JustificationMapper;
import sn.ism.gestion.web.controllers.IJustificationWebController;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.JusitficationAllResponse;
import sn.ism.gestion.web.dto.Response.JustificationSimpleResponse;
import sn.ism.gestion.web.dto.RestResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class JustificationWebControllerImpl implements IJustificationWebController
{
    @Autowired
    private final IJustificationService justificationService;
    @Autowired
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
        Justification justification = justificationService.createJustication(request.toJustification());
        Justification entityJustification = justificationMapper.toEntity(justification);

        return new ResponseEntity<>(RestResponse.response(HttpStatus.CREATED, entityJustification, "JustificationCreate"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(int page, int size)
    {

         List<JusitficationAllResponse> all = justificationService.findAllResponse();

         int start = Math.min(page * size, all.size());
         int end = Math.min(start + size, all.size());
         List<JusitficationAllResponse> content = all.subList(start, end);

         Page<JusitficationAllResponse> response = new PageImpl<>(content, PageRequest.of(page, size), all.size());

//         Page<JusitficationAllResponse> response = pageResult.map(justificationMapper::toDtoWeb);


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
        var jusitficationSimpleResponse = justificationService.findByIdWitt(id);
        return new ResponseEntity<>(RestResponse.response(HttpStatus.OK,jusitficationSimpleResponse, "justificationSimpleResponse"),
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
