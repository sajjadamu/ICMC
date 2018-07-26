/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.Jurisdiction;
import com.chest.currency.jpa.dao.JurisdictionJpaDao;

/**
 * @author root
 *
 */
@Service
@Transactional
public class JurisdictionServiceImpl implements JurisdictionService {

	private static final Logger LOG = LoggerFactory.getLogger(JurisdictionServiceImpl.class);

	@Autowired
	protected JurisdictionJpaDao jurisdictionJpaDao;

	@Override
	public boolean createJurisdiction(Jurisdiction jurisdiction) {
		LOG.info("JURISDICTION CREATED");
		boolean isSaved = jurisdictionJpaDao.createJurisdiction(jurisdiction);
		return isSaved;
	}

	@Override
	public List<Jurisdiction> getJurisdictionList() {
		List<Jurisdiction> jurisdictionList = jurisdictionJpaDao.getJurisdictionList();
		return jurisdictionList;
	}

	@Override
	public Jurisdiction getJurisdictionById(int id) {
		return jurisdictionJpaDao.getJurisdictionById(id);
	}

	@Override
	public boolean updateJurisdiction(Jurisdiction jurisdiction) {
		jurisdictionJpaDao.updateJurisdiction(jurisdiction);
		return true;
	}
	
	@Override
	public boolean UploadJurisdiction(List<Jurisdiction> jurisdictionList, Jurisdiction jurisdiction) {
		boolean isAllsuccess = false;
		try{
			LOG.info("Uploading Jurisdiction Records From CSV..");
			isAllsuccess = jurisdictionJpaDao.UploadJurisdiction(jurisdictionList, jurisdiction);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return isAllsuccess;
	}

}
