package sn.ism.gestion.web.controllers.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RestController;
import sn.ism.gestion.data.entities.SessionCours;
import sn.ism.gestion.data.services.IAbsenceService;
import sn.ism.gestion.data.services.ISessionCoursService;
import sn.ism.gestion.utils.mapper.SessionMapper;
import sn.ism.gestion.web.controllers.ISessionCoursWebController;
import sn.ism.gestion.web.dto.Request.SessionRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.SessionSimpleResponse;
import sn.ism.gestion.web.dto.RestResponse;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;

@AllArgsConstructor
@RestController
public class SessionCoursWebController implements ISessionCoursWebController {


    @Autowired
    private final ISessionCoursService sessionCoursService;
    @Autowired
    private SessionMapper sessionMapper;
    @Autowired
    private IAbsenceService absenceService;

    @Override
    public ResponseEntity<Map<String, Object>> getSessionsDuJour(int page, int size) {

        List<SessionAllResponse> all = sessionCoursService.getAllSessionCoursDuJour();

        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<SessionAllResponse> content = all.subList(start, end);

        Page<SessionAllResponse> response = new PageImpl<>(content, PageRequest.of(page, size), all.size());;
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
    public ResponseEntity<Map<String, Object>> findById(String id)
    {
        SessionCours session = sessionCoursService.findById(id);

        if (session == null) {
            return new ResponseEntity<>(
                    RestResponse.response(HttpStatus.NOT_FOUND, null, "Session not found"),
                    HttpStatus.NOT_FOUND);
        }
        SessionSimpleResponse response = sessionMapper.toDtoMobileS(session);

        return new ResponseEntity<>(
                RestResponse.response(HttpStatus.OK, response, "SessionSimpleResponse "),
                HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> Create(SessionRequest sessionData)
    {
        SessionCours session = sessionMapper.toEntity(sessionData);
        SessionCours createdSession = sessionCoursService.create(session);
        if (createdSession == null) {
            return new ResponseEntity<>(
                    RestResponse.response(HttpStatus.INTERNAL_SERVER_ERROR, null, "Failed to create session"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(
                        RestResponse.response(HttpStatus.CREATED, sessionMapper.toDtoMobileS(createdSession), "SessionRequest"),
                        HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(int page, int size)
    {
        List<SessionAllResponse> all = sessionCoursService.getAllSessionCours();
        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<SessionAllResponse> content = all.subList(start, end);
        Page<SessionAllResponse> response = new PageImpl<>(content, PageRequest.of(page, size), all.size());
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "SessionAllResponses"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> getAbsencesBySessionId(String id, int page, int size)
    {
    List<AbsenceAllResponse> absences = absenceService.getAbsencebySessionId(id);
        if (absences == null || absences.isEmpty()) {
            return new ResponseEntity<>(
                    RestResponse.response(HttpStatus.NO_CONTENT, null, "No absences found for this session"),
                    HttpStatus.NO_CONTENT);
        }

        int start = Math.min(page * size, absences.size());
        int end = Math.min(start + size, absences.size());
        List<AbsenceAllResponse> content = absences.subList(start, end);
        Page<AbsenceAllResponse> response = new PageImpl<>(content, PageRequest.of(page, size), absences.size());

        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "AbsenceResponses"),
                HttpStatus.OK);

    }

}
