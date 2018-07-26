/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.ORVBean;

public class ORVMapper implements RowMapper<ORVBean> {
	@Override
	public ORVBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ORVBean orvBean = new ORVBean();
		orvBean.setId(resultSet.getInt("id"));
		orvBean.setSR(resultSet.getString("sr"));
		orvBean.setSolId(resultSet.getInt("sol_id"));
		orvBean.setBranch(resultSet.getString("branch"));
		orvBean.setVendor(resultSet.getString("vendor"));
		orvBean.setCustodian(resultSet.getString("custodian"));
		orvBean.setVehicle(resultSet.getString("vehicle"));
		orvBean.setAccountNumber(resultSet.getString("account_number"));
		orvBean.setSackLock(resultSet.getString("sack_lock_number"));
		return orvBean;
	}
}
