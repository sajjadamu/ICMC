/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.ORVAllocation;

public class ORVAllocationMapper implements RowMapper<ORVAllocation> {

	@Override
	public ORVAllocation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ORVAllocation orv = new ORVAllocation();
		orv.setDenomination(resultSet.getInt("denomination"));
		orv.setBundle(resultSet.getInt("bundle"));
		orv.setTotal(resultSet.getInt("total"));
		orv.setBin(resultSet.getString("bin"));
		return orv;
	}

}
