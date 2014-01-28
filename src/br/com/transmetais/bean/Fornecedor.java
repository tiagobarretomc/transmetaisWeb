package br.com.transmetais.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;



@Entity
@Table(name="fornecedor")
public class Fornecedor {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String nome;
	
	@Column(name="cpf_cnpj")
	private String cpfCnpj;
	@Column(name="telefone_celular")
	private String telefoneCelular;
	@Column(name="telefone_fixo")
	private String telefoneFixo;
	private  String email;
	private String endereco;
	@ManyToOne
	@JoinColumn(name="cidade_id")
	private Cidade cidade;
	
	private String banco;
	private String agencia;
	@Column(name="tipo_conta")
	private String tipoConta;
	@Column(name="conta")
	private String contaCorrente;
	@Column(name="cpf_cnpj_titular")
	private String cpfCnpjTitular;
	@Column(name="nome_titular")
	private String nomeTitular;
	private String apelido;
	@OneToMany(mappedBy="fornecedor")
	@Fetch(FetchMode.JOIN)
	private List<FornecedorMaterial> materiaisFornecedores;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	
	public String getCpfCnpjTitular() {
		return cpfCnpjTitular;
	}
	public void setCpfCnpjTitular(String cpfCnpjTitular) {
		this.cpfCnpjTitular = cpfCnpjTitular;
	}
	
	public String getTipoConta() {
		return tipoConta;
	}
	
	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getTelefoneCelular() {
		return telefoneCelular;
	}
	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}
	public String getTelefoneFixo() {
		return telefoneFixo;
	}
	public void setTelefoneFixo(String telefoneFixo) {
		this.telefoneFixo = telefoneFixo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public Cidade getCidade() {
		return cidade;
	}
	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
	
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getContaCorrente() {
		return contaCorrente;
	}
	public void setContaCorrente(String contaCorrente) {
		this.contaCorrente = contaCorrente;
	}
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}
	
	public String getNomeTitular() {
		return nomeTitular;
	}
	
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	
	public String getApelido() {
		return apelido;
	}
	
	public void setApelido(String apelido) {
		this.apelido = apelido;
	}
	public List<FornecedorMaterial> getMateriaisFornecedores() {
		return materiaisFornecedores;
	}
	
	public void setMateriaisFornecedores(
			List<FornecedorMaterial> materiaisFornecedores) {
		this.materiaisFornecedores = materiaisFornecedores;
	}

}
