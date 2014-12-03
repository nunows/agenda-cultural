/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import bd.Locais;
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
public class LocaisJpaController {

    public LocaisJpaController() {
        emf = Persistence.createEntityManagerFactory("webappPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Locais locais) {
        if (locais.getEventosCollection() == null) {
            locais.setEventosCollection(new ArrayList<Eventos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Eventos> attachedEventosCollection = new ArrayList<Eventos>();
            for (Eventos eventosCollectionEventosToAttach : locais.getEventosCollection()) {
                eventosCollectionEventosToAttach = em.getReference(eventosCollectionEventosToAttach.getClass(), eventosCollectionEventosToAttach.getId());
                attachedEventosCollection.add(eventosCollectionEventosToAttach);
            }
            locais.setEventosCollection(attachedEventosCollection);
            em.persist(locais);
            for (Eventos eventosCollectionEventos : locais.getEventosCollection()) {
                Locais oldLocaisOfEventosCollectionEventos = eventosCollectionEventos.getLocais();
                eventosCollectionEventos.setLocais(locais);
                eventosCollectionEventos = em.merge(eventosCollectionEventos);
                if (oldLocaisOfEventosCollectionEventos != null) {
                    oldLocaisOfEventosCollectionEventos.getEventosCollection().remove(eventosCollectionEventos);
                    oldLocaisOfEventosCollectionEventos = em.merge(oldLocaisOfEventosCollectionEventos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Locais locais) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Locais persistentLocais = em.find(Locais.class, locais.getId());
            Collection<Eventos> eventosCollectionOld = persistentLocais.getEventosCollection();
            Collection<Eventos> eventosCollectionNew = locais.getEventosCollection();
            List<String> illegalOrphanMessages = null;
            for (Eventos eventosCollectionOldEventos : eventosCollectionOld) {
                if (!eventosCollectionNew.contains(eventosCollectionOldEventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Eventos " + eventosCollectionOldEventos + " since its locais field is not nullable.");
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
            locais.setEventosCollection(eventosCollectionNew);
            locais = em.merge(locais);
            for (Eventos eventosCollectionNewEventos : eventosCollectionNew) {
                if (!eventosCollectionOld.contains(eventosCollectionNewEventos)) {
                    Locais oldLocaisOfEventosCollectionNewEventos = eventosCollectionNewEventos.getLocais();
                    eventosCollectionNewEventos.setLocais(locais);
                    eventosCollectionNewEventos = em.merge(eventosCollectionNewEventos);
                    if (oldLocaisOfEventosCollectionNewEventos != null && !oldLocaisOfEventosCollectionNewEventos.equals(locais)) {
                        oldLocaisOfEventosCollectionNewEventos.getEventosCollection().remove(eventosCollectionNewEventos);
                        oldLocaisOfEventosCollectionNewEventos = em.merge(oldLocaisOfEventosCollectionNewEventos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = locais.getId();
                if (findLocais(id) == null) {
                    throw new NonexistentEntityException("The locais with id " + id + " no longer exists.");
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
            Locais locais;
            try {
                locais = em.getReference(Locais.class, id);
                locais.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The locais with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Eventos> eventosCollectionOrphanCheck = locais.getEventosCollection();
            for (Eventos eventosCollectionOrphanCheckEventos : eventosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Locais (" + locais + ") cannot be destroyed since the Eventos " + eventosCollectionOrphanCheckEventos + " in its eventosCollection field has a non-nullable locais field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(locais);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Locais> findLocaisEntities() {
        return findLocaisEntities(true, -1, -1);
    }

    public List<Locais> findLocaisEntities(int maxResults, int firstResult) {
        return findLocaisEntities(false, maxResults, firstResult);
    }

    private List<Locais> findLocaisEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Locais.class));
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

    public Locais findLocais(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Locais.class, id);
        } finally {
            em.close();
        }
    }

    public int getLocaisCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Locais> rt = cq.from(Locais.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
