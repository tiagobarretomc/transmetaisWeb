package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.Estoque;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.bean.ItemCompra;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.EstoqueDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.FornecedorMaterialDAO;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoFreteEnum;

@Resource
public class CompraController {
	
	private final Result result;
	private CompraDAO dao;
	private FornecedorDAO fornecedorDao;
	private FornecedorMaterialDAO fornecedorMaterialDao;
	private ContaAPagarDAO contaAPagarDAO;
	private EstoqueDAO estoqueDAO;
	private MaterialDAO materialDao;
	
	public CompraController(Result result, CompraDAO compraDao, FornecedorDAO fornecedorDao, FornecedorMaterialDAO fornecedorMaterialDao, ContaAPagarDAO contaAPagarDAO, 
			ContaDAO contaDao, MaterialDAO materialDao, EstoqueDAO estoqueDAO) {
		this.dao = compraDao;
		this.fornecedorDao = fornecedorDao;
		this.fornecedorMaterialDao = fornecedorMaterialDao;
		this.contaAPagarDAO = contaAPagarDAO;
		this.result = result;
		this.materialDao = materialDao;
		this.estoqueDAO = estoqueDAO;
	}
	
	//tela de listagem de compras
	@Path({"/compra/","/compra","/compra/lista"})
	public List<Compra> lista(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisSelecionados, List<StatusCompraEnum> statusCompas){
		List<Compra> lista = null;
		
		try {
			List<Fornecedor> fornecedores = fornecedorDao.findAll();
			List<Material> materiais = materialDao.findAll();
			
			lista = dao.findByFilter(fornecedorId, dataInicio, dataFim, tiposFretes, materiaisSelecionados, statusCompas);
			BigDecimal valorTotal = new BigDecimal(0);
			BigDecimal quantidade = new BigDecimal(0);
			for (Compra compra : lista) {
				for(ItemCompra item : compra.getItens()){
					valorTotal = valorTotal.add( item.getValor());
					quantidade = quantidade.add( item.getQuantidade());
					
				}
			}
			
			BigDecimal precoMedio =  new BigDecimal(0);
			
			if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
				
				precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
			}
			
			
			result.include("fornecedores", fornecedores);
			result.include("materiais", materiais);
			result.include("tiposFrete",TipoFreteEnum.values());
			result.include("statusList",StatusCompraEnum.values());
			
			result.include("valorTotal", valorTotal);
			result.include("quantidade", quantidade);
			result.include("precoMedio", precoMedio);
			
			result.include("compras",lista);
			
			//result.forwardTo("/jsp/compra/lista.jsp");
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
		public List<Compra> loadListaCompra(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisSelecionados, List<StatusCompraEnum> statusCompas){
			List<Compra> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(fornecedorId, dataInicio, dataFim, tiposFretes, materiaisSelecionados, statusCompas);
				BigDecimal valorTotal = new BigDecimal(0);
				BigDecimal quantidade = new BigDecimal(0);
				for (Compra compra : lista) {
					for(ItemCompra item : compra.getItens()){
						valorTotal = valorTotal.add( item.getValor());
						quantidade = quantidade.add( item.getQuantidade());
						
					}
				}
				
				
				BigDecimal precoMedio =  new BigDecimal(0);
				
				if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
					
					precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
				}
				
				result.include("valorTotal", valorTotal);
				result.include("quantidade", quantidade);
				result.include("precoMedio", precoMedio);
				
				result.include("compras",lista);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public void salvar(Compra compra) {
		try {
			
			compra.setConta(null);
			for (ItemCompra item : compra.getItens()) {
				item.setCompra(compra);
				
			}
			if (compra.getId() != null && compra.getId()>0){
				//alteracao
				//compra.setConta(contaDao.findById(compra.getConta().getId()));
				dao.updateEntity(compra);
				
			}else{
				//insercao - nova compra
				//FornecedorMaterial fornecedorMaterial = fornecedorMaterialDao.findById(compra.getFornecedorMaterial().getId());
				//compra.setConta(fornecedorMaterial.getFornecedor().getConta());
				compra.setStatus(StatusCompraEnum.A);
				dao.addEntity(compra);
				
				ContaAPagarCompra contaPagar = new ContaAPagarCompra();
				contaPagar.setCompra(compra);
				//contaPagar.setConta(compra.getConta());
				contaPagar.setDataPrevista(new Date());
				
				contaPagar.setValor(compra.getValor());
				contaPagar.setStatus(StatusMovimentacaoEnum.A);
				contaPagar.setDescricao("Compra Fornecedor " + compra.getFornecedor().getApelido() + " - " + compra.getFornecedor().getNome());
				
				contaAPagarDAO.addEntity(contaPagar);
				
				Estoque estoque = new Estoque();
				
				//estoqueDAO.find
				
				//compra.getConta().setSaldo(compra.getConta().getSaldo().subtract(movimentacao.getValor()));
				//contaDao.updateEntity(compra.getConta());
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(CompraController.class).lista(null, null, null, null, null, null);
	}
	
	@Path({"/compra/{compra.id}","/compra/form","/compra/novo/{compra.fornecedor.id}"})
	public Compra form(Compra compra) throws DAOException{
		
		if (compra != null && compra.getId() != null && compra.getId()>0){
			try {
				compra = dao.findById(compra.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			compra.setFornecedor(fornecedorDao.findById(compra.getFornecedor().getId()));
		}
		
		if(compra.getItens() != null)
			result.include("quantidadeItens", compra.getItens().size());
		else
			result.include("quantidadeItens", 0);
		
		
		//quando vier já com um fornecedor selecionado da lista de fornecedores
		if (compra != null && compra.getFornecedor() != null  && 
				compra.getFornecedor().getId() != null && compra.getFornecedor().getId() > 0){
			
			//Fornecedor fornecedor = fornecedorDao.findById(compra.getFornecedorMaterial().getFornecedor().getId());
			result.include("fornecedor", compra.getFornecedor());
			
			List<FornecedorMaterial> fornecedorMateriais = fornecedorMaterialDao.obterAtivosPorFiltro(compra.getFornecedor(),compra.getTipoFrete());
			result.include("fornecedorMateriais", fornecedorMateriais);
			result.include("tiposFrete",TipoFreteEnum.values());
			
			//this.result.use(Results.json()).from(fornecedorMateriais, "formulario").serialize();
		}
		
		
		
		return compra;
	}
	
	
	public void loadJsonMaterial(TipoFreteEnum tipoFrete, Fornecedor fornecedor){
		
		List<FornecedorMaterial> fornecedorMateriais = fornecedorMaterialDao.obterAtivosPorFiltro(fornecedor,tipoFrete);
		
		result.use(json()).from(fornecedorMateriais).include("material").serialize();
		
		result.nothing();
		
	}

}
