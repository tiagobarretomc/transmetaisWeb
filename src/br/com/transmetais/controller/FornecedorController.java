package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Cidade;
import br.com.transmetais.bean.Conta;
import br.com.transmetais.bean.ContaBancaria;
import br.com.transmetais.bean.ContaFornecedor;
import br.com.transmetais.bean.Estado;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.Material;
import br.com.transmetais.dao.CidadeDAO;
import br.com.transmetais.dao.ContaDAO;
import br.com.transmetais.dao.EstadoDAO;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.FornecedorDaoImpl;
import br.com.transmetais.dao.impl.MaterialDaoImpl;
import br.com.transmetais.type.TipoFaturamentoEnum;

@Resource
public class FornecedorController {
	
	private final Result result;
	
	private FornecedorDaoImpl dao;
	
	private MaterialDaoImpl materialDao;
	
	private EstadoDAO estadoDAO;
	private CidadeDAO cidadeDAO;
	private ContaDAO contaDao;

	public FornecedorController(Result result, FornecedorDaoImpl dao, MaterialDaoImpl materialDao, EstadoDAO estadoDAO, CidadeDAO cidadeDAO,ContaDAO contaDao) {
		this.result = result;
		this.dao = dao;
		this.materialDao = materialDao;
		this.estadoDAO = estadoDAO;
		this.cidadeDAO = cidadeDAO;
		this.contaDao = contaDao;
		
	}
	
	
	@Path({"/fornecedor/","/fornecedor","/fornecedor/lista"})
	public List<Fornecedor> lista(){
		List<Fornecedor> lista = null;
		
		try {
			lista = dao.findAll();
			
			result.include("fornecedores",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public void adiciona(Fornecedor fornecedor) {
		try {
			Cidade cidade = cidadeDAO.findById(fornecedor.getCidade().getId());
			fornecedor.setCidade(cidade);
			if (fornecedor.getId() != null && fornecedor.getId()>0){
				//Aleterar Fornecedor
				
				if(fornecedor.getTipoFaturamento() == TipoFaturamentoEnum.ADIANT){
					Fornecedor fornecedorAnterior = dao.findById(fornecedor.getId());
					if (fornecedorAnterior.getTipoFaturamento() == null || fornecedorAnterior.getTipoFaturamento() != TipoFaturamentoEnum.ADIANT){
						if (fornecedorAnterior.getConta() == null){
							ContaFornecedor conta = new ContaFornecedor();
							conta.setDescricao(fornecedor.getApelido() + " - " + fornecedor.getNome());
							conta.setFornecedor(fornecedorAnterior);
							//conta.setBancaria(false);
							conta.setSaldo(fornecedor.getConta().getSaldo());
							conta.setSaldoInicial(fornecedor.getConta().getSaldo());
							conta.setSaldoInicial(new BigDecimal(0));
							contaDao.addEntity(conta);
							
							//Setando a Conta ao fornecedor por se tratar de uma relacao 1x1
							fornecedor.setConta(conta);
						}else{
							fornecedor.setConta(fornecedorAnterior.getConta());
						}
					}
				}
				
				
				dao.updateEntity(fornecedor);
				
			}else{
				Conta contaFornec = fornecedor.getConta();
				fornecedor.setConta(null);
				//Adicionar o fornecedor
				dao.addEntity(fornecedor);
				
				//Se tipo Faturamento for ADIANT significa que este fornecedor trabalha com adiantamentos rotativos.
				if(fornecedor.getTipoFaturamento() == TipoFaturamentoEnum.ADIANT){
					
					//Criando a Conta do Fornecedor
					ContaFornecedor conta = new ContaFornecedor();
					conta.setDescricao(fornecedor.getApelido());
					conta.setFornecedor(fornecedor);
					//conta.setBancaria(false);
					conta.setSaldo(contaFornec.getSaldo());
					conta.setSaldoInicial(contaFornec.getSaldo());
					contaDao.addEntity(conta);
					
					//Setando a Conta ao fornecedor por se tratar de uma relacao 1x1
					fornecedor.setConta(conta);
					dao.updateEntity(fornecedor);
				}
			}
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.redirectTo(FornecedorController.class).lista();
	  }
	
	
	@Path({"/fornecedor/{fornecedor.id}","/fornecedor/form","/fornecedor/novo"})
	public Fornecedor form(Fornecedor fornecedor){
		
		if (fornecedor != null && fornecedor.getId() != null && fornecedor.getId()>0){
			try {
				fornecedor = dao.findById(fornecedor.getId());
				//System.out.println(fornecedor.getInformacoesBancarias());
				
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		List<Material> materiais = null;
		List<Estado> estados = null;
		
		try {
			materiais = materialDao.findAll();
			estados = estadoDAO.findAll();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		result.include("tiposFaturamentos", TipoFaturamentoEnum.values());
		result.include("materiais", materiais);
		result.include("estados", estados);
		return fornecedor;
	}
	
	public void loadCidades(Integer id) throws Exception{
		Estado estadoSelecionado = null;
		
		
		if (id != null && id>0){
			estadoSelecionado = estadoDAO.findById(id);
		}
		
		result.use(json()).from(estadoSelecionado.getCidades()).serialize();
		
		result.nothing();
		
	}
	
	public void loadJsonCidade(Estado estado) {
        result.use(json()).from(estado.getCidades()).serialize();
    }
	
	public List<Fornecedor> loadListaFornecedor(Long fornecedorId){
		List<Fornecedor> lista = new ArrayList<Fornecedor>();
		
		try {
			
			lista.add(dao.findById(fornecedorId));
			
			result.include("fornecedores",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}

}
