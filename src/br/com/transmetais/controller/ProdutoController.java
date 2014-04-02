
package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.bean.Produto;
import br.com.transmetais.bean.RegraTributacao;
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
@Path("/produto")
public class ProdutoController extends BaseController<Produto, ProdutoDAO>{


	private UnidadeMedidaDAO unidadeMedidaDao;
	private GrupoMaterialDAO grupoMaterialDao;
	private TipoOperacaoDaoImpl tipoOperacaoDao;
	private OrigemMercadoriaDaoImpl origemMercadoriaDao;
	private BaseDeCalculoDaoImpl baseDeCalculoDao;
	private BaseDeCalculoSTDaoImpl baseDeCalculoSTDao;
	private SituacaoTributariaDaoImpl situacaoTributariaDao;
	private CfopDaoImpl cfopDao;
	
	@Override
	protected void prePersistUpdate(Produto bean) {
	    for (RegraTributacao regra : bean.getRegrasTributacao()) {
			if(regra.getBaseCalculoST() != null 
					&& regra.getBaseCalculoST().getId() == 0){
				regra.setBaseCalculoST(null);
			}
		}
	}
	@Override
	protected void initForm(Produto bean)  {
		try{
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
	
			json = gson.toJson(bean.getRegrasTributacao());
			result.include("regras",json);
	
		}catch(DAOException e){
			e.printStackTrace();
		}
	}
	
	
	@Autowired 
	public void setUnidadeMedidaDao(UnidadeMedidaDAO unidadeMedidaDao) {
		this.unidadeMedidaDao = unidadeMedidaDao;
	}

	@Autowired 
	public void setGrupoMaterialDao(GrupoMaterialDAO grupoMaterialDao) {
		this.grupoMaterialDao = grupoMaterialDao;
	}

	@Autowired 
	public void setTipoOperacaoDao(TipoOperacaoDaoImpl tipoOperacaoDao) {
		this.tipoOperacaoDao = tipoOperacaoDao;
	}

	@Autowired 
	public void setOrigemMercadoriaDao(OrigemMercadoriaDaoImpl origemMercadoriaDao) {
		this.origemMercadoriaDao = origemMercadoriaDao;
	}

	@Autowired 
	public void setBaseDeCalculoDao(BaseDeCalculoDaoImpl baseDeCalculoDao) {
		this.baseDeCalculoDao = baseDeCalculoDao;
	}

	@Autowired 
	public void setBaseDeCalculoSTDao(BaseDeCalculoSTDaoImpl baseDeCalculoSTDao) {
		this.baseDeCalculoSTDao = baseDeCalculoSTDao;
	}

	@Autowired 
	public void setSituacaoTributariaDao(
			SituacaoTributariaDaoImpl situacaoTributariaDao) {
		this.situacaoTributariaDao = situacaoTributariaDao;
	}

	@Autowired 
	public void setCfopDao(CfopDaoImpl cfopDao) {
		this.cfopDao = cfopDao;
	}

	@Override
	protected Produto createInstance() {
		return new Produto();
	}
	@Override
	protected void postPersistUpdate(Produto bean, Result result) {
		// TODO Auto-generated method stub
		
	}

}
