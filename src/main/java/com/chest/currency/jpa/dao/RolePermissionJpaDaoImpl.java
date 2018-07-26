/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.QRole;
import com.chest.currency.entity.model.Role;
import com.mysema.query.jpa.impl.JPAQuery;

@Repository
public class RolePermissionJpaDaoImpl implements RolePermissionJpaDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(RolePermissionJpaDaoImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Role> getRoleList() {
		JPAQuery jpaQuery = getFromQueryForRole();
		List<Role> roleList = jpaQuery.list(QRole.role);
		LOG.info("Fetched Role List");
		return roleList;
	}
	
	private JPAQuery getFromQueryForRole(){
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QRole.role);
		return jpaQuery;
	}

	@Override
	public boolean saveRole(Role role) {
		em.persist(role);
		return true;
	}

	@Override
	public Role getRoleById(String id) {
		return em.find(Role.class, id);
	}

	@Override
	public void updateRole(Role role) {
		em.merge(role);
	}

}
