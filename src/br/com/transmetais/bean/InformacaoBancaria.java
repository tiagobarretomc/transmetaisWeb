package br.com.transmetais.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="informacao_bancaria")
public class InformacaoBancaria {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String agencia;
	private String banco;
	private String conta;
	@Column(name="cpf_cnpj_titular")
	private String cpfCnpjTitular;
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	private String titular;
	@ManyToOne
	@JoinColumn(name="transportador_id")
	private Transportador transportador;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getCpfCnpjTitular() {
		return cpfCnpjTitular;
	}
	public void setCpfCnpjTitular(String cpfCnpjTitular) {
		this.cpfCnpjTitular = cpfCnpjTitular;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public String getTitular() {
		return titular;
	}
	public void setTitular(String titular) {
		this.titular = titular;
	}
	public Transportador getTransportador() {
		return transportador;
	}
	public void setTransportador(Transportador transportador) {
		this.transportador = transportador;
	}
	
	

}
