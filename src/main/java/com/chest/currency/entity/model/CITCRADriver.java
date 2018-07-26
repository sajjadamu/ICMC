/*******************************************************************************
 * /* Copyright (C) Indicsoft Technologies Pvt Ltd
 * * All Rights Reserved.
 *******************************************************************************/
package com.chest.currency.entity.model;

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

import org.springframework.format.annotation.DateTimeFormat;

import com.chest.currency.enums.DateTimePattern;
import com.chest.currency.enums.Status;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity(name = "CITCRADriver")
@Table(name = "CIT_CRA_DRIVER")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@EqualsAndHashCode(of = {"id"})
@ToString
public class CITCRADriver {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	@GeneratedValue(generator = "CIT_CRA_DRIVER_SEQ")
	@SequenceGenerator(name = "CIT_CRA_DRIVER_SEQ", sequenceName = "CIT_CRA_DRIVER_SEQ", allocationSize = 100)
	protected Long id;
	
	@Basic
	@Column(name = "VENDOR_NAME")
	protected String vendorName;
	
	@Basic
	@Column(name = "VEHICLE_NUMBER")
	protected String vehicleNumber;
	
	@Basic
	@Column(name = "DRIVER_NAME")
	protected String driverName;
	
	@Basic
	@Column(name = "LICENSE_NUMBER")
	protected String licenseNumber;
	
	@Basic
	@Column(name = "LICENSE_ISSUED_STATE")
	protected String licenseIssuedState;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "LICENSE_ISSUE_DATE")
	protected Date licenseIssuedDated;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd)
	@Column(name = "LICENSE_EXPIRY_DATE")
	protected Date licenseExpiryDate;
	
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
	@Column(name = "INSERT_TIME")
	protected Calendar insertTime;
	
	@DateTimeFormat(pattern = DateTimePattern.yyyy_MM_dd_HH_mm_ss_SSS)
	@Column(name = "UPDATE_TIME")
	protected Calendar updateTime;

	@Basic
	@Column(name = "REASON_FOR_DELETION")
	protected String reasonForDeletion;
	
	@Basic
	@Column(name = "APPROVAL_FOR_DELETION")
	protected String approvalForDeletion;
	
}
