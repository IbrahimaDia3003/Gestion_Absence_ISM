package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Classe;
import sn.ism.gestion.data.entities.SessionCours;

import java.util.List;

public interface ClasseRepository extends MongoRepository<Classe, String>
{
    Classe findByEtudiantIdsContaining(String id);
}
