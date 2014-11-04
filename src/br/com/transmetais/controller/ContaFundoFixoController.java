package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.ContaFundoFixo;
import br.com.transmetais.bean.Unidade;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.ContaFundoFixoDAO;
import br.com.transmetais.dao.UnidadeDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
@Path("/contaFundoFixo")
public class ContaFundoFixoController extends BaseController<ContaFundoFixo, ContaFundoFixoDAO>{
	
	
	private UnidadeDAO unidadeDAO; 
	private ContaContabilDAO contaContabilDAO;
	
	
	@Override
	protected ContaFundoFixo createInstance() {
		return new ContaFundoFixo();
	}
	@Override
	protected void postPersistUpdate(ContaFundoFixo bean, Result result) {
		
		
		
	}
	@Override
	protected void initForm(ContaFundoFixo bean) {
		
		List<Unidade> unidades;
		List<ContaContabil> contasContabeis;
		
		try {
			unidades = unidadeDAO.findAll();
			
			result.include("unidades", unidades);
			
			contasContabeis = contaContabilDAO.findAll();
			
			result.include("contasContabeis", contasContabeis);
			
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void prePersistUpdate(ContaFundoFixo bean) {
		
		
		
	}
	
	@Autowired 
	public void setUnidadeDAO(UnidadeDAO unidadeDAO) {
		this.unidadeDAO = unidadeDAO;
	}
	
	@Autowired 
	public void setContaContabilDAO(ContaContabilDAO contaContabilDAO) {
		this.contaContabilDAO = contaContabilDAO;
	}
	
	

}
