/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.ServicingBranch;

public interface ServicingBranchService {

	public boolean UploadServicingBranch(List<ServicingBranch> servicingList, ServicingBranch sb);

	public List<ServicingBranch> getServicingBranch();

	public ServicingBranch editServicingBranch(Long id);

	public boolean updateServicingBranch(ServicingBranch servicingBranch);

}
