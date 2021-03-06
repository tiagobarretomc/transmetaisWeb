package br.com.transmetais.type;

public enum FormaPagamentoEnum {
	
	D("Dinheiro"), C("Cheque"), T("Transação Bancária"), B("Boleto Bancário"), K("Cartão de Crédito");
	
	private String descricao;
	FormaPagamentoEnum tipo;
	private FormaPagamentoEnum(String descricao) {
		this.setDescricao(descricao);
		
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public String getName(){
		return this.name();
	}
	

}
