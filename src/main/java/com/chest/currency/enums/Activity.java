package com.chest.currency.enums;

public enum Activity {

	CREATE, MODIFY, DELETE, UNLOCK, DELETE_ROLE;

	public String value() {
		return name();

	}

	public static Activity fromValue(String v) {
		return valueOf(v);
	}

}
