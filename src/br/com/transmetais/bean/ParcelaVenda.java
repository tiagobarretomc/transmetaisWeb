package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="parcela_venda")
@PrimaryKeyJoinColumn(name="parcela_id")
public class ParcelaVenda extends Parcela{
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="venda_id")
	private Venda venda;
	
	public Venda getVenda() {
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}

}
