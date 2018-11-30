/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

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
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.viewBean.SASAllocationWrapper;
import com.mysema.query.Tuple;

public interface CashPaymentService {

	public List<BinTransaction> getPreparedInActiveSoiledBoxes(BigInteger icmcId);

	public List<ProcessBundleForCRAPayment> getListCRAId(BigInteger icmcId);

	public List<Tuple> getRecordORVVoucherById(long id, Calendar sDate, Calendar eDate);

	public List<CRA> getCRARequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<SoiledRemittance> getSoiledRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<OtherBank> getOtherBankPaymentRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public long updateSASForceHandoverStatus(BigInteger icmcId, long id);

	public List<Sas> getAcceptSolId(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getRequestAcceptORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getSASAllocationRecordFromTuple(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean sasUpload(List<Sas> sasList, Sas sas);

	public List<Sas> getSASRecord(User user, Calendar sDate, Calendar eDate);

	public List<Sas> getSASRecordForceHandover(User user, Calendar sDate, Calendar eDate);

	public List<Sas> getSASRecordFromSasfile(User user);

	Sas getSASRecordById(BigInteger icmcId, Long id);

	public long updateSAS(Sas sas);

	public boolean updateCRAAndCRAAllocation(CRA cra);

	public boolean processSASAllocation(SASAllocationWrapper sasAllocation, User user, Sas sas);

	public List<SoiledRemittance> soiledRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public long updateSASStatus(Sas sas);

	public boolean updateSASStatusAccept(Sas sas);

	public void updateEditOtherBankAndOtherBankAllocation(BigInteger icmcId, long id);

	public void updateInsertSoiledAndSoiledAllocation(BigInteger icmcId, long id);

	public List<SASAllocation> getSASAllocationRecord(BigInteger icmcId);

	public List<SASAllocation> getSasAllocationBySasId(long icmcId);

	public List<SASAllocation> getSasAllocationByBinNumber(SASAllocation icmcId);

	public SASAllocation updateSasIndent(SASAllocation sasAccept, User user);

	public boolean saveSoiledRemittance(SoiledRemittance soiledRemittance);

	public SoiledRemittance getSoiledRemittanceById(BigInteger icmcId, Calendar sDate, Calendar eDate, long id);

	public List<DiversionORV> getDiversionORV(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean saveDiversionORV(DiversionORV diversionORV);

	public List<Sas> getORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getRequestORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean saveORV(ORV orv);

	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId);

	public List<ORVAllocation> getORVForAccept(BigInteger icmcId);

	public List<DiversionORVAllocation> getDiversionORVForAccept(BigInteger icmcId);

	public boolean processSoiledRemmitanceAllocation(SoiledRemittance soiled, User user);

	public boolean processDiversionORVAllocation(DiversionORV dorv, User user);

	public boolean processORVAllocation(ORV orv, User user);

	public long updateSoiledStatus(SoiledRemittanceAllocation soiled, SoiledRemittance soiledRemittanceTemp);

	public long updateDorvStatus(DiversionORVAllocation dorv);

	public long updateOrvStatus(ORVAllocation orv);

	public long updateSASStatus(SASAllocation sas);

	public boolean saveCashReleased(CashReleased cashReleased);

	public BinTransaction getBinRecordForCashReleassed(BinTransaction txn);

	public boolean updateBinMaster(BinMaster binMaster);

	public int deleteDataFromBinTxn(BinTransaction txnBin);

	public boolean updateBinTxn(BinTransaction binTransaction);

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

	public boolean createSASAllocationForORVPayment(SASAllocation sasAllocation);

	public boolean processCRAAllocation(CRA cra, User user);

	public List<CRAAccountDetail> getVendorAndMSPName(BigInteger icmcId);

	public String getAccountNumberByMSPName(String mspName, String vendor, BigInteger icmcId);

	public boolean processOtherBankAllocation(OtherBank otherBank, User user);

	public List<CRA> getCRARecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<OtherBank> getOtherBankPaymentRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getRecordORVVoucher(String solId, Calendar sDate, Calendar eDate, BigInteger icmcId);

	public List<Sas> getSolId(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getSasRecordById(BigInteger icmcId, Long[] sasId);

	public Sas getFileName(BigInteger icmcId);

	public Sas getSameDayFileName(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean updateBinTransactionForEmpty(BinTransaction binTransaction);

	public List<CRAAllocation> getCRAForAccept(BigInteger icmcId);

	public long updateCRAStatus(CRAAllocation cra);

	public long updateOtherBankStatus(OtherBankAllocation otherBank);

	public List<OtherBankAllocation> getOtherBankForAccept(BigInteger icmcId);

	public OtherBank getOtherBankRecordById(BigInteger icmcId, long id);

	public boolean updateBinTransactionAndCRAAllocation(CRAAllocation craAllocation);

	public List<BinTransaction> getBinForCRAPayment(BinTransaction binTxn);

	public boolean ForwardCRAPayment(ForwardBundleForCRAPayment forwardBundlePayment);

	public List<Tuple> craRequestSummary(long id);

	public boolean processCRAPayment(List<CRAAllocation> craAllocation, User user);

	public List<CRA> getRecordFromCRA(BigInteger icmcId);

	public List<Tuple> getSoiledSummary(BigInteger icmcId);

	public List<BinTransaction> getBundleFromBinTxnToCompareForSoiled(BigInteger icmcId, Integer denomination,
			CurrencyType currencyType);

	public List<BinTransaction> getBinNumListForSoiled(SoiledRemittanceAllocation soiled, CurrencyType type);

	public SoiledRemittanceAllocation processSoiledBoxPreparation(
			List<SoiledRemittanceAllocation> eligibleIndentRequestList, SoiledRemittanceAllocation soiledData,
			User user);

	public boolean createSoiledAfterBoxCreation(SoiledRemittanceAllocation soiled);

	public BinTransaction getBoxRecordForCashReleassed(BinTransaction binTxn);

	public List<CRAAllocation> craPaymentDetailForAccept(BigInteger icmcId);

	public boolean updateCRAAllocationStatus(CRAAllocation craAllocation);

	public boolean processCRAPaymentRequest(long id, String bin, BigDecimal bundle, User user, long craId);

	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId);

	public CRAAllocation getCRAAllocationDataById(long id, BigInteger icmcId);

	public void updateBinTxnForSoiledBox(BinTransaction binTxn);

	public List<CRAAllocation> craPaymentDetails(BigInteger icmcId, long id);

	public List<ProcessBundleForCRAPayment> forwardedCraPaymentDetails(BigInteger icmcId, long id);

	public List<CRA> solIdForCRAPayment(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<DiversionORV> diversionListForRbiOrderNo(BigInteger icmcId);

	public List<DiversionORVAllocation> dorvPaymentDetails(BigInteger icmcId, long id);

	public List<OtherBank> bankNameFromOtherBank(BigInteger icmcId);

	public List<OtherBankAllocation> otherBankPaymentDetails(BigInteger icmcId, long id);

	public boolean updateOtherBankAndOtherBankAllocation(BigInteger icmcId, long id);

	public boolean updateCRAAndCRAAllocation(BigInteger icmcId, long id);

	public List<BinTransaction> getCoinsListForSas(SASAllocation sas);

	public boolean updateDiversionAndDiversionAllocation(BigInteger icmcId, long id);

	public CoinsSequence getCoinsSequenceForDeduction(BigInteger icmcId, int denomination);

	public BinTransaction getBinRecordForAcceptInVault(BinTransaction txn);

	public long updateOtherBankStatus(OtherBank otherBank);

	public long updateDorvStatus(DiversionORV dorv);

	public long updateCRAOtherStatus(CRA cra);

	public List<Tuple> getRecordCoinsForSummary(BigInteger icmcId);

	public long updateCoinsSequence(CoinsSequence coinsSequence);

	public List<SoiledRemittance> getRemittanceOrderNo(BigInteger icmcId);

	public List<SoiledRemittanceAllocation> soiledRemittancePaymentDetails(BigInteger icmcId, long id);

	public boolean updateSoiledAndSoiledAllocation(BigInteger icmcId, long id);

	public List<Tuple> getBranchOutRecordFromSAS(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Sas> getRecordFromSAS(long id);

	public long updateSASStatus(BigInteger icmcId, long id);

	public void updateSASStatusForSASFile(BigInteger icmcId, int status);

	public List<SASAllocation> getAllAcceptedFromSASAllocation(BigInteger icmcId);

	public String getICMCName(BigInteger icmcId);

	public Long cancelSAS(User user, List<Sas> sasList);

	public void processDiversionORVCancellation(User user, Long id);

	public void processOtherBankPaymentCancellation(User user, Long id);

	public void processCRAPaymentCancellation(User user, Long idFromUI);

	public List<Tuple> machineInputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination);

	public List<Tuple> machineOutputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination,
			CurrencyType type);

	public List<SoiledRemittanceAllocation> getAcceptedListForSoiledRemitAlloc(BigInteger icmcId);

	public List<BinTransaction> getPreparedSoiledBoxes(BigInteger icmcId);

	public List<ProcessBundleForCRAPayment> processBundleForCRAPayment(BigInteger icmcId);

	public long updateProcessbundleForCRAPayment(BigInteger icmcId, long id);

	public long updateSasAllocationForCancelBranchPayment(BigInteger icmcId, long id);

	public void updateSasAllocationForCancelEditBranchPayment(BigInteger icmcId, long id);

	public long updateSasForCancelBranchPayment(BigInteger icmcId, long id);

	public long updateBinTransactionCancelBranchPayment(BigInteger icmcId, BinTransaction binTransaction);

	public BinTransaction getPendingBundleFromDB(BigInteger icmcId, BinTransaction binTxn);

	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public String getLinkBranchSolID(long icmcId);

	public String getServicingICMC(String solId);

	public boolean history(List<History> historyList);

	public List<Tuple> getBranchPaymentTotal(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getCraPaymentTotalProcessed(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getCraPaymentTotalReleased(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean saveDataInBinRegister(BinRegister binRegister);

	public String getSRNumberBySolId(String solId);

	public String getSRNumberById(long solId);

	public long removeBranchFromSAS(Sas sas);

	public List<SoiledRemittanceAllocation> TRReports(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<SASAllocation> getAllTodayAcceptedFromSASAllocation(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public SASAllocation getRequestedFromSASAllocation(BigInteger icmcId, Calendar sDate, Calendar eDate,Long parentId);

	public CRA getCRADetailById(long id);

	public List<Sas> getORVReport1(BigInteger icmcId, Calendar sDate, Calendar eDate, Long sasId);

	public List<SASAllocation> getAllAcceptedFromSASAllocation1(BigInteger icmcId);

	public List<SASAllocation> getDataToUpdateBinTransaction(BigInteger icmcId, Long parentId);

	public BinTransaction getDataFromBinTransactionForSasAllocationCancel(BigInteger icmcId, String binNumber,
			Integer denomination);

	public BinTransaction getDataFromBinTransactionForSoiledAllocationCancel(BigInteger icmcId, String box,
			Integer denomination);

	public boolean updateBinTransactionPendingBundleForCashPaymentCancel(BinTransaction binTransaction);

	public long updateOrvStatus1(long id);

	public long updateOrvAllocationStatus1(long id);

	public DiversionORV getDiversionORVById(Long id);

	public ProcessBundleForCRAPayment getCRAId(BigInteger icmcId);

	public List<CRA> valueFromCRA(BigInteger icmcId, long craId);

	public void processForAcceptanceOutwardDiversion(DiversionORVAllocation diversionORV, Calendar now, User user);

	public void processForAcceptanceOtherBankPayment(OtherBankAllocation diversionORV, Calendar now, User user);

	public void processForAcceptanceSoiledIndent(SoiledRemittanceAllocation diversionORV, User user);

	public Boolean deleteEmptyBinFromBinTransaction(BigInteger icmcId, String binNumber);

	public List<Tuple> getAllShrinkWrapBundleFromBranchReceipt(BigInteger icmcId);

	public List<BranchReceipt> getShrinkWrapBundleByDenomination(int denomination, BigInteger icmcId,
			BinCategoryType binCategoryType);

	public long processForCancelBranchPayment(BigInteger icmcId, long id);

	public boolean updatebBranchReceiptForBranchPaymentCancel(BranchReceipt branchReceipt);

	public BranchReceipt checkBinOrBoxFromBranchReceipt(BigInteger icmcId, int denomination, BigDecimal bundle,
			String binNumber);

	public List<Tuple> getSoiledSummary(BigInteger icmcId, CurrencyType currencyType);

	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId, Calendar sDate, Calendar eDate);

	List<SASAllocation> getSasAllocationByBinNumberBundle(SASAllocation sasAlo);

}