/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.ICMCReport;

public interface IcmcReportService {
	
	public List<ICMCReport> getICMCReport();
	
	public ICMCReport isCustomReportTypeNameValid(String newReportType);
	
	public boolean saveCustomICMCReport(ICMCReport icmcReport);
	
	public ICMCReport getReportById(int id);
	
	public boolean deleteReport(ICMCReport icmcReport);

}
