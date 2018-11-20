package org.myopenproject.esamu.data.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

public class AbstractDao<T, K> implements Dao<T, K>, AutoCloseable {
	private Class<T> type;
	private Field key;
	private EntityManager manager;

	@SuppressWarnings("unchecked")
	public AbstractDao() {
		manager = JpaFactory.getInstance().getEntityManager();
		type = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];	
		
		Field[] fields = type.getDeclaredFields();
		
		for (Field f : fields) {
			Annotation[] annotations = f.getAnnotationsByType(Id.class);
			
			if (annotations.length > 0) {				
				key = f;
				key.setAccessible(true);
				break;
			}
		}
	}

	@Override
	public void save(T obj) {
		manager.getTransaction().begin();
		
		try {
			if (manager.find(type, key.get(obj)) == null)
				manager.persist(obj);
			else
				manager.merge(obj);
			
			manager.getTransaction().commit();
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
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
	public T remove(K key) {
		manager.getTransaction().begin();
		T obj = manager.find(type, key);
		manager.remove(obj);
		manager.getTransaction().commit();
		
		return obj;
	}

	@Override
	public void close() {
		manager.close();
	}

	protected EntityManager getEntityManager() {
		return manager;
	}
}
