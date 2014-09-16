package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.ContaFornecedor;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;

@Resource
public class ContaFornecedorController {
	
	private ContaDAO dao;
	private MovimentacaoDAO movimentacaoDao; 
	private ContaContabilDAO contaContabilDAO;
	private FornecedorDAO fornecedorDAO;
	private final Result result;
	
	
	public ContaFornecedorController(Result result, ContaDAO dao, MovimentacaoDAO movimentacaoDao, ContaContabilDAO contaContabilDAO, FornecedorDAO fornecedorDAO) {
		this.result = result;
		this.dao = dao;
		this.movimentacaoDao = movimentacaoDao;
		this.contaContabilDAO = contaContabilDAO;
		this.fornecedorDAO = fornecedorDAO;
		
	}
	
	@Path({"/contaFornecedor/","/contaFornecedor","/contaFornecedor/lista"})
	public List<ContaFornecedor> lista() throws DAOException{
		List<ContaFornecedor> lista = null;
		
		lista = dao.obterContasFornecedor();
		
		return lista;
	}
	
	
	@Path({"/contaFornecedor/{conta.id}","/contaFornecedor/form","/contaFornecedor/novo"})
	public Conta form(ContaFornecedor conta) throws DAOException{
		
		
		if (conta != null && conta.getId() != null && conta.getId()>0){
			
			conta = (ContaFornecedor)dao.findById(conta.getId());
			
		}
		
		List<Fornecedor> fornecedores = fornecedorDAO.findAll();
		
		result.include("fornecedores",fornecedores);
		
		List<ContaContabil> contas = contaContabilDAO.findAll();
		result.include("contas", contas);
		
		
		return conta;
	}
	
	
	@Path({"/contaFornecedor/add"})
	public void add(ContaFornecedor conta) throws DAOException {
		
			
			
			if (conta.getId() != null && conta.getId()>0){
				
				ContaFornecedor contaAnt = (ContaFornecedor)dao.findById(conta.getId());
				
				contaAnt.setFornecedor(conta.getFornecedor());
				contaAnt.setLimite(conta.getLimite());
				contaAnt.setContaContabil(conta.getContaContabil());
				contaAnt.setDataSaldoInicial(conta.getDataSaldoInicial());
				contaAnt.setDescricao(conta.getDescricao());
				contaAnt.setSaldoInicial(conta.getSaldoInicial());
				
				dao.updateEntity(contaAnt);
			}else{
				//Adicionar Movimentacao com o valor do saldo inicial e setar o saldo = saldo inicial
				conta.setSaldo(conta.getSaldoInicial());
				dao.addEntity(conta);
			}
		
		result.redirectTo(ContaFornecedorController.class).lista();
	  }
	
	
	@Path("/contaFornecedor/remove/{conta.id}")
	public void remove(ContaFornecedor conta) throws DAOException {
		
		if (conta.getId() != null && conta.getId()>0){
			dao.removeEntity(conta);
		
		}
		result.redirectTo(ContaFornecedorController.class).lista();
	  }
	
	@Path("/contaFornecedor/extrato/")
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
	@Path({"/contaFornecedor/transferencia/","/contaFornecedor/transferencia"})
	public void transferencia() throws DAOException {
		result.include("contas",dao.findAll());
	}
	
	
	

}
