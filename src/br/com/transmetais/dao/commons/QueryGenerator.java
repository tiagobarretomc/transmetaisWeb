package br.com.transmetais.dao.commons;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Persistence;
import javax.persistence.Transient;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.transmetais.util.ReflectionsUtil;
/**
 * 
 * @author manuellaalmeida
 *
 */
public class QueryGenerator {
	public static final String LOWER_EQUAL_OPERATOR = "<=";
	public static final String GREATER_EQUAL_OPERATOR = ">=";
	public static final String IS_NULL_OPERATOR = "is null";
	public static final String IS_NOT_NULL_OPERATOR = "is not null";
	public static final String GREATER_OPERATOR = ">";
	public static final String LOWER_OPERATOR = "<";
	public static final String EQUAL_OPERATOR = "=";
	public static final String DIFF_OPERATOR = "<>";
	private static final String SPACE = " ";
	private static final String DOIS_PONTOS = ":";
	private static final String AND = " and ";
	private static final String OR = " or ";
	private static final String ALIAS_OBJ = "o";
	private static final String WHERE = " where ";
	private static final String SELECT = " select ";
	private static final String FROM = " from ";
	private static final String PONTO = ".";
	private static final String VIRGULA = ",";
	private static final String ORDER_BY = " order by ";
	private static final String UNDERSCORE  = "_";
	
	String entityExpression = null;

	Map<String, List<SpecificConditions>> specificConditions = null;
	
	List<String> orderFields = null;

	List<String> ignoreFields = null;

	List<String> fields = null;
	
	Map<String, Object> params = null; 
	
	Integer groupCount = 0;
	
	protected static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("unit");
    
    
    private static Session getSession()    
    {    
        return (Session) factory.createEntityManager().getDelegate();
    }    
	
	
	/**
	 * Agrupa um conjunto de condições especificas.
	 * Todos os grupos serão testado com critério de OU 
	 * e as condições agrupadas AND
	 */
	public void group(){
		groupCount++;
		for (List<SpecificConditions> scMap : specificConditions.values()) {
			for (SpecificConditions sc : scMap) {
				if(sc.getGroup() == null)
					sc.setGroup("group" + groupCount);
			}
		}
	}
	
	public QueryGenerator() {
	}
	/**
	 * Adiciona condições especificas de filtro, neste caso 
	 * as condições não seráo definidas pelo atributos não nulos
	 *  da entidade 
	 * @param field Nome do atributo a ser filtrado
	 * @param param valor de filtro
	 */
	public void addParam(String field, Object param){
		if(params == null){
			params = new HashMap<String, Object>();
		}
		params.put(field, param);
	}
	/**
	 * Adiciona os atributos da entidade pelos
	 * quais a consulta será ordenada 
	 * @param field
	 */
	public void addOrderBy(String field){
		if(orderFields == null){
			orderFields = new ArrayList<String>();
		}
		orderFields.add(field);
	}
	/**
	 * Adiciona condições diferente de igualdade ao filtro.
	 * @param field atributo filtro da entidade a ser considerado
	 * @param operator tipo de operacao
	 * @param fieldCompare campo do banco que será comparado
	 */
	public void addSpecificCondition(String field, String operator,
			String fieldCompare) {
		if (specificConditions == null) {
			specificConditions = new HashMap<String, List<SpecificConditions>>();
		}
		Boolean isNullCondition = IS_NULL_OPERATOR.equals(operator) || IS_NOT_NULL_OPERATOR.equals(operator);
		if (specificConditions.containsKey(field)) {
			this.specificConditions.get(field).add(
					new SpecificConditions(fieldCompare, operator, isNullCondition));
		} else {
			List<SpecificConditions> list = new ArrayList<SpecificConditions>();
			list.add(new SpecificConditions(fieldCompare, operator, isNullCondition));
			this.specificConditions.put(field, list);
		}

	}
	/**
	 * Adiciona operações diferente de igualdade
	 * @param field campo filtro a ser considerado
	 * @param operator tipo de operacao
	 */
	public void addSpecificCondition(String field, String operator){
		addSpecificCondition(field, operator, field);
	}
	/**
	 * Adiciona atributos da entidade que deverão ser desconsiderados 
	 * como filtro
	 * @param field
	 */
	public void addIgnoreField(String field) {
		if (ignoreFields == null) {
			this.ignoreFields = new ArrayList<String>();
		}
		this.ignoreFields.add(field);
	}
	/**
	 * Deve ser especificado quando 
	 * há necessidade de retorno de atributos especificos da entidade.
	 * @param field atributo que será retornado
	 */
	public void addField(String field) {
		if (fields == null) {
			this.fields = new ArrayList<String>();
		}
		this.fields.add(field);
	}
	/**
	 * Cria um objeto do tipo org.hibernate.Query para o filtro especificado
	 * @param obj filtro
	 * @return
	 */
	public <T> Query createQuery(T obj) {
		return createQuery(getDefaultSelectQuery(obj), obj,
				null);
	}
	/**
	 * Cria um objeto do tipo javax.persistence.Query para o filtro especificado
	 * @param obj
	 * @param manager
	 * @return
	 */
	public <T> javax.persistence.Query createQuery(T obj, EntityManager manager) {
		Map<String, Object> map = null;
		if(params == null){
			map = loadParams(obj);
		}else {
			map = params;
		}
		
		javax.persistence.Query q = manager.createQuery(createQueryString(getDefaultSelectQuery(obj), obj, map));
	
		for (String p : map.keySet()) {
			q.setParameter(p, map.get(p));
		}
		return q;
	}
	/**
	 * 
	 * @param obj Objeto filtro
	 * @param entityExpression Ex.: objList.entity
	 * @return
	 */
	public <T> String createEjbQuery(T obj, String entityExpression) {
		Map<String, Object> map = null;
		if(params == null){
			map = loadParams(obj);
		}else{
			map = params;
		}
		this.entityExpression = entityExpression;
		return createQueryString(getDefaultSelectQuery(obj), obj,
				map);
	}
	
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public <T> Query createExistsQuery(T obj) {
		return createQuery(getDefaultExistsQuery(obj), obj,
				1);
	}
	private <T> String createQueryString(String defaultQuery, 
			T obj, Map<String, Object> map){
		StringBuilder sb = new StringBuilder(defaultQuery);
		Map<String, String> groups = null;

		if(sb.indexOf(WHERE) > 0)
			sb.append(AND);
		else{
		  if (!map.isEmpty())
			sb.append(WHERE);
		}
		for (String p : map.keySet()) {
			String field = p.replaceAll(UNDERSCORE, PONTO);
			if (specificConditions != null && specificConditions.containsKey(field)) {
				List<SpecificConditions> list = specificConditions.get(field);
				for (SpecificConditions sc : list) {
					if (sc.group != null) {
						StringBuilder sbGroup = null;
						if (groups == null)
							groups = new HashMap<String, String>();
						if (groups.containsKey(sc.group))
							sbGroup = new StringBuilder(groups.get(sc.group));
						else
							sbGroup = new StringBuilder();
						if(!sc.getNullCondition()){
							sbGroup.append(ALIAS_OBJ).append(PONTO)
									.append(sc.fieldCompare).append(SPACE)
									.append(sc.operator).append(SPACE);
									if(entityExpression!=null){
										sbGroup.append("#{")
										.append(entityExpression)
										.append(PONTO).append(field)
										.append("}");
									}else{
										sbGroup.append(DOIS_PONTOS)
										.append(p);
									}
									sbGroup.append(AND);
						}else{
							sbGroup.append(sc.fieldCompare).append(SPACE)
							.append(sc.operator).append(SPACE)
							.append(AND);
						}
						groups.put(sc.group, sbGroup.toString());
					} else {
						if(!sc.getNullCondition()){
							sb.append(ALIAS_OBJ).append(PONTO)
							.append(sc.fieldCompare).append(SPACE)
							.append(sc.operator).append(SPACE);
							if(entityExpression!=null){
								sb.append("#{")
								.append(entityExpression)
								.append(PONTO).append(field)
								.append("}");
							}else{
								sb.append(DOIS_PONTOS)
								.append(p);
							}
							sb.append(AND);
						}else{
							sb.append(ALIAS_OBJ).append(PONTO)
							.append(sc.fieldCompare).append(SPACE)
							.append(sc.operator).append(SPACE)
							.append(AND);
						}
						
					}
				}
			} else {
				sb.append(ALIAS_OBJ).append(PONTO);
				sb.append(field).append(SPACE);
				sb.append(EQUAL_OPERATOR);
				if(entityExpression!=null){
					sb.append("#{")
					.append(entityExpression)
					.append(PONTO).append(field)
					.append("}");
				}else{
					sb.append(SPACE).append(DOIS_PONTOS).append(p);
				}
				sb.append(AND);
			}
			
		}
		if (groups != null) {
			sb.append("(");
			for (String group : groups.values()) {
				sb.append("(");
				sb.append(group.substring(0, group.length() - AND.length()));
				sb.append(")");
				sb.append(OR);
			}
			sb.setLength(sb.length() - OR.length());
			sb.append(")))").append(AND);
		}
		if (!map.isEmpty()) sb.setLength(sb.length() - AND.length());
		if(orderFields != null && !orderFields.isEmpty()) {
			sb.append(ORDER_BY);
			for (String fieldOrder : orderFields) {
				sb.append(ALIAS_OBJ).append(PONTO).append(fieldOrder).append(VIRGULA);
			}
			sb.setLength(sb.length() - VIRGULA.length());
		}
		System.out.println("QUERY-GENERATOR >>>>>" + sb.toString());
		return sb.toString();
	}
	private <T> Query createQuery(String defaultQuery, 
			T obj, Integer limit) {
		Map<String, Object> map = null;
		if(params == null){
			map = loadParams(obj);
		}else {
			map = params;
		}

		Query q = getSession().createQuery(createQueryString(defaultQuery, obj, map));

		if (limit != null && limit > 0)
			q.setMaxResults(limit);

		for (String p : map.keySet()) {
			q.setParameter(p, map.get(p));
		}
		return q;
	}

	
	private <T> String getDefaultSelectQuery(T obj) {
		StringBuilder sb = new StringBuilder(SELECT);
		if(fields != null && !!fields.isEmpty()){
			for (String f : fields) {
				sb.append(ALIAS_OBJ)
				.append(PONTO)
				.append(f)
				.append(VIRGULA);
			}
			sb.setLength(sb.length() - VIRGULA.length());
		}else{
			sb.append(ALIAS_OBJ);
		}
		sb.append(FROM);
		sb.append(obj.getClass().getSimpleName());
		sb.append(SPACE);
		sb.append(ALIAS_OBJ);
		return sb.toString();
	}

	private <T> String getDefaultExistsQuery(T obj) {
		return SELECT + "1" + FROM + obj.getClass().getSimpleName() + SPACE
				+ ALIAS_OBJ;
	}

	/**
	 * Retorna um Hashmap com os atributos não nulos de um objeto
	 * @param clazz
	 * @param obj
	 * @param fieldName
	 * @param params
	 * @return
	 */
	private <T, V> Map<String, Object> loadParams(T obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		loadParams(obj.getClass(), obj, null, map);
		return map;
	}
	private <T> Object loadParams(Class<?> clazz, T obj,
			 String fieldName, Map<String, Object> params) {
		Map<String, Object> map = new HashMap<String, Object>();
		Object id = null; 
		
		if (clazz.getSuperclass() != null) {
			id = loadParams(clazz.getSuperclass() , obj, fieldName, map);
		}
		if(clazz.getAnnotation(Entity.class) != null){
			Object o = null;
			Boolean fieldAnottation = Boolean.FALSE;
			for (Field field : clazz.getDeclaredFields()) {
				String f = fieldName != null ? fieldName.concat(UNDERSCORE).concat(field.getName()):field.getName();
				fieldAnottation = (field.getAnnotation(Column.class) != null 
						|| field.getAnnotation(JoinColumn.class) != null)
						|| (field.getAnnotation(Transient.class) != null && specificConditions.containsKey(f));
				if(field.getAnnotation(Id.class) != null){
					id = ReflectionsUtil.getValue(obj, field);
					if(id != null && field.getType().isPrimitive() && "0".equals(id.toString())) {
						id = null;
						fieldAnottation = Boolean.FALSE; 
					}
					if(specificConditions != null && specificConditions.containsKey(f)){
						id = null;
					}
				}
				
				if(!fieldAnottation){
					Method meth = ReflectionsUtil.getMethodGet(clazz, field.getName());
					if(meth != null){
						fieldAnottation = (meth.getAnnotation(Column.class) != null 
								|| meth.getAnnotation(JoinColumn.class) != null);
						if(meth.getAnnotation(Id.class) != null){
							id = ReflectionsUtil.invokeMethod(meth, obj);
							if(id != null && meth.getReturnType().isPrimitive() && "0".equals(id.toString())) {
								id = null;
								fieldAnottation = Boolean.FALSE; 
							}
							if(specificConditions != null && specificConditions.containsKey(f)){
								id = null;
							}
						}
					}
				}
				if (fieldAnottation) {
					field.setAccessible(Boolean.TRUE);
					try {
						o = field.get(obj);
						if (o != null) {
							if(field.getType().getAnnotation(Entity.class) != null){
								Object idField = loadParams(o.getClass(), o, f, map);
								if(idField != null &&  (ignoreFields == null || ignoreFields.isEmpty() 
										|| !ignoreFields.contains(f))){
									map.put(f, o);
								}
							}else{
								if(ignoreFields == null || ignoreFields.isEmpty() 
										|| !ignoreFields.contains(f)){
									map.put(f, o);
								}
							}
						}
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			if(id == null) {
				params.putAll(map);
			}
		}
		return id;
	}
	
	class SpecificConditions {
		private String fieldCompare = null;
		private String operator = null;
		private String group = null;
		private Boolean nullCondition = null;

		public SpecificConditions(String field, String operator, Boolean nullCondition) {
			this.fieldCompare = field;
			this.operator = operator;
			this.nullCondition = nullCondition;
		}

		public String getField() {
			return fieldCompare;
		}

		public void setField(String field) {
			this.fieldCompare = field;
		}

		public String getOperator() {
			return operator;
		}

		public void setOperator(String operator) {
			this.operator = operator;
		}

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public String getFieldCompare() {
			return fieldCompare;
		}

		public void setFieldCompare(String fieldCompare) {
			this.fieldCompare = fieldCompare;
		}

		public Boolean getNullCondition() {
			return nullCondition;
		}

		public void setNullCondition(Boolean nullCondition) {
			this.nullCondition = nullCondition;
		}
		
	};

}
