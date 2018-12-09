package com.chest.currency.enums;

public enum BinColorType {

	UNPROCESS("#c7ff00"), ATM("#ffff00"), FRESH("#ffa000"), SOILED("#ff0000"), ISSUABLE("#ad7fea");

	String color;

	public String getColor() {
		return color;
	}

	BinColorType(String color) {
		this.color = color;
	}

	public String value() {
		return name();
	}

	public static BinColorType fromValue(String v) {
		return valueOf(v);
	}

	public static void main(String[] args) {
		System.out.println("color" + BinColorType.ATM.getColor());
	}
}
