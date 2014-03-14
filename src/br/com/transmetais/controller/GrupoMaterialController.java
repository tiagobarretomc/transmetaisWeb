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
	
	
	@Path({"/grupoMaterial/{grupo.id}","/grupoMaterial/form","/grupoMaterial/novo"})
	public GrupoMaterial form(GrupoMaterial grupo) throws DAOException{
		
		if (grupo != null && grupo.getId() != null && grupo.getId()>0){
			
			grupo = dao.findById(grupo.getId());
			
		}
		
		return grupo;
	}
	
	public void add(GrupoMaterial grupoMaterial) throws DAOException {
		
		if (grupoMaterial.getId() != null && grupoMaterial.getId()>0){
			dao.updateEntity(grupoMaterial);
		}else{
			dao.addEntity(grupoMaterial);
		}
			
		result.redirectTo(GrupoMaterialController.class).lista();
	  }
	
	
	@Path("/grupoMaterial/remove/{grupoMaterial.id}")
	public void remove(GrupoMaterial grupoMaterial) throws DAOException {
		
		if (grupoMaterial.getId() != null && grupoMaterial.getId()>0){
			dao.removeEntity(grupoMaterial);
		
		}
		result.redirectTo(GrupoMaterialController.class).lista();
	  }

}
