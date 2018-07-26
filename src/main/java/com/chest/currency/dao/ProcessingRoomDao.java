/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.BinTransaction;
import com.chest.currency.viewBean.IndentRequestBean;
import com.chest.currency.viewBean.MachineAllocationBean;
import com.chest.currency.viewBean.ProcessBean;

public interface ProcessingRoomDao {

	public boolean insertIndentRequest(List<IndentRequestBean> beanList);

	public String getBinForIndentRequest(String denomination, String bundle);

	public List<IndentRequestBean> getIndentRequest();

	public boolean updateIndentRequest(IndentRequestBean bean);

	public IndentRequestBean getIndentRequestForEdit(int id);

	public boolean InsertMachineAllocationData(MachineAllocationBean machineBean);

	public boolean saveMachineAllocationAndUpdateIndent(MachineAllocationBean machineBean);

	public IndentRequestBean getIndentDataByID(int id, Connection connection) throws SQLException;

	public BinTransaction getBinFromTransaction(String bin, Connection connection) throws SQLException;

	public BinMaster getBinMasterForUpdation(String bin, Connection connection) throws SQLException;

	public int updateIndentStatus(IndentRequestBean indentBean, Connection connection) throws SQLException;

	public int updateBinTxn(String bin, Connection connection, double balanceBundle, BinTransaction txnBean)
			throws SQLException;

	public int deleteDataFromBinTxn(String bin, Connection connection) throws SQLException;

	public int updateBinMaster(String bin, Connection connection) throws SQLException;

	public boolean processIndentRequest(int id, String bin, double bundle);

	public List<BinTransaction> getBinNumListForIndent(int denomination, double bundle);

	public List<IndentRequestBean> getBinFromIndent(int denomination);

	public boolean binForIndentFromTxnAndIndent(List<IndentRequestBean> eligibleIndentRequestList);

	//public boolean saveProcessingRoom(List<ProcessBean> processBean);

	public boolean saveTxList(List<BinTransaction> txList);

	public boolean saveTxListAndProcessing(List<BinTransaction> txList, List<ProcessBean> processList);

	public List<BinMaster> getPriorityBinListByDenomAndType(int denomination, String processType);

	public List<BinTransaction> getBinTxnListByDenom(int denomination, double receiveBundle, String type);

	public int processEntry(ProcessBean processBean);

	public List<ProcessBean> getProcessData();

	public List<BinTransaction> indentSummary();

	public int getBundleFromIndent(int id);

	public boolean updateBundleInIndent(MachineAllocationBean machine);

	public List<IndentRequestBean> getRequestDataForMachineAllocation();

	public List<MachineAllocationBean> getMachineAllocationRecordForProcessing();

	public boolean updateProcessedStatus(int id);

	public boolean updateBinTxnStatus(String binType);

	public boolean UpdateProcessStatusAndBinTxnStatus(int id, String type);
}
