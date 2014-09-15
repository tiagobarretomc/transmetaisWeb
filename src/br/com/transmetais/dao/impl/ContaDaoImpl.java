package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaBancaria;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class ContaDaoImpl extends CrudDAOJPA<Conta> implements ContaDAO{
	
	public List<Conta> obterContasFinanceiras(){
		
		EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(Conta.class);
		
		crit.add(Restrictions.isNull("fornecedor"));
		
		
		
		
		return crit.list();
		
		
		
//		 EntityManager manager = factory.createEntityManager(); 
//			
//			
//		Session session = (Session) manager.getDelegate();
//		
//		
//		
//		DetachedCriteria subquery = DetachedCriteria.forClass(Fornecedor.class,"fo")
//				.createAlias("fo.conta", "conta", Criteria.INNER_JOIN)
//				.setProjection(Property.forName("conta.id"));
//		
//		Criteria crit = session.createCriteria(Conta.class,"conta")
//				.add(Property.forName("conta.id").notIn(subquery));
//				//.add(Subqueries.notIn("co.id", subquery));
//		
//		
//		
//		//crit.addOrder(Order.asc("descricao"));
//		
//		return  crit.list();
		
		
	}
	
	public List<ContaBancaria> obterContasBancarias(){
		
		EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(ContaBancaria.class);
		
		//crit.add(Restrictions.isNull("fornecedor"));
		
		
		
		
		return crit.list();
		
	}
	

}
