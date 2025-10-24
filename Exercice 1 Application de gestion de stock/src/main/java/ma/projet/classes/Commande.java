package ma.projet.classes;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "commande")
    private List<LigneCommandeProduit> lignes;

    public Commande() {}
    public Commande(Date date) {
        this.date = date;
    }
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }

    public List<LigneCommandeProduit> getLigneCommandes() { return lignes; }
    public void setLigneCommandes(List<LigneCommandeProduit> ligneCommandes) { this.lignes = ligneCommandes; }

    @Override
    public String toString() {
        return "Commande{id=" + id + ", date=" + date + "}";
    }
}