/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.BranchBean;

public class BRANCHMapper implements RowMapper<BranchBean> {

	@Override
	public BranchBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		BranchBean branchBean = new BranchBean();
		branchBean.setId(resultSet.getInt("ID"));
		branchBean.setSolId(resultSet.getInt("SOL_ID"));
		branchBean.setBranch(resultSet.getString("BRANCH_NAME"));
		branchBean.setAddress(resultSet.getString("ADDRESS"));
		branchBean.setLocation(resultSet.getString("LOCATION"));
		branchBean.setCity(resultSet.getString("CITY"));
		branchBean.setPincode(resultSet.getInt("PINCODE"));
		branchBean.setPrimaryICMC(resultSet.getInt("PRIMARY_ICMC"));
		branchBean.setSecondaryICMC(resultSet.getInt("SECONDARY_ICMC"));
		return branchBean;
	}

}
