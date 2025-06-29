package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import sn.ism.gestion.data.entities.Admin;
public interface AdminRepository extends MongoRepository<Admin, String>
{
    Admin findAdminByUtilisateurId(String utilisateurId);
}
