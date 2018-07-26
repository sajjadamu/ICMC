/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.controller.BinDashboardController;
import com.chest.currency.dao.BinDashboardDaoImpl;
import com.chest.currency.entity.model.AuditorIndent;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinRegister;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BinTransactionBOD;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CashTransfer;
import com.chest.currency.entity.model.ChestMaster;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DateRange;
import com.chest.currency.entity.model.Discrepancy;
import com.chest.currency.entity.model.DiscrepancyAllocation;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.DiversionORVAllocation;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.History;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.Mutilated;
import com.chest.currency.entity.model.OtherBankAllocation;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.RegionSummary;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.Summary;
import com.chest.currency.entity.model.TrainingRegister;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DenominationType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.jpa.dao.BinDashBoardJpaDaoImpl;
import com.chest.currency.jpa.dao.UserAdministrationJpaDao;
import com.chest.currency.jpa.dao.UserAdministrationJpaDaoImpl;
import com.chest.currency.util.UtilityMapper;
import com.ibm.icu.text.SimpleDateFormat;
import com.mysema.query.Tuple;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

@Service
@Transactional
public class BinDashboardServiceImpl implements BinDashboardService {

	@Autowired
	BinDashboardDaoImpl binDashboardDao;

	@Autowired
	UserAdministrationJpaDao userAdministrationJpaDao;

	@Autowired
	protected BinDashBoardJpaDaoImpl binDashboardJpaDao;

	private static final Logger LOG = LoggerFactory.getLogger(BinDashboardController.class);

	@Override
	public List<BinMaster> getBinNumAndColorCode(BigInteger icmcId) {
		List<BinMaster> binList = binDashboardJpaDao.getBinNumAndColorCode(icmcId);
		return binList;
	}

	@Override
	public List<BinTransaction> getAvailableCapacity(String bin, BigInteger icmcId) {
		List<BinTransaction> binList = binDashboardJpaDao.getAvailableCapacity(bin, icmcId);
		return binList;
	}

	@Override
	public List<Tuple> getRecordForSummary(BigInteger icmcId) {
		List<Tuple> binList = binDashboardJpaDao.getRecordForSummary(icmcId);
		return binList;
	}

	@Override
	public List<ICMC> getICMCName() {
		List<ICMC> icmcList = binDashboardJpaDao.getICMCName();
		return icmcList;
	}

	@Override
	public List<BinTransaction> searchBins(CurrencyType binType, int denomination, BigInteger icmcId) {
		List<BinTransaction> binList = binDashboardJpaDao.searchBins(binType, denomination, icmcId);
		return binList;
	}

	@Override
	public List<BinTransaction> getAllNonEmptyBins(BigInteger icmcId) {
		List<BinTransaction> binList = binDashboardJpaDao.getAllNonEmptyBins(icmcId);
		return binList;
	}

	@Override
	public List<BinTransaction> getAllNonEmptyBins(BigInteger icmcId, CurrencyType cType) {
		List<BinTransaction> binList = binDashboardJpaDao.getAllNonEmptyBins(icmcId, cType);
		return binList;
	}

	@Override
	public List<BinMaster> viewBinMaster(BigInteger icmcId) {
		List<BinMaster> binList = binDashboardJpaDao.viewBinMaster(icmcId);
		return binList;
	}

	@Override
	public boolean insertDataInBinMaster(BinMaster binMaster) {
		boolean isAllsuccess = false;
		try {
			isAllsuccess = binDashboardJpaDao.insertDataInBinMaster(binMaster);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllsuccess;
	}

	@Override
	public List<BinTransaction> getRecordForFIFO(BigInteger icmcId) {
		List<BinTransaction> fifoList = binDashboardJpaDao.getRecordForFIFO(icmcId);
		return fifoList;
	}

	@Override
	public List<Process> getProcessListAtm(BigInteger icmcId) {

		return binDashboardJpaDao.getProcessListAtm(icmcId);
	}

	@Override
	public List<Process> getProcessListIssuable(BigInteger icmcId) {
		return binDashboardJpaDao.getProcessListIssuable(icmcId);
	}

	@Override
	public List<Process> getProcessListSoiled(BigInteger icmcId) {
		return binDashboardJpaDao.getProcessListSoiled(icmcId);
	}

	@Override
	public List<MachineAllocation> getMachineAllocationForMachineWiseStatus(BigInteger icmcId) {
		List<MachineAllocation> machineList = binDashboardJpaDao.getMachineAllocationForMachineWiseStatus(icmcId);
		return machineList;
	}

	@Override
	public boolean UploadBinMaster(List<BinMaster> list, BinMaster binMaster) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = binDashboardJpaDao.UploadBinMaster(list, binMaster);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;

	}

	@Override
	public List<BinTransaction> recordForDailyBinRecon(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<BinTransaction> binTxnListForDailyBinRecon = binDashboardJpaDao.recordForDailyBinRecon(icmcId, sDate,
				eDate);
		return binTxnListForDailyBinRecon;
	}

	@Override
	public float getReceiveBundleForDailyReconBin(BinTransaction binTransaction) {
		float bundle = binDashboardJpaDao.getReceiveBundleForDailyReconBin(binTransaction);
		return bundle;
	}

	@Override
	public List<Summary> getZoneWiseSummaryList() {
		List<Summary> zoneWiseSummaryList = binDashboardJpaDao.getZoneWiseSummaryList();
		return zoneWiseSummaryList;
	}

	@Override
	public List<Tuple> getRecordCoinsForSummary(BigInteger icmcId) {
		List<Tuple> forCoinsList = binDashboardJpaDao.getRecordCoinsForSummary(icmcId);
		return forCoinsList;
	}

	@Override
	public BinMaster isValidBin(BigInteger icmcId, String binNumber) {
		BinMaster dbBinName = binDashboardJpaDao.isValidBin(icmcId, binNumber);
		return dbBinName;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYes(BigInteger icmcId) {
		List<BinTransaction> binNumFromBinTransactionList = binDashboardJpaDao
				.getBinNumAndTypeFromBinTransactionForVefiedYes(icmcId);
		return binNumFromBinTransactionList;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBox(BigInteger icmcId) {
		List<BinTransaction> boxNumFromBinTransactionList = binDashboardJpaDao
				.getBinNumAndTypeFromBinTransactionForVefiedYesBox(icmcId);
		return boxNumFromBinTransactionList;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBin(BigInteger icmcId) {
		List<BinTransaction> binNumFromBinTransactionList = binDashboardJpaDao
				.getBinNumAndTypeFromBinTransactionForVefiedYesBin(icmcId);
		return binNumFromBinTransactionList;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedYesBag(BigInteger icmcId) {
		List<BinTransaction> bagNumFromBinTransactionList = binDashboardJpaDao
				.getBinNumAndTypeFromBinTransactionForVefiedYesBag(icmcId);
		return bagNumFromBinTransactionList;
	}

	@Override
	public List<BinTransaction> getBinNumAndTypeFromBinTransactionForVefiedNo(BigInteger icmcId) {
		List<BinTransaction> binNumFromBinTransactionList = binDashboardJpaDao
				.getBinNumAndTypeFromBinTransactionForVefiedNo(icmcId);
		return binNumFromBinTransactionList;
	}

	@Override
	public List<Tuple> getOpeningBalanceForIO2Report(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType) {
		List<Tuple> totalSumForTO2 = binDashboardJpaDao.getOpeningBalanceForTO2Report(icmcId, sDate, eDate, cashType);
		return totalSumForTO2;
	}

	@Override
	public List<Tuple> getOpeningBalanceForIO2ReportFromIndent(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType) {
		List<Tuple> totalSumForTO2 = binDashboardJpaDao.getOpeningBalanceForIO2ReportFromIndent(icmcId, sDate, eDate,
				cashType);
		return totalSumForTO2;
	}

	@Override
	public List<Tuple> getCoinsOpeningBalanceForIO2Report(BigInteger icmcId, CashType cashType) {
		List<Tuple> totalCoinsSumForIO2 = binDashboardJpaDao.getCoinsOpeningBalanceForIO2Report(icmcId, cashType);
		return totalCoinsSumForIO2;
	}

	@Override
	public List<Tuple> getSoiledBalanceForEOD(BigInteger icmcId, CashType cashType) {
		List<Tuple> totalSoiledSumForIO2 = binDashboardJpaDao.getSoiledBalanceForEOD(icmcId, cashType);
		return totalSoiledSumForIO2;
	}

	@Override
	public List<Tuple> getRemittanceReceivedForFresh(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> totalSumForRemittance = binDashboardJpaDao.getRemittanceReceivedForFresh(icmcId, sDate, eDate);
		return totalSumForRemittance;
	}

	@Override
	public boolean insertInBinTxnBOD(BinTransactionBOD binTransactionBod) {
		boolean isSaved = binDashboardJpaDao.insertInBinTxnBOD(binTransactionBod);
		return isSaved;
	}

	@Override
	public List<BinTransactionBOD> getDataFromBinTransactionBOD(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CashType cashType, DateRange dateRange) {
		List<BinTransactionBOD> dataFromBinTxnBOD = null;
		Calendar pEDate = Calendar.getInstance();
		if (dateRange.getFromDate() != null) {
			pEDate = (Calendar) dateRange.getFromDate().clone();
			Date formDate = pEDate.getTime();
			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
			Calendar calendarDate = Calendar.getInstance();
			Date currentDate = calendarDate.getTime();
			if (fmt.format(formDate).compareTo(fmt.format(currentDate)) == 0) {
				dataFromBinTxnBOD = this.getCurrentDataFromBinTransactionBOD(icmcId, sDate, eDate, cashType, dateRange);
			} else {
				dataFromBinTxnBOD = this.getPreviousDataFromBinTransactionBOD(icmcId, sDate, eDate, cashType,
						dateRange);
			}
		} else {
			dataFromBinTxnBOD = this.getCurrentDataFromBinTransactionBOD(icmcId, sDate, eDate, cashType, dateRange);
		}
		return dataFromBinTxnBOD;
	}

	private List<BinTransactionBOD> getPreviousDataFromBinTransactionBOD(BigInteger icmcId, Calendar sDate,
			Calendar eDate, CashType cashType, DateRange dateRange) {
		Calendar pSDate = Calendar.getInstance();
		Calendar pEDate = Calendar.getInstance();
		List<BinTransactionBOD> dataFromBinTxnBOD = null;
		if (dateRange.getFromDate() != null) {
			pSDate = (Calendar) dateRange.getFromDate().clone();
			pEDate = (Calendar) dateRange.getFromDate().clone();
			Date date = pEDate.getTime();
			SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
			Calendar calendarDate = Calendar.getInstance();
			Date currentDate = calendarDate.getTime();
			if (fmt.format(date).compareTo(fmt.format(currentDate)) != 0) {
				pSDate.add(Calendar.DATE, -1);
				pEDate.add(Calendar.DATE, -1);
			}
			pSDate.set(Calendar.HOUR, 0);
			pSDate.set(Calendar.HOUR_OF_DAY, 0);
			pSDate.set(Calendar.MINUTE, 0);
			pSDate.set(Calendar.SECOND, 0);
			pSDate.set(Calendar.MILLISECOND, 0);

			pEDate.set(Calendar.HOUR, 24);
			pEDate.set(Calendar.HOUR_OF_DAY, 23);
			pEDate.set(Calendar.MINUTE, 59);
			pEDate.set(Calendar.SECOND, 59);
			pEDate.set(Calendar.MILLISECOND, 999);

			dataFromBinTxnBOD = binDashboardJpaDao.getDataFromBinTxnBODForPreviousDate(icmcId, pSDate, pEDate,
					cashType);
			int count = 0;
			while (dataFromBinTxnBOD.size() == 0) {
				pSDate.add(Calendar.DATE, -1);
				pEDate.add(Calendar.DATE, -1);
				dataFromBinTxnBOD = binDashboardJpaDao.getDataFromBinTxnBODForPreviousDate(icmcId, pSDate, pEDate,
						cashType);
				++count;
				if (count == 15) {
					break;
				}
			}
			pSDate = null;
			pEDate = null;
		}
		return dataFromBinTxnBOD;
	}

	private List<BinTransactionBOD> getCurrentDataFromBinTransactionBOD(BigInteger icmcId, Calendar sDate,
			Calendar eDate, CashType cashType, DateRange dateRange) {
		Calendar pSDate = Calendar.getInstance();
		Calendar pEDate = Calendar.getInstance();
		List<BinTransactionBOD> dataFromBinTxnBOD = null;
		dataFromBinTxnBOD = binDashboardJpaDao.getDataFromBinTransactionBOD(icmcId, sDate, eDate, cashType);
		LOG.info("getDataFromBinTransactionBOD dataFromBinTxnBOD " + dataFromBinTxnBOD);
		Calendar bodDate = null;
		for (BinTransactionBOD bod : dataFromBinTxnBOD) {
			bodDate = bod.getUpdateTime();
		}
		Calendar txnDate = binDashboardJpaDao.getDataFromBinTxnLastEntry(icmcId);
		LOG.info("getDataFromBinTxnLastEntry txnDate " + txnDate);
		if (txnDate != null) {
			txnDate.set(Calendar.HOUR, 0);
			txnDate.set(Calendar.HOUR_OF_DAY, 0);
			txnDate.set(Calendar.MINUTE, 0);
			txnDate.set(Calendar.SECOND, 0);
			txnDate.set(Calendar.MILLISECOND, 0);

		}
		if (bodDate != null) {
			bodDate.set(Calendar.HOUR, 0);
			bodDate.set(Calendar.HOUR_OF_DAY, 0);
			bodDate.set(Calendar.MINUTE, 0);
			bodDate.set(Calendar.SECOND, 0);
			bodDate.set(Calendar.MILLISECOND, 0);
		}
		List<BinTransactionBOD> secondLastMigration = binDashboardJpaDao.getDataFromBinTxnBODForLastEntry(icmcId,
				pSDate, pEDate, cashType);
		LOG.info("sDate.getTime() " + sDate.getTime());
		LOG.info("secondLastMigration " + secondLastMigration);

		if (bodDate != null && txnDate != null && bodDate.getTime().compareTo(txnDate.getTime()) == 0
				&& bodDate.getTime().compareTo(sDate.getTime()) == 0 && secondLastMigration.size() != 0) {
			dataFromBinTxnBOD = null;
			LOG.info("bodDate.getTime() " + bodDate.getTime());
			dataFromBinTxnBOD = secondLastMigration.stream().collect(Collectors.toList());
			LOG.info(
					"secondLastMigration.stream().collect(Collectors.toList()) dataFromBinTxnBOD " + dataFromBinTxnBOD);
		}

		return dataFromBinTxnBOD;
	}

	@Override
	public List<Tuple> getRemittanceSentForSoiled(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> remittanceSentForSoiledList = binDashboardJpaDao.getRemittanceSentForSoiled(icmcId, sDate, eDate);
		return remittanceSentForSoiledList;
	}

	@Override
	public List<Tuple> getDepositForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> depositForBranchList = binDashboardJpaDao.getDepositForBranch(icmcId, sDate, eDate);
		return depositForBranchList;
	}

	@Override
	public List<Tuple> getDepositFromBranch(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		List<Tuple> depositForBranchList = binDashboardJpaDao.getDepositFromBranch(icmcId, sDate, eDate, currencyType);
		return depositForBranchList;
	}

	@Override
	public List<Tuple> getProcessFromProcessingOutPut(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		List<Tuple> processFromProcessingOutPut = binDashboardJpaDao.getProcessFromProcessingOutPut(icmcId, sDate,
				eDate, currencyType);
		return processFromProcessingOutPut;
	}

	@Override
	public List<Tuple> getDepositForDSB(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> depositForDSBList = binDashboardJpaDao.getDepositForDSB(icmcId, sDate, eDate);
		return depositForDSBList;
	}

	@Override
	public List<Tuple> getDepositForDiversion(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> depositForDiversionList = binDashboardJpaDao.getDepositForDiversion(icmcId, sDate, eDate);
		return depositForDiversionList;
	}

	@Override
	public List<Tuple> getDepositForOtherBank(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> depositForOtherBankList = binDashboardJpaDao.getDepositForOtherBank(icmcId, sDate, eDate);
		return depositForOtherBankList;
	}

	@Override
	public List<Tuple> getDepositForIndent(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> depositForIndent = binDashboardJpaDao.getDepositForIndent(icmcId, sDate, eDate);
		return depositForIndent;
	}

	@Override
	public List<Tuple> getWithdrawalForDiversion(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> diversionList = binDashboardJpaDao.getWithdrawalForDiversion(icmcId, sDate, eDate);
		return diversionList;
	}

	@Override
	public List<Tuple> getWithdrawalForCRA(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> craList = binDashboardJpaDao.getWithdrawalForCRA(icmcId, sDate, eDate);
		return craList;
	}

	@Override
	public List<Tuple> getWithdrawalForProcessCRA(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> craList = binDashboardJpaDao.getWithdrawalForProcessCRA(icmcId, sDate, eDate);
		return craList;
	}

	@Override
	public List<Tuple> getWithdrawalForOtherBank(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> otherBankList = binDashboardJpaDao.getWithdrawalForOtherBank(icmcId, sDate, eDate);
		return otherBankList;
	}

	@Override
	public List<Tuple> getWithdrawalForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> sasList = binDashboardJpaDao.getWithdrawalForBranch(icmcId, sDate, eDate);
		return sasList;
	}

	@Override
	public List<Tuple> getWithdrawalFromBranch(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		List<Tuple> sasList = binDashboardJpaDao.getWithdrawalFromBranch(icmcId, sDate, eDate, currencyType);
		return sasList;
	}

	@Override
	public List<Tuple> getSoiledNotes(BigInteger icmcId) {
		List<Tuple> soiledNotesList = binDashboardJpaDao.getSoiledNotes(icmcId);
		return soiledNotesList;
	}

	@Override
	public List<Tuple> getAdditionalInfoFreshNotes(BigInteger icmcId) {
		List<Tuple> additionFreshNotes = binDashboardJpaDao.getAdditionalInfoFreshNotes(icmcId);
		return additionFreshNotes;
	}

	@Override
	public List<Tuple> getMachineAllocationSummary(BigInteger icmcId) {
		List<Tuple> pendingBungleListFromMachineAllocation = binDashboardJpaDao.getMachineAllocationSummary(icmcId);
		return pendingBungleListFromMachineAllocation;
	}

	@Override
	public List<Tuple> getCurrentMachineAllocationSummary(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> pendingBungleListFromMachineAllocation = binDashboardJpaDao
				.getCurrentMachineAllocationSummary(icmcId, sDate, eDate);
		return pendingBungleListFromMachineAllocation;
	}

	@Override
	public List<RegionSummary> getRegionWiseSummaryList(String region) {
		List<RegionSummary> regionWiseSummaryList = binDashboardJpaDao.getRegionWiseSummaryList(region);
		return regionWiseSummaryList;
	}

	@Override
	public List<Tuple> getGrandSummary() {
		List<Tuple> grandSummaryList = binDashboardJpaDao.getGrandSummary();
		return grandSummaryList;
	}

	@Override
	public List<Tuple> getZoneWiseGrandSummary() {
		List<Tuple> zoneWiseGrandSummaryList = binDashboardJpaDao.getZoneWiseGrandSummary();
		return zoneWiseGrandSummaryList;
	}

	@Override
	public List<Tuple> getFlatZoneSummaryList() {
		List<Tuple> flatZoneSummaryList = binDashboardJpaDao.getFlatZoneSummaryList();
		return flatZoneSummaryList;
	}

	@Override
	public boolean updateCurrentVersionStatus(BinTransactionBOD binTransactionBod) {
		return binDashboardJpaDao.updateCurrentVersionStatus(binTransactionBod);
	}

	@Override
	public BigDecimal getTotalICMCBalance(BigInteger icmcId) {
		BigDecimal notesTotal = BigDecimal.ZERO;
		BigDecimal coinsTotal = BigDecimal.ZERO;
		BigDecimal processingRoomTotal = BigDecimal.ZERO;
		BigDecimal totalICMCBalance = BigDecimal.ZERO;
		List<Tuple> summaryList = binDashboardJpaDao.getRecordForSummary(icmcId);
		for (Tuple tuple : summaryList) {
			if (tuple.get(0, CurrencyType.class).equals(CurrencyType.FRESH)) {
				notesTotal = getNotesTotal(notesTotal, tuple);
			} else if (tuple.get(0, CurrencyType.class).equals(CurrencyType.ISSUABLE)) {
				notesTotal = getNotesTotal(notesTotal, tuple);
			} else if (tuple.get(0, CurrencyType.class).equals(CurrencyType.UNPROCESS)) {
				notesTotal = getNotesTotal(notesTotal, tuple);
			} else if (tuple.get(0, CurrencyType.class).equals(CurrencyType.SOILED)) {
				notesTotal = getNotesTotal(notesTotal, tuple);
			} else if (tuple.get(0, CurrencyType.class).equals(CurrencyType.ATM)) {
				notesTotal = getNotesTotal(notesTotal, tuple);
			}
		}
		List<Tuple> summaryListForCoins = binDashboardJpaDao.getRecordCoinsForSummary(icmcId);
		for (Tuple tuple : summaryListForCoins) {
			if (tuple.get(1, Integer.class) == 10) {
				int denom = tuple.get(1, Integer.class);
				BigDecimal bundle = tuple.get(2, BigDecimal.class);
				coinsTotal = coinsTotal
						.add(bundle.multiply(BigDecimal.valueOf(denom)).multiply(BigDecimal.valueOf(2000)));
			} else {
				int denom = tuple.get(1, Integer.class);
				BigDecimal bundle = tuple.get(2, BigDecimal.class);
				coinsTotal = coinsTotal
						.add(bundle.multiply(BigDecimal.valueOf(denom)).multiply(BigDecimal.valueOf(2500)));
			}

			/*
			 * else if (tuple.get(1,
			 * Integer.class).equals(DenominationType.DENOM_2)) { coinsTotal =
			 * getCoinsTotal(coinsTotal, tuple); } else if (tuple.get(1,
			 * Integer.class).equals(DenominationType.DENOM_1)) { coinsTotal =
			 * getCoinsTotal(coinsTotal, tuple); }
			 */
		}
		List<Tuple> pendingBundleFromMachineAllocation = binDashboardJpaDao.getMachineAllocationSummary(icmcId);
		for (Tuple tuple : pendingBundleFromMachineAllocation) {
			processingRoomTotal = processingRoomTotal.add(tuple.get(2, BigDecimal.class));
		}
		totalICMCBalance = notesTotal.add(coinsTotal).add(processingRoomTotal);
		return totalICMCBalance;
	}

	private BigDecimal getCoinsTotal(BigDecimal coinsTotal, Tuple tuple) {
		int denom = tuple.get(1, Integer.class);
		BigDecimal bundle = tuple.get(2, BigDecimal.class);
		coinsTotal = coinsTotal.add(bundle.multiply(BigDecimal.valueOf(denom)).multiply(BigDecimal.valueOf(2500)));
		return coinsTotal;
	}

	private BigDecimal getNotesTotal(BigDecimal notesTotal, Tuple tuple) {
		int denom = tuple.get(1, Integer.class);
		BigDecimal bundle = tuple.get(2, BigDecimal.class);
		notesTotal = notesTotal.add(bundle.multiply(BigDecimal.valueOf(denom)).multiply(BigDecimal.valueOf(1000)));
		return notesTotal;
	}

	@Override
	public List<Tuple> getCoinRemittanceReceivedForFresh(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> totalSumForCoinRemittance = binDashboardJpaDao.getCoinRemittanceReceivedForFresh(icmcId, sDate,
				eDate);
		return totalSumForCoinRemittance;
	}

	@Override
	public List<Tuple> getCoinsWithdrawalForBranch(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> sasList = binDashboardJpaDao.getCoinsWithdrawalForBranch(icmcId, sDate, eDate);
		return sasList;
	}

	@Override
	public List<BinTransactionBOD> processIO2(BigInteger icmcId, Calendar sDate, Calendar eDate, DateRange dateRange) {

		List<BinTransactionBOD> binTxBodList = new ArrayList<>();

		BigDecimal openingBalanceDenomination1 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination2 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination5 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination10 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination20 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination50 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination100 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination200 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination500 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination1000 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination2000 = new BigDecimal(0);
		// Opening Balance
		List<BinTransactionBOD> summaryListForOpeningBalance = this.getDataFromBinTransactionBOD(icmcId, sDate, eDate,
				CashType.NOTES, dateRange);

		BinTransactionBOD binTxnForOpeningBalance = new BinTransactionBOD();
		// binTxnForOpeningBalance.setUpdateTime(summaryListForOpeningBalance.get(0).getUpdateTime());
		for (BinTransactionBOD bintxn : summaryListForOpeningBalance) {

			openingBalanceDenomination1 = bintxn.getDenomination1();
			if (openingBalanceDenomination1 == null) {
				openingBalanceDenomination1 = new BigDecimal(0);
			}
			openingBalanceDenomination2 = bintxn.getDenomination2();
			if (openingBalanceDenomination2 == null) {
				openingBalanceDenomination2 = new BigDecimal(0);
			}
			openingBalanceDenomination5 = bintxn.getDenomination5();
			if (openingBalanceDenomination5 == null) {
				openingBalanceDenomination5 = new BigDecimal(0);
			}

			openingBalanceDenomination10 = bintxn.getDenomination10();
			if (openingBalanceDenomination10 == null) {
				openingBalanceDenomination10 = new BigDecimal(0);
			}

			openingBalanceDenomination20 = bintxn.getDenomination20();
			if (openingBalanceDenomination20 == null) {
				openingBalanceDenomination20 = new BigDecimal(0);
			}

			openingBalanceDenomination50 = bintxn.getDenomination50();
			if (openingBalanceDenomination50 == null) {
				openingBalanceDenomination50 = new BigDecimal(0);
			}

			openingBalanceDenomination100 = bintxn.getDenomination100();
			if (openingBalanceDenomination100 == null) {
				openingBalanceDenomination100 = new BigDecimal(0);
			}

			openingBalanceDenomination200 = bintxn.getDenomination200();
			if (openingBalanceDenomination200 == null) {
				openingBalanceDenomination200 = new BigDecimal(0);
			}

			openingBalanceDenomination500 = bintxn.getDenomination500();
			if (openingBalanceDenomination500 == null) {
				openingBalanceDenomination500 = new BigDecimal(0);
			}
			openingBalanceDenomination1000 = bintxn.getDenomination1000();
			if (openingBalanceDenomination1000 == null) {
				openingBalanceDenomination1000 = new BigDecimal(0);
			}

			openingBalanceDenomination2000 = bintxn.getDenomination2000();
			if (openingBalanceDenomination2000 == null) {
				openingBalanceDenomination2000 = new BigDecimal(0);
			}

			binTxnForOpeningBalance.setDenomination1(openingBalanceDenomination1);
			binTxnForOpeningBalance.setDenomination2(openingBalanceDenomination2);
			binTxnForOpeningBalance.setDenomination5(openingBalanceDenomination5);
			binTxnForOpeningBalance.setDenomination10(openingBalanceDenomination10);
			binTxnForOpeningBalance.setDenomination20(openingBalanceDenomination20);
			binTxnForOpeningBalance.setDenomination50(openingBalanceDenomination50);
			binTxnForOpeningBalance.setDenomination100(openingBalanceDenomination100);
			binTxnForOpeningBalance.setDenomination200(openingBalanceDenomination200);
			binTxnForOpeningBalance.setDenomination500(openingBalanceDenomination500);
			binTxnForOpeningBalance.setDenomination1000(openingBalanceDenomination1000);
			binTxnForOpeningBalance.setDenomination2000(openingBalanceDenomination2000);

			binTxnForOpeningBalance.setTotalInPieces(
					openingBalanceDenomination1.add(openingBalanceDenomination2).add(openingBalanceDenomination5)
							.add(openingBalanceDenomination10).add(openingBalanceDenomination20)
							.add(openingBalanceDenomination50).add(openingBalanceDenomination100)
							.add(openingBalanceDenomination200).add(openingBalanceDenomination500)
							.add(openingBalanceDenomination1000).add(openingBalanceDenomination2000));

			binTxnForOpeningBalance.setTotalValueOfBankNotes(openingBalanceDenomination1.multiply(new BigDecimal(1))
					.add(openingBalanceDenomination2.multiply(new BigDecimal(2)).add(openingBalanceDenomination5
							.multiply(new BigDecimal(5)).add(openingBalanceDenomination10.multiply(new BigDecimal(10))
									.add(openingBalanceDenomination20.multiply(new BigDecimal(20))
											.add(openingBalanceDenomination50.multiply(new BigDecimal(50))
													.add(openingBalanceDenomination100.multiply(new BigDecimal(100))
															.add(openingBalanceDenomination200
																	.multiply(new BigDecimal(200)).add(
																			openingBalanceDenomination500
																					.multiply(new BigDecimal(500)).add(
																							openingBalanceDenomination1000
																									.multiply(
																											new BigDecimal(
																													1000))
																									.add(openingBalanceDenomination2000
																											.multiply(
																													new BigDecimal(
																															2000)))))))))))));
		}
		binTxBodList.add(0, binTxnForOpeningBalance);
		// Close Opening Balance

		// Remittance Received

		BigDecimal remittanceReceivedDenomination1 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination2 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination5 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination10 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination20 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination50 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination100 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination200 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination500 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination1000 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination2000 = new BigDecimal(0);

		List<Tuple> summaryListForRemittanceRecieved = this.getRemittanceReceivedForFresh(icmcId, sDate, eDate);

		for (Tuple t : summaryListForRemittanceRecieved) {
			if (t.get(0, Integer.class).equals(1)) {
				remittanceReceivedDenomination1 = remittanceReceivedDenomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				remittanceReceivedDenomination2 = remittanceReceivedDenomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				remittanceReceivedDenomination5 = remittanceReceivedDenomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				remittanceReceivedDenomination10 = remittanceReceivedDenomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				remittanceReceivedDenomination20 = remittanceReceivedDenomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				remittanceReceivedDenomination50 = remittanceReceivedDenomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				remittanceReceivedDenomination100 = remittanceReceivedDenomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				remittanceReceivedDenomination200 = remittanceReceivedDenomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				remittanceReceivedDenomination500 = remittanceReceivedDenomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				remittanceReceivedDenomination1000 = remittanceReceivedDenomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				remittanceReceivedDenomination2000 = remittanceReceivedDenomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		List<Tuple> summrayListForDiversion = this.getDepositForDiversion(icmcId, sDate, eDate);

		for (Tuple t : summrayListForDiversion) {
			if (t.get(0, Integer.class).equals(1)) {
				remittanceReceivedDenomination1 = remittanceReceivedDenomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				remittanceReceivedDenomination2 = remittanceReceivedDenomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				remittanceReceivedDenomination5 = remittanceReceivedDenomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				remittanceReceivedDenomination10 = remittanceReceivedDenomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				remittanceReceivedDenomination20 = remittanceReceivedDenomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				remittanceReceivedDenomination50 = remittanceReceivedDenomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				remittanceReceivedDenomination100 = remittanceReceivedDenomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				remittanceReceivedDenomination200 = remittanceReceivedDenomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				remittanceReceivedDenomination500 = remittanceReceivedDenomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				remittanceReceivedDenomination1000 = remittanceReceivedDenomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				remittanceReceivedDenomination2000 = remittanceReceivedDenomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		BinTransactionBOD binTxnBODForRemittanceNotes = new BinTransactionBOD();

		binTxnBODForRemittanceNotes.setDenomination1(remittanceReceivedDenomination1);
		binTxnBODForRemittanceNotes.setDenomination2(remittanceReceivedDenomination2);
		binTxnBODForRemittanceNotes.setDenomination5(remittanceReceivedDenomination5);
		binTxnBODForRemittanceNotes.setDenomination10(remittanceReceivedDenomination10);
		binTxnBODForRemittanceNotes.setDenomination20(remittanceReceivedDenomination20);
		binTxnBODForRemittanceNotes.setDenomination50(remittanceReceivedDenomination50);
		binTxnBODForRemittanceNotes.setDenomination100(remittanceReceivedDenomination100);
		binTxnBODForRemittanceNotes.setDenomination200(remittanceReceivedDenomination200);
		binTxnBODForRemittanceNotes.setDenomination500(remittanceReceivedDenomination500);
		binTxnBODForRemittanceNotes.setDenomination1000(remittanceReceivedDenomination1000);
		binTxnBODForRemittanceNotes.setDenomination2000(remittanceReceivedDenomination2000);
		binTxnBODForRemittanceNotes.setTotalInPieces(remittanceReceivedDenomination1
				.add(remittanceReceivedDenomination2).add(remittanceReceivedDenomination5)
				.add(remittanceReceivedDenomination10).add(remittanceReceivedDenomination20)
				.add(remittanceReceivedDenomination50).add(remittanceReceivedDenomination100)
				.add(remittanceReceivedDenomination200).add(remittanceReceivedDenomination500)
				.add(remittanceReceivedDenomination1000).add(remittanceReceivedDenomination2000));
		binTxnBODForRemittanceNotes.setTotalValueOfBankNotes(remittanceReceivedDenomination1.multiply(new BigDecimal(1))
				.add(remittanceReceivedDenomination2.multiply(new BigDecimal(2)).add(remittanceReceivedDenomination5
						.multiply(new BigDecimal(5)).add(remittanceReceivedDenomination10.multiply(new BigDecimal(10))
								.add(remittanceReceivedDenomination20.multiply(new BigDecimal(20))
										.add(remittanceReceivedDenomination50.multiply(new BigDecimal(50))
												.add(remittanceReceivedDenomination100.multiply(new BigDecimal(100))
														.add(remittanceReceivedDenomination200
																.multiply(new BigDecimal(200)).add(
																		remittanceReceivedDenomination500
																				.multiply(new BigDecimal(500)).add(
																						remittanceReceivedDenomination1000
																								.multiply(
																										new BigDecimal(
																												1000))
																								.add(remittanceReceivedDenomination2000
																										.multiply(
																												new BigDecimal(
																														2000)))))))))))));

		binTxBodList.add(1, binTxnBODForRemittanceNotes);
		// Close Remittance Received

		// Start Deposit
		BinTransactionBOD binTxnBODForDeposit = new BinTransactionBOD();

		BigDecimal denomination1 = new BigDecimal(0);
		BigDecimal denomination2 = new BigDecimal(0);
		BigDecimal denomination5 = new BigDecimal(0);
		BigDecimal denomination10 = new BigDecimal(0);
		BigDecimal denomination20 = new BigDecimal(0);
		BigDecimal denomination50 = new BigDecimal(0);
		BigDecimal denomination100 = new BigDecimal(0);
		BigDecimal denomination200 = new BigDecimal(0);
		BigDecimal denomination500 = new BigDecimal(0);
		BigDecimal denomination1000 = new BigDecimal(0);
		BigDecimal denomination2000 = new BigDecimal(0);

		List<Tuple> summrayListForBranch = this.getDepositForBranch(icmcId, sDate, eDate);

		for (Tuple t : summrayListForBranch) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = t.get(1, BigDecimal.class);
			}

		}

		List<Tuple> summrayListForDSB = this.getDepositForDSB(icmcId, sDate, eDate);

		for (Tuple t : summrayListForDSB) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = denomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = denomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = denomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = denomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = denomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = denomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = denomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = denomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = denomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = denomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = denomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		List<Tuple> summrayListForOtherBank = this.getDepositForOtherBank(icmcId, sDate, eDate);

		for (Tuple t : summrayListForOtherBank) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = denomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = denomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = denomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = denomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = denomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = denomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = denomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = denomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = denomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = denomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = denomination2000.add(t.get(1, BigDecimal.class));
			}

		}
		List<Tuple> summrayListForIndent = this.getDepositForIndent(icmcId, sDate, eDate);

		for (Tuple t : summrayListForIndent) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = denomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = denomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = denomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = denomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = denomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = denomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = denomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = denomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = denomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = denomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = denomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		binTxnBODForDeposit.setDenomination1(denomination1);
		binTxnBODForDeposit.setDenomination2(denomination2);
		binTxnBODForDeposit.setDenomination5(denomination5);
		binTxnBODForDeposit.setDenomination10(denomination10);
		binTxnBODForDeposit.setDenomination20(denomination20);
		binTxnBODForDeposit.setDenomination50(denomination50);
		binTxnBODForDeposit.setDenomination100(denomination100);
		binTxnBODForDeposit.setDenomination200(denomination200);
		binTxnBODForDeposit.setDenomination500(denomination500);
		binTxnBODForDeposit.setDenomination1000(denomination1000);
		binTxnBODForDeposit.setDenomination2000(denomination2000);
		binTxnBODForDeposit.setTotalInPieces(denomination1.add(denomination2).add(denomination5).add(denomination10)
				.add(denomination20).add(denomination50).add(denomination100).add(denomination200).add(denomination500)
				.add(denomination1000).add(denomination2000));
		binTxnBODForDeposit.setTotalValueOfBankNotes(denomination1.multiply(new BigDecimal(1)).add(denomination2
				.multiply(new BigDecimal(2))
				.add(denomination5.multiply(new BigDecimal(5)).add(denomination10.multiply(new BigDecimal(10))
						.add(denomination20.multiply(new BigDecimal(20)).add(denomination50.multiply(new BigDecimal(50))
								.add(denomination100.multiply(new BigDecimal(100)).add(denomination200
										.multiply(new BigDecimal(200)).add(denomination500.multiply(new BigDecimal(500))
												.add(denomination1000.multiply(new BigDecimal(1000))
														.add(denomination2000.multiply(new BigDecimal(2000)))))))))))));

		binTxBodList.add(2, binTxnBODForDeposit);

		// Close Deposit

		// start Remittance Sent

		BigDecimal denominationRemittanceSent1 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent2 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent5 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent10 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent20 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent50 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent100 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent200 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent500 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent1000 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent2000 = new BigDecimal(0);

		List<Tuple> diversionORVAllocation = this.getWithdrawalForDiversion(icmcId, sDate, eDate);
		for (Tuple t : diversionORVAllocation) {

			if (t.get(0, Integer.class).equals(1)) {
				denominationRemittanceSent1 = denominationRemittanceSent1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denominationRemittanceSent2 = denominationRemittanceSent2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denominationRemittanceSent5 = denominationRemittanceSent5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denominationRemittanceSent10 = denominationRemittanceSent10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denominationRemittanceSent20 = denominationRemittanceSent20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denominationRemittanceSent50 = denominationRemittanceSent50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denominationRemittanceSent100 = denominationRemittanceSent100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denominationRemittanceSent200 = denominationRemittanceSent200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denominationRemittanceSent500 = denominationRemittanceSent500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denominationRemittanceSent1000 = denominationRemittanceSent1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denominationRemittanceSent2000 = denominationRemittanceSent2000.add(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> summaryListForRemittanceSent = this.getRemittanceSentForSoiled(icmcId, sDate, eDate);

		for (Tuple t : summaryListForRemittanceSent) {
			if (t.get(0, Integer.class).equals(1)) {
				denominationRemittanceSent1 = denominationRemittanceSent1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denominationRemittanceSent2 = denominationRemittanceSent2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denominationRemittanceSent5 = denominationRemittanceSent5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denominationRemittanceSent10 = denominationRemittanceSent10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denominationRemittanceSent20 = denominationRemittanceSent20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denominationRemittanceSent50 = denominationRemittanceSent50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denominationRemittanceSent100 = denominationRemittanceSent100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denominationRemittanceSent200 = denominationRemittanceSent200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denominationRemittanceSent500 = denominationRemittanceSent500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denominationRemittanceSent1000 = denominationRemittanceSent1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denominationRemittanceSent2000 = denominationRemittanceSent2000.add(t.get(1, BigDecimal.class));
			}

		}

		BinTransactionBOD remittanceSent = new BinTransactionBOD();

		remittanceSent.setDenomination1(denominationRemittanceSent1);
		remittanceSent.setDenomination2(denominationRemittanceSent2);
		remittanceSent.setDenomination5(denominationRemittanceSent5);
		remittanceSent.setDenomination10(denominationRemittanceSent10);
		remittanceSent.setDenomination20(denominationRemittanceSent20);
		remittanceSent.setDenomination50(denominationRemittanceSent50);
		remittanceSent.setDenomination100(denominationRemittanceSent100);
		remittanceSent.setDenomination200(denominationRemittanceSent200);
		remittanceSent.setDenomination500(denominationRemittanceSent500);
		remittanceSent.setDenomination1000(denominationRemittanceSent1000);
		remittanceSent.setDenomination2000(denominationRemittanceSent2000);
		remittanceSent.setTotalInPieces(denominationRemittanceSent1.add(denominationRemittanceSent2)
				.add(denominationRemittanceSent5).add(denominationRemittanceSent10).add(denominationRemittanceSent20)
				.add(denominationRemittanceSent50).add(denominationRemittanceSent100).add(denominationRemittanceSent200)
				.add(denominationRemittanceSent500).add(denominationRemittanceSent1000)
				.add(denominationRemittanceSent2000));
		remittanceSent.setTotalValueOfBankNotes(denominationRemittanceSent1.multiply(new BigDecimal(1)).add(
				denominationRemittanceSent2.multiply(new BigDecimal(2)).add(denominationRemittanceSent5
						.multiply(new BigDecimal(5)).add(denominationRemittanceSent10.multiply(new BigDecimal(10))
								.add(denominationRemittanceSent20.multiply(new BigDecimal(20))
										.add(denominationRemittanceSent50.multiply(new BigDecimal(50))
												.add(denominationRemittanceSent100.multiply(new BigDecimal(100))
														.add(denominationRemittanceSent200.multiply(new BigDecimal(200))
																.add(denominationRemittanceSent500
																		.multiply(new BigDecimal(500)).add(
																				denominationRemittanceSent1000
																						.multiply(new BigDecimal(
																								1000))
																						.add(denominationRemittanceSent2000
																								.multiply(
																										new BigDecimal(
																												2000)))))))))))));

		binTxBodList.add(3, remittanceSent);

		// Close remittance Sent

		// Start Withdrawal

		BigDecimal denomination1W = BigDecimal.ZERO;
		BigDecimal denomination2W = BigDecimal.ZERO;
		BigDecimal denomination5W = BigDecimal.ZERO;
		BigDecimal denomination10W = BigDecimal.ZERO;
		BigDecimal denomination20W = BigDecimal.ZERO;
		BigDecimal denomination50W = BigDecimal.ZERO;
		BigDecimal denomination100W = BigDecimal.ZERO;
		BigDecimal denomination200W = BigDecimal.ZERO;
		BigDecimal denomination500W = BigDecimal.ZERO;
		BigDecimal denomination1000W = BigDecimal.ZERO;
		BigDecimal denomination2000W = BigDecimal.ZERO;

		List<Tuple> craAllocationList = this.getWithdrawalForCRA(icmcId, sDate, eDate);

		for (Tuple t : craAllocationList) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = t.get(1, BigDecimal.class);
			}

		}
		List<Tuple> processBundleForCraList = this.getWithdrawalForProcessCRA(icmcId, sDate, eDate);

		for (Tuple t : processBundleForCraList) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = denomination1W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = denomination2W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = denomination5W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = denomination10W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = denomination20W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = denomination50W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = denomination100W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = denomination200W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = denomination500W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = denomination1000W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = denomination2000W.add(t.get(1, BigDecimal.class));
			}

		}
		List<Tuple> otherBankList = this.getWithdrawalForOtherBank(icmcId, sDate, eDate);
		for (Tuple t : otherBankList) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = denomination1W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = denomination2W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = denomination5W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = denomination10W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = denomination20W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = denomination50W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = denomination100W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = denomination200W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = denomination500W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = denomination1000W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = denomination2000W.add(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> SASAllocation = this.getWithdrawalForBranch(icmcId, sDate, eDate);

		for (Tuple t : SASAllocation) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = denomination1W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = denomination2W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = denomination5W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = denomination10W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = denomination20W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = denomination50W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = denomination100W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = denomination200W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = denomination500W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = denomination1000W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = denomination2000W.add(t.get(1, BigDecimal.class));
			}
		}
		List<Tuple> IndentForWidhral = this.getDepositForIndent(icmcId, sDate, eDate);

		for (Tuple t : IndentForWidhral) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = denomination1W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = denomination2W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = denomination5W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = denomination10W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = denomination20W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = denomination50W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = denomination100W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = denomination200W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = denomination500W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = denomination1000W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = denomination2000W.add(t.get(1, BigDecimal.class));
			}
		}

		BinTransactionBOD binTxnBODForWithdrawal = new BinTransactionBOD();

		binTxnBODForWithdrawal.setDenomination1(denomination1W);
		binTxnBODForWithdrawal.setDenomination2(denomination2W);
		binTxnBODForWithdrawal.setDenomination5(denomination5W);
		binTxnBODForWithdrawal.setDenomination10(denomination10W);
		binTxnBODForWithdrawal.setDenomination20(denomination20W);
		binTxnBODForWithdrawal.setDenomination50(denomination50W);
		binTxnBODForWithdrawal.setDenomination100(denomination100W);
		binTxnBODForWithdrawal.setDenomination200(denomination200W);
		binTxnBODForWithdrawal.setDenomination500(denomination500W);
		binTxnBODForWithdrawal.setDenomination1000(denomination1000W);
		binTxnBODForWithdrawal.setDenomination2000(denomination2000W);
		binTxnBODForWithdrawal.setTotalInPieces(denomination1W.add(denomination2W).add(denomination5W)
				.add(denomination10W).add(denomination20W).add(denomination50W).add(denomination100W)
				.add(denomination200W).add(denomination500W).add(denomination1000W).add(denomination2000W));
		binTxnBODForWithdrawal.setTotalValueOfBankNotes(denomination1W.multiply(new BigDecimal(1)).add(denomination2W
				.multiply(new BigDecimal(2))
				.add(denomination5W.multiply(new BigDecimal(5)).add(denomination10W.multiply(new BigDecimal(10)).add(
						denomination20W.multiply(new BigDecimal(20)).add(denomination50W.multiply(new BigDecimal(50))
								.add(denomination100W.multiply(new BigDecimal(100))
										.add(denomination200W.multiply(new BigDecimal(200))).add(denomination500W
												.multiply(new BigDecimal(500))
												.add(denomination1000W.multiply(new BigDecimal(1000))
														.add(denomination2000W.multiply(new BigDecimal(2000))))))))))));

		binTxBodList.add(4, binTxnBODForWithdrawal);

		// Close withdrawal

		// Start Closing balance

		BigDecimal closingBalanceDenomination1 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination2 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination5 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination10 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination20 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination50 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination100 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination200 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination500 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination1000 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination2000 = new BigDecimal(0);

		BinTransactionBOD closingBalanceBOD = new BinTransactionBOD();

		closingBalanceDenomination1 = openingBalanceDenomination1.add(remittanceReceivedDenomination1)
				.add(denomination1).subtract(denominationRemittanceSent1).subtract(denomination1W);
		closingBalanceDenomination2 = openingBalanceDenomination2.add(remittanceReceivedDenomination2)
				.add(denomination2).subtract(denominationRemittanceSent2).subtract(denomination2W);
		closingBalanceDenomination5 = openingBalanceDenomination5.add(remittanceReceivedDenomination5)
				.add(denomination5).subtract(denominationRemittanceSent5).subtract(denomination5W);
		closingBalanceDenomination10 = openingBalanceDenomination10.add(remittanceReceivedDenomination10)
				.add(denomination10).subtract(denominationRemittanceSent10).subtract(denomination10W);
		closingBalanceDenomination20 = openingBalanceDenomination20.add(remittanceReceivedDenomination20)
				.add(denomination20).subtract(denominationRemittanceSent20).subtract(denomination20W);
		closingBalanceDenomination50 = openingBalanceDenomination50.add(remittanceReceivedDenomination50)
				.add(denomination50).subtract(denominationRemittanceSent50).subtract(denomination50W);
		closingBalanceDenomination100 = openingBalanceDenomination100.add(remittanceReceivedDenomination100)
				.add(denomination100).subtract(denominationRemittanceSent100).subtract(denomination100W);
		closingBalanceDenomination200 = openingBalanceDenomination200.add(remittanceReceivedDenomination200)
				.add(denomination200).subtract(denominationRemittanceSent200).subtract(denomination200W);
		closingBalanceDenomination500 = openingBalanceDenomination500.add(remittanceReceivedDenomination500)
				.add(denomination500).subtract(denominationRemittanceSent500).subtract(denomination500W);
		closingBalanceDenomination1000 = openingBalanceDenomination1000.add(remittanceReceivedDenomination1000)
				.add(denomination1000).subtract(denominationRemittanceSent1000).subtract(denomination1000W);
		closingBalanceDenomination2000 = openingBalanceDenomination2000.add(remittanceReceivedDenomination2000)
				.add(denomination2000).subtract(denominationRemittanceSent2000).subtract(denomination2000W);

		closingBalanceBOD.setDenomination1(closingBalanceDenomination1);
		closingBalanceBOD.setDenomination2(closingBalanceDenomination2);
		closingBalanceBOD.setDenomination5(closingBalanceDenomination5);
		closingBalanceBOD.setDenomination10(closingBalanceDenomination10);
		closingBalanceBOD.setDenomination20(closingBalanceDenomination20);
		closingBalanceBOD.setDenomination50(closingBalanceDenomination50);
		closingBalanceBOD.setDenomination100(closingBalanceDenomination100);
		closingBalanceBOD.setDenomination200(closingBalanceDenomination200);
		closingBalanceBOD.setDenomination500(closingBalanceDenomination500);
		closingBalanceBOD.setDenomination1000(closingBalanceDenomination1000);
		closingBalanceBOD.setDenomination2000(closingBalanceDenomination2000);

		closingBalanceBOD.setTotalInPieces(closingBalanceDenomination1.add(closingBalanceDenomination2)
				.add(closingBalanceDenomination5).add(closingBalanceDenomination10).add(closingBalanceDenomination20)
				.add(closingBalanceDenomination50).add(closingBalanceDenomination100).add(closingBalanceDenomination200)
				.add(closingBalanceDenomination500).add(closingBalanceDenomination1000)
				.add(closingBalanceDenomination2000));
		closingBalanceBOD.setTotalValueOfBankNotes(closingBalanceDenomination1.multiply(new BigDecimal(1)).add(
				closingBalanceDenomination2.multiply(new BigDecimal(2)).add(closingBalanceDenomination5
						.multiply(new BigDecimal(5)).add(closingBalanceDenomination10.multiply(new BigDecimal(10))
								.add(closingBalanceDenomination20.multiply(new BigDecimal(20))
										.add(closingBalanceDenomination50.multiply(new BigDecimal(50))
												.add(closingBalanceDenomination100.multiply(new BigDecimal(100))
														.add(closingBalanceDenomination200.multiply(new BigDecimal(200))
																.add(closingBalanceDenomination500
																		.multiply(new BigDecimal(500)).add(
																				closingBalanceDenomination1000
																						.multiply(new BigDecimal(
																								1000))
																						.add(closingBalanceDenomination2000
																								.multiply(
																										new BigDecimal(
																												2000)))))))))))));

		binTxBodList.add(5, closingBalanceBOD);
		// End Closing balance

		// start Soiled Notes
		List<BinTransactionBOD> summrayListForSoiledNotes = this.getDataFromBinTransactionBOD(icmcId, sDate, eDate,
				CashType.SOILED, dateRange);
		// List<Tuple> summrayListForSoiledNotes = this.getSoiledNotes(icmcId);

		BigDecimal SoiledDenomination1 = new BigDecimal(0);
		BigDecimal SoiledDenomination2 = new BigDecimal(0);
		BigDecimal SoiledDenomination5 = new BigDecimal(0);
		BigDecimal SoiledDenomination10 = new BigDecimal(0);
		BigDecimal SoiledDenomination20 = new BigDecimal(0);
		BigDecimal SoiledDenomination50 = new BigDecimal(0);
		BigDecimal SoiledDenomination100 = new BigDecimal(0);
		BigDecimal SoiledDenomination200 = new BigDecimal(0);
		BigDecimal SoiledDenomination500 = new BigDecimal(0);
		BigDecimal SoiledDenomination1000 = new BigDecimal(0);
		BigDecimal SoiledDenomination2000 = new BigDecimal(0);
		for (BinTransactionBOD bintxn : summrayListForSoiledNotes) {
			SoiledDenomination1 = bintxn.getDenomination1();
			if (SoiledDenomination1 == null) {
				SoiledDenomination1 = new BigDecimal(0);
			}
			SoiledDenomination2 = bintxn.getDenomination2();
			if (SoiledDenomination2 == null) {
				SoiledDenomination2 = new BigDecimal(0);
			}
			SoiledDenomination5 = bintxn.getDenomination5();
			if (SoiledDenomination5 == null) {
				SoiledDenomination5 = new BigDecimal(0);
			}
			SoiledDenomination10 = bintxn.getDenomination10();
			if (SoiledDenomination10 == null) {
				SoiledDenomination10 = new BigDecimal(0);
			}
			SoiledDenomination20 = bintxn.getDenomination20();
			if (SoiledDenomination20 == null) {
				SoiledDenomination20 = new BigDecimal(0);
			}
			SoiledDenomination50 = bintxn.getDenomination50();
			if (SoiledDenomination50 == null) {
				SoiledDenomination50 = new BigDecimal(0);
			}
			SoiledDenomination100 = bintxn.getDenomination100();
			if (SoiledDenomination100 == null) {
				SoiledDenomination100 = new BigDecimal(0);
			}
			SoiledDenomination200 = bintxn.getDenomination200();
			if (SoiledDenomination200 == null) {
				SoiledDenomination200 = new BigDecimal(0);
			}
			SoiledDenomination500 = bintxn.getDenomination500();
			if (SoiledDenomination500 == null) {
				SoiledDenomination500 = new BigDecimal(0);
			}
			SoiledDenomination1000 = bintxn.getDenomination1000();
			if (SoiledDenomination1000 == null) {
				SoiledDenomination1000 = new BigDecimal(0);
			}
			SoiledDenomination2000 = bintxn.getDenomination2000();
			if (SoiledDenomination2000 == null) {
				SoiledDenomination2000 = new BigDecimal(0);
			}
		}
		List<Tuple> SASAlloSoild = this.getWithdrawalFromBranch(icmcId, sDate, eDate, CurrencyType.SOILED);

		for (Tuple t : SASAlloSoild) {

			if (t.get(0, Integer.class).equals(1)) {
				SoiledDenomination1 = SoiledDenomination1.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				SoiledDenomination2 = SoiledDenomination2.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				SoiledDenomination5 = SoiledDenomination5.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				SoiledDenomination10 = SoiledDenomination10.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				SoiledDenomination20 = SoiledDenomination20.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				SoiledDenomination50 = SoiledDenomination50.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				SoiledDenomination100 = SoiledDenomination100.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				SoiledDenomination200 = SoiledDenomination200.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				SoiledDenomination500 = SoiledDenomination500.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				SoiledDenomination1000 = SoiledDenomination1000.subtract(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				SoiledDenomination2000 = SoiledDenomination2000.subtract(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> branchSoild = this.getDepositFromBranch(icmcId, sDate, eDate, CurrencyType.SOILED);

		for (Tuple t : branchSoild) {

			if (t.get(0, Integer.class).equals(1)) {
				SoiledDenomination1 = SoiledDenomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				SoiledDenomination2 = SoiledDenomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				SoiledDenomination5 = SoiledDenomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				SoiledDenomination10 = SoiledDenomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				SoiledDenomination20 = SoiledDenomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				SoiledDenomination50 = SoiledDenomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				SoiledDenomination100 = SoiledDenomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				SoiledDenomination200 = SoiledDenomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				SoiledDenomination500 = SoiledDenomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				SoiledDenomination1000 = SoiledDenomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				SoiledDenomination2000 = SoiledDenomination2000.add(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> processSoild = this.getProcessFromProcessingOutPut(icmcId, sDate, eDate, CurrencyType.SOILED);

		for (Tuple t : processSoild) {

			if (t.get(0, Integer.class).equals(1)) {
				SoiledDenomination1 = SoiledDenomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				SoiledDenomination2 = SoiledDenomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				SoiledDenomination5 = SoiledDenomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				SoiledDenomination10 = SoiledDenomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				SoiledDenomination20 = SoiledDenomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				SoiledDenomination50 = SoiledDenomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				SoiledDenomination100 = SoiledDenomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				SoiledDenomination200 = SoiledDenomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				SoiledDenomination500 = SoiledDenomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				SoiledDenomination1000 = SoiledDenomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				SoiledDenomination2000 = SoiledDenomination2000.add(t.get(1, BigDecimal.class));
			}
		}
		BinTransactionBOD binTxnBODForSoiledNotes = new BinTransactionBOD();

		binTxnBODForSoiledNotes.setDenomination1(SoiledDenomination1);
		binTxnBODForSoiledNotes.setDenomination2(SoiledDenomination2);
		binTxnBODForSoiledNotes.setDenomination5(SoiledDenomination5);
		binTxnBODForSoiledNotes.setDenomination10(SoiledDenomination10);
		binTxnBODForSoiledNotes.setDenomination20(SoiledDenomination20);
		binTxnBODForSoiledNotes.setDenomination50(SoiledDenomination50);
		binTxnBODForSoiledNotes.setDenomination100(SoiledDenomination100);
		binTxnBODForSoiledNotes.setDenomination200(SoiledDenomination200);
		binTxnBODForSoiledNotes.setDenomination500(SoiledDenomination500);
		binTxnBODForSoiledNotes.setDenomination1000(SoiledDenomination1000);
		binTxnBODForSoiledNotes.setDenomination2000(SoiledDenomination2000);
		binTxnBODForSoiledNotes.setTotalInPieces(SoiledDenomination1.add(SoiledDenomination2).add(SoiledDenomination5)
				.add(SoiledDenomination10).add(SoiledDenomination20).add(SoiledDenomination50)
				.add(SoiledDenomination100).add(SoiledDenomination200).add(SoiledDenomination500)
				.add(SoiledDenomination1000).add(SoiledDenomination2000));
		binTxnBODForSoiledNotes.setTotalValueOfBankNotes(SoiledDenomination1.multiply(new BigDecimal(1))
				.add(SoiledDenomination2.multiply(new BigDecimal(2))
						.add(SoiledDenomination5.multiply(new BigDecimal(5))).add(SoiledDenomination10
								.multiply(new BigDecimal(10)).add(SoiledDenomination20.multiply(new BigDecimal(20))
										.add(SoiledDenomination50.multiply(new BigDecimal(50))
												.add(SoiledDenomination100.multiply(new BigDecimal(100))
														.add(SoiledDenomination200.multiply(new BigDecimal(200))
																.add(SoiledDenomination500.multiply(new BigDecimal(500))
																		.add(SoiledDenomination1000
																				.multiply(new BigDecimal(1000)).add(
																						SoiledDenomination2000.multiply(
																								new BigDecimal(
																										2000))))))))))));


		binTxBodList.add(6, binTxnBODForSoiledNotes);

		// Close Soiled Notes

		// Start Fresh Info
		List<Tuple> summrayListForAdditionalFreshNotes = this.getAdditionalInfoFreshNotes(icmcId);

		for (Tuple t : summrayListForAdditionalFreshNotes) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = t.get(1, BigDecimal.class);
			}

		}

		BinTransactionBOD binTxnBODForFreshNotes = new BinTransactionBOD();

		binTxnBODForFreshNotes.setDenomination1(denomination1);
		binTxnBODForFreshNotes.setDenomination2(denomination2);
		binTxnBODForFreshNotes.setDenomination5(denomination5);
		binTxnBODForFreshNotes.setDenomination10(denomination10);
		binTxnBODForFreshNotes.setDenomination20(denomination20);
		binTxnBODForFreshNotes.setDenomination50(denomination50);
		binTxnBODForFreshNotes.setDenomination100(denomination100);
		binTxnBODForFreshNotes.setDenomination200(denomination200);
		binTxnBODForFreshNotes.setDenomination500(denomination500);
		binTxnBODForFreshNotes.setDenomination1000(denomination1000);
		binTxnBODForFreshNotes.setDenomination2000(denomination2000);
		binTxnBODForFreshNotes.setTotalInPieces(denomination1.add(denomination2).add(denomination5).add(denomination10)
				.add(denomination20).add(denomination50).add(denomination100).add(denomination200).add(denomination500)
				.add(denomination1000).add(denomination2000));
		binTxnBODForFreshNotes.setTotalValueOfBankNotes(denomination1.multiply(new BigDecimal(1)).add(denomination2
				.multiply(new BigDecimal(2))
				.add(denomination5.multiply(new BigDecimal(5)).add(denomination10.multiply(new BigDecimal(10))
						.add(denomination20.multiply(new BigDecimal(20)).add(denomination50.multiply(new BigDecimal(50))
								.add(denomination100.multiply(new BigDecimal(100)).add(denomination200
										.multiply(new BigDecimal(200)).add(denomination500.multiply(new BigDecimal(500))
												.add(denomination1000.multiply(new BigDecimal(1000))
														.add(denomination2000.multiply(new BigDecimal(2000)))))))))))));

		binTxBodList.add(7, binTxnBODForFreshNotes);
		// End Fresh Info

		// Start Coins opening balance Info
		List<BinTransactionBOD> summaryListForOpeningBalanceForCoins = this.getDataFromBinTransactionBOD(icmcId, sDate,
				eDate, CashType.COINS, dateRange);

		BigDecimal openingCoinBalanceDenomination1 = BigDecimal.ZERO;
		BigDecimal openingCoinBalanceDenomination2 = new BigDecimal(0);
		BigDecimal openingCoinBalanceDenomination5 = new BigDecimal(0);
		BigDecimal openingCoinBalanceDenomination10 = new BigDecimal(0);
		BigDecimal openingCoinBalanceForAnyOtherCoin = new BigDecimal(0);

		for (BinTransactionBOD bintxn : summaryListForOpeningBalanceForCoins) {

			openingCoinBalanceDenomination1 = bintxn.getDenomination1();
			if (openingCoinBalanceDenomination1 == null) {
				openingCoinBalanceDenomination1 = new BigDecimal(0);
			}
			openingCoinBalanceDenomination2 = bintxn.getDenomination2();
			if (openingCoinBalanceDenomination2 == null) {
				openingCoinBalanceDenomination2 = new BigDecimal(0);
			}
			openingCoinBalanceDenomination5 = bintxn.getDenomination5();
			if (openingCoinBalanceDenomination5 == null) {
				openingCoinBalanceDenomination5 = new BigDecimal(0);
			}

			openingCoinBalanceDenomination10 = bintxn.getDenomination10();
			if (openingCoinBalanceDenomination10 == null) {
				openingCoinBalanceDenomination10 = new BigDecimal(0);
			}

			openingCoinBalanceForAnyOtherCoin = bintxn.getAnyOtherCoin();
			if (openingCoinBalanceForAnyOtherCoin == null) {
				openingCoinBalanceForAnyOtherCoin = new BigDecimal(0);
			}
		}

		BinTransactionBOD binTxnForOpeningCoinBalance = new BinTransactionBOD();

		binTxnForOpeningCoinBalance.setDenomination1(openingCoinBalanceDenomination1);
		binTxnForOpeningCoinBalance.setDenomination2(openingCoinBalanceDenomination2);
		binTxnForOpeningCoinBalance.setDenomination5(openingCoinBalanceDenomination5);
		binTxnForOpeningCoinBalance.setDenomination10(openingCoinBalanceDenomination10);
		binTxnForOpeningCoinBalance.setAnyOtherCoin(openingCoinBalanceForAnyOtherCoin);

		binTxnForOpeningCoinBalance.setTotalCoinsPieces(openingCoinBalanceDenomination1
				.add(openingCoinBalanceDenomination2).add(openingCoinBalanceDenomination5)
				.add(openingCoinBalanceDenomination10).add(openingCoinBalanceForAnyOtherCoin));

		binTxnForOpeningCoinBalance.setTotalValueOfCoins(openingCoinBalanceDenomination1.multiply(new BigDecimal(1))
				.add(openingCoinBalanceDenomination2.multiply(new BigDecimal(2))
						.add(openingCoinBalanceDenomination5.multiply(new BigDecimal(5))
								.add(openingCoinBalanceDenomination10.multiply(new BigDecimal(10))
										.add(openingCoinBalanceForAnyOtherCoin.multiply(new BigDecimal(1)))))));

		binTxBodList.add(8, binTxnForOpeningCoinBalance);
		// End Coins opening balance Info

		// Remittance Received for Coins
		BigDecimal coinRemittanceReceivedDenomination1 = new BigDecimal(0);
		BigDecimal coinRemittanceReceivedDenomination2 = new BigDecimal(0);
		BigDecimal coinRemittanceReceivedDenomination5 = new BigDecimal(0);
		BigDecimal coinRemittanceReceivedDenomination10 = new BigDecimal(0);

		List<Tuple> coinSummaryListForRemittanceRecieved = this.getCoinRemittanceReceivedForFresh(icmcId, sDate, eDate);

		for (Tuple t : coinSummaryListForRemittanceRecieved) {
			if (t.get(0, Integer.class).equals(1)) {
				coinRemittanceReceivedDenomination1 = BigDecimal.valueOf(t.get(1, int.class) * 2500);
			}
			if (t.get(0, Integer.class).equals(2)) {
				coinRemittanceReceivedDenomination2 = BigDecimal.valueOf(t.get(1, int.class) * 2500);
			}
			if (t.get(0, Integer.class).equals(5)) {
				coinRemittanceReceivedDenomination5 = BigDecimal.valueOf(t.get(1, int.class) * 2500);
			}
			if (t.get(0, Integer.class).equals(10)) {
				coinRemittanceReceivedDenomination10 = BigDecimal.valueOf(t.get(1, int.class) * 2000);
			}
		}

		BinTransactionBOD binTxnBODForRemittanceCoins = new BinTransactionBOD();

		binTxnBODForRemittanceCoins.setDenomination1(coinRemittanceReceivedDenomination1);
		binTxnBODForRemittanceCoins.setDenomination2(coinRemittanceReceivedDenomination2);
		binTxnBODForRemittanceCoins.setDenomination5(coinRemittanceReceivedDenomination5);
		binTxnBODForRemittanceCoins.setDenomination10(coinRemittanceReceivedDenomination10);

		binTxnBODForRemittanceCoins
				.setTotalCoinsPieces(coinRemittanceReceivedDenomination1.add(coinRemittanceReceivedDenomination2)
						.add(coinRemittanceReceivedDenomination5).add(coinRemittanceReceivedDenomination10));

		binTxnBODForRemittanceCoins.setTotalValueOfCoins(coinRemittanceReceivedDenomination1.multiply(new BigDecimal(1))
				.add(coinRemittanceReceivedDenomination2.multiply(new BigDecimal(2))
						.add(coinRemittanceReceivedDenomination5.multiply(new BigDecimal(5))
								.add(coinRemittanceReceivedDenomination10.multiply(new BigDecimal(10))))));

		binTxBodList.add(9, binTxnBODForRemittanceCoins);
		// Close Remittance Received for Coins

		// Start Coins Withdrawal Transfer
		BigDecimal denominationCoin1W = new BigDecimal(0);
		BigDecimal denominationCoin2W = new BigDecimal(0);
		BigDecimal denominationCoin5W = new BigDecimal(0);
		BigDecimal denominationCoin10W = new BigDecimal(0);
		BigDecimal multiplier2000 = BigDecimal.valueOf(2000);
		BigDecimal multiplier2500 = BigDecimal.valueOf(2500);

		List<Tuple> coinPaymentList = this.getCoinsWithdrawalForBranch(icmcId, sDate, eDate);

		for (Tuple t : coinPaymentList) {
			if (t.get(0, Integer.class).equals(1)) {
				denominationCoin1W = denominationCoin1W.add(t.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (t.get(0, Integer.class).equals(2)) {
				denominationCoin2W = denominationCoin2W.add(t.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (t.get(0, Integer.class).equals(5)) {
				denominationCoin5W = denominationCoin5W.add(t.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (t.get(0, Integer.class).equals(10)) {
				denominationCoin10W = denominationCoin10W.add(t.get(1, BigDecimal.class).multiply(multiplier2000));
			}
		}

		BinTransactionBOD binTxnBODForCoinWithdrawal = new BinTransactionBOD();

		binTxnBODForCoinWithdrawal.setDenomination1(denominationCoin1W);
		binTxnBODForCoinWithdrawal.setDenomination2(denominationCoin2W);
		binTxnBODForCoinWithdrawal.setDenomination5(denominationCoin5W);
		binTxnBODForCoinWithdrawal.setDenomination10(denominationCoin10W);

		binTxnBODForCoinWithdrawal.setTotalCoinsPieces(
				denominationCoin1W.add(denominationCoin2W).add(denominationCoin5W).add(denominationCoin10W));

		binTxnBODForCoinWithdrawal.setTotalValueOfCoins(denominationCoin1W.multiply(new BigDecimal(1))
				.add(denominationCoin2W.multiply(new BigDecimal(2)).add(denominationCoin5W.multiply(new BigDecimal(5))
						.add(denominationCoin10W.multiply(new BigDecimal(10))))));

		binTxBodList.add(10, binTxnBODForCoinWithdrawal);
		// Close withdrawal Transfer Coins

		// Start Closing balance
		BigDecimal coinClosingBalanceDenomination1 = new BigDecimal(0);
		BigDecimal coinClosingBalanceDenomination2 = new BigDecimal(0);
		BigDecimal coinClosingBalanceDenomination5 = new BigDecimal(0);
		BigDecimal coinClosingBalanceDenomination10 = new BigDecimal(0);

		BinTransactionBOD coinClosingBalanceBOD = new BinTransactionBOD();

		coinClosingBalanceDenomination1 = openingCoinBalanceDenomination1.add(coinRemittanceReceivedDenomination1)
				.subtract(denominationCoin1W);
		coinClosingBalanceDenomination2 = openingCoinBalanceDenomination2.add(coinRemittanceReceivedDenomination2)
				.subtract(denominationCoin2W);
		coinClosingBalanceDenomination5 = openingCoinBalanceDenomination5.add(coinRemittanceReceivedDenomination5)
				.subtract(denominationCoin5W);
		coinClosingBalanceDenomination10 = openingCoinBalanceDenomination10.add(coinRemittanceReceivedDenomination10)
				.subtract(denominationCoin10W);

		coinClosingBalanceBOD.setDenomination1(coinClosingBalanceDenomination1);
		coinClosingBalanceBOD.setDenomination2(coinClosingBalanceDenomination2);
		coinClosingBalanceBOD.setDenomination5(coinClosingBalanceDenomination5);
		coinClosingBalanceBOD.setDenomination10(coinClosingBalanceDenomination10);

		coinClosingBalanceBOD.setTotalCoinsPieces(coinClosingBalanceDenomination1.add(coinClosingBalanceDenomination2)
				.add(coinClosingBalanceDenomination5).add(coinClosingBalanceDenomination10));

		coinClosingBalanceBOD.setTotalValueOfCoins(coinClosingBalanceDenomination1.multiply(new BigDecimal(1))
				.add(coinClosingBalanceDenomination2.multiply(new BigDecimal(2))
						.add(coinClosingBalanceDenomination5.multiply(new BigDecimal(5))
								.add(coinClosingBalanceDenomination10.multiply(new BigDecimal(10))))));

		binTxBodList.add(11, coinClosingBalanceBOD);
		// End Closing balance

		return binTxBodList;

	}

	@Override
	public ICMC getICMCObj(BigInteger icmcId) {
		return binDashboardJpaDao.getICMCObj(icmcId);
	}

	@Override
	public Map<String, BranchReceipt> getBranchDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> branchDepositTupleList = binDashboardJpaDao.getBranchDepositList(icmcId, sDate, eDate);
		Map<String, BranchReceipt> branchDepositList = UtilityMapper.mapTupleToBranchReceipt(branchDepositTupleList);

		for (Map.Entry<String, BranchReceipt> entry : branchDepositList.entrySet()) {
			if (entry.getKey() != null) {
				String[] splitData = entry.getKey().split(":");
				String srNumber = splitData[0].trim();
				String solId = splitData[1].trim();
				LOG.info("srNumber: " + srNumber);
				LOG.info("solId: " + solId);
				Calendar insertTime = binDashboardJpaDao.getInsertTimeForBranchReceipt(icmcId, solId, sDate, eDate);
				entry.getValue()
						.setReceiptTime(insertTime.get(Calendar.HOUR_OF_DAY) + ":" + insertTime.get(Calendar.MINUTE));
			}
		}
		return branchDepositList;
	}

	@Override
	public Map<String, DSB> getDSBDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> dsbDepositTupleList = binDashboardJpaDao.getDSBDepositList(icmcId, sDate, eDate);
		Map<String, DSB> dsbDepositList = UtilityMapper.mapTupleToDSBReceipt(dsbDepositTupleList);

		for (Map.Entry<String, DSB> entry : dsbDepositList.entrySet()) {
			Calendar insertTime = binDashboardJpaDao.getInsertTimeForDSBReceipt(icmcId,
					entry.getValue().getReceiptSequence(), entry.getValue().getReceiptDate(), sDate, eDate);
			entry.getValue()
					.setReceiptTime(insertTime.get(Calendar.HOUR_OF_DAY) + ":" + insertTime.get(Calendar.MINUTE));
		}
		return dsbDepositList;
	}

	@Override
	public Map<String, BankReceipt> getBankDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> bankDepositTupleList = binDashboardJpaDao.getBankDepositList(icmcId, sDate, eDate);
		Map<String, BankReceipt> bankDepositList = UtilityMapper.mapTupleToBankReceipt(bankDepositTupleList);
		return bankDepositList;
	}

	@Override
	public Map<String, FreshFromRBI> getFreshDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> freshRBINotesTupleList = binDashboardJpaDao.getRemittanceReceivedForFresh(icmcId, sDate, eDate);
		List<Tuple> freshRBICoinsTupleList = binDashboardJpaDao.getCoinRemittanceReceivedForFresh(icmcId, sDate, eDate);
		Map<String, FreshFromRBI> freshFromRBIList = UtilityMapper.mapTupleToFreshFromRBI(freshRBINotesTupleList,
				freshRBICoinsTupleList);
		return freshFromRBIList;
	}

	@Override
	public Map<String, DiversionIRV> getDirvDepositList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> dirvDepositTupleList = binDashboardJpaDao.getDepositForDiversion(icmcId, sDate, eDate);
		Map<String, DiversionIRV> dirvDepositList = UtilityMapper.mapTupleToDirvReceipt(dirvDepositTupleList);
		return dirvDepositList;
	}

	@Override
	public List<CRA> getCRAPaymentList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		// List<CRAAllocation> craPaymentList = new ArrayList<>();
		List<CRA> tempCRAPaymentList = binDashboardJpaDao.getCRAPaymentList(icmcId, sDate, eDate);
		/*
		 * for(CRA cra : tempCRAPaymentList){ CRAAllocation craAllocation =
		 * binDashboardJpaDao.getCraAllocPaymentList(icmcId, cra, sDate, eDate);
		 * craPaymentList.add(craAllocation); }
		 */
		return tempCRAPaymentList;
	}

	@Override
	public List<BinTransaction> getBinWiseSummary(BigInteger icmcId) {
		List<BinTransaction> binWiseSummaryList = binDashboardJpaDao.getBinWiseSummary(icmcId);
		return binWiseSummaryList;
	}

	@Override
	public List<Discrepancy> getDiscrepancyList(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Discrepancy> discrepancyList = binDashboardJpaDao.getDiscrepancyList(icmcId, sDate, eDate);

		for (Discrepancy discrepancy : discrepancyList) {
			for (DiscrepancyAllocation discAllocation : discrepancy.getDiscrepancyAllocations()) {
				if (discAllocation.getStatus().equals(OtherStatus.RECEIVED)) {
					BigDecimal shortValue = BigDecimal.ZERO;
					BigDecimal fakeValue = BigDecimal.ZERO;
					BigDecimal mutilValue = BigDecimal.ZERO;
					BigDecimal sadMutilValue = BigDecimal.ZERO;

					discAllocation.setSairrem(BigDecimal.ZERO);
					discAllocation.setSamutcur(BigDecimal.ZERO);
					discAllocation.setSadscash(BigDecimal.ZERO);
					discAllocation.setExcess(BigDecimal.ZERO);

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("SHORTAGE")) {
						shortValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}

					// previous code
					/*
					 * if
					 * (!discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "SHORTAGE") &&
					 * !discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "EXCESS") &&
					 * !discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "MUTILATED") && discAllocation.getDenomination() >= 50 &&
					 * discrepancy.getAccountTellerCam().equalsIgnoreCase(
					 * "ACCOUNT")) {
					 * 
					 * fakeValue = BigDecimal
					 * .valueOf(discAllocation.getDenomination() *
					 * discAllocation.getNumberOfNotes()); }
					 * 
					 * if
					 * (!discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "SHORTAGE") &&
					 * !discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "EXCESS") &&
					 * !discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "FAKE") && discAllocation.getDenomination() >= 50 &&
					 * discrepancy.getAccountTellerCam().equalsIgnoreCase(
					 * "ACCOUNT")) {
					 * 
					 * if (discAllocation.getDiscrepancyType() != null &&
					 * discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "MUTILATED") &&
					 * discAllocation.getMutilType().equalsIgnoreCase(
					 * "HALF VALUE")) { mutilValue = BigDecimal
					 * .valueOf(discAllocation.getDenomination() *
					 * discAllocation.getNumberOfNotes() * 0.5); }
					 * 
					 * if (discAllocation.getDiscrepancyType() != null &&
					 * discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "MUTILATED") &&
					 * discAllocation.getMutilType().equalsIgnoreCase(
					 * "ZERO VALUE")) { mutilValue = BigDecimal
					 * .valueOf(discAllocation.getDenomination() *
					 * discAllocation.getNumberOfNotes()); } }
					 */

					// code by shahabuddin

					if (!discAllocation.getDiscrepancyType().equalsIgnoreCase("SHORTAGE")
							&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("EXCESS")
							&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& discAllocation.getDenomination() >= 10
							&& discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT")) {

						fakeValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}

					if (!discAllocation.getDiscrepancyType().equalsIgnoreCase("SHORTAGE")
							&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("EXCESS")
							&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
							&& discAllocation.getDenomination() >= 10
							&& discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT")) {

						if (discAllocation.getDiscrepancyType() != null
								&& discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
								&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE")) {
							mutilValue = BigDecimal.valueOf(
									discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						}

						if (discAllocation.getDiscrepancyType() != null
								&& discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
								&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE")) {
							mutilValue = BigDecimal
									.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
						}
					}

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
							&& discAllocation.getDenomination() <= 10
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT"))) {
						fakeValue = BigDecimal.valueOf(discAllocation.getDenomination());
						// discAllocation.setSairrem(mutilValue);
					}

					// end by shahabuddin

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
							&& discAllocation.getDenomination() >= 100
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER"))) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
						discAllocation.setSairrem(mutilValue);
					}

					// previous code
					/*
					 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "FAKE") && discAllocation.getDenomination() <= 20 &&
					 * (discrepancy.getAccountTellerCam().equalsIgnoreCase(
					 * "ACCOUNT"))) { fakeValue =
					 * BigDecimal.valueOf(discAllocation.getDenomination()); //
					 * discAllocation.setSairrem(mutilValue); }
					 */

					/*
					 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "FAKE") && discAllocation.getDenomination() >= 100 &&
					 * (discrepancy.getAccountTellerCam().equalsIgnoreCase(
					 * "TELLER") ||
					 * discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")
					 * )) { sadMutilValue = BigDecimal
					 * .valueOf(discAllocation.getDenomination() *
					 * discAllocation.getNumberOfNotes()); }
					 */

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
							&& discAllocation.getDenomination() <= 100
							&& discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
							&& discAllocation.getDenomination() >= 200
							&& discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
						discAllocation.setSairrem(mutilValue);
					}

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
							&& discAllocation.getDenomination() <= 50
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
									|| discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM"))) {
						sadMutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& discAllocation.getDenomination() <= 50
							&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE")
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER"))) {
						sadMutilValue = BigDecimal.valueOf(discAllocation.getDenomination());
						// discAllocation.setSadscash(BigDecimal.valueOf(discAllocation.getDenomination()));
					}

					// inayat

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
									&& discAllocation.getDenomination() >= 100
									&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE"))) {
						mutilValue = BigDecimal.valueOf(discAllocation.getDenomination());
						discAllocation.setSairrem(mutilValue);
					}

					/*
					 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "MUTILATED") &&
					 * (discrepancy.getAccountTellerCam().equalsIgnoreCase(
					 * "TELLER") && discAllocation.getDenomination() <= 50 &&
					 * discAllocation.getMutilType().equalsIgnoreCase(
					 * "ZERO VALUE" ))) { mutilValue =
					 * BigDecimal.valueOf(discAllocation.getDenomination());
					 * discAllocation.setSadscash(mutilValue); }
					 */

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
									&& discAllocation.getDenomination() >= 100
									&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"))) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						discAllocation.setSairrem(mutilValue);
					}

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
									&& discAllocation.getDenomination() <= 50
									&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"))) {
						// mutilValue =
						// BigDecimal.valueOf(discAllocation.getDenomination() *
						// discAllocation.getNumberOfNotes() * 0.5);
						sadMutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						// discAllocation.setSadscash(mutilValue);
						discAllocation.setSamutcur(sadMutilValue);
					}
					// previous code
					/*
					 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "MUTILATED") &&
					 * (discrepancy.getAccountTellerCam().equalsIgnoreCase(
					 * "CAM") && discAllocation.getDenomination() >= 100 &&
					 * discAllocation.getMutilType().equalsIgnoreCase(
					 * "HALF VALUE"))) { mutilValue = BigDecimal
					 * .valueOf(discAllocation.getDenomination() *
					 * discAllocation.getNumberOfNotes() * 0.5);
					 * discAllocation.setSairrem(mutilValue); }
					 * 
					 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
					 * "MUTILATED") &&
					 * (discrepancy.getAccountTellerCam().equalsIgnoreCase(
					 * "CAM") && discAllocation.getDenomination() >= 100 &&
					 * discAllocation.getMutilType().equalsIgnoreCase(
					 * "ZERO VALUE"))) { mutilValue =
					 * BigDecimal.valueOf(discAllocation.getDenomination());
					 * discAllocation.setSairrem(mutilValue); }
					 */

					// code by shahabuddin

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")
									&& discAllocation.getDenomination() >= 10
									&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"))) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						discAllocation.setSairrem(mutilValue);
					}

					if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")
									&& discAllocation.getDenomination() >= 10
									&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE"))) {
						mutilValue = BigDecimal.valueOf(discAllocation.getDenomination());
						discAllocation.setSairrem(mutilValue);
					}

					// end by shahabuddin

					//

					discAllocation.setSairrem(shortValue.add(fakeValue).add(mutilValue));
					discAllocation.setSadscash(sadMutilValue);

					if (discAllocation.getMutilType() != null
							&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE")) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						discAllocation.setSamutcur(mutilValue);
					}

					if (discAllocation.getDiscrepancyType() != null
							&& discAllocation.getDiscrepancyType().equalsIgnoreCase("EXCESS")) {
						discAllocation.setExcess(BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes()));
					}
				}
			}
		}
		this.setAccountingTxnsInDiscrepancyReport(discrepancyList);
		return discrepancyList;
	}

	private void setAccountingTxnsInDiscrepancyReport(List<Discrepancy> discrepancyList) {
		for (Discrepancy discrepancy : discrepancyList) {

			BigDecimal sairremTotal = BigDecimal.ZERO;
			BigDecimal samutcurTotal = BigDecimal.ZERO;
			BigDecimal sadscashTotal = BigDecimal.ZERO;
			BigDecimal excessTotal = BigDecimal.ZERO;

			for (DiscrepancyAllocation discAllocation : discrepancy.getDiscrepancyAllocations()) {
				if (discAllocation.getStatus().equals(OtherStatus.RECEIVED)) {
					sairremTotal = sairremTotal.add(discAllocation.getSairrem());
					samutcurTotal = samutcurTotal.add(discAllocation.getSamutcur());
					sadscashTotal = sadscashTotal.add(discAllocation.getSadscash());
					excessTotal = excessTotal.add(discAllocation.getExcess());
				}
			}
			discrepancy.setSairremTotal(sairremTotal);
			discrepancy.setSamutcurTotal(samutcurTotal);
			discrepancy.setSadscashTotal(sadscashTotal);
			discrepancy.setExcessTotal(excessTotal);
		}
	}

	@Override
	public Map<String, Discrepancy> getFCRMList(BigInteger icmcId, Calendar sDate, Calendar eDate) {

		List<Discrepancy> discrepancyList = binDashboardJpaDao.getDiscrepancyList(icmcId, sDate, eDate);

		Map<String, Discrepancy> mapList = new LinkedHashMap<>();

		for (Discrepancy discrepancy : discrepancyList) {
			mapList.put(discrepancy.getSolId() + "" + discrepancy.getBranch(), new Discrepancy());
		}

		List<DiscrepancyAllocation> discrepancyAllocationList = new ArrayList<>();
		DiscrepancyAllocation discrepancyAllocation = new DiscrepancyAllocation(true);

		for (Discrepancy discrepancy : discrepancyList) {
			Discrepancy disc = mapList.get(discrepancy.getSolId() + "" + discrepancy.getBranch());

			if (discrepancy.getAccountNumber() != null) {
				disc.setAccountNumber(discrepancy.getAccountNumber());
			}
			disc.setBranch(discrepancy.getBranch());
			disc.setSolId(discrepancy.getSolId());
			disc.setDiscrepancyDate(discrepancy.getDiscrepancyDate());
			// disc.setDiscrepancyAllocations(discrepancy.getDiscrepancyAllocations());

			for (DiscrepancyAllocation discAlloc : discrepancy.getDiscrepancyAllocations()) {

				if (discAlloc.getDiscrepancyType().equals("FAKE") && discAlloc.getDenomination().equals(2000)) {
					discrepancyAllocation.setFakeNotes2000Total(discrepancyAllocation.getFakeNotes2000Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("FAKE") && discAlloc.getDenomination().equals(500)) {
					discrepancyAllocation.setFakeNotes500Total(discrepancyAllocation.getFakeNotes500Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("FAKE") && discAlloc.getDenomination().equals(100)) {
					discrepancyAllocation.setFakeNotes100Total(discrepancyAllocation.getFakeNotes100Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				discrepancyAllocation.setFakeNotesTotalValue(discrepancyAllocation.getFakeNotes2000Total()
						.multiply(BigDecimal.valueOf(2000))
						.add(discrepancyAllocation.getFakeNotes500Total().multiply(BigDecimal.valueOf(500))
								.add(discrepancyAllocation.getFakeNotes100Total().multiply(BigDecimal.valueOf(100)))));

				if (discAlloc.getDiscrepancyType().equals("MUTILATED") && discAlloc.getDenomination().equals(2000)) {
					discrepancyAllocation.setMutilatedNotes2000Total(discrepancyAllocation.getMutilatedNotes2000Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("MUTILATED") && discAlloc.getDenomination().equals(500)) {
					discrepancyAllocation.setMutilatedNotes500Total(discrepancyAllocation.getMutilatedNotes500Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("MUTILATED") && discAlloc.getDenomination().equals(100)) {
					discrepancyAllocation.setMutilatedNotes100Total(discrepancyAllocation.getMutilatedNotes100Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				discrepancyAllocation.setMutilatedNotesTotalValue(discrepancyAllocation.getMutilatedNotes2000Total()
						.multiply(BigDecimal.valueOf(2000))
						.add(discrepancyAllocation.getMutilatedNotes500Total().multiply(BigDecimal.valueOf(500)).add(
								discrepancyAllocation.getMutilatedNotes100Total().multiply(BigDecimal.valueOf(100)))));

				if (discAlloc.getDiscrepancyType().equals("SHORTAGE") && discAlloc.getDenomination().equals(2000)) {
					discrepancyAllocation.setShortageNotes2000Total(discrepancyAllocation.getShortageNotes2000Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("SHORTAGE") && discAlloc.getDenomination().equals(500)) {
					discrepancyAllocation.setShortageNotes500Total(discrepancyAllocation.getShortageNotes500Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("SHORTAGE") && discAlloc.getDenomination().equals(100)) {
					discrepancyAllocation.setShortageNotes100Total(discrepancyAllocation.getShortageNotes100Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				discrepancyAllocation.setShortageNotesTotalValue(discrepancyAllocation.getShortageNotes2000Total()
						.multiply(BigDecimal.valueOf(2000))
						.add(discrepancyAllocation.getShortageNotes500Total().multiply(BigDecimal.valueOf(500)).add(
								discrepancyAllocation.getShortageNotes100Total().multiply(BigDecimal.valueOf(100)))));

				if (discAlloc.getDiscrepancyType().equals("EXCESS") && discAlloc.getDenomination().equals(2000)) {
					discrepancyAllocation.setExcessNotes2000Total(discrepancyAllocation.getExcessNotes2000Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("EXCESS") && discAlloc.getDenomination().equals(500)) {
					discrepancyAllocation.setExcessNotes500Total(discrepancyAllocation.getExcessNotes500Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				if (discAlloc.getDiscrepancyType().equals("EXCESS") && discAlloc.getDenomination().equals(100)) {
					discrepancyAllocation.setExcessNotes100Total(discrepancyAllocation.getExcessNotes100Total()
							.add(BigDecimal.valueOf(discAlloc.getNumberOfNotes())));
				}
				discrepancyAllocation.setExcessNotesTotalValue(discrepancyAllocation.getExcessNotes2000Total()
						.multiply(BigDecimal.valueOf(2000))
						.add(discrepancyAllocation.getExcessNotes500Total().multiply(BigDecimal.valueOf(500)).add(
								discrepancyAllocation.getExcessNotes100Total().multiply(BigDecimal.valueOf(100)))));

				// if(discrepancyAllocation)
				discrepancyAllocationList.add(discrepancyAllocation);
			}
			disc.setDiscrepancyAllocations(discrepancyAllocationList);
		}
		return mapList;
	}

	@Override
	public Map<String, Mutilated> getMutilatedDataForDN2(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> mutilatedList = binDashboardJpaDao.getMutilatedDataForDN2(icmcId, sDate, eDate);
		Map<String, Mutilated> mutilatedDepositList = UtilityMapper.mapTupleToMutilated(mutilatedList);
		return mutilatedDepositList;
	}

	@Override
	public List<BinTransactionBOD> processTE2(BigInteger icmcId, Calendar sDate, Calendar eDate, DateRange dateRange) {

		List<BinTransactionBOD> binTxBodList = new ArrayList<>();

		BigDecimal openingBalanceDenomination1 = BigDecimal.ZERO;
		BigDecimal openingBalanceDenomination2 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination5 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination10 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination20 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination50 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination100 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination200 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination500 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination1000 = new BigDecimal(0);
		BigDecimal openingBalanceDenomination2000 = new BigDecimal(0);

		// Opening Balance
		List<BinTransactionBOD> summaryListForOpeningBalance = this.getDataFromBinTransactionBOD(icmcId, sDate, eDate,
				CashType.NOTES, dateRange);

		BinTransactionBOD binTxnForOpeningBalance = new BinTransactionBOD();

		for (BinTransactionBOD bintxn : summaryListForOpeningBalance) {

			openingBalanceDenomination1 = bintxn.getDenomination1();
			if (openingBalanceDenomination1 == null) {
				openingBalanceDenomination1 = new BigDecimal(0);
			}
			openingBalanceDenomination2 = bintxn.getDenomination2();
			if (openingBalanceDenomination2 == null) {
				openingBalanceDenomination2 = new BigDecimal(0);
			}
			openingBalanceDenomination5 = bintxn.getDenomination5();
			if (openingBalanceDenomination5 == null) {
				openingBalanceDenomination5 = new BigDecimal(0);
			}

			openingBalanceDenomination10 = bintxn.getDenomination10();
			if (openingBalanceDenomination10 == null) {
				openingBalanceDenomination10 = new BigDecimal(0);
			}

			openingBalanceDenomination20 = bintxn.getDenomination20();
			if (openingBalanceDenomination20 == null) {
				openingBalanceDenomination20 = new BigDecimal(0);
			}

			openingBalanceDenomination50 = bintxn.getDenomination50();
			if (openingBalanceDenomination50 == null) {
				openingBalanceDenomination50 = new BigDecimal(0);
			}

			openingBalanceDenomination100 = bintxn.getDenomination100();
			if (openingBalanceDenomination100 == null) {
				openingBalanceDenomination100 = new BigDecimal(0);
			}

			openingBalanceDenomination200 = bintxn.getDenomination200();
			if (openingBalanceDenomination200 == null) {
				openingBalanceDenomination200 = new BigDecimal(0);
			}

			openingBalanceDenomination500 = bintxn.getDenomination500();
			if (openingBalanceDenomination500 == null) {
				openingBalanceDenomination500 = new BigDecimal(0);
			}
			openingBalanceDenomination1000 = bintxn.getDenomination1000();
			if (openingBalanceDenomination1000 == null) {
				openingBalanceDenomination1000 = new BigDecimal(0);
			}

			openingBalanceDenomination2000 = bintxn.getDenomination2000();
			if (openingBalanceDenomination2000 == null) {
				openingBalanceDenomination2000 = new BigDecimal(0);
			}

			binTxnForOpeningBalance.setDenomination1(openingBalanceDenomination1);
			binTxnForOpeningBalance.setDenomination2(openingBalanceDenomination2);
			binTxnForOpeningBalance.setDenomination5(openingBalanceDenomination5);
			binTxnForOpeningBalance.setDenomination10(openingBalanceDenomination10);
			binTxnForOpeningBalance.setDenomination20(openingBalanceDenomination20);
			binTxnForOpeningBalance.setDenomination50(openingBalanceDenomination50);
			binTxnForOpeningBalance.setDenomination100(openingBalanceDenomination100);
			binTxnForOpeningBalance.setDenomination200(openingBalanceDenomination200);
			binTxnForOpeningBalance.setDenomination500(openingBalanceDenomination500);
			binTxnForOpeningBalance.setDenomination1000(openingBalanceDenomination1000);
			binTxnForOpeningBalance.setDenomination2000(openingBalanceDenomination2000);

			binTxnForOpeningBalance.setTotalInPieces(
					openingBalanceDenomination1.add(openingBalanceDenomination2).add(openingBalanceDenomination5)
							.add(openingBalanceDenomination10).add(openingBalanceDenomination20)
							.add(openingBalanceDenomination50).add(openingBalanceDenomination100)
							.add(openingBalanceDenomination200).add(openingBalanceDenomination500)
							.add(openingBalanceDenomination1000).add(openingBalanceDenomination2000));

			binTxnForOpeningBalance.setTotalValueOfBankNotes(openingBalanceDenomination1.multiply(new BigDecimal(1))
					.add(openingBalanceDenomination2.multiply(new BigDecimal(2)).add(openingBalanceDenomination5
							.multiply(new BigDecimal(5)).add(openingBalanceDenomination10.multiply(new BigDecimal(10))
									.add(openingBalanceDenomination20.multiply(new BigDecimal(20))
											.add(openingBalanceDenomination50.multiply(new BigDecimal(50))
													.add(openingBalanceDenomination100.multiply(new BigDecimal(100))
															.add(openingBalanceDenomination200
																	.multiply(new BigDecimal(200)).add(
																			openingBalanceDenomination500
																					.multiply(new BigDecimal(500)).add(
																							openingBalanceDenomination1000
																									.multiply(
																											new BigDecimal(
																													1000))
																									.add(openingBalanceDenomination2000
																											.multiply(
																													new BigDecimal(
																															2000)))))))))))));
		}
		binTxBodList.add(0, binTxnForOpeningBalance);
		// Close Opening Balance

		// Remittance Received

		BigDecimal remittanceReceivedDenomination1 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination2 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination5 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination10 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination20 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination50 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination100 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination200 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination500 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination1000 = new BigDecimal(0);
		BigDecimal remittanceReceivedDenomination2000 = new BigDecimal(0);

		List<Tuple> summaryListForRemittanceRecieved = this.getRemittanceReceivedForFresh(icmcId, sDate, eDate);

		for (Tuple t : summaryListForRemittanceRecieved) {
			if (t.get(0, Integer.class).equals(1)) {
				remittanceReceivedDenomination1 = remittanceReceivedDenomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				remittanceReceivedDenomination2 = remittanceReceivedDenomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				remittanceReceivedDenomination5 = remittanceReceivedDenomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				remittanceReceivedDenomination10 = remittanceReceivedDenomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				remittanceReceivedDenomination20 = remittanceReceivedDenomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				remittanceReceivedDenomination50 = remittanceReceivedDenomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				remittanceReceivedDenomination100 = remittanceReceivedDenomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				remittanceReceivedDenomination200 = remittanceReceivedDenomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				remittanceReceivedDenomination500 = remittanceReceivedDenomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				remittanceReceivedDenomination1000 = remittanceReceivedDenomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				remittanceReceivedDenomination2000 = remittanceReceivedDenomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		List<Tuple> summrayListForDiversion = this.getDepositForDiversion(icmcId, sDate, eDate);

		for (Tuple t : summrayListForDiversion) {
			if (t.get(0, Integer.class).equals(1)) {
				remittanceReceivedDenomination1 = remittanceReceivedDenomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				remittanceReceivedDenomination2 = remittanceReceivedDenomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				remittanceReceivedDenomination5 = remittanceReceivedDenomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				remittanceReceivedDenomination10 = remittanceReceivedDenomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				remittanceReceivedDenomination20 = remittanceReceivedDenomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				remittanceReceivedDenomination50 = remittanceReceivedDenomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				remittanceReceivedDenomination100 = remittanceReceivedDenomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				remittanceReceivedDenomination200 = remittanceReceivedDenomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				remittanceReceivedDenomination500 = remittanceReceivedDenomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				remittanceReceivedDenomination1000 = remittanceReceivedDenomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				remittanceReceivedDenomination2000 = remittanceReceivedDenomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		BinTransactionBOD binTxnBODForRemittanceNotes = new BinTransactionBOD();

		binTxnBODForRemittanceNotes.setDenomination1(remittanceReceivedDenomination1);
		binTxnBODForRemittanceNotes.setDenomination2(remittanceReceivedDenomination2);
		binTxnBODForRemittanceNotes.setDenomination5(remittanceReceivedDenomination5);
		binTxnBODForRemittanceNotes.setDenomination10(remittanceReceivedDenomination10);
		binTxnBODForRemittanceNotes.setDenomination20(remittanceReceivedDenomination20);
		binTxnBODForRemittanceNotes.setDenomination50(remittanceReceivedDenomination50);
		binTxnBODForRemittanceNotes.setDenomination100(remittanceReceivedDenomination100);
		binTxnBODForRemittanceNotes.setDenomination200(remittanceReceivedDenomination200);
		binTxnBODForRemittanceNotes.setDenomination500(remittanceReceivedDenomination500);
		binTxnBODForRemittanceNotes.setDenomination1000(remittanceReceivedDenomination1000);
		binTxnBODForRemittanceNotes.setDenomination2000(remittanceReceivedDenomination2000);
		binTxnBODForRemittanceNotes.setTotalInPieces(remittanceReceivedDenomination1
				.add(remittanceReceivedDenomination2).add(remittanceReceivedDenomination5)
				.add(remittanceReceivedDenomination10).add(remittanceReceivedDenomination20)
				.add(remittanceReceivedDenomination50).add(remittanceReceivedDenomination100)
				.add(remittanceReceivedDenomination200).add(remittanceReceivedDenomination500)
				.add(remittanceReceivedDenomination1000).add(remittanceReceivedDenomination2000));
		binTxnBODForRemittanceNotes.setTotalValueOfBankNotes(remittanceReceivedDenomination1.multiply(new BigDecimal(1))
				.add(remittanceReceivedDenomination2.multiply(new BigDecimal(2)).add(remittanceReceivedDenomination5
						.multiply(new BigDecimal(5)).add(remittanceReceivedDenomination10.multiply(new BigDecimal(10))
								.add(remittanceReceivedDenomination20.multiply(new BigDecimal(20))
										.add(remittanceReceivedDenomination50.multiply(new BigDecimal(50))
												.add(remittanceReceivedDenomination100.multiply(new BigDecimal(100))
														.add(remittanceReceivedDenomination200
																.multiply(new BigDecimal(200)).add(
																		remittanceReceivedDenomination500
																				.multiply(new BigDecimal(500)).add(
																						remittanceReceivedDenomination1000
																								.multiply(
																										new BigDecimal(
																												1000))
																								.add(remittanceReceivedDenomination2000
																										.multiply(
																												new BigDecimal(
																														2000)))))))))))));

		binTxBodList.add(1, binTxnBODForRemittanceNotes);
		// Close Remittance Received

		// Start Deposit
		BinTransactionBOD binTxnBODForDeposit = new BinTransactionBOD();

		BigDecimal denomination1 = new BigDecimal(0);
		BigDecimal denomination2 = new BigDecimal(0);
		BigDecimal denomination5 = new BigDecimal(0);
		BigDecimal denomination10 = new BigDecimal(0);
		BigDecimal denomination20 = new BigDecimal(0);
		BigDecimal denomination50 = new BigDecimal(0);
		BigDecimal denomination100 = new BigDecimal(0);
		BigDecimal denomination200 = new BigDecimal(0);
		BigDecimal denomination500 = new BigDecimal(0);
		BigDecimal denomination1000 = new BigDecimal(0);
		BigDecimal denomination2000 = new BigDecimal(0);

		List<Tuple> summrayListForBranch = this.getDepositForBranch(icmcId, sDate, eDate);

		for (Tuple t : summrayListForBranch) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = t.get(1, BigDecimal.class);
			}

		}

		List<Tuple> summrayListForDSB = this.getDepositForDSB(icmcId, sDate, eDate);

		for (Tuple t : summrayListForDSB) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = denomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = denomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = denomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = denomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = denomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = denomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = denomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = denomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = denomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = denomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = denomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		List<Tuple> summrayListForOtherBank = this.getDepositForOtherBank(icmcId, sDate, eDate);

		for (Tuple t : summrayListForOtherBank) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = denomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = denomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = denomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = denomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = denomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = denomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = denomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = denomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = denomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = denomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = denomination2000.add(t.get(1, BigDecimal.class));
			}

		}
		List<Tuple> summrayListIndent = this.getDepositForIndent(icmcId, sDate, eDate);

		for (Tuple t : summrayListIndent) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = denomination1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = denomination2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = denomination5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = denomination10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = denomination20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = denomination50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = denomination100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = denomination200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = denomination500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = denomination1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = denomination2000.add(t.get(1, BigDecimal.class));
			}

		}

		binTxnBODForDeposit.setDenomination1(denomination1);
		binTxnBODForDeposit.setDenomination2(denomination2);
		binTxnBODForDeposit.setDenomination5(denomination5);
		binTxnBODForDeposit.setDenomination10(denomination10);
		binTxnBODForDeposit.setDenomination20(denomination20);
		binTxnBODForDeposit.setDenomination50(denomination50);
		binTxnBODForDeposit.setDenomination100(denomination100);
		binTxnBODForDeposit.setDenomination200(denomination200);
		binTxnBODForDeposit.setDenomination500(denomination500);
		binTxnBODForDeposit.setDenomination1000(denomination1000);
		binTxnBODForDeposit.setDenomination2000(denomination2000);
		binTxnBODForDeposit.setTotalInPieces(denomination1.add(denomination2).add(denomination5).add(denomination10)
				.add(denomination20).add(denomination50).add(denomination100).add(denomination200).add(denomination500)
				.add(denomination1000).add(denomination2000));
		binTxnBODForDeposit.setTotalValueOfBankNotes(denomination1.multiply(new BigDecimal(1)).add(denomination2
				.multiply(new BigDecimal(2))
				.add(denomination5.multiply(new BigDecimal(5)).add(denomination10.multiply(new BigDecimal(10))
						.add(denomination20.multiply(new BigDecimal(20)).add(denomination50.multiply(new BigDecimal(50))
								.add(denomination100.multiply(new BigDecimal(100)).add(denomination200
										.multiply(new BigDecimal(200)).add(denomination500.multiply(new BigDecimal(500))
												.add(denomination1000.multiply(new BigDecimal(1000))
														.add(denomination2000.multiply(new BigDecimal(2000)))))))))))));

		binTxBodList.add(2, binTxnBODForDeposit);

		// Deposit Calculation

		BigDecimal depositDenomination1 = new BigDecimal(0);
		BigDecimal depositDenomination2 = new BigDecimal(0);
		BigDecimal depositDenomination5 = new BigDecimal(0);
		BigDecimal depositDenomination10 = new BigDecimal(0);
		BigDecimal depositDenomination20 = new BigDecimal(0);
		BigDecimal depositDenomination50 = new BigDecimal(0);
		BigDecimal depositDenomination100 = new BigDecimal(0);
		BigDecimal depositDenomination200 = new BigDecimal(0);
		BigDecimal depositDenomination500 = new BigDecimal(0);
		BigDecimal depositDenomination1000 = new BigDecimal(0);
		BigDecimal depositDenomination2000 = new BigDecimal(0);

		BinTransactionBOD depositBalanceBOD = new BinTransactionBOD();

		depositDenomination1 = remittanceReceivedDenomination1.add(denomination1);
		depositDenomination2 = remittanceReceivedDenomination2.add(denomination2);
		depositDenomination5 = remittanceReceivedDenomination5.add(denomination5);
		depositDenomination10 = remittanceReceivedDenomination10.add(denomination10);
		depositDenomination20 = remittanceReceivedDenomination20.add(denomination20);
		depositDenomination50 = remittanceReceivedDenomination50.add(denomination50);
		depositDenomination100 = remittanceReceivedDenomination100.add(denomination100);
		depositDenomination200 = remittanceReceivedDenomination200.add(denomination200);
		depositDenomination500 = remittanceReceivedDenomination500.add(denomination500);
		depositDenomination1000 = remittanceReceivedDenomination1000.add(denomination1000);
		depositDenomination2000 = remittanceReceivedDenomination2000.add(denomination2000);

		depositBalanceBOD.setDenomination1(depositDenomination1);
		depositBalanceBOD.setDenomination2(depositDenomination2);
		depositBalanceBOD.setDenomination5(depositDenomination5);
		depositBalanceBOD.setDenomination10(depositDenomination10);
		depositBalanceBOD.setDenomination20(depositDenomination20);
		depositBalanceBOD.setDenomination50(depositDenomination50);
		depositBalanceBOD.setDenomination100(depositDenomination100);
		depositBalanceBOD.setDenomination200(depositDenomination200);
		depositBalanceBOD.setDenomination500(depositDenomination500);
		depositBalanceBOD.setDenomination1000(depositDenomination100);
		depositBalanceBOD.setDenomination2000(depositDenomination2000);

		depositBalanceBOD.setTotalInPieces(depositDenomination1.add(depositDenomination2).add(depositDenomination5)
				.add(depositDenomination10).add(depositDenomination20).add(depositDenomination50)
				.add(depositDenomination100).add(depositDenomination200).add(depositDenomination500)
				.add(depositDenomination1000).add(depositDenomination2000));
		depositBalanceBOD.setTotalValueOfBankNotes(depositDenomination1.multiply(new BigDecimal(1))
				.add(depositDenomination2.multiply(new BigDecimal(2)).add(depositDenomination5
						.multiply(new BigDecimal(5)).add(depositDenomination10.multiply(new BigDecimal(10)).add(
								depositDenomination20.multiply(new BigDecimal(20)).add(depositDenomination50
										.multiply(new BigDecimal(50)).add(depositDenomination100
												.multiply(new BigDecimal(100)).add(depositDenomination200
														.multiply(new BigDecimal(200)).add(depositDenomination500
																.multiply(new BigDecimal(500)).add(
																		depositDenomination1000
																				.multiply(new BigDecimal(1000)).add(
																						depositDenomination2000
																								.multiply(
																										new BigDecimal(
																												2000)))))))))))));

		binTxBodList.add(3, depositBalanceBOD);

		// End of Deposit Calculation

		BigDecimal denominationRemittanceSent1 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent2 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent5 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent10 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent20 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent50 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent100 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent200 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent500 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent1000 = new BigDecimal(0);
		BigDecimal denominationRemittanceSent2000 = new BigDecimal(0);

		List<Tuple> diversionORVAllocation = this.getWithdrawalForDiversion(icmcId, sDate, eDate);
		for (Tuple t : diversionORVAllocation) {

			if (t.get(0, Integer.class).equals(1)) {
				denominationRemittanceSent1 = denominationRemittanceSent1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denominationRemittanceSent2 = denominationRemittanceSent2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denominationRemittanceSent5 = denominationRemittanceSent5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denominationRemittanceSent10 = denominationRemittanceSent10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denominationRemittanceSent20 = denominationRemittanceSent20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denominationRemittanceSent50 = denominationRemittanceSent50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denominationRemittanceSent100 = denominationRemittanceSent100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denominationRemittanceSent200 = denominationRemittanceSent200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denominationRemittanceSent500 = denominationRemittanceSent500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denominationRemittanceSent1000 = denominationRemittanceSent1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denominationRemittanceSent2000 = denominationRemittanceSent2000.add(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> summaryListForRemittanceSent = this.getRemittanceSentForSoiled(icmcId, sDate, eDate);

		for (Tuple t : summaryListForRemittanceSent) {
			if (t.get(0, Integer.class).equals(1)) {
				denominationRemittanceSent1 = denominationRemittanceSent1.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denominationRemittanceSent2 = denominationRemittanceSent2.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denominationRemittanceSent5 = denominationRemittanceSent5.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denominationRemittanceSent10 = denominationRemittanceSent10.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denominationRemittanceSent20 = denominationRemittanceSent20.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denominationRemittanceSent50 = denominationRemittanceSent50.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denominationRemittanceSent100 = denominationRemittanceSent100.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denominationRemittanceSent200 = denominationRemittanceSent200.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denominationRemittanceSent500 = denominationRemittanceSent500.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denominationRemittanceSent1000 = denominationRemittanceSent1000.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denominationRemittanceSent2000 = denominationRemittanceSent2000.add(t.get(1, BigDecimal.class));
			}

		}

		BinTransactionBOD remittanceSent = new BinTransactionBOD();

		remittanceSent.setDenomination1(denominationRemittanceSent1);
		remittanceSent.setDenomination2(denominationRemittanceSent2);
		remittanceSent.setDenomination5(denominationRemittanceSent5);
		remittanceSent.setDenomination10(denominationRemittanceSent10);
		remittanceSent.setDenomination20(denominationRemittanceSent20);
		remittanceSent.setDenomination50(denominationRemittanceSent50);
		remittanceSent.setDenomination100(denominationRemittanceSent100);
		remittanceSent.setDenomination200(denominationRemittanceSent200);
		remittanceSent.setDenomination500(denominationRemittanceSent500);
		remittanceSent.setDenomination1000(denominationRemittanceSent1000);
		remittanceSent.setDenomination2000(denominationRemittanceSent2000);

		// Close remittance Sent

		BigDecimal denomination1W = new BigDecimal(0);
		BigDecimal denomination2W = new BigDecimal(0);
		BigDecimal denomination5W = new BigDecimal(0);
		BigDecimal denomination10W = new BigDecimal(0);
		BigDecimal denomination20W = new BigDecimal(0);
		BigDecimal denomination50W = new BigDecimal(0);
		BigDecimal denomination100W = new BigDecimal(0);
		BigDecimal denomination200W = new BigDecimal(0);
		BigDecimal denomination500W = new BigDecimal(0);
		BigDecimal denomination1000W = new BigDecimal(0);
		BigDecimal denomination2000W = new BigDecimal(0);

		List<Tuple> craAllocationList = this.getWithdrawalForCRA(icmcId, sDate, eDate);

		for (Tuple t : craAllocationList) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = t.get(1, BigDecimal.class);
			}

		}

		List<Tuple> otherBankList = this.getWithdrawalForOtherBank(icmcId, sDate, eDate);
		for (Tuple t : otherBankList) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = denomination1W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = denomination2W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = denomination5W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = denomination10W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = denomination20W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = denomination50W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = denomination100W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = denomination200W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = denomination500W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = denomination1000W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = denomination2000W.add(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> SASAllocation = this.getWithdrawalForBranch(icmcId, sDate, eDate);

		for (Tuple t : SASAllocation) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = denomination1W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = denomination2W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = denomination5W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = denomination10W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = denomination20W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = denomination50W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = denomination100W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = denomination200W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = denomination500W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = denomination1000W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = denomination2000W.add(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> indentWithdrawal = this.getDepositForIndent(icmcId, sDate, eDate);

		for (Tuple t : indentWithdrawal) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1W = denomination1W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2W = denomination2W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5W = denomination5W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10W = denomination10W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20W = denomination20W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50W = denomination50W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100W = denomination100W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200W = denomination200W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500W = denomination500W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000W = denomination1000W.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000W = denomination2000W.add(t.get(1, BigDecimal.class));
			}
		}

		BinTransactionBOD binTxnBODForWithdrawal = new BinTransactionBOD();

		binTxnBODForWithdrawal.setDenomination1(denomination1W);
		binTxnBODForWithdrawal.setDenomination2(denomination2W);
		binTxnBODForWithdrawal.setDenomination5(denomination5W);
		binTxnBODForWithdrawal.setDenomination10(denomination10W);
		binTxnBODForWithdrawal.setDenomination20(denomination20W);
		binTxnBODForWithdrawal.setDenomination50(denomination50W);
		binTxnBODForWithdrawal.setDenomination100(denomination100W);
		binTxnBODForWithdrawal.setDenomination200(denomination200W);
		binTxnBODForWithdrawal.setDenomination500(denomination500W);
		binTxnBODForWithdrawal.setDenomination1000(denomination1000W);
		binTxnBODForWithdrawal.setDenomination2000(denomination2000W);

		// Total Witdrawal Calculation

		BigDecimal withdrawalDenomination1 = new BigDecimal(0);
		BigDecimal withdrawalDenomination2 = new BigDecimal(0);
		BigDecimal withdrawalDenomination5 = new BigDecimal(0);
		BigDecimal withdrawalDenomination10 = new BigDecimal(0);
		BigDecimal withdrawalDenomination20 = new BigDecimal(0);
		BigDecimal withdrawalDenomination50 = new BigDecimal(0);
		BigDecimal withdrawalDenomination100 = new BigDecimal(0);
		BigDecimal withdrawalDenomination200 = new BigDecimal(0);
		BigDecimal withdrawalDenomination500 = new BigDecimal(0);
		BigDecimal withdrawalDenomination1000 = new BigDecimal(0);
		BigDecimal withdrawalDenomination2000 = new BigDecimal(0);

		BinTransactionBOD withdrawalBalanceBOD = new BinTransactionBOD();

		withdrawalDenomination1 = denominationRemittanceSent1.add(denomination1W);
		withdrawalDenomination2 = denominationRemittanceSent2.add(denomination2W);
		withdrawalDenomination5 = denominationRemittanceSent5.add(denomination5W);
		withdrawalDenomination10 = denominationRemittanceSent10.add(denomination10W);
		withdrawalDenomination20 = denominationRemittanceSent20.add(denomination20W);
		withdrawalDenomination50 = denominationRemittanceSent50.add(denomination50W);
		withdrawalDenomination100 = denominationRemittanceSent100.add(denomination100W);
		withdrawalDenomination200 = denominationRemittanceSent200.add(denomination200W);
		withdrawalDenomination500 = denominationRemittanceSent500.add(denomination500W);
		withdrawalDenomination1000 = denominationRemittanceSent1000.add(denomination1000W);
		withdrawalDenomination2000 = denominationRemittanceSent2000.add(denomination2000W);

		withdrawalBalanceBOD.setDenomination1(withdrawalDenomination1);
		withdrawalBalanceBOD.setDenomination2(withdrawalDenomination2);
		withdrawalBalanceBOD.setDenomination5(withdrawalDenomination5);
		withdrawalBalanceBOD.setDenomination10(withdrawalDenomination10);
		withdrawalBalanceBOD.setDenomination20(withdrawalDenomination20);
		withdrawalBalanceBOD.setDenomination50(withdrawalDenomination50);
		withdrawalBalanceBOD.setDenomination100(withdrawalDenomination100);
		withdrawalBalanceBOD.setDenomination200(withdrawalDenomination200);
		withdrawalBalanceBOD.setDenomination500(withdrawalDenomination500);
		withdrawalBalanceBOD.setDenomination1000(withdrawalDenomination100);
		withdrawalBalanceBOD.setDenomination2000(withdrawalDenomination2000);

		withdrawalBalanceBOD.setTotalInPieces(withdrawalDenomination1.add(withdrawalDenomination2)
				.add(withdrawalDenomination5).add(withdrawalDenomination10).add(withdrawalDenomination20)
				.add(withdrawalDenomination50).add(withdrawalDenomination100).add(withdrawalDenomination200)
				.add(withdrawalDenomination500).add(withdrawalDenomination1000).add(withdrawalDenomination2000));
		withdrawalBalanceBOD.setTotalValueOfBankNotes(withdrawalDenomination1.multiply(new BigDecimal(1)).add(
				withdrawalDenomination2.multiply(new BigDecimal(2)).add(withdrawalDenomination5
						.multiply(new BigDecimal(5)).add(withdrawalDenomination10.multiply(new BigDecimal(10))
								.add(withdrawalDenomination20.multiply(new BigDecimal(20)).add(withdrawalDenomination50
										.multiply(new BigDecimal(50)).add(withdrawalDenomination100
												.multiply(new BigDecimal(100)).add(withdrawalDenomination200
														.multiply(new BigDecimal(200)).add(withdrawalDenomination500
																.multiply(new BigDecimal(500)).add(
																		withdrawalDenomination1000
																				.multiply(new BigDecimal(1000)).add(
																						withdrawalDenomination2000
																								.multiply(
																										new BigDecimal(
																												2000)))))))))))));

		binTxBodList.add(4, withdrawalBalanceBOD);

		BigDecimal closingBalanceDenomination1 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination2 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination5 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination10 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination20 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination50 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination100 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination200 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination500 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination1000 = new BigDecimal(0);
		BigDecimal closingBalanceDenomination2000 = new BigDecimal(0);

		BinTransactionBOD closingBalanceBOD = new BinTransactionBOD();

		closingBalanceDenomination1 = openingBalanceDenomination1.add(depositDenomination1)
				.subtract(withdrawalDenomination1);
		closingBalanceDenomination2 = openingBalanceDenomination2.add(depositDenomination2)
				.subtract(withdrawalDenomination2);
		closingBalanceDenomination5 = openingBalanceDenomination5.add(depositDenomination5)
				.subtract(withdrawalDenomination5);
		closingBalanceDenomination10 = openingBalanceDenomination10.add(depositDenomination10)
				.subtract(withdrawalDenomination10);
		closingBalanceDenomination20 = openingBalanceDenomination20.add(depositDenomination20)
				.subtract(withdrawalDenomination20);
		closingBalanceDenomination50 = openingBalanceDenomination50.add(depositDenomination50)
				.subtract(withdrawalDenomination50);
		closingBalanceDenomination100 = openingBalanceDenomination100.add(depositDenomination100)
				.subtract(withdrawalDenomination100);
		closingBalanceDenomination200 = openingBalanceDenomination200.add(depositDenomination200)
				.subtract(withdrawalDenomination200);
		closingBalanceDenomination500 = openingBalanceDenomination500.add(depositDenomination500)
				.subtract(withdrawalDenomination500);
		closingBalanceDenomination1000 = openingBalanceDenomination1000.add(depositDenomination1000)
				.subtract(withdrawalDenomination1000);
		closingBalanceDenomination2000 = openingBalanceDenomination2000.add(depositDenomination2000)
				.subtract(withdrawalDenomination2000);

		closingBalanceBOD.setDenomination1(closingBalanceDenomination1);
		closingBalanceBOD.setDenomination2(closingBalanceDenomination2);
		closingBalanceBOD.setDenomination5(closingBalanceDenomination5);
		closingBalanceBOD.setDenomination10(closingBalanceDenomination10);
		closingBalanceBOD.setDenomination20(closingBalanceDenomination20);
		closingBalanceBOD.setDenomination50(closingBalanceDenomination50);
		closingBalanceBOD.setDenomination100(closingBalanceDenomination100);
		closingBalanceBOD.setDenomination200(closingBalanceDenomination200);
		closingBalanceBOD.setDenomination500(closingBalanceDenomination500);
		closingBalanceBOD.setDenomination1000(closingBalanceDenomination1000);
		closingBalanceBOD.setDenomination2000(closingBalanceDenomination2000);

		closingBalanceBOD.setTotalInPieces(closingBalanceDenomination1.add(closingBalanceDenomination2)
				.add(closingBalanceDenomination5).add(closingBalanceDenomination10).add(closingBalanceDenomination20)
				.add(closingBalanceDenomination50).add(closingBalanceDenomination100).add(closingBalanceDenomination200)
				.add(closingBalanceDenomination500).add(closingBalanceDenomination1000)
				.add(closingBalanceDenomination2000));
		closingBalanceBOD.setTotalValueOfBankNotes(closingBalanceDenomination1.multiply(new BigDecimal(1)).add(
				closingBalanceDenomination2.multiply(new BigDecimal(2)).add(closingBalanceDenomination5
						.multiply(new BigDecimal(5)).add(closingBalanceDenomination10.multiply(new BigDecimal(10))
								.add(closingBalanceDenomination20.multiply(new BigDecimal(20))
										.add(closingBalanceDenomination50.multiply(new BigDecimal(50))
												.add(closingBalanceDenomination100.multiply(new BigDecimal(100))
														.add(closingBalanceDenomination200.multiply(new BigDecimal(200))
																.add(closingBalanceDenomination500
																		.multiply(new BigDecimal(500)).add(
																				closingBalanceDenomination1000
																						.multiply(new BigDecimal(
																								1000))
																						.add(closingBalanceDenomination2000
																								.multiply(
																										new BigDecimal(
																												2000)))))))))))));

		binTxBodList.add(5, closingBalanceBOD);

		// Start Coins opening balance Info
		List<BinTransactionBOD> summaryListForOpeningBalanceForCoins = this.getDataFromBinTransactionBOD(icmcId, sDate,
				eDate, CashType.COINS, dateRange);

		BigDecimal openingCoinBalanceDenomination1 = BigDecimal.ZERO;
		BigDecimal openingCoinBalanceDenomination2 = new BigDecimal(0);
		BigDecimal openingCoinBalanceDenomination5 = new BigDecimal(0);
		BigDecimal openingCoinBalanceDenomination10 = new BigDecimal(0);
		BigDecimal openingCoinBalanceForAnyOtherCoin = new BigDecimal(0);

		for (BinTransactionBOD bintxn : summaryListForOpeningBalanceForCoins) {

			openingCoinBalanceDenomination1 = bintxn.getDenomination1();
			if (openingCoinBalanceDenomination1 == null) {
				openingCoinBalanceDenomination1 = new BigDecimal(0);
			}
			openingCoinBalanceDenomination2 = bintxn.getDenomination2();
			if (openingCoinBalanceDenomination2 == null) {
				openingCoinBalanceDenomination2 = new BigDecimal(0);
			}
			openingCoinBalanceDenomination5 = bintxn.getDenomination5();
			if (openingCoinBalanceDenomination5 == null) {
				openingCoinBalanceDenomination5 = new BigDecimal(0);
			}

			openingCoinBalanceDenomination10 = bintxn.getDenomination10();
			if (openingCoinBalanceDenomination10 == null) {
				openingCoinBalanceDenomination10 = new BigDecimal(0);
			}

			openingCoinBalanceForAnyOtherCoin = bintxn.getAnyOtherCoin();
			if (openingCoinBalanceForAnyOtherCoin == null) {
				openingCoinBalanceForAnyOtherCoin = new BigDecimal(0);
			}
		}

		BinTransactionBOD binTxnForOpeningCoinBalance = new BinTransactionBOD();

		binTxnForOpeningCoinBalance.setDenomination1(openingCoinBalanceDenomination1);
		binTxnForOpeningCoinBalance.setDenomination2(openingCoinBalanceDenomination2);
		binTxnForOpeningCoinBalance.setDenomination5(openingCoinBalanceDenomination5);
		binTxnForOpeningCoinBalance.setDenomination10(openingCoinBalanceDenomination10);
		binTxnForOpeningCoinBalance.setAnyOtherCoin(openingCoinBalanceForAnyOtherCoin);

		binTxnForOpeningCoinBalance.setTotalCoinsPieces(openingCoinBalanceDenomination1
				.add(openingCoinBalanceDenomination2).add(openingCoinBalanceDenomination5)
				.add(openingCoinBalanceDenomination10).add(openingCoinBalanceForAnyOtherCoin));

		binTxnForOpeningCoinBalance.setTotalValueOfCoins(openingCoinBalanceDenomination1.multiply(new BigDecimal(1))
				.add(openingCoinBalanceDenomination2.multiply(new BigDecimal(2))
						.add(openingCoinBalanceDenomination5.multiply(new BigDecimal(5))
								.add(openingCoinBalanceDenomination10.multiply(new BigDecimal(10))
										.add(openingCoinBalanceForAnyOtherCoin.multiply(new BigDecimal(1)))))));

		binTxBodList.add(6, binTxnForOpeningCoinBalance);
		// End Coins opening balance Info

		// Remittance Received for Coins
		BigDecimal coinRemittanceReceivedDenomination1 = new BigDecimal(0);
		BigDecimal coinRemittanceReceivedDenomination2 = new BigDecimal(0);
		BigDecimal coinRemittanceReceivedDenomination5 = new BigDecimal(0);
		BigDecimal coinRemittanceReceivedDenomination10 = new BigDecimal(0);

		List<Tuple> coinSummaryListForRemittanceRecieved = this.getCoinRemittanceReceivedForFresh(icmcId, sDate, eDate);

		for (Tuple t : coinSummaryListForRemittanceRecieved) {
			if (t.get(0, Integer.class).equals(1)) {
				coinRemittanceReceivedDenomination1 = BigDecimal.valueOf(t.get(1, int.class) * 2500);
			}
			if (t.get(0, Integer.class).equals(2)) {
				coinRemittanceReceivedDenomination2 = BigDecimal.valueOf(t.get(1, int.class) * 2500);
			}
			if (t.get(0, Integer.class).equals(5)) {
				coinRemittanceReceivedDenomination5 = BigDecimal.valueOf(t.get(1, int.class) * 2500);
			}
			if (t.get(0, Integer.class).equals(10)) {
				coinRemittanceReceivedDenomination10 = BigDecimal.valueOf(t.get(1, int.class) * 2000);
			}
		}

		BinTransactionBOD binTxnBODForRemittanceCoins = new BinTransactionBOD();

		binTxnBODForRemittanceCoins.setDenomination1(coinRemittanceReceivedDenomination1);
		binTxnBODForRemittanceCoins.setDenomination2(coinRemittanceReceivedDenomination2);
		binTxnBODForRemittanceCoins.setDenomination5(coinRemittanceReceivedDenomination5);
		binTxnBODForRemittanceCoins.setDenomination10(coinRemittanceReceivedDenomination10);

		binTxnBODForRemittanceCoins
				.setTotalCoinsPieces(coinRemittanceReceivedDenomination1.add(coinRemittanceReceivedDenomination2)
						.add(coinRemittanceReceivedDenomination5).add(coinRemittanceReceivedDenomination10));

		binTxnBODForRemittanceCoins.setTotalValueOfCoins(coinRemittanceReceivedDenomination1.multiply(new BigDecimal(1))
				.add(coinRemittanceReceivedDenomination2.multiply(new BigDecimal(2))
						.add(coinRemittanceReceivedDenomination5.multiply(new BigDecimal(5))
								.add(coinRemittanceReceivedDenomination10.multiply(new BigDecimal(10))))));

		binTxBodList.add(7, binTxnBODForRemittanceCoins);
		// Close Remittance Received for Coins

		// Start Coins Withdrawal Transfer
		BigDecimal denominationCoin1W = new BigDecimal(0);
		BigDecimal denominationCoin2W = new BigDecimal(0);
		BigDecimal denominationCoin5W = new BigDecimal(0);
		BigDecimal denominationCoin10W = new BigDecimal(0);
		BigDecimal multiplier2000 = BigDecimal.valueOf(2000);
		BigDecimal multiplier2500 = BigDecimal.valueOf(2500);

		List<Tuple> coinPaymentList = this.getCoinsWithdrawalForBranch(icmcId, sDate, eDate);

		for (Tuple t : coinPaymentList) {
			if (t.get(0, Integer.class).equals(1)) {
				denominationCoin1W = denominationCoin1W.add(t.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (t.get(0, Integer.class).equals(2)) {
				denominationCoin2W = denominationCoin2W.add(t.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (t.get(0, Integer.class).equals(5)) {
				denominationCoin5W = denominationCoin5W.add(t.get(1, BigDecimal.class).multiply(multiplier2500));
			}
			if (t.get(0, Integer.class).equals(10)) {
				denominationCoin10W = denominationCoin10W.add(t.get(1, BigDecimal.class).multiply(multiplier2000));
			}
		}

		BinTransactionBOD binTxnBODForCoinWithdrawal = new BinTransactionBOD();

		binTxnBODForCoinWithdrawal.setDenomination1(denominationCoin1W);
		binTxnBODForCoinWithdrawal.setDenomination2(denominationCoin2W);
		binTxnBODForCoinWithdrawal.setDenomination5(denominationCoin5W);
		binTxnBODForCoinWithdrawal.setDenomination10(denominationCoin10W);

		binTxnBODForCoinWithdrawal.setTotalCoinsPieces(
				denominationCoin1W.add(denominationCoin2W).add(denominationCoin5W).add(denominationCoin10W));

		binTxnBODForCoinWithdrawal.setTotalValueOfCoins(denominationCoin1W.multiply(new BigDecimal(1))
				.add(denominationCoin2W.multiply(new BigDecimal(2)).add(denominationCoin5W.multiply(new BigDecimal(5))
						.add(denominationCoin10W.multiply(new BigDecimal(10))))));

		binTxBodList.add(8, binTxnBODForCoinWithdrawal);
		// Close withdrawal Transfer Coins

		// Coins Total Calculation
		BigDecimal denominationCoin1Total = new BigDecimal(0);
		BigDecimal denominationCoin2Total = new BigDecimal(0);
		BigDecimal denominationCoin5Total = new BigDecimal(0);
		BigDecimal denominationCoin10Total = new BigDecimal(0);

		denominationCoin1Total = openingCoinBalanceDenomination1.add(coinRemittanceReceivedDenomination1)
				.subtract(denominationCoin1W);
		denominationCoin2Total = openingCoinBalanceDenomination2.add(coinRemittanceReceivedDenomination2)
				.subtract(denominationCoin2W);
		denominationCoin5Total = openingCoinBalanceDenomination5.add(coinRemittanceReceivedDenomination5)
				.subtract(denominationCoin5W);
		denominationCoin10Total = openingCoinBalanceDenomination10.add(coinRemittanceReceivedDenomination10)
				.subtract(denominationCoin10W);

		BinTransactionBOD binTxnBODForCoinsTotal = new BinTransactionBOD();

		binTxnBODForCoinsTotal.setDenomination1(denominationCoin1Total);
		binTxnBODForCoinsTotal.setDenomination2(denominationCoin2Total);
		binTxnBODForCoinsTotal.setDenomination5(denominationCoin5Total);
		binTxnBODForCoinsTotal.setDenomination10(denominationCoin10Total);

		binTxnBODForCoinsTotal.setTotalCoinsPieces(denominationCoin1Total.add(denominationCoin2Total)
				.add(denominationCoin5Total).add(denominationCoin10Total));

		binTxnBODForCoinsTotal.setTotalValueOfCoins(denominationCoin1Total.multiply(new BigDecimal(1))
				.add(denominationCoin2Total.multiply(new BigDecimal(2)).add(denominationCoin5Total
						.multiply(new BigDecimal(5)).add(denominationCoin10Total.multiply(new BigDecimal(10))))));

		binTxBodList.add(9, binTxnBODForCoinsTotal);
		// Close Coins Total Calculation

		return binTxBodList;

	}

	@Override
	public List<Tuple> getRemittanceSentForAllSoiled(BigInteger icmcId) {
		List<Tuple> soiledAllList = binDashboardJpaDao.getRemittanceSentForAllSoiled(icmcId);
		return soiledAllList;
	}

	@Override
	public List<Tuple> getTotalNotesForDiscrepancy(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> notesList = binDashboardJpaDao.getTotalNotesForDiscrepancy(icmcId, sDate, eDate);
		return notesList;
	}

	@Override
	public List<Discrepancy> getDiscrepancyList(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {

		// List<Discrepancy> discrepancyList =
		// binDashboardJpaDao.getDiscrepancyList(icmcId, sDate, eDate);
		List<Discrepancy> discrepancyList = null;

		if (normalOrSuspense.equalsIgnoreCase("NORMAL")) {
			discrepancyList = binDashboardJpaDao.getDiscrepancyList(icmcId, sDate, eDate, "NORMAL");
		} else if (normalOrSuspense.equalsIgnoreCase("SUSPENSE")) {
			discrepancyList = binDashboardJpaDao.getDiscrepancyList(icmcId, sDate, eDate, "SUSPENSE");
		} else {
			discrepancyList = binDashboardJpaDao.getDiscrepancyList(icmcId, sDate, eDate);
		}

		for (Discrepancy discrepancy : discrepancyList) {
			for (DiscrepancyAllocation discAllocation : discrepancy.getDiscrepancyAllocations()) {

				BigDecimal shortValue = BigDecimal.ZERO;
				BigDecimal fakeValue = BigDecimal.ZERO;
				BigDecimal mutilValue = BigDecimal.ZERO;
				BigDecimal sadMutilValue = BigDecimal.ZERO;

				discAllocation.setSairrem(BigDecimal.ZERO);
				discAllocation.setSamutcur(BigDecimal.ZERO);
				discAllocation.setSadscash(BigDecimal.ZERO);
				discAllocation.setExcess(BigDecimal.ZERO);

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("SHORTAGE")) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						shortValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}
				}

				if (!discAllocation.getDiscrepancyType().equalsIgnoreCase("SHORTAGE")
						&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("EXCESS")
						&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
						&& discAllocation.getDenomination() >= 50
						&& discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT")) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						fakeValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}

				}

				// previous code
				/*
				 * if (!discAllocation.getDiscrepancyType().equalsIgnoreCase(
				 * "SHORTAGE") &&
				 * !discAllocation.getDiscrepancyType().equalsIgnoreCase(
				 * "EXCESS") &&
				 * !discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
				 * && discAllocation.getDenomination() >= 50 &&
				 * discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT")
				 * ) {
				 * 
				 * if (discAllocation.getDiscrepancyType() != null &&
				 * discAllocation.getDiscrepancyType().equalsIgnoreCase(
				 * "MUTILATED") &&
				 * discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"))
				 * { if(discAllocation.getDenomination() !=null &&
				 * discAllocation.getNumberOfNotes() !=null){ mutilValue =
				 * BigDecimal .valueOf(discAllocation.getDenomination() *
				 * discAllocation.getNumberOfNotes() * 0.5); } }
				 * 
				 * 
				 * if (discAllocation.getDiscrepancyType() != null &&
				 * discAllocation.getDiscrepancyType().equalsIgnoreCase(
				 * "MUTILATED") &&
				 * discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE"))
				 * { if(discAllocation.getDenomination() !=null &&
				 * discAllocation.getNumberOfNotes() !=null){ mutilValue =
				 * BigDecimal .valueOf(discAllocation.getDenomination() *
				 * discAllocation.getNumberOfNotes()); } } }
				 */
				// code by shahabuddin

				if (!discAllocation.getDiscrepancyType().equalsIgnoreCase("SHORTAGE")
						&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("EXCESS")
						&& !discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
						&& discAllocation.getDenomination() >= 10
						&& discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT")) {

					if (discAllocation.getDiscrepancyType() != null
							&& discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE")) {
						if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
							mutilValue = BigDecimal.valueOf(
									discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						}
					}

					if (discAllocation.getDiscrepancyType() != null
							&& discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
							&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE")) {
						if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
							mutilValue = BigDecimal
									.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
						}
					}
				}

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
						&& discAllocation.getDenomination() <= 10
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT"))) {
					fakeValue = BigDecimal.valueOf(discAllocation.getDenomination());
					// discAllocation.setSairrem(mutilValue);
				}

				// end by shahabuddin

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
						&& discAllocation.getDenomination() >= 100
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER"))) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
						discAllocation.setSairrem(mutilValue);
					}
				}
				// previous code
				/*
				 * if
				 * (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
				 * && discAllocation.getDenomination() <= 20 &&
				 * (discrepancy.getAccountTellerCam().equalsIgnoreCase("ACCOUNT"
				 * ))) { fakeValue =
				 * BigDecimal.valueOf(discAllocation.getDenomination()); //
				 * discAllocation.setSairrem(mutilValue); }
				 */

				/*
				 * if
				 * (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
				 * && discAllocation.getDenomination() >= 100 &&
				 * (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
				 * ||
				 * discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM"))) {
				 * sadMutilValue = BigDecimal
				 * .valueOf(discAllocation.getDenomination() *
				 * discAllocation.getNumberOfNotes()); }
				 */

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
						&& discAllocation.getDenomination() <= 100
						&& discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}
				}

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
						&& discAllocation.getDenomination() >= 200
						&& discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
						discAllocation.setSairrem(mutilValue);
					}
				}

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("FAKE")
						&& discAllocation.getDenomination() <= 50
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
								|| discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM"))) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						sadMutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes());
					}
				}

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
						&& discAllocation.getDenomination() <= 50
						&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE")
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER"))) {
					sadMutilValue = BigDecimal.valueOf(discAllocation.getDenomination());
					// discAllocation.setSadscash(BigDecimal.valueOf(discAllocation.getDenomination()));
				}

				// inayat

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
								&& discAllocation.getDenomination() >= 100
								&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE"))) {
					mutilValue = BigDecimal.valueOf(discAllocation.getDenomination());
					discAllocation.setSairrem(mutilValue);
				}

				/*
				 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
				 * "MUTILATED") &&
				 * (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
				 * && discAllocation.getDenomination() <= 50 &&
				 * discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE"
				 * ))) { mutilValue =
				 * BigDecimal.valueOf(discAllocation.getDenomination());
				 * discAllocation.setSadscash(mutilValue); }
				 */

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
								&& discAllocation.getDenomination() >= 100
								&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"))) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						discAllocation.setSairrem(mutilValue);
					}
				}

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("TELLER")
								&& discAllocation.getDenomination() <= 50
								&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"))) {
					// mutilValue =
					// BigDecimal.valueOf(discAllocation.getDenomination() *
					// discAllocation.getNumberOfNotes() * 0.5);
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						sadMutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						// discAllocation.setSadscash(mutilValue);
						discAllocation.setSamutcur(sadMutilValue);
					}
				}
				// previos code
				/*
				 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
				 * "MUTILATED") &&
				 * (discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM") &&
				 * discAllocation.getDenomination() >= 100 &&
				 * discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"
				 * ))) { if(discAllocation.getDenomination() !=null &&
				 * discAllocation.getNumberOfNotes() !=null){ mutilValue =
				 * BigDecimal .valueOf(discAllocation.getDenomination() *
				 * discAllocation.getNumberOfNotes() * 0.5);
				 * discAllocation.setSairrem(mutilValue); } }
				 * 
				 * if (discAllocation.getDiscrepancyType().equalsIgnoreCase(
				 * "MUTILATED") &&
				 * (discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM") &&
				 * discAllocation.getDenomination() >= 100 &&
				 * discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE"
				 * ))) { mutilValue =
				 * BigDecimal.valueOf(discAllocation.getDenomination());
				 * discAllocation.setSairrem(mutilValue); }
				 */

				// code by shahabuddin

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")
								&& discAllocation.getDenomination() >= 10
								&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE"))) {
					mutilValue = BigDecimal
							.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
					discAllocation.setSairrem(mutilValue);
				}

				if (discAllocation.getDiscrepancyType().equalsIgnoreCase("MUTILATED")
						&& (discrepancy.getAccountTellerCam().equalsIgnoreCase("CAM")
								&& discAllocation.getDenomination() >= 10
								&& discAllocation.getMutilType().equalsIgnoreCase("ZERO VALUE"))) {
					mutilValue = BigDecimal.valueOf(discAllocation.getDenomination());
					discAllocation.setSairrem(mutilValue);
				}

				// end by shahabuddin

				//

				discAllocation.setSairrem(shortValue.add(fakeValue).add(mutilValue));
				discAllocation.setSadscash(sadMutilValue);

				if (discAllocation.getMutilType() != null
						&& discAllocation.getMutilType().equalsIgnoreCase("HALF VALUE")) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						mutilValue = BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes() * 0.5);
						discAllocation.setSamutcur(mutilValue);
					}
				}

				if (discAllocation.getDiscrepancyType() != null
						&& discAllocation.getDiscrepancyType().equalsIgnoreCase("EXCESS")) {
					if (discAllocation.getDenomination() != null && discAllocation.getNumberOfNotes() != null) {
						discAllocation.setExcess(BigDecimal
								.valueOf(discAllocation.getDenomination() * discAllocation.getNumberOfNotes()));
					}
				}
			}
		}
		this.setAccountingTxnsInDiscrepancyReport(discrepancyList);
		return discrepancyList;
	}

	@Override
	public List<Tuple> getTotalNotesForDiscrepancy(BigInteger icmcId, Calendar sDate, Calendar eDate,
			String normalOrSuspense) {
		List<Tuple> notesList = binDashboardJpaDao.getTotalNotesForDiscrepancy(icmcId, sDate, eDate, normalOrSuspense);
		return notesList;
	}

	@Override
	public List<BinTransaction> searchBins(int denomination, BigInteger icmcId) {
		List<BinTransaction> binList = binDashboardJpaDao.searchBins(denomination, icmcId);
		return binList;
	}

	@Override
	public List<String> getBinOrBoxFromBinTransactionForCashTransfer(BigInteger icmcId,
			BinCategoryType binCategoryType) {
		List<String> binOrBoxList = binDashboardJpaDao.getBinOrBoxFromBinTransactionForCashTransfer(icmcId,
				binCategoryType);
		return binOrBoxList;
	}

	@Override
	public List<String> getBinFromBinMasterForCashTransfer(BigInteger icmcId, CurrencyType binType) {
		List<String> binNumberList = binDashboardJpaDao.getBinFromBinMasterForCashTransfer(icmcId, binType);
		return binNumberList;
	}

	@Override
	public BinTransaction checkBinOrBox(BigInteger icmcId, String binNumber) {
		BinTransaction binOrBox = binDashboardJpaDao.checkBinOrBox(icmcId, binNumber);
		return binOrBox;
	}

	@Override
	public BinTransaction binDetailsByBinNumber(BigInteger icmcId, String binNumber) {
		BinTransaction binOrBox = binDashboardJpaDao.binDetailsByBinNumber(icmcId, binNumber);
		return binOrBox;
	}

	@Override
	public List<String> getBoxFromBoxMasterForCashTransfer(BigInteger icmcId, int denomination,
			CurrencyType currencyType) {
		List<String> boxNumberList = binDashboardJpaDao.getBoxFromBoxMasterForCashTransfer(icmcId, denomination,
				currencyType);
		return boxNumberList;
	}

	@Override
	public boolean cashTransferInBinTxn(BinTransaction binTransaction) {
		binDashboardJpaDao.cashTransferInBinTxn(binTransaction);
		return true;
	}

	@Override
	public boolean saveCashTransfer(CashTransfer cashTransfer) {
		binDashboardJpaDao.saveCashTransfer(cashTransfer);
		return true;
	}

	@Override
	public long updateBinTxnAfterCashTransfer(BigInteger icmcId, String binNumber) {
		long count = binDashboardJpaDao.updateBinTxnAfterCashTransfer(icmcId, binNumber);
		return count;
	}

	@Override
	public boolean saveAuditorIndentRequest(AuditorIndent auditorIndent) {
		return binDashboardJpaDao.saveAuditorIndentRequest(auditorIndent);
	}

	@Override
	public BinTransaction getDataFromBinTrxnForAuditor(BinTransaction binTxn) {
		BinTransaction binTransaction = binDashboardJpaDao.getDataFromBinTrxnForAuditor(binTxn);
		return binTransaction;
	}

	@Override
	public BinMaster getBinNumberById(Long id) {
		BinMaster binMaster = binDashboardJpaDao.getBinNumberById(id);
		return binMaster;
	}

	@Override
	public BinMaster getIsAllocatedValue(BigInteger icmcId, String binNumber) {
		BinMaster binMaster = binDashboardJpaDao.getIsAllocatedValue(icmcId, binNumber);
		return binMaster;
	}

	@Override
	public boolean updateBinMaster(BigInteger icmcId, String BinNumber, int value) {
		return binDashboardJpaDao.updateBinMaster(icmcId, BinNumber, value);
	}

	@Override
	public boolean updateBoxMaster(BigInteger icmcId, String boxNumber, int value) {
		return binDashboardJpaDao.updateBoxMaster(icmcId, boxNumber, value);
	}

	@Override
	public List<BinMaster> getDisableBin(BigInteger icmcId) {
		List<BinMaster> disableBinList = binDashboardJpaDao.getDisableBin(icmcId);
		return disableBinList;
	}

	@Override
	public boolean updateDisabledBinStatus(BigInteger icmcId, String BinNumber) {
		return binDashboardJpaDao.updateDisabledBinStatus(icmcId, BinNumber);
	}

	@Override
	public BinTransaction getBinNumListForAuditorIndent(AuditorIndent auditorIndent, CurrencyType type) {
		BinTransaction binList = binDashboardJpaDao.getBinNumListForAuditorIndent(auditorIndent, type);
		return binList;
	}

	@Override
	public boolean updateBinTxn(BigInteger icmcId, BigDecimal pendingBundle) {
		return binDashboardJpaDao.updateBinTxn(icmcId, pendingBundle);
	}

	@Override
	public List<AuditorIndent> viewAuditorIndent(BigInteger icmcId) {
		List<AuditorIndent> auditorIndentList = binDashboardJpaDao.viewAuditorIndent(icmcId);
		return auditorIndentList;
	}

	@Override
	public List<AuditorIndent> viewAuditorIndentList(BigInteger icmcId) {
		List<AuditorIndent> auditorIndentList = binDashboardJpaDao.viewAuditorIndentList(icmcId);
		return auditorIndentList;
	}

	@Override
	public boolean updateAuditorIndentStatus(AuditorIndent auditorIndent) {
		return binDashboardJpaDao.updateAuditorIndentStatus(auditorIndent);
	}

	@Override
	public BinTransaction getBinRecordForAcceptInVault(BinTransaction txn) {
		BinTransaction binTxn = binDashboardJpaDao.getBinRecordForAcceptInVault(txn);
		return binTxn;
	}

	@Override
	public boolean updateBinTxn(BinTransaction binTransaction) {
		return binDashboardJpaDao.updateBinTxn(binTransaction);
	}

	@Override
	public boolean updateBinMaster(BinMaster binMaster) {
		return binDashboardJpaDao.updateBinMaster(binMaster);
	}

	@Override
	public List<Sas> coinsRegister(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Sas> coinsList = binDashboardJpaDao.coinsRegister(icmcId, sDate, eDate);
		return coinsList;
	}

	@Override
	public boolean saveTrainingRegisterData(TrainingRegister trainingRegsiter) {
		return binDashboardJpaDao.saveTrainingRegisterData(trainingRegsiter);
	}

	@Override
	public List<TrainingRegister> getTrainingRegisterData(BigInteger icmcId) {
		List<TrainingRegister> trainingRegsiterList = binDashboardJpaDao.getTrainingRegisterData(icmcId);
		return trainingRegsiterList;
	}

	@Override
	public TrainingRegister getTrainingDataBYId(Long id) {
		TrainingRegister trainingRegister = binDashboardJpaDao.getTrainingDataBYId(id);
		return trainingRegister;
	}

	@Override
	public boolean updateTrainingRegsiter(TrainingRegister trainingRegsiter) {
		boolean isUpdate = binDashboardJpaDao.updateTrainingRegsiter(trainingRegsiter);
		return isUpdate;
	}

	@Override
	public List<TrainingRegister> getTrainingRegisterReport(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<TrainingRegister> trainingRegsiterList = binDashboardJpaDao.getTrainingRegisterReport(icmcId, sDate,
				eDate);
		return trainingRegsiterList;
	}

	@Override
	public BoxMaster getBoxCapacity(BigInteger icmcId, String boxName) {
		BoxMaster boxMaster = binDashboardJpaDao.getBoxCapacity(icmcId, boxName);
		return boxMaster;
	}

	@Override
	public BranchReceipt getBranchReceiptDetailsForCashTransferQR(BigInteger icmcId, String binNumber,
			int denomination) {
		BranchReceipt branchReceiptDetails = binDashboardJpaDao.getBranchReceiptDetailsForCashTransferQR(icmcId,
				binNumber, denomination);
		return branchReceiptDetails;
	}

	@Override
	public DSB getDSBDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination) {
		DSB dsbDetails = binDashboardJpaDao.getDSBDetailsForCashTransferQR(icmcId, binNumber, denomination);
		return dsbDetails;
	}

	@Override
	public DiversionIRV getDiversionIRVDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination) {
		DiversionIRV diversionDetails = binDashboardJpaDao.getDiversionIRVDetailsForCashTransferQR(icmcId, binNumber,
				denomination);
		return diversionDetails;
	}

	@Override
	public BankReceipt getBankReceiptDetailsForCashTransferQR(BigInteger icmcId, String binNumber, int denomination) {
		BankReceipt bankReceiptDetails = binDashboardJpaDao.getBankReceiptDetailsForCashTransferQR(icmcId, binNumber,
				denomination);
		return bankReceiptDetails;
	}

	@Override
	public List<Tuple> getCRAAllocationData(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> getCRAAllocationBundle = binDashboardJpaDao.getCRAAllocationData(icmcId, sDate, eDate);
		return getCRAAllocationBundle;
	}

	@Override
	public Map<String, CRAAllocation> getCRAForCashBookWithDrawal(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> craAllocation = binDashboardJpaDao.getCRAForCashBookWithDrawal(icmcId, sDate, eDate);
		List<Tuple> craProcess = binDashboardJpaDao.getProcessCRAForCashBookWithDrawal(icmcId, sDate, eDate);

		List<Tuple> tempCraAllocation = new ArrayList<Tuple>();
		tempCraAllocation.addAll(craAllocation);
		tempCraAllocation.addAll(craProcess);
		/*
		 * // UtilityMapper.mapTupleToCRAAllocation(craProcess); List<Tuple>
		 * tempCraAllocation = new ArrayList<Tuple>();
		 * 
		 * 
		 * for(Tuple tupleCraAllocation:craAllocation){ for(Tuple
		 * tupleCraProcess:craProcess){ if (tupleCraProcess.get(0,
		 * BigInteger.class)==(tupleCraAllocation.get(0, BigInteger.class))) {
		 * List<Tuple> tempTuple = new ArrayList<Tuple>(); // Tuple tempTupleRec
		 * = craAllocation.get(0); tupleCraAllocation.get(2,
		 * BigDecimal.class).add(tupleCraProcess.get(2, BigDecimal.class));
		 * //tempTupleRec =tupleCraAllocation.get(0, BigInteger.class); //
		 * BigInteger processCraId Integer craId =new Integer(0); Integer
		 * denom=new Integer(0); BigDecimal denomBundleValue= new BigDecimal(0);
		 * tempTuple.add((Tuple)tupleCraAllocation.get(0, BigInteger.class));
		 * tempTuple.add((Tuple)tupleCraAllocation.get(1, BigInteger.class));
		 * tempTuple.add((Tuple)tupleCraAllocation);
		 * tempCraAllocation.add((Tuple) tempTuple);
		 * 
		 * 
		 * }else { tempCraAllocation.add(tupleCraAllocation); } } //List<Tuple>
		 * craList =
		 * binDashboardJpaDao.getWithdrawalForProcessCRAById(craAllo.get(index,
		 * type)); }
		 */
		Map<String, CRAAllocation> craWithdrawalListList = UtilityMapper.mapTupleToCRAAllocation(tempCraAllocation);
		for (Map.Entry<String, CRAAllocation> entry : craWithdrawalListList.entrySet()) {
			CRA cra = binDashboardJpaDao.getBranchVendorAndMSPFromCRA(icmcId, entry.getValue().getCraId());
			String branchName = cra.getBranch();
			String vendor = cra.getVendor();
			String msp = cra.getMspName();

			Calendar insertTime = binDashboardJpaDao.getInsertTimeFromCRA(icmcId, branchName, sDate, eDate);

			entry.getValue().setBranchName(branchName);
			entry.getValue().setVendor(vendor);
			entry.getValue().setMsp(msp);
			entry.getValue().setInsertTime(insertTime);
		}
		return craWithdrawalListList;
	}

	@Override
	public Map<String, DiversionORVAllocation> getoutwardDiversionForCashBookWithDrawal(BigInteger icmcId,
			Calendar sDate, Calendar eDate) {
		List<Tuple> dirvAllocation = binDashboardJpaDao.getoutwardDiversionForCashBookWithDrawal(icmcId, sDate, eDate);
		Map<String, DiversionORVAllocation> diversionWithdrawalListList = UtilityMapper
				.mapTupleToDiversionORVAllocation(dirvAllocation);
		for (Map.Entry<String, DiversionORVAllocation> entry : diversionWithdrawalListList.entrySet()) {
			String branchName = binDashboardJpaDao.getBranchFromDiversion(icmcId, entry.getValue().getDiversionOrvId());
			Calendar insertTime = binDashboardJpaDao.getInsertTimeFromDiversionORV(icmcId,
					entry.getValue().getDiversionOrvId(), sDate, eDate);
			entry.getValue().setBranchName(branchName);
			entry.getValue().setInsertTime(insertTime);
		}
		return diversionWithdrawalListList;
	}

	@Override
	public CRA getBranchVendorAndMSPFromCRA(BigInteger icmcId, long id) {
		CRA cra = binDashboardJpaDao.getBranchVendorAndMSPFromCRA(icmcId, id);
		return cra;
	}

	@Override
	public Map<String, OtherBankAllocation> getOtherBankAllocationForCashBookWithDrawal(BigInteger icmcId,
			Calendar sDate, Calendar eDate) {
		List<Tuple> otherBankAllocation = binDashboardJpaDao.getOtherBankAllocationForCashBookWithDrawal(icmcId, sDate,
				eDate);
		Map<String, OtherBankAllocation> otherBankAllocationList = UtilityMapper
				.mapTupleToOtherBankAllocation(otherBankAllocation);

		for (Map.Entry<String, OtherBankAllocation> entry : otherBankAllocationList.entrySet()) {
			String branchName = binDashboardJpaDao.getBranchFromOtherBank(icmcId, entry.getValue().getOtherBankId());
			String bankName = binDashboardJpaDao.getBankFromOtherBank(icmcId, entry.getValue().getOtherBankId());
			Calendar insertTime = binDashboardJpaDao.getInsertTimeFromOtherBank(icmcId,
					entry.getValue().getOtherBankId(), sDate, eDate);
			entry.getValue().setBranchName(branchName);
			entry.getValue().setBankName(bankName);
			entry.getValue().setInsertTime(insertTime);
		}
		return otherBankAllocationList;
	}

	@Override
	public String getBranchFromDiversion(BigInteger icmcId, long id) {
		String branchName = binDashboardJpaDao.getBranchFromDiversion(icmcId, id);
		return branchName;
	}

	@Override
	public String getBranchFromOtherBank(BigInteger icmcId, long id) {
		String branchName = binDashboardJpaDao.getBranchFromOtherBank(icmcId, id);
		return branchName;
	}

	@Override
	public Map<String, SoiledRemittanceAllocation> getSoiledAllocationForCashBookWithDrawal(BigInteger icmcId,
			Calendar sDate, Calendar eDate) {
		List<Tuple> soiledList = binDashboardJpaDao.getSoiledAllocationForCashBookWithDrawal(icmcId, sDate, eDate);
		Map<String, SoiledRemittanceAllocation> soiledListData = UtilityMapper.mapTupleToSoiledAllocation(soiledList);
		return soiledListData;
	}

	@Override
	public List<Tuple> getDataFromBranchForCashMovementRegister(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		List<Tuple> branchList = binDashboardJpaDao.getDataFromBranchForCashMovementRegister(icmcId, sDate, eDate,
				currencyType);
		return branchList;
	}

	@Override
	public List<Tuple> getWithdrawalForCRAForCashMovement(BigInteger icmcId, Calendar sDate, Calendar eDate,
			CurrencyType currencyType) {
		List<Tuple> craList = binDashboardJpaDao.getWithdrawalForCRAForCashMovement(icmcId, sDate, eDate, currencyType);
		return craList;
	}

	@Override
	public List<BinTransactionBOD> cashMovementRegister(BigInteger icmcId, Calendar sDate, Calendar eDate,
			DateRange dateRange) {

		List<BinTransactionBOD> binTxBodList = new ArrayList<>();

		// ATM

		BigDecimal denomination1ATMW = new BigDecimal(0);
		BigDecimal denomination2ATMW = new BigDecimal(0);
		BigDecimal denomination5ATMW = new BigDecimal(0);
		BigDecimal denomination10ATMW = new BigDecimal(0);
		BigDecimal denomination20ATMW = new BigDecimal(0);
		BigDecimal denomination50ATMW = new BigDecimal(0);
		BigDecimal denomination100ATMW = new BigDecimal(0);
		BigDecimal denomination200ATMW = new BigDecimal(0);
		BigDecimal denomination500ATMW = new BigDecimal(0);
		BigDecimal denomination1000ATMW = new BigDecimal(0);
		BigDecimal denomination2000ATMW = new BigDecimal(0);

		List<Tuple> craAllocationListATM = this.getWithdrawalForCRAForCashMovement(icmcId, sDate, eDate,
				CurrencyType.ATM);
		for (Tuple t : craAllocationListATM) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000ATMW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000ATMW = t.get(1, BigDecimal.class);
			}
		}

		List<Tuple> SASAllocationATM = this.getDataFromBranchForCashMovementRegister(icmcId, sDate, eDate,
				CurrencyType.ATM);
		for (Tuple t : SASAllocationATM) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1ATMW = denomination1ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2ATMW = denomination2ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5ATMW = denomination5ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10ATMW = denomination10ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20ATMW = denomination20ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50ATMW = denomination50ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100ATMW = denomination100ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200ATMW = denomination200ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500ATMW = denomination500ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000ATMW = denomination1000ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000ATMW = denomination2000ATMW.add(t.get(1, BigDecimal.class));
			}
		}

		// Add Fresh in ATM

		List<Tuple> craAllocationListFresh = this.getWithdrawalForCRAForCashMovement(icmcId, sDate, eDate,
				CurrencyType.FRESH);
		for (Tuple t : craAllocationListFresh) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1ATMW = denomination1ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2ATMW = denomination2ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5ATMW = denomination5ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10ATMW = denomination10ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20ATMW = denomination20ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50ATMW = denomination50ATMW.add(t.get(1, BigDecimal.class));
				;
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100ATMW = denomination100ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200ATMW = denomination200ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500ATMW = denomination500ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000ATMW = denomination1000ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000ATMW = denomination2000ATMW.add(t.get(1, BigDecimal.class));
			}
		}

		List<Tuple> SASAllocationFRESH = this.getDataFromBranchForCashMovementRegister(icmcId, sDate, eDate,
				CurrencyType.FRESH);
		for (Tuple t : SASAllocationFRESH) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1ATMW = denomination1ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2ATMW = denomination2ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5ATMW = denomination5ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10ATMW = denomination10ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20ATMW = denomination20ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50ATMW = denomination50ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100ATMW = denomination100ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200ATMW = denomination200ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500ATMW = denomination500ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000ATMW = denomination1000ATMW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000ATMW = denomination2000ATMW.add(t.get(1, BigDecimal.class));
			}
		}

		BinTransactionBOD binTxnBODForWithdrawalATM = new BinTransactionBOD();

		binTxnBODForWithdrawalATM.setDenomination1(denomination1ATMW);
		binTxnBODForWithdrawalATM.setDenomination2(denomination2ATMW);
		binTxnBODForWithdrawalATM.setDenomination5(denomination5ATMW);
		binTxnBODForWithdrawalATM.setDenomination10(denomination10ATMW);
		binTxnBODForWithdrawalATM.setDenomination20(denomination20ATMW);
		binTxnBODForWithdrawalATM.setDenomination50(denomination50ATMW);
		binTxnBODForWithdrawalATM.setDenomination100(denomination100ATMW);
		binTxnBODForWithdrawalATM.setDenomination200(denomination200ATMW);
		binTxnBODForWithdrawalATM.setDenomination500(denomination500ATMW);
		binTxnBODForWithdrawalATM.setDenomination1000(denomination1000ATMW);
		binTxnBODForWithdrawalATM.setDenomination2000(denomination2000ATMW);
		binTxnBODForWithdrawalATM.setTotalInPieces(denomination1ATMW.add(denomination2ATMW).add(denomination5ATMW)
				.add(denomination10ATMW).add(denomination20ATMW).add(denomination50ATMW).add(denomination100ATMW)
				.add(denomination200ATMW).add(denomination500ATMW).add(denomination1000ATMW).add(denomination2000ATMW));
		binTxnBODForWithdrawalATM.setTotalValueOfBankNotes(denomination1ATMW.multiply(new BigDecimal(1)).add(
				denomination2ATMW.multiply(new BigDecimal(2)).add(denomination5ATMW.multiply(new BigDecimal(5)).add(
						denomination10ATMW.multiply(new BigDecimal(10)).add(denomination20ATMW
								.multiply(new BigDecimal(20)).add(denomination50ATMW.multiply(new BigDecimal(50))
										.add(denomination100ATMW.multiply(new BigDecimal(100))
												.add(denomination200ATMW.multiply(new BigDecimal(200))).add(
														denomination500ATMW.multiply(new BigDecimal(500))
																.add(denomination1000ATMW.multiply(new BigDecimal(1000))
																		.add(denomination2000ATMW.multiply(
																				new BigDecimal(2000))))))))))));

		binTxBodList.add(0, binTxnBODForWithdrawalATM);

		// ISSUABLE

		BigDecimal denomination1ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination2ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination5ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination10ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination20ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination50ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination100ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination200ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination500ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination1000ISSUABLEW = new BigDecimal(0);
		BigDecimal denomination2000ISSUABLEW = new BigDecimal(0);

		List<Tuple> craAllocationListIssuable = this.getWithdrawalForCRAForCashMovement(icmcId, sDate, eDate,
				CurrencyType.ISSUABLE);
		for (Tuple t : craAllocationListIssuable) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000ISSUABLEW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000ISSUABLEW = t.get(1, BigDecimal.class);
			}
		}

		List<Tuple> SASAllocationIssuable = this.getDataFromBranchForCashMovementRegister(icmcId, sDate, eDate,
				CurrencyType.ISSUABLE);
		for (Tuple t : SASAllocationIssuable) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1ISSUABLEW = denomination1ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2ISSUABLEW = denomination2ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5ISSUABLEW = denomination5ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10ISSUABLEW = denomination10ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20ISSUABLEW = denomination20ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50ISSUABLEW = denomination50ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100ISSUABLEW = denomination100ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200ISSUABLEW = denomination200ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500ISSUABLEW = denomination500ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000ISSUABLEW = denomination1000ISSUABLEW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000ISSUABLEW = denomination2000ISSUABLEW.add(t.get(1, BigDecimal.class));
			}
		}

		BinTransactionBOD binTxnBODForWithdrawalISSUABLE = new BinTransactionBOD();

		binTxnBODForWithdrawalISSUABLE.setDenomination1(denomination1ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination2(denomination2ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination5(denomination5ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination10(denomination10ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination20(denomination20ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination50(denomination50ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination100(denomination100ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination200(denomination200ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination500(denomination500ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination1000(denomination1000ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setDenomination2000(denomination2000ISSUABLEW);
		binTxnBODForWithdrawalISSUABLE.setTotalInPieces(denomination1ISSUABLEW.add(denomination2ISSUABLEW)
				.add(denomination5ISSUABLEW).add(denomination10ISSUABLEW).add(denomination20ISSUABLEW)
				.add(denomination50ISSUABLEW).add(denomination100ISSUABLEW).add(denomination200ISSUABLEW)
				.add(denomination500ISSUABLEW).add(denomination1000ISSUABLEW).add(denomination2000ISSUABLEW));
		binTxnBODForWithdrawalISSUABLE.setTotalValueOfBankNotes(denomination1ISSUABLEW.multiply(new BigDecimal(1)).add(
				denomination2ISSUABLEW.multiply(new BigDecimal(2)).add(denomination5ISSUABLEW
						.multiply(new BigDecimal(5)).add(denomination10ISSUABLEW.multiply(new BigDecimal(10))
								.add(denomination20ISSUABLEW.multiply(new BigDecimal(20)).add(denomination50ISSUABLEW
										.multiply(new BigDecimal(50)).add(denomination100ISSUABLEW
												.multiply(new BigDecimal(100)).add(
														denomination200ISSUABLEW.multiply(new BigDecimal(200)))
												.add(denomination500ISSUABLEW.multiply(new BigDecimal(500))
														.add(denomination1000ISSUABLEW.multiply(new BigDecimal(1000))
																.add(denomination2000ISSUABLEW
																		.multiply(new BigDecimal(2000))))))))))));

		binTxBodList.add(1, binTxnBODForWithdrawalISSUABLE);

		BinTransactionBOD binTxnBODForDeposit = new BinTransactionBOD();

		BigDecimal denomination1 = new BigDecimal(0);
		BigDecimal denomination2 = new BigDecimal(0);
		BigDecimal denomination5 = new BigDecimal(0);
		BigDecimal denomination10 = new BigDecimal(0);
		BigDecimal denomination20 = new BigDecimal(0);
		BigDecimal denomination50 = new BigDecimal(0);
		BigDecimal denomination100 = new BigDecimal(0);
		BigDecimal denomination200 = new BigDecimal(0);
		BigDecimal denomination500 = new BigDecimal(0);
		BigDecimal denomination1000 = new BigDecimal(0);
		BigDecimal denomination2000 = new BigDecimal(0);

		List<Tuple> summrayListForBranch = this.getDepositForBranchForCashMovement(icmcId, sDate, eDate);

		for (Tuple t : summrayListForBranch) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000 = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000 = t.get(1, BigDecimal.class);
			}

		}

		binTxnBODForDeposit.setDenomination1(denomination1);
		binTxnBODForDeposit.setDenomination2(denomination2);
		binTxnBODForDeposit.setDenomination5(denomination5);
		binTxnBODForDeposit.setDenomination10(denomination10);
		binTxnBODForDeposit.setDenomination20(denomination20);
		binTxnBODForDeposit.setDenomination50(denomination50);
		binTxnBODForDeposit.setDenomination100(denomination100);
		binTxnBODForDeposit.setDenomination200(denomination200);
		binTxnBODForDeposit.setDenomination500(denomination500);
		binTxnBODForDeposit.setDenomination1000(denomination1000);
		binTxnBODForDeposit.setDenomination2000(denomination2000);
		binTxnBODForDeposit.setTotalInPieces(denomination1.add(denomination2).add(denomination5).add(denomination10)
				.add(denomination20).add(denomination50).add(denomination100).add(denomination200).add(denomination500)
				.add(denomination1000).add(denomination2000));
		binTxnBODForDeposit.setTotalValueOfBankNotes(denomination1.multiply(new BigDecimal(1)).add(denomination2
				.multiply(new BigDecimal(2))
				.add(denomination5.multiply(new BigDecimal(5)).add(denomination10.multiply(new BigDecimal(10))
						.add(denomination20.multiply(new BigDecimal(20)).add(denomination50.multiply(new BigDecimal(50))
								.add(denomination100.multiply(new BigDecimal(100)).add(denomination200
										.multiply(new BigDecimal(200)).add(denomination500.multiply(new BigDecimal(500))
												.add(denomination1000.multiply(new BigDecimal(1000))
														.add(denomination2000.multiply(new BigDecimal(2000)))))))))))));

		binTxBodList.add(2, binTxnBODForDeposit);

		BinTransactionBOD atmAndIssuable = new BinTransactionBOD();

		BigDecimal denomination1Sum = new BigDecimal(0);
		BigDecimal denomination2Sum = new BigDecimal(0);
		BigDecimal denomination5Sum = new BigDecimal(0);
		BigDecimal denomination10Sum = new BigDecimal(0);
		BigDecimal denomination20Sum = new BigDecimal(0);
		BigDecimal denomination50Sum = new BigDecimal(0);
		BigDecimal denomination100Sum = new BigDecimal(0);
		BigDecimal denomination200Sum = new BigDecimal(0);
		BigDecimal denomination500Sum = new BigDecimal(0);
		BigDecimal denomination1000Sum = new BigDecimal(0);
		BigDecimal denomination2000Sum = new BigDecimal(0);

		denomination2000Sum = denomination2000ATMW.add(denomination2000ISSUABLEW);
		denomination1000Sum = denomination1000ATMW.add(denomination1000ISSUABLEW);
		denomination500Sum = denomination500ATMW.add(denomination500ISSUABLEW);
		denomination200Sum = denomination200ATMW.add(denomination200ISSUABLEW);
		denomination100Sum = denomination100ATMW.add(denomination100ISSUABLEW);
		denomination50Sum = denomination50ATMW.add(denomination50ISSUABLEW);
		denomination20Sum = denomination20ATMW.add(denomination20ISSUABLEW);
		denomination10Sum = denomination10ATMW.add(denomination10ISSUABLEW);
		denomination5Sum = denomination5ATMW.add(denomination5ISSUABLEW);
		denomination2Sum = denomination2ATMW.add(denomination2ISSUABLEW);
		denomination1Sum = denomination1ATMW.add(denomination1ISSUABLEW);

		atmAndIssuable.setDenomination2000(denomination2000Sum);
		atmAndIssuable.setDenomination1000(denomination1000Sum);
		atmAndIssuable.setDenomination500(denomination500Sum);
		atmAndIssuable.setDenomination200(denomination200Sum);
		atmAndIssuable.setDenomination100(denomination100Sum);
		atmAndIssuable.setDenomination50(denomination50Sum);
		atmAndIssuable.setDenomination20(denomination20Sum);
		atmAndIssuable.setDenomination10(denomination10Sum);
		atmAndIssuable.setDenomination5(denomination5Sum);
		atmAndIssuable.setDenomination2(denomination2Sum);
		atmAndIssuable.setDenomination1(denomination1Sum);

		binTxBodList.add(3, atmAndIssuable);

		// code by shahabuddin for soiled

		BigDecimal denomination1SOILEDW = new BigDecimal(0);
		BigDecimal denomination2SOILEDW = new BigDecimal(0);
		BigDecimal denomination5SOILEDW = new BigDecimal(0);
		BigDecimal denomination10SOILEDW = new BigDecimal(0);
		BigDecimal denomination20SOILEDW = new BigDecimal(0);
		BigDecimal denomination50SOILEDW = new BigDecimal(0);
		BigDecimal denomination100SOILEDW = new BigDecimal(0);
		BigDecimal denomination200SOILEDW = new BigDecimal(0);
		BigDecimal denomination500SOILEDW = new BigDecimal(0);
		BigDecimal denomination1000SOILEDW = new BigDecimal(0);
		BigDecimal denomination2000SOILEDW = new BigDecimal(0);

		List<Tuple> craAllocationListSoiled = this.getWithdrawalForCRAForCashMovement(icmcId, sDate, eDate,
				CurrencyType.SOILED);
		for (Tuple t : craAllocationListSoiled) {
			if (t.get(0, Integer.class).equals(1)) {
				denomination1SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000SOILEDW = t.get(1, BigDecimal.class);
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000SOILEDW = t.get(1, BigDecimal.class);
			}
		}

		List<Tuple> SASAllocationSoiled = this.getDataFromBranchForCashMovementRegister(icmcId, sDate, eDate,
				CurrencyType.SOILED);
		for (Tuple t : SASAllocationSoiled) {

			if (t.get(0, Integer.class).equals(1)) {
				denomination1SOILEDW = denomination1SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2)) {
				denomination2SOILEDW = denomination2SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(5)) {
				denomination5SOILEDW = denomination5SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(10)) {
				denomination10SOILEDW = denomination10SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(20)) {
				denomination20SOILEDW = denomination20SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(50)) {
				denomination50SOILEDW = denomination50SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(100)) {
				denomination100SOILEDW = denomination100SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(200)) {
				denomination200SOILEDW = denomination200SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(500)) {
				denomination500SOILEDW = denomination500SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(1000)) {
				denomination1000SOILEDW = denomination1000SOILEDW.add(t.get(1, BigDecimal.class));
			}

			if (t.get(0, Integer.class).equals(2000)) {
				denomination2000SOILEDW = denomination2000SOILEDW.add(t.get(1, BigDecimal.class));
			}
		}

		BinTransactionBOD binTxnBODForWithdrawalSOILED = new BinTransactionBOD();

		binTxnBODForWithdrawalSOILED.setDenomination1(denomination1SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination2(denomination2SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination5(denomination5SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination10(denomination10SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination20(denomination20SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination50(denomination50SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination100(denomination100SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination200(denomination200SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination500(denomination500SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination1000(denomination1000SOILEDW);
		binTxnBODForWithdrawalSOILED.setDenomination2000(denomination2000SOILEDW);
		binTxnBODForWithdrawalSOILED.setTotalInPieces(denomination1SOILEDW.add(denomination2SOILEDW)
				.add(denomination5SOILEDW).add(denomination10SOILEDW).add(denomination20SOILEDW)
				.add(denomination50SOILEDW).add(denomination100SOILEDW).add(denomination200SOILEDW)
				.add(denomination500SOILEDW).add(denomination1000SOILEDW).add(denomination2000SOILEDW));
		binTxnBODForWithdrawalSOILED.setTotalValueOfBankNotes(denomination1SOILEDW.multiply(new BigDecimal(1))
				.add(denomination2SOILEDW.multiply(new BigDecimal(2)).add(denomination5SOILEDW
						.multiply(new BigDecimal(5)).add(denomination10SOILEDW.multiply(new BigDecimal(10)).add(
								denomination20SOILEDW.multiply(new BigDecimal(20)).add(denomination50SOILEDW
										.multiply(new BigDecimal(50)).add(
												denomination100SOILEDW.multiply(new BigDecimal(100))
														.add(denomination200SOILEDW.multiply(new BigDecimal(200))).add(
																denomination500SOILEDW.multiply(new BigDecimal(500))
																		.add(denomination1000SOILEDW
																				.multiply(new BigDecimal(1000)).add(
																						denomination2000SOILEDW
																								.multiply(
																										new BigDecimal(
																												2000))))))))))));

		binTxBodList.add(4, binTxnBODForWithdrawalSOILED);

		// code end for soiled

		return binTxBodList;
	}

	@Override
	public List<Tuple> getDepositForBranchForCashMovement(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> depositListForCashMovement = binDashboardJpaDao.getDepositForBranchForCashMovement(icmcId, sDate,
				eDate);
		return depositListForCashMovement;
	}

	@Override
	public List<Tuple> getDepositFromHistory(BigInteger icmcId, String binNumber, Calendar sDate, Calendar eDate) {
		List<Tuple> historyList = binDashboardJpaDao.getDepositFromHistory(icmcId, binNumber, sDate, eDate);
		return historyList;
	}

	@Override
	public Calendar getInsertTimeFromCRA(BigInteger icmcId, String branch, Calendar sDate, Calendar eDate) {
		Calendar insertTime = binDashboardJpaDao.getInsertTimeFromCRA(icmcId, branch, sDate, eDate);
		return insertTime;
	}

	@Override
	public List<Tuple> getIBITForIRV(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> ibitList = binDashboardJpaDao.getIBITForIRV(icmcId, sDate, eDate);
		return ibitList;
	}

	@Override
	public String getLinkBranchSolID(long icmcId) {
		String linkBranchSolID = binDashboardJpaDao.getLinkBranchSolID(icmcId);
		return linkBranchSolID;
	}

	@Override
	public String getServicingICMC(String solId) {
		String servicingICMC = binDashboardJpaDao.getServicingICMC(solId);
		return servicingICMC;
	}

	@Override
	public List<Tuple> getAllReceiptDataForBinRegister(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> receiptDataList = binDashboardJpaDao.getAllReceiptDataForBinRegister(icmcId, sDate, eDate);
		return receiptDataList;
	}

	@Override
	public List<History> getBundleFromHistory(BigInteger icmcId, String binNumber, Integer denomination) {
		List<History> bundleFromHistory = binDashboardJpaDao.getBundleFromHistory(icmcId, binNumber, denomination);
		return bundleFromHistory;
	}

	@Override
	public boolean updateBundle(BigInteger icmcId, String binNumber, Integer Denomination, BigDecimal withdrawalBundle,
			long id) {
		boolean isUpdate = binDashboardJpaDao.updateBundle(icmcId, binNumber, Denomination, withdrawalBundle, id);
		return isUpdate;
	}

	@Override
	public boolean updateStatus(BigInteger icmcId, long id) {
		boolean statusUpdated = binDashboardJpaDao.updateStatus(icmcId, id);
		return statusUpdated;
	}

	@Override
	public History getWithdrawalbundle(BigInteger icmcId, String binNumber, Integer denomination) {
		History withdrawalBundle = binDashboardJpaDao.getWithdrawalbundle(icmcId, binNumber, denomination);
		return withdrawalBundle;
	}

	@Override
	public boolean insertDataInBinRegister(BinRegister binRegister) {
		boolean isSaved = binDashboardJpaDao.insertDataInBinRegister(binRegister);
		return isSaved;
	}

	@Override
	public List<History> saveDataInBinRegister(Integer denomination, String binNumber, BigDecimal bundleFromUI,
			BigInteger icmcId) {

		BigDecimal updatedBundle = BigDecimal.ZERO;
		BigDecimal withDrawalbundle = BigDecimal.ZERO;
		List<History> bundleFromHistorytable = this.getBundleFromHistory(icmcId, binNumber, denomination);
		for (History history : bundleFromHistorytable) {
			if (bundleFromUI.compareTo(history.getReceiveBundle()) >= 0) {
				updatedBundle = bundleFromUI.subtract(history.getReceiveBundle());
				this.updateBundle(icmcId, binNumber, denomination, history.getReceiveBundle(), history.getId());
				this.updateStatus(icmcId, history.getId());
				bundleFromUI = updatedBundle;
			}

			else if (bundleFromUI.compareTo(history.getReceiveBundle()) <= 0) {
				History withdrawalbundleFromDB = this.getWithdrawalbundle(icmcId, binNumber, denomination);
				withDrawalbundle = withdrawalbundleFromDB.getWithdrawalBundle();
				this.updateBundle(icmcId, binNumber, denomination, withDrawalbundle.add(bundleFromUI), history.getId());
				if (history.getReceiveBundle().equals(withDrawalbundle.add(bundleFromUI))) {
					this.updateStatus(icmcId, history.getId());
				}
				break;
			}
		}

		return bundleFromHistorytable;
	}

	@Override
	public List<Tuple> DN2Report(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> dn2Report = binDashboardJpaDao.getMutilatedDataForDN2(icmcId, sDate, eDate);
		return dn2Report;
	}

	@Override
	public List<String> getBinFromBinTransaction(BigInteger icmcId, int denomination, CurrencyType currencyType) {
		List<String> binList = binDashboardJpaDao.getBinFromBinTransaction(icmcId, denomination, currencyType);
		return binList;
	}

	@Override
	public List<Tuple> getIBITForIO2(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<Tuple> ibitList = binDashboardJpaDao.getIBITForIO2(icmcId, sDate, eDate);
		return ibitList;
	}

	@Override
	public boolean checkAvlBundleByDenoCategory(BigInteger icmcId, BinTransaction binTransaction) {
		// List<Tuple> ibitList = binDashboardJpaDao.getIBITForIO2(icmcId,
		// sDate, eDate);
		binDashboardJpaDao.checkAvlBundleByDenoCategory(icmcId, binTransaction);

		return true;
	}

	@Override
	public ChestMaster getLastChestSlipNumber(BigInteger icmcId) {
		ChestMaster chestMaster = binDashboardJpaDao.getLastChestSlipNumber(icmcId);
		return chestMaster;
	}

	@Override
	public boolean insertChestMaster(ChestMaster chestMaster) {
		binDashboardJpaDao.insertChestMaster(chestMaster);
		return true;

	}

	@Override
	public BigInteger getChestSlipNumber(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		BigInteger chestMaster = binDashboardJpaDao.getChestSlipNumber(icmcId, sDate, eDate);
		return chestMaster;
	}

	@Override
	public List<BinTransaction> getBinFroPartialTransfer(BigInteger icmcId, Integer denomination,
			CurrencyType currencyType) {
		List<BinTransaction> binDetailsFromBinTxn = binDashboardJpaDao.getBinFroPartialTransfer(icmcId, denomination,
				currencyType);
		return binDetailsFromBinTxn;
	}

	@Override
	public void deleteForMigration(BigInteger icmcId) {
		binDashboardJpaDao.deleteForMigration(icmcId);

	}

	@Override
	public void deleteTrainingRegisterById(Long id) {
		binDashboardJpaDao.deleteTrainingRegisterById(id);
	}

	@Override
	public BinTransactionBOD checkEODHappenOrNot(BigInteger icmcId, CashType cashType, Calendar sDate, Calendar eDate) {
		BinTransactionBOD binTransactionBOD = binDashboardJpaDao.checkEODHappenOrNot(icmcId, cashType, sDate, eDate);
		return binTransactionBOD;
	}

	@Override
	public boolean insertBranchReceiptAftercashTransfer(BranchReceipt branchReceipt) {
		binDashboardJpaDao.insertBranchReceiptAftercashTransfer(branchReceipt);
		return true;
	}

	@Override
	public long updateBranchReceiptAfterCashTransfer(BigInteger icmcId, String binNumber) {
		long count = binDashboardJpaDao.updateBranchReceiptAfterCashTransfer(icmcId, binNumber);
		return count;
	}

	@Override
	public BinTransactionBOD getDataFromBinTransactionBOD(BigInteger icmcId) {
		BinTransactionBOD bodData = binDashboardJpaDao.getDataFromBinTransactionBOD(icmcId);
		return bodData;
	}

	public List<Indent> getIndentCash(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		List<Indent> indentList = binDashboardJpaDao.getIndentCash(IcmcId, sDate, eDate);
		return indentList;
	}

	@Override
	public List<SASAllocation> getsasAllocation(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		List<SASAllocation> sasAllocationList = binDashboardJpaDao.getsasAllocation(IcmcId, sDate, eDate);
		return sasAllocationList;
	}

	@Override
	public List<BranchReceipt> getBranchReceiptValue(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		List<BranchReceipt> branchReceipt = binDashboardJpaDao.getBranchReceiptValue(IcmcId, sDate, eDate);
		return branchReceipt;
	}

	@Override
	public List<DiversionIRV> getDiversionIRV(BigInteger IcmcId, Calendar sDate, Calendar eDate) {
		List<DiversionIRV> diversionIRV = binDashboardJpaDao.getDiversionIRV(IcmcId, sDate, eDate);
		return diversionIRV;
	}

}
