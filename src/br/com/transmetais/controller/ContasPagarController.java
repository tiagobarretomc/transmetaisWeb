package br.com.transmetais.controller;

import java.util.Date;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.ContaAPagarAdiantamento;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.bean.MovimentacaoContasAPagar;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
public class ContasPagarController {
	
	private final Result result;
	
	private ContaDAO contaDao;
	private MovimentacaoDAO movimentacaoDao;
	
	private ContaAPagarDAO dao;
	
	
	public ContasPagarController(Result result,ContaAPagarDAO dao, ContaDAO contaDao, MovimentacaoDAO movimentacaoDao) {
		this.dao = dao;
		this.result = result;
		this.contaDao = contaDao;
		this.movimentacaoDao = movimentacaoDao;
		
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
				
		
		result.include("contas",contaDao.obterContasFinanceiras());
		
		return contaAPagar;
	}

	
	public ContaAPagar confirmar(ContaAPagar contaAPagar) throws DAOException{
		
		
		ContaAPagar contaAPagarOrig = dao.findById(contaAPagar.getId());
		
		contaAPagarOrig.setConta(contaAPagar.getConta());
		contaAPagarOrig.setDataPagamento(contaAPagar.getDataPagamento());
		contaAPagarOrig.setStatus(StatusMovimentacaoEnum.P);
		dao.updateEntity(contaAPagarOrig);
		
		
		//Quando se tratar de adiantamento será inserido a movimentacao de crédito na conta do fornecedor
		if (contaAPagarOrig instanceof ContaAPagarAdiantamento){
			MovimentacaoContasAPagar movimentacaoDestino = new MovimentacaoContasAPagar();
			movimentacaoDestino.setContaAPagar(contaAPagarOrig);
			movimentacaoDestino.setConta(((ContaAPagarAdiantamento) contaAPagarOrig).getAdiantamento().getFornecedor().getConta());
			
			movimentacaoDestino.setValor(contaAPagarOrig.getValor());
			movimentacaoDestino.setData(contaAPagarOrig.getDataPagamento());
			movimentacaoDestino.setTipoOperacao(TipoOperacaoEnum.C);
			
			movimentacaoDao.addEntity(movimentacaoDestino);
			
			//(ContaAPagarAdiantamento)contaAPagarOrig).
			
		}else if (contaAPagarOrig instanceof ContaAPagarCompra){
			
		}
		
				
		
		result.include("contas",contaDao.obterContasFinanceiras());
		
		return contaAPagar;
	}
	
	

}
