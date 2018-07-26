/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineDowntimeUpdation;
import com.chest.currency.entity.model.MachineSoftwareUpdation;
import com.chest.currency.jpa.dao.MachineDownTimeJpaDao;

@Service
@Transactional
public class MachineServiceImpl implements MachineService {

	@Autowired
	protected MachineDownTimeJpaDao machineDownTimeJpaDao;

	@Override
	public boolean insertMachineDownTime(MachineDowntimeUpdation machine) {
		return machineDownTimeJpaDao.MachineDownTimeUpdation(machine);
	}

	@Override
	public List<MachineDowntimeUpdation> getMachineDownTime(BigInteger icmcId) {

		List<MachineDowntimeUpdation> listMachineDowntimeUpdation = machineDownTimeJpaDao
				.getListmachineDownTime(icmcId);
		return listMachineDowntimeUpdation;
	}

	@Override
	public MachineDowntimeUpdation machineRecordForModify(Long idMachineDownTime) {
		MachineDowntimeUpdation machinDown = machineDownTimeJpaDao.machineRecordForModify(idMachineDownTime);
		return machinDown;
	}

	@Override
	public boolean updateMachineDownTime(MachineDowntimeUpdation machine) {
		return machineDownTimeJpaDao.updateMachineDownTimeUpdation(machine);

	}

	@Override
	public List<MachineSoftwareUpdation> getMachineSoftwareUpdation(BigInteger icmcId) {
		List<MachineSoftwareUpdation> listMachineSoftwareUpdation = machineDownTimeJpaDao
				.getListmachineSoftware(icmcId);
		return listMachineSoftwareUpdation;
	}

	/*
	 * @Override public boolean insertMachineSoftware(MachineSoftwareUpdation
	 * machine) { return machineDownTimeJpaDao.insertMachineSoftware(machine);
	 * 
	 * }
	 */

	@Override
	public MachineSoftwareUpdation machineSoftwareRecordForModify(Long id) {
		MachineSoftwareUpdation machinDown = machineDownTimeJpaDao.machineSoftwareRecordForModify(id);
		return machinDown;
	}

	@Override
	public boolean updateMachineSoftware(MachineSoftwareUpdation machine) {
		return machineDownTimeJpaDao.updateMachineSoftwareUpdation(machine);

	}

	@Override
	public List<String> getRegionFromZoneMaster() {
		List<String> zoneMasters = machineDownTimeJpaDao.getListZoneMaster();
		return zoneMasters;
	}

	@Override
	public List<Machine> getMachineNumber(BigInteger icmcId) {
		List<Machine> machineList = machineDownTimeJpaDao.getMachineNumber(icmcId);
		return machineList;
	}

	@Override
	public boolean insertMachineSoftware(Machine machine) {
		return machineDownTimeJpaDao.insertMachineSoftware(machine);

	}

	@Override
	public long updateMachineSoftwareUpdation(Machine machine) {
		long count = machineDownTimeJpaDao.updateMachineSoftwareUpdation(machine);
		return count;
	}

	@Override
	public List<MachineDowntimeUpdation> getListmachineDownTime(BigInteger icmcId, Calendar sDate, Calendar eDate) {
		List<MachineDowntimeUpdation> machineList = machineDownTimeJpaDao.getListmachineDownTime(icmcId, sDate, eDate);
		return machineList;
	}

}
