package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.EstoqueMovimentacao;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;


public interface EstoqueMovimentacaoDAO extends CrudDAO<EstoqueMovimentacao>{
	
	public List<EstoqueMovimentacao> findByMaterial(Material material) throws DAOException;
	

}
