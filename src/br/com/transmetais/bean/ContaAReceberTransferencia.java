package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="contaareceber_transferencia")
@PrimaryKeyJoinColumn(name="contaareceber_id")
public class ContaAReceberTransferencia extends ContaAReceber{
	
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
