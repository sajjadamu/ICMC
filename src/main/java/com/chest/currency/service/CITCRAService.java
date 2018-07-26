/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.CITCRADriver;
import com.chest.currency.entity.model.CITCRAVehicle;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.ZoneMaster;

public interface CITCRAService {
	
	public boolean addCitCraVendor(CITCRAVendor citcraVendor);
	
	public List<ICMC> getICMCName(String region);
	
	//public List<ICMC> getICMCName();

	public List<CITCRAVendor> getCITCRAVendor();
	
	public boolean UploadCITCRAVendor(List<CITCRAVendor> vendorList, CITCRAVendor vendor);
	
	public CITCRAVendor vendorRecordForModify(Long id);
	
	public List<ZoneMaster> getRegionList(CITCRAVendor vendor);
	
	public List<ICMC>  getIcmcList(CITCRAVendor vendor);
	
	public void updateCITCRAVendor(CITCRAVendor vendor);
	
	public CITCRAVendor getCitCraVendorById(Long id);

	public boolean deleteCITCRAVendor(CITCRAVendor vendor);
	
	public List<CITCRAVehicle> getVehicleDetails();
	
	public boolean addVehicle(CITCRAVehicle vehicle);
	
	public boolean UploadCITCRAVehicle(List<CITCRAVehicle> vehicleList, CITCRAVehicle vehicle);
	
	public List<CITCRAVendor> getVendorNameAccordingToICMC(String regionId);

	public CITCRAVehicle vehicleRecordForModify(Long id);
	
	public boolean updateCITCRAVehicle(CITCRAVehicle vehicle);
	
	public CITCRAVehicle vehicleRecordForRemove(Long id);

	public boolean deleteCITCRAVehicle(CITCRAVehicle vehicle);
	
	public List<CITCRADriver> getVehicleDriver();
	
	public boolean addDriver(CITCRADriver driver);
	
	public boolean UploadCITCRADriver(List<CITCRADriver> driverList, CITCRADriver driver);
	
	public List<CITCRAVehicle> getVehicleNumberAccordingToVendorForDriver(String vendor);
	
	public CITCRADriver driverRecordForModify(Long id);
	
	public List<String> getVehicleNumberList();
	
	public boolean updateCITCRADriver(CITCRADriver driver);
	
	public CITCRADriver driverRecordForRemove(Long id);

	public boolean deleteCITCRADriver(CITCRADriver driver);
	
	
	//public CITCRAVendor getCITCRAVendor(int id);
	
	//public boolean CITCRAVendorAndICMCAsso(CITCRAVendorICMCAssociation association);
	
}
