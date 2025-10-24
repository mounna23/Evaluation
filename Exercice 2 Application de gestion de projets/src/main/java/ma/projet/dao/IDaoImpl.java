package ma.projet.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ma.projet.util.HibernateUtil;
import java.util.List;

public class IDaoImpl<T> implements IDao<T> {
    private Class<T> clazz;

    public IDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public boolean create(T o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(T o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(T o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public T findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.get(clazz, id);
        } finally {
            session.close();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            return session.createQuery("from " + clazz.getName()).list();
        } finally {
            session.close();
        }
    }
}