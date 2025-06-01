package sn.ism.gestion.utils.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import sn.ism.gestion.data.entities.Admin;
import sn.ism.gestion.web.dto.Request.AdminSimpleRequest;
import sn.ism.gestion.web.dto.Response.AdminAllResponse;
import sn.ism.gestion.web.dto.Response.AdminSimpleResponse;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-01T14:10:14+0000",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class AdminMapperImpl implements AdminMapper {

    @Override
    public AdminSimpleResponse toDto(Admin admin) {
        if ( admin == null ) {
            return null;
        }

        AdminSimpleResponse adminSimpleResponse = new AdminSimpleResponse();

        adminSimpleResponse.setId( admin.getId() );

        return adminSimpleResponse;
    }

    @Override
    public Admin toEntity(Admin request) {
        if ( request == null ) {
            return null;
        }

        Admin admin = new Admin();

        admin.setId( request.getId() );
        admin.setCreatedAt( request.getCreatedAt() );
        admin.setUpdatedAt( request.getUpdatedAt() );
        admin.setUtilisateurId( request.getUtilisateurId() );

        return admin;
    }

    @Override
    public AdminAllResponse toDtoAll(AdminAllResponse admin) {
        if ( admin == null ) {
            return null;
        }

        AdminAllResponse adminAllResponse = new AdminAllResponse();

        adminAllResponse.setId( admin.getId() );
        adminAllResponse.setNom( admin.getNom() );
        adminAllResponse.setPrenom( admin.getPrenom() );
        adminAllResponse.setLogin( admin.getLogin() );

        return adminAllResponse;
    }

    @Override
    public Admin toEntityR(AdminSimpleRequest request) {
        if ( request == null ) {
            return null;
        }

        Admin admin = new Admin();

        return admin;
    }
}
