/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.util.ArrayList;

import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.CashProcessing;
import com.chest.currency.viewBean.DSBBean;
import com.chest.currency.viewBean.DirvBean;
import com.chest.currency.viewBean.FreshBean;
import com.chest.currency.viewBean.IRVBean;
import com.chest.currency.viewBean.IndentRequestBean;
import com.chest.currency.viewBean.MachineAllocationBean;
import com.chest.currency.viewBean.ProcessBean;
import com.chest.currency.viewBean.ShrinkBean;

public interface UserDao {
	
	public void saveORVpayment(String sql[]);
	
	public ArrayList<ShrinkBean> getShrinkList();

	public String getCapacityByBinNum(String binNum);

	public String getAvailBin(String denomination);

	public String getBranchName(String solID);

	public ArrayList<FreshBean> getFreshData();

	public int freshEntry(FreshBean freshBean);

	public ArrayList<String> getCustodianAndVehicle(String vendor);

	public ArrayList<String> getVehicleNumber(String vendor);

	public void saveORVEntry(String sql[]);

	public void saveIRVEntry(String sql[]);

	public ArrayList<IRVBean> getIRVData();

	public ArrayList<String> getBinByDenomination(String denomination);

	public ArrayList<IRVBean> getIRVVoucher(String sDate);

	public ArrayList<BinMaster> getBinDetailsByDenoAndType(String denomination, String type);

	public int cashProcessing(CashProcessing cash);

	public ArrayList<CashProcessing> getCashProcessingDetails();

	public ArrayList<DirvBean> getDirvData();

	public int insertCurrentNoOfBundlesAndAvailCapacity(BinMaster binMaster);

	public String getAvailableCapacityForShrink(String bin);

	public ArrayList<String> getBranch(String region);

	public int updateBinForWithdrawl(BinMaster bean);

	public int updateBinForDeposit(BinMaster bean);

	public int insertUnprocessedForCustodian(ShrinkBean bean);

	public int updateBinCapacityStatus(BinMaster bean);

	public String getavailableCapacity(String bin_num);

	public int updateBinMasterForAvailableCapacity(ShrinkBean bean);

	public String getBinForIndent(String denomination, String bundle);

	public int insertUpdatedIndent(IndentRequestBean bean);

	public int updateIndentBundle(MachineAllocationBean bean);

	public int insertProcessedForCustodian(ProcessBean bean);

	public int updatebinForProcess(ProcessBean bean);

	public String selectBinForORVBranchFromProcessCustodian(String denomination, String bundle);

	public int updateShrinkStatus(String id);

	public int updateIndentStatus(String id);

	public int updateProcessedStatus(String id);

	public int updateORVStatus(String id);

	public int checkBinIsAvailableInTransaction(int denomination);

	public String getPriorityBinFromtbl_bin_transaction(String denomination);

	public int updateIsAllocated(String bin);

	public int updateCurrenctBundle(String bin);

	public String getMaxCapacity(String denomination);

	public ArrayList<String> getBinForIndentRequestBinUpdation(String denomination);

	public int ifDsbIsProcessing(DSBBean bean);

}
