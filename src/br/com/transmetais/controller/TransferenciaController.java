package br.com.transmetais.controller;

import java.math.BigDecimal;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.bean.MovimentacaoContasAPagar;
import br.com.transmetais.bean.MovimentacaoContasAReceber;
import br.com.transmetais.bean.Transferencia;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.TransferenciaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class TransferenciaController {
	
	private TransferenciaDAO dao;
	private MovimentacaoDAO movimentacaoDao;
	private ContaDAO contaDao;
	private final Result result;
	
	
	public TransferenciaController(Result result, TransferenciaDAO dao, MovimentacaoDAO movimentacaoDao, ContaDAO contaDao) {
		this.result = result;
		this.dao = dao;
		this.movimentacaoDao = movimentacaoDao;
		this.contaDao = contaDao;
		
	}
	
	@Path({"/transferencia/","/transferencia","/transferencia/lista"})
	public List<Transferencia> lista() throws DAOException{
		List<Transferencia> lista = null;
		
		lista = dao.findAll();
		
		result.include("contas",contaDao.findAll());
		
		return lista;
	}
	
	
	@Path({"/transferencia/{transferencia.id}","/transferencia/form","/transferencia/novo"})
	public Transferencia form(Transferencia transferencia) throws DAOException{
		
		
		if (transferencia != null && transferencia.getId() != null && transferencia.getId()>0){
			
			transferencia = dao.findById(transferencia.getId());
			
		}
		
		
		return transferencia;
	}
	
	@Path({"/contaBancaria/add"})
	public void add(Transferencia transferencia) throws DAOException {
		try {
			
			
			
			if (transferencia.getId() != null && transferencia.getId()>0){
				dao.updateEntity(transferencia);
			}else{
				
				dao.addEntity(transferencia);
				
				ContaAPagar contaaPagar = new ContaAPagar();
				contaaPagar.setConta(transferencia.getContaOrigem());
				contaaPagar.setDataPrevista(transferencia.getData());
				contaaPagar.setStatus(StatusMovimentacaoEnum.A);
				contaaPagar.setDescricao("Tansferencia " + transferencia.getId().toString() + transferencia.getDescricao());
				contaaPagar.setValor(transferencia.getValor());
				
					/*
					MovimentacaoContasAPagar movimentacao = new MovimentacaoContasAPagar();
					movimentacao.setConta(transferencia.getContaOrigem());
					movimentacao.setData(transferencia.getData());
					TipoOperacaoEnum tipoOperacao = null;
					
					movimentacao.setTipoOperacao( TipoOperacaoEnum.D);
					movimentacao.setValor(transferencia.getValor());
					movimentacaoDao.addEntity(movimentacao);
					
					MovimentacaoContasAReceber movimentacaoReceber = new MovimentacaoContasAReceber();
					movimentacaoReceber.setConta(transferencia.getContaDestino());
					movimentacaoReceber.setData(transferencia.getData());
					
					
					movimentacaoReceber.setTipoOperacao( TipoOperacaoEnum.C);
					movimentacaoReceber.setValor(transferencia.getValor());
					movimentacaoDao.addEntity(movimentacaoReceber);
					*/
					
					
				
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(TransferenciaController.class).lista();
	  }
	
	
	@Path("/contaBancaria/remove/{conta.id}")
	public void remove(Transferencia transferencia) throws DAOException {
		
		if (transferencia.getId() != null && transferencia.getId()>0){
			dao.removeEntity(transferencia);
		
		}
		result.redirectTo(TransferenciaController.class).lista();
	  }
	

	
	
//	@Path({"/contaBancaria/transferencia/","/contaBancaria/transferencia"})
//	public void transferencia() throws DAOException {
//		
//	}
	
	
	

}
