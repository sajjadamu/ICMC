/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.jpa.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CRALog;
import com.chest.currency.entity.model.CashReleased;
import com.chest.currency.entity.model.CoinsSequence;
import com.chest.currency.entity.model.DiversionORV;
import com.chest.currency.entity.model.DiversionORVAllocation;
import com.chest.currency.entity.model.ForwardBundleForCRAPayment;
import com.chest.currency.entity.model.History;
import com.chest.currency.entity.model.ORV;
import com.chest.currency.entity.model.ORVAllocation;
import com.chest.currency.entity.model.OtherBank;
import com.chest.currency.entity.model.OtherBankAllocation;
import com.chest.currency.entity.model.ProcessBundleForCRAPayment;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.SoiledRemittance;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.mysema.query.Tuple;

public interface CashPaymentJpaDao {

	public List<BinTransaction> getPreparedInActiveSoiledBoxes(BigInteger icmcId);

	public String getSRNumberById(long Id);

	public Sas getSameDayFileName(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public void updateSASStatusForSASFile(BigInteger icmcId, int status);

	public List<Sas> getSASRecordFromSasfile(User user);

	public List<ProcessBundleForCRAPayment> getListCRAId(BigInteger icmcId);

	public List<ProcessBundleForCRAPayment> getProcessBundleCRARecord(long craId);

	public List<Tuple> getRecordORVVoucherById(long id, Calendar sDate, Calendar eDate);

	public List<CRA> getCRARequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<SoiledRemittance> getSoiledRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<OtherBank> getOtherBankPaymentRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public long updateSASForceHandoverStatus(BigInteger icmcId, long id);

	public List<Sas> getAcceptSolId(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getRequestAcceptORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<SASAllocation> getSasAllocationByBinNumber(SASAllocation binNumber);

	public boolean sasUpload(List<Sas> sasList, Sas sas);

	public List<Sas> getSASRecord(User user, Calendar sDate, Calendar eDate);

	public List<Sas> getSASRecordForceHandover(User user, Calendar sDate, Calendar eDate);

	public Sas getSASRecordById(BigInteger icmcId, Long id);

	public long updateSAS(Sas sas);

	public boolean updateSASStatusAccept(Sas sas);

	public boolean updateCRAAndCRAAllocation(CRA cra);

	public OtherBank getOtherBankRecordById(BigInteger icmcId, long id);

	public void updateEditOtherBankAndOtherBankAllocation(BigInteger icmcId, long id);

	public void updateInsertSoiledAndSoiledAllocation(BigInteger icmcId, long id);

	public boolean insertInSASAllocation(List<SASAllocation> eligibleSASRequestList);

	public Boolean updateBranchReceiptForPayment(User user, SASAllocation binNum);

	public void updateSasAllocationForCancelEditBranchPayment(BigInteger icmcId, long id);

	public List<SoiledRemittance> soiledRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public long updateSASStatus(Sas sas);

	public List<SASAllocation> getSASAllocationRecord(BigInteger icmcId);

	public List<Tuple> getSASAllocationRecordFromTuple(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<SASAllocation> getSasAllocationBySasId(long icmcId);

	public boolean saveSoiledRemittance(SoiledRemittance soiledRemittance);

	public SoiledRemittance getSoiledRemittanceById(BigInteger icmcId, Calendar sDate, Calendar eDate, long id);

	public boolean saveDiversionORV(DiversionORV diversionORV);

	public List<DiversionORV> getDiversionORV(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getRequestORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean saveORV(ORV orv);

	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId);

	public List<ORVAllocation> getORVForAccept(BigInteger icmcId);

	public List<DiversionORVAllocation> getDiversionORVForAccept(BigInteger icmcId);

	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx);

	public List<BinTransaction> getBinNumListForSas(SASAllocation sas, CurrencyType type);

	public List<BinTransaction> getBinNumListForSasForUnprocess(SASAllocation sas, CurrencyType type,
			BinCategoryType binType);

	public List<SASAllocation> getBinFromSAS(SASAllocation sas);

	public List<BinTransaction> getBinNumListForSoiled(SoiledRemittanceAllocation soiled, CurrencyType type);

	public List<BinTransaction> getBinNumListForDiversion(DiversionORVAllocation dorv, CurrencyType type);

	public List<DiversionORVAllocation> getBinFromDorvAllocation(DiversionORVAllocation dorv);

	public List<BinTransaction> getBinNumListForORV(ORVAllocation orv, CurrencyType type);

	public List<ORVAllocation> getBinFromOrvAllocation(ORVAllocation orv);

	public long updateSoiledStatus(SoiledRemittanceAllocation soiled);

	public void updateSoiledRemittanceStatus(SoiledRemittance soiledRemittanceTemp);

	public long updateDorvStatus(DiversionORVAllocation dorv);

	public long updateORVStatus(ORVAllocation orv);

	public long updateSASstatus(SASAllocation sas);

	public boolean saveCashReleased(CashReleased cashReleased);

	public BinTransaction getBinRecordForCashReleassed(BinTransaction txn);

	public boolean updateBinMaster(BinMaster binMaster);

	public boolean updateBinTransaction(BinTransaction binTransaction);

	public int deleteDataFromBinTxn(BinTransaction txnBin);

	public boolean updateBinTxn(BinTransaction binTransaction);

	public BinTransaction getCurrencyTypeForORV(ORVAllocation orv, BigInteger icmcId);

	public List<Sas> getORVReport(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<CITCRAVendor> getVendor();

	public List<String> getCustodianName(String vendor);

	public List<String> getVehicleNumber(String vendor);

	public List<Tuple> getRecordForSummary(BigInteger icmcId);

	public List<Sas> solIdForSASPayment(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> solIdForSASPaymentAccepted(BigInteger icmcId, Calendar sDate, Calendar eDate, Set<Long> pList);

	public Sas sasPaymentDetails(long id);

	public Sas sasRecordByID(long id);

	public boolean updateSASForSASRelease(Sas sas);

	public boolean createORVPayment(Sas sas);

	public boolean createSASAllocationForORVPayment(SASAllocation sasAllocation);

	public boolean createCRAPayment(CRA cra);

	public boolean createCRALogPayment(CRALog cralog);

	public List<BinTransaction> getBinNumListForCRA(CRAAllocation cra, CurrencyType type);

	public List<CRAAllocation> getBinFromCRA(CRAAllocation cra);

	public boolean insertInCRAAllocation(List<CRAAllocation> eligibleCRARequestList);

	public List<CRAAccountDetail> getVendorAndMSPName(BigInteger icmcId);

	public String getAccountNumberByMSPName(String mspName, String vendor, BigInteger icmcId);

	public boolean createOtherBankPayment(OtherBank otherBank);

	public List<BinTransaction> getBinNumListForOtherBank(OtherBankAllocation otherBank, CurrencyType type);

	public List<CRA> getCRARecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<OtherBank> getOtherBankPaymentRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getRecordORVVoucher(String solId, Calendar sDate, Calendar eDate, BigInteger icmcId);

	public List<Sas> getSolId(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getSasRecordById(BigInteger icmcId, Long[] sasId);

	public Sas getFileName(BigInteger icmcId);

	public boolean updateBinTransactionForEmpty(BinTransaction binTransaction);

	public List<CRAAllocation> getCRAForAccept(BigInteger icmcId);

	public long updateCRAStatus(CRAAllocation cra);

	public long updateCRAOtherStatus(CRA cra);

	public List<OtherBankAllocation> getOtherBankForAccept(BigInteger icmcId);

	public long updateOtherBankStatus(OtherBankAllocation otherBank);

	public BinTransaction getPendingBundleRequestForCRA(BinTransaction binTransaction);

	public boolean updateBinTransactionForCRA(BinTransaction binTransaction);

	public CRAAllocation getBinForCRA(CRAAllocation cra);

	public boolean updateBundleInCRAAllocation(CRAAllocation craAllocation);

	public List<BinTransaction> getBinForCRAPayment(BinTransaction binTxn);

	public boolean ForwardCRAPayment(ForwardBundleForCRAPayment forwardBundlePayment);

	public List<Tuple> craRequestSummary(long id);

	public boolean insertInCRAPayment(List<CRAAllocation> CRAPaymentRequestList);

	public List<CRA> getRecordFromCRA(BigInteger icmcId);

	public List<Tuple> getSoiledSummary(BigInteger icmcId);

	public List<BinTransaction> getBundleFromBinTxnToCompare(BigInteger icmcId, Integer denomination,
			CurrencyType currencyType);

	public boolean insertSoiledBoxInBinTx(BinTransaction binTx);

	public boolean updateBinTrasactionForCRA(List<BinTransaction> binTxn);

	CRA getCRAById(long id, BigInteger icmcId);

	public boolean updateCRAAllocationForCRA(List<CRAAllocation> cra);

	public boolean createSoiledAfterBoxCreation(SoiledRemittanceAllocation soiled);

	public CRAAllocation findCraAllocation(CRAAllocation craAllocation);

	public List<BinTransaction> getBoxListForSoiled(SoiledRemittanceAllocation soiledAllocation, CurrencyType type);

	public BinTransaction getBoxRecordForCashReleassed(BinTransaction binTxn);

	public List<CRAAllocation> craPaymentDetailForAccept(BigInteger icmcId);

	public boolean updateCRAAllocationStatus(CRAAllocation craAllocation);

	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId);

	public CRAAllocation getCRAAllocationDataById(long id, BigInteger icmcId);

	public int updateBinTxnForSoiledBox(BinTransaction binTxn);

	public List<CRAAllocation> craPaymentDetails(BigInteger icmcId, long id);

	public List<ProcessBundleForCRAPayment> forwardedCraPaymentDetails(BigInteger icmcId, long id);

	public List<CRA> solIdForCRAPayment(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<DiversionORV> diversionListForRbiOrderNo(BigInteger icmcId);

	public List<DiversionORVAllocation> dorvPaymentDetails(BigInteger icmcId, long id);

	public List<OtherBank> bankNameFromOtherBank(BigInteger icmcId);

	public List<ProcessBundleForCRAPayment> processBundleForCRAPayment(BigInteger icmcId);

	public List<OtherBankAllocation> otherBankPaymentDetails(BigInteger icmcId, long id);

	public long updateOtherBankForPayment(BigInteger icmcId, long id);

	public long updateProcessbundleForCRAPayment(BigInteger icmcId, long id);

	public long updateOtherBankAllocationForPayment(BigInteger icmcId, long id);

	public long updateCRAForPayment(BigInteger icmcId, long id);

	public long updateCRAAllocationForPayment(BigInteger icmcId, long id);

	public long updateDiversionForPayment(BigInteger icmcId, long id);

	public long updateDiversionAllocationForPayment(BigInteger icmcId, long id);

	public List<BinTransaction> getCoinsListForSas(SASAllocation sas);

	public long updateSoiledForPayment(BigInteger icmcId, long id);

	public long updateSoiledAllocationForPayment(BigInteger icmcId, long id);

	public CoinsSequence getCoinsSequenceForDeduction(BigInteger icmcId, int denomination);

	public BinTransaction getBinRecordForAcceptInVault(BinTransaction txn);

	public long updateOtherBankStatus(OtherBank otherBank);

	public long updateDorvStatus(DiversionORV dorv);

	public List<Tuple> getRecordCoinsForSummary(BigInteger icmcId);

	public long updateCoinsSequence(CoinsSequence coinsSequence);

	public List<SoiledRemittance> getRemittanceOrderNo(BigInteger icmcId);

	public List<SoiledRemittanceAllocation> soiledRemittancePaymentDetails(BigInteger icmcId, long id);

	public List<Tuple> getBranchOutRecordFromSAS(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getRecordFromSAS(long id);

	public long updateSASStatus(BigInteger icmcId, long id);

	public List<SASAllocation> getAllAcceptedFromSASAllocation(BigInteger icmcId);

	public List<SASAllocation> getAllTodayAcceptedFromSASAllocation(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public SASAllocation getRequestedFromSASAllocation(BigInteger icmcId, Calendar sDate, Calendar eDate,
			Long parentId);

	public String getICMCName(BigInteger icmcId);

	public Long cancelSAS(User user, Sas sas);

	public DiversionORV getDiversionRecordForCancellation(User user, Long id);

	public Long updateDorvForCancellation(User user, Long id);

	public void updateDorvAllocationForCancellation(User user, Long id);

	public OtherBank getOtherBankRecordForCancellation(User user, Long id);

	public Long updateOtherBankForCancellation(User user, Long id);

	public void updateOtherBankAllocationForCancellation(User user, Long id);

	public CRA getCRARecordForCancellation(User user, Long id);

	public Long updateCRAafterCancellation(User user, Long id);

	public void updateCRAAllocationAfterCancellation(User user, Long id);

	public List<Tuple> machineInputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination);

	public List<Tuple> machineOutputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination,
			CurrencyType type);

	public List<SoiledRemittanceAllocation> getAcceptedListForSoiledRemitAlloc(BigInteger icmcId);

	public List<BinTransaction> getPreparedSoiledBoxes(BigInteger icmcId);

	public long updateSasAllocationForCancelBranchPayment(BigInteger icmcId, long id);

	public long updateSasForCancelBranchPayment(BigInteger icmcId, long id);

	public BinTransaction getPendingBundleFromDB(BigInteger icmcId, BinTransaction binTxn);

	public long updateBinTransactionCancelBranchPayment(BigInteger icmcId, BinTransaction binTransaction);

	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public String getLinkBranchSolID(long icmcId);

	public String getServicingICMC(String solId);

	public List<Tuple> getBranchPaymentTotal(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getCraPaymentTotalProcessed(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getCraPaymentTotalReleased(BigInteger icmcId, Calendar sDate, Calendar eDate);

	// History Code
	public boolean history(List<History> history);

	public boolean saveDataInBinRegister(BinRegister binRegister);

	public String getSRNumberBySolId(String solId);

	public long removeBranchFromSAS(Sas sas);

	public List<SoiledRemittanceAllocation> TRReports(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public CRA getCRADetailById(long id);

	public CRA getLastCreateCRAPaymentList(long craId);

	public long getLastCreateCRAPaymentId();

	public List<SASAllocation> getAllAcceptedFromSASAllocation1(BigInteger icmcId);

	public List<Sas> getORVReport1(BigInteger icmcId, Calendar sDate, Calendar eDate, Long sasId);

	public List<SASAllocation> getDataToUpdateBinTransaction(BigInteger icmcId, Long parentId);

	public BinTransaction getDataFromBinTransactionForSasAllocationCancel(BigInteger icmcId, String binNumber,
			Integer Denomination);

	public boolean updateBinTransactionPendingBundleForCashPaymentCancel(BinTransaction binTransaction);

	public BinTransaction getDataFromBinTransactionForSoiledAllocationCancel(BigInteger icmcId, String box,
			Integer denomination);

	public long updateOrvStatus1(long id); /*
											 * //shahabuddin
											 */

	public long updateOrvAllocationStatus1(long id);

	public DiversionORV getDiversionORVById(Long id);

	public ProcessBundleForCRAPayment getCRAId(BigInteger icmcId);

	public List<CRA> valueFromCRA(BigInteger icmcId, long craId);

	public Boolean deleteEmptyBinFromBinTransaction(BigInteger icmcId, String binNumber);

	public boolean getShrinkBundleFromBrancheReceipt(SASAllocation sasAllocation, CashSource cashSource);

	public List<Tuple> getAllShrinkWrapBundleFromBranchReceipt(BigInteger icmcId);

	public List<BranchReceipt> getShrinkWrapBundleByDenomination(int denomination, BigInteger icmcId,
			BinCategoryType binCategoryType);

	public boolean updatebBranchReceiptForBranchPaymentCancel(BranchReceipt binTransaction);

	public BranchReceipt checkBinOrBoxFromBranchReceipt(BigInteger icmcId, int denomination, BigDecimal bundle,
			String binNumber);

	public List<Tuple> getSoiledSummary(BigInteger icmcId, CurrencyType currencyType);

	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId, Calendar sDate, Calendar eDate);

	List<SASAllocation> getSasAllocationByBinNumberBundle(SASAllocation sasAlo);

	List<Sas> sasForCashHandover(BigInteger icmcId, Calendar sDate, Calendar eDate, Set<Long> pList);

}