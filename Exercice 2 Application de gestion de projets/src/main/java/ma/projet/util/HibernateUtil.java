package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ma.projet.classes.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            // Création de la SessionFactory à partir de hibernate.cfg.xml
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Employe.class) // Changé de Employee à Employe
                    .addAnnotatedClass(Projet.class)
                    .addAnnotatedClass(Tache.class)
                    .addAnnotatedClass(EmployeTache.class) // Changé de EmployeeTache à EmployeTache
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Ferme les caches et les pools de connexion
        getSessionFactory().close();
    }
}