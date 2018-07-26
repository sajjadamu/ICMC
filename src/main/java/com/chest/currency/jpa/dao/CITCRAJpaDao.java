/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import com.chest.currency.entity.model.CITCRADriver;
import com.chest.currency.entity.model.CITCRAVehicle;
import com.chest.currency.entity.model.CITCRAVendor;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.ZoneMaster;


public interface CITCRAJpaDao {
	
	public List<CITCRAVendor> getCITCRAVendor();
	
	public List<ICMC> getICMCName(String region);
	
	public boolean addCitCraVendor(CITCRAVendor citcraVendor);
	
	public boolean UploadCITCRAVendor(List<CITCRAVendor> vendorList, CITCRAVendor vendor);
	
	public CITCRAVendor vendorRecordForModify(Long id);
	
	public List<ZoneMaster> getRegionList(CITCRAVendor vendor);
	
	public List<ICMC> getIcmcList(CITCRAVendor vendor);
	
	public boolean updateCITCRAVendor(CITCRAVendor vendor);
	
	public CITCRAVendor getCitCraVendorById(Long idCitCraVendor);
	
	public boolean deleteCITCRAVendor(CITCRAVendor citcraVendor);
	
	public List<CITCRAVehicle> getVehicleDetails();
	
	public boolean addVehicle(CITCRAVehicle vehicle);
	
	public boolean UploadCITCRAVehicle(List<CITCRAVehicle> vehicleList, CITCRAVehicle vehicle);
	
	public List<CITCRAVendor> getVendorNameAccordingToICMC(String regionId);
	
	public CITCRAVehicle vehicleRecordForModify(Long id);
	
	public boolean updateCITCRAVehicle(CITCRAVehicle vehicle);
	
	public CITCRAVehicle vehicleRecordForRemove(Long id);
	
	public boolean deleteCITCRAVehicle(CITCRAVehicle citcraVendor);
	
	public List<CITCRADriver> getVehicleDriverDetails();
	
	public boolean addDriver(CITCRADriver driver);
	
	public boolean UploadCITCRADriver(List<CITCRADriver> driverList, CITCRADriver driver);
	
	public List<CITCRAVehicle> getVehicleNumberAccordingToVendorForDriver(String vendor);
	
	public CITCRADriver driverRecordForModify(Long id);
	
	public List<String> getVehicleNumberList();
	
	public boolean updateCITCRADriver(CITCRADriver driver);
	
	public CITCRADriver driverRecordForRemove(Long id);
	
	public boolean deleteCITCRADriver(CITCRADriver driver);

}
