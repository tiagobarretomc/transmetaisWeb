package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
@ApplicationScoped
public class MaterialDaoImpl extends CrudDAOJPA<Material>{
	private static MaterialDaoImpl instance = null;
	
	public  static  MaterialDaoImpl getInstance(){
		if (instance == null)
			instance = new MaterialDaoImpl(); 
		return instance;
	}

}
