/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum CashSource {
	
	BRANCH,
	DSB,
	RBI,
	OTHERBANK,
	DIVERSION;
	
	public String value(){
		return name();
	}
	
	public static CashSource fromValue(String v){
		return valueOf(v);
	}
}
