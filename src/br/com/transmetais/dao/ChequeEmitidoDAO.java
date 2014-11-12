package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.ChequeEmitido;
import br.com.transmetais.bean.ChequeEmitidoDespesa;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface ChequeEmitidoDAO extends CrudDAO<ChequeEmitido>{
	public List<ChequeEmitido> findByFilter(Date dataInicio, Date dataFim, ChequeEmitido cheque) throws DAOException;
	
	public List<ChequeEmitidoDespesa> obterChequeByDespesa(Despesa despesa);
}
