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
		return rolePermissionJpaDao.getRoleList();
	}

	@Override
	@Transactional
	public boolean saveRole(Role role) {
		LOG.info("New Role has been created");
		return rolePermissionJpaDao.saveRole(role);
	}

	@Override
	public Role getRoleById(String id) {
		return rolePermissionJpaDao.getRoleById(id);
	}

	@Override
	public void updateRole(Role role) {
		Role roleDB = rolePermissionJpaDao.getRoleById(role.getId());
		role.setCreatedBy(roleDB.getCreatedBy());
		role.setCreatedDateTime(roleDB.getCreatedDateTime());
		rolePermissionJpaDao.updateRole(role);
	}

}
