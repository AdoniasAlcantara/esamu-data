package org.myopenproject.esamu.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.myopenproject.esamu.data.Emergency.Status;
import org.myopenproject.esamu.data.dao.EmergencyDao;
import org.myopenproject.esamu.data.dao.UserDao;
import org.myopenproject.esamu.data.dao.Validator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmergencyDaoTest {
	static EmergencyDao eDao;
	static Emergency emergency;

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
		emergency.setLocation(location);
		
		Multimedia multimedia = new Multimedia();
		multimedia.setPicture(Files.readAllBytes(Paths.get(EmergencyDaoTest.class
				.getClassLoader().getResource("picture.jpg").getFile())));
		emergency.setMultimedia(multimedia);
		EmergencyDao.setResourcesPath("/tmp");
	}
	
	@Before
	public void newEmergencyDao() {
		eDao = new EmergencyDao();
	}
	
	@After
	public void closeEmergencyDao() {
		eDao.close();
	}
	
	@Test
	public void test1Validation() {
		assertNull(Validator.validate(emergency));
	}
	
	@Test
	public void test2Save() {
		UserDao uDao = new UserDao();
		uDao.save(emergency.getUser());
		uDao.close();
		eDao.save(emergency);
	}
	
	@Test
	public void test3Find() {
		assertEquals(emergency, eDao.find(emergency.getId()));
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
		Emergency em = eDao.find(emergency.getId());
		em.setImei("000000000000000");
		em.setStatus(Status.PROGRESS);
		em.setEnd(new Date());
		eDao.save(em);
		assertNotEquals(emergency, eDao.find(em.getId()));
	}
	
	@Test
	public void test8Remove() {
		assertNotNull(eDao.remove(emergency.getId()));
		UserDao uDao = new UserDao();
		assertNotNull(uDao.remove(emergency.getUser().getId()));
		uDao.close();
	}
}