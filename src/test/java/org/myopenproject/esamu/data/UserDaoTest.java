package org.myopenproject.esamu.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.myopenproject.esamu.data.dao.JpaUtil;
import org.myopenproject.esamu.data.dao.UserDao;
import org.myopenproject.esamu.data.model.User;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest 
{
	static User user;
	static UserDao dao;
	static EntityManager entityManager;
	
	@BeforeClass
	public static void init() {
		user = new User();
		user.setId("3EeyQIDa9FNcbGv1kYjS44ErOlz2");
		user.setName("Name");
		user.setPhone("12345678");
		user.setNotificationKey("b128e848-25a2-4f82-9bf4-ac8c63e4882d");
	}
	
	@AfterClass
	public static void cleanUp() {
		JpaUtil.close();
	}
	
	@Before
	public void beforeEach() {
		entityManager = JpaUtil.getEntityManager();
		dao = new UserDao(entityManager);
	}
	
	@After
	public void afterEach() {
		entityManager.close();
	}
    
    @Test
    public void test2Save() {
    	entityManager.getTransaction().begin();
    	dao.save(user);
    	entityManager.getTransaction().commit();
    }
    
    @Test
    public void test3Find() {
    	User other = dao.find(user.getId());
    	assertEquals(user.getId(), other.getId());
    	assertEquals(user.getName(), other.getName());
    	assertEquals(user.getPhone(), other.getPhone());
    	assertEquals(user.getNotificationKey(), other.getNotificationKey());
    }
    
    @Test
    public void test4FindAll() {
    	assertTrue(dao.findAll().contains(user));
    }
    
    @Test
    public void test5SaveChanges() {
    	entityManager.getTransaction().begin();
    	User other = dao.find(user.getId());
    	other.setName("Other name");
    	entityManager.getTransaction().commit();
    	entityManager.clear();
    	
    	other = dao.find(other.getId());
    	assertNotNull(other);
    	assertEquals("Other name", other.getName());
    }
    
    @Test
    public void test6Remove() {
    	entityManager.getTransaction().begin();
    	assertTrue(dao.remove(user.getId()));
    	entityManager.getTransaction().commit();
    	
    	assertNull(dao.find(user.getId()));
    }
}
