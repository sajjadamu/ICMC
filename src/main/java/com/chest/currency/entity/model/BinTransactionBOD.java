/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.CashType;
import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "BinTransactionBOD")
@Table(name = "BIN_TRANSACTION_BOD_BALANCE")
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BinTransactionBOD {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "BIN_TRANSACTION_BOD_BAL_SEQ")
	@SequenceGenerator(name = "BIN_TRANSACTION_BOD_BAL_SEQ", sequenceName = "BIN_TRANSACTION_BOD_BAL_SEQ", allocationSize = 100)

	protected Long id;
	
	@Basic
	@Column(name = "DENOMINATION_1")
	protected BigDecimal denomination1;
	
	
	@Basic
	@Column(name = "DENOMINATION_2")
	protected BigDecimal denomination2;
	
	
	@Basic
	@Column(name = "DENOMINATION_5")
	protected BigDecimal denomination5;
	
	
	@Basic
	@Column(name = "DENOMINATION_10")
	protected BigDecimal denomination10;
	
	
	@Basic
	@Column(name = "DENOMINATION_20")
	protected BigDecimal denomination20;
	
	
	@Basic
	@Column(name = "DENOMINATION_50")
	protected BigDecimal denomination50;
	
	
	@Basic
	@Column(name = "DENOMINATION_100")
	protected BigDecimal denomination100;
	
	
	@Basic
	@Column(name = "DENOMINATION_500")
	protected BigDecimal denomination500;
	
	@Basic
	@Column(name = "DENOMINATION_200")
	protected BigDecimal denomination200;
	
	
	@Basic
	@Column(name = "DENOMINATION_1000")
	protected BigDecimal denomination1000;
	
	@Basic
	@Column(name = "DENOMINATION_2000")
	protected BigDecimal denomination2000;
	
	
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
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_TYPE")
	protected CashType cashType;
	
	@Basic
	@Column(name = "CURRENT_VERSION")
	protected String currentVersion;
	
	@Transient
	protected BigDecimal totalInPieces;
	
	@Transient
	protected BigDecimal totalValueOfBankNotes;
	
	@Transient
	protected BigDecimal totalCoinsPieces;
	
	@Transient
	protected BigDecimal totalValueOfCoins;
	
	@Transient
	protected BigDecimal anyOtherCoin;
	
}
