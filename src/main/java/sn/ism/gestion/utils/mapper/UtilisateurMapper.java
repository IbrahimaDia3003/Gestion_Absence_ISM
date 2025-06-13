package sn.ism.gestion.utils.mapper;

import org.mapstruct.Mapper;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.Security.DTO.Request.UtilisateurCreateRequest;
import sn.ism.gestion.Security.DTO.Response.UtilisateurSimpleResponse;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {

    Utilisateur toEntity(UtilisateurCreateRequest request) ;
    
    UtilisateurSimpleResponse toDto(Utilisateur user);
  

}
