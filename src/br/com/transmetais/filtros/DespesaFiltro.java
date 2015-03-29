package br.com.transmetais.filtros;

import java.util.Date;

import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.type.StatusDespesaEnum;

public class DespesaFiltro {
	
	private StatusDespesaEnum status;
	private Fornecedor fornecedor;
	private Date dataInicio;
	private Date dataFim;
	private String descricao;
	public StatusDespesaEnum getStatus() {
		return status;
	}
	public void setStatus(StatusDespesaEnum status) {
		this.status = status;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
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
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	

}
