/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class VehicleNumberMapper implements RowMapper<String> {

	@Override
	public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		String vehicleNumber=resultSet.getString("VEHICLE_NUMBER");
		return vehicleNumber;
	}

}
