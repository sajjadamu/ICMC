/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum CashType {
	NOTES, COINS, SOILED;

	public String value() {
		return name();
	}

	public static CashType fromValue(String v) {
		return valueOf(v);
	}
}
