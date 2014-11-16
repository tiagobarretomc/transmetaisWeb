package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="parcela_compra")
@PrimaryKeyJoinColumn(name="parcela_id")
public class ParcelaCompra extends Parcela{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="compra_id")
	private Compra compra;
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}


}
