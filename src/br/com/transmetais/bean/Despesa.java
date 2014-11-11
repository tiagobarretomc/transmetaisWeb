package br.com.transmetais.bean;

import java.math.BigDecimal;
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
import br.com.transmetais.type.StatusDespesaEnum;
import br.com.transmetais.type.TipoPagamentoEnum;

@Entity
@Table(name="despesa")
public class Despesa {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private BigDecimal valor;
	@Column(name="data_competencia")
	private Date dataCompetencia;
	@Column(name="data_vencimento")
	private Date dataVencimento;
	@Column(name="data_pagamento")
	private Date dataPagamento;
	@Enumerated(EnumType.STRING)
	private StatusDespesaEnum status;
	@ManyToOne
	@JoinColumn(name="centro_aplicacao_id")
	private CentroAplicacao centroAplicacao;
	@ManyToOne
	@JoinColumn(name="conta_contabil_id")
	private ContaContabil contaContabil;
	@Enumerated(EnumType.STRING)
	@Column(name="forma_pagamento")
	private TipoPagamentoEnum formaPagamento;
	@Enumerated(EnumType.STRING)
	@Column(name="modalidade_pagamento")
	private FormaPagamentoEnum modalidadePagamento;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="conta_id")
	protected Conta conta;
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	
	@OneToMany(mappedBy="despesa", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	private List<Parcela> parcelas;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public Date getDataCompetencia() {
		return dataCompetencia;
	}
	public void setDataCompetencia(Date dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public StatusDespesaEnum getStatus() {
		return status;
	}
	public void setStatus(StatusDespesaEnum status) {
		this.status = status;
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
	public TipoPagamentoEnum getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(TipoPagamentoEnum formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public FormaPagamentoEnum getModalidadePagamento() {
		return modalidadePagamento;
	}
	public void setModalidadePagamento(FormaPagamentoEnum modalidadePagamento) {
		this.modalidadePagamento = modalidadePagamento;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public List<Parcela> getParcelas() {
		return parcelas;
	}
	public void setParcelas(List<Parcela> parcelas) {
		this.parcelas = parcelas;
	}
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

}
