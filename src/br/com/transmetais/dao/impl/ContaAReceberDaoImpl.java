package br.com.transmetais.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.ContaAReceber;
import br.com.transmetais.dao.ContaAReceberDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusMovimentacaoEnum;

@Component
public class ContaAReceberDaoImpl extends CrudDAOJPA<ContaAReceber> implements ContaAReceberDAO{
	
	
	
	public List<ContaAReceber> findByFilter(Date dataInicio, Date dataFim, StatusMovimentacaoEnum status) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT c from ContaAReceber c ";
			
			String clausulaWhere = " WHERE ";
			
			if (dataInicio != null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.dataPrevista >= :dataInicio ";
			}
			if (dataFim !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.dataPrevista <= :dataFim";
			}
			
			if (status !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.status = :status";
			}
			
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			query += " ORDER BY c.dataPrevista ASC";
			Query hqlQuery = manager.createQuery(query);
			
			
			if (dataInicio != null){
				hqlQuery.setParameter("dataInicio", dataInicio);
				
			}
			
			if (dataFim !=null){
				
				hqlQuery.setParameter("dataFim", dataFim);
			}
			
			if (status !=null){
				
				hqlQuery.setParameter("status", status);
			}
			
			
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	}

}
