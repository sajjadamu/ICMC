package com.chest.currency.enums;

public enum BinCategoryType {
	BIN, BOX, BAG,PROCESSING;

	public String value() {
		return name();
	}

	public static BinCategoryType fromValue(String v) {
		return valueOf(v);
	}
}
