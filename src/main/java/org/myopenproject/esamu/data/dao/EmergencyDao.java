package org.myopenproject.esamu.data.dao;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.myopenproject.esamu.data.model.Emergency;
import org.myopenproject.esamu.data.model.Emergency.Status;
import org.myopenproject.esamu.data.model.Multimedia;

public class EmergencyDao extends AbstractDao<Emergency, Long> {
	private static String resPath = "./";
	
	public EmergencyDao(EntityManager entityManager) {
		super(entityManager);
	}
	
	@Override
	public Emergency save(Emergency emergency) {
		EntityManager manager = getEntityManager();
		
		try {
			manager.persist(emergency);
			manager.flush();
			String path = resPath + emergency.getId() + ".jpg";
			Files.write(Paths.get(path), emergency.getMultimedia().getPicture());
		} catch (IOException e) {
			throw new RuntimeException("Cannot write resources to Emergency " + emergency.getId(), e);
		}
		
		return emergency;
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
	public boolean remove(Long key) {		
		if (super.remove(key)) {
			Path path = Paths.get(resPath + key + ".jpg");
			
			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	public List<Emergency> findByStatus(Status status) {
		String jpql = "FROM Emergency e WHERE e.status = :status";
		List<Emergency> emergencies = getEntityManager().createQuery(jpql, Emergency.class)
				.setParameter("status", status)
				.getResultList();
		
		readResources(emergencies);
		return emergencies;
	}
	
	public List<Emergency> summary(Status ...status) {
		String jpql = "SELECT NEW " + Emergency.class.getName()
				+ "(e.id, u.name, u.phone, e.start, e.status) "
				+ "FROM Emergency e JOIN e.user u "
				+ "WHERE e.status in(:status)"
				+ "ORDER BY e.id";
		
		return getEntityManager().createQuery(jpql, Emergency.class)
				.setParameter("status", Arrays.asList(status))
				.getResultList();
	}
	
	public void clean() {
		getEntityManager().createQuery("DELETE FROM Location").executeUpdate();
		getEntityManager().createQuery("DELETE FROM Emergency").executeUpdate();
		getEntityManager().createQuery("DELETE FROM User").executeUpdate();
		
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(resPath))) {
			for (Path entry : stream) {
				Files.deleteIfExists(entry);
			}
		} catch (IOException e) {
			// Ignore
		}
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
