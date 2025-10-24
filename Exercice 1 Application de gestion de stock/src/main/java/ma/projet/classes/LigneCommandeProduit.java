package ma.projet.classes;

import jakarta.persistence.*;

@Entity
public class LigneCommandeProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int quantite;

    @ManyToOne
    private Produit produit;

    @ManyToOne
    private Commande commande;

    public LigneCommandeProduit() {}
    public LigneCommandeProduit(Produit produit, Commande commande, int quantite) {
        this.produit = produit;
        this.commande = commande;
        this.quantite = quantite;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }

    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }

    @Override
    public String toString() {
        return "LigneCommandeProduit{id=" + id + ", quantite=" + quantite + "}";
    }
}