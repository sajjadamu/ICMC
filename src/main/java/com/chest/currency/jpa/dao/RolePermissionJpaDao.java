/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import com.chest.currency.entity.model.Role;

public interface RolePermissionJpaDao {
	
	public List<Role> getRoleList();
	
	public boolean saveRole(Role role);

	public Role getRoleById(String id);

	public void updateRole(Role role);

}
