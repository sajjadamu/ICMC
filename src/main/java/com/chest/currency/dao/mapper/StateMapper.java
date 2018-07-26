/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.HolidayMaster;

public class StateMapper implements RowMapper<HolidayMaster> {
	public HolidayMaster mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		HolidayMaster holiday = new HolidayMaster();
		holiday.setId(resultSet.getInt("ID"));
		holiday.setState(resultSet.getString("STATE_NAME"));
		return holiday;
	}

}
