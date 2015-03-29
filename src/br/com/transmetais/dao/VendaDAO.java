package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.Venda;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusVendaEnum;
import br.com.transmetais.type.TipoFreteCliente;

public interface VendaDAO extends CrudDAO<Venda>{
	public List<Venda> findByFilter(Long clienteId, Date dataInicio, Date dataFim, List<TipoFreteCliente> tiposFretes, List<Long> materiaisIds,List<StatusVendaEnum> statusVendaLista) throws DAOException;

}
