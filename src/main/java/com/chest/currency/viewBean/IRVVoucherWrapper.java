/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.viewBean;

import java.math.BigDecimal;
import java.util.List;

import com.chest.currency.entity.model.BranchReceipt;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IRVVoucherWrapper {
	
	private List<BranchReceipt> branchReceipts;
	private BigDecimal total;
	
}
