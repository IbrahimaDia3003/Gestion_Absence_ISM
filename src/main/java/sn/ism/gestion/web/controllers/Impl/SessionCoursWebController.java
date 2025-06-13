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
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.entities.SessionCours;
import sn.ism.gestion.data.services.IAbsenceService;
import sn.ism.gestion.data.services.ISessionCoursService;
import sn.ism.gestion.utils.mapper.SessionMapper;
import sn.ism.gestion.web.controllers.ISessionCoursWebController;
import sn.ism.gestion.web.dto.Response.AbsenceSimpleResponse;
import sn.ism.gestion.web.dto.Response.SessionSimpleResponse;
import sn.ism.gestion.web.dto.RestResponse;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;

@AllArgsConstructor
@RestController
public class SessionCoursWebController implements ISessionCoursWebController {


    @Autowired
    private final ISessionCoursService sessionCoursService;

    @Autowired
    private final IAbsenceService absenceService;

    @Autowired
    private SessionMapper sessionMapper;


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
    public ResponseEntity<Map<String, Object>> findSessionCoursByDateSession(int page, int size)
    {
        List<SessionCours> all = sessionCoursService.findSessionCoursByDateSession();
        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<SessionCours> content = all.subList(start, end);
        // Convert SessionCours to SessionAllResponse

        Page<SessionCours> response = new PageImpl<>(content, PageRequest.of(page, size), all.size());;
        return
                new ResponseEntity<>(
                        RestResponse.responsePaginate(
                                HttpStatus.OK,
                                response.getContent(),
                                response.getNumber(),
                                response.getTotalPages(),
                                response.getTotalElements(),
                                response.isFirst(),
                                response.isLast(),
                                "SessionCoursByDateSession"),
                        HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Map<String, Object>> findAbsencesBySessionId(String sessionId, int page, int size)
    {
        List<AbsenceSimpleResponse> all = absenceService.getAbsencebySessionId(sessionId);
        int start = Math.min(page * size, all.size());
        int end = Math.min(start + size, all.size());
        List<AbsenceSimpleResponse> content = all.subList(start, end);

        Page<AbsenceSimpleResponse> response = new PageImpl<>(content, PageRequest.of(page, size), all.size());

        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "AbsencesBySessionId"),
                HttpStatus.OK);
        
    }

}
