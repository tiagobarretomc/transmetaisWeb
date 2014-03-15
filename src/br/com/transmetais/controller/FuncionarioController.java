package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Estado;
import br.com.transmetais.bean.Funcionario;
import br.com.transmetais.dao.EstadoDAO;
import br.com.transmetais.dao.FuncionarioDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class FuncionarioController {
	
	private FuncionarioDAO dao;
	private final Result result;
	private EstadoDAO estadoDao;
	
	public FuncionarioController(Result result, FuncionarioDAO dao, EstadoDAO estadoDao) {
		this.result = result;
		this.dao = dao;
		this.estadoDao = estadoDao;
		
		
	}
	
	@Path({"/funcionario/","/funcionario","/funcionario/lista"})
	public List<Funcionario> lista() throws DAOException{
		List<Funcionario> lista = null;
		
		lista = dao.findAll();
		
		return lista;
	}
	
	
	@Path({"/funcionario/{funcionario.id}","/funcionario/form","/funcionario/novo"})
	public Funcionario form(Funcionario funcionario) throws DAOException{
		
		
		if (funcionario != null && funcionario.getId() != null && funcionario.getId()>0){
			
			funcionario = dao.findById(funcionario.getId());
			
		}
		
		List<Estado> estados = estadoDao.findAll();
		
		result.include("estados",estados);
		return funcionario;
	}
	
	public void add(Funcionario funcionario) throws DAOException {
		try {
			if (funcionario.getId() != null && funcionario.getId()>0){
				dao.updateEntity(funcionario);
			}else{
				dao.addEntity(funcionario);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(FuncionarioController.class).lista();
	  }
	
	
	@Path("/funcionario/remove/{funcionario.id}")
	public void remove(Funcionario funcionario) throws DAOException {
		
		if (funcionario.getId() != null && funcionario.getId()>0){
			dao.removeEntity(funcionario);
		
		}
		result.redirectTo(FuncionarioController.class).lista();
	  }

}
