package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.ItemCompra;
import br.com.transmetais.dao.ItemCompraDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class ItemCompraDaoImpl extends CrudDAOJPA<ItemCompra> implements ItemCompraDAO{

}
