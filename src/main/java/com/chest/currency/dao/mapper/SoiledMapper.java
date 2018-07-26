/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.SoiledRemittance;

public class SoiledMapper implements RowMapper<SoiledRemittance> {

	@Override
	public SoiledRemittance mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		SoiledRemittance soiledBean = new SoiledRemittance();
		soiledBean.setId(resultSet.getInt("id"));
		soiledBean.setOrderDate(resultSet.getDate("order_date"));
		soiledBean.setRemittanceOrderNumber(resultSet.getString("remittance_order_no"));
		soiledBean.setApprovedRemmitanceDate(resultSet.getDate("approved_remittance_date"));
		soiledBean.setNotes(resultSet.getString("notes"));
		soiledBean.setType(resultSet.getString("type"));
		soiledBean.setVehicleNumber(resultSet.getString("vehicle_number"));
		soiledBean.setLocation(resultSet.getString("location"));
		return soiledBean;
	}

}
