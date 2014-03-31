package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.Conta;
import br.com.transmetais.dao.commons.CrudDAO;

public interface ContaDAO extends CrudDAO<Conta>{
	
	public List<Conta> obterContasFinanceiras();

}
