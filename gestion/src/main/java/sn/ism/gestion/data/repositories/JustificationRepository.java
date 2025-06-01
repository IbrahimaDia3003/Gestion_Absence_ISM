package sn.ism.gestion.data.repositories;

import sn.ism.gestion.data.entities.Justification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JustificationRepository extends MongoRepository<Justification, String>{

}
