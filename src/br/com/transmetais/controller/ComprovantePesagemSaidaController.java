
package br.com.transmetais.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Cliente;
import br.com.transmetais.bean.ComprovantePesagemSaida;
import br.com.transmetais.bean.ItemPesagemSaida;
import br.com.transmetais.dao.ClienteDAO;
import br.com.transmetais.dao.ProdutoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.ComprovantePesagemSaidaDaoImpl;
import br.com.transmetais.util.EntityUtil;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Resource
@Path("/cps")
public class ComprovantePesagemSaidaController extends ComprovantePesagemController<ComprovantePesagemSaida,  ComprovantePesagemSaidaDaoImpl> {

	private ClienteDAO clienteDAO;
	private ProdutoDAO produtoDAO;
	
	@Override
	protected void prePersistUpdate(ComprovantePesagemSaida bean) {
		 for (ItemPesagemSaida item : bean.getItens()) {
			 	item.setComprovantePesagem(bean);
				if(item.getProduto() != null 
						&& item.getProduto().getId() == 0){
					item.setProduto(null);
				}
			}
	}
	
	@Override
	protected void initFilter(ComprovantePesagemSaida filter) {
		// TODO Auto-generated method stub
		super.initFilter(filter);
		List<Cliente> clientes;
		try {
			clientes = clienteDAO.findAll();
			result.include("clientes",clientes);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	protected void initForm(ComprovantePesagemSaida bean)  {
		try{
			super.initForm(bean);
			List<Cliente> clientes = clienteDAO.findAll();
			result.include("clientes",clientes);
			
			
			Gson gson = new Gson();
			String json = gson.toJson(EntityUtil.retrieveCombo(produtoDAO.findAll(), "id", "descricao"));
			result.include("produtoList",json);
			
			gson = new GsonBuilder().setExclusionStrategies(new ExclusionStrategy() {
				
				public boolean shouldSkipField(FieldAttributes arg0) {
					return arg0.getAnnotation(XmlTransient.class) != null;
				}
				
				public boolean shouldSkipClass(Class<?> arg0) {
					return false;
				}
			}).create();
			json = gson.toJson(bean.getItens().toArray());
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
	protected ComprovantePesagemSaida createInstance() {
		return new ComprovantePesagemSaida();
	}
	@Override
	protected void postPersistUpdate(ComprovantePesagemSaida bean, Result result) {
		// TODO Auto-generated method stub
		
	}

}
