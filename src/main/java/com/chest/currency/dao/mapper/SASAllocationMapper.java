/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.SASAllocation;

public class SASAllocationMapper implements RowMapper<SASAllocation> {

	@Override
	public SASAllocation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		SASAllocation sasAllocation = new SASAllocation();
		sasAllocation.setId(resultSet.getInt("id"));
		sasAllocation.setFreshBin(resultSet.getString("fresh_bin"));
		sasAllocation.setIssuableBin(resultSet.getString("issuable_bin"));
		sasAllocation.setDenomination(resultSet.getInt("denomination"));
		sasAllocation.setInsertTime(resultSet.getTimestamp("insert_time"));
		sasAllocation.setUpdateTime(resultSet.getTimestamp("update_time"));
		sasAllocation.setInsertBy(resultSet.getString("insert_time"));
		sasAllocation.setUpdateBy(resultSet.getString("update_by"));
		sasAllocation.setCategoryFresh(resultSet.getInt("category_fresh"));
		sasAllocation.setCategoryIssuable(resultSet.getInt("category_issuable"));
		return sasAllocation;
	}

}
