package br.com.transmetais.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.bean.Material;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.FornecedorMaterialDAO;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.TipoFreteEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class CompraController {
	
	private final Result result;
	private CompraDAO dao;
	private FornecedorDAO fornecedorDao;
	private FornecedorMaterialDAO fornecedorMaterialDao;
	private MovimentacaoDAO movimentacaoDao;
	private ContaDAO contaDao;
	private MaterialDAO materialDao;
	
	public CompraController(Result result, CompraDAO compraDao, FornecedorDAO fornecedorDao, FornecedorMaterialDAO fornecedorMaterialDao, MovimentacaoDAO movimentacaoDao, 
			ContaDAO contaDao, MaterialDAO materialDao) {
		this.dao = compraDao;
		this.fornecedorDao = fornecedorDao;
		this.fornecedorMaterialDao = fornecedorMaterialDao;
		this.movimentacaoDao = movimentacaoDao;
		this.result = result;
		this.contaDao = contaDao;
		this.materialDao = materialDao;
	}
	
	//tela de listagem de compras
	@Path({"/compra/","/compra","/compra/lista"})
	public List<Compra> lista(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisSelecionados){
		List<Compra> lista = null;
		
		try {
			List<Fornecedor> fornecedores = fornecedorDao.findAll();
			List<Material> materiais = materialDao.findAll();
			
			lista = dao.findByFilter(fornecedorId, dataInicio, dataFim, tiposFretes, materiaisSelecionados);
			BigDecimal valorTotal = new BigDecimal(0);
			BigDecimal quantidade = new BigDecimal(0);
			for (Compra compra : lista) {
				valorTotal = valorTotal.add( compra.getValor());
				quantidade = quantidade.add( compra.getQuantidade());
			}
			
			BigDecimal precoMedio =  new BigDecimal(0);
			
			if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
				
				precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
			}
			
			
			result.include("fornecedores", fornecedores);
			result.include("materiais", materiais);
			result.include("tiposFrete",TipoFreteEnum.values());
			
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
	
	
		public List<Compra> loadListaCompra(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisSelecionados){
			List<Compra> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(fornecedorId, dataInicio, dataFim, tiposFretes, materiaisSelecionados);
				BigDecimal valorTotal = new BigDecimal(0);
				BigDecimal quantidade = new BigDecimal(0);
				for (Compra compra : lista) {
					valorTotal = valorTotal.add( compra.getValor());
					quantidade = quantidade.add( compra.getQuantidade());
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
	
	public void adicionar(Compra compra) {
		try {
			
			if (compra.getId() != null && compra.getId()>0){
				//alteracao
				compra.setConta(contaDao.findById(compra.getConta().getId()));
				dao.updateEntity(compra);
				
			}else{
				//insercao - nova compra
				FornecedorMaterial fornecedorMaterial = fornecedorMaterialDao.findById(compra.getFornecedorMaterial().getId());
				compra.setConta(fornecedorMaterial.getFornecedor().getConta());
				dao.addEntity(compra);
				
				Movimentacao movimentacao = new Movimentacao();
				movimentacao.setCompra(compra);
				movimentacao.setConta(compra.getConta());
				movimentacao.setData(new Date());
				movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
				movimentacao.setValor(compra.getValor());
				movimentacaoDao.addEntity(movimentacao);
				
				compra.getConta().setSaldo(compra.getConta().getSaldo().subtract(movimentacao.getValor()));
				contaDao.updateEntity(compra.getConta());
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(CompraController.class).lista(null, null, null, null, null);
	}
	
	@Path({"/compra/{compra.id}","/compra/form","/compra/novo/{compra.fornecedorMaterial.fornecedor.id}"})
	public Compra form(Compra compra) throws DAOException{
		
		if (compra != null && compra.getId() != null && compra.getId()>0){
			try {
				compra = dao.findById(compra.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//quando vier já com um fornecedor selecionado da lista de fornecedores
		if (compra != null && compra.getFornecedorMaterial() != null && compra.getFornecedorMaterial().getFornecedor() != null && 
				compra.getFornecedorMaterial().getFornecedor().getId() != null && compra.getFornecedorMaterial().getFornecedor().getId() > 0){
			
			Fornecedor fornecedor = fornecedorDao.findById(compra.getFornecedorMaterial().getFornecedor().getId());
			result.include("fornecedor", fornecedor);
			
			List<FornecedorMaterial> fornecedorMateriais = fornecedorMaterialDao.obterAtivosPorFornecedor(fornecedor);
			result.include("fornecedorMateriais", fornecedorMateriais);
		}
		
		return compra;
	}

}
