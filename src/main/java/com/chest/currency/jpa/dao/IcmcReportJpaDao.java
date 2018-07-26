/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import com.chest.currency.entity.model.ICMCReport;

public interface IcmcReportJpaDao {
	
	public ICMCReport isCustomReportTypeNameValid(String newReportType);
	
	public List<ICMCReport> getICMCReportList();
	
	public boolean saveICMCReport(ICMCReport icmcReport);
	
	public ICMCReport getReportById(int id);
	
	public boolean deleteReport(ICMCReport icmcReport);
	
}
