package br.com.transmetais.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Cidade;
import br.com.transmetais.bean.Cliente;
import br.com.transmetais.bean.Estado;
import br.com.transmetais.dao.CidadeDAO;
import br.com.transmetais.dao.ClienteDAO;
import br.com.transmetais.dao.EstadoDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class ClienteController {
	
	private final Result result;
	
	private ClienteDAO dao;
	private CidadeDAO cidadeDao;
	private EstadoDAO estadoDao;
	
	
	public ClienteController(Result result, ClienteDAO dao, CidadeDAO cidadeDao, EstadoDAO estadoDao) {
		this.result = result;
		this.dao = dao;
		this.cidadeDao = cidadeDao;
		this.estadoDao = estadoDao;
		
	}
	
	public List<Cliente> loadListaCliente(Long clienteId){
		List<Cliente> lista = new ArrayList<Cliente>();
		
		try {
			
			lista.add(dao.findById(clienteId));
			
			result.include("clientes",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	@Path({"/cliente/","/cliente","/cliente/lista"})
	public List<Cliente> lista(){
		List<Cliente> lista = null;
		
		try {
			lista = dao.findAll();
			
			result.include("clientes",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public void adicionar(Cliente cliente) {
		try {
			Cidade cidade = cidadeDao.findById(cliente.getCidade().getId());
			cliente.setCidade(cidade);
			if (cliente.getId() != null && cliente.getId()>0){
				
				dao.updateEntity(cliente);
			}else{
				dao.addEntity(cliente);
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(ClienteController.class).lista();
	}
	
	@Path({"/cliente/{cliente.id}","/cliente/form","/cliente/novo"})
	public Cliente form(Cliente cliente){
		
		
		if (cliente != null && cliente.getId() != null && cliente.getId()>0){
			try {
				cliente = dao.findById(cliente.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Estado> estados = null;
		try {
			
			estados = estadoDao.findAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.include("estados", estados);
		return cliente;
	}
	
	

}
