package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Adiantamento;
import br.com.transmetais.dao.AdiantamentoDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class AdiantamentoDaoImpl extends CrudDAOJPA<Adiantamento> implements AdiantamentoDAO{
	
 @Override
public List<Adiantamento> findAll() throws DAOException {
	 EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(Adiantamento.class);
		List<Adiantamento> result = null;
		
		crit.addOrder(Order.desc("dataInclusao"));
		
		result =  crit.list();
		
		
		return result;
}
	
	
}
