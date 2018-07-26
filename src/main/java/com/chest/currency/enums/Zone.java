/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum Zone {
	
	NORTH,
	SOUTH,
	EAST_AND_AP,
	WEST;
	
	public String value(){
		return name();
	}
	
	public static Zone fromValue(String v){
		return valueOf(v);
	}
}
