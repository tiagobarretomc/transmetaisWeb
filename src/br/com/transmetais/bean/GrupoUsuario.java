package br.com.transmetais.bean;

public enum GrupoUsuario {
	ADMINISTRADOR(1), FINANCEIRO(2), COMERCIAL(3), FORNECEDOR(4);
	private int codigo;
	
	GrupoUsuario(int codigo){
		this.codigo = codigo;
	}
	public int getCodigo() {
		return codigo;
	}

	
}
