package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.SituacaoAdiantamentoEnum;

@Entity
@Table(name="adiantamento")
public class Adiantamento {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="data_inclusao")
	private Date dataInclusao;
	@Column(name="data_pagamento")
	private Date dataPagamento;
	@Column(name="tipo_pagamento")
	@Enumerated(EnumType.STRING)
	private FormaPagamentoEnum tipoPagamento;
	private BigDecimal valor;
	@Enumerated(EnumType.STRING)
	private SituacaoAdiantamentoEnum situacao;
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	@ManyToOne
	@JoinColumn(name="movimentacao_id")
	private Movimentacao movimentacao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataInclusao() {
		return dataInclusao;
	}
	public void setDataInclusao(Date dataInclusao) {
		this.dataInclusao = dataInclusao;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public FormaPagamentoEnum getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(FormaPagamentoEnum tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public SituacaoAdiantamentoEnum getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoAdiantamentoEnum situacao) {
		this.situacao = situacao;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Movimentacao getMovimentacao() {
		return movimentacao;
	}
	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}
	

}
