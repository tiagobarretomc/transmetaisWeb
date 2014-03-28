package br.com.transmetais.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Movimentacao;
import br.com.transmetais.dao.MovimentacaoDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class MovimentacaoDaoImpl extends CrudDAOJPA<Movimentacao> implements MovimentacaoDAO{
	
	public List<Movimentacao> findByFilter(Date dataInicio, Date dataFim) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT m from Movimentacao m ";
			
			String clausulaWhere = " WHERE ";
			
			if (dataInicio != null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " m.data >= :dataInicio ";
			}
			if (dataFim !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " m.data <= :dataFim";
			}
			
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			query += " ORDER BY m.data ASC";
			Query hqlQuery = manager.createQuery(query);
			
			
			if (dataInicio != null){
				hqlQuery.setParameter("dataInicio", dataInicio);
				
			}
			
			if (dataFim !=null){
				
				hqlQuery.setParameter("dataFim", dataFim);
			}
			
			
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	} 

}
