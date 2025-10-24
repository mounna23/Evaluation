package ma.projet.service;

import ma.projet.beans.Homme;
import ma.projet.beans.Mariage;
import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class HommeService implements IDao<Homme> {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public Homme create(Homme o) {
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
    public Homme update(Homme o) {
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
    public boolean delete(Homme o) {
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
    public List<Homme> findAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Query<Homme> query = session.createQuery("FROM Homme", Homme.class);
            return query.list();
        } finally {
            session.close();
        }
    }

    @Override
    public Homme findById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Homme homme = session.get(Homme.class, id);
            if (homme == null) {
                System.out.println("Aucun homme trouvé avec l'ID: " + id);
            }
            return homme;
        } finally {
            session.close();
        }
    }



    public List<Object[]> getEpousesEntreDates(int hommeId, LocalDate dateDebut, LocalDate dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT m.femme, m.dateDebut, m.dateFin, m.nbrEnfant " +
                    "FROM Mariage m " +
                    "WHERE m.homme.id = :hommeId " +
                    "AND m.dateDebut BETWEEN :dateDebut AND :dateFin";

            Query<Object[]> query = session.createQuery(hql);
            query.setParameter("hommeId", hommeId);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            return query.list();
        } finally {
            session.close();
        }
    }

    public List<Homme> getHommesMariesQuatreFemmesEntreDates(LocalDate dateDebut, LocalDate dateFin) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "SELECT m.homme " +
                    "FROM Mariage m " +
                    "WHERE m.dateDebut BETWEEN :dateDebut AND :dateFin " +
                    "GROUP BY m.homme " +
                    "HAVING COUNT(DISTINCT m.femme) >= 4";

            Query<Homme> query = session.createQuery(hql, Homme.class);
            query.setParameter("dateDebut", dateDebut);
            query.setParameter("dateFin", dateFin);

            return query.list();
        } finally {
            session.close();
        }
    }

    public void afficherMariagesHomme(int hommeId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Homme homme = session.get(Homme.class, hommeId);
            if (homme == null) {
                System.out.println("Homme non trouvé avec l'ID: " + hommeId);
                return;
            }

            System.out.println("Nom : " + homme.getNom().toUpperCase() + " " + homme.getPrenom().toUpperCase());

            String hql = "SELECT m FROM Mariage m WHERE m.homme.id = :hommeId ORDER BY m.dateDebut";
            Query<Mariage> query = session.createQuery(hql, Mariage.class);
            query.setParameter("hommeId", hommeId);
            List<Mariage> mariages = query.list();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Mariages en cours
            System.out.println("Mariages En Cours :");
            int countEnCours = 1;
            boolean hasEnCours = false;

            for (Mariage m : mariages) {
                if (m.getDateFin() == null) {
                    System.out.printf("%d. Femme : %-12s %-12s   Date Début : %-12s    Nbr Enfants : %d%n",
                            countEnCours,
                            m.getFemme().getNom(),
                            m.getFemme().getPrenom(),
                            dtf.format(m.getDateDebut()),
                            m.getNbrEnfant());
                    countEnCours++;
                    hasEnCours = true;
                }
            }

            if (!hasEnCours) {
                System.out.println("Aucun mariage en cours");
            }

            // Mariages échoués
            System.out.println("\nMariages échoués :");
            int countEchoues = 1;
            boolean hasEchoues = false;

            for (Mariage m : mariages) {
                if (m.getDateFin() != null) {
                    System.out.printf("%d. Femme : %-12s %-12s   Date Début : %-12s%n",
                            countEchoues,
                            m.getFemme().getNom(),
                            m.getFemme().getPrenom(),
                            dtf.format(m.getDateDebut()));
                    System.out.printf("   Date Fin : %-12s    Nbr Enfants : %d%n",
                            dtf.format(m.getDateFin()),
                            m.getNbrEnfant());
                    countEchoues++;
                    hasEchoues = true;
                }
            }

            if (!hasEchoues) {
                System.out.println("Aucun mariage échoué");
            }
        } finally {
            session.close();
        }
    }
}