/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.BinTransaction;

public class BinTransactionMapper implements RowMapper<BinTransaction> {
	public BinTransaction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		BinTransaction binData = new BinTransaction();
		binData.setId(resultSet.getInt("id"));
		binData.setBinNumber(resultSet.getString("bin_num"));
		binData.setDenomination(resultSet.getInt("denomination"));
		binData.setMaxCapacity(resultSet.getInt("max_capacity"));
		binData.setInsertTime(resultSet.getTimestamp("insert_time"));
		binData.setReceiveBundles(resultSet.getInt("receive_bundle"));
		binData.setBinType(resultSet.getString("bin_type"));
		binData.setInsertBy(resultSet.getString("insert_by"));
		binData.setUpdateTime(resultSet.getTimestamp("update_time"));
		binData.setUpdateBy(resultSet.getString("update_by"));
		binData.setICMCId(resultSet.getInt("ICMC_ID"));
		return binData;
	}
}
