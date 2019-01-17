package com.chest.currency.service;

import static com.chest.currency.util.UtilityJpa.addBigDecimal;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CRAAllocationLog;
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
import com.chest.currency.enums.DenominationType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.YesNo;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.dao.CashPaymentJpaDaoImpl;
import com.chest.currency.jpa.dao.ProcessingRoomJpaDaoImpl;
import com.chest.currency.qrgencode.QRCodeGen;
import com.chest.currency.util.UtilityJpa;
import com.chest.currency.viewBean.SASAllocationWrapper;
import com.mysema.query.Tuple;

@Service
@Transactional
public class CashPaymentServiceImpl implements CashPaymentService {

	private static final Logger LOG = LoggerFactory.getLogger(CashPaymentServiceImpl.class);

	@Autowired
	QRCodeGen qrCodeGen;

	@Autowired
	ProcessingRoomService processingRoomService;

	@Autowired
	protected CashPaymentJpaDaoImpl cashPaymentJpaDao;

	@Autowired
	ProcessingRoomJpaDaoImpl processingRoomJpaDao;

	@Override
	public boolean sasUpload(List<Sas> sasList, Sas sas) {
		return cashPaymentJpaDao.sasUpload(sasList, sas);
	}

	@Override
	public List<Sas> getSASRecord(User user, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getSASRecord(user, sDate, eDate);
	}

	@Override
	public List<Sas> getSASRecordForceHandover(User user, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getSASRecordForceHandover(user, sDate, eDate);
	}

	@Override
	public List<Sas> getSASRecordFromSasfile(User user) {
		return cashPaymentJpaDao.getSASRecordFromSasfile(user);
	}

	@Override
	public boolean processSASAllocation(SASAllocationWrapper sasAllocationWrapper, User user, Sas sas) {

		List<SASAllocation> sasList = UtilityJpa
				.mapSasAllocationGroupedToSasAllocation(sasAllocationWrapper.getSasAllocationList());
		sas.setProcessedOrUnprocessed("PROCESSED");
		findBinAndProcessSasAllocation(user, sasList, sas);
		sas.setIcmcId(sasAllocationWrapper.getIcmcId());
		this.updateSASStatus(sas);
		return true;
	}

	@Transactional
	private void findBinAndProcessSasAllocation(User user, List<SASAllocation> sasList, Sas sasAllocationParent) {
		List<SASAllocation> eligibleSASRequestList = new ArrayList<>();
		List<BinTransaction> binTransactionList = new ArrayList<>();
		List<BranchReceipt> branchReceiptList = new ArrayList<>();
		List<BinTransaction> txnList = null;

		for (SASAllocation sasAllocation : sasList) {
			if (sasAllocation.getCashType() == CashType.COINS) {
				txnList = this.getCoinsListForSas(sasAllocation);
				eligibleSASRequestList = UtilityJpa.getCoinsForSASRequest(txnList, sasAllocation.getBundle(),
						sasAllocation, sasAllocationParent, sasAllocation.getBinType(), user);
			} else {
				boolean isAllSuccess = false;
				if (sasAllocationParent.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {

					List<BinTransaction> txnListUnprocess = processingRoomService.getBinNumListForIndent(
							sasAllocation.getDenomination(), sasAllocation.getBundle(), user.getIcmcId(),
							CashSource.BRANCH, sasAllocationParent.getBinCategoryType());
					LOG.info("indentRequest controller " + txnListUnprocess);

					BigDecimal bundleFromTxn = BigDecimal.ZERO;
					for (BinTransaction binTx : txnListUnprocess) {
						bundleFromTxn = bundleFromTxn.add(
								UtilityJpa.getSubstarctedBundle(binTx, (UtilityJpa.getPendingBundleRequest(binTx))));
					}
					BigDecimal bundleForRequest = sasAllocation.getBundle();

					if (bundleFromTxn.compareTo(bundleForRequest) >= 0) {

						binTransactionList.addAll(txnListUnprocess);

						branchReceiptList = processingRoomService.getBinNumListForIndentFromBranchReceipt(
								sasAllocation.getDenomination(), sasAllocation.getBundle(), user.getIcmcId(),
								CashSource.BRANCH, sasAllocationParent.getBinCategoryType());

						eligibleSASRequestList = UtilityJpa.getBinForBranchReceiptSasRequest(txnListUnprocess,
								sasAllocation.getDenomination(), sasAllocation.getBundle(), user, sasAllocationParent,
								branchReceiptList);

						BigDecimal moreBundleNeeded = UtilityJpa
								.checkMoreRequiredBundleNeededForSas(eligibleSASRequestList, bundleForRequest);
						if (moreBundleNeeded.compareTo(BigDecimal.ZERO) != 0) {
							throw new BaseGuiException("Payment Should be in Shrink Wrap:");
						} else if (moreBundleNeeded.compareTo(BigDecimal.ZERO) == 0) {
							isAllSuccess = processingRoomService.insertSasRequestAndUpdateBinTxAndBranchReceipt(
									eligibleSASRequestList, binTransactionList, branchReceiptList, sasAllocationParent);
							isAllSuccess = true;
							if (!isAllSuccess) {
								throw new RuntimeException("Error while Branch Payment Request");
							}
						}

					}

				} else {
					txnList = cashPaymentJpaDao.getBinNumListForSas(sasAllocation, sasAllocation.getBinType());
					eligibleSASRequestList = UtilityJpa.getBinForSASRequest(txnList, sasAllocation.getBundle(),
							sasAllocation, sasAllocationParent, sasAllocation.getBinType(), user);
				}

			}
			LOG.info("BEFORE IF eligibleSASRequestList  " + eligibleSASRequestList);
			if (eligibleSASRequestList == null || eligibleSASRequestList.isEmpty()) {
				throw new BaseGuiException("Required Bin is not available for Denomination:"
						+ sasAllocation.getDenomination() + " and Category : " + sasAllocation.getBinType());
			}
			cashPaymentJpaDao.insertInSASAllocation(eligibleSASRequestList);
			/*
			 * for (SASAllocation allocation : eligibleSASRequestList) {
			 * cashPaymentJpaDao.updateBranchReceiptForPayment(user,
			 * allocation); }
			 */
			if (!sasAllocationParent.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
				for (BinTransaction btx : txnList) {
					LOG.info("binTransaction updation except Unprocess  " + btx);
					if (btx.isDirty()) {
						btx.setUpdateBy(user.getId());
						btx.setUpdateTime(Calendar.getInstance());
						this.updateBinTxn(btx);
					}
				}
			}
		}

	}

	private void findBinAndProcessCRAAllocation(User user, CRA cra) {
		List<CRAAllocation> craPaymentRequestListAll = new ArrayList<>();
		for (CRAAllocation craAllocation : cra.getCraAllocations()) {
			List<CRAAllocation> CRAPaymentRequestList = UtilityJpa.processCRARequest(craAllocation.getBundle(),
					craAllocation, craAllocation.getCurrencyType(), user);
			craPaymentRequestListAll.addAll(CRAPaymentRequestList);
		}
		cra.setStatus(OtherStatus.REQUESTED);
		cra.setCraAllocations(craPaymentRequestListAll);
		CRALog craLog = new CRALog();
		cashPaymentJpaDao.createCRAPayment(cra);
		long craId = cashPaymentJpaDao.getLastCreateCRAPaymentId();
		CRA craList = cashPaymentJpaDao.getLastCreateCRAPaymentList(craId);
		List<CRAAllocationLog> craAllocationLogListAll = new ArrayList<>();
		for (CRAAllocation craAllocationlog : craList.getCraAllocations()) {
			List<CRAAllocationLog> craAllocationLogList = UtilityJpa.processCRAAllocationLogRequest(craAllocationlog);
			craAllocationLogListAll.addAll(craAllocationLogList);
		}
		UtilityJpa.processCRALogRequest(craList, craLog);
		craLog.setCraAllocationsLog(craAllocationLogListAll);
		cashPaymentJpaDao.createCRALogPayment(craLog);
	}

	@Override
	public long updateSAS(Sas sas) {
		BigDecimal totalValue = sas.getTotalValueOfCoinsRs1()
				.multiply(BigDecimal.valueOf(1).multiply(BigDecimal.valueOf(2500)))
				.add(sas.getTotalValueOfCoinsRs10().multiply(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(2000))))
				.add(sas.getTotalValueOfCoinsRs2().multiply(BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(2500))))
				.add(sas.getTotalValueOfCoinsRs5().multiply(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(2500))))
				.add(sas.getTotalValueOfNotesRs2000A()
						.add(sas.getTotalValueOfNotesRs2000F().add(sas.getTotalValueOfNotesRs2000I())).multiply(
								BigDecimal.valueOf(2000).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs1000A()
						.add(sas.getTotalValueOfNotesRs1000F().add(sas.getTotalValueOfNotesRs1000I()))
						.multiply(BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs500A()
						.add(sas.getTotalValueOfNotesRs500F().add(sas.getTotalValueOfNotesRs500I()))
						.multiply(BigDecimal.valueOf(500).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs200A()
						.add(sas.getTotalValueOfNotesRs200F().add(sas.getTotalValueOfNotesRs200I()))
						.multiply(BigDecimal.valueOf(200).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs100A()
						.add(sas.getTotalValueOfNotesRs100I().add(sas.getTotalValueOfNotesRs100F()))
						.multiply(BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs50F().add(sas.getTotalValueOfNotesRs50I())
						.multiply(BigDecimal.valueOf(50).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs20F().add(sas.getTotalValueOfNotesRs20I())
						.multiply(BigDecimal.valueOf(20).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs10I().add(sas.getTotalValueOfNotesRs10F())
						.multiply(BigDecimal.valueOf(10).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs5F().add(sas.getTotalValueOfNotesRs5I())
						.multiply(BigDecimal.valueOf(5).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs2F().add(sas.getTotalValueOfNotesRs2I())
						.multiply(BigDecimal.valueOf(2).multiply(BigDecimal.valueOf(1000))))
				.add(sas.getTotalValueOfNotesRs1F().add(sas.getTotalValueOfNotesRs1I())
						.multiply(BigDecimal.valueOf(1).multiply(BigDecimal.valueOf(1000))));

		sas.setTotalValue(totalValue);
		return cashPaymentJpaDao.updateSAS(sas);
	}

	@Override
	public List<SoiledRemittance> soiledRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.soiledRecord(icmcId, sDate, eDate);
	}

	@Override
	public List<SoiledRemittance> getSoiledRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getSoiledRequestAcceptRecord(icmcId, sDate, eDate);
	}

	@Override
	public long updateSASStatus(Sas sas) {
		return cashPaymentJpaDao.updateSASStatus(sas);
	}

	@Override
	public List<SASAllocation> getSASAllocationRecord(BigInteger icmcId) {
		return cashPaymentJpaDao.getSASAllocationRecord(icmcId);
	}

	@Override
	public List<SASAllocation> getSasAllocationBySasId(long sasId) {
		return cashPaymentJpaDao.getSasAllocationBySasId(sasId);
	}

	@Override
	public List<SASAllocation> getSasAllocationByBinNumberBundle(SASAllocation sasAlo) {
		return cashPaymentJpaDao.getSasAllocationByBinNumberBundle(sasAlo);
	}

	@Override
	public List<SASAllocation> getSasAllocationByBinNumber(SASAllocation sasAlo) {
		return cashPaymentJpaDao.getSasAllocationByBinNumber(sasAlo);
	}

	@Override
	public boolean saveSoiledRemittance(SoiledRemittance soiledRemittance) {
		return cashPaymentJpaDao.saveSoiledRemittance(soiledRemittance);
	}

	@Override
	public SoiledRemittance getSoiledRemittanceById(BigInteger icmcId, Calendar sDate, Calendar eDate, long id) {
		return cashPaymentJpaDao.getSoiledRemittanceById(icmcId, sDate, eDate, id);
	}

	@Override
	public List<DiversionORV> getDiversionORV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<DiversionORV> diversionORVList = new ArrayList<>();
		List<DiversionORV> diversionListTemp = cashPaymentJpaDao.getDiversionORV(icmcId, sDate, eDate);
		for (DiversionORV diversionORV : diversionListTemp) {
			BigDecimal totalValue = BigDecimal.ZERO;
			for (DiversionORVAllocation diversionORVAllocation : diversionORV.getDiversionAllocations()) {
				if (diversionORVAllocation.getTotal() != null) {
					totalValue = totalValue.add(diversionORVAllocation.getTotal());
				}
			}
			diversionORV.setTotalValue(totalValue);
			diversionORVList.add(diversionORV);
		}
		return diversionORVList;
	}

	@Override
	public boolean saveDiversionORV(DiversionORV diversionORV) {
		return cashPaymentJpaDao.saveDiversionORV(diversionORV);
	}

	@Override
	public List<Sas> getORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getORVRecords(icmcId, sDate, eDate);
	}

	@Override
	public List<Sas> getRequestORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getRequestORVRecords(icmcId, sDate, eDate);
	}

	@Override
	public List<Sas> getRequestAcceptORVRecords(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getRequestAcceptORVRecords(icmcId, sDate, eDate);
	}

	@Override
	public Sas getSASRecordById(BigInteger icmcId, Long id) {
		return cashPaymentJpaDao.getSASRecordById(icmcId, id);
	}

	@Override
	public boolean saveORV(ORV orv) {
		return cashPaymentJpaDao.saveORV(orv);
	}

	@Override
	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId) {
		return cashPaymentJpaDao.getSoiledForAccept(icmcId);
	}

	@Override
	public List<ORVAllocation> getORVForAccept(BigInteger icmcId) {
		return cashPaymentJpaDao.getORVForAccept(icmcId);
	}

	@Override
	public List<DiversionORVAllocation> getDiversionORVForAccept(BigInteger icmcId) {
		return cashPaymentJpaDao.getDiversionORVForAccept(icmcId);
	}

	@Override
	@Transactional
	public boolean processSoiledRemmitanceAllocation(SoiledRemittance soiled, User user) {
		if (soiled.getId() != null) {
			this.updateInsertSoiledAndSoiledAllocation(soiled.getIcmcId(), soiled.getId());
		}
		soiled.setId(null);
		CurrencyType type = null;
		if (soiled.getTypes().equalsIgnoreCase("Normal")) {
			type = CurrencyType.SOILED;
		} else {
			type = CurrencyType.MUTILATED;
		}
		List<SoiledRemittanceAllocation> eligibleSoiledList = new ArrayList<>();
		List<BinTransaction> binTransactionList = new ArrayList<>();

		for (SoiledRemittanceAllocation soiledAllocation : soiled.getRemittanceAllocations()) {

			soiledAllocation.setIcmcId(soiled.getIcmcId());
			List<BinTransaction> txnList = cashPaymentJpaDao.getBoxListForSoiled(soiledAllocation, type);

			BigDecimal bundleFromTxn = BigDecimal.ZERO;
			for (BinTransaction binTx : txnList) {
				bundleFromTxn = bundleFromTxn.add(binTx.getReceiveBundle());
			}

			BigDecimal bundleForRequest = soiledAllocation.getBundle();

			if (bundleFromTxn.compareTo(bundleForRequest) >= 0) {
				eligibleSoiledList.addAll(UtilityJpa.getBoxForSoiled(txnList, soiledAllocation.getBundle(),
						soiledAllocation.getDenomination(), CurrencyType.SOILED, user, bundleFromTxn));
				binTransactionList.addAll(txnList);
			} else {
				throw new BaseGuiException("Available Bundle in Boxes for " + soiledAllocation.getDenomination()
						+ " denomination, are: " + bundleFromTxn);
			}
		}
		if (eligibleSoiledList.size() > 0) {
			soiled.setStatus(OtherStatus.REQUESTED);
			soiled.setRemittanceAllocations(eligibleSoiledList);
			cashPaymentJpaDao.saveSoiledRemittance(soiled);

			// BinTx loop
			for (BinTransaction btx : binTransactionList) {
				btx.setActive(1);
				this.updateBinTxn(btx);
			}
		} else {
			LOG.info("NO BOX AVAILABLE");
		}
		return true;
	}

	@Override
	@Transactional
	public boolean processDiversionORVAllocation(DiversionORV dorv, User user) {
		if (dorv.getId() != null) {
			long id = dorv.getId();
			this.processDiversionORVCancellation(user, id);
			this.updateOrvStatus1(id); // code for update
			this.updateOrvAllocationStatus1(id);
			dorv.setId(null);
		}
		List<DiversionORVAllocation> eligibleDorvList = new ArrayList<>();
		List<BinTransaction> binTransactionList = new ArrayList<>();
		BigDecimal bundleAvailableInVault = BigDecimal.ZERO;

		for (DiversionORVAllocation dorvAllocation : dorv.getDiversionAllocations()) {
			dorvAllocation.setIcmcId(dorv.getIcmcId());
			List<BinTransaction> txnList = cashPaymentJpaDao.getBinNumListForDiversion(dorvAllocation,
					dorvAllocation.getCurrencyType());

			List<DiversionORVAllocation> dorvList = cashPaymentJpaDao.getBinFromDorvAllocation(dorvAllocation);
			eligibleDorvList.addAll(UtilityJpa.getBinForDiversionORV(txnList, dorvList, dorvAllocation.getBundle(),
					user, dorvAllocation.getTotal(), dorvAllocation.getCategory(), dorvAllocation.getCurrencyType()));
			binTransactionList.addAll(txnList);

			for (BinTransaction binTxnTemp : txnList) {
				bundleAvailableInVault = bundleAvailableInVault.add(binTxnTemp.getReceiveBundle());
			}
			if (eligibleDorvList == null || eligibleDorvList.isEmpty()
					|| bundleAvailableInVault.compareTo(dorvAllocation.getBundle()) < 0) {
				throw new BaseGuiException("Required bundles are not available for Denomination:"
						+ dorvAllocation.getDenomination() + " and Category :" + dorvAllocation.getCurrencyType());
			}
		}

		if (eligibleDorvList.size() > 0) {
			dorv.setDiversionAllocations(eligibleDorvList);
			cashPaymentJpaDao.saveDiversionORV(dorv);

			// BinTx loop
			for (BinTransaction btx : binTransactionList) {
				this.updateBinTxn(btx);
			}
		} else {
			LOG.info("NO BIN AVAILABLE");
		}
		return true;
	}

	@Override
	@Transactional
	public boolean processORVAllocation(ORV orv, User user) {
		long sasId = 0;
		if (orv.getId() != null) {
			sasId = orv.getId();
			Sas checkSasStatus = this.getSASRecordById(user.getIcmcId(), sasId);
			if (checkSasStatus.getStatus() == 0) {
				List<SASAllocation> sasAllocation = this.getDataToUpdateBinTransaction(user.getIcmcId(), sasId);
				BinTransaction binTransaction = new BinTransaction();
				BranchReceipt branchReceipt = new BranchReceipt();
				BigDecimal finalBundle = new BigDecimal(0);
				for (SASAllocation sasAloData : sasAllocation) {
					binTransaction = this.getDataFromBinTransactionForSasAllocationCancel(user.getIcmcId(),
							sasAloData.getBinNumber(), sasAloData.getDenomination());
					finalBundle = binTransaction.getPendingBundleRequest().subtract(sasAloData.getBundle());
					binTransaction.setIcmcId(user.getIcmcId());
					binTransaction.setBinNumber(sasAloData.getBinNumber());
					binTransaction.setDenomination(sasAloData.getDenomination());
					binTransaction.setPendingBundleRequest(finalBundle);
					branchReceipt.setIcmcId(user.getIcmcId());
					branchReceipt.setBin(sasAloData.getBinNumber());
					branchReceipt.setDenomination(sasAloData.getDenomination());
					branchReceipt.setSasId(sasAloData.getParentId());
					this.updateBinTransactionPendingBundleForCashPaymentCancel(binTransaction);
					this.updatebBranchReceiptForBranchPaymentCancel(branchReceipt);
				}
			} else {
				throw new BaseGuiException("Accepted value cant be edited");
			}
		}
		Sas sas = new Sas(true);
		orv.setIcmcId(user.getIcmcId());
		orv.setInsertBy(user.getId());
		orv.setUpdateBy(user.getId());
		orv.setId(null);
		Calendar now = Calendar.getInstance();
		sas.setId(null);
		List<SASAllocation> sasAllocationList = new ArrayList<>();

		for (ORVAllocation orvAllocationForSAS : orv.getOrvAllocations()) {
			setCommonSasFields(orv, sas, now, orvAllocationForSAS);

			if (orv.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
				orvAllocationForSAS.setProcessedOrUnprocessed("UNPROCESS");
				sas.setProcessedOrUnprocessed("UNPROCESS");
				sas.setBinCategoryType(orv.getBinCategoryType());
			} else {
				orvAllocationForSAS.setProcessedOrUnprocessed("PROCESSED");
				sas.setProcessedOrUnprocessed("PROCESSED");
			}
			setDenomSpecificFields(sas, orvAllocationForSAS);
			prepareAddSasAllocation(orv, sas, sasAllocationList, orvAllocationForSAS);
		}
		cashPaymentJpaDao.createORVPayment(sas);
		this.findBinAndProcessSasAllocation(user, sasAllocationList, sas);
		if (sasId != 0) {
			this.updateSasForCancelBranchPayment(user.getIcmcId(), sasId);
			this.updateSasAllocationForCancelEditBranchPayment(user.getIcmcId(), sasId);
		}
		return true;
	}

	private void setDenomSpecificFields(Sas sas, ORVAllocation orvAllocationForSAS) {

		// Code for Unprocess Payment
		if (orvAllocationForSAS.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				sas.setTotalValueOfNotesRs1U(
						addBigDecimal(sas.getTotalValueOfNotesRs1U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				sas.setTotalValueOfNotesRs2U(
						addBigDecimal(sas.getTotalValueOfNotesRs2U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				sas.setTotalValueOfNotesRs5U(
						addBigDecimal(sas.getTotalValueOfNotesRs5U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				sas.setTotalValueOfNotesRs10U(
						addBigDecimal(sas.getTotalValueOfNotesRs10U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_20.getDenomination()) {
				sas.setTotalValueOfNotesRs20U(
						addBigDecimal(sas.getTotalValueOfNotesRs20U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_50.getDenomination()) {
				sas.setTotalValueOfNotesRs50U(
						addBigDecimal(sas.getTotalValueOfNotesRs50U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				sas.setTotalValueOfNotesRs100U(
						addBigDecimal(sas.getTotalValueOfNotesRs100U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_200.getDenomination()) {
				sas.setTotalValueOfNotesRs200U(
						addBigDecimal(sas.getTotalValueOfNotesRs200U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				sas.setTotalValueOfNotesRs500U(
						addBigDecimal(sas.getTotalValueOfNotesRs500U(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				sas.setTotalValueOfNotesRs2000U(
						addBigDecimal(sas.getTotalValueOfNotesRs2000U(), orvAllocationForSAS.getBundle()));
			}
		}
		// Close Unporcess Payment Code

		else {

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.COINS
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				sas.setTotalValueOfCoinsRs10(
						addBigDecimal(sas.getTotalValueOfCoinsRs10(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.COINS
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				sas.setTotalValueOfCoinsRs5(
						addBigDecimal(sas.getTotalValueOfCoinsRs5(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.COINS
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				sas.setTotalValueOfCoinsRs2(
						addBigDecimal(sas.getTotalValueOfCoinsRs2(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.COINS
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				sas.setTotalValueOfCoinsRs1(
						addBigDecimal(sas.getTotalValueOfCoinsRs1(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ATM
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				sas.setTotalValueOfNotesRs2000A(
						addBigDecimal(sas.getTotalValueOfNotesRs2000A(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				sas.setTotalValueOfNotesRs2000I(
						addBigDecimal(sas.getTotalValueOfNotesRs2000I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				sas.setTotalValueOfNotesRs2000F(
						addBigDecimal(sas.getTotalValueOfNotesRs2000F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2000.getDenomination()) {
				sas.setTotalValueOfNotesRs2000S(
						addBigDecimal(sas.getTotalValueOfNotesRs2000S(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ATM
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1000.getDenomination()) {
				sas.setTotalValueOfNotesRs1000A(
						addBigDecimal(sas.getTotalValueOfNotesRs1000A(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1000.getDenomination()) {
				sas.setTotalValueOfNotesRs1000I(
						addBigDecimal(sas.getTotalValueOfNotesRs1000I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1000.getDenomination()) {
				sas.setTotalValueOfNotesRs1000F(
						addBigDecimal(sas.getTotalValueOfNotesRs1000F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1000.getDenomination()) {
				sas.setTotalValueOfNotesRs1000S(
						addBigDecimal(sas.getTotalValueOfNotesRs1000S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ATM
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				sas.setTotalValueOfNotesRs100A(
						addBigDecimal(sas.getTotalValueOfNotesRs100A(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				sas.setTotalValueOfNotesRs100I(
						addBigDecimal(sas.getTotalValueOfNotesRs100I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				sas.setTotalValueOfNotesRs100F(
						addBigDecimal(sas.getTotalValueOfNotesRs100F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_100.getDenomination()) {
				sas.setTotalValueOfNotesRs100S(
						addBigDecimal(sas.getTotalValueOfNotesRs100S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ATM
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_200.getDenomination()) {
				sas.setTotalValueOfNotesRs200A(
						addBigDecimal(sas.getTotalValueOfNotesRs200A(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_200.getDenomination()) {
				sas.setTotalValueOfNotesRs200I(
						addBigDecimal(sas.getTotalValueOfNotesRs200I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_200.getDenomination()) {
				sas.setTotalValueOfNotesRs200F(
						addBigDecimal(sas.getTotalValueOfNotesRs200F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_200.getDenomination()) {
				sas.setTotalValueOfNotesRs200S(
						addBigDecimal(sas.getTotalValueOfNotesRs200S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				sas.setTotalValueOfNotesRs10I(
						addBigDecimal(sas.getTotalValueOfNotesRs10I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				sas.setTotalValueOfNotesRs10F(
						addBigDecimal(sas.getTotalValueOfNotesRs10F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_10.getDenomination()) {
				sas.setTotalValueOfNotesRs10S(
						addBigDecimal(sas.getTotalValueOfNotesRs10S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				sas.setTotalValueOfNotesRs1I(
						addBigDecimal(sas.getTotalValueOfNotesRs1I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				sas.setTotalValueOfNotesRs1F(
						addBigDecimal(sas.getTotalValueOfNotesRs1F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_1.getDenomination()) {
				sas.setTotalValueOfNotesRs1S(
						addBigDecimal(sas.getTotalValueOfNotesRs1S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				sas.setTotalValueOfNotesRs2I(
						addBigDecimal(sas.getTotalValueOfNotesRs2I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				sas.setTotalValueOfNotesRs2F(
						addBigDecimal(sas.getTotalValueOfNotesRs2F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_2.getDenomination()) {
				sas.setTotalValueOfNotesRs2S(
						addBigDecimal(sas.getTotalValueOfNotesRs2S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_20.getDenomination()) {
				sas.setTotalValueOfNotesRs20I(
						addBigDecimal(sas.getTotalValueOfNotesRs20I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_20.getDenomination()) {
				sas.setTotalValueOfNotesRs20F(
						addBigDecimal(sas.getTotalValueOfNotesRs20F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_20.getDenomination()) {
				sas.setTotalValueOfNotesRs20S(
						addBigDecimal(sas.getTotalValueOfNotesRs20S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				sas.setTotalValueOfNotesRs5I(
						addBigDecimal(sas.getTotalValueOfNotesRs5I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				sas.setTotalValueOfNotesRs5F(
						addBigDecimal(sas.getTotalValueOfNotesRs5F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_5.getDenomination()) {
				sas.setTotalValueOfNotesRs5S(
						addBigDecimal(sas.getTotalValueOfNotesRs5S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_50.getDenomination()) {
				sas.setTotalValueOfNotesRs50I(
						addBigDecimal(sas.getTotalValueOfNotesRs50I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_50.getDenomination()) {
				sas.setTotalValueOfNotesRs50F(
						addBigDecimal(sas.getTotalValueOfNotesRs50F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_50.getDenomination()) {
				sas.setTotalValueOfNotesRs50S(
						addBigDecimal(sas.getTotalValueOfNotesRs50S(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ATM
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				sas.setTotalValueOfNotesRs500A(
						addBigDecimal(sas.getTotalValueOfNotesRs500A(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.ISSUABLE
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				sas.setTotalValueOfNotesRs500I(
						addBigDecimal(sas.getTotalValueOfNotesRs500I(), orvAllocationForSAS.getBundle()));
			}

			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.FRESH
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				sas.setTotalValueOfNotesRs500F(
						addBigDecimal(sas.getTotalValueOfNotesRs500F(), orvAllocationForSAS.getBundle()));
			}
			if (orvAllocationForSAS.getCurrencyType() == CurrencyType.SOILED
					&& orvAllocationForSAS.getDenomination() == DenominationType.DENOM_500.getDenomination()) {
				sas.setTotalValueOfNotesRs500S(
						addBigDecimal(sas.getTotalValueOfNotesRs500S(), orvAllocationForSAS.getBundle()));
			}
		} // else Close
	}

	private void setCommonSasFields(ORV orv, Sas sas, Calendar now, ORVAllocation orvAllocationForSAS) {
		sas.setSrNo(orv.getSr());
		sas.setSolID(orv.getSolId());
		sas.setBranch(orv.getBranch());
		sas.setTotalValue(addBigDecimal(sas.getTotalValue(), orvAllocationForSAS.getTotal()));
		LOG.info("orvAllocationForSAS.getTotal() " + orvAllocationForSAS.getTotal());
		LOG.info("sas.getTotalValue() " + sas.getTotalValue());
		LOG.info("orv " + orv);
		sas.setIcmcId(orv.getIcmcId());
		sas.setInsertTime(now);
		sas.setUpdateTime(now);
		sas.setInsertBy(orv.getInsertBy());
		sas.setUpdateBy(orv.getUpdateBy());
	}

	private void prepareAddSasAllocation(ORV orv, Sas sas, List<SASAllocation> sasAllocationList,
			ORVAllocation orvAllocationForSAS) {
		SASAllocation sasAllocation = new SASAllocation();
		sasAllocation.setIcmcId(orv.getIcmcId());

		sasAllocation.setBundle(orvAllocationForSAS.getBundle());
		sasAllocation.setDenomination(orvAllocationForSAS.getDenomination());
		if (orvAllocationForSAS.getCurrencyType() == CurrencyType.COINS) {
			sasAllocation.setCashType(CashType.COINS);
			sasAllocation.setBinType(orvAllocationForSAS.getCurrencyType());
		} else {
			sasAllocation.setCashType(CashType.NOTES);
			if (orv.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
				sasAllocation.setBinType(CurrencyType.UNPROCESS);
			} else {
				sasAllocation.setBinType(orvAllocationForSAS.getCurrencyType());
			}
		}
		sasAllocationList.add(sasAllocation);
	}

	@Override
	public long updateSoiledStatus(SoiledRemittanceAllocation soiled, SoiledRemittance soiledRemittanceTemp) {
		long count = cashPaymentJpaDao.updateSoiledStatus(soiled);
		cashPaymentJpaDao.updateSoiledRemittanceStatus(soiledRemittanceTemp);
		return count;
	}

	@Override
	public long updateDorvStatus(DiversionORVAllocation dorv) {
		return cashPaymentJpaDao.updateDorvStatus(dorv);
	}

	@Override
	public long updateOrvStatus(ORVAllocation orv) {
		return cashPaymentJpaDao.updateORVStatus(orv);
	}

	@Override
	public long updateSASStatus(SASAllocation sas) {
		return cashPaymentJpaDao.updateSASstatus(sas);
	}

	@Override
	public boolean saveCashReleased(CashReleased cashReleased) {
		return cashPaymentJpaDao.saveCashReleased(cashReleased);
	}

	@Override
	public BinTransaction getBinRecordForCashReleassed(BinTransaction txn) {
		return cashPaymentJpaDao.getBinRecordForCashReleassed(txn);
	}

	@Override
	public boolean updateBinMaster(BinMaster binMaster) {
		return cashPaymentJpaDao.updateBinMaster(binMaster);
	}

	@Override
	public int deleteDataFromBinTxn(BinTransaction txnBin) {
		return cashPaymentJpaDao.deleteDataFromBinTxn(txnBin);
	}

	@Override
	@Transactional
	public boolean updateBinTxn(BinTransaction binTransaction) {
		if (binTransaction.getPendingBundleRequest().compareTo(BigDecimal.ZERO) < 0
				|| binTransaction.getPendingBundleRequest().compareTo(binTransaction.getReceiveBundle()) > 0) {
			LOG.info("updateBinTxn " + binTransaction);
			throw new BaseGuiException("Can not update vault: icmc id " + binTransaction.getIcmcId() + " and bin "
					+ binTransaction.getBinNumber());
		}
		return cashPaymentJpaDao.updateBinTxn(binTransaction);
	}

	@Override
	public List<Sas> getORVReport(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getORVReport(icmcId, sDate, eDate);
	}

	@Override
	public List<CITCRAVendor> getVendor() {
		return cashPaymentJpaDao.getVendor();
	}

	@Override
	public List<String> getCustodianName(String vendor) {
		return cashPaymentJpaDao.getCustodianName(vendor);
	}

	@Override
	public List<String> getVehicleNumber(String vendor) {
		return cashPaymentJpaDao.getVehicleNumber(vendor);
	}

	@Override
	public List<Tuple> getRecordForSummary(BigInteger icmcId) {
		return cashPaymentJpaDao.getRecordForSummary(icmcId);
	}

	@Override
	public List<Sas> solIdForSASPayment(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.solIdForSASPayment(icmcId, sDate, eDate);
	}

	@Override
	public List<Sas> solIdForSASPaymentAccepted(BigInteger icmcId, Calendar sDate, Calendar eDate, Set<Long> pList) {
		return cashPaymentJpaDao.solIdForSASPaymentAccepted(icmcId, sDate, eDate, pList);
	}

	@Override
	public Sas sasPaymentDetails(long id) {
		return cashPaymentJpaDao.sasPaymentDetails(id);
	}

	@Override
	public Sas sasRecordByID(long id) {
		return cashPaymentJpaDao.sasRecordByID(id);
	}

	@Override
	public boolean updateSASForSASRelease(Sas sas) {
		return cashPaymentJpaDao.updateSASForSASRelease(sas);
	}

	@Override
	public boolean createSASAllocationForORVPayment(SASAllocation sasAllocation) {
		return cashPaymentJpaDao.createSASAllocationForORVPayment(sasAllocation);
	}

	@Override
	public boolean processCRAAllocation(CRA cra, User user) {
		for (CRAAllocation craAllocation : cra.getCraAllocations()) {
			craAllocation.setIcmcId(cra.getIcmcId());
		}
		this.findBinAndProcessCRAAllocation(user, cra);
		return true;
	}

	@Override
	public boolean updateCRAAndCRAAllocation(CRA cra) {
		this.cancelationSetCRACraAllocation(cra);
		cashPaymentJpaDao.updateCRAAndCRAAllocation(cra);
		return true;
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
		cashPaymentJpaDao.updateEditOtherBankAndOtherBankAllocation(icmcId, id);

	}

	@Override
	public List<CRAAccountDetail> getVendorAndMSPName(BigInteger icmcId) {
		return cashPaymentJpaDao.getVendorAndMSPName(icmcId);
	}

	@Override
	public String getAccountNumberByMSPName(String mspName, String vendor, BigInteger icmcId) {
		return cashPaymentJpaDao.getAccountNumberByMSPName(mspName, vendor, icmcId);
	}

	@Transactional
	private void findBinAndProcessOtherBankAllocation(User user, OtherBank otherBank) {
		List<OtherBankAllocation> eligibleOtherBankRequestListAll = new ArrayList<>();
		List<BinTransaction> binTransactionList = new ArrayList<>();
		BigDecimal bundleAvailableInVault = BigDecimal.ZERO;

		for (OtherBankAllocation otherBankAllocation : otherBank.getOtherBankAllocations()) {
			List<BinTransaction> txnList = cashPaymentJpaDao.getBinNumListForOtherBank(otherBankAllocation,
					otherBankAllocation.getCurrencyType());
			// List<OtherBankAllocation> otherBankListDb =
			// cashPaymentJpaDao.getBinFromOtherBank(otherBankAllocation);
			List<OtherBankAllocation> eligibleOtherBankRequestList = UtilityJpa.getBinForOtherBankRequest(txnList,
					otherBankAllocation, otherBankAllocation.getBundle(), otherBankAllocation.getTotal(),
					otherBankAllocation.getCurrencyType(), user);
			binTransactionList.addAll(txnList);

			for (BinTransaction binTxnTemp : txnList) {
				bundleAvailableInVault = bundleAvailableInVault.add(binTxnTemp.getReceiveBundle());
			}
			if (eligibleOtherBankRequestList == null || eligibleOtherBankRequestList.isEmpty()
					|| bundleAvailableInVault.compareTo(otherBankAllocation.getBundle()) < 0) {
				throw new BaseGuiException(
						"Required bundles are not available for Denomination:" + otherBankAllocation.getDenomination()
								+ " and Category :" + otherBankAllocation.getCurrencyType());
			}
			eligibleOtherBankRequestListAll.addAll(eligibleOtherBankRequestList);
		}
		otherBank.setOtherBankAllocations(eligibleOtherBankRequestListAll);
		cashPaymentJpaDao.createOtherBankPayment(otherBank);

		for (BinTransaction btx : binTransactionList) {
			this.updateBinTxn(btx);
		}
	}

	@Override
	@Transactional
	public boolean processOtherBankAllocation(OtherBank otherBank, User user) {
		for (OtherBankAllocation otherBankAllocation : otherBank.getOtherBankAllocations()) {
			otherBankAllocation.setIcmcId(otherBank.getIcmcId());
		}
		this.findBinAndProcessOtherBankAllocation(user, otherBank);
		return true;
	}

	@Override
	public List<CRA> getCRARecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<CRA> craList = new ArrayList<>();
		List<CRA> craListTemp = cashPaymentJpaDao.getCRARecord(icmcId, sDate, eDate);
		for (CRA cra : craListTemp) {
			BigDecimal totalValue = BigDecimal.ZERO;
			BigDecimal acceptTotalValue = BigDecimal.ZERO;
			BigDecimal multiplyValue = new BigDecimal(1000);
			/*
			 * List<ProcessBundleForCRAPayment>
			 * procesBundleCra=cashPaymentJpaDao.getProcessBundleCRARecord(cra.
			 * getId());
			 */
			int craIdCount = 0;
			for (CRAAllocation craAllocation : cra.getCraAllocations()) {
				BigDecimal denomination = new BigDecimal(craAllocation.getDenomination());
				if (craAllocation.getTotal() != null && craAllocation.getStatus().equals(OtherStatus.PROCESSED)) {
					acceptTotalValue = craAllocation.getTotal()
							.subtract(multiplyValue.multiply(denomination).multiply(craAllocation.getForward()));
					totalValue = totalValue.add(acceptTotalValue);

				}
				if (craAllocation.getStatus().equals(OtherStatus.RELEASED)) {
					/*
					 * acceptTotalValue =
					 * totalValue.subtract(multiplyValue.multiply(denomination).
					 * multiply(craAllocation.getPendingRequestedBundle()));
					 * totalValue = totalValue.add(acceptTotalValue);
					 */
					List<ProcessBundleForCRAPayment> procesBundleCra = cashPaymentJpaDao
							.getProcessBundleCRARecord(cra.getId());
					// pendingTotalValue=pendingTotalValue.add(multiplyValue.multiply(denomination).multiply(craAllocation.getPendingRequestedBundle()));
					if (procesBundleCra != null && craIdCount == 0) {
						++craIdCount;
						for (ProcessBundleForCRAPayment proceCra : procesBundleCra) {
							BigDecimal processDenomination = new BigDecimal(proceCra.getDenomination());
							acceptTotalValue = proceCra.getBundle().multiply(multiplyValue)
									.multiply(processDenomination);
							totalValue = totalValue.add(acceptTotalValue);
						}
					}
				}
			}
			// acceptTotalValue=totalValue.subtract(pendingTotalValue);
			cra.setTotalValue(totalValue);
			craList.add(cra);
		}
		return craList;
	}

	@Override
	public List<CRA> getCRARequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<CRA> craList = new ArrayList<>();
		List<CRA> craListTemp = cashPaymentJpaDao.getCRARequestAcceptRecord(icmcId, sDate, eDate);
		for (CRA cra : craListTemp) {
			BigDecimal totalValue = BigDecimal.ZERO;
			for (CRAAllocation craAllocation : cra.getCraAllocations()) {
				if (craAllocation.getTotal() != null) {
					totalValue = totalValue.add(craAllocation.getTotal());
				}
			}
			cra.setTotalValue(totalValue);
			craList.add(cra);
		}
		return craList;
	}

	@Override
	public List<OtherBank> getOtherBankPaymentRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<OtherBank> otherBankList = new ArrayList<>();
		List<OtherBank> otherBankListTemp = cashPaymentJpaDao.getOtherBankPaymentRecord(icmcId, sDate, eDate);
		for (OtherBank otherBank : otherBankListTemp) {
			BigDecimal totalValue = BigDecimal.ZERO;
			for (OtherBankAllocation otherBankAllocation : otherBank.getOtherBankAllocations()) {
				if (otherBankAllocation.getTotal() != null) {
					totalValue = totalValue.add(otherBankAllocation.getTotal());
				}
			}
			otherBank.setTotalValue(totalValue);
			otherBankList.add(otherBank);
		}
		return otherBankList;
	}

	@Override
	public List<OtherBank> getOtherBankPaymentRequestAcceptRecord(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<OtherBank> otherBankList = new ArrayList<>();
		List<OtherBank> otherBankListTemp = cashPaymentJpaDao.getOtherBankPaymentRequestAcceptRecord(icmcId, sDate,
				eDate);
		for (OtherBank otherBank : otherBankListTemp) {
			BigDecimal totalValue = BigDecimal.ZERO;
			for (OtherBankAllocation otherBankAllocation : otherBank.getOtherBankAllocations()) {
				if (otherBankAllocation.getTotal() != null) {
					totalValue = totalValue.add(otherBankAllocation.getTotal());
				}
			}
			otherBank.setTotalValue(totalValue);
			otherBankList.add(otherBank);
		}
		return otherBankList;
	}

	@Override
	public List<Tuple> getRecordORVVoucher(String solId, Calendar sDate, Calendar eDate, BigInteger icmcId) {
		return cashPaymentJpaDao.getRecordORVVoucher(solId, sDate, eDate, icmcId);
	}

	@Override
	public List<Tuple> getRecordORVVoucherById(long id, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getRecordORVVoucherById(id, sDate, eDate);
	}

	@Override
	public List<Sas> getSolId(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getSolId(icmcId, sDate, eDate);
	}

	@Override
	public List<Sas> getSasRecordById(BigInteger icmcId, Long[] sasId) {
		return cashPaymentJpaDao.getSasRecordById(icmcId, sasId);
	}

	@Override
	public List<Sas> getAcceptSolId(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getAcceptSolId(icmcId, sDate, eDate);
	}

	@Override
	public Sas getFileName(BigInteger icmcId) {
		return cashPaymentJpaDao.getFileName(icmcId);
	}

	@Override
	public Sas getSameDayFileName(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		Sas sasFileName = cashPaymentJpaDao.getSameDayFileName(icmcId, sDate, eDate);
		return sasFileName;
	}

	@Override
	public boolean updateBinTransactionForEmpty(BinTransaction binTransaction) {
		return cashPaymentJpaDao.updateBinTransactionForEmpty(binTransaction);
	}

	@Override
	public List<CRAAllocation> getCRAForAccept(BigInteger icmcId) {
		return cashPaymentJpaDao.getCRAForAccept(icmcId);
	}

	@Override
	public long updateCRAStatus(CRAAllocation cra) {
		return cashPaymentJpaDao.updateCRAStatus(cra);
	}

	@Override
	public long updateOtherBankStatus(OtherBankAllocation otherBank) {
		return cashPaymentJpaDao.updateOtherBankStatus(otherBank);
	}

	@Override
	public List<OtherBankAllocation> getOtherBankForAccept(BigInteger icmcId) {
		return cashPaymentJpaDao.getOtherBankForAccept(icmcId);
	}

	@Override
	public OtherBank getOtherBankRecordById(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.getOtherBankRecordById(icmcId, id);
	}

	@Override
	public boolean updateBinTransactionAndCRAAllocation(CRAAllocation craAllocation) {
		BinTransaction binTx = new BinTransaction();
		binTx.setIcmcId(craAllocation.getIcmcId());
		binTx.setBinNumber(craAllocation.getBinNumber());

		BinTransaction btx = cashPaymentJpaDao.getPendingBundleRequestForCRA(binTx);

		BigDecimal pendingBundleFromBinTx = btx.getPendingBundleRequest();

		BigDecimal bundleFromCRAAllocation = craAllocation.getBundle();

		BigDecimal updateBinTxPendingBundle = pendingBundleFromBinTx.subtract(bundleFromCRAAllocation);
		binTx.setPendingBundleRequest(updateBinTxPendingBundle);
		boolean updateBinTxn = cashPaymentJpaDao.updateBinTransactionForCRA(binTx);
		if (!updateBinTxn) {
			throw new RuntimeException("Error while Updating Bin Transaction, Please try again");
		}
		craAllocation.setBundle(updateBinTxPendingBundle);
		boolean updateCRAAllocationBundle = cashPaymentJpaDao.updateBundleInCRAAllocation(craAllocation);
		if (!updateCRAAllocationBundle) {
			throw new RuntimeException("Error while Updating CRA Allocation, Please try again");
		}
		return true;
	}

	@Override
	public List<BinTransaction> getBinForCRAPayment(BinTransaction binTxn) {
		return cashPaymentJpaDao.getBinForCRAPayment(binTxn);
	}

	@Override
	public boolean ForwardCRAPayment(ForwardBundleForCRAPayment forwardBundlePayment) {
		return cashPaymentJpaDao.ForwardCRAPayment(forwardBundlePayment);
	}

	@Override
	public List<Tuple> craRequestSummary(long id) {
		return cashPaymentJpaDao.craRequestSummary(id);
	}

	@Transactional
	private void findBinAndProcessCRAPayment(User user, List<CRAAllocation> craAllocationList) {
		List<CRAAllocation> eligibleCRARequestList = new ArrayList<>();
		List<CRAAllocation> craAllocationDbList = new ArrayList<>();
		List<BinTransaction> txnList = new ArrayList<>();

		for (CRAAllocation craAllocation : craAllocationList) {

			BigDecimal availableBundleInVault = BigDecimal.ZERO;

			CRAAllocation craAllocationDb = cashPaymentJpaDao.findCraAllocation(craAllocation);
			craAllocationDbList.add(craAllocationDb);

			if (!craAllocationDb.getStatus().equals(OtherStatus.PROCESSED)) {

				if (craAllocation.getVault().compareTo(BigDecimal.ZERO) > 0) {
					craAllocation.setIcmcId(user.getIcmcId());
					List<BinTransaction> txnListLocal = cashPaymentJpaDao.getBinNumListForCRA(craAllocation,
							craAllocation.getCurrencyType());
					for (BinTransaction binTx : txnListLocal) {
						availableBundleInVault = availableBundleInVault
								.add(binTx.getReceiveBundle().subtract(UtilityJpa.getPendingBundleRequest(binTx)));
					}
					if (txnListLocal.size() != 0 && availableBundleInVault.compareTo(craAllocation.getVault()) >= 0) {
						List<CRAAllocation> eligibleCRARequestListLocal = UtilityJpa.getBinForCRARequest(txnListLocal,
								craAllocation, craAllocationDb, user);
						txnList.addAll(txnListLocal);
						eligibleCRARequestList.addAll(eligibleCRARequestListLocal);
					} else {
						throw new BaseGuiException("Required number of bundles for " + craAllocation.getDenomination()
								+ " denomination and " + craAllocation.getCurrencyType()
								+ " category are not available in the Vault");
					}
				}

				if (craAllocation.getForward().compareTo(BigDecimal.ZERO) > 0) {
					CRAAllocation craAllocationForward = UtilityJpa.getForwardProcessBundleForCRAPayment(craAllocation,
							craAllocationDb, user);
					eligibleCRARequestList.add(craAllocationForward);
				}
			} else {
				throw new BaseGuiException("This request has been already processed.");
			}
		}
		cashPaymentJpaDao.insertInCRAPayment(eligibleCRARequestList);
		cashPaymentJpaDao.updateCRAAllocationForCRA(craAllocationDbList);
		cashPaymentJpaDao.updateBinTrasactionForCRA(txnList);

	}

	@Override
	public boolean processCRAPayment(List<CRAAllocation> craAllocation, User user) {
		this.findBinAndProcessCRAPayment(user, craAllocation);
		return true;
	}

	@Override
	public List<CRA> getRecordFromCRA(BigInteger icmcId) {
		return cashPaymentJpaDao.getRecordFromCRA(icmcId);
	}

	@Override
	public List<Tuple> getSoiledSummary(BigInteger icmcId) {
		return cashPaymentJpaDao.getSoiledSummary(icmcId);
	}

	@Override
	public List<BinTransaction> getBundleFromBinTxnToCompareForSoiled(BigInteger icmcId, Integer denomination,
			CurrencyType currencyType) {
		return cashPaymentJpaDao.getBundleFromBinTxnToCompare(icmcId, denomination, currencyType);
	}

	@Override
	public List<BinTransaction> getBinNumListForSoiled(SoiledRemittanceAllocation soiled, CurrencyType type) {
		return cashPaymentJpaDao.getBinNumListForSoiled(soiled, type);
	}

	@Transactional
	@Override
	public SoiledRemittanceAllocation processSoiledBoxPreparation(
			List<SoiledRemittanceAllocation> eligibleIndentRequestList, SoiledRemittanceAllocation soiledData,
			User user) {
		boolean isSaved = false;
		SoiledRemittanceAllocation soiled = new SoiledRemittanceAllocation();
		soiled.setIcmcId(soiledData.getIcmcId());
		soiled.setDenomination(soiledData.getDenomination());
		soiled.setBundle(soiledData.getRequestBundle());
		// soiled.setBox("BOX");
		soiled.setBox("BOX:" + user.getIcmcId() + Instant.now().toEpochMilli());
		// soiled.setStatus(OtherStatus.REQUESTED);
		// soiled.setPendingBundle(soiledData.getRequestBundle());

		for (SoiledRemittanceAllocation soiledAllocation : eligibleIndentRequestList) {
			isSaved = this.updateBinTransactionForSoiled(soiledAllocation.getIcmcId(),
					soiledAllocation.getDenomination(), soiledAllocation.getBinNumber(), soiledAllocation.getBundle(),
					user);
		}
		if (isSaved) {
			isSaved = this.insertSoiledBoxInBinTx(eligibleIndentRequestList, user, soiled.getBox());
			soiled = this.getQRForSoiledBox(soiled);

		} else if (!isSaved) {
			throw new RuntimeException("you can not processing request");
		}

		// SoiledRemittanceAllocation soiledQRPath =
		// this.getQRForSoiledBox(soiled);

		return soiled;
	}

	@Transactional
	private SoiledRemittanceAllocation getQRForSoiledBox(SoiledRemittanceAllocation soiled) {
		soiled.setFilepath(getQRForSoiled(soiled));
		this.createSoiledAfterBoxCreation(soiled);
		return soiled;
	}

	private String getPath(String filepath) {
		return filepath;
	}

	private String getQRForSoiled(SoiledRemittanceAllocation soiled) {
		String filepath = qrCodeGen.generateQRForSoiled(soiled);
		return getPath(filepath);
	}

	private boolean updateBinTransactionForSoiled(BigInteger icmcId, Integer denomination, String bin,
			BigDecimal bundle, User user) {

		BinTransaction txnBean = processingRoomService.getBinFromTransaction(bin.trim(), user.getIcmcId(),
				denomination);

		if (txnBean != null && txnBean.getReceiveBundle() != null
				&& txnBean.getReceiveBundle().compareTo(BigDecimal.ZERO) > 0) {

			BigDecimal balanceBundle = txnBean.getReceiveBundle().subtract(bundle);

			if (balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
				txnBean.setReceiveBundle(BigDecimal.ZERO);
				txnBean.setPendingBundleRequest(BigDecimal.ZERO);
				txnBean.setStatus(BinStatus.EMPTY);
				txnBean.setCashSource(null);
				txnBean.setUpdateBy(user.getId());
				txnBean.setUpdateTime(Calendar.getInstance());
				boolean count = this.updateBinTxn(txnBean);
				// this.deleteEmptyBinFromBinTransaction(user.getIcmcId(),bin);
				if (count) {
					BinMaster binMater = new BinMaster();
					binMater.setBinNumber(bin);
					binMater.setIcmcId(user.getIcmcId());
					this.updateBinMaster(binMater);
				}
			} else if (balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
				txnBean.setReceiveBundle(balanceBundle);
				this.updateBinTxn(txnBean);
			}
		}
		return true;
	}

	@Transactional
	private boolean insertSoiledBoxInBinTx(List<SoiledRemittanceAllocation> eligibleIndentRequestList, User user,
			String box) {
		Calendar now = Calendar.getInstance();
		BigDecimal receiveBundle = BigDecimal.ZERO;

		BinTransaction binTx = new BinTransaction();

		binTx.setIcmcId(user.getIcmcId());
		binTx.setBinNumber(box);
		binTx.setStatus(BinStatus.FULL);
		binTx.setInsertBy(user.getId());
		binTx.setUpdateBy(user.getId());
		binTx.setInsertTime(now);
		binTx.setUpdateTime(now);
		binTx.setBinCategoryType(BinCategoryType.BOX);
		binTx.setCashType(CashType.NOTES);
		binTx.setPendingBundleRequest(new BigDecimal(0));

		for (SoiledRemittanceAllocation soiled : eligibleIndentRequestList) {
			binTx.setDenomination(soiled.getDenomination());
			receiveBundle = receiveBundle.add(soiled.getBundle());
			binTx.setBinType(soiled.getCurrencyType());
		}
		binTx.setReceiveBundle(receiveBundle);
		binTx.setMaxCapacity(binTx.getReceiveBundle());

		return cashPaymentJpaDao.insertSoiledBoxInBinTx(binTx);
	}

	@Override
	public boolean createSoiledAfterBoxCreation(SoiledRemittanceAllocation soiled) {
		return cashPaymentJpaDao.createSoiledAfterBoxCreation(soiled);
	}

	@Override
	public BinTransaction getBoxRecordForCashReleassed(BinTransaction binTxn) {
		return cashPaymentJpaDao.getBoxRecordForCashReleassed(binTxn);
	}

	@Override
	public List<CRAAllocation> craPaymentDetailForAccept(BigInteger icmcId) {
		return cashPaymentJpaDao.craPaymentDetailForAccept(icmcId);
	}

	@Override
	public boolean updateCRAAllocationStatus(CRAAllocation craAllocation) {
		return cashPaymentJpaDao.updateCRAAllocationStatus(craAllocation);
	}

	@Override
	@Transactional
	public boolean processCRAPaymentRequest(long id, String bin, BigDecimal bundle, User user, long craId) {
		CRAAllocation craAllocation = this.getCRAAllocationDataById(id, user.getIcmcId());
		BinTransaction txnBean = this.getBinFromTransaction(bin.trim(), user.getIcmcId());
		Calendar now = Calendar.getInstance();
		LOG.info("processCRAPaymentRequest  craAllocation " + craAllocation);
		LOG.info("processCRAPaymentRequest  txnBean" + txnBean);
		if (craAllocation != null && txnBean != null && txnBean.getReceiveBundle() != null
				&& txnBean.getReceiveBundle().compareTo(BigDecimal.ZERO) > 0 && craAllocation.getBundle() != null
				&& craAllocation.getBundle().compareTo(BigDecimal.ZERO) > 0) {

			BigDecimal balanceBundle = txnBean.getReceiveBundle().subtract(craAllocation.getBundle());
			// craAllocation.setStatus(OtherStatus.PROCESSED);// 0 means ready
			// for machine
			// allocation
			craAllocation.setUpdateTime(now);
			boolean isCRAAllocationUpdate = this.updateCRAAllocationStatus(craAllocation);
			CRA cra = new CRA();
			cra.setUpdateTime(now);
			cra.setId(craId);
			cra.setIcmcId(user.getIcmcId());
			this.updateCRAOtherStatus(cra);
			if (isCRAAllocationUpdate && balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
				// txnBean.setRcvBundle(BigDecimal.ZERO);
				txnBean.setReceiveBundle(BigDecimal.ZERO);
				txnBean.setPendingBundleRequest(BigDecimal.ZERO);
				txnBean.setStatus(BinStatus.EMPTY);
				txnBean.setVerified(YesNo.NULL);
				txnBean.setUpdateBy(user.getId());
				txnBean.setUpdateTime(now);

				craAllocation.setStatus(OtherStatus.ACCEPTED);

				// update time
				// int count = this.deleteDataFromBinTxn(txnBean);
				// call merge
				boolean count = this.updateBinTxn(txnBean);
				// this.deleteEmptyBinFromBinTransaction(user.getIcmcId(), bin);
				LOG.info("if count " + count);
				if (count) {
					BinMaster binMater = new BinMaster();
					binMater.setBinNumber(bin);
					binMater.setIcmcId(user.getIcmcId());
					isCRAAllocationUpdate = this.updateBinMaster(binMater);
				}
			} else if (isCRAAllocationUpdate && balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
				txnBean.setReceiveBundle(balanceBundle);
				txnBean.setUpdateTime(now);
				LOG.info("txnBean.getPendingBundleRequest() " + txnBean.getPendingBundleRequest());
				LOG.info("bundle " + bundle);
				txnBean.setPendingBundleRequest(txnBean.getPendingBundleRequest().subtract(bundle));
				isCRAAllocationUpdate = this.updateBinTxn(txnBean);
				LOG.info("else if txnBean " + txnBean);
				LOG.info("else if getpendingbundle " + txnBean.getPendingBundleRequest());
				LOG.info("else if bundle " + bundle);
			}

		}
		return true;
	}

	@Override
	public BinTransaction getBinFromTransaction(String bin, BigInteger icmcId) {
		return cashPaymentJpaDao.getBinFromTransaction(bin, icmcId);
	}

	@Override
	public CRAAllocation getCRAAllocationDataById(long id, BigInteger icmcId) {
		return cashPaymentJpaDao.getCRAAllocationDataById(id, icmcId);
	}

	@Override
	public void updateBinTxnForSoiledBox(BinTransaction binTxn) {
		cashPaymentJpaDao.updateBinTxnForSoiledBox(binTxn);
	}

	@Override
	public List<CRAAllocation> craPaymentDetails(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.craPaymentDetails(icmcId, id);
	}

	@Override
	public List<ProcessBundleForCRAPayment> forwardedCraPaymentDetails(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.forwardedCraPaymentDetails(icmcId, id);
	}

	@Override
	public List<CRA> solIdForCRAPayment(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.solIdForCRAPayment(icmcId, sDate, eDate);
	}

	@Override
	public List<DiversionORV> diversionListForRbiOrderNo(BigInteger icmcId) {
		return cashPaymentJpaDao.diversionListForRbiOrderNo(icmcId);
	}

	@Override
	public List<DiversionORVAllocation> dorvPaymentDetails(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.dorvPaymentDetails(icmcId, id);
	}

	@Override
	public List<OtherBank> bankNameFromOtherBank(BigInteger icmcId) {
		return cashPaymentJpaDao.bankNameFromOtherBank(icmcId);
	}

	@Override
	public List<OtherBankAllocation> otherBankPaymentDetails(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.otherBankPaymentDetails(icmcId, id);
	}

	@Override
	public boolean updateOtherBankAndOtherBankAllocation(BigInteger icmcId, long id) {
		cashPaymentJpaDao.updateOtherBankForPayment(icmcId, id);
		cashPaymentJpaDao.updateOtherBankAllocationForPayment(icmcId, id);
		return true;
	}

	@Override
	public boolean updateCRAAndCRAAllocation(BigInteger icmcId, long id) {
		cashPaymentJpaDao.updateCRAForPayment(icmcId, id);
		cashPaymentJpaDao.updateCRAAllocationForPayment(icmcId, id);
		return true;
	}

	@Override
	public List<BinTransaction> getCoinsListForSas(SASAllocation sas) {
		return cashPaymentJpaDao.getCoinsListForSas(sas);
	}

	@Override
	public boolean updateDiversionAndDiversionAllocation(BigInteger icmcId, long id) {
		cashPaymentJpaDao.updateDiversionForPayment(icmcId, id);
		cashPaymentJpaDao.updateDiversionAllocationForPayment(icmcId, id);
		return true;
	}

	@Override
	public CoinsSequence getCoinsSequenceForDeduction(BigInteger icmcId, int denomination) {
		return cashPaymentJpaDao.getCoinsSequenceForDeduction(icmcId, denomination);
	}

	@Override
	public BinTransaction getBinRecordForAcceptInVault(BinTransaction txn) {
		return cashPaymentJpaDao.getBinRecordForAcceptInVault(txn);
	}

	@Override
	public long updateOtherBankStatus(OtherBank otherBank) {
		return cashPaymentJpaDao.updateOtherBankStatus(otherBank);
	}

	@Override
	public long updateDorvStatus(DiversionORV dorv) {
		return cashPaymentJpaDao.updateDorvStatus(dorv);
	}

	@Override
	public List<Tuple> getRecordCoinsForSummary(BigInteger icmcId) {
		return cashPaymentJpaDao.getRecordCoinsForSummary(icmcId);
	}

	@Override
	public long updateCoinsSequence(CoinsSequence coinsSequence) {
		return cashPaymentJpaDao.updateCoinsSequence(coinsSequence);
	}

	@Override
	public List<SoiledRemittance> getRemittanceOrderNo(BigInteger icmcId) {
		return cashPaymentJpaDao.getRemittanceOrderNo(icmcId);
	}

	@Override
	public List<SoiledRemittanceAllocation> soiledRemittancePaymentDetails(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.soiledRemittancePaymentDetails(icmcId, id);
	}

	@Override
	public boolean updateSoiledAndSoiledAllocation(BigInteger icmcId, long id) {
		cashPaymentJpaDao.updateSoiledForPayment(icmcId, id);
		cashPaymentJpaDao.updateSoiledAllocationForPayment(icmcId, id);
		return true;
	}

	@Override
	public void updateInsertSoiledAndSoiledAllocation(BigInteger icmcId, long id) {
		cashPaymentJpaDao.updateInsertSoiledAndSoiledAllocation(icmcId, id);

	}

	@Override
	public long updateCRAOtherStatus(CRA cra) {
		return cashPaymentJpaDao.updateCRAOtherStatus(cra);
	}

	@Override
	public List<Tuple> getBranchOutRecordFromSAS(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getBranchOutRecordFromSAS(icmcId, sDate, eDate);
	}

	@Override
	public List<Sas> getRecordFromSAS(long id) {
		return cashPaymentJpaDao.getRecordFromSAS(id);
	}

	@Override
	public long updateSASStatus(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.updateSASStatus(icmcId, id);
	}

	@Override
	public void updateSASStatusForSASFile(BigInteger icmcId, int status) {
		cashPaymentJpaDao.updateSASStatusForSASFile(icmcId, status);
	}

	@Override
	public long updateSASForceHandoverStatus(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.updateSASForceHandoverStatus(icmcId, id);
	}

	@Override
	public List<SASAllocation> getAllAcceptedFromSASAllocation(BigInteger icmcId) {
		return cashPaymentJpaDao.getAllAcceptedFromSASAllocation(icmcId);
	}

	@Override
	public List<SASAllocation> getAllTodayAcceptedFromSASAllocation(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getAllTodayAcceptedFromSASAllocation(icmcId, sDate, eDate);
	}

	@Override
	public SASAllocation getRequestedFromSASAllocation(BigInteger icmcId, Calendar sDate, Calendar eDate,
			Long parentId) {
		return cashPaymentJpaDao.getRequestedFromSASAllocation(icmcId, sDate, eDate, parentId);
	}

	@Override
	public String getICMCName(BigInteger icmcId) {
		return cashPaymentJpaDao.getICMCName(icmcId);
	}

	@Override
	public Long cancelSAS(User user, List<Sas> sasList) {
		Long count = (long) 0;
		for (Sas sas : sasList) {
			count += cashPaymentJpaDao.cancelSAS(user, sas);
		}
		return count;
	}

	@Override
	public void processDiversionORVCancellation(User user, Long id) {
		List<BinTransaction> eligibleTxnList = new ArrayList<>();
		DiversionORV dorv = cashPaymentJpaDao.getDiversionRecordForCancellation(user, id);
		for (DiversionORVAllocation dorvAllocation : dorv.getDiversionAllocations()) {
			BinTransaction binTxn = cashPaymentJpaDao.getBinFromTransaction(dorvAllocation.getBinNumber(),
					user.getIcmcId());
			binTxn = UtilityJpa.setPendingBundleForORVCancellation(user, binTxn, dorvAllocation.getBundle());
			eligibleTxnList.add(binTxn);
		}
		if (eligibleTxnList != null) {
			this.updateDorvAndDorvAllocation(user, id);
			for (BinTransaction binTransaction : eligibleTxnList) {
				this.updateBinTxn(binTransaction);
			}
		}
	}

	private void updateDorvAndDorvAllocation(User user, Long id) {
		Long count = cashPaymentJpaDao.updateDorvForCancellation(user, id);
		if (count > 0) {
			cashPaymentJpaDao.updateDorvAllocationForCancellation(user, id);
		}
	}

	@Override
	public void processOtherBankPaymentCancellation(User user, Long id) {
		List<BinTransaction> eligibleTxnList = new ArrayList<>();
		OtherBank otherBank = cashPaymentJpaDao.getOtherBankRecordForCancellation(user, id);
		for (OtherBankAllocation otherBankAllocation : otherBank.getOtherBankAllocations()) {
			BinTransaction binTxn = cashPaymentJpaDao.getBinFromTransaction(otherBankAllocation.getBinNumber(),
					user.getIcmcId());
			binTxn = UtilityJpa.setPendingBundleForORVCancellation(user, binTxn, otherBankAllocation.getBundle());
			eligibleTxnList.add(binTxn);
		}
		if (eligibleTxnList != null) {
			this.updateOtherBankAndOtherBankAllocation(user, id);
			for (BinTransaction binTransaction : eligibleTxnList) {
				this.updateBinTxn(binTransaction);
			}
		}
	}

	private void updateOtherBankAndOtherBankAllocation(User user, Long id) {
		Long count = cashPaymentJpaDao.updateOtherBankForCancellation(user, id);
		if (count > 0) {
			cashPaymentJpaDao.updateOtherBankAllocationForCancellation(user, id);
		}
	}

	@Override
	public void processCRAPaymentCancellation(User user, Long id) {
		CRA cra = cashPaymentJpaDao.getCRARecordForCancellation(user, id);
		Long count = cashPaymentJpaDao.updateCRAafterCancellation(user, id);
		if (count > 0) {
			for (CRAAllocation craAllocation : cra.getCraAllocations()) {
				this.updateCRAandCRAAllocation(user, id, craAllocation);
			}
		}
	}

	private void updateCRAandCRAAllocation(User user, Long id, CRAAllocation craAllocation) {
		cashPaymentJpaDao.updateCRAAllocationAfterCancellation(user, id);
	}

	@Override
	public List<Tuple> machineInputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination) {
		return cashPaymentJpaDao.machineInputReport(icmcId, sDate, eDate, denomination);
	}

	@Override
	public List<Tuple> machineOutputReport(BigInteger icmcId, Calendar sDate, Calendar eDate, int denomination,
			CurrencyType type) {
		return cashPaymentJpaDao.machineOutputReport(icmcId, sDate, eDate, denomination, type);
	}

	@Override
	public List<SoiledRemittanceAllocation> getAcceptedListForSoiledRemitAlloc(BigInteger icmcId) {
		return cashPaymentJpaDao.getAcceptedListForSoiledRemitAlloc(icmcId);
	}

	@Override
	public List<BinTransaction> getPreparedSoiledBoxes(BigInteger icmcId) {
		List<BinTransaction> preparedBoxList = new ArrayList<>();
		List<BinTransaction> tempPreparedBoxList = cashPaymentJpaDao.getPreparedSoiledBoxes(icmcId);
		for (BinTransaction binTxn : tempPreparedBoxList) {
			BigDecimal value = binTxn.getReceiveBundle().multiply(BigDecimal.valueOf(binTxn.getDenomination()))
					.multiply(BigDecimal.valueOf(1000));
			binTxn.setValue(value);
			preparedBoxList.add(binTxn);
		}
		return preparedBoxList;
	}

	@Override
	public List<BinTransaction> getPreparedInActiveSoiledBoxes(BigInteger icmcId) {
		List<BinTransaction> preparedBoxList = new ArrayList<>();
		List<BinTransaction> tempPreparedBoxList = cashPaymentJpaDao.getPreparedInActiveSoiledBoxes(icmcId);
		for (BinTransaction binTxn : tempPreparedBoxList) {
			BigDecimal value = binTxn.getReceiveBundle().multiply(BigDecimal.valueOf(binTxn.getDenomination()))
					.multiply(BigDecimal.valueOf(1000));
			binTxn.setValue(value);
			preparedBoxList.add(binTxn);
		}
		return preparedBoxList;
	}

	@Override
	public List<ProcessBundleForCRAPayment> processBundleForCRAPayment(BigInteger icmcId) {
		return cashPaymentJpaDao.processBundleForCRAPayment(icmcId);
	}

	@Override
	public long updateProcessbundleForCRAPayment(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.updateProcessbundleForCRAPayment(icmcId, id);
	}

	@Override
	public long updateSasAllocationForCancelBranchPayment(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.updateSasAllocationForCancelBranchPayment(icmcId, id);
	}

	@Override
	public long updateSasForCancelBranchPayment(BigInteger icmcId, long id) {
		return cashPaymentJpaDao.updateSasForCancelBranchPayment(icmcId, id);
	}

	@Override
	public long updateBinTransactionCancelBranchPayment(BigInteger icmcId, BinTransaction binTransaction) {
		return cashPaymentJpaDao.updateBinTransactionCancelBranchPayment(icmcId, binTransaction);
	}

	@Override
	public BinTransaction getPendingBundleFromDB(BigInteger icmcId, BinTransaction binTxn) {
		return cashPaymentJpaDao.getPendingBundleFromDB(icmcId, binTxn);
	}

	@Override
	public boolean history(List<History> historyList) {
		return cashPaymentJpaDao.history(historyList);
	}

	@Override
	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getIBITForIRV(icmcId, sDate, eDate);
	}

	@Override
	public String getLinkBranchSolID(long icmcId) {
		return cashPaymentJpaDao.getLinkBranchSolID(icmcId);
	}

	@Override
	public String getServicingICMC(String solId) {
		return cashPaymentJpaDao.getServicingICMC(solId);
	}

	@Override
	public List<Tuple> getBranchPaymentTotal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getBranchPaymentTotal(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getCraPaymentTotalProcessed(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getCraPaymentTotalProcessed(icmcId, sDate, eDate);
	}

	@Override
	public List<Tuple> getCraPaymentTotalReleased(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getCraPaymentTotalReleased(icmcId, sDate, eDate);
	}

	@Override
	public boolean saveDataInBinRegister(BinRegister binRegister) {
		return cashPaymentJpaDao.saveDataInBinRegister(binRegister);
	}

	@Override
	public String getSRNumberBySolId(String solId) {
		return cashPaymentJpaDao.getSRNumberBySolId(solId);
	}

	@Override
	public String getSRNumberById(long Id) {
		return cashPaymentJpaDao.getSRNumberById(Id);
	}

	@Override
	public long removeBranchFromSAS(Sas sas) {
		return cashPaymentJpaDao.removeBranchFromSAS(sas);
	}

	@Override
	public List<SoiledRemittanceAllocation> TRReports(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.TRReports(icmcId, sDate, eDate);
	}

	@Override
	public CRA getCRADetailById(long id) {
		return cashPaymentJpaDao.getCRADetailById(id);
	}

	@Override
	public void updateSasAllocationForCancelEditBranchPayment(BigInteger icmcId, long id) {
		cashPaymentJpaDao.updateSasAllocationForCancelEditBranchPayment(icmcId, id);
	}

	@Override
	public List<SASAllocation> getAllAcceptedFromSASAllocation1(BigInteger icmcId) {
		return cashPaymentJpaDao.getAllAcceptedFromSASAllocation1(icmcId);
	}

	@Override
	public List<Sas> getORVReport1(BigInteger icmcId, Calendar sDate, Calendar eDate, Long sasId) {
		return cashPaymentJpaDao.getORVReport1(icmcId, sDate, eDate, sasId);
	}

	@Override
	@Transactional
	public List<Tuple> getSASAllocationRecordFromTuple(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getSASAllocationRecordFromTuple(icmcId, sDate, eDate);
	}

	@Override
	public List<SASAllocation> getDataToUpdateBinTransaction(BigInteger icmcId, Long parentId) {
		return cashPaymentJpaDao.getDataToUpdateBinTransaction(icmcId, parentId);
	}

	@Override
	public BinTransaction getDataFromBinTransactionForSasAllocationCancel(BigInteger icmcId, String binNumber,
			Integer denomination) {
		return cashPaymentJpaDao.getDataFromBinTransactionForSasAllocationCancel(icmcId, binNumber, denomination);
	}

	@Override
	public BinTransaction getDataFromBinTransactionForSoiledAllocationCancel(BigInteger icmcId, String box,
			Integer denomination) {
		return cashPaymentJpaDao.getDataFromBinTransactionForSasAllocationCancel(icmcId, box, denomination);
	}

	@Override
	public boolean updateBinTransactionPendingBundleForCashPaymentCancel(BinTransaction binTransaction) {
		if (binTransaction.getPendingBundleRequest().compareTo(BigDecimal.ZERO) < 0
				|| binTransaction.getPendingBundleRequest().compareTo(binTransaction.getReceiveBundle()) > 0) {
			LOG.info("updateBinTxn " + binTransaction);
			throw new BaseGuiException("Can not update vault: icmc id " + binTransaction.getIcmcId() + " and bin "
					+ binTransaction.getBinNumber());
		}
		return cashPaymentJpaDao.updateBinTransactionPendingBundleForCashPaymentCancel(binTransaction);
	}

	// sahabuddin
	@Override
	public long updateOrvStatus1(long id) {
		return cashPaymentJpaDao.updateOrvStatus1(id);
	}

	// sahabuddin
	@Override
	public long updateOrvAllocationStatus1(long id) {
		return cashPaymentJpaDao.updateOrvAllocationStatus1(id);
	}

	@Override
	public DiversionORV getDiversionORVById(Long id) {
		return cashPaymentJpaDao.getDiversionORVById(id);
	}

	@Override
	public boolean updateSASStatusAccept(Sas sas) {
		return cashPaymentJpaDao.updateSASStatusAccept(sas);

	}

	@Override
	public ProcessBundleForCRAPayment getCRAId(BigInteger icmcId) {
		return cashPaymentJpaDao.getCRAId(icmcId);
	}

	@Override
	public List<ProcessBundleForCRAPayment> getListCRAId(BigInteger icmcId) {
		return cashPaymentJpaDao.getListCRAId(icmcId);
	}

	@Override
	public List<CRA> valueFromCRA(BigInteger icmcId, long craId) {
		return cashPaymentJpaDao.valueFromCRA(icmcId, craId);
	}

	@Override
	@Transactional
	public SASAllocation updateSasIndent(SASAllocation sasAccept, User user) {
		List<SASAllocation> sasList = this.getSasAllocationByBinNumberBundle(sasAccept);
		if (sasList.isEmpty()) {
			sasList = this.getSasAllocationByBinNumber(sasAccept);
		}
		// sasAccept.setUpdateTime(Calendar.getInstance());
		LOG.info("sasList  " + sasList);
		LOG.info("sasAccept  " + sasAccept);
		boolean count = false;
		int countDelete = 0;
		BinTransaction binTxn = new BinTransaction();
		binTxn.setBinNumber(sasAccept.getBinNumber());
		binTxn.setDenomination(sasAccept.getDenomination());
		binTxn.setReceiveBundle(sasAccept.getBundle());
		binTxn.setBinType(sasAccept.getBinType());
		binTxn.setIcmcId(user.getIcmcId());
		binTxn.setUpdateTime(Calendar.getInstance());

		if (sasAccept.getCashType() == CashType.NOTES) {
			binTxn.setCashType(CashType.NOTES);
			binTxn = this.getBinRecordForCashReleassed(binTxn);
		}
		if (sasAccept.getCashType() == CashType.COINS) {
			binTxn.setCashType(CashType.COINS);
			binTxn = this.getBinRecordForCashReleassed(binTxn);
			try {
				CoinsSequence coinsSequence = this.getCoinsSequenceForDeduction(user.getIcmcId(),
						sasAccept.getDenomination());
				BigDecimal bundleFromUI = sasAccept.getBundle();
				int sequence = ((coinsSequence.getSequence()) - (bundleFromUI.intValue()));
				coinsSequence.setSequence(sequence);
				this.updateCoinsSequence(coinsSequence);
			} catch (Exception e) {
				throw new BaseGuiException("coinsSequence is not defined Plz first you defined before Request");
			}
		}
		LOG.info("sasAccept.getBundle() " + sasAccept.getBundle());
		LOG.info("binTxn.getPendingBundleRequest() " + binTxn.getPendingBundleRequest());
		LOG.info("binTxn.getReceiveBundle() " + binTxn.getReceiveBundle());
		BigDecimal balanceBundle = binTxn.getReceiveBundle().subtract(sasAccept.getBundle());
		BigDecimal pendingBundle = binTxn.getPendingBundleRequest().subtract(sasAccept.getBundle());
		LOG.info("balanceBundle " + balanceBundle);
		LOG.info("pendingBundle " + pendingBundle);
		if (pendingBundle.compareTo(balanceBundle) > 0 || pendingBundle.compareTo(BigDecimal.ZERO) < 0) {
			throw new BaseGuiException(
					"can not accept pending bundle is" + pendingBundle + " and receiving bundle is " + balanceBundle);
		}
		if (balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
			LOG.info("bin transaction received bundle  0 before empty " + binTxn);
			if (sasAccept.getCashType() == CashType.COINS) {
				countDelete = this.deleteDataFromBinTxn(binTxn);
				LOG.info("countDelete " + countDelete);

			} else {
				binTxn.setReceiveBundle(BigDecimal.ZERO);
				binTxn.setPendingBundleRequest(BigDecimal.ZERO);
				binTxn.setStatus(BinStatus.EMPTY);
				binTxn.setCashSource(null);
				binTxn.setVerified(YesNo.NULL);
				binTxn.setUpdateBy(user.getId());
				binTxn.setUpdateTime(Calendar.getInstance());
				// binTxn.setUpdateTime(now);
				count = this.updateBinTxn(binTxn);
				LOG.info("CashType.notes count " + count);
				LOG.info("bin transaction received bundle  0 after empty " + binTxn);
			}
			if (count) {
				BinMaster binMaster = new BinMaster();
				binMaster.setBinNumber(sasAccept.getBinNumber());
				binMaster.setIcmcId(user.getIcmcId());
				this.updateBinMaster(binMaster);
			}
		} else if (balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
			binTxn.setReceiveBundle(balanceBundle);
			binTxn.setPendingBundleRequest(pendingBundle);
			binTxn.setUpdateTime(Calendar.getInstance());
			count = this.updateBinTxn(binTxn);
			LOG.info("received bundle greter 0  count " + count);
			LOG.info("bin transaction received bundle greter 0 " + binTxn);
		}
		if (count || countDelete == 1) {
			for (SASAllocation sasSet : sasList) {
				sasAccept.setId(sasSet.getId());
				if (sasSet.getParentId() != null) {
					this.updateSASStatus(user.getIcmcId(), sasSet.getParentId());
				}

				this.updateSASStatus(sasAccept);
			}
		}
		// Bin Register
		BinRegister binRegister = new BinRegister();
		binRegister.setWithdrawalBundle(sasAccept.getBundle());
		binRegister.setDenomination(sasAccept.getDenomination());
		binRegister.setBinNumber(sasAccept.getBinNumber());
		binRegister.setIcmcId(user.getIcmcId());
		binRegister.setDepositOrWithdrawal("WITHDRAWAL");
		binRegister.setReceiveBundle(BigDecimal.ZERO);
		binRegister.setType(sasAccept.getBinType().value());
		binRegister.setInsertTime(Calendar.getInstance());
		binRegister.setUpdateTime(Calendar.getInstance());
		binRegister.setInsertBy(user.getId());
		binRegister.setUpdateBy(user.getId());
		binRegister.setIcmcId(user.getIcmcId());
		this.saveDataInBinRegister(binRegister);

		return sasAccept;
	}

	@Override
	@Transactional
	public void processForAcceptanceOutwardDiversion(DiversionORVAllocation diversionORV, Calendar now, User user) {
		DiversionORVAllocation dorv = new DiversionORVAllocation();
		dorv.setId(diversionORV.getId());
		dorv.setIcmcId(user.getIcmcId());
		dorv.setUpdateTime(Calendar.getInstance());
		this.updateDorvStatus(dorv);
		// cashPaymentService.updateDiversionAndDiversionAllocation(user.getIcmcId(),
		// diversionORV.getDiversionOrvId());
		DiversionORV diversion = new DiversionORV();
		diversion.setIcmcId(user.getIcmcId());
		diversion.setId(diversionORV.getDiversionOrvId());
		diversion.setUpdateTime(Calendar.getInstance());
		this.updateDorvStatus(diversion);
		BinTransaction binTxn = new BinTransaction();
		binTxn.setBinNumber(diversionORV.getBinNumber());
		binTxn.setDenomination(diversionORV.getDenomination());
		binTxn.setReceiveBundle(diversionORV.getBundle());
		binTxn.setIcmcId(user.getIcmcId());
		binTxn = this.getBinRecordForAcceptInVault(binTxn);

		BigDecimal balanceBundle = binTxn.getReceiveBundle().subtract(diversionORV.getBundle());
		if (balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
			// boolean count1 =
			// cashPaymentService.updateBinTransactionForEmpty(binTxn);
			binTxn.setReceiveBundle(BigDecimal.ZERO);
			binTxn.setPendingBundleRequest(BigDecimal.ZERO);
			binTxn.setStatus(BinStatus.EMPTY);
			binTxn.setCashSource(null);
			binTxn.setVerified(YesNo.NULL);
			binTxn.setUpdateBy(user.getId());
			boolean count = this.updateBinTxn(binTxn);
			// this.deleteEmptyBinFromBinTransaction(user.getIcmcId(),
			// diversionORV.getBinNumber());
			if (count) {
				BinMaster binMaster = new BinMaster();
				binMaster.setBinNumber(diversionORV.getBinNumber());
				binMaster.setIcmcId(user.getIcmcId());
				this.updateBinMaster(binMaster);
			}
		} else if (balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
			binTxn.setReceiveBundle(balanceBundle);
			binTxn.setPendingBundleRequest(binTxn.getPendingBundleRequest().subtract(diversionORV.getBundle()));
			binTxn.setUpdateTime(now);
			this.updateBinTxn(binTxn);
		}

		// Bin Register
		BinRegister binRegister = new BinRegister();
		binRegister.setWithdrawalBundle(diversionORV.getBundle());
		binRegister.setDenomination(diversionORV.getDenomination());
		binRegister.setBinNumber(diversionORV.getBinNumber());
		binRegister.setIcmcId(user.getIcmcId());
		binRegister.setDepositOrWithdrawal("WITHDRAWAL");
		binRegister.setType(diversionORV.getCurrencyType().value());
		binRegister.setReceiveBundle(BigDecimal.ZERO);
		binRegister.setInsertTime(now);
		binRegister.setUpdateTime(now);
		binRegister.setInsertBy(user.getId());
		binRegister.setUpdateBy(user.getId());
		binRegister.setIcmcId(user.getIcmcId());
		this.saveDataInBinRegister(binRegister);
		// Close bin Register
	}

	@Override
	@Transactional
	public void processForAcceptanceOtherBankPayment(OtherBankAllocation otherBankAllocation, Calendar now, User user) {
		OtherBankAllocation otherBank = new OtherBankAllocation();
		OtherBank otherBankStatus = new OtherBank();
		otherBank.setUpdateTime(Calendar.getInstance());
		otherBank.setIcmcId(user.getIcmcId());
		otherBank.setId(otherBankAllocation.getId());
		this.updateOtherBankStatus(otherBank);

		otherBankStatus.setIcmcId(user.getIcmcId());
		otherBankStatus.setId(otherBankAllocation.getOtherBankId());
		otherBankStatus.setUpdateTime(Calendar.getInstance());
		this.updateOtherBankStatus(otherBankStatus);

		// Calendar now=Calendar.getInstance();
		BinTransaction binTxn = new BinTransaction();
		binTxn.setBinNumber(otherBankAllocation.getBinNumber());
		binTxn.setDenomination(otherBankAllocation.getDenomination());
		binTxn.setReceiveBundle(otherBankAllocation.getBundle());
		binTxn.setIcmcId(user.getIcmcId());
		binTxn.setUpdateTime(now);
		binTxn = this.getBinRecordForAcceptInVault(binTxn);

		BigDecimal balanceBundle = binTxn.getReceiveBundle().subtract(otherBankAllocation.getBundle());

		if (balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
			// boolean count1 =
			// cashPaymentService.updateBinTransactionForEmpty(binTxn);
			binTxn.setReceiveBundle(BigDecimal.ZERO);
			binTxn.setPendingBundleRequest(BigDecimal.ZERO);
			binTxn.setStatus(BinStatus.EMPTY);
			binTxn.setCashSource(null);
			binTxn.setVerified(YesNo.NULL);
			binTxn.setUpdateBy(user.getId());
			// binTxn.setUpdateTime(Calendar.getInstance());
			binTxn.setUpdateTime(now);
			boolean count = this.updateBinTxn(binTxn);
			// this.deleteEmptyBinFromBinTransaction(user.getIcmcId(),
			// otherBankAllocation.getBinNumber());
			if (count) {
				BinMaster binMaster = new BinMaster();
				binMaster.setBinNumber(otherBankAllocation.getBinNumber());
				binMaster.setIcmcId(user.getIcmcId());
				this.updateBinMaster(binMaster);
			}
		} else if (balanceBundle.compareTo(BigDecimal.ZERO) > 0) {
			binTxn.setUpdateTime(now);
			binTxn.setReceiveBundle(balanceBundle);
			binTxn.setPendingBundleRequest(binTxn.getPendingBundleRequest().subtract(otherBankAllocation.getBundle()));
			this.updateBinTxn(binTxn);
		}

		// Bin Register

		BinRegister binRegister = new BinRegister();
		binRegister.setWithdrawalBundle(otherBankAllocation.getBundle());
		binRegister.setDenomination(otherBankAllocation.getDenomination());
		binRegister.setBinNumber(otherBankAllocation.getBinNumber());
		binRegister.setIcmcId(user.getIcmcId());
		binRegister.setDepositOrWithdrawal("WITHDRAWAL");
		binRegister.setType(otherBankAllocation.getCurrencyType().value());
		binRegister.setReceiveBundle(BigDecimal.ZERO);
		binRegister.setInsertTime(now);
		binRegister.setUpdateTime(now);
		binRegister.setInsertBy(user.getId());
		binRegister.setUpdateBy(user.getId());
		binRegister.setIcmcId(user.getIcmcId());
		this.saveDataInBinRegister(binRegister);
		// Close bin Register

	}

	@Override
	public void processForAcceptanceSoiledIndent(SoiledRemittanceAllocation soiledRemittance, User user) {
		SoiledRemittance soiledRemittanceTemp = new SoiledRemittance();
		SoiledRemittanceAllocation soiled = new SoiledRemittanceAllocation();
		BinTransaction binTxn = new BinTransaction();

		soiledRemittanceTemp.setId(soiledRemittance.getSoiledRemittanceId());
		soiledRemittance.setUpdateTime(Calendar.getInstance());

		soiled.setWeight(soiledRemittance.getWeight());
		soiled.setId(soiledRemittance.getId());
		soiled.setUpdateTime(Calendar.getInstance());

		long count = this.updateSoiledStatus(soiled, soiledRemittanceTemp);

		binTxn.setUpdateTime(Calendar.getInstance());
		binTxn.setBinCategoryType(BinCategoryType.BOX);
		binTxn.setBinType(soiledRemittance.getCurrencyType());
		binTxn.setDenomination(soiledRemittance.getDenomination());
		binTxn.setReceiveBundle(soiledRemittance.getBundle());
		binTxn.setIcmcId(user.getIcmcId());
		binTxn = this.getBoxRecordForCashReleassed(binTxn);

		BigDecimal balanceBundle = binTxn.getReceiveBundle().subtract(soiledRemittance.getBundle());
		BigDecimal pendingBundle = binTxn.getPendingBundleRequest().subtract(soiledRemittance.getBundle());
		binTxn.setReceiveBundle(balanceBundle);
		binTxn.setPendingBundleRequest(pendingBundle);
		this.updateBinTxn(binTxn);
		if (balanceBundle.compareTo(BigDecimal.ZERO) == 0) {
			this.updateBinTxnForSoiledBox(binTxn);
		}
		if (count == 0) {
			throw new BaseGuiException("Process while process Indent Request");
		}

	}

	@Override
	public Boolean deleteEmptyBinFromBinTransaction(BigInteger icmcId, String binNumber) {
		LOG.info("CashPaymentServiceImp deleteEmptyBinFromBinTransaction icmcId " + icmcId + " binNumber " + binNumber);
		return cashPaymentJpaDao.deleteEmptyBinFromBinTransaction(icmcId, binNumber);
	}

	@Override
	public List<Tuple> getAllShrinkWrapBundleFromBranchReceipt(BigInteger icmcId) {
		return cashPaymentJpaDao.getAllShrinkWrapBundleFromBranchReceipt(icmcId);
	}

	@Override
	public List<BranchReceipt> getShrinkWrapBundleByDenomination(int denomination, BigInteger icmcId,
			BinCategoryType binCategoryType) {
		return cashPaymentJpaDao.getShrinkWrapBundleByDenomination(denomination, icmcId, binCategoryType);
	}

	@Override
	public long processForCancelBranchPayment(BigInteger icmcId, long id) {
		this.updateSasForCancelBranchPayment(icmcId, id);
		this.updateSasAllocationForCancelEditBranchPayment(icmcId, id);
		List<SASAllocation> sasAllocation = this.getDataToUpdateBinTransaction(icmcId, id);
		BinTransaction binTransaction = new BinTransaction();
		BranchReceipt branchReceipt = new BranchReceipt();
		BigDecimal finalBundle = new BigDecimal(0);
		for (SASAllocation sasAloData : sasAllocation) {
			binTransaction = this.getDataFromBinTransactionForSasAllocationCancel(icmcId, sasAloData.getBinNumber(),
					sasAloData.getDenomination());
			finalBundle = binTransaction.getPendingBundleRequest().subtract(sasAloData.getBundle());
			binTransaction.setIcmcId(icmcId);
			binTransaction.setBinNumber(sasAloData.getBinNumber());
			binTransaction.setDenomination(sasAloData.getDenomination());
			binTransaction.setPendingBundleRequest(finalBundle);
			branchReceipt.setIcmcId(icmcId);
			branchReceipt.setBin(sasAloData.getBinNumber());
			branchReceipt.setDenomination(sasAloData.getDenomination());
			this.updateBinTransactionPendingBundleForCashPaymentCancel(binTransaction);
			this.updatebBranchReceiptForBranchPaymentCancel(branchReceipt);
		}
		return id;
	}

	@Override
	public boolean updatebBranchReceiptForBranchPaymentCancel(BranchReceipt branchReceipt) {
		return cashPaymentJpaDao.updatebBranchReceiptForBranchPaymentCancel(branchReceipt);
	}

	@Override
	public BranchReceipt checkBinOrBoxFromBranchReceipt(BigInteger icmcId, int denomination, BigDecimal bundle,
			String binNumber) {
		return cashPaymentJpaDao.checkBinOrBoxFromBranchReceipt(icmcId, denomination, bundle, binNumber);
	}

	@Override
	public List<Tuple> getSoiledSummary(BigInteger icmcId, CurrencyType currencyType) {
		return cashPaymentJpaDao.getSoiledSummary(icmcId, currencyType);
	}

	@Override
	public List<SoiledRemittanceAllocation> getSoiledForAccept(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		return cashPaymentJpaDao.getSoiledForAccept(icmcId, sDate, eDate);
	}
}