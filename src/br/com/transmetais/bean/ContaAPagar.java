package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.StatusMovimentacaoEnum;

@Entity
@Table(name="contaapagar")
@Inheritance(strategy = InheritanceType.JOINED)
public class ContaAPagar {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Integer id;
	@Column(name="data_prevista")
	protected Date dataPrevista;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="conta_id")
	protected Conta conta;
	protected BigDecimal valor;
	@Column(name="data_pagamento")
	protected Date dataPagamento;
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	protected StatusMovimentacaoEnum status;
	protected String descricao;
	protected BigDecimal juros;
	protected BigDecimal multa;
	@Column(name="valor_total")
	protected BigDecimal valorTotal;
	@ManyToOne
	@JoinColumn(name="parcela_id")
	private Parcela parcela;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Date getDataPrevista() {
		return dataPrevista;
	}
	public void setDataPrevista(Date dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public BigDecimal getJuros() {
		return juros;
	}
	public void setJuros(BigDecimal juros) {
		this.juros = juros;
	}
	
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public BigDecimal getMulta() {
		return multa;
	}
	
	public void setMulta(BigDecimal multa) {
		this.multa = multa;
	}
	
	public FormaPagamentoEnum getModalidadePagamento() {
		if (this instanceof ContaAPagarDespesa){
			return ((ContaAPagarDespesa)this).getDespesa().getModalidadePagamento();
		}else if(this instanceof ContaAPagarCompra){
			return ((ContaAPagarCompra)this).getCompra().getModalidadePagamento();
		}
		
		return null;
	}
	
	public ChequeEmitido getChequeEmitido(){
		if(this instanceof ContaAPagarDespesa){
			
			if (this.getParcela() == null){
				return ((ContaAPagarDespesa)this).getDespesa().getChequeEmitido();
			}
			else{
				return this.getParcela().getChequeEmitido();
			}
			//todo
		}else if(this instanceof ContaAPagarCompra){
			if (this.getParcela() == null){
				return ((ContaAPagarCompra)this).getCompra().getChequeEmitido();
			}
			else{
				return this.getParcela().getChequeEmitido();
			}
		}
		return null;
	}
	
	public Parcela getParcela() {
		return parcela;
	}
	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}
	
	
	
}
