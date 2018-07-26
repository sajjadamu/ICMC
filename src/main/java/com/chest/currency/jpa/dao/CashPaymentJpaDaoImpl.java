/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */

package com.chest.currency.jpa.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CRALog;
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
import com.chest.currency.entity.model.QBinMaster;
import com.chest.currency.entity.model.QBinTransaction;
import com.chest.currency.entity.model.QBranch;
import com.chest.currency.entity.model.QBranchReceipt;
import com.chest.currency.entity.model.QCITCRADriver;
import com.chest.currency.entity.model.QCITCRAVehicle;
import com.chest.currency.entity.model.QCITCRAVendor;
import com.chest.currency.entity.model.QCRA;
import com.chest.currency.entity.model.QCRAAccountDetail;
import com.chest.currency.entity.model.QCRAAllocation;
import com.chest.currency.entity.model.QCoinsSequence;
import com.chest.currency.entity.model.QDiversionORV;
import com.chest.currency.entity.model.QDiversionORVAllocation;
import com.chest.currency.entity.model.QForwardBundleForCRAPayment;
import com.chest.currency.entity.model.QICMC;
import com.chest.currency.entity.model.QIndent;
import com.chest.currency.entity.model.QMachineAllocation;
import com.chest.currency.entity.model.QORVAllocation;
import com.chest.currency.entity.model.QOtherBank;
import com.chest.currency.entity.model.QOtherBankAllocation;
import com.chest.currency.entity.model.QProcess;
import com.chest.currency.entity.model.QProcessBundleForCRAPayment;
import com.chest.currency.entity.model.QSASAllocation;
import com.chest.currency.entity.model.QSas;
import com.chest.currency.entity.model.QSoiledRemittance;
import com.chest.currency.entity.model.QSoiledRemittanceAllocation;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.SoiledRemittance;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.YesNo;
import com.mysema.query.Tuple;
import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPAUpdateClause;

/**
 * @author root
 *
 */
@Repository
public class CashPaymentJpaDaoImpl implements CashPaymentJpaDao {
	private static final Logger LOG = LoggerFactory.getLogger(CashPaymentJpaDaoImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public boolean sasUpload(List<Sas> sasList, Sas sas) {
		LOG.info("SAS UPLOAD");
		for (Sas sasTemp : sasList) {
			sasTemp.setInsertTime(sas.getInsertTime());
			sasTemp.setUpdateTime(sas.getUpdateTime());
			sasTemp.setInsertBy(sas.getInsertBy());
			sasTemp.setUpdateBy(sas.getUpdateBy());
			sasTemp.setIcmcId(sas.getIcmcId());
			sasTemp.setStatus(7);
			sasTemp.setEnabledisable(1);
			sasTemp.setFileName(sas.getFileName());
			em.persist(sasTemp);
		}
		return true;
	}

	private JPAQuery getFromQueryForSAS() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSas.sas);
		return jpaQuery;
	}

	@Override
	public List<Sas> getSASRecord(User user, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(user.getIcmcId()).and(QSas.sas.status.eq(1))
				.and(QSas.sas.fileName.isNotNull()).and(QSas.sas.insertTime.between(sDate, eDate)));
		List<Sas> sasList = jpaQuery.list(QSas.sas);
		return sasList;
	}

	@Override
	public List<Sas> getSASRecordForceHandover(User user, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(user.getIcmcId()).and(QSas.sas.status.eq(1))
				.and(QSas.sas.fileName.isNotNull()).and(QSas.sas.insertTime.between(sDate, eDate)));
		List<Sas> sasList = jpaQuery.list(QSas.sas);
		return sasList;
	}

	@Override
	public List<Sas> getSASRecordFromSasfile(User user) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(
				QSas.sas.icmcId.eq(user.getIcmcId()).and(QSas.sas.status.eq(7)).and(QSas.sas.fileName.isNotNull()));
		List<Sas> sasList = jpaQuery.list(QSas.sas);
		return sasList;
	}

	@Override
	public boolean insertInSASAllocation(List<SASAllocation> eligibleSASRequestList) {
		for (SASAllocation sas : eligibleSASRequestList) {
			em.persist(sas);
		}
		return true;
	}

	@Override
	public long updateSAS(Sas sas) {
		QSas qSas = QSas.sas;
		long count = new JPAUpdateClause(em, qSas).where(QSas.sas.id.eq(sas.getId()))
				.set(QSas.sas.totalValueOfCoinsRs10, sas.getTotalValueOfCoinsRs10())
				.set(QSas.sas.totalValueOfCoinsRs5, sas.getTotalValueOfCoinsRs5())
				.set(QSas.sas.totalValueOfCoinsRs2, sas.getTotalValueOfCoinsRs2())
				.set(QSas.sas.totalValueOfCoinsRs1, sas.getTotalValueOfCoinsRs1())
				.set(QSas.sas.totalValueOfNotesRs100A, sas.getTotalValueOfNotesRs100A())
				.set(QSas.sas.totalValueOfNotesRs1000A, sas.getTotalValueOfNotesRs1000A())
				.set(QSas.sas.totalValueOfNotesRs1F, sas.getTotalValueOfNotesRs1F())
				.set(QSas.sas.totalValueOfNotesRs10F, sas.getTotalValueOfNotesRs10F())
				.set(QSas.sas.totalValueOfNotesRs100F, sas.getTotalValueOfNotesRs100F())
				.set(QSas.sas.totalValueOfNotesRs1000F, sas.getTotalValueOfNotesRs1000F())
				.set(QSas.sas.totalValueOfNotesRs2F, sas.getTotalValueOfNotesRs2F())
				.set(QSas.sas.totalValueOfNotesRs20F, sas.getTotalValueOfNotesRs20F())
				.set(QSas.sas.totalValueOfNotesRs5F, sas.getTotalValueOfNotesRs5F())
				.set(QSas.sas.totalValueOfNotesRs50F, sas.getTotalValueOfNotesRs50F())
				.set(QSas.sas.totalValueOfNotesRs500A, sas.getTotalValueOfNotesRs500A())
				.set(QSas.sas.totalValueOfNotesRs500F, sas.getTotalValueOfNotesRs500F())
				.set(QSas.sas.totalValueOfNotesRs1I, sas.getTotalValueOfNotesRs1I())
				.set(QSas.sas.totalValueOfNotesRs10I, sas.getTotalValueOfNotesRs10I())
				.set(QSas.sas.totalValueOfNotesRs100I, sas.getTotalValueOfNotesRs100I())
				.set(QSas.sas.totalValueOfNotesRs1000I, sas.getTotalValueOfNotesRs1000I())
				.set(QSas.sas.totalValueOfNotesRs2I, sas.getTotalValueOfNotesRs2I())
				.set(QSas.sas.totalValueOfNotesRs20I, sas.getTotalValueOfNotesRs20I())
				.set(QSas.sas.totalValueOfNotesRs5I, sas.getTotalValueOfNotesRs5I())
				.set(QSas.sas.totalValueOfNotesRs50I, sas.getTotalValueOfNotesRs50I())
				.set(QSas.sas.totalValueOfNotesRs500I, sas.getTotalValueOfNotesRs500I())
				.set(QSas.sas.totalValueOfNotesRs2000I, sas.getTotalValueOfNotesRs2000I())
				.set(QSas.sas.totalValueOfNotesRs2000F, sas.getTotalValueOfNotesRs2000F())
				.set(QSas.sas.totalValueOfNotesRs2000A, sas.getTotalValueOfNotesRs2000A())
				.set(QSas.sas.totalValueOfNotesRs200I, sas.getTotalValueOfNotesRs200I())
				.set(QSas.sas.totalValueOfNotesRs200F, sas.getTotalValueOfNotesRs200F())
				.set(QSas.sas.totalValueOfNotesRs200A, sas.getTotalValueOfNotesRs200A())
				.set(QSas.sas.totalValue, sas.getTotalValue()).set(QSas.sas.enabledisable, new Integer(0)).execute();
		return count;
	}

	private JPAQuery getFromQueryForSoiledRemittance() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSoiledRemittance.soiledRemittance);
		return jpaQuery;
	}

	@Override
	public List<SoiledRemittance> soiledRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSoiledRemittance();
		jpaQuery.where(QSoiledRemittance.soiledRemittance.icmcId.eq(icmcId)
				.and(QSoiledRemittance.soiledRemittance.status.eq(OtherStatus.REQUESTED))
				.and(QSoiledRemittance.soiledRemittance.insertTime.between(sDate, eDate)));
		List<SoiledRemittance> soiledList = jpaQuery.list(QSoiledRemittance.soiledRemittance);
		return soiledList;
	}

	@Override
	public List<SoiledRemittance> getSoiledRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSoiledRemittance();
		jpaQuery.where(QSoiledRemittance.soiledRemittance.icmcId.eq(icmcId)
				.and(QSoiledRemittance.soiledRemittance.status.ne(OtherStatus.CANCELLED))
				.and(QSoiledRemittance.soiledRemittance.insertTime.between(sDate, eDate)));
		List<SoiledRemittance> soiledList = jpaQuery.list(QSoiledRemittance.soiledRemittance);
		return soiledList;
	}

	@Override
	public long updateSASStatus(Sas sas) {
		// em.merge(indent);
		QSas qSas = QSas.sas;
		Long count = new JPAUpdateClause(em, qSas).where(QSas.sas.icmcId.eq(sas.getIcmcId()).and(QSas.sas.status.eq(7)))
				.set(qSas.status, 1).execute();
		return count;
	}

	@Override
	public boolean updateSASStatusAccept(Sas sas) {
		em.merge(sas);
		return true;
	}

	private JPAQuery getFromQueryForSASAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSASAllocation.sASAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForDiversionORVAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionORVAllocation.diversionORVAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForORVAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QORVAllocation.oRVAllocation);
		return jpaQuery;
	}

	@Override
	public List<SASAllocation> getSASAllocationRecord(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation().where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.REQUESTED)));
		List<SASAllocation> sasAllocation = jpaQuery.list(QSASAllocation.sASAllocation);
		return sasAllocation;
	}

	@Override
	public List<SASAllocation> getSasAllocationBySasId(long sasId) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation().where(QSASAllocation.sASAllocation.parentId.eq(sasId));
		List<SASAllocation> sasAllocation = jpaQuery.list(QSASAllocation.sASAllocation);
		return sasAllocation;
	}

	@Override
	public boolean saveSoiledRemittance(SoiledRemittance soiledRemittance) {
		em.persist(soiledRemittance);
		return true;
	}

	@Override
	public SoiledRemittance getSoiledRemittanceById(BigInteger icmcId, Calendar sDate, Calendar eDate, long id) {
		JPAQuery jpaQuery = getFromQueryForSoiledRemittance();
		jpaQuery.where(
				QSoiledRemittance.soiledRemittance.icmcId.eq(icmcId).and(QSoiledRemittance.soiledRemittance.id.eq(id))
						.and(QSoiledRemittance.soiledRemittance.insertTime.between(sDate, eDate)));
		SoiledRemittance soiledList = jpaQuery.singleResult(QSoiledRemittance.soiledRemittance);
		return soiledList;
	}

	@Override
	public boolean saveDiversionORV(DiversionORV diversionORV) {
		em.persist(diversionORV);
		// em.merge(diversionORV);
		return true;
	}

	private JPAQuery getFromQueryForDiversionORV() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionORV.diversionORV);
		return jpaQuery;
	}

	@Override
	public List<DiversionORV> getDiversionORV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForDiversionORV();
		jpaQuery.where(QDiversionORV.diversionORV.icmcId.eq(icmcId)
				.and(QDiversionORV.diversionORV.otherStatus.ne(OtherStatus.CANCELLED))
				.and(QDiversionORV.diversionORV.insertTime.between(sDate, eDate)));
		List<DiversionORV> diversionList = jpaQuery.list(QDiversionORV.diversionORV);
		return diversionList;
	}

	/*
	 * @Override public List<Sas> getORVRecords(BigInteger icmcId, Calendar
	 * sDate, Calendar eDate) { JPAQuery jpaQuery = getFromQueryForSAS();
	 * jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.fileName.isNull().
	 * and(QSas.sas.status.eq(1))
	 * .or(QSas.sas.status.eq(2)).and(QSas.sas.insertTime.between(sDate,
	 * eDate)))); List<Sas> sasList = jpaQuery.list(QSas.sas); return sasList; }
	 */

	@Override
	public List<Sas> getORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId)
				.and(QSas.sas.status.eq(1).or(QSas.sas.status.eq(2)).and(QSas.sas.updateTime.between(sDate, eDate))));
		List<Sas> sasList = jpaQuery.list(QSas.sas);
		return sasList;
	}

	@Override
	public List<Sas> getRequestORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(
				QSas.sas.fileName.isNull().and(QSas.sas.status.eq(0)).and(QSas.sas.insertTime.between(sDate, eDate))));
		List<Sas> sasList = jpaQuery.list(QSas.sas);
		return sasList;
	}

	@Override
	public List<Sas> getRequestAcceptORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId)
				.and(QSas.sas.fileName.isNull()
						.and(QSas.sas.status.eq(0).or(QSas.sas.status.eq(1)).or(QSas.sas.status.eq(2)))
						.and(QSas.sas.insertTime.between(sDate, eDate))));
		List<Sas> sasList = jpaQuery.list(QSas.sas);
		return sasList;
	}

	@Override
	public boolean saveORV(ORV orv) {
		em.persist(orv);
		return true;
	}

	private JPAQuery getFromQueryForSoiledAccept() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return jpaQuery;
	}

	@Override
	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSoiledAccept();
		jpaQuery.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.REQUESTED)));
		List<SoiledRemittanceAllocation> soiledList = jpaQuery
				.list(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return soiledList;
	}

	private JPAQuery getFromQueryForORVAccept() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QORVAllocation.oRVAllocation);
		return jpaQuery;
	}

	@Override
	public List<ORVAllocation> getORVForAccept(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForORVAccept();
		jpaQuery.where(QORVAllocation.oRVAllocation.status.equalsIgnoreCase("INDENT")
				.and(QORVAllocation.oRVAllocation.icmcId.eq(icmcId)));
		List<ORVAllocation> orvList = jpaQuery.list(QORVAllocation.oRVAllocation);
		return orvList;
	}

	private JPAQuery getFromQueryForDiversionORVAccept() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QDiversionORVAllocation.diversionORVAllocation);
		return jpaQuery;
	}

	@Override
	public List<DiversionORVAllocation> getDiversionORVForAccept(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForDiversionORVAccept();
		jpaQuery.where(QDiversionORVAllocation.diversionORVAllocation.status.eq(OtherStatus.REQUESTED)
				.and(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(icmcId)));
		List<DiversionORVAllocation> dORVLIst = jpaQuery.list(QDiversionORVAllocation.diversionORVAllocation);
		return dORVLIst;
	}

	@Override
	public List<BinTransaction> getBinTxnListByDenom(BinTransaction binTx) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTx.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(binTx.getDenomination())
						.and(QBinTransaction.binTransaction.receiveBundle.gt(0)
								.and(QBinTransaction.binTransaction.binType.eq(binTx.getBinType())))));
		jpaQuery.orderBy(QBinTransaction.binTransaction.insertTime.asc());
		List<BinTransaction> binList = jpaQuery.list(QBinTransaction.binTransaction);
		return binList;
	}

	private JPAQuery getFromQueryForBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForSASRequestFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction).orderBy(QBinTransaction.binTransaction.insertTime.asc());
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getBinNumListForSas(SASAllocation sas, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(sas.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(sas.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.verified.ne(YesNo.No))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES))
				.and(QBinTransaction.binTransaction.binType.eq(type)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public List<BinTransaction> getBinNumListForSasForUnprocess(SASAllocation sas, CurrencyType type,BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(sas.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(sas.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.verified.ne(YesNo.No))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.cashSource.eq(CashSource.BRANCH))
				.and(QBinTransaction.binTransaction.binType.eq(type))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binCategoryType)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public List<SASAllocation> getBinFromSAS(SASAllocation sas) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(sas.getIcmcId())
				.and(QSASAllocation.sASAllocation.denomination.eq(sas.getDenomination()))
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.REQUESTED)));
		List<SASAllocation> sasList = jpaQuery.list(QSASAllocation.sASAllocation);
		return sasList;
	}

	@Override
	public List<BinTransaction> getBinNumListForSoiled(SoiledRemittanceAllocation soiled, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(soiled.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(soiled.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.binType.eq(type)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);

		return txnList;
	}

	@Override
	public List<BinTransaction> getBinNumListForDiversion(DiversionORVAllocation dorv, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(dorv.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(dorv.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES))
				.and(QBinTransaction.binTransaction.binType.eq(type)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public List<DiversionORVAllocation> getBinFromDorvAllocation(DiversionORVAllocation dorv) {

		JPAQuery jpaQuery = getFromQueryForDiversionORVAllocation();
		jpaQuery.where(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(dorv.getIcmcId())
				.and(QDiversionORVAllocation.diversionORVAllocation.denomination.eq(dorv.getDenomination()))
				.and(QDiversionORVAllocation.diversionORVAllocation.status.eq(OtherStatus.REQUESTED)));
		List<DiversionORVAllocation> dorvList = jpaQuery.list(QDiversionORVAllocation.diversionORVAllocation);
		return dorvList;
	}

	@Override
	public List<BinTransaction> getBinNumListForORV(ORVAllocation orv, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(orv.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(orv.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.binType.eq(type)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public List<ORVAllocation> getBinFromOrvAllocation(ORVAllocation orv) {

		JPAQuery jpaQuery = getFromQueryForORVAllocation();
		jpaQuery.where(QORVAllocation.oRVAllocation.icmcId.eq(orv.getIcmcId())
				.and(QORVAllocation.oRVAllocation.denomination.eq(orv.getDenomination()))
				.and(QORVAllocation.oRVAllocation.status.equalsIgnoreCase("INDENT")));
		List<ORVAllocation> orvList = jpaQuery.list(QORVAllocation.oRVAllocation);
		return orvList;

	}

	@Override
	public long updateSoiledStatus(SoiledRemittanceAllocation soiled) {
		QSoiledRemittanceAllocation qSoiled = QSoiledRemittanceAllocation.soiledRemittanceAllocation;
		long count = new JPAUpdateClause(em, qSoiled)
				.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.id.eq(soiled.getId()))
				.set(QSoiledRemittanceAllocation.soiledRemittanceAllocation.weight, soiled.getWeight())
				.set(qSoiled.status, OtherStatus.ACCEPTED).set(qSoiled.updateTime, soiled.getUpdateTime()).execute();
		return count;
	}

	@Override
	public void updateSoiledRemittanceStatus(SoiledRemittance soiledRemittanceTemp) {
		QSoiledRemittance qSoiledRemittance = QSoiledRemittance.soiledRemittance;
		new JPAUpdateClause(em, qSoiledRemittance)
				.where(QSoiledRemittance.soiledRemittance.id.eq(soiledRemittanceTemp.getId()))
				.set(qSoiledRemittance.status, OtherStatus.ACCEPTED)
				.set(qSoiledRemittance.updateTime, Calendar.getInstance()).execute();
	}

	@Override
	public long updateDorvStatus(DiversionORVAllocation dorv) {
		QDiversionORVAllocation qdorv = QDiversionORVAllocation.diversionORVAllocation;
		long count = new JPAUpdateClause(em, qdorv)
				.where(QDiversionORVAllocation.diversionORVAllocation.id.eq(dorv.getId())
						.and(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(dorv.getIcmcId())))
				.set(qdorv.status, OtherStatus.ACCEPTED).set(qdorv.updateTime, dorv.getUpdateTime()).execute();
		return count;
	}

	@Override
	public long updateORVStatus(ORVAllocation orv) {
		QORVAllocation qOrv = QORVAllocation.oRVAllocation;
		long count = new JPAUpdateClause(em, qOrv).where(QORVAllocation.oRVAllocation.id.eq(orv.getId()))
				.set(qOrv.status, "ACCEPTED").execute();
		return count;
	}

	@Override
	public long updateSASstatus(SASAllocation sas) {
		QSASAllocation qsasAllocation = QSASAllocation.sASAllocation;
		long count = new JPAUpdateClause(em, qsasAllocation).where(QSASAllocation.sASAllocation.id.eq(sas.getId()))
				.set(qsasAllocation.status, OtherStatus.ACCEPTED).set(qsasAllocation.updateTime, sas.getUpdateTime())
				.execute();
		return count;
	}

	@Override
	public boolean saveCashReleased(CashReleased cashReleased) {
		em.persist(cashReleased);
		return true;
	}

	private JPAQuery getFromQueryForCashReleasedFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	@Override
	public BinTransaction getBinRecordForCashReleassed(BinTransaction txn) {
		JPAQuery jpaQuery = getFromQueryForCashReleasedFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(txn.getIcmcId())
				.and(QBinTransaction.binTransaction.binNumber.eq(txn.getBinNumber())
						.and(QBinTransaction.binTransaction.cashType.eq(txn.getCashType()))
						.and(QBinTransaction.binTransaction.denomination.eq(txn.getDenomination())
								.and(QBinTransaction.binTransaction.binType.eq(txn.getBinType())
										.and(QBinTransaction.binTransaction.verified.eq(YesNo.Yes))))));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
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
	public int deleteDataFromBinTxn(BinTransaction txnBin) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPADeleteClause(em, qBinTransaction).where(qBinTransaction.binNumber.eq(txnBin.getBinNumber())
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.COINS))
				.and(QBinTransaction.binTransaction.denomination.eq(txnBin.getDenomination()))
				.and(QBinTransaction.binTransaction.icmcId.eq(txnBin.getIcmcId()))).execute();
		return (int) count;
	}

	@Override
	public boolean updateBinTxn(BinTransaction binTransaction) {
		em.merge(binTransaction);
		return true;
	}

	private JPAQuery getFromQueryForCurrencyTypeFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction).orderBy(QBinTransaction.binTransaction.insertTime.asc());
		return jpaQuery;
	}

	@Override
	public BinTransaction getCurrencyTypeForORV(ORVAllocation orv, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCurrencyTypeFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId).and(QBinTransaction.binTransaction.denomination
				.eq(orv.getDenomination()).and(QBinTransaction.binTransaction.receiveBundle.gt(orv.getBundle()))));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	private JPAQuery getFromQueryForORVReportFromBranchReceipt() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSas.sas);
		return jpaQuery;
	}

	@Override
	public List<Sas> getORVReport(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForORVReportFromBranchReceipt();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.status.eq(1).or(QSas.sas.status.eq(2)))
				.and(QSas.sas.insertTime.between(sDate, eDate)));
		List<Sas> orvList = jpaQuery.list(QSas.sas);
		return orvList;
	}

	private JPAQuery getFromQueryForVendorFromCITCRAVendor() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCITCRAVendor.cITCRAVendor);
		return jpaQuery;
	}

	@Override
	public List<CITCRAVendor> getVendor() {
		JPAQuery jpaQuery = getFromQueryForVendorFromCITCRAVendor();
		List<CITCRAVendor> vendorList = jpaQuery.list(QCITCRAVendor.cITCRAVendor);
		return vendorList;
	}

	private JPAQuery getFromQueryForDriverFromCITCRACustodian() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCITCRADriver.cITCRADriver);
		return jpaQuery;
	}

	@Override
	public List<String> getCustodianName(String vendor) {
		JPAQuery jpaQuery = getFromQueryForDriverFromCITCRACustodian();
		jpaQuery.where(QCITCRADriver.cITCRADriver.vendorName.eq(vendor));
		List<String> driverList = jpaQuery.list(QCITCRADriver.cITCRADriver.driverName);
		return driverList;
	}

	private JPAQuery getFromQueryForVehicleNumberFromCITCRAVehicle() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCITCRAVehicle.cITCRAVehicle);
		return jpaQuery;
	}

	@Override
	public List<String> getVehicleNumber(String vendor) {
		JPAQuery jpaQuery = getFromQueryForVehicleNumberFromCITCRAVehicle();
		jpaQuery.where(QCITCRAVehicle.cITCRAVehicle.name.eq(vendor));
		List<String> vehicleList = jpaQuery.list(QCITCRAVehicle.cITCRAVehicle.number);
		return vehicleList;
	}

	@Override
	public List<Tuple> getRecordForSummary(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();

		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES))
				.and(QBinTransaction.binTransaction.verified.ne(YesNo.No)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.binType);

		List<Tuple> summaryList = jpaQuery.list(QBinTransaction.binTransaction.binType,
				QBinTransaction.binTransaction.denomination, QBinTransaction.binTransaction.receiveBundle.sum());
		return summaryList;
	}

	@Override
	public List<Sas> solIdForSASPayment(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(
				QSas.sas.icmcId.eq(icmcId).and(QSas.sas.status.eq(0)).and(QSas.sas.insertTime.between(sDate, eDate)));
		List<Sas> solIdForPayment = jpaQuery.list(QSas.sas);
		return solIdForPayment;
	}

	@Override
	public List<Sas> solIdForSASPaymentAccepted(BigInteger icmcId, Calendar sDate, Calendar eDate, Set<Long> pList) {
		// JPAQuery jpaQuery = getFromQueryForSAS();
		JPAQuery jpaQuery = new JPAQuery(em);
		/*
		 * jpaQuery.from(QSASAllocation.sASAllocation)
		 * .where(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED)
		 * .and(QSASAllocation.sASAllocation.insertTime.between(sDate, eDate)))
		 * .orderBy(QSASAllocation.sASAllocation.parentId.asc());
		 * List<SASAllocation> solIdForPaymenta =
		 * jpaQuery.list(QSASAllocation.sASAllocation); Set<Long> pList=new
		 * HashSet<Long>(); for(SASAllocation parentId : solIdForPaymenta){
		 * pList.add(parentId.getParentId()); }
		 */

		/*
		 * jpaQuery.innerJoin(QSASAllocation.sASAllocation)
		 * .on((QSas.sas.id).eq(QSASAllocation.sASAllocation.parentId))
		 * .where(QSas.sas.icmcId.eq(icmcId) .and(QSas.sas.status.eq(0))
		 * .and(QSas.sas.insertTime.between(sDate, eDate))
		 * .and(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED)));
		 */
		jpaQuery.from(QSas.sas).distinct().where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.status.eq(1))
				.and(QSas.sas.insertTime.between(sDate, eDate)).and(QSas.sas.id.in(pList)));
		List<Sas> solIdForPayment = jpaQuery.list(QSas.sas);
		return solIdForPayment;
	}

	@Override
	public Sas sasPaymentDetails(long id) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.id.eq(id));
		Sas sas = jpaQuery.singleResult(QSas.sas);
		return sas;
	}

	@Override
	public Sas sasRecordByID(long id) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.id.eq(id));
		Sas sas = jpaQuery.singleResult(QSas.sas);
		return sas;
	}

	@Override
	public boolean updateSASForSASRelease(Sas sas) {
		em.merge(sas);
		return true;
	}

	@Override
	public boolean createORVPayment(Sas sas) {
		em.persist(sas);
		return true;
	}

	@Override
	public boolean createSASAllocationForORVPayment(SASAllocation sasAllocation) {
		em.persist(sasAllocation);
		return true;
	}

	@Override
	public boolean createCRAPayment(CRA cra) {
		em.persist(cra);
		return true;
	}

	@Override
	public long getLastCreateCRAPaymentId() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRA.cRA).orderBy(QCRA.cRA.id.desc());
		return jpaQuery.singleResult(QCRA.cRA.id);
	}

	@Override
	public CRA getLastCreateCRAPaymentList(long craId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRA.cRA).where(QCRA.cRA.id.eq(craId));
		CRA lastCreatedcraPayment = jpaQuery.singleResult(QCRA.cRA);

		return lastCreatedcraPayment;
	}

	@Override
	public boolean createCRALogPayment(CRALog cralog) {
		em.persist(cralog);
		return true;
	}

	@Override
	public List<BinTransaction> getBinNumListForCRA(CRAAllocation cra, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(cra.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(cra.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.verified.ne(YesNo.No))
				.and(QBinTransaction.binTransaction.binType.eq(type)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	private JPAQuery getFromQueryForCRAAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRAAllocation.cRAAllocation);
		return jpaQuery;
	}

	private JPAQuery getFromQueryForProcessBundleForCraPayment() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QProcessBundleForCRAPayment.processBundleForCRAPayment);
		return jpaQuery;
	}

	@Override
	public List<CRAAllocation> getBinFromCRA(CRAAllocation cra) {
		JPAQuery jpaQuery = getFromQueryForCRAAllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(cra.getIcmcId())
				.and(QCRAAllocation.cRAAllocation.denomination.eq(cra.getDenomination()))
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.REQUESTED)));
		List<CRAAllocation> craList = jpaQuery.list(QCRAAllocation.cRAAllocation);
		return craList;
	}

	@Override
	public boolean insertInCRAAllocation(List<CRAAllocation> eligibleCRARequestList) {
		for (CRAAllocation cra : eligibleCRARequestList) {
			em.persist(cra);
		}
		return true;
	}

	private JPAQuery getFromQueryForCRAAccountDetail() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRAAccountDetail.cRAAccountDetail);
		return jpaQuery;
	}

	@Override
	public List<CRAAccountDetail> getVendorAndMSPName(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCRAAccountDetail();
		jpaQuery.where(QCRAAccountDetail.cRAAccountDetail.icmcId.eq(icmcId));
		List<CRAAccountDetail> vendorNameList = jpaQuery.list(QCRAAccountDetail.cRAAccountDetail);
		return vendorNameList;
	}

	@Override
	public String getAccountNumberByMSPName(String mspName, String vendor, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCRAAccountDetail();
		jpaQuery.where(QCRAAccountDetail.cRAAccountDetail.icmcId.eq(icmcId)
				.and(QCRAAccountDetail.cRAAccountDetail.craVendorName.eq(vendor.trim()))
				.and(QCRAAccountDetail.cRAAccountDetail.mspName.eq(mspName.trim())));
		String mspNameFromDB = jpaQuery.singleResult(QCRAAccountDetail.cRAAccountDetail.accountNumber);
		return mspNameFromDB;
	}

	@Override
	public boolean createOtherBankPayment(OtherBank otherBank) {
		em.persist(otherBank);
		return true;
	}

	private JPAQuery getFromQueryForOtherBankAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QOtherBankAllocation.otherBankAllocation);
		return jpaQuery;
	}

	/*
	 * @Override public List<OtherBankAllocation>
	 * getBinFromOtherBank(OtherBankAllocation otherBankAllocation) { JPAQuery
	 * jpaQuery = getFromQueryForOtherBankAllocation();
	 * jpaQuery.where(QOtherBankAllocation.otherBankAllocation.icmcId.eq(
	 * otherBankAllocation.getIcmcId())
	 * .and(QOtherBankAllocation.otherBankAllocation.denomination.eq(
	 * otherBankAllocation.getDenomination()))
	 * .and(QOtherBankAllocation.otherBankAllocation.status.eq(OtherStatus.
	 * REQUESTED))); return null; }
	 */

	@Override
	public List<BinTransaction> getBinNumListForOtherBank(OtherBankAllocation otherBank, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(otherBank.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(otherBank.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES))
				.and(QBinTransaction.binTransaction.verified.ne(YesNo.No))
				.and(QBinTransaction.binTransaction.binType.eq(type)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public boolean updateBinTransaction(BinTransaction binTransaction) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qBinTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(binTransaction.getIcmcId()))
				.set(qBinTransaction.status, BinStatus.EMPTY).execute();
		return count > 0 ? true : false;
	}

	private JPAQuery getFromQueryForCRA() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRA.cRA);
		return jpaQuery;
	}

	@Override
	public List<CRA> getCRARecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId)
				.and(QCRA.cRA.status.eq(OtherStatus.RELEASED).or(QCRA.cRA.status.eq(OtherStatus.ACCEPTED)))
				.and(QCRA.cRA.insertTime.between(sDate, eDate)));
		List<CRA> craRecords = jpaQuery.list(QCRA.cRA);
		return craRecords;
	}

	@Override
	public List<ProcessBundleForCRAPayment> getProcessBundleCRARecord(long craId) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleForCraPayment();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.craId.eq(craId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.RELEASED)));
		List<ProcessBundleForCRAPayment> craRecords = jpaQuery
				.list(QProcessBundleForCRAPayment.processBundleForCRAPayment);
		return craRecords;
	}

	@Override
	public List<CRA> getCRARequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId).and(QCRA.cRA.status.ne(OtherStatus.CANCELLED))
				.and(QCRA.cRA.insertTime.between(sDate, eDate)));
		List<CRA> craRecords = jpaQuery.list(QCRA.cRA);
		return craRecords;
	}

	@Override
	public List<OtherBank> getOtherBankPaymentRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForOtherBank();
		jpaQuery.where(QOtherBank.otherBank.icmcId.eq(icmcId)
				.and(QOtherBank.otherBank.status.eq(OtherStatus.ACCEPTED)
						.or(QOtherBank.otherBank.status.eq(OtherStatus.RELEASED)))
				.and(QOtherBank.otherBank.insertTime.between(sDate, eDate)));
		List<OtherBank> otherBankList = jpaQuery.list(QOtherBank.otherBank);
		return otherBankList;
	}

	@Override
	public List<OtherBank> getOtherBankPaymentRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForOtherBank();
		jpaQuery.where(QOtherBank.otherBank.icmcId.eq(icmcId).and(QOtherBank.otherBank.status.ne(OtherStatus.CANCELLED))
				.and(QOtherBank.otherBank.insertTime.between(sDate, eDate)));
		List<OtherBank> otherBankList = jpaQuery.list(QOtherBank.otherBank);
		return otherBankList;
	}

	private JPAQuery getFromSASForVoucher() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSas.sas);
		return jpaQuery;
	}

	@Override
	public List<Tuple> getRecordORVVoucher(String solId, Calendar sDate, Calendar eDate, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromSASForVoucher();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.solID.eq(solId))
				.and(QSas.sas.status.eq(1).or(QSas.sas.status.eq(2))).and(QSas.sas.insertTime.between(sDate, eDate)));
		List<Tuple> tupleList = jpaQuery.list(QSas.sas.solID, QSas.sas.branch, QSas.sas.srNo,
				QSas.sas.totalValueOfNotesRs1000F.add(QSas.sas.totalValueOfNotesRs1000I)
						.add(QSas.sas.totalValueOfNotesRs1000A),
				QSas.sas.totalValueOfNotesRs500A.add(QSas.sas.totalValueOfNotesRs500F)
						.add(QSas.sas.totalValueOfNotesRs500I).add(QSas.sas.totalValueOfNotesRs500U),
				QSas.sas.totalValueOfNotesRs100A.add(QSas.sas.totalValueOfNotesRs100I)
						.add(QSas.sas.totalValueOfNotesRs100F).add(QSas.sas.totalValueOfNotesRs100U),
				QSas.sas.totalValueOfNotesRs50F.add(QSas.sas.totalValueOfNotesRs50I)
						.add(QSas.sas.totalValueOfNotesRs50U),
				QSas.sas.totalValueOfNotesRs20F.add(QSas.sas.totalValueOfNotesRs20I)
						.add(QSas.sas.totalValueOfNotesRs20U),
				QSas.sas.totalValueOfNotesRs10F.add(QSas.sas.totalValueOfNotesRs10I)
						.add(QSas.sas.totalValueOfNotesRs10U),
				QSas.sas.totalValueOfNotesRs5F.add(QSas.sas.totalValueOfNotesRs5I).add(QSas.sas.totalValueOfNotesRs5U),
				QSas.sas.totalValueOfNotesRs1F.add(QSas.sas.totalValueOfNotesRs1I).add(QSas.sas.totalValueOfNotesRs1U),
				QSas.sas.totalValueOfNotesRs2F.add(QSas.sas.totalValueOfNotesRs2I).add(QSas.sas.totalValueOfNotesRs2U),
				QSas.sas.totalValueOfNotesRs2000A.add(QSas.sas.totalValueOfNotesRs2000F)
						.add(QSas.sas.totalValueOfNotesRs2000I).add(QSas.sas.totalValueOfNotesRs2000U),
				QSas.sas.totalValueOfCoinsRs1, QSas.sas.totalValueOfCoinsRs2, QSas.sas.totalValueOfCoinsRs5,
				QSas.sas.totalValueOfCoinsRs10, QSas.sas.totalValueOfNotesRs200A.add(QSas.sas.totalValueOfNotesRs200F)
						.add(QSas.sas.totalValueOfNotesRs200I));
		return tupleList;
	}

	@Override
	public List<Tuple> getRecordORVVoucherById(long id, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromSASForVoucher();
		jpaQuery.where(QSas.sas.id.eq(id).and(QSas.sas.status.ne(4))
				/* .and(QSas.sas.status.eq(1)).or(QSas.sas.status.eq(2)) */
				.and(QSas.sas.insertTime.between(sDate, eDate)));
		List<Tuple> tupleList = jpaQuery.list(QSas.sas.solID, QSas.sas.branch, QSas.sas.srNo,
				QSas.sas.totalValueOfNotesRs1000F.add(QSas.sas.totalValueOfNotesRs1000I)
						.add(QSas.sas.totalValueOfNotesRs1000A),
				QSas.sas.totalValueOfNotesRs500A.add(QSas.sas.totalValueOfNotesRs500F)
						.add(QSas.sas.totalValueOfNotesRs500I).add(QSas.sas.totalValueOfNotesRs500U),
				QSas.sas.totalValueOfNotesRs100A.add(QSas.sas.totalValueOfNotesRs100I)
						.add(QSas.sas.totalValueOfNotesRs100F).add(QSas.sas.totalValueOfNotesRs100U),
				QSas.sas.totalValueOfNotesRs50F.add(QSas.sas.totalValueOfNotesRs50I)
						.add(QSas.sas.totalValueOfNotesRs50U),
				QSas.sas.totalValueOfNotesRs20F.add(QSas.sas.totalValueOfNotesRs20I)
						.add(QSas.sas.totalValueOfNotesRs20U),
				QSas.sas.totalValueOfNotesRs10F.add(QSas.sas.totalValueOfNotesRs10I)
						.add(QSas.sas.totalValueOfNotesRs10U),
				QSas.sas.totalValueOfNotesRs5F.add(QSas.sas.totalValueOfNotesRs5I).add(QSas.sas.totalValueOfNotesRs5U),
				QSas.sas.totalValueOfNotesRs1F.add(QSas.sas.totalValueOfNotesRs1I).add(QSas.sas.totalValueOfNotesRs1U),
				QSas.sas.totalValueOfNotesRs2F.add(QSas.sas.totalValueOfNotesRs2I).add(QSas.sas.totalValueOfNotesRs2U),
				QSas.sas.totalValueOfNotesRs2000A.add(QSas.sas.totalValueOfNotesRs2000F)
						.add(QSas.sas.totalValueOfNotesRs2000I).add(QSas.sas.totalValueOfNotesRs2000U),
				QSas.sas.totalValueOfCoinsRs1, QSas.sas.totalValueOfCoinsRs2, QSas.sas.totalValueOfCoinsRs5,
				QSas.sas.totalValueOfCoinsRs10, QSas.sas.totalValueOfNotesRs200A.add(QSas.sas.totalValueOfNotesRs200F)
						.add(QSas.sas.totalValueOfNotesRs200I));
		return tupleList;
	}

	@Override
	public List<Sas> getSolId(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromSASForVoucher();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.insertTime.between(sDate, eDate))
				.and(QSas.sas.status.eq(1).or(QSas.sas.status.eq(2))));
		List<Sas> solIdList = jpaQuery.list(QSas.sas);
		return solIdList;
	}

	@Override
	public List<Sas> getAcceptSolId(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromSASForVoucher();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.insertTime.between(sDate, eDate))
				.and(QSas.sas.status.eq(1).or(QSas.sas.status.eq(2))));
		List<Sas> solIdList = jpaQuery.list(QSas.sas);
		return solIdList;
	}

	@Override
	public Sas getFileName(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId));
		Sas sas = jpaQuery.singleResult(QSas.sas);
		return sas;
	}

	@Override
	public Sas getSameDayFileName(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.insertTime.between(sDate, eDate)));
		Sas sasFileName = jpaQuery.singleResult(QSas.sas);
		return sasFileName;
	}

	@Override
	public boolean updateBinTransactionForEmpty(BinTransaction binTransaction) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qBinTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(binTransaction.getIcmcId())
						.and(QBinTransaction.binTransaction.binNumber.eq(binTransaction.getBinNumber())))
				.set(qBinTransaction.status, BinStatus.EMPTY).execute();
		return count > 0 ? true : false;
	}

	private JPAQuery getFromQueryForCRAAccept() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRAAllocation.cRAAllocation);
		return jpaQuery;
	}

	@Override
	public List<CRAAllocation> getCRAForAccept(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCRAAccept();
		jpaQuery.where(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.REQUESTED)
				.and(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)));
		List<CRAAllocation> CRAList = jpaQuery.list(QCRAAllocation.cRAAllocation);
		return CRAList;
	}

	@Override
	public long updateCRAStatus(CRAAllocation cra) {
		QCRAAllocation qcra = QCRAAllocation.cRAAllocation;
		long count = new JPAUpdateClause(em, qcra).where(QCRAAllocation.cRAAllocation.id.eq(cra.getId()))
				.set(qcra.status, OtherStatus.PROCESSED).execute();
		return count;
	}

	private JPAQuery getFromQueryForOtherBankAccept() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QOtherBankAllocation.otherBankAllocation);
		return jpaQuery;
	}

	@Override
	public List<OtherBankAllocation> getOtherBankForAccept(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForOtherBankAccept();
		jpaQuery.where(QOtherBankAllocation.otherBankAllocation.status.eq(OtherStatus.REQUESTED)
				.and(QOtherBankAllocation.otherBankAllocation.icmcId.eq(icmcId)));
		List<OtherBankAllocation> otherBankList = jpaQuery.list(QOtherBankAllocation.otherBankAllocation);
		return otherBankList;
	}

	@Override
	public long updateOtherBankStatus(OtherBankAllocation otherBank) {
		QOtherBankAllocation qotherBank = QOtherBankAllocation.otherBankAllocation;
		long count = new JPAUpdateClause(em, qotherBank)
				.where(QOtherBankAllocation.otherBankAllocation.id.eq(otherBank.getId())
						.and(QOtherBankAllocation.otherBankAllocation.icmcId.eq(otherBank.getIcmcId())))
				.set(qotherBank.status, OtherStatus.ACCEPTED).set(qotherBank.updateTime, otherBank.getUpdateTime())
				.execute();
		return count;
	}

	private JPAQuery getFromQueryForCRAFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction);
		return jpaQuery;
	}

	@Override
	public BinTransaction getPendingBundleRequestForCRA(BinTransaction binTransaction) {
		JPAQuery jpaQuery = getFromQueryForCRAFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTransaction.getIcmcId())
				.and(QBinTransaction.binTransaction.binNumber.eq(binTransaction.getBinNumber())));
		BinTransaction btx = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return btx;
	}

	@Override
	public boolean updateBinTransactionForCRA(BinTransaction binTransaction) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qBinTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(binTransaction.getIcmcId())
						.and(QBinTransaction.binTransaction.binNumber.eq(binTransaction.getBinNumber())))
				.set(qBinTransaction.pendingBundleRequest, binTransaction.getPendingBundleRequest()).execute();
		return count > 0 ? true : false;
	}

	private JPAQuery getFromQueryForCRAFromCRAALllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRAAllocation.cRAAllocation);
		return jpaQuery;
	}

	@Override
	public CRAAllocation getBinForCRA(CRAAllocation cra) {
		JPAQuery jpaQuery = getFromQueryForCRAFromCRAALllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(cra.getIcmcId())
				.and(QCRAAllocation.cRAAllocation.binNumber.eq(cra.getBinNumber())));
		CRAAllocation craAllocation = jpaQuery.singleResult(QCRAAllocation.cRAAllocation);
		return craAllocation;
	}

	@Override
	public boolean updateBundleInCRAAllocation(CRAAllocation craAllocation) {
		QCRAAllocation qCraAllocation = QCRAAllocation.cRAAllocation;
		long count = new JPAUpdateClause(em, qCraAllocation)
				.where(QCRAAllocation.cRAAllocation.icmcId.eq(craAllocation.getIcmcId())
						.and(QCRAAllocation.cRAAllocation.binNumber.eq(craAllocation.getBinNumber())))
				.set(qCraAllocation.bundle, craAllocation.getBundle()).execute();
		return count > 0 ? true : false;
	}

	@Override
	public List<BinTransaction> getBinForCRAPayment(BinTransaction binTxn) {
		JPAQuery jpaQuery = getFromQueryForCRAFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTxn.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(binTxn.getDenomination())
						.and(QBinTransaction.binTransaction.binType.eq(binTxn.getBinType()))));
		List<BinTransaction> binTxnList = jpaQuery.list(QBinTransaction.binTransaction);
		return binTxnList;
	}

	@Override
	public boolean ForwardCRAPayment(ForwardBundleForCRAPayment forwardBundlePayment) {
		em.persist(forwardBundlePayment);
		return true;
	}

	@Override
	public List<Tuple> craRequestSummary(long id) {
		JPAQuery jpaQuery = getFromQueryForCRAAllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.craId.eq(id)
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.REQUESTED)));
		jpaQuery.orderBy(QCRAAllocation.cRAAllocation.denomination.desc());
		jpaQuery.groupBy(QCRAAllocation.cRAAllocation.denomination, QCRAAllocation.cRAAllocation.craId,
				QCRAAllocation.cRAAllocation.id, QCRAAllocation.cRAAllocation.currencyType);
		List<Tuple> list = jpaQuery.list(QCRAAllocation.cRAAllocation.denomination,
				QCRAAllocation.cRAAllocation.insertTime.min().as("insertTime"),
				QCRAAllocation.cRAAllocation.bundle.sum(), QCRAAllocation.cRAAllocation.currencyType,
				QCRAAllocation.cRAAllocation.craId, QCRAAllocation.cRAAllocation.id,
				QCRAAllocation.cRAAllocation.pendingRequestedBundle.sum());
		return list;
	}

	@Override
	public boolean insertInCRAPayment(List<CRAAllocation> CRAPaymentRequestList) {
		for (CRAAllocation craPayment : CRAPaymentRequestList) {
			em.persist(craPayment);
		}
		return true;
	}

	private JPAQuery getFromQueryForSolId() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCRA.cRA);
		return jpaQuery;
	}

	@Override
	public List<CRA> getRecordFromCRA(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSolId();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId).and(QCRA.cRA.status.eq(OtherStatus.REQUESTED)));
		List<CRA> craList = jpaQuery.list(QCRA.cRA);
		return craList;
	}

	@Override
	public List<Tuple> getSoiledSummary(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.SOILED))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				/* .and(QBinTransaction.binTransaction.binNumber.ne("BOX")) */
				.and(QBinTransaction.binTransaction.binNumber.notLike("BOX:%"))
				.and(QBinTransaction.binTransaction.maxCapacity.gt(0))
				.and(QBinTransaction.binTransaction.binCategoryType.ne(BinCategoryType.BAG)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination);
		List<Tuple> list = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum(),
				QBinTransaction.binTransaction.insertTime.min().as("insertTime"));
		return list;
	}

	private JPAQuery getFromQueryForBundleFromBinTxn() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction).orderBy(QBinTransaction.binTransaction.insertTime.asc())
				.orderBy(QBinTransaction.binTransaction.id.asc());
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getBundleFromBinTxnToCompare(BigInteger icmcId, Integer denomination,CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForBundleFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.denomination.eq(denomination))
				.and(QBinTransaction.binTransaction.binType.eq(currencyType))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		List<BinTransaction> bundles = jpaQuery.list(QBinTransaction.binTransaction);
		return bundles;
	}

	@Override
	public boolean insertSoiledBoxInBinTx(BinTransaction binTx) {
		em.persist(binTx);
		return true;
	}

	@Override
	public boolean updateCRAAndCRAAllocation(CRA cra) {
		cra = em.find(CRA.class, cra.getId());
		if (cra != null) {
			cra.setStatus(OtherStatus.CANCELLED);
			this.cancelationSetCRACraAllocation(cra);
			em.merge(cra);
			return true;
		} else {
			return false;
		}

	}

	private void cancelationSetCRACraAllocation(CRA cra) {
		List<CRAAllocation> craPaymentRequestListAll = new ArrayList<>();
		for (CRAAllocation craAlo : cra.getCraAllocations()) {
			craAlo.setStatus(OtherStatus.CANCELLED);
			craPaymentRequestListAll.add(craAlo);
		}
		cra.setCraAllocations(craPaymentRequestListAll);
	}

	@Override
	public void updateEditOtherBankAndOtherBankAllocation(BigInteger icmcId, long id) {

		OtherBank otherBank = em.find(OtherBank.class, id);
		otherBank.setStatus(OtherStatus.CANCELLED);
		this.cancelationSetOtherBankAllocation(otherBank);
		em.merge(otherBank);
	}

	private void cancelationSetOtherBankAllocation(OtherBank otherBank) {
		List<OtherBankAllocation> craPaymentRequestListAll = new ArrayList<>();
		for (OtherBankAllocation otherBankAllo : otherBank.getOtherBankAllocations()) {
			otherBankAllo.setStatus(OtherStatus.CANCELLED);
			craPaymentRequestListAll.add(otherBankAllo);
		}
		otherBank.setOtherBankAllocations(craPaymentRequestListAll);
	}

	@Override
	public boolean updateBinTrasactionForCRA(List<BinTransaction> binTxn) {
		for (BinTransaction btx : binTxn) {
			em.merge(btx);
		}
		return true;
	}

	public CRAAllocation findCraAllocation(CRAAllocation craAllocation) {
		CRAAllocation craAllocationDb = em.find(CRAAllocation.class, craAllocation.getId());
		return craAllocationDb;
	}

	@Override
	public boolean updateCRAAllocationForCRA(List<CRAAllocation> craAllocationList) {

		boolean isAnyPendingRequest = false;
		CRAAllocation craAllocationMaster = null;

		for (CRAAllocation craAllocation : craAllocationList) {
			craAllocationMaster = craAllocation;

			if (craAllocation.getStatus() == OtherStatus.REQUESTED) {
				isAnyPendingRequest = true;
			}
			em.merge(craAllocation);
		}

		if (!isAnyPendingRequest && craAllocationMaster != null) {
			CRA cra = em.find(CRA.class, craAllocationMaster.getCraId());
			if (cra != null) {
				cra.setStatus(OtherStatus.PROCESSED);
				cra.setUpdateBy(craAllocationMaster.getUpdateBy());
				cra.setUpdateTime(craAllocationMaster.getUpdateTime());
				em.merge(cra);

			}
		}
		return true;
	}

	@Override
	public boolean createSoiledAfterBoxCreation(SoiledRemittanceAllocation soiled) {
		em.persist(soiled);
		return true;
	}

	@Override
	public List<BinTransaction> getBoxListForSoiled(SoiledRemittanceAllocation soiledAllocation, CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForCRAFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(soiledAllocation.getIcmcId())
				.and(QBinTransaction.binTransaction.denomination.eq(soiledAllocation.getDenomination())
						.and(QBinTransaction.binTransaction.binNumber.like("BOX:%"))
						.and(QBinTransaction.binTransaction.active.eq(0))
						.and(QBinTransaction.binTransaction.id.eq(soiledAllocation.getId()))
						/*
						 * .and(QBinTransaction.binTransaction.binCategoryType.
						 * eq(BinCategoryType.BOX))
						 */
						.and(QBinTransaction.binTransaction.binType.eq(type))));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public BinTransaction getBoxRecordForCashReleassed(BinTransaction binTxn) {
		JPAQuery jpaQuery = getFromQueryForCashReleasedFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(binTxn.getIcmcId())
				.and(QBinTransaction.binTransaction.active.eq(1))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(binTxn.getBinCategoryType())
						.and(QBinTransaction.binTransaction.denomination.eq(binTxn.getDenomination())
								.and(QBinTransaction.binTransaction.binType.eq(binTxn.getBinType())))));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public List<CRAAllocation> craPaymentDetailForAccept(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCRAAllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.PROCESSED))
				.and(QCRAAllocation.cRAAllocation.binNumber.isNotNull()));
		List<CRAAllocation> craAllocationList = jpaQuery.list(QCRAAllocation.cRAAllocation);
		return craAllocationList;
	}

	@Override
	public boolean updateCRAAllocationStatus(CRAAllocation craAllocation) {
		// em.merge(indent);
		QCRAAllocation qCraAlloation = QCRAAllocation.cRAAllocation;
		Long count = new JPAUpdateClause(em, qCraAlloation)
				.where(QCRAAllocation.cRAAllocation.icmcId.eq(craAllocation.getIcmcId())
						.and(QCRAAllocation.cRAAllocation.id.eq(craAllocation.getId())))
				.set(qCraAlloation.status, OtherStatus.ACCEPTED)
				.set(qCraAlloation.updateTime, craAllocation.getUpdateTime()).execute();
		return count > 0 ? true : false;
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
				QBinTransaction.binTransaction.icmcId.eq(icmcId).and(QBinTransaction.binTransaction.binNumber.eq(bin))
						.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY)));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public CRAAllocation getCRAAllocationDataById(long id, BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForCRAFromCRAALllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.id.eq(id).and(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)));
		CRAAllocation craAllocation = jpaQuery.singleResult(QCRAAllocation.cRAAllocation);
		return craAllocation;
	}

	@Override
	public int updateBinTxnForSoiledBox(BinTransaction binTxn) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPADeleteClause(em, qBinTransaction).where(
				qBinTransaction.id.eq(binTxn.getId()).and(QBinTransaction.binTransaction.icmcId.eq(binTxn.getIcmcId()))
						.and(QBinTransaction.binTransaction.binCategoryType.eq(binTxn.getBinCategoryType())))
				.execute();
		return (int) count;
	}

	@Override
	public List<CRAAllocation> craPaymentDetails(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForCRAAllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId).and(QCRAAllocation.cRAAllocation.craId.eq(id))
				.and(QCRAAllocation.cRAAllocation.binNumber.ne("null"))
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.ACCEPTED)));
		List<CRAAllocation> craAllocation = jpaQuery.list(QCRAAllocation.cRAAllocation);
		return craAllocation;
	}

	@Override
	public List<ProcessBundleForCRAPayment> forwardedCraPaymentDetails(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleForCraPayment();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.craId.eq(id)
						.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.PROCESSED))));
		List<ProcessBundleForCRAPayment> processBundleForCraPayment = jpaQuery
				.list(QProcessBundleForCRAPayment.processBundleForCRAPayment);
		return processBundleForCraPayment;
	}

	@Override
	public List<CRA> solIdForCRAPayment(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId).and(QCRA.cRA.status.eq(OtherStatus.ACCEPTED))
				.and(QCRA.cRA.insertTime.between(sDate, eDate)));
		List<CRA> solIdFromCRA = jpaQuery.list(QCRA.cRA);
		return solIdFromCRA;
	}

	@Override
	public List<DiversionORV> diversionListForRbiOrderNo(BigInteger icmcId) {
		// TODO Auto-generated method stub
		JPAQuery jpaQuery = getFromQueryForDiversionORV();
		jpaQuery.where(QDiversionORV.diversionORV.icmcId.eq(icmcId)
				.and(QDiversionORV.diversionORV.otherStatus.eq(OtherStatus.ACCEPTED)));
		List<DiversionORV> bankNameList = jpaQuery.list(QDiversionORV.diversionORV);
		return bankNameList;
	}

	@Override
	public List<DiversionORVAllocation> dorvPaymentDetails(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForDiversionORVAllocation();
		jpaQuery.where(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(icmcId)
				.and(QDiversionORVAllocation.diversionORVAllocation.diversionOrvId.eq(id)));
		List<DiversionORVAllocation> diversionAllocationList = jpaQuery
				.list(QDiversionORVAllocation.diversionORVAllocation);
		return diversionAllocationList;
	}

	private JPAQuery getFromQueryForOtherBank() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QOtherBank.otherBank);
		return jpaQuery;
	}

	@Override
	public List<OtherBank> bankNameFromOtherBank(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForOtherBank();
		jpaQuery.where(
				QOtherBank.otherBank.icmcId.eq(icmcId).and(QOtherBank.otherBank.status.eq(OtherStatus.ACCEPTED)));
		List<OtherBank> otherBankList = jpaQuery.list(QOtherBank.otherBank);
		return otherBankList;
	}

	@Override
	public List<OtherBankAllocation> otherBankPaymentDetails(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForOtherBankAllocation();
		jpaQuery.where(QOtherBankAllocation.otherBankAllocation.icmcId.eq(icmcId)
				.and(QOtherBankAllocation.otherBankAllocation.otherBankId.eq(id)));
		List<OtherBankAllocation> otherBankDetailList = jpaQuery.list(QOtherBankAllocation.otherBankAllocation);
		return otherBankDetailList;
	}

	@Override
	public long updateOtherBankForPayment(BigInteger icmcId, long id) {
		QOtherBank qOtherBank = QOtherBank.otherBank;
		long count = new JPAUpdateClause(em, qOtherBank)
				.where(QOtherBank.otherBank.icmcId.eq(icmcId).and(QOtherBank.otherBank.id.eq(id)))
				.set(QOtherBank.otherBank.status, OtherStatus.RELEASED).execute();
		return count;
	}

	@Override
	public long updateOtherBankAllocationForPayment(BigInteger icmcId, long id) {
		QOtherBankAllocation qOtherBankAllocation = QOtherBankAllocation.otherBankAllocation;
		long count = new JPAUpdateClause(em, qOtherBankAllocation)
				.where(QOtherBankAllocation.otherBankAllocation.otherBankId.eq(id)
						.and(QOtherBankAllocation.otherBankAllocation.icmcId.eq(icmcId)))
				.set(QOtherBankAllocation.otherBankAllocation.status, OtherStatus.RELEASED)
				.set(QOtherBankAllocation.otherBankAllocation.pendingBundle, new BigDecimal(0)).execute();
		return count;
	}

	@Override
	public long updateCRAForPayment(BigInteger icmcId, long id) {
		QCRA qCRA = QCRA.cRA;
		long count = new JPAUpdateClause(em, qCRA).where(QCRA.cRA.icmcId.eq(icmcId).and(QCRA.cRA.id.eq(id)))
				.set(QCRA.cRA.status, OtherStatus.RELEASED).execute();
		return count;
	}

	@Override
	public long updateCRAAllocationForPayment(BigInteger icmcId, long id) {
		QCRAAllocation qCRAAllocation = QCRAAllocation.cRAAllocation;
		long count = new JPAUpdateClause(em, qCRAAllocation)
				.where(QCRAAllocation.cRAAllocation.craId.eq(id)
						.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.ACCEPTED))
						.and(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)))
				.set(QCRAAllocation.cRAAllocation.status, OtherStatus.RELEASED)
				.set(QCRAAllocation.cRAAllocation.pendingRequestedBundle, new BigDecimal(0)).execute();
		return count;
	}

	@Override
	public long updateDiversionForPayment(BigInteger icmcId, long id) {
		QDiversionORV qdiversion = QDiversionORV.diversionORV;
		long count = new JPAUpdateClause(em, qdiversion)
				.where(QDiversionORV.diversionORV.icmcId.eq(icmcId).and(QDiversionORV.diversionORV.id.eq(id)))
				.set(QDiversionORV.diversionORV.otherStatus, OtherStatus.RELEASED).execute();
		return count;
	}

	@Override
	public long updateDiversionAllocationForPayment(BigInteger icmcId, long id) {
		QDiversionORVAllocation qDiversionOrvAllocation = QDiversionORVAllocation.diversionORVAllocation;
		long count = new JPAUpdateClause(em, qDiversionOrvAllocation)
				.where(QDiversionORVAllocation.diversionORVAllocation.diversionOrvId.eq(id)
						.and(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(icmcId)))
				.set(QDiversionORVAllocation.diversionORVAllocation.status, OtherStatus.RELEASED)
				.set(QDiversionORVAllocation.diversionORVAllocation.pendingBundle, new BigDecimal(0)).execute();
		return count;
	}

	private JPAQuery getFromQueryForSASRequestFromBinTxnForCoins() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBinTransaction.binTransaction).orderBy(QBinTransaction.binTransaction.insertTime.desc());
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getCoinsListForSas(SASAllocation sas) {
		JPAQuery jpaQuery = getFromQueryForSASRequestFromBinTxnForCoins();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(sas.getIcmcId())
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.COINS))
				.and(QBinTransaction.binTransaction.denomination.eq(sas.getDenomination()))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.FRESH)));
		List<BinTransaction> txnList = jpaQuery.list(QBinTransaction.binTransaction);
		return txnList;
	}

	@Override
	public long updateSoiledForPayment(BigInteger icmcId, long id) {
		QSoiledRemittance qSoiledRemittance = QSoiledRemittance.soiledRemittance;
		long count = new JPAUpdateClause(em, qSoiledRemittance)
				.where(QSoiledRemittance.soiledRemittance.icmcId.eq(icmcId)
						.and(QSoiledRemittance.soiledRemittance.id.eq(id)))
				.set(QSoiledRemittance.soiledRemittance.status, OtherStatus.RELEASED).execute();
		return count;
	}

	@Override
	public long updateSoiledAllocationForPayment(BigInteger icmcId, long id) {
		QSoiledRemittanceAllocation qSoiledRemittanceAllocation = QSoiledRemittanceAllocation.soiledRemittanceAllocation;
		long count = new JPAUpdateClause(em, qSoiledRemittanceAllocation)
				.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.soiledRemittanceId.eq(id)
						.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)))
				.set(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status, OtherStatus.RELEASED)
				.set(QSoiledRemittanceAllocation.soiledRemittanceAllocation.pendingBundle, new BigDecimal(0)).execute();
		return count;
	}

	@Override
	public void updateInsertSoiledAndSoiledAllocation(BigInteger icmcId, long id) {
		SoiledRemittance soiledData = em.find(SoiledRemittance.class, id);
		soiledData.setStatus(OtherStatus.CANCELLED);
		this.cancelationSetSoiledAllocation(soiledData);
		em.merge(soiledData);

	}

	private void cancelationSetSoiledAllocation(SoiledRemittance soiledData) {
		List<SoiledRemittanceAllocation> soiledList = new ArrayList<>();
		for (SoiledRemittanceAllocation soiledAlo : soiledData.getRemittanceAllocations()) {
			soiledAlo.setStatus(OtherStatus.CANCELLED);
			soiledList.add(soiledAlo);
			this.cancelationUpdateBinTransaction(soiledAlo.getBinTxnId());
		}
		soiledData.setRemittanceAllocations(soiledList);
	}

	private long cancelationUpdateBinTransaction(long btnxId) {
		/*
		 * BinTransaction btxn=new BinTransaction();
		 * btxn.setPendingBundleRequest(null); btxn.setActive(0);
		 */
		QBinTransaction qbinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qbinTransaction).where(QBinTransaction.binTransaction.id.eq(btnxId))
				.set(QBinTransaction.binTransaction.active, 0)
				.set(QBinTransaction.binTransaction.pendingBundleRequest, new BigDecimal(0)).execute();
		return count;

	}

	private JPAQuery getFromQueryForCoinsSequence() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QCoinsSequence.coinsSequence);
		return jpaQuery;
	}

	@Override
	public CoinsSequence getCoinsSequenceForDeduction(BigInteger icmcId, int denomination) {
		JPAQuery jpaQuery = getFromQueryForCoinsSequence();
		jpaQuery.where(QCoinsSequence.coinsSequence.icmcId.eq(icmcId)
				.and(QCoinsSequence.coinsSequence.denomination.eq(denomination)));
		CoinsSequence coinsSequence = jpaQuery.singleResult(QCoinsSequence.coinsSequence);
		return coinsSequence;
	}

	@Override
	public BinTransaction getBinRecordForAcceptInVault(BinTransaction txn) {
		JPAQuery jpaQuery = getFromQueryForCashReleasedFromBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(txn.getIcmcId())
				.and(QBinTransaction.binTransaction.binNumber.eq(txn.getBinNumber())
						.and(QBinTransaction.binTransaction.denomination.eq(txn.getDenomination()))));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public long updateOtherBankStatus(OtherBank otherBank) {
		QOtherBank qOtherBank = QOtherBank.otherBank;
		long count = new JPAUpdateClause(em, qOtherBank)
				.where(QOtherBank.otherBank.id.eq(otherBank.getId())
						.and(QOtherBank.otherBank.icmcId.eq(otherBank.getIcmcId())))
				.set(qOtherBank.status, OtherStatus.ACCEPTED).set(qOtherBank.updateTime, otherBank.getUpdateTime())
				.execute();
		return count;
	}

	@Override
	public long updateDorvStatus(DiversionORV dorv) {
		QDiversionORV qdorv = QDiversionORV.diversionORV;
		long count = new JPAUpdateClause(em, qdorv)
				.where(QDiversionORV.diversionORV.id.eq(dorv.getId())
						.and(QDiversionORV.diversionORV.icmcId.eq(dorv.getIcmcId())))
				.set(qdorv.otherStatus, OtherStatus.ACCEPTED).set(qdorv.updateTime, dorv.getUpdateTime()).execute();
		return count;
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
	public long updateCoinsSequence(CoinsSequence coinsSequence) {
		QCoinsSequence qCoinsSequence = QCoinsSequence.coinsSequence;
		long count = new JPAUpdateClause(em, qCoinsSequence)
				.where(QCoinsSequence.coinsSequence.icmcId.eq(coinsSequence.getIcmcId())
						.and(QCoinsSequence.coinsSequence.denomination.eq(coinsSequence.getDenomination())))
				.set(qCoinsSequence.sequence, coinsSequence.getSequence()).execute();
		return count;
	}

	private JPAQuery getFromQueryForSoiledRemmitance() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSoiledRemittance.soiledRemittance);
		return jpaQuery;
	}

	@Override
	public List<SoiledRemittance> getRemittanceOrderNo(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSoiledRemmitance();
		jpaQuery.where(QSoiledRemittance.soiledRemittance.icmcId.eq(icmcId)
				.and(QSoiledRemittance.soiledRemittance.status.eq(OtherStatus.ACCEPTED)));
		List<SoiledRemittance> remittanceOrderNo = jpaQuery.list(QSoiledRemittance.soiledRemittance);
		return remittanceOrderNo;
	}

	private JPAQuery getFromQueryForSoiledRemmitanceAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return jpaQuery;
	}

	@Override
	public List<SoiledRemittanceAllocation> soiledRemittancePaymentDetails(BigInteger icmcId, long id) {
		JPAQuery jpaQuery = getFromQueryForSoiledRemmitanceAllocation();
		jpaQuery.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.soiledRemittanceId.eq(id)));
		List<SoiledRemittanceAllocation> soiledPaymentDetails = jpaQuery
				.list(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return soiledPaymentDetails;
	}

	@Override
	public long updateCRAOtherStatus(CRA cra) {
		QCRA qCRA = QCRA.cRA;
		long count = new JPAUpdateClause(em, qCRA)
				.where(QCRA.cRA.icmcId.eq(cra.getIcmcId()).and(QCRA.cRA.id.eq(cra.getId())))
				.set(QCRA.cRA.status, OtherStatus.ACCEPTED).set(qCRA.updateTime, cra.getUpdateTime()).execute();
		return count;
	}

	@Override
	public List<Tuple> getBranchOutRecordFromSAS(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.insertTime.between(sDate, eDate)));
		// List<Sas> sasList = jpaQuery.list(QSas.sas);
		List<Tuple> tupleList = jpaQuery.list(QSas.sas.solID, QSas.sas.srNo, QSas.sas.branch, QSas.sas.totalValue,
				QSas.sas.insertTime, QSas.sas.totalValueOfCoinsRs10, QSas.sas.totalValueOfCoinsRs5,
				QSas.sas.totalValueOfCoinsRs2, QSas.sas.totalValueOfCoinsRs1,
				QSas.sas.totalValueOfNotesRs1000F.add(QSas.sas.totalValueOfNotesRs1000I)
						.add(QSas.sas.totalValueOfNotesRs1000A),
				QSas.sas.totalValueOfNotesRs500A.add(QSas.sas.totalValueOfNotesRs500F)
						.add(QSas.sas.totalValueOfNotesRs500I),
				QSas.sas.totalValueOfNotesRs100A.add(QSas.sas.totalValueOfNotesRs100I)
						.add(QSas.sas.totalValueOfNotesRs100F),
				QSas.sas.totalValueOfNotesRs50F.add(QSas.sas.totalValueOfNotesRs50I),
				QSas.sas.totalValueOfNotesRs20F.add(QSas.sas.totalValueOfNotesRs20I),
				QSas.sas.totalValueOfNotesRs10F.add(QSas.sas.totalValueOfNotesRs10I),
				QSas.sas.totalValueOfNotesRs5F.add(QSas.sas.totalValueOfNotesRs5I),
				QSas.sas.totalValueOfNotesRs1F.add(QSas.sas.totalValueOfNotesRs1I),
				QSas.sas.totalValueOfNotesRs2F.add(QSas.sas.totalValueOfNotesRs2I), QSas.sas.totalValueOfNotesRs2000A
						.add(QSas.sas.totalValueOfNotesRs2000F).add(QSas.sas.totalValueOfNotesRs2000I));
		return tupleList;
	}

	@Override
	public List<Sas> getRecordFromSAS(long id) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.id.eq(id));
		List<Sas> tupleList = jpaQuery.list(QSas.sas);
		return tupleList;
	}

	@Override
	public long updateSASStatus(BigInteger icmcId, long id) {
		QSas qSas = QSas.sas;
		long count = new JPAUpdateClause(em, qSas).where(QSas.sas.id.eq(id).and(QSas.sas.icmcId.eq(icmcId)))
				.set(QSas.sas.status, 1).set(QSas.sas.updateTime, Calendar.getInstance()).execute();
		return count;
	}

	@Override
	public void updateSASStatusForSASFile(BigInteger icmcId, int status) {
		QSas qSas = QSas.sas;
		new JPAUpdateClause(em, qSas).where(QSas.sas.status.eq(status).and(QSas.sas.icmcId.eq(icmcId)))
				.set(QSas.sas.status, 1).execute();

	}

	@Override
	public long updateSASForceHandoverStatus(BigInteger icmcId, long id) {
		QSas qSas = QSas.sas;
		long count = new JPAUpdateClause(em, qSas).where(QSas.sas.id.eq(id).and(QSas.sas.icmcId.eq(icmcId)))
				.set(QSas.sas.status, 2).execute();
		return count;
	}

	@Override
	public List<SASAllocation> getAllAcceptedFromSASAllocation(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED)));
		List<SASAllocation> statusListForAccepted = jpaQuery.list(QSASAllocation.sASAllocation);
		return statusListForAccepted;
	}

	@Override
	public List<SASAllocation> getAllTodayAcceptedFromSASAllocation(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSASAllocation.sASAllocation)
				.where(QSASAllocation.sASAllocation.status.eq(OtherStatus.ACCEPTED)
						.and(QSASAllocation.sASAllocation.insertTime.between(sDate, eDate)))
				.orderBy(QSASAllocation.sASAllocation.parentId.asc());
		List<SASAllocation> solIdForPaymenta = jpaQuery.list(QSASAllocation.sASAllocation);
		return solIdForPaymenta;
	}

	private JPAQuery getFromQueryForICMCName() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QICMC.iCMC);
		return jpaQuery;
	}

	@Override
	public String getICMCName(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForICMCName();
		jpaQuery.where(QICMC.iCMC.id.eq(icmcId.longValue()));
		String icmcName = jpaQuery.singleResult(QICMC.iCMC.name);
		return icmcName;
	}

	@Override
	public Long cancelSAS(User user, Sas sas) {
		Calendar now = Calendar.getInstance();
		QSas qSas = QSas.sas;
		Long count = new JPAUpdateClause(em, qSas)
				.where(QSas.sas.icmcId.eq(user.getIcmcId()).and(QSas.sas.id.eq(sas.getId()))).set(qSas.status, 4)
				.set(qSas.updateBy, user.getId()).set(qSas.updateTime, now).execute();
		return count;
	}

	@Override
	public DiversionORV getDiversionRecordForCancellation(User user, Long id) {
		JPAQuery jpaQuery = getFromQueryForDiversionORV();
		jpaQuery.where(
				QDiversionORV.diversionORV.icmcId.eq(user.getIcmcId()).and(QDiversionORV.diversionORV.id.eq(id)));
		DiversionORV dorv = jpaQuery.singleResult(QDiversionORV.diversionORV);
		return dorv;
	}

	@Override
	public Long updateDorvForCancellation(User user, Long id) {
		Calendar now = Calendar.getInstance();
		QDiversionORV qdorv = QDiversionORV.diversionORV;
		Long count = new JPAUpdateClause(em, qdorv)
				.where(QDiversionORV.diversionORV.icmcId.eq(user.getIcmcId()).and(QDiversionORV.diversionORV.id.eq(id)))
				.set(qdorv.otherStatus, OtherStatus.CANCELLED).set(qdorv.updateBy, user.getId())
				.set(qdorv.updateTime, now).execute();
		return count;
	}

	@Override
	public void updateDorvAllocationForCancellation(User user, Long id) {
		Calendar now = Calendar.getInstance();
		QDiversionORVAllocation qdorvAllo = QDiversionORVAllocation.diversionORVAllocation;
		new JPAUpdateClause(em, qdorvAllo)
				.where(QDiversionORVAllocation.diversionORVAllocation.icmcId.eq(user.getIcmcId())
						.and(QDiversionORVAllocation.diversionORVAllocation.diversionOrvId.eq(id)))
				.set(qdorvAllo.status, OtherStatus.CANCELLED).set(qdorvAllo.updateBy, user.getId())
				.set(qdorvAllo.updateTime, now).execute();
	}

	@Override
	public OtherBank getOtherBankRecordForCancellation(User user, Long id) {
		JPAQuery jpaQuery = getFromQueryForOtherBank();
		jpaQuery.where(QOtherBank.otherBank.icmcId.eq(user.getIcmcId()).and(QOtherBank.otherBank.id.eq(id)));
		OtherBank otherBank = jpaQuery.singleResult(QOtherBank.otherBank);
		return otherBank;
	}

	@Override
	public Long updateOtherBankForCancellation(User user, Long id) {
		Calendar now = Calendar.getInstance();
		QOtherBank qotherBank = QOtherBank.otherBank;
		Long count = new JPAUpdateClause(em, qotherBank)
				.where(QOtherBank.otherBank.icmcId.eq(user.getIcmcId()).and(QOtherBank.otherBank.id.eq(id)))
				.set(qotherBank.status, OtherStatus.CANCELLED).set(qotherBank.updateBy, user.getId())
				.set(qotherBank.updateTime, now).execute();
		return count;
	}

	@Override
	public void updateOtherBankAllocationForCancellation(User user, Long id) {
		Calendar now = Calendar.getInstance();
		QOtherBankAllocation qotherBankAllo = QOtherBankAllocation.otherBankAllocation;
		new JPAUpdateClause(em, qotherBankAllo)
				.where(QOtherBankAllocation.otherBankAllocation.icmcId.eq(user.getIcmcId())
						.and(QOtherBankAllocation.otherBankAllocation.otherBankId.eq(id)))
				.set(qotherBankAllo.status, OtherStatus.CANCELLED).set(qotherBankAllo.updateBy, user.getId())
				.set(qotherBankAllo.updateTime, now).execute();
	}

	@Override
	public CRA getCRARecordForCancellation(User user, Long id) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRA.cRA.icmcId.eq(user.getIcmcId()).and(QCRA.cRA.id.eq(id)));
		return jpaQuery.singleResult(QCRA.cRA);
	}

	@Override
	public Long updateCRAafterCancellation(User user, Long id) {
		Calendar now = Calendar.getInstance();
		QCRA qcra = QCRA.cRA;
		Long count = new JPAUpdateClause(em, qcra).where(QCRA.cRA.icmcId.eq(user.getIcmcId()).and(QCRA.cRA.id.eq(id)))
				.set(qcra.status, OtherStatus.CANCELLED).set(qcra.updateBy, user.getId()).set(qcra.updateTime, now)
				.execute();
		return count;
	}

	@Override
	public void updateCRAAllocationAfterCancellation(User user, Long id) {
		// Calendar now = Calendar.getInstance();
		QCRAAllocation qcraAllo = QCRAAllocation.cRAAllocation;
		new JPAUpdateClause(em, qcraAllo)
				.where(QCRAAllocation.cRAAllocation.icmcId.eq(user.getIcmcId())
						.and(QCRAAllocation.cRAAllocation.craId.eq(id)))
				.set(qcraAllo.status, OtherStatus.CANCELLED).execute();
		// .set(qcraAllo.pendingRequestedBundle, BigDecimal.ZERO)
	}

	private JPAQuery getFromQueryForMachineAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QMachineAllocation.machineAllocation);
		return jpaQuery;
	}

	@Override
	public List<Tuple> machineInputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination) {
		JPAQuery jpaQuery = getFromQueryForMachineAllocation();
		jpaQuery.where(QMachineAllocation.machineAllocation.icmcId.eq(icmcId)
				.and(QMachineAllocation.machineAllocation.status.eq(OtherStatus.REQUESTED))
				.and(QMachineAllocation.machineAllocation.denomination.eq(denomination))
				.and(QMachineAllocation.machineAllocation.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QMachineAllocation.machineAllocation.denomination);
		List<Tuple> inputList = jpaQuery.list(QMachineAllocation.machineAllocation.denomination,
				QMachineAllocation.machineAllocation.issuedBundle.sum());
		return inputList;
	}

	private JPAQuery getFromQueryForProcess() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QProcess.process);
		return jpaQuery;
	}

	@Override
	public List<Tuple> machineOutputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination,
			CurrencyType type) {
		JPAQuery jpaQuery = getFromQueryForProcess();
		jpaQuery.where(QProcess.process.icmcId.eq(icmcId).and(QProcess.process.currencyType.eq(type))
				.and(QProcess.process.denomination.eq(denomination))
				.and(QProcess.process.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QProcess.process.denomination);
		List<Tuple> inputList = jpaQuery.list(QProcess.process.denomination, QProcess.process.bundle.sum());
		return inputList;
	}

	@Override
	public List<SoiledRemittanceAllocation> getAcceptedListForSoiledRemitAlloc(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSoiledRemittanceAllocation();
		jpaQuery.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.REQUESTED)));
		List<SoiledRemittanceAllocation> statusListForAccepted = jpaQuery
				.list(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return statusListForAccepted;
	}

	private JPAQuery getFromQueryForSoiledRemittanceAllocation() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return jpaQuery;
	}

	@Override
	public List<BinTransaction> getPreparedSoiledBoxes(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binNumber.like("BOX:%"))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(BinCategoryType.BOX))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.SOILED))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0)));
		List<BinTransaction> preparedBoxList = jpaQuery.list(QBinTransaction.binTransaction);
		return preparedBoxList;
	}

	@Override
	public List<BinTransaction> getPreparedInActiveSoiledBoxes(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binNumber.like("BOX:%"))
				.and(QBinTransaction.binTransaction.binCategoryType.eq(BinCategoryType.BOX))
				.and(QBinTransaction.binTransaction.binType.eq(CurrencyType.SOILED)
				.or(QBinTransaction.binTransaction.binType.eq(CurrencyType.MUTILATED)))
				.and(QBinTransaction.binTransaction.cashType.eq(CashType.NOTES))
				.and(QBinTransaction.binTransaction.active.eq(0))
				.and(QBinTransaction.binTransaction.receiveBundle.gt(0)));
		List<BinTransaction> preparedBoxList = jpaQuery.list(QBinTransaction.binTransaction);
		return preparedBoxList;
	}

	@Override
	public List<ProcessBundleForCRAPayment> processBundleForCRAPayment(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleForCraPayment();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.PROCESSED)));
		List<ProcessBundleForCRAPayment> forwardedbundleList = jpaQuery
				.list(QProcessBundleForCRAPayment.processBundleForCRAPayment);
		return forwardedbundleList;
	}

	@Override
	public long updateProcessbundleForCRAPayment(BigInteger icmcId, long id) {
		QProcessBundleForCRAPayment qprocessBundleForCraPayment = QProcessBundleForCRAPayment.processBundleForCRAPayment;
		long count = new JPAUpdateClause(em, qprocessBundleForCraPayment)
				.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)
						.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.craId.eq(id)))
				.set(QProcessBundleForCRAPayment.processBundleForCRAPayment.status, OtherStatus.RELEASED)
				.set(QProcessBundleForCRAPayment.processBundleForCRAPayment.pendingRequestedBundle, new BigDecimal(0))
				.execute();
		return count;
	}

	@Override
	public long updateSasAllocationForCancelBranchPayment(BigInteger icmcId, long id) {
		QSASAllocation qSasAllocation = QSASAllocation.sASAllocation;
		long count = new JPAUpdateClause(em, qSasAllocation)
				.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId).and(QSASAllocation.sASAllocation.id.eq(id)))
				.set(qSasAllocation.status, OtherStatus.CANCELLED).execute();
		return count;

	}

	@Override
	public long updateSasForCancelBranchPayment(BigInteger icmcId, long id) {
		QSas qSas = QSas.sas;
		long count = new JPAUpdateClause(em, qSas).where(QSas.sas.id.eq(id).and(QSas.sas.icmcId.eq(icmcId)))
				.set(qSas.status, 4).execute();
		return count;
	}

	@Override
	public long updateBinTransactionCancelBranchPayment(BigInteger icmcId, BinTransaction binTransaction) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qBinTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(icmcId).and(QBinTransaction.binTransaction.binType
						.eq(binTransaction.getBinType()).and(QBinTransaction.binTransaction.cashType
								.eq(binTransaction.getCashType())
								.and(QBinTransaction.binTransaction.denomination.eq(binTransaction.getDenomination())
										.and(QBinTransaction.binTransaction.binNumber
												.eq(binTransaction.getBinNumber()))))))
				.set(QBinTransaction.binTransaction.pendingBundleRequest, binTransaction.getPendingBundleRequest())
				.execute();
		return count;
	}

	@Override
	public BinTransaction getPendingBundleFromDB(BigInteger icmcId, BinTransaction binTxn) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.cashType.eq(binTxn.getCashType())
						.and(QBinTransaction.binTransaction.binType
								.eq(binTxn.getBinType()).and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
								.and(QBinTransaction.binTransaction.denomination.eq(binTxn.getDenomination())
										.and(QBinTransaction.binTransaction.binNumber.eq(binTxn.getBinNumber()))))));
		BinTransaction pendingBundleFromDB = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return pendingBundleFromDB;
	}

	@Override
	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QIndent.indent);
		jpaQuery.where(
				QIndent.indent.icmcId.eq(icmcId)
						.and(QIndent.indent.status.eq(OtherStatus.ACCEPTED)
								.or(QIndent.indent.status.eq(OtherStatus.PROCESSED)))
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
	public boolean history(List<History> historyList) {
		for (History history : historyList) {
			em.persist(history);
		}
		return true;
	}

	@Override
	public List<Tuple> getBranchPaymentTotal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromSASForVoucher();
		jpaQuery.where((QSas.sas.insertTime.between(sDate, eDate)));
		List<Tuple> tupleList = jpaQuery.list(
				QSas.sas.totalValueOfNotesRs2000A.add(QSas.sas.totalValueOfNotesRs2000F)
						.add(QSas.sas.totalValueOfNotesRs2000I).add(QSas.sas.totalValueOfNotesRs2000U),
				QSas.sas.totalValueOfNotesRs1000F.add(QSas.sas.totalValueOfNotesRs1000I)
						.add(QSas.sas.totalValueOfNotesRs1000A),
				QSas.sas.totalValueOfNotesRs500A.add(QSas.sas.totalValueOfNotesRs500F)
						.add(QSas.sas.totalValueOfNotesRs500I).add(QSas.sas.totalValueOfNotesRs500U),
				QSas.sas.totalValueOfNotesRs200A.add(QSas.sas.totalValueOfNotesRs200F)
						.add(QSas.sas.totalValueOfNotesRs200I),
				QSas.sas.totalValueOfNotesRs100A.add(QSas.sas.totalValueOfNotesRs100I)
						.add(QSas.sas.totalValueOfNotesRs100F).add(QSas.sas.totalValueOfNotesRs100U),
				QSas.sas.totalValueOfNotesRs50F.add(QSas.sas.totalValueOfNotesRs50I)
						.add(QSas.sas.totalValueOfNotesRs50U),
				QSas.sas.totalValueOfNotesRs20F.add(QSas.sas.totalValueOfNotesRs20I)
						.add(QSas.sas.totalValueOfNotesRs20U),
				QSas.sas.totalValueOfNotesRs10F.add(QSas.sas.totalValueOfNotesRs10I)
						.add(QSas.sas.totalValueOfNotesRs10U),
				QSas.sas.totalValueOfNotesRs5F.add(QSas.sas.totalValueOfNotesRs5I).add(QSas.sas.totalValueOfNotesRs5U),
				QSas.sas.totalValueOfNotesRs1F.add(QSas.sas.totalValueOfNotesRs1I).add(QSas.sas.totalValueOfNotesRs1U),
				QSas.sas.totalValueOfNotesRs2F.add(QSas.sas.totalValueOfNotesRs2I).add(QSas.sas.totalValueOfNotesRs2U),
				QSas.sas.totalValueOfCoinsRs1, QSas.sas.totalValueOfCoinsRs2, QSas.sas.totalValueOfCoinsRs5,
				QSas.sas.totalValueOfCoinsRs10);
		return tupleList;

	}

	@Override
	public List<Tuple> getCraPaymentTotalProcessed(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRAAllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.PROCESSED))
				.and(QCRAAllocation.cRAAllocation.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QCRAAllocation.cRAAllocation.denomination);
		jpaQuery.orderBy(QCRAAllocation.cRAAllocation.denomination.desc());
		List<Tuple> craPaymentList = jpaQuery.list(QCRAAllocation.cRAAllocation.denomination,
				QCRAAllocation.cRAAllocation.bundle.sum());
		return craPaymentList;
	}

	@Override
	public List<Tuple> getCraPaymentTotalReleased(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForCRAAllocation();
		jpaQuery.where(QCRAAllocation.cRAAllocation.icmcId.eq(icmcId)
				.and(QCRAAllocation.cRAAllocation.status.eq(OtherStatus.RELEASED))
				.and(QCRAAllocation.cRAAllocation.insertTime.between(sDate, eDate)));
		jpaQuery.groupBy(QCRAAllocation.cRAAllocation.denomination);
		jpaQuery.orderBy(QCRAAllocation.cRAAllocation.denomination.desc());
		List<Tuple> craPaymentList = jpaQuery.list(QCRAAllocation.cRAAllocation.denomination,
				QCRAAllocation.cRAAllocation.vault.sum(), QCRAAllocation.cRAAllocation.forward.sum());
		return craPaymentList;
	}

	@Override
	public boolean saveDataInBinRegister(BinRegister binRegister) {
		em.persist(binRegister);
		return true;
	}

	@Override
	public String getSRNumberBySolId(String solId) {
		JPAQuery jpaQuery = getFromSASForVoucher();
		jpaQuery.where(QSas.sas.solID.eq(solId));
		String srNumberList = jpaQuery.singleResult(QSas.sas.srNo);
		return srNumberList;
	}

	@Override
	public String getSRNumberById(long Id) {
		JPAQuery jpaQuery = getFromSASForVoucher();
		jpaQuery.where(QSas.sas.id.eq(Id));
		String srNumberList = jpaQuery.singleResult(QSas.sas.srNo);
		return srNumberList;
	}

	@Override
	public long removeBranchFromSAS(Sas sas) {
		QSas qSas = QSas.sas;
		long count = new JPAUpdateClause(em, qSas).where(QSas.sas.id.eq(sas.getId())).set(QSas.sas.status, 6).execute();
		return count;
	}

	@Override
	public List<SoiledRemittanceAllocation> TRReports(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSoiledRemittanceAllocation();
		jpaQuery.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.insertTime.between(sDate, eDate)))
				.orderBy(QSoiledRemittanceAllocation.soiledRemittanceAllocation.insertTime.desc());
		List<SoiledRemittanceAllocation> TRdata = jpaQuery.list(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return TRdata;
	}

	@Override
	public CRA getCRADetailById(long id) {
		return em.find(CRA.class, id);

	}

	@Override
	public OtherBank getOtherBankRecordById(BigInteger icmcId, long id) {
		/*
		 * JPAQuery jpaQuery = getFromQueryForOtherBank();
		 * jpaQuery.where(QOtherBank.otherBank.icmcId.eq(icmcId).and(QOtherBank.
		 * otherBank.id.eq(id))
		 * .and(QOtherBank.otherBank.status.eq(OtherStatus.REQUESTED)));
		 * OtherBank otherBankRecord =
		 * jpaQuery.singleResult(QOtherBank.otherBank); return otherBankRecord;
		 */

		return em.find(OtherBank.class, id);
	}

	@Override
	public Sas getSASRecordById(BigInteger icmcId, Long id) {
		JPAQuery jpaQuery = getFromQueryForSAS();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.status.ne(4)).and(QSas.sas.id.eq(id)));
		Sas sasList = jpaQuery.singleResult(QSas.sas);
		return sasList;
	}

	@Override
	public void updateSasAllocationForCancelEditBranchPayment(BigInteger icmcId, long id) {
		QSASAllocation qSasAllocation = QSASAllocation.sASAllocation;
		new JPAUpdateClause(em, qSasAllocation)
				.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId).and(QSASAllocation.sASAllocation.parentId.eq(id)))
				.set(qSasAllocation.status, OtherStatus.CANCELLED).execute();

	}

	@Override
	public List<Sas> getORVReport1(BigInteger icmcId, Calendar sDate, Calendar eDate, Long sasId) {
		JPAQuery jpaQuery = getFromQueryForORVReportFromBranchReceipt();
		jpaQuery.where(QSas.sas.icmcId.eq(icmcId).and(QSas.sas.status.ne(1)).and(QSas.sas.status.ne(3))
				.and(QSas.sas.insertTime.between(sDate, eDate)).and(QSas.sas.id.eq(sasId)));
		List<Sas> orvList = jpaQuery.list(QSas.sas);
		return orvList;
	}

	@Override
	public List<SASAllocation> getAllAcceptedFromSASAllocation1(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId));
		List<SASAllocation> statusListForAccepted = jpaQuery.list(QSASAllocation.sASAllocation);
		return statusListForAccepted;
	}

	@Override
	public List<Tuple> getSASAllocationRecordFromTuple(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		// TODO Auto-generated method stub
		JPAQuery jpaQuery = getFromQueryForSASAllocation();
		jpaQuery.where(QSASAllocation.sASAllocation.icmcId.eq(icmcId)
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.REQUESTED))
		/*
		 * .and(QSASAllocation.sASAllocation.insertTime.between(sDate, eDate))
		 */
		);
		jpaQuery.groupBy(QSASAllocation.sASAllocation.binNumber, QSASAllocation.sASAllocation.denomination,
				QSASAllocation.sASAllocation.cashType, QSASAllocation.sASAllocation.binType);
		List<Tuple> sasAllocation = jpaQuery.list(QSASAllocation.sASAllocation.binNumber,
				QSASAllocation.sASAllocation.denomination, QSASAllocation.sASAllocation.cashType,
				QSASAllocation.sASAllocation.binType, QSASAllocation.sASAllocation.bundle.sum());
		return sasAllocation;
	}

	@Override
	public List<SASAllocation> getSasAllocationByBinNumber(String binNumber) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation().where(QSASAllocation.sASAllocation.binNumber.eq(binNumber)
				.and(QSASAllocation.sASAllocation.status.eq(OtherStatus.REQUESTED)));
		List<SASAllocation> sasAllocation = jpaQuery.list(QSASAllocation.sASAllocation);
		return sasAllocation;
	}

	@Override
	public List<SASAllocation> getDataToUpdateBinTransaction(BigInteger icmcId, Long parentId) {
		JPAQuery jpaQuery = getFromQueryForSASAllocation();
		jpaQuery.where(
				QSASAllocation.sASAllocation.icmcId.eq(icmcId).and(QSASAllocation.sASAllocation.parentId.eq(parentId)));
		List<SASAllocation> sasAllocation = jpaQuery.list(QSASAllocation.sASAllocation);
		return sasAllocation;
	}

	@Override
	public BinTransaction getDataFromBinTransactionForSasAllocationCancel(BigInteger icmcId, String binNumber,
			Integer denomination) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binNumber.eq(binNumber))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				.and(QBinTransaction.binTransaction.denomination.eq(denomination)));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public BinTransaction getDataFromBinTransactionForSoiledAllocationCancel(BigInteger icmcId, String box,
			Integer denomination) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(
				QBinTransaction.binTransaction.icmcId.eq(icmcId).and(QBinTransaction.binTransaction.binNumber.eq(box))
						.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
						.and(QBinTransaction.binTransaction.denomination.eq(denomination)));
		BinTransaction binTransaction = jpaQuery.singleResult(QBinTransaction.binTransaction);
		return binTransaction;
	}

	@Override
	public boolean updateBinTransactionPendingBundleForCashPaymentCancel(BinTransaction binTransaction) {
		QBinTransaction qBinTransaction = QBinTransaction.binTransaction;
		long count = new JPAUpdateClause(em, qBinTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(binTransaction.getIcmcId())
						.and(QBinTransaction.binTransaction.binNumber.eq(binTransaction.getBinNumber())
								.and(QBinTransaction.binTransaction.denomination.eq(binTransaction.getDenomination()))))
				.set(qBinTransaction.pendingBundleRequest, binTransaction.getPendingBundleRequest()).execute();
		return count > 0 ? true : false;
	}

	@Override
	public long updateOrvStatus1(long id) {
		QDiversionORV qOrv = QDiversionORV.diversionORV;
		long count = new JPAUpdateClause(em, qOrv).where(QDiversionORV.diversionORV.id.eq(id))
				.set(qOrv.otherStatus, OtherStatus.CANCELLED).execute();
		return count;
	}

	@Override
	public long updateOrvAllocationStatus1(long id) {
		QDiversionORVAllocation qOrv = QDiversionORVAllocation.diversionORVAllocation;
		long count = new JPAUpdateClause(em, qOrv)
				.where(QDiversionORVAllocation.diversionORVAllocation.diversionOrvId.eq(id))
				.set(qOrv.status, OtherStatus.CANCELLED).execute();
		return count;
	}

	@Override
	public DiversionORV getDiversionORVById(Long id) {
		return em.find(DiversionORV.class, id);
	}

	private JPAQuery getCRAIDFromForwardBundleForCRA() {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QForwardBundleForCRAPayment.forwardBundleForCRAPayment);
		return jpaQuery;
	}

	@Override
	public ProcessBundleForCRAPayment getCRAId(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleForCraPayment();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.PROCESSED)));
		ProcessBundleForCRAPayment craId = jpaQuery
				.singleResult(QProcessBundleForCRAPayment.processBundleForCRAPayment);
		return craId;
	}

	@Override
	public List<ProcessBundleForCRAPayment> getListCRAId(BigInteger icmcId) {
		JPAQuery jpaQuery = getFromQueryForProcessBundleForCraPayment();
		jpaQuery.where(QProcessBundleForCRAPayment.processBundleForCRAPayment.icmcId.eq(icmcId)
				.and(QProcessBundleForCRAPayment.processBundleForCRAPayment.status.eq(OtherStatus.PROCESSED)));
		List<ProcessBundleForCRAPayment> craId = jpaQuery.list(QProcessBundleForCRAPayment.processBundleForCRAPayment);
		return craId;
	}

	@Override
	public List<CRA> valueFromCRA(BigInteger icmcId, long craId) {
		JPAQuery jpaQuery = getFromQueryForCRA();
		jpaQuery.where(QCRA.cRA.icmcId.eq(icmcId).and(QCRA.cRA.id.eq(craId)));
		List<CRA> craDetails = jpaQuery.list(QCRA.cRA);
		return craDetails;
	}

	@Override
	public void deleteEmptyBinFromBinTransaction(BigInteger icmcId, String binNumber) {
		new JPADeleteClause(em, QBinTransaction.binTransaction)
				.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
						.and(QBinTransaction.binTransaction.binNumber.eq(binNumber))
						.and(QBinTransaction.binTransaction.status.eq(BinStatus.EMPTY))).execute();

	}

	@Override
	public boolean getShrinkBundleFromBrancheReceipt(SASAllocation sasAllo, CashSource cashSource) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(sasAllo.getIcmcId())
				.and(QBranchReceipt.branchReceipt.denomination.eq(sasAllo.getDenomination()))
				.and(QBranchReceipt.branchReceipt.cashSource.eq(cashSource))
				.and(QBranchReceipt.branchReceipt.bundle.eq(sasAllo.getBundle()))
				.and(QBranchReceipt.branchReceipt.bin.eq(sasAllo.getBinNumber()))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED)));
		BigDecimal shrinkBundle = jpaQuery.singleResult(QBranchReceipt.branchReceipt.bundle);
		return shrinkBundle != null ? true : false;

	}

	@Override
	public List<Tuple> getAllShrinkWrapBundleFromBranchReceipt(BigInteger icmcId) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.cashSource.eq(CashSource.BRANCH))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED)));
		jpaQuery.orderBy(QBranchReceipt.branchReceipt.denomination.asc());
		jpaQuery.groupBy(QBranchReceipt.branchReceipt.denomination);
		 List<Tuple> branchReceipts = jpaQuery.list(QBranchReceipt.branchReceipt.denomination,QBranchReceipt.branchReceipt.bundle.sum());
		return branchReceipts;
	}

	@Override
	public List<BranchReceipt> getShrinkWrapBundleByDenomination(int denomination, BigInteger icmcId,BinCategoryType binCategoryType) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.denomination.eq(denomination))
				.and(QBranchReceipt.branchReceipt.cashSource.eq(CashSource.BRANCH))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.RECEIVED))
				.and(QBranchReceipt.branchReceipt.binCategoryType.eq(binCategoryType)));
		 List<BranchReceipt> branchReceipts = jpaQuery.list(QBranchReceipt.branchReceipt);
		return branchReceipts;
	}

	@Override
	public boolean updatebBranchReceiptForBranchPaymentCancel(BranchReceipt branchReceipt) {
		QBranchReceipt qBranchReceipt = QBranchReceipt.branchReceipt;
		long count = new JPAUpdateClause(em, qBranchReceipt)
				.where(qBranchReceipt.icmcId.eq(branchReceipt.getIcmcId())
						.and(qBranchReceipt.bin.eq(branchReceipt.getBin())
								.and(qBranchReceipt.cashSource.eq(CashSource.BRANCH))
								.and(qBranchReceipt.denomination.eq(branchReceipt.getDenomination()))))
				.set(qBranchReceipt.status, OtherStatus.RECEIVED).execute();
		return count > 0 ? true : false;
	}

	@Override
	public BranchReceipt checkBinOrBoxFromBranchReceipt(BigInteger icmcId, int denomination, BigDecimal bundle,
			String binNumber) {
		JPAQuery jpaQuery = new JPAQuery(em);
		jpaQuery.from(QBranchReceipt.branchReceipt);
		jpaQuery.where(QBranchReceipt.branchReceipt.icmcId.eq(icmcId)
				.and(QBranchReceipt.branchReceipt.denomination.eq(denomination))
				.and(QBranchReceipt.branchReceipt.cashSource.eq(CashSource.BRANCH))
				.and(QBranchReceipt.branchReceipt.currencyType.eq(CurrencyType.UNPROCESS))
				.and(QBranchReceipt.branchReceipt.status.eq(OtherStatus.PROCESSED))
				.and(QBranchReceipt.branchReceipt.bin.eq(binNumber))
				.and(QBranchReceipt.branchReceipt.bundle.eq(bundle))
				);
		BranchReceipt branchReceipts = jpaQuery.singleResult(QBranchReceipt.branchReceipt);
		return branchReceipts;
	}
	@Override
	public List<Tuple> getSoiledSummary(BigInteger icmcId,CurrencyType currencyType) {
		JPAQuery jpaQuery = getFromQueryForBinTxn();
		jpaQuery.where(QBinTransaction.binTransaction.icmcId.eq(icmcId)
				.and(QBinTransaction.binTransaction.binType.eq(currencyType))
				.and(QBinTransaction.binTransaction.status.ne(BinStatus.EMPTY))
				/* .and(QBinTransaction.binTransaction.binNumber.ne("BOX")) */
				.and(QBinTransaction.binTransaction.binNumber.notLike("BOX:%"))
				.and(QBinTransaction.binTransaction.maxCapacity.gt(0))
				.and(QBinTransaction.binTransaction.binCategoryType.ne(BinCategoryType.BAG)));
		jpaQuery.groupBy(QBinTransaction.binTransaction.denomination);
		List<Tuple> list = jpaQuery.list(QBinTransaction.binTransaction.denomination,
				QBinTransaction.binTransaction.receiveBundle.sum(),
				QBinTransaction.binTransaction.insertTime.min().as("insertTime"));
		return list;
	}
	@Override
	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId,Calendar sDate, Calendar eDate) {
		JPAQuery jpaQuery = getFromQueryForSoiledAccept();
		jpaQuery.where(QSoiledRemittanceAllocation.soiledRemittanceAllocation.icmcId.eq(icmcId)
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.insertTime.between(sDate, eDate))
				.and(QSoiledRemittanceAllocation.soiledRemittanceAllocation.status.eq(OtherStatus.ACCEPTED)));
		List<SoiledRemittanceAllocation> soiledList = jpaQuery
				.list(QSoiledRemittanceAllocation.soiledRemittanceAllocation);
		return soiledList;
	}

}