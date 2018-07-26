/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.viewBean;

public enum RolesEnum {
	
	ITG("ITG"),
	Supervisor("Supervisor"),
	ZH("ZH"),
	RH("RH"),
	CCA("CCA"),
	IAD("IAD"),
	ICMC_Head("ICMC Head"),
	OIC("OIC"),
	Vault_Custodian("Vault Custodian"),
	Processing_Room_Incharge("Processing Room Incharge"),
	Checker("Checker"),
	Maker("Maker");
	
	String role;

	public String getRole() {
		return role;
	}

	RolesEnum(String role){
		this.role = role;
	}
}
