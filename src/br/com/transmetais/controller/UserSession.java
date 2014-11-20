package br.com.transmetais.controller;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.transmetais.bean.Usuario;

@Component
@SessionScoped
public class UserSession {

    private Usuario user;

    public boolean isLogged() {
        return getUser() != null;
    }

    public void logout() {
        setUser(null);
    }

	public Usuario getUser() {
		return user;
	}

	public void setUser(Usuario user) {
		this.user = user;
	}


}