package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class MaterialDaoImpl extends CrudDAOJPA<Material> implements MaterialDAO{
	private static MaterialDaoImpl instance = null;
	
	public  static  MaterialDaoImpl getInstance(){
		if (instance == null)
			instance = new MaterialDaoImpl(); 
		return instance;
	}

}
