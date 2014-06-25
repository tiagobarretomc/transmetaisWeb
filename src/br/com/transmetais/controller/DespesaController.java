package br.com.transmetais.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.CentroAplicacao;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.dao.CentroAplicacaoDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;

@Resource
@Path("/despesa")
public class DespesaController extends BaseController<Despesa,DespesaDAO>{
	
	private CentroAplicacaoDAO centroAplicacaoDAO;
	private ContaContabilDAO contaContabilDAO;
	private ContaAPagarDAO contaAPagarDAO;
	
	
	@Override
	protected Despesa createInstance() {
		return new Despesa();
	}
	@Override
	protected void postPersistUpdate(Despesa bean, Result result) {
		ContaAPagarDespesa conta = new ContaAPagarDespesa();
		conta.setDataPrevista(bean.getDataVencimento());
		conta.setStatus(StatusMovimentacaoEnum.A);
		conta.setValor(bean.getValor());
		conta.setDespesa(bean);
		conta.setDescricao("Despesa " + bean.getId());
		
		try {
			contaAPagarDAO.addEntity(conta);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void initForm(Despesa bean) {
		
		List<ContaContabil> listaContas;
		List<CentroAplicacao> listaCentroAplicacao;
		
		try {
			listaContas = contaContabilDAO.findAll();
			listaCentroAplicacao = centroAplicacaoDAO.findAll();
			
			result.include("contas", listaContas);
			result.include("centros", listaCentroAplicacao);
			
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	protected void prePersistUpdate(Despesa bean) {
		
		bean.setStatus(StatusDespesaEnum.A);
		
	}
	
	@Autowired 
	public void setCentroAplicacaoDAO(CentroAplicacaoDAO centroAplicacaoDAO) {
		this.centroAplicacaoDAO = centroAplicacaoDAO;
	}
	
	@Autowired 
	public void setContaContabilDAO(ContaContabilDAO contaContabilDAO) {
		this.contaContabilDAO = contaContabilDAO;
	}
	
	
	@Autowired 
	public void setContaAPagarDAO(ContaAPagarDAO contaAPagarDAO) {
		this.contaAPagarDAO = contaAPagarDAO;
	}
	
	
	
	
	

}
