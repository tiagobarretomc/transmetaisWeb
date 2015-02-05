package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Venda;
import br.com.transmetais.dao.VendaDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class VendaDaoImpl extends CrudDAOJPA<Venda> implements VendaDAO{
	private static VendaDaoImpl instance = null;
	
	public  static  VendaDaoImpl getInstance(){
		if (instance == null)
			instance = new VendaDaoImpl(); 
		return instance;
	}
	
	
	

}
