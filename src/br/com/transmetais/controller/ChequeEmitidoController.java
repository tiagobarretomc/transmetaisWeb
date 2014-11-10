package br.com.transmetais.controller;

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
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.MovimentacaoAdiantamento;
import br.com.transmetais.dao.AdiantamentoDAO;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.SituacaoAdiantamentoEnum;
import br.com.transmetais.type.SituacaoChequeEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Resource
@Path("/chequeEmitido")
public class ChequeEmitidoController extends BaseController<ChequeEmitido, ChequeEmitidoDAO>{
	
	private ContaDAO contaDao;
	private MovimentacaoDAO movimentacaoDao;
	private AdiantamentoDAO adiantamentoDao;

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
		
		Date dataCompensacao = bean.getDataCompensacao();
		
		bean = dao.findById(bean.getId());
		
		bean.setDataCompensacao(dataCompensacao);
		
		bean.setStatus(SituacaoChequeEnum.C);
		
		dao.updateEntity(bean);
		
		if(bean instanceof ChequeEmitidoAdiantamento){
			Adiantamento adiantamento = ((ChequeEmitidoAdiantamento)bean).getAdiantamento();
			
			//Criar a Movimentacao origem do recurso a ser transferido para a conta do fornecedor
			MovimentacaoAdiantamento movimentacaoOrigem = new MovimentacaoAdiantamento();
			movimentacaoOrigem.setAdiantamento(adiantamento);
			movimentacaoOrigem.setConta(adiantamento.getConta());
			movimentacaoOrigem.setData(bean.getDataCompensacao());
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
			movimentacaoDestino.setData(bean.getDataCompensacao());
			movimentacaoDestino.setTipoOperacao(TipoOperacaoEnum.C);
			movimentacaoDestino.setValor(adiantamento.getValor());
			movimentacaoDao.addEntity(movimentacaoDestino);
			
			//Atualizando saldo da conta destino
			Conta contaDestino = adiantamento.getFornecedor().getConta();
			contaDestino.setSaldo(contaDestino.getSaldo().add(adiantamento.getValor()));
			contaDao.updateEntity(contaDestino);
		}
		
		
		result.include("mensagem", "Confirmação da compensação do cheque efetuado com sucesso!");
		result.forwardTo(this.getClass()).lista(null,null,null);
		
	  }
	
	public void confirmarCancelamento(ChequeEmitido bean) throws DAOException {
		
		Date dataCompensacao = bean.getDataCompensacao();
		
		String motivo = bean.getMotivoCancelamento();
		
		bean = dao.findById(bean.getId());
		
		bean.setDataCompensacao(dataCompensacao);
		
		bean.setStatus(SituacaoChequeEnum.K);
		
		bean.setMotivoCancelamento(motivo);
		
		dao.updateEntity(bean);
		
		if(bean instanceof ChequeEmitidoAdiantamento){
			Adiantamento adiantamento = ((ChequeEmitidoAdiantamento)bean).getAdiantamento();
			
			adiantamento.setSituacao(SituacaoAdiantamentoEnum.C);
			adiantamentoDao.updateEntity(adiantamento);
			
		}
		
		result.include("mensagem", "Cancelamento do Cheque efetuado com sucesso!");
		result.forwardTo(this.getClass()).lista(null,null,null);
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

}
