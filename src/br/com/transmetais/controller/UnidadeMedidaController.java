package br.com.transmetais.controller;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.UnidadeMedida;
import br.com.transmetais.dao.UnidadeMedidaDAO;

@Resource
@Path("/unidadeMedida")
public class UnidadeMedidaController extends BaseController<UnidadeMedida, UnidadeMedidaDAO>{

	@Override
	protected UnidadeMedida createInstance() {
		return new UnidadeMedida();
	}

	@Override
	protected void initForm(UnidadeMedida bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prePersistUpdate(UnidadeMedida bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void postPersistUpdate(UnidadeMedida bean, Result result) {
		// TODO Auto-generated method stub
		
	}
	


}
