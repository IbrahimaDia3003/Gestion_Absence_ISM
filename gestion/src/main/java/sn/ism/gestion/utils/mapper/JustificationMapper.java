package sn.ism.gestion.utils.mapper;

import org.mapstruct.Mapper;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.JustificationSimpleResponse;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface JustificationMapper {

    JustificationSimpleResponse toDto(Justification Justification);

    Justification toEntity(Justification request);

    Justification toEntityR(JustificationRequest request);

}
