package br.com.transmetais.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("E")
public class ComprovantePesagemEntrada extends ComprovantePesagem {
	
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	@OneToMany(fetch = FetchType.LAZY,orphanRemoval=true, cascade = {CascadeType.ALL}, targetEntity=ItemPesagem.class, mappedBy="comprovantePesagem")
	private List<ItemPesagemEntrada> itens;

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public List<ItemPesagemEntrada> getItens() {
		return itens;
	}

	public void setItens(List<ItemPesagemEntrada> itens) {
		this.itens = itens;
	}

}
