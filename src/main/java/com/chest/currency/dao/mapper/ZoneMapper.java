/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class ZoneMapper implements RowMapper<String> {
	public String mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return resultSet.getString(1);
	}
}
