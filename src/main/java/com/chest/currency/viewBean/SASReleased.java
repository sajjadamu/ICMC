/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.viewBean;

import java.math.BigDecimal;

import com.chest.currency.enums.CurrencyType;

import lombok.Data;

/**
 * @author root
 *
 */
@Data
public class SASReleased {
	private CurrencyType category;
	private Integer denomination;
	private BigDecimal bundle;
	private Long id;
	private String QR;
}
