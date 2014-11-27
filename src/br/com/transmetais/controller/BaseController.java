package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.util.EntityUtil;

@Resource
public abstract class BaseController<E, T extends CrudDAO<E>> {
	protected  Result result;
	protected  T dao;
	protected Validator validator;
	
	
	@Autowired
	public void setResult(Result result) {
		this.result = result;
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	@Autowired 
	public void setDao(T dao) {
		this.dao = dao;
	}
	
	@Path("/remove/{id}")
	public void remove(Long id) throws DAOException {
		E bean = dao.findById(id);
		String msg = null;
		if (bean != null){
			try{
				dao.removeEntity(bean);
				msg = "Registro excluído com sucesso." ;
				result.include("mensagem", msg);
			}catch(DAOException e){
				result.include("erro", e.getMessage());

			}
		}
		result.forwardTo(this.getClass()).lista();
	  }

	public void form(E bean){
		initForm(bean);
		result.include("bean",bean);
	}
	
	@Path({"/{id}","/form","/novo"})
	public E form(Long id) throws DAOException{
		E bean = null;
		
		
		if (id != null){
			
			bean = dao.findById(id);
			
		}else{
			bean = createInstance();
		}
		
		initForm(bean);
		
		result.include("bean",bean);
		
		return bean;
	}
	@Post
	public void add(E bean) {
		prePersistUpdate(bean);
	 	validateForm(bean);
		Long id = (Long)EntityUtil.getId(bean);
		String msg = null;
		if (id != null && id > 0){
			if(update(bean)){
				msg = "Registro alterado com sucesso." ;
			}
		}else{
			if(persist(bean)){
				msg = "Registro incluído com sucesso." ;
			}
		}
		postPersistUpdate(bean, result);
		result.include("mensagem", msg);
		result.forwardTo(this.getClass()).lista();
		
	  }
	
	
	public boolean persist(E bean){
		try {
			dao.addEntity(bean);
			return true;
		} catch (DAOException e) {
			result.include("erro","Erro ao incluir registro.");
			e.printStackTrace();
			return false;
		}
	}
	public boolean update(E bean){
		try {
			dao.updateEntity(bean);
			return true;
		} catch (Exception e) {
			result.include("erro", "Erro ao alterar registro.");
			e.printStackTrace();
			return false;
		}
	}
	@Path({"/lista","/lista/"})
	public List<E> lista(){
		List<E> lista = null;
		
		try {
			lista = dao.findAll();
			E filter = createInstance();
			initFilter(filter);
			result.include("filter",filter);
			result.include("beanList",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	@Path({"/filtrar"})
	public List<E> filtrar(E filter){
		List<E> lista = null;
		
		lista = dao.findByFilter(filter);
		initFilter(filter);
		result.include("beanList",lista);
		
		return lista;
		
	}
	protected void validateForm(E bean){
		validator.validate(bean);
		validator.onErrorUsePageOf(this.getClass()).form(bean);
	}
	
	protected  E createInstance(Class<E> type) throws InstantiationException, IllegalAccessException{
		E tipoBase = type.newInstance();
		return tipoBase;
	}
	protected void initFilter(E filter){}
	
	protected abstract E createInstance();
	protected abstract void initForm(E bean);
	protected abstract void prePersistUpdate(E bean);
	protected abstract void postPersistUpdate(E bean, Result result);
}
