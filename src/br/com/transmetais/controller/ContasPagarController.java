package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.bean.MovimentacaoContasAPagar;
import br.com.transmetais.bean.ParcelaDespesa;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.ParcelaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
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
	
	
	public ContasPagarController(Result result,ContaAPagarDAO dao, ContaDAO contaDao, 
			MovimentacaoDAO movimentacaoDao, CompraDAO compraDao, 
			DespesaDAO despesaDao, ParcelaDAO parcelaDao) {
		this.dao = dao;
		this.result = result;
		this.contaDao = contaDao;
		this.movimentacaoDao = movimentacaoDao;
		this.compraDao = compraDao;
		
		this.despesaDao = despesaDao;
		this.parcelaDao = parcelaDao;
		
	}
	
	//tela de listagem de compras
	@Path({"/contasPagar/","/contasPagar","/contasPagar/lista"})
	public List<ContaAPagar> lista(Date dataInicio, Date dataFim){
		List<ContaAPagar> lista = null;
		
		try {
			
			
			lista = dao.findByFilter(dataInicio, dataFim);

			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
		public List<ContaAPagar> loadListaMovimentacao(Date dataInicio, Date dataFim){
			List<ContaAPagar> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(dataInicio, dataFim);

			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public void salvar(Movimentacao movimentacao) {
		
		
		result.redirectTo(ContasPagarController.class).lista(null, null);
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
		
		//setando a conta selecionada para ser
		contaAPagarOrig.setConta(contaAPagar.getConta());
		contaAPagarOrig.setDataPagamento(contaAPagar.getDataPagamento());
		contaAPagarOrig.setStatus(StatusMovimentacaoEnum.P);
		
		if (contaAPagar.getModalidadePagamento() == FormaPagamentoEnum.C){
			contaAPagarOrig.setConta(contaAPagar.getChequeEmitido().getConta());
		}
		
		dao.updateEntity(contaAPagarOrig);
		
		MovimentacaoContasAPagar movimentacao = new MovimentacaoContasAPagar();
		movimentacao.setContaAPagar(contaAPagarOrig);
		movimentacao.setConta(contaAPagarOrig.getConta());
		
		movimentacao.setValor(contaAPagarOrig.getValor());
		movimentacao.setData(contaAPagarOrig.getDataPagamento());
		movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
		
		movimentacaoDao.addEntity(movimentacao);
		
		//Alterar o Saldo da Conta Sacada
		Conta contaSacada = contaDao.findById(contaAPagarOrig.getConta().getId());
		contaSacada.setSaldo(contaSacada.getSaldo().subtract(contaAPagarOrig.getValor()));
		contaDao.updateEntity(contaSacada);
		
		
		if (contaAPagarOrig instanceof ContaAPagarCompra ){
			ContaAPagarCompra contaCompra = (ContaAPagarCompra)contaAPagarOrig;
			contaCompra.getCompra().setStatus(StatusCompraEnum.P);
			
			
			compraDao.updateEntity(contaCompra.getCompra());
				
			
		}else if (contaAPagarOrig instanceof ContaAPagarDespesa){
			ContaAPagarDespesa contaDespesa = (ContaAPagarDespesa)contaAPagarOrig;
			
			//verificando se trata-se de uma despesa parcelada
			if (contaDespesa.getParcela() != null){
				//alterando o status da parcela em questao
				contaDespesa.getParcela().setStatus(StatusDespesaEnum.P);
				contaDespesa.getParcela().setDataPagamento(contaDespesa.getDataPagamento());
				parcelaDao.updateEntity(contaDespesa.getParcela());
				
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
			
		}
		
		result.nothing();
	}
	
	

}
