package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.ComprovantePesagemSaida;
import br.com.transmetais.dao.ComprovantePesagemDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.QueryGenerator;

@Component
public class ComprovantePesagemSaidaDaoImpl extends CrudDAOJPA<ComprovantePesagemSaida> implements ComprovantePesagemDAO<ComprovantePesagemSaida>{
	
	@Override
	public List<ComprovantePesagemSaida> findByFilter(ComprovantePesagemSaida filter) {
		QueryGenerator qGen = new QueryGenerator();
		qGen.addSpecificCondition("dataInicio",QueryGenerator.GREATER_EQUAL_OPERATOR, "dataEmissao");
		qGen.addSpecificCondition("dataFim",QueryGenerator.LOWER_EQUAL_OPERATOR, "dataEmissao");
		EntityManager manager = factory.createEntityManager();
		Query query = qGen.createQuery(filter, manager);
		return query.getResultList();
	}
	
}
