package sn.ism.gestion.utils.mapper;

import org.mapstruct.Mapper;
import sn.ism.gestion.data.entities.SessionCours;
import sn.ism.gestion.mobile.dto.Response.SessionAllMobileResponse;
import sn.ism.gestion.web.dto.Request.SessionRequest;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;
import sn.ism.gestion.web.dto.Response.SessionSimpleResponse;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    SessionAllResponse toDto(SessionCours sessionCours);

    SessionAllMobileResponse toDtoMobile(SessionCours sessionCours);
    SessionSimpleResponse toDtoMobileS(SessionCours sessionCours);

    SessionCours toEntity(SessionRequest sessionCours);

}
