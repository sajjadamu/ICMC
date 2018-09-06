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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "CRAPayment")
@Table(name = "CRA_PAYMENT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class CRAPayment {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "CRA_PAYMENT_SEQ")
	@SequenceGenerator(name = "CRA_PAYMENT_SEQ", sequenceName = "CRA_PAYMENT_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "BIN_NUM")
	protected String binNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "BIN_TYPE")
	protected CurrencyType currencyType;

	@Basic
	@Column(name = "DENOMINATION")
	protected Integer denomination;

	@Basic
	@Column(name = "BUNDLE")
	protected BigDecimal bundle;

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

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;
}
