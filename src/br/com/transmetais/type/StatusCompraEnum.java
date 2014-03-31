package br.com.transmetais.type;

public enum StatusCompraEnum {
	
	A("Aberto"), P("Pago"), C("Cancelado");
	
	private String descricao;
	StatusCompraEnum tipo;
	private StatusCompraEnum(String descricao) {
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
