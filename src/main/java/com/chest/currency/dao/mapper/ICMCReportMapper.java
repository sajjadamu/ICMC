/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.ICMCReport;

public class ICMCReportMapper implements RowMapper<ICMCReport> {
	public ICMCReport mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		ICMCReport report = new ICMCReport();
		report.setId(resultSet.getInt("ID"));
		report.setNewReportType(resultSet.getString("CUSTOM_REPORT_TYPE"));
		report.setReportContent(resultSet.getString("REPORT_CONTENT"));
		return report;
	}

}
