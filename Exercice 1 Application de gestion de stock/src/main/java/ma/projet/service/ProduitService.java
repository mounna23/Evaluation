package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;

import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {
    private EntityManager em = HibernateUtil.getEntityManager();

    @Override
    public void create(Produit o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(o);
        tx.commit();
    }

    @Override
    public Produit getById(int id) {
        return em.find(Produit.class, id);
    }

    @Override
    public List<Produit> getAll() {
        return em.createQuery("from Produit", Produit.class).getResultList();
    }

    @Override
    public void update(Produit o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(o);
        tx.commit();
    }

    @Override
    public void delete(Produit o) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.remove(em.contains(o) ? o : em.merge(o));
        tx.commit();
    }

    // 🔹 Produits par catégorie
    public List<Produit> getProduitsByCategorie(int idCategorie) {
        return em.createQuery("from Produit p where p.categorie.id = :id", Produit.class)
                .setParameter("id", idCategorie)
                .getResultList();
    }

    // 🔹 Produits entre deux dates
    public List<Produit> getProduitsCommandesEntre(Date d1, Date d2) {
        return em.createQuery(
                        "SELECT DISTINCT l.produit FROM LigneCommandeProduit l WHERE l.commande.date BETWEEN :d1 AND :d2",
                        Produit.class)
                .setParameter("d1", d1)
                .setParameter("d2", d2)
                .getResultList();
    }

    // 🔹 Produits d'une commande donnée
    public List<Object[]> getProduitsParCommande(int idCommande) {
        return em.createQuery(
                        "SELECT p.reference, p.prix, l.quantite FROM LigneCommandeProduit l JOIN l.produit p WHERE l.commande.id = :id")
                .setParameter("id", idCommande)
                .getResultList();
    }

    // 🔹 Produits dont le prix > 100
    public List<Produit> getProduitsPrixSuperieur100() {
        return em.createNamedQuery("Produit.findByPrix", Produit.class).getResultList();
    }
}
