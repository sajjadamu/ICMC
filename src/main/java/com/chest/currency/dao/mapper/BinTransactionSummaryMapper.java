/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.BinTransactionSummary;

public class BinTransactionSummaryMapper implements RowMapper<BinTransactionSummary> {
	public BinTransactionSummary mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		BinTransactionSummary binData = new BinTransactionSummary();
		binData.setDenomination(resultSet.getInt("denomination"));
		binData.setReceiveBundle(resultSet.getBigDecimal("receive_bundle"));
		binData.setBinType(resultSet.getString("bin_type"));
		return binData;
	}
}
