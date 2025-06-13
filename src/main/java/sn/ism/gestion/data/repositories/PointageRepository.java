package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Pointages;

import java.util.List;

public interface PointageRepository extends MongoRepository<Pointages, String>
{
    List<Pointages> findPointagesByVigileId(String vigileId);
}
