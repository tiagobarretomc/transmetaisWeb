package br.com.transmetais.dao.commons;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;


public class CrudDAOJPA<T> implements CrudDAO<T> {

	private Class<T> classEntity;
	protected static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");	
	
	/**
	 * Construtor
	 * @param Entity
	 * @param classEntity
	 */
	@SuppressWarnings("unchecked")
	public CrudDAOJPA() {
        this.classEntity = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	
	public EntityManager createEntityManager(){
		EntityManager manager = factory.createEntityManager();
		return manager;
	}
	
	public void startTransaction(EntityManager manager){
		manager.getTransaction().begin();
	}
	
	public void commitTransaction(EntityManager manager){
		manager.getTransaction().commit();
	}
	public void rollbackTransaction(EntityManager manager){
		manager.getTransaction().rollback();
	}
	public void closeConnection(EntityManager manager){
		manager.close();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		try {
			String query = "from " + classEntity.getSimpleName();
			return manager.createQuery(query).getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see bancohibernate.dao.commons.CrudDAO#findById(java.io.Serializable)
	 */
	public T findById(Serializable id) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		try {
			return manager.find(classEntity, id);
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

	
	
	/* (non-Javadoc)
	 * @see bancohibernate.dao.commons.CrudDAO#addEntity(java.lang.Object)
	 */
	public void addEntity(T entity) throws DAOException {
    	EntityManager manager = factory.createEntityManager(); 
        try {
            manager.getTransaction().begin();
            manager.persist(entity);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new DAOException(e);
        } finally {
        	if (manager != null) {
        		manager.close();
        	}
        }
	}

	/* (non-Javadoc)
	 * @see bancohibernate.dao.commons.CrudDAO#updateEntity(java.lang.Object)
	 */
	public void updateEntity(T entity) throws DAOException {
    	EntityManager manager = factory.createEntityManager(); 
        try {
            manager.getTransaction().begin();
            manager.merge(entity);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new DAOException(e);
        } finally {
        	if (manager != null) {
        		manager.close();
        	}
        }
	}

	/* (non-Javadoc)
	 * @see bancohibernate.dao.commons.CrudDAO#removeEntity(java.lang.Object)
	 */
	public void removeEntity(T entity) throws DAOException {
    	EntityManager manager = factory.createEntityManager(); 
        try {
            manager.getTransaction().begin();
            entity = manager.merge(entity);
            manager.remove(entity);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new DAOException(e);
        } finally {
        	if (manager != null) {
        		manager.close();
        	}
        }
	}
	
	public void removeEntity(T entity, EntityManager manager) throws DAOException {
        entity = manager.merge(entity);
        manager.remove(entity);
	}
	
	public void addEntity(T entity, EntityManager manager) throws DAOException {
		manager.persist(entity);
	}
	public void updateEntity(T entity, EntityManager manager) throws DAOException {
		manager.merge(entity);
	}
	 /**
	  * Busca o objeto de acordo com o objeto preenchido com os valores passado
	  * como exemplo.
	  *
	  * @param objeto
	  *            utilizado para realizar a busca
	  * @param ordenacoes
	  *            lista de crit?rios de ordena??o
	  * @return Lista de objetos retornada
	 * @throws DAOException 
	  */
	@SuppressWarnings("unchecked")
	
	public List<T> findByExample(final T entity, Order... ordenacoes) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		try{
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(classEntity);
		List<T> result = null;
		
		for (int i = 0; i < ordenacoes.length; i++) {
			   crit.addOrder(ordenacoes[i]);
		}
		
		result = crit.list(); 
		
		return result;
		}
		catch (Exception e) {
			throw new DAOException(e);
		}
		finally{
			if (manager != null) {
        		manager.close();
        	}
		}
	}
	
	/**
	 * Use this inside subclasses as a convenience method.
	 */
	public List<T> findByCriteria(final Criterion... criterion) {
		return findByCriteria(-1, -1, criterion);
	}

	/**
	 * Use this inside subclasses as a convenience method.
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final int firstResult,
			final int maxResults, final Criterion... criterion) {
		EntityManager manager = factory.createEntityManager(); 
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(classEntity);

		for (final Criterion c : criterion) {
			crit.add(c);
		}

		if (firstResult > 0) {
			crit.setFirstResult(firstResult);
		}

		if (maxResults > 0) {
			crit.setMaxResults(maxResults);
		}

		final List<T> result = crit.list();
		return result;
	}
	
	protected Criteria createCriteria() {
		EntityManager manager = factory.createEntityManager();
		
		Session session = (Session)manager.getDelegate();
		
		return  session.createCriteria(classEntity);

	}
	
	protected int countByCriteria(Criterion... criterion) {
		Session session = (Session) factory.createEntityManager().getDelegate();
		Criteria crit = session.createCriteria(classEntity);
		crit.setProjection(Projections.rowCount());

		for (final Criterion c : criterion) {
			crit.add(c);
		}

		return (Integer) crit.list().get(0);
	}

	
}

