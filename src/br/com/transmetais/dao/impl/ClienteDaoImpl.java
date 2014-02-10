package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Cliente;
import br.com.transmetais.dao.ClienteDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class ClienteDaoImpl extends CrudDAOJPA<Cliente> implements ClienteDAO{

}
