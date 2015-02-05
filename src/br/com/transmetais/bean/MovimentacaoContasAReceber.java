package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="movimentacao_contaareceber")
@PrimaryKeyJoinColumn(name="movimentacao_id")
public class MovimentacaoContasAReceber extends Movimentacao{
	
	
	@ManyToOne
	@JoinColumn(name="contaareceber_id")
	private ContaAReceber contaAReceber;

	
	public ContaAReceber getContaAReceber() {
		return contaAReceber;
	}
	
	public void setContaAReceber(ContaAReceber contaAReceber) {
		this.contaAReceber = contaAReceber;
	}
}
