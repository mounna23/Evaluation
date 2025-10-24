package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.util.Date;
import java.util.List;

public class TestApplication {
    public static void main(String[] args) {
        CategorieService cs = new CategorieService();
        ProduitService ps = new ProduitService();
        CommandeService cms = new CommandeService();
        LigneCommandeService lcs = new LigneCommandeService();


        Categorie c1 = new Categorie("PC", "Ordinateurs");
        cs.create(c1);
        Categorie c2 = new Categorie("ACC", "Accessoires");
        cs.create(c2);


        Produit p1 = new Produit("ES12", 120, c1);
        Produit p2 = new Produit("ZR85", 100, c1);
        Produit p3 = new Produit("EE85", 200, c2);
        ps.create(p1);
        ps.create(p2);
        ps.create(p3);


        Commande cmd1 = new Commande(new Date(113, 2, 14)); // 14 Mars 2013
        cms.create(cmd1);


        lcs.create(new LigneCommandeProduit(p1, cmd1, 7));
        lcs.create(new LigneCommandeProduit(p2, cmd1, 14));
        lcs.create(new LigneCommandeProduit(p3, cmd1, 5));

        System.out.println("\nCommande : " + cmd1.getId() + "    Date : " + cmd1.getDate());
        System.out.println("Référence\tPrix\tQuantité");
        List<Object[]> produitsCmd = ps.getProduitsParCommande(cmd1.getId());
        for (Object[] obj : produitsCmd) {
            System.out.println(obj[0] + "\t" + obj[1] + " DH\t" + obj[2]);
        }

        System.out.println("\n=== Produits prix > 100 DH ===");
        for (Produit p : ps.getProduitsPrixSuperieur100()) {
            System.out.println(p.getReference() + " - " + p.getPrix() + " DH");
        }
    }
}
