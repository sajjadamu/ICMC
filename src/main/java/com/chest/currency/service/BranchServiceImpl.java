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

import com.chest.currency.entity.model.Branch;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.entity.model.RbiMaster;
import com.chest.currency.entity.model.ZoneMaster;
import com.chest.currency.jpa.dao.BranchJpaDao;

@Service
@Transactional
public class BranchServiceImpl implements BranchService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BranchServiceImpl.class);
	
	@Autowired
	protected BranchJpaDao branchJpaDao;

	@Override
	public List<Branch> getBranch() {
		LOG.info("Fetched retail branch records");
		List<Branch> branchList = branchJpaDao.getBranch();
		return branchList;
	}

	@Override
	public List<ICMC> getServicingICMCName() {
		LOG.info("Fetched ICMC Names");
		List<ICMC> icmcList = branchJpaDao.getServicingICMCName();
		return icmcList;
	}

	@Override
	public boolean saveBranch(Branch branch) {
		boolean isAllsuccess = false;
		try{
			isAllsuccess = branchJpaDao.saveBranch(branch);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return isAllsuccess;
	}
	
	@Override
	public Branch isSolIdValid(String solId){
		Branch dbBranch = branchJpaDao.isSolIdValid(solId);
		return dbBranch;
	}
	
	@Override
	public Branch isBranchNameValid(String branch){
		Branch dbBranch = branchJpaDao.isBranchNameValid(branch);
		return dbBranch;
	}

	@Override
	public boolean uploadBranch(List<Branch> branchList, Branch branch) {
		boolean isAllsuccess = false;
		try{
			LOG.info("Uploading Branch Records From CSV..");
			isAllsuccess = branchJpaDao.uploadBranch(branchList, branch);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
		return isAllsuccess;
	}

	@Override
	public boolean RemoveBranch(Branch branch) {
		branchJpaDao.RemoveBranch(branch);
		LOG.info("Branch Record Removed");
		return true;
	}

	@Override
	public Branch getBranchById(Long id) {
		LOG.info("Going to Remove Branch Record..");
		Branch branch = branchJpaDao.getBranchById(id);
		return branch;
	}

	@Override
	public void updateBranch(Branch branch) {
		Branch branchDB = branchJpaDao.getBranchById(branch.getId());
		branch.setInsertBy(branchDB.getInsertBy());
		branch.setInsertTime(branchDB.getInsertTime());
		branchJpaDao.updateBranch(branch);
	}

	@Override
	public List<RbiMaster> getRBIMasterList() {
		List<RbiMaster> rbiMasterList = branchJpaDao.getRBIMasterList();
		return rbiMasterList;
	}

	@Override
	public List<RbiMaster> getZoneAndRegion(String rbiName) {
		List<RbiMaster> zoneAndRegionList = branchJpaDao.getZoneAndRegion(rbiName);
		return zoneAndRegionList;
	}

	@Override
	public List<String> getRBINameList() {
		List<String> rbiNameList = branchJpaDao.getRBINameList();
		return rbiNameList;
	}

	@Override
	public List<ZoneMaster> getRegionList(Branch branch) {
		List<ZoneMaster> regionList = branchJpaDao.getRegionList(branch);
		return regionList;
	}

}
