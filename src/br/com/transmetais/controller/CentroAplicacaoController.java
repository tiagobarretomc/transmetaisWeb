package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.CentroAplicacao;
import br.com.transmetais.bean.CentroCusto;
import br.com.transmetais.dao.CentroAplicacaoDAO;
import br.com.transmetais.dao.CentroCustoDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class CentroAplicacaoController {
	
	private CentroAplicacaoDAO dao;
	private CentroCustoDAO centroCustoDao;
	
	private final Result result;
	
	public CentroAplicacaoController(Result result, CentroAplicacaoDAO dao, CentroCustoDAO centroCustoDao) {
		this.result = result;
		this.dao = dao;
		this.centroCustoDao = centroCustoDao;
		
	}
	
	@Path({"/centroAplicacao/","/centroAplicacao","/centroAplicacao/lista"})
	public List<CentroAplicacao> lista() throws DAOException{
		List<CentroAplicacao> lista = null;
		
		lista = dao.findAll();
		return lista;
	}
	
	
	@Path({"/centroAplicacao/{centroAplicacao.id}","/centroAplicacao/form","/centroAplicacao/novo"})
	public CentroAplicacao form(CentroAplicacao centroAplicacao) throws DAOException{
		
		
		if (centroAplicacao != null && centroAplicacao.getId() != null && centroAplicacao.getId()>0){
			
			centroAplicacao = dao.findById(centroAplicacao.getId());
			
		}
		
		List<CentroCusto> centrosCustos = centroCustoDao.findAll();
		
		result.include("centrosCustos", centrosCustos);
		
		
		return centroAplicacao;
	}
	
	public void add(CentroAplicacao centroAplicacao) throws DAOException {
		try {
			if (centroAplicacao.getId() != null && centroAplicacao.getId()>0){
				dao.updateEntity(centroAplicacao);
			}else{
				dao.addEntity(centroAplicacao);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(CentroAplicacaoController.class).lista();
	  }
	
	
	@Path("/centroAplicacao/remove/{centroAplicacao.id}")
	public void remove(CentroAplicacao centroAplicacao) throws DAOException {
		
		if (centroAplicacao.getId() != null && centroAplicacao.getId()>0){
			dao.removeEntity(centroAplicacao);
		
		}
		result.redirectTo(CentroAplicacaoController.class).lista();
	  }

}
