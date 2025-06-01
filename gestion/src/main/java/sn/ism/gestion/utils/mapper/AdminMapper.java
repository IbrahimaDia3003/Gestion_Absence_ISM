package sn.ism.gestion.utils.mapper;

import org.mapstruct.Mapper;
import sn.ism.gestion.data.entities.Admin;
import sn.ism.gestion.web.dto.Request.AdminSimpleRequest;
import sn.ism.gestion.web.dto.Response.AdminAllResponse;
import sn.ism.gestion.web.dto.Response.AdminSimpleResponse;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminSimpleResponse toDto(Admin admin);

    Admin toEntity(Admin request);
    
    AdminAllResponse toDtoAll(AdminAllResponse admin);

    Admin toEntityR(AdminSimpleRequest request);

}
