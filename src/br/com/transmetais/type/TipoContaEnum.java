package br.com.transmetais.type;

public enum TipoContaEnum {
	
	B("Bancária"), FF("Fundo Fixo"), F("Fornecedor"), C("Cliente");
	
	private String descricao;
	TipoContaEnum tipo;
	private TipoContaEnum(String descricao) {
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
