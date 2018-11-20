package org.myopenproject.esamu.data.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaFactory {
	private static JpaFactory singleton;
	private EntityManagerFactory factory;
	
	private JpaFactory() {
		factory = Persistence.createEntityManagerFactory("esamuPu");
		singleton = this;
	}
	
	public static JpaFactory getInstance() {
		if (singleton != null)
			return singleton;
		
		return new JpaFactory();
	}
	
	public EntityManager getEntityManager() {				
		return factory.createEntityManager();
	}
	
	public void close() {
		if (factory.isOpen())
			factory.close();
	}
}
