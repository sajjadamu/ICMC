/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.dao;

import java.math.BigInteger;
import java.util.List;

import com.chest.currency.entity.model.BinTransaction;
import com.chest.currency.entity.model.BoxMaster;
import com.chest.currency.entity.model.BranchReceipt;

public interface BinTransactionJpaDao {

public 	boolean UploadBinTransaction(List<BinTransaction> binList,BinTransaction binTransaction,BigInteger icmcId);

public boolean uploadBranchReceipt(List<BranchReceipt> branchReceiptList, BranchReceipt branchReceipt);

public List<BinTransaction> getBinTransaction(BigInteger icmcId);

public BinTransaction isValidBin(BigInteger icmcId, String binNumber);

public boolean UploadBoxMaster(List<BoxMaster> boxlist, BoxMaster boxmaster);

public BoxMaster isValidBox(BigInteger icmcId, String boxname);

}
