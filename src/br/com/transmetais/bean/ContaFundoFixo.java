package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="conta_fundo_fixo")
@PrimaryKeyJoinColumn(name="conta_id")
public class ContaFundoFixo extends Conta{
	
	@ManyToOne
	@JoinColumn(name="unidade_id")
	private Unidade unidade;

	public Unidade getUnidade() {
		return unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

}
