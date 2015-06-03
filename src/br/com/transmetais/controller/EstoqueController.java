package br.com.transmetais.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Estoque;
import br.com.transmetais.bean.EstoqueMovimentacao;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.EstoqueDAO;
import br.com.transmetais.dao.EstoqueMovimentacaoDAO;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.TipoEstoqueMovimentacaoEnum;

@Resource
@Path("/estoque")
public class EstoqueController extends BaseController<Estoque, EstoqueDAO>{
	
	
	private MaterialDAO materialDAO; 
	private EstoqueMovimentacaoDAO estoqueMovimentacaoDAO;
	
	
	@Override
	protected Estoque createInstance() {
		return new Estoque();
	}
	@Override
	protected void postPersistUpdate(Estoque bean, Result result) {
		
		
		
	}
	@Override
	protected void initForm(Estoque bean) {
		
		List<Material> materiais;
		
		 
		try {
			materiais = materialDAO.findAll();
			
			result.include("materiais", materiais);
			
			result.include("listaTipos",TipoEstoqueMovimentacaoEnum.values());
			
			
			
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void prePersistUpdate(Estoque bean) {
		
		
		
	}
	
	
	
	public void adicionar(EstoqueMovimentacao bean) {
		boolean isSucesso = false;
		
		
		//Long id = (Long)EntityUtil.getId(bean.getClass(), bean);
		String msg = "Estoque ajustado com sucesso.";

		try {
			Estoque estoque = dao.findByMaterial(bean.getMaterial());
			
			if(estoque == null){
				estoque = new Estoque();
				estoque.setQuantidade(BigDecimal.ZERO);
				estoque.setValor(BigDecimal.ZERO);
				estoque.setMaterial(bean.getMaterial());
				estoque.setDataAlteracao(bean.getData());
			}
			
			validator.validate(bean);
			validator.onErrorUsePageOf(this.getClass()).form(estoque);
			
			if(bean.getTipoMovimentacao() == TipoEstoqueMovimentacaoEnum.E){
				estoque.setQuantidade(estoque.getQuantidade().add(bean.getQuantidade()));
				estoque.setValor(estoque.getValor().add(bean.getValor()));
			}else{
				estoque.setQuantidade(estoque.getQuantidade().subtract(bean.getQuantidade()));
				estoque.setValor(estoque.getValor().subtract(bean.getValor()));
			}
			
			if(estoque.getId()!= null && estoque.getId() !=0)
				dao.updateEntity(estoque);
			else
				dao.addEntity(estoque);
			
			
			estoqueMovimentacaoDAO.addEntity(bean);
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			isSucesso = false;
			e.printStackTrace();
		}
		
		if(isSucesso){
			//postPersistUpdate(bean, result);
			result.include("mensagem", msg);
			result.forwardTo(this.getClass()).lista();
		}else{
			result.include("erro","Erro ao incluir registro.");
			result.forwardTo(this.getClass()).form(new Estoque());;
		}
	}
	
	
	@Autowired 
	public void setMaterialDAO(MaterialDAO materialDAO) {
		this.materialDAO = materialDAO;
	}
	
	@Autowired
	public void setEstoqueMovimentacaoDAO(
			EstoqueMovimentacaoDAO estoqueMovimentacaoDAO) {
		this.estoqueMovimentacaoDAO = estoqueMovimentacaoDAO;
	}
	

}
