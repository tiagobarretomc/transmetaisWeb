package br.com.transmetais.controller;

import static br.com.caelum.vraptor.view.Results.json;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Estado;
import br.com.transmetais.dao.EstadoDAO;

@Resource
public class EnderecoController {
	private final Result result;
	private EstadoDAO estadoDAO;
	
	public EnderecoController(Result result, EstadoDAO estadoDAO) {
		this.result = result;
		this.estadoDAO = estadoDAO;
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
}
