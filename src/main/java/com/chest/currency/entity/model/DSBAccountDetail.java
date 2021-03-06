/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "DSBAccountDetail")
@Table(name = "DSB_ACCOUNT_DETAIL")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class DSBAccountDetail {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "DSB_ACCOUNT_DETAIL_SEQ")
	@SequenceGenerator(name = "DSB_ACCOUNT_DETAIL_SEQ", sequenceName = "DSB_ACCOUNT_DETAIL_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "DSB_VENDOR_NAME")
	protected String dsbVendorName;
	
	@Basic
	@Column(name = "ACCOUNT_NUMBER")
	protected String accountNumber;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

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
	
}
