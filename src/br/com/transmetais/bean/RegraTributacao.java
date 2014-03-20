package br.com.transmetais.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="regra_tributacao")
public class RegraTributacao {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="tipo_operacao_cd")
	private Integer codTipoOperacao;
	@Column(name="cfop_cd")
	private Integer codCfop;
	@Column(name="situacao_tributaria_cd")
	private Integer codSituacaoTributaria;
	@Column(name="origem_mercadoria_cd")
	private Integer codOrigemMercadoria;
	@Column(name="base_calculo_tributacao_cd")
	private Integer codBaseCalculo;
	@Column(name="base_calculo_tributacao_st_cd")
	private Integer codBaseCalculoST;
	@Column(name="aliquota")
	private Double  aliquota;
	@Column(name="credito")
	private Double credito;
	@Column(name="aliquota_st")
	private Double aliquotaST;
	@Column(name="credito_st")
	private Double creditoST;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCodTipoOperacao() {
		return codTipoOperacao;
	}
	public void setCodTipoOperacao(Integer codTipoOperacao) {
		this.codTipoOperacao = codTipoOperacao;
	}
	public Integer getCodCfop() {
		return codCfop;
	}
	public void setCodCfop(Integer codCfop) {
		this.codCfop = codCfop;
	}
	public Integer getCodSituacaoTributaria() {
		return codSituacaoTributaria;
	}
	public void setCodSituacaoTributaria(Integer codSituacaoTributaria) {
		this.codSituacaoTributaria = codSituacaoTributaria;
	}
	public Integer getCodOrigemMercadoria() {
		return codOrigemMercadoria;
	}
	public void setCodOrigemMercadoria(Integer codOrigemMercadoria) {
		this.codOrigemMercadoria = codOrigemMercadoria;
	}
	public Integer getCodBaseCalculo() {
		return codBaseCalculo;
	}
	public void setCodBaseCalculo(Integer codBaseCalculo) {
		this.codBaseCalculo = codBaseCalculo;
	}
	public Integer getCodBaseCalculoST() {
		return codBaseCalculoST;
	}
	public void setCodBaseCalculoST(Integer codBaseCalculoST) {
		this.codBaseCalculoST = codBaseCalculoST;
	}
	public Double getAliquota() {
		return aliquota;
	}
	public void setAliquota(Double aliquota) {
		this.aliquota = aliquota;
	}
	public Double getCredito() {
		return credito;
	}
	public void setCredito(Double credito) {
		this.credito = credito;
	}
	public Double getAliquotaST() {
		return aliquotaST;
	}
	public void setAliquotaST(Double aliquotaST) {
		this.aliquotaST = aliquotaST;
	}
	public Double getCreditoST() {
		return creditoST;
	}
	public void setCreditoST(Double creditoST) {
		this.creditoST = creditoST;
	}
	
	
}
