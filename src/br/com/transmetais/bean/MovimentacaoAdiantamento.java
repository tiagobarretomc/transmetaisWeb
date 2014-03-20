package br.com.transmetais.bean;


//@Entity
//@Table(name="movimentacao_adiantamento")
//@PrimaryKeyJoinColumn(name="adiantamento_id")
public class MovimentacaoAdiantamento {
	
	//@Column(name="adiantamento_id")
	private Adiantamento adiantamento;

	public Adiantamento getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(Adiantamento adiantamento) {
		this.adiantamento = adiantamento;
	}
	
	
	

	
}
