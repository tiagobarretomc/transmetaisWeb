package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Estado;
import br.com.transmetais.dao.EstadoDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class EstadoDaoImpl extends CrudDAOJPA<Estado> implements EstadoDAO{
	
	private static EstadoDaoImpl instance = null;
	public  static  EstadoDaoImpl getInstance(){
		if (instance == null)
			instance = new EstadoDaoImpl(); 
		return instance;
	}

}
