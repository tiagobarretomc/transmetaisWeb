package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.GrupoMaterial;
import br.com.transmetais.bean.Material;
import br.com.transmetais.bean.UnidadeMedida;
import br.com.transmetais.dao.GrupoMaterialDAO;
import br.com.transmetais.dao.UnidadeMedidaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.MaterialDaoImpl;

@Resource
public class MaterialController {
	
private final Result result;
	
	private MaterialDaoImpl dao;
	private UnidadeMedidaDAO unidadeMedidaDao;
	private GrupoMaterialDAO grupoMaterialDao;
	

	public MaterialController(Result result, MaterialDaoImpl dao, UnidadeMedidaDAO unidadeMedidaDao, GrupoMaterialDAO grupoMaterialDao) {
		this.result = result;
		this.dao = dao;
		this.unidadeMedidaDao = unidadeMedidaDao;
		this.grupoMaterialDao = grupoMaterialDao;
		
	}

	@Path({"/material/","/material","/material/lista"})
	public List<Material> lista(){
		List<Material> lista = null;
		
		try {
			lista = dao.findAll();
			
			
			result.include("materiais",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//result.use(json()).from(lista, "listaJson");
		
		return lista;
	}
	
	public void loadJsonMateriais() throws Exception{
		
		List<Material> lista = null;
		
		
		lista = dao.findAll();
			
			
		result.include("materiais",lista);
		
		
		//result.include("cidades",estadoSelecionado.getCidades());
		result.use(json()).from(lista).serialize();
		//result.forwardTo("/fornecedor/loadCidades.json.jsp");
		result.nothing();
		
	}
	
	@Path({"/material/{material.id}","/material/form","/material/novo"})
	public Material form(Material material) throws DAOException{
		
		
		if (material != null && material.getId() != null && material.getId()>0){
			
			material = dao.findById(material.getId());
			
		}
		
		List<UnidadeMedida> unidadesMedidas = unidadeMedidaDao.findAll();
		result.include("unidadesMedidas",unidadesMedidas);
		
		List<GrupoMaterial> grupos = grupoMaterialDao.findAll();
		result.include("grupos",grupos);
		
		return material;
	}
	
	public void adiciona(Material material) {
		try {
			if (material.getId() != null && material.getId()>0){
				dao.updateEntity(material);
			}else{
				dao.addEntity(material);
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(MaterialController.class).lista();
	  }
}
