package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Adiantamento;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.bean.MovimentacaoAdiantamento;
import br.com.transmetais.dao.AdiantamentoDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.SituacaoAdiantamentoEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class AdiantamentoController {
	
	private AdiantamentoDAO dao;
	private FornecedorDAO fornecedorDao;
	private MovimentacaoDAO movimentacaoDao;
	
	private final Result result;
	
	
	public AdiantamentoController(Result result, AdiantamentoDAO dao, FornecedorDAO fornecedorDao, MovimentacaoDAO movimentacaoDao) {
		this.result = result;
		this.dao = dao;
		this.fornecedorDao = fornecedorDao;
		this.movimentacaoDao = movimentacaoDao;
		
		
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
		
		result.include("fornecedores", fornecedorDao.findAll());
		result.include("tiposPagamentos", FormaPagamentoEnum.values());
	
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
		
		result.redirectTo(AdiantamentoController.class).lista();
	  }
	
	public void confirmar(Adiantamento adiantamento, Conta contaOrigem) throws DAOException {
		try {
			if (adiantamento.getId() != null && adiantamento.getId()>0){
				Adiantamento adiantOrig = dao.findById(adiantamento.getId());
				//Adiantamento só poderá ser alterado enquanto a situação for em aberto
				if (adiantOrig.getSituacao() == SituacaoAdiantamentoEnum.A){
					
					
					adiantamento.setSituacao(SituacaoAdiantamentoEnum.A);
					
					//Registro de Movimentacao de ENtrada na conta do fornecedor
					Movimentacao movimentacao = new Movimentacao();
					movimentacao.setConta(adiantOrig.getFornecedor().getConta());
					movimentacao.setData(adiantamento.getDataPagamento());
					//um valor está sendo creditado na conta do fornecedor
					movimentacao.setTipoOperacao(TipoOperacaoEnum.C);
					movimentacao.setValor(adiantamento.getValor());
					
					
					//Registro de Movimentacao de Saída de uma conta da Empresa
					Movimentacao movementacaoDebito = new Movimentacao();
					movementacaoDebito.setConta(adiantOrig.getFornecedor().getConta());
					movementacaoDebito.setData(adiantamento.getDataPagamento());
					//um valor está sendo creditado na conta do fornecedor
					movementacaoDebito.setTipoOperacao(TipoOperacaoEnum.D);
					movementacaoDebito.setValor(adiantamento.getValor());
						
					
					
					
				}
				
			
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(AdiantamentoController.class).lista();
	  }
	
	
	public void cancelar(Adiantamento adiantamento) throws DAOException {
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
		
		result.redirectTo(AdiantamentoController.class).lista();
	  }
	
	
	@Path("/adiantamento/cancelar/{adiantamento.id}")
	public void remove(Adiantamento adiantamento) throws DAOException {
		
		if (adiantamento.getId() != null && adiantamento.getId()>0){
			adiantamento = dao.findById(adiantamento.getId());
			adiantamento.setSituacao(SituacaoAdiantamentoEnum.C);
		
		}
		result.redirectTo(AdiantamentoController.class).lista();
	  }

}
