package br.com.transmetais.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;
@Component
public class FornecedorDaoImpl extends CrudDAOJPA<Fornecedor> implements FornecedorDAO{
	
	private static FornecedorDaoImpl instance = null;
	public  static  FornecedorDaoImpl getInstance(){
		if (instance == null)
			instance = new FornecedorDaoImpl(); 
		return instance;
	}
	
	@Override
	public List<Fornecedor> findAll() throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		try {
			String query = "from " + Fornecedor.class.getSimpleName() + " as f ORDER BY f.nome ASC";
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
