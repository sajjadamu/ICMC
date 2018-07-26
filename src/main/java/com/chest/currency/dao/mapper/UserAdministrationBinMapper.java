/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.UserAdministration;

public class UserAdministrationBinMapper implements RowMapper<UserAdministration> {
	public UserAdministration mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		UserAdministration userAdministration = new UserAdministration();
		userAdministration.setId(resultSet.getInt("id"));
		userAdministration.setIcmcName(resultSet.getString("icmc_name"));
		userAdministration.setBinName(resultSet.getString("bin_name"));
		userAdministration.setInsertBy(resultSet.getString("insert_by"));
		userAdministration.setUpdateBy(resultSet.getString("update_by"));
		userAdministration.setInsertTime(resultSet.getDate("insert_time"));
		userAdministration.setUpdateTime(resultSet.getDate("update_time"));
		return userAdministration;
	}
}
