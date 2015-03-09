package br.com.transmetais.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.annotations.Permission;
import br.com.transmetais.bean.GrupoUsuario;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.dao.UsuarioDAO;
import br.com.transmetais.util.SecurityUtil;

@Resource
@Path("/usuario")
@Permission(GrupoUsuario.ADMINISTRADOR)
public class UsuarioController extends BaseController<Usuario, UsuarioDAO>{

	@Override
	protected Usuario createInstance() {
		return new Usuario();
	}

	@Override
	protected void initForm(Usuario bean) {
		
	}

	@Override
	protected void prePersistUpdate(Usuario bean) {
		if(bean.getId() == 0){
			String senhaInicial = SecurityUtil.createDefaultPassword();
			bean.setSenhaInicial(senhaInicial);
			bean.setSenha(SecurityUtil.encriptyWord(senhaInicial));
		}
	}

	@Override
	protected void postPersistUpdate(Usuario bean, Result result) {
		
	}

}
