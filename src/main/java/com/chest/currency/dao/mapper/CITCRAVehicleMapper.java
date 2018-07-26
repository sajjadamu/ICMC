/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.CITCRAVehicle;

public class CITCRAVehicleMapper implements RowMapper<CITCRAVehicle> {

	@Override
	public CITCRAVehicle mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		CITCRAVehicle vehicle = new CITCRAVehicle();
		vehicle.setId(resultSet.getInt("ID"));
		vehicle.setName(resultSet.getString("VENDOR_NAME"));
		vehicle.setNumber(resultSet.getString("VEHICLE_NUMBER"));
		vehicle.setBoughtDate(resultSet.getDate("BOUGHT_DATE"));
		vehicle.setRegCityName(resultSet.getString("REGISTRATION_CITY"));
		vehicle.setInsurance(resultSet.getString("VEHICLE_INSURANCE"));
		vehicle.setFitnessExpiryDate(resultSet.getDate("FITNESS_EXPIRY_DATE"));
		vehicle.setPollutionExpiryDate(resultSet.getDate("POLLUTION_EXPIRY_DATE"));
		vehicle.setPermitDate(resultSet.getDate("PERMIT_DATE"));
		vehicle.setInsertTime(resultSet.getDate("INSERT_TIME"));
		vehicle.setUpdateTime(resultSet.getDate("UPDATE_TIME"));
		vehicle.setInsertBy(resultSet.getString("INSERT_BY"));
		vehicle.setUpdateBy(resultSet.getString("UPDATE_BY"));
		return vehicle;
	}

}
