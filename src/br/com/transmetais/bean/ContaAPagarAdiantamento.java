package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="contaapagar_adiantamento")
@PrimaryKeyJoinColumn(name="contaapagar_id")
public class ContaAPagarAdiantamento extends ContaAPagar{
	
	@ManyToOne
	@JoinColumn(name="adiantamento_id")
	private Adiantamento adiantamento;

	public Adiantamento getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(Adiantamento adiantamento) {
		this.adiantamento = adiantamento;
	}
	
	
	

	
}
