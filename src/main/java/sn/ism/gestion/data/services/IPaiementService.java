package sn.ism.gestion.data.services;

import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Paiement;

import java.util.Optional;

public interface IPaiementService extends Service<Paiement> {
     Etudiant estAjourDansPaiement(String etudiantId) ;

    Optional<Paiement> findPaiementByEtudiantId(String etudiantId);

}
