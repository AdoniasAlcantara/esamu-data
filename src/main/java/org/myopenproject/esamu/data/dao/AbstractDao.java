package org.myopenproject.esamu.data.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

public class AbstractDao<T, K> implements Dao<T, K> {
	private Class<T> type;
	private Field key;
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public AbstractDao(EntityManager entityManager) {
		manager = entityManager;
		type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		
		Field[] fields = type.getDeclaredFields();
		
		for (Field f : fields) {
			Annotation annotation = f.getAnnotation(Id.class);
			
			if (annotation != null) {				
				key = f;
				key.setAccessible(true);
				break;
			}
		}
		
		if (key == null) {
			throw new IllegalStateException("Type " + type.getName() + " doesn't an @Id defined");
		}
	}

	@Override
	public T save(T obj) {		
		try {
			if (manager.find(type, key.get(obj)) == null) {
				manager.persist(obj);				
			} else {
				obj = manager.merge(obj);
			}
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
		return obj;
	}

	@Override
	public T find(K key) {
		return manager.find(type, key);
	}

	@Override
	public List<T> findAll() {
		TypedQuery<T> query = manager.createQuery("from " + type.getName(), type);
		return query.getResultList();
	}

	@Override
	public boolean remove(K key) {
		T obj = manager.find(type, key);
		
		if (obj == null) {
			return false;
		}
		
		manager.remove(obj);
		return true;
	}

	protected EntityManager getEntityManager() {
		return manager;
	}
}
