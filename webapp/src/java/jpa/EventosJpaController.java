/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa;

import bd.Eventos;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import bd.TipoEventos;
import bd.Locais;
import jpa.exceptions.NonexistentEntityException;

/**
 *
 * @author nuno
 */
public class EventosJpaController {

    public EventosJpaController() {
        emf = Persistence.createEntityManagerFactory("webappPU");
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Eventos eventos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEventos tipoEventos = eventos.getTipoEventos();
            if (tipoEventos != null) {
                tipoEventos = em.getReference(tipoEventos.getClass(), tipoEventos.getId());
                eventos.setTipoEventos(tipoEventos);
            }
            Locais locais = eventos.getLocais();
            if (locais != null) {
                locais = em.getReference(locais.getClass(), locais.getId());
                eventos.setLocais(locais);
            }
            em.persist(eventos);
            if (tipoEventos != null) {
                tipoEventos.getEventosCollection().add(eventos);
                tipoEventos = em.merge(tipoEventos);
            }
            if (locais != null) {
                locais.getEventosCollection().add(eventos);
                locais = em.merge(locais);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Eventos eventos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos persistentEventos = em.find(Eventos.class, eventos.getId());
            TipoEventos tipoEventosOld = persistentEventos.getTipoEventos();
            TipoEventos tipoEventosNew = eventos.getTipoEventos();
            Locais locaisOld = persistentEventos.getLocais();
            Locais locaisNew = eventos.getLocais();
            if (tipoEventosNew != null) {
                tipoEventosNew = em.getReference(tipoEventosNew.getClass(), tipoEventosNew.getId());
                eventos.setTipoEventos(tipoEventosNew);
            }
            if (locaisNew != null) {
                locaisNew = em.getReference(locaisNew.getClass(), locaisNew.getId());
                eventos.setLocais(locaisNew);
            }
            eventos = em.merge(eventos);
            if (tipoEventosOld != null && !tipoEventosOld.equals(tipoEventosNew)) {
                tipoEventosOld.getEventosCollection().remove(eventos);
                tipoEventosOld = em.merge(tipoEventosOld);
            }
            if (tipoEventosNew != null && !tipoEventosNew.equals(tipoEventosOld)) {
                tipoEventosNew.getEventosCollection().add(eventos);
                tipoEventosNew = em.merge(tipoEventosNew);
            }
            if (locaisOld != null && !locaisOld.equals(locaisNew)) {
                locaisOld.getEventosCollection().remove(eventos);
                locaisOld = em.merge(locaisOld);
            }
            if (locaisNew != null && !locaisNew.equals(locaisOld)) {
                locaisNew.getEventosCollection().add(eventos);
                locaisNew = em.merge(locaisNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eventos.getId();
                if (findEventos(id) == null) {
                    throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos eventos;
            try {
                eventos = em.getReference(Eventos.class, id);
                eventos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.", enfe);
            }
            TipoEventos tipoEventos = eventos.getTipoEventos();
            if (tipoEventos != null) {
                tipoEventos.getEventosCollection().remove(eventos);
                tipoEventos = em.merge(tipoEventos);
            }
            Locais locais = eventos.getLocais();
            if (locais != null) {
                locais.getEventosCollection().remove(eventos);
                locais = em.merge(locais);
            }
            em.remove(eventos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Eventos> findEventosEntities() {
        return findEventosEntities(true, -1, -1);
    }

    public List<Eventos> findEventosEntities(int maxResults, int firstResult) {
        return findEventosEntities(false, maxResults, firstResult);
    }

    private List<Eventos> findEventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Eventos.class));
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

    public Eventos findEventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Eventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Eventos> rt = cq.from(Eventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
