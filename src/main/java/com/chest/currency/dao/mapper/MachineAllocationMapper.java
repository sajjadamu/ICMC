/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.chest.currency.viewBean.MachineAllocationBean;

public class MachineAllocationMapper implements RowMapper<MachineAllocationBean> {

	@Override
	public MachineAllocationBean mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		MachineAllocationBean machineAllocation = new MachineAllocationBean();
		machineAllocation.setDenomination(resultSet.getInt("denomination"));
		machineAllocation.setMachine(resultSet.getInt("machine"));
		machineAllocation.setIssued_bundle(resultSet.getDouble("issued_bundle"));
		return machineAllocation;
	}
}
