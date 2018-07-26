/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.statement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.jdbc.core.PreparedStatementCreator;

import com.chest.currency.viewBean.SoiledRemittance;

public class SoiledStatementCreator implements PreparedStatementCreator {

	private SoiledRemittance soiledRemittance;
	
	public SoiledStatementCreator(SoiledRemittance soiledRemittance){
		this.soiledRemittance = soiledRemittance;
	}
	
	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String Query = "insert into tbl_soiled_remittance (order_date,remittance_order_no,approved_remittance_date,notes,type,vehicle_number,location,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,?,?,now(),now(),?,?);";
		PreparedStatement preparedStatement = conn.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setDate(1,soiledRemittance.getOrderDate());
		preparedStatement.setString(2, soiledRemittance.getRemittanceOrderNumber());
		preparedStatement.setDate(3, soiledRemittance.getApprovedRemmitanceDate());
		preparedStatement.setString(4, soiledRemittance.getNotes());
		preparedStatement.setString(5, soiledRemittance.getType());
		preparedStatement.setString(6, soiledRemittance.getVehicleNumber());
		preparedStatement.setString(7,soiledRemittance.getLocation());
		preparedStatement.setString(8, soiledRemittance.getInsertBy());
		preparedStatement.setString(9, soiledRemittance.getUpdateBy());
		return preparedStatement;
	}

}
