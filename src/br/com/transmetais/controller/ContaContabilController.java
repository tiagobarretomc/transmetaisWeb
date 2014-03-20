package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class ContaContabilController {
	
	private ContaContabilDAO dao;
	private final Result result;
	
	public ContaContabilController(Result result, ContaContabilDAO dao) {
		this.result = result;
		this.dao = dao;
		
		
		
	}
	
	@Path({"/contaContabil/","/contaContabil","/contaContabil/lista"})
	public List<ContaContabil> lista() throws DAOException{
		List<ContaContabil> lista = null;
		
		lista = dao.findAll();
		
		return lista;
	}
	
	
	@Path({"/contaContabil/{contaContabil.id}","/contaContabil/form","/contaContabil/novo"})
	public ContaContabil form(ContaContabil contaContabil) throws DAOException{
		
		
		if (contaContabil != null && contaContabil.getId() != null && contaContabil.getId()>0){
			
			contaContabil = dao.findById(contaContabil.getId());
			
		}
		
		
		return contaContabil;
	}
	
	public void add(ContaContabil contaContabil) throws DAOException {
		try {
			if (contaContabil.getId() != null && contaContabil.getId()>0){
				dao.updateEntity(contaContabil);
			}else{
				dao.addEntity(contaContabil);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(ContaContabilController.class).lista();
	  }
	
	
	@Path("/contaContabil/remove/{contaContabil.id}")
	public void remove(ContaContabil contaContabil) throws DAOException {
		
		if (contaContabil.getId() != null && contaContabil.getId()>0){
			dao.removeEntity(contaContabil);
		
		}
		result.redirectTo(ContaContabilController.class).lista();
	  }

}
