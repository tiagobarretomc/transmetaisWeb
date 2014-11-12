package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.transmetais.type.StatusDespesaEnum;


@Entity
@Table(name="parcela_despesa")
public class ParcelaDespesa {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="despesa_id")
	private Despesa despesa;
	
	@Column(name="data_vencimento")
	private Date dataVencimento;
	
	@Column(name="valor")
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	private StatusDespesaEnum status;
	
	@Column(name="data_pagamento")
	private Date dataPagamento;
	
	
	private Integer numero;
	
	@OneToOne(mappedBy="parcela", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private ChequeEmitidoDespesa chequeEmitidoParcela;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	
	
	public StatusDespesaEnum getStatus() {
		return status;
	}
	
	public void setStatus(StatusDespesaEnum status) {
		this.status = status;
	}
	
	public Date getDataPagamento() {
		return dataPagamento;
	}
	
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	public Integer getNumero() {
		return numero;
	}
	
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public ChequeEmitidoDespesa getChequeEmitidoParcela() {
		return chequeEmitidoParcela;
	}

	public void setChequeEmitidoParcela(ChequeEmitidoDespesa chequeEmitidoParcela) {
		this.chequeEmitidoParcela = chequeEmitidoParcela;
	}

}
