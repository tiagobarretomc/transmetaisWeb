package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.ContaCliente;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.dao.ClienteDAO;
import br.com.transmetais.dao.ContaClienteDAO;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
@Path("/contaCliente")
public class ContaClienteController extends BaseController<ContaCliente, ContaClienteDAO>{
	
	
	
	private ContaContabilDAO contaContabilDAO;
	private ClienteDAO clienteDAO;
	
	
	@Override
	protected ContaCliente createInstance() {
		return new ContaCliente();
	}
	@Override
	protected void postPersistUpdate(ContaCliente bean, Result result) {
		
		
		
	}
	@Override
	protected void initForm(ContaCliente bean) {
		
		
		List<ContaContabil> contasContabeis;
		
		try {
			
			
			contasContabeis = contaContabilDAO.findAll();
			
			result.include("contasContabeis", contasContabeis);
			result.include("clientes", clienteDAO.findAll());
			
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void prePersistUpdate(ContaCliente bean) {
		
		//Caso trate-se de uma inclusao, o Saldo será setado igual ao saldo inicial.
		if(bean.getId() == null){
			bean.setSaldo(bean.getSaldoInicial());
		}
		
		
	}
	
	
	
	@Autowired 
	public void setContaContabilDAO(ContaContabilDAO contaContabilDAO) {
		this.contaContabilDAO = contaContabilDAO;
	}
	
	@Autowired
	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

}
