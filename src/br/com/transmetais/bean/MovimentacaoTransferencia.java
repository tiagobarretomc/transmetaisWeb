package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;



@Entity
@Table(name="movimentacao_transferencia")
@PrimaryKeyJoinColumn(name="movimentacao_id")
public class MovimentacaoTransferencia extends Movimentacao{
	
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
