package sn.ism.gestion.data.services.impl;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.data.enums.Role;
import sn.ism.gestion.data.repositories.AdminRepository;
import sn.ism.gestion.data.repositories.AbsenceRepository;
import sn.ism.gestion.data.repositories.JustificationRepository;
import sn.ism.gestion.data.repositories.UtilisateurRepository;
import sn.ism.gestion.data.services.IAdminService;
import sn.ism.gestion.utils.exceptions.EntityNotFoundExecption;
import sn.ism.gestion.utils.mapper.AdminMapper;
import sn.ism.gestion.utils.mapper.UtilisateurMapper;
import sn.ism.gestion.web.dto.Request.AdminSimpleRequest;
import sn.ism.gestion.web.dto.Response.AdminAllResponse;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private AbsenceRepository absenceRepository;
    @Autowired
    private JustificationRepository justificationRepository;
    @Autowired
    private UtilisateurMapper utilisateurMapper;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin create(Admin object) {
        return adminRepository.save(object);
    }

    public Admin createAdmin(AdminSimpleRequest adminSimpleRequest) {
        if (adminSimpleRequest.getUtilisateurcreate() == null) {
            throw new IllegalArgumentException("Les informations de l'utilisateur sont requises.");
        }
        var login = adminSimpleRequest.getUtilisateurcreate().getLogin();
        var existingUser = utilisateurRepository.findByLogin(login);
        if (existingUser.isPresent()) {
            throw new EntityNotFoundExecption("Un utilisateur avec ce login existe déjà");
        }
        Utilisateur utilisateur = utilisateurMapper.toEntity(adminSimpleRequest.getUtilisateurcreate());
        utilisateur.setRole(Role.ADMIN);
        utilisateur = utilisateurRepository.save(utilisateur);
        Admin adminCreate = adminMapper.toEntityR(adminSimpleRequest);
        adminCreate.setUtilisateurId(utilisateur.getId());

        return adminRepository.save(adminCreate);
    }

    @Override
    public Admin update(String id, Admin object) {
        return adminRepository.findById(id).map(admin -> {
            admin.setUtilisateurId(object.getUtilisateurId());
            return adminRepository.save(admin);
        }).orElse(null);
    }

    @Override
    public boolean delete(String id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Admin findById(String id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return adminRepository.findAll(pageable);
    }

    @Override
    public Page<Absence> getAllAbsences(Pageable pageable) {
        return absenceRepository.findAll(pageable);
    }

    @Override
    public Page<Justification> getAllJustifications(Pageable pageable) {
        return justificationRepository.findAll(pageable);
    }

    @Override
    public Justification traiterJustification(Justification justification) {
        return justificationRepository.save(justification);
    }
    
    @Override
    public Page<AdminAllResponse> getAllAdmins(Pageable pageable) {
        Page<Admin> admins = adminRepository.findAll(pageable);

        return admins.map(e -> {
            AdminAllResponse dto = new AdminAllResponse();
            dto.setId(e.getId());

            utilisateurRepository.findById(e.getUtilisateurId()).ifPresent(u -> {
//                dto.setUtilisateurId(u.getId());
                dto.setLogin(u.getLogin());
                dto.setNom(u.getNom());
                dto.setPrenom(u.getPrenom());
            });

            return dto;
        });
    }
    
    @Override
    public AdminAllResponse getOne(String id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aucun admin trouvé"));

        Utilisateur utilisateur = utilisateurRepository.findById(admin.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        AdminAllResponse dto = new AdminAllResponse();
        dto.setId(admin.getId());
//        dto.setUtilisateurId(utilisateur.getId());
        dto.setLogin(utilisateur.getLogin());
        dto.setNom(utilisateur.getNom());
        dto.setPrenom(utilisateur.getPrenom());

        return dto;
    }

}
