package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaBancaria;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class ContaBancariaController {
	
	private ContaDAO dao;
	private MovimentacaoDAO movimentacaoDao; 
	private ContaContabilDAO contaContabilDAO;
	private final Result result;
	
	
	public ContaBancariaController(Result result, ContaDAO dao, MovimentacaoDAO movimentacaoDao, ContaContabilDAO contaContabilDAO) {
		this.result = result;
		this.dao = dao;
		this.movimentacaoDao = movimentacaoDao;
		this.contaContabilDAO = contaContabilDAO;
		
	}
	
	@Path({"/contaBancaria/","/contaBancaria","/contaBancaria/lista"})
	public List<Conta> lista() throws DAOException{
		List<Conta> lista = null;
		
		lista = dao.obterContasBancarias();
		
		return lista;
	}
	
	
	@Path({"/contaBancaria/{conta.id}","/contaBancaria/form","/contaBancaria/novo"})
	public Conta form(ContaBancaria conta) throws DAOException{
		
		
		if (conta != null && conta.getId() != null && conta.getId()>0){
			
			conta = (ContaBancaria)dao.findById(conta.getId());
			
		}
		
		List<ContaContabil> contas = contaContabilDAO.findAll();
		result.include("contas", contas);
		
		
		return conta;
	}
	
	
	@Path({"/contaBancaria/add"})
	public void add(ContaBancaria conta) throws DAOException {
		
			
			
			if (conta.getId() != null && conta.getId()>0){
				
				ContaBancaria contaAnt = (ContaBancaria)dao.findById(conta.getId());
				
				contaAnt.setAgencia(conta.getAgencia());
				contaAnt.setBanco(conta.getBanco());
				contaAnt.setContaContabil(conta.getContaContabil());
				contaAnt.setContaCorrente(conta.getContaCorrente());
				contaAnt.setDataSaldoInicial(conta.getDataSaldoInicial());
				contaAnt.setDescricao(conta.getDescricao());
				contaAnt.setSaldoInicial(conta.getSaldoInicial());
				
				dao.updateEntity(contaAnt);
			}else{
				//Adicionar Movimentacao com o valor do saldo inicial e setar o saldo = saldo inicial
				conta.setSaldo(conta.getSaldoInicial());
				dao.addEntity(conta);
			}
		
		result.redirectTo(ContaBancariaController.class).lista();
	  }
	
	
	@Path("/contaBancaria/remove/{conta.id}")
	public void remove(ContaBancaria conta) throws DAOException {
		
		if (conta.getId() != null && conta.getId()>0){
			dao.removeEntity(conta);
		
		}
		result.redirectTo(ContaBancariaController.class).lista();
	  }
	
	@Path("/contaBancaria/extrato/")
	public void extrato(Conta conta) throws DAOException {
		
		result.include("contas",dao.findAll());
	  }
	
	public List<Movimentacao> loadExtrato(Long contaId){
		List<Movimentacao> lista = null;
		
		try {
			
			Conta conta = dao.findById(contaId);
			
			lista = movimentacaoDao.findByFilter(null, null, contaId);
			
			
			result.include("conta",conta);
			result.include("movimentacoes",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	@Path({"/contaBancaria/transferencia/","/contaBancaria/transferencia"})
	public void transferencia() throws DAOException {
		result.include("contas",dao.findAll());
	}
	
	
	

}
