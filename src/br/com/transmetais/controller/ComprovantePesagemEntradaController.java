
package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.ComprovantePesagem;
import br.com.transmetais.bean.ComprovantePesagemEntrada;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.dao.ComprovantePesagemDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.util.EntityUtil;

import com.google.gson.Gson;

@Resource
@Path("/cpe")
public class ComprovantePesagemEntradaController extends BaseController<ComprovantePesagem, ComprovantePesagemDAO>{

	private FornecedorDAO fornecedorDAO;
	private MaterialDAO materialDAO;
	
	@Override
	protected void prePersistUpdate(ComprovantePesagem bean) {
	}
	@Override
	protected void initForm(ComprovantePesagem bean)  {
		try{
			List<Fornecedor> fornecedores = fornecedorDAO.findAll();
			result.include("fornecedores",fornecedores);
	
			
			Gson gson = new Gson();
			String json = gson.toJson(EntityUtil.retrieveCombo(materialDAO.findAll(), "id", "descricao"));
			this.result.include("materialList", json);
			
			json = gson.toJson(((ComprovantePesagemEntrada)bean).getItens());
			result.include("itensPesagem",json);
			
		}catch(DAOException e){
			e.printStackTrace();
		}
	}
	@Autowired 
	public void setFornecedorDAO(FornecedorDAO fornecedorDAO) {
		this.fornecedorDAO = fornecedorDAO;
	}
	@Autowired 
	public void setMaterialDAO(MaterialDAO materialDAO) {
		this.materialDAO = materialDAO;
	}
	@Override
	protected ComprovantePesagem createInstance() {
		return new ComprovantePesagemEntrada();
	}
	protected void postPersistUpdate(ComprovantePesagem bean, Result result) {
		// TODO Auto-generated method stub
		
	}

}
