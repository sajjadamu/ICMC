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

import com.chest.currency.entity.model.ServicingBranch;
import com.chest.currency.jpa.dao.ServicingBranchJpaDao;

@Service
@Transactional
public class ServicingBranchServiceImpl implements ServicingBranchService {
	
	private static final Logger LOG = LoggerFactory.getLogger(ServicingBranchServiceImpl.class);
	
	@Autowired
	ServicingBranchJpaDao servicingBranchJpaDao;
	
	@Override
	public boolean UploadServicingBranch(List<ServicingBranch> servicingList, ServicingBranch sb) {
		boolean isAllsuccess = false;
		try{
			isAllsuccess = servicingBranchJpaDao.UploadServicingBranch(servicingList, sb);
			LOG.info("Servicing Branch Records uploaded successfully");
		}catch(Exception ex){
			throw new RuntimeException("Error while CSV File Uploading");
		}
		return isAllsuccess;
	}

	@Override
	public List<ServicingBranch> getServicingBranch() {
		List<ServicingBranch> branchList = servicingBranchJpaDao.getServicingBranch();
		LOG.info("Fetched Servicing Branch Records : "+branchList);
		return branchList;
	}

	@Override
	public ServicingBranch editServicingBranch(Long id) {
		return servicingBranchJpaDao.editServicingBranch(id);
	}

	@Override
	public boolean updateServicingBranch(ServicingBranch servicingBranch) {
		boolean isSaved = false; 
		isSaved = servicingBranchJpaDao.updateServicingBranch(servicingBranch);
		LOG.info("Servicing Branch Record updated");
		return isSaved;
	}

}
