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

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

@Entity(name = "RepeatabilityTestOutput")
@Table(name = "REPEATABILITY_TEST_OUTPUT")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class RepeatabilityTestOutput {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@GeneratedValue(generator = "REPEATABILITY_TEST_OUTPUT_SEQ")
	@SequenceGenerator(name = "REPEATABILITY_TEST_OUTPUT_SEQ", sequenceName = "REPEATABILITY_TEST_OUTPUT_SEQ", allocationSize = 100)
	@Column(name = "ID")
	protected Long id;
	
	@Basic
	@Column(name="CURRENCY_TYPE")
	protected String currencyType;

	@Basic
	@Column(name="MACHINE_NO")
	protected String machineNo;
	
	@Basic
	@Column(name="DENOMINATION")
	protected String denomination;
	
	@Basic
	@Column(name="TYPEVALUE")
	protected String typeValue;

	
	@Basic
	@Column(name="BUNDLE")
	protected String bundle;
	
	@Basic
	@Column(name="TOTAL_VALUE")
	protected String totalValue;
	
	@Basic
	@Column(name="BIN_NO")
	protected String binNo;
	
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

}
