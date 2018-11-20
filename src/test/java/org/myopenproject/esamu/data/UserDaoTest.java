package org.myopenproject.esamu.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.myopenproject.esamu.data.dao.UserDao;
import org.myopenproject.esamu.data.dao.Validator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTest 
{
	static User user;
	static UserDao dao;
	
	@BeforeClass
	public static void init() {
		user = new User();
		user.setId("3EeyQIDa9FNcbGv1kYjS44ErOlz2");
		user.setName("Name");
		user.setPhone("12345678");
		user.setNotificationKey("b128e848-25a2-4f82-9bf4-ac8c63e4882d");
	}
	
	@Before
	public void newUserDao() {
		dao = new UserDao();
	}
	
	@After
	public void closeUserDao() {
		dao.close();
	}
	
    @Test
    public void test1Validation() {
        assertNull(Validator.validate(user));
    }
    
    @Test
    public void test2Save() {
    	dao.save(user);
    }
    
    @Test
    public void test3Find() {
    	assertEquals(user, dao.find(user.getId()));
    }
    
    @Test
    public void test4FindAll() {
    	assertTrue(dao.findAll().contains(user));
    }
    
    @Test
    public void test5SaveChanges() {
    	User u = dao.find(user.getId());
    	u.setName("Other name");
    	dao.save(u);
    	assertNotEquals(user, dao.find(u.getId()));
    }
    
    @Test
    public void test6Remove() {
    	assertNotNull(dao.remove(user.getId()));
    }
}
