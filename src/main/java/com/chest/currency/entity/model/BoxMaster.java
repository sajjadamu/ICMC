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

import com.chest.currency.enums.CashSource;
import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity(name = "BoxMaster")
@Table(name = "BOX_MASTER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BoxMaster {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "BOX_MASTER_SEQ")
	@SequenceGenerator(name = "BOX_MASTER_SEQ", sequenceName = "BOX_MASTER_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "BOX_NAME")
	protected String boxName;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CASH_SOURCE")
	protected CashSource cashSource;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;
	
	@Basic
	@Column(name = "IS_ALLOCATED")
	protected Integer isAllocated;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "DENOMINATION")
	protected int denomination;

	@Basic
	@Column(name = "MAX_CAPACITY")
	protected BigDecimal maxCapacity;
	
	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;
	
	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME", nullable = false, updatable = false)
	@CreationTimestamp
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME", nullable = false)
	@UpdateTimestamp
	protected Calendar updateTime;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;
	
}
