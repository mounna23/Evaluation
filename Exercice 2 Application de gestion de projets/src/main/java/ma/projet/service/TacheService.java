package ma.projet.service;

import ma.projet.dao.IDaoImpl;
import ma.projet.classes.Tache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.Date;
import java.util.List;

public class TacheService {
    private IDaoImpl<Tache> dao;

    public TacheService() {
        this.dao = new IDaoImpl<>(Tache.class);
    }

    // CRUD methods
    public boolean create(Tache o) {
        return dao.create(o);
    }

    public boolean update(Tache o) {
        return dao.update(o);
    }

    public boolean delete(Tache o) {
        return dao.delete(o);
    }

    public Tache findById(int id) {
        return dao.findById(id);
    }

    public List<Tache> findAll() {
        return dao.findAll();
    }

    // Specific methods
    public List<Tache> getTachesPrixSuperieur1000() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Utilisation d'une requête nommée
            Query<Tache> query = session.createNamedQuery("Tache.findByPrixSuperieur1000", Tache.class);
            query.setParameter("prixMin", 1000.0);
            return query.list();
        } finally {
            session.close();
        }
    }

    public List<Tache> getTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT DISTINCT et.tache FROM EmployeTache et " +
                    "WHERE et.dateDebutReelle BETWEEN :dateDebut AND :dateFin " +
                    "OR et.dateFinReelle BETWEEN :dateDebut AND :dateFin";
            Query<Tache> query = session.createQuery(hql, Tache.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);
            return query.list();
        } finally {
            session.close();
        }
    }

    // Méthode utilitaire pour afficher les tâches
    public void afficherTachesPrixSuperieur1000() {
        List<Tache> taches = getTachesPrixSuperieur1000();
        System.out.println("=== Tâches avec prix > 1000 DH ===");
        for (Tache tache : taches) {
            System.out.println("ID: " + tache.getId() +
                    " - Nom: " + tache.getNom() +
                    " - Prix: " + tache.getPrix() + " DH");
        }
    }

    public void afficherTachesRealiseesEntreDates(Date dateDebut, Date dateFin) {
        List<Tache> taches = getTachesRealiseesEntreDates(dateDebut, dateFin);
        System.out.println("=== Tâches réalisées entre " + dateDebut + " et " + dateFin + " ===");
        for (Tache tache : taches) {
            System.out.println("ID: " + tache.getId() +
                    " - Nom: " + tache.getNom() +
                    " - Prix: " + tache.getPrix() + " DH");
        }
    }
}