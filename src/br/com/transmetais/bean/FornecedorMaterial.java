package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name="fornecedor_material")
public class FornecedorMaterial {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name="fornecedor_id")
	private Fornecedor fornecedor;
	@ManyToOne
	@JoinColumn(name="material_id")
	private Material material;
	private BigDecimal valor;
	private String status;
	@Column(name="tipo_frete")
	private String tipoFrete;
	@Column(name="inicio_vigencia")
	private Date inicioVigencia;
	@Column(name="fim_vigencia")
	private Date fimVigencia;
	
	
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTipoFrete() {
		return tipoFrete;
	}
	public void setTipoFrete(String tipoFrete) {
		this.tipoFrete = tipoFrete;
	}
	public Date getInicioVigencia() {
		return inicioVigencia;
	}
	public void setInicioVigencia(Date inicioVigencia) {
		this.inicioVigencia = inicioVigencia;
	}
	public Date getFimVigencia() {
		return fimVigencia;
	}
	public void setFimVigencia(Date fimVigencia) {
		this.fimVigencia = fimVigencia;
	}
	
	
	

}
