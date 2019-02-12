package com.chest.currency.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.chest.currency.entity.model.AuditorIndent;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BinTransactionBOD;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CashTransfer;
import com.chest.currency.entity.model.ChestMaster;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DateRange;
import com.chest.currency.entity.model.Discrepancy;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.DiversionORVAllocation;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.History;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.Mutilated;
import com.chest.currency.entity.model.OtherBankAllocation;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.RegionSummary;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.Summary;
import com.chest.currency.entity.model.TrainingRegister;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.mysema.query.Tuple;

public interface BinDashboardService {

	public boolean checkAvlBundleByDenoCategory(BigInteger icmcId, BinTransaction binTransaction);

	public List<Tuple> getWithdrawalForProcessCRA(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<BinMaster> getBinNumAndColorCode(BigInteger icmcId);

	public List<BinTransaction> getAvailableCapacity(String bin, BigInteger icmcId);

	public List<Tuple> getRecordForSummary(BigInteger icmcId);

	public List<ICMC> getICMCName();

	public List<BinTransaction> searchBins(CurrencyType binType, int denomination, BigInteger icmcId);

	public List<BinTransaction> getAllNonEmptyBins(BigInteger icmcId);

	public List<BinTransaction> getAllNonEmptyBins(BigInteger icmcId, CurrencyType binType);

	public List<BinTransaction> searchBins(int denomination, BigInteger icmcId);

	public List<BinMaster> viewBinMaster(BigInteger icmcId);

	public boolean insertDataInBinMaster(BinMaster binMaster);

	public List<BinTransaction> getRecordForFIFO(BigInteger icmcId);

	public List<Process> getProcessListAtm(BigInteger icmcId);

	public List<Process> getProcessListIssuable(BigInteger icmcId);

	public List<Process> getProcessListSoiled(BigInteger icmcId);

	public List<MachineAllocation> getMachineAllocationForMachineWiseStatus(BigInteger icmcId);

	public boolean UploadBinMaster(List<BinMaster> list, BinMaster binMaster);

	public List<BinTransaction> recordForDailyBinRecon(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public float getReceiveBundleForDailyReconBin(BinTransaction binTransaction);

	public List<Summary> getZoneWiseSummaryList();

	public List<Tuple> getRecordCoinsForSummary(BigInteger icmcId);

	public BinMaster isValidBin(BigInteger icmcId, String binNumber);

	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYes(BigInteger icmcId);

	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBox(BigInteger icmcId);

	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBin(BigInteger icmcId);

	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBag(BigInteger icmcId);

	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedNo(BigInteger icmcId);

	public List<Tuple> getOpeningBalanceForIO2Report(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType);

	public List<Tuple> getCoinsOpeningBalanceForIO2Report(BigInteger icmcId, CashType cashType);

	public List<Tuple> getSoiledBalanceForEOD(BigInteger icmcId, CashType cashType);

	public List<Tuple> getRemittanceReceivedForFresh(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean insertInBinTxnBOD(BinTransactionBOD binTransactionBod);

	public ChestMaster getLastChestSlipNumber(BigInteger icmcId);

	public boolean insertChestMaster(ChestMaster cMaster);

	public List<BinTransactionBOD> getDataFromBinTransactionBOD(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType, DateRange dateRange);

	public List<Tuple> getRemittanceSentForSoiled(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getDepositForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getDepositForDSB(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getDepositForDiversion(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getDepositForOtherBank(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getDepositForIndent(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getWithdrawalForDiversion(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getWithdrawalForCRA(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getWithdrawalForOtherBank(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getWithdrawalForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getSoiledNotes(BigInteger icmcId);

	public List<Tuple> getAdditionalInfoFreshNotes(BigInteger icmcId);

	public List<Tuple> getMachineAllocationSummary(BigInteger icmcId);

	public List<Tuple> getCurrentMachineAllocationSummary(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<RegionSummary> getRegionWiseSummaryList(String region);

	public List<Tuple> getGrandSummary();

	public List<Tuple> getZoneWiseGrandSummary();

	public List<Tuple> getFlatZoneSummaryList();

	public boolean updateCurrentVersionStatus(BinTransactionBOD binTransactionBod);

	public BigDecimal getTotalICMCBalance(BigInteger icmcId);

	public List<Tuple> getCoinRemittanceReceivedForFresh(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getCoinsWithdrawalForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<BinTransactionBOD> processIO2(BigInteger icmcId, Calendar sDate, Calendar eDate, DateRange dateRange);

	public ICMC getICMCObj(BigInteger icmcId);

	public Map<String, BranchReceipt> getBranchDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, DSB> getDSBDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, BankReceipt> getBankDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, FreshFromRBI> getFreshDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, DiversionIRV> getDirvDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<CRA> getCRAPaymentList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<BinTransaction> getBinWiseSummary(BigInteger icmcId);

	public List<Discrepancy> getDiscrepancyList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, Discrepancy> getFCRMList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, Mutilated> getMutilatedDataForDN2(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<BinTransactionBOD> processTE2(BigInteger icmcId, Calendar sDate, Calendar eDate, DateRange dateRange);

	public List<Tuple> getRemittanceSentForAllSoiled(BigInteger icmcId);

	public List<Tuple> getTotalNotesForDiscrepancy(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Discrepancy> getDiscrepancyList(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense);

	public List<Tuple> getTotalNotesForDiscrepancy(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense);

	public List<String> getBinOrBoxFromBinTransactionForCashTransfer(BigInteger icmcId,
			BinCategoryType binCategoryType);

	public List<String> getBinFromBinMasterForCashTransfer(BigInteger icmcId, CurrencyType binType);

	public List<String> getBoxFromBoxMasterForCashTransfer(BigInteger icmcId, int denomination,
			CurrencyType currencyType,BigDecimal bundle);

	public BinTransaction checkBinOrBox(BigInteger icmcId, String binNumber);

	public BinTransaction binDetailsByBinNumber(BigInteger icmcId, String binNumber);

	public boolean cashTransferInBinTxn(BinTransaction binTransaction);

	public boolean saveCashTransfer(CashTransfer cashTransfer);

	public long updateBinTxnAfterCashTransfer(BigInteger icmcId, String binNumber);

	public boolean saveAuditorIndentRequest(AuditorIndent auditorIndent);

	public BinTransaction getDataFromBinTrxnForAuditor(BinTransaction binTxn);

	public BinMaster getBinNumberById(Long id);

	public BinMaster getIsAllocatedValue(BigInteger icmcId, String binNumber);

	public boolean updateBinMaster(BigInteger icmcId, String BinNumber, int value);

	public boolean updateBoxMaster(BigInteger icmcId, String boxNumber, int value);

	public List<BinMaster> getDisableBin(BigInteger icmcId);

	public boolean updateDisabledBinStatus(BigInteger icmcId, String BinNumber);

	public BinTransaction getBinNumListForAuditorIndent(AuditorIndent auditorIndent, CurrencyType type);

	public boolean updateBinTxn(BigInteger icmcId, BigDecimal pendingBundle);

	public List<AuditorIndent> viewAuditorIndent(BigInteger icmcId);

	public List<AuditorIndent> viewAuditorIndentList(BigInteger icmcId);

	List<AuditorIndent> auditorIndentForMachineAllocation(BigInteger icmcId);

	public boolean updateAuditorIndentStatus(AuditorIndent auditorIndent);

	public BinTransaction getBinRecordForAcceptInVault(BinTransaction txn);

	public boolean updateBinTxn(BinTransaction binTransaction);

	public boolean updateBinMaster(BinMaster binMaster);

	public List<Sas> coinsRegister(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean saveTrainingRegisterData(TrainingRegister trainingRegsiter);

	public List<TrainingRegister> getTrainingRegisterData(BigInteger icmcId);

	public TrainingRegister getTrainingDataBYId(Long id);

	public void deleteTrainingRegisterById(Long id);

	public boolean updateTrainingRegsiter(TrainingRegister trainingRegsiter);

	public List<TrainingRegister> getTrainingRegisterReport(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public BoxMaster getBoxCapacity(BigInteger icmcId, String boxName);

	public BranchReceipt getBranchReceiptDetailsForCashTransferQR(BigInteger icmcId, String binNumber,
			int denomination);

	public DSB getDSBDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination);

	public DiversionIRV getDiversionIRVDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination);

	public BankReceipt getBankReceiptDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination);

	public List<Tuple> getCRAAllocationData(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, CRAAllocation> getCRAForCashBookWithDrawal(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public Map<String, DiversionORVAllocation> getoutwardDiversionForCashBookWithDrawal(BigInteger icmcId,
			Calendar sDate, Calendar eDate);

	public Map<String, OtherBankAllocation> getOtherBankAllocationForCashBookWithDrawal(BigInteger icmcId,
			Calendar sDate, Calendar eDate);

	public Map<String, SoiledRemittanceAllocation> getSoiledAllocationForCashBookWithDrawal(BigInteger icmcId,
			Calendar sDate, Calendar eDate);

	public CRA getBranchVendorAndMSPFromCRA(BigInteger icmcId, long id);

	public String getBranchFromDiversion(BigInteger icmcId, long id);

	public String getBranchFromOtherBank(BigInteger icmcId, long id);

	public List<Tuple> getDataFromBranchForCashMovementRegister(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType);

	public List<Tuple> getWithdrawalForCRAForCashMovement(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType);

	public List<BinTransactionBOD> cashMovementRegister(BigInteger icmcId, Calendar sDate, Calendar eDate,
			DateRange dateRange);

	public List<Tuple> getDepositForBranchForCashMovement(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getDepositFromHistory(BigInteger icmcId, String binNumber, Calendar sDate, Calendar eDate);

	public Calendar getInsertTimeFromCRA(BigInteger icmcId, String branch, Calendar sDate, Calendar eDate);

	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public String getLinkBranchSolID(long icmcId);

	public String getServicingICMC(String solId);

	public List<Tuple> getAllReceiptDataForBinRegister(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<History> getBundleFromHistory(BigInteger icmcId, String binNumber, Integer denomination);

	public boolean updateBundle(BigInteger icmcId, String binNumber, Integer Denomination, BigDecimal withdrawalBundle,
			long id);

	public boolean updateStatus(BigInteger icmcId, long id);

	public boolean insertDataInBinRegister(BinRegister binRegister);

	public History getWithdrawalbundle(BigInteger icmcId, String binNumber, Integer denomination);

	public List<History> saveDataInBinRegister(Integer denomination, String binNumber, BigDecimal bundleFromUI,
			BigInteger icmcId);

	public List<Tuple> DN2Report(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<String> getBinFromBinTransaction(BigInteger icmcId, int denomination, CurrencyType currencyType);

	public List<Tuple> getIBITForIO2(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public BigInteger getChestSlipNumber(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<String> getBinFroPartialTransfer(BigInteger icmcId, Integer denomination, CurrencyType currencyType,
			BigDecimal bundle, BinCategoryType binCategoryType, String bin);

	public void deleteForMigration(BigInteger icmcId);

	public List<Tuple> getOpeningBalanceForIO2ReportFromIndent(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType);

	public BinTransactionBOD checkEODHappenOrNot(BigInteger icmcId, CashType cashType, Calendar sDate, Calendar eDate);

	public boolean insertBranchReceiptAftercashTransfer(BranchReceipt branchReceipt);

	public long updateBranchReceiptAfterCashTransfer(BigInteger icmcId, String binNumber);

	public BinTransactionBOD getDataFromBinTransactionBOD(BigInteger icmcId);

	public List<Tuple> getWithdrawalFromBranch(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType);

	public List<Tuple> getDepositFromBranch(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType);

	public List<Tuple> getProcessFromProcessingOutPut(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType);

	public List<Indent> getIndentCash(BigInteger IcmcId, Calendar sDate, Calendar eDate);

	public List<SASAllocation> getsasAllocation(BigInteger IcmcId, Calendar sDate, Calendar eDate);

	public List<BranchReceipt> getBranchReceiptValue(BigInteger IcmcId, Calendar sDate, Calendar eDate);

	public List<DiversionIRV> getDiversionIRV(BigInteger IcmcId, Calendar sDate, Calendar eDate);

	List<Tuple> getProcessFromProcessingOutPut(BigInteger icmcId, Calendar sDate, Calendar eDate);

	List<Tuple> getProcessBundleProcessingOutPut(BigInteger icmcId, Calendar sDate, Calendar eDate);

	// void saveAudit(Audit audit);

	public boolean processForPartialCashTransfer(User user, String formBinOrBox, String toBinOrBox, BigDecimal bundle,
			CashTransfer cashTransfer, BinTransaction binTxn);

}
