package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.InformacaoBancaria;
import br.com.transmetais.dao.InformacaoBancariaDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class InformacaoBancariaDaoImpl extends CrudDAOJPA<InformacaoBancaria> implements InformacaoBancariaDAO{

}
