/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.DorvAllocation;

public class DORVAllocationMapper implements RowMapper<DorvAllocation> {

	@Override
	public DorvAllocation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		DorvAllocation dorv = new DorvAllocation();
		dorv.setDenomination(resultSet.getInt("denomination"));
		dorv.setBundle(resultSet.getInt("bundle"));
		dorv.setTotal(resultSet.getInt("total"));
		dorv.setBin(resultSet.getString("bin"));
		dorv.setCategory(resultSet.getString("category"));
		return dorv;
	}
}
