/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.ICMC;

public class ICMCNameForVendorMapper implements RowMapper<ICMC> {

	@Override
	public ICMC mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		ICMC  icmc = new ICMC();
		icmc.setId(resultSet.getInt("ID"));
		icmc.setName(resultSet.getString("NAME"));
		return icmc;
	}

}
