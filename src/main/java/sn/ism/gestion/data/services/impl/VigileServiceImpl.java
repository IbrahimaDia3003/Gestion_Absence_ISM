package sn.ism.gestion.data.services.impl;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sn.ism.gestion.data.entities.Vigile;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.data.enums.Role;
import sn.ism.gestion.data.repositories.UtilisateurRepository;
import sn.ism.gestion.data.repositories.VigileRepository;
import sn.ism.gestion.data.services.IVigileService;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.utils.mapper.VigileMapper;
import sn.ism.gestion.utils.mapper.UtilisateurMapper;
import sn.ism.gestion.web.dto.Request.VigileSimpleRequest;
import sn.ism.gestion.web.dto.Response.VigileAllResponse;

@Service
@RequiredArgsConstructor
public class VigileServiceImpl implements IVigileService {

    @Autowired
    private VigileRepository vigileRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    private final VigileMapper vigileMapper;


    @Override
    public Vigile create(Vigile vigile) {
        return vigileRepository.save(vigile);
    }

    public Vigile createVigile(VigileSimpleRequest vigileSimpleRequest) {
        if (vigileSimpleRequest.getUtilisateurcreate() == null) {
            throw new IllegalArgumentException("Les informations de l'utilisateur sont requises.");
        }
        var login = vigileSimpleRequest.getUtilisateurcreate().getLogin();
        var existingUser = utilisateurRepository.findByLogin(login);
        if (existingUser.isPresent()) {
            throw new EntityNotFoundExecption("Un utilisateur avec ce login existe déjà");
        }
        Utilisateur utilisateur = utilisateurMapper.toEntity(vigileSimpleRequest.getUtilisateurcreate());
        utilisateur.setRole(Role.VIGILE);
        utilisateur = utilisateurRepository.save(utilisateur);
        Vigile vigileCreate = vigileMapper.toEntityR(vigileSimpleRequest);
        vigileCreate.setUtilisateurId(utilisateur.getId());

        return vigileRepository.save(vigileCreate);
    }



    @Override
    public Vigile update(String id, Vigile vigile) {
        Optional<Vigile> existingVigileOpt = vigileRepository.findById(id);
        if (existingVigileOpt.isPresent()) {
            Vigile existingVigile = existingVigileOpt.get();
            existingVigile.setUtilisateurId(vigile.getUtilisateurId());
            return vigileRepository.save(existingVigile);
        } else {
            throw new RuntimeException("Vigile avec l'id " + id + " non trouvé");
        }
    }

    @Override
    public boolean delete(String id) {
        if (vigileRepository.existsById(id)) {
            vigileRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Vigile findById(String id) {
        return vigileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vigile avec l'id " + id + " non trouvé"));
    }

    @Override
    public List<Vigile> findAll() {
        return vigileRepository.findAll();
    }


   @Override
    public Page<VigileAllResponse> getAllVigiles(Pageable pageable) {
        Page<Vigile> Vigiles = vigileRepository.findAll(pageable);

        return Vigiles.map(e -> {
            VigileAllResponse dto = new VigileAllResponse();
            dto.setId(e.getId());

            utilisateurRepository.findById(e.getUtilisateurId()).ifPresent(u -> {
                dto.setUtilisateurId(u.getId());
                dto.setLogin(u.getLogin());
                dto.setNom(u.getNom());
                dto.setPrenom(u.getPrenom());
            });

            return dto;
        });
    }
    @Override
    public VigileAllResponse getOne(String id) {
        Vigile vigile = vigileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun Vigile trouvé"));

        Utilisateur utilisateur = utilisateurRepository.findById(vigile.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        VigileAllResponse dto = new VigileAllResponse();
        dto.setId(vigile.getId());
        dto.setUtilisateurId(utilisateur.getId());
        dto.setLogin(utilisateur.getLogin());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());

        return dto;
    }
}
