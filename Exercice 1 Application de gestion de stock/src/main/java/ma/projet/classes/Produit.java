package ma.projet.classes;

import jakarta.persistence.*;

import java.util.List;
@Entity
@NamedQuery(name = "Produit.findByPrix", query = "SELECT p FROM Produit p WHERE p.prix > 100")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String reference;
    private float prix;

    @ManyToOne
    private Categorie categorie;

    @OneToMany(mappedBy = "produit")
    private List<LigneCommandeProduit> lignes;

    public Produit() {}
    public Produit(String reference, float prix, Categorie categorie) {
        this.reference = reference;
        this.prix = prix;
        this.categorie = categorie;
    }
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getReference() { return reference; }
    public void setReference(String reference) { this.reference = reference; }

    public float getPrix() { return prix; }
    public void setPrix(float prix) { this.prix = prix; }

    public Categorie getCategorie() { return categorie; }
    public void setCategorie(Categorie categorie) { this.categorie = categorie; }

    public List<LigneCommandeProduit> getLigneCommandes() { return lignes; }
    public void setLigneCommandes(List<LigneCommandeProduit> ligneCommandes) { this.lignes = ligneCommandes; }

    @Override
    public String toString() {
        return "Produit{id=" + id + ", reference='" + reference + "', prix=" + prix + "}";
    }
}