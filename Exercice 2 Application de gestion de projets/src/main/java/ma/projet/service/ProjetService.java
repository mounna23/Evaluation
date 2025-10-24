package ma.projet.service;

import ma.projet.dao.IDaoImpl;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.text.SimpleDateFormat;

public class ProjetService {
    private IDaoImpl<Projet> dao;

    public ProjetService() {
        this.dao = new IDaoImpl<>(Projet.class);
    }

    // CRUD methods
    public boolean create(Projet o) {
        return dao.create(o);
    }

    public boolean update(Projet o) {
        return dao.update(o);
    }

    public boolean delete(Projet o) {
        return dao.delete(o);
    }

    public Projet findById(int id) {
        return dao.findById(id);
    }

    public List<Projet> findAll() {
        return dao.findAll();
    }

    // Specific methods
    public List<Tache> getTachesPlanifieesPourProjet(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT t FROM Tache t WHERE t.projet.id = :projetId";
            Query<Tache> query = session.createQuery(hql, Tache.class);
            query.setParameter("projetId", projetId);
            return query.list();
        } finally {
            session.close();
        }
    }

    public Map<String, Object> getTachesRealiseesAvecDatesReelles(int projetId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            // Récupérer le projet
            Projet projet = dao.findById(projetId);
            if (projet == null) {
                return null;
            }

            // Récupérer les tâches avec dates réelles
            String hql = "SELECT et.tache.id, et.tache.nom, et.dateDebutReelle, et.dateFinReelle " +
                    "FROM EmployeTache et " +
                    "WHERE et.tache.projet.id = :projetId " +
                    "ORDER BY et.tache.id";
            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setParameter("projetId", projetId);
            List<Object[]> results = query.list();

            // Formater les données
            Map<String, Object> result = new HashMap<>();
            result.put("projet", projet);
            result.put("taches", results);

            return result;
        } finally {
            session.close();
        }
    }

    // Méthode pour afficher le format demandé
    public void afficherTachesRealiseesAvecDatesReelles(int projetId) {
        Map<String, Object> result = getTachesRealiseesAvecDatesReelles(projetId);

        if (result != null) {
            Projet projet = (Projet) result.get("projet");
            List<Object[]> taches = (List<Object[]>) result.get("taches");

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
            SimpleDateFormat sdfCourt = new SimpleDateFormat("dd/MM/yyyy");

            System.out.println("Projet : " + projet.getId() +
                    "\tNom : " + projet.getNom() +
                    "\tDate début : " + sdf.format(projet.getDateDebut()));
            System.out.println("Liste des tâches:");
            System.out.println("Num Nom            Date Début Réelle   Date Fin Réelle");

            for (Object[] tache : taches) {
                int id = (int) tache[0];
                String nom = (String) tache[1];
                Date dateDebutReelle = (Date) tache[2];
                Date dateFinReelle = (Date) tache[3];

                System.out.printf("%-3d %-14s %-18s %s%n",
                        id, nom,
                        dateDebutReelle != null ? sdfCourt.format(dateDebutReelle) : "N/A",
                        dateFinReelle != null ? sdfCourt.format(dateFinReelle) : "N/A");
            }
        } else {
            System.out.println("Aucune donnée trouvée pour le projet ID: " + projetId);
        }
    }
}