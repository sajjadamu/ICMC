/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.BinMaster;

public class BinMasterMapper implements RowMapper<BinMaster> {
	public BinMaster mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		BinMaster binMaster = new BinMaster();
		binMaster.setId(resultSet.getInt("id"));
		binMaster.setBinNumber(resultSet.getString("bin_num"));
		binMaster.setMaxBundleCapacity(resultSet.getInt("max_bundle_capacity"));
		binMaster.setFirstPriority(resultSet.getString("first_priority"));
		/*binMaster.setSecondPriority(resultSet.getString("second_priority"));
		binMaster.setThirdPriority(resultSet.getString("third_priority"));
		binMaster.setLocationPriority(resultSet.getInt("loca_priority"));*/
		binMaster.setDenom(resultSet.getInt("denomination"));
		binMaster.setInsertBy(resultSet.getString("insert_by"));
		binMaster.setUpdateBy(resultSet.getString("update_by"));
		binMaster.setICMCId(resultSet.getInt("icmc_id"));
		return binMaster;
	}
}
