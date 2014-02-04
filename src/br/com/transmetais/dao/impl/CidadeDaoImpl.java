package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Cidade;
import br.com.transmetais.dao.CidadeDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class CidadeDaoImpl extends CrudDAOJPA<Cidade> implements CidadeDAO{

}
