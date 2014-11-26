
package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Cliente;
import br.com.transmetais.bean.ComprovantePesagem;
import br.com.transmetais.bean.ComprovantePesagemEntrada;
import br.com.transmetais.bean.ComprovantePesagemSaida;
import br.com.transmetais.dao.ClienteDAO;
import br.com.transmetais.dao.ComprovantePesagemDAO;
import br.com.transmetais.dao.ProdutoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.util.EntityUtil;

import com.google.gson.Gson;

@Resource
@Path("/cps")
public class ComprovantePesagemSaidaController extends BaseController<ComprovantePesagem, ComprovantePesagemDAO>{

	private ClienteDAO clienteDAO;
	private ProdutoDAO produtoDAO;
	
	@Override
	protected void prePersistUpdate(ComprovantePesagem bean) {
	}
	@Override
	protected void initForm(ComprovantePesagem bean)  {
		try{
	
			List<Cliente> clientes = clienteDAO.findAll();
			result.include("clientes",clientes);
			
			
			Gson gson = new Gson();
			String json = gson.toJson(EntityUtil.retrieveCombo(produtoDAO.findAll(), "id", "descricao"));
			result.include("produtoList",json);
			
			if(bean instanceof ComprovantePesagemEntrada){
				json = gson.toJson(((ComprovantePesagemEntrada)bean).getItens());
			}else if(bean instanceof ComprovantePesagemSaida){
				json = gson.toJson(((ComprovantePesagemSaida)bean).getItens());
			}
			result.include("itensPesagem",json);
			
		}catch(DAOException e){
			e.printStackTrace();
		}
	}
	@Autowired 
	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}
	@Autowired 
	public void setProdutoDAO(ProdutoDAO produtoDAO) {
		this.produtoDAO = produtoDAO;
	}
	@Override
	protected ComprovantePesagem createInstance() {
		return new ComprovantePesagemSaida();
	}
	@Override
	protected void postPersistUpdate(ComprovantePesagem bean, Result result) {
		// TODO Auto-generated method stub
		
	}

}
