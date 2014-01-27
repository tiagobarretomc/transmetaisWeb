package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.FornecedorDaoImpl;
import br.com.transmetais.dao.impl.MaterialDaoImpl;

@Resource
public class FornecedorController {
	
	private final Result result;
	
	private FornecedorDaoImpl dao;
	
	private MaterialDaoImpl materialDao;

	public FornecedorController(Result result, FornecedorDaoImpl dao, MaterialDaoImpl materialDao) {
		this.result = result;
		this.dao = dao;
		this.materialDao = materialDao;
	}
	
	
	
	public List<Fornecedor> lista(){
		List<Fornecedor> lista = null;
		
		try {
			lista = dao.findAll();
			//System.out.println();
			for (Fornecedor fornecedor : lista) {
				System.out.println(fornecedor.getNome());
			}
			
			result.include("fornecedores",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public void adiciona(Fornecedor fornecedor) {
		try {
			if (fornecedor.getId() != null && fornecedor.getId()>0){
				dao.updateEntity(fornecedor);
			}else{
				dao.addEntity(fornecedor);
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(FornecedorController.class).lista();
	  }
	
	
	@Path({"/fornecedor/{fornecedor.id}","/fornecedor/form"})
	public Fornecedor form(Fornecedor fornecedor){
		
		
		if (fornecedor != null && fornecedor.getId() != null && fornecedor.getId()>0){
			try {
				fornecedor = dao.findById(fornecedor.getId());
				
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Material> materiais = null;
		try {
			materiais = materialDao.findAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.include("materiais", materiais);
		return fornecedor;
	}

}
