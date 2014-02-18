package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class ContaDaoImpl extends CrudDAOJPA<Conta> implements ContaDAO{

}
