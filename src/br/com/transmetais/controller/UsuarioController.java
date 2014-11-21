package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.dao.UsuarioDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.util.SecurityUtil;

@Resource
public class UsuarioController{
	
	private UsuarioDAO dao;
	private final Result result;
	
	public UsuarioController(Result result, UsuarioDAO dao) {
		this.result = result;
		this.dao = dao;
		
		
	}
	
	@Path({"/usuario/","/usuario","/usuario/lista"})
	public List<Usuario> lista(){
		List<Usuario> lista = null;
		
		try {
			lista = dao.findAll();
			
			
			result.include("unidades",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	
	@Path({"/usuario/{usuario.id}","/usuario/form","/usuario/novo"})
	public Usuario form(Usuario usuario) throws DAOException{
		
		
		if (usuario != null && usuario.getId() != null && usuario.getId()>0){
			
			usuario = dao.findById(usuario.getId());
			
		}
		
		
		return usuario;
	}
	
	public void add(Usuario usuario) {
		try {
			
			if (usuario.getId() != null && usuario.getId()>0){
				dao.updateEntity(usuario);
			}else{
				usuario.setSenha(SecurityUtil.encriptyWord(usuario.getSenhaInicial()));
				dao.addEntity(usuario);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(UnidadeController.class).lista();
	  }
	
	
	@Path("/usuario/remove/{usuario.id}")
	public void remove(Usuario usuario) throws DAOException {
		
		if (usuario.getId() != null && usuario.getId()>0){
			dao.removeEntity(usuario);
		
		}
		result.redirectTo(UnidadeController.class).lista();
	  }

}
