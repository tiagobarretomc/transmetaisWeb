package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.Despesa;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.filtros.DespesaFiltro;

public interface DespesaDAO extends CrudDAO<Despesa>{
	
	public List<Despesa> findByFilter(DespesaFiltro filter) throws DAOException;
}
