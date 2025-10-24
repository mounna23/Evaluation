package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class CategorieService implements IDao<Categorie> {
    private EntityManager em = HibernateUtil.getEntityManager();

    @Override
    public void create(Categorie o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(o);
        tx.commit();
    }

    @Override
    public Categorie getById(int id) {
        return em.find(Categorie.class, id);
    }

    @Override
    public List<Categorie> getAll() {
        return em.createQuery("from Categorie", Categorie.class).getResultList();
    }

    @Override
    public void update(Categorie o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(o);
        tx.commit();
    }

    @Override
    public void delete(Categorie o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(o) ? o : em.merge(o));
        tx.commit();
    }
}
