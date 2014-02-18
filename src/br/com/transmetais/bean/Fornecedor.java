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
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;



/**
 * @author tiagocouto
 *
 */
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
	
	
	private String apelido;
	private String status;
	
	@OneToMany(mappedBy="fornecedor")
	@LazyCollection(LazyCollectionOption.TRUE)
	@Fetch(FetchMode.SELECT)
	private List<FornecedorMaterial> materiaisFornecedores;
	
	@OneToMany(mappedBy="fornecedor")
	@LazyCollection(LazyCollectionOption.TRUE)
	@Fetch(FetchMode.SELECT)
	private List<InformacaoBancaria> informacoesBancarias;
	
	
	
	public List<InformacaoBancaria> getInformacoesBancarias() {
		return informacoesBancarias;
	}
	public void setInformacoesBancarias(
			List<InformacaoBancaria> informacoesBancarias) {
		this.informacoesBancarias = informacoesBancarias;
	}
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
	
	public String getCpfCnpj() {
		return cpfCnpj;
	}
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		
		
		
		return getId().equals(((Fornecedor)arg0).getId());
	}

}
