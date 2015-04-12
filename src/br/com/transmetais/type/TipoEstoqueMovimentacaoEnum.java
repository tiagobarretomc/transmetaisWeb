package br.com.transmetais.type;

public enum TipoEstoqueMovimentacaoEnum {
	E("Entrada"), S("Saída");
	private String descricao;
	TipoEstoqueMovimentacaoEnum tipo;
	private TipoEstoqueMovimentacaoEnum(String descricao) {
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
