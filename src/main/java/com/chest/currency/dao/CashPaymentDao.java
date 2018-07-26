/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.util.List;

import com.chest.currency.viewBean.DorvAllocation;
import com.chest.currency.viewBean.DorvBean;
import com.chest.currency.viewBean.ORVAllocation;
import com.chest.currency.viewBean.ORVBean;
import com.chest.currency.viewBean.SASAllocation;
import com.chest.currency.viewBean.SASBean;
import com.chest.currency.viewBean.SoiledRemittance;
import com.chest.currency.viewBean.SoiledRemittanceAllocation;

public interface CashPaymentDao {

	public int bulkIndentRequest(List<SASBean> bulkIndentList);
	
	public List<SASBean> getSASRecord();
	
	public int sasUpdate(SASBean sasBean);
	
	public int insertInSASAllocation(SASAllocation sasAllocation);
	
	public List<SASAllocation> getSASAllocation();
	
	public List<SoiledRemittanceAllocation> getSoiledRemittanceAllocation();
	
	public int updateSASStatus();
	
	public boolean insertInSoiledRemmitance(SoiledRemittance soiledRemittance);
	
	public boolean insertInSoiledRemmitanceAllocation(List<SoiledRemittanceAllocation> soiledList, SoiledRemittance soiledRemittance);
	
	public boolean saveSoiledAndSoiledAllocation(SoiledRemittance soiledRemmitance);
	
	public boolean insertInDorv(DorvBean dorvbean);
	
	public boolean insertInDorvAllocation(List<DorvAllocation> dorvList, DorvBean dorvbean);
	
	public boolean saveDorvAndDorvAllocation(DorvBean dorvBean);

	public boolean insertInORV(ORVBean orvBean);
	
	public boolean insertInORVAllocation(List<ORVAllocation> orvList, ORVBean orvBean);
	
	public boolean saveOrvAndOrvAllocation(ORVBean orvBean);
	
	public List<ORVAllocation> getORVAllocationList();
	
	public List<DorvAllocation> getDORVAllocationList();
	
	public List<DorvBean> getDORVRecords();
	
	public List<SoiledRemittance> getSoiledRecord();
	
	public List<ORVBean> getORVRecords();
}
