package br.com.transmetais.bean;


//@Entity
//@Table(name="movimentacao_compra")
//@PrimaryKeyJoinColumn(name="compra_id")
public class MovimentacaoCompra {
	
	
	//@Column(name="compra_id")
	private Compra compra;

	public Compra getCompra() {
		return compra;
	}

	public void setCompra(Compra compra) {
		this.compra = compra;
	}

	
	
	
	

	
}
