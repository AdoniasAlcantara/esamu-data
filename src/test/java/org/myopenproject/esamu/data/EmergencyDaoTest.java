package org.myopenproject.esamu.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.myopenproject.esamu.data.dao.EmergencyDao;
import org.myopenproject.esamu.data.dao.JpaUtil;
import org.myopenproject.esamu.data.dao.UserDao;
import org.myopenproject.esamu.data.model.Emergency;
import org.myopenproject.esamu.data.model.Emergency.Status;
import org.myopenproject.esamu.data.model.Location;
import org.myopenproject.esamu.data.model.Multimedia;
import org.myopenproject.esamu.data.model.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmergencyDaoTest {
	static Emergency emergency;
	static EmergencyDao eDao;
	static EntityManager entityManager;

	@BeforeClass
	public static void init() throws IOException {
		emergency = new Emergency();
		emergency.setImei("123456789012345");
		emergency.setStatus(Status.PENDENT);
		emergency.setStart(new Date());
		
		User user = new User();
		user.setId("3EeyQIDa9FNcbGv1kYjS44ErOlz2");
		user.setName("Name");
		user.setPhone("12345678");
		user.setNotificationKey("b128e848-25a2-4f82-9bf4-ac8c63e4882d");
		emergency.setUser(user);
		
		Location location = new Location();
		location.setLatitude(0.0);
		location.setLongitude(0.0);
		location.setAddress("Address");
		location.setCity("City");
		location.setState("State");
		location.setCountry("Country");
		location.setPostalCode("12345678");
		location.setEmergency(emergency);
		emergency.setLocation(location);
		
		String resourcePath = EmergencyDaoTest.class
				.getClassLoader()
				.getResource("picture.jpg")
				.getFile();
		
		byte[] picture = Files.readAllBytes(Paths.get(resourcePath));
		Multimedia multimedia = new Multimedia();
		multimedia.setPicture(picture);
		emergency.setMultimedia(multimedia);
		EmergencyDao.setResourcesPath(System.getProperty("java.io.tmpdir"));
	}
	
	@AfterClass
	public static void cleanUp() {
		entityManager = JpaUtil.getEntityManager();
		entityManager.getTransaction().begin();
		User user = entityManager.find(User.class, "3EeyQIDa9FNcbGv1kYjS44ErOlz2");
		entityManager.remove(user);
		entityManager.getTransaction().commit();
		JpaUtil.close();
	}
	
	@Before
	public void beforeEach() {
		entityManager = JpaUtil.getEntityManager();
		eDao = new EmergencyDao(entityManager);
	}
	
	@After
	public void afterEach() {
		entityManager.close();
	}
	
	@Test
	public void test2Save() {
		entityManager.getTransaction().begin();
		UserDao uDao = new UserDao(entityManager);
		uDao.save(emergency.getUser());
		eDao.save(emergency);
		entityManager.getTransaction().commit();
	}
	
	@Test
	public void test3Find() {
		Emergency other = eDao.find(emergency.getId());
		assertEquals(emergency.getImei(), other.getImei());
		assertEquals(emergency.getStart(), other.getStart());
		assertEquals(emergency.getEnd(), other.getEnd());
		assertEquals(emergency.getAttachment(), other.getAttachment());
		
		Location loc = emergency.getLocation();
		Location otherLoc = other.getLocation();
		assertEquals(loc.getAddress(), otherLoc.getAddress());
		assertEquals(loc.getAddress(), otherLoc.getAddress());
		assertEquals(loc.getLatitude(), loc.getLongitude(), 1);
		assertEquals(loc.getCity(), otherLoc.getCity());
		assertEquals(loc.getCountry(), otherLoc.getCountry());
		
		User user = emergency.getUser();
		User otherUser = other.getUser();
		assertEquals(user.getId(), otherUser.getId());
		assertEquals(user.getName(), otherUser.getName());
		assertEquals(user.getPhone(), otherUser.getPhone());
		assertEquals(user.getNotificationKey(), otherUser.getNotificationKey());
	}
	
	@Test
	public void test4FindAll() {
		assertTrue(eDao.findAll().contains(emergency));
	}
	
	@Test
	public void test5findByStatus() {
		assertTrue(eDao.findByStatus(emergency.getStatus()).contains(emergency));
	}
	
	@Test
	public void test6Summary() {
		assertNotNull(eDao.summary(emergency.getStatus()));
	}
	
	@Test
	public void test7SaveChanges() {
		entityManager.getTransaction().begin();
		Emergency other = eDao.find(emergency.getId());
		other.setImei("000000000000000");
		other.setStatus(Status.PROGRESS);
		Date date = new Date();
		other.setEnd(date);
		eDao.save(other);
		entityManager.getTransaction().commit();
		entityManager.clear();
		
		other = eDao.find(other.getId());
		assertEquals("000000000000000", other.getImei());
		assertEquals(Status.PROGRESS, other.getStatus());
		assertEquals(date, other.getEnd());
	}
	
	@Test
	public void test8Remove() {
		entityManager.getTransaction().begin();
		assertTrue(eDao.remove(emergency.getId()));
		entityManager.getTransaction().commit();
		
		assertNull(eDao.find(emergency.getId()));
	}
}