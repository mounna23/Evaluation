package ma.projet.service;

import ma.projet.beans.Femme;
import ma.projet.beans.Personne;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    @Override
    public Femme create(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return o;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public Femme update(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return o;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Femme o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Femme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Femme> query = session.createQuery("FROM Femme", Femme.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Femme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(Femme.class, id);
        } finally {
            session.close();
        }
    }

    public Integer getNombreEnfantsEntreDates(int femmeId, LocalDate dateDebut, LocalDate dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT SUM(m.nbrEnfant) FROM Mariage m WHERE m.femme.id = :femmeId AND m.dateDebut BETWEEN :dateDebut AND :dateFin";
            Query<Long> query = session.createQuery(hql, Long.class);
            query.setParameter("femmeId", femmeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            Long result = query.uniqueResult();
            return result != null ? result.intValue() : 0;

        } catch (Exception e) {
            return 0;
        } finally {
            session.close();
        }
    }


    public List<Femme> getFemmesMarieesAuMoinsDeuxFois() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2";
            Query<Femme> query = session.createQuery(hql, Femme.class);


            List<Femme> femmes = query.list();
            for (Femme femme : femmes) {

                femme.getMariages().size();
            }
            return femmes;
        } finally {
            session.close();
        }
    }
    public Femme findPlusAgee() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "FROM Femme f ORDER BY f.dateNaissance ASC";
            Query<Femme> query = session.createQuery(hql, Femme.class);
            query.setMaxResults(1);
            return query.uniqueResult();
        } finally {
            session.close();
        }
    }
}