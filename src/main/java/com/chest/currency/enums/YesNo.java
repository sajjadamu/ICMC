/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum YesNo {
	Yes, No,NULL,YES,NO;

	public String value() {
		return name();
	}

	public static YesNo fromValue(String v) {
		return valueOf(v);
	}
}
