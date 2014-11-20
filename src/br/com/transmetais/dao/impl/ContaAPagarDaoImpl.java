package br.com.transmetais.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.bean.ContaAPagar;
import br.com.transmetais.bean.ContaAPagarCompra;
import br.com.transmetais.bean.ContaAPagarDespesa;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.bean.Parcela;
import br.com.transmetais.dao.ContaAPagarDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;

@Component
public class ContaAPagarDaoImpl extends CrudDAOJPA<ContaAPagar> implements ContaAPagarDAO{
	
	public List<ContaAPagar> findByFilter(Date dataInicio, Date dataFim) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT c from ContaAPagar c ";
			
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
			
			
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	} 
	
	public ContaAPagarDespesa obterPorDespesa(Despesa despesa, Parcela parcela) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT c from ContaAPagarDespesa c ";
			
			String clausulaWhere = " WHERE ";
			
			if (despesa != null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.despesa.id = :despesaId ";
			}
			if (parcela !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.parcela.id = :parcelaId";
			}
			
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			
			Query hqlQuery = manager.createQuery(query);
			
			
			if (despesa != null){
				hqlQuery.setParameter("despesaId", despesa.getId());
				
			}
			
			if (parcela !=null && parcela.getId()>0){
				
				hqlQuery.setParameter("parcela", parcela.getId());
			}
			
			
			return (ContaAPagarDespesa)hqlQuery.getSingleResult();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	}
	
	public ContaAPagarCompra obterPorCompra(Compra compra, Parcela parcela) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT c from ContaAPagarCompra c ";
			
			String clausulaWhere = " WHERE ";
			
			if (compra != null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.compra.id = :compraId ";
			}
			if (parcela !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.parcela.id = :parcelaId";
			}
			
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			
			Query hqlQuery = manager.createQuery(query);
			
			
			if (compra != null){
				hqlQuery.setParameter("compraId",  compra.getId());
				
			}
			
			if (parcela !=null && parcela.getId()>0){
				
				hqlQuery.setParameter("parcela", parcela.getId());
			}
			
			
			return (ContaAPagarCompra)hqlQuery.getSingleResult();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	}

}
