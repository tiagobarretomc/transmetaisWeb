package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.FornecedorMaterialDaoImpl;
import br.com.transmetais.dao.impl.MaterialDaoImpl;
import br.com.transmetais.type.TipoFreteEnum;

@Resource
public class FornecedorMaterialController {
	
	private final Result result;
	private FornecedorDAO fornecedorDao;
	private FornecedorMaterialDaoImpl dao;
	private MaterialDaoImpl materialDao;
	
	public FornecedorMaterialController(Result result, FornecedorMaterialDaoImpl dao, MaterialDaoImpl materialDao, FornecedorDAO fornecedorDao) {
		this.result = result;
		this.dao = dao;
		this.materialDao = materialDao;
		this.fornecedorDao = fornecedorDao;
	}
	
	public void associar(FornecedorMaterial fornecedorMaterial){
		//FornecedorMaterial fornecedorMaterial = new FornecedorMaterial();
		Fornecedor fornecedor = null;
		Material material = null;
		
		try {
			fornecedor = fornecedorDao.findById(fornecedorMaterial.getFornecedor().getId());
			material = materialDao.findById(fornecedorMaterial.getMaterial().getId());
			
			fornecedorMaterial.setFornecedor(fornecedor);
			fornecedorMaterial.setMaterial(material);
			//fornecedorMaterial.setValor(preco);
			fornecedorMaterial.setInicioVigencia(new Date());
			
			dao.addEntity(fornecedorMaterial);
			
			fornecedor = fornecedorDao.findById(fornecedor.getId());
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.include("fornecedor", fornecedor);
		//result.use(Results.page()).forward("WEB-INF/jsp/fornecedorMaterial/tabelaPreco.jsp");
		//result.forwardTo("/WEB-INF/jsp/fornecedorMaterial/tabelaPreco.json.jsp");
		
		this.result.use(Results.logic()).redirectTo(FornecedorController.class).form(fornecedor);
		
		
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
	
		//result.forwardTo("/WEB-INF/jsp/fornecedorMaterial/tabelaPreco.json.jsp");
		this.result.use(Results.logic()).redirectTo(FornecedorController.class).form(fornecedorMaterial.getFornecedor());
	}
	
	@Path("/fornecedorMaterial/{fornecedor.id}")
	public Fornecedor form(Fornecedor fornecedor){
		
		
		if (fornecedor != null && fornecedor.getId() != null && fornecedor.getId()>0){
			try {
				fornecedor = fornecedorDao.findById(fornecedor.getId());
				
				
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		List<Material> materiais = null;
		
		System.out.println(fornecedor.getMateriaisFornecedores());
		
		try {
			materiais = materialDao.findAll();
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.include("materiais", materiais);
		result.include("tiposFrete",TipoFreteEnum.values());
		
		return fornecedor;
	}

}
