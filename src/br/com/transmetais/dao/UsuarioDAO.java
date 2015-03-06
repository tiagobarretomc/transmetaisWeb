package br.com.transmetais.dao;

import br.com.transmetais.bean.GrupoUsuario;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;

public interface UsuarioDAO extends CrudDAO<Usuario> {
	public Usuario login(String email, String senha) throws DAOException;
	public Usuario getUsuarioByEmail(String email) throws DAOException;
	public boolean verificarAcesso(Usuario usuario, GrupoUsuario grupo) throws DAOException;

}
