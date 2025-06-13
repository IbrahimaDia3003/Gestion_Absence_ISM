package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Paiement;

import java.util.List;
import java.util.Optional;

public interface PaiementRepository extends MongoRepository<Paiement, String>{

    Etudiant findByEtudiantIdAndMoisAndAnnee(String etudiantId, String mois, String annee);

    Optional<Paiement> findPaiementByEtudiantId(String etudiantId);


}