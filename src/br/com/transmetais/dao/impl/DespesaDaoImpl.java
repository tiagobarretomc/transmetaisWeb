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
				clausulaWhere += " d.dataVencimento >= :dataInicio ";
			}
			if (filter.getDataFim() !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " d.dataVencimento <= :dataFim";
			}
			
			if (filter.getFornecedor() !=null && filter.getFornecedor().getId() !=null){
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
			
			if(filter != null && filter.getDescricao()!=null && filter.getDescricao() != ""){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " d.descricao like  :descricao ";
			}
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			query += " ORDER BY d.dataVencimento DESC";
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
			
			if (filter.getFornecedor() !=null && filter.getFornecedor().getId() !=null){
				hqlQuery.setParameter("fornecedorId", filter.getFornecedor().getId());
			}
			
			if(filter != null && filter.getDescricao()!=null && filter.getDescricao() != ""){
				hqlQuery.setParameter("descricao", "%" + filter.getDescricao() + "%");
			}
			
			
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	}
	
}
