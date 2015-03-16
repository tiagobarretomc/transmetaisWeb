package br.com.transmetais.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.annotations.Public;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.dao.UsuarioDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class LoginController {

    private Result result;
    private UserSession userSession;
    private UsuarioController usuarioController;
    private UsuarioDAO usuarioDAO;

    public LoginController(Result result, UserSession userSession, UsuarioController usuarioController, UsuarioDAO usuarioDAO) {
        this.result = result;
        this.userSession = userSession;
        this.usuarioController = usuarioController;
        this.usuarioDAO = usuarioDAO;
    }

    @Public
    @Get("/")
    public void login() {

    }

    @Public
    @Post("/autenticar")
    public void autenticar(String login, String senha) {
        Usuario user = null;
		try {
			user = usuarioDAO.getUsuarioByLoginSenha(login, senha);
		} catch (DAOException e) {
			
		}

        if (user != null) {
            userSession.setUser(user);
            result.redirectTo(IndexController.class).index();
        } else {
        	result.include("error", "Login ou senha incorretos!").redirectTo(this).login();
        }
    }

    @Get("/logout")
    public void logout() {
        userSession.logout();
        result.redirectTo(this).login();
    }

}
