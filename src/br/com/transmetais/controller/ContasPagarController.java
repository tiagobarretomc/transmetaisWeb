package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class ContasPagarController {
	
	private final Result result;
	private CompraDAO compraDao;
	private FornecedorDAO fornecedorDao;
	private ContaDAO contaDao;
	
	private MovimentacaoDAO dao;
	
	
	public ContasPagarController(Result result,CompraDAO compraDao, MovimentacaoDAO dao, FornecedorDAO fornecedorDao, ContaDAO contaDao ) {
		this.dao = dao;
		this.compraDao = compraDao;
		this.result = result;
		this.fornecedorDao = fornecedorDao;
		this.contaDao = contaDao;
	}
	
	//tela de listagem de compras
	@Path({"/contasPagar/","/contasPagar","/contasPagar/lista"})
	public List<Movimentacao> lista(Date dataInicio, Date dataFim){
		List<Movimentacao> lista = null;
		
		try {
			//List<Fornecedor> fornecedores = fornecedorDao.findAll();
			//List<Material> materiais = materialDao.findAll();
			
			lista = dao.findByFilter(dataInicio, dataFim);
//			BigDecimal valorTotal = new BigDecimal(0);
//			BigDecimal quantidade = new BigDecimal(0);
//			for (Compra compra : lista) {
//				for(ItemCompra item : compra.getItens()){
//					valorTotal = valorTotal.add( item.getValor());
//					quantidade = quantidade.add( item.getQuantidade());
//					
//				}
//			}
			
//			BigDecimal precoMedio =  new BigDecimal(0);
//			
//			if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
//				
//				precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
//			}
			
			
//			result.include("fornecedores", fornecedores);
//			result.include("materiais", materiais);
//			result.include("tiposFrete",TipoFreteEnum.values());
//			result.include("statusList",StatusCompraEnum.values());
//			
//			result.include("valorTotal", valorTotal);
//			result.include("quantidade", quantidade);
//			result.include("precoMedio", precoMedio);
//			
//			result.include("compras",lista);
//			
			//result.forwardTo("/jsp/compra/lista.jsp");
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
		public List<Movimentacao> loadListaMovimentacao(Date dataInicio, Date dataFim){
			List<Movimentacao> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(dataInicio, dataFim);
//				BigDecimal valorTotal = new BigDecimal(0);
//				BigDecimal quantidade = new BigDecimal(0);
//				for (Compra compra : lista) {
//					for(ItemCompra item : compra.getItens()){
//						valorTotal = valorTotal.add( item.getValor());
//						quantidade = quantidade.add( item.getQuantidade());
//						
//					}
//				}
//				
//				
//				BigDecimal precoMedio =  new BigDecimal(0);
//				
//				if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
//					
//					precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
//				}
//				
//				result.include("valorTotal", valorTotal);
//				result.include("quantidade", quantidade);
//				result.include("precoMedio", precoMedio);
//				
//				result.include("compras",lista);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public void salvar(Movimentacao movimentacao) {
		
		
		result.redirectTo(ContasPagarController.class).lista(null, null);
	}
	
	@Path({"/contasPagar/{movimentacao.id}"})
	public Movimentacao form(Movimentacao movimentacao) throws DAOException{
		
		
		movimentacao = dao.findById(movimentacao.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());
		
		result.include("contas",contaDao.obterContasFinanceiras());
		
		return movimentacao;
	}
	
	
	

}
