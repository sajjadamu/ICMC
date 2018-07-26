/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.viewBean;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class SASAllocationGrouped {
	
	protected CurrencyType freshBin;
	
	protected CurrencyType issuableBin;
	
	protected CurrencyType atmBin;
	
	protected CashType cashType;
	
	protected String freshBinNumber;
	
	protected String issuableBinNumber;
	
	protected BigDecimal coinsBag;
	
	protected String atmBinNumber;

	protected Integer denomination;

	protected BigDecimal freshBundle;

	protected BigDecimal issuableBundle;

	protected BigDecimal atmBundle;
	
	protected BigInteger icmcId;
	
}
