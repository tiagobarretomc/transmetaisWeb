package br.com.transmetais.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transportador")
public class Transportador {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="nome_motorista")
	private String nomeMotorista;
	@Column(name="cpf_motorista")
	private String cpfMotorista;
	@Column(name="endereco_motorista")
	private String enderecoMotorista;
	@Column(name="telefone_motorista")
	private String telefoneMotorista;
	@Column(name="nome_proprietario")
	private String nomeProprietario;
	@Column(name="cpf_cnpj_proprietario")
	private String cpfCnpjProprietario;
	@Column(name="telefone_proprietario")
	private String telefoneProprietario;
	@Column(name="placa_veiculo")
	private String placaVeiculo;	
	@Column(name="renavan")
	private String renavan;
	
	@Column(name="rntrc")
	private String rntrc;
	@Column(name="tipo_veiculo")
	private String tipoVeiculo;
	@Column(name="uf_veiculo")
	private String ufVeiculo;
	@Column(name="banco")
	private String banco;
	@Column(name="agencia")
	private String agencia;
	@Column(name="conta")
	private String conta;
	@Column(name="tipo_conta")
	private String tipoConta;
	@Column(name="nome_titular")
	private String nomeTitular;
	@Column(name="cpf_cnpj_titular")
	private String cpfCnpjTitular;
	@Column(name="valor_frete")
	private Double valorFrete;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNomeMotorista() {
		return nomeMotorista;
	}
	public void setNomeMotorista(String nomeMotorista) {
		this.nomeMotorista = nomeMotorista;
	}
	public String getCpfMotorista() {
		return cpfMotorista;
	}
	public void setCpfMotorista(String cpfMotorista) {
		this.cpfMotorista = cpfMotorista;
	}
	public String getEnderecoMotorista() {
		return enderecoMotorista;
	}
	public void setEnderecoMotorista(String enderecoMotorista) {
		this.enderecoMotorista = enderecoMotorista;
	}
	public String getTelefoneMotorista() {
		return telefoneMotorista;
	}
	public void setTelefoneMotorista(String telefoneMotorista) {
		this.telefoneMotorista = telefoneMotorista;
	}
	public String getNomeProprietario() {
		return nomeProprietario;
	}
	public void setNomeProprietario(String nomeProprietario) {
		this.nomeProprietario = nomeProprietario;
	}
	public String getCpfCnpjProprietario() {
		return cpfCnpjProprietario;
	}
	public void setCpfCnpjProprietario(String cpfCnpjProprietario) {
		this.cpfCnpjProprietario = cpfCnpjProprietario;
	}
	public String getTelefoneProprietario() {
		return telefoneProprietario;
	}
	public void setTelefoneProprietario(String telefoneProprietario) {
		this.telefoneProprietario = telefoneProprietario;
	}
	public String getPlacaVeiculo() {
		return placaVeiculo;
	}
	public void setPlacaVeiculo(String placaVeiculo) {
		this.placaVeiculo = placaVeiculo;
	}
	public String getRenavan() {
		return renavan;
	}
	public void setRenavan(String renavan) {
		this.renavan = renavan;
	}
	public String getRntrc() {
		return rntrc;
	}
	public void setRntrc(String rntrc) {
		this.rntrc = rntrc;
	}
	public String getTipoVeiculo() {
		return tipoVeiculo;
	}
	public void setTipoVeiculo(String tipoVeiculo) {
		this.tipoVeiculo = tipoVeiculo;
	}
	public String getUfVeiculo() {
		return ufVeiculo;
	}
	public void setUfVeiculo(String ufVeiculo) {
		this.ufVeiculo = ufVeiculo;
	}
	public String getBanco() {
		return banco;
	}
	public void setBanco(String banco) {
		this.banco = banco;
	}
	public String getAgencia() {
		return agencia;
	}
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}
	public String getConta() {
		return conta;
	}
	public void setConta(String conta) {
		this.conta = conta;
	}
	public String getTipoConta() {
		return tipoConta;
	}
	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}
	public String getNomeTitular() {
		return nomeTitular;
	}
	public void setNomeTitular(String nomeTitular) {
		this.nomeTitular = nomeTitular;
	}
	public String getCpfCnpjTitular() {
		return cpfCnpjTitular;
	}
	public void setCpfCnpjTitular(String cpfCnpjTitular) {
		this.cpfCnpjTitular = cpfCnpjTitular;
	}
	public Double getValorFrete() {
		return valorFrete;
	}
	public void setValorFrete(Double valorFrete) {
		this.valorFrete = valorFrete;
	}
	
	
	
	
}
