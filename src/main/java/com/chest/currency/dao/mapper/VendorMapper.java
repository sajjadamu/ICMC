/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.Vendor;

public class VendorMapper implements RowMapper<Vendor> {

	@Override
	public Vendor mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Vendor vendor = new Vendor();
		vendor.setId(resultSet.getInt("ID"));
		vendor.setName(resultSet.getString("NAME"));
		vendor.setLocation(resultSet.getString("LOCATION"));
		vendor.setAddress(resultSet.getString("ADDRESS"));
		vendor.setPincode(resultSet.getInt("PINCODE"));
		vendor.setPhoneNumber(resultSet.getInt("PHONE_NUMBER"));
		vendor.setEmailID(resultSet.getString("EMAIL_ID"));
		vendor.setPfRegNumber(resultSet.getString("PF_REG_NUMBER"));
		vendor.setEsicRegNumber(resultSet.getString("ESIC_REG_NUMBER"));
		vendor.setInsertBy(resultSet.getString("INSERT_BY"));
		vendor.setUpdateBy(resultSet.getString("UPDATE_BY"));
		vendor.setInsertTime(resultSet.getDate("INSERT_TIME"));
		vendor.setStatus(resultSet.getString("status"));
		return vendor;
	}

}
