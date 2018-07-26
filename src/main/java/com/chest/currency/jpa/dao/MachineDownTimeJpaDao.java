/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineDowntimeUpdation;
import com.chest.currency.entity.model.MachineSoftwareUpdation;


public interface MachineDownTimeJpaDao {
public boolean MachineDownTimeUpdation(MachineDowntimeUpdation machine);

public List<MachineDowntimeUpdation> getListmachineDownTime(BigInteger icmcId);

public List<MachineDowntimeUpdation> getListmachineDownTime(BigInteger icmcId,Calendar sDate,Calendar eDate);

public MachineDowntimeUpdation machineRecordForModify(Long idMachineDownTime);

public boolean updateMachineDownTimeUpdation(MachineDowntimeUpdation machine);

public List<MachineSoftwareUpdation> getListmachineSoftware(BigInteger icmcId);

/*public boolean insertMachineSoftware(MachineSoftwareUpdation machine);*/

public MachineSoftwareUpdation machineSoftwareRecordForModify(Long id);

public boolean updateMachineSoftwareUpdation(MachineSoftwareUpdation machine);

public List<String> getListZoneMaster();

public List<Machine> getMachineNumber(BigInteger icmcId);

public boolean insertMachineSoftware(Machine machine);

public long updateMachineSoftwareUpdation(Machine machine);

}