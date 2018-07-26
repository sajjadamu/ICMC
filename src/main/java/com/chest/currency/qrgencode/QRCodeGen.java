/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.qrgencode;

import com.chest.currency.entity.model.AuditorProcess;
import com.chest.currency.entity.model.BankReceipt;
import com.chest.currency.entity.model.BranchReceipt;
import com.chest.currency.entity.model.DSB;
import com.chest.currency.entity.model.DiversionIRV;
import com.chest.currency.entity.model.FreshFromRBI;
import com.chest.currency.entity.model.Process;
import com.chest.currency.entity.model.ProcessBundleForCRAPayment;
import com.chest.currency.entity.model.SoiledRemittanceAllocation;

public interface QRCodeGen {
	
	public String getPath(String userId);

	public String generateQR(BranchReceipt branchReceipt);

	public String generateDirvQR(DiversionIRV dirv);

	public String generateFreshFromRBIQR(FreshFromRBI freshFromRBI);

	public String generateDSBQR(DSB dsb);
	
	public String generateProcessingRoomQR(Process process);
	
	public String generateProcessingRoomQRAuditor(AuditorProcess process);

	public String generateICMCReceiptQR(BankReceipt icmcReceipt);

	public String generateCRAPaymentProcessingRoomQR(ProcessBundleForCRAPayment processBundleForCRAPayment);

	public String generateQRForSoiled(SoiledRemittanceAllocation soiled);

}
