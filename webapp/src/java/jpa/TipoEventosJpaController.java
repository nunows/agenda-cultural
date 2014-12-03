/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import bd.TipoEventos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bd.Eventos;
import java.util.ArrayList;
import java.util.Collection;
import jpa.exceptions.IllegalOrphanException;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author nuno
 */
public class TipoEventosJpaController {

    public TipoEventosJpaController() {
        emf = Persistence.createEntityManagerFactory("webappPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEventos tipoEventos) {
        if (tipoEventos.getEventosCollection() == null) {
            tipoEventos.setEventosCollection(new ArrayList<Eventos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Eventos> attachedEventosCollection = new ArrayList<Eventos>();
            for (Eventos eventosCollectionEventosToAttach : tipoEventos.getEventosCollection()) {
                eventosCollectionEventosToAttach = em.getReference(eventosCollectionEventosToAttach.getClass(), eventosCollectionEventosToAttach.getId());
                attachedEventosCollection.add(eventosCollectionEventosToAttach);
            }
            tipoEventos.setEventosCollection(attachedEventosCollection);
            em.persist(tipoEventos);
            for (Eventos eventosCollectionEventos : tipoEventos.getEventosCollection()) {
                TipoEventos oldTipoEventosOfEventosCollectionEventos = eventosCollectionEventos.getTipoEventos();
                eventosCollectionEventos.setTipoEventos(tipoEventos);
                eventosCollectionEventos = em.merge(eventosCollectionEventos);
                if (oldTipoEventosOfEventosCollectionEventos != null) {
                    oldTipoEventosOfEventosCollectionEventos.getEventosCollection().remove(eventosCollectionEventos);
                    oldTipoEventosOfEventosCollectionEventos = em.merge(oldTipoEventosOfEventosCollectionEventos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEventos tipoEventos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEventos persistentTipoEventos = em.find(TipoEventos.class, tipoEventos.getId());
            Collection<Eventos> eventosCollectionOld = persistentTipoEventos.getEventosCollection();
            Collection<Eventos> eventosCollectionNew = tipoEventos.getEventosCollection();
            List<String> illegalOrphanMessages = null;
            for (Eventos eventosCollectionOldEventos : eventosCollectionOld) {
                if (!eventosCollectionNew.contains(eventosCollectionOldEventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Eventos " + eventosCollectionOldEventos + " since its tipoEventos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Eventos> attachedEventosCollectionNew = new ArrayList<Eventos>();
            for (Eventos eventosCollectionNewEventosToAttach : eventosCollectionNew) {
                eventosCollectionNewEventosToAttach = em.getReference(eventosCollectionNewEventosToAttach.getClass(), eventosCollectionNewEventosToAttach.getId());
                attachedEventosCollectionNew.add(eventosCollectionNewEventosToAttach);
            }
            eventosCollectionNew = attachedEventosCollectionNew;
            tipoEventos.setEventosCollection(eventosCollectionNew);
            tipoEventos = em.merge(tipoEventos);
            for (Eventos eventosCollectionNewEventos : eventosCollectionNew) {
                if (!eventosCollectionOld.contains(eventosCollectionNewEventos)) {
                    TipoEventos oldTipoEventosOfEventosCollectionNewEventos = eventosCollectionNewEventos.getTipoEventos();
                    eventosCollectionNewEventos.setTipoEventos(tipoEventos);
                    eventosCollectionNewEventos = em.merge(eventosCollectionNewEventos);
                    if (oldTipoEventosOfEventosCollectionNewEventos != null && !oldTipoEventosOfEventosCollectionNewEventos.equals(tipoEventos)) {
                        oldTipoEventosOfEventosCollectionNewEventos.getEventosCollection().remove(eventosCollectionNewEventos);
                        oldTipoEventosOfEventosCollectionNewEventos = em.merge(oldTipoEventosOfEventosCollectionNewEventos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tipoEventos.getId();
                if (findTipoEventos(id) == null) {
                    throw new NonexistentEntityException("The tipoEventos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEventos tipoEventos;
            try {
                tipoEventos = em.getReference(TipoEventos.class, id);
                tipoEventos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEventos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Eventos> eventosCollectionOrphanCheck = tipoEventos.getEventosCollection();
            for (Eventos eventosCollectionOrphanCheckEventos : eventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This TipoEventos (" + tipoEventos + ") cannot be destroyed since the Eventos " + eventosCollectionOrphanCheckEventos + " in its eventosCollection field has a non-nullable tipoEventos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tipoEventos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEventos> findTipoEventosEntities() {
        return findTipoEventosEntities(true, -1, -1);
    }

    public List<TipoEventos> findTipoEventosEntities(int maxResults, int firstResult) {
        return findTipoEventosEntities(false, maxResults, firstResult);
    }

    private List<TipoEventos> findTipoEventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEventos.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoEventos findTipoEventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEventos> rt = cq.from(TipoEventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
