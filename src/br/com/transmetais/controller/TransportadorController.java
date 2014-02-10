package br.com.transmetais.controller;

import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.transmetais.bean.Transportador;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.dao.impl.TransportadorDaoImpl;
@Resource
public class TransportadorController {
	
	private final Result result;
	
	private TransportadorDaoImpl dao;
	
	
	
	public TransportadorController(Result result, TransportadorDaoImpl dao) {
		this.result = result;
		this.dao = dao;
		
	}
	
	@Path({"/transportador/","/transportador","/transportador/lista"})
	public List<Transportador> lista(){
		List<Transportador> lista = null;
		
		try {
			lista = dao.findAll();
			//System.out.println();
			
			
			result.include("transportadores",lista);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return lista;
	}

}
