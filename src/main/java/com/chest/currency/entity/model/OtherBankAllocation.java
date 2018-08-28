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
import javax.persistence.Transient;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.OtherStatus;

@Entity(name = "OtherBankAllocation")
@Table(name = "OTHER_BANK_ALLOCATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class OtherBankAllocation {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "OTHER_BANK_ALLOCATION_SEQ")
	@SequenceGenerator(name = "OTHER_BANK_ALLOCATION_SEQ", sequenceName = "OTHER_BANK_ALLOCATION_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "OTHER_BANK_ID")
	protected Long otherBankId;

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

	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;

	@Basic
	@Column(name = "PENDING_BUNDLE")
	protected BigDecimal pendingBundle;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected OtherStatus status;

	@Transient
	protected String branchName;

	@Transient
	protected String bankName;

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
	protected BigDecimal totalValueOfBankNotes;

	public OtherBankAllocation() {

	}

	public OtherBankAllocation(boolean initialize) {
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
			this.totalValueOfBankNotes = BigDecimal.ZERO;
		}
	}
}
