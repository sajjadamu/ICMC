package com.chest.currency.jpa.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.AuditorIndent;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BinTransactionBOD;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CashTransfer;
import com.chest.currency.entity.model.ChestMaster;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.Discrepancy;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.History;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.QAssignVaultCustodian;
import com.chest.currency.entity.model.QAuditorIndent;
import com.chest.currency.entity.model.QAuditorProcess;
import com.chest.currency.entity.model.QBankReceipt;
import com.chest.currency.entity.model.QBinMaster;
import com.chest.currency.entity.model.QBinRegister;
import com.chest.currency.entity.model.QBinTransaction;
import com.chest.currency.entity.model.QBinTransactionBOD;
import com.chest.currency.entity.model.QBoxMaster;
import com.chest.currency.entity.model.QBranch;
import com.chest.currency.entity.model.QBranchReceipt;
import com.chest.currency.entity.model.QCRA;
import com.chest.currency.entity.model.QCRAAllocation;
import com.chest.currency.entity.model.QCRAAllocationLog;
import com.chest.currency.entity.model.QCRALog;
import com.chest.currency.entity.model.QCashTransfer;
import com.chest.currency.entity.model.QChestMaster;
import com.chest.currency.entity.model.QCoinsSequence;
import com.chest.currency.entity.model.QCustodianKeySet;
import com.chest.currency.entity.model.QDSB;
import com.chest.currency.entity.model.QDefineKeySet;
import com.chest.currency.entity.model.QDiscrepancy;
import com.chest.currency.entity.model.QDiscrepancyAllocation;
import com.chest.currency.entity.model.QDiversionIRV;
import com.chest.currency.entity.model.QDiversionORV;
import com.chest.currency.entity.model.QDiversionORVAllocation;
import com.chest.currency.entity.model.QFreshFromRBI;
import com.chest.currency.entity.model.QHistory;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.entity.model.QIndent;
import com.chest.currency.entity.model.QMachineAllocation;
import com.chest.currency.entity.model.QMutilated;
import com.chest.currency.entity.model.QORV;
import com.chest.currency.entity.model.QORVAllocation;
import com.chest.currency.entity.model.QOtherBank;
import com.chest.currency.entity.model.QOtherBankAllocation;
import com.chest.currency.entity.model.QProcess;
import com.chest.currency.entity.model.QProcessBundleForCRAPayment;
import com.chest.currency.entity.model.QRegionSummary;
import com.chest.currency.entity.model.QSASAllocation;
import com.chest.currency.entity.model.QSas;
import com.chest.currency.entity.model.QSoiledRemittance;
import com.chest.currency.entity.model.QSoiledRemittanceAllocation;
import com.chest.currency.entity.model.QSummary;
import com.chest.currency.entity.model.QSuspenseOpeningBalance;
import com.chest.currency.entity.model.QTrainingRegister;
import com.chest.currency.entity.model.RegionSummary;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.Summary;
import com.chest.currency.entity.model.TrainingRegister;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.YesNo;
import com.chest.currency.util.UtilityJpa;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class BinDashBoardJpaDaoImpl implements BinDashBoardJpaDao {
	private static final Logger LOG = LoggerFactory.getLogger(BinDashBoardJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	private JPAQuery getFromQueryForBinDashboard() {
		JPAQuery jpaQuery = new JPAQuery(em);
		// jpaQuery.from(QBinMaster.binMaster, QColor.color);
		jpaQuery.from(QBinMaster.binMaster);
		return jpaQuery;
	}

	@Override
	public List<BinMaster> getBinNumAndColorCode(BigInteger icmcId) {
		LOG.info("BIN DASH BOARD");
		JPAQuery jpaQuery = getFromQueryForBinDashboard();
		jpaQuery.where(QBinMaster.binMaster.icmcId.eq(icmcId));
		List<BinMaster> binList = jpaQuery.list(QBinMaster.binMaster);
		// List<BinMaster> binList =
		// jpaQuery.list(QBinMaster.binMaster.firstPriority,QColor.color.colorCode);

		// UtilityJpa.setBinColor(binList);

		return binList;
	}

	@Override
	public List<BinTransaction> getAvailableCapacity(String bin, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.binNumber.equalsIgnoreCase(bin)
				.and(QBinTransaction.binTransaction.icmcId.eq(icmcId))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		List<BinTransaction> list = jpaQuery.list(QBinTransaction.binTransaction);

		return list;
	}

	private JPAQuery getFromQueryForBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForRemittanceReceived() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QFreshFromRBI.freshFromRBI);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForRemittanceSent() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForBranchReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForDSB() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDSB.dSB);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForDiversion() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionIRV.diversionIRV);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForOtherBank() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBankReceipt.bankReceipt);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForCRA() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRAAllocation.cRAAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForOtherBankAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QOtherBankAllocation.otherBankAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForDiversionORV() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionORVAllocation.diversionORVAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForBranch() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSASAllocation.sASAllocation);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getRecordForSummary(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();

		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.binType);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.desc());

		List<Tuple> summaryList = jpaQuery.list(QBinTransaction.binTransaction.binType,
				QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.receiveBundle.sum());
		return summaryList;
	}

	@Override
	public List<ICMC> getICMCName() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}

	@Override
	public List<BinTransaction> searchBins(CurrencyType binType, int denomination, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		if (binType.equals(CurrencyType.COINS))
			jpaQuery.where(QBinTransaction.binTransaction.cashType.eq(CashType.COINS));
		else
			jpaQuery.where(QBinTransaction.binTransaction.binType.eq(binType));
		List<BinTransaction> list = jpaQuery.list(QBinTransaction.binTransaction);
		UtilityJpa.setBinColorForTxn(list);
		return list;
	}

	@Override
	public List<BinTransaction> getAllNonEmptyBins(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		jpaQuery.orderBy(QBinTransaction.binTransaction.binType.asc());
		List<BinTransaction> list = jpaQuery.list(QBinTransaction.binTransaction);
		UtilityJpa.setBinColorForTxn(list);
		return list;
	}

	@Override
	public List<BinTransaction> getAllNonEmptyBins(BigInteger icmcId, CurrencyType cType) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binType.eq(cType)));
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.asc());
		List<BinTransaction> list = jpaQuery.list(QBinTransaction.binTransaction);
		UtilityJpa.setBinColorForTxn(list);
		return list;
	}

	private JPAQuery getFromQueryForBinMaster() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinMaster.binMaster);
		return jpaQuery;
	}

	@Override
	public List<BinMaster> viewBinMaster(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinMaster();
		jpaQuery.where(QBinMaster.binMaster.icmcId.eq(icmcId));
		List<BinMaster> list = jpaQuery.list(QBinMaster.binMaster);
		return list;
	}

	@Override
	public boolean insertDataInBinMaster(BinMaster binMaster) {
		LOG.info("INSERT NEW BIN");
		em.persist(binMaster);
		return true;
	}

	private JPAQuery getFromQueryForFIFO() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction).orderBy(QBinTransaction.binTransaction.insertTime.asc());
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getRecordForFIFO(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForFIFO();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId));
		List<BinTransaction> fifoList = jpaQuery.list(QBinTransaction.binTransaction);
		return fifoList;
	}

	private JPAQuery getFromQueryForProcessList() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QProcess.process);
		return jpaQuery;
	}

	public List<Process> getProcessListAtm(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForProcessList();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.currencyType.eq(CurrencyType.ATM)));
		List<Process> listProcess = jpaQuery.list(QProcess.process);
		return listProcess;
	}

	public List<Process> getProcessListIssuable(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForProcessList();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.currencyType.eq(CurrencyType.ISSUABLE)));
		List<Process> listProcess = jpaQuery.list(QProcess.process);
		return listProcess;
	}

	public List<Process> getProcessListSoiled(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForProcessList();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.currencyType.eq(CurrencyType.SOILED)));
		List<Process> listProcess = jpaQuery.list(QProcess.process);
		return listProcess;
	}

	private JPAQuery getFromQueryForMachineAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineAllocation.machineAllocation);
		return jpaQuery;
	}

	@Override
	public List<MachineAllocation> getMachineAllocationForMachineWiseStatus(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId));
		List<MachineAllocation> machineList = jpaQuery.list(QMachineAllocation.machineAllocation);
		return machineList;
	}

	public boolean UploadBinMaster(List<BinMaster> binMasterList, BinMaster binMaster) {
		for (BinMaster binMasterTemp : binMasterList) {
			binMasterTemp.setIsAllocated(0);
			binMasterTemp.setIcmcId(binMaster.getIcmcId());
			binMasterTemp.setInsertBy(binMaster.getInsertBy());
			binMasterTemp.setUpdateBy(binMaster.getUpdateBy());
			Calendar now = Calendar.getInstance();
			binMasterTemp.setInsertTime(now);
			binMasterTemp.setUpdateTime(now);
			em.persist(binMasterTemp);
		}
		return true;
	}

	@Override
	public List<BinTransaction> recordForDailyBinRecon(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.updateTime.between(sDate, eDate)));
		List<BinTransaction> binTxnListForDailyBinRecon = jpaQuery.list(QBinTransaction.binTransaction);
		return binTxnListForDailyBinRecon;
	}

	@Override
	public float getReceiveBundleForDailyReconBin(BinTransaction binTransaction) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.id.eq(binTransaction.getId()));
		float bundle = jpaQuery.singleResult(QBinTransaction.binTransaction.receiveBundle).floatValue();
		return bundle;
	}

	private JPAQuery getFromQueryForgetZoneWiseSummary() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSummary.summary);
		return jpaQuery;
	}

	@Override
	public List<Summary> getZoneWiseSummaryList() {
		JPAQuery jpaQuery = getFromQueryForgetZoneWiseSummary();
		List<Summary> zoneWiseSummaryList = jpaQuery.list(QSummary.summary);
		return zoneWiseSummaryList;
	}

	@Override
	public List<Tuple> getRecordCoinsForSummary(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.COINS))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(BinCategoryType.BAG)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.binType,
				QBinTransaction.binTransaction.cashType);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.desc());

		List<Tuple> summaryforCoinList = jpaQuery.list(QBinTransaction.binTransaction.binType,
				QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.receiveBundle.sum());

		return summaryforCoinList;
	}

	@Override
	public BinMaster isValidBin(BigInteger icmcId, String binNumber) {
		JPAQuery jpaQuery = getFromQueryForBinMaster();
		jpaQuery.where(
				QBinMaster.binMaster.icmcId.eq(icmcId).and(QBinMaster.binMaster.binNumber.equalsIgnoreCase(binNumber)));
		BinMaster dbBinName = jpaQuery.singleResult(QBinMaster.binMaster);
		return dbBinName;
	}

	private JPAQuery getFromQueryForBinDashboardFromBinTransaction() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYes(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinDashboardFromBinTransaction();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES))
				.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes)));
		jpaQuery.orderBy(QBinTransaction.binTransaction.binNumber.asc());
		List<BinTransaction> binListFromTransaction = jpaQuery.list(QBinTransaction.binTransaction);
		UtilityJpa.setBinColor(binListFromTransaction);
		return binListFromTransaction;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBox(BigInteger icmcId) {
		LOG.info("BIN DASH BOARD FOR BIN");
		JPAQuery jpaQuery = getFromQueryForBinDashboardFromBinTransaction();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)
						.and(QBinTransaction.binTransaction.binCategoryType.eq(BinCategoryType.BOX)
								.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes)))));
		jpaQuery.orderBy(QBinTransaction.binTransaction.binNumber.asc());
		List<BinTransaction> binListFromTransaction = jpaQuery.list(QBinTransaction.binTransaction);
		// LOG.info("BIN DASH BOARD LIS OF BIN "+binListFromTransaction);
		UtilityJpa.setBinColor(binListFromTransaction);
		return binListFromTransaction;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBin(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinDashboardFromBinTransaction();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)
						.and(QBinTransaction.binTransaction.binCategoryType.eq(BinCategoryType.BIN)
								.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes)))));
		jpaQuery.orderBy(QBinTransaction.binTransaction.binNumber.asc());
		List<BinTransaction> binListFromTransaction = jpaQuery.list(QBinTransaction.binTransaction);
		UtilityJpa.setBinColor(binListFromTransaction);
		return binListFromTransaction;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBag(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinDashboardFromBinTransaction();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)
						.and(QBinTransaction.binTransaction.binCategoryType.eq(BinCategoryType.BAG)
								.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes)))));
		jpaQuery.orderBy(QBinTransaction.binTransaction.binNumber.asc());
		List<BinTransaction> bagListFromTransaction = jpaQuery.list(QBinTransaction.binTransaction);
		UtilityJpa.setBinColor(bagListFromTransaction);
		return bagListFromTransaction;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedNo(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinDashboardFromBinTransaction();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.verified.eq(YesNo.No)));
		List<BinTransaction> binListFromTransaction = jpaQuery.list(QBinTransaction.binTransaction);
		// UtilityJpa.setBinColor(binListFromTransaction);
		return binListFromTransaction;
	}

	@Override
	public List<Tuple> getOpeningBalanceForTO2Report(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(cashType))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.cashType);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum().multiply(1000),
				QBinTransaction.binTransaction.cashType);
		return summaryList;
	}

	@Override
	public List<Tuple> getOpeningBalanceForIO2ReportFromIndent(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType) {
		JPAQuery jpaQuery = getFromQueryFromIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId)
				/* .and(QIndent.indent.insertTime.between(sDate, eDate)) */
				.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED)));
		jpaQuery.groupBy(QIndent.indent.denomination);
		jpaQuery.orderBy(QIndent.indent.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QIndent.indent.denomination,
				QIndent.indent.bundle.sum().multiply(1000));
		return summaryList;
	}

	@Override
	public List<Tuple> getCoinsOpeningBalanceForIO2Report(BigInteger icmcId, CashType cashType) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(cashType)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.cashType);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum(), QBinTransaction.binTransaction.cashType);
		return summaryList;
	}

	@Override
	public List<Tuple> getRemittanceReceivedForFresh(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForRemittanceReceived();
		jpaQuery.where(QFreshFromRBI.freshFromRBI.icmcId.eq(icmcId)
				.and(QFreshFromRBI.freshFromRBI.binCategoryType.ne(BinCategoryType.BAG))
				.and(QFreshFromRBI.freshFromRBI.potdarStatus.ne("CANCELLED"))
				.and(QFreshFromRBI.freshFromRBI.insertTime.between(sDate, eDate))
				.and(QFreshFromRBI.freshFromRBI.cashType.eq(CashType.NOTES)));
		jpaQuery.groupBy(QFreshFromRBI.freshFromRBI.denomination, QFreshFromRBI.freshFromRBI.cashType,
				QFreshFromRBI.freshFromRBI.rbiOrderNo);
		jpaQuery.orderBy(QFreshFromRBI.freshFromRBI.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QFreshFromRBI.freshFromRBI.denomination,
				QFreshFromRBI.freshFromRBI.bundle.sum().multiply(1000), QFreshFromRBI.freshFromRBI.cashType,
				QFreshFromRBI.freshFromRBI.rbiOrderNo);
		return summaryList;
	}

	@Override
	public boolean insertInBinTxnBOD(BinTransactionBOD binTransactionBod) {
		em.persist(binTransactionBod);
		return true;
	}

	private JPAQuery getFromQueryForBinTxnBOD() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransactionBOD.binTransactionBOD);
		return jpaQuery;
	}

	@Override
	public List<BinTransactionBOD> getDataFromBinTransactionBOD(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType) {
		JPAQuery jpaQuery = getFromQueryForBinTxnBOD();
		jpaQuery.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(icmcId)
				.and(QBinTransactionBOD.binTransactionBOD.cashType.eq(cashType))
				.and(QBinTransactionBOD.binTransactionBOD.currentVersion.eq("TRUE"))
		/*
		 * .and(QBinTransactionBOD.binTransactionBOD.insertTime.between(sDate,
		 * eDate))
		 */
		/* .and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)) */);
		List<BinTransactionBOD> summaryList = jpaQuery.list(QBinTransactionBOD.binTransactionBOD);
		return summaryList;
	}

	@Override
	public List<Tuple> getRemittanceSentForSoiled(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForRemittanceSent();
		jpaQuery.where(
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
						.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.ACCEPTED)
								.or(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status
										.eq(OtherStatus.RELEASED)))
						.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination);
		jpaQuery.orderBy(QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination,
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.bundle.sum().multiply(1000));
		return summaryList;
	}

	@Override
	public List<Tuple> getDepositForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.status.ne(OtherStatus.CANCELLED))
				.and(QBranchReceipt.branchReceipt.solId.isNotNull())
				.and(QBranchReceipt.branchReceipt.binCategoryType.ne(BinCategoryType.BAG))
				.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.denomination);
		jpaQuery.orderBy(QBranchReceipt.branchReceipt.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QBranchReceipt.branchReceipt.denomination,
				QBranchReceipt.branchReceipt.bundle.sum().multiply(1000));
		return summaryList;
	}

	@Override
	public List<Tuple> getDepositFromBranch(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.status.ne(OtherStatus.CANCELLED))
				.and(QBranchReceipt.branchReceipt.solId.isNotNull())
				.and(QBranchReceipt.branchReceipt.binCategoryType.ne(BinCategoryType.BAG))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(currencyType))
				.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.denomination);
		jpaQuery.orderBy(QBranchReceipt.branchReceipt.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QBranchReceipt.branchReceipt.denomination,
				QBranchReceipt.branchReceipt.bundle.sum().multiply(1000));
		return summaryList;
	}

	@Override
	public List<Tuple> getProcessFromProcessingOutPut(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForProcessList();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.currencyType.eq(currencyType))
				.and(QProcess.process.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QProcess.process.denomination);
		jpaQuery.orderBy(QProcess.process.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QProcess.process.denomination,
				QProcess.process.bundle.sum().multiply(1000));
		return summaryList;
	}

	@Override
	public List<Tuple> getProcessFromProcessingOutPut(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForProcessList();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QProcess.process.denomination);
		jpaQuery.orderBy(QProcess.process.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QProcess.process.denomination,
				QProcess.process.bundle.sum().multiply(1000));
		return summaryList;
	}

	@Override
	public List<Tuple> getProcessBundleProcessingOutPut(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForProcessList();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QProcess.process.denomination, QProcess.process.currencyType);
		jpaQuery.orderBy(QProcess.process.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QProcess.process.denomination, QProcess.process.currencyType,
				QProcess.process.bundle.sum());
		return summaryList;
	}

	@Override
	public List<Tuple> getDepositForDSB(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(QDSB.dSB.icmcId.eq(icmcId).and(QDSB.dSB.status.eq(OtherStatus.RECEIVED))
				.and(QDSB.dSB.name.isNotNull()).and(QDSB.dSB.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QDSB.dSB.denomination);
		jpaQuery.orderBy(QDSB.dSB.denomination.asc());
		List<Tuple> dsbList = jpaQuery.list(QDSB.dSB.denomination, QDSB.dSB.bundle.sum().multiply(1000));
		return dsbList;
	}

	@Override
	public List<Tuple> getDepositForDiversion(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiversion();
		jpaQuery.where(QDiversionIRV.diversionIRV.icmcId.eq(icmcId)
				.and(QDiversionIRV.diversionIRV.status.ne(OtherStatus.CANCELLED))
				.and(QDiversionIRV.diversionIRV.bankName.isNotNull())
				.and(QDiversionIRV.diversionIRV.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QDiversionIRV.diversionIRV.denomination, QDiversionIRV.diversionIRV.bankName,
				QDiversionIRV.diversionIRV.rbiOrderNo);
		jpaQuery.orderBy(QDiversionIRV.diversionIRV.denomination.asc());
		List<Tuple> diversionList = jpaQuery.list(QDiversionIRV.diversionIRV.denomination,
				QDiversionIRV.diversionIRV.bundle.sum().multiply(1000), QDiversionIRV.diversionIRV.bankName,
				QDiversionIRV.diversionIRV.rbiOrderNo);
		return diversionList;
	}

	@Override
	public List<Tuple> getDepositForOtherBank(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForOtherBank();
		jpaQuery.where(QBankReceipt.bankReceipt.icmcId.eq(icmcId).and(QBankReceipt.bankReceipt.status.eq(0))
				.and(QBankReceipt.bankReceipt.branch.isNotNull())
				.and(QBankReceipt.bankReceipt.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBankReceipt.bankReceipt.denomination);
		jpaQuery.orderBy(QBankReceipt.bankReceipt.denomination.asc());
		List<Tuple> otherBankReceiptList = jpaQuery.list(QBankReceipt.bankReceipt.denomination,
				QBankReceipt.bankReceipt.bundle.sum().multiply(1000));
		return otherBankReceiptList;
	}

	@Override
	public List<Tuple> getDepositForIndent(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		jpaQuery.where(
				QIndent.indent.icmcId.eq(icmcId)
						.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED)
								.or(QIndent.indent.status.eq(OtherStatus.PROCESSED)))
						.and(QIndent.indent.cashSource.ne(CashSource.RBI)).and(QIndent.indent.bin.isNotNull())
						.and(QIndent.indent.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QIndent.indent.denomination);
		List<Tuple> ibitList = jpaQuery.list(QIndent.indent.denomination, QIndent.indent.bundle.sum().multiply(1000));
		return ibitList;
	}

	@Override
	public List<Tuple> getWithdrawalForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.cashType.eq(CashType.NOTES))
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED))
				.and(QSASAllocation.sASAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QSASAllocation.sASAllocation.denomination);
		jpaQuery.orderBy(QSASAllocation.sASAllocation.denomination.asc());
		List<Tuple> SASAllocationList = jpaQuery.list(QSASAllocation.sASAllocation.denomination,
				QSASAllocation.sASAllocation.bundle.sum().multiply(1000));
		return SASAllocationList;
	}

	@Override
	public List<Tuple> getWithdrawalFromBranch(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.cashType.eq(CashType.NOTES))
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED))
				.and(QSASAllocation.sASAllocation.binType.eq(currencyType))
				.and(QSASAllocation.sASAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QSASAllocation.sASAllocation.denomination);
		jpaQuery.orderBy(QSASAllocation.sASAllocation.denomination.asc());
		List<Tuple> SASAllocationList = jpaQuery.list(QSASAllocation.sASAllocation.denomination,
				QSASAllocation.sASAllocation.bundle.sum().multiply(1000));
		return SASAllocationList;
	}

	@Override
	public List<Tuple> getWithdrawalForCRA(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.ACCEPTED)
						.or(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.RELEASED)))
				.and(QCRAAllocation.cRAAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QCRAAllocation.cRAAllocation.denomination);
		jpaQuery.orderBy(QCRAAllocation.cRAAllocation.denomination.asc());
		List<Tuple> otherCRAWithdrawalList = jpaQuery.list(QCRAAllocation.cRAAllocation.denomination,
				QCRAAllocation.cRAAllocation.bundle.sum().multiply(1000));
		return otherCRAWithdrawalList;
	}

	private JPAQuery getFromQueryForProcessBundleCraList() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QProcessBundleForCRAPayment.processBundleForCRAPayment);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getWithdrawalForProcessCRA(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleCraList();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.RELEASED))
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination);
		jpaQuery.orderBy(QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination.asc());
		List<Tuple> otherCRAWithdrawalList = jpaQuery.list(
				QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination,
				QProcessBundleForCRAPayment.processBundleForCRAPayment.bundle.sum().multiply(1000));
		return otherCRAWithdrawalList;
	}

	@Override
	public List<Tuple> getWithdrawalForProcessCRAById(long craId) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleCraList();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.craId.eq(craId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.RELEASED)));
		jpaQuery.groupBy(QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination);
		jpaQuery.orderBy(QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination.asc());
		List<Tuple> otherCRAWithdrawalList = jpaQuery.list(
				QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination,
				QProcessBundleForCRAPayment.processBundleForCRAPayment.bundle.sum().multiply(1000));
		return otherCRAWithdrawalList;
	}

	@Override
	public List<Tuple> getWithdrawalForDiversion(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiversionORV();
		jpaQuery.where(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(icmcId)
				.and(QDiversionORVAllocation.diversionORVAllocation.status.eq(OtherStatus.ACCEPTED)
						.or(QDiversionORVAllocation.diversionORVAllocation.status.eq(OtherStatus.RELEASED)))
				.and(QDiversionORVAllocation.diversionORVAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QDiversionORVAllocation.diversionORVAllocation.denomination);
		jpaQuery.orderBy(QDiversionORVAllocation.diversionORVAllocation.denomination.asc());
		List<Tuple> otherDiversionWithdrawalList = jpaQuery.list(
				QDiversionORVAllocation.diversionORVAllocation.denomination,
				QDiversionORVAllocation.diversionORVAllocation.bundle.sum().multiply(1000));
		return otherDiversionWithdrawalList;
	}

	@Override
	public List<Tuple> getWithdrawalForOtherBank(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForOtherBankAllocation();
		jpaQuery.where(QOtherBankAllocation.otherBankAllocation.icmcId.eq(icmcId)
				.and(QOtherBankAllocation.otherBankAllocation.status.eq(OtherStatus.ACCEPTED)
						.or(QOtherBankAllocation.otherBankAllocation.status.eq(OtherStatus.RELEASED)))
				.and(QOtherBankAllocation.otherBankAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QOtherBankAllocation.otherBankAllocation.denomination);
		jpaQuery.orderBy(QOtherBankAllocation.otherBankAllocation.denomination.asc());
		List<Tuple> otherOtherBankList = jpaQuery.list(QOtherBankAllocation.otherBankAllocation.denomination,
				QOtherBankAllocation.otherBankAllocation.bundle.sum().multiply(1000));
		return otherOtherBankList;
	}

	@Override
	public List<Tuple> getSoiledNotes(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.SOILED))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.asc());
		List<Tuple> soiledNotesList = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum().multiply(1000));
		return soiledNotesList;
	}

	@Override
	public List<Tuple> getAdditionalInfoFreshNotes(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.FRESH))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.asc());
		List<Tuple> soiledNotesList = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum().multiply(1000));
		return soiledNotesList;
	}

	@Override
	public List<Tuple> getMachineAllocationSummary(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.status.eq(OtherStatus.REQUESTED)));
		jpaQuery.groupBy(QMachineAllocation.machineAllocation.denomination);
		jpaQuery.orderBy(QMachineAllocation.machineAllocation.denomination.desc());

		List<Tuple> bundleListFromMachineAllocation = jpaQuery.list(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.pendingBundle.sum(),
				QMachineAllocation.machineAllocation.pendingBundle.sum().multiply(1000)
						.multiply(QMachineAllocation.machineAllocation.denomination));
		return bundleListFromMachineAllocation;
	}

	@Override
	public List<Tuple> getCurrentMachineAllocationSummary(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.insertTime.between(sDate, eDate))
				.and(QMachineAllocation.machineAllocation.status.eq(OtherStatus.REQUESTED)));
		jpaQuery.groupBy(QMachineAllocation.machineAllocation.denomination);
		jpaQuery.orderBy(QMachineAllocation.machineAllocation.denomination.desc());

		List<Tuple> bundleListFromMachineAllocation = jpaQuery.list(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.pendingBundle.sum(),
				QMachineAllocation.machineAllocation.pendingBundle.sum().multiply(1000)
						.multiply(QMachineAllocation.machineAllocation.denomination));
		return bundleListFromMachineAllocation;
	}

	private JPAQuery getFromQueryForRegionWiseSummary() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QRegionSummary.regionSummary);
		return jpaQuery;
	}

	@Override
	public List<RegionSummary> getRegionWiseSummaryList(String region) {
		JPAQuery jpaQuery = getFromQueryForRegionWiseSummary();
		jpaQuery.where(QRegionSummary.regionSummary.region.eq(region));
		List<RegionSummary> regionWiseSummaryList = jpaQuery.list(QRegionSummary.regionSummary);
		return regionWiseSummaryList;
	}

	@Override
	public List<Tuple> getGrandSummary() {
		JPAQuery jpaQuery = getFromQueryForRegionWiseSummary();
		jpaQuery.groupBy(QRegionSummary.regionSummary.cashType);
		List<Tuple> grandSummaryList = jpaQuery.list(QRegionSummary.regionSummary.totalValue.sum(),
				QRegionSummary.regionSummary.cashType);
		return grandSummaryList;
	}

	@Override
	public List<Tuple> getZoneWiseGrandSummary() {
		JPAQuery jpaQuery = getFromQueryForRegionWiseSummary();
		jpaQuery.groupBy(QRegionSummary.regionSummary.zone, QRegionSummary.regionSummary.cashType);
		List<Tuple> zoneWiseGrandSummaryList = jpaQuery.list(QRegionSummary.regionSummary.zone,
				QRegionSummary.regionSummary.totalValue.sum(), QRegionSummary.regionSummary.cashType);
		return zoneWiseGrandSummaryList;
	}

	@Override
	public List<Tuple> getFlatZoneSummaryList() {
		JPAQuery jpaQuery = getFromQueryForRegionWiseSummary();
		jpaQuery.groupBy(QRegionSummary.regionSummary.zone, QRegionSummary.regionSummary.binType);
		List<Tuple> flatZoneSummaryList = jpaQuery.list(QRegionSummary.regionSummary.zone,
				QRegionSummary.regionSummary.totalValue.sum(), QRegionSummary.regionSummary.binType);
		return flatZoneSummaryList;
	}

	@Override
	public boolean updateCurrentVersionStatus(BinTransactionBOD binTransactionBod) {
		QBinTransactionBOD qBinTransactionBOD = QBinTransactionBOD.binTransactionBOD;
		Long count = new JPAUpdateClause(em, qBinTransactionBOD)
				.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(binTransactionBod.getIcmcId())
						.and(QBinTransactionBOD.binTransactionBOD.currentVersion.eq("TRUE")))
				.set(qBinTransactionBOD.currentVersion, "FALSE").execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<Tuple> getCoinRemittanceReceivedForFresh(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForRemittanceReceived();
		jpaQuery.where(QFreshFromRBI.freshFromRBI.icmcId.eq(icmcId)
				.and(QFreshFromRBI.freshFromRBI.insertTime.between(sDate, eDate))
				.and(QFreshFromRBI.freshFromRBI.potdarStatus.ne("CANCELLED"))
				.and(QFreshFromRBI.freshFromRBI.cashType.eq(CashType.COINS)));
		jpaQuery.groupBy(QFreshFromRBI.freshFromRBI.denomination, QFreshFromRBI.freshFromRBI.cashType,
				QFreshFromRBI.freshFromRBI.rbiOrderNo);
		jpaQuery.orderBy(QFreshFromRBI.freshFromRBI.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QFreshFromRBI.freshFromRBI.denomination,
				QFreshFromRBI.freshFromRBI.noOfBags.sum(), QFreshFromRBI.freshFromRBI.cashType,
				QFreshFromRBI.freshFromRBI.rbiOrderNo);
		return summaryList;
	}

	@Override
	public List<Tuple> getCoinsWithdrawalForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.cashType.eq(CashType.COINS))
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED))
				.and(QSASAllocation.sASAllocation.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QSASAllocation.sASAllocation.denomination);
		jpaQuery.orderBy(QSASAllocation.sASAllocation.denomination.asc());
		List<Tuple> SASAllocationList = jpaQuery.list(QSASAllocation.sASAllocation.denomination,
				QSASAllocation.sASAllocation.bundle.sum());
		return SASAllocationList;
	}

	@Override
	public ICMC getICMCObj(BigInteger icmcId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		jpaQuery.where(QICMC.iCMC.id.eq(icmcId.longValue()));
		ICMC icmc = jpaQuery.singleResult(QICMC.iCMC);
		return icmc;
	}

	@Override
	public List<Tuple> getBranchDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.binCategoryType.ne(BinCategoryType.BAG))
				.and(QBranchReceipt.branchReceipt.status.ne(OtherStatus.CANCELLED))
				.and(QBranchReceipt.branchReceipt.branch.isNotNull())
				.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.srNumber, QBranchReceipt.branchReceipt.solId,
				QBranchReceipt.branchReceipt.branch, QBranchReceipt.branchReceipt.denomination);
		jpaQuery.orderBy(QBranchReceipt.branchReceipt.denomination.desc());
		// jpaQuery.list(QBranchReceipt.branchReceipt.srNumber,
		List<Tuple> branchDepositTupleList = jpaQuery.list(QBranchReceipt.branchReceipt.srNumber,
				QBranchReceipt.branchReceipt.solId, QBranchReceipt.branchReceipt.branch,
				QBranchReceipt.branchReceipt.denomination, QBranchReceipt.branchReceipt.bundle.sum().multiply(1000));
		return branchDepositTupleList;
	}

	@Override
	public List<Tuple> getDSBDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(QDSB.dSB.icmcId.eq(icmcId).and(QDSB.dSB.status.ne(OtherStatus.CANCELLED))
				.and(QDSB.dSB.name.isNotNull()).and(QDSB.dSB.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QDSB.dSB.receiptSequence, QDSB.dSB.receiptDate, QDSB.dSB.name, QDSB.dSB.accountNumber,
				QDSB.dSB.denomination);
		jpaQuery.orderBy(QDSB.dSB.denomination.desc());
		List<Tuple> dsbDepositTupleList = jpaQuery.list(QDSB.dSB.receiptSequence, QDSB.dSB.receiptDate, QDSB.dSB.name,
				QDSB.dSB.accountNumber, QDSB.dSB.denomination, QDSB.dSB.bundle.sum().multiply(1000));
		return dsbDepositTupleList;
	}

	@Override
	public List<Tuple> getBankDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForOtherBank();
		jpaQuery.where(QBankReceipt.bankReceipt.icmcId.eq(icmcId).and(QBankReceipt.bankReceipt.status.ne(4))
				.and(QBankReceipt.bankReceipt.branch.isNotNull())
				.and(QBankReceipt.bankReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBankReceipt.bankReceipt.bankName, QBankReceipt.bankReceipt.rtgsUTRNo,
				QBankReceipt.bankReceipt.denomination);
		jpaQuery.orderBy(QBankReceipt.bankReceipt.denomination.desc());
		List<Tuple> bankReceiptList = jpaQuery.list(QBankReceipt.bankReceipt.bankName,
				QBankReceipt.bankReceipt.rtgsUTRNo, QBankReceipt.bankReceipt.denomination,
				QBankReceipt.bankReceipt.bundle.sum().multiply(1000));
		return bankReceiptList;
	}

	@Override
	public List<BinTransactionBOD> getDataFromBinTxnBODForPreviousDate(BigInteger icmcId, Calendar sDate,
			Calendar eDate, CashType cashType) {
		JPAQuery jpaQuery = getFromQueryForBinTxnBOD();
		jpaQuery.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(icmcId)
				.and(QBinTransactionBOD.binTransactionBOD.cashType.eq(cashType))
				// .and(QBinTransactionBOD.binTransactionBOD.currentVersion.ne("TRUE"))
				.and(QBinTransactionBOD.binTransactionBOD.insertTime.between(sDate, eDate)));
		List<BinTransactionBOD> summaryList = jpaQuery.list(QBinTransactionBOD.binTransactionBOD);
		return summaryList;
	}

	@Override
	public List<BinTransactionBOD> getDataFromBinTxnBODForLastEntry(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType) {
		JPAQuery jpaQuery = getFromQueryForBinTxnBOD();
		jpaQuery.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(icmcId)
				.and(QBinTransactionBOD.binTransactionBOD.cashType.eq(cashType))
				.and(QBinTransactionBOD.binTransactionBOD.currentVersion.ne("TRUE")));
		jpaQuery.orderBy(QBinTransactionBOD.binTransactionBOD.updateTime.desc());
		jpaQuery.limit(1);
		List<BinTransactionBOD> summaryList = jpaQuery.list(QBinTransactionBOD.binTransactionBOD);
		return summaryList;
	}

	@Override
	public List<CRA> getCRAPaymentList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRAPayment();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId)
				.and(QCRA.cRA.status.eq(OtherStatus.RELEASED).and(QCRA.cRA.insertTime.between(sDate, eDate))));
		List<CRA> craPaymentList = jpaQuery.list(QCRA.cRA);
		return craPaymentList;
	}

	private JPAQuery getFromQueryForCRAPayment() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRA.cRA);
		return jpaQuery;
	}

	@Override
	public Calendar getInsertTimeForBranchReceipt(BigInteger icmcId, String solId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId).and(QBranchReceipt.branchReceipt.solId.eq(solId))
				.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		Calendar insertTime = jpaQuery.singleResult(QBranchReceipt.branchReceipt.insertTime);
		return insertTime;
	}

	@Override
	public Calendar getInsertTimeForDSBReceipt(BigInteger icmcId, Integer receiptSequence, Date receiptDate,
			Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(QDSB.dSB.icmcId.eq(icmcId).and(QDSB.dSB.receiptSequence.eq(receiptSequence))
				.and(QDSB.dSB.receiptDate.eq(receiptDate)).and(QDSB.dSB.insertTime.between(sDate, eDate)));
		Calendar insertTime = jpaQuery.singleResult(QDSB.dSB.insertTime);
		return insertTime;
	}

	@Override
	public List<BinTransaction> getBinWiseSummary(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)
						.and(QBinTransaction.binTransaction.receiveBundle.gt(0)
								.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES)
										.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes))))));
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.desc());
		List<BinTransaction> binSummaryList = jpaQuery.list(QBinTransaction.binTransaction);
		return binSummaryList;
	}

	@Override
	public List<Discrepancy> getDiscrepancyList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancy();

		jpaQuery.where(QDiscrepancy.discrepancy.icmcId.eq(icmcId)
				// .and(QDiscrepancy.discrepancy.status.ne(4))
				.and(QDiscrepancy.discrepancy.insertTime.between(sDate, eDate)));
		// code);
		List<Discrepancy> discrepancyList = jpaQuery.list(QDiscrepancy.discrepancy);
		return discrepancyList;
	}

	private JPAQuery getFromQueryForDiscrepancy() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiscrepancy.discrepancy);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForMutilated() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMutilated.mutilated);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getMutilatedDataForDN2(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForMutilated();
		jpaQuery.where(QMutilated.mutilated.icmcId.eq(icmcId).and(QMutilated.mutilated.insertTime.between(sDate, eDate))
				.and(QMutilated.mutilated.otherStatus.ne(OtherStatus.CANCELLED)));
		jpaQuery.groupBy(QMutilated.mutilated.denomination);
		jpaQuery.orderBy(QMutilated.mutilated.denomination.desc());
		List<Tuple> mutilatedList = jpaQuery.list(QMutilated.mutilated.denomination, QMutilated.mutilated.bundle.sum());
		return mutilatedList;
	}

	@Override
	public List<Tuple> getRemittanceSentForAllSoiled(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForRemittanceSent();
		jpaQuery.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.ACCEPTED)
						.or(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.RELEASED))));
		jpaQuery.groupBy(QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination);
		jpaQuery.orderBy(QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination,
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.bundle.sum().multiply(1000));
		return summaryList;
	}

	private JPAQuery getFromQueryForDiscepancyAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiscrepancyAllocation.discrepancyAllocation);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getTotalNotesForDiscrepancy(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiscepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.insertTime.between(sDate, eDate)
						.and(QDiscrepancyAllocation.discrepancyAllocation.status.ne(OtherStatus.CANCELLED))
		/*
		 * .and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.ne(
		 * "EXCESS"))
		 */));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.discrepancyType);
		jpaQuery.orderBy(QDiscrepancyAllocation.discrepancyAllocation.denomination.desc());

		List<Tuple> notesCountList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum(),
				QDiscrepancyAllocation.discrepancyAllocation.discrepancyType);
		return notesCountList;
	}

	@Override
	public List<Discrepancy> getDiscrepancyList(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancy();
		jpaQuery.where(QDiscrepancy.discrepancy.icmcId.eq(icmcId)
				.and(QDiscrepancy.discrepancy.normalOrSuspense.eq(normalOrSuspense))
				.and(QDiscrepancy.discrepancy.insertTime.between(sDate, eDate)));
		List<Discrepancy> discrepancyList = jpaQuery.list(QDiscrepancy.discrepancy);
		return discrepancyList;
	}

	@Override
	public List<Tuple> getTotalNotesForDiscrepancy(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		JPAQuery jpaQuery = getFromQueryForDiscepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq(normalOrSuspense)
						.and(QDiscrepancyAllocation.discrepancyAllocation.insertTime.between(sDate, eDate)
								.and(QDiscrepancyAllocation.discrepancyAllocation.status.ne(OtherStatus.CANCELLED))
		/*
		 * .and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.ne(
		 * "EXCESS"))
		 */)));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.discrepancyType);
		jpaQuery.orderBy(QDiscrepancyAllocation.discrepancyAllocation.denomination.desc());

		List<Tuple> notesCountList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum(),
				QDiscrepancyAllocation.discrepancyAllocation.discrepancyType);
		return notesCountList;
	}

	@Override
	public List<BinTransaction> searchBins(int denomination, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		jpaQuery.orderBy(QBinTransaction.binTransaction.binType.asc());
		List<BinTransaction> list = jpaQuery.list(QBinTransaction.binTransaction);
		UtilityJpa.setBinColorForTxn(list);
		return list;
	}

	@Override
	public List<String> getBinOrBoxFromBinTransactionForCashTransfer(BigInteger icmcId,
			BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binCategoryType)));
		List<String> list = jpaQuery.list(QBinTransaction.binTransaction.binNumber);
		return list;
	}

	@Override
	public List<String> getBinFromBinMasterForCashTransfer(BigInteger icmcId, CurrencyType binType) {
		JPAQuery jpaQuery = getFromQueryForBinDashboard();
		jpaQuery.where(QBinMaster.binMaster.icmcId.eq(icmcId)
				// .and(QBinMaster.binMaster.firstPriority.eq(binType))
				.and(QBinMaster.binMaster.isAllocated.eq(0)));
		List<String> binNumberList = jpaQuery.list(QBinMaster.binMaster.binNumber);
		return binNumberList;
	}

	private JPAQuery getFromQueryForBoxMaster() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBoxMaster.boxMaster);
		return jpaQuery;
	}

	@Override
	public List<String> getBoxFromBoxMasterForCashTransfer(BigInteger icmcId, int denomination,
			CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForBoxMaster();
		jpaQuery.where(QBoxMaster.boxMaster.icmcId.eq(icmcId).and(QBoxMaster.boxMaster.denomination.eq(denomination))
				.and(QBoxMaster.boxMaster.currencyType.eq(currencyType)).and(QBoxMaster.boxMaster.isAllocated.eq(0)));
		List<String> boxMasterList = jpaQuery.list(QBoxMaster.boxMaster.boxName);
		return boxMasterList;
	}

	@Override
	public BinTransaction checkBinOrBox(BigInteger icmcId, String binNumber) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binNumber.eq(binNumber)));
		BinTransaction binOrBoxFromDB = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binOrBoxFromDB;
	}

	@Override
	public BinTransaction binDetailsByBinNumber(BigInteger icmcId, String binNumber) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binNumber.eq(binNumber)));
		BinTransaction binOrBoxFromDB = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binOrBoxFromDB;
	}

	@Override
	public boolean cashTransferInBinTxn(BinTransaction binTransaction) {
		em.persist(binTransaction);
		return true;
	}

	@Override
	public boolean saveCashTransfer(CashTransfer cashTransfer) {
		em.persist(cashTransfer);
		return true;
	}

	@Override
	public long updateBinTxnAfterCashTransfer(BigInteger icmcId, String binNumber) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qBinTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
						.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
						.and(QBinTransaction.binTransaction.binNumber.eq(binNumber)))
				.set(QBinTransaction.binTransaction.status, BinStatus.EMPTY)
				.set(QBinTransaction.binTransaction.receiveBundle, BigDecimal.ZERO)
				.set(QBinTransaction.binTransaction.updateTime, Calendar.getInstance())
				.set(QBinTransaction.binTransaction.verified, YesNo.NULL).execute();
		return count;
	}

	@Override
	public boolean saveAuditorIndentRequest(AuditorIndent auditorIndent) {
		em.persist(auditorIndent);
		return true;
	}

	@Override
	public BinTransaction getDataFromBinTrxnForAuditor(BinTransaction binTxn) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTxn.getIcmcId())
				.and(QBinTransaction.binTransaction.binNumber.eq(binTxn.getBinNumber())
						.and(QBinTransaction.binTransaction.binType.eq(binTxn.getBinType()))));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public BinMaster getBinNumberById(Long id) {
		return em.find(BinMaster.class, id);
	}

	@Override
	public BinMaster getIsAllocatedValue(BigInteger icmcId, String binNumber) {
		JPAQuery jpaQuery = getFromQueryForBinDashboard();
		jpaQuery.where(QBinMaster.binMaster.icmcId.eq(icmcId).and(QBinMaster.binMaster.binNumber.eq(binNumber)));
		BinMaster binMaster = jpaQuery.singleResult(QBinMaster.binMaster);
		return binMaster;
	}

	@Override
	public boolean updateBinMaster(BigInteger icmcId, String BinNumber, int value) {
		QBinMaster qBinMaster = QBinMaster.binMaster;
		Long count = new JPAUpdateClause(em, qBinMaster)
				.where(QBinMaster.binMaster.icmcId.eq(icmcId).and(QBinMaster.binMaster.binNumber.eq(BinNumber)))
				.set(QBinMaster.binMaster.isAllocated, value).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean updateDisabledBinStatus(BigInteger icmcId, String BinNumber) {
		QBinMaster qBinMaster = QBinMaster.binMaster;
		Long count = new JPAUpdateClause(em, qBinMaster)
				.where(QBinMaster.binMaster.icmcId.eq(icmcId).and(QBinMaster.binMaster.binNumber.eq(BinNumber)))
				.set(QBinMaster.binMaster.isAllocated, 0).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean updateBoxMaster(BigInteger icmcId, String boxNumber, int value) {
		QBoxMaster qBoxMaster = QBoxMaster.boxMaster;
		Long count = new JPAUpdateClause(em, qBoxMaster)
				.where(QBoxMaster.boxMaster.icmcId.eq(icmcId).and(QBoxMaster.boxMaster.boxName.eq(boxNumber)))
				.set(QBoxMaster.boxMaster.isAllocated, value).execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<BinMaster> getDisableBin(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinDashboard();
		jpaQuery.where(QBinMaster.binMaster.icmcId.eq(icmcId).and(QBinMaster.binMaster.isAllocated.eq(2)));
		List<BinMaster> binMasterList = jpaQuery.list(QBinMaster.binMaster);
		return binMasterList;
	}

	private JPAQuery getFromQueryForAuditorIndentRequestFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction).orderBy(QBinTransaction.binTransaction.insertTime.asc());
		return jpaQuery;
	}

	@Override
	public BinTransaction getBinNumListForAuditorIndent(AuditorIndent auditorIndent, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForAuditorIndentRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(auditorIndent.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(auditorIndent.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.verified.ne(YesNo.No))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binNumber.eq(auditorIndent.getBinNumber()))
				.and(QBinTransaction.binTransaction.binType.eq(type)));
		BinTransaction txnList = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public boolean updateBinTxn(BigInteger icmcId, BigDecimal pendingBundle) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		Long count = new JPAUpdateClause(em, qBinTransaction).where(QBinTransaction.binTransaction.icmcId.eq(icmcId))
				.set(QBinTransaction.binTransaction.pendingBundleRequest, pendingBundle).execute();
		return count > 0 ? true : false;
	}

	private JPAQuery getFromQueryForAuditorIndent() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QAuditorIndent.auditorIndent);
		return jpaQuery;
	}

	@Override
	public List<AuditorIndent> viewAuditorIndent(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForAuditorIndent();
		jpaQuery.where(QAuditorIndent.auditorIndent.icmcId.eq(icmcId)
				.and(QAuditorIndent.auditorIndent.status.eq(OtherStatus.REQUESTED)));
		List<AuditorIndent> auditorIndentList = jpaQuery.list(QAuditorIndent.auditorIndent);
		return auditorIndentList;
	}

	@Override
	public List<AuditorIndent> viewAuditorIndentList(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForAuditorIndent();
		jpaQuery.where(QAuditorIndent.auditorIndent.icmcId.eq(icmcId));
		List<AuditorIndent> auditorIndentList = jpaQuery.list(QAuditorIndent.auditorIndent);
		return auditorIndentList;
	}

	@Override
	public List<AuditorIndent> auditorIndentForMachineAllocation(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForAuditorIndent();

		jpaQuery.where(QAuditorIndent.auditorIndent.icmcId.eq(icmcId)
				.and(QAuditorIndent.auditorIndent.status.eq(OtherStatus.ACCEPTED))
				.and(QAuditorIndent.auditorIndent.bundle.gt(0)));
		jpaQuery.groupBy(QAuditorIndent.auditorIndent.denomination);
		jpaQuery.orderBy(QAuditorIndent.auditorIndent.denomination.desc());
		List<Tuple> tupleList = jpaQuery.list(QAuditorIndent.auditorIndent.denomination,
				QAuditorIndent.auditorIndent.bundle.sum(), QAuditorIndent.auditorIndent.pendingBundleRequest.sum());

		LOG.info("AUDITOR INDENT REQUEST DATA FOR MACHINE ALLOCATION");
		List<AuditorIndent> indentList = UtilityJpa.mapTuppleAuditorIndentForMachineAllocation(tupleList);
		return indentList;

	}

	@Override
	public boolean updateAuditorIndentStatus(AuditorIndent auditorIndent) {
		QAuditorIndent qAuditorIndent = QAuditorIndent.auditorIndent;
		long count = new JPAUpdateClause(em, qAuditorIndent)
				.where(QAuditorIndent.auditorIndent.id.eq(auditorIndent.getId())
						.and(QAuditorIndent.auditorIndent.icmcId.eq(auditorIndent.getIcmcId())))
				.set(QAuditorIndent.auditorIndent.status, OtherStatus.ACCEPTED).execute();
		return count > 0 ? true : false;
	}

	@Override
	public BinTransaction getBinRecordForAcceptInVault(BinTransaction txn) {

		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(txn.getIcmcId())
				.and(QBinTransaction.binTransaction.binNumber.eq(txn.getBinNumber())
						.and(QBinTransaction.binTransaction.denomination.eq(txn.getDenomination()))));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public boolean updateBinTxn(BinTransaction binTransaction) {
		em.merge(binTransaction);
		return true;
	}

	@Override
	public boolean updateBinMaster(BinMaster binMaster) {
		QBinMaster qBinMaster = QBinMaster.binMaster;
		long count = new JPAUpdateClause(em, qBinMaster)
				.where(qBinMaster.binNumber.eq(binMaster.getBinNumber().trim())
						.and(QBinMaster.binMaster.icmcId.eq(binMaster.getIcmcId())))
				.set(qBinMaster.isAllocated, 0).execute();
		return count > 0 ? true : false;
	}

	private JPAQuery getFromQueryForCoins() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSas.sas);
		return jpaQuery;
	}

	@Override
	public List<Sas> coinsRegister(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCoins();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.insertTime.between(sDate, eDate))
				.and(QSas.sas.status.eq(1).or(QSas.sas.status.eq(2))));
		List<Sas> coinsList = jpaQuery.list(QSas.sas);
		return coinsList;
	}

	@Override
	public boolean saveTrainingRegisterData(TrainingRegister trainingRegsiter) {
		em.persist(trainingRegsiter);
		return true;
	}

	public JPAQuery getFromQueryForTrainingRegister() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QTrainingRegister.trainingRegister);
		return jpaQuery;
	}

	@Override
	public List<TrainingRegister> getTrainingRegisterData(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForTrainingRegister();
		jpaQuery.where(QTrainingRegister.trainingRegister.icmcId.eq(icmcId));
		List<TrainingRegister> trainingRegisterList = jpaQuery.list(QTrainingRegister.trainingRegister);
		return trainingRegisterList;
	}

	@Override
	public TrainingRegister getTrainingDataBYId(Long id) {
		return em.find(TrainingRegister.class, id);
	}

	@Override
	public boolean updateTrainingRegsiter(TrainingRegister trainingRegsiter) {
		em.merge(trainingRegsiter);
		return true;
	}

	@Override
	public List<TrainingRegister> getTrainingRegisterReport(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForTrainingRegister();
		jpaQuery.where(QTrainingRegister.trainingRegister.icmcId.eq(icmcId)
				.and(QTrainingRegister.trainingRegister.insertTime.between(sDate, eDate)));
		List<TrainingRegister> trainingRegister = jpaQuery.list(QTrainingRegister.trainingRegister);
		return trainingRegister;
	}

	@Override
	public BoxMaster getBoxCapacity(BigInteger icmcId, String boxName) {
		JPAQuery jpaQuery = getFromQueryForBoxMaster();
		jpaQuery.where(QBoxMaster.boxMaster.icmcId.eq(icmcId).and(QBoxMaster.boxMaster.boxName.eq(boxName)));
		BoxMaster boxMaster = jpaQuery.singleResult(QBoxMaster.boxMaster);
		return boxMaster;
	}

	@Override
	public BranchReceipt getBranchReceiptDetailsForCashTransferQR(BigInteger icmcId, String binNumber,
			int denomination) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(
				QBranchReceipt.branchReceipt.icmcId.eq(icmcId).and(QBranchReceipt.branchReceipt.bin.eq(binNumber))
						.and(QBranchReceipt.branchReceipt.denomination.eq(denomination)));
		BranchReceipt branchReceiptDetails = jpaQuery.singleResult(QBranchReceipt.branchReceipt);
		return branchReceiptDetails;
	}

	@Override
	public DSB getDSBDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(
				QDSB.dSB.icmcId.eq(icmcId).and(QDSB.dSB.bin.eq(binNumber)).and(QDSB.dSB.denomination.eq(denomination)));
		DSB dsbDetails = jpaQuery.singleResult(QDSB.dSB);
		return dsbDetails;
	}

	@Override
	public DiversionIRV getDiversionIRVDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination) {
		JPAQuery jpaQuery = getFromQueryForDiversion();
		jpaQuery.where(QDiversionIRV.diversionIRV.icmcId.eq(icmcId).and(QDiversionIRV.diversionIRV.binNumber
				.eq(binNumber).and(QDiversionIRV.diversionIRV.denomination.eq(denomination))));
		DiversionIRV diversionIRVDetails = jpaQuery.singleResult(QDiversionIRV.diversionIRV);
		return diversionIRVDetails;
	}

	@Override
	public BankReceipt getBankReceiptDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination) {
		JPAQuery jpaQuery = getFromQueryForOtherBank();
		jpaQuery.where(QBankReceipt.bankReceipt.icmcId.eq(icmcId).and(QBankReceipt.bankReceipt.binNumber.eq(binNumber)
				.and(QBankReceipt.bankReceipt.denomination.eq(denomination))));
		BankReceipt bankReceiptDetails = jpaQuery.singleResult(QBankReceipt.bankReceipt);
		return bankReceiptDetails;
	}

	@Override
	public List<Tuple> getCRAAllocationData(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QCRAAllocation.cRAAllocation.denomination);
		jpaQuery.orderBy(QCRAAllocation.cRAAllocation.denomination.desc());
		List<Tuple> craAllocationBundle = jpaQuery.list(QCRAAllocation.cRAAllocation.denomination,
				QCRAAllocation.cRAAllocation.bundle.sum());
		return craAllocationBundle;
	}

	@Override
	public List<Tuple> getBankAllocationData(BigInteger icmId, Calendar sDate, Calendar eDate) {
		return null;
	}

	@Override
	public List<Tuple> getCRAForCashBookWithDrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.ACCEPTED)
						.or(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.RELEASED)))
				.and(QCRAAllocation.cRAAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QCRAAllocation.cRAAllocation.craId, QCRAAllocation.cRAAllocation.denomination);
		jpaQuery.orderBy(QCRAAllocation.cRAAllocation.denomination.desc());
		List<Tuple> craAllocation = jpaQuery.list(QCRAAllocation.cRAAllocation.craId,
				QCRAAllocation.cRAAllocation.denomination, QCRAAllocation.cRAAllocation.bundle.sum().multiply(1000));
		return craAllocation;
	}

	@Override
	public List<Tuple> getProcessCRAForCashBookWithDrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleCraList();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.RELEASED))
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QProcessBundleForCRAPayment.processBundleForCRAPayment.craId,
				QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination);
		jpaQuery.orderBy(QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination.desc());
		List<Tuple> craAllocation = jpaQuery.list(QProcessBundleForCRAPayment.processBundleForCRAPayment.craId,
				QProcessBundleForCRAPayment.processBundleForCRAPayment.denomination,
				QProcessBundleForCRAPayment.processBundleForCRAPayment.bundle.sum().multiply(1000));
		return craAllocation;
	}

	@Override
	public CRA getBranchVendorAndMSPFromCRA(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForCRAPayment();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId).and(QCRA.cRA.id.eq(id)));
		CRA cra = jpaQuery.singleResult(QCRA.cRA);
		return cra;
	}

	@Override
	public List<Tuple> getoutwardDiversionForCashBookWithDrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiversionORV();
		jpaQuery.where(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(icmcId)
				.and(QDiversionORVAllocation.diversionORVAllocation.status.eq(OtherStatus.RELEASED)
						.or(QDiversionORVAllocation.diversionORVAllocation.status.eq(OtherStatus.ACCEPTED)))
				.and(QDiversionORVAllocation.diversionORVAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QDiversionORVAllocation.diversionORVAllocation.diversionOrvId,
				QDiversionORVAllocation.diversionORVAllocation.denomination);
		jpaQuery.orderBy(QDiversionORVAllocation.diversionORVAllocation.denomination.desc());
		List<Tuple> diversionORV = jpaQuery.list(QDiversionORVAllocation.diversionORVAllocation.diversionOrvId,
				QDiversionORVAllocation.diversionORVAllocation.denomination,
				QDiversionORVAllocation.diversionORVAllocation.bundle.sum().multiply(1000));
		return diversionORV;
	}

	@Override
	public List<Tuple> getOtherBankAllocationForCashBookWithDrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForOtherBankAllocation();
		jpaQuery.where(QOtherBankAllocation.otherBankAllocation.icmcId.eq(icmcId)
				.and(QOtherBankAllocation.otherBankAllocation.status.eq(OtherStatus.RELEASED)
						.or(QOtherBankAllocation.otherBankAllocation.status.eq(OtherStatus.ACCEPTED)))
				.and(QOtherBankAllocation.otherBankAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QOtherBankAllocation.otherBankAllocation.otherBankId,
				QOtherBankAllocation.otherBankAllocation.denomination);
		jpaQuery.orderBy(QOtherBankAllocation.otherBankAllocation.denomination.desc());
		List<Tuple> otherBankAllocationList = jpaQuery.list(QOtherBankAllocation.otherBankAllocation.otherBankId,
				QOtherBankAllocation.otherBankAllocation.denomination,
				QOtherBankAllocation.otherBankAllocation.bundle.sum().multiply(1000));
		return otherBankAllocationList;
	}

	private JPAQuery getFromQueryForDiversionORVV() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionORV.diversionORV);
		return jpaQuery;
	}

	@Override
	public String getBranchFromDiversion(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForDiversionORVV();
		jpaQuery.where(QDiversionORV.diversionORV.icmcId.eq(icmcId).and(QDiversionORV.diversionORV.id.eq(id)));
		String branchName = jpaQuery.singleResult(QDiversionORV.diversionORV.bankName);
		return branchName;
	}

	private JPAQuery getFromQueryForOtherbank() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QOtherBank.otherBank);
		return jpaQuery;
	}

	@Override
	public String getBranchFromOtherBank(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForOtherbank();
		jpaQuery.where(QOtherBank.otherBank.icmcId.eq(icmcId).and(QOtherBank.otherBank.id.eq(id)));
		String branchName = jpaQuery.singleResult(QOtherBank.otherBank.branch);
		return branchName;
	}

	@Override
	public String getBankFromOtherBank(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForOtherbank();
		jpaQuery.where(QOtherBank.otherBank.icmcId.eq(icmcId).and(QOtherBank.otherBank.id.eq(id)));
		String branchName = jpaQuery.singleResult(QOtherBank.otherBank.bankName);
		return branchName;
	}

	@Override
	public List<Tuple> getSoiledAllocationForCashBookWithDrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForRemittanceSent();
		jpaQuery.where(
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
						.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.RELEASED)
								.or(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status
										.eq(OtherStatus.ACCEPTED)))
						.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QSoiledRemittanceAllocation.soiledRemittanceAllocation.soiledRemittanceId,
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination);
		jpaQuery.orderBy(QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination.desc());
		List<Tuple> soiledList = jpaQuery.list(
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.soiledRemittanceId,
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.denomination,
				QSoiledRemittanceAllocation.soiledRemittanceAllocation.bundle.sum().multiply(1000));
		return soiledList;
	}

	@Override
	public List<Tuple> getDataFromBranchForCashMovementRegister(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForBranch();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.binType.eq(currencyType))
				.and(QSASAllocation.sASAllocation.cashType.eq(CashType.NOTES))
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED))
				.and(QSASAllocation.sASAllocation.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QSASAllocation.sASAllocation.denomination);
		jpaQuery.orderBy(QSASAllocation.sASAllocation.denomination.asc());
		List<Tuple> SASAllocationList = jpaQuery.list(QSASAllocation.sASAllocation.denomination,
				QSASAllocation.sASAllocation.bundle.sum());
		return SASAllocationList;
	}

	@Override
	public List<Tuple> getWithdrawalForCRAForCashMovement(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.currencyType.eq(currencyType))
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.RELEASED)
						.or(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.ACCEPTED)))
				.and(QCRAAllocation.cRAAllocation.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QCRAAllocation.cRAAllocation.denomination);
		jpaQuery.orderBy(QCRAAllocation.cRAAllocation.denomination.asc());
		List<Tuple> otherCRAWithdrawalList = jpaQuery.list(QCRAAllocation.cRAAllocation.denomination,
				QCRAAllocation.cRAAllocation.bundle.sum());
		return otherCRAWithdrawalList;
	}

	@Override
	public List<Tuple> getDepositForBranchForCashMovement(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(
				QBranchReceipt.branchReceipt.icmcId.eq(icmcId).and(QBranchReceipt.branchReceipt.solId.isNotNull())
						.and(QBranchReceipt.branchReceipt.status.ne(OtherStatus.CANCELLED))
						.and(QBranchReceipt.branchReceipt.binCategoryType.ne(BinCategoryType.BAG))
						.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.denomination);
		jpaQuery.orderBy(QBranchReceipt.branchReceipt.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QBranchReceipt.branchReceipt.denomination,
				QBranchReceipt.branchReceipt.bundle.sum());
		return summaryList;
	}

	private JPAQuery getFromQueryForHistory() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QHistory.history);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getDepositFromHistory(BigInteger icmcId, String binNumber, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinRegister.binRegister);
		jpaQuery.where(QBinRegister.binRegister.icmcId.eq(icmcId)
				.and(QBinRegister.binRegister.insertTime.between(sDate, eDate))
				.and(QBinRegister.binRegister.binNumber.eq(binNumber)));
		List<Tuple> historyListForDeposit = jpaQuery.list(QBinRegister.binRegister.denomination,
				QBinRegister.binRegister.receiveBundle, QBinRegister.binRegister.withdrawalBundle,
				QBinRegister.binRegister.insertTime);
		return historyListForDeposit;
	}

	@Override
	public Calendar getInsertTimeFromCRA(BigInteger icmcId, String branch, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.insertTime.between(sDate, eDate)));
		Calendar insertTime = jpaQuery.singleResult(QCRAAllocation.cRAAllocation.insertTime);
		return insertTime;
	}

	private JPAQuery getFromQueryFromIndent() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryFromIndent();
		jpaQuery.where(
				QIndent.indent.icmcId.eq(icmcId)
						.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED)
								.or(QIndent.indent.status.eq(OtherStatus.PROCESSED)))
						.and(QIndent.indent.cashSource.ne(CashSource.RBI)).and(QIndent.indent.bin.isNotNull())
						.and(QIndent.indent.updateTime.between(sDate, eDate)));
		jpaQuery.groupBy(QIndent.indent.denomination);
		List<Tuple> ibitIndentList = jpaQuery.list(QIndent.indent.denomination,
				QIndent.indent.bundle.sum().multiply(1000));

		JPAQuery jpaQuery2 = new JPAQuery(em);
		jpaQuery2.from(QAuditorIndent.auditorIndent);
		jpaQuery2.where(QAuditorIndent.auditorIndent.icmcId.eq(icmcId)
				.and(QAuditorIndent.auditorIndent.status.eq(OtherStatus.ACCEPTED)
						.or(QAuditorIndent.auditorIndent.status.eq(OtherStatus.PROCESSED)))
				.and(QAuditorIndent.auditorIndent.binNumber.isNotNull())
				.and(QAuditorIndent.auditorIndent.insertTime.between(sDate, eDate)));
		jpaQuery2.groupBy(QAuditorIndent.auditorIndent.denomination);
		List<Tuple> ibitAuditorIndentList = jpaQuery2.list(QAuditorIndent.auditorIndent.denomination,
				QAuditorIndent.auditorIndent.bundle.sum().multiply(1000));

		for (Tuple tuple : ibitAuditorIndentList) {
			ibitIndentList.add(tuple);
		}
		return ibitIndentList;
	}

	@Override
	public String getServicingICMC(String solId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranch.branch1);
		jpaQuery.where(QBranch.branch1.solId.eq(solId));
		String servicingICMC = jpaQuery.singleResult(QBranch.branch1.branch);
		return servicingICMC;
	}

	@Override
	public String getLinkBranchSolID(long icmcId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		jpaQuery.where(QICMC.iCMC.id.eq(icmcId));
		String linkSolBranchId = jpaQuery.singleResult(QICMC.iCMC.linkBranchSolId);
		return linkSolBranchId;
	}

	@Override
	public List<Tuple> historyDataToUpdateBinRegister(BigInteger icmcId, Calendar sDate, Calendar edate) {
		JPAQuery jpaQuery = getFromQueryForHistory();
		jpaQuery.where(QHistory.history.icmcId.eq(icmcId).and(QHistory.history.insertTime.between(sDate, edate)));
		jpaQuery.groupBy(QHistory.history.denomination, QHistory.history.binNumber, QHistory.history.type);
		return null;
	}

	@Override
	public List<Tuple> getAllReceiptDataForBinRegister(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QHistory.history);
		jpaQuery.where(QHistory.history.icmcId.eq(icmcId).and(QHistory.history.insertTime.between(sDate, eDate))
				.and(QHistory.history.status.eq(OtherStatus.REQUESTED)));
		jpaQuery.groupBy(QHistory.history.denomination, QHistory.history.binNumber);
		List<Tuple> receiptDataList = jpaQuery.list(QHistory.history.denomination, QHistory.history.binNumber,
				QHistory.history.receiveBundle.sum(), QHistory.history.withdrawalBundle.sum());
		return receiptDataList;
	}

	@Override
	public List<History> getBundleFromHistory(BigInteger icmcId, String binNumber, Integer denomination) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QHistory.history);
		jpaQuery.where(QHistory.history.icmcId.eq(icmcId)
				.and(QHistory.history.binNumber.eq(binNumber).and(QHistory.history.denomination.eq(denomination)
						.and(QHistory.history.status.eq(OtherStatus.REQUESTED)))));
		List<History> bundleFromHistory = jpaQuery.list(QHistory.history);
		return bundleFromHistory;
	}

	@Override
	public boolean updateBundle(BigInteger icmcId, String binNumber, Integer denomination, BigDecimal withdrawalBundle,
			long id) {
		QHistory qHistory = QHistory.history;
		long count = new JPAUpdateClause(em, qHistory)
				.where(QHistory.history.icmcId.eq(icmcId)
						.and(QHistory.history.binNumber.eq(binNumber).and(QHistory.history.id.eq(id))
								.and(QHistory.history.status.eq(OtherStatus.REQUESTED))
								.and(QHistory.history.denomination.eq(denomination))))
				.set(QHistory.history.withdrawalBundle, withdrawalBundle).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean updateStatus(BigInteger icmcId, long id) {
		QHistory qHistory = QHistory.history;
		long count = new JPAUpdateClause(em, qHistory)
				.where(QHistory.history.icmcId.eq(icmcId).and(QHistory.history.id.eq(id)))
				.set(QHistory.history.status, OtherStatus.RECEIVED).execute();
		return count > 0 ? true : false;
	}

	@Override
	public History getWithdrawalbundle(BigInteger icmcId, String binNumber, Integer denomination) {
		JPAQuery jpaQuery = getFromQueryForHistory();
		jpaQuery.where(QHistory.history.icmcId.eq(icmcId).and(QHistory.history.status.eq(OtherStatus.REQUESTED))
				.and(QHistory.history.binNumber.eq(binNumber).and(QHistory.history.denomination.eq(denomination))));
		History withdrwalBundle = jpaQuery.singleResult(QHistory.history);
		return withdrwalBundle;
	}

	@Override
	public boolean insertDataInBinRegister(BinRegister binRegister) {
		em.persist(binRegister);
		return true;
	}

	@Override
	public Calendar getInsertTimeFromDiversionORV(BigInteger icmcId, long id, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionORV.diversionORV);
		jpaQuery.where(QDiversionORV.diversionORV.icmcId.eq(icmcId).and(QDiversionORV.diversionORV.id.eq(id))
				.and(QDiversionORV.diversionORV.insertTime.between(sDate, eDate)));
		Calendar insertTime = jpaQuery.singleResult(QDiversionORV.diversionORV.insertTime);
		return insertTime;
	}

	@Override
	public Calendar getInsertTimeFromOtherBank(BigInteger icmcId, long id, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QOtherBank.otherBank);
		jpaQuery.where(QOtherBank.otherBank.icmcId.eq(icmcId).and(QOtherBank.otherBank.id.eq(id))
				.and(QOtherBank.otherBank.insertTime.between(sDate, eDate)));
		Calendar insertTime = jpaQuery.singleResult(QOtherBank.otherBank.insertTime);
		return insertTime;
	}

	@Override
	public List<String> getBinFromBinTransaction(BigInteger icmcId, int denomination, CurrencyType currencyType) {
		CashType cashType;
		if (currencyType.equals(CurrencyType.COINS)) {
			currencyType = CurrencyType.FRESH;
			cashType = CashType.COINS;
		} else {
			cashType = CashType.NOTES;
		}
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binType.eq(currencyType))
				.and(QBinTransaction.binTransaction.cashType.eq(cashType)));
		List<String> binList = jpaQuery.list(QBinTransaction.binTransaction.binNumber);
		return binList;
	}

	@Override
	public List<Tuple> getIBITForIO2(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		/*
		 * JPAQuery jpaQuery = new JPAQuery(em); jpaQuery.from(QIndent.indent);
		 */
		JPAQuery jpaQuery = getFromQueryFromIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId)
				.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED)
						.or(QIndent.indent.status.eq(OtherStatus.PROCESSED).and(QIndent.indent.cashSource
								.eq(CashSource.BRANCH).and(QIndent.indent.insertTime.between(sDate, eDate))))));
		jpaQuery.groupBy(QIndent.indent.denomination);
		List<Tuple> ibitList = jpaQuery.list(QIndent.indent.denomination, QIndent.indent.bundle.sum().multiply(1000));
		return ibitList;
	}

	@Override
	public boolean checkAvlBundleByDenoCategory(BigInteger icmcId, BinTransaction binTransaction) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.denomination.eq(binTransaction.getDenomination())
				.and(QBinTransaction.binTransaction.icmcId.eq(icmcId))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		// List<BinTransaction> list =
		// jpaQuery.list(QBinTransaction.binTransaction);

		return false;
	}

	@Override
	public ChestMaster getLastChestSlipNumber(BigInteger icmcId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QChestMaster.chestMaster);
		jpaQuery.where(QChestMaster.chestMaster.icmcId.eq(icmcId));
		jpaQuery.orderBy(QChestMaster.chestMaster.chestNumber.desc());
		ChestMaster chestMaster = jpaQuery.singleResult(QChestMaster.chestMaster);
		return chestMaster;

	}

	@Override
	public boolean insertChestMaster(ChestMaster chestMaster) {

		em.persist(chestMaster);
		return true;

	}

	@Override
	public BigInteger getChestSlipNumber(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QChestMaster.chestMaster);
		jpaQuery.where(QChestMaster.chestMaster.icmcId.eq(icmcId)
				.and(QChestMaster.chestMaster.updateTime.between(sDate, eDate)));
		jpaQuery.orderBy(QChestMaster.chestMaster.chestNumber.desc());
		BigInteger chestMaster = jpaQuery.singleResult(QChestMaster.chestMaster.chestNumber);
		return chestMaster;
	}

	@Override
	public List<String> getBinFroPartialTransfer(BigInteger icmcId, Integer denomination, CurrencyType currencyType,
			BigDecimal bundle, BinCategoryType binCategoryType, String bin) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.binType.eq(currencyType))
				.and(QBinTransaction.binTransaction.binNumber.ne(bin))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binCategoryType))
				.and(QBinTransaction.binTransaction.pendingBundleRequest.eq(new BigDecimal(0)))
				.and(QBinTransaction.binTransaction.maxCapacity.subtract(QBinTransaction.binTransaction.receiveBundle)
						.gt(bundle).or(QBinTransaction.binTransaction.maxCapacity
								.subtract(QBinTransaction.binTransaction.receiveBundle).eq(bundle))));
		List<String> binListFromBinTxn = jpaQuery.list(QBinTransaction.binTransaction.binNumber);
		return binListFromBinTxn;
	}

	@Override
	public void deleteTrainingRegisterById(Long id) {
		new JPADeleteClause(em, QTrainingRegister.trainingRegister).where(QTrainingRegister.trainingRegister.id.eq(id))
				.execute();
	}

	@Override
	public void deleteForMigration(BigInteger icmcId) {
		new JPADeleteClause(em, QBinMaster.binMaster).where(QBinMaster.binMaster.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QBoxMaster.boxMaster).where(QBoxMaster.boxMaster.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QBinTransaction.binTransaction).where(QBinTransaction.binTransaction.icmcId.eq(icmcId))
				.execute();
		new JPADeleteClause(em, QBranchReceipt.branchReceipt).where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId))
				.execute();

		new JPADeleteClause(em, QBinTransactionBOD.binTransactionBOD)
				.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(icmcId)).execute();

		new JPADeleteClause(em, QSas.sas).where(QSas.sas.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QSASAllocation.sASAllocation).where(QSASAllocation.sASAllocation.icmcId.eq(icmcId))
				.execute();
		new JPADeleteClause(em, QOtherBank.otherBank).where(QOtherBank.otherBank.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QOtherBankAllocation.otherBankAllocation)
				.where(QOtherBankAllocation.otherBankAllocation.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QCRA.cRA).where(QCRA.cRA.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QCRAAllocation.cRAAllocation).where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId))
				.execute();
		new JPADeleteClause(em, QCRALog.cRALog).where(QCRALog.cRALog.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QCRAAllocationLog.cRAAllocationLog)
				.where(QCRAAllocationLog.cRAAllocationLog.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QORV.oRV).where(QORV.oRV.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QORVAllocation.oRVAllocation).where(QORVAllocation.oRVAllocation.icmcId.eq(icmcId))
				.execute();
		new JPADeleteClause(em, QSoiledRemittance.soiledRemittance)
				.where(QSoiledRemittance.soiledRemittance.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QSoiledRemittanceAllocation.soiledRemittanceAllocation)
				.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QDiversionORV.diversionORV).where(QDiversionORV.diversionORV.icmcId.eq(icmcId))
				.execute();
		new JPADeleteClause(em, QDiversionORVAllocation.diversionORVAllocation)
				.where(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(icmcId)).execute();

		new JPADeleteClause(em, QProcess.process).where(QProcess.process.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QProcessBundleForCRAPayment.processBundleForCRAPayment)
				.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QMachineAllocation.machineAllocation)
				.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QIndent.indent).where(QIndent.indent.icmcId.eq(icmcId)).execute();

		new JPADeleteClause(em, QDSB.dSB).where(QDSB.dSB.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QFreshFromRBI.freshFromRBI).where(QFreshFromRBI.freshFromRBI.icmcId.eq(icmcId))
				.execute();
		new JPADeleteClause(em, QBankReceipt.bankReceipt).where(QBankReceipt.bankReceipt.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QDiversionIRV.diversionIRV).where(QDiversionIRV.diversionIRV.icmcId.eq(icmcId))
				.execute();

		new JPADeleteClause(em, QDiscrepancy.discrepancy).where(QDiscrepancy.discrepancy.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QDiscrepancyAllocation.discrepancyAllocation)
				.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QHistory.history).where(QHistory.history.icmcId.eq(icmcId)).execute();

		new JPADeleteClause(em, QBinRegister.binRegister).where(QBinRegister.binRegister.icmcId.eq(icmcId)).execute();
		new JPADeleteClause(em, QCoinsSequence.coinsSequence).where(QCoinsSequence.coinsSequence.icmcId.eq(icmcId))
				.execute();

		new JPADeleteClause(em, QCashTransfer.cashTransfer).where(QCashTransfer.cashTransfer.icmcId.eq(icmcId))
				.execute();
		new JPADeleteClause(em, QSuspenseOpeningBalance.suspenseOpeningBalance)
				.where(QSuspenseOpeningBalance.suspenseOpeningBalance.icmcId.eq(icmcId)).execute();

		new JPADeleteClause(em, QAssignVaultCustodian.assignVaultCustodian)
				.where(QAssignVaultCustodian.assignVaultCustodian.icmcId.eq(icmcId)).execute();

		new JPADeleteClause(em, QDefineKeySet.defineKeySet).where(QDefineKeySet.defineKeySet.icmcId.eq(icmcId))
				.execute();

		new JPADeleteClause(em, QCustodianKeySet.custodianKeySet)
				.where(QCustodianKeySet.custodianKeySet.icmcId.eq(icmcId)).execute();

		new JPADeleteClause(em, QAuditorIndent.auditorIndent).where(QAuditorIndent.auditorIndent.icmcId.eq(icmcId))
				.execute();

		new JPADeleteClause(em, QAuditorProcess.auditorProcess).where(QAuditorProcess.auditorProcess.icmcId.eq(icmcId))
				.execute();

	}

	@Override
	public BinTransactionBOD checkEODHappenOrNot(BigInteger icmcId, CashType cashType, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBinTxnBOD();
		jpaQuery.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(icmcId)
				.and(QBinTransactionBOD.binTransactionBOD.cashType.eq(cashType))
				.and(QBinTransactionBOD.binTransactionBOD.currentVersion.eq("TRUE"))
				.and(QBinTransactionBOD.binTransactionBOD.insertTime.between(sDate, eDate)));
		BinTransactionBOD binTransactionBOD = jpaQuery.singleResult(QBinTransactionBOD.binTransactionBOD);
		return binTransactionBOD;

	}

	@Override
	public Calendar getDataFromBinTxnLastEntry(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId));
		jpaQuery.orderBy(QBinTransaction.binTransaction.updateTime.desc());
		Calendar calendar = jpaQuery.singleResult(QBinTransaction.binTransaction.updateTime);
		return calendar;
	}

	@Override
	public boolean insertBranchReceiptAftercashTransfer(BranchReceipt branchReceipt) {
		em.persist(branchReceipt);
		return true;
	}

	@Override
	public long updateBranchReceiptAfterCashTransfer(BigInteger icmcId, String binNumber) {
		long count = new JPAUpdateClause(em, QBranchReceipt.branchReceipt)
				.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
						.and(QBranchReceipt.branchReceipt.bin.eq(binNumber)))
				.set(QBranchReceipt.branchReceipt.status, OtherStatus.CANCELLED).execute();
		return count;
	}

	@Override
	public BinTransactionBOD getDataFromBinTransactionBOD(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxnBOD();
		jpaQuery.where(QBinTransactionBOD.binTransactionBOD.icmcId.eq(icmcId));
		BinTransactionBOD bodData = jpaQuery.singleResult(QBinTransactionBOD.binTransactionBOD);
		return bodData;
	}

	@Override
	public List<Tuple> getSoiledBalanceForEOD(BigInteger icmcId, CashType cashType) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(cashType))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.SOILED)
						.or(QBinTransaction.binTransaction.binType.eq(CurrencyType.MUTILATED))));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.cashType);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.asc());
		List<Tuple> summaryList = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum().multiply(1000),
				QBinTransaction.binTransaction.cashType);
		return summaryList;
	}

	public JPAQuery getsasAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSASAllocation.sASAllocation);
		return jpaQuery;
	}

	@Override
	public List<SASAllocation> getsasAllocation(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getsasAllocation();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(IcmcId)
				.and(QSASAllocation.sASAllocation.binType.eq(CurrencyType.UNPROCESS))
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED))
				.and(QSASAllocation.sASAllocation.insertTime.between(sDate, eDate)));
		List<SASAllocation> sasAllocationList = jpaQuery.list(QSASAllocation.sASAllocation);
		return sasAllocationList;
	}

	public JPAQuery getIndentCash() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		return jpaQuery;
	}

	@Override
	public List<Indent> getIndentCash(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getIndentCash();
		jpaQuery.where(QIndent.indent.icmcId.eq(IcmcId).and(QIndent.indent.cashSource.ne(CashSource.RBI))
				.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED).or(QIndent.indent.status.eq(OtherStatus.PROCESSED)))
				.and(QIndent.indent.insertTime.between(sDate, eDate)));
		List<Indent> indentList = jpaQuery.list(QIndent.indent);
		return indentList;
	}

	public JPAQuery getBranchReceivedCash() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		return jpaQuery;
	}

	public JPAQuery getDiversionIRVCash() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionIRV.diversionIRV);
		return jpaQuery;
	}

	@Override
	public List<BranchReceipt> getBranchReceiptValue(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getBranchReceivedCash();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(IcmcId)
				.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate))
				.and(QBranchReceipt.branchReceipt.processedOrUnprocessed.eq("PROCESSED"))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED))
				.and(QBranchReceipt.branchReceipt.currencyType.ne(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.currencyType.ne(CurrencyType.FRESH))
				.and(QBranchReceipt.branchReceipt.currencyType.ne(CurrencyType.MUTILATED))
				.and(QBranchReceipt.branchReceipt.currencyType.ne(CurrencyType.COINS))
				.and(QBranchReceipt.branchReceipt.currencyType.ne(CurrencyType.ALL)));
		List<BranchReceipt> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt);
		return branchReceiptList;
	}

	@Override
	public List<DiversionIRV> getDiversionIRV(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getDiversionIRVCash();
		jpaQuery.where(QDiversionIRV.diversionIRV.icmcId.eq(IcmcId)
				.and(QDiversionIRV.diversionIRV.insertTime.between(sDate, eDate))
				.and(QDiversionIRV.diversionIRV.processedOrUnprocessed.eq("PROCESSED"))
				.and(QDiversionIRV.diversionIRV.status.eq(OtherStatus.RECEIVED))
				.and(QDiversionIRV.diversionIRV.currencyType.ne(CurrencyType.UNPROCESS))
				.and(QDiversionIRV.diversionIRV.currencyType.ne(CurrencyType.FRESH))
				.and(QDiversionIRV.diversionIRV.currencyType.ne(CurrencyType.MUTILATED))
				.and(QDiversionIRV.diversionIRV.currencyType.ne(CurrencyType.COINS))
				.and(QDiversionIRV.diversionIRV.currencyType.ne(CurrencyType.ALL)));
		List<DiversionIRV> diversionIRVList = jpaQuery.list(QDiversionIRV.diversionIRV);
		return diversionIRVList;
	}

}
