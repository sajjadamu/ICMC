/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum OtherStatus {

	RECEIVED, REQUESTED, PROCESSED, CANCELLED, ACCEPTED, RELEASED;

	public String value() {
		return name();
	}

	public static OtherStatus fromValue(String v) {
		return valueOf(v);
	}
}
