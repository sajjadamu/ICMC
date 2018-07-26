/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigDecimal;

import com.chest.currency.enums.Zone;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FlatSummary {
	
	protected BigDecimal atm;
	protected BigDecimal fresh;
	protected BigDecimal issuable;
	protected BigDecimal unprocess;
	protected BigDecimal soiled;
	protected BigDecimal coins;
	protected BigDecimal total;
	protected Zone zone;
	
	public FlatSummary(){
		this.atm = BigDecimal.ZERO;
		this.fresh = BigDecimal.ZERO;
		this.issuable = BigDecimal.ZERO;
		this.unprocess = BigDecimal.ZERO;
		this.soiled = BigDecimal.ZERO;
		this.coins = BigDecimal.ZERO;
		this.total = BigDecimal.ZERO;
	}

}
