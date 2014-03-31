package br.com.transmetais.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="regra_tributacao")
public class RegraTributacao {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="tipo_operacao_id")
	private TipoOperacao tipoOperacao;
	
	@ManyToOne
	@JoinColumn(name="cfop_id")
	private Cfop cfop;
	
	@ManyToOne
	@JoinColumn(name="situacao_tributaria_id")
	private SituacaoTributaria situacaoTributaria;
	
	@ManyToOne
	@JoinColumn(name="origem_mercadoria_id")
	private OrigemMercadoria origemMercadoria;
	
	@ManyToOne
	@JoinColumn(name="base_calculo_tributacao_id")
	private BaseDeCalculo baseCalculo;
	
	@ManyToOne
	@JoinColumn(name="base_calculo_tributacao_st_id")
	private BaseDeCalculoST baseCalculoST;
	
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
	
	public TipoOperacao getTipoOperacao() {
		return tipoOperacao;
	}
	public void setTipoOperacao(TipoOperacao tipoOperacao) {
		this.tipoOperacao = tipoOperacao;
	}
	public Cfop getCfop() {
		return cfop;
	}
	public void setCfop(Cfop cfop) {
		this.cfop = cfop;
	}
	public SituacaoTributaria getSituacaoTributaria() {
		return situacaoTributaria;
	}
	public void setSituacaoTributaria(SituacaoTributaria situacaoTributaria) {
		this.situacaoTributaria = situacaoTributaria;
	}
	public OrigemMercadoria getOrigemMercadoria() {
		return origemMercadoria;
	}
	public void setOrigemMercadoria(OrigemMercadoria origemMercadoria) {
		this.origemMercadoria = origemMercadoria;
	}
	public BaseDeCalculo getBaseCalculo() {
		return baseCalculo;
	}
	public void setBaseCalculo(BaseDeCalculo baseCalculo) {
		this.baseCalculo = baseCalculo;
	}
	public BaseDeCalculoST getBaseCalculoST() {
		return baseCalculoST;
	}
	public void setBaseCalculoST(BaseDeCalculoST baseCalculoST) {
		this.baseCalculoST = baseCalculoST;
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
