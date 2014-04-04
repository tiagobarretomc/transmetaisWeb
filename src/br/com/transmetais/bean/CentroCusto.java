package br.com.transmetais.bean;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name="centro_custo")
public class CentroCusto {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Long numero;
	private String descricao;
	@OneToMany(mappedBy="centroCusto")
	@LazyCollection(LazyCollectionOption.TRUE)
	@Fetch(FetchMode.SELECT)
	private List<CentroAplicacao> centrosAplicacoes;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Long getNumero() {
		return numero;
	}
	public void setNumero(Long numero) {
		this.numero = numero;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public List<CentroAplicacao> getCentrosAplicacoes() {
		return centrosAplicacoes;
	}
	public void setCentrosAplicacoes(List<CentroAplicacao> centrosAplicacoes) {
		this.centrosAplicacoes = centrosAplicacoes;
	}
	
	
	

}
