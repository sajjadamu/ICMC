/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.util;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.chest.currency.entity.model.User;
import com.chest.currency.viewBean.BinMaster;
import com.chest.currency.viewBean.BinTransaction;
import com.chest.currency.viewBean.BranchBean;
import com.chest.currency.viewBean.CITCRADriver;
import com.chest.currency.viewBean.CITCRAVehicle;
import com.chest.currency.viewBean.CITCRAVendor;
import com.chest.currency.viewBean.DSBBean;
import com.chest.currency.viewBean.DirvBean;
import com.chest.currency.viewBean.FreshBean;
import com.chest.currency.viewBean.HolidayMaster;
import com.chest.currency.viewBean.IndentRequestBean;
import com.chest.currency.viewBean.Jurisdiction;
import com.chest.currency.viewBean.MachineAllocationBean;
import com.chest.currency.viewBean.ProcessBean;
import com.chest.currency.viewBean.SASBean;
import com.chest.currency.viewBean.ShrinkBean;
import com.chest.currency.viewBean.UserBean;

public class Utility {
	
	public static String getRoles(String url){
		String a = "ITG,SUPERVISOR";
		return a;
	}
	public static List<BinTransaction> getBinForUnprocess(List<BinTransaction> binTxs, List<BinMaster> binMasters,
			double bundle, boolean isBundle) {

		double bundleRequired = bundle;
		if (!isBundle) {
			bundleRequired = bundleRequired / 10;
		}
		List<BinTransaction> alloccateTxs = new ArrayList<BinTransaction>();
		boolean isFound = false;

		if (binTxs != null && binTxs.size() > 0) {
			for (BinTransaction binTx : binTxs) {
				double availableSpace = binTx.getMaxCapacity() - binTx.getReceiveBundles();
				if (availableSpace >= bundleRequired) {
					binTx.setReceiveBundles(bundleRequired + binTx.getReceiveBundles());
					binTx.setCurrentBundles(bundleRequired);
					alloccateTxs.add(binTx);
					isFound = true;
					break;
				} else {
					binTx.setReceiveBundles(availableSpace + binTx.getReceiveBundles());
					binTx.setCurrentBundles(availableSpace);
					alloccateTxs.add(binTx);
					bundleRequired = bundleRequired - availableSpace;
				}
			}
		}

		if (!isFound) {
			if (binMasters != null && binMasters.size() > 0) {
				for (BinMaster binMaster : binMasters) {
					int availableSpace = binMaster.getMaxBundleCapacity();
					if (availableSpace >= bundleRequired) {
						BinTransaction binTx = new BinTransaction();
						binTx.setBinNumber(binMaster.getBinNumber());
						binTx.setDenomination(binMaster.getDenom());
						binTx.setMaxCapacity(binMaster.getMaxBundleCapacity());
						binTx.setBinType(binMaster.getFirstPriority());
						binTx.setReceiveBundles(bundleRequired);
						binTx.setCurrentBundles(bundleRequired);
						binTx.setInsertBy(binMaster.getInsertBy());
						binTx.setUpdateBy(binMaster.getUpdateBy());
						binTx.setICMCId(binMaster.getICMCId());
						alloccateTxs.add(binTx);
						isFound = true;
						break;
					} else {
						BinTransaction binTx = new BinTransaction();
						binTx.setBinNumber(binMaster.getBinNumber());
						binTx.setDenomination(binMaster.getDenom());
						binTx.setMaxCapacity(binMaster.getMaxBundleCapacity());
						binTx.setBinType(binMaster.getFirstPriority());
						binTx.setReceiveBundles(availableSpace);
						binTx.setCurrentBundles(availableSpace);
						binTx.setInsertBy(binMaster.getInsertBy());
						binTx.setUpdateBy(binMaster.getUpdateBy());
						binTx.setICMCId(binMaster.getICMCId());
						alloccateTxs.add(binTx);
						bundleRequired = bundleRequired - availableSpace;
					}
				}
			}
		}
		return alloccateTxs;
	}

	public static List<ShrinkBean> getShrinkBean(int solid, String branch, int denomination, double bundle,
			List<BinTransaction> binTxs, int total, String insertBy, String updateBy,int ICMCId) {

		List<ShrinkBean> shrinkBeanList = new ArrayList<ShrinkBean>();
		for (BinTransaction binTx : binTxs) {
			ShrinkBean shrinkBean = new ShrinkBean();
			shrinkBean.setSolId(solid);
			shrinkBean.setBranch(branch);
			shrinkBean.setDenomination(denomination);
			double rcvBundle = binTx.getCurrentBundles();
			shrinkBean.setBundle((int) rcvBundle);
			shrinkBean.setBin(binTx.getBinNumber());
			double totalVal = rcvBundle * denomination * 1000;
			shrinkBean.setTotal((int) totalVal);
			shrinkBean.setInsertBy(insertBy);
			shrinkBean.setUpdateBy(updateBy);
			shrinkBean.setICMCId(ICMCId);
			shrinkBeanList.add(shrinkBean);
		}
		return shrinkBeanList;
	}

	public static List<DSBBean> getDSBBean(String name, int denomination, double bundle, List<BinTransaction> binTxs,
			int total, String processinORVault, String insertBy, String updateBy,int ICMCId) {

		List<DSBBean> DSBBeanList = new ArrayList<DSBBean>();
		for (BinTransaction binTx : binTxs) {
			DSBBean dsbBean = new DSBBean();
			dsbBean.setName(name);
			dsbBean.setDenomination(denomination);
			double rcvBundle = binTx.getCurrentBundles();
			dsbBean.setBundle((int) rcvBundle);
			dsbBean.setBin(binTx.getBinNumber());
			double totalVal = rcvBundle * denomination;
			dsbBean.setTotal((int) totalVal);
			dsbBean.setProcessinORVault(processinORVault);
			dsbBean.setInsertBy(insertBy);
			dsbBean.setUpdateBy(updateBy);
			dsbBean.setIcmcId(ICMCId);
			DSBBeanList.add(dsbBean);
		}
		return DSBBeanList;
	}

	public static List<DirvBean> getDirvBean(String orderDate, String rbiOrderNo, String expiryDate, String bankName,
			int denomination, double bundle, String approvedCC, String location, String category,
			List<BinTransaction> binTxs, int total, String insertBy, String updateBy,int ICMCId) {

		List<DirvBean> DirvBeanList = new ArrayList<DirvBean>();
		for (BinTransaction binTx : binTxs) {
			DirvBean dirvBean = new DirvBean();
			dirvBean.setOrder_date(orderDate);
			dirvBean.setRbi_order_no(rbiOrderNo);
			dirvBean.setExpiry_date(expiryDate);
			dirvBean.setBankName(bankName);
			dirvBean.setDenomination(denomination);
			double rcvBundle = binTx.getCurrentBundles();
			dirvBean.setBundle((int) rcvBundle);
			dirvBean.setBinNumber(binTx.getBinNumber());
			double totalVal = rcvBundle * denomination;
			dirvBean.setTotal((int) totalVal);
			dirvBean.setCategory(category);
			dirvBean.setApprovedCC(approvedCC);
			dirvBean.setLocation(location);
			dirvBean.setInsertBy(insertBy);
			dirvBean.setUpdateBy(updateBy);
			dirvBean.setIcmcId(ICMCId);
			DirvBeanList.add(dirvBean);
		}
		return DirvBeanList;
	}

	public static List<FreshBean> getRBIBean(Date orderDate, String rbiOrderNo, String vehicleNo, String potdarName,
			int denomination, double bundle, String escortOfficer, String notesAndCoins, List<BinTransaction> binTxs,
			int total, String insertBy, String updateBy,int ICMCId) {

		List<FreshBean> freshBeanList = new ArrayList<FreshBean>();
		for (BinTransaction binTx : binTxs) {
			FreshBean freshBean = new FreshBean();
			freshBean.setOrder_date(orderDate);
			freshBean.setRbiOrderNo(rbiOrderNo);
			freshBean.setDenomination(denomination);
			double rcvBundle = binTx.getCurrentBundles();
			freshBean.setBundle((int) rcvBundle);
			freshBean.setBin(binTx.getBinNumber());
			double totalVal = rcvBundle * denomination;
			freshBean.setTotal((int) totalVal);
			freshBean.setEscort_officer_name(escortOfficer);
			freshBean.setNotesOrCoins(notesAndCoins);
			freshBean.setPotdarName(potdarName);
			freshBean.setVehicleNumber(vehicleNo);
			freshBean.setInsertBy(insertBy);
			freshBean.setUpdateBy(updateBy);
			freshBean.setIcmcId(ICMCId);
			freshBeanList.add(freshBean);
		}
		return freshBeanList;
	}

	public static List<ProcessBean> getProcessBean(Integer machineNo, String type, int denomination, Double bundle,
			List<BinTransaction> binTxs, int total, String insertBy, String updateBy) {
		List<ProcessBean> processList = new ArrayList<ProcessBean>();
		for (BinTransaction binTx : binTxs) {
			ProcessBean processBean = new ProcessBean();
			processBean.setMachineNo(machineNo);
			processBean.setType(type);
			processBean.setDenomination(denomination);
			double rcvBundle = binTx.getCurrentBundles();
			processBean.setBundle(rcvBundle);
			processBean.setBin(binTx.getBinNumber());
			double totalVal = rcvBundle * denomination;
			processBean.setTotal((int) totalVal);
			processBean.setInsertBy(insertBy);
			processBean.setUpdateBy(updateBy);
			processList.add(processBean);
		}
		return processList;
	}

	public static DSBBean getDSBBean(String name, int denomination, double bundle, int total, String processinORVault,
			String insertBy, String updateBy,int ICMCId) {
		DSBBean bean = new DSBBean();
		bean.setName(name);
		bean.setDenomination(denomination);
		bean.setBundle(bundle);
		bean.setTotal(total);
		bean.setProcessinORVault(processinORVault);
		bean.setInsertBy(insertBy);
		bean.setUpdateBy(updateBy);
		bean.setIcmcId(ICMCId);
		return bean;
	}

	public static IndentRequestBean getIndentBean(int denomination, double bundle, String insertBy, String updateBy) {
		IndentRequestBean indentBean = new IndentRequestBean();
		indentBean.setDenomination(denomination);
		indentBean.setBundle(bundle);
		indentBean.setInsertBy(insertBy);
		indentBean.setUpdateBy(updateBy);
		return indentBean;

	}

	public static MachineAllocationBean insertInMachineAllocation(int denomination, double bundle, int machine,
			double issuedBundle, String insertBy, String updateBy) {
		MachineAllocationBean machineBean = new MachineAllocationBean();
		machineBean.setDenomination(denomination);
		machineBean.setBundle(bundle);
		machineBean.setMachine(machine);
		machineBean.setIssued_bundle(issuedBundle);
		machineBean.setInsertBy(insertBy);
		machineBean.setUpdateBy(updateBy);
		return machineBean;
	}

	public static IndentRequestBean prepareIndentRequestBean(Double bundle, int id) {
		IndentRequestBean indentBean = new IndentRequestBean();
		indentBean.setBundle(bundle);
		indentBean.setId(id);
		return indentBean;
	}

	public static List<IndentRequestBean> getBinForIndentRequest(List<BinTransaction> txnList,
			List<IndentRequestBean> indentList, int denomination, double bundle) {

		Iterator<BinTransaction> txItr = txnList.iterator();
		while (txItr.hasNext()) {
			BinTransaction binTx = txItr.next();
			boolean isExist = isExistBinInIndentRequestList(binTx, indentList);
			if (isExist) {
				txItr.remove();
			}
		}

		double bundleRequired = bundle;
		List<IndentRequestBean> eligibleIndentList = new ArrayList<IndentRequestBean>();
		for (BinTransaction binTx : txnList) {
			IndentRequestBean indentRequestBean = new IndentRequestBean();
			double availableSpace = binTx.getReceiveBundles() - bundleRequired;
			if (availableSpace >= bundleRequired) {
				indentRequestBean.setBin(binTx.getBinNumber());
				indentRequestBean.setBundle(bundleRequired);
				indentRequestBean.setDenomination(binTx.getDenomination());
				eligibleIndentList.add(indentRequestBean);
				break;
			} else {
				indentRequestBean.setBin(binTx.getBinNumber());
				indentRequestBean.setBundle(binTx.getReceiveBundles());
				indentRequestBean.setDenomination(binTx.getDenomination());
				bundleRequired = bundleRequired - binTx.getReceiveBundles();
				eligibleIndentList.add(indentRequestBean);
			}
		}

		return eligibleIndentList;
	}

	private static boolean isExistBinInIndentRequestList(BinTransaction binTx, List<IndentRequestBean> indentList) {

		for (IndentRequestBean indent : indentList) {
			if (binTx.getBinNumber().equalsIgnoreCase(indent.getBin())) {
				return true;
			}
		}
		return false;
	}

	public static String getBinNameForQRCode(List<BinTransaction> binTxs) {
		StringBuilder builder = new StringBuilder();
		for (BinTransaction binTx : binTxs) {
			builder.append(binTx.getBinNumber()).append(",");
		}
		return builder.toString();
	}

	public static BinMaster mapperBinMaster(ResultSet resultSet) throws SQLException {
		BinMaster binMaster;
		binMaster = new BinMaster();
		binMaster.setId(resultSet.getInt("id"));
		binMaster.setBinNumber(resultSet.getString("bin_num"));
		binMaster.setMaxBundleCapacity(resultSet.getInt("max_bundle_capacity"));
		binMaster.setFirstPriority(resultSet.getString("first_priority"));
		binMaster.setLocationPriority(resultSet.getInt("loca_priority"));
		binMaster.setDenom(resultSet.getInt("denomination"));
		return binMaster;
	}

	public static BinTransaction mapperBinTransaction(ResultSet resultSet) throws SQLException {
		BinTransaction binData;
		binData = new BinTransaction();
		binData.setId(resultSet.getInt("id"));
		binData.setBinNumber(resultSet.getString("bin_num"));
		binData.setDenomination(resultSet.getInt("denomination"));
		binData.setMaxCapacity(resultSet.getInt("max_capacity"));
		binData.setInsertTime(resultSet.getTimestamp("insert_time"));
		binData.setReceiveBundles(resultSet.getInt("receive_bundle"));
		binData.setBinType(resultSet.getString("bin_type"));
		binData.setInsertBy(resultSet.getString("insert_by"));
		binData.setUpdateTime(resultSet.getTimestamp("update_time"));
		binData.setUpdateBy(resultSet.getString("update_by"));
		return binData;
	}

	public static IndentRequestBean mapperIndentRequest(ResultSet resultSet) throws SQLException {
		IndentRequestBean indentData;
		indentData = new IndentRequestBean();
		indentData.setId(resultSet.getInt("id"));
		indentData.setDenomination(resultSet.getInt("denomination"));
		indentData.setBundle(resultSet.getDouble("bundle"));
		indentData.setStatus(resultSet.getInt("status"));
		indentData.setBin(resultSet.getString("bin_num"));
		indentData.setDescription(resultSet.getString("description"));
		indentData.setInsertTime(resultSet.getTimestamp("insert_time"));
		indentData.setUpdateTime(resultSet.getTimestamp("update_time"));
		indentData.setInsertBy(resultSet.getString("insert_by"));
		indentData.setUpdateBy(resultSet.getString("update_by"));
		return indentData;
	}
	
	private static HolidayMaster mapToHolidayMaster(String[] splitData) {
		HolidayMaster holiday = new HolidayMaster();
		holiday.setHolidayName(getValue(splitData,0));
		holiday.setHolidayType(getValue(splitData,1));
		holiday.setHolidayDate(getValue(splitData,2));
		holiday.setAndaman(getValue(splitData,3));
		holiday.setAndhra(getValue(splitData,4));
		holiday.setArunachal(getValue(splitData,5));
		holiday.setAssam(getValue(splitData,6));
		holiday.setBihar(getValue(splitData,7));
		holiday.setChandigarh(getValue(splitData,8));
		holiday.setChhattisgarh(getValue(splitData,9));
		holiday.setDamanAndDiu(getValue(splitData,10));
		holiday.setDelhi(getValue(splitData,11));
		holiday.setGoa(getValue(splitData,12));
		holiday.setGujarat(getValue(splitData,13));
		holiday.setHimachal(getValue(splitData,14));
		holiday.setHaryana(getValue(splitData,15));
		holiday.setJharkhand(getValue(splitData,16));
		holiday.setJammuAndKashmir(getValue(splitData,17));
		holiday.setKarnataka(getValue(splitData,18));
		holiday.setKerala(getValue(splitData,19));
		holiday.setMaharashtra(getValue(splitData,20));
		holiday.setMeghalaya(getValue(splitData,21));
		holiday.setManipur(getValue(splitData,22));
		holiday.setMadhyaPradesh(getValue(splitData,23));
		holiday.setMizoram(getValue(splitData,24));
		holiday.setNagaland(getValue(splitData,25));
		holiday.setOrissa(getValue(splitData,26));
		holiday.setPunjab(getValue(splitData,27));
		holiday.setPondichhery(getValue(splitData,28));
		holiday.setRajasthan(getValue(splitData,29));
		holiday.setSikkim(getValue(splitData,30));
		holiday.setTamilNadu(getValue(splitData,31));
		holiday.setTripura(getValue(splitData,32));
		holiday.setUttarPradesh(getValue(splitData,33));
		holiday.setUttaranchal(getValue(splitData,34));
		holiday.setWestBengal(getValue(splitData,35));
		holiday.setDadarAndNagarHaveli(getValue(splitData,36));
		holiday.setLakshadweep(getValue(splitData,37));
		holiday.setTelangana(getValue(splitData,38));
		return holiday;
	}

	private static String getValue(String[] splitData, int i) {
		
		if(splitData != null && splitData.length > i){
			return splitData[i];
		}
		return "";
	}

	// Utility which converts CSV to LinkedList using Split Operation
	public static List<HolidayMaster> CSVtoArrayListForHoliday(List<String> holidayRecords) {
		List<HolidayMaster> holidayMasterList = new LinkedList<>();
		for (String str : holidayRecords) {
			String[] splitData = str.split(",");
			HolidayMaster holiday = mapToHolidayMaster(splitData);
			holidayMasterList.add(holiday);
		}
		return holidayMasterList;
	}
	
	public static List<SASBean> CSVtoArrayList(List<String> sasRecords) {
		List<SASBean> sasBeanList = new LinkedList<>();
		for (String str : sasRecords) {
			String[] splitData = str.split(",");
			SASBean sASBean = mapToSasBean(splitData);
			sasBeanList.add(sASBean);
		}
		return sasBeanList;
	}

	/*public static List<ICMC> CSVtoArrayListForICMC(List<String> icmcRecords) {
		List<ICMC> icmcList = new LinkedList<>();
		for (String str : icmcRecords) {
			String[] splitData = str.split(",");
			ICMC icmcBean = mapToICMCBean(splitData);
			icmcList.add(icmcBean);
		}
		return icmcList;
	}*/

	private static SASBean mapToSasBean(String[] splitData) {
		SASBean sasBean = new SASBean();
		sasBean.setSRno(splitData[0]);
		sasBean.setCreateDate(splitData[1]);
		sasBean.setNotes(splitData[9]);
		sasBean.setBranchEcName(splitData[28]);
		sasBean.setDateOfRequirements(splitData[29]);
		sasBean.setSrOriginatingBranchName(splitData[30]);
		sasBean.setTotalIndentedValue(Integer.parseInt(splitData[31]));
		sasBean.setTotalValueOfCoinsRs1(Integer.parseInt(splitData[32]));
		sasBean.setTotalValueOfCoinsRs10(Integer.parseInt(splitData[33]));
		sasBean.setTotalValueOfCoinsRs2(Integer.parseInt(splitData[34]));
		sasBean.setTotalValueOfCoinsRs5(Integer.parseInt(splitData[35]));
		sasBean.setTotalValueOfNotesRs1_I((Integer.parseInt(splitData[36]) / 1) / 1000);
		sasBean.setTotalValueOfNotesRs10_I((Integer.parseInt(splitData[37]) / 10) / 1000);
		sasBean.setTotalValueOfNotesRs100_I((Integer.parseInt(splitData[38]) / 100) / 1000);
		sasBean.setTotalValueOfNotesRs1000_I((Integer.parseInt(splitData[39]) / 1000) / 1000);
		sasBean.setTotalValueOfNotesRs2_I((Integer.parseInt(splitData[40]) / 2) / 1000);
		sasBean.setTotalValueOfNotesRs20_I((Integer.parseInt(splitData[41]) / 20) / 1000);
		sasBean.setTotalValueOfNotesRs5_I((Integer.parseInt(splitData[42]) / 5) / 1000);
		sasBean.setTotalValueOfNotesRs50_I((Integer.parseInt(splitData[43]) / 50) / 1000);
		sasBean.setTotalValueOfNotesRs500_I((Integer.parseInt(splitData[44]) / 500) / 1000);
		sasBean.setActiontaken(splitData[45]);
		sasBean.setDateOfDispatch(splitData[46]);
		return sasBean;
	}

	/*private static ICMC mapToICMCBean(String[] splitData) {
		ICMC icmc = new ICMC();
		icmc.setName(splitData[0]);
		icmc.setZone(splitData[1]);
		icmc.setRegion(splitData[2]);
		icmc.setAddress(splitData[3]);
		icmc.setLocation(splitData[4]);
		icmc.setCity(splitData[5]);
		icmc.setPincode(splitData[6]);
		return icmc;
	}*/

	public static SASBean updateSAS(int notes1F, int notes1I, int notes10F, int notes10I, int notes100F, int notes100I,
			int notes1000F, int notes1000I, int notes2F, int notes2I, int notes20F, int notes20I, int notes5F,
			int notes5I, int notes50F, int notes50I, int notes500F, int notes500I, int id) {
		SASBean sasBean = new SASBean();
		sasBean.setTotalValueOfNotesRs1_F(notes1F);
		sasBean.setTotalValueOfNotesRs1_I(notes1I);
		sasBean.setTotalValueOfNotesRs10_F(notes10F);
		sasBean.setTotalValueOfNotesRs10_I(notes10I);
		sasBean.setTotalValueOfNotesRs100_F(notes100F);
		sasBean.setTotalValueOfNotesRs100_I(notes100I);
		sasBean.setTotalValueOfNotesRs1000_F(notes1000F);
		sasBean.setTotalValueOfNotesRs1000_I(notes1000I);
		sasBean.setTotalValueOfNotesRs2_F(notes2F);
		sasBean.setTotalValueOfNotesRs2_I(notes2I);
		sasBean.setTotalValueOfNotesRs20_F(notes20F);
		sasBean.setTotalValueOfNotesRs20_I(notes20I);
		sasBean.setTotalValueOfNotesRs5_F(notes5F);
		sasBean.setTotalValueOfNotesRs5_I(notes5I);
		sasBean.setTotalValueOfNotesRs50_F(notes50F);
		sasBean.setTotalValueOfNotesRs50_I(notes50I);
		sasBean.setTotalValueOfNotesRs500_F(notes500F);
		sasBean.setTotalValueOfNotesRs500_I(notes500I);
		sasBean.setId(id);
		return sasBean;
	}

	private static CITCRAVendor mapToCICRAVendorBean(String[] splitData) {
		CITCRAVendor vendor = new CITCRAVendor();
		vendor.setName(splitData[0]);
		vendor.setTypeOne(splitData[1]);
		vendor.setTypeTwo(splitData[2]);
		vendor.setTypeThree(splitData[3]);
		vendor.setFPRName(splitData[4]);
		vendor.setFPRNumber(splitData[5]);
		
		//set icmc name in a array
		return vendor;
	}

	public static List<CITCRAVendor> CSVtoArrayListForCITCRAVendor(List<String> vendorRecords) {
		List<CITCRAVendor> vendorList = new LinkedList<>();
		for (String str : vendorRecords) {
			String[] splitData = str.split(",");
			CITCRAVendor icmcBean = mapToCICRAVendorBean(splitData);
			vendorList.add(icmcBean);
		}
		return vendorList;
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
			// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vehicle.setFitnessExpiryDate(date2);

		String pollution = splitData[6];
		Date date3 = null;
		try {
			date3 = formatter.parse(pollution);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		vehicle.setPollutionExpiryDate(date3);

		String permit = splitData[6];
		Date date4 = null;
		try {
			date4 = formatter.parse(permit);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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

	private static BranchBean mapToBranchBean(String[] splitData) {
		BranchBean branch = new BranchBean();
		branch.setBranch(getValue(splitData,0));
		branch.setSolId(Integer.parseInt(getValue(splitData,1)));
		branch.setAddress(getValue(splitData,2));
		branch.setLocation(getValue(splitData,3));
		branch.setCity(getValue(splitData,4));
		branch.setPincode(Integer.parseInt(getValue(splitData,5)));
		return branch;
	}

	public static List<BranchBean> CSVtoArrayListForBranch(List<String> branchRecords) {
		List<BranchBean> branchList = new LinkedList<>();
		for (String str : branchRecords) {
			String[] splitData = str.split(",");
			BranchBean branchBean = mapToBranchBean(splitData);
			branchList.add(branchBean);
		}
		return branchList;
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

	/*private static ServicingBranch mapToservicingBranch(String[] splitData) {
		ServicingBranch servicing = new ServicingBranch();
		servicing.setIcmcName(splitData[0]);
		servicing.setSolId(Integer.parseInt(splitData[1]));
		servicing.setBranchName(splitData[2]);
		servicing.setRbiJI(splitData[3]);
		servicing.setRbiSI(splitData[4]);
		servicing.setCategory(splitData[5]);
		servicing.setRBIicmc(splitData[6]);
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
	}*/

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.setLicenseIssuedDated(date1);

		String licenseExpiryDate = splitData[6];
		Date date2 = null;
		try {
			date2 = formatter.parse(licenseExpiryDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
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
	
	public static  User getUserFromSession(HttpSession session) {
		UserBean user = (UserBean) session.getAttribute("login");
		
		User userTemp = new User();
		userTemp.setId(user.getUserId());
		userTemp.setIcmcId(BigInteger.valueOf(user.getIcmcId()));
		return userTemp;
	}

}
