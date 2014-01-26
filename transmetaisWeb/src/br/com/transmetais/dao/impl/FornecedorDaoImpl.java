package br.com.transmetais.dao.impl;


import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.dao.commons.CrudDAOJPA;
@Component
@ApplicationScoped
public class FornecedorDaoImpl extends CrudDAOJPA<Fornecedor>{
	
	private static FornecedorDaoImpl instance = null;
	public  static  FornecedorDaoImpl getInstance(){
		if (instance == null)
			instance = new FornecedorDaoImpl(); 
		return instance;
	}

}
