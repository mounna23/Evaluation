package ma.projet.service;

import ma.projet.dao.IDaoImpl;
import ma.projet.classes.Employe;
import ma.projet.classes.Tache;
import ma.projet.classes.Projet;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class EmployeService {
    private IDaoImpl<Employe> dao;

    public EmployeService() {
        this.dao = new IDaoImpl<>(Employe.class);
    }

    // CRUD methods
    public boolean create(Employe o) {
        return dao.create(o);
    }

    public boolean update(Employe o) {
        return dao.update(o);
    }

    public boolean delete(Employe o) {
        return dao.delete(o);
    }

    public Employe findById(int id) {
        return dao.findById(id);
    }

    public List<Employe> findAll() {
        return dao.findAll();
    }

    // Specific methods
    public List<Tache> getTachesRealiseesParEmploye(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT et.tache FROM EmployeTache et WHERE et.employe.id = :employeId";
            Query<Tache> query = session.createQuery(hql, Tache.class);
            query.setParameter("employeId", employeId);
            return query.list();
        } finally {
            session.close();
        }
    }

    public List<Projet> getProjetsGeresParEmploye(int employeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT p FROM Projet p WHERE p.chefProjet.id = :employeId";
            Query<Projet> query = session.createQuery(hql, Projet.class);
            query.setParameter("employeId", employeId);
            return query.list();
        } finally {
            session.close();
        }
    }
}