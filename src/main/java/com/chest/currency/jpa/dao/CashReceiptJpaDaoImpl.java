/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

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
import com.chest.currency.entity.model.QBankReceipt;
import com.chest.currency.entity.model.QBinCapacityDenomination;
import com.chest.currency.entity.model.QBinMaster;
import com.chest.currency.entity.model.QBinTransaction;
import com.chest.currency.entity.model.QBoxMaster;
import com.chest.currency.entity.model.QBranch;
import com.chest.currency.entity.model.QBranchReceipt;
import com.chest.currency.entity.model.QCoinsSequence;
import com.chest.currency.entity.model.QDSB;
import com.chest.currency.entity.model.QDSBAccountDetail;
import com.chest.currency.entity.model.QDiversionIRV;
import com.chest.currency.entity.model.QFreshFromRBI;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.entity.model.QIndent;
import com.chest.currency.entity.model.QMachineAllocation;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.VaultSize;
import com.chest.currency.enums.YesNo;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author root
 *
 */
@Repository
public class CashReceiptJpaDaoImpl implements CashReceiptJpaDao {

	private static final Logger LOG = LoggerFactory.getLogger(CashReceiptJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<BinCapacityDenomination> getMaxBundleCapacity(int denomination, CurrencyType currencyType) {
		LOG.info("FETCH CAPACITY BY DENOMINATION");
		JPAQuery jpaQuery = getFromQueryForBinCapacityDenomination();
		jpaQuery.where(QBinCapacityDenomination.binCapacityDenomination.denomination.eq(denomination)
				.and(QBinCapacityDenomination.binCapacityDenomination.currencyType.eq(currencyType))
				.and(QBinCapacityDenomination.binCapacityDenomination.vaultSize.in(VaultSize.values())));

		return jpaQuery.list(QBinCapacityDenomination.binCapacityDenomination);
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

	@Override
	public boolean updateOtherBank(BankReceipt otherBankReceit) {
		em.merge(otherBankReceit);
		return true;
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

	private JPAQuery getFromQueryForBinCapacityDenomination() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinCapacityDenomination.binCapacityDenomination);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForBinMaster() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinMaster.binMaster);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForCashReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinMaster.binMaster);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForCashReceiptFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForBranchReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTx.getIcmcId())
				.and(QBinTransaction.binTransaction.cashSource.eq(binTx.getCashSource()))
				.and(QBinTransaction.binTransaction.denomination.eq(binTx.getDenomination()))
				.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes))
				.and(QBinTransaction.binTransaction.binType.eq(binTx.getBinType()))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binTx.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.receiveBundle.lt(QBinTransaction.binTransaction.maxCapacity)));
		List<BinTransaction> binList = jpaQuery.list(QBinTransaction.binTransaction);
		return binList;
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenomForProcessed(BinTransaction binTx) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTx.getIcmcId())
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binTx.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.binType.eq(binTx.getBinType()))
				.and(QBinTransaction.binTransaction.cashSource.eq(binTx.getCashSource()))
				.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes))
				.and(QBinTransaction.binTransaction.denomination.eq(binTx.getDenomination())
						.and(QBinTransaction.binTransaction.receiveBundle
								.lt(QBinTransaction.binTransaction.maxCapacity))
						.and(QBinTransaction.binTransaction.binType.eq(binTx.getBinType()))));

		List<BinTransaction> binList = jpaQuery.list(QBinTransaction.binTransaction);

		return binList;
	}

	@Override
	public boolean updateBinMaster(BinMaster binMaster) {

		JPAQuery jpaQuery = getFromQueryForBinMaster();
		jpaQuery.where(QBinMaster.binMaster.binNumber.eq(binMaster.getBinNumber()).and(
				QBinMaster.binMaster.icmcId.eq(binMaster.getIcmcId()).and(QBinMaster.binMaster.isAllocated.eq(0))));
		BinMaster dbBinMaster = jpaQuery.singleResult(QBinMaster.binMaster);

		if (dbBinMaster != null) {
			dbBinMaster.setIsAllocated(1);
			dbBinMaster.setUpdateTime(Calendar.getInstance());
			em.merge(dbBinMaster);
		} else {
			return false;
		}

		return true;
	}

	@Override
	public boolean insertInBinTxn(BinTransaction binTransaction) {
		LOG.info("insertInBinTxn Dao binTransaction" + binTransaction);
		em.persist(binTransaction);
		return true;
	}

	@Override
	public boolean updateInBinTxn(BinTransaction binTransaction) {
		em.merge(binTransaction);
		return true;
	}

	@Override
	public boolean createBranchReceipt(List<BranchReceipt> branchReceiptList) {
		for (BranchReceipt branchReceipt : branchReceiptList) {
			LOG.info("createBranchReceipt jpaDaoImpl" + branchReceipt);
			try {
				em.persist(branchReceipt);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}

	@Override
	public List<BranchReceipt> getBrachReceiptRecord(User user, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(user.getIcmcId())
				.and(QBranchReceipt.branchReceipt.solId.isNotNull())
				.and(QBranchReceipt.branchReceipt.status.ne(OtherStatus.CANCELLED))
				.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		List<BranchReceipt> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt);
		return branchReceiptList;
	}

	@Override
	public boolean createFreshFromRBI(List<FreshFromRBI> freshFromRBI) {
		for (FreshFromRBI fresh : freshFromRBI) {
			em.persist(fresh);
		}
		return true;
	}

	private JPAQuery getFromQueryForFreshFromRBI() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QFreshFromRBI.freshFromRBI);
		return jpaQuery;
	}

	@Override
	public boolean createDiversionIRV(List<DiversionIRV> diversionIRV) {
		for (DiversionIRV diversion : diversionIRV) {
			em.persist(diversion);
		}
		return true;
	}

	private JPAQuery getFromQueryForDiversionIRV() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionIRV.diversionIRV);
		return jpaQuery;
	}

	@Override
	public List<DiversionIRV> getDiversionIRVRecord(User user, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiversionIRV();
		jpaQuery.where(QDiversionIRV.diversionIRV.icmcId.eq(user.getIcmcId())
				.and(QDiversionIRV.diversionIRV.status.ne(OtherStatus.CANCELLED))
				.and(QDiversionIRV.diversionIRV.bankName.ne("NULL"))
				.and(QDiversionIRV.diversionIRV.insertTime.between(sDate, eDate)));
		List<DiversionIRV> diversionList = jpaQuery.list(QDiversionIRV.diversionIRV);
		return diversionList;
	}

	@Override
	public Long createDSB(List<DSB> dsb) {
		for (DSB dSB : dsb) {
			em.persist(dSB);
			return dSB.getId();
		}
		// return dSB.get;
		return 0l;
	}

	@Override
	public boolean saveDSB(List<DSB> dsb) {
		for (DSB dSB : dsb) {
			em.persist(dSB);
		}
		return true;
	}

	private JPAQuery getFromQueryForDSB() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDSB.dSB);
		return jpaQuery;
	}

	@Override
	public List<DSB> getDSBRecord(User user, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(QDSB.dSB.icmcId.eq(user.getIcmcId()).and(QDSB.dSB.accountNumber.isNotNull())
				.and(QDSB.dSB.status.ne(OtherStatus.CANCELLED)).and(QDSB.dSB.insertTime.between(sDate, eDate)));
		List<DSB> dsbList = jpaQuery.list(QDSB.dSB);
		return dsbList;
	}

	@Override
	public boolean insertInIndent(Indent indent) {
		em.persist(indent);
		return true;
	}

	private JPAQuery getFromQueryForBankReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBankReceipt.bankReceipt);
		return jpaQuery;
	}

	@Override
	public List<BankReceipt> getBankReceiptRecord(User user, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBankReceipt();
		jpaQuery.where(QBankReceipt.bankReceipt.icmcId.eq(user.getIcmcId())
				.and(QBankReceipt.bankReceipt.bankName.isNotNull()).and(QBankReceipt.bankReceipt.status.ne(4))
				.and(QBankReceipt.bankReceipt.insertTime.between(sDate, eDate)));
		List<BankReceipt> icmcReceiptList = jpaQuery.list(QBankReceipt.bankReceipt);
		return icmcReceiptList;
	}

	@Override
	public boolean createICMCReceipt(List<BankReceipt> icmcReceipt) {
		for (BankReceipt icmc : icmcReceipt) {
			em.persist(icmc);
		}
		return true;
	}

	@Override
	public List<ICMC> getICMCName() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		List<ICMC> icmcList = jpaQuery.list(QICMC.iCMC);
		return icmcList;
	}

	private JPAQuery getFromQueryForBranchBySolId() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranch.branch1);
		return jpaQuery;
	}

	@Override
	public String getBranchBySolId(String solId) {
		JPAQuery jpaQuery = getFromQueryForBranchBySolId();
		jpaQuery.where(QBranch.branch1.solId.eq(solId));
		String branch = jpaQuery.singleResult(QBranch.branch1.branch);
		return branch;
	}

	@Override
	public ICMC getICMCBySolId(String solId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		jpaQuery.where(QICMC.iCMC.linkBranchSolId.eq(solId));
		return jpaQuery.singleResult(QICMC.iCMC);
	}

	private JPAQuery getFromQueryForBranchFromBranchReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		return jpaQuery;
	}

	@Override
	public List<BranchReceipt> getBrachFromBranchReceipt(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBranchFromBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)).distinct();
		List<Tuple> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt.solId,
				QBranchReceipt.branchReceipt.branch);
		return getBranchReceipt(branchReceiptList);
	}

	private List<BranchReceipt> getBranchReceipt(List<Tuple> branchReceiptList) {
		List<BranchReceipt> branchReceipts = new ArrayList<>();
		for (Tuple t : branchReceiptList) {
			BranchReceipt br = new BranchReceipt();
			br.setSolId(t.get(0, String.class));
			br.setBranch(t.get(1, String.class));
			branchReceipts.add(br);
		}
		return branchReceipts;
	}

	@Override
	public List<Tuple> getVoucherRecord(String solId) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.solId.eq(solId));
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.denomination);

		List<Tuple> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt.denomination,
				QBranchReceipt.branchReceipt.bundle.sum());
		return branchReceiptList;
	}

	private JPAQuery getFromQueryForIRVReportFromBranchReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getIRVReportRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForIRVReportFromBranchReceipt();
		jpaQuery.where(
				QBranchReceipt.branchReceipt.icmcId.eq(icmcId).and(QBranchReceipt.branchReceipt.solId.isNotNull())
						.and(QBranchReceipt.branchReceipt.status.ne(OtherStatus.CANCELLED))
						.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.solId, QBranchReceipt.branchReceipt.branch,
				QBranchReceipt.branchReceipt.srNumber);
		List<Tuple> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt.solId,
				QBranchReceipt.branchReceipt.branch, QBranchReceipt.branchReceipt.srNumber,
				QBranchReceipt.branchReceipt.total.sum());
		return branchReceiptList;
	}

	@Override
	public List<Tuple> getIRVReportRecordsForDSb(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(QDSB.dSB.icmcId.eq(icmcId).and(QDSB.dSB.accountNumber.isNotNull())
				.and(QDSB.dSB.status.eq(OtherStatus.RECEIVED)).and(QDSB.dSB.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QDSB.dSB.receiptSequence, QDSB.dSB.receiptDate, QDSB.dSB.name, QDSB.dSB.accountNumber);
		List<Tuple> dsbList = jpaQuery.list(QDSB.dSB.receiptSequence, QDSB.dSB.receiptDate, QDSB.dSB.name,
				QDSB.dSB.accountNumber, QDSB.dSB.total.sum());
		return dsbList;
	}

	@Override
	public List<Tuple> getIRVReportRecordForOtherBanks(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForBankReceipt();
		jpaQuery.where(QBankReceipt.bankReceipt.icmcId.eq(icmcId).and(QBankReceipt.bankReceipt.bankName.isNotNull())
				.and(QBankReceipt.bankReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBankReceipt.bankReceipt.bankName, QBankReceipt.bankReceipt.solId,
				QBankReceipt.bankReceipt.branch, QBankReceipt.bankReceipt.rtgsUTRNo);
		List<Tuple> banksLIst = jpaQuery.list(QBankReceipt.bankReceipt.bankName, QBankReceipt.bankReceipt.solId,
				QBankReceipt.bankReceipt.branch, QBankReceipt.bankReceipt.rtgsUTRNo,
				QBankReceipt.bankReceipt.total.sum());
		return banksLIst;
	}

	private JPAQuery getFromQueryForDSBAccountDetail() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDSBAccountDetail.dSBAccountDetail);
		return jpaQuery;
	}

	@Override
	public List<DSBAccountDetail> getDSBAccountDetail(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDSBAccountDetail();
		jpaQuery.where(QDSBAccountDetail.dSBAccountDetail.icmcId.eq(icmcId));
		List<DSBAccountDetail> accountDetailList = jpaQuery.list(QDSBAccountDetail.dSBAccountDetail);
		return accountDetailList;
	}

	@Override
	public List<String> getAccountNumberForDSB(String vendorName, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDSBAccountDetail();
		jpaQuery.where(QDSBAccountDetail.dSBAccountDetail.dsbVendorName.eq(vendorName)
				.and(QDSBAccountDetail.dSBAccountDetail.icmcId.eq(icmcId)));
		List<String> accountNumber = jpaQuery.list(QDSBAccountDetail.dSBAccountDetail.accountNumber);
		return accountNumber;
	}

	private JPAQuery getFromQueryForCoinSequence() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCoinsSequence.coinsSequence);
		return jpaQuery;
	}

	@Override
	public CoinsSequence getSequence(BigInteger icmcId, int denomination) {
		JPAQuery jpaQuery = getFromQueryForCoinSequence();
		jpaQuery.where(QCoinsSequence.coinsSequence.icmcId.eq(icmcId)
				.and(QCoinsSequence.coinsSequence.denomination.eq(denomination)));
		CoinsSequence coinSequence = jpaQuery.singleResult(QCoinsSequence.coinsSequence);
		return coinSequence;
	}

	@Override
	public boolean insertInCoinSequence(CoinsSequence coinSequence) {
		em.persist(coinSequence);
		return true;
	}

	@Override
	public boolean insertCoinSequenceInBinTxn(BinTransaction binTxn) {
		em.persist(binTxn);
		return true;
	}

	private JPAQuery getFromQueryForFreshAfterCounting() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getDataFromIndentForFreshProcessing(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForFreshAfterCounting();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId).and(QIndent.indent.pendingBundleRequest.gt(0))
				.and(QIndent.indent.cashSource.eq(CashSource.RBI).and(QIndent.indent.status.eq(OtherStatus.ACCEPTED))));
		jpaQuery.groupBy(QIndent.indent.denomination);
		List<Tuple> indentListForFresh = jpaQuery.list(QIndent.indent.denomination, QIndent.indent.bundle.sum(),
				QIndent.indent.pendingBundleRequest.sum());
		return indentListForFresh;
	}

	@Override
	public List<Indent> getIndentDataForBundleCalculation(BigInteger icmcId, int denomination) {
		JPAQuery jpaQuery = getFromQueryForFreshAfterCounting();
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId)
				.and(QIndent.indent.denomination.eq(denomination).and(QIndent.indent.pendingBundleRequest.gt(0))
						.and(QIndent.indent.cashSource.eq(CashSource.RBI))
						.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED))));
		List<Indent> indentList = jpaQuery.list(QIndent.indent);
		return indentList;
	}

	@Override
	public boolean updateIndentAfterFresh(Indent indent) {
		em.merge(indent);
		return true;
	}

	private JPAQuery getFromQueryForBoxMaster() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBoxMaster.boxMaster);
		return jpaQuery;
	}

	@Override
	public List<BoxMaster> getBoxFromBoxMaster(BoxMaster boxMaster) {
		JPAQuery jpaQuery = getFromQueryForBoxMaster();
		jpaQuery.where(QBoxMaster.boxMaster.icmcId.eq(boxMaster.getIcmcId()).and(QBoxMaster.boxMaster.isAllocated.eq(0))
				.and(QBoxMaster.boxMaster.denomination.eq(boxMaster.getDenomination())
						.and(QBoxMaster.boxMaster.currencyType.eq(boxMaster.getCurrencyType()))));
		List<BoxMaster> boxMasterList = jpaQuery.list(QBoxMaster.boxMaster);
		return boxMasterList;
	}

	@Override
	public boolean updateIRV(DiversionIRV diversionIRV) {
		em.merge(diversionIRV);
		return true;
	}

	@Override
	public boolean updateIndent(Indent indent) {
		em.merge(indent);
		return true;
	}

	@Override
	public boolean updateBoxMaster(BoxMaster boxMaster) {
		JPAQuery jpaQuery = getFromQueryForBoxMaster();
		jpaQuery.where(QBoxMaster.boxMaster.boxName.eq(boxMaster.getBoxName()).and(
				QBoxMaster.boxMaster.icmcId.eq(boxMaster.getIcmcId()).and(QBoxMaster.boxMaster.isAllocated.eq(0))));
		BoxMaster dbBoxMaster = jpaQuery.singleResult(QBoxMaster.boxMaster);

		if (dbBoxMaster != null) {
			dbBoxMaster.setIsAllocated(1);
			dbBoxMaster.setUpdateTime(Calendar.getInstance());
			em.merge(dbBoxMaster);
		} else {
			return false;
		}
		return true;
	}

	@Override
	public boolean saveBox(BoxMaster boxMaster) {
		em.persist(boxMaster);
		return true;
	}

	@Override
	public List<BoxMaster> boxMasterDetails(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBoxMaster();
		jpaQuery.where(QBoxMaster.boxMaster.icmcId.eq(icmcId));
		List<BoxMaster> boxMasterList = jpaQuery.list(QBoxMaster.boxMaster);
		return boxMasterList;
	}

	@Override
	public BoxMaster getBoxDetailsById(Long id) {
		return em.find(BoxMaster.class, id);
	}

	@Override
	public boolean updateBoxDetails(BoxMaster boxMaster) {
		em.merge(boxMaster);
		return true;
	}

	@Override
	public BranchReceipt getBranchReceiptRecordById(Long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId).and(QBranchReceipt.branchReceipt.id.eq(id)));
		BranchReceipt BranchReceipt = jpaQuery.singleResult(QBranchReceipt.branchReceipt);
		return BranchReceipt;
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdate(BranchReceipt branchReceiptDb, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binCategoryType.eq(branchReceiptDb.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.binNumber.eq(branchReceiptDb.getBin()))
				.and(QBinTransaction.binTransaction.cashSource.eq(branchReceiptDb.getCashSource())));
		BinTransaction binTxn = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTxn;
	}

	@Override
	public boolean updateBranchReceipt(BranchReceipt branchReceipt) {
		em.merge(branchReceipt);
		return true;
	}

	@Override
	public DSB getDSBReceiptRecordById(Long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(QDSB.dSB.icmcId.eq(icmcId).and(QDSB.dSB.id.eq(id)));
		DSB dsb = jpaQuery.singleResult(QDSB.dSB);
		return dsb;
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdatingDSB(DSB dsbReceiptDb, BigInteger icmcId) {

		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binCategoryType.eq(dsbReceiptDb.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.cashSource.eq(dsbReceiptDb.getCashSource()))
				.and(QBinTransaction.binTransaction.binNumber.eq(dsbReceiptDb.getBin())));
		BinTransaction binTxn = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTxn;
	}

	@Override
	public boolean updateDSB(DSB dsb) {
		em.merge(dsb);
		return true;
	}

	@Override
	public DiversionIRV getDiversionIRVRecordById(Long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDiversionIRV();
		jpaQuery.where(QDiversionIRV.diversionIRV.icmcId.eq(icmcId).and(QDiversionIRV.diversionIRV.id.eq(id)));
		DiversionIRV diversionIRV = jpaQuery.singleResult(QDiversionIRV.diversionIRV);
		return diversionIRV;
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdatingDiversionReceipt(DiversionIRV diversionIRV, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binCategoryType.eq(diversionIRV.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.binNumber.eq(diversionIRV.getBinNumber())));
		BinTransaction binTxn = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTxn;
	}

	@Override
	public boolean updateDiversionIRV(DiversionIRV diversionIRV) {
		em.merge(diversionIRV);
		return true;
	}

	@Override
	public BoxMaster isValidBox(BigInteger icmcId, String boxName) {
		JPAQuery jpaQuery = getFromQueryForBoxMaster();
		jpaQuery.where(
				QBoxMaster.boxMaster.icmcId.eq(icmcId).and(QBoxMaster.boxMaster.boxName.equalsIgnoreCase(boxName)));
		BoxMaster dbBoxName = jpaQuery.singleResult(QBoxMaster.boxMaster);
		return dbBoxName;
	}

	@Override
	public List<Tuple> getCashReceiptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForIRVReportFromBranchReceipt();
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.solId, QBranchReceipt.branchReceipt.branch,
				QBranchReceipt.branchReceipt.srNumber);
		List<Tuple> branchReceiptList = jpaQuery.list(QBranchReceipt.branchReceipt.solId,
				QBranchReceipt.branchReceipt.branch, QBranchReceipt.branchReceipt.srNumber,
				QBranchReceipt.branchReceipt.total.sum());
		return branchReceiptList;
	}

	@Override
	public List<FreshFromRBI> getFreshFromRBIRecord(User user, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForFreshFromRBI();
		jpaQuery.where(QFreshFromRBI.freshFromRBI.icmcId.eq(user.getIcmcId())
				.and(QFreshFromRBI.freshFromRBI.potdarStatus.ne("CANCELLED"))
				.and(QFreshFromRBI.freshFromRBI.insertTime.between(sDate, eDate)));
		List<FreshFromRBI> freshList = jpaQuery.list(QFreshFromRBI.freshFromRBI);
		return freshList;
	}

	@Override
	public BinTransaction getBinTxnRecordFoRBIFreshediitRBI(FreshFromRBI freshRBIReceipt, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashSource.eq(CashSource.RBI))
				.and(QBinTransaction.binTransaction.binNumber.eq(freshRBIReceipt.getBin())));
		BinTransaction binTxn = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTxn;
	}

	@Override
	public BinTransaction getBinTxnRecordForBankReceipteditBankReceipt(BankReceipt bankReceiptDb, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashSource.eq(CashSource.OTHERBANK))
				.and(QBinTransaction.binTransaction.binNumber.eq(bankReceiptDb.getBinNumber())));
		BinTransaction binTxn = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTxn;
	}

	@Override
	public boolean updateBankReceipt(BankReceipt otherBankReceiptdb) {
		em.merge(otherBankReceiptdb);
		return true;
	}

	@Override
	public boolean updateFreshRBIReceipt(FreshFromRBI freshRBIReceiptDb) {
		em.merge(freshRBIReceiptDb);
		return true;
	}

	private JPAQuery getFromQueryForPendingBundleFromMachineAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineAllocation.machineAllocation);
		return jpaQuery;
	}

	@Override
	public List<MachineAllocation> getPendingBundleFromMachineAllocationForReturnBackToVault(BigInteger icmcId,
			Integer denomination, CashSource cashSource) {
		JPAQuery jpaQuery = getFromQueryForPendingBundleFromMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.status.eq(OtherStatus.REQUESTED))
				.and(QMachineAllocation.machineAllocation.denomination.eq(denomination))
				.and(QMachineAllocation.machineAllocation.cashSource.eq(cashSource)));
		List<MachineAllocation> pendingBundleList = jpaQuery.list(QMachineAllocation.machineAllocation);
		return pendingBundleList;
	}

	@Override
	public boolean updatePendingBundleInMachineAllocation(MachineAllocation machineAllocation) {
		em.merge(machineAllocation);
		return true;
	}

	@Override
	public Integer getDSBReceiptSequence(BigInteger icmcId, DSB dsb) {
		JPAQuery jpaQuery = getFromQueryForDSB();
		jpaQuery.where(QDSB.dSB.icmcId.eq(icmcId).and(QDSB.dSB.receiptDate.eq(dsb.getReceiptDate())));
		Integer sequence = jpaQuery.singleResult(QDSB.dSB.receiptSequence.max());
		return sequence;
	}

	@Override
	public BankReceipt getBankReceiptRecordById(Long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBankReceipt();
		jpaQuery.where(QBankReceipt.bankReceipt.icmcId.eq(icmcId).and(QBankReceipt.bankReceipt.id.eq(id)));
		BankReceipt bankReceipt = jpaQuery.singleResult(QBankReceipt.bankReceipt);
		return bankReceipt;
	}

	@Override
	public boolean branchHistory(List<History> historyList) {
		for (History history : historyList) {
			em.persist(history);
		}
		return true;
	}

	@Override
	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		jpaQuery.where(QIndent.indent.icmcId.eq(icmcId)
				.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED).or(QIndent.indent.status.eq(OtherStatus.PROCESSED)))
				.and(QIndent.indent.cashSource.ne(CashSource.RBI)).and(QIndent.indent.bin.ne("NULL"))
				.and(QIndent.indent.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QIndent.indent.denomination);
		List<Tuple> ibitList = jpaQuery.list(QIndent.indent.denomination, QIndent.indent.bundle.sum().multiply(1000));
		return ibitList;
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
	public BinTransaction getBinTxnRecordForUpdateedit(BranchReceipt branchReceiptDb, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binCategoryType.eq(branchReceiptDb.getBinCategoryType()))
				.and(QBinTransaction.binTransaction.binNumber.eq(branchReceiptDb.getBin())));
		BinTransaction binTxn = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTxn;
	}

	@Override
	public BinTransaction getBinTxnRecordForUpdateBox(BoxMaster boxmasterDb, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCashReceiptFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binNumber.eq(boxmasterDb.getBoxName())));
		BinTransaction binTxn = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTxn;
	}

	@Override
	public FreshFromRBI getFreshFromRBIRecordById(Long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForFreshFromRBI();
		jpaQuery.where(QFreshFromRBI.freshFromRBI.icmcId.eq(icmcId).and(QFreshFromRBI.freshFromRBI.id.eq(id)));
		FreshFromRBI freshFromRBI = jpaQuery.singleResult(QFreshFromRBI.freshFromRBI);
		return freshFromRBI;
	}

}
