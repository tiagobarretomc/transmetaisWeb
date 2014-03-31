package br.com.transmetais.type;

public enum StatusMovimentacaoEnum {
	
	A("Aberto"), P("Pago"), C("Cancelado");
	
	private String descricao;
	StatusMovimentacaoEnum tipo;
	private StatusMovimentacaoEnum(String descricao) {
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
