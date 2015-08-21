package br.com.transmetais.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.transmetais.bean.CentroAplicacao;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.filtros.DespesaFiltro;
import br.com.transmetais.type.StatusDespesaEnum;

@Resource
public class ConsultaController {
	
	
	protected  Result result;
	protected  DespesaDAO dao;
	protected Validator validator;
	
	
	@Autowired
	public void setResult(Result result) {
		this.result = result;
	}

	@Autowired
	public void setValidator(Validator validator) {
		this.validator = validator;
	}

	
	protected Despesa createInstance() {
		return new Despesa();
	}
	
	
	
	protected void initForm(Despesa bean) {
		
		List<ContaContabil> listaContas;
		List<CentroAplicacao> listaCentroAplicacao;
		List<Fornecedor> fornecedores;
		
		
//		try {
////			listaContas = contaContabilDAO.findAll();
////			listaCentroAplicacao = centroAplicacaoDAO.findAll();
////			fornecedores = fornecedorDao.findAll();
//			
////			result.include("contas", listaContas);
////			result.include("centros", listaCentroAplicacao);
////			result.include("fornecedores", fornecedores);
//			result.include("modalidades", FormaPagamentoEnum.values());
//			
//			
//			if(bean != null && bean.getId()!=null){
//				
//				
//				//List<Conta> lista = contaDAO.obterContasFinanceiras();
//				
//				
//				
//				//result.include("contasFinanceiras",lista);
//				
//				
//			}
//			
//			
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
	
	
	

	
	@Path({"/consulta/despesa","/consulta/despesa/"})
	public List<Despesa> despesa(DespesaFiltro filter) throws DAOException{
		List<Despesa> lista = null;
		
		
		initFilter(filter);
		lista = dao.findByFilter(filter);
		BigDecimal valorTotal = BigDecimal.ZERO;
		for (Despesa despesa : lista) {
			valorTotal = valorTotal.add(despesa.getValor());
		}
		
		result.include("beanList",lista);
		
		result.include("filter",filter);
		result.include("beanList",lista);
		result.include("valorTotal",valorTotal);
		//List<Fornecedor> fornecedores = fornecedorDao.findAll();
		//result.include("fornecedores",fornecedores);
		result.include("statusList",StatusDespesaEnum.values());
		
		return lista;
		
	}
	protected void initFilter(DespesaFiltro filter){
		
		Calendar data = new GregorianCalendar();  
	       
	     
		if (filter.getDataFim() == null){
			
			 int ultimo_dia_mes = data.getActualMaximum(Calendar.DAY_OF_MONTH);  
			 data.set(Calendar.DAY_OF_MONTH, ultimo_dia_mes);  
			 data.set(Calendar.HOUR_OF_DAY, 0);
			 data.set(Calendar.MINUTE, 0);
			 data.set(Calendar.SECOND, 0);
			 data.set(Calendar.MILLISECOND, 0);
			 filter.setDataFim(data.getTime());
		}
	     
		if(filter.getDataInicio() == null){
			
			int primeiro_dia_mes = data.getActualMinimum(Calendar.DAY_OF_MONTH);
			data.set(Calendar.DAY_OF_MONTH, primeiro_dia_mes);
			data.set(Calendar.HOUR_OF_DAY, 0);
			 data.set(Calendar.MINUTE, 0);
			 data.set(Calendar.SECOND, 0);
			 data.set(Calendar.MILLISECOND, 0);
			
			filter.setDataInicio(data.getTime());
		}
		
	}
	
//	@Autowired 
//	public void setCentroAplicacaoDAO(CentroAplicacaoDAO centroAplicacaoDAO) {
//		this.centroAplicacaoDAO = centroAplicacaoDAO;
//	}
//	
//	@Autowired 
//	public void setContaContabilDAO(ContaContabilDAO contaContabilDAO) {
//		this.contaContabilDAO = contaContabilDAO;
//	}
//	
	
	
	
	
	
	@Autowired
	public void setDao(DespesaDAO despesaDao) {
		this.dao = despesaDao;
	}
	
	
	
	
	protected void validateForm(Despesa bean){
		validator.validate(bean);
		//validator.onErrorUsePageOf(this.getClass()).form(bean);
	}

}
