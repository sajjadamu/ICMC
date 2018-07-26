/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.UserBean;

public class UserByIdMapper implements RowMapper<UserBean> {
	public UserBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		UserBean userBean = new UserBean();
		userBean.setId(resultSet.getInt("id"));
		userBean.setUsername(resultSet.getString("username"));
		userBean.setUserId(resultSet.getString("userId"));
		userBean.setEmail(resultSet.getString("email"));
		userBean.setZone(resultSet.getString("zone"));
		userBean.setRegion(resultSet.getString("region"));
		userBean.setIcmcId(resultSet.getInt("ICMC_ID"));
		userBean.setStatus(resultSet.getString("status"));
		userBean.setRoles(resultSet.getString("roles"));
		userBean.setRole(resultSet.getString("roles").split(","));
		return userBean;
	}
}
