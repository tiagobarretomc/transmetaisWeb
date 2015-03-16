package br.com.transmetais.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name="usuario")
public class Usuario {
	@Id
	@GeneratedValue
	private Long id;
	private String login;
	private String nome;
	private String email;
	private String senha;
	@Column(name="senha_inicial")
	private String senhaInicial;
	@Column(name="ativo")
	private boolean ativo;
	@OneToMany(mappedBy="usuario",fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	private List<UsuarioGrupo> gruposUsuario = new ArrayList<UsuarioGrupo>();

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSenhaInicial() {
		return senhaInicial;
	}
	public void setSenhaInicial(String senhaInicial) {
		this.senhaInicial = senhaInicial;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public List<UsuarioGrupo> getGruposUsuario() {
		return gruposUsuario;
	}
	public void setGruposUsuario(List<UsuarioGrupo> gruposUsuario) {
		this.gruposUsuario = gruposUsuario;
	}
	
}
