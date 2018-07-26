/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.BinCapacity;

public class BinCapacityMapper implements RowMapper<BinCapacity> {

	@Override
	public BinCapacity mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		BinCapacity binCapacity = new BinCapacity();
		binCapacity.setId(resultSet.getInt("id"));
		binCapacity.setBinCapacity(resultSet.getInt("max_bundle_capacity"));
		binCapacity.setDenomination(resultSet.getInt("denomination"));
		return binCapacity;
	}

}
