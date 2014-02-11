package br.com.transmetais.dao.impl;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.ClienteMaterial;
import br.com.transmetais.dao.ClienteMaterialDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;

@Component
public class ClienteMaterialDaoImpl  extends CrudDAOJPA<ClienteMaterial> implements ClienteMaterialDAO{
private static ClienteMaterialDaoImpl instance = null;
	
	public  static  ClienteMaterialDaoImpl getInstance(){
		if (instance == null)
			instance = new ClienteMaterialDaoImpl(); 
		return instance;
	}
}
