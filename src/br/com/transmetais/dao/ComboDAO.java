package br.com.transmetais.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.transmetais.dao.commons.DAOException;

public interface ComboDAO<T> {
	
	public EntityManager createEntityManager();
	
	public List<T> retrieveCombo() throws DAOException;

}
