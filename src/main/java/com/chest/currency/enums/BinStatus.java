/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum BinStatus {
	EMPTY, FULL, NOT_FULL;

	public String value() {
		return name();
	}

	public static BinStatus fromValue(String v) {
		return valueOf(v);
	}
}
