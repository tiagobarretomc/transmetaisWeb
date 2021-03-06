package br.com.transmetais.type;

public enum SituacaoAdiantamentoEnum {
	
	A("Aberto"), P("Aprovado"), C("Cancelado"), PG("Pago");
	
	private String descricao;
	SituacaoAdiantamentoEnum tipo;
	private SituacaoAdiantamentoEnum(String descricao) {
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
