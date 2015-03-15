package br.com.transmetais.type;

public enum StatusVendaEnum {
	
	A("Aberto"), P("Pago"), C("Cancelado");
	
	private String descricao;
	StatusVendaEnum tipo;
	private StatusVendaEnum(String descricao) {
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
