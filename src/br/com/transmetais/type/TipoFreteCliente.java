package br.com.transmetais.type;

public enum TipoFreteCliente {
	
FOB("Fob"), CIF("Cif");
	
	private String descricao;
	TipoFreteEnum tipo;
	private TipoFreteCliente(String descricao) {
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
