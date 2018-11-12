package com.chest.currency.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.chest.currency.entity.model.AuditorIndent;
import com.chest.currency.entity.model.AuditorProcess;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BinCapacityDenomination;
import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.Branch;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.CITCRADriver;
import com.chest.currency.entity.model.CITCRAVehicle;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.CRA;
import com.chest.currency.entity.model.CRAAllocation;
import com.chest.currency.entity.model.CRAAllocationLog;
import com.chest.currency.entity.model.CRALog;
import com.chest.currency.entity.model.CustodianKeySet;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.DiversionORVAllocation;
import com.chest.currency.entity.model.FakeNote;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.HolidayMaster;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.Indent;
import com.chest.currency.entity.model.Jurisdiction;
import com.chest.currency.entity.model.MachineAllocation;
import com.chest.currency.entity.model.Mutilated;
import com.chest.currency.entity.model.MutilatedIndent;
import com.chest.currency.entity.model.ORVAllocation;
import com.chest.currency.entity.model.OtherBankAllocation;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.ProcessBundleForCRAPayment;
import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.SASAllocation;
import com.chest.currency.entity.model.Sas;
import com.chest.currency.entity.model.ServicingBranch;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;
import com.chest.currency.entity.model.User;
import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinColorType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;
import com.chest.currency.enums.State;
import com.chest.currency.enums.YesNo;
import com.chest.currency.enums.Zone;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.viewBean.IRVVoucherWrapper;
import com.chest.currency.viewBean.SASAllocationGrouped;
import com.mysema.query.Tuple;

public class UtilityJpa {

	private static final Logger LOG = LoggerFactory.getLogger(UtilityJpa.class);

	public static final List<Integer> VALID_PWD_CHARS = new ArrayList<>();
	static {
		IntStream.rangeClosed('0', '9').forEach(VALID_PWD_CHARS::add); // 0-9
		IntStream.rangeClosed('A', 'Z').forEach(VALID_PWD_CHARS::add); // A-Z
		IntStream.rangeClosed('a', 'z').forEach(VALID_PWD_CHARS::add); // a-z
		// IntStream.rangeClosed('!', '*').forEach(VALID_PWD_CHARS::add); // !-*
	}

	public static List<BinTransaction> getBinByCurrencyProcessType(List<BinTransaction> binTxs,
			List<BinMaster> binMasters, BigDecimal bundle, boolean isBundle, List<BinCapacityDenomination> capacityList,
			CurrencyType currencyType, CashSource cashSource) {

		BinCapacityDenomination capacity = new BinCapacityDenomination();

		BigDecimal bundleRequired = bundle;
		if (!isBundle) {
			bundleRequired = bundleRequired.divide(BigDecimal.TEN);
		}
		List<BinTransaction> alloccateTxs = new ArrayList<BinTransaction>();
		boolean isFound = false;
		Calendar now = Calendar.getInstance();

		if (binTxs != null && binTxs.size() > 0) {
			for (BinTransaction binTx : binTxs) {

				BigDecimal availableSpace = binTx.getMaxCapacity().subtract(binTx.getReceiveBundle());

				if (availableSpace.compareTo(bundleRequired) > 0) {
					binTx.setReceiveBundle(binTx.getReceiveBundle().add(bundleRequired));
					binTx.setCurrentBundle(bundleRequired);
					binTx.setCashSource(cashSource);
					binTx.setUpdateTime(now);
					binTx.setStatus(BinStatus.NOT_FULL);
					binTx.setVerified(YesNo.Yes);
					binTx.setBinCategoryType(BinCategoryType.BIN);
					alloccateTxs.add(binTx);
					isFound = true;
					break;
				} else if (availableSpace.compareTo(bundleRequired) == 0) {
					binTx.setReceiveBundle(binTx.getReceiveBundle().add(bundleRequired));
					binTx.setCurrentBundle(bundleRequired);
					binTx.setCashSource(cashSource);
					binTx.setUpdateTime(now);
					binTx.setStatus(BinStatus.FULL);
					binTx.setVerified(YesNo.Yes);
					binTx.setBinCategoryType(BinCategoryType.BIN);
					alloccateTxs.add(binTx);
					isFound = true;
					break;
				} else {
					if (!CurrencyType.UNPROCESS.equals(currencyType) || !CashSource.BRANCH.equals(cashSource)) {
						binTx.setReceiveBundle(binTx.getReceiveBundle().add(availableSpace));
						binTx.setCurrentBundle(availableSpace);
						binTx.setCashSource(cashSource);
						binTx.setUpdateTime(now);
						binTx.setStatus(BinStatus.FULL);
						binTx.setVerified(YesNo.Yes);
						binTx.setBinCategoryType(BinCategoryType.BIN);
						alloccateTxs.add(binTx);
						bundleRequired = bundleRequired.subtract(availableSpace);
					}
				}
			}
		}

		if (!isFound) {

			if (binMasters != null && binMasters.size() >= 0) {

				for (BinMaster binMaster : binMasters) {

					getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);

					// get capacity based on binMaster bin vault size
					// BigDecimal availableSpace =
					// capacity.getMaxBundleCapacity();
					BigDecimal availableSpace = capacity.getMaxBundleCapacity();

					try {
						if (availableSpace.compareTo(bundleRequired) > 0) {
							BinTransaction binTx = new BinTransaction();
							binTx.setBinNumber(binMaster.getBinNumber());
							binTx.setDenomination(capacity.getDenomination());
							binTx.setMaxCapacity(capacity.getMaxBundleCapacity());
							binTx.setBinType(currencyType);
							binTx.setCashSource(cashSource);
							binTx.setReceiveBundle(bundleRequired);
							binTx.setCurrentBundle(bundleRequired);
							binTx.setStatus(BinStatus.NOT_FULL);
							binTx.setInsertBy(binMaster.getInsertBy());
							binTx.setUpdateBy(binMaster.getUpdateBy());
							binTx.setIcmcId(binMaster.getIcmcId());
							binTx.setBinCategoryType(BinCategoryType.BIN);
							binTx.setInsertTime(now);
							binTx.setUpdateTime(now);
							binTx.setVerified(YesNo.Yes);
							binTx.setCashType(CashType.NOTES);
							alloccateTxs.add(binTx);
							isFound = true;
							break;
						} else if (availableSpace.compareTo(bundleRequired) == 0) {

							BinTransaction binTx = new BinTransaction();
							binTx.setBinNumber(binMaster.getBinNumber());
							binTx.setDenomination(capacity.getDenomination());
							binTx.setMaxCapacity(capacity.getMaxBundleCapacity());
							binTx.setBinType(currencyType);
							binTx.setCashSource(cashSource);
							binTx.setReceiveBundle(bundleRequired);
							binTx.setCurrentBundle(bundleRequired);
							binTx.setStatus(BinStatus.FULL);
							binTx.setInsertBy(binMaster.getInsertBy());
							binTx.setUpdateBy(binMaster.getUpdateBy());
							binTx.setIcmcId(binMaster.getIcmcId());
							binTx.setBinCategoryType(BinCategoryType.BIN);
							binTx.setInsertTime(now);
							binTx.setUpdateTime(now);
							binTx.setVerified(YesNo.Yes);
							binTx.setCashType(CashType.NOTES);
							alloccateTxs.add(binTx);
							isFound = true;
							break;
						} else {
							if (!CurrencyType.UNPROCESS.equals(currencyType) || !CashSource.BRANCH.equals(cashSource)) {
								BinTransaction binTx = new BinTransaction();
								binTx.setBinNumber(binMaster.getBinNumber());
								binTx.setDenomination(capacity.getDenomination());
								binTx.setMaxCapacity(capacity.getMaxBundleCapacity());
								binTx.setBinType(currencyType);
								binTx.setCashSource(cashSource);
								binTx.setReceiveBundle(availableSpace);
								binTx.setCurrentBundle(availableSpace);
								binTx.setStatus(BinStatus.FULL);
								binTx.setPendingBundleRequest(BigDecimal.ZERO);
								binTx.setBinCategoryType(BinCategoryType.BIN);
								binTx.setInsertBy(binMaster.getInsertBy());
								binTx.setUpdateBy(binMaster.getUpdateBy());
								binTx.setIcmcId(binMaster.getIcmcId());
								binTx.setInsertTime(now);
								binTx.setUpdateTime(now);
								binTx.setVerified(YesNo.Yes);
								binTx.setCashType(CashType.NOTES);
								alloccateTxs.add(binTx);
								bundleRequired = bundleRequired.subtract(availableSpace);
							}
						}

					} catch (Exception e) {
						throw new BaseGuiException(
								"Vault Size of required bin is not available in Bin Capacity Denomination");
					}
				}
			}
		}
		// new BinTransactionWrapper
		return alloccateTxs;
	}

	public static Indent getIndentIVR(DiversionIRV dirv, User user) {
		Indent indentData = new Indent();
		indentData.setIcmcId(user.getIcmcId());
		indentData.setBundle(dirv.getBundle());
		indentData.setCashReceiptId(dirv.getId());
		indentData.setDenomination(dirv.getDenomination());
		indentData.setInsertBy(user.getId());
		indentData.setUpdateBy(user.getId());
		indentData.setCashSource(CashSource.DIVERSION);
		indentData.setInsertTime(dirv.getInsertTime());
		indentData.setUpdateTime(dirv.getUpdateTime());
		indentData.setPendingBundleRequest(dirv.getBundle());
		indentData.setStatus(OtherStatus.ACCEPTED);
		return indentData;
	}

	public static DiversionIRV getIVRForProcessing(DiversionIRV dirv, User user) {
		DiversionIRV irvData = new DiversionIRV();
		irvData.setIcmcId(user.getIcmcId());
		irvData.setOrderDate(dirv.getOrderDate());
		irvData.setExpiryDate(dirv.getExpiryDate());
		irvData.setBankName(dirv.getBankName());
		irvData.setRbiOrderNo(dirv.getRbiOrderNo());
		irvData.setLocation(dirv.getLocation());
		irvData.setApprovedCC(dirv.getApprovedCC());
		irvData.setDenomination(dirv.getDenomination());
		irvData.setProcessedOrUnprocessed(dirv.getProcessedOrUnprocessed());
		irvData.setBundle(dirv.getBundle());
		irvData.setTotal(dirv.getTotal());
		irvData.setFilepath(dirv.getFilepath());
		irvData.setInsertBy(user.getId());
		irvData.setUpdateBy(user.getId());
		irvData.setInsertTime(dirv.getInsertTime());
		irvData.setUpdateTime(dirv.getUpdateTime());
		irvData.setCurrencyType(dirv.getCurrencyType());
		irvData.setStatus(OtherStatus.RECEIVED);

		/*
		 * irvData.setReceiptDate(todaysDate);
		 */ return irvData;
	}

	public static List<BinTransaction> getBoxByCurrencyProcessType(List<BinTransaction> binTxs,
			List<BoxMaster> boxMasters, BigDecimal bundle, boolean isBundle, CurrencyType currencyType,
			CashSource cashSource) {

		BigDecimal bundleRequired = bundle;
		if (!isBundle) {
			bundleRequired = bundleRequired.divide(BigDecimal.TEN);
		}
		List<BinTransaction> alloccateTxs = new ArrayList<BinTransaction>();
		boolean isFound = false;
		Calendar now = Calendar.getInstance();

		if (binTxs != null && binTxs.size() > 0) {
			for (BinTransaction binTx : binTxs) {

				BigDecimal availableSpace = binTx.getMaxCapacity().subtract(binTx.getReceiveBundle());

				if (availableSpace.compareTo(bundleRequired) > 0) {
					binTx.setReceiveBundle(binTx.getReceiveBundle().add(bundleRequired));
					binTx.setCurrentBundle(bundleRequired);
					binTx.setStatus(BinStatus.NOT_FULL);
					binTx.setUpdateTime(now);
					binTx.setCashSource(cashSource);
					binTx.setBinCategoryType(BinCategoryType.BOX);
					binTx.setVerified(YesNo.Yes);
					alloccateTxs.add(binTx);
					isFound = true;
					break;
				} else if (availableSpace.compareTo(bundleRequired) == 0) {
					binTx.setReceiveBundle(binTx.getReceiveBundle().add(bundleRequired));
					binTx.setCurrentBundle(bundleRequired);
					binTx.setStatus(BinStatus.FULL);
					binTx.setUpdateTime(now);
					binTx.setCashSource(cashSource);
					binTx.setBinCategoryType(BinCategoryType.BOX);
					binTx.setVerified(YesNo.Yes);
					alloccateTxs.add(binTx);
					isFound = true;
					break;
				} else {
					binTx.setReceiveBundle(binTx.getReceiveBundle().add(availableSpace));
					binTx.setCurrentBundle(availableSpace);
					binTx.setStatus(BinStatus.FULL);
					binTx.setCashSource(cashSource);
					binTx.setVerified(YesNo.Yes);
					binTx.setBinCategoryType(BinCategoryType.BOX);
					binTx.setUpdateTime(now);
					alloccateTxs.add(binTx);
					bundleRequired = bundleRequired.subtract(availableSpace);
				}

			}
		}

		if (!isFound) {
			if (boxMasters != null && boxMasters.size() >= 0) {

				// Calendar now = Calendar.getInstance();

				for (BoxMaster boxMaster : boxMasters) {

					// get capacity based on binMaster bin vault size
					BigDecimal availableSpace = boxMaster.getMaxCapacity();
					if (availableSpace.compareTo(bundleRequired) > 0) {

						BinTransaction binTx = new BinTransaction();
						binTx.setBinNumber(boxMaster.getBoxName());
						binTx.setDenomination(boxMaster.getDenomination());
						binTx.setMaxCapacity(boxMaster.getMaxCapacity());
						binTx.setBinType(currencyType);
						binTx.setCashSource(cashSource);
						binTx.setReceiveBundle(bundleRequired);
						binTx.setCurrentBundle(bundleRequired);
						binTx.setStatus(BinStatus.NOT_FULL);
						binTx.setInsertBy(boxMaster.getInsertBy());
						binTx.setUpdateBy(boxMaster.getUpdateBy());
						binTx.setIcmcId(boxMaster.getIcmcId());

						binTx.setBinCategoryType(BinCategoryType.BOX);
						binTx.setInsertTime(now);
						binTx.setUpdateTime(now);
						binTx.setVerified(YesNo.Yes);
						binTx.setCashType(CashType.NOTES);
						alloccateTxs.add(binTx);
						isFound = true;
						break;
					} else if (availableSpace.compareTo(bundleRequired) == 0) {

						BinTransaction binTx = new BinTransaction();
						binTx.setBinNumber(boxMaster.getBoxName());
						binTx.setDenomination(boxMaster.getDenomination());
						binTx.setMaxCapacity(boxMaster.getMaxCapacity());
						binTx.setBinType(currencyType);
						binTx.setCashSource(cashSource);
						binTx.setReceiveBundle(bundleRequired);
						binTx.setCurrentBundle(bundleRequired);
						binTx.setStatus(BinStatus.FULL);
						binTx.setInsertBy(boxMaster.getInsertBy());
						binTx.setUpdateBy(boxMaster.getUpdateBy());
						binTx.setIcmcId(boxMaster.getIcmcId());

						binTx.setBinCategoryType(BinCategoryType.BOX);
						binTx.setInsertTime(now);
						binTx.setUpdateTime(now);
						binTx.setVerified(YesNo.Yes);
						binTx.setCashType(CashType.NOTES);
						alloccateTxs.add(binTx);
						isFound = true;
						break;
					} else {
						BinTransaction binTx = new BinTransaction();
						binTx.setBinNumber(boxMaster.getBoxName());
						binTx.setDenomination(boxMaster.getDenomination());
						binTx.setMaxCapacity(boxMaster.getMaxCapacity());
						binTx.setBinType(currencyType);
						binTx.setCashSource(cashSource);
						binTx.setReceiveBundle(availableSpace);
						binTx.setCurrentBundle(availableSpace);
						binTx.setStatus(BinStatus.FULL);
						binTx.setPendingBundleRequest(BigDecimal.ZERO);
						binTx.setBinCategoryType(BinCategoryType.BOX);
						binTx.setInsertBy(boxMaster.getInsertBy());
						binTx.setUpdateBy(boxMaster.getUpdateBy());
						binTx.setIcmcId(boxMaster.getIcmcId());
						binTx.setInsertTime(now);
						binTx.setUpdateTime(now);
						binTx.setVerified(YesNo.Yes);
						binTx.setCashType(CashType.NOTES);
						alloccateTxs.add(binTx);
						bundleRequired = bundleRequired.subtract(availableSpace);
					}
				}
			}
		}
		// new BinTransactionWrapper
		return alloccateTxs;
	}

	private static void getMaxBundleCapacityAccordingToVaultSize(List<BinCapacityDenomination> capacityList,
			BinCapacityDenomination capacity, BinMaster binMaster) {

		for (BinCapacityDenomination binCapacity : capacityList) {
			if (binCapacity.getVaultSize() == binMaster.getVaultSize()) {
				capacity.setDenomination(binCapacity.getDenomination());
				capacity.setMaxBundleCapacity(binCapacity.getMaxBundleCapacity());
				break;
			} else {
				binMaster.setVaultSize(binMaster.getVaultSize());
			}
		}
	}

	public static List<BranchReceipt> getBranchReceiptBean(BranchReceipt branchReciept, List<BinTransaction> binTxs,
			User user, List<BinCapacityDenomination> capacityList, List<BinMaster> binMasterList,
			List<BoxMaster> boxMasterList) {

		BinCapacityDenomination capacity = new BinCapacityDenomination();

		for (BinMaster binMaster : binMasterList) {
			getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);
		}
		BigDecimal denom = BigDecimal.valueOf(branchReciept.getDenomination());
		BigDecimal multiplier1000 = BigDecimal.valueOf(1000);

		List<BranchReceipt> branchReceiptList = new ArrayList<BranchReceipt>();
		for (BinTransaction binTx : binTxs) {
			BranchReceipt branchReceiptBean = new BranchReceipt();
			branchReceiptBean.setIcmcId(binTx.getIcmcId());
			branchReceiptBean.setSolId(branchReciept.getSolId());
			branchReceiptBean.setBranch(branchReciept.getBranch());
			branchReceiptBean.setSrNumber(branchReciept.getSrNumber());
			branchReceiptBean.setDenomination(binTx.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			branchReceiptBean.setFilepath(branchReciept.getFilepath());
			branchReceiptBean.setBundle(rcvBundle);
			branchReceiptBean.setBin(binTx.getBinNumber());
			BigDecimal totalVal = rcvBundle.multiply(denom).multiply(multiplier1000);
			branchReceiptBean.setTotal(totalVal);
			branchReceiptBean.setInsertBy(user.getId());
			branchReceiptBean.setUpdateBy(user.getId());
			branchReceiptBean.setInsertTime(branchReciept.getInsertTime());
			branchReceiptBean.setUpdateTime(branchReciept.getUpdateTime());
			branchReceiptBean.setBinCategoryType(branchReciept.getBinCategoryType());
			branchReceiptBean.setStatus(OtherStatus.RECEIVED);
			branchReceiptBean.setCurrencyType(binTx.getBinType());
			branchReceiptBean.setCashSource(binTx.getCashSource());
			branchReceiptBean.setProcessedOrUnprocessed(branchReciept.getProcessedOrUnprocessed());
			branchReceiptList.add(branchReceiptBean);
		}
		return branchReceiptList;
	}

	private static HolidayMaster mapToHolidayMaster(String[] splitData) {
		HolidayMaster holiday = new HolidayMaster();
		holiday.setHolidayName(getValue(splitData, 0));
		holiday.setHolidayType(getValue(splitData, 1));
		holiday.setHolidayDate(getValue(splitData, 2));
		holiday.setAndamanandnicobarislands(getValue(splitData, 3));
		holiday.setAndhrapradesh(getValue(splitData, 4));
		holiday.setArunachalPradesh(getValue(splitData, 5));
		holiday.setAssam(getValue(splitData, 6));
		holiday.setBihar(getValue(splitData, 7));
		holiday.setChandigarh(getValue(splitData, 8));
		holiday.setChhattisgarh(getValue(splitData, 9));
		holiday.setDamanAndDiu(getValue(splitData, 10));
		holiday.setDelhi(getValue(splitData, 11));
		holiday.setGoa(getValue(splitData, 12));
		holiday.setGujarat(getValue(splitData, 13));
		holiday.setHimachalpradesh(getValue(splitData, 14));
		holiday.setHaryana(getValue(splitData, 15));
		holiday.setJharkhand(getValue(splitData, 16));
		holiday.setJammuAndKashmir(getValue(splitData, 17));
		holiday.setKarnataka(getValue(splitData, 18));
		holiday.setKerala(getValue(splitData, 19));
		holiday.setMaharashtra(getValue(splitData, 20));
		holiday.setMeghalaya(getValue(splitData, 21));
		holiday.setManipur(getValue(splitData, 22));
		holiday.setMadhyaPradesh(getValue(splitData, 23));
		holiday.setMizoram(getValue(splitData, 24));
		holiday.setNagaland(getValue(splitData, 25));
		holiday.setOrissa(getValue(splitData, 26));
		holiday.setPunjab(getValue(splitData, 27));
		holiday.setPondichhery(getValue(splitData, 28));
		holiday.setRajasthan(getValue(splitData, 29));
		holiday.setSikkim(getValue(splitData, 30));
		holiday.setTamilNadu(getValue(splitData, 31));
		holiday.setTripura(getValue(splitData, 32));
		holiday.setUttarPradesh(getValue(splitData, 33));
		holiday.setUttaranchal(getValue(splitData, 34));
		holiday.setWestBengal(getValue(splitData, 35));
		holiday.setDadraandnagarhaveli(getValue(splitData, 36));
		holiday.setLakshadweep(getValue(splitData, 37));
		holiday.setTelangana(getValue(splitData, 38));
		return holiday;
	}

	private static String getValue(String[] splitData, int i) {

		if (splitData != null && splitData.length > i) {
			return splitData[i];
		}
		return "";
	}

	private static Zone getZoneValue(String[] splitData, int i) {

		if (splitData != null && splitData.length > i) {
			return Zone.valueOf(splitData[i]);
		}
		return Zone.valueOf("");
	}

	public static List<HolidayMaster> CSVtoArrayListForHoliday(List<String> holidayRecords) {
		List<HolidayMaster> holidayMasterList = new LinkedList<>();
		for (String str : holidayRecords) {
			String[] splitData = str.split(",");
			HolidayMaster holiday = mapToHolidayMaster(splitData);
			holidayMasterList.add(holiday);
		}
		return holidayMasterList;
	}

	private static Branch mapToBranch(String[] splitData) {
		Branch branch = new Branch();
		branch.setSolId(splitData[0]);
		branch.setBranch(splitData[1]);
		branch.setServicingICMC(splitData[2]);
		branch.setJurisdictionICMC(splitData[3]);
		branch.setRbiName(splitData[4]);
		branch.setZone(getZoneValue(splitData, 5));
		branch.setRegion(splitData[6]);
		branch.setAddress(splitData[7]);
		branch.setCity(splitData[8]);
		branch.setPincode(Integer.parseInt(getValue(splitData, 9)));
		return branch;
	}

	public static List<Branch> CSVtoArrayListForBranch(List<String> branchRecords) {
		List<Branch> branchList = new LinkedList<>();
		for (String str : branchRecords) {
			String[] splitData = str.split(",");
			Branch branch = mapToBranch(splitData);
			branchList.add(branch);
		}
		return branchList;
	}

	public static List<FreshFromRBI> getFreshFromRBI(FreshFromRBI fresh, List<BinTransaction> binTxs, User user,
			List<BinCapacityDenomination> capacityList, List<BinMaster> binMasterList) {
		BinCapacityDenomination capacity = new BinCapacityDenomination();
		List<FreshFromRBI> freshList = new ArrayList<>();

		BigDecimal denom = BigDecimal.valueOf(fresh.getDenomination());
		BigDecimal multiplier1000 = BigDecimal.valueOf(1000);

		for (BinMaster binMaster : binMasterList) {
			getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);
		}

		for (BinTransaction binTx : binTxs) {
			FreshFromRBI freshData = new FreshFromRBI();
			freshData.setIcmcId(binTx.getIcmcId());
			freshData.setOrderDate(fresh.getOrderDate());
			freshData.setRbiOrderNo(fresh.getRbiOrderNo());
			freshData.setVehicleNumber(fresh.getVehicleNumber());
			freshData.setPotdarName(fresh.getPotdarName());
			freshData.setEscortOfficerName(fresh.getEscortOfficerName());
			// freshData.setNotesOrCoins(fresh.getNotesOrCoins());
			// freshData.setBinOrBox(fresh.getBinOrBox());
			freshData.setBinCategoryType(fresh.getBinCategoryType());
			freshData.setCashType(fresh.getCashType());

			freshData.setCashSource(fresh.getCashSource());
			freshData.setDenomination(capacity.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			freshData.setBundle(rcvBundle);
			freshData.setPendingBundleRequest(rcvBundle);

			BigDecimal totalVal = rcvBundle.multiply(denom).multiply(multiplier1000);

			freshData.setTotal(totalVal);
			freshData.setFilepath(fresh.getFilepath());
			freshData.setBin(binTx.getBinNumber());
			freshData.setInsertBy(user.getId());
			freshData.setUpdateBy(user.getId());
			freshData.setInsertTime(fresh.getInsertTime());
			freshData.setUpdateTime(fresh.getUpdateTime());
			freshData.setPotdarStatus("PENDING");
			freshList.add(freshData);
		}
		return freshList;
	}

	public static List<FreshFromRBI> getFreshFromRBIForBox(FreshFromRBI fresh, User user) {
		List<FreshFromRBI> freshList = new ArrayList<>();
		FreshFromRBI freshData = new FreshFromRBI();
		freshData.setIcmcId(user.getIcmcId());
		freshData.setOrderDate(fresh.getOrderDate());
		freshData.setRbiOrderNo(fresh.getRbiOrderNo());
		freshData.setVehicleNumber(fresh.getVehicleNumber());
		freshData.setPotdarName(fresh.getPotdarName());
		freshData.setEscortOfficerName(fresh.getEscortOfficerName());
		freshData.setBinCategoryType(fresh.getBinCategoryType());
		freshData.setCashType(fresh.getCashType());
		freshData.setBin(fresh.getBin());
		freshData.setCashSource(fresh.getCashSource());
		freshData.setDenomination(fresh.getDenomination());
		freshData.setBundle(fresh.getBundle());
		freshData.setTotal(fresh.getTotal());
		freshData.setFilepath(fresh.getFilepath());
		freshData.setInsertBy(user.getId());
		freshData.setUpdateBy(user.getId());
		freshData.setInsertTime(fresh.getInsertTime());
		freshData.setUpdateTime(fresh.getUpdateTime());
		freshData.setPotdarStatus("PENDING");
		freshList.add(freshData);
		return freshList;
	}

	public static List<FreshFromRBI> getFreshFromRBIForCoins(FreshFromRBI fresh, User user) {
		List<FreshFromRBI> freshList = new ArrayList<>();
		FreshFromRBI freshData = new FreshFromRBI();
		freshData.setIcmcId(user.getIcmcId());
		freshData.setOrderDate(fresh.getOrderDate());
		freshData.setRbiOrderNo(fresh.getRbiOrderNo());
		freshData.setVehicleNumber(fresh.getVehicleNumber());
		freshData.setPotdarName(fresh.getPotdarName());
		freshData.setEscortOfficerName(fresh.getEscortOfficerName());
		freshData.setBin("COINS" + user.getIcmcId() + Instant.now().toEpochMilli());
		// freshData.setNotesOrCoins(fresh.getNotesOrCoins());
		freshData.setCashType(fresh.getCashType());
		freshData.setDenomination(fresh.getDenomination());
		freshData.setNoOfBags(fresh.getNoOfBags());
		freshData.setTotal(fresh.getTotal());
		freshData.setFilepath(fresh.getFilepath());
		freshData.setBagSequenceFromDB(fresh.getBagSequenceFromDB());
		freshData.setInsertBy(user.getId());
		freshData.setUpdateBy(user.getId());
		freshData.setInsertTime(fresh.getInsertTime());
		freshData.setUpdateTime(fresh.getUpdateTime());
		freshData.setPotdarStatus("PENDING");
		freshList.add(freshData);
		return freshList;
	}

	public static List<DiversionIRV> getDiversionIRV(DiversionIRV dirv, List<BinTransaction> binTxs, User user,
			List<BinCapacityDenomination> capacityList, List<BinMaster> binMasterList, List<BoxMaster> boxMasterList) {
		BinCapacityDenomination capacity = new BinCapacityDenomination();
		List<DiversionIRV> diversionIRVList = new ArrayList<>();

		BigDecimal denom = BigDecimal.valueOf(dirv.getDenomination());
		BigDecimal multiplier1000 = BigDecimal.valueOf(1000);

		for (BinMaster binMaster : binMasterList) {
			getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);
		}

		for (BinTransaction binTx : binTxs) {
			DiversionIRV diversion = new DiversionIRV();
			diversion.setIcmcId(binTx.getIcmcId());
			diversion.setOrderDate(dirv.getOrderDate());
			diversion.setRbiOrderNo(dirv.getRbiOrderNo());
			diversion.setExpiryDate(dirv.getExpiryDate());
			diversion.setBankName(dirv.getBankName());
			diversion.setApprovedCC(dirv.getApprovedCC());
			diversion.setLocation(dirv.getLocation());
			diversion.setDenomination(dirv.getDenomination());
			diversion.setCategory(dirv.getCategory());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			diversion.setBundle(rcvBundle);
			BigDecimal totalVal = rcvBundle.multiply(denom).multiply(multiplier1000);
			diversion.setTotal(totalVal);
			diversion.setFilepath(dirv.getFilepath());
			diversion.setBinNumber(binTx.getBinNumber());
			diversion.setInsertBy(user.getId());
			diversion.setUpdateBy(user.getId());
			diversion.setInsertTime(dirv.getInsertTime());
			diversion.setUpdateTime(dirv.getUpdateTime());
			if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("UNPROCESS")) {
				diversion.setCurrencyType(CurrencyType.UNPROCESS);
			}
			if (dirv.getProcessedOrUnprocessed().equalsIgnoreCase("PROCESSED")) {
				diversion.setCurrencyType(dirv.getCurrencyType());
			}
			diversion.setBinCategoryType(dirv.getBinCategoryType());
			diversion.setStatus(OtherStatus.RECEIVED);
			diversion.setProcessedOrUnprocessed(dirv.getProcessedOrUnprocessed());
			diversionIRVList.add(diversion);
		}
		return diversionIRVList;
	}

	public static List<DSB> getDSB(DSB dsb, List<BinTransaction> binTxs, User user,
			List<BinCapacityDenomination> capacityList, List<BinMaster> binMasterList, List<BoxMaster> boxMasterList) {
		BinCapacityDenomination capacity = new BinCapacityDenomination();
		List<DSB> dsbList = new ArrayList<>();
		Date todaysDate = getDateWithTimeZero();
		BigDecimal denom = BigDecimal.valueOf(dsb.getDenomination());
		BigDecimal multiplier1000 = BigDecimal.valueOf(1000);

		for (BinMaster binMaster : binMasterList) {
			getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);
		}

		for (BinTransaction binTx : binTxs) {
			DSB dsbData = new DSB();
			dsbData.setIcmcId(binTx.getIcmcId());
			dsbData.setName(dsb.getName());
			dsbData.setAccountNumber(dsb.getAccountNumber());
			dsbData.setProcessingOrVault(dsb.getProcessingOrVault());
			dsbData.setDenomination(dsb.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			dsbData.setBundle(rcvBundle);
			BigDecimal totalVal = rcvBundle.multiply(denom).multiply(multiplier1000);
			dsbData.setTotal(totalVal);
			dsbData.setFilepath(dsb.getFilepath());
			dsbData.setBin(binTx.getBinNumber());
			dsbData.setInsertBy(user.getId());
			dsbData.setInsertTime(dsb.getInsertTime());
			dsbData.setUpdateTime(dsb.getUpdateTime());
			dsbData.setBinCategoryType(dsb.getBinCategoryType());
			dsbData.setCurrencyType(CurrencyType.UNPROCESS);
			dsbData.setUpdateBy(user.getId());
			dsbData.setStatus(OtherStatus.RECEIVED);
			dsbData.setCashSource(dsb.getCashSource());
			dsbData.setReceiptSequence(dsb.getReceiptSequence());
			dsbData.setReceiptDate(todaysDate);
			dsbList.add(dsbData);
		}
		return dsbList;
	}

	@SuppressWarnings("deprecation")
	public static Date getDateWithTimeZero() {
		Date todaysDate = new Date();
		todaysDate.setHours(0);
		todaysDate.setMinutes(0);
		todaysDate.setSeconds(0);
		return todaysDate;
	}

	public static List<Process> getProcessBean(Process process, List<BinTransaction> binTxs, User user) {
		List<Process> processList = new ArrayList<>();
		for (BinTransaction binTx : binTxs) {
			Process processData = new Process();
			processData.setIcmcId(binTx.getIcmcId());
			// processData.setMachineNo(process.getMachineNo());
			processData.setCurrencyType(process.getCurrencyType());
			processData.setDenomination(process.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			processData.setBundle(rcvBundle);
			processData.setTotal(process.getTotal());
			processData.setFilepath(process.getFilepath());
			processData.setBinNumber(binTx.getBinNumber());
			processData.setInsertBy(user.getId());
			processData.setUpdateBy(user.getId());
			processData.setInsertTime(process.getInsertTime());
			processData.setUpdateTime(process.getUpdateTime());
			processData.setStatus(1);
			processData.setProcessAction(process.getProcessAction());
			processData.setBinCategoryType(process.getBinCategoryType());
			processData.setMachineId(process.getMachineId());
			processList.add(processData);
		}
		return processList;

	}

	public static List<AuditorProcess> getProcessBeanForAuditor(AuditorProcess process, List<BinTransaction> binTxs,
			User user) {
		List<AuditorProcess> processList = new ArrayList<>();
		for (BinTransaction binTx : binTxs) {
			AuditorProcess processData = new AuditorProcess();
			processData.setIcmcId(binTx.getIcmcId());
			// processData.setMachineNo(process.getMachineNo());
			processData.setCurrencyType(process.getCurrencyType());
			processData.setDenomination(process.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			processData.setBundle(rcvBundle);
			processData.setTotal(process.getTotal());
			processData.setFilepath(process.getFilepath());
			processData.setBinNumber(binTx.getBinNumber());
			processData.setInsertBy(user.getId());
			processData.setUpdateBy(user.getId());
			processData.setInsertTime(process.getInsertTime());
			processData.setUpdateTime(process.getUpdateTime());
			processData.setBinCategoryType(process.getBinCategoryType());
			processList.add(processData);
		}
		return processList;

	}

	public static ProcessBundleForCRAPayment processCRAPaymentBundle(ProcessBundleForCRAPayment process, User user) {
		ProcessBundleForCRAPayment processData = new ProcessBundleForCRAPayment();
		processData.setIcmcId(user.getIcmcId());
		processData.setCurrencyType(process.getCurrencyType());
		processData.setDenomination(process.getDenomination());
		processData.setBundle(process.getBundle());
		processData.setFilepath(process.getFilepath());
		processData.setInsertBy(user.getId());
		processData.setUpdateBy(user.getId());
		processData.setPendingRequestedBundle(process.getBundle());
		processData.setStatus(OtherStatus.PROCESSED);
		processData.setInsertTime(process.getInsertTime());
		processData.setUpdateTime(process.getUpdateTime());
		processData.setCraId(process.getCraId());
		return processData;
	}

	public static DSB getDSBForProcessing(DSB dsb, User user) {
		DSB dsbData = new DSB();
		// Date todaysDate = getDateWithTimeZero();
		Date todaysDate = new Date();
		dsbData.setIcmcId(user.getIcmcId());
		dsbData.setName(dsb.getName());
		dsbData.setAccountNumber(dsb.getAccountNumber());
		dsbData.setProcessingOrVault(dsb.getProcessingOrVault());
		dsbData.setDenomination(dsb.getDenomination());
		dsbData.setBundle(dsb.getBundle());
		dsbData.setTotal(dsb.getTotal());
		dsbData.setFilepath(dsb.getFilepath());
		dsbData.setInsertBy(user.getId());
		dsbData.setUpdateBy(user.getId());
		dsbData.setInsertTime(dsb.getInsertTime());
		dsbData.setUpdateTime(dsb.getUpdateTime());
		dsbData.setCurrencyType(dsb.getCurrencyType());
		dsbData.setStatus(OtherStatus.RECEIVED);
		dsbData.setCashSource(dsb.getCashSource());
		dsbData.setReceiptSequence(dsb.getReceiptSequence());
		dsbData.setReceiptDate(todaysDate);
		return dsbData;
	}

	public static BankReceipt getBankReceiptForProcessing(BankReceipt bankReceipt, User user) {
		BankReceipt bankReceiptData = new BankReceipt();
		// Date todaysDate = getDateWithTimeZero();
		bankReceiptData.setIcmcId(user.getIcmcId());
		bankReceiptData.setDenomination(bankReceipt.getDenomination());
		bankReceiptData.setBundle(bankReceipt.getBundle());
		bankReceiptData.setTotal(bankReceipt.getTotal());
		bankReceiptData.setFilepath(bankReceipt.getFilepath());
		bankReceiptData.setInsertBy(user.getId());
		bankReceiptData.setUpdateBy(user.getId());
		bankReceiptData.setInsertTime(bankReceipt.getInsertTime());
		bankReceiptData.setUpdateTime(bankReceipt.getUpdateTime());
		bankReceiptData.setCurrencyType(bankReceipt.getCurrencyType());
		bankReceiptData.setBinCategoryType(bankReceipt.getBinCategoryType());
		bankReceiptData.setBankName(bankReceipt.getBankName());
		bankReceiptData.setRtgsUTRNo(bankReceipt.getRtgsUTRNo());
		bankReceiptData.setSolId(bankReceipt.getSolId());
		bankReceiptData.setBranch(bankReceipt.getBranch());
		return bankReceiptData;
	}

	public static List<BankReceipt> getICMCReceipt(BankReceipt icmcReceipt, List<BinTransaction> binTxs, User user,
			List<BinCapacityDenomination> capacityList, List<BinMaster> binMasterList, List<BoxMaster> boxMasterList) {
		BinCapacityDenomination capacity = new BinCapacityDenomination();
		List<BankReceipt> icmcReceiptList = new ArrayList<>();
		BigDecimal denom = BigDecimal.valueOf(icmcReceipt.getDenomination());
		BigDecimal multiplier1000 = BigDecimal.valueOf(1000);
		for (BinMaster binMaster : binMasterList) {
			getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);
		}

		for (BinTransaction binTx : binTxs) {
			BankReceipt icmcReceiptData = new BankReceipt();
			icmcReceiptData.setIcmcId(binTx.getIcmcId());
			icmcReceiptData.setBankName(icmcReceipt.getBankName());
			icmcReceiptData.setSolId(icmcReceipt.getSolId());
			icmcReceiptData.setBranch(icmcReceipt.getBranch());
			icmcReceiptData.setDenomination(icmcReceipt.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			icmcReceiptData.setBundle(rcvBundle);
			BigDecimal totalVal = rcvBundle.multiply(denom).multiply(multiplier1000);
			icmcReceiptData.setTotal(totalVal);
			icmcReceiptData.setFilepath(icmcReceipt.getFilepath());
			icmcReceiptData.setBinNumber(binTx.getBinNumber());
			icmcReceiptData.setRtgsUTRNo(icmcReceipt.getRtgsUTRNo());
			icmcReceipt.setCurrencyType(CurrencyType.UNPROCESS);
			icmcReceipt.setBinCategoryType(icmcReceipt.getBinCategoryType());
			icmcReceiptData.setInsertBy(user.getId());
			icmcReceiptData.setUpdateBy(user.getId());
			icmcReceiptData.setInsertTime(icmcReceipt.getInsertTime());
			icmcReceiptData.setUpdateTime(icmcReceipt.getUpdateTime());
			icmcReceiptList.add(icmcReceiptData);
		}
		return icmcReceiptList;
	}

	public static Indent getIndent(BankReceipt bankReceipt, User user) {
		Indent indentData = new Indent();
		indentData.setCashReceiptId(bankReceipt.getId());
		indentData.setIcmcId(user.getIcmcId());
		indentData.setBundle(bankReceipt.getBundle());
		indentData.setDenomination(bankReceipt.getDenomination());
		indentData.setInsertBy(user.getId());
		indentData.setUpdateBy(user.getId());
		indentData.setCashSource(CashSource.OTHERBANK);
		indentData.setInsertTime(bankReceipt.getInsertTime());
		indentData.setUpdateTime(bankReceipt.getUpdateTime());
		indentData.setPendingBundleRequest(bankReceipt.getBundle());
		indentData.setStatus(OtherStatus.ACCEPTED);
		return indentData;
	}

	public static Indent getIndent(DSB dsb, User user) {
		Indent indentData = new Indent();
		indentData.setIcmcId(user.getIcmcId());
		indentData.setCashReceiptId(dsb.getId());
		indentData.setBundle(dsb.getBundle());
		indentData.setDenomination(dsb.getDenomination());
		indentData.setInsertBy(user.getId());
		indentData.setUpdateBy(user.getId());
		indentData.setCashSource(CashSource.DSB);
		indentData.setInsertTime(dsb.getInsertTime());
		indentData.setUpdateTime(dsb.getUpdateTime());
		indentData.setPendingBundleRequest(dsb.getBundle());
		indentData.setStatus(OtherStatus.ACCEPTED);
		return indentData;
	}

	public static List<Indent> getBinForIndentRequest(List<BinTransaction> txnList, int denomination, BigDecimal bundle,
			User user, Indent indentData) {

		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<Indent> eligibleIndentList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			Indent indent = new Indent();

			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);

			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (availableBundle.compareTo(bundleRequired) >= 0) {
				indent.setBin(binTx.getBinNumber());
				indent.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				indent.setDenomination(binTx.getDenomination());
				indent.setInsertBy(user.getId());
				indent.setBinCategoryType(BinCategoryType.BIN);
				indent.setPendingBundleRequest(bundleRequired);
				indent.setUpdateBy(user.getId());
				indent.setIcmcId(binTx.getIcmcId());
				indent.setCashSource(binTx.getCashSource());
				indent.setInsertTime(now);
				indent.setUpdateTime(now);
				indent.setStatus(OtherStatus.REQUESTED);
				eligibleIndentList.add(indent);
				break;
			} else if (availableBundle.compareTo(BigDecimal.ZERO) > 0) {
				indent.setBin(binTx.getBinNumber());
				indent.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				indent.setDenomination(binTx.getDenomination());
				indent.setInsertBy(user.getId());
				indent.setBinCategoryType(BinCategoryType.BIN);
				indent.setUpdateBy(user.getId());
				indent.setIcmcId(binTx.getIcmcId());
				indent.setCashSource(binTx.getCashSource());
				indent.setInsertTime(now);
				indent.setPendingBundleRequest(availableBundle);
				indent.setUpdateTime(now);
				indent.setStatus(OtherStatus.REQUESTED);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleIndentList.add(indent);
			}
		}
		return eligibleIndentList;
	}

	public static List<Indent> getBinForRBIIndentRequest(List<BinTransaction> txnList, int denomination,
			BigDecimal bundle, User user, Indent indentData) {

		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<Indent> eligibleIndentList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			Indent indent = new Indent();

			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);

			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (availableBundle.compareTo(bundleRequired) >= 0) {
				indent.setBin(binTx.getBinNumber());
				indent.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				indent.setDenomination(binTx.getDenomination());
				indent.setInsertBy(user.getId());
				indent.setBinCategoryType(BinCategoryType.BIN);
				indent.setPendingBundleRequest(bundleRequired);
				indent.setRbiOrderNo(binTx.getRbiOrderNo());
				indent.setUpdateBy(user.getId());
				indent.setIcmcId(binTx.getIcmcId());
				indent.setCashSource(binTx.getCashSource());
				indent.setInsertTime(now);
				indent.setUpdateTime(now);
				indent.setStatus(OtherStatus.REQUESTED);
				eligibleIndentList.add(indent);
				break;
			} else if (availableBundle.compareTo(BigDecimal.ZERO) > 0) {
				indent.setBin(binTx.getBinNumber());
				indent.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				indent.setDenomination(binTx.getDenomination());
				indent.setInsertBy(user.getId());
				indent.setBinCategoryType(BinCategoryType.BIN);
				indent.setUpdateBy(user.getId());
				indent.setIcmcId(binTx.getIcmcId());
				indent.setRbiOrderNo(binTx.getRbiOrderNo());
				indent.setCashSource(binTx.getCashSource());
				indent.setInsertTime(now);
				indent.setPendingBundleRequest(availableBundle);
				indent.setUpdateTime(now);
				indent.setStatus(OtherStatus.REQUESTED);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleIndentList.add(indent);
			}
		}
		return eligibleIndentList;
	}

	public static List<Indent> getBoxForIndentRequest(List<BinTransaction> txnList, int denomination, BigDecimal bundle,
			User user, Indent indentData)

	{

		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<Indent> eligibleIndentList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			Indent indent = new Indent();

			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);

			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (availableBundle.compareTo(bundleRequired) >= 0) {
				// indent.setBin("BOX");
				indent.setBin(binTx.getBinNumber());
				indent.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				indent.setDenomination(binTx.getDenomination());
				indent.setBinCategoryType(BinCategoryType.BOX);
				indent.setInsertBy(user.getId());
				indent.setUpdateBy(user.getId());
				indent.setIcmcId(binTx.getIcmcId());
				indent.setCashSource(binTx.getCashSource());
				indent.setInsertTime(now);
				indent.setUpdateTime(now);
				indent.setPendingBundleRequest(bundleRequired);
				indent.setRbiOrderNo(binTx.getRbiOrderNo());
				indent.setStatus(OtherStatus.REQUESTED);
				eligibleIndentList.add(indent);
				break;
			} else {
				// indent.setBin("BOX");
				indent.setBin(binTx.getBinNumber());
				indent.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				indent.setDenomination(binTx.getDenomination());
				indent.setBinCategoryType(BinCategoryType.BOX);
				indent.setInsertBy(user.getId());
				indent.setUpdateBy(user.getId());
				indent.setIcmcId(binTx.getIcmcId());
				indent.setCashSource(binTx.getCashSource());
				indent.setPendingBundleRequest(availableBundle);
				indent.setRbiOrderNo(binTx.getRbiOrderNo());
				indent.setInsertTime(now);
				indent.setUpdateTime(now);
				indent.setStatus(OtherStatus.REQUESTED);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleIndentList.add(indent);
			}
		}
		return eligibleIndentList;
	}

	public static List<Indent> getBinForBranchReceiptIndentRequest(List<BinTransaction> txnList, int denomination,
			BigDecimal bundle, User user, Indent indentData, List<BranchReceipt> branchReceiptList) {
		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<Indent> eligibleIndentList = new ArrayList<>();

		for (BranchReceipt br : branchReceiptList) {

			if (bundleRequired.compareTo(BigDecimal.ZERO) == 0) {
				break;
			}

			Indent indent = new Indent();

			BigDecimal availableBundleInWrap = getSubstarctedBundleForBranchReceipt(br, txnList, bundleRequired);

			if (availableBundleInWrap.doubleValue() <= 0) {
				continue;
			}

			if (availableBundleInWrap.compareTo(bundleRequired) <= 0) {
				indent.setBin(br.getBin());
				indent.setBundle(br.getBundle());
				indent.setStatus(OtherStatus.REQUESTED);
				indent.setDenomination(br.getDenomination());
				indent.setBinCategoryType(br.getBinCategoryType()); // Changed
																	// on
																	// 08thMay17
																	// from
																	// indent.setBinCategoryType(BinCategoryType.BIN);
				indent.setPendingBundleRequest(br.getBundle());
				indent.setInsertBy(user.getId());
				indent.setUpdateBy(user.getId());
				indent.setIcmcId(user.getIcmcId());
				indent.setCashSource(br.getCashSource());
				indent.setInsertTime(now);
				indent.setUpdateTime(now);
				bundleRequired = bundleRequired.subtract(br.getBundle());
				eligibleIndentList.add(indent);
			}

		}

		// throw exception if bundle > 0

		return eligibleIndentList;
	}

	public static List<SASAllocation> getBinForBranchReceiptSasRequest(List<BinTransaction> txnList, int denomination,
			BigDecimal bundle, User user, Sas sasAllocationParent, List<BranchReceipt> branchReceiptList) {
		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<SASAllocation> eligibleIndentList = new ArrayList<>();

		for (BranchReceipt br : branchReceiptList) {

			if (bundleRequired.compareTo(BigDecimal.ZERO) == 0) {
				break;
			}

			SASAllocation sasAlloc = new SASAllocation();

			BigDecimal availableBundleInWrap = getSubstarctedBundleForBranchReceipt(br, txnList, bundleRequired);

			if (availableBundleInWrap.doubleValue() <= 0) {
				continue;
			}

			if (availableBundleInWrap.compareTo(bundleRequired) <= 0) {
				sasAlloc.setBinNumber(br.getBin());
				sasAlloc.setBundle(br.getBundle());
				sasAlloc.setStatus(OtherStatus.REQUESTED);
				sasAlloc.setDenomination(br.getDenomination());
				sasAlloc.setInsertBy(user.getId());
				sasAlloc.setUpdateBy(user.getId());
				sasAlloc.setIcmcId(br.getIcmcId());
				sasAlloc.setInsertTime(now);
				sasAlloc.setUpdateTime(now);
				sasAlloc.setParentId(sasAllocationParent.getId());
				sasAlloc.setCashType(CashType.NOTES);
				sasAlloc.setBinType(br.getCurrencyType());
				bundleRequired = bundleRequired.subtract(br.getBundle());
				eligibleIndentList.add(sasAlloc);
			}

		}

		// throw exception if bundle > 0

		return eligibleIndentList;
	}

	private static Jurisdiction mapToJurisdiction(String[] splitData) {
		Jurisdiction jurisdiction = new Jurisdiction();
		jurisdiction.setSolId(Integer.parseInt(splitData[0]));
		jurisdiction.setBranchName(splitData[1]);
		jurisdiction.setIcmcName(splitData[2]);
		jurisdiction.setJurisdiction(splitData[3]);
		jurisdiction.setCity(splitData[4]);
		jurisdiction.setPincode(Integer.parseInt(splitData[5]));
		return jurisdiction;
	}

	public static List<Jurisdiction> CSVtoArrayListForJurisdiction(List<String> jurisdictionRecords) {
		List<Jurisdiction> jurisdictionList = new LinkedList<>();
		for (String str : jurisdictionRecords) {
			String[] splitData = str.split(",");
			Jurisdiction jurisdiction = mapToJurisdiction(splitData);
			jurisdictionList.add(jurisdiction);
		}
		return jurisdictionList;
	}

	private static ServicingBranch mapToservicingBranch(String[] splitData) {
		ServicingBranch servicing = new ServicingBranch();
		servicing.setIcmcName(splitData[0]);
		servicing.setSolId(Integer.parseInt(splitData[1]));
		servicing.setBranchName(splitData[2]);
		servicing.setRbiJI(splitData[3]);
		servicing.setRbiSI(splitData[4]);
		servicing.setCategory(splitData[5]);
		servicing.setRbiICMC(splitData[6]);
		return servicing;
	}

	public static List<ServicingBranch> CSVtoArrayListForServicing(List<String> servicingRecords) {
		List<ServicingBranch> servicingList = new LinkedList<>();
		for (String str : servicingRecords) {
			String[] splitData = str.split(",");
			ServicingBranch servicingBranch = mapToservicingBranch(splitData);
			servicingList.add(servicingBranch);
		}
		return servicingList;
	}

	public static List<CITCRAVendor> CSVtoArrayListForCITCRAVendor(List<String> vendorRecords, List<ICMC> icmcList) {
		List<CITCRAVendor> vendorList = new LinkedList<>();
		for (String str : vendorRecords) {
			String[] splitData = str.split(",");
			CITCRAVendor citCraVendor = mapToCICRAVendorBean(splitData, icmcList);
			vendorList.add(citCraVendor);

		}
		return vendorList;
	}

	private static CITCRAVendor mapToCICRAVendorBean(String[] splitData, List<ICMC> icmcList) {
		CITCRAVendor vendor = new CITCRAVendor();

		Map<Long, ICMC> map = new HashMap<Long, ICMC>();
		for (ICMC icmc : icmcList) {
			map.put(icmc.getId(), icmc);
		}
		Set<Long> keyValue = map.keySet();
		Iterator<Long> itr = keyValue.iterator();

		vendor.setName(splitData[0]);
		vendor.setTypeOne(splitData[1]);
		vendor.setTypeTwo(splitData[2]);
		vendor.setTypeThree(splitData[3]);
		vendor.setFPRName(splitData[4]);
		vendor.setFPRNumber(splitData[5]);
		vendor.setZone(getZoneValue(splitData, 6));
		vendor.setRegion(splitData[7]);
		if (splitData[8].contains("|")) {

			while (itr.hasNext()) {
				String[] splitDataPipe = splitData[8].split(Pattern.quote("|"));
				for (String str1 : splitDataPipe) {
					String[] splitDataOfICMCForVendor = str1.split(",");

					for (String str : splitDataOfICMCForVendor) {

						ICMC icc = map.get(itr.next());
						if (icc.getName().equals(str)) {
							List<ICMC> icmcList1 = new ArrayList<ICMC>();
							icmcList1.add(icc);
							vendor.setIcmcList(icmcList1);
							populateIcmcIds(vendor);
						}
					}
				}

			}
		} else {
			vendor.setIcmcList(icmcList);
			populateIcmcIds(vendor);
		}
		// set icmc name in a array
		return vendor;
	}

	private static void populateIcmcIds(CITCRAVendor vendor) {
		List<Long> icmcIds = new ArrayList<Long>();
		if (vendor.getIcmcList() != null) {
			for (ICMC icmc : vendor.getIcmcList()) {
				icmcIds.add(icmc.getId());
			}
		}
		vendor.setIcmcIds(icmcIds);
	}

	private static Sas mapToSasBean(String[] splitData) {
		Sas sasBean = new Sas(true);
		sasBean.setSrNo(getValue(splitData, 0));
		sasBean.setSolID(getValue(splitData, 1));
		sasBean.setBranch(getValue(splitData, 2));
		sasBean.setTotalValue(new BigDecimal(getValue(splitData, 3)));
		// sasBean.setTotalValueOfCoinsRs1(Integer.parseInt(getValue(splitData,
		// 32)));
		BigDecimal sumTotalValue = new BigDecimal(0);

		if (getValue(splitData, 4).equals("")) {
			sasBean.setTotalValueOfCoinsRs10(BigDecimal.ZERO);
		} else {
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 4)));
			// sasBean.setTotalValueOfCoinsRs10(Integer.parseInt(getValue(splitData,
			// 4)) / 20000);
			sasBean.setTotalValueOfCoinsRs10(new BigDecimal(getValue(splitData, 4)).divide(BigDecimal.valueOf(20000)));

		}

		if (getValue(splitData, 5).equals("")) {
			sasBean.setTotalValueOfCoinsRs5(BigDecimal.ZERO);
		} else {
			// sasBean.setTotalValueOfCoinsRs5(Integer.parseInt(getValue(splitData,
			// 5)) / 12500);
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 5)));
			sasBean.setTotalValueOfCoinsRs5(new BigDecimal(getValue(splitData, 5)).divide(BigDecimal.valueOf(12500)));

		}

		if (getValue(splitData, 6).equals("")) {
			sasBean.setTotalValueOfCoinsRs2(BigDecimal.ZERO);
		} else {
			// sasBean.setTotalValueOfCoinsRs2(Integer.parseInt(getValue(splitData,
			// 6)) / 5000);
			sasBean.setTotalValueOfCoinsRs2(new BigDecimal(getValue(splitData, 6)).divide(BigDecimal.valueOf(5000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 6)));
		}

		if (getValue(splitData, 7).equals("")) {
			sasBean.setTotalValueOfCoinsRs1(BigDecimal.ZERO);
		} else {
			// sasBean.setTotalValueOfCoinsRs1(Integer.parseInt(getValue(splitData,
			// 7)) / 2500);

			sasBean.setTotalValueOfCoinsRs1(new BigDecimal(getValue(splitData, 7)).divide(BigDecimal.valueOf(2500)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 7)));
		}

		if (getValue(splitData, 8).equals("")) {
			// sasBean.setTotalValueOfNotesRs1000I(0);
			sasBean.setTotalValueOfNotesRs2000A(BigDecimal.ZERO);
		} else {
			sasBean.setTotalValueOfNotesRs2000A(
					(new BigDecimal(getValue(splitData, 8)).divide(BigDecimal.valueOf(2000)))
							.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 8)));
		}

		if (getValue(splitData, 9).equals("")) {
			// sasBean.setTotalValueOfNotesRs1000I(0);
			sasBean.setTotalValueOfNotesRs2000F(BigDecimal.ZERO);
		} else {
			sasBean.setTotalValueOfNotesRs2000F(
					(new BigDecimal(getValue(splitData, 9)).divide(BigDecimal.valueOf(2000)))
							.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 9)));
		}

		if (getValue(splitData, 10).equals("")) {
			// sasBean.setTotalValueOfNotesRs1000I(0);
			sasBean.setTotalValueOfNotesRs2000I(BigDecimal.ZERO);
		} else {
			sasBean.setTotalValueOfNotesRs2000I(
					(new BigDecimal(getValue(splitData, 10)).divide(BigDecimal.valueOf(2000)))
							.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 10)));
		}

		if (getValue(splitData, 11).equals("")) {
			// sasBean.setTotalValueOfNotesRs1000I(0);
			sasBean.setTotalValueOfNotesRs1000A(BigDecimal.ZERO);
		} else {
			sasBean.setTotalValueOfNotesRs1000A(
					(new BigDecimal(getValue(splitData, 11)).divide(BigDecimal.valueOf(1000)))
							.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 11)));
		}

		if (getValue(splitData, 12).equals("")) {
			// sasBean.setTotalValueOfNotesRs1000I(0);
			sasBean.setTotalValueOfNotesRs1000F(BigDecimal.ZERO);
		} else {
			sasBean.setTotalValueOfNotesRs1000F(
					(new BigDecimal(getValue(splitData, 12)).divide(BigDecimal.valueOf(1000)))
							.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 12)));
		}

		if (getValue(splitData, 13).equals("")) {
			// sasBean.setTotalValueOfNotesRs1000I(0);
			sasBean.setTotalValueOfNotesRs1000I(BigDecimal.ZERO);
		} else {
			sasBean.setTotalValueOfNotesRs1000I(
					(new BigDecimal(getValue(splitData, 13)).divide(BigDecimal.valueOf(1000)))
							.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 13)));
		}

		if (getValue(splitData, 14).equals("")) {
			// sasBean.setTotalValueOfNotesRs500I(0);
			sasBean.setTotalValueOfNotesRs500A(BigDecimal.ZERO);
		} /*
			 * else { sasBean.setTotalValueOfNotesRs500I((Integer.parseInt(getValue(
			 * splitData, 44)) / 500) / 1000); }
			 */
		{
			sasBean.setTotalValueOfNotesRs500A((new BigDecimal(getValue(splitData, 14)).divide(BigDecimal.valueOf(500)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 14)));
		}

		if (getValue(splitData, 15).equals("")) {
			// sasBean.setTotalValueOfNotesRs500I(0);
			sasBean.setTotalValueOfNotesRs500F(BigDecimal.ZERO);
		} /*
			 * else { sasBean.setTotalValueOfNotesRs500I((Integer.parseInt(getValue(
			 * splitData, 44)) / 500) / 1000); }
			 */
		{
			sasBean.setTotalValueOfNotesRs500F((new BigDecimal(getValue(splitData, 15)).divide(BigDecimal.valueOf(500)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 15)));
		}

		if (getValue(splitData, 16).equals("")) {
			// sasBean.setTotalValueOfNotesRs500I(0);
			sasBean.setTotalValueOfNotesRs500I(BigDecimal.ZERO);
		} /*
			 * else { sasBean.setTotalValueOfNotesRs500I((Integer.parseInt(getValue(
			 * splitData, 44)) / 500) / 1000); }
			 */
		{
			sasBean.setTotalValueOfNotesRs500I((new BigDecimal(getValue(splitData, 16)).divide(BigDecimal.valueOf(500)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 16)));
		}

		if (getValue(splitData, 17).equals("")) {
			sasBean.setTotalValueOfNotesRs200A(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs200A((new BigDecimal(getValue(splitData, 17)).divide(BigDecimal.valueOf(200)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 17)));
		}

		if (getValue(splitData, 18).equals("")) {
			sasBean.setTotalValueOfNotesRs200F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs200F((new BigDecimal(getValue(splitData, 18)).divide(BigDecimal.valueOf(200)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 18)));
		}

		if (getValue(splitData, 19).equals("")) {
			sasBean.setTotalValueOfNotesRs200I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs200I((new BigDecimal(getValue(splitData, 19)).divide(BigDecimal.valueOf(200)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 19)));
		}

		if (getValue(splitData, 20).equals("")) {
			sasBean.setTotalValueOfNotesRs100A(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs100A((new BigDecimal(getValue(splitData, 20)).divide(BigDecimal.valueOf(100)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 20)));
		}

		if (getValue(splitData, 21).equals("")) {
			sasBean.setTotalValueOfNotesRs100F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs100F((new BigDecimal(getValue(splitData, 21)).divide(BigDecimal.valueOf(100)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 21)));
		}

		if (getValue(splitData, 22).equals("")) {
			sasBean.setTotalValueOfNotesRs100I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs100I((new BigDecimal(getValue(splitData, 22)).divide(BigDecimal.valueOf(100)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 22)));
		}

		if (getValue(splitData, 23).equals("")) {
			sasBean.setTotalValueOfNotesRs50F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs50F((new BigDecimal(getValue(splitData, 23)).divide(BigDecimal.valueOf(50)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 23)));
		}

		if (getValue(splitData, 24).equals("")) {
			sasBean.setTotalValueOfNotesRs50I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs50I((new BigDecimal(getValue(splitData, 24)).divide(BigDecimal.valueOf(50)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 24)));
		}

		if (getValue(splitData, 25).equals("")) {
			sasBean.setTotalValueOfNotesRs20F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs20F((new BigDecimal(getValue(splitData, 25)).divide(BigDecimal.valueOf(20)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 25)));
		}

		if (getValue(splitData, 26).equals("")) {
			sasBean.setTotalValueOfNotesRs20I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs20I((new BigDecimal(getValue(splitData, 26)).divide(BigDecimal.valueOf(20)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 26)));
		}

		if (getValue(splitData, 27).equals("")) {
			sasBean.setTotalValueOfNotesRs10F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs10F((new BigDecimal(getValue(splitData, 27)).divide(BigDecimal.valueOf(10)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 27)));
		}

		if (getValue(splitData, 28).equals("")) {
			sasBean.setTotalValueOfNotesRs10I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs10I((new BigDecimal(getValue(splitData, 28)).divide(BigDecimal.valueOf(10)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 28)));
		}

		if (getValue(splitData, 29).equals("")) {
			sasBean.setTotalValueOfNotesRs20F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs5F((new BigDecimal(getValue(splitData, 29)).divide(BigDecimal.valueOf(5)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 29)));
		}

		if (getValue(splitData, 30).equals("")) {
			sasBean.setTotalValueOfNotesRs5I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs5I((new BigDecimal(getValue(splitData, 30)).divide(BigDecimal.valueOf(5)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 30)));
		}

		if (getValue(splitData, 31).equals("")) {
			sasBean.setTotalValueOfNotesRs2F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs2F((new BigDecimal(getValue(splitData, 31)).divide(BigDecimal.valueOf(2)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 31)));
		}

		if (getValue(splitData, 32).equals("")) {
			sasBean.setTotalValueOfNotesRs2I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs2I((new BigDecimal(getValue(splitData, 32)).divide(BigDecimal.valueOf(2)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 32)));
		}

		if (getValue(splitData, 33).equals("")) {
			sasBean.setTotalValueOfNotesRs1F(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs1F((new BigDecimal(getValue(splitData, 33)).divide(BigDecimal.valueOf(1)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 33)));
		}

		if (getValue(splitData, 34).equals("")) {
			sasBean.setTotalValueOfNotesRs1I(BigDecimal.ZERO);
		}
		{
			sasBean.setTotalValueOfNotesRs1I((new BigDecimal(getValue(splitData, 34)).divide(BigDecimal.valueOf(1)))
					.divide(BigDecimal.valueOf(1000)));
			sumTotalValue = sumTotalValue.add(new BigDecimal(getValue(splitData, 34)));
		}

		if (sumTotalValue.compareTo(sasBean.getTotalValue()) == 0) {
			sasBean.setStatus(0);
		} else {
			sasBean.setTotalValue(sumTotalValue);
			sasBean.setStatus(1);
		}

		return sasBean;

	}

	public static List<Sas> CSVtoArrayList(List<String> sasRecords) {
		List<Sas> sasBeanList = new LinkedList<>();
		for (String str : sasRecords) {
			String[] splitData = str.split(",");
			Sas sASBean = mapToSasBean(splitData);
			sasBeanList.add(sASBean);
		}
		return sasBeanList;
	}

	public static void setBinColorForTxn(List<BinTransaction> binList) {
		for (BinTransaction binTx : binList) {
			binTx.setColor(getColor(binTx.getBinType()));
		}

	}

	private static CITCRAVehicle mapToCICRAVehicleBean(String[] splitData) {
		CITCRAVehicle vehicle = new CITCRAVehicle();
		vehicle.setName(splitData[0]);
		vehicle.setNumber(splitData[1]);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateinString = splitData[2];
		Date date1 = null;
		try {
			date1 = formatter.parse(dateinString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		vehicle.setBoughtDate(date1);

		vehicle.setRegCityName(splitData[3]);
		vehicle.setInsurance(splitData[4]);

		String fitness = splitData[5];
		Date date2 = null;
		try {
			date2 = formatter.parse(fitness);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		vehicle.setFitnessExpiryDate(date2);

		String pollution = splitData[6];
		Date date3 = null;
		try {
			date3 = formatter.parse(pollution);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		vehicle.setPollutionExpiryDate(date3);

		String permit = splitData[7];
		Date date4 = null;
		try {
			date4 = formatter.parse(permit);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		vehicle.setPermitDate(date4);

		return vehicle;
	}

	public static List<CITCRAVehicle> CSVtoArrayListForCITCRAVehicle(List<String> vehicleRecords) {
		List<CITCRAVehicle> vehicleList = new LinkedList<>();
		for (String str : vehicleRecords) {
			String[] splitData = str.split(",");
			CITCRAVehicle vehicle = mapToCICRAVehicleBean(splitData);
			vehicleList.add(vehicle);
		}
		return vehicleList;
	}

	private static CITCRADriver mapToCICRADriverBean(String[] splitData) {
		CITCRADriver driver = new CITCRADriver();
		driver.setVendorName(splitData[0]);
		driver.setVehicleNumber(splitData[1]);
		driver.setDriverName(splitData[2]);
		driver.setLicenseNumber(splitData[3]);
		driver.setLicenseIssuedState(splitData[4]);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String licenseIssuedDate = splitData[5];
		Date date1 = null;
		try {
			date1 = formatter.parse(licenseIssuedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		driver.setLicenseIssuedDated(date1);

		String licenseExpiryDate = splitData[6];
		Date date2 = null;
		try {
			date2 = formatter.parse(licenseExpiryDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		driver.setLicenseExpiryDate(date2);

		return driver;
	}

	public static List<CITCRADriver> CSVtoArrayListForCITCRADriver(List<String> driverRecords) {
		List<CITCRADriver> driverList = new LinkedList<>();
		for (String str : driverRecords) {
			String[] splitData = str.split(",");
			CITCRADriver driver = mapToCICRADriverBean(splitData);
			driverList.add(driver);
		}
		return driverList;
	}

	/*
	 * public static void setBinColor(List<BinMaster> binList) { for (BinMaster
	 * binMaster : binList) {
	 * binMaster.setColor(getColor(binMaster.getFirstPriority())); } }
	 */

	public static void setBinColor(List<BinTransaction> binList) {
		for (BinTransaction binTransaction : binList) {
			binTransaction.setColor(getColor(binTransaction.getBinType()));
		}
	}

	public static String getColor(CurrencyType binType) {
		String color = "";

		if (binType == CurrencyType.UNPROCESS) {
			color = BinColorType.UNPROCESS.getColor();
		} else if (binType == CurrencyType.ATM) {
			color = BinColorType.ATM.getColor();
		} else if (binType == CurrencyType.FRESH) {
			color = BinColorType.FRESH.getColor();
		} else if (binType == CurrencyType.SOILED) {
			color = BinColorType.SOILED.getColor();
		} else if (binType == CurrencyType.ISSUABLE) {
			color = BinColorType.ISSUABLE.getColor();
		}
		return color;
	}

	public static List<SASAllocation> getBinForSASRequest(List<BinTransaction> txnList, BigDecimal bundleRequired,
			SASAllocation sasData, Sas sasAllocationParent, CurrencyType binType, User user) {
		Calendar now = Calendar.getInstance();
		List<SASAllocation> eligibleSASList = new ArrayList<>();
		BigDecimal totalAvailableBundle = BigDecimal.ZERO;
		BigDecimal totalBundleRequired = bundleRequired;
		for (BinTransaction binTx : txnList) {
			SASAllocation sasAllocation = new SASAllocation();
			// BigDecimal availableBundle = binTx.getReceiveBundle();
			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);
			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);
			totalAvailableBundle = totalAvailableBundle.add(availableBundle);
			if (availableBundle.doubleValue() <= 0) {
				continue;
			}
			if (availableBundle.compareTo(bundleRequired) >= 0) {
				sasAllocation.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				setSasAllocationDetails(user, now, binTx, sasAllocation, sasAllocationParent, CashType.NOTES, binType);
				eligibleSASList.add(sasAllocation);
				break;
			} else {
				sasAllocation.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				setSasAllocationDetails(user, now, binTx, sasAllocation, sasAllocationParent, CashType.NOTES, binType);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleSASList.add(sasAllocation);
			}
		}

		if (totalAvailableBundle.compareTo(totalBundleRequired) >= 0) {

			return eligibleSASList;
		} else {
			throw new BaseGuiException("Required Bundle is not available, TotalAvailableBundle is:"
					+ totalAvailableBundle.toPlainString() + " for Denomination:" + sasData.getDenomination()
					+ " and Category : " + sasData.getBinType());
		}
	}

	public static List<SASAllocation> getCoinsForSASRequest(List<BinTransaction> txnList, BigDecimal bundleRequired,
			SASAllocation sasData, Sas sasAllocationParent, CurrencyType binType, User user) {
		Calendar now = Calendar.getInstance();
		List<SASAllocation> eligibleSASList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			SASAllocation sasAllocation = new SASAllocation();
			// BigDecimal availableBundle = binTx.getReceiveBundle();
			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);
			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);
			if (availableBundle.doubleValue() <= 0) {
				continue;
			}
			if (availableBundle.compareTo(bundleRequired) >= 0) {
				sasAllocation.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				setSasAllocationDetails(user, now, binTx, sasAllocation, sasAllocationParent, CashType.COINS,
						CurrencyType.FRESH);
				eligibleSASList.add(sasAllocation);
				break;
			} else {
				sasAllocation.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				setSasAllocationDetails(user, now, binTx, sasAllocation, sasAllocationParent, CashType.COINS,
						CurrencyType.FRESH);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleSASList.add(sasAllocation);
			}
		}
		return eligibleSASList;
	}

	private static void setSasAllocationDetails(User user, Calendar now, BinTransaction binTx,
			SASAllocation sasAllocation, Sas sasAllocationParent, CashType cashType, CurrencyType binType) {
		sasAllocation.setBinNumber(binTx.getBinNumber());
		sasAllocation.setCashType(cashType);
		sasAllocation.setBinType(binType);
		sasAllocation.setParentId(sasAllocationParent.getId());
		sasAllocation.setDenomination(binTx.getDenomination());
		sasAllocation.setIcmcId(binTx.getIcmcId());
		sasAllocation.setInsertBy(user.getId());
		sasAllocation.setUpdateBy(user.getId());
		sasAllocation.setInsertTime(now);
		sasAllocation.setUpdateTime(now);
		sasAllocation.setStatus(OtherStatus.REQUESTED);
	}

	public static List<SoiledRemittanceAllocation> getBoxForSoiled(List<BinTransaction> txnList,
			BigDecimal bundleRequired, Integer denom, CurrencyType binType, User user, BigDecimal bundleFromTxn) {
		Calendar now = Calendar.getInstance();

		List<SoiledRemittanceAllocation> eligibleSoiledList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			SoiledRemittanceAllocation soiled = new SoiledRemittanceAllocation();

			BigDecimal availableBundle = binTx.getReceiveBundle();

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (bundleRequired != BigDecimal.ZERO) {
				if (availableBundle.compareTo(bundleRequired) >= 0) {
					calculateAndSetPendingBundleRequest(bundleRequired, binTx);
					soiled.setBox(binTx.getBinNumber());
					soiled.setBundle(availableBundle);
					soiled.setDenomination(binTx.getDenomination());
					soiled.setIcmcId(user.getIcmcId());
					soiled.setCurrencyType(binTx.getBinType());
					soiled.setInsertBy(user.getId());
					soiled.setUpdateBy(user.getId());
					soiled.setInsertTime(now);
					soiled.setUpdateTime(now);
					soiled.setStatus(OtherStatus.REQUESTED);
					soiled.setBinTxnId(binTx.getId());
					// bundleRequired =
					// bundleRequired.subtract(availableBundle);
					soiled.setPendingBundle(bundleRequired);
					eligibleSoiledList.add(soiled);
				} else {
					throw new BaseGuiException("Soiled Boxes are not avaiable for " + denom + " denomination");
				}
			}
		}
		/*
		 * if (bundleRequired != BigDecimal.ZERO) { throw new BaseGuiException(
		 * "Soiled Boxes are not avaiable for "+denom+" denomination"); }
		 */
		return eligibleSoiledList;
	}

	public static BigDecimal getSubstarctedBundle(BinTransaction binTx, BigDecimal pendingBundleRequest) {
		return binTx.getReceiveBundle().subtract(pendingBundleRequest);
	}

	private static void calculateAndSetPendingBundleRequest(BigDecimal bundleRequired, BinTransaction binTx) {

		binTx.setPendingBundleRequest(binTx.getPendingBundleRequest() == null ? bundleRequired
				: binTx.getPendingBundleRequest().add(bundleRequired));
		binTx.setDirty(true);

		int isConsistent = binTx.getReceiveBundle().compareTo(binTx.getPendingBundleRequest());

		if (isConsistent < 0) {
			throw new RuntimeException("BinTx has wrong pending and receive bundle:" + binTx);
		}
	}

	public static BigDecimal getPendingBundleRequest(BinTransaction binTx) {
		return binTx.getPendingBundleRequest() == null ? BigDecimal.ZERO : binTx.getPendingBundleRequest();
	}

	@SuppressWarnings("unused")
	private static boolean isExistBinInSoiledRemmitanceAllocation(BinTransaction binTx,
			List<SoiledRemittanceAllocation> soiledList) {

		for (SoiledRemittanceAllocation soiled : soiledList) {
			if (binTx.getBinNumber().equalsIgnoreCase(soiled.getBinNumber())) {
				return true;
			}
		}
		return false;
	}

	public static List<DiversionORVAllocation> getBinForDiversionORV(List<BinTransaction> txnList,
			List<DiversionORVAllocation> dorvList, BigDecimal bundleRequired, User user, BigDecimal total,
			String category, CurrencyType binType) {
		Calendar now = Calendar.getInstance();
		List<DiversionORVAllocation> eligibleDorvList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			DiversionORVAllocation dorv = new DiversionORVAllocation();

			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);
			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			// BigDecimal availableBundle = binTx.getReceiveBundle();
			if (availableBundle.compareTo(bundleRequired) >= 0) {
				dorv.setBinNumber(binTx.getBinNumber());
				dorv.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				dorv.setPendingBundle(bundleRequired);
				dorv.setDenomination(binTx.getDenomination());
				dorv.setCurrencyType(binTx.getBinType());
				dorv.setTotal(dorv.getBundle()
						.multiply(new BigDecimal(1000).multiply(new BigDecimal(binTx.getDenomination()))));
				dorv.setCategory(category);
				dorv.setIcmcId(user.getIcmcId());
				dorv.setInsertBy(user.getId());
				dorv.setUpdateBy(user.getId());
				dorv.setInsertTime(now);
				dorv.setUpdateTime(now);
				dorv.setStatus(OtherStatus.REQUESTED);
				eligibleDorvList.add(dorv);
				break;
			} else {
				dorv.setBinNumber(binTx.getBinNumber());
				dorv.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				dorv.setPendingBundle(availableBundle);
				dorv.setDenomination(binTx.getDenomination());
				dorv.setTotal(dorv.getBundle()
						.multiply(new BigDecimal(1000).multiply(new BigDecimal(binTx.getDenomination()))));
				dorv.setCurrencyType(binTx.getBinType());
				dorv.setCategory(category);
				dorv.setIcmcId(user.getIcmcId());
				dorv.setInsertBy(user.getId());
				dorv.setUpdateBy(user.getId());
				dorv.setInsertTime(now);
				dorv.setUpdateTime(now);
				dorv.setStatus(OtherStatus.REQUESTED);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleDorvList.add(dorv);
			}
		}
		return eligibleDorvList;
	}

	@SuppressWarnings("unused")
	private static boolean isExistBinInDiversionORVAllocation(BinTransaction binTx,
			List<DiversionORVAllocation> dorvList) {

		for (DiversionORVAllocation dorv : dorvList) {
			if (binTx.getBinNumber().equalsIgnoreCase(dorv.getBinNumber())) {
				return true;
			}
		}
		return false;
	}

	public static List<ORVAllocation> getBinForORV(List<BinTransaction> txnList, List<ORVAllocation> orvList,
			BigDecimal bundleRequired, User user, BigDecimal total)

	{

		Iterator<BinTransaction> txItr = txnList.iterator();
		while (txItr.hasNext()) {
			BinTransaction binTx = txItr.next();
			boolean isExist = isExistBinInORVAllocation(binTx, orvList);
			if (isExist) {
				txItr.remove();
			}
		}

		Calendar now = Calendar.getInstance();
		List<ORVAllocation> eligibleOrvList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			ORVAllocation orv = new ORVAllocation();
			BigDecimal availableBundle = binTx.getReceiveBundle();
			if (availableBundle.compareTo(bundleRequired) >= 0) {
				orv.setBinNumber(binTx.getBinNumber());
				orv.setBundle(bundleRequired);
				orv.setDenomination(binTx.getDenomination());
				orv.setIcmcId(user.getIcmcId());
				orv.setCurrencyType(binTx.getBinType());
				orv.setTotal(total);
				orv.setInsertBy(user.getId());
				orv.setUpdateBy(user.getId());
				orv.setInsertTime(now);
				orv.setUpdateTime(now);
				orv.setStatus("INDENT");
				eligibleOrvList.add(orv);
				break;
			} else {
				orv.setBinNumber(binTx.getBinNumber());
				orv.setBundle(binTx.getReceiveBundle());
				orv.setDenomination(binTx.getDenomination());
				orv.setTotal(total);
				orv.setCurrencyType(binTx.getBinType());
				orv.setIcmcId(user.getIcmcId());
				orv.setInsertBy(user.getId());
				orv.setUpdateBy(user.getId());
				orv.setInsertTime(now);
				orv.setUpdateTime(now);
				orv.setStatus("INDENT");
				bundleRequired = bundleRequired.subtract(binTx.getReceiveBundle());
				eligibleOrvList.add(orv);
			}
		}
		return eligibleOrvList;
	}

	private static boolean isExistBinInORVAllocation(BinTransaction binTx, List<ORVAllocation> orvList) {
		for (ORVAllocation orv : orvList) {
			if (binTx.getBinNumber().equalsIgnoreCase(orv.getBinNumber())) {
				return true;
			}
		}
		return false;
	}

	private static FakeNote mapToFakeNote(String[] splitData) {
		FakeNote fake = new FakeNote();

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String sFakeNoteDate = splitData[0];
		Date fakeNoteDate = null;
		try {
			fakeNoteDate = formatter.parse(sFakeNoteDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		fake.setFakeNoteDate(fakeNoteDate);
		fake.setSolId(Integer.parseInt(getValue(splitData, 1)));
		fake.setBranch(getValue(splitData, 2));
		fake.setDenomination(Integer.parseInt(getValue(splitData, 3)));
		fake.setFakeNoteSerialNumber(getValue(splitData, 4));
		fake.setCustTellerCam(getValue(splitData, 5));
		fake.setAccountNumber(getValue(splitData, 6));
		return fake;
	}

	public static List<FakeNote> CSVtoArrayListForFakeNote(List<String> fakeNoteRecords) {
		List<FakeNote> fakeNoteList = new LinkedList<>();
		for (String str : fakeNoteRecords) {
			String[] splitData = str.split(",");
			FakeNote fakeNote = mapToFakeNote(splitData);
			fakeNoteList.add(fakeNote);
		}
		return fakeNoteList;
	}

	/**
	 * @param voucherList
	 * @return
	 */
	public static IRVVoucherWrapper prepareBranchReceiptIRVVoucher(List<Tuple> voucherList) {
		IRVVoucherWrapper branchReceiptWrapper = new IRVVoucherWrapper();
		List<BranchReceipt> branchReceipts = new ArrayList<>();

		BigDecimal grossTotal = BigDecimal.ZERO;
		if (!CollectionUtils.isEmpty(voucherList)) {
			for (Tuple t : voucherList) {
				BranchReceipt branchReceipt = new BranchReceipt();
				branchReceipt.setDenomination(t.get(0, Integer.class));
				BigDecimal bundle = t.get(1, BigDecimal.class);
				bundle = bundle.multiply(BigDecimal.TEN);// verify
				branchReceipt.setBundle(bundle);
				BigDecimal total = bundle.multiply(new BigDecimal(branchReceipt.getDenomination()));
				branchReceipt.setTotal(total);

				BigDecimal bundleAndDenoTotal = total.multiply(new BigDecimal(branchReceipt.getDenomination()));
				branchReceipt.setBundleAndDenominationTotal(bundleAndDenoTotal);

				branchReceipts.add(branchReceipt);
				grossTotal = grossTotal.add(bundleAndDenoTotal);
			}
		}
		branchReceiptWrapper.setBranchReceipts(branchReceipts);
		branchReceiptWrapper.setTotal(grossTotal);
		return branchReceiptWrapper;
	}

	public static List<SASAllocation> mapSasAllocationGroupedToSasAllocation(
			List<SASAllocationGrouped> sasAllocationList) {

		List<SASAllocation> allocations = new ArrayList<>();

		for (SASAllocationGrouped sas : sasAllocationList) {
			if (sas.getCashType() == CashType.NOTES) {
				if (sas.getAtmBin() == CurrencyType.ATM) {
					SASAllocation sasAllocation = new SASAllocation();
					sasAllocation.setBinType(sas.getAtmBin());
					sasAllocation.setBundle(sas.getAtmBundle());
					sasAllocation.setDenomination(sas.getDenomination());
					sasAllocation.setIcmcId(sas.getIcmcId());
					sasAllocation.setCashType(sas.getCashType());
					allocations.add(sasAllocation);

				}

				if (sas.getFreshBin() == CurrencyType.FRESH) {
					SASAllocation sasAllocation = new SASAllocation();
					sasAllocation.setBinType(sas.getFreshBin());
					sasAllocation.setBundle(sas.getFreshBundle());
					sasAllocation.setDenomination(sas.getDenomination());
					sasAllocation.setIcmcId(sas.getIcmcId());
					sasAllocation.setCashType(sas.getCashType());
					allocations.add(sasAllocation);

				}

				if (sas.getIssuableBin() == CurrencyType.ISSUABLE) {
					SASAllocation sasAllocation = new SASAllocation();
					sasAllocation.setBinType(sas.getIssuableBin());
					sasAllocation.setBundle(sas.getIssuableBundle());
					sasAllocation.setDenomination(sas.getDenomination());
					sasAllocation.setIcmcId(sas.getIcmcId());
					sasAllocation.setCashType(sas.getCashType());
					allocations.add(sasAllocation);
				}

			} else if (sas.getCashType() == CashType.COINS) {
				SASAllocation sasAllocation = new SASAllocation();
				sasAllocation.setBundle(sas.getCoinsBag());
				sasAllocation.setDenomination(sas.getDenomination());
				sasAllocation.setIcmcId(sas.getIcmcId());
				sasAllocation.setCashType(sas.getCashType());
				allocations.add(sasAllocation);
			}
		}
		return allocations;
	}

	public static List<CRAAllocation> processCRARequest(BigDecimal bundleRequired, CRAAllocation craAllocation,
			CurrencyType binType, User user) {
		Calendar now = Calendar.getInstance();
		List<CRAAllocation> CRAList = new ArrayList<>();
		CRAAllocation cra = new CRAAllocation();
		cra.setBundle(bundleRequired);
		cra.setCurrencyType(binType);
		cra.setInsertBy(user.getId());
		cra.setUpdateBy(user.getId());
		cra.setInsertTime(now);
		cra.setDenomination(craAllocation.getDenomination());
		cra.setTotal(craAllocation.getTotal());
		cra.setIcmcId(user.getIcmcId());
		cra.setVault(BigDecimal.ZERO);
		cra.setForward(BigDecimal.ZERO);
		cra.setPendingRequestedBundle(craAllocation.getBundle());
		cra.setUpdateTime(now);
		cra.setStatus(OtherStatus.REQUESTED);

		CRAList.add(cra);

		return CRAList;
	}

	public static List<CRAAllocationLog> processCRAAllocationLogRequest(CRAAllocation craAllocationData) {
		List<CRAAllocationLog> CRAAllocationLogList = new ArrayList<>();
		CRAAllocationLog craAloLog = new CRAAllocationLog();
		craAloLog.setCraId(craAllocationData.getCraId());
		craAloLog.setCraAllocationId(craAllocationData.getId());
		craAloLog.setBundle(craAllocationData.getBundle());
		craAloLog.setCurrencyType(craAllocationData.getCurrencyType());
		craAloLog.setInsertBy(craAllocationData.getInsertBy());
		craAloLog.setUpdateBy(craAllocationData.getUpdateBy());
		craAloLog.setInsertTime(craAllocationData.getInsertTime());
		craAloLog.setDenomination(craAllocationData.getDenomination());
		craAloLog.setTotal(craAllocationData.getTotal());
		craAloLog.setIcmcId(craAllocationData.getIcmcId());
		craAloLog.setVault(craAllocationData.getVault());
		craAloLog.setForward(craAllocationData.getForward());
		craAloLog.setPendingRequestedBundle(craAllocationData.getPendingRequestedBundle());
		craAloLog.setUpdateTime(craAllocationData.getUpdateTime());
		craAloLog.setStatus(craAllocationData.getStatus());
		craAloLog.setAction(1);
		CRAAllocationLogList.add(craAloLog);
		return CRAAllocationLogList;
	}

	public static void processCRALogRequest(CRA craList, CRALog craLog) {
		List<CRALog> CRALogList = new ArrayList<>();
		craLog.setIcmcId(craList.getIcmcId());
		craLog.setInsertBy(craList.getInsertBy());
		craLog.setUpdateBy(craList.getUpdateBy());
		craLog.setInsertTime(craList.getInsertTime());
		craLog.setUpdateTime(craList.getUpdateTime());
		craLog.setCraId(craList.getId());
		craLog.setStatus(craList.getStatus());
		craLog.setBranch(craList.getBranch());
		craLog.setSrNo(craList.getSrNo());
		craLog.setSolId(craList.getAccountNumber());
		craLog.setVendor(craList.getVendor());
		craLog.setMspName(craList.getMspName());
		craLog.setAccountNumber(craList.getAccountNumber());
		craLog.setAction(1);
		CRALogList.add(craLog);

	}

	public static CRAAllocation getForwardProcessBundleForCRAPayment(CRAAllocation craAllocation,
			CRAAllocation craAllocationDb, User user) {

		Calendar now = Calendar.getInstance();
		CRAAllocation craProcessed = new CRAAllocation();
		craProcessed.setForward(craAllocation.getForward());
		craProcessed.setPendingRequestedBundle(craProcessed.getForward());
		craProcessed.setCraId(craAllocationDb.getCraId());
		craProcessed.setCurrencyType(craAllocation.getCurrencyType());
		craProcessed.setDenomination(craAllocation.getDenomination());
		craProcessed.setIcmcId(user.getIcmcId());
		craProcessed.setInsertBy(user.getId());
		craProcessed.setUpdateBy(user.getId());
		craProcessed.setInsertTime(now);
		craProcessed.setUpdateTime(now);
		craProcessed.setStatus(OtherStatus.REQUESTED);

		craAllocationDb.setUpdateBy(user.getId());
		craAllocationDb.setUpdateTime(now);
		craAllocationDb.setForward(craAllocationDb.getForward().add(craAllocation.getForward()));
		craAllocationDb.setPendingRequestedBundle(
				craAllocationDb.getPendingRequestedBundle().subtract(craAllocation.getForward()));

		if (craAllocationDb.getPendingRequestedBundle().compareTo(BigDecimal.ZERO) == 0) {
			craAllocationDb.setStatus(OtherStatus.PROCESSED);
		}

		return craProcessed;
	}

	public static List<CRAAllocation> getBinForCRARequest(List<BinTransaction> txnList, CRAAllocation craRequestedUI,
			CRAAllocation craRequested, User user) {
		Calendar now = Calendar.getInstance();
		List<CRAAllocation> eligibleCRAList = new ArrayList<>();
		BigDecimal bundleRequired = craRequestedUI.getVault();
		CurrencyType binType = craRequestedUI.getCurrencyType();

		BigDecimal pendingBundleCount = craRequested.getPendingRequestedBundle() == null ? BigDecimal.ZERO
				: craRequested.getPendingRequestedBundle();

		for (BinTransaction binTx : txnList) {
			CRAAllocation craProcessed = new CRAAllocation();
			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);
			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (availableBundle.compareTo(bundleRequired) >= 0) {
				craProcessed.setCraId(craRequested.getCraId());
				craProcessed.setBinNumber(binTx.getBinNumber());
				craProcessed.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				craProcessed.setCurrencyType(binType);

				craProcessed.setDenomination(binTx.getDenomination());

				craProcessed.setIcmcId(binTx.getIcmcId());
				craProcessed.setInsertBy(user.getId());
				craProcessed.setUpdateBy(user.getId());
				craProcessed.setInsertTime(now);
				craProcessed.setUpdateTime(now);
				craProcessed.setStatus(OtherStatus.PROCESSED);
				pendingBundleCount = pendingBundleCount.subtract(craProcessed.getBundle());
				eligibleCRAList.add(craProcessed);
				break;
			} else {
				craProcessed.setCraId(craRequested.getCraId());
				craProcessed.setBinNumber(binTx.getBinNumber());
				craProcessed.setBundle(getSubstarctedBundle(binTx, pendingBundleRequest));
				calculateAndSetPendingBundleRequest(getSubstarctedBundle(binTx, pendingBundleRequest), binTx);
				craProcessed.setCurrencyType(binType);
				craProcessed.setDenomination(binTx.getDenomination());
				craProcessed.setIcmcId(binTx.getIcmcId());
				craProcessed.setInsertBy(user.getId());
				craProcessed.setUpdateBy(user.getId());
				craProcessed.setInsertTime(now);
				craProcessed.setUpdateTime(now);
				craProcessed.setStatus(OtherStatus.PROCESSED);
				bundleRequired = bundleRequired.subtract(getSubstarctedBundle(binTx, pendingBundleRequest));
				pendingBundleCount = pendingBundleCount.subtract(craProcessed.getBundle());
				eligibleCRAList.add(craProcessed);
			}

		}
		craRequested.setUpdateBy(user.getId());
		craRequested.setUpdateTime(now);
		craRequested.setVault(craRequested.getVault()
				.add(craRequestedUI.getVault() == null ? BigDecimal.ZERO : craRequestedUI.getVault()));
		/*
		 * craRequested.setForward(craRequested.getForward()
		 * .add(craRequestedUI.getForward() == null ? BigDecimal.ZERO :
		 * craRequestedUI.getForward()));
		 */
		craRequested.setPendingRequestedBundle(pendingBundleCount);
		if (pendingBundleCount.compareTo(BigDecimal.ZERO) == 0) {
			craRequested.setStatus(OtherStatus.PROCESSED);
		}

		/*
		 * if(pendingBundleCount.compareTo(bundleRequired)>=0) { return eligibleCRAList;
		 * } else { throw new BaseGuiException(
		 * "Required Bundle is not available, TotalAvailableBundle is:" +
		 * pendingBundleCount.toPlainString()); }
		 */
		return eligibleCRAList;
	}

	public static List<RbiMaster> CSVtoArrayListForRBIMaster(List<String> rbiRecords) {
		List<RbiMaster> rbiMasterList = new LinkedList<>();
		for (String str : rbiRecords) {
			String[] splitData = str.split(",");
			RbiMaster rbi = mapToRBI(splitData);
			rbiMasterList.add(rbi);
		}
		return rbiMasterList;
	}

	private static RbiMaster mapToRBI(String[] splitData) {
		RbiMaster rbi = new RbiMaster();
		rbi.setRbiname(splitData[0]);
		rbi.setZone(getZoneValue(splitData, 1));
		rbi.setRegion(splitData[2]);
		rbi.setState(getStateValue(splitData, 3));
		rbi.setCity(splitData[4]);
		rbi.setAddress(splitData[5]);
		rbi.setPinno(Integer.parseInt(getValue(splitData, 6)));
		return rbi;
	}

	private static State getStateValue(String[] splitData, int i) {

		if (splitData != null && splitData.length > i) {
			return State.valueOf(splitData[i]);
		}
		return State.valueOf("");
	}

	public static List<OtherBankAllocation> getBinForOtherBankRequest(List<BinTransaction> txnList,
			OtherBankAllocation otherBankData, BigDecimal bundleRequired, BigDecimal total, CurrencyType binType,
			User user) {
		Calendar now = Calendar.getInstance();
		List<OtherBankAllocation> eligibleOtherBankList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			OtherBankAllocation otherBank = new OtherBankAllocation();
			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);
			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);
			if (availableBundle.doubleValue() <= 0) {
				continue;
			}
			if (availableBundle.compareTo(bundleRequired) >= 0) {
				otherBank.setBinNumber(binTx.getBinNumber());
				otherBank.setBundle(bundleRequired);
				otherBank.setStatus(OtherStatus.REQUESTED);
				otherBank.setPendingBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				otherBank.setCurrencyType(binType);
				otherBank.setDenomination(binTx.getDenomination());
				otherBank.setTotal(total);
				otherBank.setIcmcId(binTx.getIcmcId());
				otherBank.setInsertBy(user.getId());
				otherBank.setUpdateBy(user.getId());
				otherBank.setInsertTime(now);
				otherBank.setUpdateTime(now);
				eligibleOtherBankList.add(otherBank);
				break;
			} else {
				otherBank.setBinNumber(binTx.getBinNumber());
				otherBank.setBundle(availableBundle);
				otherBank.setStatus(OtherStatus.REQUESTED);
				otherBank.setPendingBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				otherBank.setCurrencyType(binType);
				otherBank.setDenomination(binTx.getDenomination());
				otherBank.setTotal(total);
				otherBank.setIcmcId(binTx.getIcmcId());
				otherBank.setInsertBy(user.getId());
				otherBank.setUpdateBy(user.getId());
				otherBank.setInsertTime(now);
				otherBank.setUpdateTime(now);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleOtherBankList.add(otherBank);
			}
		}
		return eligibleOtherBankList;
	}

	public static List<BinTransaction> getBinForFreshFromRBI(List<BinMaster> binMasters, BigDecimal bundle,
			boolean isBundle, List<BinCapacityDenomination> capacityList, CurrencyType currencyType,
			CashSource cashSource, YesNo verified, BinCategoryType binCategoryType, CashType cashType,
			String rbiOrderNo) {
		BinCapacityDenomination capacity = new BinCapacityDenomination();

		BigDecimal bundleRequired = bundle;
		if (!isBundle) {
			bundleRequired = bundleRequired.divide(BigDecimal.TEN);
		}
		List<BinTransaction> alloccateTxs = new ArrayList<BinTransaction>();
		boolean isFound = false;
		Calendar now = Calendar.getInstance();

		if (!isFound) {
			if (binMasters != null && binMasters.size() >= 0) {

				// Calendar now = Calendar.getInstance();

				for (BinMaster binMaster : binMasters) {

					getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);

					// get capacity based on binMaster bin vault size
					BigDecimal availableSpace = capacity.getMaxBundleCapacity();
					if (availableSpace.compareTo(bundleRequired) >= 0) {

						BinTransaction binTx = new BinTransaction();
						binTx.setBinNumber(binMaster.getBinNumber());
						binTx.setDenomination(capacity.getDenomination());
						binTx.setMaxCapacity(capacity.getMaxBundleCapacity());
						binTx.setRbiOrderNo(rbiOrderNo);
						binTx.setBinType(currencyType);
						binTx.setCashSource(cashSource);
						binTx.setReceiveBundle(bundleRequired);
						binTx.setCurrentBundle(bundleRequired);
						binTx.setVerified(verified);
						binTx.setBinCategoryType(binCategoryType);
						binTx.setCashType(cashType);
						binTx.setStatus(BinStatus.NOT_FULL);
						binTx.setInsertBy(binMaster.getInsertBy());
						binTx.setUpdateBy(binMaster.getUpdateBy());
						binTx.setIcmcId(binMaster.getIcmcId());
						binTx.setInsertTime(now);
						binTx.setUpdateTime(now);
						alloccateTxs.add(binTx);
						isFound = true;
						break;
					} else {
						BinTransaction binTx = new BinTransaction();
						binTx.setBinNumber(binMaster.getBinNumber());
						binTx.setDenomination(capacity.getDenomination());
						binTx.setMaxCapacity(capacity.getMaxBundleCapacity());
						binTx.setBinType(currencyType);
						binTx.setCashSource(cashSource);
						binTx.setRbiOrderNo(rbiOrderNo);
						binTx.setReceiveBundle(availableSpace);
						binTx.setCurrentBundle(availableSpace);
						binTx.setVerified(verified);
						binTx.setBinCategoryType(binCategoryType);
						binTx.setCashType(cashType);
						binTx.setStatus(BinStatus.FULL);
						binTx.setInsertBy(binMaster.getInsertBy());
						binTx.setUpdateBy(binMaster.getUpdateBy());
						binTx.setIcmcId(binMaster.getIcmcId());
						binTx.setInsertTime(now);
						binTx.setUpdateTime(now);
						alloccateTxs.add(binTx);
						bundleRequired = bundleRequired.subtract(availableSpace);
					}
				}
			}
		}
		// new BinTransactionWrapper
		return alloccateTxs;
	}

	public static List<BinMaster> CSVtoArrayListForBin(List<String> binRecords) {
		List<BinMaster> binList = new LinkedList<>();
		for (String str : binRecords) {
			String[] splitData = str.split(",");
			BinMaster binmaster = mapToBinMasterBean(splitData);
			binList.add(binmaster);
		}
		return binList;
	}

	private static BinMaster mapToBinMasterBean(String[] splitData) {
		BinMaster binMaster = new BinMaster();
		binMaster.setBinNumber(splitData[0]);
		binMaster.setFirstPriority(getFirstPriorityValue(splitData, 1));
		binMaster.setSecondPriority(getFirstPriorityValue(splitData, 2));
		binMaster.setThirdPriority(getFirstPriorityValue(splitData, 3));
		binMaster.setLocationPriority(Integer.parseInt(splitData[4]));

		return binMaster;
	}

	private static CurrencyType getFirstPriorityValue(String[] splitData, int i) {

		if (splitData != null && splitData.length > i) {
			return CurrencyType.fromValue(splitData[i].toUpperCase());

		}
		return CurrencyType.fromValue("");
	}

	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime();
	}

	public static Calendar getStartDate() {
		Calendar sDate = Calendar.getInstance();

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

		return sDate;
	}

	public static Calendar getEndDate() {
		Calendar eDate = Calendar.getInstance();

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

		return eDate;
	}

	public static void setStartDate(Calendar sDate) {

		sDate.set(Calendar.HOUR, 0);
		sDate.set(Calendar.HOUR_OF_DAY, 0);
		sDate.set(Calendar.MINUTE, 0);
		sDate.set(Calendar.SECOND, 0);
		sDate.set(Calendar.MILLISECOND, 0);

	}

	public static void setEndDate(Calendar eDate) {

		eDate.set(Calendar.HOUR, 24);
		eDate.set(Calendar.HOUR_OF_DAY, 23);
		eDate.set(Calendar.MINUTE, 59);
		eDate.set(Calendar.SECOND, 59);
		eDate.set(Calendar.MILLISECOND, 999);

	}

	private static BigDecimal getSubstarctedBundleForBranchReceipt(BranchReceipt br, List<BinTransaction> txnList,
			BigDecimal bundleRequired) {
		for (BinTransaction binTx : txnList) {
			if (br.getBin().equalsIgnoreCase(binTx.getBinNumber())) {
				if (getSubstarctedBundle(binTx, getPendingBundleRequest(binTx).add(br.getBundle()))
						.compareTo(BigDecimal.ZERO) >= 0) {
					if (br.getBundle().compareTo(bundleRequired) <= 0) {
						calculateAndSetPendingBundleRequest(br.getBundle(), binTx);
						binTx.setDirty(true);
						br.setStatus(OtherStatus.PROCESSED);
					}
				}
				break;
			}
		}
		return br.getBundle();
	}

	public static BigDecimal checkMoreRequiredBundleNeeded(List<Indent> eligibleIndentRequestList,
			BigDecimal bundleForRequest) {

		BigDecimal availableBundle = BigDecimal.ZERO;
		for (Indent indent : eligibleIndentRequestList) {
			availableBundle = availableBundle.add(indent.getBundle());
		}
		availableBundle = bundleForRequest.subtract(availableBundle);
		return availableBundle;
	}

	public static BigDecimal checkMoreRequiredBundleNeededForSas(List<SASAllocation> eligibleIndentRequestList,
			BigDecimal bundleForRequest) {

		BigDecimal availableBundle = BigDecimal.ZERO;
		for (SASAllocation sasAlloc : eligibleIndentRequestList) {
			availableBundle = availableBundle.add(sasAlloc.getBundle());
		}
		availableBundle = bundleForRequest.subtract(availableBundle);
		return availableBundle;
	}

	public static BigDecimal checkMoreRequiredBundleNeededForMutilated(List<MutilatedIndent> eligibleIndentRequestList,
			BigDecimal bundleForRequest) {

		BigDecimal availableBundle = BigDecimal.ZERO;
		for (MutilatedIndent indent : eligibleIndentRequestList) {
			availableBundle = availableBundle.add(indent.getBundle());
		}
		availableBundle = bundleForRequest.subtract(availableBundle);
		return availableBundle;
	}

	public static List<FreshFromRBI> getFreshFromRBIafterCounting(FreshFromRBI fresh, List<BinTransaction> binTxs,
			User user, List<BinCapacityDenomination> capacityList, List<BinMaster> binMasterList,
			List<BoxMaster> boxMasterList) {
		BinCapacityDenomination capacity = new BinCapacityDenomination();
		List<FreshFromRBI> freshList = new ArrayList<>();

		for (BinMaster binMaster : binMasterList) {
			getMaxBundleCapacityAccordingToVaultSize(capacityList, capacity, binMaster);
		}

		for (BinTransaction binTx : binTxs) {
			FreshFromRBI freshData = new FreshFromRBI();
			freshData.setIcmcId(binTx.getIcmcId());
			freshData.setOrderDate(fresh.getOrderDate());
			freshData.setRbiOrderNo(fresh.getRbiOrderNo());
			freshData.setVehicleNumber(fresh.getVehicleNumber());
			freshData.setPotdarName(fresh.getPotdarName());
			freshData.setEscortOfficerName(fresh.getEscortOfficerName());
			freshData.setBinCategoryType(fresh.getBinCategoryType());
			freshData.setCashType(fresh.getCashType());
			freshData.setCashSource(fresh.getCashSource());
			freshData.setDenomination(fresh.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			freshData.setBundle(rcvBundle);
			freshData.setPendingBundleRequest(rcvBundle);
			freshData.setTotal(fresh.getTotal());
			freshData.setFilepath(fresh.getFilepath());
			freshData.setBin(binTx.getBinNumber());
			freshData.setInsertBy(user.getId());
			freshData.setUpdateBy(user.getId());
			freshData.setInsertTime(fresh.getInsertTime());
			freshData.setUpdateTime(fresh.getUpdateTime());
			freshData.setBinCategoryType(fresh.getBinCategoryType());
			freshData.setPotdarStatus("PENDING");
			freshData.setCurrencyType(binTx.getBinType());
			freshList.add(freshData);
		}
		return freshList;
	}

	public static List<SoiledRemittanceAllocation> getBinForSoiledIndentRequest(List<BinTransaction> txnList,
			int denomination, BigDecimal bundle, User user) {
		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<SoiledRemittanceAllocation> eligibleIndentList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			SoiledRemittanceAllocation soiled = new SoiledRemittanceAllocation();

			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);

			BigDecimal availableBundle = binTx.getReceiveBundle();

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (availableBundle.compareTo(bundleRequired) >= 0) {
				soiled.setBinNumber(binTx.getBinNumber());
				soiled.setBundle(bundleRequired);
				soiled.setDenomination(binTx.getDenomination());
				soiled.setCurrencyType(binTx.getBinType());
				soiled.setInsertBy(user.getId());
				soiled.setUpdateBy(user.getId());
				soiled.setIcmcId(binTx.getIcmcId());
				soiled.setInsertTime(now);
				soiled.setUpdateTime(now);
				eligibleIndentList.add(soiled);
				break;
			} else {
				soiled.setBinNumber(binTx.getBinNumber());
				soiled.setBundle(getSubstarctedBundle(binTx, pendingBundleRequest)); // binTx.getReceiveBundle();
				soiled.setDenomination(binTx.getDenomination());
				soiled.setCurrencyType(binTx.getBinType());
				soiled.setInsertBy(user.getId());
				soiled.setUpdateBy(user.getId());
				soiled.setIcmcId(binTx.getIcmcId());
				soiled.setInsertTime(now);
				soiled.setUpdateTime(now);
				bundleRequired = bundleRequired.subtract(getSubstarctedBundle(binTx, pendingBundleRequest)); // binTx.getReceiveBundle();
				eligibleIndentList.add(soiled);
			}
		}
		return eligibleIndentList;
	}

	public static List<Indent> mapTuppleToIndent(List<Tuple> indentTupleList) {

		List<Indent> indentList = new ArrayList<>();

		for (Tuple tuple : indentTupleList) {
			Indent indent = new Indent();
			indent.setCashSource(tuple.get(0, CashSource.class));
			indent.setDenomination(tuple.get(1, Integer.class));
			indent.setBin(tuple.get(2, String.class));
			indent.setBundle(tuple.get(3, BigDecimal.class));
			// set tuple values in indent
			indentList.add(indent);
		}
		return indentList;
	}

	public static List<Indent> mapTuppleToBranchIndentForMachineAllocation(List<Tuple> tupleList) {

		List<Indent> indentList = new ArrayList<>();

		for (Tuple tuple : tupleList) {
			Indent indent = new Indent();
			indent.setDenomination(tuple.get(0, Integer.class));
			indent.setCashSource(tuple.get(1, CashSource.class));
			indent.setBundle(tuple.get(2, BigDecimal.class));
			indent.setPendingBundleRequest(tuple.get(3, BigDecimal.class));
			indentList.add(indent);
		}
		return indentList;
	}

	/*
	 * public static List<Indent>
	 * getRecordsFromIndentFormachineAllocation(List<Indent> indentList, BigDecimal
	 * issuedBundle, User user) { List<Indent> eligibleIndentList = new
	 * ArrayList<>(); Calendar now = Calendar.getInstance();
	 * 
	 * BigDecimal availableBundle = BigDecimal.ZERO; for (Indent indent :
	 * indentList) { availableBundle = availableBundle.add(indent.getBundle()); } if
	 * (availableBundle.compareTo(issuedBundle) >= 0) { for (Indent indent :
	 * indentList) {
	 * 
	 * if (indent.getBundle().compareTo(issuedBundle) > 0) { issuedBundle =
	 * indent.getBundle().subtract(issuedBundle); indent.setBundle(issuedBundle); if
	 * (issuedBundle.compareTo(BigDecimal.ZERO) == 0) {
	 * indent.setStatus(OtherStatus.ACCEPTED); } else {
	 * indent.setStatus(OtherStatus.PROCESSED); } indent.setUpdateBy(user.getId());
	 * indent.setUpdateTime(now); if (indent.getBundle().compareTo(BigDecimal.ZERO)
	 * <= 0) { indent.setDirty(true); } eligibleIndentList.add(indent); break; }
	 * else if (issuedBundle.compareTo(indent.getBundle()) >= 0) { issuedBundle =
	 * issuedBundle.subtract(indent.getBundle()); indent.setBundle(BigDecimal.ZERO);
	 * indent.setStatus(OtherStatus.ACCEPTED); indent.setUpdateBy(user.getId());
	 * indent.setUpdateTime(now); if (indent.getBundle().compareTo(BigDecimal.ZERO)
	 * <= 0) { indent.setDirty(true); } eligibleIndentList.add(indent); } } } return
	 * eligibleIndentList; }
	 */

	public static List<Indent> getEligibleIndentListForMachineAllocation(List<Indent> indentList,
			BigDecimal issuedBundle, User user) {
		List<Indent> eligibleIndentList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal availableBundle = BigDecimal.ZERO;
		for (Indent indent : indentList) {
			availableBundle = availableBundle.add(indent.getPendingBundleRequest());
		}
		if (availableBundle.compareTo(issuedBundle) >= 0) {
			for (Indent indent : indentList) {
				if (issuedBundle.compareTo(BigDecimal.ZERO) > 0) {
					if (indent.getPendingBundleRequest().compareTo(issuedBundle) >= 0) {
						// issuedBundle =
						// indent.getPendingBundleRequest().subtract(issuedBundle);
						// indent.setPendingBundleRequest(issuedBundle);
						indent.setPendingBundleRequest(indent.getPendingBundleRequest().subtract(issuedBundle));
						if (indent.getPendingBundleRequest().compareTo(BigDecimal.ZERO) == 0) {
							indent.setStatus(OtherStatus.PROCESSED);
							indent.setDescription("PROCESSED");
						}
						indent.setUpdateBy(user.getId());
						indent.setUpdateTime(now);
						eligibleIndentList.add(indent);
						break;
					} else if (indent.getPendingBundleRequest().compareTo(BigDecimal.ZERO) > 0) {
						issuedBundle = issuedBundle.subtract(indent.getPendingBundleRequest());
						indent.setPendingBundleRequest(BigDecimal.ZERO);
						indent.setStatus(OtherStatus.PROCESSED);
						indent.setDescription("PROCESSED");
						indent.setUpdateBy(user.getId());
						indent.setUpdateTime(now);
						eligibleIndentList.add(indent);
					}
				}
			}
		}
		return eligibleIndentList;
	}

	public static List<MachineAllocation> getEligibleBundleListForMachineAllocation(List<MachineAllocation> bundleList,
			BigDecimal issuedBundle, User user) {
		List<MachineAllocation> eligibleBundleList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal availableBundle = BigDecimal.ZERO;
		for (MachineAllocation machineAllocation : bundleList) {
			availableBundle = availableBundle.add(machineAllocation.getPendingBundle());
		}

		if (availableBundle.compareTo(issuedBundle) >= 0) {
			for (MachineAllocation machineAllocation : bundleList) {
				if (issuedBundle.compareTo(BigDecimal.ZERO) > 0) {
					if (machineAllocation.getPendingBundle().compareTo(issuedBundle) >= 0) {
						issuedBundle = machineAllocation.getPendingBundle().subtract(issuedBundle);
						machineAllocation.setPendingBundle(issuedBundle);
						if (machineAllocation.getPendingBundle().compareTo(BigDecimal.ZERO) == 0) {
							machineAllocation.setStatus(OtherStatus.PROCESSED);
						}
						machineAllocation.setUpdateBy(user.getId());
						machineAllocation.setUpdateTime(now);
						eligibleBundleList.add(machineAllocation);
						break;
					} else if (machineAllocation.getPendingBundle().compareTo(BigDecimal.ZERO) > 0) {
						issuedBundle = issuedBundle.subtract(machineAllocation.getPendingBundle());
						machineAllocation.setPendingBundle(BigDecimal.ZERO);
						machineAllocation.setStatus(OtherStatus.PROCESSED);
						machineAllocation.setUpdateBy(user.getId());
						machineAllocation.setUpdateTime(now);
						eligibleBundleList.add(machineAllocation);
					}
				}

			}
		}
		if (availableBundle.compareTo(issuedBundle) >= 0) {
			return eligibleBundleList;
		} else {
			throw new BaseGuiException(
					"Required Bundle is not available, TotalAvailableBundle is:" + availableBundle.toPlainString());
		}

	}

	public static List<AuditorIndent> getEligibleBundleListForAuditor(List<AuditorIndent> bundleList,
			BigDecimal issuedBundle, User user) {
		List<AuditorIndent> eligibleBundleList = new ArrayList<>();
		Calendar now = Calendar.getInstance();

		BigDecimal availableBundle = BigDecimal.ZERO;
		for (AuditorIndent auditorIndent : bundleList) {
			availableBundle = availableBundle.add(auditorIndent.getPendingBundleRequest());
		}

		if (availableBundle.compareTo(issuedBundle) >= 0) {
			for (AuditorIndent auditorIndent : bundleList) {
				if (issuedBundle.compareTo(BigDecimal.ZERO) > 0) {
					if (auditorIndent.getPendingBundleRequest().compareTo(issuedBundle) >= 0) {
						issuedBundle = auditorIndent.getPendingBundleRequest().subtract(issuedBundle);
						auditorIndent.setPendingBundleRequest(issuedBundle);
						if (auditorIndent.getPendingBundleRequest().compareTo(BigDecimal.ZERO) == 0) {
							auditorIndent.setStatus(OtherStatus.PROCESSED);
						}
						auditorIndent.setUpdateBy(user.getId());
						auditorIndent.setUpdateTime(now);
						eligibleBundleList.add(auditorIndent);
						break;
					} else if (auditorIndent.getPendingBundleRequest().compareTo(BigDecimal.ZERO) > 0) {
						issuedBundle = issuedBundle.subtract(auditorIndent.getPendingBundleRequest());
						auditorIndent.setPendingBundleRequest(BigDecimal.ZERO);
						auditorIndent.setStatus(OtherStatus.PROCESSED);
						auditorIndent.setUpdateBy(user.getId());
						auditorIndent.setUpdateTime(now);
						eligibleBundleList.add(auditorIndent);
					}
				}

			}
		}
		if (availableBundle.compareTo(issuedBundle) >= 0) {
			return eligibleBundleList;
		} else {
			throw new BaseGuiException(
					"Required Bundle is not available, TotalAvailableBundle is:" + availableBundle.toPlainString());
		}

	}

	public static BranchReceipt settingBranchReceiptParametersForUpdation(BranchReceipt branchReceipt,
			BranchReceipt branchReceiptDb, User user, Calendar now) {
		branchReceipt.setIcmcId(user.getIcmcId());
		branchReceipt.setDenomination(branchReceiptDb.getDenomination());
		branchReceipt.setCurrencyType(branchReceiptDb.getCurrencyType());
		branchReceipt.setCashSource(branchReceiptDb.getCashSource());
		branchReceipt.setBinCategoryType(branchReceiptDb.getBinCategoryType());
		branchReceipt.setInsertBy(branchReceiptDb.getInsertBy());
		branchReceipt.setProcessedOrUnprocessed(branchReceiptDb.getProcessedOrUnprocessed());
		branchReceipt.setUpdateBy(user.getId());
		branchReceipt.setInsertTime(branchReceiptDb.getInsertTime());
		branchReceipt.setUpdateTime(now);
		return branchReceipt;
	}

	public static BigDecimal addBigDecimal(BigDecimal val1, BigDecimal val2) {
		return val1.add(val2);
	}

	/*
	 * public static BinTransaction setPendingBundleForDorvCancellation(User user,
	 * BinTransaction binTxn, DiversionORVAllocation dorvAllocation) { Calendar now
	 * = Calendar.getInstance();
	 * if(binTxn.getPendingBundleRequest().compareTo(dorvAllocation.getBundle()) >=
	 * 0){ binTxn.setPendingBundleRequest(binTxn.getPendingBundleRequest().subtract(
	 * dorvAllocation.getBundle())); } binTxn.setUpdateBy(user.getId());
	 * binTxn.setUpdateTime(now); return binTxn; }
	 * 
	 * public static BinTransaction setPendingBundleForOtherBankCancellation(User
	 * user, BinTransaction binTxn, OtherBankAllocation otherBankAllocation) {
	 * Calendar now = Calendar.getInstance();
	 * if(binTxn.getPendingBundleRequest().compareTo(otherBankAllocation.
	 * getBundle()) >= 0){
	 * binTxn.setPendingBundleRequest(binTxn.getPendingBundleRequest().subtract(
	 * otherBankAllocation.getBundle())); } binTxn.setUpdateBy(user.getId());
	 * binTxn.setUpdateTime(now); return binTxn; }
	 */
	@Transactional
	public static BinTransaction setPendingBundleForORVCancellation(User user, BinTransaction binTxn,
			BigDecimal cancelledBundles) {
		Calendar now = Calendar.getInstance();
		if (binTxn.getPendingBundleRequest().compareTo(cancelledBundles) >= 0) {
			binTxn.setPendingBundleRequest(binTxn.getPendingBundleRequest().subtract(cancelledBundles));
		}
		binTxn.setUpdateBy(user.getId());
		binTxn.setUpdateTime(now);
		return binTxn;
	}

	public static void PrintToPrinter(StringBuilder sb, User user) throws UnknownHostException, IOException {
		Socket clientSocket = null;
		try {
			String ip = user.getIcmcPrinter().getPrinterIP();
			LOG.info("PrintToPrinter ip " + ip);
			LOG.info("PrintToPrinter user id " + user.getId());
			int port = user.getIcmcPrinter().getPort().intValue();
			LOG.info("PrintToPrinter port " + port);
			clientSocket = new Socket(ip, port);
			LOG.info("PrintToPrinter clientSocket " + clientSocket);
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			outToServer.writeBytes(sb.toString());

		} catch (IOException ioe) {
			LOG.info("PrintToPrinter METHOD IN CATCH IOException Printer is not able to connect " + ioe);
			ioe.printStackTrace();

		} catch (Exception e) {
			LOG.info("PrintToPrinter METHOD IN CATCH Exception Printer is not able to connect " + e);
			e.printStackTrace();

		} finally {
			LOG.info("PrintToPrinter METHOD IN finally clientSocket " + clientSocket);
			if (clientSocket != null)
				clientSocket.close();
		}
	}

	public static List<Mutilated> getMutilatedBin(Mutilated mutilated, List<BinTransaction> binTxs, User user) {
		List<Mutilated> mutilatedList = new ArrayList<>();
		for (BinTransaction binTx : binTxs) {
			Mutilated mutilatedData = new Mutilated();
			mutilatedData.setIcmcId(binTx.getIcmcId());
			// processData.setMachineNo(process.getMachineNo());
			mutilatedData.setCurrencyType(mutilated.getCurrencyType());
			mutilatedData.setDenomination(mutilated.getDenomination());
			BigDecimal rcvBundle = binTx.getCurrentBundle();
			mutilatedData.setBundle(rcvBundle);
			mutilatedData.setBinNumber(binTx.getBinNumber());
			mutilatedData.setInsertBy(user.getId());
			mutilatedData.setUpdateBy(user.getId());
			mutilatedData.setInsertTime(mutilated.getInsertTime());
			mutilatedData.setUpdateTime(mutilated.getUpdateTime());
			mutilatedData.setBinCategoryType(mutilated.getBinCategoryType());
			mutilatedList.add(mutilatedData);
		}
		return mutilatedList;

	}

	public static List<MutilatedIndent> getBoxForMUtilatedIndentRequest(List<BinTransaction> txnList, int denomination,
			BigDecimal bundle, User user, MutilatedIndent MutilatedIndentData)

	{

		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<MutilatedIndent> eligibleIndentList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			MutilatedIndent mutilatedIndent = new MutilatedIndent();

			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);

			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (availableBundle.compareTo(bundleRequired) >= 0) {
				// indent.setBin("BOX");
				mutilatedIndent.setBin(binTx.getBinNumber());
				mutilatedIndent.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				mutilatedIndent.setDenomination(binTx.getDenomination());
				mutilatedIndent.setBinCategoryType(BinCategoryType.BOX);
				mutilatedIndent.setInsertBy(user.getId());
				mutilatedIndent.setUpdateBy(user.getId());
				mutilatedIndent.setIcmcId(binTx.getIcmcId());
				mutilatedIndent.setInsertTime(now);
				mutilatedIndent.setUpdateTime(now);
				mutilatedIndent.setPendingBundleRequest(bundleRequired);
				mutilatedIndent.setStatus(OtherStatus.REQUESTED);
				eligibleIndentList.add(mutilatedIndent);
				break;
			} else {
				// indent.setBin("BOX");
				mutilatedIndent.setBin(binTx.getBinNumber());
				mutilatedIndent.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				mutilatedIndent.setDenomination(binTx.getDenomination());
				mutilatedIndent.setBinCategoryType(BinCategoryType.BOX);
				mutilatedIndent.setInsertBy(user.getId());
				mutilatedIndent.setUpdateBy(user.getId());
				mutilatedIndent.setIcmcId(binTx.getIcmcId());
				mutilatedIndent.setPendingBundleRequest(availableBundle);
				mutilatedIndent.setInsertTime(now);
				mutilatedIndent.setUpdateTime(now);
				mutilatedIndent.setStatus(OtherStatus.REQUESTED);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleIndentList.add(mutilatedIndent);
			}
		}
		return eligibleIndentList;
	}

	public static List<MutilatedIndent> getBinForMutilatedIndentRequest(List<BinTransaction> txnList, int denomination,
			BigDecimal bundle, User user, MutilatedIndent mutilatedIndentData) {

		Calendar now = Calendar.getInstance();
		BigDecimal bundleRequired = bundle;
		List<MutilatedIndent> eligibleIndentList = new ArrayList<>();
		for (BinTransaction binTx : txnList) {
			MutilatedIndent mutilatedIndent = new MutilatedIndent();

			BigDecimal pendingBundleRequest = getPendingBundleRequest(binTx);

			BigDecimal availableBundle = getSubstarctedBundle(binTx, pendingBundleRequest);

			if (availableBundle.doubleValue() <= 0) {
				continue;
			}

			if (availableBundle.compareTo(bundleRequired) >= 0) {
				mutilatedIndent.setBin(binTx.getBinNumber());
				mutilatedIndent.setBundle(bundleRequired);
				calculateAndSetPendingBundleRequest(bundleRequired, binTx);
				mutilatedIndent.setDenomination(binTx.getDenomination());
				mutilatedIndent.setInsertBy(user.getId());
				mutilatedIndent.setBinCategoryType(BinCategoryType.BIN);
				mutilatedIndent.setPendingBundleRequest(bundleRequired);
				mutilatedIndent.setUpdateBy(user.getId());
				mutilatedIndent.setIcmcId(binTx.getIcmcId());
				mutilatedIndent.setInsertTime(now);
				mutilatedIndent.setUpdateTime(now);
				mutilatedIndent.setStatus(OtherStatus.REQUESTED);
				eligibleIndentList.add(mutilatedIndent);
				break;
			} else if (availableBundle.compareTo(BigDecimal.ZERO) > 0) {
				mutilatedIndent.setBin(binTx.getBinNumber());
				mutilatedIndent.setBundle(availableBundle);
				calculateAndSetPendingBundleRequest(availableBundle, binTx);
				mutilatedIndent.setDenomination(binTx.getDenomination());
				mutilatedIndent.setInsertBy(user.getId());
				mutilatedIndent.setBinCategoryType(BinCategoryType.BIN);
				mutilatedIndent.setUpdateBy(user.getId());
				mutilatedIndent.setIcmcId(binTx.getIcmcId());
				mutilatedIndent.setInsertTime(now);
				mutilatedIndent.setPendingBundleRequest(availableBundle);
				mutilatedIndent.setUpdateTime(now);
				mutilatedIndent.setStatus(OtherStatus.REQUESTED);
				bundleRequired = bundleRequired.subtract(availableBundle);
				eligibleIndentList.add(mutilatedIndent);
			}
		}
		return eligibleIndentList;
	}

	public File discrepancyImagePath() {

		String folderPath = "/home/inayat/image";
		File file = new File(folderPath);
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int date1 = now.get(Calendar.DATE);

		file = new File(folderPath + "/" + year + "/" + "/" + month + "/" + date1);

		if (!file.exists()) {
			if (file.mkdirs()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}

		return file;
	}

	public static String getImages(String src) throws IOException {

		/*
		 * String folderPath="/home/inayat/image"; File file = new File(folderPath);
		 * 
		 * int indexname = src.lastIndexOf("/");
		 * 
		 * if (indexname == src.length()) { src = src.substring(1, indexname); }
		 * 
		 * indexname = src.lastIndexOf("/"); String name = src.substring(indexname,
		 * src.length()); // downloadImage=name[0]; //System.out.println("name===" +
		 * name);
		 * 
		 * URL url = new URL(src); InputStream in = url.openStream();
		 * 
		 * OutputStream out = new BufferedOutputStream(new FileOutputStream(file +
		 * name));
		 * 
		 * for (int b; (b = in.read()) != -1;) { out.write(b); } out.close();
		 * in.close();
		 */

		String folderPath = "/home/rakesh/image";
		URL url = new URL(src);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(folderPath);
		byte[] b = new byte[2048];
		while (is.read(b) != -1) {
			os.write(b);
		}
		is.close();
		os.close();
		return src;

	}

	public static List<CustodianKeySet> mapTuppleToCustodian(List<Tuple> indentTupleList) {

		List<CustodianKeySet> custodianList = new ArrayList<>();
		for (Tuple tuple : indentTupleList) {
			CustodianKeySet custodianKeySet = new CustodianKeySet();
			custodianKeySet.setCustodian(tuple.get(0, String.class));
			custodianKeySet.setIcmcId(tuple.get(1, BigInteger.class));

			custodianList.add(custodianKeySet);
		}
		return custodianList;
	}

}
