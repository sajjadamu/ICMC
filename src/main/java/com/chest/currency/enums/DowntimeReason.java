/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.enums;

public enum DowntimeReason {
	STEPPER_MOTOR_PROBLEM, 
	DM1, 
	DM2,
	CM,
	SCREEN_PROBLEM,
	NOTES_STUCK_IN_SENSOR,
	HIGH_REJECTION,
	SOFTWARE_UPDATION,
	PM,
	HARDWARE_PROBLEM,
	OTHERS;

	public String value() {
		return name();
	}

	public static DowntimeReason fromValue(String v) {
		return valueOf(v);
	}
}
