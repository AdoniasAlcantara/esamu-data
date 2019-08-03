package org.myopenproject.esamu.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {
	private static EntityManagerFactory factory;
	
	private JpaUtil() {}
	
	public static EntityManager getEntityManager() {
		if (factory == null || !factory.isOpen()) {
			 factory = Persistence.createEntityManagerFactory("esamuPu");
		}
		
		return factory.createEntityManager();
	}
	
	public static void close() {
		if (factory != null && factory.isOpen()) {
			factory.close();			
		}
	}
}
