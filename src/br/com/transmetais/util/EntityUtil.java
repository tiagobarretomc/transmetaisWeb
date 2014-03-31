package br.com.transmetais.util;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.Id;

public class EntityUtil {
	public static <E> Object getId(E bean){
		if(bean == null) return null;
		Class<E> clazz = (Class<E>) bean.getClass();
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
		return null;
	}
}
