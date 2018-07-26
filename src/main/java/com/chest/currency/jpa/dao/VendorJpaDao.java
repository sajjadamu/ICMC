/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import com.chest.currency.entity.model.Vendor;

public interface VendorJpaDao {
	
	public boolean addVendor(Vendor vendor);

	public List<Vendor> getVendorList();

}
