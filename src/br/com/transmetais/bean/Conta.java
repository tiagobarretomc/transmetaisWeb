package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="conta")
@Inheritance(strategy = InheritanceType.JOINED)
public class Conta {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;
	protected String descricao;
	protected BigDecimal saldo;
	@Column(name="saldo_inicial")
	protected BigDecimal saldoInicial;
	@Column(name="data_saldo_inicial")
	protected Date dataSaldoInicial;
	@ManyToOne
	@JoinColumn(name="conta_contabil_id")
	protected ContaContabil contaContabil;
	
	
	
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
	
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}
	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}
	
	
	public Date getDataSaldoInicial() {
		return dataSaldoInicial;
	}
	public void setDataSaldoInicial(Date dataSaldoInicial) {
		this.dataSaldoInicial = dataSaldoInicial;
	}
	public ContaContabil getContaContabil() {
		return contaContabil;
	}
	public void setContaContabil(ContaContabil contaContabil) {
		this.contaContabil = contaContabil;
	}
	
	
	
	
	
	

}
