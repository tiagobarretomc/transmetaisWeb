package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Cliente;
import br.com.transmetais.bean.ClienteMaterial;
import br.com.transmetais.dao.ClienteMaterialDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.type.StatusFornecedorMaterialEnum;
import br.com.transmetais.type.TipoFreteCliente;

@Component
public class ClienteMaterialDaoImpl  extends CrudDAOJPA<ClienteMaterial> implements ClienteMaterialDAO{
	
	public ClienteMaterial obterAtivoPorClienteAndMaterial(ClienteMaterial clienteMaterial) {
		
		EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(ClienteMaterial.class);
		ClienteMaterial result = null;
		
		crit.add(Restrictions.eq("status", StatusFornecedorMaterialEnum.ATIVO));
		crit.add(Restrictions.eq("cliente", clienteMaterial.getCliente()));
		crit.add(Restrictions.eq("material", clienteMaterial.getMaterial()));
		crit.add(Restrictions.eq("tipoFrete", clienteMaterial.getTipoFrete()));
		
		
		result =  (ClienteMaterial)crit.uniqueResult();
		
		
		
		return result;
		
	}
	
	public List<ClienteMaterial> obterAtivosPorFiltro(Cliente cliente, TipoFreteCliente tipoFrete) {
		
		EntityManager manager = factory.createEntityManager(); 
		
		
		Session session = (Session) manager.getDelegate();
		Criteria crit = session.createCriteria(ClienteMaterial.class);
		List<ClienteMaterial> result = null;
		
		crit.add(Restrictions.eq("status", StatusFornecedorMaterialEnum.ATIVO));
		crit.add(Restrictions.eq("cliente", cliente));
		
		if (tipoFrete != null){
			crit.add(Restrictions.eq("tipoFrete", tipoFrete));
		}
		
		result =  crit.list();
		
		
		
		return result;
		
	}
	
	
}
