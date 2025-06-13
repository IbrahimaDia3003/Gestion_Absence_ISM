package sn.ism.gestion.Security.controllers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sn.ism.gestion.Config.Security.JwtService;
import sn.ism.gestion.Security.controllers.IUtilisateurController;
import sn.ism.gestion.data.entities.Utilisateur;
import sn.ism.gestion.data.repositories.UtilisateurRepository;
import sn.ism.gestion.data.services.IUtilisateurService;
import sn.ism.gestion.utils.mapper.UtilisateurMapper;
import sn.ism.gestion.Security.DTO.Request.LoginRequest;
import sn.ism.gestion.Security.DTO.Request.UtilisateurCreateRequest;
import sn.ism.gestion.Security.DTO.Response.UtilisateurSimpleResponse;
import sn.ism.gestion.web.dto.RestResponse;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/utilisateurs")
@CrossOrigin(origins = "http://localhost:4200")
public class UtilisateurController implements IUtilisateurController {

    private final IUtilisateurService utilisateurService;
    private final UtilisateurMapper utilisateurMapper;
    private final UtilisateurRepository utilisateurRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseEntity<Map<String, Object>> login(LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getLogin(), request.getMotDePasse())
            );

            if (auth.isAuthenticated()) {
                Utilisateur utilisateur = utilisateurRepository.findByLogin(request.getLogin())
                        .orElse(null);

                if (utilisateur != null) {
                    String token = jwtService.generateToken(utilisateur.getLogin());

                    Map<String, Object> data = new HashMap<>();
                    data.put("token", token);
                    data.put("utilisateur", utilisateur); // tu peux remplacer par un DTO si tu veux masquer le mot de passe

                    return new ResponseEntity<>(
                            RestResponse.response(HttpStatus.OK, data, "Connexion réussie"),
                            HttpStatus.OK
                    );
                } else {
                    return new ResponseEntity<>(
                            RestResponse.response(HttpStatus.NOT_FOUND, null, "Utilisateur non trouvé"),
                            HttpStatus.NOT_FOUND
                    );
                }
            } else {
                return new ResponseEntity<>(
                        RestResponse.response(HttpStatus.UNAUTHORIZED, null, "Identifiants invalides"),
                        HttpStatus.UNAUTHORIZED
                );
            }
        } catch (Exception e) {
            return new ResponseEntity<>(
                    RestResponse.response(HttpStatus.UNAUTHORIZED, null, "Erreur d'authentification"),
                    HttpStatus.UNAUTHORIZED
            );
        }
    }



    public ResponseEntity<Map<String, Object>> Create(
            UtilisateurCreateRequest request, BindingResult bindingResult) {
        return  null;

    }


    @Override
    public ResponseEntity<Map<String, Object>> SelectAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Utilisateur> utilisateurs = utilisateurService.findAll(pageable);
        Page<UtilisateurSimpleResponse> response = utilisateurs.map(utilisateurMapper::toDto);
        return new ResponseEntity<>(
                RestResponse.responsePaginate(
                        HttpStatus.OK,
                        response.getContent(),
                        response.getNumber(),
                        response.getTotalPages(),
                        response.getTotalElements(),
                        response.isFirst(),
                        response.isLast(),
                        "utilisateurAllResponse"),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> SelectdById(String id) {
        var utilisateur = utilisateurService.findById(id);
        var utilisateurDto = utilisateurMapper.toDto(utilisateur);
        return new ResponseEntity<>( RestResponse.response(HttpStatus.OK,utilisateurDto, "utilisateurSimpleResponse"),
                   HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Map<String, Object>> Update(String id, Utilisateur objet) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Update'");
    }

    @Override
    public ResponseEntity<Map<String, Object>> Delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'Delete'");
    }

//    @Override
//    public ResponseEntity<Map<String, Object>> findByLogin(String login) {
//        Utilisateur utilisateur = utilisateurService.findByLogin(login);
//        UtilisateurSimpleResponse dto = utilisateurMapper.toDto(utilisateur);
//        if(dto != null)
//            return new ResponseEntity<>(
//                    RestResponse.response(HttpStatus.OK, dto, "UtilisateurSimpleResponse"),
//                    HttpStatus.OK);
//        return new ResponseEntity<>(
//                RestResponse.response(HttpStatus.NOT_FOUND, null, "UtilisateurSimpleResponse"),
//                HttpStatus.NOT_FOUND);
//    }
//
}