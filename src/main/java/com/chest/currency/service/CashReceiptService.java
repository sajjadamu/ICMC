/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

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
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.YesNo;
import com.mysema.query.Tuple;

@Transactional
public interface CashReceiptService {

	public BinTransaction getBinTxnRecordForUpdateBox(BoxMaster boxmasterDb, BigInteger icmcId);

	public List<DiversionIRV> processForUpdatingIndentIVRReceipt(DiversionIRV diversionIRV,DiversionIRV diversionIRVDB, Indent indentdb, User user);
	
	public List<BinCapacityDenomination> getMaxBundleCapacity(int denomination, CurrencyType currencyType);

	public List<BinMaster> getPriorityBinListByType(BinMaster binMaster);

	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx);
	
	public List<BinTransaction> getBinTxnListByDenomForProcessed(BinTransaction binTx);

	public boolean updateBinMaster(BinMaster binMaster);

	public boolean createBranchReceipt(List<BranchReceipt> branchReceipt);

	public List<BranchReceipt> getBrachReceiptRecord(User user,Calendar sDate,Calendar eDate);

	public boolean insertInBinTxn(BinTransaction binTransaction);

	public boolean updateInBinTxn(BinTransaction binTransaction);

	public List<BranchReceipt> processBranchReceipt(BranchReceipt branchReceipt, User user);

	public boolean createFreshFromRBI(List<FreshFromRBI> freshFromRBI);

	

	public List<FreshFromRBI> processFreshFromRBI(FreshFromRBI fresh, User user,YesNo yesNo,BinCategoryType binCategoryType,CashType cashType);
	
	public boolean createDiversionIRV(List<DiversionIRV> diversionIRV);

	public List<DiversionIRV> getDiversionIRVRecord(User user,Calendar sDate,Calendar eDate);
	
	public List<DiversionIRV> processDiversionIRV(DiversionIRV dirv, User user);

	public boolean createDSB(List<DSB> dsb);
	
	public List<FreshFromRBI> getFreshFromRBIRecord(User user,Calendar sDate,Calendar eDate);

public BinTransaction getBinTxnRecordForRBIFreshedit(FreshFromRBI freshRBIReceipt, BigInteger icmcId);

public List<FreshFromRBI> processForUpdatingFreshRBI(BinTransaction binTxn, FreshFromRBI freshRBIReceipt,
			FreshFromRBI freshRBIReceiptDb, User user);

	public List<DSB> getDSBRecord(User user,Calendar sDate,Calendar eDate);
	
	public List<DSB> processDSB(DSB dsb, User user);
	
	public boolean insertInIndent(Indent indent);
	
	public List<BankReceipt> getBankReceiptRecord(User user,Calendar sDate,Calendar eDate);
	
	public boolean createICMCReceipt(List<BankReceipt> icmcReceipt);
	
	public List<BankReceipt> processBankReceipt(BankReceipt icmcReceipt, User user);
	
	public List<ICMC> getICMCName();
	
	public String getBranchNameBySolId(String solId);
	
	public List<BranchReceipt> getBrachFromBranchReceipt(BigInteger icmcId);

	public List<Tuple> getVoucherRecord(String solId);
	
	public List<Tuple> getIRVReportRecord(BigInteger icmcId,Calendar sDate,Calendar eDate);
	
	public List<DSBAccountDetail> getDSBAccountDetail(BigInteger icmcId);
	
	public List<String> getAccountNumberForDSB(String vendorName,BigInteger icmcId);
	
	public CoinsSequence getSequence(BigInteger icmcId,int denomination);
	
	public boolean insertInCoinSequence(CoinsSequence coinSequence);
	
	public boolean insertCoinSequenceInBinTxn(BinTransaction binTxn);
	
	List<BinTransaction> getBinTxnListByDenomAfterCountingFresh(FreshFromRBI fresh, User user);
	
	public List<FreshFromRBI> processFreshFromRBIAfterCounting(FreshFromRBI freshfromRBI, User user);

	public List<Tuple> getDataFromIndentForFreshProcessing(BigInteger icmcId);
	
	public List<Indent> getIndentDataForBundleCalculation(BigInteger icmcId, int denomination);
	
	public boolean updateIndentAfterFresh(Indent indent);

	public List<Tuple> getIRVReportRecordsForDSb(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getIRVReportRecordForOtherBanks(BigInteger icmcId,	Calendar sDate, Calendar eDate);
	
	public List<BoxMaster> getBoxFromBoxMaster(BoxMaster boxMaster);
	
	public boolean saveBox(BoxMaster boxMaster);
	
	public List<BoxMaster> boxMasterDetails(BigInteger icmcId);
	
	public boolean updateBoxMaster(BoxMaster boxMaster);

	public BoxMaster getBoxDetailsById(Long id);

	public boolean updateBoxDetails(BoxMaster boxMaster);

	public BranchReceipt getBranchReceiptRecordById(Long id, BigInteger icmcId);

	public BinTransaction getBinTxnRecordForUpdate(BranchReceipt branchReceiptDb, BigInteger icmcId);

	public List<BranchReceipt> processForUpdatingShrinkEntry(BinTransaction binTxn, BranchReceipt branchReceipt,
			BranchReceipt branchReceiptDb, User user);

	public com.chest.currency.entity.model.DSB getDSBReceiptRecordById(Long id, BigInteger icmcId);

	public BinTransaction getBinTxnRecordForUpdatingDSB(DSB dsbReceiptDb, BigInteger icmcId);
	
	public List<DSB> processDSBforEdit(DSB dsb, User user);
	
	public boolean processForUpdatingDSBReceipt1(DSB dsbdb, User user);

	/*public boolean processForUpdatingDSBReceipt(BinTransaction binTxn, DSB dsb, User user);*/
	

List<DSB> processForUpdatingIndentDSBReceipt(BinTransaction binTxn, DSB dsb,DSB dsbdb, Indent indentdb, User user);

	
	public DiversionIRV getDiversionIRVRecordById(Long id, BigInteger icmcId);
	



	public BinTransaction getBinTxnRecordForUpdatingDiversionReceipt(DiversionIRV diversionIRV, BigInteger icmcId);

	public boolean processForUpdatingDiversionReceipt(BinTransaction binTxn, DiversionIRV diversionIRV, User user);

	public BoxMaster isValidBox(BigInteger icmcId, String boxName);
	
	public List<Tuple> getCashReceiptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Integer getDSBReceiptSequence(BigInteger icmcId, DSB dsb);

	public BankReceipt getBankReceiptRecordById(Long id, BigInteger icmcId);

	public FreshFromRBI getFreshFromRBIRecordById(Long id, BigInteger icmcId);

	public List<Tuple> getIBITForIRV(BigInteger icmcId,Calendar sDate,Calendar eDate);
	
	public String getLinkBranchSolID(long icmcId);
	
	public String getServicingICMC(String solId);
	
	//History Code
	public boolean branchHistory(List<History> history);
	
	public BinTransaction getBinTxnRecordForUpdateedit(BranchReceipt branchReceiptDb, BigInteger icmcId);
	
	public BinTransaction getBinTxnRecordForBankReceiptedit(BankReceipt bankReceiptDb, BigInteger icmcId);

	public List<BankReceipt> processForUpdatingBankReceipt(BinTransaction binTxn, BankReceipt otherBankReceipt,
				BankReceipt otherBankReceiptDb, User user);


	List<DSB> processForUpdatingDSBReceipt(BinTransaction binTxn, DSB dsb, DSB dsbdb, User user);

	List<BankReceipt> processForUpdatingIndentOtherBankReceipt(BankReceipt otherBankReceipt,
			BankReceipt otherBankReceiptdb, Indent indentdb, User user);

}
