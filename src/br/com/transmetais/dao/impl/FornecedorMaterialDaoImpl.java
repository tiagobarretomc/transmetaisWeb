package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Fornecedor;
import br.com.transmetais.bean.FornecedorMaterial;
import br.com.transmetais.dao.FornecedorMaterialDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.type.StatusFornecedorMaterialEnum;

@Component
public class FornecedorMaterialDaoImpl  extends CrudDAOJPA<FornecedorMaterial> implements FornecedorMaterialDAO{
private static FornecedorMaterialDaoImpl instance = null;
	
	public  static  FornecedorMaterialDaoImpl getInstance(){
		if (instance == null)
			instance = new FornecedorMaterialDaoImpl(); 
		return instance;
	}
	
	
		public List<FornecedorMaterial> obterAtivosPorFornecedor(Fornecedor fornecedor) {
			
			EntityManager manager = factory.createEntityManager(); 
			
			
			Session session = (Session) manager.getDelegate();
			Criteria crit = session.createCriteria(FornecedorMaterial.class);
			List<FornecedorMaterial> result = null;
			
			crit.add(Restrictions.eq("status", StatusFornecedorMaterialEnum.ATIVO));
			crit.add(Restrictions.eq("fornecedor", fornecedor));
			
			result =  crit.list();
			
			
			
			return result;
			
		}
	
	
}
