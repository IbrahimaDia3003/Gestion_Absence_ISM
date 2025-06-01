package sn.ism.gestion.data.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Utilisateur;

public interface UtilisateurRepository extends MongoRepository<Utilisateur, String> {
    
    Optional<Utilisateur> findByLogin(String login);
    boolean existsByLogin(String login);
}
