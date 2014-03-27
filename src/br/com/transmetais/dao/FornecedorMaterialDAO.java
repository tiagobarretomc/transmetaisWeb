package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.type.TipoFreteEnum;

public interface FornecedorMaterialDAO  extends CrudDAO<FornecedorMaterial>{
	
	public List<FornecedorMaterial> obterAtivosPorFiltro(Fornecedor fornecedor, TipoFreteEnum tipoFrete);
	public FornecedorMaterial obterAtivoPorFornecedorAndMaterial(FornecedorMaterial fornecedorMaterial);

}
