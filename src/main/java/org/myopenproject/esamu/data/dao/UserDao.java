package org.myopenproject.esamu.data.dao;

import javax.persistence.EntityManager;

import org.myopenproject.esamu.data.model.User;

public class UserDao extends AbstractDao<User, String> {
	public UserDao(EntityManager entityManager) {
		super(entityManager);
	}
}
