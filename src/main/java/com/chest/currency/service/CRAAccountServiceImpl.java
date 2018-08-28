/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.CRAAccountDetail;
import com.chest.currency.entity.model.ICMC;
import com.chest.currency.jpa.dao.CRAAccountJpaDao;

@Service
@Transactional
public class CRAAccountServiceImpl implements CRAAccountService {

	@Autowired
	protected CRAAccountJpaDao craAccountJpaDao;

	@Override
	public List<ICMC> getICMCName() {
		return craAccountJpaDao.getICMCName();
	}

	@Override
	public List<CRAAccountDetail> getCRAccountDetailList() {
		List<CRAAccountDetail> craAccountDetail = craAccountJpaDao.getCRAAccountDetailList();
		return craAccountDetail;
	}

	@Override
	public boolean addCRAAccountDetails(CRAAccountDetail craAccountDetail) {
		return craAccountJpaDao.addCRAAccountDetails(craAccountDetail);
	}

	@Override
	public CRAAccountDetail getCRAAccountDetailById(long id) {
		return craAccountJpaDao.getCRAAccountDetailById(id);
	}

	@Override
	public boolean updateCRAccountDetail(CRAAccountDetail craAccountDetail) {

		return craAccountJpaDao.updateCRAAccountDetail(craAccountDetail);
	}

}
