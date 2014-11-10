package br.com.transmetais.bean;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.transmetais.type.SituacaoChequeEnum;

@Entity
@Table(name="cheque_emitido")
@Inheritance(strategy = InheritanceType.JOINED)
public class ChequeEmitido {
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="conta_id")
	private Conta conta;
	private Date data;
	private BigDecimal valor;
	@Column(name="numero_cheque")
	private String numeroCheque;
	@Enumerated(EnumType.STRING)
	private SituacaoChequeEnum status;
	@Column(name="data_compensacao")
	private Date dataCompensacao;
	@Column(name="motivo_cancelamento")
	private String motivoCancelamento;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public String getNumeroCheque() {
		return numeroCheque;
	}
	public void setNumeroCheque(String numeroCheque) {
		this.numeroCheque = numeroCheque;
	}
	
	public SituacaoChequeEnum getStatus() {
		return status;
	}
	
	public void setStatus(SituacaoChequeEnum status) {
		this.status = status;
	}
	
	public Date getDataCompensacao() {
		return dataCompensacao;
	}
	
	public void setDataCompensacao(Date dataCompensacao) {
		this.dataCompensacao = dataCompensacao;
	}
	
	public String getMotivoCancelamento() {
		return motivoCancelamento;
	}
	
	public void setMotivoCancelamento(String motivoCancelamento) {
		this.motivoCancelamento = motivoCancelamento;
	}
}
