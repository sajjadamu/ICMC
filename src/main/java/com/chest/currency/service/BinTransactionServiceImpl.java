/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.exception.BaseGuiException;
import com.chest.currency.jpa.dao.BinTransactionJpaDao;

@Service
@Transactional
public class BinTransactionServiceImpl implements BinTransactionService {

	@Autowired
	private BinTransactionJpaDao binTransactionJpaDao;

	@Override
	public boolean UploadBinTransaction(List<BinTransaction> binList, BinTransaction binTransaction,
			BigInteger icmcId) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = binTransactionJpaDao.UploadBinTransaction(binList, binTransaction, icmcId);
		} catch (Exception ex) {
			throw new BaseGuiException(ex.getMessage());
		}
		return isAllSuccess;
	}

	@Override
	public BoxMaster isValidBox(BigInteger icmcId, String boxname) {
		return binTransactionJpaDao.isValidBox(icmcId, boxname);
	}

	@Override
	public boolean UploadBoxMaster(List<BoxMaster> boxlist, BoxMaster boxmaster) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = binTransactionJpaDao.UploadBoxMaster(boxlist, boxmaster);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return isAllSuccess;
	}

	@Override
	public BinTransaction isValidBin(BigInteger icmcId, String binNumber) {
		return binTransactionJpaDao.isValidBin(icmcId, binNumber);
	}

	@Override
	public boolean uploadBranchReceipt(List<BranchReceipt> branchReceiptList, BranchReceipt branchReceipt) {
		boolean isAllSuccess = false;
		try {
			isAllSuccess = binTransactionJpaDao.uploadBranchReceipt(branchReceiptList, branchReceipt);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage());
		}
		return isAllSuccess;
	}

	@Override
	public List<BinTransaction> getBinTransaction(BigInteger icmcId) {
		return binTransactionJpaDao.getBinTransaction(icmcId);
	}

}
