package sn.ism.gestion.data.services;

import java.util.Optional;

//import org.springframework.security.core.userdetails.UserDetailsService;
import sn.ism.gestion.Config.Service;
import sn.ism.gestion.data.entities.Utilisateur;

//import org.springframework.security.core.userdetails.UserDetailsService;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.web.dto.Response.UtilisateurSimpleResponse;

public interface IUtilisateurService extends Service<Utilisateur>
{
    Utilisateur findByLogin(String login);
}