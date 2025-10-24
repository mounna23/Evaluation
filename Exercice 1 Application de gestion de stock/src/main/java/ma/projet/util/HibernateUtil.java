package ma.projet.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("stockPU");

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
