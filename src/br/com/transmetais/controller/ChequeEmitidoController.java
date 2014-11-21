package br.com.transmetais.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Adiantamento;
import br.com.transmetais.bean.ChequeEmitido;
import br.com.transmetais.bean.ChequeEmitidoAdiantamento;
import br.com.transmetais.bean.ChequeEmitidoCompra;
import br.com.transmetais.bean.ChequeEmitidoDespesa;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.MovimentacaoAdiantamento;
import br.com.transmetais.bean.MovimentacaoCompra;
import br.com.transmetais.bean.MovimentacaoContasAPagar;
import br.com.transmetais.bean.MovimentacaoDespesa;
import br.com.transmetais.bean.ParcelaCompra;
import br.com.transmetais.bean.ParcelaDespesa;
import br.com.transmetais.dao.AdiantamentoDAO;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.ParcelaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.SituacaoAdiantamentoEnum;
import br.com.transmetais.type.SituacaoChequeEnum;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoOperacaoEnum;
import br.com.transmetais.type.TipoPagamentoEnum;

@Resource
@Path("/chequeEmitido")
public class ChequeEmitidoController extends BaseController<ChequeEmitido, ChequeEmitidoDAO>{
	
	private ContaDAO contaDao;
	private MovimentacaoDAO movimentacaoDao;
	private AdiantamentoDAO adiantamentoDao;
	private ParcelaDAO parcelaDao;
	private ContaAPagarDAO contaAPagarDao;
	private DespesaDAO despesaDao;
	private CompraDAO compraDao;
	

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
	
	@Path({"/cancelar/{id}","/cancelar/{id}/"})
	public ChequeEmitido cancelar(Long id) throws DAOException{
		ChequeEmitido bean = null;
		
		
		if (id != null){
			
			bean = dao.findById(id);
			
		}else{
			bean = createInstance();
		}
		
		initForm(bean);
		
		result.include("bean",bean);
		
		return bean;
	}
	
	@Path({"/detalhar/{id}","/detalhar/{id}/"})
	public ChequeEmitido detalhar(Long id) throws DAOException{
		ChequeEmitido bean = null;
		
		
		if (id != null){
			
			bean = dao.findById(id);
			
		}else{
			bean = createInstance();
		}
		
		initForm(bean);
		
		result.include("bean",bean);
		
		return bean;
	}
	
	public void aprovar(ChequeEmitido bean) throws DAOException {
		
		Date dataCompensacao = bean.getDataStatus();
		
		bean = dao.findById(bean.getId());
		
		bean.setDataStatus(dataCompensacao);
		
		bean.setStatus(SituacaoChequeEnum.C);
		
		dao.updateEntity(bean);
		
		if (bean.getParcela() != null){
			bean.getParcela().setStatus(StatusDespesaEnum.P);
			bean.getParcela().setDataPagamento(bean.getDataStatus());
			parcelaDao.updateEntity(bean.getParcela());
		}
		
		if(bean instanceof ChequeEmitidoAdiantamento){
			Adiantamento adiantamento = ((ChequeEmitidoAdiantamento)bean).getAdiantamento();
			
			//Criar a Movimentacao origem do recurso a ser transferido para a conta do fornecedor
			MovimentacaoAdiantamento movimentacaoOrigem = new MovimentacaoAdiantamento();
			movimentacaoOrigem.setAdiantamento(adiantamento);
			movimentacaoOrigem.setConta(adiantamento.getConta());
			movimentacaoOrigem.setData(bean.getDataStatus());
			movimentacaoOrigem.setTipoOperacao(TipoOperacaoEnum.D);
			movimentacaoOrigem.setValor(adiantamento.getValor());
			
			movimentacaoDao.addEntity(movimentacaoOrigem);
			
			//Atualizando saldo da conta origem
			Conta contaOrigem = adiantamento.getConta();
			contaOrigem.setSaldo(contaOrigem.getSaldo().subtract(adiantamento.getValor()));
			contaDao.updateEntity(contaOrigem);
			
			//Criar a Movimentacao de Destivo na Conta do Fornecedor
			MovimentacaoAdiantamento movimentacaoDestino = new MovimentacaoAdiantamento();
			movimentacaoDestino.setAdiantamento(adiantamento);
			movimentacaoDestino.setConta(adiantamento.getFornecedor().getConta());
			movimentacaoDestino.setData(bean.getDataStatus());
			movimentacaoDestino.setTipoOperacao(TipoOperacaoEnum.C);
			movimentacaoDestino.setValor(adiantamento.getValor());
			movimentacaoDao.addEntity(movimentacaoDestino);
			
			//Atualizando saldo da conta destino
			Conta contaDestino = adiantamento.getFornecedor().getConta();
			contaDestino.setSaldo(contaDestino.getSaldo().add(adiantamento.getValor()));
			contaDao.updateEntity(contaDestino);
		}
		else if(bean instanceof ChequeEmitidoDespesa) {
			
			ChequeEmitidoDespesa chequeEmitidoDespesa = (ChequeEmitidoDespesa)bean;
			
			
			
			// Se o pagamento for a prazo será necessário encerrar tb a conta a pagar
			if (chequeEmitidoDespesa.getDespesa().getFormaPagamento() == TipoPagamentoEnum.P){
				
				ContaAPagarDespesa contaAPagarDespesa = contaAPagarDao.obterPorDespesa(chequeEmitidoDespesa.getDespesa(), chequeEmitidoDespesa.getParcela());
				
				
				contaAPagarDespesa.setConta(bean.getConta());
				contaAPagarDespesa.setDataPagamento(chequeEmitidoDespesa.getDataStatus());
				contaAPagarDespesa.setStatus(StatusMovimentacaoEnum.P);
				contaAPagarDespesa.setMulta(new BigDecimal(0));
				contaAPagarDespesa.setJuros(new BigDecimal(0));
				contaAPagarDespesa.setValorTotal(contaAPagarDespesa.getValor());
				contaAPagarDao.updateEntity(contaAPagarDespesa);
				
				MovimentacaoContasAPagar movimentacao = new MovimentacaoContasAPagar();
				movimentacao.setContaAPagar(contaAPagarDespesa);
				movimentacao.setConta(contaAPagarDespesa.getConta());
				movimentacao.setValor(contaAPagarDespesa.getValor());
				movimentacao.setData(contaAPagarDespesa.getDataPagamento());
				movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
				
				
				movimentacaoDao.addEntity(movimentacao);
				
				
				if (bean.getParcela() != null){
					
					
					//verificar se as demais parcelas
					
					boolean despesaQuitada = true;
					for (ParcelaDespesa parcela : contaAPagarDespesa.getDespesa().getParcelas()) {
						
						if(parcela.getStatus() != StatusDespesaEnum.P){
							despesaQuitada = false;
							break;
						}
						
					}
					
					//Se todas as parcelas da despesa estiverem quitadas, entao devemos mudar o estado da despesa.
					if (despesaQuitada){
						
						contaAPagarDespesa.getDespesa().setStatus(StatusDespesaEnum.P);
						contaAPagarDespesa.getDespesa().setDataPagamento(contaAPagarDespesa.getDataPagamento());
						despesaDao.updateEntity(contaAPagarDespesa.getDespesa());
					}
				
				}else{
					chequeEmitidoDespesa.getDespesa().setDataPagamento(chequeEmitidoDespesa.getDataStatus());
					chequeEmitidoDespesa.getDespesa().setStatus(StatusDespesaEnum.P);
					despesaDao.updateEntity(chequeEmitidoDespesa.getDespesa());
				}
				
			}else{
				MovimentacaoDespesa movimentacaDespesa= new MovimentacaoDespesa();
				movimentacaDespesa.setDespesa(chequeEmitidoDespesa.getDespesa());
				movimentacaDespesa.setConta(chequeEmitidoDespesa.getConta());
				
				movimentacaDespesa.setValor(chequeEmitidoDespesa.getValor());
				movimentacaDespesa.setData(chequeEmitidoDespesa.getDataStatus());
				movimentacaDespesa.setTipoOperacao(TipoOperacaoEnum.D);
				movimentacaoDao.addEntity(movimentacaDespesa);
				
				chequeEmitidoDespesa.getDespesa().setDataPagamento(chequeEmitidoDespesa.getDataStatus());
				chequeEmitidoDespesa.getDespesa().setStatus(StatusDespesaEnum.P);
				despesaDao.updateEntity(chequeEmitidoDespesa.getDespesa());
				
			}
				
			
			//Alterar o Saldo da Conta Sacada
			Conta contaSacada = contaDao.findById(bean.getConta().getId());
			contaSacada.setSaldo(contaSacada.getSaldo().subtract(bean.getValor()));
			contaDao.updateEntity(contaSacada);
			
			
			
		}
		else if(bean instanceof ChequeEmitidoCompra) {
			
			
			ChequeEmitidoCompra chequeEmitidoCompra = (ChequeEmitidoCompra)bean;
			
			
			
			// Se o pagamento for a prazo será necessário encerrar tb a conta a pagar
			if (chequeEmitidoCompra.getCompra().getFormaPagamento() == TipoPagamentoEnum.P){
				
				ContaAPagarCompra contaAPagarCompra = contaAPagarDao.obterPorCompra(chequeEmitidoCompra.getCompra(), chequeEmitidoCompra.getParcela());
				
				
				contaAPagarCompra.setConta(bean.getConta());
				contaAPagarCompra.setDataPagamento(bean.getDataStatus());
				contaAPagarCompra.setStatus(StatusMovimentacaoEnum.P);
				contaAPagarCompra.setMulta(new BigDecimal(0));
				contaAPagarCompra.setJuros(new BigDecimal(0));
				contaAPagarCompra.setValorTotal(contaAPagarCompra.getValor());
				contaAPagarDao.updateEntity(contaAPagarCompra);
				
				MovimentacaoContasAPagar movimentacao = new MovimentacaoContasAPagar();
				movimentacao.setContaAPagar(contaAPagarCompra);
				movimentacao.setConta(contaAPagarCompra.getConta());
				
				movimentacao.setValor(contaAPagarCompra.getValor());
				movimentacao.setData(contaAPagarCompra.getDataPagamento());
				movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
				
				
				movimentacaoDao.addEntity(movimentacao);
				
				
				if (bean.getParcela() != null){
					
					
					//verificar se as demais parcelas
					
					boolean despesaQuitada = true;
					for (ParcelaCompra parcela : contaAPagarCompra.getCompra().getParcelas()) {
						
						if(parcela.getStatus() != StatusDespesaEnum.P){
							despesaQuitada = false;
							break;
						}
						
					}
					
					//Se todas as parcelas da despesa estiverem quitadas, entao devemos mudar o estado da despesa.
					if (despesaQuitada){
						
						contaAPagarCompra.getCompra().setStatus(StatusCompraEnum.P);
						contaAPagarCompra.getCompra().setDataPagamento(contaAPagarCompra.getDataPagamento());
						compraDao.updateEntity(contaAPagarCompra.getCompra());
					}
				
				}else{
					chequeEmitidoCompra.getCompra().setDataPagamento(chequeEmitidoCompra.getDataStatus());
					chequeEmitidoCompra.getCompra().setStatus(StatusCompraEnum.P);
					compraDao.updateEntity(chequeEmitidoCompra.getCompra());
				}
				
			}else{
				MovimentacaoCompra movimentacaoCompra= new MovimentacaoCompra();
				movimentacaoCompra.setCompra(chequeEmitidoCompra.getCompra());
				movimentacaoCompra.setConta(chequeEmitidoCompra.getConta());
				
				movimentacaoCompra.setValor(chequeEmitidoCompra.getValor());
				movimentacaoCompra.setData(chequeEmitidoCompra.getDataStatus());
				movimentacaoCompra.setTipoOperacao(TipoOperacaoEnum.D);
				movimentacaoDao.addEntity(movimentacaoCompra);
				
				chequeEmitidoCompra.getCompra().setDataPagamento(chequeEmitidoCompra.getDataStatus());
				chequeEmitidoCompra.getCompra().setStatus(StatusCompraEnum.P);
				compraDao.updateEntity(chequeEmitidoCompra.getCompra());
				
			}
				
			
			//Alterar o Saldo da Conta Sacada
			Conta contaSacada = contaDao.findById(bean.getConta().getId());
			contaSacada.setSaldo(contaSacada.getSaldo().subtract(bean.getValor()));
			contaDao.updateEntity(contaSacada);
			
		}
		
		
		result.include("mensagem", "Confirmação da compensação do cheque efetuado com sucesso!");
		result.redirectTo(this.getClass()).lista(null,null,null);
		
	  }
	
	public void confirmarCancelamento(ChequeEmitido bean)  {
		
		Date dataCancelamento = bean.getDataStatus();
		
		String motivo = bean.getMotivoCancelamento();
		
		try{
			
			bean = dao.findById(bean.getId());
			
			bean.setDataStatus(dataCancelamento);
			
			bean.setStatus(SituacaoChequeEnum.K);
			
			bean.setMotivoCancelamento(motivo);
			
			dao.updateEntity(bean);
			
			if(bean instanceof ChequeEmitidoAdiantamento){
				Adiantamento adiantamento = ((ChequeEmitidoAdiantamento)bean).getAdiantamento();
				
				adiantamento.setSituacao(SituacaoAdiantamentoEnum.C);
				adiantamento.setDataCancelamento(new Date());
				adiantamentoDao.updateEntity(adiantamento);
				
			}
			else if(bean instanceof ChequeEmitidoDespesa){
				ChequeEmitidoDespesa chequeDespesa = (ChequeEmitidoDespesa)bean;
				
				//Caso seja uma despesa a vista
				if(chequeDespesa.getDespesa().getFormaPagamento() == TipoPagamentoEnum.V){
					chequeDespesa.getDespesa().setStatus(StatusDespesaEnum.C);
					chequeDespesa.getDespesa().setDataCancelamento(new Date());
					despesaDao.updateEntity(chequeDespesa.getDespesa());
				}
				//Despesa a prazo
				else{
					
					ContaAPagarDespesa contaAPagarDespesa = contaAPagarDao.obterPorDespesa(chequeDespesa.getDespesa(), chequeDespesa.getParcela());
					
					contaAPagarDespesa.setDataCancelamento(new Date());
					contaAPagarDespesa.setStatus(StatusMovimentacaoEnum.C);
					contaAPagarDao.updateEntity(contaAPagarDespesa);
					
					//Se for despesa parcelada
					if(chequeDespesa.getParcela() != null){
						chequeDespesa.getParcela().setStatus(StatusDespesaEnum.C);
						chequeDespesa.getParcela().setDataCancelamento(new Date());
						
						parcelaDao.updateEntity(chequeDespesa.getParcela());
					}
					//se for uma despesa a prazo nao parcelada
					else{
						
						chequeDespesa.getDespesa().setStatus(StatusDespesaEnum.C);
						chequeDespesa.getDespesa().setDataCancelamento(new Date());
						despesaDao.updateEntity(chequeDespesa.getDespesa());
						
					}
				}
			}
			else if(bean instanceof ChequeEmitidoCompra){
				
				ChequeEmitidoCompra chequeCompra = (ChequeEmitidoCompra)bean;
				
				//Caso seja uma compra a vista
				if(chequeCompra.getCompra().getFormaPagamento() == TipoPagamentoEnum.V){
					chequeCompra.getCompra().setStatus(StatusCompraEnum.C);
					chequeCompra.getCompra().setDataCancelamento(new Date());
					compraDao.updateEntity(chequeCompra.getCompra());
				}
				//compra a prazo
				else{
					
					ContaAPagarCompra contaAPagarCompra = contaAPagarDao.obterPorCompra(chequeCompra.getCompra(), chequeCompra.getParcela());
					
					contaAPagarCompra.setDataCancelamento(new Date());
					contaAPagarCompra.setStatus(StatusMovimentacaoEnum.C);
					contaAPagarDao.updateEntity(contaAPagarCompra);
					
					//Se for compra parcelada
					if(chequeCompra.getParcela() != null){
						chequeCompra.getParcela().setStatus(StatusDespesaEnum.C);
						chequeCompra.getParcela().setDataCancelamento(new Date());
						
						parcelaDao.updateEntity(chequeCompra.getParcela());
					}
					//se for uma compra a prazo nao parcelada
					else{
						
						chequeCompra.getCompra().setStatus(StatusCompraEnum.C);
						chequeCompra.getCompra().setDataCancelamento(new Date());
						compraDao.updateEntity(chequeCompra.getCompra());
						
					}
				}
				
			}
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
		
		result.include("mensagem", "Cancelamento do Cheque efetuado com sucesso!");
		result.redirectTo(this.getClass()).lista(null,null,null);
	}
	
		

	
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
	
	@Autowired
	public void setMovimentacaoDao(MovimentacaoDAO movimentacaoDao) {
		this.movimentacaoDao = movimentacaoDao;
	}
	
	@Autowired
	public void setAdiantamentoDao(AdiantamentoDAO adiantamentoDao) {
		this.adiantamentoDao = adiantamentoDao;
	}
	@Autowired
	public void setContaAPagarDao(ContaAPagarDAO contaAPagarDao) {
		this.contaAPagarDao = contaAPagarDao;
	}
	@Autowired
	public void setParcelaDao(ParcelaDAO parcelaDao) {
		this.parcelaDao = parcelaDao;
	}
	@Autowired
	public void setDespesaDao(DespesaDAO despesaDao) {
		this.despesaDao = despesaDao;
	}
	@Autowired
	public void setCompraDao(CompraDAO compraDao) {
		this.compraDao = compraDao;
	}
	

}
