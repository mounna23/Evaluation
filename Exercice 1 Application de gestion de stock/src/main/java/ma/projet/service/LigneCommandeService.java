package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {
    private EntityManager em = HibernateUtil.getEntityManager();

    @Override
    public void create(LigneCommandeProduit o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(o);
        tx.commit();
    }

    @Override
    public LigneCommandeProduit getById(int id) {
        return em.find(LigneCommandeProduit.class, id);
    }

    @Override
    public List<LigneCommandeProduit> getAll() {
        return em.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).getResultList();
    }

    @Override
    public void update(LigneCommandeProduit o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(o);
        tx.commit();
    }

    @Override
    public void delete(LigneCommandeProduit o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(o) ? o : em.merge(o));
        tx.commit();
    }
}
