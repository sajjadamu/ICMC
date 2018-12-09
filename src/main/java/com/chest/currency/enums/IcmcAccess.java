package com.chest.currency.enums;

public enum IcmcAccess {
	
	ICMC,
	REGION,
	ZONE,
	ALL,
	IT_ADMIN;
	
	public String value(){
		return name();
	}
	
	public static IcmcAccess fromValue(String v){
		return valueOf(v);
	}
	
}
