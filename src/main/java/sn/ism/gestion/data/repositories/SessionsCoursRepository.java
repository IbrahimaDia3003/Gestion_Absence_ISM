package sn.ism.gestion.data.repositories;

import org.springframework.data.mongodb.repository.Query;
import sn.ism.gestion.data.entities.SessionCours;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SessionsCoursRepository extends MongoRepository<SessionCours, String>
{
    @Query("{ 'date' : ?0 }")
    List<SessionCours> findByDate(LocalDate date);
    List<SessionCours> findSessionCoursByDateSession(LocalDate dateSession);
    List<SessionCours> findSessionCoursByClasseId(String classeId);
}
