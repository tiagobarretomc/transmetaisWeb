package br.com.transmetais.dao;

import java.util.List;

import br.com.transmetais.bean.Cliente;
import br.com.transmetais.bean.ClienteMaterial;
import br.com.transmetais.dao.commons.CrudDAO;
import br.com.transmetais.type.TipoFreteCliente;

public interface ClienteMaterialDAO  extends CrudDAO<ClienteMaterial>{
	
	public ClienteMaterial obterAtivoPorClienteAndMaterial(ClienteMaterial clienteMaterial);
	public List<ClienteMaterial> obterAtivosPorFiltro(Cliente cliente, TipoFreteCliente tipoFrete);

}
