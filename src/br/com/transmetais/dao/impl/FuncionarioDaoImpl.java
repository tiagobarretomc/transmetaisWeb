package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.bean.Funcionario;
import br.com.transmetais.dao.FuncionarioDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class FuncionarioDaoImpl extends CrudDAOJPA<Funcionario> implements FuncionarioDAO{
	
 @Override
public List<Funcionario> findAll() throws DAOException {
	 EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(Funcionario.class);
		List<Funcionario> result = null;
		
		crit.addOrder(Order.asc("nome"));
		
		result =  crit.list();
		
		
		return result;
}
	
	
}
