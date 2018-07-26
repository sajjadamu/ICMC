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

import com.chest.currency.viewBean.DorvBean;

public class DiversionOrvStatementCreator implements PreparedStatementCreator {

	private DorvBean dorvbean;
	
	public DiversionOrvStatementCreator(DorvBean dorvbean){
		this.dorvbean = dorvbean;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String sql = "insert into tbl_diversion_orv (order_date,rbi_order_no,expiry_date,bank_name,approved_cc,location,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,?,now(),now(),?,?);";
		PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setDate(1,dorvbean.getOrderDate());
		preparedStatement.setString(2, dorvbean.getRBIOrderNumber());
		preparedStatement.setDate(3, dorvbean.getExpiryDate());
		preparedStatement.setString(4, dorvbean.getBankName());
		preparedStatement.setString(5, dorvbean.getApprovedCC());
		preparedStatement.setString(6, dorvbean.getLocation());
		preparedStatement.setString(7, dorvbean.getInsertBy());
		preparedStatement.setString(8, dorvbean.getUpdateBy());
		return preparedStatement;
	}

}
