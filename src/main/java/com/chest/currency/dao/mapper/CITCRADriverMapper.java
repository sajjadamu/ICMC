/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.CITCRADriver;

public class CITCRADriverMapper implements RowMapper<CITCRADriver> {

	@Override
	public CITCRADriver mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		CITCRADriver driver = new CITCRADriver();
		driver.setId(resultSet.getInt("ID"));
		driver.setVendorName(resultSet.getString("VENDOR_NAME"));
		driver.setVehicleNumber(resultSet.getString("VEHICLE_NUMBER"));
		driver.setDriverName(resultSet.getString("DRIVER_NAME"));
		driver.setLicenseNumber(resultSet.getString("LICENSE_NUMBER"));
		driver.setLicenseIssuedState(resultSet.getString("LICENSE_ISSUED_STATE"));
		driver.setLicenseIssuedDated(resultSet.getDate("LICENSE_ISSUE_DATE"));
		driver.setLicenseExpiryDate(resultSet.getDate("LICENSE_EXPIRY_DATE"));
		driver.setInsertBy(resultSet.getString("INSERT_BY"));
		driver.setUpdateBy(resultSet.getString("UPDATE_BY"));
		driver.setInsertTime(resultSet.getTimestamp("INSERT_TIME"));
		driver.setUpdateTime(resultSet.getTimestamp("UPDATE_TIME"));
		return driver;
	}

}
