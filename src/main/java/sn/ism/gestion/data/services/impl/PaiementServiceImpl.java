package sn.ism.gestion.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Paiement;
import sn.ism.gestion.data.repositories.PaiementRepository;
import sn.ism.gestion.data.services.IPaiementService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements IPaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Override
    public Etudiant estAjourDansPaiement(String etudiantId) {
        LocalDate maintenant = LocalDate.now();
        String mois = String.valueOf(maintenant.getMonthValue());
        String annee = String.valueOf(maintenant.getYear());
        return paiementRepository.findByEtudiantIdAndMoisAndAnnee(etudiantId, mois, annee);
    }

    @Override
    public Optional<Paiement> findPaiementByEtudiantId(String etudiantId)
    {
        return paiementRepository.findPaiementByEtudiantId(etudiantId);
    }

    @Override
    public Paiement create(Paiement object) {
        return null;
    }

    @Override
    public Paiement update(String id, Paiement object) {
        return null;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public Paiement findById(String id) {
        return null;
    }

    @Override
    public List<Paiement> findAll() {
        return List.of();
    }

}
