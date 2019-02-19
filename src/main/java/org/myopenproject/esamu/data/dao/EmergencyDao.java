package org.myopenproject.esamu.data.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.myopenproject.esamu.data.Emergency;
import org.myopenproject.esamu.data.Emergency.Status;
import org.myopenproject.esamu.data.Multimedia;

public class EmergencyDao extends AbstractDao<Emergency, Long> {
	private static String resPath = "./";
	
	@Override
	public void save(Emergency emergency) {
		EntityManager manager = getEntityManager(); 
		
		try {
			manager.getTransaction().begin();
			manager.persist(emergency);
			manager.flush();
			String path = resPath + emergency.getId() + ".jpg";
			Files.write(Paths.get(path), emergency.getMultimedia().getPicture());
			manager.getTransaction().commit();
		} catch (IOException e) {
			manager.getTransaction().rollback();
			throw new RuntimeException("Cannot write resources to Emergency " + emergency.getId(), e);
		}
	}
	
	@Override
	public Emergency find(Long key) {
		Emergency emergency = super.find(key);
		readResources(emergency);
		return emergency;
	}
	
	@Override
	public List<Emergency> findAll() {
		List<Emergency> emergencies = super.findAll();
		readResources(emergencies);
		return emergencies;
	}
	
	@Override
	public Emergency remove(Long key) {
		Emergency emergency = super.remove(key);
		
		if (emergency != null) {
			Path path = Paths.get(resPath + key + ".jpg");
			
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
		
		return emergency;
	}
	
	public List<Emergency> findByStatus(Status status) {
		TypedQuery<Emergency> query = getEntityManager()
				.createQuery("SELECT e FROM Emergency e WHERE e.status = :status", Emergency.class);
		query.setParameter("status", status);
		List<Emergency> emergencies = query.getResultList();
		readResources(emergencies);
		
		return emergencies;
	}
	
	public List<Emergency> summary(Status status) {
		String queryStr = "SELECT NEW Emergency"
				+ "(e.id, u.name, u.phone, e.start, e.status) "
				+ "FROM Emergency e INNER JOIN e.user u "
				+ "WHERE e.status = :status "
				+ "ORDER BY e.id";
		TypedQuery<Emergency> query = getEntityManager().createQuery(queryStr, Emergency.class);
		query.setParameter("status", status);
		return query.getResultList();
	}
	
	public void clean() {
		getEntityManager().getTransaction().begin();
		Query query = getEntityManager().createQuery("DELETE FROM Emergency");
		query.executeUpdate();
		getEntityManager().getTransaction().commit();
	}
	
	public static void setResourcesPath(String resPath) {
		EmergencyDao.resPath = resPath + "/";
	}
	
	private void readResources(List<Emergency> emergencies) {
		if (emergencies == null)
			return;
		
		for (Emergency e: emergencies)
			readResources(e);
	}
	
	private void readResources(Emergency emergency) {
		if (emergency == null)
			return;
		
		String path = resPath + emergency.getId() + ".jpg";
		
		try {
			byte[] picture = Files.readAllBytes(Paths.get(path));
			Multimedia multimedia = new Multimedia();
			multimedia.setPicture(picture);
			emergency.setMultimedia(multimedia);
		} catch (IOException e) {
			throw new RuntimeException("Missing resources from Emergency " + emergency.getId(), e);
		}
	}
}
