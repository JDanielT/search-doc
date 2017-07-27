package br.com.zone.search.doc.daos;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

/**
 * @author daniel
 * @param <T>
 */
public class DAOGenerico<T extends BaseEntity> implements Serializable {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public DAOGenerico(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public DAOGenerico(Class<T> entityClass, EntityManager em) {
        this.entityClass = entityClass;
        this.entityManager = em;
    }

    public void salvar(T t) {
        entityManager.persist(t);
    }

    public void excluir(T entity) {
        T entityToBeRemoved = entityManager.getReference(entityClass, entity.getId());
        entityManager.remove(entityToBeRemoved);
    }

    public T atualizar(T t) {
        return entityManager.merge(t);
    }

    public T buscarPorId(Object id) {
        return entityManager.find(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    protected List<T> buscarPorParametro(String query, Object... params) {
        Query q = entityManager.createQuery(query);
        for (int i = 0; i < params.length; i++) {
            q.setParameter(i + 1, params[i]);
        }
        return q.getResultList();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> buscarTodos() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        Root<T> root = cq.from(entityClass);
        cq.select(cb.construct(entityClass));
        cq.orderBy(cb.desc(root.get("id")));
        return entityManager.createQuery(cq).getResultList();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public List<T> buscarTodos(String... atributos) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery cq = entityManager.getCriteriaBuilder().createQuery();
        Root<T> root = cq.from(entityClass);
        Selection[] selections = new Selection[atributos.length];
        for (int i = 0; i < atributos.length; i++) {
            selections[i] = root.get(atributos[i]);
        }
        cq.select(cb.construct(entityClass, selections));
        cq.orderBy(cb.desc(root.get("id")));
        return entityManager.createQuery(cq).getResultList();
    }
    
    public List<T> buscarComLimite(int paginaAtual, int tamanhoPagina) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> root = cq.from(entityClass);
        cq.select(root);
        cq.orderBy(cb.desc(root.get("id")));
        TypedQuery typedQuery = entityManager.createQuery(cq);
        typedQuery.setFirstResult(paginaAtual);
        typedQuery.setMaxResults(tamanhoPagina);
        return typedQuery.getResultList();
    }

    public List<T> buscarComLimite(int inicio, int tamanhoPagina, String consulta, Object... params) {
        Query q = entityManager.createQuery(consulta);
        q.setFirstResult(inicio);
        q.setMaxResults(tamanhoPagina);
        for (int i = 0; i < params.length; i++) {
            q.setParameter(i + 1, params[i]);
        }
        return q.getResultList();
    }

    public Long getMaxId() {
        Query q = entityManager.createQuery("SELECT MAX(entity.id) FROM " + entityClass.getSimpleName() + " entity");
        Number result = (Number) q.getSingleResult();
        return result.longValue();
    }
    
    public Long count() {
        Query q = entityManager.createQuery("SELECT COUNT(entity.id) FROM " + entityClass.getSimpleName() + " entity");
        Number result = (Number) q.getSingleResult();
        return result.longValue();
    }

    @SuppressWarnings("unchecked")
    protected <T extends Serializable> T buscarUmResultado(String query, Object... params) {
        T result = null;
        try {
            Query q = entityManager.createQuery(query);
            for (int i = 0; i < params.length; i++) {
                q.setParameter(i + 1, params[i]);
            }
            result = (T) q.getSingleResult();
        } catch (NoResultException ex) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, ex.getMessage());
        }
        return result;
    }

}
