package com.chest.currency.enums;

public enum PermissionModule {
	
	ADMIN,
	BIN_DASHBOARD,
	VAULT_MANAGEMENT,
	CIT_CRA_DETAILS,
	CASH_RECEIPT,
	CASH_PAYMENT,
	PROCESSING_ROOM,
	FAKE_NOTE_MANAGEMENT,
	MIGRATION,
	VERIFICATION,
	REPORTS;
	
	public String value(){
		return name();
	}
	
	public static PermissionModule fromValue(String v){
		return valueOf(v);
	}
}
