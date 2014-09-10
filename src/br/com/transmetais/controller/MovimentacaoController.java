package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class MovimentacaoController {
	
private final Result result;
	
	private MovimentacaoDAO dao;
	private FornecedorDAO fornecedorDAO;
	
	

	public MovimentacaoController(Result result, MovimentacaoDAO dao, FornecedorDAO fornecedorDAO) {
		this.result = result;
		this.dao = dao;
		this.fornecedorDAO = fornecedorDAO;
		
		
	}

	@Path({"/movimentacao/{fornecedor.id}"})
	public List<Movimentacao> lista(Fornecedor fornecedor) throws DAOException{
		List<Movimentacao> lista = null;
		
		fornecedor = fornecedorDAO.findById(fornecedor.getId());
		
		lista = dao.findByFilter(null, null, fornecedor.getConta().getId());
			
		result.include("fornecedor",fornecedor);
		
		return lista;
	}
	
//	public void loadJsonMateriais() throws Exception{
//		
//		List<Material> lista = null;
//		
//		
//		lista = dao.findAll();
//			
//			
//		result.include("materiais",lista);
//		
//		
//		//result.include("cidades",estadoSelecionado.getCidades());
//		result.use(json()).from(lista).serialize();
//		//result.forwardTo("/fornecedor/loadCidades.json.jsp");
//		result.nothing();
//		
//	}
	
//	@Path({"/material/{material.id}","/material/form","/material/novo"})
//	public Material form(Material material) throws DAOException{
//		
//		
//		if (material != null && material.getId() != null && material.getId()>0){
//			
//			material = dao.findById(material.getId());
//			
//		}
//		
//		List<UnidadeMedida> unidadesMedidas = unidadeMedidaDao.findAll();
//		result.include("unidadesMedidas",unidadesMedidas);
//		
//		List<GrupoMaterial> grupos = grupoMaterialDao.findAll();
//		result.include("grupos",grupos);
//		
//		result.include("contas", contaContabilDao.findAll());
//		
//		return material;
//	}
//	
//	public void adiciona(Material material) {
//		try {
//			if(material.getContaContabil() != null && material.getContaContabil().getId() == null)
//				material.setContaContabil(null);
//			if (material.getId() != null && material.getId()>0){
//				dao.updateEntity(material);
//			}else{
//				dao.addEntity(material);
//			}
//			
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		result.redirectTo(MovimentacaoController.class).lista();
//	  }
}
