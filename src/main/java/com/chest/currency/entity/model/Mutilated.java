/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.OtherStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Mutilated")
@Table(name = "MUTILATED")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Mutilated {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "MUTILATED_SEQ")
	@SequenceGenerator(name = "MUTILATED_SEQ", sequenceName = "MUTILATED_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

	@Basic
	@Column(name = "TOTAL")
	protected BigDecimal total;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_CATEGORY_TYPE")
	protected BinCategoryType binCategoryType;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;

	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_SOURCE")
	protected CashSource cashSource;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "OTHER_STATUS")
	protected OtherStatus otherStatus;

	@Transient
	protected BigDecimal denom1Pieces;
	@Transient
	protected BigDecimal denom2Pieces;
	@Transient
	protected BigDecimal denom5Pieces;
	@Transient
	protected BigDecimal denom10Pieces;
	@Transient
	protected BigDecimal denom20Pieces;
	@Transient
	protected BigDecimal denom50Pieces;
	@Transient
	protected BigDecimal denom100Pieces;
	@Transient
	protected BigDecimal denom200Pieces;
	@Transient
	protected BigDecimal denom500Pieces;
	@Transient
	protected BigDecimal denom1000Pieces;
	@Transient
	protected BigDecimal denom2000Pieces;
	@Transient
	protected BigDecimal totalInPieces;
	@Transient
	protected BigDecimal totalValue;
	@Transient
	protected Calendar insertedTime;

	@Transient
	protected BigDecimal totalDenomination1;
	@Transient
	protected BigDecimal totalDenomination2;
	@Transient
	protected BigDecimal totalDenomination5;
	@Transient
	protected BigDecimal totalDenomination10;
	@Transient
	protected BigDecimal totalDenomination20;
	@Transient
	protected BigDecimal totalDenomination50;
	@Transient
	protected BigDecimal totalDenomination100;
	@Transient
	protected BigDecimal totalDenomination200;
	@Transient
	protected BigDecimal totalDenomination500;
	@Transient
	protected BigDecimal totalDenomination1000;
	@Transient
	protected BigDecimal totalDenomination2000;
	
	@Transient
	protected BigDecimal totalUsingDenomination;

	public Mutilated() {

	}

	public Mutilated(boolean initialize) {
		if (initialize) {
			this.denom1Pieces = BigDecimal.ZERO;
			this.denom2Pieces = BigDecimal.ZERO;
			this.denom5Pieces = BigDecimal.ZERO;
			this.denom10Pieces = BigDecimal.ZERO;
			this.denom20Pieces = BigDecimal.ZERO;
			this.denom50Pieces = BigDecimal.ZERO;
			this.denom100Pieces = BigDecimal.ZERO;
			this.denom200Pieces = BigDecimal.ZERO;
			this.denom500Pieces = BigDecimal.ZERO;
			this.denom1000Pieces = BigDecimal.ZERO;
			this.denom2000Pieces = BigDecimal.ZERO;
			this.totalInPieces = BigDecimal.ZERO;
			this.totalValue = BigDecimal.ZERO;
			this.insertedTime = Calendar.getInstance();

			this.totalDenomination1 = BigDecimal.ZERO;
			this.totalDenomination2 = BigDecimal.ZERO;
			this.totalDenomination5 = BigDecimal.ZERO;
			this.totalDenomination10 = BigDecimal.ZERO;
			this.totalDenomination20 = BigDecimal.ZERO;
			this.totalDenomination50 = BigDecimal.ZERO;
			this.totalDenomination100 = BigDecimal.ZERO;
			this.totalDenomination200 = BigDecimal.ZERO;
			this.totalDenomination500 = BigDecimal.ZERO;
			this.totalDenomination1000 = BigDecimal.ZERO;
			this.totalDenomination2000 = BigDecimal.ZERO;
			
		}
	}
}
