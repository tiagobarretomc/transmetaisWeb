package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class MaterialDaoImpl extends CrudDAOJPA<Material> implements MaterialDAO{
	private static MaterialDaoImpl instance = null;
	
	public  static  MaterialDaoImpl getInstance(){
		if (instance == null)
			instance = new MaterialDaoImpl(); 
		return instance;
	}
	
	
	@Override
	public List<Material> findAll() throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		try {
			String query = "SELECT m from " + Material.class.getSimpleName() + " as m inner join m.grupoMaterial as gm ORDER BY gm.id";
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
