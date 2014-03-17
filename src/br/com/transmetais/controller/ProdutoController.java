package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.bean.Produto;
import br.com.transmetais.bean.UnidadeMedida;
import br.com.transmetais.dao.GrupoMaterialDAO;
import br.com.transmetais.dao.ProdutoDAO;
import br.com.transmetais.dao.UnidadeMedidaDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class ProdutoController {
	
	private ProdutoDAO dao;
	private final Result result;
	private UnidadeMedidaDAO unidadeMedidaDao;
	private GrupoMaterialDAO grupoMaterialDao;
	
	public ProdutoController(Result result, ProdutoDAO dao,  UnidadeMedidaDAO unidadeMedidaDao, GrupoMaterialDAO grupoMaterialDao) {
		this.result = result;
		this.unidadeMedidaDao = unidadeMedidaDao;
		this.dao = dao;
		this.grupoMaterialDao = grupoMaterialDao;
		
		
	}
	
	@Path({"/produto/","/produto","/produto/lista"})
	public List<Produto> lista(){
		List<Produto> lista = null;
		
		try {
			lista = dao.findAll();
			
			
			result.include("produtos",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return lista;
	}
	
	
	@Path({"/produto/{produto.id}","/produto/form","/produto/novo"})
	public Produto form(Produto produto) throws DAOException{
		
		List<UnidadeMedida> unidadesMedidas = unidadeMedidaDao.findAll();
		result.include("unidadesMedidas",unidadesMedidas);
		
		List<GrupoMaterial> grupos = grupoMaterialDao.findAll();
		result.include("grupos",grupos);
		
		if (produto != null && produto.getId() != null && produto.getId()>0){
			
			produto = dao.findById(produto.getId());
			
		}
		
		
		return produto;
	}
	
	public void add(Produto produto) {
		try {
			if (produto.getId() != null && produto.getId()>0){
				dao.updateEntity(produto);
			}else{
				dao.addEntity(produto);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(ProdutoController.class).lista();
	  }
	
	
	@Path("/produto/remove/{produto.id}")
	public void remove(Produto produto) throws DAOException {
		
		if (produto.getId() != null && produto.getId()>0){
			dao.removeEntity(produto);
		
		}
		result.redirectTo(ProdutoController.class).lista();
	  }

}
