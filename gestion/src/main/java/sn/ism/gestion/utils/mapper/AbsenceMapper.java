package sn.ism.gestion.utils.mapper;

import org.mapstruct.Mapper;
import sn.ism.gestion.data.entities.Absence;
import sn.ism.gestion.web.dto.Request.AbsenceRequest;
import sn.ism.gestion.web.dto.Response.AbsenceAllResponse;
import sn.ism.gestion.web.dto.Response.AbsenceSimpleResponse;

@Mapper(componentModel = "spring")
public interface AbsenceMapper {

    AbsenceAllResponse toDto(AbsenceAllResponse absence);

    AbsenceSimpleResponse toDtoAll(AbsenceSimpleResponse etudiant);

    Absence toEntity(Absence request);

    Absence toEntityR(AbsenceRequest request);

}
