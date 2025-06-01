package sn.ism.gestion.utils.mapper;

import org.mapstruct.Mapper;
import sn.ism.gestion.data.entities.SessionCours;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.web.dto.Request.UtilisateurCreateRequest;
import sn.ism.gestion.web.dto.Response.SessionAllResponse;
import sn.ism.gestion.web.dto.Response.SessionSimpleResponse;
import sn.ism.gestion.web.dto.Response.UtilisateurSimpleResponse;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    SessionAllResponse toDto(SessionAllResponse sessionCours);

    SessionCours toEntity(SessionCours sessionCours);

}
