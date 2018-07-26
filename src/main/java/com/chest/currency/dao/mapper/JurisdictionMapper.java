/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.Jurisdiction;

public class JurisdictionMapper implements RowMapper<Jurisdiction> {

	@Override
	public Jurisdiction mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Jurisdiction jurisdiction = new Jurisdiction();
		jurisdiction.setId(resultSet.getInt("ID"));
		jurisdiction.setSolId(resultSet.getInt("SOL_ID"));
		jurisdiction.setBranchName(resultSet.getString("BRANCH_NAME"));
		jurisdiction.setIcmcName(resultSet.getString("ICMC_NAME"));
		jurisdiction.setJurisdiction(resultSet.getString("JURISDICTION"));
		jurisdiction.setCity(resultSet.getString("CITY"));
		jurisdiction.setPincode(resultSet.getInt("PINCODE"));
		jurisdiction.setStatus(resultSet.getString("STATUS"));
		jurisdiction.setInsertBy(resultSet.getString("INSERT_BY"));
		jurisdiction.setUpdateBy(resultSet.getString("UPDATE_BY"));
		jurisdiction.setInsertTime(resultSet.getDate("INSERT_TIME"));
		jurisdiction.setUpdateTime(resultSet.getDate("UPDATE_TIME"));
		return jurisdiction;
	}

}
