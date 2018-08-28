/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.util.List;

import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.BinTransaction;
import com.chest.currency.viewBean.DSBBean;
import com.chest.currency.viewBean.DirvBean;
import com.chest.currency.viewBean.FreshBean;
import com.chest.currency.viewBean.IndentRequestBean;
import com.chest.currency.viewBean.ShrinkBean;

public interface CashReceiptDao {

	// public boolean saveShrinkList(List<ShrinkBean> shrink);

	public boolean saveDirvList(List<DirvBean> dirv);

	public boolean saveRBIFresh(List<FreshBean> fresh);

	public boolean saveDSBList(List<DSBBean> dsb);

	public List<BinMaster> getPriorityBinListByDenomAndType(int denomination, String processType, int icmcId);

	public List<BinTransaction> getBinTxnListByDenom(int denomination, double receiveBundle);

	public boolean saveTxList(List<BinTransaction> txList);

	// public boolean saveTxListAndShrink(List<BinTransaction> txList,
	// List<ShrinkBean> shrinkBeanList);

	public boolean saveTxListAndDSB(List<BinTransaction> txList, List<DSBBean> dSBBeanList);

	public boolean saveTxListAndDiversion(List<BinTransaction> txList, List<DirvBean> dirvBeanList);

	public boolean saveTxListAndRBIFresh(List<BinTransaction> txList, List<FreshBean> freshBeanList);

	public boolean saveDSBForProcessing(DSBBean bean);

	public boolean saveIndentFromDSB(IndentRequestBean indentBean);

	public boolean saveTxListAndDSB(DSBBean dsbBean, IndentRequestBean indentBean);

	public List<ShrinkBean> getShrinkList(int icmcId);

	public List<DSBBean> getDSBRecord(int icmcId);

	public List<FreshBean> getFreshRecord(int icmcId);

	public List<DirvBean> getDirvRecord(int icmcId);

}
