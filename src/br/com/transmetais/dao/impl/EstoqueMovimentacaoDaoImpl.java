package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Estoque;
import br.com.transmetais.bean.EstoqueMovimentacao;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.EstoqueMovimentacaoDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class EstoqueMovimentacaoDaoImpl extends CrudDAOJPA<EstoqueMovimentacao> implements EstoqueMovimentacaoDAO{
	

	public List<EstoqueMovimentacao> findByMaterial(Material material) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(Estoque.class);
		
		crit.add(Restrictions.eq("material", material));
		
		//crit.setMaxResults(1);
		List<EstoqueMovimentacao> retorno = crit.list();
		
		return retorno;
		
	}
	
	
}
