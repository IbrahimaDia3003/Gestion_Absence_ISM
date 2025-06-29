package sn.ism.gestion;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
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


//        // Sessions de cours
//        List<SessionCours> sessions = new ArrayList<>();
//        int sessionsParClasse = 5;
//        Random random = new Random();
//
//        // Génère une liste aléatoire de dates (dans les 30 prochains jours)
////        List<LocalDate> datesPossibles = new ArrayList<>();
//
//        List<LocalDate> datesPossibles = List.of(
//        LocalDate.of(2025, 6, 14),
//        LocalDate.of(2025, 6, 15),
//        LocalDate.of(2025, 6, 16),
//        LocalDate.of(2025, 6, 17),
//        LocalDate.of(2025, 6, 18)
//
//
//        );
////        LocalDate startDate = LocalDate.now();
////        for (int i = 0; i < 30; i += random.nextInt(2)) { // saut de 1 à 2 jours
////            datesPossibles.add(startDate.plusDays(i));
////        }
//        Collections.shuffle(datesPossibles);
//
//        // Maps pour suivre l'occupation des classes et salles
//        Map<String, Map<LocalDate, Set<LocalTime>>> occupationClasse = new HashMap<>();
//        Map<String, Map<LocalDate, Set<LocalTime>>> occupationSalle = new HashMap<>();
//
//        for (Classe classe : classes) {
//            occupationClasse.putIfAbsent(classe.getId(), new HashMap<>());
//            Set<LocalDate> datesDejaUtilisees = new HashSet<>();
//
//            int sessionsAjoutees = 0;
//            int dateIndex = 0;
//
//            while (sessionsAjoutees < sessionsParClasse && dateIndex < datesPossibles.size()) {
//                LocalDate date = datesPossibles.get(dateIndex++);
//                if (datesDejaUtilisees.contains(date)) continue; // évite répétition de dates
//                datesDejaUtilisees.add(date);
//
//                occupationClasse.get(classe.getId()).putIfAbsent(date, new HashSet<>());
//
//                for (int heure = 8; heure <= 16; heure += 2) {
//                    LocalTime heureDebut = LocalTime.of(heure, 0);
//                    LocalTime heureFin = heureDebut.plusHours(2);
//
//                    // Vérifie si classe est disponible
//                    Set<LocalTime> heuresClasse = occupationClasse.get(classe.getId()).get(date);
//                    if (heuresClasse.contains(heureDebut)) continue;
//
//                    // Trouve une salle libre
//                    Salle salleLibre = null;
//                    for (Salle salle : salles) {
//                        occupationSalle.putIfAbsent(salle.getId(), new HashMap<>());
//                        occupationSalle.get(salle.getId()).putIfAbsent(date, new HashSet<>());
//
//                        Set<LocalTime> heuresSalle = occupationSalle.get(salle.getId()).get(date);
//                        if (!heuresSalle.contains(heureDebut)) {
//                            salleLibre = salle;
//                            break;
//                        }
//                    }
//
//                    if (salleLibre == null) continue;
//
//                    // Crée la session
//                    SessionCours session = new SessionCours();
//                    session.setClasseId(classe.getId());
//                    session.setDateSession(date);
//                    session.setHeureDebut(heureDebut);
//                    session.setHeureFin(heureFin);
//                    session.setNombreHeures(2*classes.size());
//                    session.setMode(ModeCours.PRESENTIEL);
//                    session.setCoursId(coursList.get(sessionsAjoutees % coursList.size()).getId());
//                    session.setSalleId(salleLibre.getId());
//                    session.setValide(true);
//
//                    // Enregistre la session et occupe les créneaux
//                    sessions.add(session);
//                    occupationClasse.get(classe.getId()).get(date).add(heureDebut);
//                    occupationSalle.get(salleLibre.getId()).get(date).add(heureDebut);
//                    sessionsAjoutees++;
//                    break;
//                }
//            }
//        }
//
//        sessionCoursRepository.saveAll(sessions);


        // Sessions de cours
        List<SessionCours> sessions = new ArrayList<>();
        int sessionsParClasse = 5;

// Dates fixes : 14, 15, 16 juin 2025
        List<LocalDate> datesPossibles = List.of(
                LocalDate.of(2025, 6, 14),
                LocalDate.of(2025, 6, 16),
                LocalDate.of(2025, 6, 18)
        );

// Maps pour suivre l'occupation des classes et salles
        Map<String, Map<LocalDate, Set<LocalTime>>> occupationClasse = new HashMap<>();
        Map<String, Map<LocalDate, Set<LocalTime>>> occupationSalle = new HashMap<>();

        for (Classe classe : classes) {
            occupationClasse.putIfAbsent(classe.getId(), new HashMap<>());
            Set<LocalDate> datesDejaUtilisees = new HashSet<>();

            int sessionsAjoutees = 0;
            int dateIndex = 0;

            while (sessionsAjoutees < sessionsParClasse && dateIndex < datesPossibles.size()) {
                LocalDate date = datesPossibles.get(dateIndex++);
                if (datesDejaUtilisees.contains(date)) continue; // évite répétition de dates
                datesDejaUtilisees.add(date);

                occupationClasse.get(classe.getId()).putIfAbsent(date, new HashSet<>());

                for (int heure = 8; heure <= 16; heure += 2) {
                    LocalTime heureDebut = LocalTime.of(heure, 0);
                    LocalTime heureFin = heureDebut.plusHours(2);

                    // Vérifie si la classe est disponible
                    Set<LocalTime> heuresClasse = occupationClasse.get(classe.getId()).get(date);
                    if (heuresClasse.contains(heureDebut)) continue;

                    // Trouve une salle libre
                    Salle salleLibre = null;
                    for (Salle salle : salles) {
                        occupationSalle.putIfAbsent(salle.getId(), new HashMap<>());
                        occupationSalle.get(salle.getId()).putIfAbsent(date, new HashSet<>());

                        Set<LocalTime> heuresSalle = occupationSalle.get(salle.getId()).get(date);
                        if (!heuresSalle.contains(heureDebut)) {
                            salleLibre = salle;
                            break;
                        }
                    }

                    if (salleLibre == null) continue;

                    // Crée la session
                    SessionCours session = new SessionCours();
                    session.setClasseId(classe.getId());
                    session.setDateSession(date);
                    session.setHeureDebut(heureDebut);
                    session.setHeureFin(heureFin);
                    session.setNombreHeures(2 * classes.size());
                    session.setMode(ModeCours.PRESENTIEL);
                    session.setCoursId(coursList.get(sessionsAjoutees % coursList.size()).getId());
                    session.setSalleId(salleLibre.getId());
                    session.setValide(true);

                    // Enregistre la session et occupe les créneaux
                    sessions.add(session);
                    occupationClasse.get(classe.getId()).get(date).add(heureDebut);
                    occupationSalle.get(salleLibre.getId()).get(date).add(heureDebut);
                    sessionsAjoutees++;
                    break;
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