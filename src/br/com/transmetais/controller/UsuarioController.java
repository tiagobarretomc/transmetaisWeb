package br.com.transmetais.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.dao.UsuarioDAO;
import br.com.transmetais.util.SecurityUtil;

@Resource
@Path("/usuario")
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
		bean.setSenhaInicial(SecurityUtil.createDefaultPassword());
	}

	@Override
	protected void postPersistUpdate(Usuario bean, Result result) {
		
	}

}
