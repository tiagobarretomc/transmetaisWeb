package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.ComprovantePesagemEntrada;
import br.com.transmetais.dao.ComprovantePesagemDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.QueryGenerator;

@Component
public class ComprovantePesagemEntradaDaoImpl extends CrudDAOJPA<ComprovantePesagemEntrada> implements ComprovantePesagemDAO<ComprovantePesagemEntrada>{
	
	@Override
	public List<ComprovantePesagemEntrada> findByFilter(ComprovantePesagemEntrada filter) {
		QueryGenerator qGen = new QueryGenerator();
		qGen.addSpecificCondition("dataInicio",QueryGenerator.GREATER_EQUAL_OPERATOR, "dataEmissao");
		qGen.addSpecificCondition("dataFim",QueryGenerator.LOWER_EQUAL_OPERATOR, "dataEmissao");
		EntityManager manager = factory.createEntityManager();
		Query query = qGen.createQuery(filter, manager);
		return query.getResultList();
	}
	
}
