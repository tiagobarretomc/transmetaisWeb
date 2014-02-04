package br.com.transmetais.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.InformacaoBancaria;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.InformacaoBancariaDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class InformacaoBancariaController {
	
	private final Result result;
	private InformacaoBancariaDAO dao;
	private FornecedorDAO fornecedorDao;
	


	public InformacaoBancariaController(Result result, InformacaoBancariaDAO dao, FornecedorDAO fornecedorDao) {
		this.result = result;
		this.dao = dao;
		this.fornecedorDao = fornecedorDao;
		
	}
	
	public void adicionar(InformacaoBancaria informacaoBancaria){
		
		Fornecedor fornecedor = null;
		
		
		try {
			fornecedor = fornecedorDao.findById(informacaoBancaria.getFornecedor().getId());
			
			informacaoBancaria.setFornecedor(fornecedor);
						
			dao.addEntity(informacaoBancaria);
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.include("fornecedor", fornecedor);
		//result.use(Results.page()).forward("WEB-INF/jsp/fornecedorMaterial/tabelaPreco.jsp");
		//result.forwardTo("/WEB-INF/jsp/fornecedorMaterial/tabelaPreco.json.jsp");
		
		this.result.use(Results.logic()).redirectTo(FornecedorController.class).form(fornecedor);
		
		
	}

}
