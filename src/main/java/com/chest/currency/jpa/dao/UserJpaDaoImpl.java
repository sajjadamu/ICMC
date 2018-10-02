package com.chest.currency.jpa.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.QUser;
import com.chest.currency.entity.model.User;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class UserJpaDaoImpl implements UserJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserJpaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;
	
	private JPAQuery getFromQueryForUser(){
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QUser.user);
		return jpaQuery;
	}

	@Override
	public User isValidUser(User userTemp) {
		LOG.info("Authenticating User Credentials");
		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.id.equalsIgnoreCase(userTemp.getId())
				.and(QUser.user.password.eq(userTemp.getPassword())));
		return jpaQuery.singleResult(QUser.user);
	}

}
