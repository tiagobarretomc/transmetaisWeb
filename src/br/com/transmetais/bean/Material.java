package br.com.transmetais.bean;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="material")
public class Material {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String sigla;
	private String descricao;
	@ManyToOne
	@JoinColumn(name="unidade_medida_id")
	private UnidadeMedida unidadeMedida;
	
	@ManyToOne
	@JoinColumn(name="grupo_material_id")
	private GrupoMaterial grupoMaterial;
	
	@ManyToOne
	@JoinColumn(name="conta_contabil_id")
	private ContaContabil contaContabil;
	
	private String ncm;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
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
	public GrupoMaterial getGrupoMaterial() {
		return grupoMaterial;
	}
	public void setGrupoMaterial(GrupoMaterial grupoMaterial) {
		this.grupoMaterial = grupoMaterial;
	}
	
	public String getNcm() {
		return ncm;
	}
	
	public void setNcm(String ncm) {
		this.ncm = ncm;
	}
	
	public ContaContabil getContaContabil() {
		return contaContabil;
	}
	
	public void setContaContabil(ContaContabil contaContabil) {
		this.contaContabil = contaContabil;
	}

}
