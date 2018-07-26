/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.Vendor;


public interface VendorService {
	
	public boolean addVendor(Vendor vendor);

	public List<Vendor> getVendorList();

}
