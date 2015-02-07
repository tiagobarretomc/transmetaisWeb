/*
 IBPM - Ferramenta de produtividade Java
 Copyright (c) 1986-2009 Infox Tecnologia da Informação Ltda.

 Este programa é software livre; você pode redistribuí-lo e/ou modificá-lo 
 sob os termos da GNU GENERAL PUBLIC LICENSE (GPL) conforme publicada pela 
 Free Software Foundation; versão 2 da Licença.
 Este programa é distribuído na expectativa de que seja útil, porém, SEM 
 NENHUMA GARANTIA; nem mesmo a garantia implícita de COMERCIABILIDADE OU 
 ADEQUAÇÃO A UMA FINALIDADE ESPECÍFICA.
 
 Consulte a GNU GPL para mais detalhes.
 Você deve ter recebido uma cópia da GNU GPL junto com este programa; se não, 
 veja em http://www.gnu.org/licenses/   
*/
package br.com.transmetais.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public final class ReflectionsUtil {
	
	
	private ReflectionsUtil() { }

	public static Field getField(Object o, String fieldName) {
		Exception exc = null;
		Class<?> cl = o.getClass();
		while (cl != null) {
			try {
				Field f = cl.getDeclaredField(fieldName);
				f.setAccessible(true);
				return f;
			} catch(Exception e) {
				cl = cl.getSuperclass();
				exc = e;
			}
		}
		return null;
	}

	public static Object getValue(Object o, String fieldName) {
		try {
			Field field = getField(o, fieldName);
			field.setAccessible(true);
			if (field != null) {
				return field.get(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object getValue(Object o, Field field) {
		field.setAccessible(true);
		try {
			if (field != null) {
				return field.get(o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String capitalize(String str)
	{
	  return Character.toUpperCase(str.charAt(0)) + str.substring(1);
	}
	
	public static String getStringValue(Object o, String fieldName) {
		return (String) getValue(o, fieldName);
	}
	
	public static Method getMethodGet(Class clazz, String field){
		try {
			return clazz.getDeclaredMethod("get" + capitalize(field));
		} catch (NoSuchMethodException e) {
		} catch (SecurityException e) {
		}
		return null;
	}
	
	public static void setValue(Object o, String fieldName, Object value) {
		try {
			Field field = getField(o, fieldName);
			field.setAccessible(true);
			if (field != null) {
				field.set(o, value);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Retorna os campos de uma classe e todas as suas superclasses
	 * @param clazz
	 * @return lista dos campos
	 */
	@SuppressWarnings("unchecked")
	public static List<Field> getFields(Class clazz) {
		List<Field> fields = new ArrayList<Field>();
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			for (Field field : superClass.getDeclaredFields()) {
				fields.add(field);
			}
		}
		return fields;
	}
	
	public static Object invokeMethod(Method method, Object obj){
		try {
			return method.invoke(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}