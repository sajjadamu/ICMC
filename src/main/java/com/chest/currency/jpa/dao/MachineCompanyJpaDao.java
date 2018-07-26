/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import com.chest.currency.entity.model.MachineCompany;

public interface MachineCompanyJpaDao {

	public	List<MachineCompany> getMachineCompany();

	public	boolean insertMachineCompany(MachineCompany machineCompany);

	public MachineCompany getMachineCompanyForModify(Long id);

	public boolean updateMachineCompany(MachineCompany machineCompany);

	public MachineCompany isValidMachineCompany(String companyname);

}
