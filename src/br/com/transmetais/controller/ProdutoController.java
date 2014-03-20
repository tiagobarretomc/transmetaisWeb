package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import br.com.transmetais.bean.BaseDeCalculo;
import br.com.transmetais.bean.BaseDeCalculoST;
import br.com.transmetais.bean.Cfop;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.bean.OrigemMercadoria;
import br.com.transmetais.bean.Produto;
import br.com.transmetais.bean.SituacaoTributaria;
import br.com.transmetais.bean.UnidadeMedida;
import br.com.transmetais.dao.GrupoMaterialDAO;
import br.com.transmetais.dao.ProdutoDAO;
import br.com.transmetais.dao.UnidadeMedidaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.BaseDeCalculoDaoImpl;
import br.com.transmetais.dao.impl.BaseDeCalculoSTDaoImpl;
import br.com.transmetais.dao.impl.CfopDaoImpl;
import br.com.transmetais.dao.impl.OrigemMercadoriaDaoImpl;
import br.com.transmetais.dao.impl.SituacaoTributariaDaoImpl;
import br.com.transmetais.dao.impl.TipoOperacaoDaoImpl;

import com.google.gson.Gson;

@Resource
public class ProdutoController {
	
	private ProdutoDAO dao;
	private final Result result;
	private UnidadeMedidaDAO unidadeMedidaDao;
	private GrupoMaterialDAO grupoMaterialDao;
	private TipoOperacaoDaoImpl tipoOperacaoDao;
	private OrigemMercadoriaDaoImpl origemMercadoriaDao;
	private BaseDeCalculoDaoImpl baseDeCalculoDao;
	private BaseDeCalculoSTDaoImpl baseDeCalculoSTDao;
	private SituacaoTributariaDaoImpl situacaoTributariaDao;
	private CfopDaoImpl cfopDao;
	
	public ProdutoController(Result result, ProdutoDAO dao,  UnidadeMedidaDAO unidadeMedidaDao, GrupoMaterialDAO grupoMaterialDao, 
			TipoOperacaoDaoImpl tipoOperacaoDao, OrigemMercadoriaDaoImpl origemMercadoriaDao, BaseDeCalculoDaoImpl baseDeCalculoDao,
			BaseDeCalculoSTDaoImpl baseDeCalculoSTDao, SituacaoTributariaDaoImpl situacaoTributariaDao, CfopDaoImpl cfopDao) {
		this.result = result;
		this.unidadeMedidaDao = unidadeMedidaDao;
		this.dao = dao;
		this.grupoMaterialDao = grupoMaterialDao;
		this.tipoOperacaoDao = tipoOperacaoDao;
		this.origemMercadoriaDao = origemMercadoriaDao;
		this.baseDeCalculoDao = baseDeCalculoDao;
		this.baseDeCalculoSTDao = baseDeCalculoSTDao;
		this.situacaoTributariaDao = situacaoTributariaDao;
		this.cfopDao = cfopDao;
		
		
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
		
		
		Gson gson = new Gson();
		String json = gson.toJson(tipoOperacaoDao.retrieveCombo());
		this.result.include("tipoOperacaoList", json);
		
		json = gson.toJson(origemMercadoriaDao.retrieveCombo());
		result.include("origemMercadoriaList",json);
		
		json = gson.toJson(situacaoTributariaDao.retrieveCombo());
		result.include("situacaoTributariaList",json);
		
		json = gson.toJson(cfopDao.retrieveCombo());
		result.include("cfopList",json);
		
		json = gson.toJson(baseDeCalculoDao.retrieveCombo());
		result.include("baseDeCalculoList",json);
		
		json = gson.toJson(baseDeCalculoSTDao.retrieveCombo());
		result.include("baseDeCalculoSTList",json);
		
		
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
