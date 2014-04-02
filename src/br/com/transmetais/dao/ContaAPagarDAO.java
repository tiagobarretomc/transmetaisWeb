package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface ContaAPagarDAO extends CrudDAO<ContaAPagar>{
	
	public List<ContaAPagar> findByFilter(Date dataInicio, Date dataFim) throws DAOException;

}
