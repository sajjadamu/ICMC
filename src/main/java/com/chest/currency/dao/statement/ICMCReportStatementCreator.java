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

import com.chest.currency.viewBean.ICMCReport;

public class ICMCReportStatementCreator implements PreparedStatementCreator {

	private ICMCReport icmcReport;
	
	public ICMCReportStatementCreator(ICMCReport icmcReport){
		this.icmcReport = icmcReport;
	}
	
	@Override
	public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
		String Query = "INSERT INTO ICMC_REPORT (CUSTOM_REPORT_TYPE,INSERT_BY,UPDATE_BY,INSERT_TIME,UPDATE_TIME) VALUES(?,?,?,now(),now());";
		PreparedStatement preparedStatement = conn.prepareStatement(Query, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, icmcReport.getNewReportType());
		preparedStatement.setString(2, icmcReport.getInsertBy());
		preparedStatement.setString(3, icmcReport.getUpdateBy());
		return preparedStatement;
	}
}
