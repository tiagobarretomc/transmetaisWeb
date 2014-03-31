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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.transmetais.type.StatusMovimentacaoEnum;
import br.com.transmetais.type.TipoOperacaoEnum;

@Entity
@Table(name="movimentacao")
@Inheritance(strategy = InheritanceType.JOINED)
public class Movimentacao {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	protected Date data;
	@Enumerated(EnumType.STRING)
	@Column(name="tipo_operacao")
	protected TipoOperacaoEnum tipoOperacao;
	@ManyToOne
	@JoinColumn(name="conta_id")
	protected Conta conta;
	
	
	protected BigDecimal valor;
	@Column(name="valor_previsto")
	protected BigDecimal valorPrevisto;
	@Column(name="data_pagamento")
	protected Date dataPagamento;
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	protected StatusMovimentacaoEnum status;
	
	protected String descricao;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public TipoOperacaoEnum getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(TipoOperacaoEnum tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getValorPrevisto() {
		return valorPrevisto;
	}
	public void setValorPrevisto(BigDecimal valorPrevisto) {
		this.valorPrevisto = valorPrevisto;
	}
	public Date getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public StatusMovimentacaoEnum getStatus() {
		return status;
	}
	public void setStatus(StatusMovimentacaoEnum status) {
		this.status = status;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	
}
