/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.ZoneMaster;

public interface RbiMasterService {

	public boolean saveRbiMaster(RbiMaster rbiMaster);

	public List<RbiMaster> getAllRbiMaster();

	public RbiMaster getRbiMasterObject(Long id);
	
	public boolean updateRbiMaster(RbiMaster rbiMaster);

	public List<ZoneMaster> getRegionList(RbiMaster rbi);
	
	public RbiMaster isValidRbiName(String userId);

	public boolean uploadRBIMaster(List<RbiMaster> rbiMasterList, RbiMaster rbiMaster);
}
