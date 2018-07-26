/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
/**
 * 
 */
package com.chest.currency.entity.model;

import java.math.BigDecimal;
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

import com.chest.currency.enums.CurrencyType;
import com.chest.currency.enums.VaultSize;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author root
 *
 */
@Entity(name = "BinCapacityDenomination")
@Table(name = "BIN_CAPACITY_DENOMINATION")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class BinCapacityDenomination {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "BIN_CAP_DENOM_SEQ")
	@SequenceGenerator(name = "BIN_CAP_DENOM_SEQ", sequenceName = "BIN_CAP_DENOM_SEQ", allocationSize = 100)
	protected Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "VAULT_SIZE")
	protected VaultSize vaultSize;
	
	@Basic
	@Column(name = "DENOMINATION")
	protected int denomination;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "CURRENCY_TYPE")
	protected CurrencyType currencyType;
	
	@Basic
	@Column(name = "MAX_BUNDLE_CAPACITY")
	protected BigDecimal maxBundleCapacity;
	
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
	
	/*@Basic
	@Column(name = "ICMC_ID" )
	protected BigInteger icmcId;*/
	
}
