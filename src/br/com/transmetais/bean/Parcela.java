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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.transmetais.type.StatusDespesaEnum;


@Entity
@Table(name="parcela")
@Inheritance(strategy = InheritanceType.JOINED)
public class Parcela {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	
	
	
	@Column(name="data_vencimento")
	private Date dataVencimento;
	
	@Column(name="valor")
	protected BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	protected StatusDespesaEnum status;
	
	@Column(name="data_pagamento")
	protected Date dataPagamento;
	
	@Column(name="data_cancelamento")
	protected Date dataCancelamento;
	
	protected Integer numero;
	
	@OneToOne(mappedBy="parcela", fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	protected ChequeEmitido chequeEmitido;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public ChequeEmitido getChequeEmitido() {
		return chequeEmitido;
	}

	public void setChequeEmitido(ChequeEmitido chequeEmitido) {
		this.chequeEmitido = chequeEmitido;
	}
	
	public Date getDataCancelamento() {
		return dataCancelamento;
	}
	
	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

}
