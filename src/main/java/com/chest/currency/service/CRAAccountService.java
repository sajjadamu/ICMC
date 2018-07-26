/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.ICMC;

public interface CRAAccountService {
	

	public List<ICMC> getICMCName();

	public List<CRAAccountDetail> getCRAccountDetailList();

	public boolean addCRAAccountDetails(CRAAccountDetail craAccountDetail);

	public CRAAccountDetail getCRAAccountDetailById(long id);

	public boolean updateCRAccountDetail(CRAAccountDetail craAccountDetail);


}
