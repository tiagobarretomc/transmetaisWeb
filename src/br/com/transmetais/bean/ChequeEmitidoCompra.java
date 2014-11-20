package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="cheque_emitido_compra")
@PrimaryKeyJoinColumn(name="cheque_emitido_id")
public class ChequeEmitidoCompra extends ChequeEmitido{
	
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
