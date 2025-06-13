package sn.ism.gestion.data.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import sn.ism.gestion.data.entities.Justification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface JustificationRepository extends MongoRepository<Justification, String>
{
    Justification findJustificationByAbsenceId(String absenceId);
}
