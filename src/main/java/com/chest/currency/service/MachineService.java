/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import com.chest.currency.entity.model.Machine;
import com.chest.currency.entity.model.MachineDowntimeUpdation;
import com.chest.currency.entity.model.MachineSoftwareUpdation;


public interface MachineService {
public boolean insertMachineDownTime(MachineDowntimeUpdation machine);

public List<MachineDowntimeUpdation> getMachineDownTime(BigInteger icmcId);

public MachineDowntimeUpdation machineRecordForModify(Long idMachineDownTime);

public boolean updateMachineDownTime(MachineDowntimeUpdation machine);

public List<MachineSoftwareUpdation> getMachineSoftwareUpdation(BigInteger icmcId);

/*public boolean insertMachineSoftware(MachineSoftwareUpdation machine);*/

public MachineSoftwareUpdation machineSoftwareRecordForModify(Long id);

public boolean updateMachineSoftware(MachineSoftwareUpdation machine);

public List<String> getRegionFromZoneMaster();

public List<Machine> getMachineNumber(BigInteger icmcId);

public boolean insertMachineSoftware(Machine machine);

public long updateMachineSoftwareUpdation(Machine machine);

public List<MachineDowntimeUpdation> getListmachineDownTime(BigInteger icmcId,Calendar sDate,Calendar eDate);

}
