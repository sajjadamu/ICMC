/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum DenominationType {
	
	DENOM_2000(2000),
	DENOM_1000(1000),
	DENOM_500(500),
	DENOM_200(200),
	DENOM_100(100),
	DENOM_50(50),
	DENOM_20(20),
	DENOM_10(10),
	DENOM_5(5),
	DENOM_2(2),
	DENOM_1(1);
	
	int denomination;
	
	public int getDenomination() {
		return denomination;
	}

	DenominationType(int denomination){
		this.denomination = denomination;
	}
	
	public String value(){
		return name();
	}
	
	public static DenominationType fromValue(String v){
		return valueOf(v);
	}
	
}
