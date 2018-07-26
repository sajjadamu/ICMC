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
public class GrandSummary {

	protected BigDecimal total;
	protected BigDecimal totalNotes;
	protected BigDecimal totalCoins;
	protected Zone zone;

	protected BigDecimal totalNorth;
	protected BigDecimal totalNorthNotes;
	protected BigDecimal totalNorthCoins;
	
	protected BigDecimal totalSouth;
	protected BigDecimal totalSouthNotes;
	protected BigDecimal totalSouthCoins;
	
	protected BigDecimal totalEast;
	protected BigDecimal totalEastNotes;
	protected BigDecimal totalEastCoins;
	
	protected BigDecimal totalWest;
	protected BigDecimal totalWestNotes;
	protected BigDecimal totalWestCoins;
}
