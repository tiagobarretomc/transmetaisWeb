package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="conta")
public class Compra {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date data;
	
	@ManyToOne
	@JoinColumn(name="fornecedor_material_id")
	private FornecedorMaterial fornecedorMaterial;
	private BigDecimal quantidade;
	private BigDecimal valor;
	private BigDecimal preco;
	@ManyToOne
	@JoinColumn(name="conta_id")
	private Conta conta;
	private String observacao;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public FornecedorMaterial getFornecedorMaterial() {
		return fornecedorMaterial;
	}
	public void setFornecedorMaterial(FornecedorMaterial fornecedorMaterial) {
		this.fornecedorMaterial = fornecedorMaterial;
	}
	public BigDecimal getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public BigDecimal getPreco() {
		return preco;
	}
	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	
	
	
}
