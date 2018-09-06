
package com.chest.currency.jpa.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
import com.chest.currency.entity.model.QAssignVaultCustodian;
import com.chest.currency.entity.model.QAuditorIndent;
import com.chest.currency.entity.model.QBankReceipt;
import com.chest.currency.entity.model.QBinMaster;
import com.chest.currency.entity.model.QBinTransaction;
import com.chest.currency.entity.model.QBranchReceipt;
import com.chest.currency.entity.model.QCRAAllocation;
import com.chest.currency.entity.model.QCustodianKeySet;
import com.chest.currency.entity.model.QDSB;
import com.chest.currency.entity.model.QDefineKeySet;
import com.chest.currency.entity.model.QDiscrepancy;
import com.chest.currency.entity.model.QDiscrepancyAllocation;
import com.chest.currency.entity.model.QDiversionIRV;
import com.chest.currency.entity.model.QForwardBundleForCRAPayment;
import com.chest.currency.entity.model.QFreshCurrency;
import com.chest.currency.entity.model.QFreshFromRBI;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.entity.model.QIndent;
import com.chest.currency.entity.model.QMachine;
import com.chest.currency.entity.model.QMachineAllocation;
import com.chest.currency.entity.model.QMachineMaintenance;
import com.chest.currency.entity.model.QMutilated;
import com.chest.currency.entity.model.QMutilatedIndent;
import com.chest.currency.entity.model.QProcess;
import com.chest.currency.entity.model.QRepeatabilityTestInput;
import com.chest.currency.entity.model.QRepeatabilityTestOutput;
import com.chest.currency.entity.model.QSuspenseOpeningBalance;
import com.chest.currency.entity.model.QUser;
import com.chest.currency.entity.model.RepeatabilityTestInput;
import com.chest.currency.entity.model.RepeatabilityTestOutput;
import com.chest.currency.entity.model.SuspenseOpeningBalance;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.Status;
import com.chest.currency.enums.YesNo;
import com.chest.currency.util.UtilityJpa;
import com.chest.currency.util.UtilityMapper;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

@Repository
public class ProcessingRoomJpaDaoImpl implements ProcessingRoomJpaDao {
	private static final Logger LOG = LoggerFactory.getLogger(BinDashBoardJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	private JPAQuery getFromQueryForIndent() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForMutilatedIndent() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMutilatedIndent.mutilatedIndent);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForProcess() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QProcess.process);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForICMC() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		return jpaQuery;
	}

	@Override
	public List<Indent> viewIndentRequest(BigInteger icmcId) {

		// StringExpression nameConcatenation =
		// QIndent.indent.bundle.coalesce(BigDecimal.ZERO).asString().concat(",");

		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.status.eq(OtherStatus.REQUESTED)));
		jpaQuery.groupBy(QIndent.indent.cashSource, QIndent.indent.denomination, QIndent.indent.bin);
		List<Tuple> indentTupleList = jpaQuery.list(QIndent.indent.cashSource, QIndent.indent.denomination,
				QIndent.indent.bin, QIndent.indent.bundle.sum());
		List<Indent> indentList = UtilityJpa.mapTuppleToIndent(indentTupleList);
		return indentList;
	}

	// code for the searching data on basis of data

	@Override
	public List<DiscrepancyAllocation> getDiscrepancyByDate(BigInteger icmcId, java.util.Date sDate,
			java.util.Date tDate, String normalOrSuspense) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation(); // getFromQueryForDiscrepancy();//getFromQueryForDiscrepancyAllocation
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.status.eq(OtherStatus.RECEIVED))
				.and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyDate.between(sDate, tDate))
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq(normalOrSuspense)));
		List<DiscrepancyAllocation> descripancyList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation);
		return descripancyList;
	}

	@Override
	public List<Indent> viewBinDetail(int denomination, String bin, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.status.eq(OtherStatus.REQUESTED))
				.and(QIndent.indent.denomination.eq(denomination)).and(QIndent.indent.bin.eq(bin)));
		List<Indent> indentList = jpaQuery.list(QIndent.indent);
		return indentList;
	}

	@Override
	public Indent viewUpdateIndentOtherBankRequest(BankReceipt otherBankReceiptdb, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId)
				.and(QIndent.indent.denomination.eq(otherBankReceiptdb.getDenomination()))
				.and(QIndent.indent.bundle.eq(otherBankReceiptdb.getBundle()))
				.and(QIndent.indent.cashSource.eq(CashSource.OTHERBANK))
				.and(QIndent.indent.cashReceiptId.eq(otherBankReceiptdb.getId())));
		Indent indentTxn = jpaQuery.singleResult(QIndent.indent);

		return indentTxn;
	}

	@Override
	public Indent viewUpdateIndentIVRRequest(DiversionIRV diversionIRV, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(
				QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.denomination.eq(diversionIRV.getDenomination()))
						.and(QIndent.indent.bundle.eq(diversionIRV.getBundle()))
						.and(QIndent.indent.cashSource.eq(CashSource.DIVERSION))
						.and(QIndent.indent.cashReceiptId.eq(diversionIRV.getId())));
		Indent indentTxn = jpaQuery.singleResult(QIndent.indent);

		return indentTxn;
	}

	private JPAQuery getFromQueryForIndentRequestFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction).orderBy(QBinTransaction.binTransaction.insertTime.asc());
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getBinNumListForIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForIndentRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.cashSource.eq(cashSource))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.UNPROCESS))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binCategoryType)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public List<Indent> getBinFromIndent(int denomination) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(
				QIndent.indent.denomination.eq(denomination).and(QIndent.indent.status.eq(OtherStatus.REQUESTED)));
		List<Indent> indentList = jpaQuery.list(QIndent.indent);
		return indentList;
	}

	@Override
	public boolean insertIndentRequest(List<Indent> eligibleIndentRequestList) {
		for (Indent indent : eligibleIndentRequestList) {
			em.persist(indent);
		}
		return true;
	}

	@Override
	public Indent getIndentById(long id) {
		return em.find(Indent.class, id);
	}

	@Override
	public boolean updateIndentRequest(Indent indent) {
		QIndent qIndent = QIndent.indent;
		Long count = new JPAUpdateClause(em, qIndent)
				.where(QIndent.indent.icmcId.eq(indent.getIcmcId()).and(QIndent.indent.bin.eq(indent.getBin().trim()))
						.and(QIndent.indent.description.isNull()))
				.set(qIndent.status, OtherStatus.ACCEPTED).set(qIndent.updateTime, indent.getUpdateTime()).execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<Indent> getAggregatedIndentRequestForMachineAllocation(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.status.eq(OtherStatus.ACCEPTED))
				.and(QIndent.indent.bundle.gt(0).and(QIndent.indent.cashSource.eq(cashSource))));
		jpaQuery.groupBy(QIndent.indent.denomination, QIndent.indent.cashSource);
		jpaQuery.orderBy(QIndent.indent.denomination.desc());
		List<Tuple> tupleList = jpaQuery.list(QIndent.indent.denomination, QIndent.indent.cashSource,
				QIndent.indent.bundle.sum(), QIndent.indent.pendingBundleRequest.sum());

		LOG.info("INDENT REQUEST DATA FOR MACHINE ALLOCATION");
		List<Indent> indentList = UtilityJpa.mapTuppleToBranchIndentForMachineAllocation(tupleList);
		return indentList;
	}

	@Override

	public Indent getIndentDataById(long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.id.eq(id).and(QIndent.indent.icmcId.eq(icmcId)));
		Indent indent = jpaQuery.singleResult(QIndent.indent);
		return indent;
	}

	private JPAQuery getFromQueryForBinFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	@Override
	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinFromBinTxn();
		jpaQuery.where(
				QBinTransaction.binTransaction.binNumber.eq(bin).and(QBinTransaction.binTransaction.icmcId.eq(icmcId))
						.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public boolean updateIndentStatus(Indent indent) {
		em.merge(indent);
		return true;
	}

	@Override
	public int deleteDataFromBinTxn(BinTransaction txnBin) {

		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPADeleteClause(em, qBinTransaction).where(qBinTransaction.binNumber.eq(txnBin.getBinNumber())
				.and(QBinTransaction.binTransaction.icmcId.eq(txnBin.getIcmcId()))).execute();

		return (int) count;
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

	@Override
	public boolean updateBinTxn(BinTransaction binTransaction) {
		em.merge(binTransaction);
		return true;
	}

	@Override
	public boolean insertInMachineAllocation(MachineAllocation machineAllocation) {
		em.persist(machineAllocation);
		// em.merge(machineAllocation);
		return true;
	}

	@Override
	public boolean updateBundleInIndent(Indent indent) {
		em.merge(indent);
		return true;
	}

	@Override
	public List<Process> getProcessedDataList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForProcess();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.status.eq(1))
		// .and(QProcess.process.insertTime.between(sDate, eDate))
		);
		LOG.info("PROCESSED DATA");
		List<Process> processList = jpaQuery.list(QProcess.process);
		return processList;
	}

	private JPAQuery getFromQueryForCashReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinMaster.binMaster);
		return jpaQuery;
	}

	@Override
	public List<BinMaster> getPriorityBinListByType(BinMaster master) {
		LOG.info("FETCH FROM BIN MASTER BY DENOMINATION");
		JPAQuery jpaQuery = getFromQueryForCashReceipt();
		List<BinMaster> binList = getFirstPriorityBinMaster(master, jpaQuery);
		getSecondPriorityBinMaster(master, binList);
		getThirdPriorityBinMaster(master, binList);
		getFourthPriorityBinMaster(master, binList);
		getFifthPriorityBinMaster(master, binList);
		return binList;
	}

	private void getFifthPriorityBinMaster(BinMaster master, List<BinMaster> binList) {
		JPAQuery jpaQuery;
		jpaQuery = getFromQueryForCashReceipt();
		jpaQuery.where(QBinMaster.binMaster.fifthPriority.eq(master.getFirstPriority())
				.and(QBinMaster.binMaster.icmcId.eq(master.getIcmcId())).and(QBinMaster.binMaster.isAllocated.eq(0)));

		jpaQuery.orderBy(QBinMaster.binMaster.locationPriority.asc()).orderBy(QBinMaster.binMaster.id.asc());

		binList.addAll(jpaQuery.list(QBinMaster.binMaster));
	}

	private void getFourthPriorityBinMaster(BinMaster master, List<BinMaster> binList) {
		JPAQuery jpaQuery;
		jpaQuery = getFromQueryForCashReceipt();
		jpaQuery.where(QBinMaster.binMaster.fourthPriority.eq(master.getFirstPriority())
				.and(QBinMaster.binMaster.icmcId.eq(master.getIcmcId())).and(QBinMaster.binMaster.isAllocated.eq(0)));

		jpaQuery.orderBy(QBinMaster.binMaster.locationPriority.asc()).orderBy(QBinMaster.binMaster.id.asc());

		binList.addAll(jpaQuery.list(QBinMaster.binMaster));
	}

	private void getThirdPriorityBinMaster(BinMaster master, List<BinMaster> binList) {
		JPAQuery jpaQuery;
		jpaQuery = getFromQueryForCashReceipt();
		jpaQuery.where(QBinMaster.binMaster.thirdPriority.eq(master.getFirstPriority())
				.and(QBinMaster.binMaster.icmcId.eq(master.getIcmcId())).and(QBinMaster.binMaster.isAllocated.eq(0)));

		jpaQuery.orderBy(QBinMaster.binMaster.locationPriority.asc()).orderBy(QBinMaster.binMaster.id.asc());

		binList.addAll(jpaQuery.list(QBinMaster.binMaster));
	}

	private void getSecondPriorityBinMaster(BinMaster master, List<BinMaster> binList) {
		JPAQuery jpaQuery;
		jpaQuery = getFromQueryForCashReceipt();
		jpaQuery.where(QBinMaster.binMaster.secondPriority.eq(master.getFirstPriority())
				.and(QBinMaster.binMaster.icmcId.eq(master.getIcmcId())).and(QBinMaster.binMaster.isAllocated.eq(0)));

		jpaQuery.orderBy(QBinMaster.binMaster.locationPriority.asc()).orderBy(QBinMaster.binMaster.id.asc());

		binList.addAll(jpaQuery.list(QBinMaster.binMaster));
	}

	private List<BinMaster> getFirstPriorityBinMaster(BinMaster master, JPAQuery jpaQuery) {
		jpaQuery.where(QBinMaster.binMaster.firstPriority.eq(master.getFirstPriority())
				.and(QBinMaster.binMaster.icmcId.eq(master.getIcmcId())).and(QBinMaster.binMaster.isAllocated.eq(0)));

		jpaQuery.orderBy(QBinMaster.binMaster.locationPriority.asc()).orderBy(QBinMaster.binMaster.id.asc());

		List<BinMaster> binList = jpaQuery.list(QBinMaster.binMaster);
		return binList;
	}

	private JPAQuery getFromQueryForProcessFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx) {
		JPAQuery jpaQuery = getFromQueryForProcessFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTx.getIcmcId())
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binTx.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes))
				.and(QBinTransaction.binTransaction.denomination.eq(binTx.getDenomination())
						.and(QBinTransaction.binTransaction.receiveBundle
								.lt(QBinTransaction.binTransaction.maxCapacity))
						.and(QBinTransaction.binTransaction.binType.eq(binTx.getBinType()))));

		List<BinTransaction> binList = jpaQuery.list(QBinTransaction.binTransaction);

		return binList;
	}

	private JPAQuery getFromQueryForBinMaster() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinMaster.binMaster);
		return jpaQuery;
	}

	@Override
	public boolean updateBinMasterForProcess(BinMaster binMaster) {

		JPAQuery jpaQuery = getFromQueryForBinMaster();
		jpaQuery.where(QBinMaster.binMaster.binNumber.eq(binMaster.getBinNumber()).and(
				QBinMaster.binMaster.icmcId.eq(binMaster.getIcmcId()).and(QBinMaster.binMaster.isAllocated.eq(0))));
		BinMaster dbBinMaster = jpaQuery.singleResult(QBinMaster.binMaster);

		if (dbBinMaster != null) {
			dbBinMaster.setIsAllocated(1);
			em.merge(dbBinMaster);
		} else {
			return false;
		}

		return true;
	}

	@Override
	public boolean insertInBinTxn(BinTransaction binTransaction) {
		new JPADeleteClause(em, QBinTransaction.binTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(binTransaction.getIcmcId())
						.and(QBinTransaction.binTransaction.binNumber.eq(binTransaction.getBinNumber())
								.and(QBinTransaction.binTransaction.status.eq(BinStatus.EMPTY))))
				.execute();
		em.persist(binTransaction);
		return true;
	}

	@Override
	public boolean updateInBinTxn(BinTransaction binTransaction) {
		em.merge(binTransaction);
		return true;
	}

	@Override
	public boolean createProcess(List<Process> processList) {
		for (Process process : processList) {
			em.persist(process);
		}
		return true;
	}

	@Override
	public boolean updateProcessStatus(Process process) {
		QProcess qProcess = QProcess.process;
		long count = new JPAUpdateClause(em, qProcess)
				.where(QProcess.process.icmcId.eq(process.getIcmcId()).and(QProcess.process.id.eq(process.getId())))
				.set(qProcess.status, 0).execute();
		;
		return count > 0 ? true : false;
	}

	private JPAQuery getFromQueryForBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	@Override
	public List<Tuple> indentSummary(BigInteger icmcId, CashSource cashSource) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashSource.eq(cashSource))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.UNPROCESS))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.binCategoryType);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.desc());
		List<Tuple> list = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.insertTime.min().as("insertTime"),
				QBinTransaction.binTransaction.receiveBundle.sum(),
				QBinTransaction.binTransaction.pendingBundleRequest.sum(),
				QBinTransaction.binTransaction.binCategoryType);
		return list;
	}

	public boolean UploadDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet) {
		for (DefineKeySet definekeyset : list) {
			definekeyset.setInsertBy(defineKeySet.getInsertBy());
			definekeyset.setUpdateBy(defineKeySet.getUpdateBy());
			definekeyset.setIcmcId(defineKeySet.getIcmcId());
			Calendar now = Calendar.getInstance();
			definekeyset.setInsertTime(now);
			definekeyset.setUpdateTime(now);
			em.persist(definekeyset);
		}
		return false;
	}

	private JPAQuery getFromQueryForDefineKeySet() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDefineKeySet.defineKeySet);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForCustodiannameForKeySet() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCustodianKeySet.custodianKeySet);
		return jpaQuery;
	}

	public List<String> getDefineKeySet(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCustodiannameForKeySet();
		jpaQuery.where(QCustodianKeySet.custodianKeySet.icmcId.eq(icmcId));
		jpaQuery.groupBy(QCustodianKeySet.custodianKeySet.custodian);
		List<String> custodianList = jpaQuery.list(QCustodianKeySet.custodianKeySet.custodian);
		return custodianList;
	}

	public DefineKeySet defineKeySetRecordForModify(Long id) {

		return em.find(DefineKeySet.class, id);
	}

	public boolean UpdateDefineKeySet(List<DefineKeySet> list, DefineKeySet defineKeySet) {
		for (DefineKeySet definekeyset : list) {
			definekeyset.setId(defineKeySet.getId());
			definekeyset.setInsertBy(defineKeySet.getInsertBy());
			definekeyset.setUpdateBy(defineKeySet.getUpdateBy());
			definekeyset.setIcmcId(defineKeySet.getIcmcId());
			Calendar now = Calendar.getInstance();
			definekeyset.setInsertTime(now);
			definekeyset.setUpdateTime(now);
			em.merge(definekeyset);
		}
		return false;
	}

	private JPAQuery getFromQueryForAssignVaultCustodian() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QAssignVaultCustodian.assignVaultCustodian);
		return jpaQuery;
	}

	public List<AssignVaultCustodian> getListAssignVaultCustodian(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForAssignVaultCustodian();
		jpaQuery.where(QAssignVaultCustodian.assignVaultCustodian.icmcId.eq(icmcId)
				.and(QAssignVaultCustodian.assignVaultCustodian.insertTime.between(sDate, eDate)));
		List<AssignVaultCustodian> indentList = jpaQuery.list(QAssignVaultCustodian.assignVaultCustodian);
		return indentList;
	}

	public boolean saveAssignVaultCustodian(AssignVaultCustodian assignVaultCustodian) {
		em.persist(assignVaultCustodian);
		return true;
	}

	public AssignVaultCustodian assignVaultCustodianRecordForModify(Long id) {

		return em.find(AssignVaultCustodian.class, id);
	}

	public boolean updateAssignVaultCustodian(AssignVaultCustodian assignVaultCustodian) {
		em.merge(assignVaultCustodian);
		return true;
	}

	private JPAQuery getFromQueryForProcessing() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineAllocation.machineAllocation);
		return jpaQuery;
	}

	@Override
	public List<MachineAllocation> getMachineAllocationRecordForProcessing(BigInteger icmcId, Calendar sDate,
			Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForProcessing();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.insertTime.between(sDate, eDate)));
		List<MachineAllocation> list = jpaQuery.list(QMachineAllocation.machineAllocation);
		return list;
	}

	private JPAQuery getFromQueryForRepeatabilityTestInput() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QRepeatabilityTestInput.repeatabilityTestInput);
		return jpaQuery;
	}

	public List<RepeatabilityTestInput> getRepeatabilityTestInput(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForRepeatabilityTestInput();
		jpaQuery.where(QRepeatabilityTestInput.repeatabilityTestInput.icmcId.eq(icmcId));
		List<RepeatabilityTestInput> listRepeatabilit = jpaQuery.list(QRepeatabilityTestInput.repeatabilityTestInput);
		return listRepeatabilit;
	}

	public boolean insertRepeatabilityTestInput(RepeatabilityTestInput repeatabilityTestInput) {
		em.persist(repeatabilityTestInput);
		return true;
	}

	public RepeatabilityTestInput repeatabilityTestInputRecordForModify(Long id) {

		return em.find(RepeatabilityTestInput.class, id);
	}

	public boolean updateRepeatabilityTestInput(RepeatabilityTestInput repeatabilityTestInput) {
		em.merge(repeatabilityTestInput);
		return true;
	}

	private JPAQuery getFromQueryForRepeatabilityTestOutput() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QRepeatabilityTestOutput.repeatabilityTestOutput);
		return jpaQuery;
	}

	public List<RepeatabilityTestOutput> getRepeatabilityTestOutput(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForRepeatabilityTestOutput();
		jpaQuery.where(QRepeatabilityTestOutput.repeatabilityTestOutput.icmcId.eq(icmcId));
		List<RepeatabilityTestOutput> listRepeatabilit = jpaQuery
				.list(QRepeatabilityTestOutput.repeatabilityTestOutput);
		return listRepeatabilit;
	}

	public boolean insertRepeatabilityTestOutput(RepeatabilityTestOutput repeatabilityTestOutput) {
		em.persist(repeatabilityTestOutput);
		return true;
	}

	public RepeatabilityTestOutput repeatabilityTestOutputRecordForModify(Long id) {
		return em.find(RepeatabilityTestOutput.class, id);
	}

	public boolean updateRepeatabilityTestOutput(RepeatabilityTestOutput repeatabilityTestOutput) {
		em.merge(repeatabilityTestOutput);
		return true;
	}

	public boolean modifyDefineKeySet(DefineKeySet defineKeySet) {
		em.merge(defineKeySet);
		return true;

	}

	private JPAQuery getFromQueryForFreshCurrency() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QFreshCurrency.freshCurrency);
		return jpaQuery;

	}

	public List<FreshCurrency> getFreshCurrency(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForFreshCurrency();
		jpaQuery.where(QFreshCurrency.freshCurrency.icmcId.eq(icmcId));
		List<FreshCurrency> listF = jpaQuery.list(QFreshCurrency.freshCurrency);
		return listF;
	}

	public boolean insertFreshCurrency(FreshCurrency freshCurrency) {
		em.persist(freshCurrency);
		return true;
	}

	public FreshCurrency freshCurrencyForModify(Long id) {

		return em.find(FreshCurrency.class, id);
	}

	public boolean updateFreshCurrency(FreshCurrency freshCurrency) {
		em.merge(freshCurrency);
		return true;
	}

	@Override
	public Indent viewUpdateIndentRequest(DSB dsbdb, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.denomination.eq(dsbdb.getDenomination()))
				.and(QIndent.indent.bundle.eq(dsbdb.getBundle())).and(QIndent.indent.cashSource.eq(CashSource.DSB))
				.and(QIndent.indent.cashReceiptId.eq(dsbdb.getId())));
		Indent indentTxn = jpaQuery.singleResult(QIndent.indent);

		return indentTxn;
	}

	@Override
	public boolean updateIndent(Indent indent) {
		em.merge(indent);
		return true;
	}

	@Override
	public boolean updateIndentStatusForCancel(Indent indent) {
		QIndent qIndent = QIndent.indent;
		Long count = new JPAUpdateClause(em, qIndent).where((QIndent.indent.id.eq(indent.getId())))
				.set(qIndent.status, OtherStatus.CANCELLED).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean saveDiscrepancy(Discrepancy discrepancy) {
		em.persist(discrepancy);
		return true;
	}

	private JPAQuery getFromQueryForDiscrepancy() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiscrepancy.discrepancy);
		return jpaQuery;
	}

	@Override
	public List<DiscrepancyAllocation> getDiscrepancy(BigInteger icmcId, String normalOrSuspense) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation(); // getFromQueryForDiscrepancy();//getFromQueryForDiscrepancyAllocation
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.status.eq(OtherStatus.RECEIVED))
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq(normalOrSuspense)));
		List<DiscrepancyAllocation> descripancyList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation);
		return descripancyList;
	}

	private JPAQuery getFromQueryForFreshFromRBI() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QFreshFromRBI.freshFromRBI);
		return jpaQuery;
	}

	@Override
	public List<FreshFromRBI> getFreshFromRBIRecord(User user) {
		JPAQuery jpaQuery = getFromQueryForFreshFromRBI();
		jpaQuery.where(QFreshFromRBI.freshFromRBI.icmcId.eq(user.getIcmcId()));
		List<FreshFromRBI> freshList = jpaQuery.list(QFreshFromRBI.freshFromRBI);
		return freshList;
	}

	@Override
	public boolean updateBinTransaction(BinTransaction binTransaction) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qBinTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(binTransaction.getIcmcId())
						.and(QBinTransaction.binTransaction.binNumber.eq(binTransaction.getBinNumber())))
				.set(qBinTransaction.status, BinStatus.EMPTY).execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<Tuple> indentSummaryForFresh(BigInteger icmcId, CashSource cashSource) {
		JPAQuery jpaQuery = getFromQueryForFreshFromRBI();
		jpaQuery.where(
				QFreshFromRBI.freshFromRBI.icmcId.eq(icmcId).and(QFreshFromRBI.freshFromRBI.cashSource.eq(cashSource))
						.and(QFreshFromRBI.freshFromRBI.cashType.ne(CashType.COINS)));
		jpaQuery.groupBy(QFreshFromRBI.freshFromRBI.denomination, QFreshFromRBI.freshFromRBI.rbiOrderNo,
				QFreshFromRBI.freshFromRBI.binCategoryType);
		List<Tuple> list = jpaQuery.list(QFreshFromRBI.freshFromRBI.rbiOrderNo,
				QFreshFromRBI.freshFromRBI.binCategoryType, QFreshFromRBI.freshFromRBI.denomination,
				QFreshFromRBI.freshFromRBI.bundle.sum(), QFreshFromRBI.freshFromRBI.pendingBundleRequest.sum(),
				QFreshFromRBI.freshFromRBI.insertTime.min().as("insertTime"));
		return list;
	}

	private JPAQuery getFromQueryForMachine() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachine.machine);
		return jpaQuery;
	}

	@Override
	public List<Machine> getMachineNumber(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMachine();
		jpaQuery.where(QMachine.machine.icmcId.eq(icmcId));
		List<Machine> machineList = jpaQuery.list(QMachine.machine);
		return machineList;
	}

	public List<DefineKeySet> getKeyNumber(DefineKeySet defineKeySet) {
		JPAQuery jpaQuery = getFromQueryForDefineKeySet();
		jpaQuery.where(QDefineKeySet.defineKeySet.custodian.equalsIgnoreCase(defineKeySet.getCustodian()));
		List<DefineKeySet> keyNumberList = jpaQuery.list(QDefineKeySet.defineKeySet);
		return keyNumberList;
	}

	public List<DefineKeySet> getLocationOfLock(DefineKeySet defineKeySet) {
		JPAQuery jpaQuery = getFromQueryForDefineKeySet();
		jpaQuery.where(QDefineKeySet.defineKeySet.custodian.equalsIgnoreCase(defineKeySet.getCustodian()));
		List<DefineKeySet> locationOfLockList = jpaQuery.list(QDefineKeySet.defineKeySet);
		return locationOfLockList;
	}

	@Override
	public List<Tuple> getKeySetDetail(String custodian, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDefineKeySet();
		jpaQuery.where(
				QDefineKeySet.defineKeySet.custodian.eq(custodian).and(QDefineKeySet.defineKeySet.icmcId.eq(icmcId)));
		List<Tuple> keySetList = jpaQuery.list(QDefineKeySet.defineKeySet.custodian,
				QDefineKeySet.defineKeySet.keyNumber, QDefineKeySet.defineKeySet.locationOfLock,
				QDefineKeySet.defineKeySet.id);
		return keySetList;
	}

	private JPAQuery getFromQueryForIndentRequestFromBranchReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt).orderBy(QBranchReceipt.branchReceipt.insertTime.asc());
		return jpaQuery;
	}

	@Override
	public List<BranchReceipt> getBinNumListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForIndentRequestFromBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.denomination.eq(denomination))
				.and(QBranchReceipt.branchReceipt.cashSource.eq(cashSource))
				.and(QBranchReceipt.branchReceipt.bundle.gt(0))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.binCategoryType.eq(binCategoryType))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED)));
		List<BranchReceipt> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt);
		return branchReceiptList;
	}

	@Override
	public List<BranchReceipt> getRetunBinListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForIndentRequestFromBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.denomination.eq(denomination))
				.and(QBranchReceipt.branchReceipt.cashSource.eq(cashSource))
				.and(QBranchReceipt.branchReceipt.bundle.gt(0))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.binCategoryType.eq(binCategoryType))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED))
				.and(QBranchReceipt.branchReceipt.filepath.isNotNull()).and(QBranchReceipt.branchReceipt.solId.isNull())
				.and(QBranchReceipt.branchReceipt.branch.isNull()).and(QBranchReceipt.branchReceipt.srNumber.isNull()));
		jpaQuery.orderBy(QBranchReceipt.branchReceipt.insertTime.desc());
		List<BranchReceipt> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt);
		return branchReceiptList;
	}

	@Override
	public List<BranchReceipt> getBranchUploadBinListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForIndentRequestFromBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.denomination.eq(denomination))
				.and(QBranchReceipt.branchReceipt.cashSource.eq(cashSource))
				.and(QBranchReceipt.branchReceipt.bundle.gt(0))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.binCategoryType.eq(binCategoryType))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED))
				.and(QBranchReceipt.branchReceipt.solId.isNull()).and(QBranchReceipt.branchReceipt.filepath.isNull()));
		List<BranchReceipt> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt);
		return branchReceiptList;
	}

	@Override
	public List<BranchReceipt> getInsertBinListForIndentFromBranchReceipt(Integer denomination, BigDecimal bundle,
			BigInteger icmcId, CashSource cashSource, BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForIndentRequestFromBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.denomination.eq(denomination))
				.and(QBranchReceipt.branchReceipt.cashSource.eq(cashSource))
				.and(QBranchReceipt.branchReceipt.bundle.gt(0))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.binCategoryType.eq(binCategoryType))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED))
				.and(QBranchReceipt.branchReceipt.solId.isNotNull())
				.and(QBranchReceipt.branchReceipt.filepath.isNotNull()));
		List<BranchReceipt> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt);
		return branchReceiptList;
	}

	@Override
	public boolean updateBranchReceipt(BranchReceipt br) {
		em.merge(br);
		return true;
	}

	@Override
	public boolean updateBranchReceiptForSas(BranchReceipt br) {
		LOG.info("br.getId() update branch receipt " + br.getId());

		QBranchReceipt qBranchReceipt = QBranchReceipt.branchReceipt;
		Long count = new JPAUpdateClause(em, qBranchReceipt)
				.where(qBranchReceipt.icmcId.eq(br.getIcmcId()).and(qBranchReceipt.id.eq(br.getId())))
				.set(qBranchReceipt.status, OtherStatus.PROCESSED).execute();
		LOG.info("count " + count);
		return count > 0 ? true : false;
	}

	@Override
	public boolean saveCRAPaymentProcessRecord(ProcessBundleForCRAPayment processBundleForCRAPayment) {
		em.persist(processBundleForCRAPayment);
		return true;
	}

	private JPAQuery getFromQueryForForwardBundleForCRAPayment() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QForwardBundleForCRAPayment.forwardBundleForCRAPayment);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getForwardBundleTotalForCRAPayment(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForForwardBundleForCRAPayment();
		jpaQuery.where(QForwardBundleForCRAPayment.forwardBundleForCRAPayment.icmcId.eq(icmcId));
		jpaQuery.groupBy(QForwardBundleForCRAPayment.forwardBundleForCRAPayment.denomination);
		List<Tuple> list = jpaQuery.list(QForwardBundleForCRAPayment.forwardBundleForCRAPayment.denomination,
				QForwardBundleForCRAPayment.forwardBundleForCRAPayment.bundle.sum());
		return list;
	}

	private JPAQuery getFromQueryForForwardBundleForCRA() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QForwardBundleForCRAPayment.forwardBundleForCRAPayment);
		return jpaQuery;
	}

	@Override
	public ForwardBundleForCRAPayment getBundleFromForwardCRA(ForwardBundleForCRAPayment forwardBundle) {
		JPAQuery jpaQuery = getFromQueryForForwardBundleForCRA();
		jpaQuery.where(QForwardBundleForCRAPayment.forwardBundleForCRAPayment.denomination
				.eq(forwardBundle.getDenomination()));
		ForwardBundleForCRAPayment denomination = jpaQuery
				.singleResult(QForwardBundleForCRAPayment.forwardBundleForCRAPayment);
		return denomination;
	}

	@Override
	public boolean updateBundleForwardBundleForCRA(ForwardBundleForCRAPayment forwardBundleInCRAPayment) {
		// em.merge(forwardBundleInCRAPayment);
		QForwardBundleForCRAPayment qForwardBudleForPayment = QForwardBundleForCRAPayment.forwardBundleForCRAPayment;
		Long count = new JPAUpdateClause(em, qForwardBudleForPayment)
				.where(QForwardBundleForCRAPayment.forwardBundleForCRAPayment.denomination
						.eq(forwardBundleInCRAPayment.getDenomination()))
				.set(qForwardBudleForPayment.bundle, forwardBundleInCRAPayment.getBundle()).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean insertBranchIndentRequest(List<BranchReceipt> eligibleBranchIndentRequestList) {
		for (BranchReceipt branchReceipt : eligibleBranchIndentRequestList) {
			em.merge(branchReceipt);
		}
		return true;
	}

	@Override
	public boolean updateFreshFromRBI(FreshFromRBI br) {
		em.merge(br);
		return true;
	}

	@Override
	public List<Tuple> indentSummaryForFreshFromBinTxn(BigInteger icmcId, CashSource cashSource) {
		JPAQuery jpaQuery = getFromQueryForBinFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.cashSource.eq(cashSource))
				.and(QBinTransaction.binTransaction.cashType.ne(CashType.COINS))
				.and(QBinTransaction.binTransaction.verified.eq(YesNo.No)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.rbiOrderNo,
				QBinTransaction.binTransaction.binCategoryType);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.desc());
		List<Tuple> list = jpaQuery.list(QBinTransaction.binTransaction.rbiOrderNo,
				QBinTransaction.binTransaction.binCategoryType, QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum(),
				QBinTransaction.binTransaction.pendingBundleRequest.sum(),
				QBinTransaction.binTransaction.insertTime.min().as("insertTime"));
		return list;
	}

	@Override
	public List<BinTransaction> getBinNumListForFreshIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			CashSource cashSource, BinCategoryType binCategoryType, String rbiOrderNo) {
		JPAQuery jpaQuery = getFromQueryForIndentRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.rbiOrderNo.eq(rbiOrderNo))
				.and(QBinTransaction.binTransaction.cashSource.eq(cashSource))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binCategoryType)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	private JPAQuery getFromQueryForCRAForProcessing() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRAAllocation.cRAAllocation);
		return jpaQuery;
	}

	@Override
	public List<CRAAllocation> getDataFromCRAAllocationForProcessing(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCRAForProcessing();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId).and(QCRAAllocation.cRAAllocation.forward.gt(0))
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.REQUESTED)));
		List<CRAAllocation> craAllocationList = jpaQuery.list(QCRAAllocation.cRAAllocation);
		return craAllocationList;
	}

	@Override
	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancy();
		jpaQuery.where(QDiscrepancy.discrepancy.icmcId.eq(icmcId)
				.and(QDiscrepancy.discrepancy.insertTime.between(sDate, eDate)));
		return jpaQuery.list(QDiscrepancy.discrepancy);
	}

	@Override
	public CRAAllocation getPendingBundleById(CRAAllocation craAllocation) {
		JPAQuery jpaQuery = getFromQueryForCRAForProcessing();
		jpaQuery.where(QCRAAllocation.cRAAllocation.id.eq(craAllocation.getId())
				.and(QCRAAllocation.cRAAllocation.icmcId.eq(craAllocation.getIcmcId())));
		CRAAllocation craAllocationData = jpaQuery.singleResult(QCRAAllocation.cRAAllocation);
		return craAllocationData;
	}

	@Override
	public boolean updateForwarPending(CRAAllocation craAllocation) {
		QCRAAllocation qCraAllocation = QCRAAllocation.cRAAllocation;
		Long count = new JPAUpdateClause(em, qCraAllocation)
				.where(QCRAAllocation.cRAAllocation.icmcId.eq(craAllocation.getIcmcId())
						.and(QCRAAllocation.cRAAllocation.id.eq(craAllocation.getId())))
				// .set(QCRAAllocation.cRAAllocation.bundle,
				// craAllocation.getBundle())
				.set(QCRAAllocation.cRAAllocation.pendingRequestedBundle, craAllocation.getPendingRequestedBundle())
				.execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<Indent> getIndentListForMachineAllocation(BigInteger icmcId, Integer denomination,
			CashSource cashSource) {
		JPAQuery jpaQuery = getFromQueryForIndent();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.denomination.eq(denomination))
				.and(QIndent.indent.bundle.gt(0)).and(QIndent.indent.status.eq(OtherStatus.ACCEPTED))
				.and(QIndent.indent.cashSource.eq(cashSource)));
		List<Indent> indentList = jpaQuery.list(QIndent.indent);
		return indentList;
	}

	@Override
	public BigDecimal getTotalBundleInBin(int denomination, String bin, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.binNumber.eq(bin)));
		BigDecimal totalBundle = jpaQuery.singleResult(QBinTransaction.binTransaction.receiveBundle);
		return totalBundle;
	}

	private JPAQuery getFromQueryForMachineAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineAllocation.machineAllocation);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getMachineAllocationRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.insertTime.between(sDate, eDate)));

		jpaQuery.groupBy(QMachineAllocation.machineAllocation.denomination);
		jpaQuery.orderBy(QMachineAllocation.machineAllocation.denomination.desc());

		// List<Tuple> summaryforCoinList =
		// jpaQuery.list(QBinTransaction.binTransaction.binType,QBinTransaction.binTransaction.denomination,
		// QBinTransaction.binTransaction.receiveBundle.sum());

		List<Tuple> machineList = jpaQuery.list(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.pendingBundle.sum());
		return machineList;
	}

	@Override
	public List<Tuple> getProcessRecord(BigInteger icmcId, Calendar sDate, Calendar eDate, CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForProcess();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.insertTime.between(sDate, eDate))
				.and(QProcess.process.currencyType.eq(currencyType)).and(QProcess.process.status.eq(1)));
		jpaQuery.groupBy(QProcess.process.denomination, QProcess.process.currencyType);
		jpaQuery.orderBy(QProcess.process.denomination.desc());

		List<Tuple> ProcessRecordList = jpaQuery.list(QProcess.process.currencyType, QProcess.process.denomination,
				QProcess.process.bundle.sum());
		return ProcessRecordList;

	}

	@Override
	public List<Integer> getMachineNumberList(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMachine();
		jpaQuery.where(QMachine.machine.icmcId.eq(icmcId));
		jpaQuery.orderBy(QMachine.machine.machineNo.asc());
		List<Integer> machineNumberList = jpaQuery.list(QMachine.machine.machineNo);
		return machineNumberList;
	}

	private JPAQuery getFromQueryForDiscrepancyAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiscrepancyAllocation.discrepancyAllocation);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getDiscrepancyListForIOReport(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.insertTime.between(sDate, eDate)));

		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType,
				QDiscrepancyAllocation.discrepancyAllocation.denomination);

		jpaQuery.orderBy(QDiscrepancyAllocation.discrepancyAllocation.denomination.desc());

		List<Tuple> processDiscrepancyList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType,
				QDiscrepancyAllocation.discrepancyAllocation.denomination);
		return processDiscrepancyList;
	}

	private JPAQuery getFromQueryForPendingBundleFromMachineAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineAllocation.machineAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForPendingBundleFromAuditorIndent() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QAuditorIndent.auditorIndent);
		return jpaQuery;
	}

	@Override
	public List<MachineAllocation> getPendingBundleFromMachineAllocation(BigInteger icmcId, Integer denomination,
			String machineOrManual) {
		JPAQuery jpaQuery = getFromQueryForPendingBundleFromMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.ismanual.eq(machineOrManual))
				.and(QMachineAllocation.machineAllocation.status.eq(OtherStatus.REQUESTED))
				.and(QMachineAllocation.machineAllocation.denomination.eq(denomination)));
		List<MachineAllocation> pendingBundleList = jpaQuery.list(QMachineAllocation.machineAllocation);
		return pendingBundleList;
	}

	@Override
	public boolean updatePendingBundleInMachineAllocation(MachineAllocation machineAllocation) {
		em.merge(machineAllocation);
		return true;
	}

	@Override
	public String getMachineSerialNo(BigInteger icmcId, int machineNo) {
		JPAQuery jpaQuery = getFromQueryForMachine();
		jpaQuery.where(QMachine.machine.icmcId.eq(icmcId).and(QMachine.machine.machineNo.eq(machineNo)));
		return jpaQuery.singleResult(QMachine.machine.machineSINo);
	}

	@Override
	public List<Tuple> getPendingBundle(BigInteger icmcId, Calendar sDate, Calendar edate) {
		JPAQuery jpaQuery = getFromQueryForPendingBundleFromMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.pendingBundle.gt(0))
				// .and(QMachineAllocation.machineAllocation.insertTime.between(sDate,
				// edate))
				.and(QMachineAllocation.machineAllocation.ismanual.eq("YES")));
		jpaQuery.groupBy(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.cashSource);
		List<Tuple> bundleList = jpaQuery.list(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.pendingBundle.sum(),
				QMachineAllocation.machineAllocation.cashSource);
		return bundleList;
	}

	@Override
	public List<Tuple> getPendingBundleByMachine(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForPendingBundleFromMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.pendingBundle.gt(0))
				// .and(QMachineAllocation.machineAllocation.insertTime.between(sDate,
				// eDate))
				.and(QMachineAllocation.machineAllocation.ismanual.eq("NO")));
		jpaQuery.groupBy(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.cashSource);
		List<Tuple> bundleList = jpaQuery.list(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.pendingBundle.sum(),
				QMachineAllocation.machineAllocation.cashSource);
		return bundleList;
	}

	@Override
	public List<BinTransaction> getBinTxnList(Process process, User user) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(user.getIcmcId())
				.and(QBinTransaction.binTransaction.binCategoryType.eq(process.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.denomination.eq(process.getDenomination())
						.and(QBinTransaction.binTransaction.binType.eq(process.getCurrencyType()))
						.and(QBinTransaction.binTransaction.cashSource.eq(process.getCashSource()))
						.and(QBinTransaction.binTransaction.receiveBundle
								.lt(QBinTransaction.binTransaction.maxCapacity))
						.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes))));
		jpaQuery.orderBy(QBinTransaction.binTransaction.insertTime.asc());
		List<BinTransaction> binList = jpaQuery.list(QBinTransaction.binTransaction);

		return binList;
	}

	@Override
	public List<MachineAllocation> getAggregatedBundleToBeReturnedToVault(BigInteger icmcId, CashSource cashSource,
			Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.status.eq(OtherStatus.REQUESTED))
				.and(QMachineAllocation.machineAllocation.pendingBundle.gt(0)
						.and(QMachineAllocation.machineAllocation.cashSource.eq(cashSource)))
		// .and(QMachineAllocation.machineAllocation.insertTime.between(sDate,
		// eDate))
		);
		jpaQuery.groupBy(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.cashSource);
		jpaQuery.orderBy(QMachineAllocation.machineAllocation.denomination.desc());
		List<Tuple> tupleList = jpaQuery.list(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.cashSource,
				QMachineAllocation.machineAllocation.pendingBundle.sum());

		List<MachineAllocation> machineAllocationList = UtilityMapper
				.mapTuppleToAggregatedBundleToBeReturnedToVault(tupleList);
		return machineAllocationList;
	}

	private JPAQuery getFromQueryForUserName() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QUser.user);
		return jpaQuery;
	}

	@Override
	public String getUserName(String userId) {
		JPAQuery jpaQuery = getFromQueryForUserName();
		jpaQuery.where(QUser.user.id.eq(userId));
		return jpaQuery.singleResult(QUser.user.name);
	}

	@Override
	public Process getRepritRecord(Long id) {
		JPAQuery jpaQuery = getFromQueryForProcess();
		jpaQuery.where(QProcess.process.id.eq(id));
		Process process = jpaQuery.singleResult(QProcess.process);
		return process;
	}

	private JPAQuery getFromQueryForMitulatedFullValue() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMutilated.mutilated);
		return jpaQuery;
	}

	@Override
	public boolean createMutilated(List<Mutilated> mutilatedList) {
		for (Mutilated mutilated : mutilatedList) {
			em.persist(mutilated);
		}
		return true;
	}

	@Override
	public List<Tuple> indentSummaryForMutilated(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.MUTILATED)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.binType, QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle, QBinTransaction.binTransaction.binNumber,
				QBinTransaction.binTransaction.binCategoryType, QBinTransaction.binTransaction.pendingBundleRequest);
		jpaQuery.orderBy(QBinTransaction.binTransaction.denomination.desc());
		List<Tuple> tupleList = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle, QBinTransaction.binTransaction.binNumber,
				QBinTransaction.binTransaction.binCategoryType, QBinTransaction.binTransaction.pendingBundleRequest);
		return tupleList;
	}

	@Override
	public List<BinTransaction> getBinNumListForMutilatedIndent(int denomination, BigDecimal bundle, BigInteger icmcId,
			BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForIndentRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.MUTILATED))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binCategoryType)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public boolean insertMitulatedIndentRequest(List<MutilatedIndent> eligibleIndentRequestList) {
		for (MutilatedIndent mutilatedIndent : eligibleIndentRequestList) {
			em.persist(mutilatedIndent);
		}
		return true;
	}

	@Override
	public List<MutilatedIndent> getMutilatedIndent(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMutilatedIndent();
		jpaQuery.where(QMutilatedIndent.mutilatedIndent.icmcId.eq(icmcId)
				.and(QMutilatedIndent.mutilatedIndent.status.eq(OtherStatus.REQUESTED)));
		List<MutilatedIndent> mutilatedIndentList = jpaQuery.list(QMutilatedIndent.mutilatedIndent);
		return mutilatedIndentList;
	}

	@Override
	public boolean updateMutilatedIndentRequest(MutilatedIndent indent) {
		QMutilatedIndent qMutilatedIndent = QMutilatedIndent.mutilatedIndent;
		Long count = new JPAUpdateClause(em, qMutilatedIndent)
				.where(QMutilatedIndent.mutilatedIndent.icmcId.eq(indent.getIcmcId())
						.and(QMutilatedIndent.mutilatedIndent.id.eq(indent.getId()))
						.and(QMutilatedIndent.mutilatedIndent.status.eq(OtherStatus.REQUESTED)))
				.set(qMutilatedIndent.status, OtherStatus.ACCEPTED).execute();
		return count > 0 ? true : false;
	}

	@Override
	public String getICMCName(long icmcId) {
		JPAQuery jpaQuery = getFromQueryForICMC();
		jpaQuery.where(QICMC.iCMC.id.eq(icmcId));
		String icmcName = jpaQuery.singleResult(QICMC.iCMC.name);
		return icmcName;
	}

	@Override
	public List<Discrepancy> getDiscrepancyReports(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancy();
		jpaQuery.where(QDiscrepancy.discrepancy.icmcId.eq(icmcId)
				.and(QDiscrepancy.discrepancy.normalOrSuspense.eq(normalOrSuspense))
				.and(QDiscrepancy.discrepancy.insertTime.between(sDate, eDate)));
		return jpaQuery.list(QDiscrepancy.discrepancy);
	}

	@Override
	public Discrepancy getDescripancyDataForEdit(Long discrepancyId, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancy();
		jpaQuery.where(QDiscrepancy.discrepancy.icmcId.eq(icmcId).and(QDiscrepancy.discrepancy.id.eq(discrepancyId)));
		Discrepancy discrepancy = jpaQuery.singleResult(QDiscrepancy.discrepancy);
		return discrepancy;
	}

	@Override
	public DiscrepancyAllocation getDescripancyAllocationDataForEdit(Long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.id.eq(id)));
		DiscrepancyAllocation discrepaancyAllocation = jpaQuery
				.singleResult(QDiscrepancyAllocation.discrepancyAllocation);
		return discrepaancyAllocation;
	}

	@Override
	public boolean updateDiscrepancy(DiscrepancyAllocation discrepancyAllocation) {
		em.merge(discrepancyAllocation);
		return true;
	}

	@Override
	public DiscrepancyAllocation getDescripancyAllocationById(Long id) {
		return em.find(DiscrepancyAllocation.class, id);
	}

	@Override
	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.insertTime.between(sDate, eDate)));
		// .and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.eq("MUTILATED")));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.mutilType);
		List<Tuple> mutilatedList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.mutilType,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum());
		return mutilatedList;
	}

	@Override
	public List<Tuple> getMutilatedNotesSummary(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.insertTime.between(sDate, eDate))
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq(normalOrSuspense)));
		// .and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.eq("MUTILATED")));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.mutilType);
		List<Tuple> mutilatedList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.mutilType,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum());
		return mutilatedList;
	}

	@Override
	public boolean addMachineMaintenance(MachineMaintenance machineMaintainance) {
		em.persist(machineMaintainance);
		return true;
	}

	public JPAQuery getFromMachineMaintenance() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineMaintenance.machineMaintenance);
		return jpaQuery;
	}

	@Override
	public List<MachineMaintenance> viewMachineMaintenance(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromMachineMaintenance();
		jpaQuery.where(QMachineMaintenance.machineMaintenance.icmcId.eq(icmcId)
				.and(QMachineMaintenance.machineMaintenance.status.eq(Status.ENABLED)));
		List<MachineMaintenance> machineMaintenanceList = jpaQuery.list(QMachineMaintenance.machineMaintenance);
		return machineMaintenanceList;
	}

	@Override
	public MachineMaintenance getMachineMaintenanceById(long id) {
		return em.find(MachineMaintenance.class, id);
	}

	@Override
	public boolean updateMachineMaintenanceStatus(MachineMaintenance machineMaintenance) {
		em.merge(machineMaintenance);
		return true;
	}

	@Override
	public boolean createProcessForAuditor(List<AuditorProcess> processList) {
		for (AuditorProcess process : processList) {
			em.persist(process);
		}
		return true;
	}

	@Override
	public List<Tuple> getPendingBundleForAuditor(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForPendingBundleFromAuditorIndent();
		jpaQuery.where(
				QAuditorIndent.auditorIndent.icmcId.eq(icmcId).and(QAuditorIndent.auditorIndent.pendingBundleRequest
						.gt(0).and(QAuditorIndent.auditorIndent.status.eq(OtherStatus.ACCEPTED))));
		jpaQuery.groupBy(QAuditorIndent.auditorIndent.denomination);
		List<Tuple> bundleList = jpaQuery.list(QAuditorIndent.auditorIndent.denomination,
				QAuditorIndent.auditorIndent.pendingBundleRequest.sum());
		return bundleList;
	}

	@Override
	public List<AuditorIndent> getPendingBundleFromAuditorIndent(BigInteger icmcId, Integer denomination) {
		JPAQuery jpaQuery = getFromQueryForPendingBundleFromAuditorIndent();
		jpaQuery.where(QAuditorIndent.auditorIndent.icmcId.eq(icmcId).and(QAuditorIndent.auditorIndent.denomination
				.eq(denomination).and(QAuditorIndent.auditorIndent.status.eq(OtherStatus.ACCEPTED))));
		List<AuditorIndent> auditorIndentList = jpaQuery.list(QAuditorIndent.auditorIndent);
		return auditorIndentList;
	}

	@Override
	public boolean updatePendingBundleInAuditorIndent(AuditorIndent auditorIndent) {
		em.merge(auditorIndent);
		return true;
	}

	@Override
	public BinTransaction getDataFromBinTransaction(String bin, BigInteger icmcId, Integer denomination,
			CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForBinFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.binNumber.eq(bin)
				.and(QBinTransaction.binTransaction.icmcId.eq(icmcId)
						.and(QBinTransaction.binTransaction.binType.eq(currencyType))
						.and(QBinTransaction.binTransaction.denomination.eq(denomination)))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public boolean updateProcessingRoomStatus(Process process) {
		QProcess qProcess = QProcess.process;
		Long count = new JPAUpdateClause(em, qProcess)
				.where(QProcess.process.icmcId.eq(process.getIcmcId()).and(QProcess.process.id.eq(process.getId())))
				.set(QProcess.process.status, 0).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean updatePendingForCancel(long id, BigDecimal pendingBundle) {
		QMachineAllocation qMachineAllocation = QMachineAllocation.machineAllocation;
		long count = new JPAUpdateClause(em, qMachineAllocation).where(QMachineAllocation.machineAllocation.id.eq(id))
				.set(QMachineAllocation.machineAllocation.pendingBundle,
						QMachineAllocation.machineAllocation.pendingBundle.add(pendingBundle))
				.set(QMachineAllocation.machineAllocation.status, OtherStatus.REQUESTED).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean insertSuspenseOpeningBalance(SuspenseOpeningBalance suspenseOpeningBalance) {
		em.persist(suspenseOpeningBalance);
		return true;
	}

	private JPAQuery getFromQueryForSuspenseOpeningBalance() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSuspenseOpeningBalance.suspenseOpeningBalance);
		return jpaQuery;
	}

	@Override
	public List<SuspenseOpeningBalance> getSuspenseOpeningBalance(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSuspenseOpeningBalance();
		jpaQuery.where(QSuspenseOpeningBalance.suspenseOpeningBalance.icmcId.eq(icmcId));
		List<SuspenseOpeningBalance> suspenseOpeningBalance = jpaQuery
				.list(QSuspenseOpeningBalance.suspenseOpeningBalance);
		return suspenseOpeningBalance;
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegister(
			BigInteger icmcId/*
								 * , Calendar sDate, Calendar eDate
								 */) {
		JPAQuery jpaQuery = getFromQueryForSuspenseOpeningBalance();
		jpaQuery.where(QSuspenseOpeningBalance.suspenseOpeningBalance.icmcId.eq(icmcId)
				.and(QSuspenseOpeningBalance.suspenseOpeningBalance.currentVersion.eq("TRUE"))
		/*
		 * .and(QSuspenseOpeningBalance.suspenseOpeningBalance.insertTime.
		 * between(sDate, eDate))
		 */);
		List<SuspenseOpeningBalance> suspenseOpeningBalanceList = jpaQuery
				.list(QSuspenseOpeningBalance.suspenseOpeningBalance);
		return suspenseOpeningBalanceList;
	}

	@Override
	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq("SUSPENSE")
						.and(QDiscrepancyAllocation.discrepancyAllocation.insertTime.between(sDate, eDate)
								.and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.ne("EXCESS")))));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination);
		jpaQuery.orderBy(QDiscrepancyAllocation.discrepancyAllocation.denomination.desc());

		List<Tuple> notesCountList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum());
		return notesCountList;

	}

	@Override
	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq("SUSPENSE")
						.and(QDiscrepancyAllocation.discrepancyAllocation.insertTime.between(sDate, eDate)
								.and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.eq("EXCESS")))));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination);
		jpaQuery.orderBy(QDiscrepancyAllocation.discrepancyAllocation.denomination.desc());

		List<Tuple> notesCountList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum());
		return notesCountList;

	}

	@Override
	public boolean branchHistory(List<History> historyList) {
		for (History history : historyList) {
			em.persist(history);
		}
		return true;
	}

	@Override
	public boolean updateCurrentVersionStatus(SuspenseOpeningBalance suspenseOpeningBalance) {
		QSuspenseOpeningBalance qSuspeneOpeningBalance = QSuspenseOpeningBalance.suspenseOpeningBalance;
		Long count = new JPAUpdateClause(em, qSuspeneOpeningBalance)
				.where(QSuspenseOpeningBalance.suspenseOpeningBalance.icmcId.eq(suspenseOpeningBalance.getIcmcId())
						.and(QSuspenseOpeningBalance.suspenseOpeningBalance.currentVersion.eq("TRUE")))
				.set(qSuspeneOpeningBalance.currentVersion, "FALSE").execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDate(BigInteger icmcId, Calendar sDate,
			Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSuspenseOpeningBalance();
		jpaQuery.where(QSuspenseOpeningBalance.suspenseOpeningBalance.icmcId.eq(icmcId)
				.and(QSuspenseOpeningBalance.suspenseOpeningBalance.insertTime.between(sDate, eDate)));
		List<SuspenseOpeningBalance> suspenseOpeningBalanceList = jpaQuery
				.list(QSuspenseOpeningBalance.suspenseOpeningBalance);
		return suspenseOpeningBalanceList;
	}

	@Override
	public List<Tuple> getTotalNotesForWithdrawal(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq("SUSPENSE")
						.and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.ne("EXCESS"))));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination);
		jpaQuery.orderBy(QDiscrepancyAllocation.discrepancyAllocation.denomination.desc());

		List<Tuple> notesCountList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum());
		return notesCountList;

	}

	@Override
	public List<Tuple> geTotalNotesForDeposit(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDiscrepancyAllocation();
		jpaQuery.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
				.and(QDiscrepancyAllocation.discrepancyAllocation.normalOrSuspense.eq("SUSPENSE")
						.and(QDiscrepancyAllocation.discrepancyAllocation.discrepancyType.eq("EXCESS"))));
		jpaQuery.groupBy(QDiscrepancyAllocation.discrepancyAllocation.denomination);
		jpaQuery.orderBy(QDiscrepancyAllocation.discrepancyAllocation.denomination.desc());

		List<Tuple> notesCountList = jpaQuery.list(QDiscrepancyAllocation.discrepancyAllocation.denomination,
				QDiscrepancyAllocation.discrepancyAllocation.numberOfNotes.sum());
		return notesCountList;
	}

	@Override
	public boolean saveDataInBinRegister(BinRegister binRegister) {
		em.persist(binRegister);
		return true;
	}

	@Override
	public boolean deleteDiscrepancy(long id, BigInteger icmcId) {
		QDiscrepancyAllocation qDiscrepancyAllocation = QDiscrepancyAllocation.discrepancyAllocation;
		long count = new JPAUpdateClause(em, qDiscrepancyAllocation)
				.where(QDiscrepancyAllocation.discrepancyAllocation.icmcId.eq(icmcId)
						.and(QDiscrepancyAllocation.discrepancyAllocation.id.eq(id)))
				.set(QDiscrepancyAllocation.discrepancyAllocation.status, OtherStatus.CANCELLED).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean deleteDiscrepancywithooutallocation(long id, BigInteger icmcId) {
		QDiscrepancy qDiscrepancy = QDiscrepancy.discrepancy;
		long count = new JPAUpdateClause(em, qDiscrepancy)
				.where(QDiscrepancy.discrepancy.icmcId.eq(icmcId).and(QDiscrepancy.discrepancy.id.eq(id)))
				.set(QDiscrepancy.discrepancy.status, 4).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean insertFullValueMutilated(Mutilated mutilated) {
		em.persist(mutilated);
		return true;
	}

	@Override
	public boolean UploadChestSlip(List<ChestMaster> list, ChestMaster chestSlip) {
		for (ChestMaster chestSlipSet : list) {
			chestSlipSet.setInsertBy(chestSlip.getInsertBy());
			chestSlipSet.setUpdateBy(chestSlip.getUpdateBy());
			chestSlipSet.setInsertTime(chestSlip.getInsertTime());
			chestSlipSet.setUpdateTime(chestSlip.getUpdateTime());
			em.persist(chestSlipSet);
		}
		return true;
	}

	@Override
	public List<MachineAllocation> getBundleByCashSource(BigInteger icmcId, String isMachineOrManual,
			Integer denomination, CashSource cashSource, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForPendingBundleFromMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.ismanual.eq(isMachineOrManual))
				.and(QMachineAllocation.machineAllocation.status.eq(OtherStatus.REQUESTED))
				.and(QMachineAllocation.machineAllocation.denomination.eq(denomination))
				// .and(QMachineAllocation.machineAllocation.insertTime.between(sDate,
				// eDate))
				.and(QMachineAllocation.machineAllocation.cashSource.eq(cashSource)));
		List<MachineAllocation> pendingBundleList = jpaQuery.list(QMachineAllocation.machineAllocation);
		return pendingBundleList;
	}

	@Override
	public boolean insertCosutodianName(CustodianKeySet custodianKeySet) {
		em.persist(custodianKeySet);
		return true;
	}

	@Override
	public List<CustodianKeySet> getAssignVaultCustodian(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCustodiannameForKeySet();
		jpaQuery.where(QCustodianKeySet.custodianKeySet.icmcId.eq(icmcId));
		jpaQuery.groupBy(QCustodianKeySet.custodianKeySet.custodian);
		List<CustodianKeySet> assignVaultCustodianList = jpaQuery.list(QCustodianKeySet.custodianKeySet);
		return assignVaultCustodianList;
	}

	private JPAQuery getFromQueryForUser() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QUser.user);
		return jpaQuery;
	}

	@Override
	public User isValidUser(String username, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForUser();
		jpaQuery.where(QUser.user.id.equalsIgnoreCase(username)
				.and(QUser.user.status.eq(Status.ENABLED).and(QUser.user.icmcId.eq(icmcId))));
		return jpaQuery.singleResult(QUser.user);
	}

	@Override
	public Boolean InsertByDAteSuspeseOpeningBalance(long id, BigInteger icmcId,
			BigDecimal openingBalanceOfDenomination_10, BigDecimal openingBalanceOfDenomination_20,
			BigDecimal openingBalanceOfDenomination_50, BigDecimal openingBalanceOfDenomination_100,
			BigDecimal openingBalanceOfDenomination_200, BigDecimal openingBalanceOfDenomination_500,
			BigDecimal openingBalanceOfDenomination_2000) {
		QSuspenseOpeningBalance suspenseOpeningBal = QSuspenseOpeningBalance.suspenseOpeningBalance;
		long count = new JPAUpdateClause(em, suspenseOpeningBal)
				.where(QSuspenseOpeningBalance.suspenseOpeningBalance.id.eq(id)
						.and(QSuspenseOpeningBalance.suspenseOpeningBalance.icmcId.eq(icmcId)))
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.denomination10, openingBalanceOfDenomination_10)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.denomination20, openingBalanceOfDenomination_20)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.denomination50, openingBalanceOfDenomination_50)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.denomination100, openingBalanceOfDenomination_100)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.denomination200, openingBalanceOfDenomination_200)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.denomination500, openingBalanceOfDenomination_500)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.denomination2000, openingBalanceOfDenomination_2000)
				.execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean updateSuspenseBalanceFromLink(long id, BigDecimal replenishment_2000, BigDecimal replenishment_500,
			BigDecimal replenishment_200, BigDecimal replenishment_100, BigDecimal replenishment_50,
			BigDecimal replenishment_20, BigDecimal replenishment_10, BigDecimal depletion_2000,
			BigDecimal depletion_500, BigDecimal depletion_200, BigDecimal depletion_100, BigDecimal depletion_50,
			BigDecimal depletion_20, BigDecimal depletion_10, String srNumber) {
		QSuspenseOpeningBalance qSuspenseBalanceRepl = QSuspenseOpeningBalance.suspenseOpeningBalance;
		long count = new JPAUpdateClause(em, qSuspenseBalanceRepl)
				.where(QSuspenseOpeningBalance.suspenseOpeningBalance.id.eq(id))
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.replenishment_2000, replenishment_2000)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.replenishment_500, replenishment_500)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.replenishment_200, replenishment_200)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.replenishment_100, replenishment_100)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.replenishment_50, replenishment_50)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.replenishment_20, replenishment_20)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.replenishment_10, replenishment_10)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.depletion_2000, depletion_2000)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.depletion_500, depletion_500)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.depletion_200, depletion_200)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.depletion_100, depletion_100)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.depletion_50, depletion_50)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.depletion_20, depletion_20)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.depletion_10, depletion_10)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.srNumber, srNumber).execute();
		return count > 0 ? true : false;
	}

	@Override
	public boolean updateSuspenseBalance(long id, BigDecimal deposit_2000, BigDecimal deposit_500,
			BigDecimal deposit_200, BigDecimal deposit_100, BigDecimal deposit_50, BigDecimal deposit_20,
			BigDecimal deposit_10, BigDecimal withdrawal_2000, BigDecimal withdrawal_500, BigDecimal withdrawal_200,
			BigDecimal withdrawal_100, BigDecimal withdrawal_50, BigDecimal withdrawal_20, BigDecimal withdrawal_10) {
		QSuspenseOpeningBalance qSuspenseBalance = QSuspenseOpeningBalance.suspenseOpeningBalance;
		long count = new JPAUpdateClause(em, qSuspenseBalance)
				.where(QSuspenseOpeningBalance.suspenseOpeningBalance.id.eq(id))
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.withdrawal_2000, withdrawal_2000)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.withdrawal_500, withdrawal_500)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.withdrawal_200, withdrawal_200)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.withdrawal_100, withdrawal_100)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.withdrawal_50, withdrawal_50)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.withdrawal_20, withdrawal_20)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.withdrawal_10, withdrawal_10)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.deposit_2000, deposit_2000)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.deposit_500, deposit_500)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.deposit_200, deposit_200)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.deposit_100, deposit_100)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.deposit_50, deposit_50)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.deposit_20, deposit_20)
				.set(QSuspenseOpeningBalance.suspenseOpeningBalance.deposit_10, deposit_10).execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<SuspenseOpeningBalance> openingBalanceForSuspenseRegisterPreviousDateByDesc(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSuspenseOpeningBalance();
		jpaQuery.where(QSuspenseOpeningBalance.suspenseOpeningBalance.icmcId.eq(icmcId));
		jpaQuery.orderBy(QSuspenseOpeningBalance.suspenseOpeningBalance.insertTime.desc());
		jpaQuery.limit(1);

		List<SuspenseOpeningBalance> suspenseOpeningBalanceListDesc = jpaQuery
				.list(QSuspenseOpeningBalance.suspenseOpeningBalance);
		return suspenseOpeningBalanceListDesc;
	}

	@Override
	public List<Mutilated> getMitulatedFullValue(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForMitulatedFullValue();
		jpaQuery.where(
				QMutilated.mutilated.icmcId.eq(icmcId).and(QMutilated.mutilated.otherStatus.eq(OtherStatus.REQUESTED)));
		List<Mutilated> mutilatedList = jpaQuery.list(QMutilated.mutilated);
		return mutilatedList;
	}

	@Override
	public List<Mutilated> getMutilatedValueDetails(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForMitulatedFullValue();
		jpaQuery.where(QMutilated.mutilated.icmcId.eq(icmcId).and(QMutilated.mutilated.insertTime.between(sDate, eDate))
				.and(QMutilated.mutilated.currencyType.eq(CurrencyType.MUTILATED))
				.and(QMutilated.mutilated.otherStatus.eq(OtherStatus.REQUESTED)));
		List<Mutilated> mutilatedList = jpaQuery.list(QMutilated.mutilated);
		return mutilatedList;
	}

	public boolean processMutilatedRequest(Long id) {
		QMutilated qmutilated = QMutilated.mutilated;
		Long count = new JPAUpdateClause(em, qmutilated).where(QMutilated.mutilated.id.eq(id))
				.set(QMutilated.mutilated.otherStatus, OtherStatus.ACCEPTED).execute();
		return count > 0 ? true : false;

	}

	@Override
	public List<DSB> getDSB(Indent indent, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDSB.dSB);
		jpaQuery.where(QDSB.dSB.icmcId.eq(indent.getIcmcId()).and(QDSB.dSB.bin.eq(indent.getBin()))
				.and(QDSB.dSB.insertTime.between(sDate, eDate)).and(QDSB.dSB.isIndent.eq(false))
				.and(QDSB.dSB.status.eq(OtherStatus.RECEIVED)).and(QDSB.dSB.denomination.eq(indent.getDenomination())));

		return jpaQuery.list(QDSB.dSB);
	}

	@Override
	public List<BankReceipt> getBankReceipt(Indent indent, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBankReceipt.bankReceipt);
		jpaQuery.where(QBankReceipt.bankReceipt.icmcId.eq(indent.getIcmcId())
				.and(QBankReceipt.bankReceipt.binNumber.eq(indent.getBin()))
				.and(QBankReceipt.bankReceipt.insertTime.between(sDate, eDate))
				.and(QBankReceipt.bankReceipt.isIndent.eq(false)).and(QBankReceipt.bankReceipt.status.eq(0))
				.and(QBankReceipt.bankReceipt.denomination.eq(indent.getDenomination())));

		return jpaQuery.list(QBankReceipt.bankReceipt);
	}

	@Override
	public List<DiversionIRV> getDiversionIRV(Indent indent, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionIRV.diversionIRV);
		jpaQuery.where(QDiversionIRV.diversionIRV.icmcId.eq(indent.getIcmcId())
				.and(QDiversionIRV.diversionIRV.binNumber.eq(indent.getBin()))
				.and(QDiversionIRV.diversionIRV.insertTime.between(sDate, eDate))
				.and(QDiversionIRV.diversionIRV.isIndent.eq(false))
				.and(QDiversionIRV.diversionIRV.status.eq(OtherStatus.RECEIVED))
				.and(QDiversionIRV.diversionIRV.denomination.eq(indent.getDenomination())));

		return jpaQuery.list(QDiversionIRV.diversionIRV);
	}

	@Override
	public boolean updateDSB(DSB dsb) {
		em.merge(dsb);
		return true;
	}

	@Override
	public boolean updateBankReceipt(BankReceipt bankReceipt) {
		em.merge(bankReceipt);
		return true;
	}

	@Override
	public Boolean updateDiversionIRV(DiversionIRV diversionIRV) {
		em.merge(diversionIRV);
		return true;
	}
}
