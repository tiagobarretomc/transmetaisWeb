package br.com.transmetais.type;

public enum TipoOperacaoEnum {
	C("Crédito"), D("Débito");
	private String descricao;
	TipoOperacaoEnum tipo;
	private TipoOperacaoEnum(String descricao) {
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
