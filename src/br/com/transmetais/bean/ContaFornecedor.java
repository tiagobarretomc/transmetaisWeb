package br.com.transmetais.bean;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="conta_fornecedor")
@PrimaryKeyJoinColumn(name="conta_id")
public class ContaFornecedor extends Conta{
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	private BigDecimal limite;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	
	public BigDecimal getLimite() {
		return limite;
	}
	
	public void setLimite(BigDecimal limite) {
		this.limite = limite;
	}

}
