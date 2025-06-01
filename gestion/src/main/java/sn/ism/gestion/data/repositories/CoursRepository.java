package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Cours;

public interface CoursRepository extends MongoRepository<Cours, String>
{

}
