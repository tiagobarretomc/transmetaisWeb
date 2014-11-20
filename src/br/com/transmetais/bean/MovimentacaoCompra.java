package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="movimentacao_compra")
@PrimaryKeyJoinColumn(name="movimentacao_id")
public class MovimentacaoCompra extends Movimentacao{
	
	@ManyToOne
	@JoinColumn(name="compra_id")
	private Compra compra;

	public Compra getCompra() {
		return compra;
	}
	
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	
	

	
}