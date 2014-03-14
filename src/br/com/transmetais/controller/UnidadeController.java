package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Unidade;
import br.com.transmetais.dao.UnidadeDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class UnidadeController {
	
	private UnidadeDAO dao;
	private final Result result;
	
	public UnidadeController(Result result, UnidadeDAO dao) {
		this.result = result;
		this.dao = dao;
		
		
	}
	
	@Path({"/unidade/","/unidade","/unidade/lista"})
	public List<Unidade> lista(){
		List<Unidade> lista = null;
		
		try {
			lista = dao.findAll();
			
			
			result.include("unidades",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	
	@Path({"/unidade/{unidade.id}","/unidade/form","/unidade/novo"})
	public Unidade form(Unidade unidade) throws DAOException{
		
		
		if (unidade != null && unidade.getId() != null && unidade.getId()>0){
			
			unidade = dao.findById(unidade.getId());
			
		}
		
		
		return unidade;
	}
	
	public void add(Unidade unidade) {
		try {
			if (unidade.getId() != null && unidade.getId()>0){
				dao.updateEntity(unidade);
			}else{
				dao.addEntity(unidade);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(UnidadeController.class).lista();
	  }
	
	
	@Path("/unidade/remove/{unidade.id}")
	public void remove(Unidade unidade) throws DAOException {
		
		if (unidade.getId() != null && unidade.getId()>0){
			dao.removeEntity(unidade);
		
		}
		result.redirectTo(UnidadeController.class).lista();
	  }

}
