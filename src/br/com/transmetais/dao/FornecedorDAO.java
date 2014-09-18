package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface FornecedorDAO extends CrudDAO<Fornecedor>{
	
	public List<Fornecedor> obterComRotativo() throws DAOException;
	
	public List<Fornecedor> obterTodosSemConta() throws DAOException;

}
