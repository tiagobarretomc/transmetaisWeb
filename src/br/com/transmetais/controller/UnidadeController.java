package br.com.transmetais.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Unidade;
import br.com.transmetais.dao.UnidadeDAO;

@Resource
@Path("/unidade")
public class UnidadeController extends BaseController<Unidade, UnidadeDAO>{
	
	@Override
	protected Unidade createInstance() {
		return new Unidade();
	}

	@Override
	protected void initForm(Unidade bean) {
		
	}

	@Override
	protected void prePersistUpdate(Unidade bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void postPersistUpdate(Unidade bean, Result result) {
		// TODO Auto-generated method stub
		
	}

}
