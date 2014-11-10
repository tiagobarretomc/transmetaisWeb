package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Adiantamento;
import br.com.transmetais.bean.ChequeEmitido;
import br.com.transmetais.bean.ChequeEmitidoAdiantamento;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.MovimentacaoAdiantamento;
import br.com.transmetais.dao.AdiantamentoDAO;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.SituacaoAdiantamentoEnum;
import br.com.transmetais.type.SituacaoChequeEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class AdiantamentoController {
	
	private AdiantamentoDAO dao;
	private FornecedorDAO fornecedorDao;
	private MovimentacaoDAO movimentacaoDao;
	private ContaDAO contaDao;
	private ChequeEmitidoDAO chequeEmitidoDao;
	
	private final Result result;
	
	
	public AdiantamentoController(Result result, AdiantamentoDAO dao, FornecedorDAO fornecedorDao, MovimentacaoDAO movimentacaoDao, ContaDAO contaDao,
			ChequeEmitidoDAO chequeEmitidoDao) {
		this.result = result;
		this.dao = dao;
		this.fornecedorDao = fornecedorDao;
		this.movimentacaoDao = movimentacaoDao;
		this.contaDao = contaDao;
		this.chequeEmitidoDao = chequeEmitidoDao;
		
		
	}
	
	@Path({"/adiantamento/","/adiantamento","/adiantamento/lista"})
	public List<Adiantamento> lista() throws DAOException{
		List<Adiantamento> lista = null;
		
		lista = dao.findAll();
		
		return lista;
	}
	
	
	@Path({"/adiantamento/{adiantamento.id}","/adiantamento/form","/adiantamento/novo"})
	public Adiantamento form(Adiantamento adiantamento) throws DAOException{
		
		
		if (adiantamento != null && adiantamento.getId() != null && adiantamento.getId()>0){
			
			adiantamento = dao.findById(adiantamento.getId());
			
		}
		
		result.include("fornecedores", fornecedorDao.obterComRotativo());
		result.include("tiposPagamentos", FormaPagamentoEnum.values());
	
		return adiantamento;
	}
	
	@Path({"/adiantamento/aprovar/{adiantamento.id}"})
	public Adiantamento aprovar (Adiantamento adiantamento) throws DAOException{
		
		
		if (adiantamento != null && adiantamento.getId() != null && adiantamento.getId()>0){
			
			adiantamento = dao.findById(adiantamento.getId());
			
		}
		
		//Utlizamos para Adiantamento apenas Cheque, Transferencia bancária e Dinheiro.
		FormaPagamentoEnum[] tiposPagamentos = new FormaPagamentoEnum[]{FormaPagamentoEnum.C, FormaPagamentoEnum.T, FormaPagamentoEnum.D};
		
		
		//result.include("fornecedores", fornecedorDao.findAll());
		result.include("tiposPagamentos", tiposPagamentos);
		//result.include("contas",contaDao.obterContasFinanceiras());
	
		return adiantamento;
	}
	
	public void add(Adiantamento adiantamento) throws DAOException {
		try {
			if (adiantamento.getId() != null && adiantamento.getId()>0){
				//Adiantamento só poderá ser alterado enquanto a situação for em aberto
				if (adiantamento.getSituacao() == SituacaoAdiantamentoEnum.A) 
					dao.updateEntity(adiantamento);
			}else{
				adiantamento.setDataInclusao(new Date());
				adiantamento.setSituacao(SituacaoAdiantamentoEnum.A);
				dao.addEntity(adiantamento);
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.include("mensagem", "Registro incluído com sucesso!");
		result.redirectTo(AdiantamentoController.class).lista();
	  }
	
	//Confirmação de Pagamento do Adiantamento
	public void confirmar(Adiantamento adiantamento, ChequeEmitidoAdiantamento cheque) throws DAOException {
		try {
			if (adiantamento.getId() != null && adiantamento.getId()>0){
				Adiantamento adiantOrig = dao.findById(adiantamento.getId());
				adiantOrig.setConta(adiantamento.getConta());
				adiantOrig.setDataPagamento(adiantamento.getDataPagamento());
				adiantOrig.setTipoPagamento(adiantamento.getTipoPagamento());
				//adiantOrig.setSituacao();
				//Adiantamento só poderá ser alterado enquanto a situação for em aberto
				if (adiantOrig.getSituacao() == SituacaoAdiantamentoEnum.A){
					
					
					adiantOrig.setSituacao(SituacaoAdiantamentoEnum.PG);
					
					
					dao.updateEntity(adiantOrig);
					
					//Quando se tratar de cheque nao podemos inserir movimentacao nem atualizar saldos
					if (adiantamento.getTipoPagamento() != FormaPagamentoEnum.C){
						
						adiantamento = dao.findById(adiantamento.getId());;
					
						//Criar a Movimentacao origem do recurso a ser transferido para a conta do fornecedor
						MovimentacaoAdiantamento movimentacaoOrigem = new MovimentacaoAdiantamento();
						movimentacaoOrigem.setAdiantamento(adiantamento);
						movimentacaoOrigem.setConta(adiantamento.getConta());
						movimentacaoOrigem.setData(adiantamento.getDataPagamento());
						movimentacaoOrigem.setTipoOperacao(TipoOperacaoEnum.D);
						movimentacaoOrigem.setValor(adiantamento.getValor());
						
						movimentacaoDao.addEntity(movimentacaoOrigem);
						
						//Atualizando saldo da conta origem
						Conta contaOrigem = adiantamento.getConta();
						contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(adiantamento.getValor()));
						contaDao.updateEntity(contaOrigem);
						
						//Criar a Movimentacao de Destivo na Conta do Fornecedor
						MovimentacaoAdiantamento movimentacaoDestino = new MovimentacaoAdiantamento();
						movimentacaoDestino.setAdiantamento(adiantamento);
						movimentacaoDestino.setConta(adiantamento.getFornecedor().getConta());
						movimentacaoDestino.setData(adiantamento.getDataPagamento());
						movimentacaoDestino.setTipoOperacao(TipoOperacaoEnum.C);
						movimentacaoDestino.setValor(adiantamento.getValor());
						movimentacaoDao.addEntity(movimentacaoDestino);
						
						//Atualizando saldo da conta destino
						Conta contaDestino = adiantamento.getFornecedor().getConta();
						contaDestino.setSaldo(contaDestino.getSaldo().add(adiantamento.getValor()));
						contaDao.updateEntity(contaDestino);

					}
					//Quando se tratar de cheque precisamos persistir o cheque emitido
					else{
						
						//ChequeEmitidoAdiantamento cheque = new ChequeEmitidoAdiantamento();
						
						cheque.setConta(adiantOrig.getConta());
						cheque.setData(adiantOrig.getDataPagamento());
						cheque.setAdiantamento(adiantOrig);
						cheque.setValor(adiantOrig.getValor());
						cheque.setStatus(SituacaoChequeEnum.A);
						
						chequeEmitidoDao.addEntity(cheque);
					}
					
					result.include("mensagem", "Adiantamento efetivado com sucesso!");
					
				}
				
			
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(AdiantamentoController.class).lista();
	  }
	
	
	
	@Path({"/adiantamento/cancelar/{adiantamento.id}"}) 
	public void cancelar(Adiantamento adiantamento) throws DAOException {
		
		if (adiantamento.getId() != null && adiantamento.getId()>0){
			
			adiantamento = dao.findById(adiantamento.getId());
			
			//Adiantamento só poderá ser alterado enquanto a situação for em aberto
			if (adiantamento.getSituacao() == SituacaoAdiantamentoEnum.A) {
				adiantamento = dao.findById(adiantamento.getId());
				adiantamento.setSituacao(SituacaoAdiantamentoEnum.C);
				adiantamento.setData(new Date());
				
				dao.updateEntity(adiantamento);
			}			
		}
			result.redirectTo(AdiantamentoController.class).lista();
	  }
	
	public void loadContas(FormaPagamentoEnum tipoPagamento) throws Exception{
		
		
		List<Conta> lista = null;
		
		
		// se for pagamento em dinheiro carregar contas de fundo fixo
		if (tipoPagamento == FormaPagamentoEnum.D){
			lista = (List<Conta>)contaDao.obterContasFundoFixo();
		}
		//se nao for pagamento em dinheiro carregar as contas bancárias
		else{
			
			lista = (List<Conta>)contaDao.obterContasBancarias();
		}
		
		
		if (lista != null)
			result.use(json()).from(lista).serialize();
		
		result.nothing();
		
	}
	

}
