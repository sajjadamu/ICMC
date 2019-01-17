/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.chest.currency.entity.model.AssignVaultCustodian;
import com.chest.currency.entity.model.AuditorIndent;
import com.chest.currency.entity.model.AuditorProcess;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.ChestMaster;
import com.chest.currency.entity.model.CustodianKeySet;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DefineKeySet;
import com.chest.currency.entity.model.Discrepancy;
import com.chest.currency.entity.model.DiscrepancyAllocation;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.ForwardBundleForCRAPayment;
import com.chest.currency.entity.model.FreshCurrency;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.History;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.MachineMaintenance;
import com.chest.currency.entity.model.Mutilated;
import com.chest.currency.entity.model.MutilatedIndent;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.ProcessBundleForCRAPayment;
import com.chest.currency.entity.model.RepeatabilityTestInput;
import com.chest.currency.entity.model.RepeatabilityTestOutput;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.SuspenseOpeningBalance;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.mysema.query.Tuple;

public interface ProcessingRoomService {

	public String getBinForIndentRequest(String denomination, String bundle);

	public boolean UpdateProcessStatusAndBinTxnStatus(int id, String type);

	public List<Indent> getIndentRequest(BigInteger icmcId);

	public List<Indent> viewBinDetail(int denomination, String bin, BigInteger icmcId);

	public Indent getUpdateIndentOtherBankRequest(BankReceipt otherBankReceiptdb, BigInteger icmcId);

	public Indent getUpdateIndentIVRRequest(DiversionIRV diversionIRV, BigInteger icmcId);

	public List<BinTransaction> getBinNumListForIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType);

	public List<Indent> getBinFromIndent(int denomination);

	public Indent getUpdateIndentRequest(DSB dsbdb, BigInteger icmcId);

	public Indent getIndentById(long id);

	public boolean updateIndentRequest(Indent indent);

	public boolean updateMutilatedIndentRequest(MutilatedIndent mutilatedIndent);

	// public List<Indent> getIndentRequestForMachineAllocation(BigInteger
	// icmcId, CashSource cashSource);

	// code for the searching data on basis of date

	public List<DiscrepancyAllocation> getDiscrepancyByDate(BigInteger icmcId, Date sDate, Date tDate,
			String normalOrSuspense);

	public List<Indent> getAggregatedIndentRequestForMachineAllocation(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate);

	public Indent getIndentDataById(long id, BigInteger icmcId);

	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId, Integer denomination);

	public boolean updateIndentStatus(Indent indent);

	public int deleteDataFromBinTxn(BinTransaction binTransaction);

	public boolean updateBinMaster(BinMaster binMaster);

	public boolean updateBinTxn(BinTransaction binTransaction);

	public boolean processIndentRequest(String bin, BigDecimal bundle, User user, Integer denomination);

	public boolean processMutilatedIndentRequest(String bin, BigDecimal bundle, User user, Long id);

	public boolean insertInMachineAllocation(MachineAllocation machineAllocation);

	// public Indent getBundleFromIndent(String bin,CashSource
	// cashSource,BigInteger icmcId);

	public boolean updateBundleInIndent(Indent indent);

	public boolean processMachineAllocation(MachineAllocation machine, Indent indent, User user);

	public List<Process> getProcessedDataList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<BinMaster> getPriorityBinListByType(BinMaster binMaster);

	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx);

	public boolean updateBinMasterForProcess(BinMaster binMaster);

	public boolean insertInBinTxn(BinTransaction binTransaction);

	public boolean updateInBinTxn(BinTransaction binTransaction);

	public boolean createProcess(List<Process> process);

	public boolean createProcessForAuditor(List<AuditorProcess> process);

	public List<Process> processRecordForMachine(Process process, User user);

	public String cancelProcessedDataFromProcessingRoom(long id, BigDecimal bundleFromUI, int denomination,
			String binNumber, CurrencyType type, long machineId, User user, Process process);

	public List<AuditorProcess> processRecordForAuditorIndent(AuditorProcess process, User user);

	public boolean updatePrcocessStatus(Process process);

	public List<Tuple> indentSummary(BigInteger icmcId, CashSource cashSource);

	public boolean UploadDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet);

	public boolean UploadChestSlip(List<ChestMaster> list, ChestMaster chestSlip);

	public List<String> getDefineKeySet(BigInteger icmcId);

	public DefineKeySet defineKeySetRecordForModify(Long id);

	public boolean updateDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet);

	public List<AssignVaultCustodian> getAssignVaultCustodian(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean saveAssignVaultCustodian(AssignVaultCustodian assignVaultCustodian,
			AssignVaultCustodian vaultCustodian);

	public AssignVaultCustodian assignVaultCustodianRecordForModify(Long id);

	public boolean updateAssignVaultCustodian(AssignVaultCustodian assignVaultCustodian);

	public List<MachineAllocation> getMachineAllocationRecordForProcessing(BigInteger icmcId, Calendar sDate,
			Calendar eDate);

	public List<RepeatabilityTestInput> getRepeatabilityTestInput(BigInteger icmcId);

	public boolean insertRepeatabilityTestInput(RepeatabilityTestInput repeatabilityTestInput);

	public RepeatabilityTestInput repeatabilityTestInputRecordForModify(Long id);

	public boolean updateRepeatabilityTestInput(RepeatabilityTestInput repeatabilityTestInput);

	public List<RepeatabilityTestOutput> getRepeatabilityTestOutput(BigInteger icmcId);

	public boolean insertRepeatabilityTestOutput(RepeatabilityTestOutput repeatabilityTestOutput);

	public RepeatabilityTestOutput repeatabilityTestOutputRecordForModify(Long id);

	public boolean updateRepeatabilityTestOutput(RepeatabilityTestOutput repeatabilityTestOutput);

	public boolean modifyDefineKeySet(DefineKeySet defineKeySet);

	public List<FreshCurrency> getFreshCurrency(BigInteger icmcId);

	public boolean insertFreshCurrency(FreshCurrency freshCurrency);

	public FreshCurrency freshCurrencyForModify(Long id);

	public boolean updateFreshCurrency(FreshCurrency freshCurrency);

	public boolean updateIndentStatusForCancel(Indent indent);

	public boolean saveDiscrepancy(Discrepancy discrepancy);

	public List<DiscrepancyAllocation> getDiscrepancy(BigInteger icmcId, String normalOrSuspense);

	public List<FreshFromRBI> getFreshFromRBIRecord(User user);

	public boolean updateBinTransaction(BinTransaction binTransaction);

	public List<Tuple> indentSummaryForFresh(BigInteger icmcId, CashSource cashSource);

	public List<Machine> getMachineNumber(BigInteger icmcId);

	public List<DefineKeySet> getKeyNumber(DefineKeySet defineKeySet);

	public List<DefineKeySet> getLocationOfLock(DefineKeySet defineKeySet);

	public List<Tuple> getKeySetDetail(String custodian, BigInteger icmcId);

	public List<BranchReceipt> getBinNumListForIndentFromBranchReceipt(Integer denomination, BigDecimal requestBundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType);

	public boolean saveCRAPaymentProcessRecord(ProcessBundleForCRAPayment processBundleForCRAPayment);

	public ProcessBundleForCRAPayment processRecordForCRAPayment(ProcessBundleForCRAPayment process, User user);

	public List<Tuple> getForwardBundleTotalForCRAPayment(BigInteger icmcId);

	public ForwardBundleForCRAPayment getBundleFromForwardCRA(ForwardBundleForCRAPayment forwardBundle);

	// public boolean updateBundleForwardBundleForCRA(ForwardBundleForCRAPayment
	// forwardBundleInCRAPayment);

	public boolean insertBranchIndentRequest(List<BranchReceipt> eligibleBranchIndentRequestList);

	public boolean insertIndentRequestAndUpdateBinTxAndBranchReceipt(List<Indent> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList, List<BranchReceipt> branchReceiptList);

	public boolean insertSasRequestAndUpdateBinTxAndBranchReceipt(List<SASAllocation> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList, List<BranchReceipt> branchReceiptList, Sas sasDetails);

	public boolean insertIndentRequestAndUpdateBinTxAndFreshFromRBI(List<Indent> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList, List<FreshFromRBI> freshList);

	public List<Tuple> indentSummaryForFreshFromBinTxn(BigInteger icmcId, CashSource cashSource);

	public List<BinTransaction> getBinNumListForFreshIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType, String rbiOrderNo);

	/*
	 * public List<Tuple> getDataFromCRAAllocationForProcessing(BigInteger
	 * icmcId);
	 */

	public List<CRAAllocation> getDataFromCRAAllocationForProcessing(BigInteger icmcId);

	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense);

	public CRAAllocation getPendingBundleById(CRAAllocation craAllocation);

	public boolean updateForwarPending(CRAAllocation craAllocation);

	public BigDecimal getTotalBundleInBin(int denomination, String bin, BigInteger icmcId);

	public List<Tuple> getMachineAllocationRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getProcessRecord(BigInteger icmcId, Calendar sDate, Calendar eDate, CurrencyType currencyType);

	public boolean processMachineAllocationForBranch(MachineAllocation machineAllocation, Indent indent);

	public List<Integer> getMachineNumberList(BigInteger icmcId);

	public List<Tuple> getDiscrepancyListForIOReport(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<MachineAllocation> getPendingBundleFromMachineAllocation(BigInteger icmcId, Integer denomination,
			String machineOrManual);

	public boolean updatePendingBundleInMachineAllocation(MachineAllocation machineAllocation);

	public String getMachineSerialNo(BigInteger icmcId, int machineNo);

	public List<Tuple> getPendingBundle(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getPendingBundleByMachine(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<MachineAllocation> getAggregatedBundleToBeReturnedToVault(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate);

	public String getUserName(String userId);

	public Process getRepritRecord(Long id);

	public List<Mutilated> getMitulatedFullValue(BigInteger icmcId);

	public List<Mutilated> processRecordForMutilated(Mutilated mutilated, User user);

	public boolean createMutilated(List<Mutilated> mutilatedList);

	public List<Tuple> indentSummaryForMutilated(BigInteger icmcId);

	public List<BinTransaction> getBinNumListForMutilatedIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			BinCategoryType binCategoryType);

	public boolean insertMitulatedIndentRequest(List<MutilatedIndent> eligibleIndentRequestList);

	public boolean insertMutilatedIndentRequestAndUpdateBinTxn(List<MutilatedIndent> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList);

	public List<MutilatedIndent> getMutilatedIndent(BigInteger icmcId);

	public String getICMCName(long icmcId);

	public Discrepancy getDescripancyDataForEdit(Long id, BigInteger icmcId);

	public DiscrepancyAllocation getDescripancyAllocationDataForEdit(Long id, BigInteger icmcId);

	public boolean updateDiscrepancy(DiscrepancyAllocation discrepancyAllocation);

	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense);

	public boolean addMachineMaintenance(MachineMaintenance machineMaintainance);

	public List<MachineMaintenance> viewMachineMaintenance(BigInteger icmcId);

	public MachineMaintenance getMachineMaintenanceById(long id);

	public boolean updateMachineMaintenanceStatus(MachineMaintenance machineMaintenance);

	public List<Tuple> getPendingBundleForAuditor(BigInteger icmcId);

	public List<AuditorIndent> getPendingBundleFromAuditorIndent(BigInteger icmcId, Integer denomination);

	public boolean updatePendingBundleInAuditorIndent(AuditorIndent auditorIndent);

	public BinTransaction getDataFromBinTransaction(String bin, BigInteger icmcId, Integer denomination,
			CurrencyType currencyType);

	public boolean updateProcessingRoomStatus(Process process);

	public boolean updatePendingForCancel(long id, BigDecimal pendingBundle);

	public boolean insertSuspenseOpeningBalance(SuspenseOpeningBalance suspenseOpeningBalance);

	public List<SuspenseOpeningBalance> getSuspenseOpeningBalance(BigInteger icmcId);

	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegister(BigInteger icmcId);

	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId);

	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId);

	public boolean branchHistory(List<History> historyList);

	public boolean updateCurrentVersionStatus(SuspenseOpeningBalance suspenseOpeningBalance);

	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDate(BigInteger icmcId, Calendar sDate,
			Calendar eDate);

	public boolean saveDataInBinRegister(BinRegister binRegister);

	public boolean deleteDiscrepancy(long id, BigInteger icmcId);

	public boolean insertFullValueMutilated(Mutilated mutilated);

	public List<MachineAllocation> getBundleByCashSource(BigInteger icmcId, String isMachineOrManual,
			Integer denomination, CashSource cashSource, Calendar sDate, Calendar eDate);

	boolean deleteDiscrepancywithooutallocation(long id, BigInteger icmcId);

	public List<CustodianKeySet> getAssignVaultCustodian(BigInteger icmcId);

	public AssignVaultCustodian getHandoveredChargUserId(BigInteger icmcId, String custodian);

	public AssignVaultCustodian getHandoveredChargByHandOverId(BigInteger icmcId, String userId);

	public User isValidUser(String username, BigInteger icmcId);

	public boolean InsertByDAteSuspeseOpeningBalance(long id, BigInteger icmcId,
			BigDecimal openingBalanceOfDenomination_10, BigDecimal openingBalanceOfDenomination_20,
			BigDecimal openingBalanceOfDenomination_50, BigDecimal openingBalanceOfDenomination_100,
			BigDecimal openingBalanceOfDenomination_200, BigDecimal openingBalanceOfDenomination_500,
			BigDecimal openingBalanceOfDenomination_2000);

	public boolean insertSuspenseOpeningBalanceLink(SuspenseOpeningBalance suspenseOpeningBalance);

	public boolean updateSuspenseBalanceFromLink(long id, BigDecimal replenishment_2000, BigDecimal replenishment_500,
			BigDecimal replenishment_200, BigDecimal replenishment_100, BigDecimal replenishment_50,
			BigDecimal replenishment_20, BigDecimal replenishment_10, BigDecimal depletion_2000,
			BigDecimal depletion_500, BigDecimal depletion_200, BigDecimal depletion_100, BigDecimal depletion_50,
			BigDecimal depletion_20, BigDecimal depletion_10, String srNumber);

	public boolean updateSuspenseBalance(long id, BigDecimal deposit_2000, BigDecimal deposit_500,
			BigDecimal deposit_200, BigDecimal deposit_100, BigDecimal deposit_50, BigDecimal deposit_20,
			BigDecimal deposit_10, BigDecimal withdrawal_2000, BigDecimal withdrawal_500, BigDecimal withdrawal_200,
			BigDecimal withdrawal_100, BigDecimal withdrawal_50, BigDecimal withdrawal_20, BigDecimal withdrawal_10);

	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDateByDesc(BigInteger icmcId);

	public List<Mutilated> getMutilatedValueDetails(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public boolean processMutilatedRequest(Long id);

	// public boolean getUpdateCashReceiveForIndentRequest(CashSource
	// cashSource,
	// String binNum, BigInteger icmcId);

	public boolean updateCashReceiveForIndentRequest(List<Indent> indenrRequest);

	Discrepancy getDiscrepancyForUploadingImage(User user, Calendar sDate, Calendar eDate);

	void uploadDiscrepancyImage(Discrepancy discrepancy);

	boolean auditorMachineAllocation(MachineAllocation machine, AuditorIndent auditorIndent, User user);

	boolean updateBundleInAuditorIndent(AuditorIndent indent);

	public List<MachineAllocation> getBundleFromMachineAllocation(BigInteger icmcId, Integer denomination);

	List<AuditorProcess> getAuditorProcessedData(BigInteger icmcId, Calendar sDate, Calendar eDate);

	AuditorProcess getRepritAuditorProcessRecord(Long id);

}
