/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.Role;
import com.chest.currency.jpa.dao.RolePermissionJpaDao;

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

	private static final Logger LOG = LoggerFactory.getLogger(RolePermissionServiceImpl.class);

	@Autowired
	protected RolePermissionJpaDao rolePermissionJpaDao;

	@Override
	public List<Role> getRoleList() {
		LOG.info("Going to fetch Roles List");
		List<Role> roleList = rolePermissionJpaDao.getRoleList();
		return roleList;
	}

	@Override
	@Transactional
	public boolean saveRole(Role role) {
		boolean isAllSuccess = false;
		isAllSuccess = rolePermissionJpaDao.saveRole(role);
		LOG.info("New Role has been created");
		return isAllSuccess;
	}

	@Override
	public Role getRoleById(String id) {
		Role role = rolePermissionJpaDao.getRoleById(id);
		return role;
	}

	@Override
	public void updateRole(Role role) {
		Role roleDB = rolePermissionJpaDao.getRoleById(role.getId());
		role.setCreatedBy(roleDB.getCreatedBy());
		role.setCreatedDateTime(roleDB.getCreatedDateTime());
		rolePermissionJpaDao.updateRole(role);
	}

}
