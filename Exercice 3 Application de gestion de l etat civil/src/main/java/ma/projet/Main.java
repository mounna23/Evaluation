package ma.projet;

import ma.projet.beans.Femme;
import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.service.FemmeService;
import ma.projet.service.HommeService;
import ma.projet.service.MariageService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Main {

    private static HommeService hommeService = new HommeService();
    private static FemmeService femmeService = new FemmeService();
   // private static MariageService mariageService = new MariageService();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) throws Exception {
        executerRequetes();
        ma.projet.util.HibernateUtil.shutdown();
    }


    private static void executerRequetes() throws Exception {
        System.out.println("\n=== APPLICATION DE GESTION DE L'ÉTAT CIVIL ===");

        List<Homme> hommes = hommeService.findAll();
        Homme safiSaid = null;
        for (Homme h : hommes) {
            if ("SAID".equals(h.getNom()) && "Safi".equals(h.getPrenom())) {
                safiSaid = h;
                break;
            }
        }

        if (safiSaid == null) {
            System.out.println("SAFI SAID non trouvé!");
            return;
        }

        int safiSaidId = safiSaid.getId();


        System.out.println("\n1. Liste des femmes:");
        List<Femme> femmes = femmeService.findAll();
        femmes.forEach(f -> System.out.println("- " + f.getNom() + " " + f.getPrenom()));

        System.out.println("\n2. Femme la plus âgée:");
        Femme plusAgee = femmeService.findPlusAgee();
        if (plusAgee != null) {
            System.out.println(plusAgee.getNom() + " " + plusAgee.getPrenom() +
                    " - Née le: " + dtf.format(plusAgee.getDateNaissance()));
        }

        System.out.println("\n3. Épouses de SAFI SAID:");
        LocalDate dateDebut = LocalDate.of(1980, 1, 1);
        LocalDate dateFin = LocalDate.of(2010, 1, 1);
        List<Object[]> epouses = hommeService.getEpousesEntreDates(safiSaidId, dateDebut, dateFin);
        if (epouses.isEmpty()) {
            System.out.println("Aucune épouse trouvée pour cette période");
        } else {
            epouses.forEach(e -> System.out.println("- Femme: " + ((Femme)e[0]).getNom() + " " + ((Femme)e[0]).getPrenom() +
                    " - Date début: " + dtf.format((LocalDate)e[1])));
        }


        System.out.println("\n4. Nombre d'enfants de SALIMA SAFI:");

        Femme salimaSafi = null;
        for (Femme f : femmes) {
            if ("SAFI".equals(f.getNom()) && "Salima".equals(f.getPrenom())) {
                salimaSafi = f;
                break;
            }
        }
        if (salimaSafi != null) {
            Integer nbrEnfants = femmeService.getNombreEnfantsEntreDates(salimaSafi.getId(), dateDebut, dateFin);
            System.out.println("Nombre d'enfants: " + nbrEnfants);
        } else {
            System.out.println("SALIMA SAFI non trouvée");
        }


        System.out.println("\n5. Femmes mariées au moins deux fois:");
        List<Femme> femmesMariees2Fois = femmeService.getFemmesMarieesAuMoinsDeuxFois();
        if (femmesMariees2Fois.isEmpty()) {
            System.out.println("Aucune femme mariée au moins deux fois");
        } else {
            for (Femme f : femmesMariees2Fois) {
                System.out.println("- " + f.getNom() + " " + f.getPrenom());
            }
        }


        System.out.println("\n6. Hommes mariés à 4 femmes entre dates:");
        List<Homme> hommes4Femmes = hommeService.getHommesMariesQuatreFemmesEntreDates(dateDebut, dateFin);
        System.out.println("Nombre d'hommes: " + hommes4Femmes.size());
        hommes4Femmes.forEach(h -> System.out.println("- " + h.getNom() + " " + h.getPrenom()));


        System.out.println("\n7. Détails des mariages de SAFI SAID:");
        hommeService.afficherMariagesHomme(safiSaidId);

        System.out.println("\n=== FIN DU PROGRAMME ===");
    }
}