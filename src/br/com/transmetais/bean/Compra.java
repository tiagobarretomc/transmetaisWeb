package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="compra")
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
	
	@Column(name="num_nf")
	private String numNf;
	
	@OneToMany(mappedBy="compra")
	@LazyCollection(LazyCollectionOption.TRUE)
	@Fetch(FetchMode.SELECT)
	private List<ItemCompra> itens;
	
	
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
	public String getNumNf() {
		return numNf;
	}
	public void setNumNf(String numNf) {
		this.numNf = numNf;
	}
	public List<ItemCompra> getItens() {
		return itens;
	}
	public void setItens(List<ItemCompra> itens) {
		this.itens = itens;
	}
	
	
	
}
