package br.com.transmetais.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.ChequeEmitido;
import br.com.transmetais.bean.ChequeEmitidoDespesa;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.dao.ChequeEmitidoDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class ChequeEmitidoDaoImpl extends CrudDAOJPA<ChequeEmitido> implements ChequeEmitidoDAO{
	
	public List<ChequeEmitidoDespesa> obterChequeByDespesa(Despesa despesa){
		return null;
	}
	public List<ChequeEmitido> findByFilter(Date dataInicio, Date dataFim, ChequeEmitido cheque) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT distinct c from ChequeEmitido c  ";
			
			String clausulaWhere = " WHERE ";
			if (cheque != null && cheque.getConta() != null && cheque.getConta().getId() != null){
				clausulaWhere += "c.conta.id = :contaId";
			}
			if (dataInicio != null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.data >= :dataInicio ";
			}
			if (dataFim !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.data <= :dataFim";
			}
			if(cheque != null && cheque.getNumeroCheque() != null && cheque.getNumeroCheque() != ""){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.numeroCheque =  :numeroCheque ";
			}
			
			
			if(cheque != null && cheque.getStatus()!=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.status in ( :status) ";
			}
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			query += " ORDER BY c.data DESC";
			Query hqlQuery = manager.createQuery(query);
			
			if (cheque != null && cheque.getConta() != null && cheque.getConta().getId() != null){
				hqlQuery.setParameter("contaId", cheque.getConta().getId());
			}
			
			if (dataInicio != null){
				hqlQuery.setParameter("dataInicio", dataInicio);
				
			}
			
			if (dataFim !=null){
				
				hqlQuery.setParameter("dataFim", dataFim);
			}
			
			if(cheque != null && cheque.getNumeroCheque() != null && cheque.getNumeroCheque() != ""){
				hqlQuery.setParameter("numeroCheque", cheque.getNumeroCheque());
			}
			
			if(cheque != null && cheque.getStatus()!=null){
				hqlQuery.setParameter("status", cheque.getStatus());
			}
			
			
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	}
	
}
