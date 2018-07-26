/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.service;

import java.math.BigInteger;
import java.util.List;

import com.chest.currency.entity.model.BinMaster;
import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;

public interface BinTransactionService {

	public boolean UploadBinTransaction(List<BinTransaction> binList,BinTransaction binTransaction,BigInteger icmcId);

	public boolean uploadBranchReceipt(List<BranchReceipt> branchReceiptList, BranchReceipt branchReceipt);
	
	public List<BinTransaction> getBinTransaction(BigInteger icmcId);
	
	public BinTransaction isValidBin(BigInteger icmcId, String binNumber);

	public BoxMaster isValidBox(BigInteger icmcId, String boxname);

	public boolean UploadBoxMaster(List<BoxMaster> boxlist,BoxMaster boxmaster);

}
