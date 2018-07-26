/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.IndentRequestBean;

public class IndentRequestBeanMapper implements RowMapper<IndentRequestBean> {
	public IndentRequestBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		IndentRequestBean indentData = new IndentRequestBean();
		indentData.setId(resultSet.getInt("id"));
		indentData.setDenomination(resultSet.getInt("denomination"));
		indentData.setBundle(resultSet.getDouble("bundle"));
		indentData.setStatus(resultSet.getInt("status"));
		indentData.setBin(resultSet.getString("bin_num"));
		indentData.setDescription(resultSet.getString("description"));
		indentData.setInsertTime(resultSet.getTimestamp("insert_time"));
		indentData.setUpdateTime(resultSet.getTimestamp("update_time"));
		indentData.setInsertBy(resultSet.getString("insert_by"));
		indentData.setUpdateBy(resultSet.getString("update_by"));
		return indentData;
	}
}
