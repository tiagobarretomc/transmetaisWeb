package br.com.transmetais.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.annotations.Public;
import br.com.transmetais.bean.Usuario;

@Resource
public class LoginController {

    private Result result;
    private UserSession userSession;
    private UsuarioController usuarioController;

    public LoginController(Result result, UserSession userSession, UsuarioController usuarioController) {
        this.result = result;
        this.userSession = userSession;
        this.usuarioController = usuarioController;
    }

    @Public
    @Get("/login")
    public void login() {

    }

    @Public
    @Post("/autenticar")
    public void autenticar(Usuario usuario) {
    	//TODO Criar metodo autenticar
        Usuario user = null;//usuarioController.autenticar(usuario.getEmail(), usuario.getSenha());

        if (user != null) {
            userSession.setUser(user);

            result.redirectTo(IndexController.class).index();
        } else {
            result.include("error", "E-mail ou senha incorreta!").redirectTo(this).login();
        }
    }

    @Get("/logout")
    public void logout() {
        userSession.logout();
        result.redirectTo(this).login();
    }

}
