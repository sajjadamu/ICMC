/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.BinCategoryType;
import com.chest.currency.enums.BinStatus;
import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CashType;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.YesNo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author root
 *
 */
@Entity(name = "BinTransaction")
@Table(name = "BIN_TRANSACTION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BinTransaction {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "BIN_TRANSACTION_SEQ")
	@SequenceGenerator(name = "BIN_TRANSACTION_SEQ", sequenceName = "BIN_TRANSACTION_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

	@Basic
	@Column(name = "DENOMINATION")
	protected int denomination;

	@Basic
	@Column(name = "MAX_CAPACITY")
	protected BigDecimal maxCapacity;

	@Basic
	@Column(name = "RECEIVE_BUNDLE")
	protected BigDecimal receiveBundle;

	@Transient
	protected BigDecimal currentBundle;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_TYPE")
	protected CurrencyType binType;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected BinStatus status;

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

	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;

	@Basic
	@Column(name = "PENDING_BUNDLE_REQUEST",nullable = false)
	protected BigDecimal pendingBundleRequest=new BigDecimal(0);

	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_SOURCE")
	protected CashSource cashSource;

	@Enumerated(EnumType.STRING)
	@Column(name = "VERIFIED")
	protected YesNo verified;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_CATEGORY_TYPE")
	protected BinCategoryType binCategoryType;

	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_TYPE")
	protected CashType cashType;

	@Basic
	@Column(name = "RBI_ORDER_NO")
	protected String rbiOrderNo;

	@Basic
	@Column(name = "ACTIVE")
	protected int active;

	
	@Transient
	private BigDecimal rcvBundle;
	@Transient
	private BigDecimal fresh;
	@Transient
	private BigDecimal unprocess;
	@Transient
	private BigDecimal issuable;
	@Transient
	private BigDecimal ATM;
	@Transient
	private BigDecimal soiled;
	@Transient
	private BigDecimal mutilated;
	@Transient
	private BigDecimal total;
	@Transient
	private BigDecimal value;

	@Transient
	private String name;

	@Transient
	protected String color;

	@Transient
	private BigDecimal extractBundleForSoiled;

	@Transient
	private boolean isDirty;

}
