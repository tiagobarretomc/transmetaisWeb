package br.com.transmetais.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.bean.MovimentacaoContasAPagar;
import br.com.transmetais.bean.ParcelaCompra;
import br.com.transmetais.bean.ParcelaDespesa;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.ParcelaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.SituacaoChequeEnum;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class ContasPagarController {
	
	private final Result result;
	
	private ContaDAO contaDao;
	private MovimentacaoDAO movimentacaoDao;
	private ContaAPagarDAO dao;
	private CompraDAO compraDao;
	private DespesaDAO despesaDao;
	private ParcelaDAO parcelaDao;
	private ChequeEmitidoDAO chequeEmitidoDAO;
	private FornecedorDAO fornecedorDAO;
	
	
	public ContasPagarController(Result result,ContaAPagarDAO dao, ContaDAO contaDao, 
			MovimentacaoDAO movimentacaoDao, CompraDAO compraDao, 
			DespesaDAO despesaDao, ParcelaDAO parcelaDao,ChequeEmitidoDAO chequeEmitidoDAO, FornecedorDAO fornecedorDAO) {
		this.dao = dao;
		this.result = result;
		this.contaDao = contaDao;
		this.movimentacaoDao = movimentacaoDao;
		this.compraDao = compraDao;
		
		this.despesaDao = despesaDao;
		this.parcelaDao = parcelaDao;
		this.chequeEmitidoDAO = chequeEmitidoDAO;
		this.fornecedorDAO = fornecedorDAO;
		
	}
	
	//tela de listagem de compras
	@Path({"/contasPagar/","/contasPagar","/contasPagar/lista"})
	public List<ContaAPagar> lista(Fornecedor fornecedor, Date dataInicio, Date dataFim, StatusMovimentacaoEnum status){
		List<ContaAPagar> lista = null;
		
		try {
			
			Calendar data = new GregorianCalendar();
			if (dataFim == null){
				
				 int ultimo_dia_mes = data.getActualMaximum(Calendar.DAY_OF_MONTH);  
				 data.set(Calendar.DAY_OF_MONTH, ultimo_dia_mes);  
				 data.set(Calendar.HOUR_OF_DAY, 0);
				 data.set(Calendar.MINUTE, 0);
				 data.set(Calendar.SECOND, 0);
				 data.set(Calendar.MILLISECOND, 0);
				 dataFim = data.getTime();
				 
			}
		     
			if(dataInicio == null){
				
				int primeiro_dia_mes = data.getActualMinimum(Calendar.DAY_OF_MONTH);
				data.set(Calendar.DAY_OF_MONTH, primeiro_dia_mes);
				data.set(Calendar.HOUR_OF_DAY, 0);
				 data.set(Calendar.MINUTE, 0);
				 data.set(Calendar.SECOND, 0);
				 data.set(Calendar.MILLISECOND, 0);
				
				dataInicio = data.getTime();
			}
			
			
			lista = dao.findByFilter(fornecedor,dataInicio, dataFim, status);
			result.include("statusList",StatusMovimentacaoEnum.values());
			result.include("dataInicio",dataInicio);
			result.include("dataFim",dataFim);
			result.include("fornecedor",fornecedor);
			result.include("status",status);
			
			List<Fornecedor> fornecedores = fornecedorDAO.findAll();
			result.include("fornecedores",fornecedores);
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
		public List<ContaAPagar> loadListaMovimentacao(Fornecedor fornecedor, Date dataInicio, Date dataFim, StatusMovimentacaoEnum status){
			List<ContaAPagar> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(fornecedor, dataInicio, dataFim, status);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public void salvar(Movimentacao movimentacao) {
		
		result.redirectTo(ContasPagarController.class).lista(null,null, null, null);
	}
	
	@Path({"/contasPagar/{contaAPagar.id}"})
	public ContaAPagar form(ContaAPagar contaAPagar) throws DAOException{
		
		
		contaAPagar = dao.findById(contaAPagar.getId());
		List<Conta> contas = null;
		if (contaAPagar.getModalidadePagamento() == FormaPagamentoEnum.T){
			contas = contaDao.obterContasBancarias();
		}else{
			contas = contaDao.obterContasFinanceiras();
		}
		result.include("contas", contas);
		
		
//		if (contaAPagar instanceof ContaAPagarDespesa){
//			ContaAPagarDespesa contaApagarDespesa = (ContaAPagarDespesa)contaAPagar;
//			
////			//Verificando se tratar-se de pagamento em cheque
////			if(contaApagarDespesa.getDespesa().getModalidadePagamento() == FormaPagamentoEnum.C){
////				
////				//Trata-se de uma despesa não parcelada
////				if(contaApagarDespesa.getParcela() == null){
////				
////					if(contaApagarDespesa.getDespesa().getChequeEmitidoList()!=null && contaApagarDespesa.getDespesa().getChequeEmitidoList().size()>0){
////						result.include("cheque", contaApagarDespesa.getDespesa().getChequeEmitidoList().get(0));
////					}
////				
////				//Se for parcelado
////				}else{
////					if(contaApagarDespesa.getParcela().getChequeEmitidoParcela()!=null){
////						result.include("cheque", contaApagarDespesa.getParcela().getChequeEmitidoParcela());
////					}
////				}
////				
////			}
//			
//		}
		
		//result.include("contas",contaDao.findAll());
		
		return contaAPagar;
	}

	
	public void confirmar(ContaAPagar contaAPagar) throws DAOException{
		
		
		ContaAPagar contaAPagarOrig = dao.findById(contaAPagar.getId());
		
		if(contaAPagarOrig.getChequeEmitido() != null){
			contaAPagar.setConta(contaAPagarOrig.getChequeEmitido().getConta());
		}
		
		//setando a conta selecionada para ser
		contaAPagarOrig.setConta(contaAPagar.getConta());
		contaAPagarOrig.setDataPagamento(contaAPagar.getDataPagamento());
		contaAPagarOrig.setStatus(StatusMovimentacaoEnum.P);
		contaAPagarOrig.setMulta(contaAPagar.getMulta());
		contaAPagarOrig.setJuros(contaAPagar.getJuros());
		contaAPagarOrig.setValorTotal(contaAPagar.getValorTotal());
		
		if (contaAPagarOrig.getModalidadePagamento() == FormaPagamentoEnum.C){
			contaAPagarOrig.setConta(contaAPagarOrig.getChequeEmitido().getConta());
		}
		
		contaAPagarOrig.setValorTotal(contaAPagarOrig.getValor());
		
		if (contaAPagarOrig.getMulta() != null){
			
			contaAPagarOrig.setValorTotal(contaAPagarOrig.getValorTotal().add(contaAPagarOrig.getMulta()));
		}
		if (contaAPagarOrig.getJuros() != null){
			
			contaAPagarOrig.setValorTotal(contaAPagarOrig.getValorTotal().add(contaAPagarOrig.getJuros()));
		}
		
		dao.updateEntity(contaAPagarOrig);
		
		if(contaAPagarOrig.getParcela() != null){
			contaAPagarOrig.getParcela().setDataPagamento(contaAPagar.getDataPagamento());
			contaAPagarOrig.getParcela().setStatus(StatusDespesaEnum.P);
			parcelaDao.updateEntity(contaAPagarOrig.getParcela());
		}
		
		MovimentacaoContasAPagar movimentacao = new MovimentacaoContasAPagar();
		movimentacao.setContaAPagar(contaAPagarOrig);
		movimentacao.setConta(contaAPagarOrig.getConta());
		
		movimentacao.setValor(contaAPagarOrig.getValorTotal());
		movimentacao.setData(contaAPagarOrig.getDataPagamento());
		movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
		
		movimentacaoDao.addEntity(movimentacao);
		
		//Alterar o Saldo da Conta Sacada
		Conta contaSacada = contaDao.findById(contaAPagarOrig.getConta().getId());
		contaSacada.setSaldo(contaSacada.getSaldo().subtract(contaAPagarOrig.getValor()));
		contaDao.updateEntity(contaSacada);
		
		
		if (contaAPagarOrig instanceof ContaAPagarCompra ){
			ContaAPagarCompra contaCompra = (ContaAPagarCompra)contaAPagarOrig;
			//verificando se trata-se de uma despesa parcelada
			if (contaCompra.getParcela() != null){
				
				
				//verificar se as demais parcelas
				
				boolean compraQuitada = true;
				for (ParcelaCompra parcela : contaCompra.getCompra().getParcelas()) {
					
					if(parcela.getStatus() != StatusDespesaEnum.P){
						compraQuitada = false;
						break;
					}
					
				}
				
				//Se todas as parcelas da despesa estiverem quitadas, entao devemos mudar o estado da despesa.
				if (compraQuitada){
					
					contaCompra.getCompra().setStatus(StatusCompraEnum.P);
					contaCompra.getCompra().setDataPagamento(contaCompra.getDataPagamento());
					compraDao.updateEntity(contaCompra.getCompra());
				}
				
				
				
			}else{
				contaCompra.getCompra().setStatus(StatusCompraEnum.P);
				contaCompra.getCompra().setDataPagamento(contaCompra.getDataPagamento());
				compraDao.updateEntity(contaCompra.getCompra());
				
			}
//			if (contaCompra.getParcela().getChequeEmitido() != null){
//				contaCompra.getParcela().getChequeEmitido().setStatus(SituacaoChequeEnum.C);
//				contaCompra.getParcela().getChequeEmitido().setDataStatus(new Date());
//				chequeEmitidoDAO.updateEntity(contaCompra.getParcela().getChequeEmitido());
//				
//			}
			
//			contaCompra.getCompra().setStatus(StatusCompraEnum.P);
//			
//			
//			compraDao.updateEntity(contaCompra.getCompra());
				
			
		}else if (contaAPagarOrig instanceof ContaAPagarDespesa){
			ContaAPagarDespesa contaDespesa = (ContaAPagarDespesa)contaAPagarOrig;
			
			//verificando se trata-se de uma despesa parcelada
			if (contaDespesa.getParcela() != null){
				
				
				//verificar se as demais parcelas
				
				boolean despesaQuitada = true;
				for (ParcelaDespesa parcela : contaDespesa.getDespesa().getParcelas()) {
					
					if(parcela.getStatus() != StatusDespesaEnum.P){
						despesaQuitada = false;
						break;
					}
					
				}
				
				//Se todas as parcelas da despesa estiverem quitadas, entao devemos mudar o estado da despesa.
				if (despesaQuitada){
					
					contaDespesa.getDespesa().setStatus(StatusDespesaEnum.P);
					contaDespesa.getDespesa().setDataPagamento(contaDespesa.getDataPagamento());
					despesaDao.updateEntity(contaDespesa.getDespesa());
				}
				
			}else{
				contaDespesa.getDespesa().setStatus(StatusDespesaEnum.P);
				contaDespesa.getDespesa().setDataPagamento(contaDespesa.getDataPagamento());
				despesaDao.updateEntity(contaDespesa.getDespesa());
			}
			
//			if (contaDespesa.getChequeEmitido() != null){
//				contaDespesa.getParcela().getChequeEmitido().setStatus(SituacaoChequeEnum.C);
//				contaDespesa.getParcela().getChequeEmitido().setDataStatus(new Date());
//				chequeEmitidoDAO.updateEntity(contaDespesa.getParcela().getChequeEmitido());
//				
//			}
			
		}
		
		if (contaAPagarOrig.getChequeEmitido() != null){
			contaAPagarOrig.getChequeEmitido().setStatus(SituacaoChequeEnum.C);
			contaAPagarOrig.getChequeEmitido().setDataStatus(new Date());
			chequeEmitidoDAO.updateEntity(contaAPagarOrig.getChequeEmitido());
			
		}
		
		result.include("mensagem", "Confirmação de pagamento da conta efetuado com sucesso!");
		result.forwardTo(this.getClass()).lista(null,null,null, null);
	}
	
	

}
