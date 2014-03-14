package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.dao.GrupoMaterialDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class GrupoMaterialController {
	
	private GrupoMaterialDAO dao;
	private final Result result;
	
	public GrupoMaterialController(Result result, GrupoMaterialDAO dao) {
		this.result = result;
		this.dao = dao;
		
		
	}
	
	@Path({"/grupoMaterial/","/grupoMaterial","/grupoMaterial/lista"})
	public List<GrupoMaterial> lista() throws DAOException{
		List<GrupoMaterial> lista = null;
		
		lista = dao.findAll();
		
		return lista;
	}
	
	
	@Path({"/grupoMaterial/{unidade.id}","/grupoMaterial/form","/grupoMaterial/novo"})
	public GrupoMaterial form(GrupoMaterial grupo) throws DAOException{
		
		if (grupo != null && grupo.getId() != null && grupo.getId()>0){
			
			grupo = dao.findById(grupo.getId());
			
		}
		
		return grupo;
	}
	
	public void add(GrupoMaterial grupo) throws DAOException {
		
		if (grupo.getId() != null && grupo.getId()>0){
			dao.updateEntity(grupo);
		}else{
			dao.addEntity(grupo);
		}
			
		result.redirectTo(GrupoMaterialController.class).lista();
	  }
	
	
	@Path("/unidadeMedida/remove/{unidade.id}")
	public void remove(GrupoMaterial grupo) throws DAOException {
		
		if (grupo.getId() != null && grupo.getId()>0){
			dao.removeEntity(grupo);
		
		}
		result.redirectTo(GrupoMaterialController.class).lista();
	  }

}
