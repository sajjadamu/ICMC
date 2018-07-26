/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum VaultSize {
	
	LARGE,
	MEDIUM,
	MINI,
	VERYLARGE,
	SMALL;
	
	public String value(){
		return name();
	}
	
	public static VaultSize fromValue(String v){
		return valueOf(v);
	}
}
