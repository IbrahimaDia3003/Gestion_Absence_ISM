package sn.ism.gestion.data.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.data.entities.Admin;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Request.AdminSimpleRequest;
import sn.ism.gestion.web.dto.Response.AdminAllResponse;

public interface IAdminService extends Service<Admin> {

    AdminAllResponse getOne(String id);
    Admin createAdmin(AdminSimpleRequest adminSimpleRequest) ;
    Page<Absence> getAllAbsences(Pageable pageable);
    Page<Justification> getAllJustifications(Pageable pageable);
    Justification traiterJustification(Justification justification);
    Page<AdminAllResponse> getAllAdmins(Pageable pageable) ;
}
