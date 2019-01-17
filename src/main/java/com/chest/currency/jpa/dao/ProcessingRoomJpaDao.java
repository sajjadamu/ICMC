package com.chest.currency.jpa.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
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
import com.chest.currency.entity.model.SuspenseOpeningBalance;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.mysema.query.Tuple;

public interface ProcessingRoomJpaDao {

	public List<Indent> viewIndentRequest(BigInteger icmcId);

	public List<Indent> viewBinDetail(int denomination, String bin, BigInteger icmcId);

	public List<BinTransaction> getBinNumListForIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType);

	public List<BinTransaction> getBinNumListForFreshIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType, String rbiOrderNo);

	public List<Indent> getBinFromIndent(int denomination);

	public boolean insertIndentRequest(List<Indent> eligibleIndentRequestList);

	public Indent getIndentById(long id);

	public AssignVaultCustodian getHandoveredChargUserId(BigInteger icmcId, String cudtodian);

	public AssignVaultCustodian getHandoveredChargByHandOverId(BigInteger icmcId, String userId);

	// code for the searching data on the basis of date

	List<DiscrepancyAllocation> getDiscrepancyByDate(BigInteger icmcId, java.util.Date sDate, java.util.Date tDate,
			String normalOrSuspense);

	public MachineMaintenance getMachineMaintenanceById(long id);

	public boolean updateIndentRequest(Indent indent);

	public boolean updateMutilatedIndentRequest(MutilatedIndent indent);

	// public List<Indent> getIndentRequestForMachineAllocation(BigInteger
	// icmcId, CashSource cashSource);

	public List<Indent> getAggregatedIndentRequestForMachineAllocation(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate);

	public Indent getIndentDataById(long id, BigInteger icmcId);

	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId, Integer denomination);

	public boolean updateIndentStatus(Indent indent);

	public int deleteDataFromBinTxn(BinTransaction tx);

	public boolean updateBinMaster(BinMaster binMaster);

	public boolean updateBinTxn(BinTransaction binTransaction);

	public boolean insertInMachineAllocation(MachineAllocation machineAllocation);

	// public Indent getBundleFromIndent(String bin,CashSource
	// cashSource,BigInteger icmcId);

	public boolean updateBundleInIndent(Indent indent);

	public List<Process> getProcessedDataList(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<BinMaster> getPriorityBinListByType(BinMaster binMaster);

	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx);

	public boolean updateBinMasterForProcess(BinMaster binMaster);

	public boolean insertInBinTxn(BinTransaction binTransaction);

	public boolean updateInBinTxn(BinTransaction binTransaction);

	public boolean createProcess(List<Process> process);

	public boolean createProcessForAuditor(List<AuditorProcess> process);

	public boolean updateProcessStatus(Process process);

	public List<Tuple> indentSummary(BigInteger icmcId, CashSource cashType);

	public boolean UploadDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet);

	public boolean UploadChestSlip(List<ChestMaster> list, ChestMaster chestSlip);

	public List<AssignVaultCustodian> getListAssignVaultCustodian(BigInteger icmcId, Calendar sDate, Calendar eDate);

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

	public List<String> getDefineKeySet(BigInteger icmcId);

	public boolean insertFreshCurrency(FreshCurrency freshCurrency);

	public FreshCurrency freshCurrencyForModify(Long id);

	public boolean updateFreshCurrency(FreshCurrency freshCurrency);

	public boolean updateIndentStatusForCancel(Indent indent);

	public boolean saveDiscrepancy(Discrepancy discrepancy);

	public boolean updateIndent(Indent indent);

	public Indent viewUpdateIndentRequest(DSB dsbdb, BigInteger icmcId);

	public List<DiscrepancyAllocation> getDiscrepancy(BigInteger icmcId, String normalOrSuspense);

	public List<FreshFromRBI> getFreshFromRBIRecord(User user);

	public boolean updateBinTransaction(BinTransaction binTransaction);

	public List<Tuple> indentSummaryForFresh(BigInteger icmcId, CashSource cashSource);

	public List<Machine> getMachineNumber(BigInteger icmcId);

	public List<Tuple> getKeySetDetail(String custodian, BigInteger icmcId);

	public List<BranchReceipt> getBinNumListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType);

	public List<BranchReceipt> getRetunBinListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType);

	public List<BranchReceipt> getBranchUploadBinListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType);

	public List<BranchReceipt> getInsertBinListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType);

	public boolean updateBranchReceipt(BranchReceipt br);

	public boolean updateBranchReceiptForSas(BranchReceipt br);

	public boolean saveCRAPaymentProcessRecord(ProcessBundleForCRAPayment processBundleForCRAPayment);

	public List<Tuple> getForwardBundleTotalForCRAPayment(BigInteger icmcId);

	public ForwardBundleForCRAPayment getBundleFromForwardCRA(ForwardBundleForCRAPayment forwardBundle);

	public boolean updateBundleForwardBundleForCRA(ForwardBundleForCRAPayment forwardBundleInCRAPayment);

	public boolean insertBranchIndentRequest(List<BranchReceipt> eligibleBranchIndentRequestList);

	public List<Tuple> indentSummaryForFreshFromBinTxn(BigInteger icmcId, CashSource cashSource);

	public boolean updateFreshFromRBI(FreshFromRBI br);

	/*
	 * public List<Tuple> getDataFromCRAAllocationForProcessing(BigInteger
	 * icmcId);
	 */

	public List<CRAAllocation> getDataFromCRAAllocationForProcessing(BigInteger icmcId);

	public CRAAllocation getPendingBundleById(CRAAllocation craAllocation);

	public boolean updateForwarPending(CRAAllocation craAllocation);

	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense);

	public List<Indent> getIndentListForMachineAllocation(BigInteger icmcId, Integer denomination,
			CashSource cashSource);

	public List<AuditorIndent> getAuditorIndentListForMachineAllocation(BigInteger icmcId, Integer denomination);

	public BigDecimal getTotalBundleInBin(int denomination, String bin, BigInteger icmcId);

	public List<Tuple> getMachineAllocationRecord(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getProcessRecord(BigInteger icmcId, Calendar sDate, Calendar eDate, CurrencyType currencyType);

	public List<Integer> getMachineNumberList(BigInteger icmcId);

	public List<Tuple> getDiscrepancyListForIOReport(BigInteger icmcId, Calendar sDate, Calendar eDate);

	List<MachineAllocation> getPendingBundleFromMachineAllocation(BigInteger icmcId, Integer denomination,
			String machineOrManual);

	public boolean updatePendingBundleInMachineAllocation(MachineAllocation machineAllocation);

	public String getMachineSerialNo(BigInteger icmcId, int machineNo);

	public List<Tuple> getPendingBundle(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<BinTransaction> getBinTxnList(Process process, User user);

	public List<MachineAllocation> getAggregatedBundleToBeReturnedToVault(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate);

	public String getUserName(String userId);

	public Process getRepritRecord(Long id);

	public List<Mutilated> getMitulatedFullValue(BigInteger icmcId);

	public boolean createMutilated(List<Mutilated> mutilatedList);

	public List<Tuple> indentSummaryForMutilated(BigInteger icmcId);

	public List<BinTransaction> getBinNumListForMutilatedIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			BinCategoryType binCategoryType);

	public boolean insertMitulatedIndentRequest(List<MutilatedIndent> eligibleIndentRequestList);

	public List<MutilatedIndent> getMutilatedIndent(BigInteger icmcId);

	public String getICMCName(long icmcId);

	public Discrepancy getDescripancyDataForEdit(Long id, BigInteger icmcId);

	public DiscrepancyAllocation getDescripancyAllocationDataForEdit(Long id, BigInteger icmcId);

	public DiscrepancyAllocation getDescripancyAllocationById(Long id);

	public boolean updateDiscrepancy(DiscrepancyAllocation discrepancyAllocation);

	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense);

	public boolean addMachineMaintenance(MachineMaintenance machineMaintainance);

	public List<MachineMaintenance> viewMachineMaintenance(BigInteger icmcId);

	public boolean updateMachineMaintenanceStatus(MachineMaintenance machineMaintenance);

	public List<Tuple> getPendingBundleForAuditor(BigInteger icmcId);

	List<AuditorIndent> getPendingBundleFromAuditorIndent(BigInteger icmcId, Integer denomination);

	public boolean updatePendingBundleInAuditorIndent(AuditorIndent auditorIndent);

	public BinTransaction getDataFromBinTransaction(String bin, BigInteger icmcId, Integer denomination,
			CurrencyType currencyType);

	public boolean updateProcessingRoomStatus(Process process);

	public boolean updatePendingForCancel(long id, BigDecimal pendingBundle);

	public boolean insertSuspenseOpeningBalance(SuspenseOpeningBalance suspenseOpeningBalance);

	public List<SuspenseOpeningBalance> getSuspenseOpeningBalance(BigInteger icmcId);

	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegister(BigInteger icmcId);

	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDate(BigInteger icmcId, Calendar sDate,
			Calendar eDate);

	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId);

	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId, Calendar sDate, Calendar eDate);

	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId);

	public boolean updateCurrentVersionStatus(SuspenseOpeningBalance suspenseOpeningBalance);

	// History Code
	public boolean branchHistory(List<History> history);

	public boolean saveDataInBinRegister(BinRegister binRegister);

	public boolean deleteDiscrepancy(long id, BigInteger icmcId);

	public boolean insertFullValueMutilated(Mutilated mutilated);

	public List<Tuple> getPendingBundleByMachine(BigInteger icmcId, Calendar sDate, Calendar eDate);

	Indent viewUpdateIndentIVRRequest(DiversionIRV diversionIRV, BigInteger icmcId);

	Indent viewUpdateIndentOtherBankRequest(BankReceipt otherBankReceiptdb, BigInteger icmcId);

	public List<MachineAllocation> getBundleByCashSource(BigInteger icmcId, String isMachineOrManual,
			Integer denomination, CashSource cashSource, Calendar sDate, Calendar eDate);

	boolean deleteDiscrepancywithooutallocation(long id, BigInteger icmcId);

	public boolean insertCosutodianName(CustodianKeySet custodianKeySet);

	public List<CustodianKeySet> getAssignVaultCustodian(BigInteger icmcId);

	public User isValidUser(String username, BigInteger icmcId);

	public Boolean InsertByDAteSuspeseOpeningBalance(long id, BigInteger icmcId,
			BigDecimal openingBalanceOfDenomination_10, BigDecimal openingBalanceOfDenomination_20,
			BigDecimal openingBalanceOfDenomination_50, BigDecimal openingBalanceOfDenomination_100,
			BigDecimal openingBalanceOfDenomination_200, BigDecimal openingBalanceOfDenomination_500,
			BigDecimal openingBalanceOfDenomination_2000);

	public boolean updateSuspenseBalanceFromLink(long id, BigDecimal replenishment_2000, BigDecimal replenishment_500,
			BigDecimal replenishment_200, BigDecimal replenishment_100, BigDecimal replenishment_50,
			BigDecimal replenishment_20, BigDecimal replenishment_10, BigDecimal depletion_2000,
			BigDecimal depletion_500, BigDecimal depletion_200, BigDecimal depletion_100, BigDecimal depletion_50,
			BigDecimal depletion_20, BigDecimal depletion_10, String srNumber);

	public boolean updateSuspenseBalance(long id, BigDecimal deposit_2000, BigDecimal deposit_500,
			BigDecimal deposit_200, BigDecimal deposit_100, BigDecimal deposit_50, BigDecimal deposit_20,
			BigDecimal deposit_10, BigDecimal withdrawal_2000, BigDecimal withdrawal_500, BigDecimal withdrawal_200,
			BigDecimal withdrawal_100, BigDecimal withdrawal_50, BigDecimal withdrawal_20, BigDecimal withdrawal_10);// code
																														// by
																														// shahabuddin

	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDateByDesc(BigInteger icmcId);

	public List<Mutilated> getMutilatedValueDetails(BigInteger icmcId, Calendar sDate, Calendar eDate);

	List<DSB> getDSB(Indent indent, Calendar sDate, Calendar eDate);

	List<BankReceipt> getBankReceipt(Indent indent, Calendar sDate, Calendar eDate);

	List<DiversionIRV> getDiversionIRV(Indent indent, Calendar sDate, Calendar eDate);

	boolean updateDSB(DSB dsb);

	boolean updateBankReceipt(BankReceipt bankReceipt);

	Boolean updateDiversionIRV(DiversionIRV diversionIRV);

	Discrepancy getDiscrepancyForUploadingImage(User user, Calendar sDate, Calendar eDate);

	void uploadDiscrepancyImage(Discrepancy discrepancy);

	boolean updateBundleInAuditorIndent(AuditorIndent indent);

	public List<MachineAllocation> getBundleFromMachineAllocation(BigInteger icmcId, Integer denomination);

	List<AuditorProcess> getAuditorProcessedData(BigInteger icmcId, Calendar sDate, Calendar eDate);

	AuditorProcess getRepritAuditorProcessRecord(Long id);

}
