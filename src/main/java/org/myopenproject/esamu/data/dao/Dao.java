package org.myopenproject.esamu.data.dao;

import java.util.List;

public interface Dao<T, K> {
	public T save(T obj);
	public T find(K key);
	public List<T> findAll();
	public boolean remove(K key);
}
