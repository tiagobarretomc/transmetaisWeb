package br.com.transmetais.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
@DiscriminatorValue("S")
public class ComprovantePesagemSaida extends ComprovantePesagem {
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, targetEntity=ItemPesagem.class,mappedBy="comprovantePesagem")
	private List<ItemPesagemSaida> itens;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemPesagemSaida> getItens() {
		return itens;
	}

	public void setItens(List<ItemPesagemSaida> itens) {
		this.itens = itens;
	}
}
