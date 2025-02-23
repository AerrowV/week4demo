package dat.dao;

import dat.entities.Activity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

public class ActivityDAO {

    private static EntityManagerFactory emf;
    private static ActivityDAO instance = null;

    private ActivityDAO() {}

    public static ActivityDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new ActivityDAO();
        }
        return instance;
    }

    public Activity create(Activity activity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                em.persist(activity);
                em.getTransaction().commit();
                return activity;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new RuntimeException("Error creating activity: " + e.getMessage());
            }
        }
    }

    public Activity findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Activity.class, id);
        }
    }

    public List<Activity> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT a FROM Activity a JOIN FETCH a.city", Activity.class).getResultList();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Error finding activities: " + e.getMessage());
        }
    }


    public Activity update(Activity activity) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                Activity updatedActivity = em.merge(activity);
                em.getTransaction().commit();
                return updatedActivity;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new RuntimeException("Error updating activity: " + e.getMessage());
            }
        }
    }

    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            try {
                Activity activity = em.find(Activity.class, id);
                if (activity != null) {
                    em.remove(activity);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new RuntimeException("Error deleting activity: " + e.getMessage());
            }
        }
    }
}
