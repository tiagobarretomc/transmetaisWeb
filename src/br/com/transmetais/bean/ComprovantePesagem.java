package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import br.com.transmetais.type.TipoFreteEnum;
@Entity
@Table(name="comprovante_pesagem")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", length = 1, discriminatorType = DiscriminatorType.STRING)
public abstract class ComprovantePesagem {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="dt_emissao")
	private Date dataEmissao;
	@Column(name="nr_ticket")
	@NotNull
	private String numeroTicket;
	@Column(name="balanca")
	private String balanca;
	@Column(name="nm_transportador")
	private String nomeTransportador;
	@NotNull
	@Column(name="placa_veiculo")
	private String placaVeiculo;
	@Column(name="tipo_frete")
	@Enumerated(EnumType.STRING)
	private TipoFreteEnum tipoFrete;
	@ManyToOne
	@JoinColumn(name="tipo_veiculo_id")
	private TipoVeiculo tipoVeiculo;
	@NotNull
	@Column(name="tara_veiculo")
	private BigDecimal taraVeiculo;
	@Column(name="peso_bruto_veiculo")
	private BigDecimal pesoBruto;
	@Column(name="peso_liq_veiculo")
	private BigDecimal pesoLiquido;
	@Column(name="perc_impureza")
	private Double percentualImpureza;
	@Column(name="peso_impureza")
	private BigDecimal pesoImpureza;
	@OneToOne(cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinColumn(name="arquivo_id")
	private Arquivo arquivo;
	
	/**
	 * Para filtro
	 */
	@Transient
	private Date dataInicio;
	@Transient
	private Date dataFim;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getNumeroTicket() {
		return numeroTicket;
	}

	public void setNumeroTicket(String numeroTicket) {
		this.numeroTicket = numeroTicket;
	}

	public String getBalanca() {
		return balanca;
	}

	public void setBalanca(String balanca) {
		this.balanca = balanca;
	}

	public String getNomeTransportador() {
		return nomeTransportador;
	}

	public void setNomeTransportador(String nomeTransportador) {
		this.nomeTransportador = nomeTransportador;
	}

	public String getPlacaVeiculo() {
		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}

	public TipoFreteEnum getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(TipoFreteEnum tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public TipoVeiculo getTipoVeiculo() {
		return tipoVeiculo;
	}

	public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}

	public BigDecimal getTaraVeiculo() {
		return taraVeiculo;
	}

	public void setTaraVeiculo(BigDecimal taraVeiculo) {
		this.taraVeiculo = taraVeiculo;
	}

	public BigDecimal getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(BigDecimal pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public BigDecimal getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(BigDecimal pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public Double getPercentualImpureza() {
		return percentualImpureza;
	}

	public void setPercentualImpureza(Double percentualImpureza) {
		this.percentualImpureza = percentualImpureza;
	}

	public BigDecimal getPesoImpureza() {
		return pesoImpureza;
	}

	public void setPesoImpureza(BigDecimal pesoImpureza) {
		this.pesoImpureza = pesoImpureza;
	}

	public Arquivo getArquivo() {
		return arquivo;
	}

	public void setArquivo(Arquivo arquivo) {
		this.arquivo = arquivo;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
