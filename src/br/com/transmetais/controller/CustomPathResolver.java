package br.com.transmetais.controller;

import org.springframework.context.annotation.Primary;

import br.com.caelum.vraptor.http.FormatResolver;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.DefaultPathResolver;

@Component
@Primary
public class CustomPathResolver extends DefaultPathResolver {

	public CustomPathResolver(FormatResolver resolver) {
		super(resolver);
	}
	@Override
	protected String extractControllerFromName(String baseName) {
		if(ComprovantePesagemEntradaController.class.getSimpleName().equals(baseName)
			|| ComprovantePesagemSaidaController.class.getSimpleName().equals(baseName))
			return "comprovantePesagem";
		return super.extractControllerFromName(baseName);
	}
	@Override
	public String pathFor(ResourceMethod method) {
		// TODO Auto-generated method stub
		return super.pathFor(method);
	}

	@Override
	protected String getPrefix() {
		// TODO Auto-generated method stub
		return super.getPrefix();
	}
}
