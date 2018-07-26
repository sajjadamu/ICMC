/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.enums;

/**
 * @author root
 *
 */
public enum CurrencyType {
	
	UNPROCESS,
	ATM,
	FRESH,
	SOILED,
	ISSUABLE,
	MUTILATED,
	COINS,
	ALL;
	
	public String value(){
		return name();
	}
	
	public static CurrencyType fromValue(String v){
		return valueOf(v);
	}
}
