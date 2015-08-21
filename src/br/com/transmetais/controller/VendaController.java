package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.CentroAplicacao;
import br.com.transmetais.bean.Cliente;
import br.com.transmetais.bean.ClienteMaterial;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaAReceberVenda;
import br.com.transmetais.bean.ContaContabil;
import br.com.transmetais.bean.Estoque;
import br.com.transmetais.bean.EstoqueMovimentacaoSaida;
import br.com.transmetais.bean.ItemVenda;
import br.com.transmetais.bean.Material;
import br.com.transmetais.bean.MovimentacaoVenda;
import br.com.transmetais.bean.ParcelaVenda;
import br.com.transmetais.bean.Venda;
import br.com.transmetais.dao.CentroAplicacaoDAO;
import br.com.transmetais.dao.ClienteDAO;
import br.com.transmetais.dao.ClienteMaterialDAO;
import br.com.transmetais.dao.ContaAReceberDAO;
import br.com.transmetais.dao.ContaContabilDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.EstoqueDAO;
import br.com.transmetais.dao.EstoqueMovimentacaoDAO;
import br.com.transmetais.dao.MaterialDAO;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.VendaDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.StatusVendaEnum;
import br.com.transmetais.type.TipoEstoqueMovimentacaoEnum;
import br.com.transmetais.type.TipoFreteCliente;
import br.com.transmetais.type.TipoOperacaoEnum;
import br.com.transmetais.type.TipoPagamentoEnum;

@Resource
public class VendaController {
	
	private final Result result;
	private VendaDAO dao;
	private ClienteDAO clienteDao;
	private ClienteMaterialDAO clienteMaterialDAO;
	private ContaAReceberDAO contaAReceberDAO;
	private EstoqueDAO estoqueDAO;
	private MaterialDAO materialDao;
	private ContaDAO contaDao;
	private ContaContabilDAO contaContabilDAO;
	private CentroAplicacaoDAO centroAplicacaoDAO;
	//private ChequeEmitidoDAO chequeEmitidoDAO;
	private MovimentacaoDAO movimentacaoDAO;
	private EstoqueMovimentacaoDAO estoqueMovimentacaoDAO;
	
	public VendaController(Result result, VendaDAO vendaDao, ClienteDAO clienteDao, ClienteMaterialDAO clienteMaterialDAO, ContaAReceberDAO contaAReceberDAO, 
			ContaDAO contaDao, MaterialDAO materialDao, EstoqueDAO estoqueDAO,ContaContabilDAO contaContabilDAO, CentroAplicacaoDAO centroAplicacaoDAO,
			MovimentacaoDAO movimentacaoDAO, EstoqueMovimentacaoDAO estoqueMovimentacaoDAO) {
		this.dao = vendaDao;
		this.clienteDao = clienteDao;
		this.clienteMaterialDAO = clienteMaterialDAO;
		this.contaAReceberDAO = contaAReceberDAO;
		this.result = result;
		this.materialDao = materialDao;
		this.estoqueDAO = estoqueDAO;
		this.contaDao = contaDao;
		this.contaContabilDAO = contaContabilDAO;
		this.centroAplicacaoDAO = centroAplicacaoDAO;
		
		this.movimentacaoDAO = movimentacaoDAO;
		this.estoqueMovimentacaoDAO = estoqueMovimentacaoDAO;
	}
	
	//tela de listagem de compras
	@Path({"/venda/","/venda","/venda/lista"})
	public List<Venda> lista(Long clienteId, Date dataInicio, Date dataFim, List<TipoFreteCliente> tiposFretes, List<Long> materiaisSelecionados, List<StatusVendaEnum> statusVendas){
		List<Venda> lista = null;
		
		try {
			List<Cliente> clientes = clienteDao.findAll();
			List<Material> materiais = materialDao.findAll();
			
			lista = dao.findByFilter(clienteId, dataInicio, dataFim, tiposFretes, materiaisSelecionados, statusVendas);
			BigDecimal valorTotal = new BigDecimal(0);
			BigDecimal quantidade = new BigDecimal(0);
			for (Venda venda : lista) {
				for(ItemVenda item : venda.getItens()){
					valorTotal = valorTotal.add( item.getValor());
					quantidade = quantidade.add( item.getQuantidade());
					
				}
			}
			
			
			BigDecimal precoMedio =  new BigDecimal(0);
			
			if(valorTotal.compareTo(new BigDecimal(0)) != 0 && quantidade.compareTo(new BigDecimal(0)) != 0){
				
				precoMedio = valorTotal.divide(quantidade, BigDecimal.ROUND_HALF_DOWN);
			}
			
			
			result.include("clientes", clientes);
			result.include("materiais", materiais);
			result.include("tiposFrete",TipoFreteCliente.values());
			result.include("statusList",StatusVendaEnum.values());
			
			result.include("valorTotal", valorTotal);
			result.include("quantidade", quantidade);
			result.include("precoMedio", precoMedio);
			
			result.include("vendas",lista);
			
			//result.forwardTo("/jsp/compra/lista.jsp");
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	
		public List<Venda> loadListaVenda(Long clienteId, Date dataInicio, Date dataFim, List<TipoFreteCliente> tiposFretes, List<Long> materiaisSelecionados, List<StatusVendaEnum> statusVendas){
			List<Venda> lista = null;
			
			try {
				
				
				lista = dao.findByFilter(clienteId, dataInicio, dataFim, tiposFretes, materiaisSelecionados, statusVendas);
				BigDecimal valorTotal = new BigDecimal(0);
				BigDecimal quantidade = new BigDecimal(0);
				for (Venda venda : lista) {
					for(ItemVenda item : venda.getItens()){
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
				
				result.include("vendas",lista);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return lista;
		}
	
	public void salvar(Venda venda) {
		try {
			
			
			Cliente cliente = clienteDao.findById(venda.getCliente().getId());
			
			venda.setStatus(StatusVendaEnum.A);
			
//			if(venda.getCentroAplicacao() != null && venda.getCentroAplicacao().getId() == null){
//				venda.setCentroAplicacao(null);
//			}
//			
//			if(venda.getContaContabil() != null && venda.getContaContabil().getId() == null){
//				venda.setContaContabil(null);
//			}
			
			//Caso o cliente trabalhe com adiantamentos receberá um tratamento diferenciado.
			if (cliente.getConta() != null){
				venda.setFormaPagamento(TipoPagamentoEnum.A);
				venda.setDataVencimento(venda.getData());
			}else{
				
				
				
				if(venda.getParcelas()!=null){
					Collections.sort(venda.getParcelas(), new Comparator<ParcelaVenda>() {
						  public int compare(ParcelaVenda o1, ParcelaVenda o2) {
						      return o1.getDataVencimento().compareTo(o2.getDataVencimento());
						  }
					});
				}
				int numero =1;
				
				if (venda.getParcelas() != null){
					//ChequeEmitidoCompra cheque = null;
					//Atualizando a despesa de cada parcela 
					for (ParcelaVenda parcela : venda.getParcelas()) {
						parcela.setVenda(venda);
						parcela.setStatus(StatusDespesaEnum.A);
						parcela.setNumero(numero);
						numero++;
						
						
						if(venda.getModalidadePagamento() == FormaPagamentoEnum.C && venda.getParcelas() != null){
//							cheque = new ChequeEmitidoCompra();
//							cheque.setNumeroCheque(parcela.getChequeEmitido().getNumeroCheque());
//							cheque.setValor(parcela.getChequeEmitido().getValor());
//							cheque.setConta(compra.getConta());
//							cheque.setData(parcela.getDataVencimento());
//							cheque.setCompra(compra);
//							cheque.setValor(parcela.getValor());
//							cheque.setStatus(SituacaoChequeEnum.A);
//							cheque.setParcela(parcela);
//							cheque.setDataStatus(new Date());
//							parcela.setChequeEmitido(cheque);
							
						}
					}
					
				}
			}
			
			
			
			
			//Quando se tratar de despesa/compra a vista a data de vencimento é a mesma data da competencia
			if (venda.getFormaPagamento() == TipoPagamentoEnum.V){
				venda.setDataVencimento(venda.getData());
				venda.setStatus(StatusVendaEnum.P);
				venda.setDataRecebimento(venda.getData());
			}
			
			
			
			
			
			
			
			
			
			
			
			//compra.setConta(null);
			for (ItemVenda item : venda.getItens()) {
				item.setVenda(venda);
				
			}
			
			if (venda.getId() != null && venda.getId()>0){
				//alteracao
				//compra.setConta(contaDao.findById(compra.getConta().getId()));
				dao.updateEntity(venda);
				
			}	else{
				//insercao - nova compra
				//FornecedorMaterial fornecedorMaterial = fornecedorMaterialDao.findById(compra.getFornecedorMaterial().getId());
				//compra.setConta(fornecedorMaterial.getFornecedor().getConta());
				if (venda.getFormaPagamento() == TipoPagamentoEnum.V ){
					venda.setStatus(StatusVendaEnum.P);
				}else{
					
					venda.setStatus(StatusVendaEnum.A);
				}
				dao.addEntity(venda);
				
				
				
				if(cliente.getConta()!=null){
					
					//Setando os dados da Movimentacao.
					MovimentacaoVenda movimentacao = new MovimentacaoVenda();
					movimentacao.setVenda(venda);
					movimentacao.setData(venda.getData());
					movimentacao.setTipoOperacao(TipoOperacaoEnum.C);
					movimentacao.setValor(venda.getValor());
					movimentacao.setConta(cliente.getConta());
					
					//inserindo a movimentacao no vanco de dados
					movimentacaoDAO.addEntity(movimentacao);
					
					if (cliente.getConta().getSaldo() == null){
						cliente.getConta().setSaldo(BigDecimal.ZERO);
					}
					
					//Atualizando o Saldo da conta subtrainda o valor da despesa
					cliente.getConta().setSaldo(cliente.getConta().getSaldo().add(venda.getValor()));
					
					//persistindo na base de dados
					contaDao.updateEntity(cliente.getConta());
				}
				else{
					
				
//					if (venda.getModalidadePagamento() == FormaPagamentoEnum.C && venda.getChequeEmitido() != null){
////						try{
////							chequeEmitidoDAO.addEntity(compra.getChequeEmitido());
////						}catch(DAOException ex){
////							ex.printStackTrace();
////							result.include("erro", ex.getMessage());
////						}
//					}
					
					//Caso se trate de pagamento a Vista
					if (venda.getFormaPagamento() == TipoPagamentoEnum.V ){
						
						//Caso o pagamento for em cheque. o saldo só devera ser atualizado e a movimentacao gerada quando o cheque tiver a sua compensação confirmada.
						if(venda.getModalidadePagamento() != FormaPagamentoEnum.C){
							
							//Setando os dados da Movimentacao.
							MovimentacaoVenda movimentacao = new MovimentacaoVenda();
							movimentacao.setVenda(venda);
							movimentacao.setData(venda.getData());
							movimentacao.setTipoOperacao(TipoOperacaoEnum.C);
							movimentacao.setValor(venda.getValor());
							movimentacao.setConta(venda.getConta());
							
							
							try {
								
								//inserindo a movimentacao no vanco de dados
								movimentacaoDAO.addEntity(movimentacao);
								
								//Obter a conta que sera creditado o valor da venda
								Conta conta = contaDao.findById(venda.getConta().getId());
								
								//Atualizando o Saldo da conta add o valor da venda
								conta.setSaldo(conta.getSaldo().add(venda.getValor()));
								
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
						if(venda.getParcelas() != null && venda.getParcelas().size()>0){
							
							for (ParcelaVenda parcela : venda.getParcelas()) {
								
								ContaAReceberVenda contaAReceber = new ContaAReceberVenda();
								contaAReceber.setParcela(parcela);
								if(venda.getModalidadePagamento() == FormaPagamentoEnum.C){
									contaAReceber.setConta(venda.getConta());
								}else{
									
									contaAReceber.setConta(null);
								}
								contaAReceber.setDataPrevista(parcela.getDataVencimento());
								contaAReceber.setDescricao("Parcela da Venda - " + venda.getId().toString());
								contaAReceber.setStatus(StatusMovimentacaoEnum.A);
								contaAReceber.setValor(parcela.getValor());
								contaAReceber.setVenda(venda);
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
							conta.setDataPrevista(venda.getDataVencimento());
							conta.setStatus(StatusMovimentacaoEnum.A);
							conta.setValor(venda.getValor());
							conta.setVenda(venda);
							conta.setDescricao("Venda " + venda.getId() );
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
				for (ItemVenda item : venda.getItens()) {
					
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
					
					BigDecimal precoMedio = BigDecimal.ZERO;
					
					if(estoque.getValor().compareTo(BigDecimal.ZERO) > 0 && estoque.getQuantidade().compareTo(BigDecimal.ZERO) > 0){
						
						precoMedio = estoque.getValor().divide( estoque.getQuantidade(), BigDecimal.ROUND_HALF_UP);
					}else{
						precoMedio = item.getPreco();
					}
					
					estoque.setQuantidade(estoque.getQuantidade().subtract(item.getQuantidade()));
					estoque.setValor(estoque.getValor().subtract(precoMedio));
					
					//
					if (estoque.getId() !=null && estoque.getId() != 0){
						estoqueDAO.updateEntity(estoque);
					}else{
						estoque.setMaterial(item.getMaterial());
						estoqueDAO.addEntity(estoque);
					}
					
					//Adicionar Movimentacao de estoqeu
					EstoqueMovimentacaoSaida estoqueMovimentacao = new EstoqueMovimentacaoSaida();
					estoqueMovimentacao.setData(item.getVenda().getData());
					estoqueMovimentacao.setQuantidade(item.getQuantidade());
					estoqueMovimentacao.setValor(item.getValor());
					estoqueMovimentacao.setVenda(item.getVenda());
					estoqueMovimentacao.setMaterial(item.getMaterial());
					estoqueMovimentacao.setTipoMovimentacao(TipoEstoqueMovimentacaoEnum.S);
					estoqueMovimentacaoDAO.addEntity(estoqueMovimentacao);
					
				}
				
				
				//compra.getConta().setSaldo(compra.getConta().getSaldo().subtract(movimentacao.getValor()));
				//contaDao.updateEntity(compra.getConta());
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(VendaController.class).lista(null, null, null, null, null, null);
	}
	
	@Path({"/venda/{venda.id}","/venda/form","/venda/novo/{venda.cliente.id}"})
	public Venda form(Venda venda) throws DAOException{
		
		if (venda != null && venda.getId() != null && venda.getId()>0){
			try {
				venda = dao.findById(venda.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());a
				
				
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			if(venda != null && venda.getCliente()!=null && venda.getCliente().getId()!=null)
				venda.setCliente(clienteDao.findById(venda.getCliente().getId()));
		}
		
		if(venda.getItens() != null)
			result.include("quantidadeItens", venda.getItens().size());
		else
			result.include("quantidadeItens", 0);
		
		
		//quando vier já com um cliente selecionado da lista de clientes
		if (venda != null && venda.getCliente() != null  && 
				venda.getCliente().getId() != null && venda.getCliente().getId() > 0){
			
			
			result.include("cliente", venda.getCliente());
			
			List<ClienteMaterial> clienteMaterial = clienteMaterialDAO.obterAtivosPorFiltro(venda.getCliente(),venda.getTipoFrete());
			result.include("fornecedorMateriais", clienteMaterial);
			result.include("tiposFrete",TipoFreteCliente.values());
			
			//this.result.use(Results.json()).from(fornecedorMateriais, "formulario").serialize();
		}
		
		List<ContaContabil> listaContas;
		List<CentroAplicacao> listaCentroAplicacao;
		
		
		listaContas = contaContabilDAO.findAll();
		listaCentroAplicacao = centroAplicacaoDAO.findAll();
		
		result.include("contas", listaContas);
		result.include("centros", listaCentroAplicacao);
		
		
		
		return venda;
	}
	
	
	public void loadJsonMaterial(TipoFreteCliente tipoFrete, Cliente cliente){
		
		List<ClienteMaterial> clienteMateriais = clienteMaterialDAO.obterAtivosPorFiltro(cliente,tipoFrete);
		
		result.use(json()).from(clienteMateriais).include("material").serialize();
		
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

}
