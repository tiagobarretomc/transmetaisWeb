package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.com.transmetais.type.FormaPagamentoEnum;
import br.com.transmetais.type.StatusVendaEnum;
import br.com.transmetais.type.TipoFreteCliente;
import br.com.transmetais.type.TipoPagamentoEnum;

@Entity
@Table(name="venda")
public class Venda {
	@Id
	@GeneratedValue
	private Long id;
	private Date data;
	
//	@ManyToOne
//	@JoinColumn(name="cliente_material_id")
//	private ClienteMaterial clienteMaterial;
	
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	private BigDecimal valor;
	@ManyToOne
	@JoinColumn(name="conta_id")
	private Conta conta;
	private String observacao;
	
	@Column(name="num_nf")
	private String numNf;
	
	@OneToMany(mappedBy="venda", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	private List<ItemVenda> itens;
	
	@Column(name="tipo_frete")
	@Enumerated(EnumType.STRING)
	private TipoFreteCliente tipoFrete;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private StatusVendaEnum status;
	
	@Enumerated(EnumType.STRING)
	@Column(name="modalidade_pagamento")
	private FormaPagamentoEnum modalidadePagamento;
	@Enumerated(EnumType.STRING)
	@Column(name="forma_pagamento")
	private TipoPagamentoEnum formaPagamento;
	@Column(name="data_recebimento")
	private Date dataRecebimento;
	@Column(name="data_cancelamento")
	private Date dataCancelamento;
	@Column(name="data_vencimento")
	private Date dataVencimento;
	@OneToMany(mappedBy="venda", fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval=true)
	private List<ParcelaVenda> parcelas;
	
	@ManyToOne
	@JoinColumn(name="comprovante_pesagem_id")
	private ComprovantePesagemSaida comprovantePesagem;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

//	public ClienteMaterial getClienteMaterial() {
//		return clienteMaterial;
//	}
//
//	public void setClienteMaterial(ClienteMaterial clienteMaterial) {
//		this.clienteMaterial = clienteMaterial;
//	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getNumNf() {
		return numNf;
	}

	public void setNumNf(String numNf) {
		this.numNf = numNf;
	}

	public List<ItemVenda> getItens() {
		return itens;
	}

	public void setItens(List<ItemVenda> itens) {
		this.itens = itens;
	}

	public TipoFreteCliente getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(TipoFreteCliente tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public StatusVendaEnum getStatus() {
		return status;
	}

	public void setStatus(StatusVendaEnum status) {
		this.status = status;
	}

	public FormaPagamentoEnum getModalidadePagamento() {
		return modalidadePagamento;
	}

	public void setModalidadePagamento(FormaPagamentoEnum modalidadePagamento) {
		this.modalidadePagamento = modalidadePagamento;
	}

	public TipoPagamentoEnum getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(TipoPagamentoEnum formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Date getDataRecebimento() {
		return dataRecebimento;
	}

	public void setDataRecebimento(Date dataRecebimento) {
		this.dataRecebimento = dataRecebimento;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	

	public List<ParcelaVenda> getParcelas() {
		return parcelas;
	}

	public void setParcelas(List<ParcelaVenda> parcelas) {
		this.parcelas = parcelas;
	}

	public ComprovantePesagemSaida getComprovantePesagem() {
		return comprovantePesagem;
	}

	public void setComprovantePesagem(ComprovantePesagemSaida comprovantePesagem) {
		this.comprovantePesagem = comprovantePesagem;
	}
	
	
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	
	
}
