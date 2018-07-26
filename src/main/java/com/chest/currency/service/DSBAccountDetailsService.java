/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.List;

import com.chest.currency.entity.model.DSBAccountDetail;
import com.chest.currency.entity.model.ICMC;

public interface DSBAccountDetailsService {
	public List<ICMC> getICMCName();

	public List<DSBAccountDetail> getDSBAccountDetailList();

	public boolean addDSBAccountDetails(DSBAccountDetail dsbAccountDetail);

	public DSBAccountDetail getDSBAccountDetailById(long id);

	public boolean updateDSBAccountDetail(DSBAccountDetail dsbAccountDetail);
	
	public DSBAccountDetail isVendorNameValid(String vendorName, BigInteger icmcId);
}
