package ma.projet.classes;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "projets")
public class Projet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nom")
    private String nom;

    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;

    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;

    @ManyToOne
    @JoinColumn(name = "chef_projet_id")
    private Employe chefProjet; // Changé de "Employee" à "Employe"

    // Constructeurs
    public Projet() {}

    public Projet(String nom, Date dateDebut, Date dateFin) {
        this.nom = nom;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public Date getDateDebut() { return dateDebut; }
    public void setDateDebut(Date dateDebut) { this.dateDebut = dateDebut; }

    public Date getDateFin() { return dateFin; }
    public void setDateFin(Date dateFin) { this.dateFin = dateFin; }

    public Employe getChefProjet() { return chefProjet; } // Changé de getChefProjet
    public void setChefProjet(Employe chefProjet) { this.chefProjet = chefProjet; } // Changé de setChefProjet
}