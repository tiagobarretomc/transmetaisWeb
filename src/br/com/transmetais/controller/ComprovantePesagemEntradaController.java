
package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.ComprovantePesagemEntrada;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.bean.ItemPesagemEntrada;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.util.EntityUtil;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Resource
@Path("/cpe")
public class ComprovantePesagemEntradaController extends ComprovantePesagemController<ComprovantePesagemEntrada>{

	private FornecedorDAO fornecedorDAO;
	private MaterialDAO materialDAO;
	
	@Override
	protected void prePersistUpdate(ComprovantePesagemEntrada bean) {
		if(bean.getItens() != null){
			 for (ItemPesagemEntrada item : bean.getItens()) {
			 	item.setComprovantePesagem(bean);
				if(item.getMaterial() != null 
						&& item.getMaterial().getId() == 0){
					item.setMaterial(null);
				}
			}
		}
	}
	@Override
	protected void initForm(ComprovantePesagemEntrada bean)  {
		try{
			super.initForm(bean);
			List<Fornecedor> fornecedores = fornecedorDAO.findAll();
			result.include("fornecedores",fornecedores);
	
			
			Gson gson = new Gson();
			String json = gson.toJson(EntityUtil.retrieveCombo(materialDAO.findAll(), "id", "descricao"));
			this.result.include("materialList", json);
			Gson gsonItens = new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
				
				public boolean shouldSkipField(FieldAttributes field) {
					return field.getName().equals("comprovantePesagem");
				}
				
				public boolean shouldSkipClass(Class<?> clazz) {
					return clazz.getSimpleName().equals(GrupoMaterial.class.getSimpleName());
				}
			}).create();
			json = gsonItens.toJson(bean.getItens());
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
	protected ComprovantePesagemEntrada createInstance() {
		return new ComprovantePesagemEntrada();
	}
	protected void postPersistUpdate(ComprovantePesagemEntrada bean, Result result) {
		// TODO Auto-generated method stub
		
	}

}
