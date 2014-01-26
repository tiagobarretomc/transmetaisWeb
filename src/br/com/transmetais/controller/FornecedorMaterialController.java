package br.com.transmetais.controller;

import java.math.BigDecimal;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.FornecedorDaoImpl;
import br.com.transmetais.dao.impl.FornecedorMaterialDaoImpl;
import br.com.transmetais.dao.impl.MaterialDaoImpl;

@Resource
public class FornecedorMaterialController {
	
	private final Result result;
	private FornecedorDaoImpl fornecedorDao;
	private FornecedorMaterialDaoImpl dao;
	private MaterialDaoImpl materialDao;
	
	public FornecedorMaterialController(Result result, FornecedorMaterialDaoImpl dao, MaterialDaoImpl materialDao, FornecedorDaoImpl fornecedorDao) {
		this.result = result;
		this.dao = dao;
		this.materialDao = materialDao;
		this.fornecedorDao = fornecedorDao;
	}
	
	public void associar(Long fornecedorId, Long materialId, BigDecimal preco){
		FornecedorMaterial fornecedorMaterial = new FornecedorMaterial();
		Fornecedor fornecedor = null;
		Material material = null;
		
		try {
			fornecedor = fornecedorDao.findById(fornecedorId);
			material = materialDao.findById(materialId);
			
			fornecedorMaterial.setFornecedor(fornecedor);
			fornecedorMaterial.setMaterial(material);
			fornecedorMaterial.setValor(preco);
			
			dao.addEntity(fornecedorMaterial);
			
			fornecedor = fornecedorDao.findById(fornecedorId);
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.include("fornecedor", fornecedor);
		//result.use(Results.page()).forward("WEB-INF/jsp/fornecedorMaterial/tabelaPreco.jsp");
		result.forwardTo("/WEB-INF/jsp/fornecedorMaterial/tabelaPreco.json.jsp");
		
		
	}
	
	@Path({"/fornecedorMaterial/excluir/{fornecedorMaterial.id}"})
	public void excluir(FornecedorMaterial fornecedorMaterial){
		//FornecedorMaterial fornecedorMaterial = new FornecedorMaterial();
		//fornecedorMaterial.setId(fornecedorMaterialId);
		
		try {
			fornecedorMaterial = dao.findById(fornecedorMaterial.getId());
			
			result.include("fornecedor", fornecedorMaterial.getFornecedor());
			dao.removeEntity(fornecedorMaterial);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		result.forwardTo("/WEB-INF/jsp/fornecedorMaterial/tabelaPreco.json.jsp");
	}

}
