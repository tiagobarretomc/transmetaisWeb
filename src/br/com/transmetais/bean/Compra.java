package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.TipoFreteEnum;
import br.com.transmetais.type.TipoPagamentoEnum;

@Entity
@Table(name="compra")
public class Compra {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_material_id")
	private FornecedorMaterial fornecedorMaterial;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	private BigDecimal valor;
	@ManyToOne
	@JoinColumn(name="conta_id")
	private Conta conta;
	private String observacao;
	
	@Column(name="num_nf")
	private String numNf;
	
	@OneToMany(mappedBy="compra", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	private List<ItemCompra> itens;
	
	@Column(name="tipo_frete")
	@Enumerated(EnumType.STRING)
	private TipoFreteEnum tipoFrete;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private StatusCompraEnum status;
	
	@Enumerated(EnumType.STRING)
	@Column(name="modalidade_pagamento")
	private FormaPagamentoEnum modalidadePagamento;
	@Column(name="forma_pagamento")
	private TipoPagamentoEnum formaPagamento;
	@OneToMany(mappedBy="compra", fetch=FetchType.LAZY)
	private List<ChequeEmitidoCompra> chequeEmitidoList;
	@Column(name="data_vencimento")
	private Date dataVencimento;
	@Column(name="data_pagamento")
	private Date dataPagamento;
	@Column(name="data_cancelamento")
	private Date dataCancelamento;
	@Column(name="data_competencia")
	private Date dataCompetencia;
	@OneToMany(mappedBy="compra", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	private List<ParcelaCompra> parcelas;
	@ManyToOne
	@JoinColumn(name="centro_aplicacao_id")
	private CentroAplicacao centroAplicacao;
	@ManyToOne
	@JoinColumn(name="conta_contabil_id")
	private ContaContabil contaContabil;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public FornecedorMaterial getFornecedorMaterial() {
		return fornecedorMaterial;
	}
	public void setFornecedorMaterial(FornecedorMaterial fornecedorMaterial) {
		this.fornecedorMaterial = fornecedorMaterial;
	}

	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public String getNumNf() {
		return numNf;
	}
	public void setNumNf(String numNf) {
		this.numNf = numNf;
	}
	public List<ItemCompra> getItens() {
		return itens;
	}
	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public TipoFreteEnum getTipoFrete() {
		return tipoFrete;
	}
	public void setTipoFrete(TipoFreteEnum tipoFrete) {
		this.tipoFrete = tipoFrete;
	}
	public StatusCompraEnum getStatus() {
		return status;
	}
	public void setStatus(StatusCompraEnum status) {
		this.status = status;
	}
	
	public FormaPagamentoEnum getModalidadePagamento() {
		return modalidadePagamento;
	}
	
	public void setModalidadePagamento(FormaPagamentoEnum modalidadePagamento) {
		this.modalidadePagamento = modalidadePagamento;
	}
	
	public ChequeEmitidoCompra getChequeEmitido(){
		if(chequeEmitidoList != null && !chequeEmitidoList.isEmpty()){
			return chequeEmitidoList.get(0);
		}
		return null;
	}
	public void setChequeEmitido(ChequeEmitidoCompra chequeEmitido){
		if(chequeEmitidoList == null){
			chequeEmitidoList = new ArrayList<ChequeEmitidoCompra>();
		}
		chequeEmitidoList.add(chequeEmitido);
	}
	
	public List<ChequeEmitidoCompra> getChequeEmitidoList() {
		return chequeEmitidoList;
	}
	
	public void setChequeEmitidoList(List<ChequeEmitidoCompra> chequeEmitidoList) {
		this.chequeEmitidoList = chequeEmitidoList;
	}
	
	public Date getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public Date getDataVencimento() {
		return dataVencimento;
	}
	
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	public List<ParcelaCompra> getParcelas() {
		return parcelas;
	}
	
	public void setParcelas(List<ParcelaCompra> parcelas) {
		this.parcelas = parcelas;
	}
	
	public TipoPagamentoEnum getFormaPagamento() {
		return formaPagamento;
	}
	
	public void setFormaPagamento(TipoPagamentoEnum formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	
	public Date getDataCancelamento() {
		return dataCancelamento;
	}
	
	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}
	public CentroAplicacao getCentroAplicacao() {
		return centroAplicacao;
	}
	public void setCentroAplicacao(CentroAplicacao centroAplicacao) {
		this.centroAplicacao = centroAplicacao;
	}
	public ContaContabil getContaContabil() {
		return contaContabil;
	}
	public void setContaContabil(ContaContabil contaContabil) {
		this.contaContabil = contaContabil;
	}
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	
	
	
}
