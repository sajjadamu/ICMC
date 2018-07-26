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

import com.chest.currency.entity.model.HolidayMaster;
import com.chest.currency.jpa.dao.HolidayMasterJpaDao;

@Service
@Transactional
public class HolidayMasterServiceImpl implements HolidayMasterService {
	
	private static final Logger LOG = LoggerFactory.getLogger(HolidayMasterServiceImpl.class);
	
	@Autowired
	protected HolidayMasterJpaDao holidayMasterJpaDao;

	@Override
	public List<HolidayMaster> getHolidayList(String state) {
		LOG.info("Fetching Holiday List");
		List<HolidayMaster> holidayList = holidayMasterJpaDao.getHolidayList(state);
		return holidayList;
	}

	@Override
	public boolean bulkHolidayRequest(List<HolidayMaster> bulkHolidayList, HolidayMaster holiday) {
		boolean isAllsuccess;
		try{
			isAllsuccess = holidayMasterJpaDao.bulkHolidayRequest(bulkHolidayList, holiday);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return isAllsuccess;
	}


}
