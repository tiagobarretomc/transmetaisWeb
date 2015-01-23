package br.com.transmetais.controller;

import java.io.Serializable;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.transmetais.bean.Usuario;

@Component
@SessionScoped
public class UserSession implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7335609776581390861L;
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