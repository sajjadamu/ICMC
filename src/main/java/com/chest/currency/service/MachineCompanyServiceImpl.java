/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.MachineCompany;
import com.chest.currency.jpa.dao.MachineCompanyJpaDao;

@Service
@Transactional
public class MachineCompanyServiceImpl implements MachineCompanyService {

	@Autowired
	MachineCompanyJpaDao machineCompanyJpaDao;
	
	
	@Override
	public List<MachineCompany> getMachineCompany() {
		return machineCompanyJpaDao.getMachineCompany();
	}
	
	@Override
	public boolean insertMachineCompany(MachineCompany machineCompany) {
		return machineCompanyJpaDao.insertMachineCompany(machineCompany);
		
	}
	
	@Override
	public MachineCompany getMachineCompanyForModify(Long id) {
		return machineCompanyJpaDao.getMachineCompanyForModify(id);
	}
	
	@Override
	public boolean updateMachineCompany(MachineCompany machineCompany) {
		MachineCompany oldMachineCompany= machineCompanyJpaDao.getMachineCompanyForModify(machineCompany.getId());
		machineCompany.setInsertTime(oldMachineCompany.getInsertTime());
		machineCompany.setInsertBy(oldMachineCompany.getInsertBy());
		return machineCompanyJpaDao.updateMachineCompany(machineCompany);
	}
	
	@Override
	public MachineCompany isValidMachineCompany(String companyname) {
		return machineCompanyJpaDao.isValidMachineCompany(companyname);
	}
}
