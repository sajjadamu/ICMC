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
import java.util.ArrayList;
import java.util.List;

import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class SASAllocationWrapper {
	
	protected CurrencyType freshBin;
	
	protected CurrencyType issuableBin;
	
	protected String freshBinNumber;
	
	protected CashType cashType;
	
	protected String issuableBinNumber;
	
	protected String atmBinNumber;

	protected Integer denomination;

	protected BigDecimal freshBundle;

	protected BigDecimal issuableBundle;
	
	protected BigDecimal atmBundle;
	
	protected BigDecimal coinsBag;

	protected BigInteger icmcId;
	
	protected Long id;//sas id
	
	private	List<SASAllocationGrouped> sasAllocationList = new ArrayList<>();
	private BigDecimal totalFresh;
	private BigDecimal totalIssubale;
}
