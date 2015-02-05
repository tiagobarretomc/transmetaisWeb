package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="contaareceber_venda")
@PrimaryKeyJoinColumn(name="contaareceber_id")
public class ContaAReceberVenda extends ContaAReceber{
	
	
	@ManyToOne
	@JoinColumn(name="venda_id")
	private Venda venda;
	
	public Venda getVenda() {
		return venda;
	}
	
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
}
