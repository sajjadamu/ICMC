/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum Status {
	
	ENABLED,
	DISABLED,
	DELETED;
	
	public String value(){
		return name();
	}
	
	public static Status fromValue(String v){
		return valueOf(v);
	}
}
