package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class CompraDaoImpl extends CrudDAOJPA<Compra> implements CompraDAO{
	
}
