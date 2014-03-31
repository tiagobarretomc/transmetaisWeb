package br.com.transmetais.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.transmetais.dao.ComboDAO;
import br.com.transmetais.dao.commons.DAOException;


public abstract class ComboDaoImpl<T> implements ComboDAO<T>{

	private Class<T> classEntity;
	protected static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");	
	
	@SuppressWarnings("unchecked")
	public ComboDaoImpl() {
		this.classEntity = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	public EntityManager createEntityManager() {
		EntityManager manager = factory.createEntityManager();
		return manager;
	}

	@SuppressWarnings("unchecked")
	public List<T> retrieveCombo() throws DAOException{
		EntityManager manager = factory.createEntityManager(); 
		try {
			String query = "select new br.com.transmetais.bean.Combo(id, CONCAT(codigo,CONCAT(' - ',descricao))) from " + classEntity.getSimpleName();
			return manager.createQuery(query).getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}

}
