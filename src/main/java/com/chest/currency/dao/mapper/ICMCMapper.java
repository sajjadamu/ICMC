/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.ICMC;

public class ICMCMapper implements RowMapper<ICMC> {

	@Override
	public ICMC mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ICMC icmc = new ICMC();
		icmc.setId(resultSet.getInt("ID"));
		icmc.setAddress(resultSet.getString("ADDRESS"));
		icmc.setZone(resultSet.getString("ZONE"));
		icmc.setRegion(resultSet.getString("REGION"));
		icmc.setLocation(resultSet.getString("LOCATION"));
		icmc.setCity(resultSet.getString("CITY"));
		icmc.setPincode(resultSet.getString("PINCODE"));
		icmc.setName(resultSet.getString("NAME"));
		icmc.setInsertBy(resultSet.getString("INSERT_BY"));
		icmc.setUpdateBy(resultSet.getString("UPDATE_BY"));
		icmc.setInsertTime(resultSet.getDate("INSERT_TIME"));
		icmc.setUpdateTime(resultSet.getDate("UPDATE_TIME"));
		return icmc;
	}

}
