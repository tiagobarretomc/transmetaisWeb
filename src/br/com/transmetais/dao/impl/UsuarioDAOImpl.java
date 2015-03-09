package br.com.transmetais.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.GrupoUsuario;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.dao.UsuarioDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.util.SecurityUtil;

@Component
@ApplicationScoped
public class UsuarioDAOImpl extends CrudDAOJPA<Usuario> implements UsuarioDAO{
	
	private static UsuarioDAOImpl instance = null;
	public  static UsuarioDAOImpl getInstance(){
		if (instance == null)
			instance = new UsuarioDAOImpl(); 
		return instance;
	}
	public Usuario login(String email, String senha) throws DAOException {
		List<Usuario> l = findByCriteria(Restrictions.ilike("login", email),Restrictions.ilike("senha", senha));
		if(!l.isEmpty()){
			return l.get(0);
		}
		return null;
	}
	public Usuario getUsuarioByEmail(String email) throws DAOException {
		List<Usuario> l = findByCriteria(Restrictions.ilike("email", email));
		if(!l.isEmpty()){
			return l.get(0);
		}
		return null;
	}
	public Usuario getUsuarioByLoginSenha(String login, String senha) throws DAOException {
		String senhaCriptografada = SecurityUtil.encriptyWord(senha);
		return findSingleByCriteria(Restrictions.eq("login", login), Restrictions.eq("login", login));
	}
	public boolean verificarAcesso(Usuario usuario, GrupoUsuario grupo)
			throws DAOException {
		// TODO Auto-generated method stub
		return false;
	}
	 
	 
}
