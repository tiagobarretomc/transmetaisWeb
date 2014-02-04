package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class FornecedorMaterialDaoImpl  extends CrudDAOJPA<FornecedorMaterial>{
private static FornecedorMaterialDaoImpl instance = null;
	
	public  static  FornecedorMaterialDaoImpl getInstance(){
		if (instance == null)
			instance = new FornecedorMaterialDaoImpl(); 
		return instance;
	}
}
