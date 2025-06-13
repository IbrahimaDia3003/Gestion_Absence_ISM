package sn.ism.gestion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sn.ism.gestion.data.entities.SessionCours;
import sn.ism.gestion.data.repositories.SessionsCoursRepository;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
class GestionApplicationTests {

//    @Autowired
//    private SessionsCoursRepository sessionCoursRepository;
//    public List<SessionCours> getSessionsDuJourWithEtudiant() {
//        LocalDate aujourdHui = LocalDate.now();
//        List<SessionCours> sessionsDuJours = sessionCoursRepository.findSessionCoursByDateSession(aujourdHui);
//        if (sessionsDuJours.isEmpty())
//            return null;
//        return sessionsDuJours;
//
//    }
    @Test
    void contextLoads()
    {
//        getSessionsDuJourWithEtudiant();
    }

}
