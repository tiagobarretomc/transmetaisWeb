package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Cidade;
import br.com.transmetais.bean.Estado;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.CidadeDAO;
import br.com.transmetais.dao.EstadoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.FornecedorDaoImpl;
import br.com.transmetais.dao.impl.MaterialDaoImpl;

@Resource
public class FornecedorController {
	
	private final Result result;
	
	private FornecedorDaoImpl dao;
	
	private MaterialDaoImpl materialDao;
	
	private EstadoDAO estadoDAO;
	private CidadeDAO cidadeDAO;

	public FornecedorController(Result result, FornecedorDaoImpl dao, MaterialDaoImpl materialDao, EstadoDAO estadoDAO, CidadeDAO cidadeDAO) {
		this.result = result;
		this.dao = dao;
		this.materialDao = materialDao;
		this.estadoDAO = estadoDAO;
		this.cidadeDAO = cidadeDAO;
		
	}
	
	
	@Path({"/fornecedor/","/fornecedor","/fornecedor/lista"})
	public List<Fornecedor> lista(){
		List<Fornecedor> lista = null;
		
		try {
			lista = dao.findAll();
			
			result.include("fornecedores",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public void adiciona(Fornecedor fornecedor) {
		try {
			Cidade cidade = cidadeDAO.findById(fornecedor.getCidade().getId());
			fornecedor.setCidade(cidade);
			if (fornecedor.getId() != null && fornecedor.getId()>0){
				
				dao.updateEntity(fornecedor);
			}else{
				dao.addEntity(fornecedor);
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(FornecedorController.class).lista();
	  }
	
	
	@Path({"/fornecedor/{fornecedor.id}","/fornecedor/form","/fornecedor/novo"})
	public Fornecedor form(Fornecedor fornecedor){
		
		
		if (fornecedor != null && fornecedor.getId() != null && fornecedor.getId()>0){
			try {
				fornecedor = dao.findById(fornecedor.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		List<Material> materiais = null;
		List<Estado> estados = null;
		try {
			materiais = materialDao.findAll();
			estados = estadoDAO.findAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		result.include("materiais", materiais);
		result.include("estados", estados);
		return fornecedor;
	}
	
	public void loadCidades(Integer id) throws Exception{
		Estado estadoSelecionado = null;
		
		
		if (id != null && id>0){
			estadoSelecionado = estadoDAO.findById(id);
		}
		
		//result.include("cidades",estadoSelecionado.getCidades());
		result.use(json()).from(estadoSelecionado.getCidades()).serialize();
		//result.forwardTo("/fornecedor/loadCidades.json.jsp");
		result.nothing();
		
	}
	
	public void loadJsonCidade(Estado estado) {
        result.use(json()).from(estado.getCidades()).serialize();
    }

}
