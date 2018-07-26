/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.util.List;

import com.chest.currency.viewBean.BinColor;
import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.BinTransaction;
import com.chest.currency.viewBean.BinTransactionSummary;
import com.chest.currency.viewBean.ICMC;

public interface BinDashboardDao {
	
	public List<BinColor> getBinNumAndColorCode(int icmcId);

	public List<String> getAvailableCapacity(String bin);
	
	public List<BinTransactionSummary> getBinSummary(int denomination,String binType);
	
	public List<BinMaster> getBinMasterRecord();
	
	public int insertDataInBinMaster(BinMaster binMaster);
	
	public List<BinTransaction> searchBin(int denomination,String binType,int icmcId);
	
	public List<BinTransactionSummary> getRecordForSummary(int icmcId);
	
	public List<ICMC> getICMCName();
}
