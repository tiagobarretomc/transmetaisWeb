package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.transmetais.bean.CentroAplicacao;
import br.com.transmetais.bean.ChequeEmitidoDespesa;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.MovimentacaoDespesa;
import br.com.transmetais.bean.ParcelaDespesa;
import br.com.transmetais.dao.CentroAplicacaoDAO;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.filtros.DespesaFiltro;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.SituacaoChequeEnum;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoOperacaoEnum;
import br.com.transmetais.type.TipoPagamentoEnum;
import br.com.transmetais.util.EntityUtil;

@Resource
@Path("/despesa")
public class DespesaController {
	
	private CentroAplicacaoDAO centroAplicacaoDAO;
	private ContaContabilDAO contaContabilDAO;
	private ContaAPagarDAO contaAPagarDAO;
	private ContaDAO contaDAO;
	private MovimentacaoDAO movimentacaoDAO;
	private ChequeEmitidoDAO chequeEmitidoDao;
	private FornecedorDAO fornecedorDao;
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
	
	protected void postPersistUpdate(Despesa bean, Result result) {
		
		if (bean.getModalidadePagamento() == FormaPagamentoEnum.C && bean.getChequeEmitido() != null){
			try{
				chequeEmitidoDao.addEntity(bean.getChequeEmitido());
			}catch(DAOException ex){
				ex.printStackTrace();
				result.include("erro", ex.getMessage());
			}
		}
		
		//Caso se trate de pagamento a Vista
		if (bean.getFormaPagamento() == TipoPagamentoEnum.V ){
			
			//Caso o pagamento for em cheque. o saldo só devera ser atualizado e a movimentacao gerada quando o cheque tiver a sua compensação confirmada.
			if(bean.getModalidadePagamento() != FormaPagamentoEnum.C){
				
				//Setando os dados da Movimentacao.
				MovimentacaoDespesa movimentacao = new MovimentacaoDespesa();
				movimentacao.setDespesa(bean);
				movimentacao.setData(bean.getDataCompetencia());
				movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
				movimentacao.setValor(bean.getValor());
				movimentacao.setConta(bean.getConta());
				
				
				try {
					
					//inserindo a movimentacao no vanco de dados
					movimentacaoDAO.addEntity(movimentacao);
					
					//Obter a conta que sera debitado o valor da despesa
					Conta conta = contaDAO.findById(bean.getConta().getId());
					
					//Atualizando o Saldo da conta subtrainda o valor da despesa
					conta.setSaldo(conta.getSaldo().subtract(bean.getValor()));
					
					//persistindo na base de dados
					contaDAO.updateEntity(conta);
					
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
			
		}
		// Caso a forma de pagamento for à prazo
		else{
			
			//Verificando se trata-se de uma compra a prazo parcelada.
			if(bean.getParcelas() != null && bean.getParcelas().size()>0){
				
				for (ParcelaDespesa parcela : bean.getParcelas()) {
					
					ContaAPagarDespesa contaApagar = new ContaAPagarDespesa();
					contaApagar.setParcela(parcela);
					if(bean.getModalidadePagamento() == FormaPagamentoEnum.C){
						contaApagar.setConta(bean.getConta());
					}else{
						
						contaApagar.setConta(null);
					}
					contaApagar.setDataPrevista(parcela.getDataVencimento());
					contaApagar.setDescricao("Parcela da Despesa - " +bean.getId().toString() + " - " + bean.getDescricao());
					contaApagar.setStatus(StatusMovimentacaoEnum.A);
					contaApagar.setValor(parcela.getValor());
					contaApagar.setDespesa(bean);
					//contaApagar.setModalidadePagamento(bean.getModalidadePagamento());
					
					try {
						contaAPagarDAO.addEntity(contaApagar);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			//Caso a compra for à prazo e não for parcelada.
			else{
				
				ContaAPagarDespesa conta = new ContaAPagarDespesa();
				conta.setDataPrevista(bean.getDataVencimento());
				conta.setStatus(StatusMovimentacaoEnum.A);
				conta.setValor(bean.getValor());
				conta.setDespesa(bean);
				conta.setDescricao("Despesa " + bean.getId() + " - " + bean.getDescricao());
				//Se nao há parcelas o campo fica null
				conta.setParcela(null);
				//conta.setModalidadePagamento(bean.getModalidadePagamento());
				
				try {
					contaAPagarDAO.addEntity(conta);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		
	}
	
	protected void initForm(Despesa bean) {
		
		List<ContaContabil> listaContas;
		List<CentroAplicacao> listaCentroAplicacao;
		List<Fornecedor> fornecedores;
		
		try {
			listaContas = contaContabilDAO.findAll();
			listaCentroAplicacao = centroAplicacaoDAO.findAll();
			fornecedores = fornecedorDao.findAll();
			
			result.include("contas", listaContas);
			result.include("centros", listaCentroAplicacao);
			result.include("fornecedores", fornecedores);
			
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	protected void prePersistUpdate(Despesa bean) throws DAOException {
		
		
		//Verificando se trata-se de uma alteracao;
		if(bean.getId()!= null && bean.getId() > 0){
			Despesa oldBean = dao.findById(bean.getId());
				
			
			
		}
		
		if(bean.getModalidadePagamento() == FormaPagamentoEnum.C && bean.getParcelas() == null){
			if(bean.getChequeEmitido() != null){
				ChequeEmitidoDespesa cheque = new ChequeEmitidoDespesa();
				cheque.setNumeroCheque(bean.getChequeEmitido().getNumeroCheque());
				cheque.setConta(bean.getConta());
				cheque.setData(bean.getDataCompetencia());
				cheque.setDespesa(bean);
				cheque.setValor(bean.getValor());
				cheque.setStatus(SituacaoChequeEnum.A);
				cheque.setDataStatus(new Date());
				bean.setChequeEmitido(cheque);
				
				//chequeEmitidoDao.addEntity(cheque);
				
			}
		}else if(bean.getModalidadePagamento() == FormaPagamentoEnum.C && bean.getParcelas() != null){
			bean.setChequeEmitidoList(null);
		}
		
		
		bean.setStatus(StatusDespesaEnum.A);
		if(bean.getParcelas()!=null){
			Collections.sort(bean.getParcelas(), new Comparator<ParcelaDespesa>() {
				  public int compare(ParcelaDespesa o1, ParcelaDespesa o2) {
				      return o1.getDataVencimento().compareTo(o2.getDataVencimento());
				  }
			});
		}
		int numero =1;
		
		if (bean.getParcelas() != null){
			ChequeEmitidoDespesa cheque = null;
			//Atualizando a despesa de cada parcela 
			for (ParcelaDespesa parcela : bean.getParcelas()) {
				parcela.setDespesa(bean);
				parcela.setStatus(StatusDespesaEnum.A);
				parcela.setNumero(numero);
				numero++;
				
				
				if(bean.getModalidadePagamento() == FormaPagamentoEnum.C && bean.getParcelas() != null){
					cheque = new ChequeEmitidoDespesa();
					cheque.setNumeroCheque(parcela.getChequeEmitido().getNumeroCheque());
					cheque.setValor(parcela.getChequeEmitido().getValor());
					cheque.setConta(bean.getConta());
					cheque.setData(parcela.getDataVencimento());
					cheque.setDespesa(bean);
					cheque.setValor(parcela.getValor());
					cheque.setStatus(SituacaoChequeEnum.A);
					cheque.setParcela(parcela);
					cheque.setDataStatus(new Date());
					parcela.setChequeEmitido(cheque);
					
				}
			}
			
		}
		
		//Quando se tratar de despesa/compra a vista a data de vencimento é a mesma data da competencia
		if (bean.getFormaPagamento() == TipoPagamentoEnum.V){
			bean.setDataVencimento(bean.getDataCompetencia());
		}
		
		
		
		
		
	}
	
	
//	public List<Despesa> lista(){
//		List<Despesa> lista = null;
//		
//		try {
//			lista = dao.findAll();
//			DespesaFiltro filter = new DespesaFiltro();
//			initFilter(filter);
//			result.include("filter",filter);
//			result.include("beanList",lista);
//			List<Fornecedor> fornecedores = fornecedorDao.findAll();
//			result.include("fornecedores",fornecedores);
//			result.include("statusList",StatusDespesaEnum.values());
//			
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return lista;
//	}
	
	@Path({"/lista","/lista/"})
	public List<Despesa> lista(DespesaFiltro filter) throws DAOException{
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
		List<Fornecedor> fornecedores = fornecedorDao.findAll();
		result.include("fornecedores",fornecedores);
		result.include("statusList",StatusDespesaEnum.values());
		
		return lista;
		
	}
	protected void initFilter(DespesaFiltro filter){
		
		Calendar data = new GregorianCalendar();  
	       
	     
		if (filter.getDataFim() == null){
			
			 int ultimo_dia_mes = data.getActualMaximum(Calendar.DAY_OF_MONTH);  
			 data.set(Calendar.DAY_OF_MONTH, ultimo_dia_mes);  
			 
			 filter.setDataFim(data.getTime());
		}
	     
		if(filter.getDataInicio() == null){
			
			int primeiro_dia_mes = data.getActualMinimum(Calendar.DAY_OF_MONTH);
			data.set(Calendar.DAY_OF_MONTH, primeiro_dia_mes);
			 
			
			filter.setDataInicio(data.getTime());
		}
		
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
	
	@Autowired
	public void setContaDAO(ContaDAO contaDAO){
		this.contaDAO = contaDAO;
	}
	
	
	@Autowired
	public void setMovimentacaoDAO(MovimentacaoDAO movimentacaoDAO) {
		this.movimentacaoDAO = movimentacaoDAO;
	}
	
	@Autowired
	public void setChequeEmitidoDao(ChequeEmitidoDAO chequeEmitidoDao) {
		this.chequeEmitidoDao = chequeEmitidoDao;
	}
	
	@Autowired
	public void setFornecedorDao(FornecedorDAO fornecedorDao) {
		this.fornecedorDao = fornecedorDao;
	}
	
	@Autowired
	public void setDao(DespesaDAO dao) {
		this.dao = dao;
	}
	
	
	public void loadModalidades(TipoPagamentoEnum tipoPagamento) throws Exception{
		
		
		Map<String, String> mapa = new HashMap<String, String>();
		if (tipoPagamento == TipoPagamentoEnum.V){
			mapa.put(FormaPagamentoEnum.D.getName(), FormaPagamentoEnum.D.getDescricao());
			mapa.put(FormaPagamentoEnum.C.getName(), FormaPagamentoEnum.C.getDescricao());
			mapa.put(FormaPagamentoEnum.T.getName(), FormaPagamentoEnum.T.getDescricao());
			mapa.put(FormaPagamentoEnum.B.getName(), FormaPagamentoEnum.B.getDescricao());
			
			
		}else{
			
			mapa.put(FormaPagamentoEnum.C.getName(), FormaPagamentoEnum.C.getDescricao());
			mapa.put(FormaPagamentoEnum.T.getName(), FormaPagamentoEnum.T.getDescricao());
			mapa.put(FormaPagamentoEnum.B.getName(), FormaPagamentoEnum.B.getDescricao());
		}
		
		result.use(json()).from(mapa).serialize();
		
		result.nothing();
		
	}
	
	
	public void loadContas(TipoPagamentoEnum tipoPagamento, FormaPagamentoEnum formaPagamento) throws Exception{
		
	
		List<Conta> lista = null;
		
		//Carregar as contas financeiras apenas quando for despesa paga a vista
		if (tipoPagamento == TipoPagamentoEnum.V){
			// se for pagamento em dinheiro carregar contas de fundo fixo
			if (formaPagamento == FormaPagamentoEnum.D){
				lista = (List<Conta>)contaDAO.obterContasFundoFixo();
				
			}
			else if(formaPagamento == FormaPagamentoEnum.B){
				lista = (List<Conta>)contaDAO.obterContasFinanceiras();
			}
			//se nao for pagamento em dinheiro carregar as contas bancárias
			else{
				
					
					lista = (List<Conta>)contaDAO.obterContasBancarias();
				
			}
		}else if(formaPagamento == FormaPagamentoEnum.C){
			lista = (List<Conta>)contaDAO.obterContasBancarias();
		}
		
		if (lista != null)
			result.use(json()).from(lista).serialize();
		
		result.nothing();
		
	}
	
	
	@Path("/remove/{id}")
	public void remove(Long id) throws DAOException {
		Despesa bean = dao.findById(id);
		String msg = null;
		if (bean != null){
			try{
				dao.removeEntity(bean);
				msg = "Registro excluído com sucesso." ;
				result.include("mensagem", msg);
			}catch(DAOException e){
				result.include("erro", e.getMessage());

			}
		}
		result.forwardTo(this.getClass()).lista(new DespesaFiltro());
	  }

	public void form(Despesa bean){
		initForm(bean);
		result.include("bean",bean);
	}
	
	@Path({"/{id}","/form","/novo"})
	public Despesa form(Long id) throws DAOException{
		Despesa bean = null;
		
		
		if (id != null){
			
			bean = dao.findById(id);
			
		}else{
			bean = createInstance();
		}
		
		initForm(bean);
		
		result.include("bean",bean);
		
		return bean;
	}
	@SuppressWarnings("unchecked")
	@Post
	public void add(Despesa bean) throws DAOException {
		boolean isSucesso = false;
		prePersistUpdate(bean);
	 	validateForm(bean);
		Long id = (Long)EntityUtil.getId(bean.getClass(), bean);
		String msg = null;
		if (id != null && id > 0){
			if(update(bean)){
				msg = "Registro alterado com sucesso." ;
				isSucesso = true;
			} 
		}else{
			if(persist(bean)){
				msg = "Registro incluído com sucesso." ;
				isSucesso = true;
			}
		}
		if(isSucesso){
			postPersistUpdate(bean, result);
			result.include("mensagem", msg);
			result.forwardTo(this.getClass()).lista(new DespesaFiltro());
		}else{
			result.include("erro","Erro ao incluir registro.");
			result.forwardTo(this.getClass()).form(bean);;
		}
	  }
	
	
	public boolean persist(Despesa bean){
		try {
			dao.addEntity(bean);
			return true;
		} catch (DAOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public boolean update(Despesa bean){
		try {
			dao.updateEntity(bean);
			return true;
		} catch (Exception e) {
			result.include("erro", "Erro ao alterar registro.");
			e.printStackTrace();
			return false;
		}
	}
	
	protected void validateForm(Despesa bean){
		validator.validate(bean);
		validator.onErrorUsePageOf(this.getClass()).form(bean);
	}

}
