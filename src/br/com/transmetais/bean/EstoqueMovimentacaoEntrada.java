package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="estoque_movimentacao_entrada")
@PrimaryKeyJoinColumn(name="estoque_movimentacao_id")
public class EstoqueMovimentacaoEntrada extends EstoqueMovimentacao{
	
	@ManyToOne
	@JoinColumn(name="compra_id")
	private Compra compra;
	
	public Compra getCompra() {
		return compra;
	}
	
	public void setCompra(Compra compra) {
		this.compra = compra;
	}

}
