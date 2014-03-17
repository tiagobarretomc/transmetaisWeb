package br.com.transmetais.bean;

import java.util.Set;

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
	private UnidadeMedida unidadeMedida;
	@ManyToOne
	@JoinColumn(name="grupo_material_id")
	private GrupoMaterial grupoMaterial;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "regra_tributacao_produto", joinColumns = { 
			@JoinColumn(name = "regra_tributacao_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "produto_id", 
					nullable = false, updatable = false) })
	private Set<RegraTributacao> regrasTributacao;
	
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

}
