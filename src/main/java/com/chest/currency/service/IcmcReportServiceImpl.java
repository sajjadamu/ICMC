/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.ICMCReport;
import com.chest.currency.jpa.dao.IcmcReportJpaDao;

@Service
@Transactional
public class IcmcReportServiceImpl implements IcmcReportService {
	
	private static final Logger LOG = LoggerFactory.getLogger(IcmcReportServiceImpl.class);
	
	@Autowired
	protected IcmcReportJpaDao icmcReportJpaDao;

	@Override
	public List<ICMCReport> getICMCReport() {
		LOG.info("getICMCReport");
		List<ICMCReport> icmcReportList = icmcReportJpaDao.getICMCReportList();
		return icmcReportList;
	}

	@Override
	public ICMCReport isCustomReportTypeNameValid(String newReportType) {
		ICMCReport icmcCustomReportName = icmcReportJpaDao.isCustomReportTypeNameValid(newReportType);
		return icmcCustomReportName;
	}
	
	@Override
	public boolean saveCustomICMCReport(ICMCReport icmcReport) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = icmcReportJpaDao.saveICMCReport(icmcReport);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public ICMCReport getReportById(int id) {
		ICMCReport icmcReport = icmcReportJpaDao.getReportById(id);
		return icmcReport;
	}

	@Override
	public boolean deleteReport(ICMCReport icmcReport) {
		icmcReportJpaDao.deleteReport(icmcReport);
		return true;
	}

}
