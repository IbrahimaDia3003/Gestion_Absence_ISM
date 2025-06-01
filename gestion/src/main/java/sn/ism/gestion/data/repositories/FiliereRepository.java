package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Filiere;

public interface FiliereRepository extends MongoRepository<Filiere, String> {
    
}
