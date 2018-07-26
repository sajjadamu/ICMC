/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.Role;

public interface RolePermissionService {
	
	public List<Role> getRoleList();
	
	public boolean saveRole(Role role);

	public Role getRoleById(String id);

	public void updateRole(Role role);

}
