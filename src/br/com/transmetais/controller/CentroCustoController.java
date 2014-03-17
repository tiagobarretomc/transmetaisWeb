package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.CentroCusto;
import br.com.transmetais.dao.CentroCustoDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class CentroCustoController {
	
	private CentroCustoDAO dao;
	private final Result result;
	
	public CentroCustoController(Result result, CentroCustoDAO dao) {
		this.result = result;
		this.dao = dao;
		
		
	}
	
	@Path({"/centroCusto/","/centroCusto","/centroCusto/lista"})
	public List<CentroCusto> lista() throws DAOException{
		List<CentroCusto> lista = null;
		
		lista = dao.findAll();
		return lista;
	}
	
	
	@Path({"/centroCusto/{centroCusto.id}","/centroCusto/form","/centroCusto/novo"})
	public CentroCusto form(CentroCusto centroCusto) throws DAOException{
		
		
		if (centroCusto != null && centroCusto.getId() != null && centroCusto.getId()>0){
			
			centroCusto = dao.findById(centroCusto.getId());
			
		}
		
		
		return centroCusto;
	}
	
	public void add(CentroCusto centroCusto) throws DAOException {
		try {
			if (centroCusto.getId() != null && centroCusto.getId()>0){
				dao.updateEntity(centroCusto);
			}else{
				dao.addEntity(centroCusto);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(CentroCustoController.class).lista();
	  }
	
	
	@Path("/centroCusto/remove/{centroCusto.id}")
	public void remove(CentroCusto centroCusto) throws DAOException {
		
		if (centroCusto.getId() != null && centroCusto.getId()>0){
			dao.removeEntity(centroCusto);
		
		}
		result.redirectTo(CentroCustoController.class).lista();
	  }

}
