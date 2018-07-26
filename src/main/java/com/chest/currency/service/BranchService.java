/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.Branch;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.ZoneMaster;

public interface BranchService {
	
	public List<Branch> getBranch();
	
	public List<ICMC> getServicingICMCName();
	
	public boolean saveBranch(Branch branch);
	
	public Branch isSolIdValid(String solId);
	
	public Branch isBranchNameValid(String branch);
	
	public boolean uploadBranch(List<Branch> branchList, Branch branch);
	
	public boolean RemoveBranch(Branch branch);

	public Branch getBranchById(Long id);

	public void updateBranch(Branch branch);

	public List<RbiMaster> getRBIMasterList();

	public List<RbiMaster> getZoneAndRegion(String rbiName);

	public List<String> getRBINameList();

	public List<ZoneMaster> getRegionList(Branch branch);

}
