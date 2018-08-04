/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

	private static final Logger LOG = LoggerFactory.getLogger(CashReceiptServiceImpl.class);

	@Autowired
	ProcessingRoomDaoImpl processingRoomDao;

	@Autowired
	ProcessingRoomJpaDaoImpl processingRoomJpaDao;

	@Autowired
	QRCodeGen qrCodeGen;

	@Autowired
	CashReceiptService cashReceiptService;

	@Override
	public String getBinForIndentRequest(String denomination, String bundle) {
		LOG.info("Bin For Indent Request");
		String binNumber = processingRoomDao.getBinForIndentRequest(denomination, bundle);
		LOG.info("Bin For Indent Request");
		return binNumber;
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
		List<Indent> indentList = processingRoomJpaDao.viewBinDetail(denomination, bin, icmcId);
		return indentList;
	}

	@Override
	public List<DiscrepancyAllocation> getDiscrepancyByDate(BigInteger icmcId, Date sDate, Date tDate,
			String normalOrSuspense) {
		List<DiscrepancyAllocation> discrepancyList = processingRoomJpaDao.getDiscrepancyByDate(icmcId, sDate, tDate,
				normalOrSuspense);
		return discrepancyList;
	}

	@Override
	public Indent getUpdateIndentOtherBankRequest(BankReceipt otherBankReceiptdb, BigInteger icmcId) {
		Indent indentList = processingRoomJpaDao.viewUpdateIndentOtherBankRequest(otherBankReceiptdb, icmcId);
		return indentList;
	}

	@Override
	public Indent getUpdateIndentIVRRequest(DiversionIRV diversionIRV, BigInteger icmcId) {
		Indent indentList = processingRoomJpaDao.viewUpdateIndentIVRRequest(diversionIRV, icmcId);
		return indentList;
	}

	@Override
	public List<BinTransaction> getBinNumListForIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType) {
		List<BinTransaction> binFromTxnList = processingRoomJpaDao.getBinNumListForIndent(denomination, bundle, icmcId,
				cashSource, binCategoryType);
		return binFromTxnList;
	}

	@Override
	public List<Indent> getBinFromIndent(int denomination) {
		List<Indent> binListFromIndent = processingRoomJpaDao.getBinFromIndent(denomination);
		return binListFromIndent;
	}

	@Override
	public Indent getUpdateIndentRequest(DSB dsbdb, BigInteger icmcId) {
		Indent indentList = processingRoomJpaDao.viewUpdateIndentRequest(dsbdb, icmcId);
		return indentList;
	}

	@Override
	public Indent getIndentById(long id) {
		Indent indent = processingRoomJpaDao.getIndentById(id);
		return indent;
	}

	@Override
	public boolean updateIndentRequest(Indent indent) {
		boolean isSaved = processingRoomJpaDao.updateIndentRequest(indent);
		return isSaved;
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
		List<Indent> indentListForMachineAllocation = processingRoomJpaDao
				.getAggregatedIndentRequestForMachineAllocation(icmcId, cashSource, sDate, eDate);
		return indentListForMachineAllocation;
	}

	@Override

	public Indent getIndentDataById(long id, BigInteger icmcId) {
		Indent indent = processingRoomJpaDao.getIndentDataById(id, icmcId);
		return indent;
	}

	@Override
	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId) {
		BinTransaction binTxn = processingRoomJpaDao.getBinFromTransaction(bin, icmcId);
		return binTxn;
	}

	@Override
	public boolean updateIndentStatus(Indent indent) {
		boolean IsSaved = processingRoomJpaDao.updateIndentRequest(indent);
		return IsSaved;
	}

	@Override
	@Transactional
	public boolean processIndentRequest(String bin, BigDecimal bundle, User user) {

		Indent indent = new Indent();// this.getIndentDataById(id,
		indent.setUpdateTime(Calendar.getInstance()); // user.getIcmcId());
		indent.setBin(bin);
		indent.setBundle(bundle);
		indent.setIcmcId(user.getIcmcId());
		indent.setUpdateTime(Calendar.getInstance());
		boolean isIndentUpdate = false;
		BinTransaction txnBean = this.getBinFromTransaction(bin.trim(), user.getIcmcId());

		if (indent != null && txnBean != null && txnBean.getReceiveBundle() != null
				&& txnBean.getReceiveBundle().compareTo(BigDecimal.ZERO) > 0 && indent.getBundle() != null
				&& indent.getBundle().compareTo(BigDecimal.ZERO) > 0) {

			BigDecimal balanceBundle = txnBean.getReceiveBundle().subtract(indent.getBundle());

			LOG.info("processing available balanceBundle " + balanceBundle);
			// indent.setStatus(OtherStatus.PROCESSED);// 0 means ready for
			// machine
			// allocation
			isIndentUpdate = this.updateIndentStatus(indent);
			LOG.info("update indentStatus " + isIndentUpdate);
			if (isIndentUpdate && balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
				// txnBean.setRcvBundle(BigDecimal.ZERO);
				txnBean.setReceiveBundle(BigDecimal.ZERO);
				txnBean.setPendingBundleRequest(BigDecimal.ZERO);
				txnBean.setStatus(BinStatus.EMPTY);
				txnBean.setCashSource(null);
				txnBean.setVerified(YesNo.NULL);
				txnBean.setUpdateBy(user.getId());
				txnBean.setUpdateTime(Calendar.getInstance());
				// update time
				// int count = this.deleteDataFromBinTxn(txnBean);
				// call merge
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
		int count = processingRoomJpaDao.deleteDataFromBinTxn(txnBean);
		return count;
	}

	@Override
	public boolean updateBinMaster(BinMaster binMaster) {
		boolean isSaved = processingRoomJpaDao.updateBinMaster(binMaster);
		return isSaved;
	}

	@Override
	@Transactional
	public boolean updateBinTxn(BinTransaction binTransaction) {
		if (binTransaction.getPendingBundleRequest().compareTo(BigDecimal.ZERO) < 0
				|| binTransaction.getPendingBundleRequest().compareTo(binTransaction.getReceiveBundle()) > 0) {
			throw new BaseGuiException("Can not update vault: icmc id " + binTransaction.getIcmcId() + " and bin "
					+ binTransaction.getBinNumber());
		}
		boolean isSaved = processingRoomJpaDao.updateBinTxn(binTransaction);
		return isSaved;
	}

	@Override
	public boolean insertInMachineAllocation(MachineAllocation machineAllocation) {
		boolean isSaved = processingRoomJpaDao.insertInMachineAllocation(machineAllocation);
		return isSaved;
	}

	/*
	 * @Override public Indent getBundleFromIndent(String bin, CashSource
	 * cashSource, BigInteger icmcId) { Indent indent =
	 * processingRoomJpaDao.getBundleFromIndent(bin, cashSource, icmcId); return
	 * indent; }
	 */

	@Override
	public boolean updateBundleInIndent(Indent indent) {
		boolean isSaved = processingRoomJpaDao.updateBundleInIndent(indent);
		return isSaved;
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
	public List<Process> getProcessedDataList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Process> processList = processingRoomJpaDao.getProcessedDataList(icmcId, sDate, eDate);
		return processList;
	}

	@Override
	public List<BinMaster> getPriorityBinListByType(BinMaster binMaster) {
		List<BinMaster> priorityBinList = processingRoomJpaDao.getPriorityBinListByType(binMaster);
		return priorityBinList;
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx) {
		List<BinTransaction> binListFromTxn = processingRoomJpaDao.getBinTxnListByDenom(binTx);
		return binListFromTxn;
	}

	@Override
	public boolean updateBinMasterForProcess(BinMaster binMaster) {
		boolean isSaved = processingRoomJpaDao.updateBinMasterForProcess(binMaster);
		return isSaved;
	}

	@Override
	public boolean insertInBinTxn(BinTransaction binTransaction) {
		boolean isSaved = processingRoomJpaDao.insertInBinTxn(binTransaction);
		return isSaved;
	}

	@Override
	public boolean updateInBinTxn(BinTransaction binTransaction) {
		boolean isSaved = processingRoomJpaDao.updateInBinTxn(binTransaction);
		return isSaved;
	}

	@Override
	public boolean createProcess(List<Process> process) {
		boolean isSaved = processingRoomJpaDao.createProcess(process);
		return isSaved;
	}

	@Override
	public List<Process> processRecordForMachine(Process process, User user) {

		List<BinMaster> binMasterList = new ArrayList<BinMaster>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();
		List<Process> processList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();

		BigDecimal bundleFromTxn = BigDecimal.ZERO;

		// Machine Allocation Table Code

		String machineOrManual = "";
		if (process.getProcessAction() == ProcessAction.MACHINE) {
			machineOrManual = "NO";
		}
		if (process.getProcessAction() == ProcessAction.MANUAL) {
			machineOrManual = "YES";
		}

		/*
		 * List<MachineAllocation> pendingBundleListFromMachineAllocation =
		 * processingRoomJpaDao
		 * .getPendingBundleFromMachineAllocation(user.getIcmcId(),
		 * process.getDenomination(), machineOrManual);
		 * 
		 */
		Calendar sDate = Calendar.getInstance();
		Calendar eDate = Calendar.getInstance();

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);
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
		List<BinMaster> binMasterList = this.getPriorityBinListByType(master);
		return binMasterList;
	}

	private List<BinTransaction> getBinTxnListByDenom(Process process, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(process.getBundle());
		binTxTemp.setDenomination(process.getDenomination());
		binTxTemp.setBinType(process.getCurrencyType());
		binTxTemp.setBinCategoryType(process.getBinCategoryType());
		List<BinTransaction> binList = this.getBinTxnListByDenom(binTxTemp);
		return binList;
	}

	private List<BinTransaction> getBinTxnListByDenomForAuditorProcess(AuditorProcess process, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(process.getBundle());
		binTxTemp.setDenomination(process.getDenomination());
		binTxTemp.setBinType(process.getCurrencyType());
		binTxTemp.setBinCategoryType(process.getBinCategoryType());
		List<BinTransaction> binList = this.getBinTxnListByDenom(binTxTemp);
		return binList;
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
		String path = getPath(filepath);
		return path;
	}

	public String getQrCodeAuditor(AuditorProcess process) {
		String filepath = qrCodeGen.generateProcessingRoomQRAuditor(process);
		String path = getPath(filepath);
		return path;
	}

	public String getQrCodeForCRA(ProcessBundleForCRAPayment process) {
		String filepath = qrCodeGen.generateCRAPaymentProcessingRoomQR(process);
		String path = getPath(filepath);
		return path;
	}

	@Override
	public boolean updatePrcocessStatus(Process process) {
		boolean IsSaved = processingRoomJpaDao.updateProcessStatus(process);
		return IsSaved;
	}

	@Override
	public List<Tuple> indentSummary(BigInteger icmcId, CashSource cashSource) {
		List<Tuple> list = processingRoomJpaDao.indentSummary(icmcId, cashSource);
		return list;
	}

	@Override
	public boolean UploadDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet) {
		boolean isAllSuccess = false;
		CustodianKeySet custodianKeySet = new CustodianKeySet();
		try {
			isAllSuccess = processingRoomJpaDao.UploadDefineKeySet(list, defineKeySet);
			for (DefineKeySet list1 : list) {
				custodianKeySet.setCustodian(list1.getCustodian());
				custodianKeySet.setIcmcId(defineKeySet.getIcmcId());
				custodianKeySet.setInsertBy(defineKeySet.getInsertBy());
				custodianKeySet.setUpdateBy(defineKeySet.getUpdateBy());
				custodianKeySet.setInsertTime(defineKeySet.getInsertTime());
				custodianKeySet.setUpdateTime(defineKeySet.getUpdateTime());
			}
			processingRoomJpaDao.insertCosutodianName(custodianKeySet);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;

	}

	@Override
	public List<CustodianKeySet> getDefineKeySet(BigInteger icmcId) {

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
		List<MachineAllocation> machineList = processingRoomJpaDao.getMachineAllocationRecordForProcessing(icmcId,
				sDate, eDate);
		return machineList;
	}

	@Override
	public boolean saveAssignVaultCustodian(AssignVaultCustodian assignVaultCustodian) {

		return processingRoomJpaDao.saveAssignVaultCustodian(assignVaultCustodian);
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
			da.setFilepath(discrepancy.getFilepath());
			da.setAccountTellerCam(discrepancy.getAccountTellerCam());
			da.setCustomerName(discrepancy.getCustomerName());
			da.setAccountNumber(discrepancy.getAccountNumber());
		}
		return processingRoomJpaDao.saveDiscrepancy(discrepancy);
	}

	@Override
	public List<DiscrepancyAllocation> getDiscrepancy(BigInteger icmcId, String normalOrSuspense) {
		List<DiscrepancyAllocation> discrepancyList = processingRoomJpaDao.getDiscrepancy(icmcId, normalOrSuspense);
		return discrepancyList;
	}

	@Override
	public List<FreshFromRBI> getFreshFromRBIRecord(User user) {
		List<FreshFromRBI> potdarList = processingRoomJpaDao.getFreshFromRBIRecord(user);
		return potdarList;
	}

	@Override
	public boolean updateBinTransaction(BinTransaction binTransaction) {
		boolean isUpdate = processingRoomJpaDao.updateBinTransaction(binTransaction);
		return isUpdate;
	}

	@Override
	public List<Tuple> indentSummaryForFresh(BigInteger icmcId, CashSource cashSource) {
		List<Tuple> indentSummaryForFresh = processingRoomJpaDao.indentSummaryForFresh(icmcId, cashSource);
		return indentSummaryForFresh;
	}

	@Override
	public List<Machine> getMachineNumber(BigInteger icmcId) {
		List<Machine> machineList = processingRoomJpaDao.getMachineNumber(icmcId);
		return machineList;
	}

	@Override
	public List<DefineKeySet> getKeyNumber(DefineKeySet defineKeySet) {
		List<DefineKeySet> keyNumberList = processingRoomJpaDao.getKeyNumber(defineKeySet);
		return keyNumberList;
	}

	@Override
	public List<DefineKeySet> getLocationOfLock(DefineKeySet defineKeySet) {
		List<DefineKeySet> locationOfLockList = processingRoomJpaDao.getLocationOfLock(defineKeySet);
		return locationOfLockList;
	}

	@Override
	public List<BranchReceipt> getBinNumListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType) {
		List<BranchReceipt> periorityBinList=new ArrayList<>(); 
		
		List<BranchReceipt> returnBinFromBranchReceiptList = processingRoomJpaDao
				.getRetunBinListForIndentFromBranchReceipt(denomination, bundle, icmcId, cashSource, binCategoryType);
		
		List<BranchReceipt> branchUploadBinFromBranchReceiptList = processingRoomJpaDao
				.getBranchUploadBinListForIndentFromBranchReceipt(denomination, bundle, icmcId, cashSource, binCategoryType);
		
		List<BranchReceipt> insertBinListFromBranchReceiptList = processingRoomJpaDao
				.getInsertBinListForIndentFromBranchReceipt(denomination, bundle, icmcId, cashSource, binCategoryType);
		
		if(returnBinFromBranchReceiptList.size() !=0){
		periorityBinList.addAll(returnBinFromBranchReceiptList);}
		
		if(branchUploadBinFromBranchReceiptList.size() !=0){
			periorityBinList.addAll(branchUploadBinFromBranchReceiptList);}
		
		if(insertBinListFromBranchReceiptList.size() !=0){
		periorityBinList.addAll(insertBinListFromBranchReceiptList);}
		
		return periorityBinList;
	}

	@Override
	public boolean saveCRAPaymentProcessRecord(ProcessBundleForCRAPayment processBundleForCRAPayment) {
		boolean isSaved = processingRoomJpaDao.saveCRAPaymentProcessRecord(processBundleForCRAPayment);
		return isSaved;
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
		List<Tuple> list = processingRoomJpaDao.getForwardBundleTotalForCRAPayment(icmcId);
		return list;
	}

	@Override
	public ForwardBundleForCRAPayment getBundleFromForwardCRA(ForwardBundleForCRAPayment forwardBundle) {
		ForwardBundleForCRAPayment denomination = processingRoomJpaDao.getBundleFromForwardCRA(forwardBundle);
		return denomination;
	}

	@Override
	public boolean insertBranchIndentRequest(List<BranchReceipt> eligibleBranchIndentRequestList) {
		boolean isSaved = processingRoomJpaDao.insertBranchIndentRequest(eligibleBranchIndentRequestList);
		return isSaved;
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
		// TODO Auto-generated method stub
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
		List<Tuple> indentSummaryForFreshFromBinTxn = processingRoomJpaDao.indentSummaryForFreshFromBinTxn(icmcId,
				cashSource);
		return indentSummaryForFreshFromBinTxn;
	}

	@Override
	public List<BinTransaction> getBinNumListForFreshIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType, String rbiOrderNo) {
		List<BinTransaction> binTxnListForFresh = processingRoomJpaDao.getBinNumListForFreshIndent(denomination, bundle,
				icmcId, cashSource, binCategoryType, rbiOrderNo);
		return binTxnListForFresh;
	}

	@Override
	public List<CRAAllocation> getDataFromCRAAllocationForProcessing(BigInteger icmcId) {
		// TODO Auto-generated method stub
		List<CRAAllocation> craAllocationList = processingRoomJpaDao.getDataFromCRAAllocationForProcessing(icmcId);
		return craAllocationList;
	}

	@Override
	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Discrepancy> discrepancyReportList = processingRoomJpaDao.getDiscrepancyReports(icmcId, sDate, eDate);
		return discrepancyReportList;
	}

	@Override
	public CRAAllocation getPendingBundleById(CRAAllocation craAllocation) {
		CRAAllocation craAllocationData = processingRoomJpaDao.getPendingBundleById(craAllocation);
		return craAllocationData;
	}

	@Override
	public boolean updateForwarPending(CRAAllocation craAllocation) {
		return processingRoomJpaDao.updateForwarPending(craAllocation);
	}

	@Override
	public List<Tuple> getMachineAllocationRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> machineAllocationReportList = processingRoomJpaDao.getMachineAllocationRecord(icmcId, sDate, eDate);
		return machineAllocationReportList;
	}

	@Override
	public List<Tuple> getProcessRecord(BigInteger icmcId, Calendar sDate, Calendar eDate, CurrencyType currencyType) {
		List<Tuple> processReportList = processingRoomJpaDao.getProcessRecord(icmcId, sDate, eDate, currencyType);
		return processReportList;
	}

	@Override
	public BigDecimal getTotalBundleInBin(int denomination, String bin, BigInteger icmcId) {
		return processingRoomJpaDao.getTotalBundleInBin(denomination, bin, icmcId);
	}

	@Override
	public boolean processMachineAllocationForBranch(MachineAllocation machineAllocation, Indent indent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Integer> getMachineNumberList(BigInteger icmcId) {
		List<Integer> machineNumberList = processingRoomJpaDao.getMachineNumberList(icmcId);
		return machineNumberList;
	}

	@Override
	public List<Tuple> getDiscrepancyListForIOReport(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> processDiscrepancyList = processingRoomJpaDao.getDiscrepancyListForIOReport(icmcId, sDate, eDate);
		return processDiscrepancyList;
	}

	@Override
	public List<MachineAllocation> getPendingBundleFromMachineAllocation(BigInteger icmcId, Integer denomination,
			String machineOrManual) {
		List<MachineAllocation> pendingBundleList = processingRoomJpaDao.getPendingBundleFromMachineAllocation(icmcId,
				denomination, machineOrManual);
		return pendingBundleList;
	}

	@Override
	public boolean updatePendingBundleInMachineAllocation(MachineAllocation machineAllocation) {
		boolean isUpdate = processingRoomJpaDao.updatePendingBundleInMachineAllocation(machineAllocation);
		return isUpdate;
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
		List<Tuple> list = processingRoomJpaDao.getPendingBundle(icmcId, sDate, eDate);
		return list;
	}

	@Override
	public List<Tuple> getPendingBundleByMachine(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> list = processingRoomJpaDao.getPendingBundleByMachine(icmcId, sDate, eDate);
		return list;
	}

	@Override
	public List<MachineAllocation> getAggregatedBundleToBeReturnedToVault(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate) {
		List<MachineAllocation> bundleToBeReturnedList = processingRoomJpaDao
				.getAggregatedBundleToBeReturnedToVault(icmcId, cashSource, sDate, eDate);
		return bundleToBeReturnedList;
	}

	@Override
	public String getUserName(String userId) {
		return processingRoomJpaDao.getUserName(userId);
	}

	@Override
	public Process getRepritRecord(Long id) {
		return processingRoomJpaDao.getRepritRecord(id);
	}

	public List<Mutilated> getMitulatedFullValue(BigInteger icmcId) {
		List<Mutilated> getMutilatedList = processingRoomJpaDao.getMitulatedFullValue(icmcId);
		return getMutilatedList;
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
		List<BinTransaction> binList = this.getBinTxnListByDenom(binTxTemp);
		return binList;
	}

	@Override
	public boolean createMutilated(List<Mutilated> mutilatedList) {
		boolean isSaved = processingRoomJpaDao.createMutilated(mutilatedList);
		return isSaved;
	}

	@Override
	public List<Tuple> indentSummaryForMutilated(BigInteger icmcId) {
		List<Tuple> mutilatedList = processingRoomJpaDao.indentSummaryForMutilated(icmcId);
		return mutilatedList;
	}

	@Override
	public List<BinTransaction> getBinNumListForMutilatedIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			BinCategoryType binCategoryType) {
		List<BinTransaction> binLitsForMutilatedIndent = processingRoomJpaDao
				.getBinNumListForMutilatedIndent(denomination, bundle, icmcId, binCategoryType);
		return binLitsForMutilatedIndent;
	}

	@Override
	public boolean insertMitulatedIndentRequest(List<MutilatedIndent> eligibleIndentRequestList) {
		boolean isSuccess = processingRoomJpaDao.insertMitulatedIndentRequest(eligibleIndentRequestList);
		return isSuccess;
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
		List<MutilatedIndent> mutilatedIndentList = processingRoomJpaDao.getMutilatedIndent(icmcId);
		return mutilatedIndentList;
	}

	@Override
	public boolean processMutilatedIndentRequest(String bin, BigDecimal bundle, User user, Long id) {
		MutilatedIndent mutilatedIndent = new MutilatedIndent();
		mutilatedIndent.setBin(bin);
		mutilatedIndent.setBundle(bundle);
		mutilatedIndent.setIcmcId(user.getIcmcId());
		mutilatedIndent.setId(id);
		boolean isIndentUpdate = false;
		BinTransaction txnBin = this.getBinFromTransaction(bin.trim(), user.getIcmcId());

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
		boolean isUpdate = processingRoomJpaDao.updateMutilatedIndentRequest(mutilatedIndent);
		return isUpdate;
	}

	@Override
	public String getICMCName(long icmcId) {
		String icmcName = processingRoomJpaDao.getICMCName(icmcId);
		return icmcName;
	}

	@Override
	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		List<Discrepancy> discrepancyReportList = processingRoomJpaDao.getDiscrepancyReports(icmcId, sDate, eDate,
				normalOrSuspense);
		return discrepancyReportList;
	}

	@Override
	public Discrepancy getDescripancyDataForEdit(Long id, BigInteger icmcId) {
		Discrepancy discrepancyDataForEdit = processingRoomJpaDao.getDescripancyDataForEdit(id, icmcId);
		return discrepancyDataForEdit;
	}

	@Override
	public DiscrepancyAllocation getDescripancyAllocationDataForEdit(Long id, BigInteger icmcId) {
		DiscrepancyAllocation discrepancyAllocation = processingRoomJpaDao.getDescripancyAllocationDataForEdit(id,
				icmcId);
		return discrepancyAllocation;
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
		List<Tuple> mutilatedNotesSummary = processingRoomJpaDao.getMutilatedNotesSummary(icmcId, sDate, eDate);
		return mutilatedNotesSummary;
	}

	@Override
	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		List<Tuple> mutilatedNotesSummary = processingRoomJpaDao.getMutilatedNotesSummary(icmcId, sDate, eDate,
				normalOrSuspense);
		return mutilatedNotesSummary;
	}

	@Override
	public boolean addMachineMaintenance(MachineMaintenance machineMaintainance) {
		processingRoomJpaDao.addMachineMaintenance(machineMaintainance);
		return true;
	}

	@Override
	public List<MachineMaintenance> viewMachineMaintenance(BigInteger icmcId) {
		List<MachineMaintenance> machineMaintenanceList = processingRoomJpaDao.viewMachineMaintenance(icmcId);
		return machineMaintenanceList;
	}

	@Override
	public MachineMaintenance getMachineMaintenanceById(long id) {
		MachineMaintenance machineMaintenance = processingRoomJpaDao.getMachineMaintenanceById(id);
		return machineMaintenance;
	}

	@Override
	public boolean updateMachineMaintenanceStatus(MachineMaintenance machineMaintenance) {
		processingRoomJpaDao.updateMachineMaintenanceStatus(machineMaintenance);
		return true;
	}

	@Override
	public List<AuditorProcess> processRecordForAuditorIndent(AuditorProcess process, User user) {

		List<BinMaster> binMasterList = new ArrayList<BinMaster>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();
		List<AuditorProcess> processList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();

		BigDecimal bundleFromTxn = BigDecimal.ZERO;

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

		List<AuditorIndent> pendingBundleListFromAuditorIndent = processingRoomJpaDao
				.getPendingBundleFromAuditorIndent(user.getIcmcId(), process.getDenomination());

		List<AuditorIndent> eligiblePendingBundleList = UtilityJpa
				.getEligibleBundleListForAuditor(pendingBundleListFromAuditorIndent, process.getBundle(), user);

		if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
			throw new BaseGuiException(
					"Required Bundle is Not available for Denomination:" + process.getDenomination());
		}

		for (AuditorIndent auditorIndent : eligiblePendingBundleList) {
			updatePendingBundleInAuditorIndent(auditorIndent);
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
		List<Tuple> pendingbundleList = processingRoomJpaDao.getPendingBundleForAuditor(icmcId);
		return pendingbundleList;
	}

	@Override
	public List<AuditorIndent> getPendingBundleFromAuditorIndent(BigInteger icmcId, Integer denomination) {
		List<AuditorIndent> auditorIndentList = processingRoomJpaDao.getPendingBundleFromAuditorIndent(icmcId,
				denomination);
		return auditorIndentList;
	}

	@Override
	public boolean updatePendingBundleInAuditorIndent(AuditorIndent auditorIndent) {
		boolean isUpdate = processingRoomJpaDao.updatePendingBundleInAuditorIndent(auditorIndent);
		return isUpdate;
	}

	@Override
	public BinTransaction getDataFromBinTransaction(String bin, BigInteger icmcId, Integer denomination,
			CurrencyType currencyType) {
		BinTransaction binTransaction = processingRoomJpaDao.getDataFromBinTransaction(bin, icmcId, denomination,
				currencyType);
		return binTransaction;
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
		}
		return "SUCCESS";
	}

	@Override
	public boolean insertSuspenseOpeningBalance(SuspenseOpeningBalance suspenseOpeningBalance) {
		boolean saveSuspense = processingRoomJpaDao.insertSuspenseOpeningBalance(suspenseOpeningBalance);
		return saveSuspense;
	}

	@Override
	public List<SuspenseOpeningBalance> getSuspenseOpeningBalance(BigInteger icmcId) {
		List<SuspenseOpeningBalance> suspenseOpeningBalance = processingRoomJpaDao.getSuspenseOpeningBalance(icmcId);
		return suspenseOpeningBalance;
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegister(BigInteger icmcId) {
		List<SuspenseOpeningBalance> suspenseOpeningBalanceList = processingRoomJpaDao
				.openingBalanceForSuspenseRegister(icmcId);
		return suspenseOpeningBalanceList;
	}

	@Override
	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> withdrawalList = processingRoomJpaDao.getTotalNotesForWithdrawal(icmcId, sDate, eDate);
		return withdrawalList;
	}

	@Override
	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> depositList = processingRoomJpaDao.geTotalNotesForDeposit(icmcId, sDate, eDate);
		return depositList;
	}

	@Override
	public boolean branchHistory(List<History> historyList) {
		boolean isSaved = processingRoomJpaDao.branchHistory(historyList);
		return isSaved;
	}

	@Override
	public boolean updateCurrentVersionStatus(SuspenseOpeningBalance suspenseOpeningBalance) {
		boolean isSaved = processingRoomJpaDao.updateCurrentVersionStatus(suspenseOpeningBalance);
		return isSaved;
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDate(BigInteger icmcId, Calendar sDate,
			Calendar eDate) {
		List<SuspenseOpeningBalance> openingBalance = processingRoomJpaDao
				.openingBalanceForSuspenseRegisterPreviousDate(icmcId, sDate, eDate);
		return openingBalance;
	}

	@Override
	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId) {
		List<Tuple> withdrawalList = processingRoomJpaDao.getTotalNotesForWithdrawal(icmcId);
		return withdrawalList;
	}

	@Override
	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId) {
		List<Tuple> depositList = processingRoomJpaDao.geTotalNotesForDeposit(icmcId);
		return depositList;
	}

	@Override
	public boolean saveDataInBinRegister(BinRegister binRegister) {
		boolean isSave = processingRoomJpaDao.saveDataInBinRegister(binRegister);
		return isSave;
	}

	@Override
	public boolean deleteDiscrepancy(long id, BigInteger icmcId) {
		boolean isUpdate = processingRoomJpaDao.deleteDiscrepancy(id, icmcId);
		return isUpdate;
	}

	@Override
	public boolean deleteDiscrepancywithooutallocation(long id, BigInteger icmcId) {
		boolean isUpdate = processingRoomJpaDao.deleteDiscrepancywithooutallocation(id, icmcId);
		return isUpdate;
	}

	@Override
	public boolean insertFullValueMutilated(Mutilated mutilated) {
		boolean isSaved = processingRoomJpaDao.insertFullValueMutilated(mutilated);
		return isSaved;
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
		List<MachineAllocation> bundleListBySource = processingRoomJpaDao.getBundleByCashSource(icmcId,
				isMachineOrManual, denomination, cashSource, sDate, eDate);
		return bundleListBySource;
	}

	@Override
	public List<DefineKeySet> getKeySetDetail(String custodian, BigInteger icmcId) {
		List<DefineKeySet> list = processingRoomJpaDao.getKeySetDetail(custodian, icmcId);
		return list;
	}

	@Override
	public List<CustodianKeySet> getAssignVaultCustodian(BigInteger icmcId) {
		List<CustodianKeySet> custodianList = processingRoomJpaDao.getAssignVaultCustodian(icmcId);
		return custodianList;
	}

	@Override
	public User isValidUser(String username, BigInteger icmcId) {
		User userBean = processingRoomJpaDao.isValidUser(username, icmcId);
		return userBean;
	}

	@Override
	public boolean InsertByDAteSuspeseOpeningBalance(long id, BigInteger icmcId,
			BigDecimal openingBalanceOfDenomination_10, BigDecimal openingBalanceOfDenomination_20,
			BigDecimal openingBalanceOfDenomination_50, BigDecimal openingBalanceOfDenomination_100,
			BigDecimal openingBalanceOfDenomination_200, BigDecimal openingBalanceOfDenomination_500,
			BigDecimal openingBalanceOfDenomination_2000) {
		boolean IsUpdate = processingRoomJpaDao.InsertByDAteSuspeseOpeningBalance(id, icmcId,
				openingBalanceOfDenomination_10, openingBalanceOfDenomination_20, openingBalanceOfDenomination_50,
				openingBalanceOfDenomination_100, openingBalanceOfDenomination_200, openingBalanceOfDenomination_500,
				openingBalanceOfDenomination_2000);

		return IsUpdate;
	}

	@Override
	public boolean insertSuspenseOpeningBalanceLink(SuspenseOpeningBalance suspenseOpeningBalance) {
		boolean saveSuspense = processingRoomJpaDao.insertSuspenseOpeningBalance(suspenseOpeningBalance);
		return saveSuspense;
	}

	@Override
	public boolean updateSuspenseBalanceFromLink(long id, BigDecimal replenishment_2000, BigDecimal replenishment_500,
			BigDecimal replenishment_200, BigDecimal replenishment_100, BigDecimal replenishment_50,
			BigDecimal replenishment_20, BigDecimal replenishment_10, BigDecimal depletion_2000,
			BigDecimal depletion_500, BigDecimal depletion_200, BigDecimal depletion_100, BigDecimal depletion_50,
			BigDecimal depletion_20, BigDecimal depletion_10, String srNumber) {
		boolean IsUpdate = processingRoomJpaDao.updateSuspenseBalanceFromLink(id, replenishment_2000, replenishment_500,
				replenishment_200, replenishment_100, replenishment_50, replenishment_20, replenishment_10,
				depletion_2000, depletion_500, depletion_200, depletion_100, depletion_50, depletion_20, depletion_10,
				srNumber);
		return IsUpdate;
	}

	@Override
	public boolean updateSuspenseBalance(long id, BigDecimal deposit_2000, BigDecimal deposit_500,
			BigDecimal deposit_200, BigDecimal deposit_100, BigDecimal deposit_50, BigDecimal deposit_20,
			BigDecimal deposit_10, BigDecimal withdrawal_2000, BigDecimal withdrawal_500, BigDecimal withdrawal_200,
			BigDecimal withdrawal_100, BigDecimal withdrawal_50, BigDecimal withdrawal_20, BigDecimal withdrawal_10)

	{
		boolean IsUpdate = processingRoomJpaDao.updateSuspenseBalance(id, deposit_2000, deposit_500, deposit_200,
				deposit_100, deposit_50, deposit_20, deposit_10, withdrawal_2000, withdrawal_500, withdrawal_200,
				withdrawal_100, withdrawal_50, withdrawal_20, withdrawal_10); // code
																				// balance
		return IsUpdate;
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDateByDesc(BigInteger icmcId) {
		List<SuspenseOpeningBalance> openingBalanceDesc = processingRoomJpaDao
				.openingBalanceForSuspenseRegisterPreviousDateByDesc(icmcId);
		return openingBalanceDesc;
	}

	@Override
	public List<Mutilated> getMutilatedValueDetails(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Mutilated> mutilatedList = processingRoomJpaDao.getMutilatedValueDetails(icmcId, sDate, eDate);
		return mutilatedList;
	}
	
	@Override
	public boolean processMutilatedRequest(Long id)
	{
		boolean isUpdate = processingRoomJpaDao.processMutilatedRequest(id);
		return isUpdate;
	}
}
