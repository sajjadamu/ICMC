/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao;

import java.util.List;

import com.chest.currency.viewBean.CITCRAVehicle;
import com.chest.currency.viewBean.CITCRAVendor;

public interface CITCRADao {

	//public int UploadCITCRAVendor(List<CITCRAVendor> vendorList, Map<String, ICMC> map);

	//public boolean CITCRAVendorAndICMCAsso(CITCRAVendorICMCAssociation association);
	
	//public boolean addInCITCRAVendorAndCITCRAVendorICMCAsso(CITCRAVendor vendor,CITCRAVendorICMCAssociation vendorAsso);
	
	public List<CITCRAVendor> getVendorNameAccordingToICMC(int icmcId);
	
	public List<CITCRAVehicle> getVehicleNumberAccordingToVendorForDriver(String vendor);
	
	public List<String> getVehicleNumberList();
	
}
