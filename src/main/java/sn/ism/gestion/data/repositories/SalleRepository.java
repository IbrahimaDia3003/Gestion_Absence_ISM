package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Salle;

public interface SalleRepository extends MongoRepository<Salle, String>
{
}
