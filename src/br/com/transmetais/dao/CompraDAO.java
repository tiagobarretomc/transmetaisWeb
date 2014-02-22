package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.Compra;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface CompraDAO extends CrudDAO<Compra>{
	public List<Compra> findByFilter(Long fornecedorId, Date dataInicio, Date dataFim) throws DAOException;

}
