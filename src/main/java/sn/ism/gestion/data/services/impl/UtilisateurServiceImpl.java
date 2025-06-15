package sn.ism.gestion.data.services.impl;

import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import sn.ism.gestion.Security.DTO.Response.UtilisateurSimpleResponse;
import sn.ism.gestion.data.entities.Admin;
import sn.ism.gestion.data.entities.Etudiant;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.data.entities.Vigile;
import sn.ism.gestion.data.enums.Role;
import sn.ism.gestion.data.repositories.AdminRepository;
import sn.ism.gestion.data.repositories.EtudiantRepository;
import sn.ism.gestion.data.repositories.UtilisateurRepository;
import sn.ism.gestion.data.repositories.VigileRepository;
import sn.ism.gestion.data.services.IUtilisateurService;

@AllArgsConstructor
@Service
public class UtilisateurServiceImpl implements IUtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepo;

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private VigileRepository vigileRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public Utilisateur create(Utilisateur object)
    {
        return utilisateurRepo.save(object);
    }

    @Override
    public Utilisateur update(String id, Utilisateur object) {
        Utilisateur existing = utilisateurRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        object.setId(existing.getId());
        return utilisateurRepo.save(object);
    }

    @Override
    public boolean delete(String id) {
        if (!utilisateurRepo.existsById(id)) return false;
        utilisateurRepo.deleteById(id);
        return true;
    }

    @Override
    public Utilisateur findById(String id) {
        return utilisateurRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    @Override
    public List<Utilisateur> findAll() {
        return utilisateurRepo.findAll();
    }


    @Override
    public UtilisateurSimpleResponse findByLogin(String login)
    {
        Utilisateur user = utilisateurRepo.findByLogin(login)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec login: " + login));

        UtilisateurSimpleResponse utilisateur = new UtilisateurSimpleResponse();
        utilisateur.setLogin(login);
        utilisateur.setNom(user.getNom());
        utilisateur.setPrenom(user.getPrenom());
        utilisateur.setId(user.getId());
        utilisateur.setRole(user.getRole());
        utilisateur.setPhoto(user.getPhoto());
        utilisateur.setMotDePasse(user.getMotDePasse());
        if (user.getRole() == Role.ETUDIANT)
        {
            List<Etudiant> etudiants = etudiantRepository.findAll();
            for (Etudiant etudiant : etudiants) {
                if (etudiant.getUtilisateurId().equals(user.getId())) {
                    utilisateur.setUserConnectId(etudiant.getId());
                    break;
                }
            }
        } else if (user.getRole() == Role.VIGILE)

        {
            List<Vigile> vigiles = vigileRepository.findAll();
            for (Vigile vigile : vigiles) {
                if (vigile.getUtilisateurId().equals(user.getId())) {
                    utilisateur.setUserConnectId(vigile.getId());
                    break;
                }
            }
        } else if (user.getRole() == Role.ADMIN)
        {
            List<Admin> admins = adminRepository.findAll();
            for (Admin admin : admins) {
                if (admin.getUtilisateurId().equals(user.getId())) {
                    utilisateur.setUserConnectId(admin.getId());
                    break;
                }
            }
        }

        return utilisateur;


    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        UtilisateurSimpleResponse utilisateur = findByLogin(login);

        return User.withUsername(utilisateur.getLogin())
                .password(utilisateur.getMotDePasse())
                .authorities("ROLE_" + utilisateur.getRole().name())
                .build();
    }
    @Override
    public Page<Utilisateur> findAll(Pageable pageable) {
        return utilisateurRepo.findAll(pageable);
    }
}
