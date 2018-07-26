/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import com.chest.currency.entity.model.HolidayMaster;


public interface HolidayMasterJpaDao {
	
	public List<HolidayMaster> getHolidayList(String state);
	
	public boolean bulkHolidayRequest(List<HolidayMaster> bulkHolidayList, HolidayMaster holiday);
	
}
