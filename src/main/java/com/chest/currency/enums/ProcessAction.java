/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum ProcessAction {
	
	MACHINE,
	MANUAL;

	public String value() {
		return name();
	}

	public static ProcessAction fromValue(String v) {
		return valueOf(v);
	}
}
