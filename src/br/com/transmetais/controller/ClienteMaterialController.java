package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.transmetais.bean.Cliente;
import br.com.transmetais.bean.ClienteMaterial;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.ClienteDAO;
import br.com.transmetais.dao.ClienteMaterialDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.MaterialDaoImpl;
import br.com.transmetais.type.StatusFornecedorMaterialEnum;
import br.com.transmetais.type.TipoFreteCliente;

@Resource
public class ClienteMaterialController {
	
	private final Result result;
	private ClienteDAO clienteDao;
	private ClienteMaterialDAO dao;
	private MaterialDaoImpl materialDao;
	
	public ClienteMaterialController(Result result, ClienteMaterialDAO dao, MaterialDaoImpl materialDao, ClienteDAO clienteDao) {
		this.result = result;
		this.dao = dao;
		this.materialDao = materialDao;
		this.clienteDao = clienteDao;
	}
	
	public void associar(ClienteMaterial clienteMaterial){
		
		Cliente cliente = null;
		Material material = null;
		
		try {
			cliente = clienteDao.findById(clienteMaterial.getCliente().getId());
			material = materialDao.findById(clienteMaterial.getMaterial().getId());
			
			clienteMaterial.setCliente(cliente);
			clienteMaterial.setMaterial(material);
			
			clienteMaterial.setInicioVigencia(null);
			clienteMaterial.setStatus(StatusFornecedorMaterialEnum.BLOQUEADO);
			dao.addEntity(clienteMaterial);
			
			cliente = clienteDao.findById(cliente.getId());
			
		} catch (DAOException e) {
			e.printStackTrace();
		}
		
		result.include("cliente", cliente);
		
		this.result.use(Results.logic()).redirectTo(ClienteMaterialController.class).form(cliente);
		
		
	}
	
	@Path({"/clienteMaterial/inativar/{clienteMaterial.id}"})
	public void inativar(ClienteMaterial clienteMaterial){
		
		try {
			clienteMaterial = dao.findById(clienteMaterial.getId());
			clienteMaterial.setFimVigencia(new Date());
			clienteMaterial.setStatus(StatusFornecedorMaterialEnum.INATIVO);
			
			result.include("cliente", clienteMaterial.getCliente());
			dao.updateEntity(clienteMaterial);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	
		//result.forwardTo("/WEB-INF/jsp/fornecedorMaterial/tabelaPreco.json.jsp");
		this.result.use(Results.logic()).redirectTo(ClienteMaterialController.class).form(clienteMaterial.getCliente());
	}
	
	@Path({"/clienteMaterial/ativar/{clienteMaterial.id}"})
	public void ativar(ClienteMaterial clienteMaterial){
		
		try {
			clienteMaterial = dao.findById(clienteMaterial.getId());
			
			List<ClienteMaterial> listaMateriais = clienteMaterial.getCliente().getClientesMateriais();
			for (ClienteMaterial clienteMat : listaMateriais) {
				if(clienteMat.getMaterial().getId() == clienteMaterial.getMaterial().getId() &&
						clienteMat.getTipoFrete() == clienteMaterial.getTipoFrete() &&
								clienteMat.getStatus() == StatusFornecedorMaterialEnum.ATIVO){
					
					clienteMat.setStatus(StatusFornecedorMaterialEnum.INATIVO);
					clienteMat.setFimVigencia(new Date());
					dao.updateEntity(clienteMat);
				}
			
			}
			
			clienteMaterial.setInicioVigencia(new Date());
			clienteMaterial.setStatus(StatusFornecedorMaterialEnum.ATIVO);
			
			result.include("cliente", clienteMaterial.getCliente());
			dao.updateEntity(clienteMaterial);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	
		//result.forwardTo("/WEB-INF/jsp/fornecedorMaterial/tabelaPreco.json.jsp");
		this.result.use(Results.logic()).redirectTo(ClienteMaterialController.class).form(clienteMaterial.getCliente());
	}
	
	@Path("/clienteMaterial/{cliente.id}")
	public Cliente form(Cliente cliente){
		
		
		if (cliente != null && cliente.getId() != null && cliente.getId()>0){
			try {
				cliente = clienteDao.findById(cliente.getId());
				
				
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}
		List<Material> materiais = null;
		
		System.out.println(cliente.getClientesMateriais());
		
		try {
			materiais = materialDao.findAll();
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.include("materiais", materiais);
		result.include("tiposFrete",TipoFreteCliente.values());
		
		return cliente;
	}
	
	public void obterPreco(ClienteMaterial clienteMaterial) throws DAOException{
		clienteMaterial = dao.obterAtivoPorClienteAndMaterial(clienteMaterial);
		
		//result.include();
		result.use(Results.http()).body(clienteMaterial.getValor().toString());
		result.nothing();
	}
	
}
