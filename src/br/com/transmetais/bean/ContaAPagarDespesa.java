package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="contaapagar_despesa")
@PrimaryKeyJoinColumn(name="contaapagar_id")
public class ContaAPagarDespesa extends ContaAPagar{
	
	
	@ManyToOne
	@JoinColumn(name="despesa_id")
	private Despesa despesa;

	public Despesa getDespesa() {
		return despesa;
	}

	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

	
	
}
