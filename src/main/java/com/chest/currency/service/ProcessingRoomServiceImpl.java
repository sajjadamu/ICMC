package com.chest.currency.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chest.currency.dao.ProcessingRoomDaoImpl;
import com.chest.currency.entity.model.AssignVaultCustodian;
import com.chest.currency.entity.model.AuditorIndent;
import com.chest.currency.entity.model.AuditorProcess;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinCapacityDenomination;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
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
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.ProcessAction;
import com.chest.currency.enums.YesNo;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.dao.ProcessingRoomJpaDaoImpl;
import com.chest.currency.qrgencode.QRCodeGen;
import com.chest.currency.util.UtilityJpa;
import com.mysema.query.Tuple;

@Service
@Transactional
public class ProcessingRoomServiceImpl implements ProcessingRoomService {

	private static final Logger LOG = LoggerFactory.getLogger(ProcessingRoomServiceImpl.class);

	@Autowired
	ProcessingRoomDaoImpl processingRoomDao;

	@Autowired
	ProcessingRoomJpaDaoImpl processingRoomJpaDao;

	@Autowired
	QRCodeGen qrCodeGen;

	@Autowired
	CashReceiptService cashReceiptService;

	@Autowired
	ICMCService icmcService;

	@Override
	public String getBinForIndentRequest(String denomination, String bundle) {
		LOG.info("Bin For Indent Request");
		return processingRoomDao.getBinForIndentRequest(denomination, bundle);
	}

	@Override
	@Transactional
	public boolean UpdateProcessStatusAndBinTxnStatus(int id, String type) {
		boolean isSaved = processingRoomDao.UpdateProcessStatusAndBinTxnStatus(id, type);
		if (!isSaved) {
			throw new RuntimeException("Error while Update Process and Bin Txn Record, Please try again");
		}
		return isSaved;
	}

	@Override
	public List<Indent> getIndentRequest(BigInteger icmcId) {
		List<Indent> indentList = processingRoomJpaDao.viewIndentRequest(icmcId);
		return indentList;
	}

	@Override
	public List<Indent> viewBinDetail(int denomination, String bin, BigInteger icmcId) {
		return processingRoomJpaDao.viewBinDetail(denomination, bin, icmcId);
	}

	@Override
	public List<DiscrepancyAllocation> getDiscrepancyByDate(BigInteger icmcId, Date sDate, Date tDate,
			String normalOrSuspense) {
		return processingRoomJpaDao.getDiscrepancyByDate(icmcId, sDate, tDate, normalOrSuspense);
	}

	@Override
	public Indent getUpdateIndentOtherBankRequest(BankReceipt otherBankReceiptdb, BigInteger icmcId) {
		return processingRoomJpaDao.viewUpdateIndentOtherBankRequest(otherBankReceiptdb, icmcId);
	}

	@Override
	public Indent getUpdateIndentIVRRequest(DiversionIRV diversionIRV, BigInteger icmcId) {
		return processingRoomJpaDao.viewUpdateIndentIVRRequest(diversionIRV, icmcId);
	}

	@Override
	public List<BinTransaction> getBinNumListForIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType) {
		return processingRoomJpaDao.getBinNumListForIndent(denomination, bundle, icmcId, cashSource, binCategoryType);
	}

	@Override
	public List<Indent> getBinFromIndent(int denomination) {
		return processingRoomJpaDao.getBinFromIndent(denomination);
	}

	@Override
	public Indent getUpdateIndentRequest(DSB dsbdb, BigInteger icmcId) {
		return processingRoomJpaDao.viewUpdateIndentRequest(dsbdb, icmcId);
	}

	@Override
	public Indent getIndentById(long id) {
		return processingRoomJpaDao.getIndentById(id);
	}

	@Override
	public boolean updateIndentRequest(Indent indent) {
		return processingRoomJpaDao.updateIndentRequest(indent);
	}

	/*
	 * @Override public List<Indent>
	 * getIndentRequestForMachineAllocation(BigInteger icmcId, CashSource
	 * cashSource) { List<Indent> indentListForMachineAllocation =
	 * processingRoomJpaDao.getIndentRequestForMachineAllocation(icmcId,
	 * cashSource); return indentListForMachineAllocation; }
	 */
	@Override
	public List<Indent> getAggregatedIndentRequestForMachineAllocation(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getAggregatedIndentRequestForMachineAllocation(icmcId, cashSource, sDate, eDate);
	}

	@Override

	public Indent getIndentDataById(long id, BigInteger icmcId) {
		return processingRoomJpaDao.getIndentDataById(id, icmcId);
	}

	@Override
	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId, Integer denomination) {
		return processingRoomJpaDao.getBinFromTransaction(bin, icmcId, denomination);
	}

	@Override
	public boolean updateIndentStatus(Indent indent) {
		return processingRoomJpaDao.updateIndentRequest(indent);
	}

	@Override
	@Transactional
	public boolean processIndentRequest(String bin, BigDecimal bundle, User user, Integer denomination) {

		Indent indent = new Indent();// this.getIndentDataById(id,
		indent.setUpdateTime(Calendar.getInstance()); // user.getIcmcId());
		indent.setBin(bin);
		indent.setBundle(bundle);
		indent.setIcmcId(user.getIcmcId());
		indent.setDenomination(denomination);
		boolean isIndentUpdate = false;
		BinTransaction txnBean = this.getBinFromTransaction(bin.trim(), user.getIcmcId(), denomination);
		LOG.info("processIndentRequest txnBean " + txnBean);
		LOG.info("processIndentRequest indent " + indent);
		if (null != txnBean.getReceiveBundle() && txnBean.getReceiveBundle().compareTo(BigDecimal.ZERO) > 0
				&& null != indent.getBundle() && indent.getBundle().compareTo(BigDecimal.ZERO) > 0) {
			LOG.info("subtracting balance ");
			BigDecimal balanceBundle = txnBean.getReceiveBundle().subtract(indent.getBundle());

			LOG.info("processing available balanceBundle " + balanceBundle);
			isIndentUpdate = this.updateIndentStatus(indent);
			LOG.info("update indentStatus " + isIndentUpdate);
			if (isIndentUpdate && balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
				txnBean.setReceiveBundle(BigDecimal.ZERO);
				txnBean.setPendingBundleRequest(BigDecimal.ZERO);
				txnBean.setStatus(BinStatus.EMPTY);
				txnBean.setCashSource(null);
				txnBean.setVerified(YesNo.NULL);
				txnBean.setUpdateBy(user.getId());
				txnBean.setUpdateTime(Calendar.getInstance());
				// int count = this.deleteDataFromBinTxn(txnBean);
				boolean count = this.updateBinTxn(txnBean);
				LOG.info("update bintransaction" + count);
				if (count) {
					BinMaster binMater = new BinMaster();
					binMater.setBinNumber(bin);
					binMater.setIcmcId(user.getIcmcId());
					if (BinCategoryType.BIN == txnBean.getBinCategoryType()) {
						isIndentUpdate = this.updateBinMaster(binMater);
					}
				}
			} else if (isIndentUpdate && balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
				txnBean.setReceiveBundle(balanceBundle);
				txnBean.setPendingBundleRequest(txnBean.getPendingBundleRequest().subtract(indent.getBundle()));
				txnBean.setUpdateTime(Calendar.getInstance());
				isIndentUpdate = this.updateBinTxn(txnBean);
			}
		}
		if (!isIndentUpdate) {
			throw new BaseGuiException("Bin is Empty: While process Indent Request");
		}
		return isIndentUpdate;
	}

	@Override
	public int deleteDataFromBinTxn(BinTransaction txnBean) {
		return processingRoomJpaDao.deleteDataFromBinTxn(txnBean);
	}

	@Override
	public boolean updateBinMaster(BinMaster binMaster) {
		return processingRoomJpaDao.updateBinMaster(binMaster);
	}

	@Override
	public boolean updateBinTxn(BinTransaction binTransaction) {
		if (binTransaction.getPendingBundleRequest().compareTo(BigDecimal.ZERO) < 0
				|| binTransaction.getPendingBundleRequest().compareTo(binTransaction.getReceiveBundle()) > 0) {
			throw new BaseGuiException("Can not update vault: icmc id " + binTransaction.getIcmcId() + " and bin "
					+ binTransaction.getBinNumber());
		}
		return processingRoomJpaDao.updateBinTxn(binTransaction);
	}

	@Override
	public boolean insertInMachineAllocation(MachineAllocation machineAllocation) {
		return processingRoomJpaDao.insertInMachineAllocation(machineAllocation);
	}

	/*
	 * @Override public Indent getBundleFromIndent(String bin, CashSource
	 * cashSource, BigInteger icmcId) { Indent indent =
	 * processingRoomJpaDao.getBundleFromIndent(bin, cashSource, icmcId); return
	 * indent; }
	 */

	@Override
	public boolean updateBundleInAuditorIndent(AuditorIndent indent) {
		return processingRoomJpaDao.updateBundleInAuditorIndent(indent);
	}

	@Override
	public boolean updateBundleInIndent(Indent indent) {
		return processingRoomJpaDao.updateBundleInIndent(indent);
	}

	@Override
	public boolean processMachineAllocation(MachineAllocation machine, Indent indent, User user) {
		boolean isAllsuccess = false;

		List<Indent> indentList = processingRoomJpaDao.getIndentListForMachineAllocation(machine.getIcmcId(),
				machine.getDenomination(), machine.getCashSource());
		List<Indent> eligibleIndentList = UtilityJpa.getEligibleIndentListForMachineAllocation(indentList,
				machine.getIssuedBundle(), user);

		if (!CollectionUtils.isEmpty(eligibleIndentList)) {
			for (Indent indentTemp : eligibleIndentList) {
				updateBundleInIndent(indentTemp);
			}

			isAllsuccess = this.insertInMachineAllocation(machine);
		}
		return isAllsuccess;
	}

	@Override
	public boolean auditorMachineAllocation(MachineAllocation machine, AuditorIndent indent, User user) {
		boolean isAllsuccess = false;

		List<AuditorIndent> indentList = processingRoomJpaDao
				.getAuditorIndentListForMachineAllocation(machine.getIcmcId(), machine.getDenomination());
		List<AuditorIndent> eligibleIndentList = UtilityJpa.getEligibleAuditorIndentListForMachineAllocation(indentList,
				machine.getIssuedBundle(), user);

		if (!CollectionUtils.isEmpty(eligibleIndentList)) {
			for (AuditorIndent indentTemp : eligibleIndentList) {
				updateBundleInAuditorIndent(indentTemp);
			}
			isAllsuccess = this.insertInMachineAllocation(machine);
		}
		return isAllsuccess;
	}

	@Override
	public List<Process> getProcessedDataList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getProcessedDataList(icmcId, sDate, eDate);
	}

	@Override
	public List<AuditorProcess> getAuditorProcessedData(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getAuditorProcessedData(icmcId, sDate, eDate);
	}

	@Override
	public List<BinMaster> getPriorityBinListByType(BinMaster binMaster) {
		return processingRoomJpaDao.getPriorityBinListByType(binMaster);
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx) {
		return processingRoomJpaDao.getBinTxnListByDenom(binTx);
	}

	@Override
	public boolean updateBinMasterForProcess(BinMaster binMaster) {
		return processingRoomJpaDao.updateBinMasterForProcess(binMaster);
	}

	@Override
	public boolean insertInBinTxn(BinTransaction binTransaction) {
		return processingRoomJpaDao.insertInBinTxn(binTransaction);
	}

	@Override
	public boolean updateInBinTxn(BinTransaction binTransaction) {
		return processingRoomJpaDao.updateInBinTxn(binTransaction);
	}

	@Override
	public boolean createProcess(List<Process> process) {
		return processingRoomJpaDao.createProcess(process);
	}

	@Override
	public List<Process> processRecordForMachine(Process process, User user) {

		List<BinMaster> binMasterList = new ArrayList<BinMaster>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();
		List<Process> processList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();

		BigDecimal bundleFromTxn = BigDecimal.ZERO;

		String machineOrManual = "";
		if (process.getProcessAction() == ProcessAction.MACHINE) {
			machineOrManual = "NO";
		}
		if (process.getProcessAction() == ProcessAction.MANUAL) {
			machineOrManual = "YES";
		}

		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();
		UtilityJpa.setStartDate(sDate);
		UtilityJpa.setEndDate(eDate);

		List<MachineAllocation> pendingBundleListFromMachineAllocation = this.getBundleByCashSource(user.getIcmcId(),
				machineOrManual, process.getDenomination(), process.getCashSource(), sDate, eDate);

		List<MachineAllocation> eligiblePendingBundleList = UtilityJpa.getEligibleBundleListForMachineAllocation(
				pendingBundleListFromMachineAllocation, process.getBundle(), user);

		if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
			throw new BaseGuiException(
					"Required Bundle is Not available for Denomination:" + process.getDenomination());
		}

		for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
			updatePendingBundleInMachineAllocation(machineAllocation);
			process.setMachineId(machineAllocation.getId());
		}

		// End Of Machine Allocation Code

		if (process.getBinCategoryType() == BinCategoryType.BIN) {
			List<BinCapacityDenomination> capacityList = cashReceiptService
					.getMaxBundleCapacity(process.getDenomination(), process.getCurrencyType());

			binMasterList = getPriorityBinListByType(user, process.getCurrencyType());

			binList = getBinTxnListByDenom(process, user);

			binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, process.getBundle(), true,
					capacityList, process.getCurrencyType(), process.getCashSource());

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(process.getBundle()) == 0) {
				processList = UtilityJpa.getProcessBean(process, binTxs, user);
				addTransactions(user, binTxs, process.getCurrencyType());
			} else {
				throw new BaseGuiException("Space is not available in BIN for " + process.getCurrencyType()
						+ " Category and " + process.getDenomination() + " Denomination");
			}
		}

		if (process.getBinCategoryType() == BinCategoryType.BOX) {
			BoxMaster boxMaster = new BoxMaster();
			boxMaster.setIcmcId(user.getIcmcId());
			boxMaster.setDenomination(process.getDenomination());
			boxMaster.setCurrencyType(process.getCurrencyType());
			boxMasterList = cashReceiptService.getBoxFromBoxMaster(boxMaster);
			binList = getBinTxnListByDenom(process, user);

			binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, process.getBundle(), true,
					process.getCurrencyType(), process.getCashSource());

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(process.getBundle()) == 0) {
				processList = UtilityJpa.getProcessBean(process, binTxs, user);
				addInTransactionsForBox(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available in BOX for " + process.getCurrencyType()
						+ " Category and " + process.getDenomination() + " Denomination");
			}
		}

		/*
		 * String machineOrManual = ""; if (process.getProcessAction() ==
		 * ProcessAction.MACHINE) { machineOrManual = "NO"; } if
		 * (process.getProcessAction() == ProcessAction.MANUAL) {
		 * machineOrManual = "YES"; }
		 * 
		 * List<MachineAllocation> pendingBundleListFromMachineAllocation =
		 * processingRoomJpaDao
		 * .getPendingBundleFromMachineAllocation(user.getIcmcId(),
		 * process.getDenomination(), machineOrManual);
		 * 
		 * List<MachineAllocation> eligiblePendingBundleList =
		 * UtilityJpa.getEligibleBundleListForMachineAllocation(
		 * pendingBundleListFromMachineAllocation, process.getBundle(), user);
		 * 
		 * if (eligiblePendingBundleList == null ||
		 * eligiblePendingBundleList.isEmpty()) { throw new BaseGuiException(
		 * "Required Bundle is Not available for Denomination:" +
		 * process.getDenomination()); }
		 * 
		 * for (MachineAllocation machineAllocation : eligiblePendingBundleList)
		 * { updatePendingBundleInMachineAllocation(machineAllocation);
		 * process.setMachineId(machineAllocation.getId()); }
		 */ addProcess(processList);

		// History Code
		List<History> historyList = new ArrayList<History>();
		History history = null;
		for (Process branch : processList) {
			history = new History();
			history.setBinNumber(branch.getBinNumber());
			history.setDenomination(branch.getDenomination());
			history.setReceiveBundle(branch.getBundle());
			history.setWithdrawalBundle(BigDecimal.ZERO);
			history.setInsertBy(branch.getInsertBy());
			history.setUpdateBy(branch.getUpdateBy());
			history.setInsertTime(branch.getInsertTime());
			history.setUpdateTime(branch.getInsertTime());
			history.setIcmcId(branch.getIcmcId());
			history.setStatus(OtherStatus.REQUESTED);
			history.setDepositOrWithdrawal("DEPOSIT");
			history.setType(process.getCurrencyType().value());
			historyList.add(history);
		}

		branchHistory(historyList);

		return processList;
	}

	private List<BinMaster> getPriorityBinListByType(User user, CurrencyType currencyType) {
		BinMaster master = new BinMaster();
		master.setFirstPriority(currencyType);
		master.setIcmcId(user.getIcmcId());
		return this.getPriorityBinListByType(master);
	}

	private List<BinTransaction> getBinTxnListByDenom(Process process, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(process.getBundle());
		binTxTemp.setDenomination(process.getDenomination());
		binTxTemp.setBinType(process.getCurrencyType());
		binTxTemp.setBinCategoryType(process.getBinCategoryType());
		return this.getBinTxnListByDenom(binTxTemp);
	}

	private List<BinTransaction> getBinTxnListByDenomForAuditorProcess(AuditorProcess process, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(process.getBundle());
		binTxTemp.setDenomination(process.getDenomination());
		binTxTemp.setBinType(process.getCurrencyType());
		binTxTemp.setBinCategoryType(process.getBinCategoryType());
		return this.getBinTxnListByDenom(binTxTemp);
	}

	private void addTransactions(User user, List<BinTransaction> binTxs, CurrencyType currencyType) {
		BinMaster masterTemp = new BinMaster();
		masterTemp.setFirstPriority(currencyType);
		masterTemp.setIcmcId(user.getIcmcId());

		for (BinTransaction binTx : binTxs) {
			if (binTx.getId() != null && binTx.getId() > 0) {
				this.updateInBinTxn(binTx);
			} else {
				masterTemp.setBinNumber(binTx.getBinNumber());
				masterTemp.setIcmcId(binTx.getIcmcId());
				this.updateBinMasterForProcess(masterTemp);
				this.insertInBinTxn(binTx);
			}
		}
	}

	private void addProcess(List<Process> processList) {
		for (Process br : processList) {
			br.setFilepath(getQrCode(br));
		}
		this.createProcess(processList);
	}

	private void addProcessForAuditor(List<AuditorProcess> processList) {
		for (AuditorProcess br : processList) {
			br.setFilepath(getQrCodeAuditor(br));
		}
		this.createProcessForAuditor(processList);
	}

	private void addProcessForCRA(ProcessBundleForCRAPayment processList) {
		processList.setFilepath(getQrCodeForCRA(processList));
		this.saveCRAPaymentProcessRecord(processList);
	}

	private String getPath(String filepath) {
		return filepath;
	}

	public String getQrCode(Process process) {
		String filepath = qrCodeGen.generateProcessingRoomQR(process);
		return getPath(filepath);
	}

	public String getQrCodeAuditor(AuditorProcess process) {
		String filepath = qrCodeGen.generateProcessingRoomQRAuditor(process);
		return getPath(filepath);
	}

	public String getQrCodeForCRA(ProcessBundleForCRAPayment process) {
		String filepath = qrCodeGen.generateCRAPaymentProcessingRoomQR(process);
		return getPath(filepath);
	}

	@Override
	public boolean updatePrcocessStatus(Process process) {
		return processingRoomJpaDao.updateProcessStatus(process);
	}

	@Override
	public List<Tuple> indentSummary(BigInteger icmcId, CashSource cashSource) {
		return processingRoomJpaDao.indentSummary(icmcId, cashSource);
	}

	@Override
	public boolean UploadDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet) {
		boolean isAllSuccess = false;

		try {
			isAllSuccess = processingRoomJpaDao.UploadDefineKeySet(list, defineKeySet);
			Set<String> custodians = new LinkedHashSet<String>();
			for (DefineKeySet custodian : list) {
				custodians.add(custodian.getCustodian());
			}
			for (String custodian : custodians) {
				CustodianKeySet custodianKeySet = new CustodianKeySet();
				custodianKeySet.setCustodian(custodian);
				custodianKeySet.setIcmcId(defineKeySet.getIcmcId());
				custodianKeySet.setInsertBy(defineKeySet.getInsertBy());
				custodianKeySet.setUpdateBy(defineKeySet.getUpdateBy());
				custodianKeySet.setInsertTime(defineKeySet.getInsertTime());
				custodianKeySet.setUpdateTime(defineKeySet.getUpdateTime());
				processingRoomJpaDao.insertCosutodianName(custodianKeySet);
			}

		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;

	}

	@Override
	public List<String> getDefineKeySet(BigInteger icmcId) {
		return processingRoomJpaDao.getDefineKeySet(icmcId);
	}

	@Override
	public DefineKeySet defineKeySetRecordForModify(Long id) {
		return processingRoomJpaDao.defineKeySetRecordForModify(id);
	}

	@Override
	public boolean updateDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = processingRoomJpaDao.UpdateDefineKeySet(list, defineKeySet);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public List<AssignVaultCustodian> getAssignVaultCustodian(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getListAssignVaultCustodian(icmcId, sDate, eDate);
	}

	public List<MachineAllocation> getMachineAllocationRecordForProcessing(BigInteger icmcId, Calendar sDate,
			Calendar eDate) {
		return processingRoomJpaDao.getMachineAllocationRecordForProcessing(icmcId, sDate, eDate);
	}

	@Override
	public boolean saveAssignVaultCustodian(AssignVaultCustodian assignVaultCustodian,
			AssignVaultCustodian vaultCustodian) {
		return processingRoomJpaDao.saveAssignVaultCustodian(assignVaultCustodian, vaultCustodian);
	}

	@Override
	public AssignVaultCustodian assignVaultCustodianRecordForModify(Long id) {
		return processingRoomJpaDao.assignVaultCustodianRecordForModify(id);
	}

	@Override
	public boolean updateAssignVaultCustodian(AssignVaultCustodian assignVaultCustodian) {
		return processingRoomJpaDao.updateAssignVaultCustodian(assignVaultCustodian);
	}

	@Override
	public List<RepeatabilityTestInput> getRepeatabilityTestInput(BigInteger icmcId) {
		return processingRoomJpaDao.getRepeatabilityTestInput(icmcId);
	}

	@Override
	public boolean insertRepeatabilityTestInput(RepeatabilityTestInput repeatabilityTestInput) {
		return processingRoomJpaDao.insertRepeatabilityTestInput(repeatabilityTestInput);

	}

	@Override
	public RepeatabilityTestInput repeatabilityTestInputRecordForModify(Long id) {
		return processingRoomJpaDao.repeatabilityTestInputRecordForModify(id);
	}

	@Override
	public boolean updateRepeatabilityTestInput(RepeatabilityTestInput repeatabilityTestInput) {

		return processingRoomJpaDao.updateRepeatabilityTestInput(repeatabilityTestInput);
	}

	@Override
	public List<RepeatabilityTestOutput> getRepeatabilityTestOutput(BigInteger icmcId) {
		return processingRoomJpaDao.getRepeatabilityTestOutput(icmcId);
	}

	@Override
	public boolean insertRepeatabilityTestOutput(RepeatabilityTestOutput repeatabilityTestOutput) {
		return processingRoomJpaDao.insertRepeatabilityTestOutput(repeatabilityTestOutput);

	}

	@Override
	public RepeatabilityTestOutput repeatabilityTestOutputRecordForModify(Long id) {
		return processingRoomJpaDao.repeatabilityTestOutputRecordForModify(id);
	}

	@Override
	public boolean updateRepeatabilityTestOutput(RepeatabilityTestOutput repeatabilityTestOutput) {
		return processingRoomJpaDao.updateRepeatabilityTestOutput(repeatabilityTestOutput);
	}

	@Override
	public boolean modifyDefineKeySet(DefineKeySet defineKeySet) {
		processingRoomJpaDao.modifyDefineKeySet(defineKeySet);
		return true;
	}

	@Override
	public List<FreshCurrency> getFreshCurrency(BigInteger icmcId) {
		return processingRoomJpaDao.getFreshCurrency(icmcId);
	}

	@Override
	public boolean insertFreshCurrency(FreshCurrency freshCurrency) {
		return processingRoomJpaDao.insertFreshCurrency(freshCurrency);
	}

	@Override
	public FreshCurrency freshCurrencyForModify(Long id) {
		return processingRoomJpaDao.freshCurrencyForModify(id);
	}

	@Override
	public boolean updateFreshCurrency(FreshCurrency freshCurrency) {
		return processingRoomJpaDao.updateFreshCurrency(freshCurrency);
	}

	@Override
	public boolean updateIndentStatusForCancel(Indent indent) {
		boolean isSaved = processingRoomJpaDao.updateIndentStatusForCancel(indent);
		return isSaved;
	}

	@Override
	public boolean saveDiscrepancy(Discrepancy discrepancy) {
		for (DiscrepancyAllocation da : discrepancy.getDiscrepancyAllocations()) {
			da.setIcmcId(discrepancy.getIcmcId());
			da.setInsertBy(discrepancy.getInsertBy());
			da.setUpdateBy(discrepancy.getUpdateBy());
			da.setInsertTime(discrepancy.getInsertTime());
			da.setUpdateTime(discrepancy.getUpdateTime());
			da.setNormalOrSuspense(discrepancy.getNormalOrSuspense());
			da.setStatus(OtherStatus.RECEIVED);
			da.setMachineNumber(discrepancy.getMachineNumber());
			da.setDiscrepancyDate(discrepancy.getDiscrepancyDate());
			da.setSolId(discrepancy.getSolId());
			da.setBranch(discrepancy.getBranch());
			// da.setFilepath(discrepancy.getFilepath());
			da.setAccountTellerCam(discrepancy.getAccountTellerCam());
			da.setCustomerName(discrepancy.getCustomerName());
			da.setAccountNumber(discrepancy.getAccountNumber());
		}
		return processingRoomJpaDao.saveDiscrepancy(discrepancy);
	}

	@Override
	public List<DiscrepancyAllocation> getDiscrepancy(BigInteger icmcId, String normalOrSuspense) {
		return processingRoomJpaDao.getDiscrepancy(icmcId, normalOrSuspense);
	}

	@Override
	public List<FreshFromRBI> getFreshFromRBIRecord(User user) {
		return processingRoomJpaDao.getFreshFromRBIRecord(user);
	}

	@Override
	public boolean updateBinTransaction(BinTransaction binTransaction) {
		return processingRoomJpaDao.updateBinTransaction(binTransaction);
	}

	@Override
	public List<Tuple> indentSummaryForFresh(BigInteger icmcId, CashSource cashSource) {
		return processingRoomJpaDao.indentSummaryForFresh(icmcId, cashSource);
	}

	@Override
	public List<Machine> getMachineNumber(BigInteger icmcId) {
		return processingRoomJpaDao.getMachineNumber(icmcId);
	}

	@Override
	public List<DefineKeySet> getKeyNumber(DefineKeySet defineKeySet) {
		return processingRoomJpaDao.getKeyNumber(defineKeySet);
	}

	@Override
	public List<DefineKeySet> getLocationOfLock(DefineKeySet defineKeySet) {
		return processingRoomJpaDao.getLocationOfLock(defineKeySet);
	}

	@Override
	public List<BranchReceipt> getBinNumListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType) {
		List<BranchReceipt> periorityBinList = new ArrayList<>();

		List<BranchReceipt> returnBinFromBranchReceiptList = processingRoomJpaDao
				.getRetunBinListForIndentFromBranchReceipt(denomination, bundle, icmcId, cashSource, binCategoryType);

		List<BranchReceipt> branchUploadBinFromBranchReceiptList = processingRoomJpaDao
				.getBranchUploadBinListForIndentFromBranchReceipt(denomination, bundle, icmcId, cashSource,
						binCategoryType);

		List<BranchReceipt> insertBinListFromBranchReceiptList = processingRoomJpaDao
				.getInsertBinListForIndentFromBranchReceipt(denomination, bundle, icmcId, cashSource, binCategoryType);

		if (returnBinFromBranchReceiptList.size() != 0) {
			periorityBinList.addAll(returnBinFromBranchReceiptList);
		}

		if (branchUploadBinFromBranchReceiptList.size() != 0) {
			periorityBinList.addAll(branchUploadBinFromBranchReceiptList);
		}

		if (insertBinListFromBranchReceiptList.size() != 0) {
			periorityBinList.addAll(insertBinListFromBranchReceiptList);
		}

		return periorityBinList;
	}

	@Override
	public boolean saveCRAPaymentProcessRecord(ProcessBundleForCRAPayment processBundleForCRAPayment) {
		return processingRoomJpaDao.saveCRAPaymentProcessRecord(processBundleForCRAPayment);
	}

	@Override
	public ProcessBundleForCRAPayment processRecordForCRAPayment(ProcessBundleForCRAPayment process, User user) {

		CRAAllocation craAllocation = new CRAAllocation();
		craAllocation.setId(process.getId());
		craAllocation.setIcmcId(user.getIcmcId());

		craAllocation = this.getPendingBundleById(craAllocation);
		BigDecimal pendingbundle = craAllocation.getPendingRequestedBundle().subtract(process.getBundle());
		if (pendingbundle.compareTo(BigDecimal.ZERO) == 0) {
			craAllocation.setStatus(OtherStatus.ACCEPTED);
			// craAllocation.setBundle(process.getBundle());
			craAllocation.setPendingRequestedBundle(pendingbundle);
			this.updateForwarPending(craAllocation);

		} else {
			// craAllocation.setStatus(OtherStatus.ACCEPTED);
			craAllocation.setPendingRequestedBundle(pendingbundle);
			// craAllocation.setBundle(process.getBundle());
			this.updateForwarPending(craAllocation);
		}

		ProcessBundleForCRAPayment processCRAPayment = UtilityJpa.processCRAPaymentBundle(process, user);

		List<MachineAllocation> pendingBundleListFromMachineAllocation = processingRoomJpaDao
				.getPendingBundleFromMachineAllocation(user.getIcmcId(), process.getDenomination(), "NO");

		List<MachineAllocation> eligiblePendingBundleList = UtilityJpa.getEligibleBundleListForMachineAllocation(
				pendingBundleListFromMachineAllocation, process.getBundle(), user);

		if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
			throw new BaseGuiException(
					"Required Bundle is Not available for Denomination:" + process.getDenomination());
		}

		for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
			updatePendingBundleInMachineAllocation(machineAllocation);
		}

		addProcessForCRA(processCRAPayment);

		return processCRAPayment;
	}

	@Override
	public List<Tuple> getForwardBundleTotalForCRAPayment(BigInteger icmcId) {
		return processingRoomJpaDao.getForwardBundleTotalForCRAPayment(icmcId);
	}

	@Override
	public ForwardBundleForCRAPayment getBundleFromForwardCRA(ForwardBundleForCRAPayment forwardBundle) {
		return processingRoomJpaDao.getBundleFromForwardCRA(forwardBundle);
	}

	@Override
	public boolean insertBranchIndentRequest(List<BranchReceipt> eligibleBranchIndentRequestList) {
		return processingRoomJpaDao.insertBranchIndentRequest(eligibleBranchIndentRequestList);
	}

	@Override
	@Transactional
	public boolean insertIndentRequestAndUpdateBinTxAndBranchReceipt(List<Indent> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList, List<BranchReceipt> branchReceiptList) {

		boolean isSaved = processingRoomJpaDao.insertIndentRequest(eligibleIndentRequestList);

		LOG.info("insertIndentRequestAndUpdateBinTxAndBranchReceipt " + binTransactionList);
		for (BinTransaction btx : binTransactionList) {
			if (btx.isDirty()) {
				LOG.info("indent request updateBinTxn " + btx);
				btx.setUpdateTime(Calendar.getInstance());
				isSaved = this.updateBinTxn(btx);
				LOG.info("indent request updateBinTxn isSaved" + isSaved);
			}
		}

		for (BranchReceipt branchReceipt : branchReceiptList) {
			LOG.info("indent request updateBranchReceipt " + branchReceipt);
			if (branchReceipt.getStatus() == OtherStatus.PROCESSED) {
				isSaved = processingRoomJpaDao.updateBranchReceipt(branchReceipt);
			}
			LOG.info("indent request updateBranchReceipt isSaved" + isSaved);
		}

		if (!isSaved) {
			throw new RuntimeException("Error while Indent Request Saving");
		}
		return true;
	}

	@Override
	@Transactional
	public boolean insertSasRequestAndUpdateBinTxAndBranchReceipt(List<SASAllocation> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList, List<BranchReceipt> branchReceiptList, Sas sasAllocationParent) {
		boolean isSaved = true;
		LOG.info("insertSasRequestAndUpdateBinTxAndBranchReceipt " + binTransactionList);
		for (BinTransaction btx : binTransactionList) {
			if (btx.isDirty()) {
				LOG.info("sas requestinsert update " + btx);
				btx.setUpdateTime(Calendar.getInstance());
				isSaved = this.updateBinTxn(btx);
			}
		}
		for (BranchReceipt branchReceipt : branchReceiptList) {
			if (branchReceipt.getStatus() == OtherStatus.PROCESSED) {
				branchReceipt.setSasId(sasAllocationParent.getId());
				isSaved = processingRoomJpaDao.updateBranchReceipt(branchReceipt);
				// isSaved =
				// processingRoomJpaDao.updateBranchReceiptForSas(branchReceipt);
				LOG.info("sas branchReceipt update " + branchReceipt);
			}
		}
		if (!isSaved) {
			throw new RuntimeException("Error while Branch Payment Request");
		}
		return true;
	}

	@Override
	public boolean insertIndentRequestAndUpdateBinTxAndFreshFromRBI(List<Indent> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList, List<FreshFromRBI> freshList) {
		boolean isSaved = processingRoomJpaDao.insertIndentRequest(eligibleIndentRequestList);
		for (BinTransaction btx : binTransactionList) {
			if (btx.isDirty()) {
				btx.setUpdateTime(Calendar.getInstance());
				isSaved = this.updateBinTxn(btx);
			}
		}
		for (FreshFromRBI freshReceipt : freshList) {
			if (freshReceipt.getCashSource() == CashSource.RBI) {
				isSaved = processingRoomJpaDao.updateFreshFromRBI(freshReceipt);
			}
		}
		if (!isSaved) {
			throw new RuntimeException("Error while Indent Request Saving");
		}
		return true;
	}

	@Override
	public List<Tuple> indentSummaryForFreshFromBinTxn(BigInteger icmcId, CashSource cashSource) {
		return processingRoomJpaDao.indentSummaryForFreshFromBinTxn(icmcId, cashSource);
	}

	@Override
	public List<BinTransaction> getBinNumListForFreshIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType, String rbiOrderNo) {
		return processingRoomJpaDao.getBinNumListForFreshIndent(denomination, bundle, icmcId, cashSource,
				binCategoryType, rbiOrderNo);
	}

	@Override
	public List<CRAAllocation> getDataFromCRAAllocationForProcessing(BigInteger icmcId) {
		return processingRoomJpaDao.getDataFromCRAAllocationForProcessing(icmcId);
	}

	@Override
	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getDiscrepancyReports(icmcId, sDate, eDate);
	}

	@Override
	public Discrepancy getDiscrepancyForUploadingImage(User user, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getDiscrepancyForUploadingImage(user, sDate, eDate);
	}

	@Override
	public void uploadDiscrepancyImage(Discrepancy discrepancy) {
		processingRoomJpaDao.uploadDiscrepancyImage(discrepancy);

	}

	@Override
	public CRAAllocation getPendingBundleById(CRAAllocation craAllocation) {
		return processingRoomJpaDao.getPendingBundleById(craAllocation);
	}

	@Override
	public boolean updateForwarPending(CRAAllocation craAllocation) {
		return processingRoomJpaDao.updateForwarPending(craAllocation);
	}

	@Override
	public List<Tuple> getMachineAllocationRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getMachineAllocationRecord(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getProcessRecord(BigInteger icmcId, Calendar sDate, Calendar eDate, CurrencyType currencyType) {
		return processingRoomJpaDao.getProcessRecord(icmcId, sDate, eDate, currencyType);
	}

	@Override
	public BigDecimal getTotalBundleInBin(int denomination, String bin, BigInteger icmcId) {
		return processingRoomJpaDao.getTotalBundleInBin(denomination, bin, icmcId);
	}

	@Override
	public boolean processMachineAllocationForBranch(MachineAllocation machineAllocation, Indent indent) {
		return false;
	}

	@Override
	public List<Integer> getMachineNumberList(BigInteger icmcId) {
		return processingRoomJpaDao.getMachineNumberList(icmcId);
	}

	@Override
	public List<Tuple> getDiscrepancyListForIOReport(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getDiscrepancyListForIOReport(icmcId, sDate, eDate);
	}

	@Override
	public List<MachineAllocation> getPendingBundleFromMachineAllocation(BigInteger icmcId, Integer denomination,
			String machineOrManual) {
		return processingRoomJpaDao.getPendingBundleFromMachineAllocation(icmcId, denomination, machineOrManual);
	}

	@Override
	public boolean updatePendingBundleInMachineAllocation(MachineAllocation machineAllocation) {
		return processingRoomJpaDao.updatePendingBundleInMachineAllocation(machineAllocation);
	}

	private void addInTransactionsForBox(User user, List<BinTransaction> binTxs) {
		BoxMaster masterTemp = new BoxMaster();
		masterTemp.setIcmcId(user.getIcmcId());

		for (BinTransaction binTx : binTxs) {
			if (binTx.getId() != null && binTx.getId() > 0) {
				this.updateInBinTxn(binTx);
			} else {
				masterTemp.setBoxName(binTx.getBinNumber());
				masterTemp.setIcmcId(binTx.getIcmcId());
				cashReceiptService.updateBoxMaster(masterTemp);
				this.insertInBinTxn(binTx);
			}
		}
	}

	@Override
	public String getMachineSerialNo(BigInteger icmcId, int machineNo) {
		return processingRoomJpaDao.getMachineSerialNo(icmcId, machineNo);
	}

	@Override
	public List<Tuple> getPendingBundle(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getPendingBundle(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getPendingBundleByMachine(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getPendingBundleByMachine(icmcId, sDate, eDate);
	}

	@Override
	public List<MachineAllocation> getAggregatedBundleToBeReturnedToVault(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getAggregatedBundleToBeReturnedToVault(icmcId, cashSource, sDate, eDate);
	}

	@Override
	public String getUserName(String userId) {
		return processingRoomJpaDao.getUserName(userId);
	}

	@Override
	public Process getRepritRecord(Long id) {
		return processingRoomJpaDao.getRepritRecord(id);
	}

	@Override
	public AuditorProcess getRepritAuditorProcessRecord(Long id) {
		return processingRoomJpaDao.getRepritAuditorProcessRecord(id);
	}

	public List<Mutilated> getMitulatedFullValue(BigInteger icmcId) {
		return processingRoomJpaDao.getMitulatedFullValue(icmcId);
	}

	@Override
	public List<Mutilated> processRecordForMutilated(Mutilated mutilated, User user) {

		List<BinMaster> binMasterList = new ArrayList<BinMaster>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();
		List<Mutilated> mutilatedList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();

		BigDecimal bundleFromTxn = BigDecimal.ZERO;
		if (mutilated.getBinCategoryType() == BinCategoryType.BIN) {

			List<BinCapacityDenomination> capacityList = cashReceiptService
					.getMaxBundleCapacity(mutilated.getDenomination(), mutilated.getCurrencyType());

			binMasterList = getPriorityBinListByType(user, mutilated.getCurrencyType());

			binList = getBinTxnListByDenomForMutilated(mutilated, user);

			binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, mutilated.getBundle(), true,
					capacityList, mutilated.getCurrencyType(), mutilated.getCashSource());

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(mutilated.getBundle()) == 0) {
				mutilatedList = UtilityJpa.getMutilatedBin(mutilated, binTxs, user);
				addTransactions(user, binTxs, mutilated.getCurrencyType());
			} else {
				throw new BaseGuiException("Space is not available in BIN for " + mutilated.getCurrencyType()
						+ " Category and " + mutilated.getDenomination() + " Denomination");
			}
		}

		if (mutilated.getBinCategoryType() == BinCategoryType.BOX) {
			BoxMaster boxMaster = new BoxMaster();
			boxMaster.setIcmcId(user.getIcmcId());
			boxMaster.setDenomination(mutilated.getDenomination());
			boxMaster.setCurrencyType(mutilated.getCurrencyType());
			boxMasterList = cashReceiptService.getBoxFromBoxMaster(boxMaster);
			binList = getBinTxnListByDenomForMutilated(mutilated, user);

			binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, mutilated.getBundle(), true,
					mutilated.getCurrencyType(), mutilated.getCashSource());

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(mutilated.getBundle()) == 0) {
				mutilatedList = UtilityJpa.getMutilatedBin(mutilated, binTxs, user);
				addInTransactionsForBox(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available in BOX for " + mutilated.getCurrencyType()
						+ " Category and " + mutilated.getDenomination() + " Denomination");
			}
		}

		createMutilated(mutilatedList);

		return mutilatedList;
	}

	private List<BinTransaction> getBinTxnListByDenomForMutilated(Mutilated mutilated, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(mutilated.getBundle());
		binTxTemp.setDenomination(mutilated.getDenomination());
		binTxTemp.setBinType(mutilated.getCurrencyType());
		binTxTemp.setBinCategoryType(mutilated.getBinCategoryType());
		return this.getBinTxnListByDenom(binTxTemp);
	}

	@Override
	public boolean createMutilated(List<Mutilated> mutilatedList) {
		return processingRoomJpaDao.createMutilated(mutilatedList);
	}

	@Override
	public List<Tuple> indentSummaryForMutilated(BigInteger icmcId) {
		return processingRoomJpaDao.indentSummaryForMutilated(icmcId);
	}

	@Override
	public List<BinTransaction> getBinNumListForMutilatedIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			BinCategoryType binCategoryType) {
		return processingRoomJpaDao.getBinNumListForMutilatedIndent(denomination, bundle, icmcId, binCategoryType);
	}

	@Override
	public boolean insertMitulatedIndentRequest(List<MutilatedIndent> eligibleIndentRequestList) {
		return processingRoomJpaDao.insertMitulatedIndentRequest(eligibleIndentRequestList);
	}

	@Override
	public boolean insertMutilatedIndentRequestAndUpdateBinTxn(List<MutilatedIndent> eligibleIndentRequestList,
			List<BinTransaction> binTransactionList) {
		boolean isSaved = processingRoomJpaDao.insertMitulatedIndentRequest(eligibleIndentRequestList);
		for (BinTransaction btx : binTransactionList) {
			if (btx.isDirty()) {
				isSaved = this.updateBinTxn(btx);
			}
		}
		if (!isSaved) {
			throw new RuntimeException("Error while Mutilated Indent Request Saving");
		}
		return true;
	}

	@Override
	public List<MutilatedIndent> getMutilatedIndent(BigInteger icmcId) {
		return processingRoomJpaDao.getMutilatedIndent(icmcId);
	}

	@Override
	public boolean processMutilatedIndentRequest(String bin, BigDecimal bundle, User user, Long id) {

		MutilatedIndent mutilatedIndent = new MutilatedIndent();
		mutilatedIndent.setBin(bin);
		mutilatedIndent.setBundle(bundle);
		mutilatedIndent.setIcmcId(user.getIcmcId());
		mutilatedIndent.setId(id);
		boolean isIndentUpdate = false;

		BinTransaction txnBin = this.getBinFromTransaction(bin.trim(), user.getIcmcId(), 2000);

		if (mutilatedIndent != null && txnBin != null && txnBin.getReceiveBundle() != null
				&& txnBin.getReceiveBundle().compareTo(BigDecimal.ZERO) > 0 && mutilatedIndent.getBundle() != null
				&& mutilatedIndent.getBundle().compareTo(BigDecimal.ZERO) > 0) {

			BigDecimal balanceBundle = txnBin.getReceiveBundle().subtract(mutilatedIndent.getBundle());

			isIndentUpdate = this.updateMutilatedIndentRequest(mutilatedIndent);

			if (isIndentUpdate && balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
				txnBin.setReceiveBundle(BigDecimal.ZERO);
				txnBin.setPendingBundleRequest(BigDecimal.ZERO);
				txnBin.setStatus(BinStatus.EMPTY);
				txnBin.setUpdateBy(user.getId());
				txnBin.setUpdateTime(Calendar.getInstance());

				boolean count = this.updateBinTxn(txnBin);
				if (count) {
					BinMaster binMater = new BinMaster();
					binMater.setBinNumber(bin);
					binMater.setIcmcId(user.getIcmcId());
					if (BinCategoryType.BIN == txnBin.getBinCategoryType()) {
						isIndentUpdate = this.updateBinMaster(binMater);
					}
				}
			} else if (isIndentUpdate && balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
				txnBin.setReceiveBundle(balanceBundle);
				txnBin.setPendingBundleRequest(txnBin.getPendingBundleRequest().subtract(bundle));
				isIndentUpdate = this.updateBinTxn(txnBin);
			}

		}
		if (!isIndentUpdate) {
			throw new RuntimeException("Error while process Indent Request");
		}
		return isIndentUpdate;
	}

	@Override
	public boolean updateMutilatedIndentRequest(MutilatedIndent mutilatedIndent) {
		return processingRoomJpaDao.updateMutilatedIndentRequest(mutilatedIndent);
	}

	@Override
	public String getICMCName(long icmcId) {
		return processingRoomJpaDao.getICMCName(icmcId);
	}

	@Override
	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		return processingRoomJpaDao.getDiscrepancyReports(icmcId, sDate, eDate, normalOrSuspense);
	}

	@Override
	public Discrepancy getDescripancyDataForEdit(Long id, BigInteger icmcId) {
		return processingRoomJpaDao.getDescripancyDataForEdit(id, icmcId);
	}

	@Override
	public DiscrepancyAllocation getDescripancyAllocationDataForEdit(Long id, BigInteger icmcId) {
		return processingRoomJpaDao.getDescripancyAllocationDataForEdit(id, icmcId);
	}

	@Override
	public boolean updateDiscrepancy(DiscrepancyAllocation discrepancyAllocation) {
		DiscrepancyAllocation discrepancyIDFromDB = processingRoomJpaDao
				.getDescripancyAllocationById(discrepancyAllocation.getId());
		discrepancyAllocation.setInsertBy(discrepancyIDFromDB.getInsertBy());
		discrepancyAllocation.setUpdateBy(discrepancyIDFromDB.getUpdateBy());
		discrepancyAllocation.setInsertTime(discrepancyIDFromDB.getInsertTime());
		discrepancyAllocation.setDiscrepancyId(discrepancyIDFromDB.getDiscrepancyId());
		discrepancyAllocation.setIcmcId(discrepancyIDFromDB.getIcmcId());
		discrepancyAllocation.setPrintYear(discrepancyIDFromDB.getPrintYear());
		discrepancyAllocation.setDateOnShrinkWrap(discrepancyIDFromDB.getDateOnShrinkWrap());
		discrepancyAllocation.setNormalOrSuspense(discrepancyIDFromDB.getNormalOrSuspense());
		discrepancyAllocation.setMachineNumber(discrepancyAllocation.getMachineNumber());
		discrepancyAllocation.setDiscrepancyDate(discrepancyIDFromDB.getDiscrepancyDate());
		discrepancyAllocation.setSolId(discrepancyAllocation.getSolId());
		discrepancyAllocation.setBranch(discrepancyAllocation.getBranch());
		discrepancyAllocation.setFilepath(discrepancyIDFromDB.getFilepath());
		discrepancyAllocation.setAccountTellerCam(discrepancyAllocation.getAccountTellerCam());
		discrepancyAllocation.setCustomerName(discrepancyAllocation.getCustomerName());
		discrepancyAllocation.setAccountNumber(discrepancyAllocation.getAccountNumber());
		discrepancyAllocation.setStatus(discrepancyIDFromDB.getStatus());
		return processingRoomJpaDao.updateDiscrepancy(discrepancyAllocation);
	}

	@Override
	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getMutilatedNotesSummary(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		return processingRoomJpaDao.getMutilatedNotesSummary(icmcId, sDate, eDate, normalOrSuspense);
	}

	@Override
	public boolean addMachineMaintenance(MachineMaintenance machineMaintainance) {
		return processingRoomJpaDao.addMachineMaintenance(machineMaintainance);
	}

	@Override
	public List<MachineMaintenance> viewMachineMaintenance(BigInteger icmcId) {
		return processingRoomJpaDao.viewMachineMaintenance(icmcId);
	}

	@Override
	public MachineMaintenance getMachineMaintenanceById(long id) {
		return processingRoomJpaDao.getMachineMaintenanceById(id);
	}

	@Override
	public boolean updateMachineMaintenanceStatus(MachineMaintenance machineMaintenance) {
		return processingRoomJpaDao.updateMachineMaintenanceStatus(machineMaintenance);
	}

	/*
	 * @Override public List<AuditorProcess>
	 * processRecordForAuditorIndent(AuditorProcess process, User user) {
	 * 
	 * List<BinMaster> binMasterList = new ArrayList<BinMaster>();
	 * List<BinTransaction> binList = new ArrayList<>(); List<BinTransaction>
	 * binTxs = new ArrayList<>(); List<AuditorProcess> processList = new
	 * ArrayList<>(); List<BoxMaster> boxMasterList = new ArrayList<>();
	 * 
	 * BigDecimal bundleFromTxn = BigDecimal.ZERO;
	 * 
	 * if (process.getBinCategoryType() == BinCategoryType.BIN) {
	 * List<BinCapacityDenomination> capacityList = cashReceiptService
	 * .getMaxBundleCapacity(process.getDenomination(),
	 * process.getCurrencyType());
	 * 
	 * binMasterList = getPriorityBinListByType(user,
	 * process.getCurrencyType());
	 * 
	 * binList = getBinTxnListByDenomForAuditorProcess(process, user);
	 * 
	 * binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList,
	 * process.getBundle(), true, capacityList, process.getCurrencyType(),
	 * process.getCashSource());
	 * 
	 * for (BinTransaction binTxn : binTxs) { bundleFromTxn =
	 * bundleFromTxn.add(binTxn.getCurrentBundle()); } if
	 * (bundleFromTxn.compareTo(process.getBundle()) == 0) { processList =
	 * UtilityJpa.getProcessBeanForAuditor(process, binTxs, user);
	 * addTransactions(user, binTxs, process.getCurrencyType()); } else { throw
	 * new BaseGuiException("Space is not available in BIN for " +
	 * process.getCurrencyType() + " Category and " + process.getDenomination()
	 * + " Denomination"); } }
	 * 
	 * if (process.getBinCategoryType() == BinCategoryType.BOX) { BoxMaster
	 * boxMaster = new BoxMaster(); boxMaster.setIcmcId(user.getIcmcId());
	 * boxMaster.setDenomination(process.getDenomination());
	 * boxMaster.setCurrencyType(process.getCurrencyType()); boxMasterList =
	 * cashReceiptService.getBoxFromBoxMaster(boxMaster); binList =
	 * getBinTxnListByDenomForAuditorProcess(process, user);
	 * 
	 * binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList,
	 * process.getBundle(), true, process.getCurrencyType(),
	 * process.getCashSource());
	 * 
	 * for (BinTransaction binTxn : binTxs) { bundleFromTxn =
	 * bundleFromTxn.add(binTxn.getCurrentBundle()); } if
	 * (bundleFromTxn.compareTo(process.getBundle()) == 0) { processList =
	 * UtilityJpa.getProcessBeanForAuditor(process, binTxs, user);
	 * addInTransactionsForBox(user, binTxs); } else { throw new
	 * BaseGuiException("Space is not available in BOX for " +
	 * process.getCurrencyType() + " Category and " + process.getDenomination()
	 * + " Denomination"); } }
	 * 
	 * List<AuditorIndent> pendingBundleListFromAuditorIndent =
	 * processingRoomJpaDao .getPendingBundleFromAuditorIndent(user.getIcmcId(),
	 * process.getDenomination());
	 * 
	 * List<AuditorIndent> eligiblePendingBundleList = UtilityJpa
	 * .getEligibleBundleListForAuditor(pendingBundleListFromAuditorIndent,
	 * process.getBundle(), user);
	 * 
	 * if (eligiblePendingBundleList == null ||
	 * eligiblePendingBundleList.isEmpty()) { throw new BaseGuiException(
	 * "Required Bundle is Not available for Denomination:" +
	 * process.getDenomination()); }
	 * 
	 * for (AuditorIndent auditorIndent : eligiblePendingBundleList) {
	 * updatePendingBundleInAuditorIndent(auditorIndent); }
	 * 
	 * addProcessForAuditor(processList);
	 * 
	 * return processList; }
	 */

	@Override
	public List<AuditorProcess> processRecordForAuditorIndent(AuditorProcess process, User user) {

		List<BinMaster> binMasterList = new ArrayList<BinMaster>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();
		List<AuditorProcess> processList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();

		BigDecimal bundleFromTxn = BigDecimal.ZERO;

		List<MachineAllocation> pendingBundleListFromMachineAllocation = this
				.getBundleFromMachineAllocation(user.getIcmcId(), process.getDenomination());

		List<MachineAllocation> eligiblePendingBundleList = UtilityJpa.getEligibleBundleListForMachineAllocation(
				pendingBundleListFromMachineAllocation, process.getBundle(), user);

		if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
			throw new BaseGuiException(
					"Required Bundle is Not available for Denomination:" + process.getDenomination());
		}

		for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
			updatePendingBundleInMachineAllocation(machineAllocation);
			// process.setMachineId(machineAllocation.getId());
		}

		// End Of Machine Allocation Code

		if (process.getBinCategoryType() == BinCategoryType.BIN) {
			List<BinCapacityDenomination> capacityList = cashReceiptService
					.getMaxBundleCapacity(process.getDenomination(), process.getCurrencyType());

			binMasterList = getPriorityBinListByType(user, process.getCurrencyType());

			binList = getBinTxnListByDenomForAuditorProcess(process, user);

			binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, process.getBundle(), true,
					capacityList, process.getCurrencyType(), process.getCashSource());

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(process.getBundle()) == 0) {
				processList = UtilityJpa.getProcessBeanForAuditor(process, binTxs, user);
				addTransactions(user, binTxs, process.getCurrencyType());
			} else {
				throw new BaseGuiException("Space is not available in BIN for " + process.getCurrencyType()
						+ " Category and " + process.getDenomination() + " Denomination");
			}
		}

		if (process.getBinCategoryType() == BinCategoryType.BOX) {
			BoxMaster boxMaster = new BoxMaster();
			boxMaster.setIcmcId(user.getIcmcId());
			boxMaster.setDenomination(process.getDenomination());
			boxMaster.setCurrencyType(process.getCurrencyType());
			boxMasterList = cashReceiptService.getBoxFromBoxMaster(boxMaster);
			binList = getBinTxnListByDenomForAuditorProcess(process, user);

			binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, process.getBundle(), true,
					process.getCurrencyType(), process.getCashSource());

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(process.getBundle()) == 0) {
				processList = UtilityJpa.getProcessBeanForAuditor(process, binTxs, user);
				addInTransactionsForBox(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available in BOX for " + process.getCurrencyType()
						+ " Category and " + process.getDenomination() + " Denomination");
			}
		}

		addProcessForAuditor(processList);

		return processList;
	}

	@Override
	public boolean createProcessForAuditor(List<AuditorProcess> process) {
		return processingRoomJpaDao.createProcessForAuditor(process);
	}

	@Override
	public List<Tuple> getPendingBundleForAuditor(BigInteger icmcId) {
		return processingRoomJpaDao.getPendingBundleForAuditor(icmcId);
	}

	@Override
	public List<AuditorIndent> getPendingBundleFromAuditorIndent(BigInteger icmcId, Integer denomination) {
		return processingRoomJpaDao.getPendingBundleFromAuditorIndent(icmcId, denomination);
	}

	@Override
	public boolean updatePendingBundleInAuditorIndent(AuditorIndent auditorIndent) {
		return processingRoomJpaDao.updatePendingBundleInAuditorIndent(auditorIndent);
	}

	@Override
	public BinTransaction getDataFromBinTransaction(String bin, BigInteger icmcId, Integer denomination,
			CurrencyType currencyType) {
		return processingRoomJpaDao.getDataFromBinTransaction(bin, icmcId, denomination, currencyType);
	}

	@Override
	public boolean updateProcessingRoomStatus(Process process) {
		return processingRoomJpaDao.updateProcessingRoomStatus(process);
	}

	@Override
	public boolean updatePendingForCancel(long id, BigDecimal pendingBundle) {
		return processingRoomJpaDao.updatePendingForCancel(id, pendingBundle);
	}

	@Override
	public String cancelProcessedDataFromProcessingRoom(long id, BigDecimal bundleFromUI, int denomination,
			String binNumber, CurrencyType type, long machineId, User user, Process process) {

		BinTransaction binTxn = processingRoomJpaDao.getDataFromBinTransaction(binNumber, user.getIcmcId(),
				denomination, type);
		BigDecimal bundleFromBinTxn = binTxn.getReceiveBundle();
		BigDecimal bundleFromPage = bundleFromUI;
		BigDecimal balanceBundle = bundleFromBinTxn.subtract(bundleFromPage);

		BinTransaction binTransaction = new BinTransaction();

		Calendar now = Calendar.getInstance();

		BinMaster binMaster = new BinMaster();
		binMaster.setIcmcId(user.getIcmcId());
		binMaster.setBinNumber(binNumber);

		if (balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
			binTransaction.setReceiveBundle(BigDecimal.ZERO);
			binTransaction.setPendingBundleRequest(BigDecimal.ZERO);
			binTransaction.setStatus(BinStatus.EMPTY);
			binTransaction.setCashSource(null);
			binTransaction.setVerified(YesNo.NULL);
			binTransaction.setUpdateBy(user.getId());
			binTransaction.setInsertBy(user.getId());
			binTransaction.setUpdateTime(now);
			binTransaction.setInsertTime(binTxn.getInsertTime());
			binTransaction.setBinNumber(binNumber);
			binTransaction.setDenomination(denomination);
			binTransaction.setMaxCapacity(binTxn.getMaxCapacity());
			binTransaction.setBinCategoryType(binTxn.getBinCategoryType());
			binTransaction.setBinType(binTxn.getBinType());
			binTransaction.setCashType(binTxn.getCashType());
			binTransaction.setRbiOrderNo(binTxn.getRbiOrderNo());
			binTransaction.setIcmcId(user.getIcmcId());
			binTransaction.setId(binTxn.getId());
			boolean count = this.updateBinTxn(binTransaction);
			if (count) {
				processingRoomJpaDao.updateBinMaster(binMaster);
				processingRoomJpaDao.updateProcessingRoomStatus(process);
				processingRoomJpaDao.updatePendingForCancel(machineId, bundleFromUI);
			}
		} else if (balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
			binTxn.setReceiveBundle(binTxn.getReceiveBundle().subtract(bundleFromPage));
			this.updateBinTxn(binTxn);
			processingRoomJpaDao.updateProcessingRoomStatus(process);
			processingRoomJpaDao.updatePendingForCancel(machineId, bundleFromUI);
		} else {
			throw new BaseGuiException("withdrawal bin can't cancel available bundle is " + bundleFromBinTxn);
		}
		return "SUCCESS";
	}

	@Override
	public boolean insertSuspenseOpeningBalance(SuspenseOpeningBalance suspenseOpeningBalance) {
		return processingRoomJpaDao.insertSuspenseOpeningBalance(suspenseOpeningBalance);
	}

	@Override
	public List<SuspenseOpeningBalance> getSuspenseOpeningBalance(BigInteger icmcId) {
		return processingRoomJpaDao.getSuspenseOpeningBalance(icmcId);
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegister(BigInteger icmcId) {
		return processingRoomJpaDao.openingBalanceForSuspenseRegister(icmcId);
	}

	@Override
	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getTotalNotesForWithdrawal(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.geTotalNotesForDeposit(icmcId, sDate, eDate);
	}

	@Override
	public boolean branchHistory(List<History> historyList) {
		return processingRoomJpaDao.branchHistory(historyList);
	}

	@Override
	public boolean updateCurrentVersionStatus(SuspenseOpeningBalance suspenseOpeningBalance) {
		return processingRoomJpaDao.updateCurrentVersionStatus(suspenseOpeningBalance);
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDate(BigInteger icmcId, Calendar sDate,
			Calendar eDate) {
		return processingRoomJpaDao.openingBalanceForSuspenseRegisterPreviousDate(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId) {
		return processingRoomJpaDao.getTotalNotesForWithdrawal(icmcId);
	}

	@Override
	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId) {
		return processingRoomJpaDao.geTotalNotesForDeposit(icmcId);
	}

	@Override
	public boolean saveDataInBinRegister(BinRegister binRegister) {
		return processingRoomJpaDao.saveDataInBinRegister(binRegister);
	}

	@Override
	public boolean deleteDiscrepancy(long id, BigInteger icmcId) {
		return processingRoomJpaDao.deleteDiscrepancy(id, icmcId);
	}

	@Override
	public boolean deleteDiscrepancywithooutallocation(long id, BigInteger icmcId) {
		return processingRoomJpaDao.deleteDiscrepancywithooutallocation(id, icmcId);
	}

	@Override
	public boolean insertFullValueMutilated(Mutilated mutilated) {
		return processingRoomJpaDao.insertFullValueMutilated(mutilated);
	}

	@Override
	public boolean UploadChestSlip(List<ChestMaster> list, ChestMaster chestSlip) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = processingRoomJpaDao.UploadChestSlip(list, chestSlip);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;

	}

	@Override
	public List<MachineAllocation> getBundleByCashSource(BigInteger icmcId, String isMachineOrManual,
			Integer denomination, CashSource cashSource, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getBundleByCashSource(icmcId, isMachineOrManual, denomination, cashSource, sDate,
				eDate);
	}

	@Override
	public List<MachineAllocation> getBundleFromMachineAllocation(BigInteger icmcId, Integer denomination) {
		return processingRoomJpaDao.getBundleFromMachineAllocation(icmcId, denomination);
	}

	@Override
	public List<Tuple> getKeySetDetail(String custodian, BigInteger icmcId) {
		return processingRoomJpaDao.getKeySetDetail(custodian, icmcId);
	}

	@Override
	public List<CustodianKeySet> getAssignVaultCustodian(BigInteger icmcId) {
		return processingRoomJpaDao.getAssignVaultCustodian(icmcId);
	}

	@Override
	public AssignVaultCustodian getHandoveredChargUserId(BigInteger icmcId, String custodian) {
		return processingRoomJpaDao.getHandoveredChargUserId(icmcId, custodian);
	}

	@Override
	public AssignVaultCustodian getHandoveredChargByHandOverId(BigInteger icmcId, String userId) {
		return processingRoomJpaDao.getHandoveredChargByHandOverId(icmcId, userId);
	}

	@Override
	public User isValidUser(String username, BigInteger icmcId) {
		return processingRoomJpaDao.isValidUser(username, icmcId);
	}

	@Override
	public boolean InsertByDAteSuspeseOpeningBalance(long id, BigInteger icmcId,
			BigDecimal openingBalanceOfDenomination_10, BigDecimal openingBalanceOfDenomination_20,
			BigDecimal openingBalanceOfDenomination_50, BigDecimal openingBalanceOfDenomination_100,
			BigDecimal openingBalanceOfDenomination_200, BigDecimal openingBalanceOfDenomination_500,
			BigDecimal openingBalanceOfDenomination_2000) {
		return processingRoomJpaDao.InsertByDAteSuspeseOpeningBalance(id, icmcId, openingBalanceOfDenomination_10,
				openingBalanceOfDenomination_20, openingBalanceOfDenomination_50, openingBalanceOfDenomination_100,
				openingBalanceOfDenomination_200, openingBalanceOfDenomination_500, openingBalanceOfDenomination_2000);
	}

	@Override
	public boolean insertSuspenseOpeningBalanceLink(SuspenseOpeningBalance suspenseOpeningBalance) {
		return processingRoomJpaDao.insertSuspenseOpeningBalance(suspenseOpeningBalance);
	}

	@Override
	public boolean updateSuspenseBalanceFromLink(long id, BigDecimal replenishment_2000, BigDecimal replenishment_500,
			BigDecimal replenishment_200, BigDecimal replenishment_100, BigDecimal replenishment_50,
			BigDecimal replenishment_20, BigDecimal replenishment_10, BigDecimal depletion_2000,
			BigDecimal depletion_500, BigDecimal depletion_200, BigDecimal depletion_100, BigDecimal depletion_50,
			BigDecimal depletion_20, BigDecimal depletion_10, String srNumber) {
		return processingRoomJpaDao.updateSuspenseBalanceFromLink(id, replenishment_2000, replenishment_500,
				replenishment_200, replenishment_100, replenishment_50, replenishment_20, replenishment_10,
				depletion_2000, depletion_500, depletion_200, depletion_100, depletion_50, depletion_20, depletion_10,
				srNumber);
	}

	@Override
	public boolean updateSuspenseBalance(long id, BigDecimal deposit_2000, BigDecimal deposit_500,
			BigDecimal deposit_200, BigDecimal deposit_100, BigDecimal deposit_50, BigDecimal deposit_20,
			BigDecimal deposit_10, BigDecimal withdrawal_2000, BigDecimal withdrawal_500, BigDecimal withdrawal_200,
			BigDecimal withdrawal_100, BigDecimal withdrawal_50, BigDecimal withdrawal_20, BigDecimal withdrawal_10) {
		return processingRoomJpaDao.updateSuspenseBalance(id, deposit_2000, deposit_500, deposit_200, deposit_100,
				deposit_50, deposit_20, deposit_10, withdrawal_2000, withdrawal_500, withdrawal_200, withdrawal_100,
				withdrawal_50, withdrawal_20, withdrawal_10);
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDateByDesc(BigInteger icmcId) {
		return processingRoomJpaDao.openingBalanceForSuspenseRegisterPreviousDateByDesc(icmcId);
	}

	@Override
	public List<Mutilated> getMutilatedValueDetails(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return processingRoomJpaDao.getMutilatedValueDetails(icmcId, sDate, eDate);
	}

	@Override
	public boolean processMutilatedRequest(Long id) {
		return processingRoomJpaDao.processMutilatedRequest(id);
	}

	private boolean getUpdateCashReceiveForIndentRequest(Indent indent) {
		Calendar sDate = UtilityJpa.getStartDate();
		Calendar eDate = UtilityJpa.getEndDate();
		if (CashSource.DSB.equals(indent.getCashSource())) {
			List<DSB> dsbs = processingRoomJpaDao.getDSB(indent, sDate, eDate);
			BigDecimal totalbundle = indent.getBundle();
			for (DSB dsb : dsbs) {
				totalbundle = totalbundle.subtract(dsb.getBundle());
				do {
					dsb.setIsIndent(true);
					processingRoomJpaDao.updateDSB(dsb);
				} while (totalbundle.compareTo(BigDecimal.ZERO) > 0);
				if (totalbundle.compareTo(BigDecimal.ZERO) < 0) {
					break;
				}

			}
		} else if (CashSource.OTHERBANK.equals(indent.getCashSource())) {
			List<BankReceipt> bankReceipts = processingRoomJpaDao.getBankReceipt(indent, sDate, eDate);
			BigDecimal totalbundle = indent.getBundle();
			for (BankReceipt receipt : bankReceipts) {
				totalbundle = totalbundle.subtract(receipt.getBundle());
				do {
					receipt.setIsIndent(true);
					processingRoomJpaDao.updateBankReceipt(receipt);
				} while (totalbundle.compareTo(BigDecimal.ZERO) > 0);
				if (totalbundle.compareTo(BigDecimal.ZERO) < 0) {
					break;
				}
			}
		} else if (CashSource.DIVERSION.equals(indent.getCashSource())) {
			List<DiversionIRV> diversionIRVs = processingRoomJpaDao.getDiversionIRV(indent, sDate, eDate);
			BigDecimal totalbundle = indent.getBundle();
			for (DiversionIRV diversionIRV : diversionIRVs) {
				totalbundle = totalbundle.subtract(diversionIRV.getBundle());
				do {
					diversionIRV.setIsIndent(true);
					processingRoomJpaDao.updateDiversionIRV(diversionIRV);
				} while (totalbundle.compareTo(BigDecimal.ZERO) > 0);
				if (totalbundle.compareTo(BigDecimal.ZERO) < 0) {
					break;
				}
			}
		}
		return true;
	}

	@Override
	@Transactional
	public boolean updateCashReceiveForIndentRequest(List<Indent> eligibleIndentRequestList) {
		boolean isUpdate = false;
		for (Indent indent : eligibleIndentRequestList) {
			isUpdate = getUpdateCashReceiveForIndentRequest(indent);
		}
		return isUpdate;
	}

}
