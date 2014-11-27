package br.com.transmetais.bean;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
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
@Table(name="item_pesagem")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
public abstract class ItemPesagem {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="peso_perc")
	private Double pesoPercentual;
	@Column(name="peso_liquido")
	private BigDecimal pesoLiquido;
	@ManyToOne
	@JoinColumn(name="comprov_pesagem_id")
	private ComprovantePesagem comprovantePesagem;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getPesoLiquido() {
		return pesoLiquido;
	}
	public void setPesoLiquido(BigDecimal pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}
	public Double getPesoPercentual() {
		return pesoPercentual;
	}
	public void setPesoPercentual(Double pesoPercentual) {
		this.pesoPercentual = pesoPercentual;
	}
	public ComprovantePesagem getComprovantePesagem() {
		return comprovantePesagem;
	}
	public void setComprovantePesagem(ComprovantePesagem comprovantePesagem) {
		this.comprovantePesagem = comprovantePesagem;
	}
	
	
	
}
