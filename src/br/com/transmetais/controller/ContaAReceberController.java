package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAReceber;
import br.com.transmetais.bean.ContaAReceberVenda;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.bean.MovimentacaoContasAReceber;
import br.com.transmetais.bean.ParcelaVenda;
import br.com.transmetais.dao.ContaAReceberDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.ParcelaDAO;
import br.com.transmetais.dao.VendaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.StatusVendaEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class ContaAReceberController {
	
	private final Result result;
	
	private ContaDAO contaDao;
	private MovimentacaoDAO movimentacaoDao;
	private ContaAReceberDAO dao;
	private VendaDAO vendaDao;
	private ParcelaDAO parcelaDao;
	//private ChequeEmitidoDAO chequeEmitidoDAO;
	
	
	public ContaAReceberController(Result result,ContaAReceberDAO dao, ContaDAO contaDao, 
			MovimentacaoDAO movimentacaoDao, VendaDAO vendaDao, 
			ParcelaDAO parcelaDao) {
		this.dao = dao;
		this.result = result;
		this.contaDao = contaDao;
		this.movimentacaoDao = movimentacaoDao;
		this.vendaDao = vendaDao;
		
	
		this.parcelaDao = parcelaDao;
	
		
	}
	
	//tela de listagem de compras
	@Path({"/contaAReceber/","/contaAReceber","/contaAReceber/lista"})
	public List<ContaAReceber> lista(Date dataInicio, Date dataFim, StatusMovimentacaoEnum status){
		List<ContaAReceber> lista = null;
		
		try {
			
			
			lista = dao.findByFilter(dataInicio, dataFim, status);
			result.include("statusList",StatusMovimentacaoEnum.values());
			result.include("dataInicio",dataInicio);
			result.include("dataFim",dataFim);
			result.include("status",status);

			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
		public List<ContaAReceber> loadListaMovimentacao(Date dataInicio, Date dataFim, StatusMovimentacaoEnum status){
			List<ContaAReceber> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(dataInicio, dataFim, status);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public void salvar(Movimentacao movimentacao) {
		
		result.redirectTo(ContaAReceberController.class).lista(null, null, null);
	}
	
	@Path({"/contaAReceber/{contaAReceber.id}"})
	public ContaAReceber form(ContaAReceber contaAReceber) throws DAOException{
		
		
		contaAReceber = dao.findById(contaAReceber.getId());
		List<Conta> contas = null;
		if (contaAReceber.getModalidadePagamento() == FormaPagamentoEnum.T){
			contas = contaDao.obterContasBancarias();
		}else{
			contas = contaDao.obterContasFinanceiras();
		}
		result.include("contas", contas);
		
	
		return contaAReceber;
	}

	
	public void confirmar(ContaAReceber contaAReceber) throws DAOException{
		
		
		ContaAReceber contaAReceberOrig = dao.findById(contaAReceber.getId());
		
//		if(contaAReceberOrig.getChequeEmitido() != null){
//			contaAReceber.setConta(contaAReceberOrig.getChequeEmitido().getConta());
//		}
		
		//setando a conta selecionada para ser
		contaAReceberOrig.setConta(contaAReceber.getConta());
		contaAReceberOrig.setDataPagamento(contaAReceber.getDataPagamento());
		contaAReceberOrig.setStatus(StatusMovimentacaoEnum.P);
		contaAReceberOrig.setMulta(contaAReceber.getMulta());
		contaAReceberOrig.setJuros(contaAReceber.getJuros());
		contaAReceberOrig.setValorTotal(contaAReceber.getValorTotal());
		contaAReceberOrig.setValor(contaAReceber.getValor());
		
		if (contaAReceberOrig.getModalidadePagamento() == FormaPagamentoEnum.C){
			//contaAReceberOrig.setConta(contaAReceberOrig.getChequeEmitido().getConta());
		}
		
		contaAReceberOrig.setValorTotal(contaAReceberOrig.getValor());
		
		if (contaAReceberOrig.getMulta() != null){
			
			contaAReceberOrig.setValorTotal(contaAReceberOrig.getValorTotal().add(contaAReceberOrig.getMulta()));
		}
		if (contaAReceberOrig.getJuros() != null){
			
			contaAReceberOrig.setValorTotal(contaAReceberOrig.getValorTotal().add(contaAReceberOrig.getJuros()));
		}
		
		dao.updateEntity(contaAReceberOrig);
		
		if(contaAReceberOrig.getParcela() != null){
			contaAReceberOrig.getParcela().setDataPagamento(contaAReceber.getDataPagamento());
			contaAReceberOrig.getParcela().setStatus(StatusDespesaEnum.P);
			parcelaDao.updateEntity(contaAReceberOrig.getParcela());
		}
		
		MovimentacaoContasAReceber movimentacao = new MovimentacaoContasAReceber();
		movimentacao.setContaAReceber(contaAReceberOrig);
		movimentacao.setConta(contaAReceberOrig.getConta());
		
		movimentacao.setValor(contaAReceberOrig.getValorTotal());
		movimentacao.setData(contaAReceberOrig.getDataPagamento());
		movimentacao.setTipoOperacao(TipoOperacaoEnum.C);
		
		movimentacaoDao.addEntity(movimentacao);
		
		//Alterar o Saldo da Conta Creditada
		Conta contaCreditada = contaDao.findById(contaAReceberOrig.getConta().getId());
		contaCreditada.setSaldo(contaCreditada.getSaldo().add(contaAReceberOrig.getValorTotal()));
		contaDao.updateEntity(contaCreditada);
		
		
		if (contaAReceberOrig instanceof ContaAReceberVenda ){
			ContaAReceberVenda contaVenda = (ContaAReceberVenda)contaAReceberOrig;
			
			//verificando se trata-se de uma venda parcelada
			if (contaVenda.getParcela() != null){
				
				
				//verificar se as demais parcelas
				
				boolean compraQuitada = true;
				for (ParcelaVenda parcela : contaVenda.getVenda().getParcelas()) {
					
					if(parcela.getStatus() != StatusDespesaEnum.P){
						compraQuitada = false;
						break;
					}
					
				}
				
				//Se todas as parcelas da despesa estiverem quitadas, entao devemos mudar o estado da despesa.
				if (compraQuitada){
					
					contaVenda.getVenda().setStatus(StatusVendaEnum.P);
					contaVenda.getVenda().setDataRecebimento(contaVenda.getDataPagamento());
					vendaDao.updateEntity(contaVenda.getVenda());
				}
				
				
				
			}else{
				contaVenda.getVenda().setStatus(StatusVendaEnum.P);
				contaVenda.getVenda().setDataRecebimento(contaVenda.getDataPagamento());
				vendaDao.updateEntity(contaVenda.getVenda());
				
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
				
			
		
		
//		if (contaAReceberOrig.getChequeEmitido() != null){
//			contaAReceberOrig.getChequeEmitido().setStatus(SituacaoChequeEnum.C);
//			contaAReceberOrig.getChequeEmitido().setDataStatus(new Date());
//			chequeEmitidoDAO.updateEntity(contaAReceberOrig.getChequeEmitido());
//			
//		}
		
		result.include("mensagem", "Confirmação de recebimento da conta efetuado com sucesso!");
		result.redirectTo(this.getClass()).lista(null,null, null);
		}
	
	}

}
