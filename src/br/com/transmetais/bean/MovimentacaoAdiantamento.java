package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="movimentacao_adiantamento")
@PrimaryKeyJoinColumn(name="movimentacao_id")
public class MovimentacaoAdiantamento extends Movimentacao{
	
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
