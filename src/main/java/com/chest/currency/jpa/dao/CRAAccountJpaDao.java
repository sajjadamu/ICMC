/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.util.List;

import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.ICMC;

public interface CRAAccountJpaDao {
	public boolean addCRAAccountDetails(CRAAccountDetail craAccountDetail);

	public List<CRAAccountDetail> getCRAAccountDetailList();

	public List<ICMC> getICMCName();

	public CRAAccountDetail getCRAAccountDetailById(long id);

	public boolean updateCRAAccountDetail(CRAAccountDetail craAccountDetail);
}
