package sn.ism.gestion.web.controllers.Impl;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import sn.ism.gestion.data.entities.Admin;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.data.services.IAdminService;
import sn.ism.gestion.data.services.IJustificationService;
import sn.ism.gestion.utils.mapper.AdminMapper;
import sn.ism.gestion.web.controllers.IAdminWebController;
import sn.ism.gestion.web.dto.Request.AdminSimpleRequest;
import sn.ism.gestion.web.dto.Request.JustificationValidationRequest;
import sn.ism.gestion.web.dto.Response.AdminAllResponse;
import sn.ism.gestion.web.dto.RestResponse;

@RequiredArgsConstructor
@RestController
public class AdminWebController implements IAdminWebController {

    @Autowired
    private final IJustificationService justificationService;
    @Autowired
    private final IAdminService adminService;
    @Autowired
    private final AdminMapper adminMapper;

    @Override
    public ResponseEntity<Map<String, Object>> Create(AdminSimpleRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }
        Admin admin = adminService.createAdmin(request);
        Admin entityAdmin = adminMapper.toEntity(admin);

        return new ResponseEntity<>(RestResponse.response(HttpStatus.CREATED, entityAdmin, "AdminCreate"), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(int page,int size)
    {
        Pageable pageable = PageRequest.of(page, size);
        Page<AdminAllResponse> admins = adminService.getAllAdmins(pageable);
        Page<AdminAllResponse> response = admins.map(adminMapper::toDtoAll);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "adminAllResponse"),
                HttpStatus.OK);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        var admin = adminService.getOne(id);
        var adminDto = adminMapper.toDtoAll(admin);
        return new ResponseEntity<>(
                 RestResponse.response(
                        HttpStatus.OK,adminDto,
                        "adminSimpleResponse"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Admin request) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<Map<String, Object>> traiterJustification(String justificationId, JustificationValidationRequest request)
    {
        Justification justificationAbsence = justificationService.traiterJustication(justificationId, request);

        if (justificationAbsence == null)
            return new ResponseEntity<>(
                    RestResponse.response(
                            HttpStatus.NO_CONTENT, null, "Justification not found or already processed"),
                    HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(
                RestResponse.response(
                        HttpStatus.ACCEPTED, justificationAbsence, "traitementJustifiction"),
                HttpStatus.ACCEPTED);
    }

}
