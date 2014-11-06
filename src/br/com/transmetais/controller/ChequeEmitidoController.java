package br.com.transmetais.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.ChequeEmitido;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.SituacaoChequeEnum;

@Resource
@Path("/chequeEmitido")
public class ChequeEmitidoController extends BaseController<ChequeEmitido, ChequeEmitidoDAO>{
	
	private ContaDAO contaDao;

	@Override
	protected ChequeEmitido createInstance() {
		return new ChequeEmitido();
	}
	
	
	@Override
	public List<ChequeEmitido> lista(){
		return this.lista(null, null, null);
	}
	
	@Path({"/lista","/lista/"})
	public List<ChequeEmitido> lista(ChequeEmitido cheque, Date dataInicio, Date dataFim){
		List<ChequeEmitido> lista = null;
		
		try {
			
			Calendar data = new GregorianCalendar();  
		       
		     
			if (dataFim == null){
				
				 int ultimo_dia_mes = data.getActualMaximum(Calendar.DAY_OF_MONTH);  
				 data.set(Calendar.DAY_OF_MONTH, ultimo_dia_mes);  
				 
				 dataFim = data.getTime();
			}
		     
			if(dataInicio == null){
				
				int primeiro_dia_mes = data.getActualMinimum(Calendar.DAY_OF_MONTH);
				data.set(Calendar.DAY_OF_MONTH, primeiro_dia_mes);
				 
				dataInicio = data.getTime();
			}
			
			lista = dao.findByFilter(dataInicio, dataFim, cheque);
			
			result.include("dataInicio",dataInicio);
			result.include("dataFim",dataFim);
			result.include("cheque",cheque);
			
			List<Conta> contas = contaDao.obterContasBancarias();
			result.include("contas", contas);
			
			result.include("situacoes", SituacaoChequeEnum.values());
			
			
			result.include("beanList",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
//	public List<ChequeEmitido> loadLista(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisSelecionados, List<StatusCompraEnum> statusCompas){
//		List<Compra> lista = null;
//		
//		try {
//			
//			
//			lista = dao.findByFilter(fornecedorId, dataInicio, dataFim, tiposFretes, materiaisSelecionados, statusCompas);
//			BigDecimal valorTotal = new BigDecimal(0);
//			BigDecimal quantidade = new BigDecimal(0);
//			for (Compra compra : lista) {
//				for(ItemCompra item : compra.getItens()){
//					valorTotal = valorTotal.add( item.getValor());
//					quantidade = quantidade.add( item.getQuantidade());
//					
//				}
//			}
//			
//			
//			BigDecimal precoMedio =  new BigDecimal(0);
//			
//			if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
//				
//				precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
//			}
//			
//			result.include("valorTotal", valorTotal);
//			result.include("quantidade", quantidade);
//			result.include("precoMedio", precoMedio);
//			
//			result.include("compras",lista);
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return lista;
//	}
	
	@Override
	protected void postPersistUpdate(ChequeEmitido bean, Result result) {
		
		
		
	}
	@Override
	protected void initForm(ChequeEmitido bean) {
		
		
		
		
		
	}
	@Override
	protected void prePersistUpdate(ChequeEmitido bean) {
		
			
	}
	
	@Autowired
	public void setContaDao(ContaDAO contaDao) {
		this.contaDao = contaDao;
	}

}
