package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.util.List;

public class CommandeService implements IDao<Commande> {
    private EntityManager em = HibernateUtil.getEntityManager();

    @Override
    public void create(Commande o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(o);
        tx.commit();
    }

    @Override
    public Commande getById(int id) {
        return em.find(Commande.class, id);
    }

    @Override
    public List<Commande> getAll() {
        return em.createQuery("from Commande", Commande.class).getResultList();
    }

    @Override
    public void update(Commande o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(o);
        tx.commit();
    }

    @Override
    public void delete(Commande o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(o) ? o : em.merge(o));
        tx.commit();
    }
}
