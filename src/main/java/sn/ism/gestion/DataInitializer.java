package sn.ism.gestion;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sn.ism.gestion.data.entities.*;
import sn.ism.gestion.data.enums.*;
import sn.ism.gestion.data.repositories.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Component
public class DataInitializer {

    @Autowired private UtilisateurRepository utilisateurRepository;
    @Autowired private EtudiantRepository etudiantRepository;
    @Autowired private ClasseRepository classeRepository;
    @Autowired private FiliereRepository filiereRepository;
    @Autowired private VigileRepository vigileRepository;
    @Autowired private AbsenceRepository absenceRepository;
    @Autowired private SessionsCoursRepository sessionCoursRepository;
    @Autowired private CoursRepository coursRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private PaiementRepository paiementRepository;
    @Autowired private JustificationRepository justificationRepository;
    @Autowired private SalleRepository salleRepository;


    @PostConstruct
    public void init() {
        // Nettoyage
        absenceRepository.deleteAll();
        sessionCoursRepository.deleteAll();
        coursRepository.deleteAll();
        vigileRepository.deleteAll();
        etudiantRepository.deleteAll();
        classeRepository.deleteAll();
        filiereRepository.deleteAll();
        utilisateurRepository.deleteAll();
        justificationRepository.deleteAll();


        // Utilisateurs
        List<Utilisateur> utilisateurs = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Utilisateur u = new Utilisateur();
            u.setNom("Nom" + i);
            u.setPrenom("Prenom" + i);
            u.setLogin("login" + i);
            u.setMotDePasse(passwordEncoder.encode("pass" + i));
            u.setPhoto("absent.img");
            if (i == 1 || i == 2) {
                u.setRole(Role.VIGILE);
            }
            else if (i == 3)
            {
                u.setRole(Role.ADMIN);

            } else {
                u.setRole(Role.ETUDIANT);
            }
            utilisateurs.add(u);
        }
        utilisateurRepository.saveAll(utilisateurs);

        // Filières
        List<Filiere> filieres = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            Filiere f = new Filiere();
            f.setNom("Filiere " + i);
            filieres.add(f);
        }
        filiereRepository.saveAll(filieres);

        List<Salle> salles = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Salle salle = new Salle();
            salle.setNom("Salle " + i);
            salle.setNumero("S" + i);
            salle.setNombrePlaces(30 + i * 5); // Nombre de places variable
            salles.add(salle);
        }
        salleRepository.saveAll(salles);

        // Classes
        List<Classe> classes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Classe c = new Classe();
            c.setLibelle("Classe " + i);
            c.setNiveau("Niveau " + i);
            c.setFiliereId(filieres.get(i % filieres.size()).getId());
            c.setAnneeScolaireId("2024-2025");
            c.setEtudiantIds(new ArrayList<>());
            classes.add(c);
        }
        classeRepository.saveAll(classes);


        // Étudiants
        List<Etudiant> etudiants = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Etudiant e = new Etudiant();
            e.setMatricule("MATRICULE" + (i + 1));
            e.setUtilisateurId(utilisateurs.get(i).getId());
            e.setTelephone("77000000" + (i + 1));
            Classe classe = classes.get(i % classes.size());
            e.setClasseId(classe.getId());
            e.setAbsenceIds(new ArrayList<>());
            etudiants.add(e);
            classe.getEtudiantIds().add(e.getId());
        }
        etudiantRepository.saveAll(etudiants);

//        for (Classe classe : classes) {
//            List<String> ids = etudiants.stream()
//                    .filter(e -> e.getClasseId().equals(classe.getId()))
//                    .map(Etudiant::getId)
//                    .toList();
//            classe.setEtudiantIds(ids);
//        }
//        classeRepository.saveAll(classes);

        List<Paiement> paiements = new ArrayList<>();
        for (int i = 0; i < etudiants.size(); i++) {
            Etudiant etudiant = etudiants.get(i);
            for (int j = 1; j <= 6; j++) {
                Paiement paiement = new Paiement();
                paiement.setEtudiantId(etudiant.getId());
                paiement.setMontant(new BigDecimal("100000"));
                paiement.setDatePaiement(LocalDate.now().minusMonths(j + i));
//                paiement.setStatus(StatusPaiment.PAYE);
                if (i == 0) {
                    paiement.setStatus(StatusPaiment.NO_PAYE);
                } else if (i == 1) {
                    paiement.setStatus(StatusPaiment.PASSE);
                } else {
                    paiement.setStatus(StatusPaiment.PAYE);
                }
                // dates différentes
                paiements.add(paiement);
            }
        }
        paiementRepository.saveAll(paiements);

        // Vigiles
        List<Vigile> vigiles = new ArrayList<>();
        for (int i = 1; i<2; i++) {
            Vigile v = new Vigile();
            v.setUtilisateurId(utilisateurs.get(i).getId());
            vigiles.add(v);
        }
        vigileRepository.saveAll(vigiles);

        // Cours
        List<Cours> coursList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Cours cours = new Cours();
            cours.setLibelle("Cours " + (i + 1));
            cours.setProfesseurId("prof-" + (i + 1));
            cours.setModuleId("module-" + (i + 1));
            cours.setClasseIds(List.of(classes.get(i % classes.size()).getId()));
            cours.setSemestre(i % 2 == 0 ? "Semestre 1" : "Semestre 2");
            cours.setNombreHeuresGlobal(30 + i * 5);
            cours.setAnneeScolaireId("2024-2025");
            coursList.add(cours);
        }
        coursRepository.saveAll(coursList);

        // Sessions de cours
        List<SessionCours> sessions = new ArrayList<>();
        int nombreSessionsParClasse = 5;
        int heureDebutInitiale = 8;
        int heureFinMax = 18;
        int nombreJours = 10; // Sessions possibles sur les 10 prochains jours

        record TimeSlot(LocalTime start, LocalTime end) {
            boolean overlaps(TimeSlot other) {
                return !(end.isBefore(other.start) || start.isAfter(other.end));
            }
        }

// Occupation des classes et salles par jour
        Map<String, Map<LocalDate, List<TimeSlot>>> occupationClasse = new HashMap<>();
        Map<String, Map<LocalDate, List<TimeSlot>>> occupationSalle = new HashMap<>();

        for (Classe classe : classes) {
            occupationClasse.putIfAbsent(classe.getId(), new HashMap<>());
            int sessionsCrees = 0;

            for (int jourOffset = 0; jourOffset < nombreJours && sessionsCrees < nombreSessionsParClasse; jourOffset++) {
                LocalDate dateSession = LocalDate.now().plusDays(jourOffset);
                occupationClasse.get(classe.getId()).putIfAbsent(dateSession, new ArrayList<>());

                // Essayer différentes heures dans la journée
                for (int h = heureDebutInitiale; h <= heureFinMax - 2; h += 2) {
                    LocalTime heureDebut = LocalTime.of(h, 0);
                    LocalTime heureFin = heureDebut.plusHours(2);
                    TimeSlot nouveauCreneau = new TimeSlot(heureDebut, heureFin);

                    // Vérifie que la classe est disponible
                    List<TimeSlot> slotsClasse = occupationClasse.get(classe.getId()).get(dateSession);
                    boolean classeLibre = slotsClasse.stream().noneMatch(slot -> slot.overlaps(nouveauCreneau));
                    if (!classeLibre) continue;

                    // Trouver une salle disponible
                    for (Salle salle : salles) {
                        occupationSalle.putIfAbsent(salle.getId(), new HashMap<>());
                        occupationSalle.get(salle.getId()).putIfAbsent(dateSession, new ArrayList<>());
                        List<TimeSlot> slotsSalle = occupationSalle.get(salle.getId()).get(dateSession);

                        boolean salleLibre = slotsSalle.stream().noneMatch(slot -> slot.overlaps(nouveauCreneau));
                        if (!salleLibre) continue;

                        // Tout est OK → créer la session
                        SessionCours session = new SessionCours();
                        session.setClasseId(classe.getId());
                        session.setDateSession(dateSession);
                        session.setHeureDebut(heureDebut);
                        session.setHeureFin(heureFin);
                        session.setNombreHeures(2);
                        session.setMode(ModeCours.PRESENTIEL);
                        session.setCoursId(coursList.get(sessionsCrees % coursList.size()).getId());
                        session.setSalleId(salle.getId());
                        session.setValide(true);

                        sessions.add(session);

                        // Marquer les créneaux comme occupés
                        slotsClasse.add(nouveauCreneau);
                        slotsSalle.add(nouveauCreneau);

                        sessionsCrees++;
                        break;
                    }

                    if (sessionsCrees >= nombreSessionsParClasse) break;
                }
            }
        }

        sessionCoursRepository.saveAll(sessions);


        // Absences fictives
        List<Absence> absences = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Absence a = new Absence();
            a.setId(UUID.randomUUID().toString());
            a.setEtudiantId(etudiants.get(i).getId());
            a.setSessionId(sessions.get(i % sessions.size()).getId());
            a.setType(i % 2 == 0 ? Situation.ABSENCE : Situation.RETARD);

            if (i == 0 || i ==2 || i==3) {
                a.setType(Situation.ABSENCE);
            } else{
                a.setType(Situation.RETARD);
            }
            a.setJustifiee(i % 2 == 0);
            a.setJustificationId("JUSTIF" + i);
            absences.add(a);
            etudiants.get(i).getAbsenceIds().add(a.getId());
        }

        absenceRepository.saveAll(absences);

        for (Etudiant etudiant : etudiants) {
            List<String> ids = absences.stream()
                    .filter(a -> a.getEtudiantId().equals(etudiant.getId()))
                    .map(Absence::getId)
                    .toList();
            etudiant.setAbsenceIds(ids);
        }
        etudiantRepository.saveAll(etudiants);


        List<Justification> justifications = new ArrayList<>();
        for (int i=0 ; i<=2 ;i++)
        {
            Justification justification = new Justification();
            justification.setAbsenceId(absences.get(i).getId());
            justification.setCommentaire("daw diangu rek dou dara");
            justification.setFichierUrl("justification" + i + ".pdf");
            justification.setStatut(StatutJustification.EN_ATTENTE);

            justifications.add(justification);
        }

        justificationRepository.saveAll(justifications);
        for (int i=0 ; i<=absences.size()-1 ;i++)
        {
            Absence absence = absences.get(i);
            if (absence.getJustificationId() == null || absence.getJustificationId().isEmpty()) {
                absence.setJustificationId(justifications.get(i).getId());
            }
        }
        absenceRepository.saveAll(absences);



        System.out.println("=== Données initialisées avec succès ===");
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void initialiserAbsencesDuJour() {
        LocalDate dateDuJour = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 100);
        List<SessionCours> sessionsDuJour = sessionCoursRepository.findByDate(dateDuJour);

        List<Absence> absences = new ArrayList<>();

        for (SessionCours session : sessionsDuJour) {
            List<Etudiant> etudiants = etudiantRepository.findByclasseId(session.getClasseId());
            for (Etudiant etudiant : etudiants) {
                Optional<Absence> dejaCree = absenceRepository
                        .findOneBySessionIdAndEtudiantId(session.getId(), etudiant.getId());
                if (dejaCree.isEmpty()) {
                    Absence absence = new Absence();
                    absence.setId(UUID.randomUUID().toString());
                    absence.setEtudiantId(etudiant.getId());
                    absence.setSessionId(session.getId());
                    absence.setType(Situation.ABSENCE);
                    absence.setJustifiee(false);
                    absences.add(absence);
                }
            }
        }

        if (!absences.isEmpty()) {
            absenceRepository.saveAll(absences);
            System.out.println("Absences du jour " + dateDuJour + " enregistrées : " + absences.size());
        } else {
            System.out.println("Aucune absence créée pour " + dateDuJour);
        }
    }

}