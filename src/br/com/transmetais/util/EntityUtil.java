package br.com.transmetais.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import sun.reflect.Reflection;
import br.com.transmetais.bean.Combo;
import br.com.transmetais.dao.commons.DAOException;

public class EntityUtil {
	public static <E> Object getId(Class<?> clazz, E bean){
		if(bean == null || bean.getClass().equals(Object.class)) return null;
		if(clazz.isAnnotationPresent(Entity.class)){
			    for (Field field : clazz.getDeclaredFields()) {
					if(field.isAnnotationPresent(Id.class)){
						try {
							field.setAccessible(true);
							return field.get(bean);
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
		}
		return getId(bean.getClass().getSuperclass(),bean);
	}
	
	public static <T, V> List<Combo<V>> retrieveCombo(List<T> lista,String idField, String valueField) throws DAOException{
		List<Combo<V>> comboList = new ArrayList<Combo<V>>();
		if(lista != null && !lista.isEmpty()){
			Class<?> clazz = null; 
			Combo<V> itemCombo;
			clazz = lista.get(0).getClass();
			Field idFieldObj = null;
			Field valueFieldObj = null;
			try {
				idFieldObj = clazz.getDeclaredField(idField);
				valueFieldObj = clazz.getDeclaredField(valueField);
				idFieldObj.setAccessible(true);
				valueFieldObj.setAccessible(true);
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(idFieldObj != null && valueFieldObj != null){
				for (T t : lista) {
					try {
						itemCombo = new Combo<V>((V)idFieldObj.get(t), valueFieldObj.get(t).toString());
						comboList.add(itemCombo);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return comboList;
	}
	
}