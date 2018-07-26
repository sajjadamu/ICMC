/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.ProcessBean;

public class ProcessMapper implements RowMapper<ProcessBean> {

	@Override
	public ProcessBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ProcessBean process = new ProcessBean();
		process.setId(resultSet.getInt("id"));
		process.setType(resultSet.getString("currency_type"));
		process.setDenomination(resultSet.getInt("denomination"));
		process.setTotal(resultSet.getInt("total"));
		process.setBundle(resultSet.getDouble("bundle"));
		process.setInsertTime(resultSet.getDate("insert_time"));
		process.setBin(resultSet.getString("bin_number"));
		process.setMachineNo(resultSet.getInt("machine_no"));
		return process;
	}

}
