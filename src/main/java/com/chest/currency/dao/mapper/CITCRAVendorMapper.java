/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.CITCRAVendor;

public class CITCRAVendorMapper implements RowMapper<CITCRAVendor> {

	@Override
	public CITCRAVendor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		CITCRAVendor vendor = new CITCRAVendor();
		vendor.setId(resultSet.getInt("ID"));
		vendor.setName(resultSet.getString("NAME"));
		vendor.setTypeOne(resultSet.getString("TYPE_ONE"));
		vendor.setTypeTwo(resultSet.getString("TYPE_TWO"));
		vendor.setTypeThree(resultSet.getString("TYPE_THREE"));
		vendor.setFPRName(resultSet.getString("FPR_NAME"));
		vendor.setFPRNumber(resultSet.getString("FPR_NUMBER"));
		vendor.setInsertBy(resultSet.getString("INSERT_BY"));
		vendor.setUpdateBy(resultSet.getString("UPDATE_BY"));
		vendor.setInsertTime(resultSet.getDate("INSERT_TIME"));
		vendor.setUpdateTime(resultSet.getDate("UPDATE_TIME"));
		vendor.setZone(resultSet.getString("ZONE"));
		vendor.setRegion(resultSet.getString("REGION"));
		return vendor;
	}

}
