package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.bean.Parcela;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface ContaAPagarDAO extends CrudDAO<ContaAPagar>{
	
	public List<ContaAPagar> findByFilter(Date dataInicio, Date dataFim) throws DAOException;
	
	public ContaAPagarDespesa obterPorDespesa(Despesa despesa, Parcela parcela) throws DAOException ;
	
	public ContaAPagarCompra obterPorCompra(Compra compra, Parcela parcela) throws DAOException ;

}
