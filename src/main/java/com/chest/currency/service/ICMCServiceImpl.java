/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.Branch;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.User;
import com.chest.currency.jpa.dao.ICMCJpaDao;

@Service
@Transactional
public class ICMCServiceImpl implements ICMCService {

	private static final Logger LOG = LoggerFactory.getLogger(UserAdministrationServiceImpl.class);

	@Autowired
	protected ICMCJpaDao icmcJpaDao;

	@Autowired
	ConcurrentHashMap<Long, ICMC> icmcConcurrentHashMap;

	@Override
	public boolean createICMC(ICMC icmc) {
		boolean isSaved = icmcJpaDao.createICMC(icmc);
		return isSaved;
	}

	@Override
	public ICMC isIcmcNameValid(String name) {
		ICMC dbIcmc = icmcJpaDao.isIcmcNameValid(name);
		return dbIcmc;
	}

	@Override
	public List<ICMC> getICMCList() {
		List<ICMC> icmcList = icmcJpaDao.getICMCList();
		return icmcList;
	}

	@Override
	public ICMC getICMCById(Long id) {
		return icmcJpaDao.getICMCById(id);
	}

	@Override
	public boolean removeICMC(ICMC icmc) {
		icmcJpaDao.removeICMC(icmc);
		return true;
	}

	@Override
	public boolean uploadICMC(List<ICMC> icmcList, ICMC icmc) {
		boolean isAllsuccess = false;
		try {
			LOG.info("Uploading ICMC Records From CSV..");
			isAllsuccess = icmcJpaDao.uploadICMC(icmcList, icmc);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllsuccess;
	}

	@Override
	public List<ICMC> getICMCList(List<Long> icmcIds) {
		return icmcJpaDao.getICMCList(icmcIds);
	}

	@Override
	public boolean updateICMC(ICMC icmc) {
		ICMC icmcDB = icmcJpaDao.getICMCById(icmc.getId());
		icmc.setInsertBy(icmcDB.getInsertBy());
		icmc.setInsertTime(icmcDB.getInsertTime());
		return icmcJpaDao.updateICMC(icmc);
	}

	@Override
	public List<Branch> getRBINameZoneAndRegion(String linkBranchSolId) {
		List<Branch> branchList = icmcJpaDao.getRBINameZoneAndRegion(linkBranchSolId);
		return branchList;
	}

	@Override
	public List<String> getRegionList(ICMC icmc) {
		List<String> regionList = icmcJpaDao.getRegionList(icmc);
		return regionList;
	}

	@Override
	public ICMC getSynchronizedIcmc(User user) {
		LOG.info("user SynchronizedIcmc..ID." + user.getId());
		ICMC icmc = null;
		try {
			icmc = icmcConcurrentHashMap.get(user.getIcmcId().longValue());
			if (icmc == null) {
				LOG.info("icmc is not in map...");
				icmc = this.getICMCById(user.getIcmcId().longValue());
				icmcConcurrentHashMap.putIfAbsent(icmc.getId(), icmc);
				icmc = icmcConcurrentHashMap.get(user.getIcmcId().longValue());
				LOG.info("icmc is put in map...in if", icmc.getId());
			} else {
				LOG.info("icmc is already in map...in else ICMCServiceImpl.getSynchronizedIcmc");
			}
		} catch (Exception e) {
			LOG.info("Catch Exception" + e);
		}
		return icmc;
	}

}
