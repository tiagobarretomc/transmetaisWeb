package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.MovimentacaoTransferencia;
import br.com.transmetais.bean.Transferencia;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.TransferenciaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
@Path("/transferencia")
public class TransferenciaController extends BaseController<Transferencia, TransferenciaDAO>{
	
	private MovimentacaoDAO movimentacaoDao;
	private ContaDAO contaDao;
	
	
	@Override
	protected void prePersistUpdate(Transferencia bean) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void postPersistUpdate(Transferencia bean, Result result) {
		
//		ContaAPagar contaaPagar = new ContaAPagar();
//		contaaPagar.setConta(bean.getContaOrigem());
//		contaaPagar.setDataPrevista(bean.getData());
//		contaaPagar.setStatus(StatusMovimentacaoEnum.A);
//		contaaPagar.setDescricao("Tansferencia " + bean.getId().toString() + bean.getDescricao());
//		contaaPagar.setValor(bean.getValor());
		
		
		MovimentacaoTransferencia movimentacaoOrigem = new MovimentacaoTransferencia();
		movimentacaoOrigem.setConta(bean.getContaOrigem());
		movimentacaoOrigem.setData(bean.getData());
		movimentacaoOrigem.setTransferencia(bean);
		movimentacaoOrigem.setTipoOperacao(TipoOperacaoEnum.D);
		movimentacaoOrigem.setValor(bean.getValor());
		
		
		
		try{
			movimentacaoDao.addEntity(movimentacaoOrigem);
		}catch(DAOException ex){
			ex.printStackTrace();
			result.include("erro", ex.getMessage());
		}
		
		//Atualizando saldo da conta origem
		Conta contaOrigem = bean.getContaOrigem();
		try {
			contaOrigem = contaDao.findById(contaOrigem.getId());
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			result.include("erro", e1.getMessage());
		}
		contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(bean.getValor()));
		
		try{
			contaDao.updateEntity(contaOrigem);
		}catch(DAOException ex){
			ex.printStackTrace();
			result.include("erro", ex.getMessage());
		}
		
		
		MovimentacaoTransferencia movimentacaoDestino = new MovimentacaoTransferencia();
		movimentacaoDestino.setConta(bean.getContaDestino());
		movimentacaoDestino.setData(bean.getData());
		movimentacaoDestino.setTransferencia(bean);
		movimentacaoDestino.setTipoOperacao(TipoOperacaoEnum.C);
		movimentacaoDestino.setValor(bean.getValor());
		try{
			
			movimentacaoDao.addEntity(movimentacaoDestino);
		}catch(DAOException ex){
			ex.printStackTrace();
			result.include("erro", ex.getMessage());
		}
		
		//Atualizando saldo da conta de Destino
		Conta contaDestino = bean.getContaDestino();
		try {
			contaDestino = contaDao.findById(contaDestino.getId());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.include("erro", e.getMessage());
		}
		contaDestino.setSaldo(contaDestino.getSaldo().add(bean.getValor()));
		
		try{
			contaDao.updateEntity(contaDestino);
		}catch(DAOException ex){
			ex.printStackTrace();
			result.include("erro", ex.getMessage());
		}
		
	}
	
	
	
	
	
	
	@Autowired 
	public void setContaDao(ContaDAO contaDao) {
		this.contaDao = contaDao;
	}
	
	@Autowired
	public void setMovimentacaoDao(MovimentacaoDAO movimentacaoDao) {
		this.movimentacaoDao = movimentacaoDao;
	}

	@Override
	protected Transferencia createInstance() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void initForm(Transferencia bean) {
		List<Conta> contas = contaDao.obterContasFinanceiras();
		result.include("contas", contas);
		
	}
	
	
	
	
//	@Path({"/contaBancaria/transferencia/","/contaBancaria/transferencia"})
//	public void transferencia() throws DAOException {
//		
//	}
	
	
	

}
