package br.com.transmetais.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.caelum.vraptor.ioc.Component;
import br.com.transmetais.bean.Venda;
import br.com.transmetais.dao.VendaDAO;
import br.com.transmetais.dao.commons.CrudDAOJPA;
import br.com.transmetais.dao.commons.DAOException;
import br.com.transmetais.type.StatusVendaEnum;
import br.com.transmetais.type.TipoFreteCliente;

@Component
public class VendaDaoImpl extends CrudDAOJPA<Venda> implements VendaDAO{
//	private static VendaDaoImpl instance = null;
//	
//	public  static  VendaDaoImpl getInstance(){
//		if (instance == null)
//			instance = new VendaDaoImpl(); 
//		return instance;
//	}
	
	
	@Override
	public List<Venda> findAll() throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		try {
			String query = "SELECT v from " + Venda.class.getSimpleName() + " as v  inner join v.clienteMaterial as cm inner join cm.cliente c ORDER BY v.data DESC, c.razaoSocial ASC";
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
	public List<Venda> findByFilter(Long clienteId, Date dataInicio, Date dataFim, List<TipoFreteCliente> tiposFretes, List<Long> materiaisIds,List<StatusVendaEnum> statusVendaLista) throws DAOException {
		EntityManager manager = factory.createEntityManager(); 
		
		try {
			String query = "SELECT distinct v from Venda v inner join v.itens it ";
			
			String clausulaWhere = " WHERE ";
			if (clienteId != null && clienteId >0){
				clausulaWhere += "v.cliente.id = :clienteId";
			}
			if (dataInicio != null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " v.data >= :dataInicio ";
			}
			if (dataFim !=null){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " v.data <= :dataFim";
			}
			if(tiposFretes != null && tiposFretes.size()>0){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " v.tipoFrete in ( :tiposFretes) ";
			}
			
			if(materiaisIds != null && materiaisIds.size()>0){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " it.material.id in ( :materiaisIds) ";
			}
			
			if(statusVendaLista != null && statusVendaLista.size()>0){
				if(clausulaWhere != " WHERE "){
					clausulaWhere += " AND ";
				}
				clausulaWhere += " v.status in ( :statusVendaLista) ";
			}
			
			if (clausulaWhere != " WHERE ")
				query += clausulaWhere;
			
			query += " ORDER BY v.data DESC, v.cliente.razaoSocial ASC";
			Query hqlQuery = manager.createQuery(query);
			
			if (clienteId != null && clienteId >0)
				hqlQuery.setParameter("clienteId", clienteId);
			
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
			if (statusVendaLista != null && statusVendaLista.size()>0){
				
				hqlQuery.setParameter("statusVendaLista", statusVendaLista);
			}
			return hqlQuery.getResultList();
		} catch (Exception e) {
		    throw new DAOException(e);
		} finally {
		}
	} 

}
