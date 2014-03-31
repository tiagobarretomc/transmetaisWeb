package br.com.transmetais.controller;

import java.math.BigDecimal;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.Estado;
import br.com.transmetais.bean.Funcionario;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class ContaBancariaController {
	
	private ContaDAO dao;
	private final Result result;
	
	
	public ContaBancariaController(Result result, ContaDAO dao) {
		this.result = result;
		this.dao = dao;
		
		
	}
	
	@Path({"/contaBancaria/","/contaBancaria","/contaBancaria/lista"})
	public List<Conta> lista() throws DAOException{
		List<Conta> lista = null;
		
		lista = dao.findAll();
		
		return lista;
	}
	
	
	@Path({"/contaBancaria/{conta.id}","/contaBancaria/form","/contaBancaria/novo"})
	public Conta form(Conta conta) throws DAOException{
		
		
		if (conta != null && conta.getId() != null && conta.getId()>0){
			
			conta = dao.findById(conta.getId());
			
		}
		
		
		return conta;
	}
	
	@Path({"/contaBancaria/add"})
	public void add(Conta conta) throws DAOException {
		try {
			
			if(conta.getBancaria() == null){
				conta.setBancaria(Boolean.FALSE);
			}
			
			if (conta.getId() != null && conta.getId()>0){
				dao.updateEntity(conta);
			}else{
				//Adicionar Movimentacao com o valor do saldo inicial e setar o saldo = saldo inicial
				conta.setSaldo(conta.getSaldoInicial());
				dao.addEntity(conta);
				
				if(conta.getSaldo().compareTo(new BigDecimal(0)) != 0){
					
					Movimentacao movimentacao = new Movimentacao();
					movimentacao.setConta(conta);
					movimentacao.setData(conta.getDataSaldoInicial());
					TipoOperacaoEnum tipoOperacao = null;
					if(conta.getSaldo().compareTo(new BigDecimal(0)) < 0){
						tipoOperacao = TipoOperacaoEnum.D;
					}else if(conta.getSaldo().compareTo(new BigDecimal(0)) > 0){
						tipoOperacao = TipoOperacaoEnum.C;
					}
					movimentacao.setTipoOperacao(tipoOperacao);
					movimentacao.setValor(conta.getSaldoInicial());
					
				}
			}
			
		} catch (DAOException e) {
			
			e.printStackTrace();
		}
		
		result.redirectTo(ContaBancariaController.class).lista();
	  }
	
	
	@Path("/contaBancaria/remove/{conta.id}")
	public void remove(Conta conta) throws DAOException {
		
		if (conta.getId() != null && conta.getId()>0){
			dao.removeEntity(conta);
		
		}
		result.redirectTo(ContaBancariaController.class).lista();
	  }

}
