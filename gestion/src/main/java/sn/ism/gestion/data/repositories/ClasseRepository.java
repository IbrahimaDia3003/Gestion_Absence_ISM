package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Classe;

public interface ClasseRepository extends MongoRepository<Classe, String> {
    
}
