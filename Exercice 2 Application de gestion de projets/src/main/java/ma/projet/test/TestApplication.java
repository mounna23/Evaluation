package ma.projet.test;

import ma.projet.service.*;
import ma.projet.classes.*;
import ma.projet.util.HibernateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestApplication {
    private static EmployeService employeService = new EmployeService();
    private static ProjetService projetService = new ProjetService();
    private static TacheService tacheService = new TacheService();
    //private static EmployeTacheService employeTacheService = new EmployeTacheService();

    public static void main(String[] args) {
        try {
            System.out.println("=== Tâches réalisées par l'employé 1 ===");
            List<Tache> tachesEmploye = employeService.getTachesRealiseesParEmploye(1);
            for (Tache tache : tachesEmploye) {
                System.out.println("Tâche: " + tache.getNom() + " - Prix: " + tache.getPrix() + " DH");
            }
            System.out.println("\n=== Projets gérés par l'employé 1 ===");
            List<Projet> projetsEmploye = employeService.getProjetsGeresParEmploye(1);
            for (Projet projet : projetsEmploye) {
                System.out.println("Projet: " + projet.getNom());
            }

            System.out.println("\n=== Taches planifiées pour le projet 1 ===");
            List<Tache> tachesProjet = projetService.getTachesPlanifieesPourProjet(1);
            for (Tache tache : tachesProjet) {
                System.out.println("Tâche: " + tache.getNom() + " - Prix: " + tache.getPrix() + " DH");
            }

            System.out.println("\n=== Tâches réalisées avec dates réelles ===");
            projetService.afficherTachesRealiseesAvecDatesReelles(1);

            tacheService.afficherTachesPrixSuperieur1000();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date dateDebut = sdf.parse("01/01/2023");
                Date dateFin = sdf.parse("31/12/2023");
                tacheService.afficherTachesRealiseesEntreDates(dateDebut, dateFin);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } finally {
            HibernateUtil.shutdown();
        }
    }
}