/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinCapacityDenomination;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CoinsSequence;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DSBAccountDetail;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.History;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.mysema.query.Tuple;

/**
 * @author root
 *
 */
public interface CashReceiptJpaDao {
	
	BinTransaction getBinTxnRecordForUpdateBox(BoxMaster boxmasterDb, BigInteger icmcId);
	
	public BinTransaction getBinTxnRecordForUpdateedit(BranchReceipt branchReceiptDb, BigInteger icmcId);
	
	public List<BinCapacityDenomination> getMaxBundleCapacity(int denomination, CurrencyType currencyType);

	public List<BinMaster> getPriorityBinListByType(BinMaster binMaster);

	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx);
	
	public List<BinTransaction> getBinTxnListByDenomForProcessed(BinTransaction binTx);

	public boolean updateBinMaster(BinMaster binMaster);

	public boolean insertInBinTxn(BinTransaction binTransaction);

	public boolean updateInBinTxn(BinTransaction binTransaction);

	public boolean createBranchReceipt(List<BranchReceipt> branchReceipt);

	public List<BranchReceipt> getBrachReceiptRecord(User user,Calendar sDate,Calendar eDate);

	public boolean createFreshFromRBI(List<FreshFromRBI> freshFromRBI);

	public List<FreshFromRBI> getFreshFromRBIRecord(User user,Calendar sDate,Calendar eDate);

	public boolean createDiversionIRV(List<DiversionIRV> diversionIRV);

	public List<DiversionIRV> getDiversionIRVRecord(User user,Calendar sDate,Calendar eDate);

	public Long createDSB(List<DSB> dsb);

	public List<DSB> getDSBRecord(User user,Calendar sDate,Calendar eDate);

	public boolean insertInIndent(Indent indent);

	public boolean createICMCReceipt(List<BankReceipt> icmcReceipt);

	public List<BankReceipt> getBankReceiptRecord(User user,Calendar sDate,Calendar eDate);

	public List<ICMC> getICMCName();

	public String getBranchBySolId(String solId);

	public List<BranchReceipt> getBrachFromBranchReceipt(BigInteger icmcId);
	
	public List<Tuple> getVoucherRecord(String SolId);
	
	public List<Tuple> getIRVReportRecord(BigInteger icmcId,Calendar sDate,Calendar eDate);
	
	public List<DSBAccountDetail> getDSBAccountDetail(BigInteger icmcId);
	
	public List<String> getAccountNumberForDSB(String vendorName,BigInteger icmcId);
	
	public CoinsSequence getSequence(BigInteger icmcId,int denomination);
	
	public boolean insertInCoinSequence(CoinsSequence coinSequence);
	
	public boolean insertCoinSequenceInBinTxn(BinTransaction binTxn);
	
	public List<Tuple> getDataFromIndentForFreshProcessing(BigInteger icmcId);
	
	public List<Indent> getIndentDataForBundleCalculation(BigInteger icmcId,int denomination);
	
	public boolean updateIndentAfterFresh(Indent indent);

	public List<Tuple> getIRVReportRecordsForDSb(BigInteger icmcId,	Calendar sDate, Calendar eDate);

	public List<Tuple> getIRVReportRecordForOtherBanks(BigInteger icmcId,Calendar sDate, Calendar eDate);
	
	public List<BoxMaster> getBoxFromBoxMaster(BoxMaster boxMaster);
	
	public boolean updateBoxMaster(BoxMaster boxMaster);
	
	public boolean saveBox(BoxMaster boxMaster);
	
	public List<BoxMaster> boxMasterDetails(BigInteger icmcId);

	public BoxMaster getBoxDetailsById(Long id);

	public boolean updateBoxDetails(BoxMaster boxMaster);

	public BranchReceipt getBranchReceiptRecordById(Long id, BigInteger icmcId);

	public BinTransaction getBinTxnRecordForUpdate(BranchReceipt branchReceiptDb, BigInteger icmcId);

	public boolean updateBranchReceipt(BranchReceipt branchReceipt);

	public DSB getDSBReceiptRecordById(Long id, BigInteger icmcId);

	public BinTransaction getBinTxnRecordForUpdatingDSB(DSB dsbReceiptDb, BigInteger icmcId);

	public boolean updateDSB(DSB dsb);
	public boolean updateIndent(Indent indent);



	public DiversionIRV getDiversionIRVRecordById(Long id, BigInteger icmcId);

	public BinTransaction getBinTxnRecordForUpdatingDiversionReceipt(DiversionIRV diversionIRV, BigInteger icmcId);

	public boolean updateDiversionIRV(DiversionIRV diversionIRV);

	public BoxMaster isValidBox(BigInteger icmcId, String boxName);
	
	public List<Tuple> getCashReceiptRecord(BigInteger icmcId,Calendar sDate,Calendar eDate);

	public List<MachineAllocation> getPendingBundleFromMachineAllocationForReturnBackToVault(BigInteger icmcId,
			Integer denomination, CashSource cashSource);

	public boolean updatePendingBundleInMachineAllocation(MachineAllocation machineAllocation);

	public Integer getDSBReceiptSequence(BigInteger icmcId, DSB dsb);

	public BankReceipt getBankReceiptRecordById(Long id, BigInteger icmcId);

	public FreshFromRBI getFreshFromRBIRecordById(Long id, BigInteger icmcId);
	
	public List<Tuple> getIBITForIRV(BigInteger icmcId,Calendar sDate,Calendar eDate);
	
	public String getLinkBranchSolID(long icmcId);
	
	public String getServicingICMC(String solId);
	
	//History Code
	public boolean branchHistory(List<History> history);

	BinTransaction getBinTxnRecordFoRBIFreshediitRBI(FreshFromRBI freshRBIReceipt, BigInteger icmcId);

	boolean updateFreshRBIReceipt(FreshFromRBI freshRBIReceiptDb);

	BinTransaction getBinTxnRecordForBankReceipteditBankReceipt(BankReceipt bankReceiptDb, BigInteger icmcId);

	boolean updateBankReceipt(BankReceipt otherBankReceiptdb);

	boolean updateIRV(DiversionIRV diversionIRV);

	boolean updateOtherBank(BankReceipt otherBankReceit);

	boolean saveDSB(List<DSB> dsb);
}
