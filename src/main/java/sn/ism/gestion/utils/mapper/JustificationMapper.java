package sn.ism.gestion.utils.mapper;

import org.mapstruct.Mapper;
import sn.ism.gestion.data.entities.Justification;
import sn.ism.gestion.mobile.dto.Response.JustificationSimpleMobileResponse;
import sn.ism.gestion.web.dto.Request.JustificationRequest;
import sn.ism.gestion.web.dto.Response.JusitficationAllResponse;

@Mapper(componentModel = "spring")
public interface JustificationMapper {

    JustificationSimpleMobileResponse toDtoMobile(Justification Justification);

    JustificationSimpleMobileResponse toDto(Justification Justification);

//    JusitficationAllResponse toDtoWeb(Justification Justification);


    Justification toEntity(Justification request);

    Justification toEntityR(JustificationRequest request);

}
