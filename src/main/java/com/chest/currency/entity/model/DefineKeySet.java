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

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity(name="KeySet")
@Table(name="KEY_SET")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class DefineKeySet {

	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "KEY_SET_SEQ")
	@SequenceGenerator(name = "KEY_SET_SEQ", sequenceName = "KEY_SET_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "CUSTODIAN")
	protected String custodian;
	
	@Basic
	@Column(name = "KEY_NUMBER")
	protected String keyNumber;
	
	@Basic
	@Column(name = "LOCATION_OF_LOCK")
	protected String locationOfLock;
	
	@Basic
	@Column(name = "ICMC_ID")
	protected BigInteger icmcId;

	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;
	
	@Basic
	@Column(name = "UPDATE_BY")
	protected String updateBy;
	
	@Basic
	@Column(name = "INSERT_BY")
	protected String insertBy;
}
