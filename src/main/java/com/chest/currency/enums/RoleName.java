/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum RoleName {
	
	ITG,
	SUPERVISOR,
	ZH,
	RH,
	CCA,
	IAD,
	ICMC_HEAD,
	OIC,
	VAULT_CUSTODIAN,
	PROCESSING_ROOM_INCHARGE,
	CHECKER,
	MAKER;
	
	public String value(){
		return name();
	}
	
	public static RoleName fromValue(String v){
		return valueOf(v);
	}
}
