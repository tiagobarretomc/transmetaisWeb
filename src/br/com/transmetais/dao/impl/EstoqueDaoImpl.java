package br.com.transmetais.dao.impl;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Estoque;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.EstoqueDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class EstoqueDaoImpl extends CrudDAOJPA<Estoque> implements EstoqueDAO{
	

	public Estoque findByMaterial(Material material) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(Estoque.class);
		
		crit.add(Restrictions.eq("material", material));
		
		crit.setMaxResults(1);
		Estoque retorno = (Estoque) crit.uniqueResult();
		
		return retorno;
		
	}
	
	
}
