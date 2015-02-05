package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class ContaContabilDaoImpl extends CrudDAOJPA<ContaContabil> implements ContaContabilDAO{
	 @Override
	 public List<ContaContabil> findAll() throws DAOException {
	 	 EntityManager manager = factory.createEntityManager(); 
	 		
	 		
	 		Session session = (Session) manager.getDelegate();
	 		Criteria crit = session.createCriteria(ContaContabil.class);
	 		List<ContaContabil> result = null;
	 		
	 		crit.addOrder(Order.asc("numero"));
	 		
	 		result =  crit.list();
	 		
	 		
	 		return result;
	 }
	
	
}
