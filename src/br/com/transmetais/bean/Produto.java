package br.com.transmetais.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;



@Entity
@Table(name="produto")
public class Produto {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String codigo;
	private String descricao;
	@ManyToOne
	@JoinColumn(name="unidade_medida_id")
	@XmlTransient
	private UnidadeMedida unidadeMedida;
	@ManyToOne
	@JoinColumn(name="grupo_material_id")
	@XmlTransient
	private GrupoMaterial grupoMaterial;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinTable(name = "regra_tributacao_produto", joinColumns = { 
			@JoinColumn(name = "produto_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "regra_tributacao_id", 
					nullable = false, updatable = false) })
	@XmlTransient
	private List<RegraTributacao> regrasTributacao = new ArrayList<RegraTributacao>();
	private String ncm;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public UnidadeMedida getUnidadeMedida() {
		return unidadeMedida;
	}
	public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}
	public String getNcm() {
		return ncm;
	}
	
	public void setNcm(String ncm) {
		this.ncm = ncm;
	}
	public GrupoMaterial getGrupoMaterial() {
		return grupoMaterial;
	}
	public void setGrupoMaterial(GrupoMaterial grupoMaterial) {
		this.grupoMaterial = grupoMaterial;
	}
	public List<RegraTributacao> getRegrasTributacao() {
		return regrasTributacao;
	}
	public void setRegrasTributacao(List<RegraTributacao> regrasTributacao) {
		this.regrasTributacao = regrasTributacao;
	}

}
