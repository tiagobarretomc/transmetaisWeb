package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="contaapagar_transferencia")
@PrimaryKeyJoinColumn(name="contaapagar_id")
public class ContaAPagarTransferencia extends ContaAPagar{
	
	@ManyToOne
	@JoinColumn(name="transferencia_id")
	private Transferencia transferencia;

	public Transferencia getTransferencia() {
		return transferencia;
	}

	public void setTransferencia(Transferencia transferencia) {
		this.transferencia = transferencia;
	}

	
}
