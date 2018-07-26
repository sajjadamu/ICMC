/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.SoiledRemittanceAllocation;

public class SoiledAllocationMapper implements RowMapper<SoiledRemittanceAllocation> {

	@Override
	public SoiledRemittanceAllocation mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		SoiledRemittanceAllocation soiledRemittanceAllocation = new SoiledRemittanceAllocation();
		soiledRemittanceAllocation.setId(resultSet.getInt("id"));
		soiledRemittanceAllocation.setDenomination(resultSet.getInt("denomination"));
		soiledRemittanceAllocation.setBundle(resultSet.getInt("bundle"));
		soiledRemittanceAllocation.setTotal(resultSet.getInt("total"));
		soiledRemittanceAllocation.setBin(resultSet.getString("bin"));
		soiledRemittanceAllocation.setBox(resultSet.getString("box"));
		soiledRemittanceAllocation.setWeight(resultSet.getString("weight"));
		return soiledRemittanceAllocation;
	}

}
