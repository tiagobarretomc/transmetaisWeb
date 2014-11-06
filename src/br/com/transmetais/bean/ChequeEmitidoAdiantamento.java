package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="cheque_emitido_adiantamento")
@PrimaryKeyJoinColumn(name="cheque_emitido_id")
public class ChequeEmitidoAdiantamento extends ChequeEmitido{
	
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
