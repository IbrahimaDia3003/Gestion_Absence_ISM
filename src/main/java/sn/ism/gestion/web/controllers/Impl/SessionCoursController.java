package sn.ism.gestion.web.controllers.Impl;

import java.time.LocalDate;
import java.util.Map;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.ism.gestion.data.services.ISessionCoursService;
import sn.ism.gestion.utils.mapper.SessionMapper;
import sn.ism.gestion.web.controllers.ISessionCoursController;
import sn.ism.gestion.web.dto.RestResponse;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;

@AllArgsConstructor
@RestController
public class SessionCoursController implements ISessionCoursController {


    private final ISessionCoursService sessionCoursService;

    private SessionMapper sessionMapper;

    @Override
    public ResponseEntity<Map<String, Object>> getSessionsDuJour(LocalDate date ,
              @RequestParam(defaultValue = "0") int page,
              @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<SessionAllResponse> session = sessionCoursService.getAllSessionCours(LocalDate.now(), pageable);
        Page<SessionAllResponse> response = session.map(sessionMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "SessionAllSimple"),
                HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Map<String, Object>> findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    
}
