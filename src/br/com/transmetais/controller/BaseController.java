package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
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
		if (id != null && id>0){
			dao.removeEntity(bean);
		
		}
		result.redirectTo(this.getClass()).lista();
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
	
	public void add(E bean) {
	 	validateForm(bean);
			prePersistUpdate(bean);
			Long id = (Long)EntityUtil.getId(bean);
			if (id != null && id > 0){
				update(bean);
			}else{
				persist(bean);
			}
			postPersistUpdate(bean, result);
			result.redirectTo(this.getClass()).lista();
		
	  }
	
	
	public void persist(E bean){
		try {
			dao.addEntity(bean);
		} catch (DAOException e) {
			result.include("erro", e.getMessage());
			e.printStackTrace();		
		}
	}
	public void update(E bean){
		try {
			dao.updateEntity(bean);
		} catch (Exception e) {
			result.include("erro", e.getMessage());
			e.printStackTrace();		
		}
	}
	@Path({"/lista"})
	public List<E> lista(){
		List<E> lista = null;
		
		try {
			lista = dao.findAll();
			
			
			result.include("beanList",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	protected void validateForm(E bean){
		validator.validate(bean);
		validator.onErrorRedirectTo(this.getClass()).form(bean);
	}
	
	protected abstract E createInstance();
	protected abstract void initForm(E bean);
	protected abstract void prePersistUpdate(E bean);
	protected abstract void postPersistUpdate(E bean, Result result);
}
