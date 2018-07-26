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

import com.chest.currency.viewBean.ORVBean;

public class ORVStatementCreator implements PreparedStatementCreator {

	private ORVBean orvBean;

	public ORVStatementCreator(ORVBean orvBean) {
		this.orvBean = orvBean;
	}

	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String Query = "insert into tbl_orv(sr,sack_lock_number,sol_id,branch,vendor,custodian,vehicle,account_number,insert_time,update_time,insert_by,update_by) values (?,?,?,?,?,?,?,?,now(),now(),?,?);";
		PreparedStatement preparedStatement = conn.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, orvBean.getSR());
		preparedStatement.setString(2, orvBean.getSackLock());
		preparedStatement.setInt(3, orvBean.getSolId());
		preparedStatement.setString(4, orvBean.getBranch());
		preparedStatement.setString(5, orvBean.getVendor());
		preparedStatement.setString(6, orvBean.getCustodian());
		preparedStatement.setString(7, orvBean.getVehicle());
		preparedStatement.setString(8, orvBean.getAccountNumber());
		preparedStatement.setString(9, orvBean.getInsertBy());
		preparedStatement.setString(10, orvBean.getUpdateBy());
		return preparedStatement;
	}

}
