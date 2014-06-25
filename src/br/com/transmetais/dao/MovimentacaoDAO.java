package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface MovimentacaoDAO extends CrudDAO<Movimentacao>{
	
	public List<Movimentacao> findByFilter(Date dataInicio, Date dataFim, Long contaId) throws DAOException;

}
