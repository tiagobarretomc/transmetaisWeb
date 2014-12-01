package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Arquivo;
import br.com.transmetais.dao.ArquivoDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class ArquivoDaoImpl extends CrudDAOJPA<Arquivo> implements ArquivoDAO{
	private static ArquivoDaoImpl instance = null;
	
	public  static  ArquivoDaoImpl getInstance(){
		if (instance == null)
			instance = new ArquivoDaoImpl(); 
		return instance;
	}

}
