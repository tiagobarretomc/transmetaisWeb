package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.ContaAReceber;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface ContaAReceberDAO extends CrudDAO<ContaAReceber>{
	
	public List<ContaAReceber> findByFilter(Date dataInicio, Date dataFim) throws DAOException;

}
