package br.com.transmetais.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.annotations.Permission;
import br.com.transmetais.bean.GrupoUsuario;
import br.com.transmetais.bean.Usuario;
import br.com.transmetais.bean.UsuarioGrupo;
import br.com.transmetais.dao.UsuarioDAO;
import br.com.transmetais.util.SecurityUtil;

import com.google.gson.Gson;

@Resource
@Path("/usuario")
@Permission(GrupoUsuario.ADMINISTRADOR)
public class UsuarioController extends BaseController<Usuario, UsuarioDAO>{

	@Override
	protected Usuario createInstance() {
		return new Usuario();
	}
	@Post
	public void adicionar(Usuario bean, List<String> grupos) {
		bean.getGruposUsuario().clear();
		UsuarioGrupo usuarioGrupo = null;
		for (String grupoUsuario : grupos) {
			usuarioGrupo = new UsuarioGrupo();
			usuarioGrupo.setUsuario(bean);
			usuarioGrupo.setGrupoUsuario(GrupoUsuario.valueOf(grupoUsuario));
			bean.getGruposUsuario().add(usuarioGrupo);
		}
		super.add(bean);
	}

	@Override
	protected void initForm(Usuario bean) {
		Gson gson = new Gson();
		List<String> gruposDoUsuario = new ArrayList<String>();
		for (UsuarioGrupo usuarioGrupo : bean.getGruposUsuario()) {
			gruposDoUsuario.add(usuarioGrupo.getGrupoUsuario().name());
		}
		String gruposDoUsuarioJson = gson.toJson(gruposDoUsuario);
		result.include("gruposDoUsuarioJson",gruposDoUsuarioJson);
		result.include("gruposDoUsuario",gruposDoUsuario);
		List<GrupoUsuario> gruposUsuarios = new ArrayList<GrupoUsuario>();
		for (GrupoUsuario grupoUsuario : Arrays.asList(GrupoUsuario.values())) {
			if(!gruposDoUsuario.contains(grupoUsuario.name())){
				gruposUsuarios.add(grupoUsuario);
			}
		}
		result.include("gruposUsuarios",gruposUsuarios);
	}

	@Override
	protected void prePersistUpdate(Usuario bean) {
		if(bean.getId() == null){
			String senhaInicial = SecurityUtil.createDefaultPassword();
			bean.setSenhaInicial(senhaInicial);
			bean.setSenha(SecurityUtil.encriptyWord(senhaInicial));
		}
	}

	@Override
	protected void postPersistUpdate(Usuario bean, Result result) {
		
	}

}
