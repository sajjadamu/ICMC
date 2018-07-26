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

import com.chest.currency.viewBean.CITCRAVendor;

public class CITCRAVendorICMCAssoCreator implements PreparedStatementCreator {
	private CITCRAVendor vendor;

public CITCRAVendorICMCAssoCreator(CITCRAVendor vendor){
	this.vendor = vendor;
}

@Override
public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
	String sql = "insert into CIT_CRA_VENDOR (NAME,TYPE_ONE,TYPE_TWO,TYPE_THREE,FPR_NAME,FPR_NUMBER,INSERT_BY,UPDATE_BY,INSERT_TIME,UPDATE_TIME,ZONE,REGION) values (?,?,?,?,?,?,?,?,now(),now(),?,?);";
	PreparedStatement preparedStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	preparedStatement.setString(1, vendor.getName());
	preparedStatement.setString(2, vendor.getTypeOne());
	preparedStatement.setString(3, vendor.getTypeTwo());
	preparedStatement.setString(4, vendor.getTypeThree());
	preparedStatement.setString(5, vendor.getFPRName());
	preparedStatement.setString(6, vendor.getFPRNumber());
	preparedStatement.setString(7, vendor.getInsertBy());
	preparedStatement.setString(8, vendor.getUpdateBy());
	preparedStatement.setString(9, vendor.getZone());
	preparedStatement.setString(10, vendor.getRegion());
	//preparedStatement.setString(11, vendor.getIcmc());
	return preparedStatement;
}
}
