/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.ServicingBranch;

public class ServicingBranchMapper implements RowMapper<ServicingBranch> {

	@Override
	public ServicingBranch mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		ServicingBranch servicing = new ServicingBranch();
		servicing.setId(resultSet.getInt("ID"));
		servicing.setIcmcName(resultSet.getString("ICMC_NAME"));
		servicing.setSolId(resultSet.getInt("SOL_ID"));
		servicing.setBranchName(resultSet.getString("BRANCH"));
		servicing.setRbiJI(resultSet.getString("RBI_JI"));
		servicing.setRbiSI(resultSet.getString("RBI_SI"));
		servicing.setCategory(resultSet.getString("CATEGORY"));
		servicing.setRBIicmc(resultSet.getString("RBI_ICMC"));
		return servicing;
	}

}
