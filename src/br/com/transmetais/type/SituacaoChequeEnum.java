package br.com.transmetais.type;

public enum SituacaoChequeEnum {
	
	A("Aguardando Compensação"), C("Compensado"), K("Cancelado");
	
	private String descricao;
	SituacaoChequeEnum tipo;
	private SituacaoChequeEnum(String descricao) {
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
