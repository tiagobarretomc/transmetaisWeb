package br.com.transmetais.interceptors;

import java.util.Arrays;
import java.util.Collection;

import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.transmetais.annotations.Permission;
import br.com.transmetais.annotations.Public;
import br.com.transmetais.bean.GrupoUsuario;
import br.com.transmetais.controller.UserSession;
import br.com.transmetais.dao.commons.PermissionDeniedException;

@Intercepts
public class PermissionInterceptor implements Interceptor {

	private final Result result;
	private final UserSession userSession;
	
	public PermissionInterceptor(Result result, UserSession userSession) {
		this.result = result;
		this.userSession = userSession;
	}


	public boolean accepts(ResourceMethod method) {
		return !(method.getMethod().isAnnotationPresent(Public.class) || method.getResource().getType().isAnnotationPresent(Public.class));
	}

	public void intercept(InterceptorStack stack, ResourceMethod method, Object resource) {
		Permission methodPermission = method.getMethod().getAnnotation(Permission.class);
		Permission controllerPermission = method.getResource().getType().getAnnotation(Permission.class);
		
		if (this.hasAccess(methodPermission) && this.hasAccess(controllerPermission)) {
			stack.next(method, resource);
		} else {
			//TODO Ver como tratar exceção
		}
		
	}

	private boolean hasAccess(Permission permission) {
		//TODO Criar metodo de validacao
		if (permission == null) {
			return true;
		}

		Collection<GrupoUsuario> perfilList = Arrays.asList(permission.value());
		
		return false;
	}

}