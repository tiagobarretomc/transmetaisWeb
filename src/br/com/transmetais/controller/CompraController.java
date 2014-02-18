package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.FornecedorMaterialDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class CompraController {
	
	private final Result result;
	private CompraDAO dao;
	private FornecedorDAO fornecedorDao;
	private FornecedorMaterialDAO fornecedorMaterialDao;
	
	
	public CompraController(Result result, CompraDAO compraDao, FornecedorDAO fornecedorDao, FornecedorMaterialDAO fornecedorMaterialDao) {
		this.dao = compraDao;
		this.fornecedorDao = fornecedorDao;
		this.fornecedorMaterialDao = fornecedorMaterialDao;
		this.result = result;
	}
	
	//tela de listagem de compras
	@Path({"/compra/","/compra","/compra/lista"})
	public List<Compra> lista(){
		List<Compra> lista = null;
		
		try {
			lista = dao.findAll();
			
			result.include("compras",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public void adicionar(Compra compra) {
		try {
			
			if (compra.getId() != null && compra.getId()>0){
				
				dao.updateEntity(compra);
			}else{
				dao.addEntity(compra);
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(ClienteController.class).lista();
	}
	
	@Path({"/compra/{compra.id}","/compra/form","/compra/novo/{compra.fornecedorMaterial.fornecedor.id}"})
	public Compra form(Compra compra) throws DAOException{
		
		if (compra != null && compra.getId() != null && compra.getId()>0){
			try {
				compra = dao.findById(compra.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//quando vier já com um fornecedor selecionado da lista de fornecedores
		if (compra != null && compra.getFornecedorMaterial() != null && compra.getFornecedorMaterial().getFornecedor() != null && 
				compra.getFornecedorMaterial().getFornecedor().getId() != null && compra.getFornecedorMaterial().getFornecedor().getId() > 0){
			
			Fornecedor fornecedor = fornecedorDao.findById(compra.getFornecedorMaterial().getFornecedor().getId());
			result.include("fornecedor", fornecedor);
			
			List<FornecedorMaterial> fornecedorMateriais = fornecedorMaterialDao.obterAtivosPorFornecedor(fornecedor);
			result.include("fornecedorMateriais", fornecedorMateriais);
		}
		
		return compra;
	}

}
