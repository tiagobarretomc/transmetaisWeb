package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="movimentacao_contaapagar")
@PrimaryKeyJoinColumn(name="movimentacao_id")
public class MovimentacaoContasAPagar extends Movimentacao{
	
	
	@ManyToOne
	@JoinColumn(name="contaapagar_id")
	private ContaAPagar contaAPagar;

	public ContaAPagar getContaAPagar() {
		return contaAPagar;
	}

	public void setContaAPagar(ContaAPagar contaAPagar) {
		this.contaAPagar = contaAPagar;
	}
	
}
