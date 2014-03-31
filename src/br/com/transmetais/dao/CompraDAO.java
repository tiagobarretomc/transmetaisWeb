package br.com.transmetais.dao;

import java.util.Date;
import java.util.List;

import br.com.transmetais.bean.Compra;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.TipoFreteEnum;

public interface CompraDAO extends CrudDAO<Compra>{
	public List<Compra> findByFilter(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisIds, List<StatusCompraEnum> statusCompraLista) throws DAOException;

}
