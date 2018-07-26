/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.jpa.persistence.converter;

import java.text.Format;

import java.util.Locale;

import com.ibm.icu.util.Currency;

public class CurrencyFormatter {

	/*public static void main(String[] args) {
		Double number = new Double("10850000000");
		CurrencyFormatter cft = new CurrencyFormatter();
		System.out.println(cft.inrFormatter(number));
	}*/

	public static StringBuilder inrFormatter(Object amount) {
		StringBuilder formattedValue = new StringBuilder(Currency.getInstance(new Locale("en_IN")).getSymbol());
		try {
			Format formatter = com.ibm.icu.text.NumberFormat.getNumberInstance(new Locale("en", "IN"));
			formattedValue = formattedValue.append(formatter.format(amount));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formattedValue;
	}

}
