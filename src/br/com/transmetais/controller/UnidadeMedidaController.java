package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.UnidadeMedida;
import br.com.transmetais.dao.UnidadeMedidaDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class UnidadeMedidaController {
	
	private UnidadeMedidaDAO dao;
	private final Result result;
	
	public UnidadeMedidaController(Result result, UnidadeMedidaDAO dao) {
		this.result = result;
		this.dao = dao;
		
		
	}
	
	@Path({"/unidadeMedida/","/unidadeMedida","/unidadeMedida/lista"})
	public List<UnidadeMedida> lista(){
		List<UnidadeMedida> lista = null;
		
		try {
			lista = dao.findAll();
			
			
			result.include("unidades",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	
	@Path({"/unidadeMedida/{unidade.id}","/unidadeMedida/form","/unidadeMedida/novo"})
	public UnidadeMedida form(UnidadeMedida unidade) throws DAOException{
		
		
		if (unidade != null && unidade.getId() != null && unidade.getId()>0){
			
			unidade = dao.findById(unidade.getId());
			
		}
		
		
		return unidade;
	}
	
	public void add(UnidadeMedida unidade) {
		try {
			if (unidade.getId() != null && unidade.getId()>0){
				dao.updateEntity(unidade);
			}else{
				dao.addEntity(unidade);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(UnidadeMedidaController.class).lista();
	  }
	
	
	@Path("/unidadeMedida/remove/{unidade.id}")
	public void remove(UnidadeMedida unidade) throws DAOException {
		
		if (unidade.getId() != null && unidade.getId()>0){
			dao.removeEntity(unidade);
		
		}
		result.redirectTo(UnidadeMedidaController.class).lista();
	  }

}
