package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Vigile;

public interface VigileRepository extends MongoRepository<Vigile, String> {
    
}
