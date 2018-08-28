/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.DSBAccountDetail;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.jpa.dao.DSBAccountDetailsJpaDao;

@Service
@Transactional
public class DSBAccountDetailsServiceImpl implements DSBAccountDetailsService {

	@Autowired
	protected DSBAccountDetailsJpaDao dsbAccountJpaDao;

	@Override
	public List<ICMC> getICMCName() {
		return dsbAccountJpaDao.getICMCName();
	}

	@Override
	public List<DSBAccountDetail> getDSBAccountDetailList() {
		List<DSBAccountDetail> dsbAccountDetail = dsbAccountJpaDao.getDSBAccountDetailList();
		return dsbAccountDetail;
	}

	@Override
	public boolean addDSBAccountDetails(DSBAccountDetail dsbAccountDetail) {
		return dsbAccountJpaDao.addDSBAccountDetails(dsbAccountDetail);
	}

	@Override
	public DSBAccountDetail getDSBAccountDetailById(long id) {
		return dsbAccountJpaDao.getDSBAccountDetailById(id);
	}

	@Override
	public boolean updateDSBAccountDetail(DSBAccountDetail dsbAccountDetail) {
		return dsbAccountJpaDao.updateDSBAccountDetail(dsbAccountDetail);
	}

	@Override
	public DSBAccountDetail isVendorNameValid(String vendorName, BigInteger icmcId) {
		DSBAccountDetail vendorNameFromDB = dsbAccountJpaDao.isVendorNameValid(vendorName, icmcId);
		return vendorNameFromDB;
	}
}
