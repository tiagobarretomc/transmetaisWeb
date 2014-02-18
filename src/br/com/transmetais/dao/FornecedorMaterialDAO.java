package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.dao.commons.CrudDAO;

public interface FornecedorMaterialDAO  extends CrudDAO<FornecedorMaterial>{
	
	public List<FornecedorMaterial> obterAtivosPorFornecedor(Fornecedor fornecedor);

}
