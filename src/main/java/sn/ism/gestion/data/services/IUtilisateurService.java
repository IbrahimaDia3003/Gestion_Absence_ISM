package sn.ism.gestion.data.services;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.Security.DTO.Response.UtilisateurSimpleResponse;
import sn.ism.gestion.data.entities.Utilisateur;

import org.springframework.security.core.userdetails.UserDetailsService;
import sn.ism.gestion.data.entities.Utilisateur;

public interface IUtilisateurService extends Service<Utilisateur>, UserDetailsService {


    UtilisateurSimpleResponse findByLogin(String login);
    Page<Utilisateur> findAll(Pageable pageable);
}