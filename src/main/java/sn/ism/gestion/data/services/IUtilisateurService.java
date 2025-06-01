package sn.ism.gestion.data.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetailsService;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Utilisateur;

import org.springframework.security.core.userdetails.UserDetailsService;
import sn.ism.gestion.data.entities.Utilisateur;

public interface IUtilisateurService extends Service<Utilisateur>, UserDetailsService {


    Utilisateur findByLogin(String login);

}