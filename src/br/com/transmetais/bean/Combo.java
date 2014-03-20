package br.com.transmetais.bean;

public class Combo<T> {
	private T codigo;
	private String descricao;
	
	public Combo(T codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public T getCodigo() {
		return codigo;
	}

	public void setCodigo(T codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
