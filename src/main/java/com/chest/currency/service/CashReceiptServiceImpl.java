package com.chest.currency.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.dao.CashReceiptDaoImpl;
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
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.YesNo;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.dao.CashReceiptJpaDao;
import com.chest.currency.qrgencode.QRCodeGen;
import com.chest.currency.util.UtilityJpa;
import com.chest.currency.util.UtilityMapper;
import com.mysema.query.Tuple;

@Service
@Transactional
public class CashReceiptServiceImpl implements CashReceiptService {

	private static final Logger LOG = LoggerFactory.getLogger(CashReceiptServiceImpl.class);

	@Autowired
	QRCodeGen qrCodeGen;

	@Autowired
	CashReceiptDaoImpl cashReceiptDao;

	@Autowired
	protected CashReceiptJpaDao cashReceiptJpaDao;

	@Autowired
	ConcurrentHashMap<Long, ICMC> icmcConcurrentHashMap;

	@Autowired
	ICMCService icmcService;

	@Autowired
	CashPaymentService cashPaymentService;

	private String getPath(String filepath) {
		return filepath;
	}

	public String getQrCode(BranchReceipt branchReceipt) {
		String filepath = qrCodeGen.generateQR(branchReceipt);
		return getPath(filepath);
	}

	public String getDSBQRCode(DSB dsb) {
		String filepath = qrCodeGen.generateDSBQR(dsb);
		return getPath(filepath);
	}

	public String getRBIQrCode(FreshFromRBI freshFromRBI) {
		String filepath = qrCodeGen.generateFreshFromRBIQR(freshFromRBI);
		return getPath(filepath);
	}

	public String getDiversionIRVQRCode(DiversionIRV dirv) {
		String filepath = qrCodeGen.generateDirvQR(dirv);
		return getPath(filepath);
	}

	public String getICMCReceiptQRCode(BankReceipt icmcReceipt) {
		String filepath = qrCodeGen.generateICMCReceiptQR(icmcReceipt);
		return getPath(filepath);
	}

	public List<BinCapacityDenomination> getMaxBundleCapacity(int denomination, CurrencyType currencyType) {
		LOG.info("FETCH CAPACITY");
		return cashReceiptJpaDao.getMaxBundleCapacity(denomination, currencyType);
	}

	public List<BinMaster> getPriorityBinListByType(BinMaster binMaster) {
		return cashReceiptJpaDao.getPriorityBinListByType(binMaster);
	}

	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx) {
		return cashReceiptJpaDao.getBinTxnListByDenom(binTx);
	}

	public boolean updateBinMaster(BinMaster binMaster) {
		return cashReceiptJpaDao.updateBinMaster(binMaster);
	}

	public boolean insertInBinTxn(BinTransaction binTransaction) {
		Boolean isDeleted = cashPaymentService.deleteEmptyBinFromBinTransaction(binTransaction.getIcmcId(),
				binTransaction.getBinNumber());
		LOG.info("deleteEmptyBinFromBinTransaction" + isDeleted);
		LOG.info("binTransaction" + binTransaction);

		return cashReceiptJpaDao.insertInBinTxn(binTransaction);
	}

	@Transactional
	public boolean updateInBinTxn(BinTransaction binTransaction) {
		return cashReceiptJpaDao.updateInBinTxn(binTransaction);
	}

	@Transactional
	public boolean createBranchReceipt(List<BranchReceipt> branchReceipt) {
		return cashReceiptJpaDao.createBranchReceipt(branchReceipt);
	}

	public List<BranchReceipt> getBrachReceiptRecord(User user, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getBrachReceiptRecord(user, sDate, eDate);
	}

	private List<BinMaster> getPriorityBinListByType(User user, CurrencyType currencyType) {
		BinMaster master = new BinMaster();
		master.setFirstPriority(currencyType);
		master.setIcmcId(user.getIcmcId());
		return this.getPriorityBinListByType(master);
	}

	@Override
	@Transactional
	public List<BranchReceipt> processBranchReceipt(BranchReceipt branchReceipt, User user) {

		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();

		List<BranchReceipt> branchRecieptList = new ArrayList<>();

		List<BoxMaster> boxMasterList = new ArrayList<>();
		List<BinCapacityDenomination> capacityList = new ArrayList<>();
		List<BinMaster> binMasterList = new ArrayList<>();
		BigDecimal bundleToBeKeptInICMC = BigDecimal.ZERO;

		if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
			if (branchReceipt.getBinCategoryType() == BinCategoryType.BIN) {
				capacityList = this.getMaxBundleCapacity(branchReceipt.getDenomination(),
						branchReceipt.getCurrencyType());
				binMasterList = getPriorityBinListByType(user);
				binList = getBinTxnListByDenom(branchReceipt, user);

				binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, branchReceipt.getBundle(),
						false, capacityList, CurrencyType.UNPROCESS, CashSource.BRANCH);

				branchRecieptList = UtilityJpa.getBranchReceiptBean(branchReceipt, binTxs, user, capacityList,
						binMasterList, boxMasterList);

				for (BranchReceipt brTemp : branchRecieptList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(brTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(branchReceipt.getBundle().divide(BigDecimal.TEN)) == 0) {
					addTransactions(user, binTxs);
				} else {
					throw new BaseGuiException("Space is not available for " + branchReceipt.getBundle()
							+ " packets in " + branchReceipt.getDenomination() + " denomination");
				}
			} else if (branchReceipt.getBinCategoryType() == BinCategoryType.BOX) {
				BoxMaster boxMaster = new BoxMaster();
				boxMaster.setIcmcId(user.getIcmcId());
				boxMaster.setDenomination(branchReceipt.getDenomination());
				boxMaster.setCurrencyType(branchReceipt.getCurrencyType());
				boxMaster.setCashSource(CashSource.BRANCH);
				boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);

				binList = getBinTxnListByDenom(branchReceipt, user);
				binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, branchReceipt.getBundle(),
						false, CurrencyType.UNPROCESS, CashSource.BRANCH);
				branchRecieptList = UtilityJpa.getBranchReceiptBean(branchReceipt, binTxs, user, capacityList,
						binMasterList, boxMasterList);

				for (BranchReceipt brTemp : branchRecieptList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(brTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(branchReceipt.getBundle().divide(BigDecimal.TEN)) == 0) {
					addInTransactionsForBox(user, binTxs);
				} else {
					throw new BaseGuiException("Space is not available for " + branchReceipt.getBundle()
							+ " packets in " + branchReceipt.getDenomination() + " denomination");
				}
			}
		} else if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {
			if (branchReceipt.getBinCategoryType() == BinCategoryType.BIN) {
				capacityList = this.getMaxBundleCapacity(branchReceipt.getDenomination(),
						branchReceipt.getCurrencyType());
				binMasterList = getPriorityBinListByType(user, branchReceipt.getCurrencyType());
				binList = getBinTxnListByDenom(branchReceipt, user);

				binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, branchReceipt.getBundle(), true,
						capacityList, branchReceipt.getCurrencyType(), CashSource.BRANCH);

				branchRecieptList = UtilityJpa.getBranchReceiptBean(branchReceipt, binTxs, user, capacityList,
						binMasterList, boxMasterList);

				for (BranchReceipt brTemp : branchRecieptList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(brTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(branchReceipt.getBundle()) == 0) {
					addTransactionsForProcessed(user, binTxs, branchReceipt.getCurrencyType());
				} else {
					throw new BaseGuiException("Space is not available for " + branchReceipt.getBundle()
							+ " packets in " + branchReceipt.getDenomination() + " denomination");
				}

			} else if (branchReceipt.getBinCategoryType() == BinCategoryType.BOX) {
				BoxMaster boxMaster = new BoxMaster();
				boxMaster.setIcmcId(user.getIcmcId());
				boxMaster.setDenomination(branchReceipt.getDenomination());
				boxMaster.setCurrencyType(branchReceipt.getCurrencyType());
				boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);

				binList = getBinTxnListByDenom(branchReceipt, user);
				binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, branchReceipt.getBundle(), true,
						branchReceipt.getCurrencyType(), branchReceipt.getCashSource());
				branchRecieptList = UtilityJpa.getBranchReceiptBean(branchReceipt, binTxs, user, capacityList,
						binMasterList, boxMasterList);

				for (BranchReceipt brTemp : branchRecieptList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(brTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(branchReceipt.getBundle()) == 0) {
					addInTransactionsForBox(user, binTxs);
				} else {
					throw new BaseGuiException("Space is not available for " + branchReceipt.getBundle()
							+ " packets in " + branchReceipt.getDenomination() + " denomination");
				}
			}
		}

		Boolean isAdd = addBranchReciept(branchRecieptList);
		if (!isAdd)
			throw new BaseGuiException("Please Try again");

		// History Code
		List<History> historyList = new ArrayList<History>();
		History history = null;
		for (BranchReceipt branch : branchRecieptList) {
			history = new History();
			history.setBinNumber(branch.getBin());
			history.setDenomination(branch.getDenomination());
			history.setReceiveBundle(branch.getBundle());
			history.setWithdrawalBundle(BigDecimal.ZERO);
			history.setInsertBy(branch.getInsertBy());
			history.setUpdateBy(branch.getUpdateBy());
			history.setInsertTime(branch.getInsertTime());
			history.setUpdateTime(branch.getInsertTime());
			history.setIcmcId(branch.getIcmcId());
			history.setDepositOrWithdrawal("DEPOSIT");
			history.setType("U");
			history.setStatus(OtherStatus.REQUESTED);
			historyList.add(history);
		}

		branchHistory(historyList);

		// updating machine allocation for return back to vault
		if (branchReceipt.isFromProcessingRoom()) {
			List<MachineAllocation> pendingBundleListFromMachineAllocation = cashReceiptJpaDao
					.getPendingBundleFromMachineAllocationForReturnBackToVault(user.getIcmcId(),
							branchReceipt.getDenomination(), CashSource.BRANCH);

			List<MachineAllocation> eligiblePendingBundleList = UtilityMapper
					.getEligibleBundleListForMachineAllocationDuringReturnBackToVault(
							pendingBundleListFromMachineAllocation,
							branchReceipt.getBundle().divide(BigDecimal.valueOf(10)),
							branchReceipt.isFromProcessingRoom(), user);

			if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
				throw new BaseGuiException(
						"Required bundles are not available for denomination:" + branchReceipt.getDenomination());
			}

			for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
				this.updatePendingBundleInMachineAllocationForReturnBackToVault(machineAllocation);
			}
		}
		return branchRecieptList;
	}

	private boolean updatePendingBundleInMachineAllocationForReturnBackToVault(MachineAllocation machineAllocation) {
		return cashReceiptJpaDao.updatePendingBundleInMachineAllocation(machineAllocation);
	}

	@Override
	public BinTransaction getBinTxnRecordForRBIFreshedit(FreshFromRBI freshRBIReceiptDb, BigInteger icmcId) {
		return cashReceiptJpaDao.getBinTxnRecordFoRBIFreshediitRBI(freshRBIReceiptDb, icmcId);
	}

	@Override
	public List<FreshFromRBI> processForUpdatingFreshRBI(BinTransaction binTxn, FreshFromRBI freshRBIReceipt,
			FreshFromRBI freshRBIReceiptDb, User user) {
		boolean isAllSuccess = false;
		List<FreshFromRBI> freshRBIReceiptList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal updatedReceiveBundleForTxn = BigDecimal.ZERO;
		int receivenofbag = freshRBIReceiptDb.getNoOfBags();
		if (freshRBIReceiptDb.getCashType() == CashType.COINS) {
			updatedReceiveBundleForTxn = binTxn.getReceiveBundle().subtract(BigDecimal.valueOf(receivenofbag));
		} else if (freshRBIReceiptDb.getCashType() == CashType.NOTES) {
			updatedReceiveBundleForTxn = binTxn.getReceiveBundle().subtract(freshRBIReceiptDb.getBundle());
		}

		BigDecimal receiveBundleFromUI = freshRBIReceipt.getBundle();
		int denomFromUI = freshRBIReceipt.getDenomination();
		int nofbegfromUI = freshRBIReceipt.getNoOfBags();
		binTxn.setReceiveBundle(updatedReceiveBundleForTxn);
		if (binTxn.getReceiveBundle().equals(BigDecimal.ZERO)) {
			binTxn.setStatus(BinStatus.EMPTY);
		}
		isAllSuccess = this.updateInBinTxn(binTxn);
		if (isAllSuccess) {
			freshRBIReceiptDb.setPotdarStatus("CANCELLED");
			cashReceiptJpaDao.updateFreshRBIReceipt(freshRBIReceiptDb);
		}
		freshRBIReceipt.setIcmcId(user.getIcmcId());
		freshRBIReceipt.setDenomination(denomFromUI);
		freshRBIReceipt.setBundle(receiveBundleFromUI);

		freshRBIReceipt.setOrderDate(freshRBIReceiptDb.getOrderDate());
		if (freshRBIReceiptDb.getCashType() == CashType.COINS) {
			freshRBIReceipt.setCashType(CashType.COINS);
		} else if (freshRBIReceiptDb.getCashType() == CashType.NOTES) {
			freshRBIReceipt.setCashType(CashType.NOTES);
		}

		freshRBIReceipt.setBinCategoryType(BinCategoryType.BOX);
		freshRBIReceipt.setRbiOrderNo(freshRBIReceiptDb.getRbiOrderNo());
		freshRBIReceipt.setNoOfBags(nofbegfromUI);
		freshRBIReceipt.setCurrencyType(CurrencyType.FRESH);
		freshRBIReceipt.setCashSource(CashSource.RBI);
		freshRBIReceipt.setInsertTime(now);
		freshRBIReceipt.setUpdateTime(now);

		freshRBIReceiptList = this.processFreshFromRBIUpdate(freshRBIReceipt, user);

		return freshRBIReceiptList;
	}

	public List<FreshFromRBI> processFreshFromRBIUpdate(FreshFromRBI fresh, User user) {
		List<FreshFromRBI> freshFromRBIList = new ArrayList<>();

		if (fresh.getCashType() == CashType.NOTES) {
			if (fresh.getBinCategoryType() == BinCategoryType.BOX) {
				fresh.setBin("BOX" + user.getIcmcId() + Instant.now().toEpochMilli());
				freshFromRBIList = UtilityJpa.getFreshFromRBIForBox(fresh, user);
				BinTransaction binTxsForBox = new BinTransaction();
				binTxsForBox.setDenomination(fresh.getDenomination());
				binTxsForBox.setReceiveBundle(fresh.getBundle());
				binTxsForBox.setBinNumber(fresh.getBin());
				binTxsForBox.setIcmcId(user.getIcmcId());
				binTxsForBox.setInsertBy(user.getId());
				binTxsForBox.setStatus(BinStatus.NOT_FULL);
				binTxsForBox.setUpdateBy(user.getId());
				binTxsForBox.setBinType(CurrencyType.FRESH);
				binTxsForBox.setCashSource(CashSource.RBI);
				binTxsForBox.setBinCategoryType(BinCategoryType.BOX);
				binTxsForBox.setCashType(CashType.NOTES);
				binTxsForBox.setVerified(YesNo.No);
				binTxsForBox.setRbiOrderNo(fresh.getRbiOrderNo());
				Calendar now = Calendar.getInstance();
				binTxsForBox.setInsertTime(now);
				binTxsForBox.setUpdateTime(now);
				addInTransactionsForBox(user, binTxsForBox);
				addFreshFromRBI(freshFromRBIList);
			} else if (fresh.getBinCategoryType() == BinCategoryType.BIN) {
				List<BinMaster> binMasterList = getPriorityBinListByTypeForFresh(user);
				List<BinCapacityDenomination> capacityList = this.getMaxBundleCapacity(fresh.getDenomination(),
						fresh.getCurrencyType());

				List<BinTransaction> binTxs = UtilityJpa.getBinForFreshFromRBI(binMasterList, fresh.getBundle(), true,
						capacityList, CurrencyType.FRESH, CashSource.RBI, YesNo.Yes, BinCategoryType.BIN,
						CashType.NOTES, fresh.getRbiOrderNo());

				freshFromRBIList = UtilityJpa.getFreshFromRBI(fresh, binTxs, user, capacityList, binMasterList);
				addTransactions(user, binTxs);
				addFreshFromRBI(freshFromRBIList);

			}
		}

		if (fresh.getCashType() == CashType.COINS) {
			freshFromRBIList = UtilityJpa.getFreshFromRBIForCoins(fresh, user);
			CoinsSequence coinsSequence = new CoinsSequence();
			coinsSequence = this.getSequence(user.getIcmcId(), fresh.getDenomination());
			if (coinsSequence == null) {
				coinsSequence = new CoinsSequence();
				int finalSequence = fresh.getNoOfBags();
				addFreshFromRBI(freshFromRBIList);
				prepareAndInsertSequence(fresh, user, coinsSequence, finalSequence);

			} else {
				int sequenceFromUI = fresh.getNoOfBags();
				int sequenceFromDB = coinsSequence.getSequence();
				int finalSequence = sequenceFromUI + sequenceFromDB;
				// fresh.setBin(freshFromRBIList.get(0).get(5));
				addFreshFromRBI(freshFromRBIList);
				for (FreshFromRBI ff : freshFromRBIList) {
					fresh.setBin(ff.getBin());
				}

				prepareAndInsertSequence(fresh, user, coinsSequence, finalSequence);

			}
			// addFreshFromRBI(freshFromRBIList);

			// History
			List<History> historyList = new ArrayList<History>();
			History history = null;
			for (FreshFromRBI branch : freshFromRBIList) {
				history = new History();
				history.setBinNumber(branch.getBin());
				history.setDenomination(branch.getDenomination());
				history.setReceiveBundle(branch.getBundle());
				history.setWithdrawalBundle(BigDecimal.ZERO);
				history.setInsertBy(branch.getInsertBy());
				history.setUpdateBy(branch.getUpdateBy());
				history.setInsertTime(branch.getInsertTime());
				history.setUpdateTime(branch.getInsertTime());
				history.setIcmcId(branch.getIcmcId());
				history.setDepositOrWithdrawal("DEPOSIT");
				history.setType("F");
				historyList.add(history);
			}

			branchHistory(historyList);
		}

		return freshFromRBIList;
	}

	@Override
	@Transactional
	public List<FreshFromRBI> processFreshFromRBI(FreshFromRBI fresh, User user, YesNo yesNo,
			BinCategoryType binCategoryType, CashType cashType) {
		List<FreshFromRBI> freshFromRBIList = new ArrayList<>();

		if (fresh.getCashType() == CashType.NOTES) {
			if (fresh.getBinCategoryType() == BinCategoryType.BOX) {
				fresh.setBin("BOX" + user.getIcmcId() + Instant.now().toEpochMilli());
				freshFromRBIList = UtilityJpa.getFreshFromRBIForBox(fresh, user);
				BinTransaction binTxsForBox = new BinTransaction();
				binTxsForBox.setDenomination(fresh.getDenomination());
				binTxsForBox.setReceiveBundle(fresh.getBundle());
				binTxsForBox.setMaxCapacity(fresh.getBundle());
				binTxsForBox.setBinNumber(fresh.getBin());
				binTxsForBox.setIcmcId(user.getIcmcId());
				binTxsForBox.setInsertBy(user.getId());
				binTxsForBox.setStatus(BinStatus.NOT_FULL);
				binTxsForBox.setUpdateBy(user.getId());
				binTxsForBox.setBinType(CurrencyType.FRESH);
				binTxsForBox.setCashSource(CashSource.RBI);
				binTxsForBox.setBinCategoryType(BinCategoryType.BOX);
				binTxsForBox.setCashType(CashType.NOTES);
				binTxsForBox.setVerified(YesNo.No);
				binTxsForBox.setRbiOrderNo(fresh.getRbiOrderNo());
				Calendar now = Calendar.getInstance();
				binTxsForBox.setInsertTime(now);
				binTxsForBox.setUpdateTime(now);
				addInTransactionsForBox(user, binTxsForBox);
				addFreshFromRBI(freshFromRBIList);
			} else if (fresh.getBinCategoryType() == BinCategoryType.BIN) {
				List<BinMaster> binMasterList = getPriorityBinListByTypeForFresh(user);
				List<BinCapacityDenomination> capacityList = this.getMaxBundleCapacity(fresh.getDenomination(),
						fresh.getCurrencyType());
				List<BinTransaction> binTxs = UtilityJpa.getBinForFreshFromRBI(binMasterList, fresh.getBundle(), true,
						capacityList, CurrencyType.FRESH, CashSource.RBI, yesNo, BinCategoryType.BIN, CashType.NOTES,
						fresh.getRbiOrderNo());
				freshFromRBIList = UtilityJpa.getFreshFromRBI(fresh, binTxs, user, capacityList, binMasterList);
				addTransactions(user, binTxs);
				addFreshFromRBI(freshFromRBIList);
			}
		} else if (fresh.getCashType() == CashType.COINS) {
			freshFromRBIList = UtilityJpa.getFreshFromRBIForCoins(fresh, user);
			CoinsSequence coinsSequence = new CoinsSequence();
			coinsSequence = this.getSequence(user.getIcmcId(), fresh.getDenomination());
			LOG.info("coinsSequence " + coinsSequence);
			LOG.info("INSERT fresh  " + fresh);
			if (coinsSequence == null) {
				coinsSequence = new CoinsSequence();
				int finalSequence = fresh.getNoOfBags();
				addFreshFromRBI(freshFromRBIList);
				for (FreshFromRBI ff : freshFromRBIList) {
					fresh.setBin(ff.getBin());
				}
				prepareAndInsertSequence(fresh, user, coinsSequence, finalSequence);
			} else {
				int sequenceFromUI = fresh.getNoOfBags();
				int sequenceFromDB = coinsSequence.getSequence();
				int finalSequence = sequenceFromUI + sequenceFromDB;
				// fresh.setBin(freshFromRBIList.get(0).get(5));
				addFreshFromRBI(freshFromRBIList);
				for (FreshFromRBI ff : freshFromRBIList) {
					fresh.setBin(ff.getBin());
				}
				prepareAndInsertSequence(fresh, user, coinsSequence, finalSequence);
			}
			// History
			List<History> historyList = new ArrayList<History>();
			History history = null;
			for (FreshFromRBI branch : freshFromRBIList) {
				history = new History();
				history.setBinNumber(branch.getBin());
				history.setDenomination(branch.getDenomination());
				history.setReceiveBundle(branch.getBundle());
				history.setWithdrawalBundle(BigDecimal.ZERO);
				history.setInsertBy(branch.getInsertBy());
				history.setUpdateBy(branch.getUpdateBy());
				history.setInsertTime(branch.getInsertTime());
				history.setUpdateTime(branch.getInsertTime());
				history.setIcmcId(branch.getIcmcId());
				history.setDepositOrWithdrawal("DEPOSIT");
				history.setType("F");
				historyList.add(history);
			}
			branchHistory(historyList);
		}
		return freshFromRBIList;
	}

	@Override
	public List<BankReceipt> processForUpdatingIndentOtherBankReceipt(BankReceipt otherBankReceipt,
			BankReceipt otherBankReceiptdb, Indent indentdb, User user) {
		List<BankReceipt> otherReceiptList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal receiveBundleFromUI = otherBankReceipt.getBundle();
		int denomFromUI = otherBankReceipt.getDenomination();
		// BigDecimal updatedReceiveBundleForTxn =
		// binTxn.getReceiveBundle().subtract(dsbdb.getBundle());
		indentdb.setStatus(OtherStatus.CANCELLED);
		cashReceiptJpaDao.updateIndent(indentdb);
		otherBankReceiptdb.setStatus(4);
		cashReceiptJpaDao.updateOtherBank(otherBankReceiptdb);

		otherBankReceipt.setDenomination(denomFromUI);
		otherBankReceipt.setBundle(receiveBundleFromUI);

		otherBankReceipt.setInsertBy(user.getId());
		otherBankReceipt.setUpdateBy(user.getId());
		otherBankReceipt.setInsertTime(now);
		otherBankReceipt.setUpdateTime(now);
		otherBankReceipt.setBinCategoryType(BinCategoryType.PROCESSING);
		otherBankReceipt.setCurrencyType(otherBankReceiptdb.getCurrencyType());
		otherReceiptList = this.processBankReceipt(otherBankReceipt, user);
		return otherReceiptList;
	}

	private void prepareAndInsertSequence(FreshFromRBI fresh, User user, CoinsSequence coinsSequence,
			int finalSequence) {
		Calendar now = Calendar.getInstance();
		coinsSequence.setIcmcId(user.getIcmcId());
		coinsSequence.setSequence(finalSequence);
		coinsSequence.setInsertTime(now);
		coinsSequence.setUpdateTime(now);
		coinsSequence.setInsertBy(user.getId());
		coinsSequence.setUpdateBy(user.getId());
		coinsSequence.setDenomination(fresh.getDenomination());
		this.insertInCoinSequence(coinsSequence);

		BinTransaction binTxn = new BinTransaction();
		binTxn.setCashSource(CashSource.RBI);
		binTxn.setDenomination(fresh.getDenomination());
		binTxn.setBinType(CurrencyType.FRESH);
		binTxn.setCashType(CashType.COINS);
		binTxn.setBinNumber(fresh.getBin());
		// binTxn.setRcvBundle(BigDecimal.valueOf(fresh.getNoOfBags()));
		binTxn.setReceiveBundle(BigDecimal.valueOf(fresh.getNoOfBags()));
		binTxn.setMaxCapacity(BigDecimal.valueOf(fresh.getNoOfBags()));
		binTxn.setBinCategoryType(BinCategoryType.BAG);
		binTxn.setStatus(BinStatus.FULL);
		binTxn.setVerified(YesNo.Yes);
		binTxn.setRbiOrderNo(fresh.getRbiOrderNo());
		binTxn.setInsertBy(user.getId());
		binTxn.setUpdateBy(user.getId());
		binTxn.setIcmcId(user.getIcmcId());
		binTxn.setInsertTime(now);
		binTxn.setUpdateTime(now);
		this.insertCoinSequenceInBinTxn(binTxn);
	}

	@Override
	@Transactional
	public List<DiversionIRV> processDiversionIRV(DiversionIRV dirv, User user) {

		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();

		List<DiversionIRV> diversionIRVList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();
		List<BinMaster> binMasterList = new ArrayList<>();
		List<BinCapacityDenomination> capacityList = new ArrayList<>();
		BigDecimal bundleToBeKeptInICMC = BigDecimal.ZERO;

		if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
			if (dirv.getBinCategoryType() == BinCategoryType.BIN) {
				capacityList = this.getMaxBundleCapacity(dirv.getDenomination(), dirv.getCurrencyType());

				binMasterList = getPriorityBinListByType(user);
				binList = getBinTxnListByDenom(dirv, user);

				binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, dirv.getBundle(), true,
						capacityList, CurrencyType.UNPROCESS, CashSource.DIVERSION);

				diversionIRVList = UtilityJpa.getDiversionIRV(dirv, binTxs, user, capacityList, binMasterList,
						boxMasterList);
				for (DiversionIRV dirvTemp : diversionIRVList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(dirvTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(dirv.getBundle()) == 0) {
					addTransactions(user, binTxs);
					addDiversionIRV(diversionIRVList);
				} else {
					throw new BaseGuiException("Space is not available in BIN for " + dirv.getBundle() + " bundles in "
							+ dirv.getDenomination() + " denomination");
				}
			} else if (dirv.getBinCategoryType() == BinCategoryType.PROCESSING) {
				dirv = UtilityJpa.getIVRForProcessing(dirv, user);
				diversionIRVList.add(dirv);
				addDiversionIRV(diversionIRVList);
				Indent indent1 = UtilityJpa.getIndentIVR(dirv, user);
				this.insertInIndent(indent1);

			} else if (dirv.getBinCategoryType() == BinCategoryType.BOX) {
				BoxMaster boxMaster = new BoxMaster();
				boxMaster.setIcmcId(user.getIcmcId());
				boxMaster.setDenomination(dirv.getDenomination());
				boxMaster.setCurrencyType(dirv.getCurrencyType());
				boxMaster.setCashSource(CashSource.DIVERSION);
				boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);

				binList = getBinTxnListByDenom(dirv, user);

				binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, dirv.getBundle(), true,
						CurrencyType.UNPROCESS, CashSource.DIVERSION);

				diversionIRVList = UtilityJpa.getDiversionIRV(dirv, binTxs, user, capacityList, binMasterList,
						boxMasterList);

				for (DiversionIRV dirvTemp : diversionIRVList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(dirvTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(dirv.getBundle()) == 0) {
					addInTransactionsForBox(user, binTxs);
					addDiversionIRV(diversionIRVList);
				} else {
					throw new BaseGuiException("Space is not available in BOX for " + dirv.getBundle() + " bundles in "
							+ dirv.getDenomination() + " denomination");
				}
			}
		} else if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {

			if (dirv.getBinCategoryType() == BinCategoryType.BIN) {
				capacityList = this.getMaxBundleCapacity(dirv.getDenomination(), dirv.getCurrencyType());
				binMasterList = getPriorityBinListByType(user, dirv.getCurrencyType());
				binList = getBinTxnListByDenom(dirv, user);
				binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, dirv.getBundle(), true,
						capacityList, dirv.getCurrencyType(), CashSource.DIVERSION);
				diversionIRVList = UtilityJpa.getDiversionIRV(dirv, binTxs, user, capacityList, binMasterList,
						boxMasterList);

				for (DiversionIRV brTemp : diversionIRVList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(brTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(dirv.getBundle()) == 0) {
					addTransactionsForProcessed(user, binTxs, dirv.getCurrencyType());
					addDiversionIRV(diversionIRVList);
				} else {
					throw new BaseGuiException("Space is not available for " + dirv.getBundle() + " packets in "
							+ dirv.getDenomination() + " denomination");
				}
			} else if (dirv.getBinCategoryType() == BinCategoryType.PROCESSING) {
				dirv = UtilityJpa.getIVRForProcessing(dirv, user);
				diversionIRVList.add(dirv);
				addDiversionIRV(diversionIRVList);
				Indent indent1 = UtilityJpa.getIndentIVR(dirv, user);
				this.insertInIndent(indent1);
			} else if (dirv.getBinCategoryType() == BinCategoryType.BOX) {
				BoxMaster boxMaster = new BoxMaster();
				boxMaster.setIcmcId(user.getIcmcId());
				boxMaster.setDenomination(dirv.getDenomination());
				boxMaster.setCurrencyType(dirv.getCurrencyType());
				boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);

				binList = getBinTxnListByDenom(dirv, user);
				binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, dirv.getBundle(), true,
						dirv.getCurrencyType(), CashSource.DIVERSION);
				diversionIRVList = UtilityJpa.getDiversionIRV(dirv, binTxs, user, capacityList, binMasterList,
						boxMasterList);

				for (DiversionIRV brTemp : diversionIRVList) {
					bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(brTemp.getBundle());
				}
				if (bundleToBeKeptInICMC.compareTo(dirv.getBundle()) == 0) {
					addInTransactionsForBox(user, binTxs);
					addDiversionIRV(diversionIRVList);
				} else {
					throw new BaseGuiException("Space is not available for " + dirv.getBundle() + " packets in "
							+ dirv.getDenomination() + " denomination");
				}
			}
		}
		// History Code
		List<History> historyList = new ArrayList<History>();
		History history = null;
		for (DiversionIRV branch : diversionIRVList) {
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
			history.setDepositOrWithdrawal("DEPOSIT");
			history.setType("U");
			history.setStatus(OtherStatus.REQUESTED);
			historyList.add(history);
		}

		branchHistory(historyList);

		// updating machine allocation for return back to vault
		if (dirv.isFromProcessingRoom()) {
			List<MachineAllocation> pendingBundleListFromMachineAllocation = cashReceiptJpaDao
					.getPendingBundleFromMachineAllocationForReturnBackToVault(user.getIcmcId(), dirv.getDenomination(),
							CashSource.DIVERSION);

			List<MachineAllocation> eligiblePendingBundleList = UtilityMapper
					.getEligibleBundleListForMachineAllocationDuringReturnBackToVault(
							pendingBundleListFromMachineAllocation, dirv.getBundle(), dirv.isFromProcessingRoom(),
							user);

			if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
				throw new BaseGuiException(
						"Required bundles are not available for denomination:" + dirv.getDenomination());
			}

			for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
				this.updatePendingBundleInMachineAllocationForReturnBackToVault(machineAllocation);
			}
		}

		return diversionIRVList;
	}

	@Override
	@Transactional
	public List<DSB> processDSB(DSB dsb, User user) {

		List<DSB> dsbList = new ArrayList<>();
		List<BinMaster> binMasterList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();
		BigDecimal bundleToBeKeptInICMC = BigDecimal.ZERO;
		List<BinCapacityDenomination> capacityList = this.getMaxBundleCapacity(dsb.getDenomination(),
				dsb.getCurrencyType());
		if (dsb.getBinCategoryType() == BinCategoryType.BIN) {
			binMasterList = getPriorityBinListByType(user);
			binList = getBinTxnListByDenom(dsb, user);
			binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, dsb.getBundle(), true, capacityList,
					CurrencyType.UNPROCESS, CashSource.DSB);
			dsbList = UtilityJpa.getDSB(dsb, binTxs, user, capacityList, binMasterList, boxMasterList);
			for (DSB dsbTemp : dsbList) {
				bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(dsbTemp.getBundle());
			}
			LOG.info("bundleToBeKeptInICMC FOR  BIN OR VAULT " + bundleToBeKeptInICMC);
			LOG.info("dsb.getBundle() FOR  BIN OR VAULT " + dsb.getBundle());
			LOG.info("dsbList for saving bin " + dsbList);
			addDSBForSave(dsbList);
			if (bundleToBeKeptInICMC.compareTo(dsb.getBundle()) == 0) {
				addTransactions(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available for " + dsb.getBundle() + " bundles in "
						+ dsb.getDenomination() + " denomination in Requested Source");
			}
		} else if (dsb.getBinCategoryType() == BinCategoryType.BOX) {
			BoxMaster boxMaster = new BoxMaster();
			boxMaster.setIcmcId(user.getIcmcId());
			boxMaster.setDenomination(dsb.getDenomination());
			boxMaster.setCurrencyType(dsb.getCurrencyType());
			boxMaster.setCashSource(CashSource.DSB);
			boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);
			binList = getBinTxnListByDenom(dsb, user);
			binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, dsb.getBundle(), true,
					CurrencyType.UNPROCESS, CashSource.DSB);
			dsbList = UtilityJpa.getDSB(dsb, binTxs, user, capacityList, binMasterList, boxMasterList);

			LOG.info("dsbLi FOR BOX " + dsbList);
			for (DSB dsbTemp : dsbList) {
				bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(dsbTemp.getBundle());
			}
			LOG.info("bundleToBeKeptInICMC FOR BOX " + bundleToBeKeptInICMC);
			LOG.info("dsb.getBundle() FOR BOX " + dsb.getBundle());
			LOG.info("dsbList for saving box" + dsbList);
			addDSBForSave(dsbList);
			if (bundleToBeKeptInICMC.compareTo(dsb.getBundle()) == 0) {
				addInTransactionsForBox(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available for " + dsb.getBundle() + " bundles in "
						+ dsb.getDenomination() + " denomination");
			}
		}

		else if (dsb.getBinCategoryType() == BinCategoryType.PROCESSING) {
			LOG.info("dsb.getBinCategoryType() FOR PROCESSING " + dsb.getBinCategoryType());
			dsb = UtilityJpa.getDSBForProcessing(dsb, user);
			dsbList.add(dsb);
			Long id = addDSB(dsbList);
			dsb.setId(id);
			Indent indent = UtilityJpa.getIndent(dsb, user);
			this.insertInIndent(indent);
		}
		/* addDSB(dsbList); */
		// History Code
		List<History> historyList = new ArrayList<History>();
		History history = null;
		for (DSB branch : dsbList) {
			history = new History();
			history.setBinNumber(branch.getBin());
			history.setDenomination(branch.getDenomination());
			history.setReceiveBundle(branch.getBundle());
			history.setWithdrawalBundle(BigDecimal.ZERO);
			history.setInsertBy(branch.getInsertBy());
			history.setUpdateBy(branch.getUpdateBy());
			history.setInsertTime(branch.getInsertTime());
			history.setUpdateTime(branch.getInsertTime());
			history.setDepositOrWithdrawal("DEPOSIT");
			history.setType("UNPROCESS");
			history.setIcmcId(branch.getIcmcId());
			history.setStatus(OtherStatus.REQUESTED);
			historyList.add(history);
		}

		branchHistory(historyList);
		// updating machine allocation for return back to vault
		if (dsb.isFromProcessingRoom()) {
			List<MachineAllocation> pendingBundleListFromMachineAllocation = cashReceiptJpaDao
					.getPendingBundleFromMachineAllocationForReturnBackToVault(user.getIcmcId(), dsb.getDenomination(),
							CashSource.DSB);

			List<MachineAllocation> eligiblePendingBundleList = UtilityMapper
					.getEligibleBundleListForMachineAllocationDuringReturnBackToVault(
							pendingBundleListFromMachineAllocation, dsb.getBundle(), dsb.isFromProcessingRoom(), user);

			if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
				throw new BaseGuiException(
						"Required bundles are not available for denomination:" + dsb.getDenomination());
			}

			for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
				this.updatePendingBundleInMachineAllocationForReturnBackToVault(machineAllocation);
			}
		}
		LOG.info("return dsbList " + dsbList);
		return dsbList;
	}

	private void addTransactions(User user, List<BinTransaction> binTxs) {
		BinMaster masterTemp = new BinMaster();
		masterTemp.setFirstPriority(CurrencyType.UNPROCESS);
		masterTemp.setIcmcId(user.getIcmcId());

		for (BinTransaction binTx : binTxs) {
			if (binTx.getId() != null && binTx.getId() > 0) {
				this.updateInBinTxn(binTx);
			} else {
				masterTemp.setBinNumber(binTx.getBinNumber());
				masterTemp.setIcmcId(binTx.getIcmcId());
				this.updateBinMaster(masterTemp);
				Boolean isDeleted = cashPaymentService.deleteEmptyBinFromBinTransaction(binTx.getIcmcId(),
						binTx.getBinNumber());
				LOG.info("deleteEmptyBinFromBinTransaction isDeleted  " + isDeleted);
				LOG.info("addTransactions binTx  " + binTx);
				this.insertInBinTxn(binTx);
			}
		}
	}

	private void addTransactionsForProcessed(User user, List<BinTransaction> binTxs, CurrencyType currencyType) {
		BinMaster masterTemp = new BinMaster();
		masterTemp.setFirstPriority(currencyType);
		masterTemp.setIcmcId(user.getIcmcId());

		for (BinTransaction binTx : binTxs) {
			if (binTx.getId() != null && binTx.getId() > 0) {
				this.updateInBinTxn(binTx);
			} else {
				masterTemp.setBinNumber(binTx.getBinNumber());
				masterTemp.setIcmcId(binTx.getIcmcId());
				this.updateBinMaster(masterTemp);
				cashPaymentService.deleteEmptyBinFromBinTransaction(binTx.getIcmcId(), binTx.getBinNumber());
				this.insertInBinTxn(binTx);
			}
		}
	}

	@Transactional
	private void addInTransactionsForBox(User user, List<BinTransaction> binTxs) {
		BoxMaster masterTemp = new BoxMaster();
		masterTemp.setIcmcId(user.getIcmcId());
		for (BinTransaction binTx : binTxs) {
			if (binTx.getId() != null && binTx.getId() > 0) {
				this.updateInBinTxn(binTx);
			} else {
				masterTemp.setBoxName(binTx.getBinNumber());
				cashReceiptJpaDao.updateBoxMaster(masterTemp);
				// cashPaymentService.deleteEmptyBinFromBinTransaction(binTx.getIcmcId(),
				// binTx.getBinNumber());
				this.insertInBinTxn(binTx);
			}
		}
	}

	private void addInTransactionsForBox(User user, BinTransaction binTxs) {
		// cashPaymentService.deleteEmptyBinFromBinTransaction(binTxs.getIcmcId(),
		// binTxs.getBinNumber());
		this.insertInBinTxn(binTxs);
	}

	private List<BinTransaction> getBinTxnListByDenom(BranchReceipt branchReceipt, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		List<BinTransaction> binList = new ArrayList<>();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setDenomination(branchReceipt.getDenomination());
		binTxTemp.setBinCategoryType(branchReceipt.getBinCategoryType());
		if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
			binTxTemp.setReceiveBundle(branchReceipt.getBundle().divide(BigDecimal.TEN));
			binTxTemp.setCashSource(CashSource.BRANCH);
			binTxTemp.setBinType(CurrencyType.UNPROCESS);
			binList = this.getBinTxnListByDenom(binTxTemp);
		} else if (branchReceipt.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {
			binTxTemp.setReceiveBundle(branchReceipt.getBundle());
			binTxTemp.setBinType(branchReceipt.getCurrencyType());
			binTxTemp.setCashSource(CashSource.BRANCH);
			binList = this.getBinTxnListByDenomForProcessed(binTxTemp);
		}
		return binList;
	}

	private List<BinTransaction> getBinTxnListByDenom(FreshFromRBI fresh, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(fresh.getBundle());
		binTxTemp.setDenomination(fresh.getDenomination());
		binTxTemp.setCashSource(CashSource.RBI);
		binTxTemp.setVerified(YesNo.No);
		// binTxTemp.setBinCategoryType(BinCategoryType.BIN);
		binTxTemp.setBinType(fresh.getCurrencyType());
		binTxTemp.setBinCategoryType(fresh.getBinCategoryType());

		return this.getBinTxnListByDenom(binTxTemp);
	}

	private List<BinTransaction> getBinTxnListByDenom(DiversionIRV dirv, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(dirv.getBundle());
		binTxTemp.setDenomination(dirv.getDenomination());
		binTxTemp.setCashSource(CashSource.DIVERSION);
		binTxTemp.setBinCategoryType(dirv.getBinCategoryType());
		binTxTemp.setBinType(dirv.getCurrencyType());

		return this.getBinTxnListByDenom(binTxTemp);
	}

	private List<BinTransaction> getBinTxnListByDenom(DSB dsb, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(dsb.getBundle());
		binTxTemp.setDenomination(dsb.getDenomination());
		binTxTemp.setCashSource(CashSource.DSB);
		binTxTemp.setBinType(dsb.getCurrencyType());
		binTxTemp.setBinCategoryType(dsb.getBinCategoryType());

		return this.getBinTxnListByDenom(binTxTemp);
	}

	private List<BinTransaction> getBinTxnListByDenom(BankReceipt icmcReceipt, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(icmcReceipt.getBundle().divide(BigDecimal.TEN));
		binTxTemp.setDenomination(icmcReceipt.getDenomination());
		binTxTemp.setCashSource(CashSource.OTHERBANK);
		binTxTemp.setBinType(icmcReceipt.getCurrencyType());
		binTxTemp.setBinCategoryType(icmcReceipt.getBinCategoryType());
		return this.getBinTxnListByDenom(binTxTemp);
	}

	private List<BinMaster> getPriorityBinListByType(User user) {
		BinMaster master = new BinMaster();
		master.setFirstPriority(CurrencyType.UNPROCESS);
		master.setIcmcId(user.getIcmcId());
		return this.getPriorityBinListByType(master);
	}

	private List<BinMaster> getPriorityBinListByTypeForFresh(User user) {
		BinMaster master = new BinMaster();
		master.setFirstPriority(CurrencyType.FRESH);
		master.setIcmcId(user.getIcmcId());
		return this.getPriorityBinListByType(master);
	}

	@Transactional
	private Boolean addBranchReciept(List<BranchReceipt> branchRecieptList) {
		for (BranchReceipt br : branchRecieptList) {
			br.setFilepath(getQrCode(br));
		}
		return this.createBranchReceipt(branchRecieptList);
	}

	private void addDSBForSave(List<DSB> dsbList) {
		for (DSB br : dsbList) {
			br.setFilepath(getDSBQRCode(br));
		}
		this.saveDSB(dsbList);
	}

	private void addFreshFromRBI(List<FreshFromRBI> freshList) {
		for (FreshFromRBI br : freshList) {
			br.setFilepath(getRBIQrCode(br));
		}
		this.createFreshFromRBI(freshList);
	}

	private void addDiversionIRV(List<DiversionIRV> dirvList) {
		for (DiversionIRV br : dirvList) {
			br.setFilepath(getDiversionIRVQRCode(br));
		}
		this.createDiversionIRV(dirvList);
	}

	private Long addDSB(List<DSB> dsbList) {
		for (DSB br : dsbList) {
			br.setFilepath(getDSBQRCode(br));
		}
		return this.createDSB(dsbList);
	}

	private void addICMCReceipt(List<BankReceipt> icmcReceiptList) {
		for (BankReceipt br : icmcReceiptList) {
			br.setFilepath(getICMCReceiptQRCode(br));
		}
		this.createICMCReceipt(icmcReceiptList);
	}

	@Override
	public boolean createFreshFromRBI(List<FreshFromRBI> freshFromRBI) {
		return cashReceiptJpaDao.createFreshFromRBI(freshFromRBI);
	}

	@Override
	public List<FreshFromRBI> getFreshFromRBIRecord(User user, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getFreshFromRBIRecord(user, sDate, eDate);
	}

	@Override
	public boolean createDiversionIRV(List<DiversionIRV> diversionIRV) {
		return cashReceiptJpaDao.createDiversionIRV(diversionIRV);
	}

	@Override
	public List<DiversionIRV> getDiversionIRVRecord(User user, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getDiversionIRVRecord(user, sDate, eDate);
	}

	@Override
	public Long createDSB(List<DSB> dsb) {
		return cashReceiptJpaDao.createDSB(dsb);
	}

	@Override
	public boolean saveDSB(List<DSB> dsb) {
		return cashReceiptJpaDao.saveDSB(dsb);
	}

	@Override
	public List<DSB> getDSBRecord(User user, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getDSBRecord(user, sDate, eDate);
	}

	@Override
	public boolean insertInIndent(Indent indent) {
		return cashReceiptJpaDao.insertInIndent(indent);
	}

	@Override
	public List<BankReceipt> getBankReceiptRecord(User user, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getBankReceiptRecord(user, sDate, eDate);
	}

	@Override
	public boolean createICMCReceipt(List<BankReceipt> icmcReceipt) {
		return cashReceiptJpaDao.createICMCReceipt(icmcReceipt);
	}

	@Override
	@Transactional
	public List<BankReceipt> processBankReceipt(BankReceipt bankReceipt, User user) {

		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();

		List<BankReceipt> otherBankReceiptList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();
		List<BinCapacityDenomination> capacityList = new ArrayList<>();
		List<BinMaster> binMasterList = new ArrayList<>();
		BigDecimal bundleToBeKeptInICMC = BigDecimal.ZERO;

		if (bankReceipt.getBinCategoryType() == BinCategoryType.BIN) {
			capacityList = this.getMaxBundleCapacity(bankReceipt.getDenomination(), bankReceipt.getCurrencyType());

			binMasterList = getPriorityBinListByType(user);

			binList = getBinTxnListByDenom(bankReceipt, user);

			binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, bankReceipt.getBundle(), true,
					capacityList, CurrencyType.UNPROCESS, CashSource.OTHERBANK);

			otherBankReceiptList = UtilityJpa.getICMCReceipt(bankReceipt, binTxs, user, capacityList, binMasterList,
					boxMasterList);

			for (BankReceipt otherBankTemp : otherBankReceiptList) {
				bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(otherBankTemp.getBundle());
			}
			if (bundleToBeKeptInICMC.compareTo(bankReceipt.getBundle()) == 0) {
				addTransactions(user, binTxs);
				addICMCReceipt(otherBankReceiptList);

			} else {
				throw new BaseGuiException("Space is not available in BIN for " + bankReceipt.getBundle()
						+ " bundles in " + bankReceipt.getDenomination() + " denomination");
			}
		} else if (bankReceipt.getBinCategoryType() == BinCategoryType.BOX) {
			BoxMaster boxMaster = new BoxMaster();
			boxMaster.setIcmcId(user.getIcmcId());
			boxMaster.setDenomination(bankReceipt.getDenomination());
			boxMaster.setCurrencyType(bankReceipt.getCurrencyType());
			boxMaster.setCashSource(CashSource.OTHERBANK);
			boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);
			binList = getBinTxnListByDenom(bankReceipt, user);
			binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, bankReceipt.getBundle(), true,
					CurrencyType.UNPROCESS, CashSource.OTHERBANK);
			otherBankReceiptList = UtilityJpa.getICMCReceipt(bankReceipt, binTxs, user, capacityList, binMasterList,
					boxMasterList);

			for (BankReceipt otherBankTemp : otherBankReceiptList) {
				bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(otherBankTemp.getBundle());
			}
			if (bundleToBeKeptInICMC.compareTo(bankReceipt.getBundle()) == 0) {
				addInTransactionsForBox(user, binTxs);
				addICMCReceipt(otherBankReceiptList);
			} else {
				throw new BaseGuiException("Space is not available in BOX for " + bankReceipt.getBundle()
						+ " bundles in " + bankReceipt.getDenomination() + " denomination");
			}
		}

		else if (bankReceipt.getBinCategoryType() == BinCategoryType.PROCESSING) {
			bankReceipt = UtilityJpa.getBankReceiptForProcessing(bankReceipt, user);
			otherBankReceiptList.add(bankReceipt);
			addICMCReceipt(otherBankReceiptList);
			Indent indent = UtilityJpa.getIndent(bankReceipt, user);
			this.insertInIndent(indent);

		}

		// addICMCReceipt(otherBankReceiptList);

		// History Code
		List<History> historyList = new ArrayList<History>();
		History history = null;
		for (BankReceipt branch : otherBankReceiptList) {
			history = new History();
			history.setBinNumber(branch.getBinNumber());
			history.setDenomination(branch.getDenomination());
			history.setReceiveBundle(branch.getBundle());
			history.setWithdrawalBundle(BigDecimal.ZERO);
			history.setInsertBy(branch.getInsertBy());
			history.setUpdateBy(branch.getUpdateBy());
			history.setInsertTime(branch.getInsertTime());
			history.setUpdateTime(branch.getInsertTime());
			history.setDepositOrWithdrawal("DEPOSIT");
			history.setType("U");
			history.setStatus(OtherStatus.REQUESTED);
			history.setIcmcId(branch.getIcmcId());
			historyList.add(history);
		}

		branchHistory(historyList);

		// updating machine allocation for return back to vault
		if (bankReceipt.isFromProcessingRoom()) {
			List<MachineAllocation> pendingBundleListFromMachineAllocation = cashReceiptJpaDao
					.getPendingBundleFromMachineAllocationForReturnBackToVault(user.getIcmcId(),
							bankReceipt.getDenomination(), CashSource.OTHERBANK);

			List<MachineAllocation> eligiblePendingBundleList = UtilityMapper
					.getEligibleBundleListForMachineAllocationDuringReturnBackToVault(
							pendingBundleListFromMachineAllocation, bankReceipt.getBundle(),
							bankReceipt.isFromProcessingRoom(), user);

			if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
				throw new BaseGuiException(
						"Required bundles are not available for denomination:" + bankReceipt.getDenomination());
			}

			for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
				this.updatePendingBundleInMachineAllocationForReturnBackToVault(machineAllocation);
			}
		}

		return otherBankReceiptList;
	}

	@Override
	public List<ICMC> getICMCName() {
		return cashReceiptJpaDao.getICMCName();
	}

	@Override
	public String getBranchNameBySolId(String solId) {
		return cashReceiptJpaDao.getBranchBySolId(solId);
	}

	@Override
	public ICMC getICMCBySolId(String solId) {
		return cashReceiptJpaDao.getICMCBySolId(solId);
	}

	@Override
	public ICMC getICMCByName(String name) {
		return cashReceiptJpaDao.getICMCByName(name);
	}

	@Override
	public List<BranchReceipt> getBrachFromBranchReceipt(BigInteger icmcId) {
		return cashReceiptJpaDao.getBrachFromBranchReceipt(icmcId);
	}

	@Override
	public List<Tuple> getVoucherRecord(String solId) {
		return cashReceiptJpaDao.getVoucherRecord(solId);
	}

	@Override
	public List<Tuple> getIRVReportRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getIRVReportRecord(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getIRVReportRecordForOtherBanks(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getIRVReportRecordForOtherBanks(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getIRVReportRecordsForDSb(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getIRVReportRecordsForDSb(icmcId, sDate, eDate);
	}

	@Override
	public List<DSBAccountDetail> getDSBAccountDetail(BigInteger icmcId) {
		return cashReceiptJpaDao.getDSBAccountDetail(icmcId);
	}

	@Override
	public List<String> getAccountNumberForDSB(String vendorName, BigInteger icmcId) {
		return cashReceiptJpaDao.getAccountNumberForDSB(vendorName, icmcId);
	}

	@Override
	public CoinsSequence getSequence(BigInteger icmcId, int denomination) {
		return cashReceiptJpaDao.getSequence(icmcId, denomination);
	}

	@Override
	public boolean insertInCoinSequence(CoinsSequence coinSequence) {
		return cashReceiptJpaDao.insertInCoinSequence(coinSequence);
	}

	@Override
	public boolean insertCoinSequenceInBinTxn(BinTransaction binTxn) {
		return cashReceiptJpaDao.insertCoinSequenceInBinTxn(binTxn);
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenomAfterCountingFresh(FreshFromRBI fresh, User user) {
		BinTransaction binTxTemp = new BinTransaction();
		binTxTemp.setIcmcId(user.getIcmcId());
		binTxTemp.setReceiveBundle(fresh.getBundle());
		binTxTemp.setDenomination(fresh.getDenomination());
		binTxTemp.setCashSource(CashSource.RBI);
		return this.getBinTxnListByDenom(binTxTemp);
	}

	@Override
	public List<FreshFromRBI> processFreshFromRBIAfterCounting(FreshFromRBI freshfromRBI, User user) {

		List<BinCapacityDenomination> capacityList = new ArrayList<>();
		List<BinMaster> binMasterList = new ArrayList<>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();
		List<FreshFromRBI> freshList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();

		BigDecimal bundleFromTxn = BigDecimal.ZERO;

		if (freshfromRBI.getBinCategoryType() == BinCategoryType.BIN) {
			capacityList = this.getMaxBundleCapacity(freshfromRBI.getDenomination(), freshfromRBI.getCurrencyType());

			binMasterList = getPriorityBinListByTypeForFresh(user);
			binList = getBinTxnListByDenom(freshfromRBI, user);

			binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, freshfromRBI.getBundle(), true,
					capacityList, CurrencyType.FRESH, CashSource.RBI);

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(freshfromRBI.getBundle()) == 0) {
				freshList = UtilityJpa.getFreshFromRBIafterCounting(freshfromRBI, binTxs, user, capacityList,
						binMasterList, boxMasterList);
				addTransactions(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available in BIN for " + freshfromRBI.getCurrencyType()
						+ " Category and " + freshfromRBI.getDenomination() + " Denomination");
			}
		}

		if (freshfromRBI.getBinCategoryType() == BinCategoryType.BOX) {
			BoxMaster boxMaster = new BoxMaster();
			boxMaster.setIcmcId(user.getIcmcId());
			boxMaster.setDenomination(freshfromRBI.getDenomination());
			boxMaster.setCurrencyType(freshfromRBI.getCurrencyType());
			boxMaster.setCashSource(CashSource.RBI);
			boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);
			binList = getBinTxnListByDenom(freshfromRBI, user);

			binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, freshfromRBI.getBundle(), true,
					CurrencyType.FRESH, CashSource.RBI);

			for (BinTransaction binTxn : binTxs) {
				bundleFromTxn = bundleFromTxn.add(binTxn.getCurrentBundle());
			}
			if (bundleFromTxn.compareTo(freshfromRBI.getBundle()) == 0) {
				freshList = UtilityJpa.getFreshFromRBIafterCounting(freshfromRBI, binTxs, user, capacityList,
						binMasterList, boxMasterList);
				addInTransactionsForBox(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available in BOX for " + freshfromRBI.getCurrencyType()
						+ " Category and " + freshfromRBI.getDenomination() + " Denomination");
			}
		}
		addFreshFromRBI(freshList);

		List<Indent> indentList = cashReceiptJpaDao.getIndentDataForBundleCalculation(user.getIcmcId(),
				freshfromRBI.getDenomination());

		BigDecimal bundleRequired = freshfromRBI.getBundle();

		for (Indent indent : indentList) {
			BigDecimal availableBundle = indent.getPendingBundleRequest();
			if (availableBundle.compareTo(bundleRequired) > 0) {
				indent.setPendingBundleRequest(availableBundle.subtract(bundleRequired));
				cashReceiptJpaDao.updateIndentAfterFresh(indent);
				break;
			} else if (availableBundle.compareTo(BigDecimal.ZERO) > 0) {
				indent.setStatus(OtherStatus.PROCESSED);
				indent.setPendingBundleRequest(BigDecimal.ZERO);
				bundleRequired = bundleRequired.subtract(availableBundle);
				cashReceiptJpaDao.updateIndentAfterFresh(indent);
			}

		}
		return freshList;
	}

	@Override
	public List<Tuple> getDataFromIndentForFreshProcessing(BigInteger icmcId) {
		return cashReceiptJpaDao.getDataFromIndentForFreshProcessing(icmcId);
	}

	@Override
	public List<Indent> getIndentDataForBundleCalculation(BigInteger icmcId, int denomination) {
		return cashReceiptJpaDao.getIndentDataForBundleCalculation(icmcId, denomination);
	}

	@Override
	public boolean updateIndentAfterFresh(Indent indent) {
		return cashReceiptJpaDao.updateIndentAfterFresh(indent);
	}

	@Override
	public List<BoxMaster> getBoxFromBoxMaster(BoxMaster boxMaster) {
		return cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);
	}

	@Override
	public boolean saveBox(BoxMaster boxMaster) {
		return cashReceiptJpaDao.saveBox(boxMaster);
	}

	@Override
	public List<BoxMaster> boxMasterDetails(BigInteger icmcId) {
		return cashReceiptJpaDao.boxMasterDetails(icmcId);
	}

	@Override
	public boolean updateBoxMaster(BoxMaster boxMaster) {
		return cashReceiptJpaDao.updateBoxMaster(boxMaster);
	}

	@Override
	public BoxMaster getBoxDetailsById(Long id) {
		return cashReceiptJpaDao.getBoxDetailsById(id);
	}

	@Override
	public boolean updateBoxDetails(BoxMaster boxMaster) {
		return cashReceiptJpaDao.updateBoxDetails(boxMaster);
	}

	@Override
	public BranchReceipt getBranchReceiptRecordById(Long id, BigInteger icmcId) {
		return cashReceiptJpaDao.getBranchReceiptRecordById(id, icmcId);
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdate(BranchReceipt branchReceiptDb, BigInteger icmcId) {
		return cashReceiptJpaDao.getBinTxnRecordForUpdate(branchReceiptDb, icmcId);
	}

	@Override
	@Transactional
	public List<BranchReceipt> processForUpdatingShrinkEntry(BinTransaction binTxn, BranchReceipt branchReceipt,
			BranchReceipt branchReceiptDb, User user) {
		boolean isAllSuccess = false;
		List<BranchReceipt> branchReceiptList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal receiveBundleFromUI = branchReceipt.getBundle();
		int denomFromUI = branchReceipt.getDenomination();
		BigDecimal updatedReceiveBundleForTxn = binTxn.getReceiveBundle().subtract(branchReceiptDb.getBundle());

		binTxn.setReceiveBundle(updatedReceiveBundleForTxn);
		if (binTxn.getReceiveBundle().equals(BigDecimal.ZERO)) {
			binTxn.setStatus(BinStatus.EMPTY);
		}
		isAllSuccess = this.updateInBinTxn(binTxn);
		if (isAllSuccess) {
			branchReceipt.setBundle(branchReceiptDb.getBundle());
			branchReceipt.setStatus(OtherStatus.CANCELLED);
			branchReceipt.setFilepath(branchReceiptDb.getFilepath());
			UtilityJpa.settingBranchReceiptParametersForUpdation(branchReceipt, branchReceiptDb, user, now);
			cashReceiptJpaDao.updateBranchReceipt(branchReceipt);
		}

		branchReceipt.setDenomination(denomFromUI);
		branchReceipt.setBundle(receiveBundleFromUI);
		branchReceiptList = this.processBranchReceipt(branchReceipt, user);

		return branchReceiptList;
	}

	@Override
	public DSB getDSBReceiptRecordById(Long id, BigInteger icmcId) {
		return cashReceiptJpaDao.getDSBReceiptRecordById(id, icmcId);
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdatingDSB(DSB dsbReceiptDb, BigInteger icmcId) {
		return cashReceiptJpaDao.getBinTxnRecordForUpdatingDSB(dsbReceiptDb, icmcId);
	}

	@Override
	public boolean processForUpdatingDSBReceipt1(DSB dsbdb, User user) {
		return cashReceiptJpaDao.updateDSB(dsbdb);
	}

	@Override
	public List<DSB> processDSBforEdit(DSB dsb, User user) {

		List<DSB> dsbList = new ArrayList<>();

		List<BinMaster> binMasterList = new ArrayList<>();
		List<BoxMaster> boxMasterList = new ArrayList<>();
		List<BinTransaction> binList = new ArrayList<>();
		List<BinTransaction> binTxs = new ArrayList<>();

		BigDecimal bundleToBeKeptInICMC = BigDecimal.ZERO;
		List<BinCapacityDenomination> capacityList = this.getMaxBundleCapacity(dsb.getDenomination(),
				dsb.getCurrencyType());

		if (dsb.getProcessingOrVault().equalsIgnoreCase("Vault")
				|| dsb.getProcessingOrVault().equalsIgnoreCase("BIN")) {
			binMasterList = getPriorityBinListByType(user);

			binList = getBinTxnListByDenom(dsb, user);
			binTxs = UtilityJpa.getBinByCurrencyProcessType(binList, binMasterList, dsb.getBundle(), true, capacityList,
					CurrencyType.UNPROCESS, CashSource.DSB);
			dsbList = UtilityJpa.getDSB(dsb, binTxs, user, capacityList, binMasterList, boxMasterList);
			for (DSB dsbTemp : dsbList) {
				bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(dsbTemp.getBundle());
			}
			if (bundleToBeKeptInICMC.compareTo(dsb.getBundle()) == 0) {
				addTransactions(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available for " + dsb.getBundle() + " bundles in "
						+ dsb.getDenomination() + " denomination");
			}
		}

		else if (dsb.getProcessingOrVault().equalsIgnoreCase("BOX")) {
			BoxMaster boxMaster = new BoxMaster();
			boxMaster.setIcmcId(user.getIcmcId());
			boxMaster.setDenomination(dsb.getDenomination());
			boxMaster.setCurrencyType(dsb.getCurrencyType());
			boxMaster.setCashSource(CashSource.DSB);
			boxMasterList = cashReceiptJpaDao.getBoxFromBoxMaster(boxMaster);
			binList = getBinTxnListByDenom(dsb, user);
			binTxs = UtilityJpa.getBoxByCurrencyProcessType(binList, boxMasterList, dsb.getBundle(), true,
					CurrencyType.UNPROCESS, CashSource.DSB);
			dsbList = UtilityJpa.getDSB(dsb, binTxs, user, capacityList, binMasterList, boxMasterList);
			for (DSB dsbTemp : dsbList) {
				bundleToBeKeptInICMC = bundleToBeKeptInICMC.add(dsbTemp.getBundle());
			}
			if (bundleToBeKeptInICMC.compareTo(dsb.getBundle()) == 0) {
				addInTransactionsForBox(user, binTxs);
			} else {
				throw new BaseGuiException("Space is not available for " + dsb.getBundle() + " bundles in "
						+ dsb.getDenomination() + " denomination");
			}
		}

		else if (dsb.getProcessingOrVault().equalsIgnoreCase("processing")) {
			Indent indent = UtilityJpa.getIndent(dsb, user);
			this.insertInIndent(indent);
			dsb = UtilityJpa.getDSBForProcessing(dsb, user);
			dsbList.add(dsb);
		}

		addDSB(dsbList);

		// History Code
		List<History> historyList = new ArrayList<History>();
		History history = null;
		for (DSB branch : dsbList) {
			history = new History();
			history.setBinNumber(branch.getBin());
			history.setDenomination(branch.getDenomination());
			history.setReceiveBundle(branch.getBundle());
			history.setWithdrawalBundle(BigDecimal.ZERO);
			history.setInsertBy(branch.getInsertBy());
			history.setUpdateBy(branch.getUpdateBy());
			history.setInsertTime(branch.getInsertTime());
			history.setUpdateTime(branch.getInsertTime());
			history.setDepositOrWithdrawal("DEPOSIT");
			history.setType("U");
			history.setIcmcId(branch.getIcmcId());
			history.setStatus(OtherStatus.REQUESTED);
			historyList.add(history);
		}

		branchHistory(historyList);

		// updating machine allocation for return back to vault
		if (dsb.isFromProcessingRoom()) {
			List<MachineAllocation> pendingBundleListFromMachineAllocation = cashReceiptJpaDao
					.getPendingBundleFromMachineAllocationForReturnBackToVault(user.getIcmcId(), dsb.getDenomination(),
							CashSource.DSB);

			List<MachineAllocation> eligiblePendingBundleList = UtilityMapper
					.getEligibleBundleListForMachineAllocationDuringReturnBackToVault(
							pendingBundleListFromMachineAllocation, dsb.getBundle(), dsb.isFromProcessingRoom(), user);

			if (eligiblePendingBundleList == null || eligiblePendingBundleList.isEmpty()) {
				throw new BaseGuiException(
						"Required bundles are not available for denomination:" + dsb.getDenomination());
			}

			for (MachineAllocation machineAllocation : eligiblePendingBundleList) {
				this.updatePendingBundleInMachineAllocationForReturnBackToVault(machineAllocation);
			}
		}

		return dsbList;
	}

	@Override
	public List<DSB> processForUpdatingDSBReceipt(BinTransaction binTxn, DSB dsb, DSB dsbdb, User user) {
		boolean isAllSuccess = false;
		List<DSB> dsbReceiptList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal receiveBundleFromUI = dsb.getBundle();
		int denomFromUI = dsb.getDenomination();
		BigDecimal updatedReceiveBundleForTxn = binTxn.getReceiveBundle().subtract(dsbdb.getBundle());

		binTxn.setReceiveBundle(updatedReceiveBundleForTxn);
		if (binTxn.getReceiveBundle().equals(BigDecimal.ZERO)) {
			binTxn.setStatus(BinStatus.EMPTY);
		}
		isAllSuccess = this.updateInBinTxn(binTxn);
		if (isAllSuccess) {
			dsbdb.setStatus(OtherStatus.CANCELLED);
			dsbdb.setUpdateTime(now);
			dsbdb.setUpdateBy(user.getId());
			cashReceiptJpaDao.updateDSB(dsbdb);
		}
		dsb.setFilepath(dsbdb.getFilepath());
		dsb.setDenomination(denomFromUI);
		dsb.setBundle(receiveBundleFromUI);
		dsb.setProcessingOrVault(dsbdb.getProcessingOrVault());
		dsb.setReceiptSequence(dsbdb.getReceiptSequence());
		dsb.setCurrencyType(dsbdb.getCurrencyType());
		dsb.setBinCategoryType(dsbdb.getBinCategoryType());
		dsb.setCashSource(CashSource.DSB);

		dsb.setInsertTime(now);
		dsb.setUpdateTime(now);

		dsbReceiptList = this.processDSB(dsb, user);

		/*
		 * binTxn.setReceiveBundle(binTxn.getReceiveBundle().subtract(dsb.
		 * getBundle())); if (binTxn.getReceiveBundle().equals(BigDecimal.ZERO))
		 * { binTxn.setStatus(BinStatus.EMPTY); } isAllSuccess =
		 * this.updateInBinTxn(binTxn); if (isAllSuccess) {
		 * dsb.setStatus(OtherStatus.CANCELLED);
		 * cashReceiptJpaDao.updateDSB(dsb); } return isAllSuccess;
		 */
		return dsbReceiptList;
	}

	@Override
	public List<DSB> processForUpdatingIndentDSBReceipt(BinTransaction binTxn, DSB dsb, DSB dsbdb, Indent indent,
			User user) {
		List<DSB> dsbReceiptList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal receiveBundleFromUI = dsb.getBundle();
		int denomFromUI = dsb.getDenomination();
		// BigDecimal updatedReceiveBundleForTxn =
		// binTxn.getReceiveBundle().subtract(dsbdb.getBundle());
		indent.setStatus(OtherStatus.CANCELLED);
		cashReceiptJpaDao.updateIndent(indent);
		dsbdb.setStatus(OtherStatus.CANCELLED);
		cashReceiptJpaDao.updateDSB(dsbdb);

		dsb.setDenomination(denomFromUI);
		dsb.setBundle(receiveBundleFromUI);
		dsb.setProcessingOrVault(dsbdb.getProcessingOrVault());
		dsb.setReceiptSequence(dsbdb.getReceiptSequence());
		dsb.setCurrencyType(dsbdb.getCurrencyType());
		dsb.setBinCategoryType(BinCategoryType.PROCESSING);
		dsb.setCashSource(CashSource.DSB);
		dsb.setInsertTime(now);
		dsb.setUpdateTime(now);
		dsbReceiptList = this.processDSB(dsb, user);

		return dsbReceiptList;
	}

	@Override
	public DiversionIRV getDiversionIRVRecordById(Long id, BigInteger icmcId) {
		return cashReceiptJpaDao.getDiversionIRVRecordById(id, icmcId);
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdatingDiversionReceipt(DiversionIRV diversionIRV, BigInteger icmcId) {
		return cashReceiptJpaDao.getBinTxnRecordForUpdatingDiversionReceipt(diversionIRV, icmcId);
	}

	@Override
	public boolean processForUpdatingDiversionReceipt(BinTransaction binTxn, DiversionIRV diversionIRV, User user) {
		boolean isAllSuccess = false;
		binTxn.setReceiveBundle(binTxn.getReceiveBundle().subtract(diversionIRV.getBundle()));
		if (binTxn.getReceiveBundle().equals(BigDecimal.ZERO)) {
			binTxn.setStatus(BinStatus.EMPTY);
		}
		isAllSuccess = this.updateInBinTxn(binTxn);
		if (isAllSuccess) {
			diversionIRV.setStatus(OtherStatus.CANCELLED);
			cashReceiptJpaDao.updateDiversionIRV(diversionIRV);
		}
		return isAllSuccess;
	}

	@Override
	public BoxMaster isValidBox(BigInteger icmcId, String boxName) {
		return cashReceiptJpaDao.isValidBox(icmcId, boxName);
	}

	@Override
	public List<Tuple> getCashReceiptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getCashReceiptRecord(icmcId, sDate, eDate);
	}

	@Override
	public Integer getDSBReceiptSequence(BigInteger icmcId, DSB dsb) {
		Date todaysDate = UtilityJpa.getDateWithTimeZero();
		dsb.setReceiptDate(todaysDate);
		return cashReceiptJpaDao.getDSBReceiptSequence(icmcId, dsb);
	}

	@Override
	public BankReceipt getBankReceiptRecordById(Long id, BigInteger icmcId) {
		return cashReceiptJpaDao.getBankReceiptRecordById(id, icmcId);
	}

	@Override
	public FreshFromRBI getFreshFromRBIRecordById(Long id, BigInteger icmcId) {
		return cashReceiptJpaDao.getFreshFromRBIRecordById(id, icmcId);
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenomForProcessed(BinTransaction binTx) {
		return cashReceiptJpaDao.getBinTxnListByDenomForProcessed(binTx);
	}

	@Override
	public boolean branchHistory(List<History> history) {
		return cashReceiptJpaDao.branchHistory(history);
	}

	@Override
	public BinTransaction getBinTxnRecordForBankReceiptedit(BankReceipt bankReceiptDb, BigInteger icmcId) {
		return cashReceiptJpaDao.getBinTxnRecordForBankReceipteditBankReceipt(bankReceiptDb, icmcId);
	}

	@Override
	public List<BankReceipt> processForUpdatingBankReceipt(BinTransaction binTxn, BankReceipt otherBankReceipt,
			BankReceipt otherBankReceiptDb, User user) {
		boolean isAllSuccess = false;
		List<BankReceipt> bankReceiptList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal receiveBundleFromUI = otherBankReceipt.getBundle();
		int denomFromUI = otherBankReceipt.getDenomination();
		BigDecimal updatedReceiveBundleForTxn = binTxn.getReceiveBundle().subtract(otherBankReceiptDb.getBundle());

		binTxn.setReceiveBundle(updatedReceiveBundleForTxn);
		if (binTxn.getReceiveBundle().equals(BigDecimal.ZERO)) {
			binTxn.setStatus(BinStatus.EMPTY);
		}
		isAllSuccess = this.updateInBinTxn(binTxn);
		if (isAllSuccess) {
			otherBankReceiptDb.setStatus(4);
			// UtilityJpa.settingBankReceiptParametersForUpdation(otherBankReceipt,
			// otherBankReceiptDb, user, now);
			cashReceiptJpaDao.updateBankReceipt(otherBankReceiptDb);
		}
		otherBankReceipt.setIcmcId(user.getIcmcId());
		otherBankReceipt.setDenomination(denomFromUI);
		otherBankReceipt.setBundle(receiveBundleFromUI);
		otherBankReceipt.setRtgsUTRNo(otherBankReceiptDb.getRtgsUTRNo());
		otherBankReceipt.setBinCategoryType(BinCategoryType.BIN);
		otherBankReceipt.setCurrencyType(CurrencyType.UNPROCESS);
		otherBankReceipt.setInsertTime(now);
		otherBankReceipt.setUpdateTime(now);

		bankReceiptList = this.processBankReceipt(otherBankReceipt, user);

		return bankReceiptList;
	}

	@Override
	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashReceiptJpaDao.getIBITForIRV(icmcId, sDate, eDate);
	}

	@Override
	public String getLinkBranchSolID(long icmcId) {
		return cashReceiptJpaDao.getLinkBranchSolID(icmcId);
	}

	@Override
	public String getServicingICMC(String solId) {
		return cashReceiptJpaDao.getServicingICMC(solId);
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdateedit(BranchReceipt branchReceiptDb, BigInteger icmcId) {
		return cashReceiptJpaDao.getBinTxnRecordForUpdateedit(branchReceiptDb, icmcId);
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdateBox(BoxMaster boxmasterDb, BigInteger icmcId) {
		return cashReceiptJpaDao.getBinTxnRecordForUpdateBox(boxmasterDb, icmcId);
	}

	@Override
	public List<DiversionIRV> processForUpdatingIndentIVRReceipt(DiversionIRV diversionIRV, DiversionIRV diversionIRVDB,
			Indent indentdb, User user) {
		List<DiversionIRV> irvReceiptList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal receiveBundleFromUI = diversionIRV.getBundle();
		int denomFromUI = diversionIRV.getDenomination();
		// BigDecimal updatedReceiveBundleForTxn =
		// binTxn.getReceiveBundle().subtract(dsbdb.getBundle());
		indentdb.setStatus(OtherStatus.CANCELLED);
		cashReceiptJpaDao.updateIndent(indentdb);
		diversionIRVDB.setStatus(OtherStatus.CANCELLED);
		cashReceiptJpaDao.updateIRV(diversionIRVDB);

		diversionIRV.setDenomination(denomFromUI);
		diversionIRV.setBundle(receiveBundleFromUI);

		diversionIRV.setInsertBy(user.getId());
		diversionIRV.setUpdateBy(user.getId());
		diversionIRV.setInsertTime(now);
		diversionIRV.setUpdateTime(now);
		diversionIRV.setOrderDate(diversionIRVDB.getOrderDate());
		diversionIRV.setBinCategoryType(BinCategoryType.PROCESSING);
		diversionIRV.setCurrencyType(diversionIRVDB.getCurrencyType());
		diversionIRV.setProcessedOrUnprocessed(diversionIRVDB.getProcessedOrUnprocessed());
		irvReceiptList = this.processDiversionIRV(diversionIRV, user);

		return irvReceiptList;
	}
}