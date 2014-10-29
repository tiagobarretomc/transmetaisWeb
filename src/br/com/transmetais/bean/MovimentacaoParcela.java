package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="movimentacao_parcela")
@PrimaryKeyJoinColumn(name="movimentacao_id")
public class MovimentacaoParcela extends Movimentacao{
	
	@ManyToOne
	@JoinColumn(name="parcela_id")
	private Parcela parcela;

	public Parcela getParcela() {
		return parcela;
	}
	public void setParcela(Parcela parcela) {
		this.parcela = parcela;
	}
	
	
	

	
}
