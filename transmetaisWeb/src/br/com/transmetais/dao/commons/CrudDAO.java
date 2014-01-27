package br.com.transmetais.dao.commons;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

public interface CrudDAO<T> {

	/**
	 * Adiciona uma entity na persist?ncia
	 * @param entity
	 * @throws DAOException
	 */
	public void addEntity(T entity) throws DAOException;
	
	public void addEntity(T entity, EntityManager manager) throws DAOException;

	/**
	 * Atualiza uma entity na persist?ncia
	 * @param entity
	 * @throws DAOException
	 */
	public void updateEntity(T entity) throws DAOException;
	
	public void updateEntity(T entity, EntityManager manager) throws DAOException;
	
	/**
	 * Remove uma entity na persist?ncia
	 * @param entity
	 * @throws DAOException
	 */
	public void removeEntity(T entity) throws DAOException;
	
	public void removeEntity(T entity, EntityManager manager) throws DAOException;
	
	public EntityManager createEntityManager();
	
	public void startTransaction(EntityManager manager);
	
	public void commitTransaction(EntityManager manager);
	
	public void rollbackTransaction(EntityManager manager);
	
	public void closeConnection(EntityManager manager);
	/**
	 * Retorna a lista de entities da persist?ncia
	 * @return
	 * @throws DAOException
	 */
	public List<T> findAll() throws DAOException;
	 
	/**
	 * Retorna uma entity a partir de um identificador.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public T findById(Serializable id) throws DAOException; 
	
	public List<T> findByExample(final T entity, Order... ordenacoes)  throws DAOException;
	
	public List<T> findByCriteria(final int firstResult,	final int maxResults, final Criterion... criterion);
	
	public List<T> findByCriteria(final Criterion... criterion);
	
}

