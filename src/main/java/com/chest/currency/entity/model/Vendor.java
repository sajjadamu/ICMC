/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;

@Entity(name = "Vendor")
@Table(name = "VENDOR")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = { "id" })
@ToString
public class Vendor {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "VENDOR_SEQ")
	@SequenceGenerator(name = "VENDOR_SEQ", sequenceName = "VENDOR_SEQ", allocationSize = 100)
	protected Long id;

	@Basic
	@Column(name = "NAME")
	protected String name;

	@Basic
	@Column(name = "ADDRESS")
	protected String address;

	@Basic
	@Column(name = "LOCATION")
	protected String location;

	@Basic
	@Column(name = "CITY")
	protected String city;

	@Basic
	@Column(name = "PINCODE")
	protected Integer pincode;

	@Basic
	@Column(name = "PHONE_NUMBER")
	protected Long phoneNumber;

	@Basic
	@Column(name = "EMAIL_ID")
	protected String emailID;

	@Basic
	@Column(name = "PF_REG_NUMBER")
	protected String pfRegNumber;

	@Basic
	@Column(name = "ESIC_REG_NUMBER")
	protected String esicRegNumber;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	protected Status status;

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
