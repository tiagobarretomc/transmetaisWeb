package br.com.transmetais.type;

public enum StatusFornecedorEnum {
	
	ATIVO, INATIVO, BLOQUEADO;
	
	public String getName(){
		return this.name();
	}

}
