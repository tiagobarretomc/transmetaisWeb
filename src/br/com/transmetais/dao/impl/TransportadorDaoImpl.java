package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Transportador;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class TransportadorDaoImpl extends CrudDAOJPA<Transportador>{
	
	private static TransportadorDaoImpl instance = null;
	public  static  TransportadorDaoImpl getInstance(){
		if (instance == null)
			instance = new TransportadorDaoImpl(); 
		return instance;
	}

}
