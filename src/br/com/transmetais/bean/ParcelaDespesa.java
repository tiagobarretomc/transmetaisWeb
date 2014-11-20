package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="parcela_despesa")
@PrimaryKeyJoinColumn(name="parcela_id")
public class ParcelaDespesa extends Parcela{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="despesa_id")
	private Despesa despesa;
	
	public Despesa getDespesa() {
		return despesa;
	}
	
	public void setDespesa(Despesa despesa) {
		this.despesa = despesa;
	}

}
