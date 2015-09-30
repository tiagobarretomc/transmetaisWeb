package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.CentroAplicacao;
import br.com.transmetais.bean.ChequeEmitidoCompra;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.ComprovantePesagemEntrada;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.Email;
import br.com.transmetais.bean.Estoque;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.bean.ItemCompra;
import br.com.transmetais.bean.ItemPesagemEntrada;
import br.com.transmetais.bean.Material;
import br.com.transmetais.bean.MovimentacaoCompra;
import br.com.transmetais.bean.ParcelaCompra;
import br.com.transmetais.dao.CentroAplicacaoDAO;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.EstoqueDAO;
import br.com.transmetais.dao.FornecedorDAO;
import br.com.transmetais.dao.FornecedorMaterialDAO;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.CompraDaoImpl;
import br.com.transmetais.dao.impl.ComprovantePesagemEntradaDaoImpl;
import br.com.transmetais.mail.EmailService;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.SituacaoChequeEnum;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoFreteEnum;
import br.com.transmetais.type.TipoOperacaoEnum;
import br.com.transmetais.type.TipoPagamentoEnum;

@Resource
public class CompraController {
	
	private final Result result;
	private CompraDAO dao;
	private FornecedorDAO fornecedorDao;
	private FornecedorMaterialDAO fornecedorMaterialDao;
	private ContaAPagarDAO contaAPagarDAO;
	private EstoqueDAO estoqueDAO;
	private MaterialDAO materialDao;
	private ContaDAO contaDao;
	private ContaContabilDAO contaContabilDAO;
	private CentroAplicacaoDAO centroAplicacaoDAO;
	private ChequeEmitidoDAO chequeEmitidoDAO;
	private MovimentacaoDAO movimentacaoDAO;
	private ComprovantePesagemEntradaDaoImpl comprovantePesagemDAO;
	private EmailService emailService;
	
	public CompraController(Result result, CompraDAO compraDao, FornecedorDAO fornecedorDao, FornecedorMaterialDAO fornecedorMaterialDao, ContaAPagarDAO contaAPagarDAO, 
			ContaDAO contaDao, MaterialDAO materialDao, EstoqueDAO estoqueDAO,ContaContabilDAO contaContabilDAO, CentroAplicacaoDAO centroAplicacaoDAO,
			ChequeEmitidoDAO chequeEmitidoDAO, MovimentacaoDAO movimentacaoDAO, ComprovantePesagemEntradaDaoImpl comprovantePesagemDAO, EmailService emailService) {
		this.dao = compraDao;
		this.fornecedorDao = fornecedorDao;
		this.fornecedorMaterialDao = fornecedorMaterialDao;
		this.contaAPagarDAO = contaAPagarDAO;
		this.result = result;
		this.materialDao = materialDao;
		this.estoqueDAO = estoqueDAO;
		this.contaDao = contaDao;
		this.contaContabilDAO = contaContabilDAO;
		this.centroAplicacaoDAO = centroAplicacaoDAO;
		this.chequeEmitidoDAO = chequeEmitidoDAO;
		this.movimentacaoDAO = movimentacaoDAO;
		this.comprovantePesagemDAO = comprovantePesagemDAO;
		this.emailService = emailService;
	}
	
	//tela de listagem de compras
	@Path({"/compra/","/compra","/compra/lista"})
	public List<Compra> lista(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisSelecionados, List<StatusCompraEnum> statusCompas){
		List<Compra> lista = null;
		
		Calendar data = new GregorianCalendar();  
	       
	     
		if (dataFim == null){
			
			 int ultimo_dia_mes = data.getActualMaximum(Calendar.DAY_OF_MONTH);  
			 data.set(Calendar.DAY_OF_MONTH, ultimo_dia_mes);  
			 data.set(Calendar.HOUR_OF_DAY, 0);
			 data.set(Calendar.MINUTE, 0);
			 data.set(Calendar.SECOND, 0);
			 data.set(Calendar.MILLISECOND, 0);
			 dataFim = data.getTime();
			 
		}
	     
		if(dataInicio == null){
			
			int primeiro_dia_mes = data.getActualMinimum(Calendar.DAY_OF_MONTH);
			data.set(Calendar.DAY_OF_MONTH, primeiro_dia_mes);
			 data.set(Calendar.HOUR_OF_DAY, 0);
			 data.set(Calendar.MINUTE, 0);
			 data.set(Calendar.SECOND, 0);
			 data.set(Calendar.MILLISECOND, 0); 
			
			dataInicio = data.getTime();
		}
		
		try {
			List<Fornecedor> fornecedores = fornecedorDao.findAll();
			List<Material> materiais = materialDao.findAll();
			
			lista = dao.findByFilter(fornecedorId, dataInicio, dataFim, tiposFretes, materiaisSelecionados, statusCompas);
			BigDecimal valorTotal = new BigDecimal(0);
			BigDecimal quantidade = new BigDecimal(0);
			for (Compra compra : lista) {
				for(ItemCompra item : compra.getItens()){
					valorTotal = valorTotal.add( item.getValor());
					quantidade = quantidade.add( item.getQuantidade());
					
				}
			}
			
			BigDecimal precoMedio =  new BigDecimal(0);
			
			if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
				
				precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
			}
			
			
			result.include("fornecedores", fornecedores);
			result.include("materiais", materiais);
			result.include("tiposFrete",TipoFreteEnum.values());
			result.include("statusList",StatusCompraEnum.values());
			
			result.include("valorTotal", valorTotal);
			result.include("quantidade", quantidade);
			result.include("precoMedio", precoMedio);
			
			result.include("dataInicio",dataInicio);
			result.include("dataFim",dataFim);
			
			
			result.include("compras",lista);
			
			//result.forwardTo("/jsp/compra/lista.jsp");
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
		public List<Compra> loadListaCompra(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisSelecionados, List<StatusCompraEnum> statusCompas){
			List<Compra> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(fornecedorId, dataInicio, dataFim, tiposFretes, materiaisSelecionados, statusCompas);
				BigDecimal valorTotal = new BigDecimal(0);
				BigDecimal quantidade = new BigDecimal(0);
				for (Compra compra : lista) {
					for(ItemCompra item : compra.getItens()){
						valorTotal = valorTotal.add( item.getValor());
						quantidade = quantidade.add( item.getQuantidade());
						
					}
				}
				
				
				BigDecimal precoMedio =  new BigDecimal(0);
				
				if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
					
					precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
				}
				
				result.include("valorTotal", valorTotal);
				result.include("quantidade", quantidade);
				result.include("precoMedio", precoMedio);
				
				result.include("compras",lista);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public void salvar(Compra compra) {
		try {
			
			
			Fornecedor fornecedor = fornecedorDao.findById(compra.getFornecedor().getId());
			
			compra.setStatus(StatusCompraEnum.A);
			
			if(compra.getCentroAplicacao() != null && compra.getCentroAplicacao().getId() == null){
				compra.setCentroAplicacao(null);
			}
			
			if(compra.getContaContabil() != null && compra.getContaContabil().getId() == null){
				compra.setContaContabil(null);
			}
			
			//Caso o fornecedor trabalhe com adiantamentos receberá um tratamento diferenciado.
			if (fornecedor.getConta() != null){
				compra.setFormaPagamento(TipoPagamentoEnum.A);
				compra.setDataVencimento(compra.getDataCompetencia());
			}else{
				if(compra.getModalidadePagamento() == FormaPagamentoEnum.C && compra.getParcelas() == null){
					if(compra.getChequeEmitido() != null){
						ChequeEmitidoCompra cheque = new ChequeEmitidoCompra();
						cheque.setNumeroCheque(compra.getChequeEmitido().getNumeroCheque());
						cheque.setConta(compra.getConta());
						cheque.setData(compra.getDataCompetencia());
						cheque.setCompra(compra);
						cheque.setValor(compra.getValor());
						cheque.setStatus(SituacaoChequeEnum.A);
						cheque.setDataStatus(new Date());
						compra.setChequeEmitido(cheque);
						
						//chequeEmitidoDao.addEntity(cheque);
						
					}
				}else if(compra.getModalidadePagamento() == FormaPagamentoEnum.C && compra.getParcelas() != null){
					compra.setChequeEmitidoList(null);
				}
				
				
				if(compra.getParcelas()!=null){
					Collections.sort(compra.getParcelas(), new Comparator<ParcelaCompra>() {
						  public int compare(ParcelaCompra o1, ParcelaCompra o2) {
						      return o1.getDataVencimento().compareTo(o2.getDataVencimento());
						  }
					});
				}
				int numero =1;
				
				if (compra.getParcelas() != null){
					ChequeEmitidoCompra cheque = null;
					//Atualizando a despesa de cada parcela 
					for (ParcelaCompra parcela : compra.getParcelas()) {
						parcela.setCompra(compra);
						parcela.setStatus(StatusDespesaEnum.A);
						parcela.setNumero(numero);
						numero++;
						
						
						if(compra.getModalidadePagamento() == FormaPagamentoEnum.C && compra.getParcelas() != null){
							cheque = new ChequeEmitidoCompra();
							cheque.setNumeroCheque(parcela.getChequeEmitido().getNumeroCheque());
							cheque.setValor(parcela.getChequeEmitido().getValor());
							cheque.setConta(compra.getConta());
							cheque.setData(parcela.getDataVencimento());
							cheque.setCompra(compra);
							cheque.setValor(parcela.getValor());
							cheque.setStatus(SituacaoChequeEnum.A);
							cheque.setParcela(parcela);
							cheque.setDataStatus(new Date());
							parcela.setChequeEmitido(cheque);
							
						}
					}
					
				}
			}
			
			
			
			
			//Quando se tratar de despesa/compra a vista a data de vencimento é a mesma data da competencia
			if (compra.getFormaPagamento() == TipoPagamentoEnum.V){
				compra.setDataVencimento(compra.getDataCompetencia());
			}
			
			
			
			
			
			
			
			
			
			
			
			//compra.setConta(null);
			for (ItemCompra item : compra.getItens()) {
				item.setCompra(compra);
				
			}
			
			if (compra.getId() != null && compra.getId()>0){
				//alteracao
				//compra.setConta(contaDao.findById(compra.getConta().getId()));
				dao.updateEntity(compra);
				
			}	else{
				//insercao - nova compra
				//FornecedorMaterial fornecedorMaterial = fornecedorMaterialDao.findById(compra.getFornecedorMaterial().getId());
				//compra.setConta(fornecedorMaterial.getFornecedor().getConta());
				compra.setStatus(StatusCompraEnum.A);
				dao.addEntity(compra);
				
				if(fornecedor.getConta()!=null){
					
					//Setando os dados da Movimentacao.
					MovimentacaoCompra movimentacao = new MovimentacaoCompra();
					movimentacao.setCompra(compra);
					movimentacao.setData(compra.getData());
					movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
					movimentacao.setValor(compra.getValor());
					movimentacao.setConta(fornecedor.getConta());
					
					//inserindo a movimentacao no vanco de dados
					movimentacaoDAO.addEntity(movimentacao);
					
					if (fornecedor.getConta().getSaldo() == null){
						fornecedor.getConta().setSaldo(BigDecimal.ZERO);
					}
					
					//Atualizando o Saldo da conta subtrainda o valor da despesa
					fornecedor.getConta().setSaldo(fornecedor.getConta().getSaldo().subtract(compra.getValor()));
					
					//persistindo na base de dados
					contaDao.updateEntity(fornecedor.getConta());
				}
				else{
					
				
					if (compra.getModalidadePagamento() == FormaPagamentoEnum.C && compra.getChequeEmitido() != null){
						try{
							chequeEmitidoDAO.addEntity(compra.getChequeEmitido());
						}catch(DAOException ex){
							ex.printStackTrace();
							result.include("erro", ex.getMessage());
						}
					}
					
					//Caso se trate de pagamento a Vista
					if (compra.getFormaPagamento() == TipoPagamentoEnum.V ){
						
						//Caso o pagamento for em cheque. o saldo só devera ser atualizado e a movimentacao gerada quando o cheque tiver a sua compensação confirmada.
						if(compra.getModalidadePagamento() != FormaPagamentoEnum.C){
							
							//Setando os dados da Movimentacao.
							MovimentacaoCompra movimentacao = new MovimentacaoCompra();
							movimentacao.setCompra(compra);
							movimentacao.setData(compra.getDataCompetencia());
							movimentacao.setTipoOperacao(TipoOperacaoEnum.D);
							movimentacao.setValor(compra.getValor());
							movimentacao.setConta(compra.getConta());
							
							
							try {
								
								//inserindo a movimentacao no vanco de dados
								movimentacaoDAO.addEntity(movimentacao);
								
								//Obter a conta que sera debitado o valor da despesa
								Conta conta = contaDao.findById(compra.getConta().getId());
								
								//Atualizando o Saldo da conta subtrainda o valor da despesa
								conta.setSaldo(conta.getSaldo().subtract(compra.getValor()));
								
								//persistindo na base de dados
								contaDao.updateEntity(conta);
								
							} catch (DAOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
						
						
					}
					
					
					// Caso a forma de pagamento for à prazo
					else{
						
						//Verificando se trata-se de uma compra a prazo parcelada.
						if(compra.getParcelas() != null && compra.getParcelas().size()>0){
							
							for (ParcelaCompra parcela : compra.getParcelas()) {
								
								ContaAPagarCompra contaApagar = new ContaAPagarCompra();
								contaApagar.setParcela(parcela);
								if(compra.getModalidadePagamento() == FormaPagamentoEnum.C){
									contaApagar.setConta(compra.getConta());
								}else{
									
									contaApagar.setConta(null);
								}
								contaApagar.setDataPrevista(parcela.getDataVencimento());
								contaApagar.setDescricao("Parcela da Compra - " + compra.getId().toString());
								contaApagar.setStatus(StatusMovimentacaoEnum.A);
								contaApagar.setValor(parcela.getValor());
								contaApagar.setCompra(compra);
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
							
							ContaAPagarCompra conta = new ContaAPagarCompra();
							conta.setDataPrevista(compra.getDataVencimento());
							conta.setStatus(StatusMovimentacaoEnum.A);
							conta.setValor(compra.getValor());
							conta.setCompra(compra);
							conta.setDescricao("Compra " + compra.getId() );
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
				
				
				
				
				
				
				
				
				
				
				
				
//				ContaAPagarCompra contaPagar = new ContaAPagarCompra();
//				contaPagar.setCompra(compra);
//				
//				contaPagar.setDataPrevista(new Date());
//				
//				contaPagar.setValor(compra.getValor());
//				contaPagar.setStatus(StatusMovimentacaoEnum.A);
//				contaPagar.setDescricao("Compra Fornecedor " + compra.getFornecedor().getApelido() + " - " + compra.getFornecedor().getNome());
//				
//				contaAPagarDAO.addEntity(contaPagar);
				
				//Adicionar no estoque.
				for (ItemCompra item : compra.getItens()) {
					
					Estoque estoque = estoqueDAO.findByMaterial(item.getMaterial());
					
					// para o caso de nao haver nada no estoque desse material.
					if ( estoque ==null){
						estoque = new Estoque();
						estoque.setQuantidade(new BigDecimal(0));
					}
					
					if (estoque.getValor() == null){
						estoque.setValor(new BigDecimal(0));
					}
					
					estoque.setDataAlteracao(new Date());
					
					estoque.setQuantidade(estoque.getQuantidade().add(item.getQuantidade()));
					estoque.setValor(estoque.getValor().add(item.getValor()));
					
					//
					if (estoque.getId() !=null && estoque.getId() != 0){
						estoqueDAO.updateEntity(estoque);
					}else{
						estoque.setMaterial(item.getMaterial());
						estoqueDAO.addEntity(estoque);
					}
					
				}
				
				
				//compra.getConta().setSaldo(compra.getConta().getSaldo().subtract(movimentacao.getValor()));
				//contaDao.updateEntity(compra.getConta());
				compra = dao.findById(compra.getId());
				emailService.enviarEmailCompraEmitida(compra);
//				Email email = new Email();
//				email.setAssunto("Compra - " + compra.getId().toString());
//				String mensagem = "<html>Compra: " + compra.getId() + "<br>";
//				email.setMensagem("");
//				mensagem += email.getMensagem().concat("Fornecedor: " + compra.getFornecedor().getApelido() + " - " + compra.getFornecedor().getNome() + "<br>");
//				mensagem += email.getMensagem().concat("<br>");
//				
//				mensagem += email.getMensagem().concat("Itens compra : <br>");
//				for (ItemCompra item : compra.getItens()) {
//					mensagem += email.getMensagem().concat(item.getMaterial().getDescricao() + "<br>");
//					mensagem += email.getMensagem().concat("Pre&ccedil;o (" + item.getMaterial().getUnidadeMedida().getSigla() + "): " + NumberFormat.getCurrencyInstance().format(item.getPreco()) + "<br>");
//					mensagem += email.getMensagem().concat("Quantidade (" + item.getMaterial().getUnidadeMedida().getSigla() + "): " + NumberFormat.getNumberInstance().format(item.getQuantidade()) + "<br>");
//					mensagem += email.getMensagem().concat("Valor: " +  NumberFormat.getCurrencyInstance().format(item.getValor())   + "<br>");
//					
//					
//				}
//				
//				mensagem += email.getMensagem().concat("<br>");
//				
//				mensagem += email.getMensagem().concat("Valor Total: " + NumberFormat.getCurrencyInstance().format(compra.getValor()) + "<br>");
//				
//				mensagem += email.getMensagem().concat("<br>");
//				
//				mensagem += email.getMensagem().concat("Forma de pagamento: " + compra.getFormaPagamento().getDescricao()  + "<br>");
//				mensagem += email.getMensagem().concat("Modalidade de pagamento: " + compra.getModalidadePagamento().getDescricao()  + "<br>");
//				mensagem += email.getMensagem().concat("</html>");
//				email.setMensagem(mensagem);
//				email.enviar();
			}
			
			
			if(compra.getComprovantePesagem() != null){
				
				ComprovantePesagemEntrada comprovante = comprovantePesagemDAO.findById(compra.getComprovantePesagem().getId());
				comprovante.setFaturado("S");
				comprovantePesagemDAO.updateEntity(comprovante);
				
				
			}
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		result.redirectTo(CompraController.class).lista(null, null, null, null, null, null);
	}
	
	@Path({"/compra/{compra.id}","/compra/form","/compra/novo/{compra.fornecedor.id}"})
	public Compra form(Compra compra) throws DAOException{
		
		if (compra != null && compra.getId() != null && compra.getId()>0){
			try {
				compra = dao.findById(compra.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());a
				
				
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(compra != null && compra.getFornecedor()!=null && compra.getFornecedor().getId()!=null)
				compra.setFornecedor(fornecedorDao.findById(compra.getFornecedor().getId()));
		}
		
		if(compra.getItens() != null)
			result.include("quantidadeItens", compra.getItens().size());
		else
			result.include("quantidadeItens", 0);
		
		
		//quando vier já com um fornecedor selecionado da lista de fornecedores
		if (compra != null && compra.getFornecedor() != null  && 
				compra.getFornecedor().getId() != null && compra.getFornecedor().getId() > 0){
			
			//Fornecedor fornecedor = fornecedorDao.findById(compra.getFornecedorMaterial().getFornecedor().getId());
			result.include("fornecedor", compra.getFornecedor());
			
			List<FornecedorMaterial> fornecedorMateriais = fornecedorMaterialDao.obterAtivosPorFiltro(compra.getFornecedor(),compra.getTipoFrete());
			result.include("fornecedorMateriais", fornecedorMateriais);
			result.include("tiposFrete",TipoFreteEnum.values());
			
			//this.result.use(Results.json()).from(fornecedorMateriais, "formulario").serialize();
		}
		
		List<ContaContabil> listaContas;
		List<CentroAplicacao> listaCentroAplicacao;
		
		
		listaContas = contaContabilDAO.findAll();
		listaCentroAplicacao = centroAplicacaoDAO.findAll();
		
		result.include("contas", listaContas);
		result.include("centros", listaCentroAplicacao);
		
		
		
		return compra;
	}
	
	
	public void loadJsonMaterial(TipoFreteEnum tipoFrete, Fornecedor fornecedor){
		
		List<FornecedorMaterial> fornecedorMateriais = fornecedorMaterialDao.obterAtivosPorFiltro(fornecedor,tipoFrete);
		
		result.use(json()).from(fornecedorMateriais).include("material").serialize();
		
		result.nothing();
		
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
				lista = (List<Conta>)contaDao.obterContasFundoFixo();
				
			}
			else if(formaPagamento == FormaPagamentoEnum.B){
				lista = (List<Conta>)contaDao.obterContasFinanceiras();
			}
			//se nao for pagamento em dinheiro carregar as contas bancárias
			else{
				
					
					lista = (List<Conta>)contaDao.obterContasBancarias();
				
			}
		}else if(formaPagamento == FormaPagamentoEnum.C){
			lista = (List<Conta>)contaDao.obterContasBancarias();
		}
		
		if (lista != null)
			result.use(json()).from(lista).serialize();
		
		result.nothing();
		
	}
	
	@Path("/emitirCompra/{id}")
	public void EmitirCompra(Long id) throws DAOException {
		
		ComprovantePesagemEntrada bean = comprovantePesagemDAO.findById(id);
		
		if (bean!= null && (bean.getFaturado() == null || bean.getFaturado().equals("N"))){
			
			Compra compra = new Compra();
			
			compra.setData(bean.getDataEmissao());
			compra.setFornecedor(bean.getFornecedor());
			compra.setTipoFrete(bean.getTipoFrete());
			compra.setComprovantePesagem(bean);
			compra.setItens(new ArrayList<ItemCompra>());
			compra.setValor(BigDecimal.ZERO);
			
			ItemCompra item = null;
			for (ItemPesagemEntrada itemPesagem : bean.getItens()) {
				item = new ItemCompra();
				item.setCompra(compra);
				item.setMaterial(itemPesagem.getMaterial());
				item.setQuantidade(itemPesagem.getPesoLiquido());
				List<FornecedorMaterial> listaFornecMat =  fornecedorMaterialDao.obterAtivosPorFiltro(compra.getFornecedor(), compra.getTipoFrete());
				
				for (FornecedorMaterial fornecedorMaterial : listaFornecMat) {
					if (fornecedorMaterial.getMaterial().getId().equals(itemPesagem.getMaterial().getId())){
						item.setPreco(fornecedorMaterial.getValor());
						item.setValor(item.getPreco().multiply(item.getQuantidade()));
						compra.setValor(compra.getValor().add(item.getValor()));
						break;
					}
				}
				compra.getItens().add(item);
			}
			
			
			result.forwardTo(this).form(compra);
		}else{
			result.forwardTo(this).lista(null, null, null, null, null, null);
		}
		
		
	
	}

}
