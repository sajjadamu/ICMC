/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.CITCRAVendor;

public class VendorForVehicleMapper implements RowMapper<CITCRAVendor>{

	@Override
	public CITCRAVendor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		CITCRAVendor vendor = new CITCRAVendor();
		vendor.setName(resultSet.getString("NAME"));
		return vendor;
	}

}
