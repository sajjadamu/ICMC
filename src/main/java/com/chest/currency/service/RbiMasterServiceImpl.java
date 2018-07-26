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

import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.jpa.dao.RbiMasterJpaDao;

@Service
@Transactional
public class RbiMasterServiceImpl implements RbiMasterService {
	
	private static final Logger LOG = LoggerFactory.getLogger(RbiMasterServiceImpl.class);

	@Autowired
	RbiMasterJpaDao rbiMasterJpaDao;
	
	public boolean saveRbiMaster(RbiMaster rbiMaster) {
		return rbiMasterJpaDao.saveRbiMaster(rbiMaster);
	}
	
	@Override
	public List<RbiMaster> getAllRbiMaster() {
		return rbiMasterJpaDao.getAllRbiMaster();
	}
	
	@Override
	public RbiMaster getRbiMasterObject(Long id) {
		return rbiMasterJpaDao.getRbiMasterObject(id);
	}
	
	@Override
	public boolean updateRbiMaster(RbiMaster rbiMaster) {
		RbiMaster oldRbiMaster = rbiMasterJpaDao.getRbiMasterObject(rbiMaster.getId());
		rbiMaster.setInsertTime(oldRbiMaster.getInsertTime());
		rbiMaster.setInsertBy(oldRbiMaster.getInsertBy());
		return rbiMasterJpaDao.updateRbiMaster(rbiMaster);
	}
	
	@Override
	public List<ZoneMaster> getRegionList(RbiMaster rbi) {
		List<ZoneMaster> regionList = rbiMasterJpaDao.getRegionList(rbi);
		return regionList;
	}
	
	public RbiMaster isValidRbiName(String userId) {
		RbiMaster userBean = rbiMasterJpaDao.isValidRbiName(userId);
		return userBean;
	}

	@Override
	public boolean uploadRBIMaster(List<RbiMaster> rbiMasterList, RbiMaster rbiMaster) {
		boolean isAllsuccess = false;
		try{
			LOG.info("Uploading RBI Master Records From CSV..");
			isAllsuccess = rbiMasterJpaDao.uploadRBIMaster(rbiMasterList, rbiMaster);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return isAllsuccess;
	}
}
