package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Adiantamento;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagarAdiantamento;
import br.com.transmetais.dao.AdiantamentoDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.SituacaoAdiantamentoEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;

@Resource
public class AdiantamentoController {
	
	private AdiantamentoDAO dao;
	private FornecedorDAO fornecedorDao;
	private ContaAPagarDAO contaAPagarDAO;
	private ContaDAO contaDao;
	
	private final Result result;
	
	
	public AdiantamentoController(Result result, AdiantamentoDAO dao, FornecedorDAO fornecedorDao, ContaAPagarDAO contaAPagarDAO, ContaDAO contaDao) {
		this.result = result;
		this.dao = dao;
		this.fornecedorDao = fornecedorDao;
		this.contaAPagarDAO = contaAPagarDAO;
		this.contaDao = contaDao;
		
		
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
		
		//result.include("fornecedores", fornecedorDao.findAll());
		result.include("tiposPagamentos", FormaPagamentoEnum.values());
		result.include("contas",contaDao.obterContasFinanceiras());
	
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
					
					
					adiantOrig.setSituacao(SituacaoAdiantamentoEnum.P);
					//adiantOrig.setDataPagamento(adiantamento.getDataPagamento());
					adiantOrig.setTipoPagamento(adiantamento.getTipoPagamento());
					
					dao.updateEntity(adiantOrig);
					
					ContaAPagarAdiantamento contaPagar = new ContaAPagarAdiantamento();
					contaPagar.setDataPrevista(adiantOrig.getDataPagamento());
					contaPagar.setAdiantamento(adiantOrig);
					contaPagar.setStatus(StatusMovimentacaoEnum.A);
					contaPagar.setValor(adiantOrig.getValor());
					contaPagar.setDescricao("Adiantamento ao Fornecedor " + adiantOrig.getFornecedor().getApelido() + " - " +adiantOrig.getFornecedor().getNome());
					
					contaAPagarDAO.addEntity(contaPagar);
					
					
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
	
	
	

}
