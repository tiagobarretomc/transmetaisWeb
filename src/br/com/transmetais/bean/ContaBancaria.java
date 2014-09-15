package br.com.transmetais.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="conta_bancaria")
@PrimaryKeyJoinColumn(name="conta_id")
public class ContaBancaria extends Conta {
	
	
	
	private String banco;
	private String agencia;
	@Column(name="conta_corrente")
	private String contaCorrente;
	
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getContaCorrente() {
		return contaCorrente;
	}
	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	
	
	
}
