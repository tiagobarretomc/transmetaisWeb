package br.com.transmetais.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Despesa;
import br.com.transmetais.dao.DespesaDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.filtros.DespesaFiltro;

@Component
public class DespesaDaoImpl extends CrudDAOJPA<Despesa> implements DespesaDAO{
	
	public List<Despesa> findByFilter(DespesaFiltro filter) throws DAOException{
		EntityManager manager = factory.createEntityManager();
		try {
			String query = "SELECT distinct d from Despesa d  ";
			
			String clausulaWhere = " WHERE ";
			
			if (filter.getDataInicio() != null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " d.data >= :dataInicio ";
			}
			if (filter.getDataFim() !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " d.data <= :dataFim";
			}
			
			if (filter.getFornecedor() !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " d.fornecedor.id = :fornecedorId";
			}
			
			if(filter != null && filter.getStatus()!=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " d.status in ( :status) ";
			}
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			query += " ORDER BY c.data DESC";
			Query hqlQuery = manager.createQuery(query);
			
			
			
			if (filter.getDataInicio() != null){
				hqlQuery.setParameter("dataInicio", filter.getDataInicio());
				
			}
			
			if (filter.getDataFim() !=null){
				
				hqlQuery.setParameter("dataFim", filter.getDataFim());
			}
			
			
			
			if(filter.getStatus() != null){
				hqlQuery.setParameter("status", filter.getStatus());
			}
			
			if(filter.getFornecedor() != null){
				hqlQuery.setParameter("fornecedorId", filter.getFornecedor().getId());
			}
			
			
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	}
	
}
