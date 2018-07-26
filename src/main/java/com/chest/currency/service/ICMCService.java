/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.Branch;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.User;

public interface ICMCService {
	
	public boolean createICMC(ICMC icmc);
	
	public ICMC isIcmcNameValid(String name);

	public List<ICMC> getICMCList();
	
	public List<ICMC> getICMCList(List<Long> icmcIds);
	
	public ICMC getICMCById(Long id);
	
	public boolean removeICMC(ICMC icmc);
	
	public boolean uploadICMC(List<ICMC> icmcList, ICMC icmc);

	public boolean updateICMC(ICMC icmc);

	public List<Branch> getRBINameZoneAndRegion(String linkBranchSolId);

	public List<String> getRegionList(ICMC icmc);
	
	public ICMC getSynchronizedIcmc(User user);
}
