package br.com.transmetais.type;

public enum TipoFreteEnum {
	FOB("Fob Transmetais"), CIF("Cif Transmetais"), POU("Posto na Usina"), CAC("Caminhão Carregado"), ENC("Echarutado");
	
	private String descricao;
	TipoFreteEnum tipo;
	private TipoFreteEnum(String descricao) {
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
