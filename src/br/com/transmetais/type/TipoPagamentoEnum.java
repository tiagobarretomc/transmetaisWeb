package br.com.transmetais.type;

public enum TipoPagamentoEnum {
	
	V("A Vista"), P("A Prazo"), A("Antecipada");
	
	private String descricao;
	TipoPagamentoEnum tipo;
	private TipoPagamentoEnum(String descricao) {
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
