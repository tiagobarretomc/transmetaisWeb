package br.com.transmetais.type;

public enum TipoFaturamentoEnum {
	ADIANT("Adiantamentos Rotativos"), AVISTA("À Vista");
	
	private String descricao;
	TipoFaturamentoEnum tipo;
	private TipoFaturamentoEnum(String descricao) {
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
