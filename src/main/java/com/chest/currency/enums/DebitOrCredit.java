/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum DebitOrCredit {
	
	Dr,
	Cr;
	
	public String value(){
		return name();
	}
	
	public static DebitOrCredit fromValue(String v){
		return valueOf(v);
	}
}
