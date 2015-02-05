package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.CentroAplicacao;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAReceberVenda;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.ParcelaVenda;
import br.com.transmetais.bean.Venda;
import br.com.transmetais.dao.CentroAplicacaoDAO;
import br.com.transmetais.dao.ContaAReceberDAO;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.VendaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoPagamentoEnum;

@Resource
@Path("/venda")
public class VendaController extends BaseController<Venda,VendaDAO>{
	
	private CentroAplicacaoDAO centroAplicacaoDAO;
	private ContaContabilDAO contaContabilDAO;
	private ContaAReceberDAO contaAReceberDAO;
	private ContaDAO contaDAO;
	private MovimentacaoDAO movimentacaoDAO;
	
	
	@Override
	protected Venda createInstance() {
		return new Venda();
	}
	@Override
	protected void postPersistUpdate(Venda bean, Result result) {
		
		
		
		//Caso se trate de pagamento a Vista
		if (bean.getFormaPagamento() == TipoPagamentoEnum.V ){
			
			//TODO
			
			
			
			
		}
		// Caso a forma de pagamento for à prazo
		else{
			
			//Verificando se trata-se de uma compra a prazo parcelada.
			if(bean.getParcelas() != null && bean.getParcelas().size()>0){
				
				for (ParcelaVenda parcela : bean.getParcelas()) {
					
					ContaAReceberVenda contaAReceber = new ContaAReceberVenda();
					contaAReceber.setParcela(parcela);
					if(bean.getModalidadePagamento() == FormaPagamentoEnum.C){
						contaAReceber.setConta(bean.getConta());
					}else{
						
						contaAReceber.setConta(null);
					}
					contaAReceber.setDataPrevista(parcela.getDataVencimento());
					contaAReceber.setDescricao("Parcela da Venda - " +bean.getId().toString() + " - " + bean.getCliente().getRazaoSocial());
					contaAReceber.setStatus(StatusMovimentacaoEnum.A);
					contaAReceber.setValor(parcela.getValor());
					contaAReceber.setVenda(bean);
					//contaApagar.setModalidadePagamento(bean.getModalidadePagamento());
					
					try {
						contaAReceberDAO.addEntity(contaAReceber);
					} catch (DAOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
			//Caso a compra for à prazo e não for parcelada.
			else{
				
				ContaAReceberVenda conta = new ContaAReceberVenda();
				conta.setDataPrevista(bean.getDataRecebimento());
				conta.setStatus(StatusMovimentacaoEnum.A);
				conta.setValor(bean.getValor());
				conta.setVenda(bean);
				conta.setDescricao("Despesa " + bean.getId() + " - " + bean.getCliente().getRazaoSocial());
				//Se nao há parcelas o campo fica null
				conta.setParcela(null);
				//conta.setModalidadePagamento(bean.getModalidadePagamento());
				
				try {
					contaAReceberDAO.addEntity(conta);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		
		
		
	}
	@Override
	protected void initForm(Venda bean) {
		
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
	protected void prePersistUpdate(Venda bean) {
		
		
		bean.setStatus(StatusCompraEnum.A);
		if(bean.getParcelas()!=null){
			Collections.sort(bean.getParcelas(), new Comparator<ParcelaVenda>() {
				  public int compare(ParcelaVenda o1, ParcelaVenda o2) {
				      return o1.getDataVencimento().compareTo(o2.getDataVencimento());
				  }
			});
		}
		int numero =1;
		
		if (bean.getParcelas() != null){
			
			//Atualizando a despesa de cada parcela 
			for (ParcelaVenda parcela : bean.getParcelas()) {
				parcela.setVenda(bean);
				parcela.setStatus(StatusDespesaEnum.A);
				parcela.setNumero(numero);
				numero++;
				
			}
			
		}
		
		//Quando se tratar de despesa/compra a vista a data de vencimento é a mesma data da competencia
		if (bean.getFormaPagamento() == TipoPagamentoEnum.V){
			bean.setDataRecebimento(bean.getData());
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
	public ContaAReceberDAO getContaAReceberDAO() {
		return contaAReceberDAO;
	}
	
	@Autowired
	public void setContaDAO(ContaDAO contaDAO){
		this.contaDAO = contaDAO;
	}
	
	
	@Autowired
	public void setMovimentacaoDAO(MovimentacaoDAO movimentacaoDAO) {
		this.movimentacaoDAO = movimentacaoDAO;
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
	
	
	

}
