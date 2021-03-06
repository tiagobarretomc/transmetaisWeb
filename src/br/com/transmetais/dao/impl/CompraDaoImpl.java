package br.com.transmetais.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Compra;
import br.com.transmetais.dao.CompraDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusCompraEnum;
import br.com.transmetais.type.TipoFreteEnum;

@Component
public class CompraDaoImpl extends CrudDAOJPA<Compra> implements CompraDAO{
	
	@Override
	public List<Compra> findAll() throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		try {
			String query = "SELECT c from " + Compra.class.getSimpleName() + " as c  inner join c.fornecedorMaterial as fm inner join fm.fornecedor f ORDER BY c.data DESC, f.nome ASC";
			return manager.createQuery(query).getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
			if (manager != null) {
				manager.close();
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Compra> findByFilter(Long fornecedorId, Date dataInicio, Date dataFim, List<TipoFreteEnum> tiposFretes, List<Long> materiaisIds,List<StatusCompraEnum> statusCompraLista) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT distinct c from Compra c inner join c.itens it ";
			
			String clausulaWhere = " WHERE ";
			if (fornecedorId != null && fornecedorId >0){
				clausulaWhere += "c.fornecedor.id = :fornecedorId";
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
			if(tiposFretes != null && tiposFretes.size()>0){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.tipoFrete in ( :tiposFretes) ";
			}
			
			if(materiaisIds != null && materiaisIds.size()>0){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " it.material.id in ( :materiaisIds) ";
			}
			
			if(statusCompraLista != null && statusCompraLista.size()>0){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " c.status in ( :statusCompraLista) ";
			}
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			query += " ORDER BY c.data DESC, c.fornecedor.nome ASC";
			Query hqlQuery = manager.createQuery(query);
			
			if (fornecedorId != null && fornecedorId >0)
				hqlQuery.setParameter("fornecedorId", fornecedorId);
			
			if (dataInicio != null){
				hqlQuery.setParameter("dataInicio", dataInicio);
				
			}
			
			if (dataFim !=null){
				
				hqlQuery.setParameter("dataFim", dataFim);
			}
			
			if (tiposFretes != null && tiposFretes.size()>0){
				
				hqlQuery.setParameter("tiposFretes", tiposFretes);
			}
			if (materiaisIds != null && materiaisIds.size()>0){
				
				hqlQuery.setParameter("materiaisIds", materiaisIds);
			}
			if (statusCompraLista != null && statusCompraLista.size()>0){
				
				hqlQuery.setParameter("statusCompraLista", statusCompraLista);
			}
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	} 
	
}
