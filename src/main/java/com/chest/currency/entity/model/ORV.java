/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.chest.currency.enums.BinCategoryType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "ORV")
@Table(name = "ORV")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class ORV {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "ORV_SEQ")
	@SequenceGenerator(name = "ORV_SEQ", sequenceName = "ORV_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "SR")
	protected String sr;
	
	@Basic
	@Column(name = "SACK_LOCK_NUMBER")
	protected String sackLockNumber;
	
	@Basic
	@Column(name = "SOL_ID")
	protected String solId;

	@Basic
	@Column(name = "BRANCH")
	protected String branch;
	
	@Basic
	@Column(name = "VENDOR")
	protected String vendor;
	
	@Basic
	@Column(name = "CUSTODIAN")
	protected String custodian;
	
	
	@Basic
	@Column(name = "VEHICLE")
	protected String vehicle;
	
	@Basic
	@Column(name = "STATUS")
	protected int status;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;
	
	@Basic
	@Column(name = "ACCOUNT_NUMBER")
	protected String accountNumber;

	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;

	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;
	
	@Basic
	@Column(name="PROCESSED_OR_UNPROCESSED")
	protected String processedOrUnprocessed;
	
	@Transient
	@Enumerated(EnumType.STRING)
	protected BinCategoryType binCategoryType;
	
	@OneToMany (cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="ORV_ID", referencedColumnName="ID")
	protected List<ORVAllocation> orvAllocations;
}
