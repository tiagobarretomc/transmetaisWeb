package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="contaapagar_compra")
@PrimaryKeyJoinColumn(name="contaapagar_id")
public class ContaAPagarCompra extends ContaAPagar{
	
	
	@ManyToOne
	@JoinColumn(name="compra_id")
	private Compra compra;
	
//	@ManyToOne
//	@JoinColumn(name="parcela_compra_id")
//	private ParcelaCompra parcela;

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	
	
//	public ParcelaCompra getParcela() {
//		return parcela;
//	}
//	
//	public void setParcela(ParcelaCompra parcela) {
//		this.parcela = parcela;
//	}
}
