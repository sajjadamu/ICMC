/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.dao.CITCRADaoImpl;
import com.chest.currency.entity.model.CITCRADriver;
import com.chest.currency.entity.model.CITCRAVehicle;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.jpa.dao.CITCRAJpaDao;

@Service
@Transactional
public class CITCRAServiceImpl implements CITCRAService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CITCRAServiceImpl.class);
	
	@Autowired
	CITCRAJpaDao citCraJpaDao;
	
	@Autowired
	CITCRADaoImpl citCraDao;

	@Override
	public boolean addCitCraVendor(CITCRAVendor citcraVendor) {
		boolean isSaved = citCraJpaDao.addCitCraVendor(citcraVendor);
		LOG.info("CIT CRA VENDOR SAVED");
		return isSaved;
	}
	
	@Override
	public List<ICMC> getICMCName(String region) {
		List<ICMC> icmcList = citCraJpaDao.getICMCName(region);
		return icmcList;
	}
	
	@Override
	@Transactional
	public boolean UploadCITCRAVendor(List<CITCRAVendor> vendorList, CITCRAVendor vendor) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.UploadCITCRAVendor(vendorList, vendor);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}
	
	/*@Override
	public boolean UploadCITCRAVendor(List<CITCRAVendor> vendorList) {
		
		//get ICMC Records and store in a HashMap with Key:ICMC Name and Value as a ICMC Record
		boolean isAllSuccess = false;
		Map<String, ICMC> map = new HashMap<>();
		List<ICMC> icmcList = citCraJpaDao.getICMCName();
		for(ICMC icmc : icmcList)
		{
			map.put(icmc.getName(), icmc);
		}
		isAllSuccess = citCraJpaDao.UploadCITCRAVendor(vendorList,map);
		if (isAllSuccess) {
			throw new RuntimeException("Error while CSV File Uploading");
		}
		return isAllSuccess;
	}*/
	
	/*@Override
	public List<ICMC> getICMCName() {
		List<ICMC> icmcList=citCraJpaDao.getICMCName();
		return icmcList;
	}*/
	
	@Override
	public CITCRAVendor vendorRecordForModify(Long idCitCraVendor) {
		return citCraJpaDao.vendorRecordForModify(idCitCraVendor);
	}

	@Override
	public List<CITCRAVendor> getCITCRAVendor() {
		List<CITCRAVendor> citCraList = citCraJpaDao.getCITCRAVendor();
		LOG.info("Fetched CIT_CRA_VENDOR List..");
		return citCraList;
	}

	@Override
	public List<ZoneMaster> getRegionList(CITCRAVendor vendor) {
		List<ZoneMaster> regionList=citCraJpaDao.getRegionList(vendor);
		return regionList;
	}
	
	@Override
	public List<ICMC>  getIcmcList(CITCRAVendor vendor){
		List<ICMC> icmcList=citCraJpaDao.getIcmcList(vendor);
		return icmcList;
	}
	
	@Override
	public void updateCITCRAVendor(CITCRAVendor vendor) {
		citCraJpaDao.updateCITCRAVendor(vendor);
		LOG.info("CIT CRA VENDOR UPDATED");
	}
	
	@Override
	public CITCRAVendor getCitCraVendorById(Long id) {
		return citCraJpaDao.getCitCraVendorById(id);
	}

	@Override
	public boolean deleteCITCRAVendor(CITCRAVendor vendor) {
		boolean isSaved = citCraJpaDao.deleteCITCRAVendor(vendor);
		LOG.info("CIT CRA Vendor Removed");
		return isSaved;
	}
	
	@Override
	public List<CITCRAVehicle> getVehicleDetails() {
		List<CITCRAVehicle> vehcileList = citCraJpaDao.getVehicleDetails();
		return vehcileList;
	}
	
	@Override
	public boolean addVehicle(CITCRAVehicle vehicle) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.addVehicle(vehicle);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}
	
	@Override
	@Transactional
	public boolean UploadCITCRAVehicle(List<CITCRAVehicle> vehicleList, CITCRAVehicle vehicle) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.UploadCITCRAVehicle(vehicleList, vehicle);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}
	
	@Override
	public List<CITCRAVendor> getVendorNameAccordingToICMC(String regionId) {
		List<CITCRAVendor> vendorList = citCraJpaDao.getVendorNameAccordingToICMC(regionId);
		return vendorList;
	}
	
	@Override
	public CITCRAVehicle vehicleRecordForModify(Long id) {
		return citCraJpaDao.vehicleRecordForModify(id);
	}
	
	@Override
	public boolean updateCITCRAVehicle(CITCRAVehicle vehicle) {
		boolean isSaved = citCraJpaDao.updateCITCRAVehicle(vehicle);
		LOG.info("CIT CRA VEHICLE UPDATED");
		return isSaved;
	}
	
	@Override
	public CITCRAVehicle vehicleRecordForRemove(Long id) {
		return citCraJpaDao.vehicleRecordForRemove(id);
	}
	
	@Override
	public boolean deleteCITCRAVehicle(CITCRAVehicle vehicle) {
		boolean isSaved = citCraJpaDao.deleteCITCRAVehicle(vehicle);
		LOG.info("CIT CRA VEHICLE UPDATE");
		return isSaved;
	}

	@Override
	public List<CITCRADriver> getVehicleDriver() {
		List<CITCRADriver> driverList = citCraJpaDao.getVehicleDriverDetails();
		return driverList;
	}
	
	@Override
	public boolean addDriver(CITCRADriver driver) {
		boolean isSaved = citCraJpaDao.addDriver(driver);
		LOG.info("CIT CRA DRIVER SAVED");
		return isSaved;
	}
	
	@Override
	public boolean UploadCITCRADriver(List<CITCRADriver> driverList, CITCRADriver driver) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = citCraJpaDao.UploadCITCRADriver(driverList, driver);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}
	
	@Override
	public List<CITCRAVehicle> getVehicleNumberAccordingToVendorForDriver(String vendor) {
		List<CITCRAVehicle> vehicleNumber=citCraJpaDao.getVehicleNumberAccordingToVendorForDriver(vendor);
		return vehicleNumber;
	}
	
	@Override
	public CITCRADriver driverRecordForModify(Long id) {
		return citCraJpaDao.driverRecordForModify(id);
	}
	
	@Override
	public List<String> getVehicleNumberList() {
		List<String> vehicleNumberList=citCraJpaDao.getVehicleNumberList();
		return vehicleNumberList;
	}
	
	@Override
	public boolean updateCITCRADriver(CITCRADriver driver) {
		boolean isSaved = citCraJpaDao.updateCITCRADriver(driver);
		LOG.info("CIT CRA DRIVER UPDATED");
		return isSaved;
	}
	
	
	@Override
	public CITCRADriver driverRecordForRemove(Long id) {
		return citCraJpaDao.driverRecordForRemove(id);
	}

	@Override
	public boolean deleteCITCRADriver(CITCRADriver driver) {
		boolean isSaved = citCraJpaDao.deleteCITCRADriver(driver);
		LOG.info("CIT CRA DRIVER REMOVED");
		return isSaved;
	}
	
	
	/*@Override
	public CITCRAVendor getCITCRAVendor(int id){
		CITCRAVendor citcraVendors=citCraDao.getCITCRAVendor(id);
		return citcraVendors;
	}
	
	@Override
	public boolean CITCRAVendorAndICMCAsso(CITCRAVendorICMCAssociation association) {
		boolean isSaved=citCraDao.CITCRAVendorAndICMCAsso(association);
		return isSaved;
	}
	*/
}
