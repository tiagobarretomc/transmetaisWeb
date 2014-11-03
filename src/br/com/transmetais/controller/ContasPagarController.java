package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.ContaAPagarAdiantamento;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.bean.MovimentacaoContasAPagar;
import br.com.transmetais.bean.Parcela;
import br.com.transmetais.dao.AdiantamentoDAO;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.ParcelaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.SituacaoAdiantamentoEnum;
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
	private AdiantamentoDAO adiantamentoDao;
	private CompraDAO compraDao;
	private DespesaDAO despesaDao;
	private ParcelaDAO parcelaDao;
	
	
	public ContasPagarController(Result result,ContaAPagarDAO dao, ContaDAO contaDao, 
			MovimentacaoDAO movimentacaoDao, AdiantamentoDAO adiantamentoDao, CompraDAO compraDao, 
			DespesaDAO despesaDao, ParcelaDAO parcelaDao) {
		this.dao = dao;
		this.result = result;
		this.contaDao = contaDao;
		this.movimentacaoDao = movimentacaoDao;
		this.compraDao = compraDao;
		this.adiantamentoDao = adiantamentoDao;
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
				
		
		result.include("contas",contaDao.findAll());
		
		return contaAPagar;
	}

	
	public void confirmar(ContaAPagar contaAPagar) throws DAOException{
		
		
		ContaAPagar contaAPagarOrig = dao.findById(contaAPagar.getId());
		
		//setando a conta selecionada para ser
		contaAPagarOrig.setConta(contaAPagar.getConta());
		contaAPagarOrig.setDataPagamento(contaAPagar.getDataPagamento());
		contaAPagarOrig.setStatus(StatusMovimentacaoEnum.P);
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
		
		
		//Quando se tratar de adiantamento será inserido a movimentacao de crédito na conta do fornecedor
		if (contaAPagarOrig instanceof ContaAPagarAdiantamento){
			
			ContaAPagarAdiantamento contaAdiant = (ContaAPagarAdiantamento) contaAPagarOrig;
			
			MovimentacaoContasAPagar movimentacaoDestino = new MovimentacaoContasAPagar();
			movimentacaoDestino.setContaAPagar(contaAPagarOrig);
			movimentacaoDestino.setConta(contaAdiant.getAdiantamento().getFornecedor().getConta());
			
			movimentacaoDestino.setValor(contaAPagarOrig.getValor());
			movimentacaoDestino.setData(contaAPagarOrig.getDataPagamento());
			movimentacaoDestino.setTipoOperacao(TipoOperacaoEnum.C);
			
			movimentacaoDao.addEntity(movimentacaoDestino);
			
			//Alterar o Saldo do fornecedor
			Conta conta = contaAdiant.getAdiantamento().getFornecedor().getConta();
			conta.setSaldo(conta.getSaldo().add(movimentacaoDestino.getValor()));
			contaDao.updateEntity(conta);
			
			contaAdiant.getAdiantamento().setSituacao(SituacaoAdiantamentoEnum.PG);
			adiantamentoDao.updateEntity(contaAdiant.getAdiantamento());
			
		}else if (contaAPagarOrig instanceof ContaAPagarCompra ){
			ContaAPagarCompra contaCompra = (ContaAPagarCompra)contaAPagarOrig;
			contaCompra.getCompra().setStatus(StatusCompraEnum.P);
			
			
			compraDao.updateEntity(contaCompra.getCompra());
			
//			//Caso o Fornecedor trabalhe com adiantamentos devemos creditar o valor do carregamento na conta dele.
//			if(contaCompra.getCompra().getFornecedor().getTipoFaturamento() == TipoFaturamentoEnum.ADIANT){
//				
//				
//				MovimentacaoContasAPagar movimentacaoDestino = new MovimentacaoContasAPagar();
//				movimentacaoDestino.setContaAPagar(contaAPagarOrig);
//				movimentacaoDestino.setConta(contaCompra.getCompra().getFornecedor().getConta());
//				
//				movimentacaoDestino.setValor(contaAPagarOrig.getValor());
//				movimentacaoDestino.setData(contaAPagarOrig.getDataPagamento());
//				movimentacaoDestino.setTipoOperacao(TipoOperacaoEnum.C);
//				
//				movimentacaoDao.addEntity(movimentacaoDestino);
//				
//				//Alterar o Saldo do fornecedor
//				Conta conta = contaCompra.getCompra().getFornecedor().getConta();
//				conta.setSaldo(conta.getSaldo().subtract(movimentacaoDestino.getValor()));
//				contaDao.updateEntity(conta);
//				
//				//Setando a registroo de compra como pago
//				contaCompra.getCompra().setStatus(StatusCompraEnum.P);
//				compraDao.updateEntity(contaCompra.getCompra());
//				
//				
//			}
				
			
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
				for (Parcela parcela : contaDespesa.getDespesa().getParcelas()) {
					
					if(parcela.getStatus() == StatusDespesaEnum.A){
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
