/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

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

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name = "Machine")
@Table(name = "MACHINE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Machine {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "MACHINE_SEQ")
	@SequenceGenerator(name = "MACHINE_SEQ", sequenceName = "MACHINE_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@Basic
	@Column(name = "COMPANY_NAME")
	protected String companyname;

	@Basic
	@Column(name = "MACHINE_NO")
	protected Integer machineNo;

	@Basic
	@Column(name = "ASSET_CODE")
	protected String assetCode;

	@Basic
	@Column(name = "MACHINE_SI_NO")
	protected String machineSINo;

	@Transient
	protected Long ageing;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "PURCHASE_DATE")
	protected Date purchasedate;

	@Basic
	@Column(name = "MODEL_TYPE")
	protected String modelType;

	@Basic
	@Column(name = "STANDARD_PRODUCTIVITY")
	protected String standardProductivity;

	@Basic
	@Column(name = "SAP")
	protected Integer sap;

	@Basic
	@Column(name = "MAP")
	protected Integer map;

	@Basic
	@Column(name = "OSAP")
	protected Integer osap;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "PM_DATE")
	protected Date pmDate;

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
	@Column(name = "STATUS")
	protected Status status;

}
