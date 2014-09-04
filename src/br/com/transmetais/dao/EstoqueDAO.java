package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.Estoque;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface EstoqueDAO extends CrudDAO<Estoque>{
	
	public List<Estoque> findByMaterial(Material material) throws DAOException;
	

}
